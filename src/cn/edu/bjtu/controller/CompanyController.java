package cn.edu.bjtu.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Linetransport;
/**
 * ��˾��ؿ�����
 * @author RussWest0
 *
 */
@Controller
public class CompanyController {
	
	@Resource
	CompanyService companyService;
	@Resource
	HibernateTemplate ht;
	
	ModelAndView mv=new ModelAndView();
	
	@RequestMapping("/company")
	/**
	 * �������й�˾��Ϣ
	 * @return
	 */
	public ModelAndView getAllCompany()
	{	
		int Display=10;//Ĭ�ϵ�ÿҳ��С
		int PageNow=1;//Ĭ�ϵĵ�ǰҳ��
		List companyList = companyService.getAllCompany(Display,PageNow);
		int count = companyService.getTotalRows("All", "All", "All", "All");// ��ȡ�ܼ�¼��,����Ҫwhere�Ӿ䣬���Բ�������All
		System.out.println("count+"+count);
		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		
		mv.addObject("companyList", companyList);
		mv.setViewName("resource_list5");// �����Դ������������ʾ������Ϣ
		return mv;
	}
	
	
	@RequestMapping(value="/companyDetail",method=RequestMethod.GET)
	/**
	 * ���ع�˾�ľ�����Ϣ
	 * @param id
	 * @return
	 */
	public ModelAndView companyDetail(@RequestParam String id)
	{
		System.out.println("id+"+id);
		Carrierinfo carrierinfo=companyService.getCarrierInfo(id);
		System.out.println("tele+"+carrierinfo.getPhone());//null
		
		//��˾��صĸ�����Ϣ������������Ϣ�Լ��ֿ���Ϣ
		List linetransportList = companyService.getLinetransportByCarrierId(id);
		List citylineList = companyService.getCitylineByCarrierId(id);
		List warehouseList = companyService.getwarehouseByCarrierId(id);
		
		mv.addObject("carrierinfo", carrierinfo);
		mv.addObject("linetransportList", linetransportList);
		mv.addObject("citylineList", citylineList);
		mv.addObject("warehouseList", warehouseList);
		mv.setViewName("resource_detail5");
		return mv;
	}
	
	@RequestMapping("companyselected")
	/**
	 * ���ҷ��������ĳ��˷�
	 * @param city
	 * @param resourceRate
	 * @param serviceIndustry
	 * @param creditRate
	 * @param business
	 * @param Display
	 * @param PageNow
	 * @return
	 */
	public ModelAndView getSelectedCompany(@RequestParam String city,
			@RequestParam String resourceRate,
			@RequestParam String serviceIndustry,
			@RequestParam String creditRate,
			@RequestParam String business,
			@RequestParam int Display,
			@RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response)
			{
				System.out.println("���빫˾ɸѡ������");
				try {
					response.setCharacterEncoding("UTF-8");
					request.setCharacterEncoding("UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("�Ѿ����������");
				System.out.println("rate+"+creditRate);
				
				List companyList = companyService.getSelectedCompany(
						resourceRate, serviceIndustry, creditRate, business, Display,
						PageNow);
				int count = companyService.getTotalRows(resourceRate, serviceIndustry, creditRate, business);// ��ȡ�ܼ�¼��

				int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
				//System.out.println("�ܼ�¼��+"+count);
				//System.out.println("ҳ��+"+pageNum);
				mv.addObject("companyList", companyList);
				mv.addObject("count", count);
				mv.addObject("pageNum", pageNum);
				mv.addObject("pageNow", PageNow);
				mv.setViewName("resource_list5");
				
				return mv;
			}

}
