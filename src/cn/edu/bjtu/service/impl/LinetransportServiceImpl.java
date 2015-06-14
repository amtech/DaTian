package cn.edu.bjtu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.bean.search.LinetransportSearchBean;
import cn.edu.bjtu.dao.LinetransportDao;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.DataModel;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.HQL_POJO;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Linetransport;

import com.alibaba.fastjson.JSONArray;

@Transactional
@Service
/**
 * 
 * @author RussWest0
 *
 */
public class LinetransportServiceImpl implements LinetransportService {

	@Resource
	LinetransportDao linetransportDao;
	@Resource
	Linetransport linetransport;
	/*@Resource
	BaseDao baseDao;*/
	@Resource
	HQLTool hqltool;
	private Logger logger=Logger.getLogger(LinetransportServiceImpl.class);

	private String hql = "";
	private static boolean flag = false;

	@Override
	/**
	 * �������и����б�
	 */
	public List getAllLinetransport(int Display, int PageNow) {
		// TODO Auto-generated method stub
		return linetransportDao.getAllLinetransport(Display, PageNow);
	}
	
	@Override
	/**
	 * �������и����б�
	 */
	public List getAllLinetransportWithoutPage() {
		// TODO Auto-generated method stub
		return linetransportDao.getAllLinetransportWithoutPage();
	}

	@Override
	/**
	 * ���ظ�����Ϣ
	 */
	public Linetransport getLinetransportInfo(String linetransportid) {
		// TODO Auto-generated method stub
		return linetransportDao.getLinetransportInfo(linetransportid);
	}

	@Override
	/**
	 * ����ɸѡ����
	 */
	public List getSelectedLine(String startPlace, String endPlace,
			String type, String startPlace1, String refPrice, int Display,
			int PageNow) {
		// TODO Auto-generated method stub

		String sql = "";
		if (refPrice.equals("����2Ԫ/kg")) {
			String[] paramList = { "startPlace", "endPlace", "type" };// ûstartplace1
			String[] valueList = { startPlace, endPlace, type };
			sql = spellHql2(paramList, valueList);
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
			String[] paramList = { "startPlace", "endPlace", "type" };// ûstartplace1
			String[] valueList = { startPlace, endPlace, type };
			sql = spellHql2(paramList, valueList);
			int i = 0;
			for (; i < paramList.length; i++) {
				if (!(valueList[i].equals("All"))) {
					break;
				}
			}
			if (i == paramList.length) {
				// Ҫ�����������All����Ҫ����where�ֶ�
				sql += "where refPrice >= 1 and refPrice <= 2";
			} else
				sql += "and refPrice >= 1 and refPrice <= 2";
		} else if (refPrice.equals("С��1Ԫ/kg")) {
			String[] paramList = { "startPlace", "endPlace", "type" };// ûstartplace1
			String[] valueList = { startPlace, endPlace, type };
			sql = spellHql2(paramList, valueList);
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
		} else {
			String[] paramList = { "startPlace", "endPlace", "type", "refPrice" };// ûstartplace1
			String[] valueList = { startPlace, endPlace, type, refPrice };
			sql = spellHql2(paramList, valueList);
		}

		return linetransportDao.getSelectedLine(sql, Display, PageNow);
		// return null;

	}

	@Override
	/**
	 * ��ȡ�ܼ�¼���� 
	 */
	public int getTotalRows(String startPlace, String endPlace, String type,
			String startPlace1, String refPrice) {
		// TODO Auto-generated method stub
		String sql = "";
		if (refPrice.equals("����2Ԫ/kg")) {
			String[] paramList = { "startPlace", "endPlace", "type" };// ûstartplace1
			String[] valueList = { startPlace, endPlace, type };
			sql = spellHql2(paramList, valueList);
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
			String[] paramList = { "startPlace", "endPlace", "type" };// ûstartplace1
			String[] valueList = { startPlace, endPlace, type };
			sql = spellHql2(paramList, valueList);
			int i = 0;
			for (; i < paramList.length; i++) {
				if (!(valueList[i].equals("All"))) {
					break;
				}
			}
			if (i == paramList.length) {
				// Ҫ�����������All����Ҫ����where�ֶ�
				sql += "where refPrice >= 1 and refPrice <= 2";
			} else
				sql += "and refPrice >= 1 and refPrice <= 2";
		} else if (refPrice.equals("С��1Ԫ/kg")) {
			String[] paramList = { "startPlace", "endPlace", "type" };// ûstartplace1
			String[] valueList = { startPlace, endPlace, type };
			sql = spellHql2(paramList, valueList);
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
		} else {
			String[] paramList = { "startPlace", "endPlace", "type", "refPrice" };// ûstartplace1
			String[] valueList = { startPlace, endPlace, type, refPrice };
			sql = spellHql2(paramList, valueList);
		}
		// System.out.println("hql+"+sql);
		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}

	/**
	 * ���ߺ���-ƴ��hql
	 * 
	 * @param paramList
	 * @param valueList
	 * @return ����ƴ�Ӻõ�hql���
	 */
	private String spellHql2(String[] paramList, String[] valueList) {
		HQL_POJO hqlobj = new HQL_POJO();
		hqlobj.hql = "from LineCarrierView ";// ��仯
		for (int i = 0; i < paramList.length; i++) {
			if (!(valueList[i].equals("All")||valueList[i].equals("���Ļ�ƴ��")||valueList[i].equals("")))// Ҫ�ǵ���all��˵����Ĭ�ϵģ�����д��where�Ӿ�
			{
				hqlobj.hql += HQLTool.spellHql(hqlobj).hql;
				hqlobj.hql += paramList[i] + "='" + valueList[i] + "' ";
			}
		}
		return hqlobj.hql;
	}

	/*
	 * public static void main(String[] args) { int i = new
	 * LinetransportServiceImpl().getTotalRows("All", "All", "All", "All",
	 * "All"); }
	 */

	@Override
	/**
	 * ��������
	 */
	public boolean insertLine(String lineName, String startPlace,
			String endPlace, int onWayTime, String type, float refPrice,
			String remarks, String carrierId, String path, String fileName) {
		// TODO Auto-generated method stub
		linetransport.setId(IdCreator.createlineTransportId());
		linetransport.setCarrierId(carrierId);// ����session���carrierid
		linetransport.setLineName(lineName);
		linetransport.setStartPlace(startPlace);
		linetransport.setEndPlace(endPlace);
		linetransport.setOnWayTime(onWayTime);
		linetransport.setRefPrice(refPrice);
		linetransport.setRelDate(new Date());
		linetransport.setRemarks(remarks);
		linetransport.setType(type);
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			linetransport.setDetailPrice(fileLocation);
		}
		linetransportDao.save(linetransport);// ����ʵ��
		return true;

	}

	@Override
	/**
	 * ����ĳ��˾�����и�����Ϣ
	 */
	public List getCompanyLine(String carrierId, int Display, int PageNow) {
		// TODO Auto-generated method stub
		return linetransportDao.getCompanyLine(carrierId, Display, PageNow);// δ���
	}

	@Override
	// δʵ��
	public String getLinetransportIdByCity(String startPlace, String endPlace) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public int getCompanyTotalRows(String carrierId) {
		// TODO Auto-generated method stub
		return linetransportDao.getCompanyTotalRows(carrierId);
	}

	@Override
	/**
	 * ���¸���
	 */
	public boolean updateLine(String id, String lineName, String startPlace,
			String endPlace, int onWayTime, String type, float refPrice,
			String remarks, String carrierId, String path, String fileName) {
		// TODO Auto-generated method stub

		linetransport = getLinetransportInfo(id);// ����id���ҵ�������Ϣ
		linetransport.setLineName(lineName);
		linetransport.setStartPlace(startPlace);
		linetransport.setEndPlace(endPlace);
		linetransport.setOnWayTime(onWayTime);
		linetransport.setType(type);
		linetransport.setRefPrice(refPrice);
		linetransport.setRemarks(remarks);
		linetransport.setRelDate(new Date());
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			linetransport.setDetailPrice(fileLocation);
		}

		linetransportDao.update(linetransport);
		return true;

	}

	@Override
	/**
	 * ɾ������
	 */
	public boolean deleteLine(String id) {
		linetransport = getLinetransportInfo(id);// ����id���ҵ�������Ϣ

		linetransportDao.delete(linetransport);
		
		return true;
	}
	
	
	@Override
	/**
	 * ������ɸѡ����
	 */
	public DataModel getSelectedLineNew(LinetransportSearchBean linetransportbean,
			PageUtil pageUtil,HttpSession session) {
		// TODO Auto-generated method stub
		String userId=(String)session.getAttribute(Constant.USER_ID);
		Map<String,Object> params=new HashMap<String,Object>();
		String sql = "select t1.id,"
				+ "t1.carrierId,"
				+ "t1.lineName,"
				+ "t1.startPlace,"
				+ "t1.endPlace,"
				+ "t1.refPrice,"
				+ "t1.relDate,"
				+ "t1.type,"
				+ "t1.onWayTime,"
				+ "t1.companyName,"
				+ "t3.status "
				+ " from line_carrier_view t1 "
				+ "left join ("
				+ "select * from focus t2 ";
				
		if(userId!=null){//�����ǰ���û���¼�������м����û���Ϣ
			sql+=" where t2.focusType='linetransport' and t2.clientId=:clientId ";
			params.put("clientId", userId);
		}
		sql+=") t3 on t1.id=t3.focusId ";
		String wheresql=whereSql(linetransportbean,params);
		sql+=wheresql;
		
		JSONArray jsonArray = new JSONArray();
		int page=pageUtil.getCurrentPage()==0?1:pageUtil.getCurrentPage();
		int display=pageUtil.getDisplay()==0?10:pageUtil.getDisplay();
		List<Object[]> objectList=linetransportDao.findBySql(sql, params,page,display);
		
		List<LinetransportSearchBean> lineList=new ArrayList<LinetransportSearchBean>();
		for(Iterator<Object[]> it=objectList.iterator();it.hasNext();){
			LinetransportSearchBean lineBean=new LinetransportSearchBean();
			
			Object[] obj=it.next();
			lineBean.setId((String)obj[0]);
			lineBean.setCarrierId((String)obj[1]);
			lineBean.setLineName((String)obj[2]);
			lineBean.setStartPlace((String)obj[3]);
			lineBean.setEndPlace((String)obj[4]);
			lineBean.setRefPrice(((Float)obj[5])+"");
			lineBean.setRelDate((Date)obj[6]);
			lineBean.setTransportType((String)obj[7]);
			lineBean.setOnWayTime((Integer)obj[8]);
			lineBean.setCompanyName((String)obj[9]);
			lineBean.setStatus((String)obj[10]);
			lineList.add(lineBean);
		}
		//����������
		DataModel dataModel=new DataModel();
		/*String countsql="select count(t1.id) from line_carrier_view t1"+whereSql(linetransportbean,params);
		//Long count=linetransportDao.countBySql(countsql, params);
		Long count=linetransportDao.countBySql("select count(*) from linetransport");
		dataModel.setTotal(count);*/
		dataModel.setRows(lineList);
		return dataModel;
	}
	
	/**
	 * ƴ��where
	 * @param linetransportBean
	 * @param page
	 * @param params
	 * @return
	 */
	private String whereSql(LinetransportSearchBean linetransportBean,Map<String,Object> params){
		
		String wheresql=" where 1=1 ";
		if(linetransportBean.getStartPlace()!=null && !linetransportBean.getStartPlace().trim().equals("���Ļ�ƴ��")&&
				!linetransportBean.getStartPlace().trim().equals("ȫ��") && !linetransportBean.getStartPlace().trim().equals("")){
			wheresql+=" and t1.startPlace=:startPlace";
			params.put("startPlace", linetransportBean.getStartPlace());
		}
		if(linetransportBean.getEndPlace()!=null && !linetransportBean.getEndPlace().trim().equals("���Ļ�ƴ��")&&
				!linetransportBean.getEndPlace().trim().equals("ȫ��") && !linetransportBean.getStartPlace().trim().equals("")){
			wheresql+=" and t1.endPlace=:endPlace";
			params.put("endPlace", linetransportBean.getEndPlace());
		}
		if(linetransportBean.getRefPrice()!=null && !linetransportBean.getRefPrice().trim().equals("All") && !linetransportBean.getRefPrice().trim().equals("")){
			String refPrice=linetransportBean.getRefPrice().trim();
			if(refPrice.equals("����2Ԫ/kg")){
				wheresql+=" and t1.refPrice > 2 ";
			}
			if(refPrice.equals("1��2Ԫ/kg")){
				wheresql+=" and t1.refPrice > 1 and t1.refPrice < 1 ";
			}
			if(refPrice.equals("С��1Ԫ/kg")){
				wheresql+=" and t1.refPrice <1 ";
			}
		}
		if(linetransportBean.getTransportType()!=null && !linetransportBean.getTransportType().trim().equals("All") && !linetransportBean.getTransportType().trim().equals("")){
			wheresql+=" and t1.type=:transportType ";
			params.put("transportType", linetransportBean.getTransportType());
		}
		//ʼ�������Ȳ�ʵ��
		/*if(linetransportBean.getFromPlace() != null && linetr linetransportBean.getFromPlace().trim().equals("All")){
			
		}*/
		
		return wheresql;
		
	}
	/**
	 * ����ɸѡ������
	 */
	@Override
	public Integer getSelectedLineTotalRows(LinetransportSearchBean lineBean) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		//String countsql="select count(t1.id) from line_carrier_view t1"+whereSql(lineBean,params);
		String hql="select count(*) from LineCarrierView t1"+whereSql(lineBean, params);
		//Long count=linetransportDao.countBySql(countsql, params);
		//Long count=linetransportDao.countBySql("select count(*) from linetransport");
		Long count=linetransportDao.count(hql, params);
		
		return count.intValue();
		
	}
	

}
