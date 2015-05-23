package cn.edu.bjtu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.SearchService;

/**
 * ����������
 * 
 * @author RussWest0
 *
 */
@Controller
public class SearchController {

	@Autowired
	SearchService searchServiceImpl;
	ModelAndView mv = new ModelAndView();

	@RequestMapping(value = "searchResource", method = RequestMethod.POST)
	public ModelAndView getSearchInfo(String resourceChoose,
			String searchContent,
			@RequestParam(required = false) Integer Display,
			@RequestParam(required = false) Integer PageNow) {
		// System.out.println("�������������� ");
		// System.out.println("resourcechoose+" + resourceChoose);
		 //System.out.println("resourcecontent+"+searchContent);
		if (Display == null)
			Display = 10;// Ĭ�ϵ�ÿҳ��С
		if (PageNow == null)
			PageNow = 1;// Ĭ�ϵĵ�ǰҳ��

		if (resourceChoose.equals("��·"))// ƥ���������
		{

			System.out.println("������·");
			List resultList = new ArrayList();
			List resultListPart1 = searchServiceImpl
					.getLineResourceByStartPlace(searchContent);

			List resultListPart2 = searchServiceImpl
					.getLineResourceByEndPlace(searchContent);

			System.out.println("listpart1+" + resultListPart1.size()
					+ "+listpart2+" + resultListPart2.size());
			// �ϲ������

			resultList.addAll(resultListPart1);
			resultList.addAll(resultListPart2);

			/*int count = resultList.size();
			// System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);*/

			mv.addObject("linetransportList", resultList);// ����Ҫ��linetransport��Ķ��ϣ���Ȼҳ����ʾ������
			mv.setViewName("resource_list");

		} else if (resourceChoose.equals("����"))// ƥ���������
		{
			System.out.println("�����������");
			//List resultList = new ArrayList();
			List resultList = searchServiceImpl
					.getCitylineResourceByName(searchContent);

			//List resultListPart2 = searchServiceImpl.getLineResourceByEndPlace(searchContent);

			System.out.println("list+" + resultList.size());
			//		+ "+listpart2+" + resultListPart2.size());
			// �ϲ������

			//resultList.addAll(resultListPart1);
			//resultList.addAll(resultListPart2);

			/*int count = resultList.size();
			// System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);*/

			mv.addObject("citylineList", resultList);// ����Ҫ��linetransport��Ķ��ϣ���Ȼҳ����ʾ������
			mv.setViewName("resource_list2");
		} else if (resourceChoose.equals("����"))// ƥ���������
		{
			System.out.println("�������");
			//List resultList = new ArrayList();
			List resultList = searchServiceImpl
					.getGoodsResourceByName(searchContent);

			//List resultListPart2 = searchServiceImpl.getLineResourceByEndPlace(searchContent);

			System.out.println("list+" + resultList.size());
			//		+ "+listpart2+" + resultListPart2.size());
			// �ϲ������

			//resultList.addAll(resultListPart1);
			//resultList.addAll(resultListPart2);

			/*int count = resultList.size();
			// System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);*/

			mv.addObject("goodsformInfo", resultList);// ����Ҫ��linetransport��Ķ��ϣ���Ȼҳ����ʾ������
			mv.setViewName("resource_list6");
		} else if (resourceChoose.equals("��˾")) {// ƥ�䳵������
			System.out.println("���빫˾");
			//List resultList = new ArrayList();
			List resultList = searchServiceImpl
					.getCompanyResourceByCompanyName(searchContent);

			//List resultListPart2 = searchServiceImpl.getLineResourceByEndPlace(searchContent);

			System.out.println("list+" + resultList.size());
			//		+ "+listpart2+" + resultListPart2.size());
			// �ϲ������

			//resultList.addAll(resultListPart1);
			//resultList.addAll(resultListPart2);

			/*int count = resultList.size();
			// System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);*/

			mv.addObject("companyList", resultList);// ����Ҫ��linetransport��Ķ��ϣ���Ȼҳ����ʾ������
			mv.setViewName("resource_list5");
		} else if (resourceChoose.equals("����")) {// ƥ�䳵��
			System.out.println("���복��");
			//List resultList = new ArrayList();
			List resultList = searchServiceImpl
					.getCarResourceByCarNum(searchContent);

			//List resultListPart2 = searchServiceImpl.getLineResourceByEndPlace(searchContent);

			System.out.println("list+" + resultList.size());
			//		+ "+listpart2+" + resultListPart2.size());
			// �ϲ������

			//resultList.addAll(resultListPart1);
			//resultList.addAll(resultListPart2);

			/*int count = resultList.size();
			// System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);*/

			mv.addObject("carList", resultList);// ����Ҫ��linetransport��Ķ��ϣ���Ȼҳ����ʾ������
			mv.setViewName("resource_list3");
		} else if (resourceChoose.equals("�ֿ�")) {// ƥ��ֿ�
			System.out.println("����ֿ�");
			//List resultList = new ArrayList();
			List resultList = searchServiceImpl
					.getWarehouseResourceByName(searchContent);

			//List resultListPart2 = searchServiceImpl.getLineResourceByEndPlace(searchContent);

			System.out.println("list+" + resultList.size());
			//		+ "+listpart2+" + resultListPart2.size());
			// �ϲ������

			//resultList.addAll(resultListPart1);
			//resultList.addAll(resultListPart2);

			/*int count = resultList.size();
			// System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);*/

			mv.addObject("warehouseList", resultList);// ����Ҫ��linetransport��Ķ��ϣ���Ȼҳ����ʾ������
			mv.setViewName("resource_list4");
		}

		// mv.setViewName("");
		return mv;
	}
}
