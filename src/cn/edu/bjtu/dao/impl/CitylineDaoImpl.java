package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.CitylineDao;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.vo.Cityline;

@Repository
public class CitylineDaoImpl extends BaseDaoImpl<Cityline> implements CitylineDao{
	
	@Resource
	private HibernateTemplate ht;
	@Resource 
	private HQLTool hqltool;
	

	
	@Override
	/**
	 * ����ָ����·��Ϣ
	 */
	public Cityline getCitylineInfo(String citylineid) {
		
		return ht.get(Cityline.class, citylineid);
	}

	@Override
	/**
	 * ���ع�˾����������·��Ϣ
	 */
	@Deprecated
	public List getCompanyCityline(String carrierId) {
		
		return ht.find("from Cityline where carrierId='"+carrierId+"'");
		
	}
	
	@Override
	public List getSelectedCityline(String hql, int display, int pageNow) {
		
		int page = pageNow;
		int pageSize = display;

		return hqltool.getQueryList(hql, page, pageSize);//Dao���ҳ������ȡ���˷���
	}

}
