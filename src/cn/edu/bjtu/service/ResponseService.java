package cn.edu.bjtu.service;

import cn.edu.bjtu.vo.Response;

/**
 * ������ص�serivce
 * @author RussWest0
 * @date   2015��6��2�� ����11:06:13
 */
public interface ResponseService {

	/**
	 * ����id�õ���ͼʵ��
	 * @param responseId
	 * @return
	 */
	public Response getResponseById(String responseId);
}
