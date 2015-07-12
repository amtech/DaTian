package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Driverinfo;
import cn.edu.bjtu.vo.Linetransport;

import com.alibaba.fastjson.JSONArray;

public interface DriverService {
	@Deprecated
	public List getAllDriver();

	public Driverinfo getDriverInfo(String driverId);
	
	public Driverinfo getDriverByCarId(String carId);

	public List getAllDriverName(String carrierId);
	public List getAllDriver(String carrierId);
	
	public String getDriverIdByName(String driverName);
	@Deprecated
	public boolean insertDriver(String name, String sex, String licenceRate,
			String phone, String IDCard, String licenceNum, String licenceTime,
			String carrierId,String path,String fileName);
	public boolean insertNewDriver(Driverinfo driver,HttpServletRequest request,MultipartFile file);
	@Deprecated
	public List getCompanyDriver(String carrierId);
	@Deprecated
	public boolean updateDriver(String id, String name, String sex,String IDCard, String licenceNum,
			String licenceRate, String licenceTime,	String phone, String carrierId,String path,String fileName);
	public boolean updateNewDriver(Driverinfo driver,HttpServletRequest request,MultipartFile file);
	
	public boolean deleteDriver(String id);
	
	/**
	 * �ҵ���Ϣ-˾����Ϣ
	 * @Title: getUserDriverResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: JSONArray 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����4:13:11
	 */
	public JSONArray getUserDriverResource(HttpSession session,PageUtil pageUtil);
	
	/**
	 * �ҵ���Ϣ-˾����Ϣ-�ܼ�¼����
	 * @Title: getUserDriverResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����4:16:24
	 */
	public Integer getUserDriverResourceTotalRows(HttpSession session);

}
