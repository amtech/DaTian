package cn.edu.bjtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CompanycertificateService;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Companycertificate;

@Controller
public class CompanycertificateController {
	@Resource
	CompanycertificateService companycertificateService;
	ModelAndView mv = new ModelAndView();
	

	@RequestMapping("getcompanyvalidateform")
	/**
	 * ��֤��˾��
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getCompanyValidateForm() {
		mv.setViewName("mgmt_a_info4");
		return mv;
	}
	
	
	@RequestMapping("companycertificate")
	/**
	 * ��֤��˾��Ϣ
	 */
	public ModelAndView companycertificate(
			@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String companyName, @RequestParam(required = false) String divisionCode,
			@RequestParam(required = false) String legalName, @RequestParam(required = false) String legalIDCard,
			@RequestParam(required = false) String companyAddr, @RequestParam(required = false) String companyType,
			@RequestParam(required = false) String companyScale, @RequestParam(required = false) String invoiceKind,
			@RequestParam(required = false) String serviceIndustry, @RequestParam(required = false) String businessKind,
			@RequestParam(required = false) String companyContact, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String basicSituation,
			HttpServletRequest request,	HttpServletResponse response) {
		String userId=(String)request.getSession().getAttribute("userId");
		
				String path = null;
				String fileName = null;
				if (file.getSize() != 0)// ���ϴ��ļ������
				{
					path = UploadPath.getCompanyCertificatePath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
					fileName = file.getOriginalFilename();
					fileName = userId + "_" + fileName;// �ļ���
					File targetFile = new File(path, fileName);
					try { // ���� �ļ�
						file.transferTo(targetFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// System.out.println("path+fileName+" + path + "-" + fileName);
				}
				// û���ϴ��ļ������path �� filenNameĬ��Ϊnull

		boolean flag=companycertificateService.validateCompany(userId,companyName,divisionCode,legalName,
				legalIDCard,companyAddr,companyType,companyScale,invoiceKind,serviceIndustry,
				businessKind,companyContact,phone,basicSituation,path,fileName);
		if(flag==true){
			try {
				response.sendRedirect("accountinfo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("��֤��˾����");//logging
				e.printStackTrace();
			}
		}
		else mv.setViewName("mgmt_a_info");
		return mv;
	}
	
	@RequestMapping("detailcompanycertificate")
	/**
	 * �鿴��˾��֤��Ϣ
	 */
	
	public ModelAndView detailCompanyCertificate(
			@RequestParam int flag,
			HttpServletRequest request, HttpServletResponse response) {

		String companyId = (String) request.getSession().getAttribute("userId");
		Companycertificate companycertificate = companycertificateService.getCompanycertificate(companyId);
		mv.addObject("detailCompanyCertificate", companycertificate);
		if (flag == 0) {// ��Ӧ��˾��֤�鿴����
			mv.setViewName("mgmt_a_info4a");
		} else if (flag == 1)// ��Ӧ��˾��֤����
		{
			mv.setViewName("mgmt_a_info6");
		} 
		return mv;
	}
	
	@RequestMapping(value = "downloadcompanycertificatematerial", method = RequestMethod.GET)
	/**
	 * ������֤��˾����ز���
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downloadCompanyCertificateMaterial(
			HttpServletRequest request, HttpServletResponse response) {
		
		String companyId=(String)request.getSession().getAttribute("userId");
		Companycertificate companycertificate = companycertificateService.getCompanycertificate(companyId);
		try {
			String file = companycertificate.getRelatedMaterial();
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
		return mv;

	}
	
	@RequestMapping("companycertificateupdate")
	/**
	 * ��֤��˾
	 */
	public ModelAndView companycertificateUpdate(
			@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String companyName, @RequestParam(required = false) String divisionCode,
			@RequestParam(required = false) String legalName, @RequestParam(required = false) String legalIDCard,
			@RequestParam(required = false) String companyAddr, @RequestParam(required = false) String companyType,
			@RequestParam(required = false) String companyScale, @RequestParam(required = false) String invoiceKind,
			@RequestParam(required = false) String serviceIndustry, @RequestParam(required = false) String businessKind,
			@RequestParam(required = false) String companyContact, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String basicSituation,
			HttpServletRequest request,	HttpServletResponse response) {
		System.out.println("������֤��˾���¿�����");
		String userId=(String)request.getSession().getAttribute("userId");
		
		// ////////////////////////////////////////////
				String path = null;
				String fileName = null;
				// System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
				if (file.getSize() != 0)// ���ϴ��ļ������
				{
					path = UploadPath.getCompanyCertificatePath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
					fileName = file.getOriginalFilename();
					fileName = userId + "_" + fileName;// �ļ���
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
		
		boolean flag=companycertificateService.companycertificateUpdate(userId,companyName,divisionCode,legalName,
				legalIDCard,companyAddr,companyType,companyScale,invoiceKind,serviceIndustry,
				businessKind,companyContact,phone,basicSituation,path,fileName);
		System.out.println(flag);
		if(flag==true){
			try {
				System.out.println("redirect֮ǰ");
				response.sendRedirect("accountinfo");
				System.out.println("redirect֮��");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("��֤��˾���³���");//logging
				e.printStackTrace();
			}
		}
		else mv.setViewName("mgmt_a_info");
		return mv;
	}
}
