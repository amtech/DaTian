package cn.edu.bjtu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.service.RegisterService;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Clientinfo;
import cn.edu.bjtu.vo.Userinfo;
@Service("registerServiceImpl")
/**
 * 
 * @author RussWest0
 *
 */
public class RegisterServiceImpl implements RegisterService{

	@Resource 
	BaseDao baseDao;
	@Resource 
	Userinfo userInfo;
	@Resource
	Clientinfo clientInfo;
	@Override
	public String register(String username, String password, String phone) {
		// TODO Auto-generated method stub
		userInfo.setUsername(username);
		userInfo.setPhone(phone);
		userInfo.setId(IdCreator.createClientId());
		userInfo.setPassword(password);//δ����
		userInfo.setStatus("δ��֤");
		clientInfo.setId(userInfo.getId());//ͬʱ����Ϣ���б���ʵ��
		//clientInfo.setStatus("δ��֤");//�����û� �����ó�δ��֤ 
		baseDao.save(userInfo);//����ʵ��
		
		baseDao.save(clientInfo);
		//registerInfo(userInfo.getId());
		return userInfo.getId();
	}
	/*@Override
	public boolean registerInfo(String userId) {
		// TODO Auto-generated method stub
		clientInfo.setId(userId);
		return baseDao.save(clientInfo);
	}*/
	
	

}
