
package cn.edu.bjtu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.GoodsInfoService;
import cn.edu.bjtu.service.ResponseService;
import cn.edu.bjtu.vo.Goodsform;
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
	
	ModelAndView mv=new ModelAndView();
	
	/**
	 * �鿴��������
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
	
	//�����ύ����ҳ��֮��δ���...........
	public ModelAndView confirmResponse(String responseid,String carrierid,String goodsid){
		//��Ҫ�޸Ĵ���������Ϣ����������Ϊ��ȡ��
		
		//�������޸�״̬
		responseService.confirmResponse(responseid,carrierid,goodsid);//�޸�ȷ�Ϸ�����ϢΪ��ȷ�ϣ�����������ϢΪ��ȡ��״̬
		//������޸�״̬
		goodsInfoService.confirmResponse(goodsid);
		
		
		mv.addObject("", "");
		
		mv.setViewName("mgmt_d_order_s2a");
		return mv;
	}

}
