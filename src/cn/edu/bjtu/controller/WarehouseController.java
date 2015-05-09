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
import cn.edu.bjtu.service.WarehouseService;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Warehouse;

@Controller
public class WarehouseController {

	@Resource
	WarehouseService warehouseService;
	@Resource
	CompanyService companyService;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/warehouse")
	/**
	 * �������вֿ���Ϣ����ͼ��ѯ��
	 * @return
	 */
	public ModelAndView getAllWarehouse(@RequestParam int flag,
			HttpServletRequest request) {
		int Display=10;//Ĭ�ϵ�ÿҳ��С
		int PageNow=1;//Ĭ�ϵĵ�ǰҳ��
		
		
		if (flag == 0) {// ��Ӧ��Դ���������
			List warehouseList = warehouseService.getAllWarehouse(Display,PageNow);
			int count = warehouseService.getTotalRows("All", "All", "All", "All");// ��ȡ�ܼ�¼��,����Ҫwhere�Ӿ䣬���Բ�������All
			System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);
			
			mv.addObject("warehouseList", warehouseList);
			mv.setViewName("resource_list4");
		} else if (flag == 1) {// ��Ӧ�ҵ���Ϣ�����������Ϣ
			String carrierId=(String)request.getSession().getAttribute("userId");
			// carrierId = "C-0002";// ��Ҫɾ��
			List warehouseList = warehouseService
					.getCompanyWarehouse(carrierId);
			mv.addObject("warehouseList", warehouseList);
			mv.setViewName("mgmt_r_warehouse");
		}
		return mv;
	}

	@RequestMapping(value = "/warehousedetail", method = RequestMethod.GET)
	/**
	 * ��ȡ�ض��Ĳֿ���Ϣ
	 * ͬʱ���ع�˾�Ͳֿ����������Ϣ
	 * @param
	 * @return
	 */
	public ModelAndView getWarehouseInfo(
			@RequestParam("warehouseId") String warehouseid,
			@RequestParam("carrierId") String carrierId,
			@RequestParam("flag") int flag) {
		Warehouse warehouseInfo = warehouseService
				.getWarehouseInfo(warehouseid);

		mv.addObject("warehouseInfo", warehouseInfo);
		if (flag == 0) {// ��Ӧ��Դ���ֿ�����
			Carrierinfo carrierInfo = companyService.getCarrierInfo(carrierId);
			mv.addObject("carrierInfo", carrierInfo);
			mv.setViewName("resource_detail4");
		} else if (flag == 1)// ��Ӧ�ҵ���Ϣ���ֿ�����
			mv.setViewName("mgmt_r_warehouse4");
		else if (flag == 2)// �ҵ���Ϣ���ֿ����
			mv.setViewName("mgmt_r_warehouse3");

		return mv;
	}

	@RequestMapping("warehouseselected")
	/**
	 * ���ط���ɸѡ�����Ĳֿ���Ϣ
	 * @param city
	 * @param type
	 * @param storageForm
	 * @param houseArea
	 * @param Display
	 * @param PageNow
	 * @return
	 */
	public ModelAndView getSelectedWarehouse(@RequestParam String city,
			@RequestParam String type, @RequestParam String storageForm,
			@RequestParam String houseArea, @RequestParam int Display,
			@RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("����ֿ�ɸѡ������");
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("�Ѿ����������");

		List warehouseList = warehouseService.getSelectedWarehouse(
				city, type, storageForm, houseArea,
				Display, PageNow);
		int count = warehouseService.getTotalRows(city, type, storageForm, houseArea);// ��ȡ�ܼ�¼��

		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		System.out.println("�ܼ�¼��+"+count);
		System.out.println("ҳ��+"+pageNum);
		mv.addObject("warehouseList", warehouseList);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("resource_list4");
		
		return mv;
	}

	@RequestMapping(value = "/insertWarehouse", method = RequestMethod.POST)
	/**
	 * �����ֿ�
	 * @param name
	 * @param contact
	 * @param address
	 * @param city
	 * @param type
	 * @param houseArea
	 * @param yardArea
	 * @param height
	 * @param kind
	 * @param fireRate
	 * @param storageForm
	 * @param environment
	 * @param phone
	 * @param remarks
	 * @param serviceContent
	 * @param fireSecurity
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertWarehouse(@RequestParam MultipartFile file,
			@RequestParam String name,
			@RequestParam String contact, @RequestParam String address,
			@RequestParam String city, @RequestParam String type,
			@RequestParam float houseArea, @RequestParam float yardArea,
			@RequestParam float height, @RequestParam String kind,
			@RequestParam String fireRate, @RequestParam String storageForm,
			@RequestParam String environment, @RequestParam String phone,
			@RequestParam String remarks, @RequestParam String serviceContent,
			@RequestParam String fireSecurity, HttpServletRequest request,
			HttpServletResponse response) {
		// �˴���ȡsession���carrierid�����淽������һ������
		// String
		String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";// ɾ��
		
		String path = null;
		String fileName = null;
		// System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getWarehousePath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
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
		
		System.out.println("�������ֿ������");
		boolean flag = warehouseService.insertWarehouse(name, city, address,
				type, kind, houseArea, yardArea, height, fireRate, storageForm,
				fireSecurity, environment, serviceContent, contact, phone,
				remarks, carrierId, path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("warehouse?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("warehouse������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}
	
	@RequestMapping(value = "/updateWarehouse", method = RequestMethod.POST)
	/**
	 * ���²ֿ���Ϣ
	 * @param id
	 * @param name
	 * @param city
	 * @param address
	 * @param type
	 * @param kind
	 * @param houseArea
	 * @param yardArea
	 * @param height
	 * @param fireRate
	 * @param storageForm
	 * @param fireSecurity
	 * @param environment
	 * @param serviceContent
	 * @param contact
	 * @param phone
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateWarehouse(@RequestParam MultipartFile file,
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String name,
			@RequestParam String city,
			@RequestParam String address,
			@RequestParam String type,
			@RequestParam String kind,
			@RequestParam float houseArea,
			@RequestParam float yardArea,
			@RequestParam float height,
			@RequestParam String fireRate,
			@RequestParam String storageForm,
			@RequestParam String fireSecurity,
			@RequestParam String environment,
			@RequestParam String serviceContent,
			@RequestParam String contact,
			@RequestParam String phone,
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {

		// �˴���ȡsession���carrierid�����淽������һ������
		// String
		String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";// ɾ��

		String path = null;
		String fileName = null;
		// System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getWarehousePath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
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
	
		
		boolean flag = warehouseService.updateWarehouse(id, name, city, address, type,
				kind, houseArea, yardArea, height, fireRate, storageForm, fireSecurity,
				environment, serviceContent, contact, phone, remarks, carrierId, path, fileName);
		if (flag == true) {
			
			try {
				response.sendRedirect("warehouse?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("warehouse���º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;

	}
	
	@RequestMapping(value = "warehousedelete", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteWarehouse(
			@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("����ɾ��������");
		System.out.println(id);
		// �˴���ȡsession���carrierid�����淽������һ������
		//String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��
		boolean flag = warehouseService.deleteWarehouse(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("warehouse?flag=1");// �ض�����ʾ���µĽ��
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
}
