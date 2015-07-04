package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.bean.search.CargoSearchBean;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.GoodsClientView;
import cn.edu.bjtu.vo.Goodsform;

import com.alibaba.fastjson.JSONArray;

public interface GoodsInfoService {
	
	public GoodsClientView getAllGoodsDetail(String id);
	public Goodsform getMyGoodsDetail(String id);
	@Deprecated
	public List getSelectedGoodsInfo(String startPlace, String endPlace, String transportType, int Display,int PageNow);
	@Deprecated
	public int getTotalRows(String startPlace, String endPlace, String transportType);
	
	public boolean insertGoods(String name,
			String type,
			float weight,
			String transportType,
			String transportReq,
			String startPlace,
			String endPlace,
			String damageReq,
			String VIPService,
			String oriented,
			String limitDate,
			String invoice,
			String remarks,
			String clientId,
			String path,
			String fileName);
	
	public boolean updateGoods(String id, String name,
			String type,
			float weight,
			String transportType,
			String transportReq,
			String startPlace,
			String endPlace,
			String damageReq,
			String VIPService,
			String oriented,
			String limitDate,
			String invoice,
			String remarks,
			String clientId,
			String path,
			String fileName);
	
	public boolean commitResponse(String goodsId,String remarks,String userId,String path,String fileName);
	
	public List getAllResponse(String carrierId);
	@Deprecated
	public List getUserGoodsInfo(String clientId);
	
	public boolean deleteGoods(String id);
	
	/**
	 * ȷ�Ϸ������޸Ļ���״̬
	 * @param goodsId
	 * @return
	 */
	public boolean confirmResponse(String goodsId);
	
	/**
	 * ��Դ��-����-ɸѡ
	 * @param cargoBean
	 * @param pageUtil
	 * @param session
	 * @return
	 */
	public JSONArray getSelectedCargoNew(CargoSearchBean cargoBean,PageUtil pageUtil,HttpSession session);
	
	/**
	 * ��Դ��-�����ܼ�¼��
	 * @param cargoBean
	 * @return
	 */
	public Integer getSelectedCargoTotalRows(CargoSearchBean cargoBean);
	
	/**
	 * �ҵ���Ϣ-������Ϣ
	 * @Title: getUserCargoResource 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: JSONArray 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����4:59:36
	 */
	public JSONArray getUserCargoResource(HttpSession session,PageUtil pageUtil);
	
	/**
	 * �ҵ���Ϣ-����in��
	 * @Title: getUserCargoTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����5:20:34
	 */
	public Integer getUserCargoTotalRows(HttpSession session);

}
