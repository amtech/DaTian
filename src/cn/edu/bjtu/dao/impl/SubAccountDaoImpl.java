package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.SubAccountDao;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.vo.SubAccount;


@Repository
/**
 * ���˻�dao��ʵ��
 *
 */
public class SubAccountDaoImpl implements SubAccountDao{

	@Resource
	HibernateTemplate ht;
	@Resource
	BaseDao baseDao;
	@Resource 
	private HQLTool hqltool;
	
	@Override
	public List getSubAccount(String userId) {
		// TODO Auto-generated method stub
		return ht.find("from SubAccount where hostAccountId='"+userId+"'");
	}
	
	@Override
	public List getFindSubAccount(String sql){
		return hqltool.getQueryListSubAccount(sql);
	}
	
	@Override
	public SubAccount getSubAccountDetail(String id){
		return ht.get(SubAccount.class, id);
	}
	
	@Override
	public boolean changeStatus(String id){
		
		SubAccount subAccount = (SubAccount) ht.get(SubAccount.class, id);
		//System.out.println("orderEntity+" + order);
		String temp="";
		temp=subAccount.getStatus();
		if(temp.equals("��ͣ��")){
			subAccount.setStatus("����");// �޸�״̬
		}
		else if(temp.equals("����")){
			subAccount.setStatus("��ͣ��");// �޸�״̬
		}

		return baseDao.update(subAccount);
	}
	
	@Override
	public boolean deleteSubAccount(String id){
		
		SubAccount subAccount = ht.get(SubAccount.class, id);
		return baseDao.delete(subAccount);
	}
}
