package cn.edu.bjtu.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.CarTeamDao;
import cn.edu.bjtu.vo.Carteam;
@Repository
public class CarTeamDaoImpl extends BaseDaoImpl<Carteam> implements CarTeamDao{
	
	@Override
	/**
	 * ���س�����Ϣ
	 */
	public List getCarteam(String carrierId) {
		// TODO Auto-generated method stub
		return this.find("from Carteam where carrierId='"+carrierId+"'");
	}
	
	@Override
	/**
	 * ���ؾ��峵����Ϣ
	 */
	public Carteam getCarteamInfo(String id) {
		// TODO Auto-generated method stub
		return this.get(Carteam.class,id);
	}
}
