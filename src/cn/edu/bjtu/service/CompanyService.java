package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.bean.search.CompanySearchBean;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Carrierinfo;

import com.alibaba.fastjson.JSONArray;

public interface CompanyService {
	
	public List getAllCompany(int Display,int PageNow);
	public List getSelectedCompany(String resourceRate, String serviceIndustry, 
			String creditRate, String business, int Display,int PageNow);
	public int getTotalRows(String resourceRate, String serviceIndustry, 
			String creditRate, String business);
	
	/*public Carrierinfo getCarrierInfo(String id);*/
	public List getLinetransportByCarrierId(String id);
	public List getCitylineByCarrierId(String id);
	public List getwarehouseByCarrierId(String id);

	public Carrierinfo getCompanyById(String carrierId);
	public List getAllCompanyWithoutPage();
	
	/**
	 * ��Դ��-��˾ɸѡ
	 * @Title: getSelectedCompanyNew 
	 * @Description: TODO 
	 * @param: @param companyBean
	 * @param: @param pageUtil
	 * @param: @param session
	 * @param: @return 
	 * @return: String 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��6��15�� ����4:42:53
	 */
	public JSONArray getSelectedCompanyNew(CompanySearchBean companyBean,PageUtil pageUtil,HttpSession session);
	
	/**
	 * ��Դ����˾ɸѡ�ܼ�¼��
	 * @Title: getSelectedCompanyTotalRows 
	 * @Description: TODO 
	 * @param: @param companyBean
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��6��15�� ����4:43:04
	 */
	public Integer getSelectedCompanyTotalRows(CompanySearchBean companyBean);
}
