package cn.edu.bjtu.service;

import java.util.List;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.vo.Response;

/**
 * ������ص�serivce
 * @author RussWest0
 * @date   2015��6��2�� ����11:06:13
 */
public interface ResponseService{

	/**
	 * ����id�õ���ͼʵ��,�����ҵķ���ҳ���ϵ��
	 * @param responseId
	 * @return
	 */
//	public Response getResponseById(String responseId);
	
	/**
	 * ���ݻ���id�õ����ﷴ����Ϣ
	 * @param goodsId
	 * @return
	 */
	public List<Response> getResponseListByGoodsId(String goodsId);
	
	/**
	 * ����id�õ�����������Ϣ
	 * @param responseId
	 * @return
	 */
	public Response getResponseById(String responseId);
	
	/**
	 * ȷ�Ϸ������޸�״̬
	 * @param responseId
	 * @return
	 */
	public boolean confirmResponse(String responseId,String carrierId,String goodsId);
}
