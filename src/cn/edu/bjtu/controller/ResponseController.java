
package cn.edu.bjtu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.GoodsInfoService;
import cn.edu.bjtu.service.ResponseService;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Response;

@Controller
/**
 * ������ؿ�����
 * @author RussWest0
 * @date   2015��6��2�� ����11:04:13
 */
public class ResponseController {
	
	@Autowired
	ResponseService responseService;
	@Autowired
	GoodsInfoService goodsInfoService;
	
	@Autowired
	CompanyService companyService;
	
	ModelAndView mv=new ModelAndView();
	
	/**
	 * �ҵĻ���-�鿴��������(δȷ��ǰ)
	 * @param goodsid
	 * @return
	 */
	@RequestMapping("viewResponseDetail")
	public ModelAndView viewResponseDetail(String goodsid){
		List<Response> respList=responseService.getResponseListByGoodsId(goodsid);
		
		mv.addObject("respList", respList);
		mv.setViewName("mgmt_r_cargo5a");
		return mv;
		
	}
	
	/**
	 * �ҵĻ���-�鿴��������(ȷ�Ϻ�)
	 * @param goodsid
	 * @return
	 */
	@RequestMapping("viewResponseDetailAfter")
	public ModelAndView viewResponseDetail2(String goodsid){
		List<Response> respList=responseService.getResponseListByGoodsId(goodsid);
		
		mv.addObject("respList", respList);
		mv.setViewName("mgmt_r_cargo5b");
		return mv;
		
	}
	
	/**
	 * �ҵĻ���-�鿴��������ȷ�ϣ�-�鿴
	 * @param goodsid
	 * @return
	 */
	@RequestMapping("viewResponseDetailInfo")
	public ModelAndView viewResponseDetailInfo(String responseid){
		
		Response response=responseService.getResponseById(responseid);
		
		mv.addObject("response", response);
		mv.setViewName("mgmt_r_cargo6b");
		
		return mv;
		
		
	}
	
	/**
	 * �ҵķ���-�鿴����
	 * @param goodsid
	 * @return
	 */
	@RequestMapping("viewResponseInfo")
	public ModelAndView viewResponse(String responseid){
		
		Response response=responseService.getResponseById(responseid);
		
		mv.addObject("response", response);
		mv.setViewName("mgmt_d_response3");
		
		return mv;
		
	}
	/**
	 * ��ȡ����ȷ�ϱ� 
	 * @param goodsid
	 * @param carrrierid
	 * @return
	 */
	@RequestMapping("getConfirmResponseForm")
	public ModelAndView getConfirmResponseForm(String goodsid,String carrrierid,String responseid){
		//��Ҫ׼�������������ļ���˵��
		Response response=responseService.getResponseById(responseid);
		
		mv.addObject("responseinfo", response);
		
		mv.setViewName("mgmt_r_cargo6a");
		return mv;
	}
	
	
	@RequestMapping("confirmResponse")
	public ModelAndView confirmResponse(String responseid,String carrierid,String goodsid){
		//��Ҫ�޸Ĵ���������Ϣ����������Ϊ��ȡ��
		
		//�������޸�״̬
		responseService.confirmResponse(responseid,carrierid,goodsid);//�޸�ȷ�Ϸ�����ϢΪ��ȷ�ϣ�����������ϢΪ��ȡ��״̬
		//������޸�״̬
		goodsInfoService.confirmResponse(goodsid);
		
		Carrierinfo carrierinfo=companyService.getCompanyById(carrierid);
		//ҳ������Ҫ���˷�id
		mv.addObject("carrierInfo", carrierinfo);
		
		mv.setViewName("mgmt_d_order_s2a");
		return mv;
	}

}
