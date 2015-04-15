package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.CarDao;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.vo.Carinfo;
import cn.edu.bjtu.vo.Driverinfo;

@Repository
public class CarDaoImpl implements CarDao{
	
	@Resource
	private HibernateTemplate ht;
	@Resource 
	private HQLTool hqltool;
	
	@Override
	/**
	 * �������г�����Ϣ
	 * ��ͼ��ѯ
	 * 
	 */
	public List getAllCar(int Display,int PageNow) {
		// TODO Auto-generated method stub
		int page = PageNow;
		int pageSize = Display;
		String hql=" from CarCarrierView";
		
		return hqltool.getQueryList(hql, page, pageSize);//dao�����ȡ���ݷ���
		
	}
	
	@Override
	/**
	 * ���ؾ��峵����Ϣ
	 */
	public Carinfo getCarInfo(String carid) {
		// TODO Auto-generated method stub
		return ht.get(Carinfo.class, carid);
		
	}

	@Override
	/**
	 * ��������˾����Ϣ
	 */
	public List getAllDriver() {
		// TODO Auto-generated method stub
		return ht.find("from Driverinfo");
	}

	@Override
	public Driverinfo getDriverInfo(String driverId) {
		// TODO Auto-generated method stub
		return ht.get(Driverinfo.class,driverId);
	}

	@Override
	/**
	 * ���ع�˾����
	 */
	public List getCompanyCar(String carrierId) {
		// TODO Auto-generated method stub
		return ht.find("from Carinfo where carrierId='"+carrierId+"'");
	}

	@Override
	/**
	 * ��ȡĳ��˾������˾������ 
	 */
	public List getAllDriverName(String carrierId) {
		// TODO Auto-generated method stub
		return ht.find("select driverName from Driverinfo where carrierId='"+carrierId+"'");
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
		return ht.find("from Driverinfo where carrierId='"+carrier+"'");
	}
	
	
	@Override
	public List getSelectedCar(String hql, int display, int pageNow) {
		// TODO Auto-generated method stub
		//System.out.println("hql+"+hql);
		int page = pageNow;
		int pageSize = display;

		return hqltool.getQueryList(hql, page, pageSize);//Dao���ҳ������ȡ���˷���
	}
	
}
