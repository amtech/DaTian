package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Contract;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * @author RussWest0
 *
 */
public interface ContractService {
	public List getCompanyContract(String carrierId);

	public Contract getContractInfo(String contractId);

	public boolean insertContract(String id,String name, String caculateType,
			String carrierAccount, String carrierId, String startDate, String endDate,
			String contact, String phone, String remarks, String clientId,
			String monthlyStatementDays,String path, String fileName);
	public boolean shutdownContract(String contractId,String reason);
	public List getFindContract(String clientId,String startDate,String endDate,String name,int Display,int PageNow);
	public int getFindContractTotalRows(String carrierId,String startDate,String endDate,String name,int Display,int PageNow);

	public boolean changeStatus(String id);

	public List<Contract> getContractByClientId(String clientId);

	List getFindContract2(String carrierId, String startDate, String endDate,
			String name, int Display, int PageNow);
	
	/**
	 * �ҵ���Ϣ-��ͬ��Ϣ
	 * @Title: getUserContract 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: JSONObject 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����5:44:24
	 */
	public JSONArray getUserContract(HttpSession session,PageUtil pageUtil);
	
	/**
	 * �ҵ���Ϣ-��ͬ��Ϣ-�ܼ�¼��
	 * @Title: getUserContractTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����5:46:09
	 */
	public Integer getUserContractTotalRows(HttpSession session);

}
