package cn.edu.bjtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import cn.edu.bjtu.service.GoodsInfoService;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Driverinfo;
import cn.edu.bjtu.vo.GoodsClientView;
import cn.edu.bjtu.vo.Goodsform;

@Controller
public class GoodsInfoController {

	@Resource
	GoodsInfoService goodsInfoService;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/goodsform")
	/**
	 * ��Դ������
	 * @param flag
	 * @param request
	 * @return
	 */
	public ModelAndView getAllGoodsInfo(@RequestParam int flag,
			HttpServletRequest request) {
		int Display = 10;// Ĭ�ϵ�ÿҳ��С
		int PageNow = 1;// Ĭ�ϵĵ�ǰҳ��

		if (flag == 0) {
			List goodsInfoList = goodsInfoService.getAllGoodsInfo(Display,
					PageNow);
			int count = goodsInfoService.getTotalRows("All", "All", "All");// ��ȡ�ܼ�¼��,����Ҫwhere�Ӿ䣬���Բ�������All
			System.out.println("count+" + count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);

			mv.addObject("goodsformInfo", goodsInfoList);
			mv.setViewName("resource_list6");// �����Դ������������ʾ������Ϣ
		} else if (flag == 1) {
			String clientId = (String) request.getSession().getAttribute(
					"userId");
			List goodsList = goodsInfoService.getUserGoodsInfo(clientId);
			mv.addObject("goodsList", goodsList);
			mv.setViewName("mgmt_r_cargo");
		}

		return mv;
	}

	@RequestMapping("/goodsdetail")
	/**
	 * ��Դ����������
	 * @param id
	 * @return
	 */
	public ModelAndView getAllGoodsDetail(@RequestParam String id) {
		System.out.println(id);
		GoodsClientView goodsformInfo = goodsInfoService.getAllGoodsDetail(id);
		// System.out.println(goodsformInfo);
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
	public ModelAndView getSelectedGoodsInfo(@RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam String transportType,
			@RequestParam int Display, @RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("����goodsInfo������");
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
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
			@RequestParam(required = false) String orientedUser,
			@RequestParam String limitDate, @RequestParam String invoice,
			@RequestParam(required = false) String relatedMaterial,
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("������������");

		String clientId = (String) request.getSession().getAttribute("userId");
		// ////////////////////////////////////////////
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

		boolean flag = goodsInfoService.insertGoods(name, type, weight,
				transportType, transportReq, startPlace, endPlace, damageReq,
				VIPService, oriented, limitDate, invoice, remarks, clientId,
				path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("goodsform?flag=1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mv;
	}

	@RequestMapping("getallresponse")
	/**
	 * ��ȡ���з���
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getAllResponse(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("���뷴��������");
		String userId = (String) request.getSession().getAttribute("userId");

		List responseList = goodsInfoService.getAllResponse(userId);

		mv.addObject("responseList", responseList);
		mv.setViewName("mgmt_d_response");
		return mv;
	}

	@RequestMapping("getresponseform")
	/**
	 * ��ȡ����������
	 * @param goodsid
	 * @return
	 */
	public ModelAndView getResponseForm(String goodsid) {
		mv.addObject("goodsId", goodsid);

		mv.setViewName("mgmt_d_response2");

		return mv;
	}

	@RequestMapping("commitresponse")
	/**
	 * ��������
	 * @param goodsid
	 * @return
	 */
	public ModelAndView commitResponse(String goodsid, String remarks,
			HttpServletRequest request, HttpServletResponse response) {

		String carrierId = (String) request.getSession().getAttribute("userId");
		// System.out.println("���봴������ ������+goodsid+" + goodsid);

		boolean flag = goodsInfoService.commitResponse(goodsid, remarks,
				carrierId);
		if (flag == true) {
			try {
				response.sendRedirect("getallresponse");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return mv;
	}

	@RequestMapping("mygoodsdetail")
	public ModelAndView myGoodsDetail(@RequestParam String id,
			@RequestParam int flag, HttpServletRequest request,
			HttpServletResponse response) {
		String clientId = (String) request.getSession().getAttribute("userId");
		GoodsClientView goodsformInfo = goodsInfoService.getAllGoodsDetail(id);
		// System.out.println(goodsformInfo);
		mv.addObject("goodsdetail", goodsformInfo);

		if (flag == 1) {
			mv.setViewName("mgmt_r_cargo4");
		}

		else if (flag == 2) {
			mv.setViewName("mgmt_r_cargo3");
		}

		return mv;
	}

	@RequestMapping(value = "updategoods", method = RequestMethod.POST)
	public ModelAndView updateGoods(@RequestParam MultipartFile file,
			@RequestParam String id,
			@RequestParam String name, @RequestParam String type,
			@RequestParam float weight, @RequestParam String transportType,
			@RequestParam String transportReq, @RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam String damageReq,
			@RequestParam String VIPService,
			@RequestParam(required = false) String VIPServiceDetail,
			@RequestParam(required = false) String oriented,
			@RequestParam(required = false) String orientedUser,
			@RequestParam String limitDate, @RequestParam String invoice,
			@RequestParam(required = false) String relatedMaterial,
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("������������");

		String clientId = (String) request.getSession().getAttribute("userId");

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mv;
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
			// TODO Auto-generated catch block
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
		System.out.println("����ɾ��������");
		System.out.println(id);
		// �˴���ȡsession���carrierid�����淽������һ������
		// String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��
		GoodsClientView goodsformInfo = goodsInfoService.getAllGoodsDetail(id);
		try {
			String file = goodsformInfo.getRelatedMaterial();
			/*File tempFile =new File(file.trim());  	          
	        String fileName = tempFile.getName();  			*/
			InputStream is = new FileInputStream(file);
			response.reset(); // ��Ҫ�����response�еĻ�����Ϣ
			response.setHeader("Content-Disposition", "attachment; filename="
					+ file);
			//response.setContentType("application/vnd.ms-excel");// ���ݸ�����Ҫ,����������ļ�������
			javax.servlet.ServletOutputStream out = response.getOutputStream();
			byte[] content = new byte[1024];
			int length = 0;
			while ((length = is.read(content)) != -1) {
				out.write(content, 0, length);
			}
			out.write(content);
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("�ض���ʧ��");
			e.printStackTrace();
		}

		//response.setHeader("Content-disposition", "attachment;filename="+ citylineInfo.getDetailPrice());
		return mv;

	}
	
}
