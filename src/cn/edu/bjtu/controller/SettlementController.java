package cn.edu.bjtu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * �ҵĽ���-������
 * @author RussWest0
 *
 */
@Controller
public class SettlementController {
	public ModelAndView mv=new ModelAndView();
	
	@RequestMapping("/mysettlement")
	public ModelAndView getMySettlement(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("������������");
		String userId=(String )request.getSession().getAttribute("userId");
		
		//List orderList=
		
		return mv;
	}
}
