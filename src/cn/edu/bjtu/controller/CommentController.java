package cn.edu.bjtu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CommentService;


@Controller
/**
 * ������صĲ���
 * @author RussWest0
 * @date   2015��5��23�� ����4:35:59
 */
public class CommentController {
	
	
	@Autowired
	 CommentService commentService;	
	
	ModelAndView mv=new ModelAndView();
	
	@RequestMapping("/commitcomment")
	public ModelAndView commitComment(HttpSession session ,
			String rate1,String rate2,String rate3,String rate4,
			String remarks)
	{
		String userId=(String)session.getAttribute("userId");
		if(userId==null)//δ��¼
		{
			mv.setViewName("login");
		}
		boolean flag=commentService.commitComment(rate1,rate2,rate3,rate4,remarks,userId);
		
		mv.setViewName("mgmt_d_order_s");
		return mv;
		
	}

}
