package cn.edu.bjtu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.bjtu.dao.LoginDao;
import cn.edu.bjtu.service.LoginService;
import cn.edu.bjtu.vo.Userinfo;
@Service
public class LoginServiceImpl implements LoginService{	
	
	@Resource(name="loginDaoImpl")
	private LoginDao loginDao;
	
	@Override
	public Userinfo checkLogin(String username, String password) {
		// TODO Auto-generated method stub
		
		/*�������һЩ�߼�����*/
		
		
		return loginDao.checkLogin(username, password); 
	}

}
