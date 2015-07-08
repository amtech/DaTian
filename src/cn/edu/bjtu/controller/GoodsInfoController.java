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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.bean.search.CargoSearchBean;
import cn.edu.bjtu.service.FocusService;
import cn.edu.bjtu.service.GoodsInfoService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.DownloadFile;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.GoodsClientView;
import cn.edu.bjtu.vo.Goodsform;

import com.alibaba.fastjson.JSONArray;

@Controller
public class GoodsInfoController {

	@Resource
	GoodsInfoService goodsInfoService;
	@Autowired
	FocusService focusService;
	ModelAndView mv = new ModelAndView();

	/**
	 * ��Դ������
	 * @param flag
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/goodsform",params="flag=0")
	public String getAllGoodsInfo() {
		return "resource_list6";
	}
	
	/**
	 * ��Դ��-����ɸѡ
	 * @param cargoBean
	 * @param pageUtil
	 * @param session
	 * @return
	 */
	@RequestMapping(value="getSelectedCargoAjax",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getSelectedCargoAjax(CargoSearchBean cargoBean,PageUtil pageUtil,HttpSession session){
		JSONArray jsonArray = goodsInfoService.getSelectedCargoNew(cargoBean, pageUtil,
				session);
		
		return jsonArray.toString();
	}
	/**
	 * ���ػ���ɸѡҳ���ܼ�¼��
	 * @param warehouseBean
	 * @return
	 */
	@RequestMapping("getSelectedCargoTotalRowsAjax")
	@ResponseBody
	public Integer getSelectedCargoTotalRowsAjax(CargoSearchBean cargoBean){
		Integer count=goodsInfoService.getSelectedCargoTotalRows(cargoBean);
		return count;
	}
	
	/**
	 * �ҵ���Ϣ-������Ϣ
	 * @param flag
	 * @param request
	 * @return
	 */
	@Deprecated
	@RequestMapping(value="/goodsform",params="flag=1")
	public ModelAndView getMyInfoGoods(@RequestParam int flag,
			HttpSession session) {
			String clientId = (String) session.getAttribute(
					"userId");
			List goodsList = goodsInfoService.getUserGoodsInfo(clientId);
			mv.addObject("goodsList", goodsList);
			mv.setViewName("mgmt_r_cargo");

		return mv;
	}

	@RequestMapping("/goodsdetail")
	/**
	 * ��Դ����������
	 * @param id
	 * @return
	 */
	public ModelAndView getAllGoodsDetail(@RequestParam String id) {
		GoodsClientView goodsformInfo = goodsInfoService.getAllGoodsDetail(id);
		mv.addObject("goodsformInfo", goodsformInfo);
		mv.setViewName("resource_detail6");

		return mv;
	}

	@RequestMapping("goodsformselected")
	/**
	 *  * ��ȡ���������Ļ���
	 * @param startPlace
	 * @param endPlace
	 * @param transportType
	 * @param Display
	 * @param PageNow
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	public ModelAndView getSelectedGoodsInfo(@RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam String transportType,
			@RequestParam int Display, @RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// 
			e.printStackTrace();
		}

		List goodsInfoList = goodsInfoService.getSelectedGoodsInfo(startPlace,
				endPlace, transportType, Display, PageNow);
		int count = goodsInfoService.getTotalRows(startPlace, endPlace,
				transportType);// ��ȡ�ܼ�¼��

		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		// System.out.println("�ܼ�¼��+"+count);
		// System.out.println("ҳ��+"+pageNum);
		mv.addObject("goodsformInfo", goodsInfoList);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("resource_list6");

		return mv;
	}

	@RequestMapping(value = "insertGoods", method = RequestMethod.POST)
	public ModelAndView insertGoods(@RequestParam MultipartFile file,
			@RequestParam String name, @RequestParam String type,
			@RequestParam float weight, @RequestParam String transportType,
			@RequestParam String transportReq, @RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam String damageReq,
			@RequestParam String VIPService,
			@RequestParam(required = false) String VIPServiceDetail,
			@RequestParam String oriented,
			@RequestParam String limitDate, @RequestParam String invoice,
			@RequestParam(required = false) String relatedMaterial,
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {

		String clientId = (String) request.getSession().getAttribute(Constant.USER_ID);
		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getGoodsPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = clientId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// û���ϴ��ļ������path �� filenNameĬ��Ϊnull


		boolean flag = goodsInfoService.insertGoods(name, type, weight,
				transportType, transportReq, startPlace, endPlace, damageReq,
				VIPService, oriented, limitDate, invoice, remarks, clientId,
				path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("goodsform?flag=1");
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}
		}
		return mv;
	}

	@RequestMapping("mygoodsdetail")
	public ModelAndView myGoodsDetail(@RequestParam String id,
			@RequestParam int flag, HttpServletRequest request,
			HttpServletResponse response) {
		String clientId = (String) request.getSession().getAttribute(Constant.USER_ID);
		GoodsClientView goodsformInfo = goodsInfoService.getAllGoodsDetail(id);
		mv.addObject("goodsdetail", goodsformInfo);

		if (flag == 1) {
			mv.setViewName("mgmt_r_cargo4");
		}

		else if (flag == 2) {
			mv.setViewName("mgmt_r_cargo3");
		}

		return mv;
	}

	//@RequestMapping(value = "updategoods", method = RequestMethod.POST)
	@Deprecated
	public ModelAndView updateGoods(@RequestParam MultipartFile file,
			@RequestParam String id,
			@RequestParam String name, @RequestParam String type,
			@RequestParam float weight, @RequestParam String transportType,
			@RequestParam String transportReq, @RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam String damageReq,
			@RequestParam String VIPService,
			@RequestParam(required = false) String VIPServiceDetail,
			@RequestParam(required = false) String oriented,
			@RequestParam String limitDate, @RequestParam String invoice,
			@RequestParam(required = false) String relatedMaterial,
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {

		String clientId = (String) request.getSession().getAttribute(Constant.USER_ID);

		String path = null;
		String fileName = null;
		// System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getGoodsPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = clientId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// System.out.println("path+fileName+" + path + "-" + fileName);
		}
		// û���ϴ��ļ������path �� filenNameĬ��Ϊnull

		// ////////////////////////////////////////////
		
		boolean flag = goodsInfoService.updateGoods(id, name, type, weight,
				transportType, transportReq, startPlace, endPlace, damageReq,
				VIPService, oriented, limitDate, invoice, remarks, clientId,
				path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("goodsform?flag=1");
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}
		}
		return mv;
	}
	
	@RequestMapping(value = "updategoods", method = RequestMethod.POST)
	public String updateNewGoods(Goodsform goods,MultipartFile file,
			HttpServletRequest request) {
		boolean flag=goodsInfoService.updateNewGoods(goods,request,file);
		return "redirect:goodsform?flag=1";
	}

	@RequestMapping("deletegoods")
	/**
	 * ɾ���û�
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView deleteGoods(@RequestParam String id,
			HttpServletRequest request, HttpServletResponse response) {

		boolean flag = goodsInfoService.deleteGoods(id);
		try {
			if (flag == true)
				response.sendRedirect("goodsform?flag=1");
			else
				System.out.println("ɾ��ʧ��");// Ӧ��¼��־
		} catch (IOException e) {
			// 
			// �˴�Ӧ��¼��־
			e.printStackTrace();

		}

		return mv;
	}
	
	@RequestMapping(value = "downloadgoodsrelated", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView downloadGoodsRelated(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		GoodsClientView goodsformInfo = goodsInfoService.getAllGoodsDetail(id);
			String file = goodsformInfo.getRelatedMaterial();
			DownloadFile.downloadFile(file,request,response);
		return mv;

	}
	/**
	 * �ҵ���Ϣ=�ҵĻ���
	 * @Title: getUserCargoResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����4:56:10
	 */
	@ResponseBody
	@RequestMapping(value="getUserCargoResourceAjax",produces = "text/html;charset=UTF-8")
	public String getUserCargoResource(HttpSession session,PageUtil pageUtil){
		JSONArray jsonArray=goodsInfoService.getUserCargoResource(session,pageUtil);
		return jsonArray.toString();
	}
	
	/**
	 * �ҵ���Ϣ-������Ϣ-�ܼ�¼��
	 * @Title: getUserCargoResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����4:58:56
	 */
	@ResponseBody
	@RequestMapping("getUserCargoResourceTotalRowsAjax")
	public Integer getUserCargoResourceTotalRows(HttpSession session){
		return goodsInfoService.getUserCargoTotalRows(session);
	}

}
