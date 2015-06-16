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

import cn.edu.bjtu.service.AddressService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.vo.Address;


@Controller
public class AddressController {
	
	ModelAndView mv=new ModelAndView();
	
	@Autowired
	AddressService addressService;
	@Resource
	Address address;
	
	@RequestMapping("getaddress")
	public ModelAndView getAddress(HttpServletRequest request,HttpServletResponse response)
	{
		String userId=(String)request.getSession().getAttribute(Constant.USER_ID);
		List addressList = addressService.getAddress(userId);
		mv.addObject("addressList", addressList);
		mv.setViewName("mgmt_a_address");
		return mv;
	}
	
	
	@RequestMapping("deleteaddress")
	/**
	 * ɾ��
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView deleteAddress(
			@RequestParam String id,
			HttpServletRequest request,HttpServletResponse response){
		
		boolean flag = addressService.deleteAddress(id);
		try {
			if (flag == true)
				response.sendRedirect("getaddress");
			else
				System.out.println("ɾ��ʧ��");// Ӧ��¼��־
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// �˴�Ӧ��¼��־
			e.printStackTrace();

		}
		
		return mv;
	}
	
	@RequestMapping("addaddress")
	/**
	 * ��ת����������
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addAddress(
			HttpServletRequest request,HttpServletResponse response){
		
		String userId=(String)request.getSession().getAttribute(Constant.USER_ID);
		mv.setViewName("mgmt_a_address2");
		return mv;
	}
	
	 @RequestMapping("insertaddress")
	/**
	 * ��ת����������
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertAddress(
		@RequestParam String name,
		@RequestParam String address,
		@RequestParam String phone,
		HttpServletRequest request,HttpServletResponse response){
		
		String clientId=(String)request.getSession().getAttribute(Constant.USER_ID);
		boolean flag = addressService.insertAddress(name,address,phone,clientId);
			try {
				if (flag == true)
					response.sendRedirect("getaddress");
				else
					System.out.println("���ʧ��");// Ӧ��¼��־
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ��¼��־
				e.printStackTrace();

			}
			
		return mv;
	}
	
	 @RequestMapping("updateaddress")
		/**
		 * ��ת�����½���
		 * @param id
		 * @return
		 */
	 	public ModelAndView updateAddress(
	 			@RequestParam String id,
				HttpServletRequest request,HttpServletResponse response){
			
			String userId=(String)request.getSession().getAttribute(Constant.USER_ID);
			System.out.println("�Ѿ�����address������");

			Address address = addressService.getAddressDetail(id);
			System.out.println("address+" + address);
			mv.addObject("address", address);
			mv.setViewName("mgmt_a_address3");
			return mv;
	 }
	
	 @RequestMapping("doupdateaddress")
		public ModelAndView doUpdateAddress(
			@RequestParam String id,
			@RequestParam String name,
			@RequestParam String address,
			@RequestParam String phone,
			HttpServletRequest request,HttpServletResponse response){
			//System.out.println("�Ѿ�����updatesubaccount������");
				
			boolean flag = addressService.updateAddress(id, name, address,
					phone);
			try {
				if (flag == true)
					response.sendRedirect("getaddress");
				else
					System.out.println("����ʧ��");// Ӧ��¼��־
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ��¼��־
				e.printStackTrace();
			}
			
			return mv;
		}
	 
}
