package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.BusinessClientDao;
import cn.edu.bjtu.dao.ClientDao;
import cn.edu.bjtu.dao.UserinfoDao;
import cn.edu.bjtu.service.ClientService;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Businessclient;
import cn.edu.bjtu.vo.Clientinfo;
import cn.edu.bjtu.vo.Userinfo;
/**
 * client�����ʵ��
 * @author RussWest0
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService{

	@Autowired	
	ClientDao clientDao;
	@Autowired
	BusinessClientDao businessClientDao;
	@Resource
	Businessclient businessClient;
	@Autowired
	UserinfoDao userinfoDao;
	
	@Override
	/**
	 *���ع�˾�ͻ� 
	 */
	public List getCompanyClient(String carrierId) {
		// TODO Auto-generated method stub
		return clientDao.getCompanyClient(carrierId);
	}
	@Override
	/**
	 * ͨ��id��ȡ�ͻ���Ϣ
	 */
	public Clientinfo getClientInfo(String clientId) {
		// TODO Auto-generated method stub
		return clientDao.getClientInfo(clientId);
	}
	
	@Override
	/**
	 * ��ȡ�ͻ���Ϣ(businessclient)
	 */
	public Businessclient getBusinessclientInfo(String businessclientId) {
		// TODO Auto-generated method stub
		return clientDao.getBusinessclientInfo(businessclientId);
	}
	
	@Override
	/**
	 * �����ͻ�s
	 */
	public boolean insertBusinessClient(String account, String clientName,
			String clientBusiness, String contact, String phone,
			String remarks, String carrierId,String path,String fileName) {
		// TODO Auto-generated method stub
		businessClient.setAccount(account);
		businessClient.setCarrierId(carrierId);
		businessClient.setClientBusiness(clientBusiness);
		businessClient.setClientName(clientName);
		businessClient.setContact(contact);
		businessClient.setId(IdCreator.createBusinessClientId());
		businessClient.setPhone(phone);
		businessClient.setRelDate(new Date());
		businessClient.setRemarks(remarks);
		
		// �����ļ�·��
		if(path!=null && fileName!=null) {
			String fileLocation = path + "//" + fileName;
			businessClient.setRelatedMaterial(fileLocation);
		}
		businessClientDao.save(businessClient);//����ʵ��
		return true;
	}
	
	@Override
	/**
	 * ���¿ͻ�
	 */
	public boolean updateBusinessClient(String id, String account, String clientName,
			String clientBusiness, String contact, String phone,
			String remarks, String carrierId,String path,String fileName) {
		// TODO Auto-generated method stub
		businessClient=getBusinessclientInfo(id);//����id���ҵ��ͻ���Ϣ
		businessClient.setAccount(account);
		businessClient.setClientName(clientName);
		businessClient.setClientBusiness(clientBusiness);
		businessClient.setContact(contact);
		businessClient.setPhone(phone);
		businessClient.setRelDate(new Date());
		businessClient.setRemarks(remarks);
		businessClient.setCarrierId(carrierId);
		
		// �����ļ�·��
		if(path!=null && fileName!=null) {
			String fileLocation = path + "//" + fileName;
			businessClient.setRelatedMaterial(fileLocation);
		}
		businessClientDao.update(businessClient);//����ʵ��
		return true;
	}
	@Override
	/**
	 * ɾ���ͻ�
	 */
	public boolean deleteBusinessClient(String id){
		businessClient=getBusinessclientInfo(id);//����id���ҵ��ͻ���Ϣ
		businessClientDao.delete(businessClient);
		return true;
	}
	@Override
	public String getBasicUserInfo(String userId) {
		// TODO Auto-generated method stub
		return clientDao.getBasicUserInfo(userId);
	}
	@Override
	/**
	 * ����û�ͷ�����õ�״̬
	 */
	public boolean checkHeadIconStatus(String userId) {
		// TODO Auto-generated method stub
		Userinfo userinfo=userinfoDao.get(Userinfo.class, userId);
		if(userinfo !=null){
			if(userinfo.getHeadIcon().equals("������")){
				return true;//������ͷ��
			}else 
				return false;//δ����ͷ��
		}
		return false;
		
	}
	@Override
	public String getStatus(String userId) {
		// TODO Auto-generated method stub
		return clientDao.getStatus(userId);
	}
	@Override
	/**
	 * �����û���Ϣ��֤
	 */
	public boolean validateUser(String userId, String realName, String phone,
			String IDCard, String sex, String path, String fileName) {
		// TODO Auto-generated method stub
		return clientDao.validateUser(userId,realName,phone,IDCard,sex, path, fileName);
	}
	@Override
	/**
	 *  ���¸����û���Ϣ
	 */
	public boolean updateClientinfo(Clientinfo clientinfo, String path,
			String fileName, String userId) {
		// TODO Auto-generated method stub
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			clientinfo.setIDPicture(fileLocation);//�����ļ��ϴ�·��
		}
		Userinfo userinfo=userinfoDao.get(Userinfo.class,userId);
		// add by RussWest0 at 2015��6��4��,����8:23:30 
//		���º���ʾ�����
		userinfo.setStatus("�����");
		clientinfo.setId(userId);
		
		userinfoDao.update(userinfo);
		clientDao.update(clientinfo);//������Ϣ
		return true;
	}
	
	
	
	
	
}
