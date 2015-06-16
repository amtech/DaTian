package cn.edu.bjtu.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.bjtu.dao.CompanyDao;
import cn.edu.bjtu.dao.GoodsInfoDao;
import cn.edu.bjtu.dao.ResponseDao;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.GoodsClientView;
import cn.edu.bjtu.vo.Goodsform;
import cn.edu.bjtu.vo.Response;

@Repository
public class GoodsInfoDaoImpl extends BaseDaoImpl<Goodsform> implements GoodsInfoDao {

	@Resource
	private HibernateTemplate ht;
	@Resource
	private HQLTool hqltool;
	
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ResponseDao responseDao;

	/*@Resource
	private BaseDao baseDao;*/
	/*@Autowired
	GoodsInfoDao goodsInfoDao;*/


	@Override
	public GoodsClientView getAllGoodsDetail(String id) {
		// TODO Auto-generated method stub

		return ht.get(GoodsClientView.class, id);

	}

	@Override
	public Goodsform getMyGoodsDetail(String id) {
		// TODO Auto-generated method stub

		return this.get(Goodsform.class, id);

	}

	@Override
	@Deprecated
	public List getSelectedGoodsInfo(String hql, int display, int pageNow) {
		// TODO Auto-generated method stub
		int page = pageNow;
		int pageSize = display;

		return hqltool.getQueryList(hql, page, pageSize);// Dao���ҳ������ȡ���˷���
	}

	@Override
	/**
	 * �ύ����Dao
	 */
	public boolean commitResponse(String goodsId, String remarks, String userId,String path,String fileName) {
		// TODO Auto-generated method stub
		Goodsform goods = this.get(Goodsform.class, goodsId);
		String clientId="";
		String committer="";
		String phone="";
		if(goods!=null){
			clientId=goods.getClientId();
		}
		Carrierinfo carrier=companyDao.getCarrierInfo(userId);
		if(carrier!=null){
			committer=carrier.getCompanyContact();
			phone=carrier.getPhone();
		}
		//goods.setRemarks(remarks);
		int feedBackQuantity=goods.getFeedbackQuantity();
		feedBackQuantity++;//����������1
		goods.setFeedbackQuantity(feedBackQuantity);
		this.update(goods);
		//�˴�����Ҫ��¼����������
		Response response=new Response();
		response.setId(IdCreator.createResponseId());
		response.setCarrierId(userId);
		response.setClientId(clientId);
		response.setCommitter(committer);
		response.setGoodsId(goodsId);
		response.setPhone(phone);
		response.setRemarks(remarks);
		response.setRelDate(new Date());
		response.setStatus("��ȷ��");//add by RussWest0 at 2015��6��6��,����3:07:08 
		// �����ļ�·��
				if (path != null && fileName != null) {
					String fileLocation = path + "//" + fileName;
					response.setRelatedMaterial(fileLocation);
				}
		//���淴����Ϣ
		responseDao.save(response);
		
		return true;
	}

	@Override
	public List getAllResponse(String carrierId) {
		// TODO Auto-generated method stub
		//return ht.find("from Goodsform where clientId='" + carrierId + "'");
		return this.find("from GoodsResponseView where carrierId='"+carrierId+"'");
	}

	@Override
	public List getUserGoodsInfo(String clientId) {
		// TODO Auto-generated method stub
		return ht.find("from Goodsform where clientId='" + clientId + "'");
	}

	@Override
	public boolean deleteGoods(String id) {

		Goodsform goodsform = ht.get(Goodsform.class, id);
		this.delete(goodsform);
		return true;
	}

}
