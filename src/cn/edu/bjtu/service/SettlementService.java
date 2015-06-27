package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface SettlementService {
	

	/**
	 * ����ҳ��Ĳ�ѯ
	 * @param carrierId
	 * @param name
	 * @param display
	 * @param pageNow
	 * @return
	 */
	public List getFindSettlement(String carrierId, String name, int display, int pageNow);
	
	/*public int getFindSettlementTotalRows(String carrierId, String name, int display, int pageNow);*/

	public List getOrderStatement(String orderNum);
	
	/**
	 * �����û� �ѽ�����/��������flag=0�ѽ���  flag=1������
	 * @param session
	 */
	public  Float getUserSettlementMoney(HttpSession session,int flag);
	
	/**
	 * ��ȡ����list
	 * @param session
	 * @return
	 */
	public List getUserSettlementList(HttpSession session);
	
	
	
}
