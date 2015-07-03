package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.bean.search.LinetransportSearchBean;
import cn.edu.bjtu.util.DataModel;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Linetransport;

import com.alibaba.fastjson.JSONArray;

public interface LinetransportService {

	public List getAllLinetransport(int Display,int PageNow);

	public Linetransport getLinetransportInfo(String linetransportid);

	@Deprecated
	public List getSelectedLine(String startPlace, String endPlace,
			String type, String startPlace1, String refPrice,int Display,int PageNow);

	public boolean insertLine(String lineName, String startPlace,
			String endPlace, int onWayTime, String type, float refPrice,
			String remarks, String carrierId,String path,String fileName);

	public List getCompanyLine(String carrierId,int Display,int PageNow);

	public String getLinetransportIdByCity(String startPlace, String endPlace);

	public boolean updateLine(String id, String lineName, String startPlace,
			String endPlace, int onWayTime, String type, float refPrice,
			String remarks, String carrierId,String path,String fileName);
	@Deprecated
	public int getTotalRows(String startPlace, String endPlace,
			String type, String startPlace1, String refPrice);
	@Deprecated
	public int getCompanyTotalRows(String carrierId);
	
	public boolean deleteLine(String id);

	
	/**
	 * ��Դ����ȡɸѡ��ĳ���������Դ
	 * @param linetransportbean
	 * @param page
	 * @param session
	 * @return
	 */
	public DataModel getSelectedLineNew(LinetransportSearchBean linetransportbean,
			PageUtil page,HttpSession session);
	
	
	/**
	 * ��Դ��ɸѡ������
	 * @param lineBean
	 * @return
	 */
	public Integer getSelectedLineTotalRows(LinetransportSearchBean lineBean);
	
	/**
	 * �ҵ���Ϣ-�ҵĸ�����Դ
	 * @param session
	 * @param pageUtil
	 * @return
	 */
	public JSONArray getUserLinetransportResource(HttpSession session,PageUtil pageUtil);
	/**
	 * �ҵ���Ϣ-������Դ-�ܼ�¼��
	 * @Title: getUserLinetransportResourceTotalRows 
	 *  
	 * @param: @param session
	 * @param: @return 
	 * @return: Integer 
	 * @throws: �쳣 
	 * @author: chendonghao 
	 * @date: 2015��7��3�� ����9:43:48
	 */
	public Integer getUserLinetransportResourceTotalRows(HttpSession session);

	
}
