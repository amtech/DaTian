package cn.edu.bjtu.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.CarDao;
import cn.edu.bjtu.dao.DriverDao;
import cn.edu.bjtu.service.DriverService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Carinfo;
import cn.edu.bjtu.vo.Driverinfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class DriverServiceImpl implements DriverService{

	@Autowired
	DriverDao driverDao;
	@Autowired
	Driverinfo driverinfo;
	@Autowired
	CarDao carDao;
	
	
	@Override
	/**
	 * ��������˾����Ϣ
	 */
	public List getAllDriver() {
		
		return driverDao.getAllDriver();
	}

	@Override
	/**
	 * ͨ��driverid�ҵ�˾����Ϣ
	 */
	public Driverinfo getDriverInfo(String driverId) {
		
		return driverDao.getDriverInfo(driverId);
	}
	
	@Override
	/**
	 * ͨ��carid�ҵ�driverinfo
	 */
	public Driverinfo getDriverByCarId(String carId) {
		
		String driverId = ((Carinfo) carDao.getCarInfo(carId)).getDriverId();

		return driverDao.getDriverInfo(driverId);
	}

	@Override
	/**
	 * ��ȡ���е�˾�����������³���ҳ��ʹ��
	 */
	public List getAllDriverName(String carrierId) {
		
		return driverDao.getAllDriverName(carrierId);
	}

	@Override
	public List getAllDriver(String carrierId) {
		
		return driverDao.getAllDriver(carrierId);
	}
	
	
	@Override
	// δʵ��
	public String getDriverIdByName(String driverName) {
		
		return "";
	}

	@Override
	/**
	 * ����˾��
	 */
	public boolean insertDriver(String name, String sex, String licenceRate,
			String phone, String IDCard, String licenceNum, String licenceTime,
			String carrierId, String path, String fileName) {
		
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
		driverDao.save(driverinfo);// ����ʵ��
		return true;
	}

	@Override
	/**
	 * ���ع�˾˾��
	 */
	public List getCompanyDriver(String carrierId) {
		
		return driverDao.getCompanyDriver(carrierId);
	}
	
	@Override
	/**
	 * ����˾��
	 */
	public boolean updateDriver(String id, String name, String sex,
			String IDCard, String licenceNum, String licenceRate,
			String licenceTime, String phone, String carrierId, String path,
			String fileName) {
		
		// driverinfo.setAge(age);
		driverinfo = getDriverInfo(id);// ����id���ҵ�������Ϣ
		driverinfo.setDriverName(name);
		driverinfo.setSex(sex);
		driverinfo.setIDCard(IDCard);
		driverinfo.setLicenceNum(licenceNum);
		driverinfo.setLicenceRate(licenceRate);
		driverinfo.setLicenceTime(stringToDate(licenceTime));
		driverinfo.setPhone(phone);
		//driverinfo.setRelDate(new Date());
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			driverinfo.setIdscans(fileLocation);
		}
		driverDao.update(driverinfo);// ����ʵ��
		return true;
	}
	
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
	 * ɾ��˾��
	 * @param id
	 * @return
	 */
	public boolean deleteDriver(String id) {
		driverinfo = getDriverInfo(id);// ����id���ҵ�������Ϣ
		driverDao.delete(driverinfo);
		return true;
	}
	/**
	 * �ҵ���Ϣ-˾����Ϣ
	 */
	@Override
	public JSONArray getUserDriverResource(HttpSession session,PageUtil pageUtil) {
		String carrierId=(String)session.getAttribute(Constant.USER_ID);
		String hql="from Driverinfo t where t.carrierId=:carrierId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("carrierId", carrierId);
		int page=pageUtil.getCurrentPage()==0?1:pageUtil.getCurrentPage();
		int display=pageUtil.getDisplay()==0?10:pageUtil.getDisplay();
		List<Driverinfo> driverList=driverDao.find(hql, params,page,display);
		
		JSONArray jsonArray=new JSONArray();
		for(Driverinfo driver:driverList){
			JSONObject jsonObject=(JSONObject)JSONObject.toJSON(driver);
			jsonArray.add(jsonObject);
		}
		
		return jsonArray;
		
	}

	/**
	 * �ҵ���Ϣ-˾����Ϣ-�ܼ�¼����
	 */
	@Override
	public Integer getUserDriverResourceTotalRows(HttpSession session) {
		String carrierId=(String)session.getAttribute(Constant.USER_ID);
		String hql="select count(*) from Driverinfo t where t.carrierId=:carrierId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("carrierId", carrierId);
		
		Long count=driverDao.count(hql, params);
		return count.intValue();
		
	}
	
}
