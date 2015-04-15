package cn.edu.bjtu.controller;

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
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CitylineService;
import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Cityline;

@Controller
/**
 * �����������
 * @author RussWest0
 *
 */
public class CitylineController {

	@Resource
	CitylineService citylineService;
	@Resource
	CompanyService companyService;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/cityline")
	/**
	 * ��ȡ���г���������·��Ϣ
	 * @param flag
	 * @return
	 */
	public ModelAndView getAllCityline(@RequestParam int flag,
			HttpServletRequest request) {
		int Display=10;//Ĭ�ϵ�ÿҳ��С
		int PageNow=1;//Ĭ�ϵĵ�ǰҳ��
		
		if (flag == 0) {
			List citylineList = citylineService.getAllCityline(Display,PageNow);
			int count = citylineService.getTotalRows("All", "All", "All");// ��ȡ�ܼ�¼��,����Ҫwhere�Ӿ䣬���Բ�������All
			System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);
			
			mv.addObject("citylineList", citylineList);
			mv.setViewName("resource_list2");// �����Դ������������ʾ������Ϣ
		} else if (flag == 1) {
			// ������sessionȡid
			String carrierId=(String) request.getSession().getAttribute("userId");
			//String carrierId = "C-0002";// ɾ��
			List citylineList = citylineService.getCompanyCityline(carrierId);
			mv.addObject("citylineList", citylineList);
			mv.setViewName("mgmt_r_city");// �����߳���������ʾ������Ϣ
		}
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
			@RequestParam("flag") int flag) {
		System.out.println("citylineid+" + citylineId);
		Cityline citylineInfo = citylineService.getCitylineInfo(citylineId); // ��Ҫ�ع�,����һ���������·����list

		mv.addObject("citylineInfo", citylineInfo);
		if (flag == 0) {
			Carrierinfo carrierInfo = companyService.getCarrierInfo(carrierId);
			mv.addObject("carrierInfo", carrierInfo);
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
	public ModelAndView getSelectedCityLine(@RequestParam String cityName,
			@RequestParam String VIPService, @RequestParam String refPrice,
			@RequestParam int Display, @RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("����cityline������");
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("�Ѿ����������");

		List citylineList = citylineService.getSelectedCityline(
				cityName, VIPService, refPrice, Display,
				PageNow);
		int count = citylineService.getTotalRows(cityName, VIPService, refPrice);// ��ȡ�ܼ�¼��
		//System.out.println("coount+"+count);
		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		//System.out.println("�ܼ�¼��+"+count);
		//System.out.println("ҳ��+"+pageNum);
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
	public ModelAndView insertCityLine(@RequestParam String name,
			@RequestParam String cityName, @RequestParam String VIPService,
			@RequestParam float refPrice, @RequestParam String remarks,
			@RequestParam(required = false) String VIPDetail,
			HttpServletRequest request, HttpServletResponse response) {
		String carrierId=(String)request.getSession().getAttribute("userId");
		// carrierId = "C-0002";// ɾ��
		/*boolean flag = linetransportService.insertLine(lineName, startPlace,
				endPlace, onWayTime, type, refPrice, remarks, carrierId);*/
		System.out.println("vipdetail+"+VIPDetail);
		boolean flag=citylineService.insertCityLine(name, cityName, VIPService, refPrice, remarks, carrierId, VIPDetail);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("cityline?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("cityline������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
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
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String citylineName, @RequestParam String cityName,
			@RequestParam String VIPService,
			@RequestParam String VIPServiceText,// ȱ����ϸ��ֵ�������
			@RequestParam float refPrice,
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {

		// �˴���ȡsession���carrierid�����淽������һ������
		String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��
		boolean flag = citylineService.updateLine(id, citylineName, 
				cityName, VIPService, VIPServiceText, refPrice, remarks, carrierId);
		if (flag == true) {
			
			try {
				response.sendRedirect("cityline?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("cityline���º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;

	}
	
	@RequestMapping(value = "citydelete", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteCityline(
			@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("����ɾ��������");
		System.out.println(id);
		// �˴���ȡsession���carrierid�����淽������һ������
		//String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��
		boolean flag = citylineService.deleteCityline(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("cityline?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("ɾ�����ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;

	}
	
}
