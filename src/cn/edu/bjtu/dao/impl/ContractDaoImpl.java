package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.ContractDao;
import cn.edu.bjtu.vo.Contract;


import cn.edu.bjtu.util.HQLTool;
@Repository
/**
 * ��ͬdao��ʵ��
 * @author RussWest0
 *
 */
public class ContractDaoImpl implements ContractDao{

	@Resource
	HibernateTemplate ht;
	@Resource
	BaseDao baseDao;
	@Resource 
	private HQLTool hqltool;
	
	Contract contract=null;
	@Override
	/**
	 * ���ع�˾��ͬ
	 */
	public List getCompanyContract(String carrierId) {
		// TODO Auto-generated method stub
		return ht.find("from Contract where carrierId='"+carrierId+"'");
	}
	@Override
	/**
	 * ���غ�ͬ��Ϣ
	 */
	public Contract getContractInfo(String contractId) {
		// TODO Auto-generated method stub
		return ht.get(Contract.class, contractId);
	}
	@Override
	/**
	 * ��ֹ��ͬ
	 */
	public boolean shutdownContract(String contractId, String reason) {
		// TODO Auto-generated method stub
		contract=ht.get(Contract.class, contractId);
		contract.setState("����ֹ");//����״̬
		contract.setReason(reason);
		return baseDao.update(contract);		
	}
	
	
	@Override
	public List getFindContract(String hql, int display, int pageNow) {
		// TODO Auto-generated method stub
		//System.out.println("hql+"+hql);
		int page = pageNow;
		int pageSize = display;
		
		return hqltool.getQueryList(hql, page, pageSize);//Dao���ҳ������ȡ���˷���
	}
	
}
