package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.vo.Address;

import com.alibaba.fastjson.JSONArray;


public interface AddressService {

	public List getAddress(String userId);
	public Address getAddressDetail(String id);
	public boolean deleteAddress(String id);
	
	//����address��address��������������address������
	public boolean insertAddress(String name, String paramaddress, String phone, String clientId);
	
	public boolean updateAddress(String id, String name, String paramaddress, String phone);
	
	/**
	 * ����û����õ�ַ
	 * @param session
	 * @param address
	 */
	public void addUserAddress(HttpSession session,Address address);
	
	/**
	 * �¶���ʱ��ȡ�û��ĳ��õ�ַ�б�
	 * @param session
	 * @return
	 */
	public JSONArray getUserFrequentAddress(HttpSession session);
}
