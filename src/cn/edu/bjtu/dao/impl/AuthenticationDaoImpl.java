package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.AuthenticationDao;

import cn.edu.bjtu.vo.Clientinfo;
import cn.edu.bjtu.vo.Userinfo;
@Repository
/**
 * ��ͬdao��ʵ��
 * @author RussWest0
 *
 */
public class AuthenticationDaoImpl extends BaseDaoImpl<Userinfo> implements AuthenticationDao{

	@Resource
	HibernateTemplate ht;
	
	Userinfo userinfo=null;
	@Override
	/**
	 * ������֤�б�
	 */
	public List<Userinfo> getAllAuthentication() {
		
		return this.find("from Userinfo where status = '�����' or status = '�����'");
	}
	
	@Override
	/**
	 * ������֤��Ϣ
	 */
	public Clientinfo getAuthenticationInfo(String clientId) {
		
		return ht.get(Clientinfo.class, clientId);
	}

	@Override
	/**
	 * ���ظ�����Ϣ
	 * @param clientId
	 */
	public Userinfo getMyUserDetail(String clientId) {
		
		
		return ht.get(Userinfo.class,clientId);
		
	}

	@Override
	public List getFindUser(String username) {
		
		String sql = "from Userinfo where (status = '�����' or status = '�����') ";
			if (username.equals("�û���")) {
				// ����ʱ�������û���
			} else
				sql += "and username like '%" + username + "%'";
		return ht.find(sql);
	}

	
}