package cn.edu.bjtu.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.FocusService;
import cn.edu.bjtu.vo.Focus;

@Controller
/**
 * ��ע��ؿ�����
 * @author RussWest0
 *
 */
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
			String clientId=(String)request.getSession().getAttribute("userId");
			/*if(clientId==null)
			{
				mv.setViewName("login");
				return mv;
			}*/
			String focusType = request.getParameter("type");
			String foucsId = request.getParameter("id");
			List focusJudgement = focusService.getFocusJudgement(clientId,focusType,foucsId);
			//System.out.println("focusJudgement="+focusJudgement);
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
				//System.out.println(focus.getId());
			}
			//boolean flag = focusService.insertFocus(clientId,focusType,foucsId);
/*				try {
					if (flag == true)
						response.sendRedirect("getaddress");
					else
						System.out.println("����ʧ��");// Ӧ��¼��־
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// �˴�Ӧ��¼��־
					e.printStackTrace();

				}*/
				
			return null;
		}
	
	@RequestMapping("getallfocus")
	/**
	 * ��עҳ��
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getAllFocus(HttpServletRequest request,
			HttpServletResponse response) {
		String clientId = (String) request.getSession().getAttribute("userId");
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
		String clientId = (String) request.getSession().getAttribute("userId");
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
			// TODO Auto-generated catch block
			// �˴�Ӧ��¼��־
			e.printStackTrace();

		}
		
		return mv;
	}
}