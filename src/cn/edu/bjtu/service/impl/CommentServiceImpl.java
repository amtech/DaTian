package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.CommentDao;
import cn.edu.bjtu.service.CommentService;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Comment;

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
	
	@Override
	/**
	 * �ύ����
	 */
	public boolean commitComment(String rate1, String rate2, String rate3,
			String rate4, String remarks, String userId) {
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
	
	
	
}
