package cn.edu.bjtu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.FocusDao;
import cn.edu.bjtu.dao.impl.BaseDaoImpl;
import cn.edu.bjtu.service.FocusService;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Focus;

@Service
@Transactional
public class FocusServiceImpl extends BaseDaoImpl<Focus> implements FocusService {
	
	@Resource
	HibernateTemplate ht;
	/*@Resource 
	BaseDao baseDao;*/
	@Autowired
	FocusDao focous;
	@Resource 
	Focus focus;
	@Autowired
	FocusDao focusDao;

	@Override
	/**
	  * ��ӹ�ע
	  */
	public boolean insertFocus(String clientId, String foucsType, String foucsId){
		
		focus.setId(IdCreator.createFocusId());
		focus.setClientId(clientId);
		focus.setFocusType(foucsType);
		focus.setFocusId(foucsId);
		focus.setStatus("��Ч");
		focusDao.save(focus);
		return true;
	}

	@Override
	/**
	  * �ж�ĳ����Ϣ�Ƿ��ѱ���ע 
	  */
	public List getFocusJudgement(String clientId, String focusType,
			String foucsId) {
		
		return focusDao.getFocusJudgement(clientId,focusType,foucsId);
	}

	@Override
	/**
	  * ɾ����ע 
	  */
	public boolean deleteFocus(String id){
		return focusDao.deleteFocus(id);
	}
	@Override
	/**
	 * ��ע�б��ȡ
	 */
	public List getFocusList(String clientId,String focusType) {
		
		return focusDao.getFocusList(clientId,focusType);
	}
	
	@Override
	/**
	 * �ҵĹ�ע�б��ȡ
	 */
	public List getAllFocusLine(String clientId){
		return focusDao.getAllFocusLine(clientId);
	}
	@Override
	public List getAllFocusCityline(String clientId){
		return focusDao.getAllFocusCityline(clientId);
	}
	@Override
	public List getAllFocusWarehouse(String clientId){
		return focusDao.getAllFocusWarehouse(clientId);
	}
	@Override
	public List getAllFocusCar(String clientId){
		return focusDao.getAllFocusCar(clientId);
	}
	@Override
	public List getAllFocusCompany(String clientId){
		return focusDao.getAllFocusCompany(clientId);
	}
	@Override
	public List getAllFocusGoods(String clientId){
		return focusDao.getAllFocusGoods(clientId);
	}
	
	@Override
	public List findFocusLine(String text,String clientId){
		String sql="from FocusLinetransportView ";
		if(text.equals("��ע����")){
			//����ʱ������Ͷ������
		}
		else sql+="where (startPlace like '%"+text+"%' or endPlace like '%"+text+"%') and clientId='"+clientId+"'";
		return focusDao.getFind(sql);
	}
	@Override
	public List findFocusCityline(String text,String clientId){
		String sql="from FocusCitylineView ";
		if(text.equals("��ע����")){
			//����ʱ������Ͷ������
		}
		else sql+="where name like '%"+text+"%' and clientId='"+clientId+"'";
		return focusDao.getFind(sql);
	}
	@Override
	public List findFocusWarehouse(String text,String clientId){
		String sql="from FocusWarehouseView ";
		if(text.equals("��ע����")){
			//����ʱ������Ͷ������
		}
		else sql+="where name like '%"+text+"%' and clientId='"+clientId+"'";
		return focusDao.getFind(sql);
	}
	@Override
	public List findFocusCar(String text,String clientId){
		String sql="from FocusCarView ";
		if(text.equals("��ע����")){
			//����ʱ������Ͷ������
		}
		else sql+="where carNum like '%"+text+"%' and clientId='"+clientId+"'";
		return focusDao.getFind(sql);
	}
	@Override
	public List findFocusCompany(String text,String clientId){
		String sql="from FocusCompanyView ";
		if(text.equals("��ע����")){
			//����ʱ������Ͷ������
		}
		else sql+="where companyName like '%"+text+"%' and clientId='"+clientId+"'";
		return focusDao.getFind(sql);
	}
	@Override
	public List findFocusGoods(String text,String clientId){
		String sql="from FocusGoodsView ";
		if(text.equals("��ע����")){
			//����ʱ������Ͷ������
		}
		else sql+="where name like '%"+text+"%' and clientId='"+clientId+"'";
		return focusDao.getFind(sql);
	}
	
	
}
