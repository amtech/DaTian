package cn.edu.bjtu.service;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;

/**
 *  �����ͻ����
 * @author RussWest0
 * @date   2015��6��22�� ����7:27:09
 */
public interface BusinessClientService {

	/**
	 * ��ȡ�û��������ͻ�
	 * @return
	 */
	public JSONArray getUserBusinessClient(HttpSession session);
}
