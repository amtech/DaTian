package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.LinetransportDao;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.HQL_POJO;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Linetransport;

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

		System.out.println(sql);
		// System.out.println("hql+" + sql);
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
			if (!(valueList[i].equals("All")))// Ҫ�ǵ���all��˵����Ĭ�ϵģ�����д��where�Ӿ�
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
		linetransport.setCarrierId(carrierId);// ����session���carrierid
		// linetransport.setDetailPrice(detailPrice);
		linetransport.setEndPlace(endPlace);
		linetransport.setId(IdCreator.createlineTransportId());
		linetransport.setOnWayTime(onWayTime);
		linetransport.setRefPrice(refPrice);
		linetransport.setRelDate(new Date());
		linetransport.setRemarks(remarks);
		linetransport.setStartPlace(startPlace);
		linetransport.setType(type);
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			linetransport.setDetailPrice(fileLocation);
		}
		linetransportDao.save(linetransport);// ����ʵ��
		return false;

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
		return false;

	}

	@Override
	/**
	 * ɾ������
	 */
	public boolean deleteLine(String id) {
		linetransport = getLinetransportInfo(id);// ����id���ҵ�������Ϣ

		System.out.println(linetransport);
		System.out.println(id);
		linetransportDao.delete(linetransport);
		
		return false;
	}

}
