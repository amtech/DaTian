package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.WarehouseDao;
import cn.edu.bjtu.service.WarehouseService;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Warehouse;

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
	@Resource
	BaseDao baseDao;
	@Resource
	HQLTool hqltool;

	@Override
	public List getAllWarehouse(int Display, int PageNow) {
		// TODO Auto-generated method stub
		return warehouseDao.getAllWarehouse(Display, PageNow);
	}

	@Override
	/**
	 * ����ɸѡ����
	 */
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
		// System.out.println("hql+" + sql);
		return warehouseDao.getSelectedWarehouse(sql, Display, PageNow);
	}

	@Override
	/**
	 * ��ȡ�ܼ�¼���� 
	 */
	public int getTotalRows(String city, String type, String storageForm,
			String houseArea) {
		// TODO Auto-generated method stub
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

		// System.out.println("hql+"+sql);
		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}

	@Override
	public Warehouse getWarehouseInfo(String Warehouseid) {
		// TODO Auto-generated method stub

		return warehouseDao.getWarehouseInfo(Warehouseid);
	}

	@Override
	public List getCompanyWarehouse(String carrierId) {
		// TODO Auto-generated method stub
		return warehouseDao.getCompanyWarehouse(carrierId);
	}

	@Override
	public boolean insertWarehouse(String name, String city, String address,
			String type, String kind, float houseArea, float yardArea,
			float height, String fireRate, String storageForm,
			String fireSecurity, String environment, String serviceContent,
			String contact, String phone, String remarks, String carrierId,String path,String fileName) {
		// TODO Auto-generated method stub
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
		 baseDao.save(warehouse);// ����ʵ��
		 return true;
	}

	@Override
	public boolean updateWarehouse(String id, String name, String city,
			String address, String type, String kind, float houseArea,
			float yardArea, float height, String fireRate, String storageForm,
			String fireSecurity, String environment, String serviceContent,
			String contact, String phone, String remarks, String carrierId,String path,String fileName) {
		// TODO Auto-generated method stub

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
		 baseDao.update(warehouse);// ����ʵ��
		 return true;
	}
	public boolean deleteWarehouse(String id){
		warehouse = getWarehouseInfo(id);// ����id���ҵ��ֿ���Ϣ
		baseDao.delete(warehouse);
		return true;
	}
}
