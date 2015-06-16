package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.bean.page.ComplaintBean;
import cn.edu.bjtu.dao.ComplaintDao;
import cn.edu.bjtu.service.ComplaintService;
import cn.edu.bjtu.service.OrderService;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Complaintform;

@Service("complaintServiceImpl")
@Transactional
public class ComplaintServiceImpl implements ComplaintService {

	@Resource
	ComplaintDao complaintDao;
	@Resource
	Complaintform complaintform;
	@Resource
	OrderService orderService;

	@Override
	public List getUserCompliant(String userId) {
		// TODO Auto-generated method stub
		return complaintDao.getUserCompliant(userId);
	}

	@Override
	public List getAllUserCompliant() {

		return complaintDao.getAllUserCompliant();
	}

	@Override
	public Complaintform getComplaintById(String id) {

		return complaintDao.get(Complaintform.class,id);
	}

	@Override
	/**
	 * ����Ͷ��
	 */
	public boolean insertComplaint(ComplaintBean complaintBean, String carrierId, String path, String fileName) {
		// TODO Auto-generated method stub

		complaintform.setId(IdCreator.createCityLineId());
		complaintform.setType(complaintBean.getType());
		complaintform.setTheme(complaintBean.getTheme());
		complaintform.setContent(complaintBean.getContent());
		complaintform.setOrderNum(complaintBean.getOrderNum());
		
		complaintform.setCarrierId(carrierId);
		complaintform.setClientId(carrierId);
		complaintform.setRelDate(new Date());
		// add by RussWest0 at 2015��5��30��,����10:05:22
		
		// �����ļ�·��
		if (path != null && fileName != null) {
			String fileLocation = path + "//" + fileName;
			complaintform.setRelatedMaterial(fileLocation);
		}

		complaintform.setState("������");
		complaintDao.save(complaintform);
		return true;

	}

	@Override
	public boolean doAcceptComplaint(String id, String feedback) {

		complaintform = getComplaintById(id);
		complaintform.setFeedback(feedback);
		complaintform.setState("������");
		complaintDao.update(complaintform);
		return true;
	}

	@Override
	public List getFindComplaint(String theme, int flag, String clientId) {
		String sql = "from ComplaintClientView ";
		if (flag == 0) {
			if (theme.equals("Ͷ������")) {
				// ����ʱ������Ͷ������
			} else
				sql += "where theme like '%" + theme + "%' ";

		} else if (flag == 1) {
			if (theme.equals("Ͷ������")) {
				// ����ʱ������Ͷ������
			} else
				sql += "where theme like '%" + theme + "%' and clientId='"
						+ clientId + "'";

		}
		return complaintDao.getFindComplaint(sql);
	}
}
