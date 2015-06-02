package cn.edu.bjtu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.ResponseService;
import cn.edu.bjtu.vo.Response;

@Controller
/**
 * ������ؿ�����
 * @author RussWest0
 * @date   2015��6��2�� ����11:04:13
 */
public class ResponseController {
	
	@Autowired
	ResponseService responseService;
	
	ModelAndView mv=new ModelAndView();
	@RequestMapping("viewResponseDetail")
	public ModelAndView viewResponseDetail(String responseid){
		
		//ҳ����ȱ���ļ����ع���
		Response respose=responseService.getResponseById(responseid);
		mv.addObject("response", respose);
		mv.setViewName("mgmt_d_response3");
		return mv;
		
	}

}
