package cn.edu.bjtu.controller;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.AuthenticationService;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Clientinfo;

@Controller
/**
 * ��֤��ؿ�����
 * @author RussWest0
 *
 */
public class AuthenticationController {
	
	@Resource(name="authenticationServiceImpl")
	AuthenticationService authenticationService;

	ModelAndView mv = new ModelAndView();
	
	@RequestMapping("/authentic")
	/**
	 * ��ȡ���д���֤��Ϣ
	 * @param request
	 * @return
	 */
	public ModelAndView getAllAuthentication(HttpServletRequest request) {
		//String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		System.out.println("auth-controller");
		List validateList = authenticationService.getAllAuthentication();
		System.out.println("validateList" + validateList);
		mv.addObject("validateList", validateList);
		mv.setViewName("mgmt_m_register");
		return mv;

	}
	
	@RequestMapping("authenticdetail")
	/**
	 * ������֤��Ϣ����
	 * @param clientId
	 * @param flag
	 * @return
	 */
	public ModelAndView getAuthenticationInfo(@RequestParam String clientId,
			@RequestParam int flag, HttpServletRequest request, HttpServletResponse response) {
		//String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		Clientinfo clientinfo = authenticationService.getAuthenticationInfo(clientId);
		mv.addObject("clientinfo", clientinfo);
		if (flag == 1)// ����
		{
			//mv.setViewName("mgmt_m_register2");
		} else if (flag == 2)// ��ֹ
		{
			mv.setViewName("mgmt_m_register2");
		} else if (flag == 3)
		{
			boolean judge = authenticationService.updateAuthenticStatus(clientId,"�����");
			try {
				response.sendRedirect("authentic");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (flag == 4)
		{
			boolean judge = authenticationService.updateAuthenticStatus(clientId,"δͨ��");
			try {
				response.sendRedirect("authentic");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return mv;
	}
	
	@RequestMapping("authenticview")
	/**
	 * ��ͬ����
	 * @param clientId
	 * @param flag
	 * @return
	 */
	public ModelAndView getAuthenticationCheck(@RequestParam String clientId
			, HttpServletRequest request, HttpServletResponse response) {
		//String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		Clientinfo clientinfo = authenticationService.getAuthenticationInfo(clientId);
		mv.addObject("clientinfo", clientinfo);
		mv.setViewName("mgmt_m_register2a");
		return mv;
	}

}
