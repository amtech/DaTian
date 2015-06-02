package cn.edu.bjtu.service.impl;

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
	 * ����id�õ�responseʵ��
	 */
	public Response getResponseById(String responseId) {
		// TODO Auto-generated method stub
		
		return responseDao.get(Response.class, responseId);
	}
	
	

}
