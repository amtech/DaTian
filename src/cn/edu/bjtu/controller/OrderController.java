package cn.edu.bjtu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CarService;
import cn.edu.bjtu.service.CitylineService;
import cn.edu.bjtu.service.CommentService;
import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.DriverService;
import cn.edu.bjtu.service.GoodsInfoService;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.service.OrderService;
import cn.edu.bjtu.service.ResponseService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Carinfo;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Cityline;
import cn.edu.bjtu.vo.Comment;
import cn.edu.bjtu.vo.Driverinfo;
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

	@Resource
	LinetransportService linetransportService;
	@Resource
	CitylineService citylineService;
	@Autowired
	CompanyService companyService;
	@Autowired
	CommentService commentService;
	@Autowired
	GoodsInfoService goodsInfoService;
	@Autowired
	ResponseService responseService;
	
	@Autowired
	DriverService driverService;
	
	ModelAndView mv = new ModelAndView();

	@RequestMapping("/sendorderinfo")
	/**
	 * ���ύ�Ķ�����Ϣ
	 * @return
	 */
	public ModelAndView getAllSendOrderInfo(HttpSession session) {
		// ��session��ȡ�û�Id
		String userId = (String) session.getAttribute(Constant.USER_ID);
		List orderList = orderService.getAllSendOrderInfo(userId);
		mv.addObject("orderList", orderList);
		if (userId == null){
			mv.setViewName("login");
		}
		else{
			mv.setViewName("mgmt_d_order_s");
		}
		return mv;
	}

	@RequestMapping("/recieveorderinfo")
	/**
	 * ���յ��Ķ�����Ϣ
	 * @return
	 */
	public ModelAndView getAllRecieveOrderInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = (String) request.getSession().getAttribute(Constant.USER_ID);
		List orderList = orderService.getAllRecieveOrderInfo(userId);
		mv.addObject("receiveOrderList", orderList);
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

	/**
	 * ��ȡ�����
	 * @param orderid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("acceptOrderForm")
	public ModelAndView getAcceptOrderForm(String orderid,
			HttpServletRequest request, HttpServletResponse response) {

		// ��Ҫ�����˾˾���б� add by RussWest0 at 2015��6��7��,����7:56:32 
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		List<Driverinfo> driverList=driverService.getAllDriver(carrierId);
		mv.addObject("driverList",driverList);
		// ��Ҫ��ȡ���ƺź�˾����
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
			HttpServletResponse response,String driver) {

		// ��Ҫ���¶�����˾���б������޸Ķ���״̬Ϊ������(���ջ�)
		// ��Ҫ�ض���,��������ҳ��
		//��ȡ��˾��������δ������ add by RussWest0 at 2015��6��7��,����8:03:50 
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
	/**
	 * ��ȡǩ���ϴ���
	 * @param orderid
	 * @return
	 */
	public ModelAndView getSignBillForm(String orderid) {
		// �ϴ�ͼƬ�����ʵ���˷ѣ��޸Ķ���״̬Ϊ��ȷ��
		// ��Ҫ��ҳ������ʾ��ͬ�涨�˷Ѻ�Ԥ���˷�
		// �ϴ�ͼƬδʵ��
		float expectedMoney = orderService.getExpectedMoney(orderid);
		// System.out.println("ǩ���ϴ�+orderid+" + orderid);
		mv.addObject("expectedPrice", expectedMoney);
		mv.addObject("orderId", orderid);
		mv.setViewName("mgmt_d_order_r6");
		return mv;
	}

	@RequestMapping("signBill")
	public ModelAndView SignBill(@RequestParam(required = false) MultipartFile file,String orderid, float actualPrice,
			String explainReason, HttpServletRequest request,
			HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// ////////////////////////////////////////////////////////////////////////

		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getSignBillPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		//û���ϴ��ļ������path �� filenNameĬ��Ϊnull
		boolean flag = orderService.signBill(orderid, actualPrice,
				explainReason,path,fileName);
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
		Float expectedPrice = order.getExpectedPrice();
		Float actualPrice = order.getActualPrice();
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
			@RequestParam float expectedPrice,
			@RequestParam String deliveryName,
			@RequestParam String recieverName,
			@RequestParam String deliveryPhone,
			@RequestParam String recieverPhone,
			@RequestParam String deliveryAddr,
			@RequestParam String recieverAddr, @RequestParam String remarks,
			HttpServletRequest request, HttpServletResponse response

	) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// �ַ������
		
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_s4a");
		return mv;
	}

	@RequestMapping(value = "orderDetailFinish")
	/**
	 * 
	 * ������ɺ�鿴����
	 * @param orderid
	 * @return
	 */
	public ModelAndView orderDetailFinish(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
		mv.addObject("orderInfo", orderInfo);
		//ҳ����Ҫ������Ϣ add by RussWest0 at 2015��6��7��,����4:04:16 
		Comment comment=commentService.getCommentByOrderId(orderid);
		mv.addObject("comment", comment);
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r4");
		return mv;
	}
	
	@RequestMapping(value = "getOrderDetailCargoTrack")
	/**
	 * 
	 * 
	 * @param orderid
	 * @return
	 */
	public ModelAndView getOrderDetailCargoTrack(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String orderNum, 
			@RequestParam String carNum) {
		List cargoTrackList = orderService.getCargoTrack(orderNum,carNum);
		mv.addObject("cargoTrackList", cargoTrackList);
		mv.setViewName("mgmt_d_order_s7");
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
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r4a");
		return mv;
	}

	@RequestMapping(value = "getOrderDetailFinish")
	/**
	 * 
	 * ���˷�-���յ��Ķ���-�����-�鿴
	 * @param orderid
	 * @return
	 */
	public ModelAndView getOrderDetailFinish(HttpServletRequest request,
			HttpServletResponse response, String orderid) {
		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
		mv.addObject("orderInfo", orderInfo);
		//ҳ����Ҫ������Ϣ
		Comment comment=commentService.getCommentByOrderId(orderid);
		mv.addObject("comment",comment);
		mv.setViewName("mgmt_d_order_r4b");
		return mv;
	}

	@RequestMapping("getOrderWaitToConfirmUpdate")
	public ModelAndView getOrderWaitToConfirmUpdate(String orderid) {

		// ��Ҫ��ҳ������ʾ��ͬ�涨�˷Ѻ�Ԥ���˷�,ʵ���˷�,ԭ��
		// �ϴ�ͼƬδʵ��

		OrderCarrierView orderInfo = orderService.getOrderByOrderId(orderid);
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_order_r6a");
		return mv;
	}

	@RequestMapping("updateSignBill")
	public ModelAndView updateSignBill(String orderid,
			float actualPrice, String explainReason,
			HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false) MultipartFile file) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getSignBillPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		boolean flag = orderService.DoGetOrderWaitToConfirmUpdate(orderid,
				actualPrice, explainReason,path,fileName);
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
	public ModelAndView getNewOrderForm(@RequestParam String carrierid,
			@RequestParam(required=false) String resourceId,@RequestParam int flag) {
		// ��Ҫȡ�����˷���˾����
		//flag��resourceType�б�ʶ1Ϊ���ߣ�2Ϊ���У�3Ϊ����,4Ϊ��˾
		int resourceType = 0;
		if(flag==4){//�ӹ�˾ҳ���ύ����
			Carrierinfo carrierInfo=companyService.getCompanyById(carrierid);
			mv.addObject("carrierInfo", carrierInfo);
			mv.addObject("carrierId", carrierid);
			
			mv.setViewName("mgmt_d_order_s2a");
			return mv;
		}
		if(flag==1){//�Ӹ�����Դ�ύ����
			Linetransport linetransportInfo = linetransportService
					.getLinetransportInfo(resourceId);
			resourceType = 1;
			mv.addObject("resourceType", resourceType);
			mv.addObject("linetransportInfo", linetransportInfo);
		}
		if(flag==2){// �ӳ��������ύ����
			Cityline citylineInfo = citylineService.getCitylineInfo(resourceId);
			resourceType = 2;
			mv.addObject("resourceType", resourceType);
			mv.addObject("citylineInfo", citylineInfo);
		}
		if(flag==3){//�ӳ�����Դ�ύ����
			Carinfo carInfo = carService.getCarInfo(resourceId);
			resourceType = 3;
			mv.addObject("resourceType", resourceType);
			mv.addObject("carInfo", carInfo);
		}
		Carrierinfo company=companyService.getCompanyById(carrierid);
		mv.addObject("companyName", company.getCompanyName());
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
			String hasCarrierContract, @RequestParam String deliveryName,
			@RequestParam String recieverName,
			@RequestParam String deliveryPhone,
			@RequestParam String recieverPhone,
			@RequestParam String deliveryAddr,
			@RequestParam String recieverAddr, String remarks,
			String goodsName, float goodsWeight, float goodsVolume,
			float declaredPrice, float expectedPrice, float insurance,
			String contractId, HttpServletRequest request,
			HttpServletResponse response,@RequestParam String isLinkToClientWayBill,
			@RequestParam(required=false) String clientWayBillNum,String resourceName,String resourceType,String companyName) {
		// ҳ��������ֶ�û�д���
		// clientName�������У�����û��ʹ��
		String userId = (String) request.getSession().getAttribute(Constant.USER_ID);
		boolean flag = orderService.createNewOrder(userId, hasCarrierContract,
				deliveryName, recieverName, deliveryPhone, recieverPhone,
				deliveryAddr, recieverAddr, remarks, goodsName, goodsVolume,
				goodsWeight, expectedPrice, declaredPrice, insurance,
				contractId, carrierid,isLinkToClientWayBill,clientWayBillNum,resourceName,resourceType,companyName,clientName);
		if (flag == true) {
			// mv.setViewName("mgmt_d_order_s");
			try {
				response.sendRedirect("sendorderinfo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mv.setViewName("mgmt_d_order_s");
		return mv;
	}
	
	
	@RequestMapping("createNewOrderFromGoods")
	/**
	 * �ӻ���ҳ�洴���¶���
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
	public ModelAndView createNewOrderFromGoods(String carrierid, String clientName,
			String hasCarrierContract, @RequestParam String deliveryName,
			@RequestParam String recieverName,
			@RequestParam String deliveryPhone,
			@RequestParam String recieverPhone,
			@RequestParam String deliveryAddr,
			@RequestParam String recieverAddr, String remarks,
			String goodsName, float goodsWeight, float goodsVolume,
			float declaredPrice, float expectedPrice, float insurance,
			String contractId, HttpServletRequest request,
			HttpServletResponse response,@RequestParam String isLinkToClientWayBill,
			@RequestParam(required=false) String clientWayBillNum,String resourceName,String resourceType,String companyName,
			String responseid,String goodsid) {
		// ҳ��������ֶ�û�д���
		// clientName�������У�����û��ʹ��
		String userId = (String) request.getSession().getAttribute(Constant.USER_ID);
		boolean flag = orderService.createNewOrder(userId, hasCarrierContract,
				deliveryName, recieverName, deliveryPhone, recieverPhone,
				deliveryAddr, recieverAddr, remarks, goodsName, goodsVolume,
				goodsWeight, expectedPrice, declaredPrice, insurance,
				contractId, carrierid,isLinkToClientWayBill,clientWayBillNum,resourceName,resourceType,companyName,clientName);
		if (flag == true) {
			//�������޸�״̬
			responseService.confirmResponse(responseid,carrierid,goodsid);//�޸�ȷ�Ϸ�����ϢΪ��ȷ�ϣ�����������ϢΪ��ȡ��״̬
			//������޸�״̬
			goodsInfoService.confirmResponse(goodsid);
			
			try {
				response.sendRedirect("sendorderinfo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mv.setViewName("mgmt_d_order_s");
		return mv;
	}

}
