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
	@Autowired
	OrderDao orderDao;
	/**
	 * 获取用户的订单
	 */
	@Override
	public List getUserSettlementList(String userId) {
		//已完成的订单才能结算
		String hql="from SettlementCarrierView t where t.clientId=:clientId "
				+ " and t.state='已完成'";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("clientId", userId);
		return settlementDao.find(hql, params);
	}
	/**
	 * 获取生成对账单的订单
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
		if(name.equals("承运方名称或承运方合同编号")){
			//查找时不考虑合同名字
			name = "";
		}
		//没有时间限制
		sql+=" (companyName like '%"+name+"%' or contractId like '%"+name+"%')";
		return settlementDao.getFindSettlement(sql, display, pageNow);
	}
	/**
	 * 返回用户已结算金额/待结算金额  flag=0已结算/flag=1待结算
	 */
	@Override
	public Float getUserSettlementMoney(HttpSession session,int flag) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		String hql ="from Orderform t where t.settlementState=:settlementState "
				+ "and t.clientId=:clientId";
		Map<String,Object> params=new HashMap<String,Object>();
		if(flag==0){
			params.put("settlementState", "已生成");
		}else{//flag=1
			params.put("settlementState", "未生成");
		}
		params.put("clientId", userId);
		List<Orderform> orderList=orderDao.find(hql, params);
		float totalMoney=0F;
		if(orderList!=null && orderList.size()>0){
			for(Orderform o:orderList){
				totalMoney+=o.getActualPrice();//结算金额按实际运费算
			}
		}
		
		return totalMoney;
	}
	
	
	

}
