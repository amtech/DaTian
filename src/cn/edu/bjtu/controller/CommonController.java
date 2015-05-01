package cn.edu.bjtu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.util.CheckLogin;
import cn.edu.bjtu.util.IdCreator;

@Controller
/**
 * �˿�������Ҫ���ڼ򵥵�ҳ����ת��û����������
 * @author RussWest0
 *
 */
public class CommonController {

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/myinfo")
	public ModelAndView getMyInfo() {
		mv.setViewName("mgmt");
		return mv;
	}

	@RequestMapping("/insert")
	/**
	 * �ҵ���Ϣ-�ҵ���Դ-���е���������
	 * @param flag
	 * @return
	 */
	public ModelAndView insert(@RequestParam int flag,HttpServletRequest request,HttpServletResponse response) {
		
		//CheckLogin.checkLogin(request, response);//����¼
		System.out.println("common������");
		if (flag == 1)
			mv.setViewName("mgmt_r_line2");// ����
		else if (flag == 2)
			mv.setViewName("mgmt_r_city2");// ��������
		else if (flag == 3)
			mv.setViewName("mgmt_r_car2");// ����
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
	/*@RequestMapping("userdetailinfo")
	public String getdetail*/
}
