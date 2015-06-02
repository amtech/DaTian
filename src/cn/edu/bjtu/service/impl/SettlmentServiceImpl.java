package cn.edu.bjtu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bjtu.dao.SettlementDao;
import cn.edu.bjtu.service.SettlementService;
import cn.edu.bjtu.util.HQLTool;
@Service("settlementServiceImpl")
@Transactional
public class SettlmentServiceImpl implements SettlementService{
	@Resource
	HQLTool hqltool;
	@Resource
	SettlementDao settlementDao;
	@Override
	public List getUserOrder(String userId) {
		// TODO Auto-generated method stub
		return settlementDao.getUserOrder(userId);
	}
	@Override
	public List getFindSettlement(String carrierId, String name, int display, int pageNow) {
		// TODO Auto-generated method stub
		String sql="from SettlementCarrierView where carrierId='"+carrierId+"' and ";
		System.out.println("name1="+name);
		if(name.equals("���˷����ƻ���˷���ͬ���")){
			//����ʱ�����Ǻ�ͬ����
			System.out.println("name2="+name);
			name = "";
			System.out.println("name3="+name);
		}
		//û��ʱ������
		sql+=" (companyName like '%"+name+"%' or contractId like '%"+name+"%')";
		return settlementDao.getFindSettlement(sql, display, pageNow);
	}
	@Override
	public int getFindSettlementTotalRows(String carrierId, String name, int display, int pageNow) {
		// TODO Auto-generated method stub
		String sql="from SettlementCarrierView where carrierId='"+carrierId+"' and ";
		if(name.equals("���˷����ƻ���˷���ͬ���")){
			//����ʱ�����Ǻ�ͬ����
			System.out.println("name2="+name);
			name = "";
			System.out.println("name3="+name);
		}
		//û��ʱ������
		sql+=" (companyName like '%"+name+"%' or contractId like '%"+name+"%')";
		return hqltool.getTotalRows(sql);// �����HQLToolʵ��ǧ�����Լ�new��������@Resource
	}

}
