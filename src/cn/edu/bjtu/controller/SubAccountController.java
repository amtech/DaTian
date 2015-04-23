package cn.edu.bjtu.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.SubAccountService;
import cn.edu.bjtu.vo.SubAccount;

@Controller
public class SubAccountController {
	
	ModelAndView mv=new ModelAndView();
	
	@Resource 
	SubAccountService subAccountService;
	
	@RequestMapping("getsubaccount")
	public ModelAndView getSubAccount(HttpServletRequest request,HttpServletResponse response)
	{
		String userId=(String)request.getSession().getAttribute("userId");
		List subAccountList = subAccountService.getSubAccount(userId);
		System.out.println("subAccountList+" + subAccountList);
		mv.addObject("subAccountList", subAccountList);
		mv.setViewName("mgmt_a_subaccount");
		return mv;
	}
	
	@RequestMapping("findbyaccountname")
	/**
	 * ���˻��Ĳ�ѯ����
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView findByAccountName(
			@RequestParam String username,
			HttpServletRequest request,HttpServletResponse response){
		
		String userId=(String)request.getSession().getAttribute("userId");
		//System.out.println("�Ѿ�����subaccount������");

		List subAccountList = subAccountService.getFindSubAccount(userId, username);
		//System.out.println("subAccountList+" + subAccountList);
		mv.addObject("subAccountList", subAccountList);
		mv.setViewName("mgmt_a_subaccount");
		return mv;
	}
	
	@RequestMapping("subaccountdetail")
	/**
	 * ��ʾ�������˻���Ϣ
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView subAccountDetail(
			@RequestParam String id,
			HttpServletRequest request,HttpServletResponse response){
		
		String userId=(String)request.getSession().getAttribute("userId");
		//System.out.println("�Ѿ�����subaccount������");

		SubAccount subAccount = subAccountService.getSubAccountDetail(id);
		//System.out.println("subAccount+" + subAccount);
		mv.addObject("subAccount", subAccount);
		mv.setViewName("mgmt_a_subaccount4");
		return mv;
	}
	
	@RequestMapping("changestatus")
	/**
	 * �����û�ͣ������״̬
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView changeStatus(
			@RequestParam String id,
			HttpServletRequest request,HttpServletResponse response){
		
		String userId=(String)request.getSession().getAttribute("userId");
		System.out.println("�Ѿ�����subaccount������");
		boolean flag = subAccountService.changeStatus(id);
		try {
			if (flag == true)
				response.sendRedirect("getsubaccount");
			else
				System.out.println("�����û�ͣ������ʧ��");// Ӧ��¼��־
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// �˴�Ӧ��¼��־
			e.printStackTrace();

		}
		
		return mv;
	}
	
	@RequestMapping("deletesubaccount")
	/**
	 * ɾ���û�
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView deleteSubAccount(
			@RequestParam String id,
			HttpServletRequest request,HttpServletResponse response){
		
		String userId=(String)request.getSession().getAttribute("userId");
		//System.out.println("�Ѿ�����subaccount������");
		boolean flag = subAccountService.deleteSubAccount(id);
		try {
			if (flag == true)
				response.sendRedirect("getsubaccount");
			else
				System.out.println("ɾ���û�ʧ��");// Ӧ��¼��־
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// �˴�Ӧ��¼��־
			e.printStackTrace();

		}
		
		return mv;
	}
	
	@RequestMapping("addsubaccount")
	/**
	 * ��ת�������û�����
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addSubAccount(
			HttpServletRequest request,HttpServletResponse response){
		
		String userId=(String)request.getSession().getAttribute("userId");
		String username=(String)request.getSession().getAttribute("username");
		//System.out.println("�Ѿ�����subaccount������");
		mv.addObject("username", username);
		mv.setViewName("mgmt_a_subaccount2");
		return mv;
	}
	
	 @RequestMapping("insertsubaccount")
	/**
	 * ��ת�������û�����
	 * @param username
	 * @param password
	 * @param resourceManagement
	 * @param transactionManagement
	 * @param schemaManagement
	 * @param statisticsManagement
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertSubAccount(
		@RequestParam String username,
		@RequestParam String password,
		@RequestParam(required=false) String resourceManagement,
		@RequestParam(required=false) String transactionManagement,
		@RequestParam(required=false) String schemaManagement,
		@RequestParam(required=false) String statisticsManagement,
		@RequestParam String remarks,
		HttpServletRequest request,HttpServletResponse response){
		
		 String hostAccountId=(String)request.getSession().getAttribute("userId");
		 String hostAccountName=(String)request.getSession().getAttribute("username");
			
			//System.out.println("�Ѿ�����insertsubaccount������");
			
			boolean flag = subAccountService.insertSubAccount(username,password,resourceManagement,
					transactionManagement,schemaManagement,statisticsManagement,remarks,
					hostAccountId,hostAccountName);
			try {
				if (flag == true)
					response.sendRedirect("getsubaccount");
				else
					System.out.println("����û�ʧ��");// Ӧ��¼��־
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ��¼��־
				e.printStackTrace();

			}
			
		return mv;
	}
	
	 @RequestMapping("updatesubaccount")
		/**
		 * ��ת�������û�����
		 * @param id
		 * @return
		 */
	 	public ModelAndView updateSubAccount(
	 			@RequestParam String id,
				HttpServletRequest request,HttpServletResponse response){
			
			String userId=(String)request.getSession().getAttribute("userId");
			System.out.println("�Ѿ�����subaccount������");

			SubAccount subAccount = subAccountService.getSubAccountDetail(id);
			System.out.println("subAccount+" + subAccount);
			mv.addObject("subAccount", subAccount);
			mv.setViewName("mgmt_a_subaccount3");
			return mv;
	 }
	
	 @RequestMapping("doupdate")
		/**
		 * ��ת�������û�����
		 * @param id
		 * @param username
		 * @param password
		 * @param resourceManagement
		 * @param transactionManagement
		 * @param schemaManagement
		 * @param statisticsManagement
		 * @param remarks
		 * @param request
		 * @param response
		 * @return
		 */
		public ModelAndView doUpdate(
			@RequestParam String id,
			@RequestParam String username,
			@RequestParam String password,
			@RequestParam(required=false) String resourceManagement,
			@RequestParam(required=false) String transactionManagement,
			@RequestParam(required=false) String schemaManagement,
			@RequestParam(required=false) String statisticsManagement,
			@RequestParam String remarks,
			HttpServletRequest request,HttpServletResponse response){
			//System.out.println("�Ѿ�����updatesubaccount������");
				
			boolean flag = subAccountService.updateSubAccount(id, username, password,
					resourceManagement, transactionManagement, schemaManagement,
					statisticsManagement, remarks);
			try {
				if (flag == true)
					response.sendRedirect("getsubaccount");
				else
					System.out.println("����û�ʧ��");// Ӧ��¼��־
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ��¼��־
				e.printStackTrace();
			}
			
			return mv;
		}
	 
}
