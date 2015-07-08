package cn.edu.bjtu.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.bean.search.CarSearchBean;
import cn.edu.bjtu.dao.CarDao;
import cn.edu.bjtu.dao.CarTeamDao;
import cn.edu.bjtu.service.CarService;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Carinfo;
import cn.edu.bjtu.vo.Carteam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Transactional
@Service("carServiceImpl")
public class CarServiceImpl implements CarService {

	@Autowired
	CarDao carDao;
	@Resource
	Carinfo carinfo;

	@Autowired
	CarTeamDao carTeamDao;
	@Resource
	LinetransportService linetransportService;
	@Resource
	Carteam carteam;
	@Resource
	HQLTool hqltool;
	
	
	/**
	 * ������Դ��ɸѡcar
	 */
	@Override
	public JSONArray getSelectedCarNew(CarSearchBean carbean,
			PageUtil pageUtil, HttpSession session) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		Map<String,Object> params=new HashMap<String,Object>();
			String sql = "select t1.id,"
				+ "t1.carrierId,"
				+ "t1.carNum,"
				+ "t1.companyName,"
				+ "t1.carBase,"
				+ "t1.carState,"
				+ "t1.carLength,"
				+ "t1.carWeight,"
				+ "t1.carLocation,"
				+ "t1.relDate,"
				+ "t1.linetransportId,"
				+ "t3.status "
				+ " from car_carrier_view t1 "
				+ "left join ("
				+ "select * from focus t2 ";
				
		if(userId!=null){//�����ǰ���û���¼�������м����û���Ϣ
			sql+=" where t2.focusType='car' and t2.clientId=:clientId ";
			params.put("clientId", userId);
		}
		sql+=") t3 on t1.id=t3.focusId ";
		String wheresql=whereSql(carbean,params);
		sql+=wheresql;
		
		JSONArray jsonArray = new JSONArray();
		int page=pageUtil.getCurrentPage()==0?1:pageUtil.getCurrentPage();
		int display=pageUtil.getDisplay()==0?10:pageUtil.getDisplay();
		List<Object[]> objectList=carDao.findBySql(sql, params,page,display);
		
		List<CarSearchBean> carList=new ArrayList<CarSearchBean>();
		for(Iterator<Object[]> it=objectList.iterator();it.hasNext();){
			CarSearchBean carBean=new CarSearchBean();
			Object[] obj=it.next();
			carBean.setId((String)obj[0]);
			carBean.setCarrierId((String)obj[1]);
			carBean.setCarNum((String)obj[2]);
			carBean.setCompanyName((String)obj[3]);;
			carBean.setCarBase((String)obj[4]);
			carBean.setCarState((String)obj[5]);
			carBean.setCarLength((Double)obj[6]+"");
			carBean.setCarWeight((Double)obj[7]+"");;
			carBean.setCarLocation((String)obj[8]);
			carBean.setRelDate((Date)obj[9]);
			carBean.setLinetransportId((String)obj[10]);;
			carBean.setStatus((String)obj[11]);
			carList.add(carBean);
		}
		
		for(int i=0;i<carList.size();i++){
			JSONObject jsonObject=(JSONObject)JSONObject.toJSON(carList.get(i));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	/**
	 * where sql
	 * @param carBean
	 * @param params
	 * @return
	 */
	private String whereSql(CarSearchBean carBean,Map<String,Object> params){
		String wheresql=" where 1=1 ";
		if(carBean.getStartPlace()!=null && !carBean.getStartPlace().trim().equals("���Ļ�ƴ��")&&!carBean.getStartPlace().trim().equals("")&&!carBean.getStartPlace().trim().equals("ȫ��")){
			wheresql+=" and t1.startPlace=:startPlace ";
			params.put("startPlace", carBean.getStartPlace());
		}
		if(carBean.getEndPlace()!=null && !carBean.getEndPlace().trim().equals("���Ļ�ƴ��")&&!carBean.getStartPlace().trim().equals("")&&!carBean.getStartPlace().trim().equals("ȫ��")){
			wheresql+=" and t1.endPlace=:endPlace ";
			params.put("endPlace", carBean.getEndPlace());
		}
		if(carBean.getCarBase()!=null && !carBean.getCarBase().equals("") && !carBean.getCarBase().equals("All")){
			wheresql+=" and t1.carBase=:carBase ";
			params.put("carBase", carBean.getCarBase());
		}
		if(carBean.getCarLength()!=null && !carBean.getCarLength().trim().equals("All") && !carBean.getCarLength().trim().equals("")){
			String carLength=carBean.getCarLength();
			if (carLength.equals("10��")) {
				wheresql+=" and t1.carLength=10";
			}
			if (carLength.equals("12��")) {
				wheresql+=" and t1.carLength=12";
			}
			if (carLength.equals("14��")) {
				wheresql+=" and t1.carLength=14";
			}
		}
		if(carBean.getCarWeight()!=null && !carBean.getCarWeight().trim().equals("All")&& !carBean.getCarWeight().trim().equals("")){
			
			String carWeight=carBean.getCarWeight();
			if (carWeight.equals("8��")) {
				wheresql+=" and t1.carWeight=8";
			}
			if (carWeight.equals("12��")) {
				wheresql+=" and t1.carWeight=10";
			}
			if (carWeight.equals("16��")) {
				wheresql+=" and t1.carWeight=16";
			}
			if (carWeight.equals("20��")) {
				wheresql+=" and t1.carWeight=20";
			}
		}
		
		return wheresql;
	}


	@Override
	/**
	 * ����ɸѡ����
	 */
	@Deprecated
	public List getSelectedCar(String carLocation, String carBase,
			String carLength, String carWeight, int Display, int PageNow) {

		String[] paramList = { "carLocation", "carBase", "carLength",
				"carWeight" };// ûstartplace1
		String[] valueList = { carLocation, carBase, carLength, carWeight };
		String hql = "from CarCarrierView ";// ��仯
		String sql = HQLTool.spellHql2(hql, paramList, valueList);
		return carDao.getSelectedCar(sql, Display, PageNow);
	}

	@Override
	/**
	 * ��ȡ�ܼ�¼���� 
	 */
	@Deprecated
	public int getTotalRows(String carLocation, String carBase,
			String carLength, String carWeight) {
		
		String[] paramList = { "carLocation", "carBase", "carLength",
				"carWeight" };// ûstartplace1
		String[] valueList = { carLocation, carBase, carLength, carWeight };
		String hql = "from CarCarrierView ";// ��仯
		String sql = HQLTool.spellHql2(hql, paramList, valueList);
		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}

	@Override
	/**
	 * �����ض�������Ϣ
	 */
	public Carinfo getCarInfo(String carid) {
		

		return carDao.getCarInfo(carid);
	}

	
	@Deprecated
	@Override
	public List getCompanyCar(String carrierId) {
		
		return carDao.getCompanyCar(carrierId);
	}

	

	@Override
	/**
	 * ���ӳ���
	 */
	public boolean insertNewCar(Carinfo car,HttpServletRequest request){
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);//�����ļ�

		car.setId(IdCreator.createlineTransportId());
		car.setRelDate(new Date());
		car.setCarrierId(carrierId);
		car.setCarState("ͣЪ");
		carDao.save(car);
		return true;
	}
	@Deprecated
	public boolean insertCar(String carNum, String carTeam,
			String locationType, String terminalId, String carBase, String carBrand,
			String carType, String carUse, double carLength, double carWidth,
			double carHeight, double carWeight, String driverId,
			String purchaseTime, String storage, String startPlace,
			String endPlace, String stopPlace, String carrierId) {
		
		
		carinfo.setId(IdCreator.createCarId());
		carinfo.setCarNum(carNum);
		carinfo.setCarTeam(carTeam);
		carinfo.setLocationType(locationType);
		carinfo.setTerminalId(terminalId);
		carinfo.setCarBase(carBase);
		carinfo.setCarBrand(carBrand);
		carinfo.setCarType(carType);
		carinfo.setCarUse(carUse);
		carinfo.setCarLength(carLength);
		carinfo.setCarWidth(carWidth);
		carinfo.setCarHeight(carHeight);
		carinfo.setCarWeight(carWeight);
		carinfo.setDriverId(driverId);
		carinfo.setPurchaseTime(stringToDate(purchaseTime));
		carinfo.setStorage(storage);
		carinfo.setStartPlace(startPlace);
		carinfo.setEndPlace(endPlace);
		carinfo.setStopPlace(stopPlace);
		carinfo.setCarrierId(carrierId);
		carinfo.setRelDate(new Date());
		
		carDao.save(carinfo);
		return true;
	}

	

	@Override
	/**
	 * ���³�����Ϣ
	 */
	@Deprecated
	public boolean updateCar(String id, String carNum, String carTeam,
			String locType, String terminalId, String carType, String carBase,
			String carBrand, String carUse, double carLength, double carWidth,
			double carHeight, double carWeight, String carPurTime,
			String storage, String driverId, String startPlace,// ȱ�ٲ���
			String endPlace,// ȱ�ٲ���
			String stopPlace,// ȱ�ٲ���
			String carrierId) {
		
		carinfo = getCarInfo(id);// ����id���ҵ�������Ϣ
		carinfo.setCarTeam(carTeam);
		carinfo.setLocationType(locType);
		carinfo.setTerminalId(terminalId);
		carinfo.setCarType(carType);
		carinfo.setCarBase(carBase);
		carinfo.setCarBrand(carBrand);
		carinfo.setCarUse(carUse);
		carinfo.setCarLength(carLength);
		carinfo.setCarWidth(carWidth);
		carinfo.setCarHeight(carHeight);
		carinfo.setCarWeight(carWeight);
		carinfo.setPurchaseTime(stringToDate(carPurTime));
		carinfo.setStorage(storage);
		carinfo.setDriverId(driverId);
		carinfo.setStopPlace(stopPlace);
		carinfo.setStartPlace(startPlace);
		carinfo.setEndPlace(endPlace);
		carDao.update(carinfo);
		return true;

	}
	
	@Override
	public boolean updateNewCar(Carinfo car,HttpServletRequest request){
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);

		Carinfo carInstance = carDao.get(Carinfo.class,car.getId());
		carInstance.setCarTeam(car.getCarTeam());
		carInstance.setLocationType(car.getLocationType());
		carInstance.setTerminalId(car.getTerminalId());
		carInstance.setCarType(car.getCarType());
		carInstance.setCarBase(car.getCarBase());
		carInstance.setCarBrand(car.getCarBrand());
		carInstance.setCarUse(car.getCarUse());
		carInstance.setCarLength(car.getCarLength());
		carInstance.setCarWidth(car.getCarWidth());
		carInstance.setCarHeight(car.getCarHeight());
		carInstance.setCarWeight(car.getCarWeight());
		carInstance.setPurchaseTime(car.getPurchaseTime());
		carInstance.setStorage(car.getStorage());
		carInstance.setDriverId(car.getDriverId());
		carInstance.setStopPlace(car.getStopPlace());
		carInstance.setStartPlace(car.getStartPlace());
		carInstance.setEndPlace(car.getEndPlace());
		

		//����
		carDao.update(carInstance);
		return true;
	}
	
	
	/**
	 * �ַ���תΪ��������
	 * @param str
	 * @return
	 */
	private static Date stringToDate(String str) {  
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = null;  
        try {  
            // Fri Feb 24 00:00:00 CST 2012  
            date = format.parse(str);   
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        // 2012-02-24  
        date = java.sql.Date.valueOf(str);  
                                              
        return date;  
} 
	
	

	@Override
	/**
	 * ɾ������
	 * @param id
	 * @return
	 */
	public boolean deleteCar(String id) {
		carinfo = getCarInfo(id);// ����id���ҵ�������Ϣ

		carDao.delete(carinfo);
		return true;
	}

	/**
	 * ������Դ��-����ɸѡ��¼������
	 */
	@Override
	public Integer getSelectedCarTotalRows(CarSearchBean carBean) {
		
		Map<String,Object> params=new HashMap<String,Object>();
		String hql="select count(*) from CarCarrierView t1"+whereSql(carBean, params);
		Long count=carDao.count(hql, params);
		
		return count.intValue();
	}
	
	/**
	 * ��ȡ��˾������Ϣ�б�
	 */
	@Override
	public List<Carteam> getCompanyCarteamList(HttpSession session) {
		String carrierId=(String)session.getAttribute(Constant.USER_ID);
		String hql="from Carteam t where t.carrierId=:carrierId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("carrierId", carrierId);
		
		return carTeamDao.find(hql, params);
	}
	
	/**
	 * �ҵ���Ϣ-������Ϣ-�ܼ�¼����
	 */
	@Override
	public Integer getUserCarResourceTotalRows(HttpSession session) {
		String carrierId=(String)session.getAttribute(Constant.USER_ID);
		String hql="select count(*) from Carinfo t where t.carrierId=:carrierId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("carrierId", carrierId);
		Long count=carDao.count(hql, params);
		
		return count.intValue();
		
	}

	/**
	 * �ҵ���Ϣ-������Ϣ
	 */
	@Override
	public JSONArray getUserCarResource(HttpSession session,PageUtil pageUtil) {
		String carrierId=(String)session.getAttribute(Constant.USER_ID);
		String hql="from Carinfo t where t.carrierId=:carrierId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("carrierId", carrierId);
		int page=pageUtil.getCurrentPage()==0?1:pageUtil.getCurrentPage();
		int display=pageUtil.getDisplay()==0?10:pageUtil.getDisplay();
		List<Carinfo> carList=carDao.find(hql, params,page,display);
		JSONArray jsonArray=new JSONArray();
		for(Carinfo car:carList){
			JSONObject jsonObject=(JSONObject)JSONObject.toJSON(car);
			jsonArray.add(jsonObject);
		}
		
		return jsonArray;
	}
	
}
