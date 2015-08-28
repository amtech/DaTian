package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.bean.page.OrderBean;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.OrderCarrierView;
import cn.edu.bjtu.vo.Orderform;

import com.alibaba.fastjson.JSONArray;

public interface OrderService {

	public OrderCarrierView getSendOrderDetail(String id);

	public Orderform getRecieveOrderDetail(String id);

	public Orderform getOrderByOrderNum(String orderNum);

	public OrderCarrierView getOrderByOrderId(String orderId);

	public boolean acceptOrder(String orderId);

	public float getExpectedMoney(String orderId);

	public boolean signBill(String orderId, float actualPrice,
			String explainReason,String fileLocation);

	public Orderform getOrderInfo(String orderId);

	public boolean confirmCargo(String orderId);

	public boolean cancel(String cancelReason, String orderId);
	/*@Deprecated
	public boolean updateOrder(String orderid, String clientName,
			String hasCarrierContract, String contractId, String goodsName,
			float goodsWeight, float goodsVolume, float declaredPrice,
			float insurance, float expectedPrice, String deliveryName,
			String deliveryPhone, String deliveryAddr, String recieverName,
			String recieverPhone, String recieverAddr, String remarks);
*/
	public boolean updateSignBill(String orderId,
			float actualPrice, String explainReason,String fileLocation);
	
/*	@Deprecated
	public boolean createNewOrder(String userId, String hasCarrierContract,
			String deliveryName, String receiverName, String deliveryPhone,
			String receiverPhone, String deliveryAddr, String receiverAddr,
			String remarks, String goodsName, float goodsVolume,
			float goodsWeight, float expectedPrice, float declaredPrice,
			float insurance, String contractId, String carrierId,
			String isLinkToClientWayBill,String clientWayBillNum, 
			String resourceName, String resourceType,String companyName,String clientName);*/

	public List getCargoTrack(String orderNum, String carNum);
	
	/**
	 * �����û�����������
	 * @param session
	 * @return
	 */
	public Long getUserWaitToAcceptNum(HttpSession session);
	
	/**
	 * �����û����ջ�������
	 * @param session
	 * @return
	 */
	public Long getUserWaitToReceiveNum(HttpSession session);
	
	/**
	 * �����û���������Ŀ 
	 * @param session
	 * @return
	 */
	public Long getUserWaitToSettleNum(HttpSession session);
	
	/**
	 * �����û�����ɶ�����
	 * @param session
	 * @return
	 */
	public Long finishedNum(HttpSession session);
	
	/**
	 * �½�����
	 * @param session
	 * @param orderBean
	 * @return
	 */
	public boolean createOrder(HttpSession session,OrderBean orderBean);
	
	/**
	 * ���¶���
	 * @param session
	 * @param orderBean
	 * @return
	 */
	public boolean updateOrder(HttpSession session,OrderBean orderBean);
	
	/**
	 * ���ύ�Ķ���-�ܼ�¼��
	 * @param session
	 * @return
	 */
	public Integer getUserSendOrderTotalRows(HttpSession session,Orderform order);
	
	/**
	 *  ����еĶ���
	 * @param session
	 * @return
	 */
	public JSONArray getUserSendOrder(HttpSession session,PageUtil pageUtil,Orderform order);

	/**
	 * ���յ��Ķ���
	 * @param session
	 * @return
	 */
	public JSONArray getUserRecieveOrder(HttpSession session,PageUtil pageUtil,Orderform order);
	
	/**
	 * ���յ��Ķ���-�ܼ�¼��
	 * @param session
	 * @return
	 */
	public Integer getUserRecieveOrderTotalRows(HttpSession session,Orderform order);

}
