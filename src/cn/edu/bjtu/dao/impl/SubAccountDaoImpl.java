package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.SubAccountDao;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.vo.SubAccount;


@Repository

/**
 * ���˻�dao��ʵ��
 *
 */
public class SubAccountDaoImpl extends BaseDaoImpl<SubAccount> implements SubAccountDao{

	@Resource
	HibernateTemplate ht;
	/*@Resource
	BaseDao baseDao;*/
	/*@Autowired
	SubAccountDao subAccountDao;*/
	@Resource 
	private HQLTool hqltool;
	
	@Override
	public List getSubAccount(String userId) {
		
		return ht.find("from SubAccount where hostAccountId='"+userId+"'");
	}
	
	@Override
	public List getFindSubAccount(String sql){
		return hqltool.getQueryListSubAccount(sql);
	}
	
	@Override
	public boolean changeStatus(String id){
		
		SubAccount subAccount = (SubAccount) ht.get(SubAccount.class, id);
		String temp="";
		temp=subAccount.getStatus();
		if(temp.equals("��ͣ��")){
			subAccount.setStatus("����");// �޸�״̬
		}
		else if(temp.equals("����")){
			subAccount.setStatus("��ͣ��");// �޸�״̬
		}

		//return baseDao.update(subAccount);
		this.save(subAccount);
		return true;
	}
	
	@Override
	public boolean deleteSubAccount(String id){
		
		SubAccount subAccount = ht.get(SubAccount.class, id);
		this.delete(subAccount);
		 return true;
	}
}
