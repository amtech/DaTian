package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.bean.page.FocusBean;
import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.vo.Focus;

import com.alibaba.fastjson.JSONArray;

public interface FocusService extends BaseDao<Focus>{
	public boolean insertFocus(String clientId, String foucsType, String foucsId);
	public List getFocusJudgement(String clientId, String foucsType, String foucsId);
	public boolean deleteFocus(String id);
	public List getFocusList(String clientId,String focusType);

	public List getAllFocusLine(String clientId);
	public List getAllFocusCityline(String clientId);
	public List getAllFocusWarehouse(String clientId);
	public List getAllFocusCar(String clientId);
	public List getAllFocusCompany(String clientId);
	public List getAllFocusGoods(String clientId);
	
	public List findFocusLine(String text,String clientId);
	public List findFocusCityline(String text,String clientId);
	public List findFocusWarehouse(String text,String clientId);
	public List findFocusCar(String text,String clientId);
	public List findFocusCompany(String text,String clientId);
	public List findFocusGoods(String text,String clientId);
	
	/**
	 * ������ע
	 * @Title: searchFocus 
	 *  
	 * @param: @param search_content
	 * @param: @param session
	 * @param: @return 
	 * @return: JSONArray 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��6�� ����12:20:37
	 */
	public JSONArray searchFocus(FocusBean focusBean,HttpSession session);
	
	

}
