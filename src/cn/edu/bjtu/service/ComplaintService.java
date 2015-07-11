package cn.edu.bjtu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.bjtu.bean.page.ComplaintBean;
import cn.edu.bjtu.vo.Complaintform;

import com.alibaba.fastjson.JSONArray;

public interface ComplaintService {

	@Deprecated
	public List getUserCompliant(String userId);
	public List getAllUserCompliant();
	public Complaintform getComplaintById(String id);
	/*public boolean insertComplaint(String type, String theme,
			String content, String orderNum, String carrierId,String path,String fileName);*/
	public boolean insertComplaint(ComplaintBean complaintBean,String carrierId,String path,String fileName);
	public boolean doAcceptComplaint(String id, String feedback);
	public List getFindComplaint(String theme,int flag, String clientId);
	
	/**
	 * ��ȡ�û�Ͷ����
	 * @param session
	 * @return
	 */
	public Double getUserComplaintRateAjax(HttpSession session);
	
	/**
	 * ������Ϣ-�ҵ�Ͷ��
	 * @param session
	 * @return
	 */
	public JSONArray getUserComplaint(HttpSession session);
	
	/**
	 * ������Ϣ-�ҵ�Ͷ��-�ܼ�¼����
	 * @param session
	 * @return
	 */
	public Integer getUserComplaintTotalRows(HttpSession session);
}
