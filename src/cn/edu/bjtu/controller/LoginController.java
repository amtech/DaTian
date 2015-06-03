 package cn.edu.bjtu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.ComplaintService;
import cn.edu.bjtu.service.FocusService;
import cn.edu.bjtu.service.LoginService;
import cn.edu.bjtu.util.Encrypt;
import cn.edu.bjtu.vo.Userinfo;

@Controller
public class LoginController {
	@Autowired
	LoginService loginService;
	@Autowired
	FocusService focusService;
	ModelAndView mv = new ModelAndView();
	@Autowired
	ComplaintService complaintService;
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginAction(String username, String password,int userkind,
			HttpServletRequest request,HttpServletResponse response) {
		String psw = Encrypt.MD5(password);
		mv.addObject("username", username);
		mv.addObject("password", psw);
		
		Userinfo userinfo = loginService.checkLogin(username, psw,userkind);
		if (userinfo != null) {// ��¼�ɹ������
			request.getSession().setAttribute("username",userinfo.getUsername());
			request.getSession().setAttribute("userId", userinfo.getId());
			request.getSession().setAttribute("email", userinfo.getEmail());
			request.getSession().setAttribute("userKind",
					userinfo.getUserKind());// �û�����
			if (userinfo.getUserKind() == 1) {// ����Ա�û�
				List allCompliantList = complaintService.getAllUserCompliant();
				mv.addObject("allCompliantList", allCompliantList);
				mv.setViewName("mgmt_m_complain");
				return mv;
			} else {// �����û�
				mv.setViewName("mgmt");
			}
		}else {//��¼ʧ�ܵ����
			String msg = "��¼���������µ�¼!";
			mv.addObject("msg", msg);
			if(userkind==1){
				mv.setViewName("adminLogin");
			}else{
				mv.setViewName("login");
			}
			
		}
		return mv;

	}
	/*@RequestMapping("adminLogin")
	
	 * ����Ա��¼
	 * @param session
	 * @param response
	 * @return
	 
	// add by RussWest0 at 2015��5��30��,����11:24:12 
	public ModelAndView adminLogin(Userinfo userinfo,HttpSession session,HttpServletRequest request,HttpServletResponse response){
		
		
		String psw = Encrypt.MD5(userinfo.getPassword());
		Userinfo user = loginService.checkLogin(userinfo.getUsername(),psw,userinfo.getUserKind());
		if(user!=null){
			session.setAttribute("username", userinfo.getUsername());
			session.setAttribute("userId",userinfo.getId());
			session.setAttribute("email",userinfo.getEmail());
			session.setAttribute("userKind",userinfo.getUserKind());//�û�����
				//request.getRequestDispatcher("allcomplaint");
				//response.sendRedirect("allcomplaint");
				List allCompliantList=complaintService.getAllUserCompliant();
				mv.addObject("allCompliantList", allCompliantList);
				mv.setViewName("mgmt_m_complain");
				return mv;
		}else{
			mv.addObject("msg", "��¼�û���������������µ�¼");
			mv.setViewName("adminLogin");
			return mv;
		}
	}*/
	
	@RequestMapping("/logout")
	/**
	 * �˳���¼
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		session.removeAttribute("username");
		session.removeAttribute("userId");
		// add by RussWest0 at 2015��5��30��,����11:00:02 
		session.invalidate();//sessionʧЧ
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping("focusNum")
	public String focusNum(
			HttpSession session,HttpServletResponse response) throws Exception{
		String userId = (String) session.getAttribute("userId");
		List focusList = focusService.getFocusList(userId,"");
		int num = focusList.size();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(num);
		//System.out.println("username="+username);
		return null;
	}
}