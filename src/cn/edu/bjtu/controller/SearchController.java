package cn.edu.bjtu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.SearchService;

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
	ModelAndView mv = new ModelAndView();
	
	@RequestMapping("searchResourceAjax")
	public String getSearchResult(String search_content,String resource_kind,int display,int currentPage){
		
		JSONArray jsonArray=new JSONArray();
		
		if(resource_kind.equals("��·")){
			
		}else if(resource_kind.equals("����")){
			
		}
		else if(resource_kind.equals("����")){
			
		}
		
		else if(resource_kind.equals("�ֿ�")){
	
		}

		else if(resource_kind.equals("��˾")){
	
		}
		else if(resource_kind.equals("����")){
			
		}
		
		return "";
		
		
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
