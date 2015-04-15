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
		// System.out.println("�����յ�����������");
		String userId = (String) request.getSession().getAttribute("userId");
		// System.out.println("userId+"+userId);
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

	/*@RequestMapping(value = "insertOrder", method = RequestMethod.POST)
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
			@RequestParam float freight, @RequestParam String contractNum,
			@RequestParam String remarks) {

		System.out.println("���붩��������");
		boolean flag = orderService.insertOrder(goodsName, contactWaybill,
				deliveryAddr, recieverAddr, deliveryName, deliveryPhone,
				recieverName, recieverPhone, goodsWeight, goodsVolume,
				expectedPrice, insurance, freight, contractNum, remarks);
		if (flag == true)
			mv.setViewName("mgmt_r_line");
		else
			mv.setViewName("fail");
		// mv.setViewName("mgmt_r_line");
		return mv;
	}*/

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
		mv.setViewName("mgmt_d_order_r6");
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
		//����ҳ�����
		System.out.println("service attitude+"+serviceAttitude);
		System.out.println("cargoSafety+"+cargoSafety);
		
		
		return mv;
	}
}
