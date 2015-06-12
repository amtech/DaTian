package cn.edu.bjtu.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.ContractService;
import cn.edu.bjtu.util.DownloadFile;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Contract;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
	private Logger logger=Logger.getLogger(ContractController.class);

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/contract")
	/**
	 * ��ȡ�����û����еĺ�ͬ
	 * @param contractId
	 * @param flag
	 * @param request
	 * @return
	 */
	public ModelAndView getCompanyContractForUser(HttpServletRequest request) {
		String clientId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		List<Contract> contractList = contractService.getContractByClientId(clientId);
		mv.addObject("contractList", contractList);
		
		List companyList = companyService.getAllCompanyWithoutPage();
		mv.addObject("companyList", companyList);
		mv.setViewName("mgmt_r_contact_s");
		return mv;

	}
	
	@RequestMapping("/contract2")
	/**
	 * ��ȡ��˾���еĺ�ͬ
	 * @param contractId
	 * @param flag
	 * @param request
	 * @return
	 */
	public ModelAndView getCompanyContractForCompany(HttpServletRequest request) {
		String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		List contractList = contractService.getCompanyContract(carrierId);
		mv.addObject("contractList", contractList);
		Carrierinfo carrierInfo = companyService.getCompanyById(carrierId);
		mv.addObject("carrierInfo", carrierInfo);
		mv.setViewName("mgmt_r_contact_r");
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
		Carrierinfo carrierInfo = companyService.getCompanyById(carrierId);
		mv.addObject("carrierInfo", carrierInfo);
		//����
		if (flag == 1)// ����
		{
			mv.setViewName("mgmt_r_contact_s4");
		} else if (flag == 2)// ��ֹ
		{
			mv.setViewName("mgmt_r_contact_s3");
		}
		 else if (flag == 3)// ��ֹ��鿴��������ֹԭ��
		{
			mv.setViewName("mgmt_r_contact_s4a");
		}
		//���˷�
		 else if (flag == 11)// ȷ��
		{
			mv.setViewName("mgmt_r_contact_r2");
		} else if (flag == 22)// ��ֹ
		{
			mv.setViewName("mgmt_r_contact_r3");
		}
		 else if (flag == 33)// ��ֹ��鿴��������ֹԭ��
		{
			mv.setViewName("mgmt_r_contact_r4a");
		}
		 else if (flag == 44)// ��ͨ�鿴����
		{
			mv.setViewName("mgmt_r_contact_r4");
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
			//@RequestParam String carrierAccount,
			@RequestParam String carrierId,
			@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam String contact, @RequestParam String phone,
			@RequestParam String remarks,
			@RequestParam(required=false) String monthlyStatementDays, 
			HttpServletRequest request,	HttpServletResponse response) {
		String clientId=(String)request.getSession().getAttribute("userId");

		String path = null;
		String fileName = null;
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
		}
		// û���ϴ��ļ������path �� filenNameĬ��Ϊnull

		// ////////////////////////////////////////////
	
		String carrierAccount=companyService.getCompanyById(carrierId).getCompanyName();
		
		boolean flag = contractService.insertContract(id, name, caculateType,
				carrierAccount,carrierId, startDate, endDate, contact, phone, remarks,
				clientId, monthlyStatementDays, path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("contract");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_contact_s");
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
	public ModelAndView shutdownContract(@RequestParam String contractId,
			@RequestParam int rorsflag,//��ʶ�ǳ��˷���������
			@RequestParam String reason,HttpServletResponse response)
	{
		
		boolean flag=false;
		flag=contractService.shutdownContract(contractId, reason);
		if(flag==true&&rorsflag==1)
			try {
				response.sendRedirect("contract");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if(flag==true&&rorsflag==2)
			try {
				response.sendRedirect("contract2");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return mv;
	}
	
	@RequestMapping(value="findcontract",method = RequestMethod.POST)
	/**
	 * ���Һ�ͬ
	 */
	public ModelAndView findContract(@RequestParam int flag,
			@RequestParam String startDate,@RequestParam String endDate,
			@RequestParam String name, HttpServletResponse response, HttpServletRequest request)
	{
		int PageNow=1;//Ĭ�ϵĵ�ǰҳ��
		int Display=10;//Ĭ�ϵ�ÿҳ��С
		
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(flag==1){//����

		String clientId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";
		List contractList = contractService.getFindContract(clientId, startDate, endDate, name, Display, PageNow);
		mv.addObject("contractList", contractList);
		/*Carrierinfo carrierInfo = companyService.getCompanyById(clientId);
		mv.addObject("carrierInfo", carrierInfo);*/
		
		int count = contractService.getFindContractTotalRows(clientId, startDate, endDate, name, Display, PageNow);// ��ȡ��ѯ�ܼ�¼��
		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);

			mv.setViewName("mgmt_r_contact_s");
		}
		if(flag==2){//���˷�

			String carrierId=(String)request.getSession().getAttribute("userId");
			//String carrierId = "C-0002";
			List contractList = contractService.getFindContract2(carrierId, startDate, endDate, name, Display, PageNow);
			mv.addObject("contractList", contractList);
			/*Carrierinfo carrierInfo = companyService.getCompanyById(carrierId);
			mv.addObject("carrierInfo", carrierInfo);*/
			
			int count = contractService.getFindContractTotalRows(carrierId, startDate, endDate, name, Display, PageNow);// ��ȡ��ѯ�ܼ�¼��
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);

			mv.setViewName("mgmt_r_contact_s");
			mv.setViewName("mgmt_r_contact_r");
		}
		return mv;
		
	}

	@RequestMapping(value = "downloadcontactrelated", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView downloadContactRelated(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		Contract contract = contractService.getContractInfo(id);
		String file = contract.getRelatedMaterial();
		DownloadFile.downloadFile(file,request,response);
		return mv;

	}
	
	
	@RequestMapping(value = "confirmcontract", method = RequestMethod.POST)
	public ModelAndView confirmContract(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		String userId=(String)request.getSession().getAttribute("userId");
		boolean flag = contractService.changeStatus(id);
		try {
			if (flag == true)
				response.sendRedirect("contract2");
			else
				System.out.println("ȷ�Ϻ�ͬʧ��");// Ӧ��¼��־
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// �˴�Ӧ��¼��־
			e.printStackTrace();

		}
		return mv;
	}
	
	/**
	 * ��ȡ��ǰ�û��ĺ�ͬid
	 * @param currentUserId
	 * @return
	 */
	@RequestMapping("getUserContractIdAjax")
	@ResponseBody
	public String getUserContractId(String currentUserId,HttpServletResponse response){
		List<Contract> contractList=contractService.getContractByClientId(currentUserId);
		JSONArray jsonArray=new JSONArray();
		for(int i=0;i<contractList.size();i++){
			JSONObject jsonObject=(JSONObject)JSONObject.toJSON(contractList.get(i));
			jsonArray.add(jsonObject);
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=UTF-8");
		return jsonArray.toString();
		
	}
	
}
