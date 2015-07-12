package cn.edu.bjtu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.BusinessClientService;
import cn.edu.bjtu.service.ClientService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.DownloadFile;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Businessclient;
import cn.edu.bjtu.vo.Linetransport;

import com.alibaba.fastjson.JSONArray;

@Controller
/**
 * ��˾�ͻ�businessclient��ؿ�����
 * @author RussWest0
 * @date   2015��5��28�� ����11:16:36
 */
public class BusinessClientController {
	
	@Resource
	ClientService clientService;
	@Autowired
	BusinessClientService businessClientService;
	
	ModelAndView mv=new ModelAndView();
	
	
	@RequestMapping("/client")
	/**
	 * ��ȡ��˾���пͻ�
	 * @param request
	 * @return
	 */
	@Deprecated
	public ModelAndView getCompanyClient(HttpServletRequest request) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// ɾ��
		List clientList = clientService.getCompanyClient(carrierId);// ��ȡbusinessclient������user
																	// client
		mv.addObject("clientList", clientList);
		mv.setViewName("mgmt_r_customer");
		return mv;
	}
	
	@RequestMapping("clientdetail")
	/**
	 * ��ȡ�ͻ���ϸ��Ϣ
	 * @param clientId
	 * @param flag
	 * @return
	 */
	public ModelAndView getClientInfo(@RequestParam String clientId,
			@RequestParam int flag) {
		// Clientinfo clientInfo = clientService.getClientInfo(clientId);
		Businessclient clientInfo = clientService
				.getBusinessclientInfo(clientId);
		mv.addObject("clientInfo", clientInfo);
		if (flag == 1) {
			mv.setViewName("mgmt_r_customer4");
		} else if (flag == 2) {
			mv.setViewName("mgmt_r_customer3");
		}
		return mv;
	}

	/**
	 * ���businessclient�¿ͻ�
	 * @return
	 */
	@RequestMapping(value = "insertClient", method = RequestMethod.POST)
	public String insertNewClient(Businessclient client,MultipartFile file,
			HttpServletRequest request) {
		boolean flag=businessClientService.insertNewClient(client,file,request);
		return "redirect:client";
	}
	@Deprecated
	public ModelAndView insertClient(@RequestParam MultipartFile file,@RequestParam String account,
			@RequestParam String clientName,
			@RequestParam String clientBusiness, @RequestParam String contact,
			@RequestParam String phone, @RequestParam String remarks,
			HttpServletRequest request, HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);

		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getClientPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// û���ϴ��ļ������path �� filenNameĬ��Ϊnull
		
		boolean flag = clientService.insertBusinessClient(account, clientName,
				clientBusiness, contact, phone, remarks, carrierId,path,fileName);
		if (flag == true) {
			try {
				response.sendRedirect("client");// �ض�����ʾ���µĽ��
												// error,�޷��ض���
			} catch (Exception e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_customer");
		return mv;
	}
	/**
	 * ����businessclient��Ϣ
	 */
	//@RequestMapping(value = "updateClient", method = RequestMethod.POST)
	/**
	 * 
	 * ����businessclient��Ϣ
	 * @param id
	 * @param account
	 * @param clientName
	 * @param clientBusiness
	 * @param contact
	 * @param phone
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	public ModelAndView updateClientInfo(@RequestParam MultipartFile file,
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String account, @RequestParam String clientName,
			@RequestParam String clientBusiness, @RequestParam String contact,
			@RequestParam String phone, @RequestParam String remarks,
			HttpServletRequest request, HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// ɾ��

		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getClientPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// û���ϴ��ļ������path �� filenNameĬ��Ϊnull

		// ////////////////////////////////////////////
	
		
		boolean flag = clientService.updateBusinessClient(id, account, clientName,
				clientBusiness, contact, phone, remarks, carrierId,path,fileName);
		if (flag == true) {
			try {
				response.sendRedirect("client");// �ض�����ʾ���µĽ��
												// error,�޷��ض���
			} catch (Exception e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_customer");
		return mv;
	}

	@RequestMapping(value = "updateClient", method = RequestMethod.POST)
	public String updateNewClient(Businessclient client,MultipartFile file,
			HttpServletRequest request) {
		boolean flag=businessClientService.updateNewClient(client,file,request);
		return "redirect:client";
	}
	
	@RequestMapping(value = "clientdelete", method = RequestMethod.GET)
	/**
	 * ɾ��businessclient
	 */
	public ModelAndView deleteClient(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		boolean flag = clientService.deleteBusinessClient(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("client");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_customer");
		return mv;
	}
	
	/**
	 * ����businessclient��ص��ļ�
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "downloadclientrelated", method = RequestMethod.GET)
	public ModelAndView downloadClientRelated(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		Businessclient clientInfo = clientService.getBusinessclientInfo(id);
		String file = clientInfo.getRelatedMaterial();
		DownloadFile.downloadFile(file,request,response);

		return mv;
	}
	
	/**
	 * ��ȡ�û��������ͻ�(����ҳ��)
	 * @param session
	 * @return
	 */
	@RequestMapping(value="getUserBusinessClientAjax",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserBusinessClientAjax(HttpSession session){
		
		JSONArray jsonArray=businessClientService.getUserBusinessClient(session);
		
		return jsonArray.toString();
		
		
	}
	/**
	 * �ҵ���Ϣ-�ͻ���Ϣ
	 * @Title: getUserBusinessClient 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����4:26:54
	 */
	@ResponseBody
	@RequestMapping(value="getUserBusinessClientResourceAjax",produces = "text/html;charset=UTF-8")
	public String getUserBusinessClient(HttpSession session,PageUtil pageUtil){
		JSONArray jsonArray=businessClientService.getUserBusinessClient(session,pageUtil);
		
		return jsonArray.toString();
		
	}
	
	/**
	 * �ҵ���Ϣ-�ͻ���Ϣ�ܼ�¼��
	 * @Title: getUserBusinessClientTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����4:28:30
	 */
	@ResponseBody
	@RequestMapping(value="getUserBusinessClientTotalRowsAjax")
	public Integer getUserBusinessClientTotalRows(HttpSession session){
		return businessClientService.getUserBusinessClientTotalRows(session);
		
	}
}
