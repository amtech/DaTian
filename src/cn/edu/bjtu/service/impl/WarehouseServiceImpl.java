package cn.edu.bjtu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.bean.search.WarehouseSearchBean;
import cn.edu.bjtu.dao.WarehouseDao;
import cn.edu.bjtu.service.WarehouseService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Warehouse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Repository
/**
 * 
 * @author RussWest0
 *
 */
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

	@Resource
	WarehouseDao warehouseDao;
	@Resource
	Warehouse warehouse;
	/*@Resource
	BaseDao baseDao;*/
	@Resource
	HQLTool hqltool;


	@Override
	/**
	 * ����ɸѡ����
	 */
	@Deprecated
	public List getSelectedWarehouse(String city, String type,
			String storageForm, String houseArea, int Display, int PageNow) {

		if (type.equals("��˰�ֿ�")) {
			type = "��˰";
		} else if (type.equals("�Ǳ�˰�ֿ�")) {
			type = "�Ǳ�˰";
		}

		if (storageForm.equals("��ͨ�ֿ�")) {
			storageForm = "��ͨ";
		} else if (storageForm.equals("��زֿ�")) {
			storageForm = "���";
		} else if (storageForm.equals("���²ֿ�")) {
			storageForm = "����";
		} else if (storageForm.equals("¶��ֿ�")) {
			storageForm = "¶��";
		} else if (storageForm.equals("Σ��Ʒ�ֿ�")) {
			storageForm = "Σ��Ʒ";
		}

		String sql = "";
		String hql = "from WarehouseCarrierView ";// ��仯
		if (houseArea.equals("����1��ƽ����")) {

			String[] paramList = { "city", "type", "storageForm" };
			String[] valueList = { city, type, storageForm };
			sql = HQLTool.spellHql2(hql, paramList, valueList);

			int i = 0;
			for (; i < paramList.length; i++) {
				if (!(valueList[i].equals("All"))) {
					break;
				}
			}
			if (i == paramList.length) {
				// Ҫ�����������All����Ҫ����where�ֶ�
				sql += "where houseArea > 10000";
			} else
				sql += "and houseArea > 10000";

		} else if (houseArea.equals("����2��ƽ����")) {
			String[] paramList = { "city", "type", "storageForm" };
			String[] valueList = { city, type, storageForm };
			sql = HQLTool.spellHql2(hql, paramList, valueList);
			int i = 0;
			for (; i < paramList.length; i++) {
				if (!(valueList[i].equals("All"))) {
					break;
				}
			}
			if (i == paramList.length) {
				// Ҫ�����������All����Ҫ����where�ֶ�
				sql += "where houseArea > 20000";
			} else
				sql += "and houseArea > 20000";

		} else if (houseArea.equals("����5��ƽ����")) {
			String[] paramList = { "city", "type", "storageForm" };
			String[] valueList = { city, type, storageForm };
			sql = HQLTool.spellHql2(hql, paramList, valueList);
			int i = 0;
			for (; i < paramList.length; i++) {
				if (!(valueList[i].equals("All"))) {
					break;
				}
			}
			if (i == paramList.length) {
				// Ҫ�����������All����Ҫ����where�ֶ�
				sql += "where houseArea > 50000";
			} else
				sql += "and houseArea > 50000";

		} else {
			String[] paramList = { "city", "type", "storageForm" };
			String[] valueList = { city, type, storageForm };
			sql = HQLTool.spellHql2(hql, paramList, valueList);

		}
		return warehouseDao.getSelectedWarehouse(sql, Display, PageNow);
	}

	@Override
	/**
	 * ��ȡ�ܼ�¼���� 
	 */
	@Deprecated
	public int getTotalRows(String city, String type, String storageForm,
			String houseArea) {
		
		if (type.equals("��˰�ֿ�")) {
			type = "��˰";
		} else if (type.equals("�Ǳ�˰�ֿ�")) {
			type = "�Ǳ�˰";
		}

		if (storageForm.equals("��ͨ�ֿ�")) {
			storageForm = "��ͨ";
		} else if (storageForm.equals("��زֿ�")) {
			storageForm = "���";
		} else if (storageForm.equals("���²ֿ�")) {
			storageForm = "����";
		} else if (storageForm.equals("¶��ֿ�")) {
			storageForm = "¶��";
		} else if (storageForm.equals("Σ��Ʒ�ֿ�")) {
			storageForm = "Σ��Ʒ";
		}

		String sql = "";
		String hql = "from WarehouseCarrierView ";// ��仯
		if (houseArea.equals("����1��ƽ����")) {

			String[] paramList = { "city", "type", "storageForm" };
			String[] valueList = { city, type, storageForm };
			sql = HQLTool.spellHql2(hql, paramList, valueList);

			int i = 0;
			for (; i < paramList.length; i++) {
				if (!(valueList[i].equals("All"))) {
					break;
				}
			}
			if (i == paramList.length) {
				// Ҫ�����������All����Ҫ����where�ֶ�
				sql += "where houseArea > 10000";
			} else
				sql += "and houseArea > 10000";

		} else if (houseArea.equals("����2��ƽ����")) {
			String[] paramList = { "city", "type", "storageForm" };
			String[] valueList = { city, type, storageForm };
			sql = HQLTool.spellHql2(hql, paramList, valueList);
			int i = 0;
			for (; i < paramList.length; i++) {
				if (!(valueList[i].equals("All"))) {
					break;
				}
			}
			if (i == paramList.length) {
				// Ҫ�����������All����Ҫ����where�ֶ�
				sql += "where houseArea > 20000";
			} else
				sql += "and houseArea > 20000";

		} else if (houseArea.equals("����5��ƽ����")) {
			String[] paramList = { "city", "type", "storageForm" };
			String[] valueList = { city, type, storageForm };
			sql = HQLTool.spellHql2(hql, paramList, valueList);
			int i = 0;
			for (; i < paramList.length; i++) {
				if (!(valueList[i].equals("All"))) {
					break;
				}
			}
			if (i == paramList.length) {
				// Ҫ�����������All����Ҫ����where�ֶ�
				sql += "where houseArea > 50000";
			} else
				sql += "and houseArea > 50000";

		} else {
			String[] paramList = { "city", "type", "storageForm" };
			String[] valueList = { city, type, storageForm };
			sql = HQLTool.spellHql2(hql, paramList, valueList);

		}

		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}

	@Override
	public Warehouse getWarehouseInfo(String Warehouseid) {
		

		return warehouseDao.getWarehouseInfo(Warehouseid);
	}

	@Override
	public List getCompanyWarehouse(String carrierId) {
		
		return warehouseDao.getCompanyWarehouse(carrierId);
	}

	@Override
	public boolean insertWarehouse(String name, String city, String address,
			String type, String kind, float houseArea, float yardArea,
			float height, String fireRate, String storageForm,
			String fireSecurity, String environment, String serviceContent,
			String contact, String phone, String remarks, String carrierId,String path,String fileName) {
		
		warehouse.setAddress(address);
		warehouse.setCarrierId(carrierId);
		warehouse.setCity(city);
		warehouse.setContact(contact);
		// warehouse.setDetailPrice(detailPrice);
		warehouse.setEnvironment(environment);
		warehouse.setFireRate(fireRate);
		warehouse.setFireSecurity(fireSecurity);
		warehouse.setHeight(height);
		warehouse.setHouseArea(houseArea);
		warehouse.setId(IdCreator.createRepositoryId());
		warehouse.setKind(kind);
		warehouse.setName(name);
		warehouse.setPhone(phone);
		warehouse.setRelDate(new Date());
		warehouse.setRemarks(remarks);
		warehouse.setServiceContent(serviceContent);
		warehouse.setStorageForm(storageForm);
		warehouse.setType(type);
		warehouse.setYardArea(yardArea);
		
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			warehouse.setDetailPrice(fileLocation);
		}
		 warehouseDao.save(warehouse);// ����ʵ��
		 return true;
	}

	@Override
	public boolean updateWarehouse(String id, String name, String city,
			String address, String type, String kind, float houseArea,
			float yardArea, float height, String fireRate, String storageForm,
			String fireSecurity, String environment, String serviceContent,
			String contact, String phone, String remarks, String carrierId,String path,String fileName) {
		

		warehouse = getWarehouseInfo(id);// ����id���ҵ��ֿ���Ϣ
		warehouse.setName(name);
		warehouse.setCity(city);
		warehouse.setAddress(address);
		warehouse.setType(type);
		warehouse.setKind(kind);
		warehouse.setHouseArea(houseArea);
		warehouse.setYardArea(yardArea);
		warehouse.setHeight(height);
		warehouse.setFireRate(fireRate);
		warehouse.setStorageForm(storageForm);
		warehouse.setFireSecurity(fireSecurity);
		warehouse.setEnvironment(environment);
		warehouse.setServiceContent(serviceContent);
		warehouse.setContact(contact);
		warehouse.setPhone(phone);
		warehouse.setRelDate(new Date());
		warehouse.setRemarks(remarks);
		warehouse.setCarrierId(carrierId);
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			warehouse.setDetailPrice(fileLocation);
		}
		 warehouseDao.update(warehouse);// ����ʵ��
		 return true;
	}
	public boolean deleteWarehouse(String id){
		warehouse = getWarehouseInfo(id);// ����id���ҵ��ֿ���Ϣ
		warehouseDao.delete(warehouse);
		return true;
	}

	/**
	 * ��Դ��-�ֿ�ɸѡ
	 */
	@Override
	public JSONArray getSelectedWarehouseNew(WarehouseSearchBean warehouseBean,
			PageUtil pageUtil, HttpSession session) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		Map<String,Object> params=new HashMap<String,Object>();
			String sql = "select t1.id,"
				+ "t1.carrierId,"
				+ "t1.name,"
				+ "t1.companyName,"
				+ "t1.fireRate,"
				+ "t1.type,"
				+ "t1.houseArea,"
				+ "t1.relDate,"
				+ "t3.status "
				+ " from warehouse_carrier_view t1 "
				+ "left join ("
				+ "select * from focus t2 ";
				
		if(userId!=null){//�����ǰ���û���¼�������м����û���Ϣ
			sql+=" where t2.focusType='warehouse' and t2.clientId=:clientId ";
			params.put("clientId", userId);
		}
		sql+=") t3 on t1.id=t3.focusId ";
		String wheresql=whereSql(warehouseBean,params);
		sql+=wheresql;
		
		JSONArray jsonArray = new JSONArray();
		int page=pageUtil.getCurrentPage()==0?1:pageUtil.getCurrentPage();
		int display=pageUtil.getDisplay()==0?10:pageUtil.getDisplay();
		List<Object[]> objectList=warehouseDao.findBySql(sql, params,page,display);
		
		List<WarehouseSearchBean> warehouseList=new ArrayList<WarehouseSearchBean>();
		for(Iterator<Object[]> it=objectList.iterator();it.hasNext();){
			WarehouseSearchBean instanceBean=new WarehouseSearchBean();
			Object[] obj=it.next();
			instanceBean.setId((String)obj[0]);
			instanceBean.setCarrierId((String)obj[1]);
			instanceBean.setName((String)obj[2]);;
			instanceBean.setCompanyName((String)obj[3]);;
			instanceBean.setFireRate((String)obj[4]);
			instanceBean.setType((String)obj[5]);
			instanceBean.setHouseArea((Float)obj[6]+"");
			instanceBean.setRelDate((Date)obj[7]);;
			instanceBean.setStatus((String)obj[8]);
			warehouseList.add(instanceBean);
		}
		
		for(int i=0;i<warehouseList.size();i++){
			JSONObject jsonObject=(JSONObject)JSONObject.toJSON(warehouseList.get(i));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	/**
	 * where sql
	 * @param warehouseBean
	 * @param params
	 * @return
	 */
	private String whereSql(WarehouseSearchBean warehouseBean,Map<String,Object> params){
		String wheresql=" where 1=1 ";
		if(warehouseBean.getCity()!=null && !warehouseBean.getCity().equals("���Ļ�ƴ��") && !warehouseBean.getCity().equals("All") && !warehouseBean.getCity().equals("")){
			wheresql+=" and t1.city=:city";
			params.put("city", warehouseBean.getCity());
		}
		if(warehouseBean.getType()!=null && !warehouseBean.getType().equals("") && !warehouseBean.getType().equals("All")&& !warehouseBean.getType().equals("")){
			String type=warehouseBean.getType();
			if(type.equals("��˰�ֿ�")){
				wheresql+=" and t1.type='��˰'";
			}
			if(type.equals("�Ǳ�˰�ֿ�")){
				wheresql+=" and t1.type='�Ǳ�˰'";
			}
		}
		if(warehouseBean.getStorageForm()!= null && !warehouseBean.getStorageForm().equals("") &&!warehouseBean.getStorageForm().equals("All")){
			String storageForm=warehouseBean.getStorageForm();
			if(storageForm.equals("��ͨ�ֿ�")){
				wheresql+=" and t1.storageForm='��ͨ'";
			}
			if(storageForm.equals("��زֿ�")){
				wheresql+=" and t1.storageForm='���'";
			}
			if(storageForm.equals("���²ֿ�")){
				wheresql+=" and t1.storageForm='����'";
			}
			if(storageForm.equals("¶��ֿ�")){
				wheresql+=" and t1.storageForm='¶��'";
			}
			if(storageForm.equals("Σ��Ʒ�ֿ�")){
				wheresql+=" and t1.storageForm='Σ��Ʒ'";
			}
		}
		if(warehouseBean.getHouseArea()!=null && !warehouseBean.getHouseArea().equals("") && !warehouseBean.getHouseArea().equals("All")){
			String houseArea=warehouseBean.getHouseArea();
			if (houseArea.equals("����1��ƽ����")) {
				wheresql+=" and t1.houseArea>=10000";
			}
			if (houseArea.equals("����2��ƽ����")) {
				wheresql+=" and t1.houseArea>=20000";
			}
			if (houseArea.equals("����5��ƽ����")) {
				wheresql+=" and t1.houseArea>=50000";
			}
		}
		
		return wheresql;
	}

	/**
	 * ��Դ��-�ֿ�ɸѡ�ܼ�¼��
	 */
	@Override
	public Integer getSelectedWarehouseTotalRows(
			WarehouseSearchBean warehouseBean) {
		Map<String,Object> params=new HashMap<String,Object>();
		String hql="select count(*) from WarehouseCarrierView t1 "+whereSql(warehouseBean, params);
		Long count=warehouseDao.count(hql, params);
		
		return count.intValue();
	}
	
}
