package cn.edu.bjtu.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CarService;
import cn.edu.bjtu.service.OrderService;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Linetransport;
import cn.edu.bjtu.vo.OrderCarrierView;
import cn.edu.bjtu.vo.Orderform;

/**
 * 
 * @author RussWest0
 *
 */
@Controller
public class OrderController {

	@Resource
	OrderService orderService;

	@Resource(name = "carServiceImpl")
	CarService carService;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/sendorderinfo")
	/**
	 * ���ύ�Ķ�����Ϣ
	 * @return
	 */
	public ModelAndView getAllSendOrderInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// ��session��ȡ�û�Id
		String userId = (String) request.getSession().getAttribute("userId");
		List orderList = orderService.getAllSendOrderInfo(userId);
		System.out.println("orderList+" + orderList);
		mv.addObject("orderList", orderList);
		mv.setViewName("mgmt_d_order_s");

		return mv;
	}

	@RequestMapping("/recieveorderinfo")
	/**
	 * ���յ��Ķ�����Ϣ
	 * @return
	 */
	public ModelAndView getAllRecieveOrderInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// ��session��ȡ�û�Id
		// System.out.println("�����յ�����������");
		String userId = (String) request.getSession().getAttribute("userId");
		// System.out.println("userId+" + userId);
		List orderList = orderService.getAllRecieveOrderInfo(userId);
		// System.out.println("orderList+"+orderList);
		mv.addObject("receiveOrderList", orderList);
		// mv.addObject("name", "iver99");
		mv.setViewName("mgmt_d_order_r");

		return mv;
	}

	@RequestMapping("/sendorderdetail")
	/**
	 * �ύ��������
	 * @param id
	 * @return
	 */
	public ModelAndView getSendOrderDetail(@RequestParam String id) {
		// System.out.println(id);// ///
		OrderCarrierView sendorderdetail = orderService.getSendOrderDetail(id);
		mv.addObject("sendorderdetail", sendorderdetail);

		mv.setViewName("mgmt_d_order_s4");

		return mv;
	}

	@RequestMapping("/recieveorderdetail")
	/**
	 * �յ���������
	 * @param id
	 * @return
	 */
	public ModelAndView getAllRecieveOrderDetail(@RequestParam String id) {
		System.out.println(id);
		Orderform recieveorderdetail = orderService.getRecieveOrderDetail(id);
		mv.addObject("recieveorderdetail", recieveorderdetail);
		mv.setViewName("mgmt_d_order_r3");

		return mv;
	}

	@RequestMapping(value = "insertOrder", method = RequestMethod.POST)
	public ModelAndView insertOrder(@RequestParam String goodsName,
			@RequestParam String clientCompany,
			@RequestParam String contactWaybill,
			@RequestParam String carrierAccount,
			@RequestParam String deliveryAddr,
			@RequestParam String recieverAddr,
			@RequestParam String deliveryName,
			@RequestParam String deliveryPhone,
			@RequestParam String recieverName,
			@RequestParam String recieverPhone,
			@RequestParam float goodsWeight, @RequestParam float goodsVolume,
			@RequestParam float expectedPrice, @RequestParam float insurance,
			@RequestParam float freight, @RequestParam String contractId,
			@RequestParam String remarks) {

		System.out.println("���붩��������");
		boolean flag = orderService.insertOrder(goodsName, contactWaybill,
				deliveryAddr, recieverAddr, deliveryName, deliveryPhone,
				recieverName, recieverPhone, goodsWeight, goodsVolume,
				expectedPrice, insurance, freight, contractId, remarks);
		if (flag == true)
			mv.setViewName("mgmt_r_line");
		else
			mv.setViewName("fail");
		// mv.setViewName("mgmt_r_line");
		return mv;
	}

	@RequestMapping("getUpdateOrderForm")
	/**
	 * ��ȡ���¶�����
	 * @param orderId
	 * @return
	 */
	public ModelAndView getUpdateOrderForm(String orderId) {

		OrderCarrierView orderCarrierView = orderService
				.getOrderByOrderId(orderId);// ����ͼ��

		mv.addObject("orderinfo", orderCarrierView);
		mv.setViewName("mgmt_d_order_s3");
		return mv;
	}

	@RequestMapping("acceptOrderForm")
	public ModelAndView getAcceptOrderForm(String orderid,
			HttpServletRequest request, HttpServletResponse response) {

		// ��Ҫ�����˾˾���б�
		String carrierId = (String) request.getSession().getAttribute("userId");

		// ��Ҫ��ȡ���ƺź�˾����
		// System.out.println("��ȡ���ձ�-orderid+"+orderid);
		mv.addObject("orderId", orderid);

		mv.setViewName("mgmt_d_order_r2");
		return mv;
	}

	@RequestMapping("acceptOrder")
	/**
	 * �������
	 * @param orderid
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView acceptOrder(String orderid, HttpServletRequest request,
			HttpServletResponse response) {

		// ��Ҫ���¶�����˾���б������޸Ķ���״̬Ϊ������(���ջ�)
		// ��Ҫ�ض���,��������ҳ��
		System.out.println("���ն���+orderId+" + orderid);
		System.out.println("���ն���-orderId+" + orderid);
		boolean flag = orderService.acceptOrder(orderid);
		try {
			if (flag == true)
				response.sendRedirect("recieveorderinfo");
			else
				System.out.println("���ն���ʧ��");// Ӧ��¼��־
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// �˴�Ӧ��¼��־
			e.printStackTrace();

		}
		// mv.setViewName("mgmt_d_order_r");
		return mv;
	}

	@RequestMapping("getSignBillForm")
	public ModelAndView getSignBillForm(String orderid) {
		// �ϴ�ͼƬ�����ʵ���˷ѣ��޸Ķ���״̬Ϊ��ȷ��
		// ��Ҫ��ҳ������ʾ��ͬ�涨�˷Ѻ�Ԥ���˷�
		// �ϴ�ͼƬδʵ��
		float expectedMoney = orderService.getExpectedMoney(orderid);

		System.out.println("expectedPrice+" + expectedMoney);
		// System.out.println("ǩ���ϴ�+orderid+" + orderid);
		mv.addObject("expectedPrice", expectedMoney);
		mv.addObject("orderId", orderid);
		return mv;
	}

	@RequestMapping("signBill")
	public ModelAndView SignBill(String orderid, float actualPrice,
			String explainReason, HttpServletRequest request,
			HttpServletResponse response) {
		// System.out.println("actualPrice+"+actualPrice);
		// System.out.println("explainReason+"+explainReason);
		boolean flag = orderService.signBill(orderid, actualPrice,
				explainReason);
		try {
			if (flag == true)
				response.sendRedirect("recieveorderinfo");
			else
				System.out.println("ǩ���ϴ�ʧ��");// logging...
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}

	/*
	 * @RequestMapping("receivecargoform")
	 *//**
	 * ��ȡ�ջ���
	 * 
	 * @param orderid
	 * @return
	 */
	/*
	 * public ModelAndView getReceiveCargoForm(String orderid) {
	 * 
	 * // ��Ҫ���¶���״̬Ϊ���ջ�(����״̬) mv.setViewName("mgmt_d_order_s5"); return mv; }
	 */
	@RequestMapping("getConfirmForm")
	/**
	 * ��ȡȷ���ջ���
	 * @param orderid
	 * @return
	 */
	public ModelAndView getConfirmForm(String orderid) {
		// ����ȷ���ջ�ҳ��
		// ��Ҫ�涨���ã�ʵ�ʷ��ã�˵��
		Orderform order = orderService.getOrderInfo(orderid);
		float expectedPrice = order.getExpectedPrice();
		float actualPrice = order.getActualPrice();
		String explianReason = order.getExplainReason();
		mv.addObject("orderId", orderid);
		mv.addObject("expectedPrice", expectedPrice);
		mv.addObject("actualPrice", actualPrice);
		mv.addObject("explainReason", explianReason);
		mv.setViewName("mgmt_d_order_s5");
		return mv;
	}

	@RequestMapping("confirm")
	public ModelAndView confirm(String orderid, HttpServletRequest request,
			HttpServletResponse response) {
		// �޸Ķ���Ϊ������
		boolean flag = orderService.confirmCargo(orderid);
		mv.addObject("orderId", orderid);
		if (flag == true)
			try {
				response.sendRedirect("sendorderinfo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// logging
			}
		else
			System.out.println("ȷ���ջ�ʧ��");// logging

		return mv;
	}

	@RequestMapping("getCommentForm")
	/**
	 * ��ȡ����ҳ��
	 * @param orderid
	 * @return
	 */
	public ModelAndView getCommentForm(String orderid)

	{

		mv.addObject("orderId", orderid);
		mv.setViewName("mgmt_d_order_s8");
		return mv;
	}

	@RequestMapping("comment")
	public ModelAndView comment(String orderid, int serviceAttitude,
			int transportEfficiency, int cargoSafety, int totalMoney,
			HttpServletRequest request, HttpServletResponse response) {
		// �޸Ķ���״̬Ϊ�����
		// �洢��������
		// ����ҳ�����
		System.out.println("service attitude+" + serviceAttitude);
		System.out.println("cargoSafety+" + cargoSafety);

		return mv;
	}

	@RequestMapping(value = "updateOrder")
	/**
	 * ���¶���
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView updateorder(@RequestParam("orderid") String orderid,
			HttpServletRequest request) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����·��Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_s3");
		return mv;
	}

	@RequestMapping(value = "doUpdate", method = RequestMethod.POST)
	/**
	 *
	 * @return
	 */
	public ModelAndView doUpdate(

	@RequestParam String orderid, @RequestParam String clientName,
			@RequestParam String hasCarrierContract,
			@RequestParam String contractId, @RequestParam String goodsName,
			@RequestParam float goodsWeight, @RequestParam float goodsVolume,
			@RequestParam float declaredPrice, @RequestParam float insurance,
			@RequestParam float expectedPrice, @RequestParam String deliveryName,
			@RequestParam String recieverName, @RequestParam String deliveryPhone,
			@RequestParam String recieverPhone, @RequestParam String deliveryAddr,
			@RequestParam String recieverAddr, @RequestParam String remarks,
			HttpServletRequest request, HttpServletResponse response

	) {
		//System.out.println("����order���¿�����");
		String carrierId = (String) request.getSession().getAttribute("userId");
		// �ַ������
		/*String[] de = delivery.split("/");
		String[] re = reciever.split("/");
		String deliveryName = de[0];
		String deliveryPhone = de[1];
		String deliveryAddr = de[2];
		String recieverName = re[0];
		String recieverPhone = re[1];
		String recieverAddr = re[2];*/
		boolean flag = orderService.updateOrder(orderid, clientName,
				hasCarrierContract, contractId, goodsName, goodsWeight,
				goodsVolume, declaredPrice, insurance, expectedPrice,
				deliveryName, deliveryPhone, deliveryAddr, recieverName,
				recieverPhone, recieverAddr, remarks);
		if (flag == true) {

			try {
				response.sendRedirect("sendorderinfo");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("order��������º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}

	@RequestMapping(value = "cancelOrder")
	/**
	 * ȡ������
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView cancelOrder(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_s9");
		return mv;
	}

	@RequestMapping(value = "getOrderCancelOrder")
	/**
	 * ȡ������
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView getOrderCancelOrder(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r7");
		return mv;
	}

	@RequestMapping(value = "doCancel", method = RequestMethod.POST)
	/**
	 * ȡ������
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView doCancel(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String cancelReason,
			String orderid) {
		boolean flag = orderService.cancel(cancelReason, orderid);
		if (flag == true)
			try {
				response.sendRedirect("sendorderinfo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// logging
			}
		else
			System.out.println("ȡ��ʧ��");// logging

		return mv;
	}

	@RequestMapping(value = "getOrderDoCancel", method = RequestMethod.POST)
	/**
	 * ȡ������
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView getOrderDoCancel(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String cancelReason,
			String orderid) {
		boolean flag = orderService.cancel(cancelReason, orderid);
		if (flag == true)
			try {
				response.sendRedirect("recieveorderinfo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// logging
			}
		else
			System.out.println("ȡ��ʧ��");// logging

		return mv;
	}

	@RequestMapping(value = "orderDetail")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView orderDetail(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_s4");
		return mv;
	}

	@RequestMapping(value = "orderDetailWaitToReceive")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView orderDetailWaitToReceive(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_s6");
		return mv;
	}

	@RequestMapping(value = "orderDetailAlreadyCancel")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView orderDetailAlreadyCancel(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_s4a");
		return mv;
	}

	@RequestMapping(value = "orderDetailFinish")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView orderDetailFinish(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_s6a");
		return mv;
	}

	@RequestMapping(value = "orderDetailComment")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView orderDetailComment(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_s6b");
		return mv;
	}

	@RequestMapping(value = "getOrderDetail")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView getOrderDetail(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r3");
		return mv;
	}

	@RequestMapping(value = "getOrderDetailCancel")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView getOrderDetailCancel(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r3a");
		return mv;
	}

	@RequestMapping(value = "getOrderDetailWaitToReceive")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView getOrderDetailWaitToReceive(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r4");
		return mv;
	}

	@RequestMapping(value = "getOrderDetailWaitToConfirm")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView getOrderDetailWaitToConfirm(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r4a");
		return mv;
	}

	@RequestMapping(value = "getOrderDetailFinish")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView getOrderDetailFinish(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r4b");
		return mv;
	}

	@RequestMapping("getOrderWaitToConfirmUpdate")
	public ModelAndView getOrderWaitToConfirmUpdate(String orderid) {

		// ��Ҫ��ҳ������ʾ��ͬ�涨�˷Ѻ�Ԥ���˷�,ʵ���˷�,ԭ��
		// �ϴ�ͼƬδʵ��

		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);// ��Ҫ�ع�������һ����Ϣ
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r6a");
		return mv;
	}

	@RequestMapping("DoGetOrderWaitToConfirmUpdate")
	public ModelAndView DoGetOrderWaitToConfirmUpdate(String orderid,
			float actualPrice, String explainReason,
			HttpServletRequest request, HttpServletResponse response) {
		// System.out.println("actualPrice+"+actualPrice);
		// System.out.println("explainReason+"+explainReason);
		boolean flag = orderService.DoGetOrderWaitToConfirmUpdate(orderid,
				actualPrice, explainReason);
		try {
			if (flag == true)
				response.sendRedirect("recieveorderinfo");
			else
				System.out.println("��ȷ��ʧ��");// logging...
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping("getneworderform")
	/**
	 * ��ȡ����������
	 * @return
	 */
	public ModelAndView getNewOrderForm(String carrierid) {
		// ��Ҫȡ�����˷���˾����
		mv.addObject("carrierId", carrierid);
		mv.setViewName("mgmt_d_order_s2");
		return mv;
	}

	@RequestMapping("createneworder")
	/**
	 * �����¶���
	 * @param clientName
	 * @param hasCarrierContract
	 * @param senderInfo
	 * @param receiverInfo
	 * @param remarks
	 * @param goodsName
	 * @param goodsWeight
	 * @param goodsVolume
	 * @param declaredPrice
	 * @param expectedPrice
	 * @param insurance
	 * @param contractId
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView createNewOrder(String carrierid, String clientName,
			String hasCarrierContract, String senderInfo, String receiverInfo,
			String remarks, String goodsName, float goodsWeight,
			float goodsVolume, float declaredPrice, float expectedPrice,
			float insurance, String contractId,HttpServletRequest request,
			HttpServletResponse response) {
		// ҳ��������ֶ�û�д���
		//clientName�������У�����û��ʹ��
		String userId = (String) request.getSession().getAttribute("userId");
		System.out.println("carrierId+"+carrierid);
		boolean flag = orderService.createNewOrder(userId, hasCarrierContract,
				senderInfo, receiverInfo, remarks, goodsName, goodsVolume,
				goodsWeight, expectedPrice, declaredPrice, insurance,
				contractId,carrierid);
		if (flag == true){
			//mv.setViewName("mgmt_d_order_s");
			try {
				response.sendRedirect("sendorderinfo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("redirecting");
				System.out.println("������������");//logging
				e.printStackTrace();
			}
		}
		return mv;
	}

}
