package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bjtu.dao.ClientDao;
import cn.edu.bjtu.dao.CompanyDao;
import cn.edu.bjtu.dao.RegisterDao;
import cn.edu.bjtu.dao.UserinfoDao;
import cn.edu.bjtu.service.RegisterService;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Clientinfo;
import cn.edu.bjtu.vo.Userinfo;
@Service("registerServiceImpl")
/**
 * 
 * @author RussWest0
 *
 */
public class RegisterServiceImpl implements RegisterService{

	@Autowired
	UserinfoDao userinfoDao;
	@Autowired
	ClientDao clientDao;
	@Autowired
	CompanyDao companyDao;	
	@Resource
	Carrierinfo carrierinfo;
	@Resource 
	Userinfo userInfo;
	@Resource
	Clientinfo clientInfo;
	@Resource
	RegisterDao registerDao;
	@Override
	public String register(String username, String password, String phone,int userKind) {
		// TODO Auto-generated method stub
		userInfo.setUsername(username);
		userInfo.setPhone(phone);
		userInfo.setId(IdCreator.createClientId());
		userInfo.setPassword(password);//δ����
		userInfo.setStatus("δ��֤");
		userInfo.setEmailStatus("δ��");
		userInfo.setPhoneStatus("�Ѱ�");/////////////��Ҫ�޸�
		userInfo.setSecurityQuestionStatus("δ����");
		//userInfo.setPrivilege(privilege);
		userInfo.setStatus("δ��֤");
		userInfo.setUserKind(userKind);
		
		if(userKind == 1){//�����û�
		clientInfo.setId(userInfo.getId());//ͬʱ����Ϣ���б���ʵ��
		//clientInfo.setCarrierId(carrierId);
		clientInfo.setCreateDate(new Date());
		clientInfo.setPhone(phone);
		clientDao.save(clientInfo);
		}
		else //��ҵ�û�
		{
			carrierinfo.setPhone(phone);
			carrierinfo.setId(userInfo.getId());
			carrierinfo.setStatus("δ��֤");
			companyDao.save(carrierinfo);
		}
		//clientInfo.setStatus("δ��֤");//�����û� �����ó�δ��֤ 
		userinfoDao.save(userInfo);//����ʵ��
		return userInfo.getId();
	}
	@Override
	/**
	 * ����û���
	 */
	public List getUserCheck(String username) {
		// TODO Auto-generated method stub
		return registerDao.getUserCheck(username);
	}
	
	

}
