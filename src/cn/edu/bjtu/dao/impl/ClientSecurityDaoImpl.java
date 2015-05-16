package cn.edu.bjtu.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.ClientSecurityDao;
import cn.edu.bjtu.vo.Clientinfo;
import cn.edu.bjtu.vo.Userinfo;
@Repository
public class ClientSecurityDaoImpl implements ClientSecurityDao{
	
	@Autowired
	private HibernateTemplate ht;
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	/**
	 * ��������
	 */
	public boolean checkOldPassword(String oldPassword,String userId) {
		// TODO Auto-generated method stub
		Userinfo user=ht.get(Userinfo.class, userId);
		
		if(user.getPassword().equals(oldPassword))
			return true;
		return false;
	}

	@Override
	/**
	 * �޸�����
	 */
	public boolean changePassword(String newPassword, String userId) {
		// TODO Auto-generated method stub
		Userinfo user=ht.get(Userinfo.class, userId);
		user.setPassword(newPassword);
		return baseDao.update(user);
	}

	@Override
	/**
	 * ������
	 */
	public boolean bindEmail(String email, String userId) {
		// TODO Auto-generated method stub
		Userinfo user=ht.get(Userinfo.class, userId);
		user.setEmail(email);
		user.setEmailStatus("�Ѱ�");//�޸�״̬
		Clientinfo clientinfo=ht.get(Clientinfo.class,userId);
		clientinfo.setEmail(email);
		
		return baseDao.update(user) && baseDao.update(clientinfo);
	}

	@Override
	/**
	 * �����û���Ϣ
	 */
	public Userinfo getUserById(String userId) {
		// TODO Auto-generated method stub
		return ht.get(Userinfo.class, userId);
	}

	@Override
	/**
	 * �޸İ�����
	 */
	public boolean changeBindEmail(String newEmail, String userId) {
		// TODO Auto-generated method stub
		Userinfo userinfo=ht.get(Userinfo.class, userId);
		
		userinfo.setEmail(newEmail);
		
		Clientinfo clientinfo=ht.get(Clientinfo.class, userId);
		
		clientinfo.setEmail(newEmail);
		
		return baseDao.update(userinfo) && baseDao.update(clientinfo);
		
	}
	
	

}
