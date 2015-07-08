package cn.edu.bjtu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.bjtu.dao.ContractDao;
import cn.edu.bjtu.service.ContractService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.util.ParseDate;
import cn.edu.bjtu.util.UploadFile;
import cn.edu.bjtu.vo.Contract;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
	@Deprecated
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
	public boolean insertNewContract(Contract contract,HttpServletRequest request,MultipartFile file){
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		//�����ļ�
		String fileLocation=UploadFile.uploadFile(file, carrierId, "contract");

		
		//�����ļ�λ�� 
		contract.setRelatedMaterial(fileLocation);
		contractDao.save(contract);// ����ʵ��
		return true;
	}
	@Deprecated
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
	
	/**
	 * �ҵ���Ϣ-��ͬ��Ϣ
	 */
	@Override
	public JSONArray getUserContract(HttpSession session,PageUtil pageUtil) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		Integer userKind=(Integer)session.getAttribute(Constant.USER_KIND);
		String hql="from Contract t where ";
		if(userKind == 2){//�����û�
			hql+="t.clientId=:userId";
		}else if(userKind == 3){//��ҵ�û�
			hql+="t.carrierId=:userId";
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		int page=pageUtil.getCurrentPage()==0?1:pageUtil.getCurrentPage();
		int display=pageUtil.getDisplay()==0?10:pageUtil.getDisplay();
		List<Contract> contractList = contractDao.find(hql, params,page,display);
		JSONArray jsonArray = new JSONArray();
		for (Contract contract : contractList) {
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(contract);
			jsonArray.add(jsonObject);
		}
		
		return jsonArray;

	}

	/**
	 * �ҵ���Ϣ-��ͬ��Ϣ-�ܼ�¼��
	 */
	@Override
	public Integer getUserContractTotalRows(HttpSession session) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		Integer userKind=(Integer)session.getAttribute(Constant.USER_KIND);
		String hql="select count(*) from Contract t where ";
		if(userKind == 2){//�����û�
			hql+="t.clientId=:userId";
		}else if(userKind == 3){//��ҵ�û�
			hql+="t.carrierId=:userId";
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		
		Long count=contractDao.count(hql, params);
		return count.intValue();
	}

}
