package cn.edu.bjtu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.ResponseDao;
import cn.edu.bjtu.service.ResponseService;
import cn.edu.bjtu.vo.Response;
@Service
@Transactional
/**
 * ����serviceʵ��
 * @author RussWest0
 * @date   2015��6��2�� ����11:08:09
 */
public class ResponseServiceImpl implements ResponseService{

	@Autowired
	ResponseDao responseDao;

	@Override
	/**
	 * ���ݻ���id�õ�������Ϣ
	 */
	public List<Response> getResponseListByGoodsId(String goodsId) {
		// TODO ���ݻ���id�õ�������Ϣ
		Map<String,Object> params=new HashMap<String ,Object>();
		String hql="from Response where goodsId=:goodsId";
		params.put("goodsId", goodsId);
		List<Response> respList=responseDao.find(hql, params);
		return respList;
	}

	@Override
	/**
	 * ����id���ص���������Ϣ
	 */
	public Response getResponseById(String responseId) {
		return responseDao.get(Response.class, responseId);
	}

	

	/**
	 * ȷ�Ϸ����������޸�����������Ϣ״̬
	 */
	@Override
	public boolean confirmResponse(String responseId, String carrierId,
			String goodsId) {
		//List<Response> confirmList=responseDao.find("from Response where ")
		Response confirmResp=responseDao.get(Response.class,responseId);
		if(confirmResp!=null){
			confirmResp.setStatus("��ȷ��");//�޸�ȷ�ϵķ�����¼
			responseDao.update(confirmResp);
		}
		
		String hql="from Response where goodsId=:goodsId and carrierId <>:carrierId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("carrierId", carrierId);
		params.put("goodsId", goodsId);
		
		List<Response> unconfirmRespList=responseDao.find(hql,params);
		//�޸ĸû�����Ϣ������������ϢΪ��ȡ��
		if(unconfirmRespList!=null){
			for(Response resp:unconfirmRespList){
				resp.setStatus("��ȡ��");
				responseDao.update(resp);
				
			}
		}
		
		
		return true;
	}
	
	
	
	
	
	

}
