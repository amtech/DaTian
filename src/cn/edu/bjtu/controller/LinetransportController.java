package cn.edu.bjtu.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Linetransport;

@Controller
/**
 * ������ؿ�����
 * @author RussWest0
 *
 */
public class LinetransportController {

	/*
	 * @Resource HibernateTemplate ht;
	 */
	@RequestMapping("/linetransport")
	/**
	 * �������и�����Ϣ
	 * @return
	 */
	public ModelAndView getAllLinetransport(@RequestParam int flag,
			@RequestParam(required = false) Integer Display,
			@RequestParam(required = false) Integer PageNow,
			HttpServletRequest request) {
		if (Display == null)
			Display = 10;// Ĭ�ϵ�ÿҳ��С
		if (PageNow == null)
			PageNow = 1;// Ĭ�ϵĵ�ǰҳ��

		if (flag == 0) {
			List linetransportList = linetransportService.getAllLinetransport(
					Display, PageNow);
			int count = linetransportService.getTotalRows("All", "All", "All",
					"All", "All");// ��ȡ�ܼ�¼��,����Ҫwhere�Ӿ䣬���Բ�������All
			// System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);
			mv.addObject("linetransportList", linetransportList);
			mv.setViewName("resource_list");
		} else if (flag == 1) {
			// �����sessionȡ��id����ѯָ����line
			String carrierId = (String) request.getSession().getAttribute(
					"userId");

			List linetransportList = linetransportService.getCompanyLine(
					carrierId, Display, PageNow);// ������������
			// System.out.println("linetransportsize+"+linetransportList.size());
			int count = linetransportService.getCompanyTotalRows(carrierId);// �����ķ÷�
			// System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);

			mv.addObject("linetransportList", linetransportList);
			mv.setViewName("mgmt_r_line");
		}
		return mv;
	}

	@RequestMapping(value = "/linetransportdetail", method = RequestMethod.GET)
	/**
	 * ��ȡ�ض��ĸ�����Ϣ
	 * @param linetransportid
	 * @return
	 */
	public ModelAndView getLinetransportInfo(
			@RequestParam("linetransportid") String linetransportid,
			@RequestParam("carrierId") String carrierId,
			@RequestParam("flag") int flag, HttpServletRequest request) {
		Linetransport linetransportInfo = linetransportService
				.getLinetransportInfo(linetransportid);// ��Ҫ�ع�������һ����·��Ϣ
		mv.addObject("linetransportInfo", linetransportInfo);
		if (flag == 0) {
			// carrierId=(String)request.getSession().getAttribute("carrierId");
			// carrierId="C-0002";//ɾ��
			Carrierinfo carrierInfo = companyService.getCarrierInfo(carrierId);
			mv.addObject("carrierInfo", carrierInfo);
			mv.setViewName("resource_detail1");
		} else if (flag == 1) {// ����
			mv.setViewName("mgmt_r_line4");
		} else if (flag == 2) {// ����
			mv.setViewName("mgmt_r_line3");

		}
		return mv;
	}

	@RequestMapping(value = { "linetransportselected", "searchResourceselected" })
	// ͬʱ������������
	/**              
	 * ���ظ��߷���ɸѡ����������Ϣ
	 * @param startPlace
	 * @param endPlace
	 * @param type
	 * @param startPlace
	 * @param refPrice
	 * @param Display
	 * @param PageNow
	 * @return
	 */
	public ModelAndView getSelectedLine(@RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam String type,
			@RequestParam String startPlace1, @RequestParam String refPrice,
			@RequestParam int Display, @RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("�Ѿ�����linetransport������");

		List linetransportList = linetransportService.getSelectedLine(
				startPlace, endPlace, type, startPlace1, refPrice, Display,
				PageNow);
		int count = linetransportService.getTotalRows(startPlace, endPlace,
				type, startPlace1, refPrice);// ��ȡ�ܼ�¼��

		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		// System.out.println("�ܼ�¼��+"+count);
		// System.out.println("ҳ��+"+pageNum);
		mv.addObject("linetransportList", linetransportList);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("resource_list");

		return mv;
	}

	@RequestMapping(value = "insertLine", method = RequestMethod.POST)
	/**
	 * ����������·
	 * @param lineName
	 * @param startPlace
	 * @param endPlace
	 * @param onWayTime
	 * @param type
	 * @param refPrice
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertLine(
			@RequestParam(required = false) MultipartFile file,// new add
			@RequestParam String lineName, @RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam int onWayTime,
			@RequestParam String type,
			@RequestParam float refPrice,// ȱ����ϸ���۲���
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute("userId");
		// ////////////////////////////////////////////////////////////////////////

		String path = null;
		String fileName = null;
		//System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getLinetransportPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("path+fileName+" + path + "-" + fileName);
			// //////////////////////////////////////////////////////////////////
		} 
		//û���ϴ��ļ������path �� filenNameĬ��Ϊnull
		boolean flag = linetransportService.insertLine(lineName, startPlace,
				endPlace, onWayTime, type, refPrice, remarks, carrierId, path,
				fileName);
		// �޸Ĵ˷���,������������
		if (flag == true) {
			try {
				response.sendRedirect("linetransport?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("linetransport������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}

	@RequestMapping(value = "updateLine", method = RequestMethod.POST)
	/**
	 * ���¸�����Ϣ
	 * @param id
	 * @param lineName
	 * @param startPlace
	 * @param endPlace
	 * @param onWayTime
	 * @param type
	 * @param refPrice
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateLine(@RequestParam MultipartFile file,
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String lineName, @RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam int onWayTime,
			@RequestParam String type,
			@RequestParam float refPrice,// ȱ����ϸ���۲���
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute("userId");
		//////////////////////////////////////////////
		String path = null;
		String fileName = null;
		//System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getLinetransportPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("path+fileName+" + path + "-" + fileName);
		} 
		//û���ϴ��ļ������path �� filenNameĬ��Ϊnull
		
		//////////////////////////////////////////////
		
		boolean flag = linetransportService.updateLine(id, lineName,
				startPlace, endPlace, onWayTime, type, refPrice, remarks,
				carrierId,path,fileName);//change
		if (flag == true) {
			try {
				response.sendRedirect("linetransport?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("linetransport���º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;

	}

	@RequestMapping(value = "linetransportdelete", method = RequestMethod.GET)
	/**
	 * ɾ������
	 */
	public ModelAndView deleteLine(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		boolean flag = linetransportService.deleteLine(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("linetransport?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("linetransportɾ�����ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;

	}

	@Resource
	LinetransportService linetransportService;
	@Resource
	CompanyService companyService;

	ModelAndView mv = new ModelAndView();
}
