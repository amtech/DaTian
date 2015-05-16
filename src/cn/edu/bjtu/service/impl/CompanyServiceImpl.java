package cn.edu.bjtu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.CompanyDao;
import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.vo.Carrierinfo;

/**
 * 
 * @author RussWest0
 *
 */
@Repository
public class CompanyServiceImpl implements CompanyService{
	
	@Resource
	CompanyDao companyDao;
	@Resource
	HQLTool hqltool;
	
	@Override
	/**
	 * �������й�˾
	 */
	public List getAllCompany(int Display,int PageNow)  {
		// TODO Auto-generated method stub
		return companyDao.getAllCompany(Display,PageNow);
	}

	@Override
	/**
	 * ���ع�˾��Ϣ
	 */
	public Carrierinfo getCarrierInfo(String id) {
		// TODO Auto-generated method stub
		return companyDao.getCarrierInfo(id);
	}
	
	@Override
	/**
	 * ����ɸѡ��˾
	 */
	public List getSelectedCompany(String resourceRate, String serviceIndustry, 
			String creditRate, String business, int Display,int PageNow) {
		String sql="";
		if(resourceRate.equals("������Դ")){
			resourceRate="����";
		}
		else if(resourceRate.equals("������Դ")){
			resourceRate="����";
		}
		else if(resourceRate.equals("��Χ��Դ")){
			resourceRate="��Χ";
		}
		
		if(serviceIndustry.equals("ҽҩ��ҵ")){
			serviceIndustry="ҽҩ";
		}
		else if(serviceIndustry.equals("������ҵ")){
			serviceIndustry="����";
		}
		else if(serviceIndustry.equals("������ҵ")){
			serviceIndustry="����";
		}
		if(business.equals("��������ҵ��"))
		{
			String line="1";
			String [] paramList={"resourceRate","serviceIndustry","creditRate","line"};//ûstartplace1 
			String [] valueList={resourceRate,serviceIndustry,creditRate,line};
			String hql="from Carrierinfo ";//��仯
			sql=HQLTool.spellHql2(hql,paramList, valueList);
		}
		else if(business.equals("��������ҵ��"))
		{
			String city="1";
			String [] paramList={"resourceRate","serviceIndustry","creditRate","city"};//ûstartplace1 
			String [] valueList={resourceRate,serviceIndustry,creditRate,city};
			String hql="from Carrierinfo ";//��仯
			sql=HQLTool.spellHql2(hql,paramList, valueList);
		}
		else if(business.equals("�ִ�ҵ��"))
		{
			String warehouse="1";
			String [] paramList={"resourceRate","serviceIndustry","creditRate","warehouse"};//ûstartplace1 
			String [] valueList={resourceRate,serviceIndustry,creditRate,warehouse};
			String hql="from Carrierinfo ";//��仯
			sql=HQLTool.spellHql2(hql,paramList, valueList);
		}
		else
		{
			String [] paramList={"resourceRate","serviceIndustry","creditRate"};//ûstartplace1 
			String [] valueList={resourceRate,serviceIndustry,creditRate};
			String hql="from Carrierinfo ";//��仯
			sql=HQLTool.spellHql2(hql,paramList, valueList);
		}
		
		//System.out.println("hql+" + sql);
		return companyDao.getSelectedCompany(sql,Display,PageNow);
	}
	
	@Override
	/**
	 * ��ȡ�ܼ�¼���� 
	 */
	public int getTotalRows(String resourceRate, String serviceIndustry, String creditRate, String business) {
		// TODO Auto-generated method stub
		String sql="";
		if(resourceRate.equals("������Դ")){
			resourceRate="����";
		}
		else if(resourceRate.equals("������Դ")){
			resourceRate="����";
		}
		else if(resourceRate.equals("��Χ��Դ")){
			resourceRate="��Χ";
		}
		
		if(serviceIndustry.equals("ҽҩ��ҵ")){
			serviceIndustry="ҽҩ";
		}
		else if(serviceIndustry.equals("������ҵ")){
			serviceIndustry="����";
		}
		else if(serviceIndustry.equals("������ҵ")){
			serviceIndustry="����";
		}
		if(business.equals("��������ҵ��"))
		{
			String line="1";
			String [] paramList={"resourceRate","serviceIndustry","creditRate","line"};//ûstartplace1 
			String [] valueList={resourceRate,serviceIndustry,creditRate,line};
			String hql="from Carrierinfo ";//��仯
			sql=HQLTool.spellHql2(hql,paramList, valueList);
		}
		else if(business.equals("��������ҵ��"))
		{
			String city="1";
			String [] paramList={"resourceRate","serviceIndustry","creditRate","city"};//ûstartplace1 
			String [] valueList={resourceRate,serviceIndustry,creditRate,city};
			String hql="from Carrierinfo ";//��仯
			sql=HQLTool.spellHql2(hql,paramList, valueList);
		}
		else if(business.equals("�ִ�ҵ��"))
		{
			String warehouse="1";
			String [] paramList={"resourceRate","serviceIndustry","creditRate","warehouse"};//ûstartplace1 
			String [] valueList={resourceRate,serviceIndustry,creditRate,warehouse};
			String hql="from Carrierinfo ";//��仯
			sql=HQLTool.spellHql2(hql,paramList, valueList);
		}
		else
		{
			String [] paramList={"resourceRate","serviceIndustry","creditRate"};//ûstartplace1 
			String [] valueList={resourceRate,serviceIndustry,creditRate};
			String hql="from Carrierinfo ";//��仯
			sql=HQLTool.spellHql2(hql,paramList, valueList);
		}
		//System.out.println("hql+"+sql);
		return hqltool.getTotalRows(sql);//�����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}
	
	@Override
	public List getLinetransportByCarrierId(String id){
		return companyDao.getLinetransportByCarrierId(id);
	}
	
	@Override
	public List getCitylineByCarrierId(String id){
		return companyDao.getCitylineByCarrierId(id);
	}
	
	@Override
	public List getwarehouseByCarrierId(String id){
		return companyDao.getwarehouseByCarrierId(id);
	}
	
}
