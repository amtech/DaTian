package cn.edu.bjtu.service;

import com.alibaba.fastjson.JSONArray;

/**
 * �������
 * @author RussWest0
 *
 */
public interface SearchService {
	
	/**
	 * �ڳ�����������������Դ���
	 * @param cityName
	 * @return
	 */
	public JSONArray getLineResourceByCityName(String cityName,int display,int currentPage);
	/**
	 * �������������������ͽ��
	 * @param name
	 * @return
	 */
	public JSONArray getCitylineResourceByName(String name,int display,int currentPage);
	/**
	 * ���ݻ�����������ȡ���
	 * @param name
	 * @return
	 */
	public JSONArray getGoodsResourceByName(String name,int display,int currentPage);
	/**
	 * ���ݹ�˾��������ȡ���
	 * @param name
	 * @return
	 */
	public JSONArray getCompanyResourceByCompanyName(String companyName,int display,int currentPage);
	/**
	 * ���ݳ���������������ȡ���
	 * @param name
	 * @return
	 */
	public JSONArray getCarResourceByCarNum(String carNum,int display,int currentPage);
	/**
	 * ���ݲֿ���������ȡ���
	 * @param name
	 * @return
	 */
	public JSONArray getWarehouseResourceByName(String name,int display,int currentPage);
}
