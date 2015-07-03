package cn.edu.bjtu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.BusinessClientDao;
import cn.edu.bjtu.service.BusinessClientService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.vo.Businessclient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Service
@Transactional
public class BusinessClientServiceImpl implements BusinessClientService{

	@Autowired
	BusinessClientDao businessClientDao;
	/**
	 * ��ȡ�õ������ͻ�
	 */
	@Override
	public JSONArray getUserBusinessClient(HttpSession session) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		Map<String,Object> params=new HashMap<String,Object>();
		String hql="from Businessclient t where t.carrierId=:userId";
		params.put("userId", userId);
		List<Businessclient> clientList=businessClientDao.find(hql, params);
		
		JSONArray jsonArray=new JSONArray();
		if(clientList!=null && clientList.size()>0){
			for(int i=0;i<clientList.size();i++){
				JSONObject jsonObject=(JSONObject)JSONObject.toJSON(clientList.get(i));
				jsonArray.add(jsonObject);
			}
			
		}
		
		return jsonArray;
	}
	
	/**
	 * �ҵ���Ϣ-�ͻ���Ϣ-�ܼ�¼��
	 */
	@Override
	public Integer getUserBusinessClientTotalRows(HttpSession session) {
		String userId=(String)session.getAttribute(Constant.USER_ID);
		Map<String,Object> params=new HashMap<String,Object>();
		String hql="select count(*) from Businessclient t where t.carrierId=:userId";
		params.put("userId", userId);
		Long count=businessClientDao.count(hql, params);
		return count.intValue();
	}

}
