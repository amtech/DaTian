package cn.edu.bjtu.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.FocusService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.vo.Focus;

import com.alibaba.fastjson.JSONArray;

/**
 * ��ע��ؿ�����
 * @author RussWest0
 *
 */
@Controller
public class FocusController {

	@Autowired
	FocusService focusService;
	@Resource
	Focus focus;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("focus")
	/**
	  * ��עҳ��
	  * @param request
	  * @param response
	  * @return
 	  */
	public String insertFocus(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
			String clientId=(String)request.getSession().getAttribute(Constant.USER_ID);
			//String userKind=(String)request.getSession().getAttribute("useKind");
			
			if(clientId==null)
			{
				response.setContentType("text/html;charset=UTF-8");
			    response.getWriter().print("login");
				return null;
			}
			/*//������� add by RussWest0 at 2015��6��8��,����11:08:13 
			if(userKind==null){
				response.sendRedirect("loginForm");
			}*/
			String focusType = request.getParameter("type");
			String foucsId = request.getParameter("id");
			List focusJudgement = focusService.getFocusJudgement(clientId,focusType,foucsId);
			boolean flag = true;
			if(focusJudgement.isEmpty())
			{
				flag = focusService.insertFocus(clientId,focusType,foucsId);
			    response.setContentType("text/html;charset=UTF-8");
			    response.getWriter().print("insert");				
			}

			else{
				focus = (Focus) focusJudgement.get(0);
				flag = focusService.deleteFocus(focus.getId());
				response.setContentType("text/html;charset=UTF-8");
			    response.getWriter().print("delete");
			}
			
			return null;
		}
	
	@RequestMapping("getallfocus")
	/**
	 *��ȡ��ǰ�û������й�ע
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getAllFocus(HttpServletRequest request,
			HttpServletResponse response) {
		String clientId = (String) request.getSession().getAttribute(Constant.USER_ID);
		if (clientId == null) {
			mv.setViewName("login");
			return mv;
		}
		List focusLineList = focusService.getAllFocusLine(clientId);
		mv.addObject("focusLineList", focusLineList);
		List focusCitylineList = focusService.getAllFocusCityline(clientId);
		mv.addObject("focusCitylineList", focusCitylineList);
		List focusWarehouseList = focusService.getAllFocusWarehouse(clientId);
		mv.addObject("focusWarehouseList", focusWarehouseList);
		List focusCarList = focusService.getAllFocusCar(clientId);
		mv.addObject("focusCarList", focusCarList);
		List focusCompanyList = focusService.getAllFocusCompany(clientId);
		mv.addObject("focusCompanyList", focusCompanyList);
		List focusGoodsList = focusService.getAllFocusGoods(clientId);
		mv.addObject("focusGoodsList", focusGoodsList);
		mv.setViewName("mgmt_d_focus");
		
		return mv;
	}
	
	@RequestMapping("deletefocus")
	/**
	 * ��עҳ��
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView deleteFocus(HttpServletRequest request,
			HttpServletResponse response,@RequestParam String id) {
		String clientId = (String) request.getSession().getAttribute(Constant.USER_ID);
		if (clientId == null) {
			mv.setViewName("login");
			return mv;
		}
		boolean flag = focusService.deleteFocus(id);
		
		try {
			if (flag == true)
				response.sendRedirect("getallfocus");
			else
				System.out.println("ɾ��ʧ��");// Ӧ��¼��־
		} catch (IOException e) {
			// 
			// �˴�Ӧ��¼��־
			e.printStackTrace();

		}
		
		return mv;
	}
	
	@RequestMapping("findfocus")
	/**
	 * ���˻��Ĳ�ѯ����
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView findFocus(
			@RequestParam String text,
			HttpServletRequest request,HttpServletResponse response){
		
		String clientId=(String)request.getSession().getAttribute(Constant.USER_ID);	
		
		List focusLineList = focusService.findFocusLine(text,clientId);
		mv.addObject("focusLineList", focusLineList);
		List focusCitylineList = focusService.findFocusCityline(text,clientId);
		mv.addObject("focusCitylineList", focusCitylineList);
		List focusWarehouseList = focusService.findFocusWarehouse(text,clientId);
		mv.addObject("focusWarehouseList", focusWarehouseList);
		List focusCarList = focusService.findFocusCar(text,clientId);
		mv.addObject("focusCarList", focusCarList);
		List focusCompanyList = focusService.findFocusCompany(text,clientId);
		mv.addObject("focusCompanyList", focusCompanyList);
		List focusGoodsList = focusService.findFocusGoods(text,clientId);
		mv.addObject("focusGoodsList", focusGoodsList);
		mv.setViewName("mgmt_d_focus");
		
		return mv;
	}
	
	/**
	 * ������ע
	 * @Title: searchFocus 
	 * @Description: TODO 
	 * @param: @param search_content
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��6�� ����11:44:57
	 */
	@RequestMapping(value="searchFocus",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String searchFocus(String search_content,HttpSession session){
		JSONArray jsonArray=focusService.searchFocus(search_content,session);
		
		return jsonArray.toString();
	}

	
}
