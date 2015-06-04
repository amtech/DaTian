package cn.edu.bjtu.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.OrderDao;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.OrderCarrierView;
import cn.edu.bjtu.vo.Orderform;
import cn.edu.bjtu.vo.Track;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Orderform> implements OrderDao {

	@Resource
	private HibernateTemplate ht;

	/*@Resource
	private BaseDao baseDao;*/
	/*@Autowired
	OrderDao orderDao;*/

	@Override
	/**
	 * 
	 */
	public List getAllSendOrderInfo(String userId) {
		// TODO Auto-generated method stub
		// System.out.println("dao-userid+"+userId);
		//return ht.find("from OrderCarrierView where clientId='" + userId + "'");
		return this.find("from OrderCarrierView where clientId='"+userId+"'");

	}

	@Override
	public List getAllRecieveOrderInfo(String userId) {
		// TODO Auto-generated method stub
		return this.find("from OrderCarrierView where carrierId='" + userId + "'");
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
	public List getCargoTrack(String orderNum, String carNum) {
		// TODO Auto-generated method stub
		//System.out.println("from Track where orderNum='" + orderNum + "' and carNum='" + carNum + "'");
		return ht.find("from Track where orderNum='" + orderNum + "' and carNum='" + carNum + "'");
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
		Orderform order = this.get(Orderform.class, orderId);
		//System.out.println("orderEntity+" + order);
		order.setState("���ջ�");// �޸�״̬

		this.update(order);
		return true;

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
	public boolean signBill(String orderId,float actualPrice,String explainReason,String path,String fileName) {
		// TODO Auto-generated method stub
		
		Orderform order = (Orderform) ht.get(Orderform.class, orderId);
		order.setState("��ȷ��");//�޸� ����״̬
		order.setActualPrice(actualPrice);
		order.setExplainReason(explainReason);
		// �����ļ�·��
				if (path != null && fileName != null) {
					String fileLocation = path + "//" + fileName;
					order.setAcceptPicture(fileLocation);
				}
		this.update(order);
		return true;
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
		
		this.update(order);
		return true;
	}
	
	@Override
	/**
	 * ȡ����������
	 */
	public boolean cancel(String cancelReason, String orderId) {
		// TODO Auto-generated method stub
		Orderform order=ht.get(Orderform.class, orderId);
		order.setCancelReason(cancelReason);
		order.setState("��ȡ��");
		
		this.update(order);
		return true;
	}
	
	@Override
	/**
	 * ���˷����´�ȷ�϶���
	 */
	public boolean DoGetOrderWaitToConfirmUpdate(String orderId,float actualPrice,String explainReason) {
		// TODO Auto-generated method stub
		Orderform order = (Orderform) ht.get(Orderform.class, orderId);
		order.setActualPrice(actualPrice);
		order.setExplainReason(explainReason);
		this.update(order);
		return true;
	}

	@Override
	public boolean createNewOrder(String userId, String hasCarrierContract,
			String remarks, String goodsName, float goodsVolume,
			float goodsWeight, float expectedPrice, float declaredPrice,
			float insurance, String contractId, String deliveryName,
			String deliveryPhone, String deliveryAddr, String receiverName,
			String receiverPhone, String receiverAddr,String carrierId,String isLinkToClientWayBill,String clientWayBillNum, String resourceName, String resourceType,String companyName) {
		// TODO Auto-generated method stub
		Orderform order=new Orderform();
		order.setClientId(userId);
		order.setHasCarrierContract(hasCarrierContract);
		order.setRemarks(remarks);
		order.setGoodsName(goodsName);
		order.setGoodsVolume(goodsVolume);
		order.setGoodsWeight(goodsWeight);
		order.setExpectedPrice(expectedPrice);
		order.setDeclaredPrice(declaredPrice);
		order.setInsurance(insurance);
		order.setContractId(contractId);
		order.setDeliveryAddr(deliveryAddr);
		order.setDeliveryName(deliveryName);
		order.setDeliveryPhone(deliveryPhone);
		order.setRecieverAddr(receiverAddr);
		order.setRecieverName(receiverName);
		order.setRecieverPhone(receiverPhone);
		// add by RussWest0 at 2015��6��1��,����12:46:08 
		order.setIsLinkToClientWayBill(isLinkToClientWayBill);
		order.setClientWayBillNum(clientWayBillNum);
		order.setResourceName(resourceName);
		order.setResourceType(resourceType);
		
		
		order.setSubmitTime(new Date());
		order.setId(IdCreator.createOrderId());
		order.setOrderNum(IdCreator.createOrderNum());
		order.setCarrierId(carrierId);
		order.setSettlementState("δ����");// add by RussWest0 at 2015��6��4��,����8:40:27 
		//order.setClientName(clientName);
		//order.setResourceType(resourceType);
		order.setState("������");//����״̬
		order.setCompanyName(companyName);
		
		this.save(order);
		return true;
		
	}
	

	
}
