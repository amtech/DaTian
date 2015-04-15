package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.CarDao;
import cn.edu.bjtu.service.CarService;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.util.ParseDate;
import cn.edu.bjtu.vo.Carinfo;
import cn.edu.bjtu.vo.Driverinfo;

@Service("carServiceImpl")
public class CarServiceImpl implements CarService{
	
	@Resource
	CarDao carDao;
	@Resource
	Carinfo carinfo;
	@Resource
	BaseDao baseDao;
	@Resource
	LinetransportService linetransportService;
	@Resource(name="carServiceImpl")
	CarService carService;
	@Resource
	Driverinfo driverinfo;
	//List driverNameList=new ArrayList();
	@Resource
	HQLTool hqltool;
	
	@Override
	/**
	 * �������г���
	 */
	public List getAllCar(int Display,int PageNow) {
		// TODO Auto-generated method stub
		
		
		return carDao.getAllCar(Display, PageNow);
	}
	
	@Override
	/**
	 * ����ɸѡ����
	 */
	public List getSelectedCar(String carLocation, String carBase, String carLength, String carWeight, int Display,int PageNow) {
		
		String [] paramList={"carLocation","carBase","carLength","carWeight"};//ûstartplace1 
		String [] valueList={carLocation,carBase,carLength,carWeight};
		String hql="from CarCarrierView ";//��仯
		String sql=HQLTool.spellHql2(hql,paramList, valueList);
		//System.out.println("hql+" + sql);
		return carDao.getSelectedCar(sql,Display,PageNow);
	}
	
	@Override
	/**
	 * ��ȡ�ܼ�¼���� 
	 */
	public int getTotalRows(String carLocation, String carBase, String carLength, String carWeight) {
		// TODO Auto-generated method stub
		String [] paramList={"carLocation","carBase","carLength","carWeight"};//ûstartplace1 
		String [] valueList={carLocation,carBase,carLength,carWeight};
		String hql="from CarCarrierView ";//��仯
		String sql=HQLTool.spellHql2(hql,paramList, valueList);
		//System.out.println("hql+"+sql);
		return hqltool.getTotalRows(sql);//�����HQLToolʵ��ǧ�����Լ�new��������@Resource
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
		String driverId=((Carinfo)carDao.getCarInfo(carId)).getDriverId();
		
		return  carDao.getDriverInfo(driverId);
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
	/**
	 * ���ӳ���
	 */
	public boolean insertCar(String carNum, String carTeam,
			String locationType, String carBase, String carBrand,String carType,
			String carUse, double carLength, double carWidth, double carHeight,
			double carWeight, String driverName,String purchaseTime, String storage,
			String startPlace, String endPlace, String carrierId) {
		// TODO Auto-generated method stub
		carinfo.setCarBase(carBase);
		carinfo.setCarBrand(carBrand);
		carinfo.setCarHeight(carHeight);
		carinfo.setCarLength(carLength);
		//carinfo.setCarLocation(carLocation);
		carinfo.setCarNum(carNum);
		carinfo.setCarrierId(carrierId);
		//carinfo.setCarState(carState);
		carinfo.setCarTeam(carTeam);
		carinfo.setCarType(carType);
		carinfo.setCarUse(carUse);
		carinfo.setCarWeight(carWeight);
		carinfo.setCarWidth(carWidth);
		
		String driverId=carService.getDriverIdByName(driverName);//δʵ��
		carinfo.setDriverId(driverId);
		
		String linetransportId=linetransportService.getLinetransportIdByCity(startPlace,endPlace);//δʵ��n
		carinfo.setLinetransportId(linetransportId);
		
		carinfo.setId(IdCreator.createCarId());
		//carinfo.setLinetransportId(linetransportId);
		carinfo.setLocationType(locationType);
		carinfo.setPurchaseTime(ParseDate.parseDate(purchaseTime));
		carinfo.setRelDate(new Date());
		carinfo.setStorage(storage);
		//carinfo.setTerminalId(terminalId);
		
		return baseDao.save(carinfo);
	}

	@Override//δʵ��
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
			String carrierId) {
		// TODO Auto-generated method stub
		//driverinfo.setAge(age);
		driverinfo.setCarrierId(carrierId);
		driverinfo.setDriverName(name);
		driverinfo.setId(IdCreator.createDriverId());
		driverinfo.setIDCard(IDCard);
		driverinfo.setLicenceNum(licenceNum);
		driverinfo.setLicenceRate(licenceRate);
		driverinfo.setLicenceTime(ParseDate.parseDate(licenceTime));//���ﻹ������
		driverinfo.setPhone(phone);
		driverinfo.setRelDate(new Date());
		driverinfo.setSex(sex);
		return baseDao.save(driverinfo);//����ʵ��
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
	public boolean updateCar(String id, String carNum, String carTeam, String locType, 
			String GPSText, String carType, String carBase, String carBrand, String carUse,
			double carLength, double carWidth, double carHeight, double carWeight, String carPurTime,
			String storage,
			String driverName,//ȱ�ٲ���
			String startPlace,//ȱ�ٲ���
			String endPlace,//ȱ�ٲ���
			String stopPlace,//ȱ�ٲ���
			String carrierId){
		// TODO Auto-generated method stub
		System.out.println("in updateCar");//null
		carinfo=getCarInfo(id);//����id���ҵ�������Ϣ
		
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
		//carinfo.setPurchaseTime(carPurTime);
		carinfo.setStorage(storage);
		
		System.out.println("set over");//null
		return baseDao.update(carinfo);
	
	}
	
	@Override
	/**
	 * ����˾��
	 */
	public boolean updateDriver(String id, String name, String sex, String IDCard,
			String licenceNum, String licenceRate, String licenceTime, String phone,  
			String carrierId) {
		// TODO Auto-generated method stub
		//driverinfo.setAge(age);
		driverinfo=getDriverInfo(id);//����id���ҵ�������Ϣ
		driverinfo.setCarrierId(carrierId);
		driverinfo.setDriverName(name);
		driverinfo.setSex(sex);
		driverinfo.setIDCard(IDCard);
		driverinfo.setLicenceNum(licenceNum);
		driverinfo.setLicenceRate(licenceRate);
		driverinfo.setPhone(phone);
		driverinfo.setRelDate(new Date());
		return baseDao.update(driverinfo);//����ʵ��
	}
	@Override
	/**
	 * ɾ������
	 * @param id
	 * @return
	 */
	public boolean deleteCar(String id){
		carinfo=getCarInfo(id);//����id���ҵ�������Ϣ

		return baseDao.delete(carinfo);
	}
	
	@Override
	/**
	 * ɾ��˾��
	 * @param id
	 * @return
	 */
	public boolean deleteDriver(String id){
		driverinfo=getDriverInfo(id);//����id���ҵ�������Ϣ
		return baseDao.delete(driverinfo);
	}
	
}
