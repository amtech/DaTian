package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.CompanyDao;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.vo.Carrierinfo;
/**
 * 
 * @author RussWest0
 *
 */
@Repository
public class CompanyDaoImpl extends BaseDaoImpl<Carrierinfo> implements CompanyDao{
	
	@Resource
	private HibernateTemplate ht;
	@Resource 
	private HQLTool hqltool;
	
	
	@Override
	/**
	 * �������й�˾��Ϣ
	 */
	@Deprecated
	public List getAllCompany(int Display,int PageNow) {
		
		//return ht.find("from Carrierinfo");
		int page = PageNow;
		int pageSize = Display;
		String hql=" from Carrierinfo";
		
		return hqltool.getQueryList(hql, page, pageSize);//dao�����ȡ���ݷ���
		
		
	}

	@Override
	/**
	 * �������й�˾��Ϣ,���÷�ҳ
	 */
	@Deprecated
	public List getAllCompanyWithoutPage() {
		
		//return ht.find("from Carrierinfo");
		return ht.find("from Carrierinfo");
		
		
		
	}

	@Override
	/**
	 * ���ط���ɸѡ�����Ĺ�˾��Ϣ
	 */
	@Deprecated
	public List getSelectedCompany(String hql, int display, int pageNow) {
		
		int page = pageNow;
		int pageSize = display;

		return hqltool.getQueryList(hql, page, pageSize);//Dao���ҳ������ȡ���˷���
	}


	@Override
	/**
	 * �����ض��Ĺ�˾��Ϣ
	 */
	public Carrierinfo getCarrierInfo(String id) {
		
		
		return ht.get(Carrierinfo.class, id);
	}
	
	@Override
	public List getLinetransportByCarrierId(String id){
		return ht.find("from Linetransport as s where s.carrierId='" + id+ "'");
	}
	
	@Override
	public List getCitylineByCarrierId(String id){
		return ht.find("from Cityline as s where s.carrierId='" + id+ "'");
	}
	
	@Override
	public List getwarehouseByCarrierId(String id){
		return ht.find("from Warehouse as s where s.carrierId='" + id+ "'");
	}
	
	
	

}
