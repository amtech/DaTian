package cn.edu.bjtu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.DriverService;
import cn.edu.bjtu.util.IdCreator;

@Controller
/**
 * �˿�������Ҫ���ڼ򵥵�ҳ����ת��û����������
 * @author RussWest0
 *
 */
public class CommonController {
	
	/*@Autowired
	CarService carService;*/
	@Autowired
	DriverService driverService;
	@Autowired
	CompanyService companyService;
	ModelAndView mv = new ModelAndView();

	@RequestMapping("/myinfo")
	public ModelAndView getMyInfo(HttpSession session) {
		String userId=(String)session.getAttribute("userId");
		// add by RussWest0 at 2015��5��30��,����7:09:34 
		if(userId==null){
			mv.setViewName("login");
		}else{
			mv.setViewName("mgmt");
		}
		return mv;
	}

	@RequestMapping("/insert")
	/**
	 * �ҵ���Ϣ-�ҵ���Դ-���е���������
	 * @param flag
	 * @return
	 */
	public ModelAndView insert(@RequestParam int flag,HttpServletRequest request,HttpServletResponse response) {
		
		if (flag == 1)
			mv.setViewName("mgmt_r_line2");// ����
		else if (flag == 2)
			mv.setViewName("mgmt_r_city2");// ��������
		else if (flag == 3){
			String carrierId=(String)request.getSession().getAttribute("userId");
			List driverList = driverService.getAllDriver(carrierId);
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_car2");// ����
		}
		else if (flag == 4)
			mv.setViewName("mgmt_r_warehouse2");// �ֿ�
		else if (flag == 5)
			mv.setViewName("mgmt_r_driver2");// ˾��
		else if (flag == 6)
			mv.setViewName("mgmt_r_customer2");//�ͻ� 
		else if(flag==7)
		{
			String id=IdCreator.createContractId();
			mv.addObject("id", id);
			String clientId=(String)request.getSession().getAttribute("userId");
			List companyList = companyService.getAllCompanyWithoutPage();
			mv.addObject("companyList", companyList);
			mv.setViewName("mgmt_r_contact_s2");//��ͬ
		}
		else if (flag == 8)
			mv.setViewName("mgmt_d_complain2");//Ͷ�� 
		else if (flag == 9)
			mv.setViewName("mgmt_r_cargo2");//���� 
		else if (flag == 10)
			mv.setViewName("mgmt_r_car_fleet2");//����
		return mv;
	}
	
	@RequestMapping("loginForm")
	public String getLoginForm()
	{
		return "login";
	}
	@RequestMapping("registerForm")
	public String getRegisterForm()
	{
		return "register";
	}
	
	@RequestMapping(value="homepage",method=RequestMethod.GET)
	/**
	 * �ص���ҳ
	 * @return
	 */
	public String gotoHomePage()
	{
		return "index";
	}
}
