package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.ContractDao;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.vo.Contract;
import cn.edu.bjtu.vo.SubAccount;
@Repository
/**
 * ��ͬdao��ʵ��
 * @author RussWest0
 *
 */
public class ContractDaoImpl extends BaseDaoImpl<Contract> implements ContractDao{

	@Resource
	HibernateTemplate ht;
	/*@Resource
	BaseDao baseDao;*/
	/*@Autowired
	ContractDao contractDao;*/
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
	
	/*@Override
	public List getCompanyContractForUser(String clientId) {
		// TODO Auto-generated method stub
		return this.find("from Contract where clientId='"+clientId+"'");
	}*/
	
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
		/*baseDao.update(contract);		*/
		this.update(contract);
		return true;
	}
	
	
	@Override
	public List getFindContract(String hql, int display, int pageNow) {
		// TODO Auto-generated method stub
		int page = pageNow;
		int pageSize = display;
		
		return hqltool.getQueryList(hql, page, pageSize);//Dao���ҳ������ȡ���˷���
	}
	@Override
	public boolean changeStatus(String id) {
		// TODO Auto-generated method stub
		Contract contract = (Contract) ht.get(Contract.class, id);
		String temp="";
		temp=contract.getState();
		if(temp.equals("��ȷ��")){
			contract.setState("��Ч");// �޸�״̬
		}

		//return baseDao.update(subAccount);
		this.update(contract);
		return true;
	}
	
}
