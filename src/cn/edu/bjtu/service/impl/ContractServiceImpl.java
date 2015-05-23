package cn.edu.bjtu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.BaseDao;
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
	BaseDao baseDao;
	@Resource
	HQLTool hqltool;

	
	@Override
	/**
	 * ��ȡ��˾��ͬ
	 */
	public List getCompanyContract(String carrierId) {
		// TODO Auto-generated method stub
		
		return contractDao.getCompanyContract(carrierId);
	}
	@Override
	/**
	 * ��ȡ��ͬ��Ϣ
	 */
	public Contract getContractInfo(String contractId) {
		// TODO Auto-generated method stub
		return contractDao.getContractInfo(contractId);
	}
	@Override
	/**
	 * ������ͬ
	 */
	public boolean insertContract(String id,String name, String caculateType,
			String carrierAccount, String startDate, String endDate,
			String contact, String phone, String remarks, String carrierId,
			String monthlyStatementDays, String path, String fileName) {
		// TODO Auto-generated method stub
		contract.setCaculateType(caculateType);
		contract.setCarrierAccount(carrierAccount);
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
		baseDao.save(contract);//����ʵ��
		return false;
		
	}
	@Override
	/**
	 * ��ֹ��ͬ
	 */
	public boolean shutdownContract(String contractId, String reason) {
		// TODO Auto-generated method stub
		return contractDao.shutdownContract(contractId,reason);
	}
	
	@Override
	/**
	 * ��ѯ��ͬ
	 */
	public List getFindContract(String carrierId,String startDate,String endDate,String name,int Display,int PageNow){
		String sql="from Contract where carrierId='"+carrierId+"' and ";
		System.out.println("name1="+name+"startDate="+startDate+"endDate="+endDate);
		if(name.equals("��ͬ����")){
			//����ʱ�����Ǻ�ͬ����
			System.out.println("name2="+name);
			name = "";
			System.out.println("name3="+name);
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
		//System.out.println("name1="+name);
		if(name.equals("��ͬ����")){
			//����ʱ�����Ǻ�ͬ����
			//System.out.println("name2="+name);
			name = "";
			//System.out.println("name3="+name);
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

}
