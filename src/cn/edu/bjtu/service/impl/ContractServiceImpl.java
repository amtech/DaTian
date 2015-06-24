package cn.edu.bjtu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.ContractDao;
import cn.edu.bjtu.service.ContractService;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.ParseDate;
import cn.edu.bjtu.vo.Contract;
@Transactional
@Service("contractServiceImpl")
/**
 * ��ͬ�����ʵ�� 
 * @author RussWest0
 *
 */
public class ContractServiceImpl implements ContractService{

	@Resource
	ContractDao contractDao;
	@Resource 
	Contract contract;
	@Resource
	HQLTool hqltool;

	
	@Override
	/**
	 * ��ȡ��˾��ͬ
	 */
	public List getCompanyContract(String carrierId) {
		
		
		return contractDao.getCompanyContract(carrierId);
	}
	
	
	@Override
	public List<Contract> getContractByClientId(String clientId) {
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("clientId", clientId);
		String hql="from Contract where clientId=:clientId";
		return contractDao.find(hql,params);
	}

	@Override
	/**
	 * ��ȡ��ͬ��Ϣ
	 */
	public Contract getContractInfo(String contractId) {
		
		return contractDao.getContractInfo(contractId);
	}
	@Override
	/**
	 * ������ͬ
	 */
	public boolean insertContract(String id,String name, String caculateType,
			String carrierAccount, String carrierId, String startDate, String endDate,
			String contact, String phone, String remarks, String clientId,
			String monthlyStatementDays,String path, String fileName) {
		
		contract.setCaculateType(caculateType);
		contract.setCarrierAccount(carrierAccount);
		contract.setClientId(clientId);
		contract.setCarrierId(carrierId);
		contract.setContact(contact);
		contract.setEndDate(ParseDate.parseDate(startDate));
		contract.setStartDate(ParseDate.parseDate(endDate));
		contract.setName(name);
		contract.setPhone(phone);
		contract.setRemarks(remarks);
		contract.setId(id);
		contract.setState("��ȷ��");
		if(monthlyStatementDays != ""){
			contract.setMonthlyStatementDays(monthlyStatementDays);
		}
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			contract.setRelatedMaterial(fileLocation);
		}
		contractDao.save(contract);//����ʵ��
		return true;
		
	}
	@Override
	/**
	 * ��ֹ��ͬ
	 */
	public boolean shutdownContract(String contractId, String reason) {
		
		return contractDao.shutdownContract(contractId,reason);
	}
	
	@Override
	/**
	 * ��ѯ��ͬ�����󷽣�
	 */
	public List getFindContract(String clientId,String startDate,String endDate,String name,int Display,int PageNow){
		String sql="from Contract where clientId='"+clientId+"' and ";
		if(name.equals("��ͬ����")){
			//����ʱ�����Ǻ�ͬ����
			name = "";
		}
		if(!startDate.equals("��ʼʱ��")){
			if(!endDate.equals("����ʱ��")){
				//��ѯʱ�п�ʼ�ͽ�ֹʱ��
				sql+=" startDate >= '"+startDate+"' and endDate <= '"+endDate+"' and name like '%"+name+"%'";
				
			}
			else{
				//ֻ�п�ʼ����û�н�ֹ����
				sql+=" startDate >= '"+startDate+"' and name like '%"+name+"%'";
				
			}
		}
		else if(!endDate.equals("����ʱ��")){
			//ֻ�н���ʱ��û�п�ʼʱ��
			sql+=" endDate <= '"+endDate+"' and name like '%"+name+"%'";
			
		}
		else{
			//û��ʱ������
			sql+=" name like '%"+name+"%'";
		}
		return contractDao.getFindContract(sql, Display, PageNow);
	}
	
	@Override
	/**
	 * ��ѯ��ͬ�����˷���
	 */
	public List getFindContract2(String carrierId,String startDate,String endDate,String name,int Display,int PageNow){
		String sql="from Contract where carrierId='"+carrierId+"' and ";
		if(name.equals("��ͬ����")){
			//����ʱ�����Ǻ�ͬ����
			name = "";
		}
		if(!startDate.equals("��ʼʱ��")){
			if(!endDate.equals("����ʱ��")){
				//��ѯʱ�п�ʼ�ͽ�ֹʱ��
				sql+=" startDate >= '"+startDate+"' and endDate <= '"+endDate+"' and name like '%"+name+"%'";
				
			}
			else{
				//ֻ�п�ʼ����û�н�ֹ����
				sql+=" startDate >= '"+startDate+"' and name like '%"+name+"%'";
				
			}
		}
		else if(!endDate.equals("����ʱ��")){
			//ֻ�н���ʱ��û�п�ʼʱ��
			sql+=" endDate <= '"+endDate+"' and name like '%"+name+"%'";
			
		}
		else{
			//û��ʱ������
			sql+=" name like '%"+name+"%'";
		}
		return contractDao.getFindContract(sql, Display, PageNow);
	}
	
	@Override
	/**
	 * ��ѯ��ͬ�Ľ��ҳ��
	 */
	public int getFindContractTotalRows(String carrierId,String startDate,String endDate,String name,int Display,int PageNow){
		String sql="from Contract where carrierId='"+carrierId+"' and ";
		if(name.equals("��ͬ����")){
			//����ʱ�����Ǻ�ͬ����
			name = "";
		}
		if(!startDate.equals("��ʼʱ��")){
			if(!endDate.equals("����ʱ��")){
				//��ѯʱ�п�ʼ�ͽ�ֹʱ��
				sql+=" startDate >= '"+startDate+"' and endDate <= '"+endDate+"' and name like '%"+name+"%'";
				
			}
			else{
				//ֻ�п�ʼ����û�н�ֹ����
				sql+=" startDate >= '"+startDate+"' and name like '%"+name+"%'";
				
			}
		}
		else if(!endDate.equals("����ʱ��")){
			//ֻ�н���ʱ��û�п�ʼʱ��
			sql+=" endDate <= '"+endDate+"' and name like '%"+name+"%'";
			
		}
		else{
			//û��ʱ������
			sql+=" name like '%"+name+"%'";
		}
		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}
	@Override
	public boolean changeStatus(String id) {
		
		return contractDao.changeStatus(id);
	}
	
}
