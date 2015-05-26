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
import cn.edu.bjtu.vo.Driverinfo;

@Controller
/**
 * �ͻ�������
 * @author RussWest0
 *
 */
public class ClientController {
	@Resource
	ClientService clientService;
	ModelAndView mv = new ModelAndView();

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
	 * ����¿ͻ�
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
		System.out.println("����client������insert");
		String carrierId = (String) request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��

		String path = null;
		String fileName = null;
		// System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
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
	
		
		boolean flag = clientService.insertClient(account, clientName,
				clientBusiness, contact, phone, remarks, carrierId,path,fileName);
		System.out.println("flag+" + flag);
		if (flag == true) {
			try {
				System.out.println("redirect֮ǰ");
				response.sendRedirect("client");// �ض�����ʾ���µĽ��
												// error,�޷��ض���
				// mv.setViewName("mgmt_r_car");
				System.out.println("redirect֮��");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("client������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}

	@RequestMapping(value = "updateClient", method = RequestMethod.POST)
	/**
	 * 
	 * ���¿ͻ�
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
	public ModelAndView updateClient(@RequestParam MultipartFile file,
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String account, @RequestParam String clientName,
			@RequestParam String clientBusiness, @RequestParam String contact,
			@RequestParam String phone, @RequestParam String remarks,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("����client������update");
		String carrierId = (String) request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��

		String path = null;
		String fileName = null;
		// System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
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
	
		
		boolean flag = clientService.updateClient(id, account, clientName,
				clientBusiness, contact, phone, remarks, carrierId,path,fileName);
		System.out.println("flag+" + flag);
		if (flag == true) {
			try {
				System.out.println("redirect֮ǰ");
				response.sendRedirect("client");// �ض�����ʾ���µĽ��
												// error,�޷��ض���
				// mv.setViewName("mgmt_r_car");
				System.out.println("redirect֮��");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("client���º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}

	@RequestMapping(value = "clientdelete", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteClient(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("����ɾ��������");
		System.out.println(id);
		// �˴���ȡsession���carrierid�����淽������һ������
		// String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��
		boolean flag = clientService.deleteClient(id);
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
			mv.setViewName("fail");
		return mv;
	}

	@RequestMapping("accountinfo")
	/**
	 * �г���ǰ�û�����֤��Ϣ
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getAccountInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// �˷����ڿ�����Ҫ�ж��û�����,��Ϊ��ҵ�û��͸����û�����֤ҳ�治һ��
		// ��ǰ�¿��Ǹ����û�����ҵ�û��Ȳ����ǣ������ݿ�û�б�)
		String userId = (String) request.getSession().getAttribute("userId");
		if(userId==null)//δ��¼
		{
			mv.setViewName("login");
			return mv;
		}
		int userKind=(Integer)request.getSession().getAttribute("userKind");
		boolean flag = clientService.checkHeadIcon(userId,userKind);
		// �����û�
		/*if(userKind==2){//��ͨ�û�{
*/		String status = clientService.getStatus(userId);
		mv.addObject("status", status);
		mv.addObject("headCheck", flag);
	/*}else if(userKind==3){//��ҵ�û�
		
	}*/
		mv.setViewName("mgmt_a_info");
		return mv;
	}

	@RequestMapping("basicuserinfo")
	/**
	 * ������Ϣ
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getBasicUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = (String) request.getSession().getAttribute("userId");

		String email = clientService.getBasicUserInfo(userId);
		mv.addObject("email", email);
		mv.setViewName("mgmt_a_info2");
		return mv;
	}

	@RequestMapping("getvalidateform")
	/**
	 * ��֤�˻���
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getValidateForm() {
		mv.setViewName("mgmt_a_info3");
		return mv;
	}

	@RequestMapping("validateaccount")
	public ModelAndView validateAccount(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = (String) request.getSession().getAttribute("userId");
		// δ���
		return mv;
	}

	@RequestMapping("validateuser")
	public ModelAndView validateUser(String realName, String phone,
			String IDCard, String sex, HttpServletRequest request,
			HttpServletResponse response) {
		//System.out.println("������֤�û�������");
		String userId=(String)request.getSession().getAttribute("userId");
		
		boolean flag=clientService.validateUser(userId,realName,phone,IDCard,sex);
		if(flag==true)
			try {
				response.sendRedirect("accountinfo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("��֤�˻�����");//logging
				e.printStackTrace();
			}
		return mv;
	}

	@RequestMapping(value = "downloadclientrelated", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView downloadClientRelated(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("����ɾ��������");
		System.out.println(id);
		// �˴���ȡsession���carrierid�����淽������һ������
		// String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��
		Businessclient clientInfo = clientService.getBusinessclientInfo(id);
		try {
			String file = clientInfo.getRelatedMaterial();
			/*File tempFile =new File(file.trim());  	          
	        String fileName = tempFile.getName();  			*/
			InputStream is = new FileInputStream(file);
			response.reset(); // ��Ҫ�����response�еĻ�����Ϣ
			response.setHeader("Content-Disposition", "attachment; filename="
					+ file);
			//response.setContentType("application/vnd.ms-excel");// ���ݸ�����Ҫ,����������ļ�������
			javax.servlet.ServletOutputStream out = response.getOutputStream();
			byte[] content = new byte[1024];
			int length = 0;
			while ((length = is.read(content)) != -1) {
				out.write(content, 0, length);
			}
			out.write(content);
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("�ض���ʧ��");
			e.printStackTrace();
		}

		//response.setHeader("Content-disposition", "attachment;filename="+ citylineInfo.getDetailPrice());
		return mv;

	}
	
	
}
