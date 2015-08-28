package cn.edu.bjtu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.LinetransportDao;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.PageUtil;
import cn.edu.bjtu.vo.Linetransport;

@Repository
public class LinetransportDaoImpl extends BaseDaoImpl<Linetransport> implements LinetransportDao {

	@Resource
	private HibernateTemplate ht;

	@Override
	/**
	 * ���ؾ��������Ϣ
	 */
	public Linetransport getLinetransportInfo(String linetransportid) {
		
		return ht.get(Linetransport.class, linetransportid);
	}

	
	

}
