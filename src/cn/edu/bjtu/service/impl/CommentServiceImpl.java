package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.CommentDao;
import cn.edu.bjtu.dao.OrderDao;
import cn.edu.bjtu.service.CommentService;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Comment;
import cn.edu.bjtu.vo.Orderform;

@Service
@Transactional
/**
 * ������ص�ҵ��ʵ����
 * @author RussWest0
 * @date   2015��5��23�� ����5:11:05
 */
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentDao commentDao;
	@Autowired
	OrderDao orderDao;
	
	@Override
	/**
	 * �ύ����,�޸Ķ���״̬
	 */
	public boolean commitComment(String rate1, String rate2, String rate3,
			String rate4, String remarks, String userId,String orderid) {
		// TODO Auto-generated method stub
		Comment comment=new Comment();
		comment.setId(IdCreator.createAssessId());
		comment.setRelDate(new Date());
		comment.setClientId(userId);
		comment.setServiceAttitude(rate1);
		comment.setTotalMoney(rate4);
		comment.setTransportEfficiency(rate2);
		comment.setCargoSafety(rate3);
		comment.setComment(remarks);
		
		//add by RussWest0 at 2015��6��7��,����4:25:06 
		Orderform order=orderDao.get(Orderform.class, orderid);
		comment.setLinetransportId(order.getLinetransportId());
		comment.setWarehouseId(order.getWarehouseId());
		comment.setCarrierId(order.getCarrierId());
		comment.setCitylineId(order.getCitylineId());
		comment.setOrderId(order.getId());
		
		if(order!= null){
			order.setState("�����");
			order.setCommentId(comment.getId());
			orderDao.update(order);
		}
		
		commentDao.save(comment);
		
		
		return true;
	}

	@Override
	/**
	 *���ݹ�˾id�͸���id�õ�����
	 */
	public List<Comment> getLinetransportCommentById(String linetransportId,String userId) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		String hql="from Comment where linetransportId=:linetransportId and carrierId=:carrierId";
		params.put("linetransportId", linetransportId);
		params.put("carrierId", userId);
		return commentDao.find(hql, params);
	}

	@Override
	public List<Comment> getCitylineCommentById(String citylineId, String userId) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		String hql="from Comment where citylineId=:citylineId and carrierId=:carrierId";
		params.put("citylineId", citylineId);
		params.put("carrierId", userId);
		return commentDao.find(hql, params);
	}

	@Override
	public List<Comment> getCarCommentById(String carId, String userId) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		String hql="from Comment where carId=:carId and carrierId=:carrierId";
		params.put("carId", carId);
		params.put("carrierId", userId);
		return commentDao.find(hql, params);
	}

	@Override
	public List<Comment> getWarehouseCommentById(String warehouseid,
			String userId) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		String hql="from Comment where warehouseid=:warehouseid and carrierId=:carrierId";
		params.put("warehouseid", warehouseid);
		params.put("carrierId", userId);
		return commentDao.find(hql, params);
	}

	/**
	 * ���ݶ���id�õ���������
	 */
	@Override
	public Comment getCommentByOrderId(String orderId) {
		// TODO Auto-generated method stub
		String hql="from Comment where orderId=:orderId";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("orderId", orderId);
		List<Comment> commentList=commentDao.find(hql, params);
		
		if(commentList!=null){
			return commentList.get(0);
		}
		return null;
	}
	
	/**
	 * �����û��ĺ�����
	 */
	@Override
	public Float getUserGoodCommentRateAjax(HttpSession session) {
		// TODO Auto-generated method stub
		return 0f;
	}
	
	
	
	
	
}
