package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.OrderDao;
import cn.edu.bjtu.vo.OrderCarrierView;
import cn.edu.bjtu.vo.Orderform;

@Repository
public class OrderDaoImpl implements OrderDao {

	@Resource
	private HibernateTemplate ht;

	@Resource
	private BaseDao baseDao;

	@Override
	/**
	 * �������г�����Ϣ
	 */
	public List getAllSendOrderInfo(String userId) {
		// TODO Auto-generated method stub
		// System.out.println("dao-userid+"+userId);
		return ht.find("from OrderCarrierView where clientId='" + userId + "'");

	}

	@Override
	public List getAllRecieveOrderInfo(String userId) {
		// TODO Auto-generated method stub
		return ht
				.find("from OrderCarrierView where carrierId='" + userId + "'");
	}

	@Override
	public OrderCarrierView getSendOrderDetail(String id) {
		// TODO Auto-generated method stub
		return ht.get(OrderCarrierView.class, id);
	}

	

	@Override
	public Orderform getRecieveOrderDetail(String id) {
		// TODO Auto-generated method stub
		return ht.get(Orderform.class, id);
	}

	@Override
	/**
	 * ͨ��������Ż�ȡĳ����id
	 */
	public List getOrderIdByOrderNum(String orderNum) {
		// TODO Auto-generated method stub
		return ht.find("select id from Orderform where orderNum='" + orderNum
				+ "'");
	}

	@Override
	public OrderCarrierView getOrderByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return ht.get(OrderCarrierView.class, orderId);
	}

	@Override
	/**
	 * ���˷��޸Ķ���״̬Ϊ���ջ�
	 */
	public boolean acceptOrder(String orderId) {
		// TODO Auto-generated method stub
		Orderform order = (Orderform) ht.get(Orderform.class, orderId);
		//System.out.println("orderEntity+" + order);
		order.setState("���ջ�");// �޸�״̬

		return baseDao.update(order);

	}

	@Override
	public float getExpectedMoney(String orderId) {
		// TODO Auto-generated method stub
		List list = ht.find("select expectedPrice from Orderform where id='" + orderId + "'");
		if (list != null)
		{
			//Orderform order=(Float)list.get(0);
			return ((Float)list.get(0)).floatValue();
		}
		else
			return 0.0f;
	}

	@Override
	/**
	 * ���˷��޸Ķ���״̬Ϊ��ȷ��
	 */
	public boolean signBill(String orderId,float actualPrice,String explainReason) {
		// TODO Auto-generated method stub
		Orderform order = (Orderform) ht.get(Orderform.class, orderId);
		order.setState("��ȷ��");//�޸� ����״̬
		order.setActualPrice(actualPrice);
		order.setExplainReason(explainReason);
		return baseDao.update(order);
	}

	@Override
	/**
	 * ���ض�������Ϣ
	 */
	public Orderform getOrderInfo(String orderId) {
		// TODO Auto-generated method stub
		return (Orderform) ht.get(Orderform.class, orderId);
	}

	@Override
	/**
	 * ȷ���ջ�����
	 */
	public boolean confirmCargo(String orderId) {
		// TODO Auto-generated method stub
		Orderform order=ht.get(Orderform.class, orderId);
		order.setState("������");
		
		return baseDao.update(order);
	}
	
	
	

}
