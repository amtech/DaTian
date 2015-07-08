package cn.edu.bjtu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.edu.bjtu.bean.search.CityLineSearchBean;
import cn.edu.bjtu.bean.search.LinetransportSearchBean;
import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.CitylineDao;
import cn.edu.bjtu.service.CitylineService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.util.UploadFile;
import cn.edu.bjtu.vo.Cityline;
import cn.edu.bjtu.vo.Linetransport;
@Transactional
@Repository
/**
 * �������ͷ����ʵ��
 * @author RussWest0
 *
 */
public class CitylineServiceImpl implements CitylineService {

	@Resource
	CitylineDao citylineDao;
	@Resource
	Cityline cityline;
	/*@Resource
	BaseDao baseDao;*/
	@Resource
	HQLTool hqltool;

	
	
	/**
	 * ��Դ����ȡɸѡ��������
	 */
	@Override
	public JSONArray getSelectedLineNew(CityLineSearchBean cityLineBean,
			PageUtil pageUtil, HttpSession session) {
		
		String userId=(String)session.getAttribute(Constant.USER_ID);
		Map<String,Object> params=new HashMap<String,Object>();
			String sql = "select t1.id,"
				+ "t1.carrierId,"
				+ "t1.cityName,"
				+ "t1.name,"
				+ "t1.refPrice,"
				+ "t1.relDate,"
				+ "t1.VIPService,"
				+ "t1.creditRate,"
				+ "t1.companyName,"
				+ "t3.status "
				+ " from city_carrier_view t1 "
				+ "left join ("
				+ "select * from focus t2 ";
				
		if(userId!=null){//�����ǰ���û���¼�������м����û���Ϣ
			sql+=" where t2.focusType='cityline' and t2.clientId=:clientId ";
			params.put("clientId", userId);
		}
		sql+=") t3 on t1.id=t3.focusId ";
		String wheresql=whereSql(cityLineBean,params);
		sql+=wheresql;
		
		JSONArray jsonArray = new JSONArray();
		int page=pageUtil.getCurrentPage()==0?1:pageUtil.getCurrentPage();
		int display=pageUtil.getDisplay()==0?10:pageUtil.getDisplay();
		List<Object[]> objectList=citylineDao.findBySql(sql, params,page,display);
		
		List<CityLineSearchBean> citylineList=new ArrayList<CityLineSearchBean>();
		for(Iterator<Object[]> it=objectList.iterator();it.hasNext();){
			CityLineSearchBean citylinebean=new CityLineSearchBean();
			Object[] obj=it.next();
			citylinebean.setId((String)obj[0]);
			citylinebean.setCarrierId((String)obj[1]);
			citylinebean.setCityName((String)obj[2]);
			citylinebean.setName((String)obj[3]);;
			citylinebean.setRefPrice((Float)obj[4]+"");
			citylinebean.setRelDate((Date)obj[5]);
			citylinebean.setVIPService((String)obj[6]);
			citylinebean.setCreditRate((Integer)obj[7]);
			citylinebean.setCompanyName((String)obj[8]);
			citylinebean.setStatus((String)obj[9]);
			citylineList.add(citylinebean);
		}
		
		for(int i=0;i<citylineList.size();i++){
			JSONObject jsonObject=(JSONObject)JSONObject.toJSON(citylineList.get(i));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	/**
	 * where sql
	 * @param citylineBean
	 * @param params
	 * @return
	 */
	private String whereSql(CityLineSearchBean citylineBean,Map<String,Object> params){
		
		String wheresql=" where 1=1 ";
		if (citylineBean.getCityName() != null
				&& !citylineBean.getCityName().trim().equals("���Ļ�ƴ��")
				&& !citylineBean.getCityName().equals("")&& !citylineBean.getCityName().equals("ȫ��")) {
			wheresql+=" and t1.cityName like '%"+citylineBean.getCityName()+"%' ";
			//params.put("cityName", citylineBean.getCityName());
		}
		if(citylineBean.getRefPrice()!=null && !citylineBean.getRefPrice().trim().equals("") && !citylineBean.getRefPrice().trim().equals("All")){
			String refPrice=citylineBean.getRefPrice().trim();
			if(refPrice.equals("����2Ԫ/kg")){
				wheresql+=" and t1.refPrice >= 2 ";
			}
			if(refPrice.equals("1��2Ԫ/kg")){
				wheresql+=" and t1.refPrice >= 1 and t1.refPrice <= 2 ";
			}
			if(refPrice.equals("С��1Ԫ/kg")){
				wheresql+=" and t1.refPrice <=1 ";
			}
		}
		if(citylineBean.getVIPService()!=null && !citylineBean.getVIPService().trim().equals("") && !citylineBean.getVIPService().equals("All")){
			String VIPService=citylineBean.getVIPService().trim();
			if(VIPService.equals("����ֵ����")){
				wheresql+=" and t1.VIPService='��' ";
			}
			if(VIPService.equals("����ֵ����")){
				wheresql+=" and t1.VIPService='��' ";
			}
		}
		return wheresql;
	}


	@Override
	/**
	 * ����ɸѡ����������·
	 */
	@Deprecated
	public List getSelectedCityline(String cityName, String VIPService,
			String refPrice, int Display, int PageNow) {
		String sql = "";
		String hql = "from CityCarrierView ";// ��仯
		if (VIPService.equals("����ֵ����")) {
			String[] paramList = { "cityName", "VIPService" };
			String[] valueList = { cityName, "��" };
			sql = HQLTool.spellHql2(hql, paramList, valueList);
			if (refPrice.equals("����2Ԫ/kg")) {
				sql += "and refPrice > 2";
			} else if (refPrice.equals("1��2Ԫ/kg")) {
				sql += "and refPrice >= 1 and refPrice <=2";
			} else if (refPrice.equals("С��1Ԫ/kg")) {
				sql += "and refPrice < 1";
			}
		} else if (VIPService.equals("����ֵ����")) {
			String[] paramList = { "cityName", "VIPService" };
			String[] valueList = { cityName, "��" };
			sql = HQLTool.spellHql2(hql, paramList, valueList);
			if (refPrice.equals("����2Ԫ/kg")) {
				sql += "and refPrice > 2";
			} else if (refPrice.equals("1��2Ԫ/kg")) {
				sql += "and refPrice >= 1 and refPrice <=2";
			} else if (refPrice.equals("С��1Ԫ/kg")) {
				sql += "and refPrice < 1";
			}
		} else {// ��������ֵ����,ֵΪAll

			String[] paramList = { "cityName", "VIPService" };
			String[] valueList = { cityName, VIPService };
			sql = HQLTool.spellHql2(hql, paramList, valueList);

			if (refPrice.equals("����2Ԫ/kg")) {
				int i = 0;
				for (; i < paramList.length; i++) {
					if (!(valueList[i].equals("All"))) {
						break;
					}
				}
				if (i == paramList.length) {
					// Ҫ�����������All����Ҫ����where�ֶ�
					sql += "where refPrice > 2";
				} else
					sql += "and refPrice > 2";

			} else if (refPrice.equals("1��2Ԫ/kg")) {
				int i = 0;
				for (; i < paramList.length; i++) {
					if (!(valueList[i].equals("All"))) {
						break;
					}
				}
				if (i == paramList.length) {
					// Ҫ�����������All����Ҫ����where�ֶ�
					sql += "where refPrice >= 1 and refPrice <=2";
				} else
					sql += "and refPrice >= 1 and refPrice <=2";
			} else if (refPrice.equals("С��1Ԫ/kg")) {
				int i = 0;
				for (; i < paramList.length; i++) {
					if (!(valueList[i].equals("All"))) {
						break;
					}
				}
				if (i == paramList.length) {
					// Ҫ�����������All����Ҫ����where�ֶ�
					sql += "where refPrice < 1";
				} else
					sql += "and refPrice < 1";
			}

		}
		
		return citylineDao.getSelectedCityline(sql, Display, PageNow);
	}

	@Override
	/**
	 * ��ȡ�ܼ�¼���� 
	 */
	@Deprecated
	public int getTotalRows(String cityName, String VIPService, String refPrice) {
		
		String sql = "";
		String hql = "from CityCarrierView ";// ��仯
		if (VIPService.equals("����ֵ����")) {
			String[] paramList = { "cityName", "VIPService" };
			String[] valueList = { cityName, "��" };
			sql = HQLTool.spellHql2(hql, paramList, valueList);
			if (refPrice.equals("����2Ԫ/kg")) {
				sql += "and refPrice > 2";
			} else if (refPrice.equals("1��2Ԫ/kg")) {
				sql += "and refPrice >= 1 and refPrice <=2";
			} else if (refPrice.equals("С��1Ԫ/kg")) {
				sql += "and refPrice < 1";
			}
		} else if (VIPService.equals("����ֵ����")) {
			String[] paramList = { "cityName", "VIPService" };
			String[] valueList = { cityName, "��" };
			sql = HQLTool.spellHql2(hql, paramList, valueList);
			if (refPrice.equals("����2Ԫ/kg")) {
				sql += "and refPrice > 2";
			} else if (refPrice.equals("1��2Ԫ/kg")) {
				sql += "and refPrice >= 1 and refPrice <=2";
			} else if (refPrice.equals("С��1Ԫ/kg")) {
				sql += "and refPrice < 1";
			}
		} else {// ��������ֵ����,ֵΪAll

			String[] paramList = { "cityName", "VIPService" };
			String[] valueList = { cityName, VIPService };
			sql = HQLTool.spellHql2(hql, paramList, valueList);

			if (refPrice.equals("����2Ԫ/kg")) {
				int i = 0;
				for (; i < paramList.length; i++) {
					if (!(valueList[i].equals("All"))) {
						break;
					}
				}
				if (i == paramList.length) {
					// Ҫ�����������All����Ҫ����where�ֶ�
					sql += "where refPrice > 2";
				} else
					sql += "and refPrice > 2";

			} else if (refPrice.equals("1��2Ԫ/kg")) {
				int i = 0;
				for (; i < paramList.length; i++) {
					if (!(valueList[i].equals("All"))) {
						break;
					}
				}
				if (i == paramList.length) {
					// Ҫ�����������All����Ҫ����where�ֶ�
					sql += "where refPrice >= 1 and refPrice <=2";
				} else
					sql += "and refPrice >= 1 and refPrice <=2";
			} else if (refPrice.equals("С��1Ԫ/kg")) {
				int i = 0;
				for (; i < paramList.length; i++) {
					if (!(valueList[i].equals("All"))) {
						break;
					}
				}
				if (i == paramList.length) {
					// Ҫ�����������All����Ҫ����where�ֶ�
					sql += "where refPrice < 1";
				} else
					sql += "and refPrice < 1";
			}

		}
		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}

	@Override
	/**
	 * ��ȡ����������Ϣ
	 */
	public Cityline getCitylineInfo(String citylineid) {
		

		return citylineDao.getCitylineInfo(citylineid);
	}

	@Override
	/**
	 * ��ȡ��˾����������·
	 */
	@Deprecated
	public List getCompanyCityline(String carrierId) {
		
		return citylineDao.getCompanyCityline(carrierId);
	}

	@Override
	/**
	 * ������������
	 */
	public boolean insertNewCityline(Cityline cityline,
			HttpServletRequest request, MultipartFile file){
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		//�����ļ�
		String fileLocation=UploadFile.uploadFile(file, carrierId, "cityline");

		cityline.setCarrierId(carrierId);
		cityline.setRelDate(new Date());
		cityline.setId(IdCreator.createCityLineId());
		
		//�����ļ�λ�� 
		cityline.setDetailPrice(fileLocation);
		citylineDao.save(cityline);// ����ʵ��
		return true;
	}
	@Deprecated
	public boolean insertCityLine(String name, String cityName,
			String VIPService, float refPrice, String remarks, String carrierId, String VIPDetail,
			String path, String fileName) {
		
		cityline.setId(IdCreator.createCityLineId());
		cityline.setName(name);
		cityline.setCityName(cityName);
		cityline.setVIPService(VIPService);
		cityline.setRefPrice(refPrice);
		cityline.setRemarks(remarks);
		cityline.setCarrierId(carrierId);
		cityline.setRelDate(new Date());
		if(VIPDetail != ""){
			cityline.setVIPDetail(VIPDetail);
		}
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			cityline.setDetailPrice(fileLocation);
		}
		citylineDao.save(cityline);
		return true;
	}

	@Override
	/**
	 * ���³�������
	 */
	@Deprecated
	public boolean updateLine(String id, String citylineName, String cityName,
			String VIPService, String VIPDetail, float refPrice,
			String remarks, String carrierId,String path,String fileName) {
		

		cityline = getCitylineInfo(id);// ����id���ҵ�����������Ϣ

		cityline.setName(citylineName);
		cityline.setCityName(cityName);
		cityline.setVIPService(VIPService);
		cityline.setVIPDetail(VIPDetail);
		cityline.setRefPrice(refPrice);
		cityline.setRemarks(remarks);
		cityline.setRelDate(new Date());
		
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			cityline.setDetailPrice(fileLocation);
		}
		citylineDao.update(cityline);
		return true;
	}
	
	@Override
	public boolean updateNewCityline(Cityline cityline,
			HttpServletRequest request,MultipartFile file) {
		
		String carrierId = (String) request.getSession().getAttribute(Constant.USER_ID);
		//�����ļ�
		String fileLocation=UploadFile.uploadFile(file, carrierId, "cityline");

		Cityline citylineInstance = citylineDao.get(Cityline.class,cityline.getId());
		citylineInstance.setName(cityline.getName());
		citylineInstance.setCityName(cityline.getCityName());
		citylineInstance.setVIPService(cityline.getVIPService());
		citylineInstance.setVIPDetail(cityline.getVIPDetail());
		citylineInstance.setRefPrice(cityline.getRefPrice());
		citylineInstance.setRemarks(cityline.getRemarks());
		citylineInstance.setRelDate(new Date());
		
		//�����ļ�λ�� 
		citylineInstance.setDetailPrice(fileLocation);

		//����
		citylineDao.update(citylineInstance);
		return true;
		
		
	}
	
	
	@Override
	/**
	 * ɾ����������
	 */
	public boolean deleteCityline(String id) {
		cityline = getCitylineInfo(id);// ����id���ҵ�����������Ϣ
		citylineDao.delete(cityline);
		return true;
	}

	
	/***
	 * ���س�������ɸѡ��¼������
	 */
	@Override
	public Integer getSelectedCityLineTotalRows(CityLineSearchBean citylineBean) {
		
				Map<String,Object> params=new HashMap<String,Object>();
				String hql="select count(*) from CityCarrierView t1"+whereSql(citylineBean, params);
				Long count=citylineDao.count(hql, params);
				
				return count.intValue();
	}
	
	/**
	 * �ҵ���Ϣ-��������-�ܼ�¼��
	 */
	@Override
	public Integer getUserCitylineResourceTotalRows(HttpSession session) {
		String carrierId=(String) session.getAttribute(Constant.USER_ID);
		String hql="select count(*) from Cityline t where t.carrierId=:carrierId ";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("carrierId", carrierId);
		Long count=citylineDao.count(hql, params);
		return count.intValue();
	}

	/**
	 * �ҵ���Ϣ-��������
	 */
	@Override
	public JSONArray getUserCitylineResource(HttpSession session,PageUtil pageUtil) {
		String carrierId=(String)session.getAttribute(Constant.USER_ID);
		String hql="from Cityline t where t.carrierId=:carrierId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("carrierId", carrierId);
		int page=pageUtil.getCurrentPage()==0?1:pageUtil.getCurrentPage();
		int display=pageUtil.getDisplay()==0?10:pageUtil.getDisplay();
		List<Cityline> cityLineList=citylineDao.find(hql, params,page,display);
		JSONArray jsonArray=new JSONArray();
		for(Cityline cityLine:cityLineList){
			JSONObject jsonObject=(JSONObject)JSONObject.toJSON(cityLine);
			jsonArray.add(jsonObject);
		}
		
		return jsonArray;
		
	}
}
