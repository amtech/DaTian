package cn.edu.bjtu.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.ContractService;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Contract;

@Controller
/**
 * ��ͬ������
 * @author RussWest0
 *
 */
public class ContractController {

	@Resource(name="contractServiceImpl")
	ContractService contractService;
	@Resource
	CompanyService companyService;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/contract")
	/**
	 * ��ȡ��˾���еĺ�ͬ
	 * @param contractId
	 * @param flag
	 * @param request
	 * @return
	 */
	public ModelAndView getCompanyContract(HttpServletRequest request) {
		String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		List contractList = contractService.getCompanyContract(carrierId);
		System.out.println("contractList+" + contractList);
		mv.addObject("contractList", contractList);
		Carrierinfo carrierInfo = companyService.getCarrierInfo(carrierId);
		mv.addObject("carrierInfo", carrierInfo);
		mv.setViewName("mgmt_r_contact_s");
		return mv;

	}

	@RequestMapping("contractdetail")
	/**
	 * ��ͬ����
	 * @param contractId
	 * @param flag
	 * @return
	 */
	public ModelAndView getContractInfo(@RequestParam String contractId,
			@RequestParam int flag, HttpServletRequest request) {
		String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		Contract contract = contractService.getContractInfo(contractId);
		mv.addObject("contract", contract);
		Carrierinfo carrierInfo = companyService.getCarrierInfo(carrierId);
		mv.addObject("carrierInfo", carrierInfo);
		if (flag == 1)// ����
		{
			mv.setViewName("mgmt_r_contact_s4");
		} else if (flag == 2)// ��ֹ
		{
			mv.setViewName("mgmt_r_contact_s3");
		}

		return mv;

	}

	@RequestMapping(value = "insertContract", method = RequestMethod.POST)
	/**
	 * ������ͬ
	 * @param id
	 * @param name
	 * @param caculateType
	 * @param carrierAccount
	 * @param startDate
	 * @param endDate
	 * @param contact
	 * @param phone
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertContract(@RequestParam MultipartFile file,@RequestParam String id,
			@RequestParam String name, @RequestParam String caculateType,
			@RequestParam String carrierAccount,
			@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam String contact, @RequestParam String phone,
			@RequestParam String remarks,
			@RequestParam(required=false) String monthlyStatementDays, 
			HttpServletRequest request,	HttpServletResponse response) {
		String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";// ɾ��

		String path = null;
		String fileName = null;
		// System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getContactPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
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
	
		boolean flag = contractService.insertContract(id, name, caculateType,
				carrierAccount, startDate, endDate, contact, phone, remarks,
				carrierId, monthlyStatementDays, path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("contract");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("contract������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}
	@RequestMapping(value="shutdownContract",method = RequestMethod.POST)
	/**
	 * ��ֹ��ͬ
	 * @param contractId
	 * @param reason
	 * @param response
	 * @return
	 */
	public ModelAndView shutdownContract(@RequestParam String contractId,@RequestParam String reason,HttpServletResponse response)
	{
		
		boolean flag=false;
		flag=contractService.shutdownContract(contractId, reason);
		if(flag==true)
			try {
				response.sendRedirect("contract");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("��ֹ��ͬʧ��");
				e.printStackTrace();
			}
		return mv;
	}
	
	@RequestMapping(value="findcontract",method = RequestMethod.POST)
	/**
	 * ���Һ�ͬ
	 */
	public ModelAndView findContract(@RequestParam String startDate,@RequestParam String endDate,
			@RequestParam String name, HttpServletResponse response, HttpServletRequest request)
	{
		int PageNow=1;//Ĭ�ϵĵ�ǰҳ��
		int Display=10;//Ĭ�ϵ�ÿҳ��С
		
		System.out.println("����contract������");
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("�Ѿ����������");

		String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		List contractList = contractService.getFindContract(carrierId, startDate, endDate, name, Display, PageNow);
		System.out.println("contractList+" + contractList);
		mv.addObject("contractList", contractList);
		Carrierinfo carrierInfo = companyService.getCarrierInfo(carrierId);
		mv.addObject("carrierInfo", carrierInfo);
		
		int count = contractService.getFindContractTotalRows(carrierId, startDate, endDate, name, Display, PageNow);// ��ȡ��ѯ�ܼ�¼��
		System.out.println("coount+"+count);
		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		System.out.println("�ܼ�¼��+"+count);
		System.out.println("ҳ��+"+pageNum);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("mgmt_r_contact_s");
		return mv;
		
	}

}
