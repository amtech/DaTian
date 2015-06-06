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
	 * �������г�����Ϣ
	 */
	public List getAllCityline(int Display,int PageNow) {
		// TODO Auto-generated method stub
		
		int page = PageNow;
		int pageSize = Display;
		String hql=" from CityCarrierView";
		
		return hqltool.getQueryList(hql, page, pageSize);//dao�����ȡ���ݷ���
		
	}

	@Override
	public List getAllCitylineWithoutPage() {
		// TODO Auto-generated method stub
		return ht.find(" from CityCarrierView");
	}
	
	@Override
	/**
	 * ����ָ����·��Ϣ
	 */
	public Cityline getCitylineInfo(String citylineid) {
		// TODO Auto-generated method stub
		return ht.get(Cityline.class, citylineid);
	}

	@Override
	/**
	 * ���ع�˾����������·��Ϣ
	 */
	public List getCompanyCityline(String carrierId) {
		// TODO Auto-generated method stub
		return ht.find("from Cityline where carrierId='"+carrierId+"'");
		
	}
	
	@Override
	public List getSelectedCityline(String hql, int display, int pageNow) {
		// TODO Auto-generated method stub
		//System.out.println("hql+"+hql);
		int page = pageNow;
		int pageSize = display;

		return hqltool.getQueryList(hql, page, pageSize);//Dao���ҳ������ȡ���˷���
	}

}
