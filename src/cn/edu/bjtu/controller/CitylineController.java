package cn.edu.bjtu.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.bean.search.CityLineSearchBean;
import cn.edu.bjtu.service.CitylineService;
import cn.edu.bjtu.service.CommentService;
import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.FocusService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.DownloadFile;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Cityline;
import cn.edu.bjtu.vo.Comment;

import com.alibaba.fastjson.JSONArray;

@Controller
/**
 * �����������
 * @author RussWest0
 *
 */
public class CitylineController {
	@Autowired
	CommentService commentService;
	@Resource
	CitylineService citylineService;
	@Resource
	CompanyService companyService;
	@Autowired
	FocusService focusService;
	ModelAndView mv = new ModelAndView();
	
	/**
	 * ��Դ�����г���������·��Ϣ
	 * @param flag
	 * @return
	 */
	@RequestMapping(value="/cityline",params="flag=0")
	public String getAllCityline() {
		
		return "resource_list2";
		
	}
	/**
	 * ��Դ����������ɸѡ
	 * @param citylineBean
	 * @param page
	 * @param session
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getCityLineAjax",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCitylineAjax(CityLineSearchBean citylineBean,
			PageUtil page, HttpSession session, HttpServletResponse response,
			Model model) {
		JSONArray linetransportArray = citylineService.getSelectedLineNew(
				citylineBean,page,session);
		//model.addAttribute("count", 100);
		return linetransportArray.toString();
	}
	
	/**
	 * ��ȡ��������ɸѡ������
	 * @param lineBean
	 * @return
	 */
	@RequestMapping("getSelectedCityLineTotalRowsAjax")
	@ResponseBody
	public Integer getSelectedLineTotalRows(CityLineSearchBean citylineBean){
		Integer count=citylineService.getSelectedCityLineTotalRows(citylineBean);
		return count;
	}
	
	@RequestMapping(value="/cityline",params="flag=1")
	/**
	 * �ҵ���Ϣ-�û������г���������·��Ϣ
	 * @param flag
	 * @return
	 */
	@Deprecated
	public ModelAndView getUserCityline(@RequestParam int flag,
			HttpServletRequest request) {
		// ������sessionȡid
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// ɾ��
		List citylineList = citylineService.getCompanyCityline(carrierId);
		mv.addObject("citylineList", citylineList);
		mv.setViewName("mgmt_r_city");// �����߳���������ʾ������Ϣ
		return mv;
	}

	@RequestMapping(value = "/citylinedetail", method = RequestMethod.GET)
	/**
	 * ��ȡ����������·����
	 * @param citylineId
	 * @param carrierId
	 * @param flag
	 * @return
	 */
	public ModelAndView getCitylineInfo(
			@RequestParam("citylineId") String citylineId,
			@RequestParam("carrierId") String carrierId,
			@RequestParam("flag") int flag,
			HttpServletRequest request) {
		Cityline citylineInfo = citylineService.getCitylineInfo(citylineId); // ��Ҫ�ع�,����һ���������·����list
		String clientId = (String) request.getSession().getAttribute(Constant.USER_ID);
		List focusList = focusService.getFocusList(clientId,"cityline");
		mv.addObject("focusList", focusList);
		mv.addObject("citylineInfo", citylineInfo);
		if (flag == 0) {
			Carrierinfo carrierInfo = companyService.getCompanyById(carrierId);
			List<Comment> commentList=commentService.getCompanyComment(carrierId);
			mv.addObject("commentList",commentList);
			mv.addObject("carrierInfo", carrierInfo);
			//��Ҫ��ȡ��Դ��Ӧ�Ĺ�˾������ƽ����bean
			Comment comment=commentService.getCompanyAverageCommentRate(carrierId);
			mv.addObject("avgComment", comment);
			mv.setViewName("resource_detail2");// ��Դ����������ҳ��
		} else if (flag == 1)
			mv.setViewName("mgmt_r_city");// 3���и��º�ɾ��������ҳ��
		else if (flag == 2)
			mv.setViewName("mgmt_r_city4");// 4�ǵ�������ҳ��
		else if (flag == 3)
			mv.setViewName("mgmt_r_city3");// ������� ��ҳ��
		return mv;
	}

	@RequestMapping("citylineselected")
	/**
	 * ��ȡ���������ĳ���������·
	 * @param cityName
	 * @param VIPService
	 * @param refPrice
	 * @param Display
	 * @param PageNow
	 * @return
	 */
	@Deprecated
	public ModelAndView getSelectedCityLine(@RequestParam String cityName,
			@RequestParam String VIPService, @RequestParam String refPrice,
			@RequestParam int Display, @RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// 
			e.printStackTrace();
		}

		List citylineList = citylineService.getSelectedCityline(cityName,
				VIPService, refPrice, Display, PageNow);
		int count = citylineService
				.getTotalRows(cityName, VIPService, refPrice);// ��ȡ�ܼ�¼��
		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		mv.addObject("citylineList", citylineList);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("resource_list2");

		return mv;
	}

	@RequestMapping(value = "/insertCityLine", method = RequestMethod.POST)
	/**
	 * ��������������·
	 * @param name
	 * @param cityName
	 * @param VIPService
	 * @param refPrice
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertCityLine(@RequestParam MultipartFile file,
			@RequestParam String name, @RequestParam String cityName,
			@RequestParam String VIPService, @RequestParam float refPrice,
			@RequestParam String remarks,
			@RequestParam(required = false) String VIPDetail,
			HttpServletRequest request, HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// carrierId = "C-0002";// ɾ��
		/*
		 * boolean flag = linetransportService.insertLine(lineName, startPlace,
		 * endPlace, onWayTime, type, refPrice, remarks, carrierId);
		 */


		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getCitylinePath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// //////////////////////////////////////////////////////////////////
		}

		boolean flag = citylineService.insertCityLine(name, cityName,
				VIPService, refPrice, remarks, carrierId, VIPDetail, path,
				fileName);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("cityline?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_line");
		return mv;
	}

	@RequestMapping(value = "/updateCityline", method = RequestMethod.POST)
	/**
	 * * ���³���������Ϣ
	 * @param id
	 * @param citylineName
	 * @param cityName
	 * @param VIPService
	 * @param refPrice
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateCityline(
			@RequestParam MultipartFile file,
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String citylineName,
			@RequestParam String cityName,
			@RequestParam String VIPService,
			@RequestParam String VIPDetail,// ȱ����ϸ��ֵ�������
			@RequestParam float refPrice, @RequestParam String remarks,
			HttpServletRequest request, HttpServletResponse response) {

		// �˴���ȡsession���carrierid�����淽������һ������
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// ɾ��

		// ////////////////////////////////////////////
		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getCitylinePath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// û���ϴ��ļ������path �� filenNameĬ��Ϊnull

		// ////////////////////////////////////////////

		boolean flag = citylineService.updateLine(id, citylineName, cityName,
				VIPService, VIPDetail, refPrice, remarks, carrierId, path,
				fileName);
		if (flag == true) {

			try {
				response.sendRedirect("cityline?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_line");
		return mv;

	}

	@RequestMapping(value = "citydelete", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteCityline(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		boolean flag = citylineService.deleteCityline(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("cityline?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_line");
		return mv;

	}

	@RequestMapping(value = "downloaddetailprice", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView downloadDetailPrice(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		Cityline citylineInfo = citylineService.getCitylineInfo(id); // ��Ҫ�ع�,����һ���������·����list
			String file = citylineInfo.getDetailPrice();
			DownloadFile.downloadFile(file,request,response);
		return mv;

	}
	
	/**
	 * �ҵ���Ϣ-��������
	 * @Title: getUserCitylineResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����9:53:18
	 */
	@RequestMapping(value="getUserCitylineResourceAjax",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserCitylineResource(HttpSession session,PageUtil pageUtil){
		JSONArray jsonArray=citylineService.getUserCitylineResource(session,pageUtil);
		
		return jsonArray.toString();
	}
	
	/**
	 * �ҵ���Ϣ-��������-�ܼ�¼��
	 * @Title: getUserCitylineResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����9:55:10
	 */
	@RequestMapping("getUserCitylineResourceTotalRows")
	@ResponseBody
	public Integer getUserCitylineResourceTotalRows(HttpSession session){
		
		return citylineService.getUserCitylineResourceTotalRows(session);
	}

}
