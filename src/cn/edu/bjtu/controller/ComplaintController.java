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

import cn.edu.bjtu.service.ComplaintService;
import cn.edu.bjtu.service.OrderService;
import cn.edu.bjtu.vo.Complaintform;
import cn.edu.bjtu.vo.OrderCarrierView;

@Controller
/**
 *  Ͷ�߱������
 * @author RussWest0
 *
 */
public class ComplaintController {
	
	@Resource(name="complaintServiceImpl")
	ComplaintService complaintService;
	@Resource
	OrderService orderService;
	
	ModelAndView mv=new ModelAndView();
	@RequestMapping("/mycomplaint")
	public ModelAndView getUserComplaint(HttpServletRequest request,HttpServletResponse response)
	{	
		System.out.println("����Ͷ�߿�����");
		String userId=(String)request.getSession().getAttribute("userId");
		System.out.println("userid="+userId);
		
		List compliantList=complaintService.getUserCompliant(userId);
		System.out.println("listsize+"+compliantList.size());
		mv.addObject("compliantList", compliantList);
		mv.setViewName("mgmt_d_complain");
		return mv;
	}
	
	@RequestMapping("/complaintdetail")
	public ModelAndView getcomplaintInfo(
			@RequestParam("id") String id,
			@RequestParam("orderId") String orderId)
	{	
		System.out.println("����Ͷ�߿�����");
		Complaintform complaintInfo = complaintService.getComplaintInfo(id);
		mv.addObject("complaintInfo", complaintInfo);
		OrderCarrierView orderInfo = orderService.getSendOrderDetail(orderId);
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_complain3");
		
		return mv;
	}

	
	@RequestMapping(value = "insertComplaint", method = RequestMethod.POST)
	/**
	 * * �����ҵ�Ͷ��
	 * @param type
	 * @param theme
	 * @param content
	 * @param orderNum
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertComplaint(@RequestParam String type,
			@RequestParam String theme, @RequestParam String content,
			@RequestParam String orderNum,
			HttpServletRequest request, HttpServletResponse response) {
		String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0001";// ɾ��
		/*boolean flag = linetransportService.insertLine(lineName, startPlace,
				endPlace, onWayTime, type, refPrice, remarks, carrierId);*/
		boolean flag=complaintService.insertComplaint(type, theme, content, orderNum, carrierId, request, response);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("mycomplaint");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("complaint������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}
}
