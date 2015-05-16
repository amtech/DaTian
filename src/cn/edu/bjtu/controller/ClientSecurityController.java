package cn.edu.bjtu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.ClientSecurityService;
import cn.edu.bjtu.vo.Userinfo;

@Controller
public class ClientSecurityController {

	@Autowired
	ClientSecurityService clientSecurityService;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/mysecurity")
	/**
	 * ��ȡ�ҵİ�ȫ����ҳ��
	 * @param session
	 * @return
	 */
	public ModelAndView getMySercurityPage(HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		// System.out.println("userId+"+userId);
		Userinfo userinfo = clientSecurityService.getUserById(userId);
		mv.addObject("userinfo", userinfo);
		mv.setViewName("mgmt_a_security");
		return mv;

	}

	@RequestMapping("getchangepasswordpage")
	/**
	 * ��ȡ�޸������
	 * @return
	 */
	public ModelAndView gotoChangePasswordPage() {

		mv.setViewName("mgmt_a_security2");
		return mv;
	}

	@RequestMapping("changepassword")
	/*
	 * �޸�����
	 */
	public ModelAndView changePassword(HttpSession session, String oldPassword,
			String newPassword, String repeatPassword) {
		String userId = (String) session.getAttribute("userId");
		boolean flag = false;
		flag = clientSecurityService.checkOldPassword(oldPassword, userId);
		if (flag == true)// ��������ȷ �����
		{
			if (newPassword.equals(repeatPassword))// ����������һ��
			{
				clientSecurityService.changePassword(newPassword, userId);// �޸�����
				String msg = "�޸�����ɹ�";
				mv.addObject("msg", msg);
				mv.setViewName("mgmt_a_security");
				return mv;
			} else// ���������벻һ��
			{
				String msg = "�����������벻һ�£�����������������!";
				mv.addObject("msg", msg);
				mv.setViewName("mgmt_a_security2");
				return mv;
			}

		} else// ���������
		{
			String msg = "ԭʼ�������!����������";
			mv.addObject("msg", msg);
			mv.setViewName("mgmt_a_security2");
			return mv;
		}
	}

	@RequestMapping("getbindemailpage")
	/**
	 * ������ҳ��
	 * @param session
	 * @return
	 */
	public ModelAndView gotoBindEmailPage() {
		mv.setViewName("mgmt_a_security4");
		return mv;
	}

	@RequestMapping("bindemail")
	public ModelAndView bindEmail(HttpSession session, String email) {
		String userId = (String) session.getAttribute("userId");
		boolean flag = clientSecurityService.bindEmail(email, userId);
		if (flag == true) {
			mv.setViewName("mgmt_a_security");
			String msg = "����󶨳ɹ�";
			mv.addObject("msg", msg);
			return mv;
		} else {
			mv.setViewName("mgmt_a_security4");
			String msg = "��ʧ��";
			mv.addObject("msg", msg);
			return mv;
		}

	}

	@RequestMapping("getchangebindemailpage")
	/**			     
	 * ��ȡ���������ҳ��
	 * @param session
	 * @return
	 */
	public ModelAndView gotoChangeEmailPage(HttpSession session) {
		String userId = (String) session.getAttribute("userId");

		String email = (String) session.getAttribute("email");
		// System.out.println("email+"+email);////
		mv.addObject("email", email);
		mv.setViewName("mgmt_a_security4b");
		return mv;
	}

	@RequestMapping("changebindemail")
	public ModelAndView changeBindEmail(HttpSession session, String newEmail) {
		String userId = (String) session.getAttribute("userId");
		boolean flag = false;
		flag = clientSecurityService.changeBindEmail(newEmail, userId);
		if (flag == true) {
			String msg = "�޸İ�����ɹ�";
			mv.addObject("msg", msg);
			mv.setViewName("mgmt_a_security");
			return mv;
		} else {
			String msg = "�޸İ����������������д!";
			mv.addObject("msg", msg);
			mv.setViewName("mgmt_a_security4b");
			return mv;
		}

	}
}
