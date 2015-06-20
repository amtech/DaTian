package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.OrderDao;
import cn.edu.bjtu.dao.SettlementDao;
import cn.edu.bjtu.service.OrderService;
import cn.edu.bjtu.service.SettlementService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Orderform;
import cn.edu.bjtu.vo.Settlement;
@Service("settlementServiceImpl")
@Transactional
public class SettlmentServiceImpl implements SettlementService{
	@Resource
	HQLTool hqltool;
	@Resource
	SettlementDao settlementDao;
	/**
	 * ��ȡ�û��Ķ���
	 */
	@Override
	public List getUserSettlementList(String userId) {
		//����ɵĶ������ܽ���
		String hql="from SettlementCarrierView t where t.clientId=:clientId "
				+ " and t.state='�����'";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("clientId", userId);
		return settlementDao.find(hql, params);
	}
	/**
	 * ��ȡ���ɶ��˵��Ķ���
	 */
	@Override
	public List getOrderStatement(String orderNum) {
		// TODO Auto-generated method stub
		return settlementDao.getOrderStatement(orderNum);
	}
	@Override
	public List getFindSettlement(String carrierId, String name, int display, int pageNow) {
		// TODO Auto-generated method stub
		String sql="from SettlementCarrierView where carrierId='"+carrierId+"' and ";
		if(name.equals("���˷����ƻ���˷���ͬ���")){
			//����ʱ�����Ǻ�ͬ����
			name = "";
		}
		//û��ʱ������
		sql+=" (companyName like '%"+name+"%' or contractId like '%"+name+"%')";
		return settlementDao.getFindSettlement(sql, display, pageNow);
	}
	/*@Override
	public int getFindSettlementTotalRows(String carrierId, String name, int display, int pageNow) {
		// TODO Auto-generated method stub
		String sql="from SettlementCarrierView where carrierId='"+carrierId+"' and ";
		if(name.equals("���˷����ƻ���˷���ͬ���")){
			//����ʱ�����Ǻ�ͬ����
			name = "";
		}
		//û��ʱ������
		sql+=" (companyName like '%"+name+"%' or contractId like '%"+name+"%')";
		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}*/
	
	
	

}
