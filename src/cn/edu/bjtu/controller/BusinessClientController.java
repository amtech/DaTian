package cn.edu.bjtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import cn.edu.bjtu.service.ClientService;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Businessclient;

@Controller
/**
 * ��˾�ͻ�businessclient��ؿ�����
 * @author RussWest0
 * @date   2015��5��28�� ����11:16:36
 */
public class BusinessClientController {
	
	@Resource
	ClientService clientService;
	
	ModelAndView mv=new ModelAndView();
	
	
	@RequestMapping("/client")
	/**
	 * ��ȡ��˾���пͻ�
	 * @param request
	 * @return
	 */
	public ModelAndView getCompanyClient(HttpServletRequest request) {
		String carrierId = (String) request.getSession().getAttribute("userId");
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

	@RequestMapping(value = "insertClient", method = RequestMethod.POST)
	/**
	 * ���businessclient�¿ͻ�
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
	public ModelAndView insertClient(@RequestParam MultipartFile file,@RequestParam String account,
			@RequestParam String clientName,
			@RequestParam String clientBusiness, @RequestParam String contact,
			@RequestParam String phone, @RequestParam String remarks,
			HttpServletRequest request, HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute("userId");
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
		
		boolean flag = clientService.insertBusinessClient(account, clientName,
				clientBusiness, contact, phone, remarks, carrierId,path,fileName);
		if (flag == true) {
			try {
				response.sendRedirect("client");// �ض�����ʾ���µĽ��
												// error,�޷��ض���
				// mv.setViewName("mgmt_r_car");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("client������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_customer");
		return mv;
	}
	/**
	 * ����businessclient��Ϣ
	 */
	@RequestMapping(value = "updateClient", method = RequestMethod.POST)
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
	public ModelAndView updateClientInfo(@RequestParam MultipartFile file,
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String account, @RequestParam String clientName,
			@RequestParam String clientBusiness, @RequestParam String contact,
			@RequestParam String phone, @RequestParam String remarks,
			HttpServletRequest request, HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute("userId");
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
			// System.out.println("path+fileName+" + path + "-" + fileName);
		}
		// û���ϴ��ļ������path �� filenNameĬ��Ϊnull

		// ////////////////////////////////////////////
	
		
		boolean flag = clientService.updateBusinessClient(id, account, clientName,
				clientBusiness, contact, phone, remarks, carrierId,path,fileName);
		System.out.println("flag+" + flag);
		if (flag == true) {
			try {
				response.sendRedirect("client");// �ض�����ʾ���µĽ��
												// error,�޷��ض���
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("client���º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_customer");
		return mv;
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
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("ɾ�����ض���ʧ��");
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
		try {
			String file = clientInfo.getRelatedMaterial();
			File tempFile =new File(file.trim());  	          
	        String fileName = tempFile.getName();  			
			InputStream is = new FileInputStream(file);
			response.reset(); // ��Ҫ�����response�еĻ�����Ϣ
			response.setHeader("Content-Disposition", "attachment; filename="
					+ java.net.URLEncoder.encode(fileName, "UTF-8"));
			javax.servlet.ServletOutputStream out = response.getOutputStream();
			byte[] content = new byte[1024];
			int length = 0;
			while ((length = is.read(content)) != -1) {
				out.write(content, 0, length);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("�ض���ʧ��");
			e.printStackTrace();
		}

		return mv;
	}
}
