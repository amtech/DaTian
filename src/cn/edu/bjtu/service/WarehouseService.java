package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.bjtu.bean.search.WarehouseSearchBean;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Linetransport;
import cn.edu.bjtu.vo.Warehouse;

import com.alibaba.fastjson.JSONArray;

public interface WarehouseService {

	@Deprecated
	public List getSelectedWarehouse(String city, String type, String storageForm, String houseArea, int Display,int PageNow);
	@Deprecated
	public int getTotalRows(String city, String type, String storageForm, String houseArea);
	
	public Warehouse getWarehouseInfo(String warehouseid);
	@Deprecated
	public List getCompanyWarehouse(String carrierId);
	@Deprecated
	public boolean insertWarehouse(String name,String city,String address,String type,String kind,
			float houseArea,float yardArea,float height,String fireRate,String storageForm,
			String fireSecurity,String environment,String serviceContent,String contact,
			String phone,String remarks,String carrierId,String path,String fileName);
	public boolean insertNewWarehouse(Warehouse warehouse,HttpServletRequest request,MultipartFile file);
	@Deprecated
	public boolean updateWarehouse(String id, String name,String city,String address,String type,String kind,
			float houseArea,float yardArea,float height,String fireRate,String storageForm,
			String fireSecurity,String environment,String serviceContent,
			String contact,String phone,String remarks,String carrierId,String path,String fileName);
	public boolean updateNewWarehouse(Warehouse warehouse,HttpServletRequest request,MultipartFile file);
	
	
	public boolean deleteWarehouse(String id);
	
	/**
	 * ��Դ��-�ֿ�ɸѡ
	 * @param warehouseBean
	 * @param pageUtil
	 * @param session
	 * @return
	 */
	public JSONArray getSelectedWarehouseNew(WarehouseSearchBean warehouseBean,PageUtil pageUtil,HttpSession session);

	/**
	 * ��Դ��-�ֿ�ɸѡ������
	 * @param warehouseBean
	 * @return
	 */
	public Integer getSelectedWarehouseTotalRows(WarehouseSearchBean warehouseBean);
	
	/**
	 * �ҵ���Ϣ-�ҵĻ���
	 * @Title: getUserWarehouseResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: JSONArray 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����11:29:35
	 */
	public JSONArray getUserWarehouseResource(HttpSession session,PageUtil pageUtil);
	
	/**
	 * �ҵ���Ϣ-�ҵĻ���-�ܼ�¼����
	 * @Title: getUserWarehouseResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����11:30:06
	 */
	public Integer getUserWarehouseResourceTotalRows(HttpSession session);

}
