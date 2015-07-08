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
	 * ��ȡ�û��Ķ���
	 */
	@Override
	@Deprecated
	public List getUserSettlementList(HttpSession session) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		Integer userKind=(Integer)session.getAttribute(Constant.USER_KIND);
		//����ɵĶ������ܽ���
		String hql="from SettlementCarrierView t where t.state='�����' ";
		Map<String,Object> params=new HashMap<String,Object>();
		if(userKind==2){//�����û�
			hql+=" and t.clientId=:clientId ";
			params.put("clientId", userId);
		}else if(userKind==3){//��ҵ�û�
			hql+=" and t.carrierId=:carrierId ";
			params.put("carrierId", userId);
		}
		return settlementDao.find(hql, params);
	}
	/**
	 * ��ȡ���ɶ��˵��Ķ���
	 */
	@Override
	public List getOrderStatement(String orderNum) {
		
		return settlementDao.getOrderStatement(orderNum);
	}
	@Override
	public List getFindSettlement(String carrierId, String name, int display, int pageNow) {
		
		String sql="from SettlementCarrierView where carrierId='"+carrierId+"' and ";
		if(name.equals("���˷����ƻ���˷���ͬ���")){
			//����ʱ�����Ǻ�ͬ����
			name = "";
		}
		//û��ʱ������
		sql+=" (companyName like '%"+name+"%' or contractId like '%"+name+"%')";
		return settlementDao.getFindSettlement(sql, display, pageNow);
	}
	/**
	 * �����û��ѽ�����/��������  flag=0�ѽ���/flag=1������
	 */
	@Override
	public Float getUserSettlementMoney(HttpSession session,int flag) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		String hql ="from Orderform t where t.settlementState=:settlementState "
				+ "and t.clientId=:clientId";
		Map<String,Object> params=new HashMap<String,Object>();
		if(flag==0){
			params.put("settlementState", "������");
		}else{//flag=1
			hql+=" and t.state='�����'";
			params.put("settlementState", "δ����");
		}
		params.put("clientId", userId);
		List<Orderform> orderList=orderDao.find(hql, params);
		float totalMoney=0F;
		if(orderList!=null && orderList.size()>0){
			for(Orderform o:orderList){
				if(o.getActualPrice()!=null){
					
					totalMoney+=o.getActualPrice();//�����ʵ���˷���
				}
			}
		}
		
		return totalMoney;
	}

}
