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

import cn.edu.bjtu.service.SettlementService;
import cn.edu.bjtu.vo.Carrierinfo;

/**
 * �ҵĽ���-������
 * @author RussWest0
 *
 */
@Controller
public class SettlementController {
	public ModelAndView mv=new ModelAndView();
	@Resource(name="settlementServiceImpl")
	SettlementService settlementService;
	
	@RequestMapping("/mysettlement")
	/**
	 * ��ȡ��ǰ�û��Ľ���
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getMySettlement(HttpServletRequest request,HttpServletResponse response)
	{
		String userId=(String )request.getSession().getAttribute("userId");
		
		List orderList=settlementService.getUserOrder(userId);
		mv.addObject("orderList", orderList);
		mv.setViewName("mgmt_d_settle_s");
		return mv;
	}
	
	@RequestMapping(value="findsettlement",method = RequestMethod.POST)
	/**
	 * ���Һ�ͬ
	 */
	public ModelAndView findSettlement(//@RequestParam String startDate,@RequestParam String endDate,
			@RequestParam String name, HttpServletResponse response, HttpServletRequest request)
	{
		int PageNow=1;//Ĭ�ϵĵ�ǰҳ��
		int Display=10;//Ĭ�ϵ�ÿҳ��С
		
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		List settlementList = settlementService.getFindSettlement(carrierId, name, Display, PageNow);
		System.out.println("settlementList+" + settlementList);
		mv.addObject("orderList", settlementList);
		
		int count = settlementService.getFindSettlementTotalRows(carrierId, name, Display, PageNow);// ��ȡ��ѯ�ܼ�¼��
		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		
		mv.setViewName("mgmt_d_settle_s");
		return mv;
		
	}
	
}
