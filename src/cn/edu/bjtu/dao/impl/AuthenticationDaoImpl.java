package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.AuthenticationDao;
import cn.edu.bjtu.dao.BaseDao;


import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.vo.Clientinfo;
import cn.edu.bjtu.vo.Goodsform;
import cn.edu.bjtu.vo.Userinfo;
@Repository
/**
 * ��ͬdao��ʵ��
 * @author RussWest0
 *
 */
public class AuthenticationDaoImpl implements AuthenticationDao{

	@Resource
	HibernateTemplate ht;
	@Resource
	BaseDao baseDao;
	@Resource 
	private HQLTool hqltool;
	
	Userinfo userinfo=null;
	@Override
	/**
	 * ������֤�б�
	 */
	public List getAllAuthentication() {
		// TODO Auto-generated method stub
		return ht.find("from Userinfo where status = '�����' or status = '�����'");
	}
	
	@Override
	/**
	 * ������֤��Ϣ
	 */
	public Clientinfo getAuthenticationInfo(String clientId) {
		// TODO Auto-generated method stub
		return ht.get(Clientinfo.class, clientId);
	}

	@Override
	/**
	 * ���ظ�����Ϣ
	 * @param clientId
	 */
	public Userinfo getMyUserDetail(String clientId) {
		// TODO Auto-generated method stub
		
		return ht.get(Userinfo.class,clientId);
		
	}

	
}