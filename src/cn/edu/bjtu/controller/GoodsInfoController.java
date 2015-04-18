package cn.edu.bjtu.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.GoodsInfoService;
import cn.edu.bjtu.vo.GoodsClientView;

@Controller
public class GoodsInfoController {

	@Resource
	GoodsInfoService goodsInfoService;

	ModelAndView mv=new ModelAndView();
	
	@RequestMapping("/goodsform")
	public ModelAndView getAllGoodsInfo(@RequestParam int flag,
			HttpServletRequest request) {
		int Display=10;//Ĭ�ϵ�ÿҳ��С
		int PageNow=1;//Ĭ�ϵĵ�ǰҳ��
		
		if (flag == 0) {
			List goodsInfoList = goodsInfoService.getAllGoodsInfo(Display,PageNow);
			int count = goodsInfoService.getTotalRows("All", "All", "All");// ��ȡ�ܼ�¼��,����Ҫwhere�Ӿ䣬���Բ�������All
			System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);
			
			mv.addObject("goodsformInfo", goodsInfoList);
			mv.setViewName("resource_list6");// �����Դ������������ʾ������Ϣ
		} 
		/*else if (flag == 1) {
			// ������sessionȡid
			// String carrierId=request.getSession().getAttribute("carrierId");
			String carrierId = "C-0002";// ɾ��
			List goodformList = goodformService.getCompanyGoodform(carrierId);
			mv.addObject("goodformList", goodformList);
			mv.setViewName("mgmt_r_cargo");// �����߳���������ʾ������Ϣ
		}*/
		return mv;
	}
	
	@RequestMapping("/goodsdetail")
	public ModelAndView getAllGoodsDetail(
			@RequestParam String id
			)
	{
		System.out.println(id);
		GoodsClientView goodsformInfo=goodsInfoService.getAllGoodsDetail(id);
		System.out.println(goodsformInfo);
		mv.addObject("goodsformInfo",goodsformInfo);
		mv.setViewName("resource_detail6");
		
		return mv;
	}
	
	@RequestMapping("goodsformselected")
	/**
	 *  * ��ȡ���������ĸ���
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
		//System.out.println("�Ѿ����������");

		List goodsInfoList = goodsInfoService.getSelectedGoodsInfo(
				startPlace, endPlace, transportType, Display,
				PageNow);
		int count = goodsInfoService.getTotalRows(startPlace, endPlace, transportType);// ��ȡ�ܼ�¼��

		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		//System.out.println("�ܼ�¼��+"+count);
		//System.out.println("ҳ��+"+pageNum);
		mv.addObject("goodsformInfo", goodsInfoList);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("resource_list6");

		return mv;
	}
	
	@RequestMapping(value="insertGoods",method=RequestMethod.POST)
	public ModelAndView insertGoods(@RequestParam String name,
			@RequestParam String type,
			@RequestParam float weight,
			@RequestParam String transportType,
			@RequestParam String transportReq,
			@RequestParam String startPlace,
			@RequestParam String endPlace,
			@RequestParam String damageReq,
			@RequestParam String vipservice,
			@RequestParam String service,
			@RequestParam String oriented,
			@RequestParam String user,
			@RequestParam String limitDate,
			@RequestParam String invoice,
			@RequestParam String relatedMaterial,
			@RequestParam String remarks
			)
	{
		
		System.out.println("������������");
		boolean flag=goodsInfoService.insertGoods(name, type, weight, transportType, transportReq, startPlace, endPlace, damageReq, vipservice, oriented, limitDate, invoice, relatedMaterial, remarks);
		if(flag==true)
			mv.setViewName("mgmt_r_line");
		else 
			mv.setViewName("fail");
		//mv.setViewName("mgmt_r_line");
		return mv;
	}
	
}
