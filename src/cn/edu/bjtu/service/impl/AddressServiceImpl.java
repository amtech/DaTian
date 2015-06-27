package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.AddressDao;
import cn.edu.bjtu.service.AddressService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Address;

@Transactional
@Service
/**
 * ���˻������ʵ�� 
 * 
 *
 */
public class AddressServiceImpl implements AddressService{
	
	@Resource
	HibernateTemplate ht;
	/*@Resource 
	BaseDao baseDao;*/
	@Autowired
	AddressDao addressDao;
	@Resource 
	Address address;
	
	@Override
	/**
	 * ��ȡ��ַ�б�
	 */
	public List getAddress(String userId) {
		
		
		return addressDao.getAddress(userId);
	}
	
	
	@Override
	public Address getAddressDetail(String id){
		
		return addressDao.getAddressDetail(id);
	}
	
	
	@Override
	public boolean deleteAddress(String id){
		return addressDao.deleteAddress(id);
	}
	
	@Override
	/**
	 * ����address��address��������������address������
	 */
	public boolean insertAddress(String name, String paramaddress, String phone, String clientId){
		
		address.setId(IdCreator.createAddressId());
		address.setName(name);
		address.setAddress(paramaddress);
		address.setPhone(phone);
		address.setRelDate(new Date());
		address.setClientId(clientId);
		 addressDao.save(address);
		 return true;
	}
	
	@Override
	public boolean updateAddress(String id, String name, String paramaddress, String phone){
	
		address = addressDao.getAddressDetail(id);// ����id����

		address.setAddress(paramaddress);
		address.setName(name);
		address.setPhone(phone);
		addressDao.update(address);
		return true;
	}
	
	/**
	 * ����û����õ�ַ
	 */
	@Override
	public void addUserAddress(HttpSession session,Address address) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		address.setClientId(userId);
		address.setFrequency(0);
		address.setId(IdCreator.createAddressId());
		address.setRelDate(new Date());
		
		addressDao.save(address);
		
	}
	
}
