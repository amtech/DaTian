package cn.edu.bjtu.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.CarDao;
import cn.edu.bjtu.service.CarService;
import cn.edu.bjtu.service.GoodsInfoService;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.util.ParseDate;
import cn.edu.bjtu.vo.Carinfo;
import cn.edu.bjtu.vo.Carteam;
import cn.edu.bjtu.vo.Driverinfo;
@Transactional
@Service("carServiceImpl")
public class CarServiceImpl implements CarService {

	@Resource
	CarDao carDao;
	@Resource
	Carinfo carinfo;
	@Resource
	BaseDao baseDao;
	@Resource
	LinetransportService linetransportService;
	@Resource(name = "carServiceImpl")
	CarService carService;
	@Resource
	Driverinfo driverinfo;
	@Resource
	Carteam carteam;
	// List driverNameList=new ArrayList();
	@Resource
	HQLTool hqltool;
	
	
	@Override
	/**
	 * �������г���
	 */
	public List getAllCar(int Display, int PageNow) {
		// TODO Auto-generated method stub

		return carDao.getAllCar(Display, PageNow);
	}

	@Override
	/**
	 * ����ɸѡ����
	 */
	public List getSelectedCar(String carLocation, String carBase,
			String carLength, String carWeight, int Display, int PageNow) {

		String[] paramList = { "carLocation", "carBase", "carLength",
				"carWeight" };// ûstartplace1
		String[] valueList = { carLocation, carBase, carLength, carWeight };
		String hql = "from CarCarrierView ";// ��仯
		String sql = HQLTool.spellHql2(hql, paramList, valueList);
		// System.out.println("hql+" + sql);
		return carDao.getSelectedCar(sql, Display, PageNow);
	}

	@Override
	/**
	 * ��ȡ�ܼ�¼���� 
	 */
	public int getTotalRows(String carLocation, String carBase,
			String carLength, String carWeight) {
		// TODO Auto-generated method stub
		String[] paramList = { "carLocation", "carBase", "carLength",
				"carWeight" };// ûstartplace1
		String[] valueList = { carLocation, carBase, carLength, carWeight };
		String hql = "from CarCarrierView ";// ��仯
		String sql = HQLTool.spellHql2(hql, paramList, valueList);
		// System.out.println("hql+"+sql);
		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}

	@Override
	/**
	 * �����ض�������Ϣ
	 */
	public Carinfo getCarInfo(String carid) {
		// TODO Auto-generated method stub

		return carDao.getCarInfo(carid);
	}

	@Override
	/**
	 * ��������˾����Ϣ
	 */
	public List getAllDriver() {
		// TODO Auto-generated method stub
		return carDao.getAllDriver();
	}

	@Override
	/**
	 * ͨ��driverid�ҵ�˾����Ϣ
	 */
	public Driverinfo getDriverInfo(String driverId) {
		// TODO Auto-generated method stub
		return carDao.getDriverInfo(driverId);
	}

	@Override
	public List getCompanyCar(String carrierId) {
		// TODO Auto-generated method stub
		return carDao.getCompanyCar(carrierId);
	}

	@Override
	/**
	 * ͨ��carid�ҵ�driverinfo
	 */
	public Driverinfo getDriverByCarId(String carId) {
		// TODO Auto-generated method stub
		String driverId = ((Carinfo) carDao.getCarInfo(carId)).getDriverId();

		return carDao.getDriverInfo(driverId);
	}

	@Override
	/**
	 * ��ȡ���е�˾�����������³���ҳ��ʹ��
	 */
	public List getAllDriverName(String carrierId) {
		// TODO Auto-generated method stub
		return carDao.getAllDriverName(carrierId);
	}

	@Override
	public List getAllDriver(String carrierId) {
		// TODO Auto-generated method stub
		return carDao.getAllDriver(carrierId);
	}

	@Override
	/**
	 * ���ӳ���
	 */
	public boolean insertCar(String carNum, String carTeam,
			String locationType, String carBase, String carBrand,
			String carType, String carUse, double carLength, double carWidth,
			double carHeight, double carWeight, String driverId,
			String purchaseTime, String storage, String startPlace,
			String endPlace, String carrierId) {
		// TODO Auto-generated method stub
		carinfo.setCarBase(carBase);
		carinfo.setCarBrand(carBrand);
		carinfo.setCarHeight(carHeight);
		carinfo.setCarLength(carLength);
		// carinfo.setCarLocation(carLocation);
		carinfo.setCarNum(carNum);
		carinfo.setCarrierId(carrierId);
		// carinfo.setCarState(carState);
		carinfo.setCarTeam(carTeam);
		carinfo.setCarType(carType);
		carinfo.setCarUse(carUse);
		carinfo.setCarWeight(carWeight);
		carinfo.setCarWidth(carWidth);

		// String driverId=carService.getDriverIdByName(driverName);//δʵ��
		carinfo.setDriverId(driverId);

		String linetransportId = linetransportService.getLinetransportIdByCity(
				startPlace, endPlace);// δʵ��n
		carinfo.setLinetransportId(linetransportId);

		carinfo.setId(IdCreator.createCarId());
		// carinfo.setLinetransportId(linetransportId);
		carinfo.setLocationType(locationType);
		carinfo.setPurchaseTime(stringToDate(purchaseTime));
		carinfo.setRelDate(new Date());
		carinfo.setStorage(storage);
		// carinfo.setTerminalId(terminalId);

		baseDao.save(carinfo);
		return true;
	}

	@Override
	// δʵ��
	public String getDriverIdByName(String driverName) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	/**
	 * ����˾��
	 */
	public boolean insertDriver(String name, String sex, String licenceRate,
			String phone, String IDCard, String licenceNum, String licenceTime,
			String carrierId, String path, String fileName) {
		// TODO Auto-generated method stub
		// driverinfo.setAge(age);
		driverinfo.setCarrierId(carrierId);
		driverinfo.setDriverName(name);
		driverinfo.setId(IdCreator.createDriverId());
		driverinfo.setIDCard(IDCard);
		driverinfo.setLicenceNum(licenceNum);
		driverinfo.setLicenceRate(licenceRate);
		driverinfo.setLicenceTime(stringToDate(licenceTime));
		driverinfo.setPhone(phone);
		driverinfo.setRelDate(new Date());
		driverinfo.setSex(sex);
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			driverinfo.setIdscans(fileLocation);
		}
		baseDao.save(driverinfo);// ����ʵ��
		return true;
	}

	@Override
	/**
	 * ���ع�˾˾��
	 */
	public List getCompanyDriver(String carrierId) {
		// TODO Auto-generated method stub
		return carDao.getCompanyDriver(carrierId);
	}

	@Override
	/**
	 * ���³�����Ϣ
	 */
	public boolean updateCar(String id, String carNum, String carTeam,
			String locType, String terminalId, String carType, String carBase,
			String carBrand, String carUse, double carLength, double carWidth,
			double carHeight, double carWeight, String carPurTime,
			String storage, String driverId, String startPlace,// ȱ�ٲ���
			String endPlace,// ȱ�ٲ���
			String stopPlace,// ȱ�ٲ���
			String carrierId) {
		// TODO Auto-generated method stub
		System.out.println("in updateCar");// null
		carinfo = getCarInfo(id);// ����id���ҵ�������Ϣ
		carinfo.setTerminalId(terminalId);
		carinfo.setCarTeam(carTeam);
		carinfo.setLocationType(locType);
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
		
		System.out.println("set over");// null
		baseDao.update(carinfo);
		return true;

	}

	public static Date stringToDate(String str) {  
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
	 * ����˾��
	 */
	public boolean updateDriver(String id, String name, String sex,
			String IDCard, String licenceNum, String licenceRate,
			String licenceTime, String phone, String carrierId, String path,
			String fileName) {
		// TODO Auto-generated method stub
		// driverinfo.setAge(age);
		driverinfo = getDriverInfo(id);// ����id���ҵ�������Ϣ
		driverinfo.setCarrierId(carrierId);
		driverinfo.setDriverName(name);
		driverinfo.setSex(sex);
		driverinfo.setIDCard(IDCard);
		driverinfo.setLicenceNum(licenceNum);
		driverinfo.setLicenceRate(licenceRate);
		driverinfo.setLicenceTime(stringToDate(licenceTime));
		driverinfo.setPhone(phone);
		driverinfo.setRelDate(new Date());
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			driverinfo.setIdscans(fileLocation);
		}
		baseDao.update(driverinfo);// ����ʵ��
		return true;
	}

	@Override
	/**
	 * ɾ������
	 * @param id
	 * @return
	 */
	public boolean deleteCar(String id) {
		carinfo = getCarInfo(id);// ����id���ҵ�������Ϣ

		baseDao.delete(carinfo);
		return true;
	}

	@Override
	/**
	 * ɾ��˾��
	 * @param id
	 * @return
	 */
	public boolean deleteDriver(String id) {
		driverinfo = getDriverInfo(id);// ����id���ҵ�������Ϣ
		baseDao.delete(driverinfo);
		return true;
	}

	@Override
	public List getCarteam(String carrierId) {
		// TODO Auto-generated method stub
		return carDao.getCarteam(carrierId);
	}

	@Override
	public Carteam getCarteamInfo(String id) {
		// TODO Auto-generated method stub
		return carDao.getCarteamInfo(id);
	}

	@Override
	public boolean insertCarteam(String teamName, String carCount,
			String chief, String phone, String explaination, String carrierId) {
		System.out.println("insertcarteam " + IdCreator.createCarteamId()
				+ teamName + carCount + carrierId + chief + phone
				+ explaination + new Date());// null
		carteam.setId(IdCreator.createCarteamId());
		carteam.setTeamName(teamName);
		carteam.setCarCount(carCount);
		carteam.setCarrierId(carrierId);
		carteam.setChief(chief);
		carteam.setPhone(phone);
		carteam.setExplaination(explaination);
		carteam.setRelDate(new Date());
		// return true;
		baseDao.save(carteam);// ����ʵ��
		return true;
	}

	@Override
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	public boolean deleteCarteam(String id) {
		carteam = getCarteamInfo(id);// ����id���ҵ�������Ϣ
		baseDao.delete(carteam);
		return true;
	}

	@Override
	public boolean updateCarteam(String id, String teamName, String carCount,
			String chief, String phone, String explaination) {
		carteam = getCarteamInfo(id);// ����id���ҵ�������Ϣ
		carteam.setTeamName(teamName);
		carteam.setCarCount(carCount);
		carteam.setChief(chief);
		carteam.setPhone(phone);
		carteam.setExplaination(explaination);
		// return true;
		baseDao.update(carteam);// ����ʵ��
		return true;
	}
}
