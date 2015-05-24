package cn.edu.bjtu.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.DriverDao;
import cn.edu.bjtu.vo.Driverinfo;
@Repository
public class DriverDaoImpl extends BaseDaoImpl<Driverinfo> implements DriverDao{
	@Override
	/**
	 * ��������˾����Ϣ
	 */
	public List getAllDriver() {
		// TODO Auto-generated method stub
		return this.find("from Driverinfo");
	}

	@Override
	public Driverinfo getDriverInfo(String driverId) {
		// TODO Auto-generated method stub
		return this.get(Driverinfo.class,driverId);
	}
	@Override
	/**
	 * ��ȡĳ��˾������˾������ 
	 */
	public List getAllDriverName(String carrierId) {
		// TODO Auto-generated method stub
		return this.find("select driverName from Driverinfo where carrierId='"+carrierId+"'");
	}

	@Override
	/**
	 * ��ȡĳ��˾������˾����Ϣ 
	 */
	public List getAllDriver(String carrierId) {
		// TODO Auto-generated method stub
		return this.find("from Driverinfo where carrierId='"+carrierId+"'");
	}
	

	@Override
	public String getDriverIdByName(String driverName) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	/**
	 * ���ع�˾˾��
	 */
	public List getCompanyDriver(String carrier) {
		// TODO Auto-generated method stub
		return this.find("from Driverinfo where carrierId='"+carrier+"'");
	}

}
