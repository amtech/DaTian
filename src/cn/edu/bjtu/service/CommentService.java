package cn.edu.bjtu.service;

import java.util.List;

import cn.edu.bjtu.vo.Comment;

/**
 * �������ҵ���߼�
 * @author RussWest0
 * @date   2015��5��23�� ����5:09:32
 */
public interface CommentService {
	/**
	 * �ύ����
	 * @param rate1
	 * @param rate2
	 * @param rate3
	 * @param rate4
	 * @param remarks
	 * @param userId
	 * @return
	 */
	public boolean commitComment(String rate1,String rate2,String rate3,String rate4,String remarks,String userId);

	/**
	 * ����carrierid�õ���˾��������
	 * @param userId
	 * @return
	 */
	public List<Comment> getLinetransportCommentById(String linetransportId,String userId);
	
	
}
