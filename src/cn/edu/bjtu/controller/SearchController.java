package cn.edu.bjtu.controller;

import javax.servlet.http.HttpSession;

import jxl.common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.SearchService;
import cn.edu.bjtu.util.PageUtil;

import com.alibaba.fastjson.JSONArray;

/**
 * ����������
 * 
 * @author RussWest0
 *
 */
@Controller
public class SearchController {

	@Autowired
	SearchService searchService;
	private static final Logger logger=Logger.getLogger(SearchController.class);
	ModelAndView mv = new ModelAndView();
	
	/**
	 * ��������
	 * @param search_content
	 * @param resource_kind
	 * @param pageUtil
	 * @param session
	 * @return
	 */
	@RequestMapping(value="searchResourceAjax",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getSearchResult(String search_content,String resource_kind,PageUtil pageUtil,HttpSession session){
		
		JSONArray jsonArray=new JSONArray();
		if(resource_kind.equals("��·")){
			jsonArray=searchService.getLineResourceByCityName(search_content, pageUtil, session);
		}else if(resource_kind.equals("����")){
			jsonArray=searchService.getCitylineResourceByName(search_content, pageUtil, session);
		}
		else if(resource_kind.equals("����")){
			jsonArray=searchService.getCarResourceByCarNum(search_content, pageUtil, session);
		}
		else if(resource_kind.equals("�ֿ�")){
			jsonArray=searchService.getWarehouseResourceByName(search_content, pageUtil, session);
		}
		else if(resource_kind.equals("��˾")){
			jsonArray=searchService.getCompanyResourceByCompanyName(search_content, pageUtil, session);
		}
		else if(resource_kind.equals("����")){
			try{
				
				jsonArray=searchService.getGoodsResourceByName(search_content, pageUtil, session);
				throw new Exception("������");
			}catch(Exception e){
				//logger.error(e.getStackTrace());
				e.printStackTrace();
			}
		}
		
		return jsonArray.toString();
		
		
	}
	

	/*@RequestMapping(value = "searchResource")
	@Deprecated
	public ModelAndView getSearchInfo(String resourceChoose,
			String searchContent,
			@RequestParam(required = false) Integer Display,
			@RequestParam(required = false) Integer PageNow) {
		if (Display == null)
			Display = 10;// Ĭ�ϵ�ÿҳ��С
		if (PageNow == null)
			PageNow = 1;// Ĭ�ϵĵ�ǰҳ��

		if (resourceChoose.equals("��·"))// ƥ���������
		{

			List resultList = new ArrayList();
			List resultListPart1 = searchService
					.getLineResourceByStartPlace(searchContent);

			List resultListPart2 = searchService
					.getLineResourceByEndPlace(searchContent);

			// �ϲ������

			resultList.addAll(resultListPart1);
			resultList.addAll(resultListPart2);


			mv.addObject("linetransportList", resultList);// ����Ҫ��linetransport��Ķ��ϣ���Ȼҳ����ʾ������
			mv.setViewName("resource_list");

		} else if (resourceChoose.equals("����"))// ƥ���������
		{
			//List resultList = new ArrayList();
			List resultList = searchService
					.getCitylineResourceByName(searchContent);

			mv.addObject("citylineList", resultList);// 
			mv.setViewName("resource_list2");
		} else if (resourceChoose.equals("����"))// ƥ���������
		{
			//List resultList = new ArrayList();
			List resultList = searchService
					.getGoodsResourceByName(searchContent);

			mv.addObject("goodsformInfo", resultList);// 
			mv.setViewName("resource_list6");
		} else if (resourceChoose.equals("��˾")) {// ƥ�䳵������
			List resultList = searchService
					.getCompanyResourceByCompanyName(searchContent);

			mv.addObject("companyList", resultList);// 
			mv.setViewName("resource_list5");
		} else if (resourceChoose.equals("����")) {// ƥ�䳵��
			List resultList = searchService
					.getCarResourceByCarNum(searchContent);

			mv.addObject("carList", resultList);//
			mv.setViewName("resource_list3");
		} else if (resourceChoose.equals("�ֿ�")) {// ƥ��ֿ�
			List resultList = searchService
					.getWarehouseResourceByName(searchContent);


			mv.addObject("warehouseList", resultList);
			mv.setViewName("resource_list4");
		}
		return mv;
	}*/
}
