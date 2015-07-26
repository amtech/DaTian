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
	 * 资源栏-车辆信息
	 * @return
	 */
	@RequestMapping(value="/car",params="flag=0")
	public String getAllCar() {
		return "resource_list3";
	}
	
	/**
	 * 获取我的信息-车辆信息
	 * @return
	 */
	@Deprecated
	@RequestMapping(value="car",params="flag=1")
	public ModelAndView getMyInfoCar(HttpServletRequest request){
		String carrierId = (String) request.getSession().getAttribute(
				"userId");
		// String carrierId = "C-0002";// 删除
		List carList = carService.getCompanyCar(carrierId);
		mv.addObject("carList", carList);
		List driverList = driverService.getAllDriverName(carrierId);
		mv.addObject("driverList", driverList);
		mv.setViewName("mgmt_r_car");// 后台还没实现
		return mv;
		
	}
	
	/**
	 * 资源栏获取筛选后的车辆信息
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
	 * 返回资源-车辆筛选记录总条数
	 * @return
	 */
	@RequestMapping(value="getSelectedCarTotalRowsAjax",method = RequestMethod.POST)
	@ResponseBody
	public Integer getSelectedCarTotalRows(CarSearchBean carBean){
		Integer count=carService.getSelectedCarTotalRows(carBean);
		return count;
	}

	/**
	 * 获取特定的车辆信息
	 * 同时返回公司和车辆两个表的信息
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/cardetail", method = RequestMethod.GET)
	public ModelAndView getCarInfo(@RequestParam("carId") String carId,
			@RequestParam("carrierId") String carrierId,
			@RequestParam("linetransportId") String linetransportId,
			@RequestParam("flag") int flag, HttpServletRequest request) {
		Carinfo carInfo = carService.getCarInfo(carId);// 车辆信息
		mv.addObject("carInfo", carInfo);
		String clientId = (String) request.getSession().getAttribute(Constant.USER_ID);
		List focusList = focusService.getFocusList(clientId,"car");
		Linetransport line = linetransportService
				.getLinetransportInfo(linetransportId);// 干线信息
		mv.addObject("focusList", focusList);
		mv.addObject("linetransportInfo", line);
		if (flag == 0) {// 对应资源栏车辆详情
			Carrierinfo carrierInfo = companyService.getCompanyById(carrierId);
			List<Comment> commentList=commentService.getCompanyComment(carrierId);
			//需要获取资源对应的公司的评价平均数bean
			Comment comment=commentService.getCompanyAverageCommentRate(carrierId);
			mv.addObject("avgComment", comment);
			mv.addObject("commentList",commentList);
			
			mv.addObject("carrierInfo", carrierInfo);

			mv.setViewName("resource_detail3");
		} else if (flag == 1)// 对应我的信息列车辆信息
		{
			// 需要司机信息
			Driverinfo driverinfo = driverService.getDriverByCarId(carId);
			mv.addObject("driverInfo", driverinfo);
			mv.setViewName("mgmt_r_car4");
		} else if (flag == 2)// 对应我的信息-车辆-更新
		{
			// 需要司机信息
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
	 * 返回符合筛选条件的车辆信息
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
		// endPlace, 没有目的城市
				carBase, carLength, carWeight,
				// location, 没有定位信息
				Display, PageNow);
		int count = carService.getTotalRows(carLocation,
		// endPlace,
				carBase, carLength, carWeight
		// , location
				);// 获取总记录数

		int pageNum = (int) Math.ceil(count * 1.0 / Display);// 页数
		mv.addObject("carList", carList);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("resource_list3");

		return mv;
	}

	@RequestMapping("driver")
	/**
	 * 获取司机列表
	 * @param flag
	 * @return
	 */
	@Deprecated
	public ModelAndView getAllDriver(@RequestParam int flag,
			HttpServletRequest request, HttpServletResponse response) {
		// 从session里取出id查询
		if (flag == 0) {// 所有的司机信息
			List driverList = driverService.getAllDriver();
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_driver");
		} else if (flag == 1) {// 公司司机列表
			// 这里用session取id
			String carrierId = (String) request.getSession().getAttribute(
					"userId");
			// String carrierId = "C-0002";// 删除
			List driverList = driverService.getCompanyDriver(carrierId);
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_driver");
		}

		return mv;
	}

	@RequestMapping("driverdetail")
	/**
	 * 司机信息详情
	 * @param driverId
	 * @param flag
	 * @return
	 */
	public ModelAndView getDriverInfo(@RequestParam String driverId,
			@RequestParam int flag) {
		Driverinfo driver = driverService.getDriverInfo(driverId);
		mv.addObject("driver", driver);
		if (flag == 1) {// 对应司机详情
			mv.setViewName("mgmt_r_driver4");
		} else if (flag == 2)// 对应司机更新
		{
			mv.setViewName("mgmt_r_driver3");
		}

		return mv;
	}

	@RequestMapping(value = "insertCar", method = RequestMethod.POST)
	/**
	 * 新增车辆信息
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
				response.sendRedirect("car?flag=1");// 重定向，显示最新的结果 error,无法重定向
				// mv.setViewName("mgmt_r_car");
			} catch (Exception e) {
				// 
				// 此处应该记录日志
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;
	}

	@RequestMapping(value = "/insertDriver", method = RequestMethod.POST)
	/**
	 * 新增司机
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
		// String carrierId = "C-0002";// 删除
		// ////////////////////////////////////////////////////////////////////////

		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// 有上传文件的情况
		{
			path = UploadPath.getDriverPath();// 不同的地方取不同的上传路径
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// 文件名
			File targetFile = new File(path, fileName);
			try { // 保存 文件
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// //////////////////////////////////////////////////////////////////
		}
		// 没有上传文件的情况path 和 filenName默认为null

		boolean flag = driverService.insertDriver(name, sex, licenceRate, phone,
				IDCard, licenceNum, licenceTime, carrierId, path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("driver?flag=1");// 重定向，显示最新的结果
														// error,无法重定向
				// mv.setViewName("mgmt_r_car");
			} catch (Exception e) {
				// 
				// 此处应该记录日志
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_driver");
		return mv;
	}

	//@RequestMapping(value = "updateCar", method = RequestMethod.POST)
	/**
	 * * 更新车辆信息（不包括司机和路线）
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
			@RequestParam String id,// GET方式传入，在action中
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

		// 此处获取session里的carrierid，下面方法增加一个参数
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// 删除

		boolean flag = carService.updateCar(id, carNum, carTeam, locType,
				terminalId, carType, carBase, carBrand, carUse, carLength,
				carWidth, carHeight, carWeight, carPurTime, storage, driverId,
				startPlace, endPlace, stopPlace, carrierId);

		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("car?flag=1");// 重定向，显示最新的结果
			} catch (IOException e) {
				// 
				// 此处应该记录日志
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
	 * * 更新司机信息
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
			@RequestParam String id,// GET方式传入，在action中
			@RequestParam String name, @RequestParam String sex,
			@RequestParam String IDCard, @RequestParam String licenceNum,
			@RequestParam String licenceRate, @RequestParam String licenceTime,
			@RequestParam String phone, HttpServletRequest request,
			HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// 删除

		// ////////////////////////////////////////////////////////////////////////
		String path = null;
		String fileName = null;
		if (file.getSize() != 0)// 有上传文件的情况
		{
			path = UploadPath.getDriverPath();// 不同的地方取不同的上传路径
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// 文件名
			File targetFile = new File(path, fileName);
			try { // 保存 文件
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 没有上传文件的情况path 和 filenName默认为null
		boolean flag = driverService.updateDriver(id, name, sex, IDCard,
				licenceNum, licenceRate, licenceTime, phone, carrierId, path, fileName);
		if (flag == true) {
			try {
				response.sendRedirect("driver?flag=1");// 重定向，显示最新的结果
														// error,无法重定向
				// mv.setViewName("mgmt_r_car");
			} catch (Exception e) {
				// 
				// 此处应该记录日志
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
	 * 删除
	 */
	public ModelAndView deleteCar(@RequestParam String id,// GET方式传入，在action中
			HttpServletRequest request, HttpServletResponse response) {
		boolean flag = carService.deleteCar(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("car?flag=1");// 重定向，显示最新的结果
			} catch (IOException e) {
				// 
				// 此处应该记录日志
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;

	}

	@RequestMapping(value = "driverdelete", method = RequestMethod.GET)
	/**
	 * 删除
	 */
	public ModelAndView deleteDriver(@RequestParam String id,// GET方式传入，在action中
			HttpServletRequest request, HttpServletResponse response) {
		boolean flag = driverService.deleteDriver(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("driver?flag=1");// 重定向，显示最新的结果
			} catch (IOException e) {
				// 
				// 此处应该记录日志
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_driver");
		return mv;

	}

	/**
	 * 获取车队列表
	 * @return
	 */
	@RequestMapping("carteam")
	public ModelAndView getCarteam(HttpServletRequest request,
			HttpServletResponse response) {
		// 从session里取出id查询
		// 这里用session取id
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		// String carrierId = "C-0002";// 删除
		List<Carteam> carteamList = carTeamService.getCarteam(carrierId);
		mv.addObject("carteamList", carteamList);
		mv.setViewName("mgmt_r_car_fleet");

		return mv;
	}

	@RequestMapping(value = "/carteamdetail", method = RequestMethod.GET)
	/**
	 * 获取特定的车队信息
	 * @param
	 * @return
	 */
	public ModelAndView getCarteamDetail(@RequestParam String id,
			@RequestParam("flag") int flag, HttpServletRequest request) {
		Carteam carteaminfo = carTeamService.getCarteamInfo(id);// 车队信息
		mv.addObject("carteaminfo", carteaminfo);
		if (flag == 1)// 对应车队信息查看
		{
			mv.setViewName("mgmt_r_car_fleet4");
		} else if (flag == 2)// 对应车队信息更新
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
				response.sendRedirect("carteam");// 重定向，显示最新的结果
			} catch (IOException e) {
				// 
				// 此处应该记录日志
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;
	}

	@RequestMapping(value = "deletecarteam", method = RequestMethod.GET)
	/**
	 * 删除
	 */
	public ModelAndView deleteCarteam(@RequestParam String id,// GET方式传入，在action中
			HttpServletRequest request, HttpServletResponse response) {

		boolean flag = carTeamService.deleteCarteam(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("carteam");// 重定向，显示最新的结果
			} catch (IOException e) {
				// 
				// 此处应该记录日志
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
				response.sendRedirect("carteam");// 重定向，显示最新的结果
			} catch (IOException e) {
				// 
				// 此处应该记录日志
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_car");
		return mv;
	}
	
	@RequestMapping(value = "downloadidscans", method = RequestMethod.GET)
	/**
	 * 删除
	 */
	public ModelAndView downloadIdScans(@RequestParam String id,// GET方式传入，在action中
			HttpServletRequest request, HttpServletResponse response) {
		Driverinfo driverinfo = driverService.getDriverInfo(id);
			String file = driverinfo.getIdscans();
			DownloadFile.downloadFile(file,request,response);
		return mv;

	}
	
	/**
	 * 异步获取公司的车队列表
	 * @Title: getCompanyCarteamList 
	 * @param: @return 
	 * @return: String 
	 * @throws: 异常 
	 * @author: chendonghao 	
	 * @date: 2015年6月29日 下午5:32:46
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
	 * 我的信息-车辆信息
	 * @Title: getUserCarResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: 异常 
	 * @author: chendonghao 
	 * @date: 2015年7月3日 上午11:10:00
	 */
	@RequestMapping(value="getUserCarResourceAjax",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserCarResource(HttpSession session,PageUtil pageUtil){
		
		JSONArray jsonArray=carService.getUserCarResource(session,pageUtil);
		
		return jsonArray.toString();
	}
	
	/**
	 * 我的信息-车辆信息-总记录条数
	 * @Title: getUserCarResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: 异常 
	 * @author: chendonghao 
	 * @date: 2015年7月3日 上午11:11:56
	 */
	@RequestMapping(value="getUserCarResourceTotalRowsAjax")
	@ResponseBody
	public Integer getUserCarResourceTotalRows(HttpSession session){
		
		return carService.getUserCarResourceTotalRows(session);
	}
	/**
	 * 我的信息-司机信息
	 * @Title: getUserCarResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: 异常 
	 * @author: chendonghao 
	 * @date: 2015年7月3日 上午11:10:00
	 */
	@RequestMapping(value="getUserDriverResourceAjax",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserDriverResource(HttpSession session,PageUtil pageUtil){
		
		JSONArray jsonArray=driverService.getUserDriverResource(session,pageUtil);
		
		return jsonArray.toString();
	}
	
	/**
	 * 我的信息-车辆信息-总记录条数
	 * @Title: getUserCarResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: 异常 
	 * @author: chendonghao 
	 * @date: 2015年7月3日 上午11:11:56
	 */
	@RequestMapping(value="getUserDriverResourceTotalRowsAjax")
	@ResponseBody
	public Integer getUserDriverResourceTotalRows(HttpSession session){
		
		return driverService.getUserDriverResourceTotalRows(session);
	}
	
	/**
	 * 我的资源-车辆信息-车队i信息
	 * @Title: getUserCarTeamResource 
	 * @Description: TODO 
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: 异常 
	 * @author: chendonghao 
	 * @date: 2015年7月15日 上午11:17:55
	 */
	@RequestMapping(value="getUserCarTeamResourceAjax",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserCarTeamResource(HttpSession session,PageUtil pageUtil){
		return carTeamService.getUserCarTeamResource(session,pageUtil);
	}
	/**
	 * 我的资源-车辆信息-车队i信息-总记录数
	 * @Title: getUserCarTeamResourceTotalRows 
	 * @Description: TODO 
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: 异常 
	 * @author: chendonghao 
	 * @date: 2015年7月15日 上午11:19:43
	 */
	@RequestMapping("getUserCarTeamResourceTotalRowsAjax")
	@ResponseBody
	public Integer getUserCarTeamResourceTotalRows(HttpSession session){
		return carTeamService.getUserCarTeamResourceTotalRows(session);
	}

}
