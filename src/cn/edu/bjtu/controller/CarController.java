package cn.edu.bjtu.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import cn.edu.bjtu.bean.search.CarSearchBean;
import cn.edu.bjtu.service.CarService;
import cn.edu.bjtu.service.CarTeamService;
import cn.edu.bjtu.service.CommentService;
import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.DriverService;
import cn.edu.bjtu.service.FocusService;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.DownloadFile;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Carinfo;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Carteam;
import cn.edu.bjtu.vo.Comment;
import cn.edu.bjtu.vo.Driverinfo;
import cn.edu.bjtu.vo.Linetransport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class CarController {
	@Autowired
	CommentService commentService;
	@Autowired
	CarService carService;
	@Resource
	CompanyService companyService;
	
	@Autowired
	CarTeamService carTeamService;
	
	@Autowired
	DriverService driverService;
	
	@Resource
	LinetransportService linetransportService;
	@Autowired
	FocusService focusService;

	ModelAndView mv = new ModelAndView();

	/**
	 * ��Դ��-������Ϣ
	 * @return
	 */
	@RequestMapping(value="/car",params="flag=0")
	public String getAllCar() {
		return "resource_list3";
	}
	
	/**
	 * ��ȡ�ҵ���Ϣ-������Ϣ
	 * @return
	 */
	@Deprecated
	@RequestMapping(value="car",params="flag=1")
	public ModelAndView getMyInfoCar(HttpServletRequest request){
		String carrierId = (String) request.getSession().getAttribute(
				"userId");
		// String carrierId = "C-0002";// ɾ��
		List carList = carService.getCompanyCar(carrierId);
		mv.addObject("carList", carList);
		List driverList = driverService.getAllDriverName(carrierId);
		mv.addObject("driverList", driverList);
		mv.setViewName("mgmt_r_car");// ��̨��ûʵ��
		return mv;
		
	}
	
	/**
	 * ��Դ����ȡɸѡ��ĳ�����Ϣ
	 * @return
	 */
	@RequestMapping(value="getSelectedCarAjax",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCarSelected(CarSearchBean carBean,PageUtil pageUtil,HttpSession session){
		
		JSONArray jsonArray = carService.getSelectedCarNew(carBean, pageUtil,
				session);
		
		return jsonArray.toString();
	}
	
	/**
	 * ������Դ-����ɸѡ��¼������
	 * @return
	 */
	@RequestMapping(value="getSelectedCarTotalRowsAjax",method = RequestMethod.POST)
	@ResponseBody
	public Integer getSelectedCarTotalRows(CarSearchBean carBean){
		Integer count=carService.getSelectedCarTotalRows(carBean);
		return count;
	}

	/**
	 * ��ȡ�ض��ĳ�����Ϣ
	 * ͬʱ���ع�˾�ͳ������������Ϣ
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/cardetail", method = RequestMethod.GET)
	public ModelAndView getCarInfo(@RequestParam("carId") String carId,
			@RequestParam("carrierId") String carrierId,
			@RequestParam("linetransportId") String linetransportId,
			@RequestParam("flag") int flag, HttpServletRequest request) {
		Carinfo carInfo = carService.getCarInfo(carId);// ������Ϣ
		mv.addObject("carInfo", carInfo);
		String clientId = (String) request.getSession().getAttribute(Constant.USER_ID);
		List focusList = focusService.getFocusList(clientId,"car");
		Linetransport line = linetransportService
				.getLinetransportInfo(linetransportId);// ������Ϣ
		mv.addObject("focusList", focusList);
		mv.addObject("linetransportInfo", line);
		if (flag == 0) {// ��Ӧ��Դ����������
			Carrierinfo carrierInfo = companyService.getCompanyById(carrierId);
			List<Comment> commentList=commentService.getCompanyComment(carrierId);
			//��Ҫ��ȡ��Դ��Ӧ�Ĺ�˾������ƽ����bean
			Comment comment=commentService.getCompanyAverageCommentRate(carrierId);
			mv.addObject("avgComment", comment);
			mv.addObject("commentList",commentList);
			
			mv.addObject("carrierInfo", carrierInfo);

			mv.setViewName("resource_detail3");
		} else if (flag == 1)// ��Ӧ�ҵ���Ϣ�г�����Ϣ
		{
			// ��Ҫ˾����Ϣ
			Driverinfo driverinfo = driverService.getDriverByCarId(carId);
			mv.addObject("driverInfo", driverinfo);
			mv.setViewName("mgmt_r_car4");
		} else if (flag == 2)// ��Ӧ�ҵ���Ϣ-����-����
		{
			// ��Ҫ˾����Ϣ
			Driverinfo driverinfo = driverService.getDriverByCarId(carId);
			mv.addObject("driverInfo", driverinfo);
			List driverList = driverService.getAllDriver(carrierId);
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_car3");
		}
		return mv;
	}

	@RequestMapping("carselected")
	/**
	 * ���ط���ɸѡ�����ĳ�����Ϣ
	 * @param carLocation1
	 * @param endPlace
	 * @param carUse
	 * @param carColdStorage
	 * @param carLength
	 * @param carLocation
	 * @param Display
	 * @param PageNow
	 * @return
	 */
	@Deprecated
	public ModelAndView getSelectedCar(@RequestParam String carLocation,
			@RequestParam String endPlace, @RequestParam String carBase,
			@RequestParam String carLength, @RequestParam String carWeight,
			// @RequestParam String location,
			@RequestParam int Display, @RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// 
			e.printStackTrace();
		}

		List carList = carService.getSelectedCar(carLocation,
		// endPlace, û��Ŀ�ĳ���
				carBase, carLength, carWeight,
				// location, û�ж�λ��Ϣ
				Display, PageNow);
		int count = carService.getTotalRows(carLocation,
		// endPlace,
				carBase, carLength, carWeight
		// , location
				);// ��ȡ�ܼ�¼��

		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		mv.addObject("carList", carList);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("resource_list3");

		return mv;
	}

	@RequestMapping("driver")
	/**
	 * ��ȡ˾���б�
	 * @param flag
	 * @return
	 */
	@Deprecated
	public ModelAndView getAllDriver(@RequestParam int flag,
			HttpServletRequest request, HttpServletResponse response) {
		// ��session��ȡ��id��ѯ
		if (flag == 0) {// ���е�˾����Ϣ
			List driverList = driverService.getAllDriver();
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_driver");
		} else if (flag == 1) {// ��˾˾���б�
			// ������sessionȡid
			String carrierId = (String) request.getSession().getAttribute(
					"userId");
			// String carrierId = "C-0002";// ɾ��
			List driverList = driverService.getCompanyDriver(carrierId);
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_driver");
		}

		return mv;
	}

	@RequestMapping("driverdetail")
	/**
	 * ˾����Ϣ����
	 * @param driverId
	 * @param flag
	 * @return
	 */
	public ModelAndView getDriverInfo(@RequestParam String driverId,
			@RequestParam int flag) {
		Driverinfo driver = driverService.getDriverInfo(driverId);
		mv.addObject("driver", driver);
		if (flag == 1) {// ��Ӧ˾������
			mv.setViewName("mgmt_r_driver4");
		} else if (flag == 2)// ��Ӧ˾������
		{
			mv.setViewName("mgmt_r_driver3");
		}

		return mv;
	}

	@RequestMapping(value = "insertCar", method = RequestMethod.POST)
	/**
	 * ����������Ϣ
	 */
	public String insertNewCar(Carinfo car,
			HttpServletRequest request) {
		boolean flag=carService.insertNewCar(car,request);
		return "redirect:car?flag=1";
	}
	@Deprecated
	public ModelAndView insertCar(@RequestParam String carNum,
			@RequestParam String carTeam, @RequestParam String locationType,
			@RequestParam(required = false) String terminalId,
			@RequestParam String carType, @RequestParam String carBase,
			@RequestParam String carBrand, @RequestParam String carUse,
			@RequestParam double carLength, @RequestParam double carWidth,
			@RequestParam double carHeight, @RequestParam double carWeight,
			@RequestParam String driverId, @RequestParam String purchaseTime,
			@RequestParam String storage, @RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam String stopPlace,
			HttpServletRequest request,	HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		boolean flag = carService.insertCar(carNum, carTeam, locationType, terminalId,
				carBase, carBrand, carType, carUse, carLength, carWidth,
				carHeight, carWeight, driverId, purchaseTime, storage,
				startPlace, endPlace, stopPlace, carrierId);
		if (flag == true) {
			try {
				response.sendRedirect("car?flag=1");// �ض�����ʾ���µĽ�� error,�޷��ض���
				// mv.setViewName("mgmt_r_car");
			} catch (Exception e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;
	}

	@RequestMapping(value = "/insertDriver", method = RequestMethod.POST)
	/**
	 * ����˾��
	 */
	public String insertNewDriver(Driverinfo driver,MultipartFile file,
			HttpServletRequest request) {
		boolean flag=driverService.insertNewDriver(driver,request,file);
		return "redirect:driver?flag=1";
	}
	@Deprecated
	public ModelAndView insertDriver(
			@RequestParam(required = false) MultipartFile file,// new add
			@RequestParam String name, @RequestParam String sex,
			@RequestParam String licenceRate, @RequestParam String phone,
			@RequestParam String IDCard, @RequestParam String licenceNum,
			@RequestParam String licenceTime,

			HttpServletRequest request, HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// ɾ��
		// ////////////////////////////////////////////////////////////////////////

		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getDriverPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// //////////////////////////////////////////////////////////////////
		}
		// û���ϴ��ļ������path �� filenNameĬ��Ϊnull

		boolean flag = driverService.insertDriver(name, sex, licenceRate, phone,
				IDCard, licenceNum, licenceTime, carrierId, path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("driver?flag=1");// �ض�����ʾ���µĽ��
														// error,�޷��ض���
				// mv.setViewName("mgmt_r_car");
			} catch (Exception e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_driver");
		return mv;
	}

	//@RequestMapping(value = "updateCar", method = RequestMethod.POST)
	/**
	 * * ���³�����Ϣ��������˾����·�ߣ�
	 * @param id
	 * @param carNum
	 * @param carTeam
	 * @param locType
	 * @param GPSText
	 * @param carType
	 * @param carBase
	 * @param carBrand
	 * @param carUse
	 * @param carLength
	 * @param carWidth
	 * @param carHeight
	 * @param carWeight
	 * @param carPurTime
	 * @param storage
	 * @param driverName
	 * @param startPlace
	 * @param endPlace
	 * @param stopPlace
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	public ModelAndView updateCar(
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String carNum,
			@RequestParam String carTeam,
			@RequestParam String locType,
			@RequestParam String terminalId,
			@RequestParam String carType, @RequestParam String carBase,
			@RequestParam String carBrand, @RequestParam String carUse,
			@RequestParam double carLength, @RequestParam double carWidth,
			@RequestParam double carHeight, @RequestParam double carWeight,
			@RequestParam String carPurTime, @RequestParam String storage,
			@RequestParam String driverId, @RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam String stopPlace,
			HttpServletRequest request, HttpServletResponse response) {

		// �˴���ȡsession���carrierid�����淽������һ������
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// ɾ��

		boolean flag = carService.updateCar(id, carNum, carTeam, locType,
				terminalId, carType, carBase, carBrand, carUse, carLength,
				carWidth, carHeight, carWeight, carPurTime, storage, driverId,
				startPlace, endPlace, stopPlace, carrierId);

		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("car?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;

	}

	@RequestMapping(value = "updateCar", method = RequestMethod.POST)
	public String updateNewCar(Carinfo car,
			HttpServletRequest request) {
		boolean flag=carService.updateNewCar(car,request);
		return "redirect:car?flag=1";
	}
	
	//@RequestMapping(value = "/updateDriver", method = RequestMethod.POST)
	/**
	 * * ����˾����Ϣ
	 * @param id
	 * @param name
	 * @param sex
	 * @param IDCard
	 * @param licenceNum
	 * @param licenceRate
	 * @param licenceTime
	 * @param phone
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	public ModelAndView updateDriver(
			@RequestParam(required = false) MultipartFile file,// new add
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String name, @RequestParam String sex,
			@RequestParam String IDCard, @RequestParam String licenceNum,
			@RequestParam String licenceRate, @RequestParam String licenceTime,
			@RequestParam String phone, HttpServletRequest request,
			HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// ɾ��

		// ////////////////////////////////////////////////////////////////////////
		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getDriverPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
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
		boolean flag = driverService.updateDriver(id, name, sex, IDCard,
				licenceNum, licenceRate, licenceTime, phone, carrierId, path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("driver?flag=1");// �ض�����ʾ���µĽ��
														// error,�޷��ض���
				// mv.setViewName("mgmt_r_car");
			} catch (Exception e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_driver");
		return mv;
	}

	@RequestMapping(value = "/updateDriver", method = RequestMethod.POST)
	public String updateNewDriver(Driverinfo driver,MultipartFile file,
			HttpServletRequest request) {
		boolean flag=driverService.updateNewDriver(driver,request,file);
		return "redirect:driver?flag=1";
	}
	
	
	@RequestMapping(value = "cardelete", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteCar(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		boolean flag = carService.deleteCar(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("car?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;

	}

	@RequestMapping(value = "driverdelete", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteDriver(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		boolean flag = driverService.deleteDriver(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("driver?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_driver");
		return mv;

	}

	/**
	 * ��ȡ�����б�
	 * @return
	 */
	@RequestMapping("carteam")
	public ModelAndView getCarteam(HttpServletRequest request,
			HttpServletResponse response) {
		// ��session��ȡ��id��ѯ
		// ������sessionȡid
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// ɾ��
		List<Carteam> carteamList = carTeamService.getCarteam(carrierId);
		mv.addObject("carteamList", carteamList);
		mv.setViewName("mgmt_r_car_fleet");

		return mv;
	}

	@RequestMapping(value = "/carteamdetail", method = RequestMethod.GET)
	/**
	 * ��ȡ�ض��ĳ�����Ϣ
	 * @param
	 * @return
	 */
	public ModelAndView getCarteamDetail(@RequestParam String id,
			@RequestParam("flag") int flag, HttpServletRequest request) {
		Carteam carteaminfo = carTeamService.getCarteamInfo(id);// ������Ϣ
		mv.addObject("carteaminfo", carteaminfo);
		if (flag == 1)// ��Ӧ������Ϣ�鿴
		{
			mv.setViewName("mgmt_r_car_fleet4");
		} else if (flag == 2)// ��Ӧ������Ϣ����
		{
			mv.setViewName("mgmt_r_car_fleet3");
		}
		return mv;
	}

	@RequestMapping(value = "insertcarteam", method = RequestMethod.POST)
	/**
	 */
	public ModelAndView insertCarteam(@RequestParam String teamName,
			@RequestParam String carCount, @RequestParam String chief,
			@RequestParam String phone, @RequestParam String explaination,
			HttpServletRequest request, HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		boolean flag = carTeamService.insertCarteam(teamName, carCount, chief,
				phone, explaination, carrierId);
		// boolean flag=true;
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("carteam");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;
	}

	@RequestMapping(value = "deletecarteam", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteCarteam(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {

		boolean flag = carTeamService.deleteCarteam(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("carteam");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;

	}

	@RequestMapping(value = "updatecarteam", method = RequestMethod.POST)
	/**
	 */
	public ModelAndView updateCarteam(@RequestParam String id,
			@RequestParam String teamName, @RequestParam String carCount,
			@RequestParam String chief, @RequestParam String phone,
			@RequestParam String explaination, HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = carTeamService.updateCarteam(id, teamName, carCount, chief,
				phone, explaination);
		// boolean flag=true;
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("carteam");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// 
				// �˴�Ӧ�ü�¼��־
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;
	}
	
	@RequestMapping(value = "downloadidscans", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView downloadIdScans(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		Driverinfo driverinfo = driverService.getDriverInfo(id);
			String file = driverinfo.getIdscans();
			DownloadFile.downloadFile(file,request,response);
		return mv;

	}
	
	/**
	 * �첽��ȡ��˾�ĳ����б�
	 * @Title: getCompanyCarteamList 
	 * @param: @return 
	 * @return: String 
	 * @throws: �쳣 
	 * @author: chendonghao 	
	 * @date: 2015��6��29�� ����5:32:46
	 */
	@RequestMapping(value="getCompanyCarteamList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getCompanyCarteamList(HttpSession session){
		List<Carteam> carTeamList=carService.getCompanyCarteamList(session);
		//JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		if(carTeamList!=null && carTeamList.size()>0){
			for(int i=0;i<carTeamList.size();i++){
				JSONObject jsonObject=(JSONObject)JSONObject.toJSON(carTeamList.get(i));
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray.toString();
		
	}
	
	
	/**
	 * �ҵ���Ϣ-������Ϣ
	 * @Title: getUserCarResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����11:10:00
	 */
	@RequestMapping(value="getUserCarResourceAjax",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserCarResource(HttpSession session,PageUtil pageUtil){
		
		JSONArray jsonArray=carService.getUserCarResource(session,pageUtil);
		
		return jsonArray.toString();
	}
	
	/**
	 * �ҵ���Ϣ-������Ϣ-�ܼ�¼����
	 * @Title: getUserCarResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����11:11:56
	 */
	@RequestMapping(value="getUserCarResourceTotalRowsAjax")
	@ResponseBody
	public Integer getUserCarResourceTotalRows(HttpSession session){
		
		return carService.getUserCarResourceTotalRows(session);
	}
	/**
	 * �ҵ���Ϣ-˾����Ϣ
	 * @Title: getUserCarResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����11:10:00
	 */
	@RequestMapping(value="getUserDriverResourceAjax",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserDriverResource(HttpSession session,PageUtil pageUtil){
		
		JSONArray jsonArray=driverService.getUserDriverResource(session,pageUtil);
		
		return jsonArray.toString();
	}
	
	/**
	 * �ҵ���Ϣ-������Ϣ-�ܼ�¼����
	 * @Title: getUserCarResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����11:11:56
	 */
	@RequestMapping(value="getUserDriverResourceTotalRowsAjax")
	@ResponseBody
	public Integer getUserDriverResourceTotalRows(HttpSession session){
		
		return driverService.getUserDriverResourceTotalRows(session);
	}
	
	/**
	 * �ҵ���Դ-������Ϣ-����i��Ϣ
	 * @Title: getUserCarTeamResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��15�� ����11:17:55
	 */
	@RequestMapping(value="getUserCarTeamResourceAjax",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserCarTeamResource(HttpSession session,PageUtil pageUtil){
		return carTeamService.getUserCarTeamResource(session,pageUtil);
	}
	/**
	 * �ҵ���Դ-������Ϣ-����i��Ϣ-�ܼ�¼��
	 * @Title: getUserCarTeamResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��15�� ����11:19:43
	 */
	@RequestMapping("getUserCarTeamResourceTotalRowsAjax")
	@ResponseBody
	public Integer getUserCarTeamResourceTotalRows(HttpSession session){
		return carTeamService.getUserCarTeamResourceTotalRows(session);
	}

	
	/**
	 * �ӹ�˾����ҳ����붩�����ع�˾�ĳ�����Դ  
	 * @param carrierId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getCompanyCarAjax",produces="text/html;charset=UTF-8")
	public String getCompanyCarAjax(String carrierId){
		return carService.getCompanyCarAjax(carrierId);
		
	}
}
