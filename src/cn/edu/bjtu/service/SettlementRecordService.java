package cn.edu.bjtu.service;

import javax.servlet.http.HttpSession;

/**
 * ���ڼ�¼���ɶ��˵���Ϣ
 * @author RussWest0
 * @date   2015��6��20�� ����7:11:59
 */
public interface SettlementRecordService {
	/**
	 * �޸Ķ���״̬Ϊ�ѽ��㣬����¼������
	 * @param orderNum
	 * @param session
	 * @return
	 */
	public boolean finishSettlement(String orderNum,HttpSession session);

}
