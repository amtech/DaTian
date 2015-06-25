package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.OrderDao;
import cn.edu.bjtu.dao.SettlementRecordDao;
import cn.edu.bjtu.service.OrderService;
import cn.edu.bjtu.service.SettlementRecordService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Orderform;
import cn.edu.bjtu.vo.Settlement;
@Service
@Transactional
public class SettlementRecordServiceImpl implements SettlementRecordService{
	
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDao orderDao;
	@Autowired
	SettlementRecordDao settlementRecordDao;
	/**
	 * �޸Ķ���״̬Ϊ�ѽ��㣬����¼������
	 */
	@Override
	public boolean finishSettlement(String orderNum, HttpSession session) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		String username=(String)session.getAttribute(Constant.USER_NAME);
		Orderform order=orderService.getOrderByOrderNum(orderNum);
		
		order.setSettlementState("������");
		Settlement settlement=new Settlement();
		settlement.setId(IdCreator.createSettlementId());
		settlement.setOrderNum(orderNum);
		settlement.setUserId(userId);
		settlement.setOrderId(order.getId());//add by RussWest0 at 2015��6��25��,����9:33:21 
		settlement.setCreateTime(new Date());
		settlement.setUsername(username);
		settlementRecordDao.save(settlement);//�������ɶ��˵���¼
		orderDao.update(order);
		
		return true;
		
	}
	
	/**
	 * ���ݶ����Ż�ȡ�������ɼ�¼
	 */
	@Override
	public List<Settlement> getSettlementRecordByOrderNum(String orderNum) {
		
		String hql="from Settlement t where t.orderNum=:orderNum";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("orderNum", orderNum);
		
		return settlementRecordDao.find(hql, params);
		
	}
}
