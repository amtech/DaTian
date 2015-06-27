package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.vo.Settlement;

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
	
	/**
	 * ���ݶ����Ż�ȡ�������ɼ�¼
	 * @param orderNum
	 * @return
	 */
	public List<Settlement> getSettlementRecordByOrderNum(String orderNum);

}
