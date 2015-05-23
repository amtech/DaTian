package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.AddressDao;
import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.service.AddressService;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Address;

@Transactional
@Service("AddressServiceImpl")
/**
 * ���˻������ʵ�� 
 * 
 *
 */
public class AddressServiceImpl implements AddressService{
	
	@Resource
	HibernateTemplate ht;
	@Resource 
	BaseDao baseDao;
	@Resource 
	AddressDao addressDao;
	@Resource 
	Address address;
	
	@Override
	/**
	 * ��ȡ��ַ�б�
	 */
	public List getAddress(String userId) {
		// TODO Auto-generated method stub
		
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
		 baseDao.save(address);
		 return true;
	}
	
	@Override
	public boolean updateAddress(String id, String name, String paramaddress, String phone){
	
		address = addressDao.getAddressDetail(id);// ����id����

		address.setAddress(paramaddress);
		address.setName(name);
		address.setPhone(phone);
		baseDao.update(address);
		return true;
	}
	
}
