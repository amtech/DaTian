package cn.edu.bjtu.controller;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.bean.page.ComplaintBean;
import cn.edu.bjtu.service.ComplaintService;
import cn.edu.bjtu.service.OrderService;
import cn.edu.bjtu.util.DownloadFile;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Complaintform;
import cn.edu.bjtu.vo.OrderCarrierView;

@Controller
/**
 *  Ͷ�߱������
 * @author RussWest0
 *
 */
public class ComplaintController {

	@Resource(name = "complaintServiceImpl")
	ComplaintService complaintService;
	@Resource
	OrderService orderService;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/mycomplaint")
	public ModelAndView getUserComplaint(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = (String) request.getSession().getAttribute("userId");

		List compliantList = complaintService.getUserCompliant(userId);
		mv.addObject("compliantList", compliantList);
		mv.setViewName("mgmt_d_complain");
		return mv;
	}

	@RequestMapping("/complaintdetail")
	public ModelAndView getcomplaintInfo(@RequestParam("id") String id,
			@RequestParam("orderId") String orderId) {
		Complaintform complaintInfo = complaintService.getComplaintInfo(id);
		mv.addObject("complaintInfo", complaintInfo);
		OrderCarrierView orderInfo = orderService.getSendOrderDetail(orderId);
		mv.addObject("orderInfo", orderInfo);
		mv.setViewName("mgmt_d_complain3");

		return mv;
	}

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
	@RequestMapping(value = "insertComplaint", method = RequestMethod.POST)
	public ModelAndView insertComplaint(
			@RequestParam(required = false) MultipartFile file,
			ComplaintBean complaintBean, HttpServletRequest request,
			HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute("userId");

		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getComplaintPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		boolean flag = complaintService.insertComplaint(complaintBean, carrierId, path, fileName);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("mycomplaint");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt");
		return mv;
	}

	@RequestMapping("/allcomplaint")
	/**
	 * ��̨Ͷ�߹���(����Ա)
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getAllUserComplaint(HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		// add by RussWest0 at 2015��5��30��,����10:40:43
		if (userId == null) {// δ��¼
			mv.setViewName("adminLogin");
			return mv;
		}
		if ((int) session.getAttribute("userKind") != 1) {// �ǹ���Ա
			mv.addObject("msg", "�ǹ���Ա���ܽ���");
			mv.setViewName("index");
			return mv;
		}
		List allCompliantList = complaintService.getAllUserCompliant();
		mv.addObject("allCompliantList", allCompliantList);
		mv.setViewName("mgmt_m_complain");
		return mv;
	}

	@RequestMapping("/getcomplaintdetail")
	public ModelAndView getComplaintDetail(@RequestParam String id,
			@RequestParam String orderid, @RequestParam int flag,
			HttpServletRequest request, HttpServletResponse response) {
		Complaintform complaintInfo = complaintService.getComplaintInfo(id);
		mv.addObject("complaintInfo", complaintInfo);
		OrderCarrierView orderInfo = orderService.getSendOrderDetail(orderid);
		mv.addObject("orderinfo", orderInfo);
		if (flag == 0) {

			mv.setViewName("mgmt_m_complain2");
		} else if (flag == 1) {

			mv.setViewName("mgmt_m_complain3");
		}
		return mv;
	}

	@RequestMapping("/doacceptcomplaint")
	/**
	 * ����Ͷ��
	 * @param id
	 * @param feedback
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView doAcceptComplaint(@RequestParam String id,
			@RequestParam String feedback, HttpServletRequest request,
			HttpServletResponse response) {

		boolean flag = complaintService.doAcceptComplaint(id, feedback);
		if (flag == true) {
			try {
				response.sendRedirect("allcomplaint");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_m_complain");
		return mv;
	}

	@RequestMapping("findbycomplainttheme")
	/**
	 * ���˻��Ĳ�ѯ����
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView findByComplaintTheme(@RequestParam String theme,
			@RequestParam int flag, HttpServletRequest request,
			HttpServletResponse response) {

		String clientId = (String) request.getSession().getAttribute("userId");
		if (flag == 0) {// ��̨���������
			List complaintList = complaintService.getFindComplaint(theme, flag,
					clientId);
			mv.addObject("allCompliantList", complaintList);
			mv.setViewName("mgmt_m_complain");
		} else if (flag == 1) {// �ҵĽ���-�ҵ�Ͷ�ߵ�����
			List complaintList = complaintService.getFindComplaint(theme, flag,
					clientId);
			mv.addObject("compliantList", complaintList);
			mv.setViewName("mgmt_d_complain");
		}
		return mv;
	}

	@RequestMapping(value = "downloadrelatedmaterial", method = RequestMethod.GET)
	public ModelAndView downloadRelatedMaterial(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		Complaintform complaintInfo = complaintService.getComplaintInfo(id);
		String file = complaintInfo.getRelatedMaterial();
		DownloadFile.downloadFile(file,request,response);

		return mv;

	}
	
	/**
	 * ����Ͷ���ļ�             
	 * @param request
	 * @param response
	 * @param complaintid
	 * @return
	 */
	@RequestMapping("downloadComplaintMaterial")
	public ModelAndView downloadComplaintMaterial(HttpServletRequest request,HttpServletResponse response,String complaintid){
		
		Complaintform complaint=complaintService.getComplaintInfo(complaintid);
		String fileLocation=complaint.getRelatedMaterial();
		DownloadFile.downloadFile(fileLocation, request, response);
		return mv;
	}

}
