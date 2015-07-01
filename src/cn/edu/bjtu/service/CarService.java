package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.bean.search.CarSearchBean;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Carinfo;
import cn.edu.bjtu.vo.Carteam;

import com.alibaba.fastjson.JSONArray;

public interface CarService {
	@Deprecated
	public List getSelectedCar(String carLocation, String carBase, String carLength, String carWeight, int Display,int PageNow);
	@Deprecated
	public int getTotalRows(String carLocation, String carBase, String carLength, String carWeight);
	
	public Carinfo getCarInfo(String carid);


	public List getCompanyCar(String carrierId);


	public boolean insertCar(String carNum, String carTeam,
			String locationType, String terminalId, String carBase, String carBrand,
			String carType, String carUse, double carLength, double carWidth,
			double carHeight, double carWeight, String driverId,
			String purchaseTime, String storage, String startPlace,
			String endPlace, String stopPlace, String carrierId);

	

	public boolean updateCar(String id, String carNum, String carTeam, String locType, 
			String terminalId, String carType, String carBase, String carBrand, String carUse,
			double carLength, double carWidth, double carHeight, double carWeight, String carPurTime,
			String storage,String driverId, String startPlace,String endPlace,
			String stopPlace,String carrierId);
	
	
	public boolean deleteCar(String id);
	
	/**
	 * ��ȡ��Դ��ɸѡcar
	 * @param carbean
	 * @param pageUtil
	 * @param session
	 * @return
	 */
	public JSONArray getSelectedCarNew(CarSearchBean carbean,PageUtil pageUtil,HttpSession session);
	
	/**
	 * ��ȡ��Դ��-����ɸѡ��¼������
	 * @param carBean
	 * @return
	 */
	public Integer getSelectedCarTotalRows(CarSearchBean carBean);
	
	/**
	 * ��ȡ��˾���������б�
	 * @Title: getCompanyCarteamList 
	 * @Description: TODO 
	 * @param: @param session
	 * @param: @return 
	 * @return: JSONArray 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��6��29�� ����5:35:42
	 */
	public List<Carteam> getCompanyCarteamList(HttpSession session);
	
	
	
}
