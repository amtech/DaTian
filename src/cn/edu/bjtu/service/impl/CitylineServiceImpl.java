package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.CitylineDao;
import cn.edu.bjtu.service.CitylineService;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Cityline;

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
	@Resource
	BaseDao baseDao;
	@Resource
	HQLTool hqltool;

	@Override
	/**
	 * ��ȡ���г���������·
	 */
	public List getAllCityline(int Display, int PageNow) {
		// TODO Auto-generated method stub

		return citylineDao.getAllCityline(Display, PageNow);
	}

	@Override
	/**
	 * ����ɸѡ����������·
	 */
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
		
		// System.out.println("hql+" + sql);
		return citylineDao.getSelectedCityline(sql, Display, PageNow);
	}

	@Override
	/**
	 * ��ȡ�ܼ�¼���� 
	 */
	public int getTotalRows(String cityName, String VIPService, String refPrice) {
		// TODO Auto-generated method stub
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
		// System.out.println("hql+"+sql);
		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}

	@Override
	/**
	 * ��ȡ����������Ϣ
	 */
	public Cityline getCitylineInfo(String citylineid) {
		// TODO Auto-generated method stub

		return citylineDao.getCitylineInfo(citylineid);
	}

	@Override
	/**
	 * ��ȡ��˾����������·
	 */
	public List getCompanyCityline(String carrierId) {
		// TODO Auto-generated method stub
		return citylineDao.getCompanyCityline(carrierId);
	}

	@Override
	/**
	 * ������������
	 */
	public boolean insertCityLine(String name, String cityName,
			String VIPService, float refPrice, String remarks, String carrierId, String VIPDetail,
			String path, String fileName) {
		// TODO Auto-generated method stub
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
		return baseDao.save(cityline);
	}

	@Override
	/**
	 * ���³�������
	 */
	public boolean updateLine(String id, String citylineName, String cityName,
			String VIPService, String VIPDetail, float refPrice,
			String remarks, String carrierId,String path,String fileName) {
		// TODO Auto-generated method stub

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
		return baseDao.update(cityline);

	}
	
	@Override
	/**
	 * ɾ����������
	 */
	public boolean deleteCityline(String id) {
		cityline = getCitylineInfo(id);// ����id���ҵ�����������Ϣ
		return baseDao.delete(cityline);
	}
}
