package cn.edu.bjtu.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	
	@RequestMapping("/allcomplaint")
	/**
	 * ��̨Ͷ�߹���(����Ա)
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getAllUserComplaint(HttpSession session)
	{	
		String userId=(String) session.getAttribute("userId");
		// add by RussWest0 at 2015��5��30��,����10:40:43 
		if(userId==null){//δ��¼
			mv.setViewName("adminLogin");
			return mv;
		}
		if((int)session.getAttribute("userKind") != 1){//�ǹ���Ա
			mv.addObject("msg", "�ǹ���Ա���ܽ���");
			mv.setViewName("index");
		}
		List allCompliantList=complaintService.getAllUserCompliant();
		mv.addObject("allCompliantList", allCompliantList);
		mv.setViewName("mgmt_m_complain");
		return mv;
	}
	
	@RequestMapping("/getcomplaintdetail")
	public ModelAndView getComplaintDetail(
			@RequestParam String id,@RequestParam String orderid,@RequestParam int flag,
			HttpServletRequest request,HttpServletResponse response)
	{	
		Complaintform complaintInfo = complaintService.getComplaintInfo(id);
		mv.addObject("complaintInfo", complaintInfo);
		OrderCarrierView orderInfo = orderService.getSendOrderDetail(orderid);
		mv.addObject("orderinfo", orderInfo);
		if(flag==0){

			mv.setViewName("mgmt_m_complain2");
		}
		else if(flag==1){

			mv.setViewName("mgmt_m_complain3");
		}
		return mv;
	}
	
	@RequestMapping("/doacceptcomplaint")
	public ModelAndView doAcceptComplaint(
			@RequestParam String id, @RequestParam String feedback,
			HttpServletRequest request,HttpServletResponse response)
	{	
		
		boolean flag = complaintService.doAcceptComplaint(id, feedback);
		if (flag == true) {
			try {
				response.sendRedirect("allcomplaint");
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
	
	@RequestMapping("findbycomplainttheme")
	/**
	 * ���˻��Ĳ�ѯ����
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView findByComplaintTheme(
			@RequestParam String theme,@RequestParam int flag,
			HttpServletRequest request,HttpServletResponse response){
		
		String clientId=(String)request.getSession().getAttribute("userId");
		if(flag==0){//��̨���������
			List complaintList = complaintService.getFindComplaint(theme,flag,clientId);
			System.out.println("complaintList+" + complaintList);
			System.out.println("listsize+"+complaintList.size());
			mv.addObject("allCompliantList", complaintList);
			mv.setViewName("mgmt_m_complain");
		}
		else if(flag==1){//�ҵĽ���-�ҵ�Ͷ�ߵ�����
			List complaintList = complaintService.getFindComplaint(theme,flag,clientId);
			mv.addObject("compliantList", complaintList);
			mv.setViewName("mgmt_d_complain");
		}
		return mv;
	}
}
