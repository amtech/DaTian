package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.WarehouseDao;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.vo.Warehouse;

@Repository
public class WarehouseDaoImpl extends BaseDaoImpl<Warehouse> implements WarehouseDao{
	
	@Resource
	private HibernateTemplate ht;
	@Resource 
	private HQLTool hqltool;
	
	@Override
	/**
	 * �������вֿ���Ϣ
	 * ��ͼ��ѯ
	 * 
	 */
	public List getAllWarehouse(int Display,int PageNow) {
		// TODO Auto-generated method stub
		int page = PageNow;
		int pageSize = Display;
		String hql=" from WarehouseCarrierView";
		
		return hqltool.getQueryList(hql, page, pageSize);//dao�����ȡ���ݷ���

	}
	
	@Override
	/**
	 * ���ؾ��������Ϣ
	 */
	public Warehouse getWarehouseInfo(String warehouseid) {
		// TODO Auto-generated method stub
		return ht.get(Warehouse.class, warehouseid);
		
	}

	@Override
	public List getCompanyWarehouse(String carrierId) {
		// TODO Auto-generated method stub
		return ht.find("from Warehouse where carrierId='"+carrierId+"'");
	}
	
	@Override
	public List getSelectedWarehouse(String hql, int display, int pageNow) {
		// TODO Auto-generated method stub
		//System.out.println("hql+"+hql);
		int page = pageNow;
		int pageSize = display;

		return hqltool.getQueryList(hql, page, pageSize);//Dao���ҳ������ȡ���˷���
	}
}
