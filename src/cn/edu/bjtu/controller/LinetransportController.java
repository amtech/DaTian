package cn.edu.bjtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CommentService;
import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.FocusService;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.util.UploadPath;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Comment;
import cn.edu.bjtu.vo.Linetransport;

@Controller
/**
 * ������ؿ�����
 * @author RussWest0
 *
 */
public class LinetransportController {

/*	@Autowired
	private Logger logger = Logger.getLogger(LinetransportController.class);*/
	
	
	@RequestMapping("/linetransport")
	/**
	 * �������и�����Ϣ
	 * @return
	 */
	public ModelAndView getAllLinetransport(@RequestParam int flag,
			@RequestParam(required = false) Integer Display,
			@RequestParam(required = false) Integer PageNow,
			HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		if (Display == null)
			Display = 10;// Ĭ�ϵ�ÿҳ��С
		if (PageNow == null)
			PageNow = 1;// Ĭ�ϵĵ�ǰҳ��

		if (flag == 0) {
			List linetransportList = linetransportService.getAllLinetransport(
					Display, PageNow);
			int count = linetransportService.getTotalRows("All", "All", "All",
					"All", "All");// ��ȡ�ܼ�¼��,����Ҫwhere�Ӿ䣬���Բ�������All
			
			/*String userId = (String) request.getSession().getAttribute(
					"userId");*/
			List focusList = focusService.getFocusList(userId,"linetransport");
			// System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);
			mv.addObject("focusList", focusList);
			mv.addObject("linetransportList", linetransportList);
			mv.setViewName("resource_list");
		} else if (flag == 1) {
			// �����sessionȡ��id����ѯָ����line
			
			if(userId==null)
			{
				mv.setViewName("login");
				return mv;
			}
			List linetransportList = linetransportService.getCompanyLine(
					userId, Display, PageNow);// ������������

			mv.addObject("linetransportList", linetransportList);
				mv.setViewName("mgmt_r_line");
		}
		return mv;
	}

	@RequestMapping(value = "/linetransportdetail", method = RequestMethod.GET)
	/**
	 * ��ȡ�ض��ĸ�����Ϣ
	 * @param linetransportid
	 * @return
	 */
	public ModelAndView getLinetransportInfo(
			@RequestParam("linetransportid") String linetransportid,
			@RequestParam("carrierId") String carrierId,
			@RequestParam("flag") int flag, HttpSession session) {
		Linetransport linetransportInfo = linetransportService
				.getLinetransportInfo(linetransportid);// ��Ҫ�ع�������һ����·��Ϣ
		mv.addObject("linetransportInfo", linetransportInfo);
		String userId = (String) session.getAttribute("userId");
		List focusList = focusService.getFocusList(userId,"linetransport");
		mv.addObject("focusList", focusList);
		if (flag == 0) {
			Carrierinfo carrierInfo = companyService.getCarrierInfo(carrierId);
			//�˴���Ҫ��ȡ�������������� 
			// add by RussWest0 at 2015��5��30��,����9:19:53 
			List<Comment> commentList=commentService.getLinetransportCommentById(linetransportid,carrierId);
			mv.addObject("commentList",commentList);
			mv.addObject("carrierInfo", carrierInfo);
			mv.setViewName("resource_detail1");
		} else if (flag == 1) {// ����
			mv.setViewName("mgmt_r_line4");
		} else if (flag == 2) {// ����
			mv.setViewName("mgmt_r_line3");

		}
		return mv;
	}

	@RequestMapping(value = { "linetransportselected", "searchResourceselected" })
	// ͬʱ������������
	/**              
	 * ���ظ��߷���ɸѡ����������Ϣ
	 * @param startPlace
	 * @param endPlace
	 * @param type
	 * @param startPlace
	 * @param refPrice
	 * @param Display
	 * @param PageNow
	 * @return
	 */
	public ModelAndView getSelectedLine(@RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam String type,
			@RequestParam String startPlace1, @RequestParam String refPrice,
			@RequestParam int Display, @RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List linetransportList = linetransportService.getSelectedLine(
				startPlace, endPlace, type, startPlace1, refPrice, Display,
				PageNow);
		int count = linetransportService.getTotalRows(startPlace, endPlace,
				type, startPlace1, refPrice);// ��ȡ�ܼ�¼��

		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		mv.addObject("linetransportList", linetransportList);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("resource_list");

		return mv;
	}

	@RequestMapping(value = "insertLine", method = RequestMethod.POST)
	/**
	 * ����������·
	 * @param lineName
	 * @param startPlace
	 * @param endPlace
	 * @param onWayTime
	 * @param type
	 * @param refPrice
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertLine(
			@RequestParam(required = false) MultipartFile file,// new add
			@RequestParam String lineName, @RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam int onWayTime,
			@RequestParam String type,
			@RequestParam float refPrice,// ȱ����ϸ���۲���
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute("userId");
		// ////////////////////////////////////////////////////////////////////////

		String path = null;
		String fileName = null;
		//System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getLinetransportPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("path+fileName+" + path + "-" + fileName);
			// //////////////////////////////////////////////////////////////////
		} 
		//û���ϴ��ļ������path �� filenNameĬ��Ϊnull
		boolean flag = linetransportService.insertLine(lineName, startPlace,
				endPlace, onWayTime, type, refPrice, remarks, carrierId, path,
				fileName);
		// �޸Ĵ˷���,������������
		if (flag == true) {
			try {
				response.sendRedirect("linetransport?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("linetransport������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_line");
		return mv;
	}

	@RequestMapping(value = "updateLine", method = RequestMethod.POST)
	/**
	 * ���¸�����Ϣ
	 * @param id
	 * @param lineName
	 * @param startPlace
	 * @param endPlace
	 * @param onWayTime
	 * @param type
	 * @param refPrice
	 * @param remarks
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateLine(@RequestParam MultipartFile file,
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String lineName, @RequestParam String startPlace,
			@RequestParam String endPlace, @RequestParam int onWayTime,
			@RequestParam String type,
			@RequestParam float refPrice,// ȱ����ϸ���۲���
			@RequestParam String remarks, HttpServletRequest request,
			HttpServletResponse response) {
		String carrierId = (String) request.getSession().getAttribute("userId");
		//////////////////////////////////////////////
		String path = null;
		String fileName = null;
		//System.out.println("file+"+file+"filename"+file.getOriginalFilename());//���ϴ��ļ����ǻ���ʾ��ֵ
		if (file.getSize() != 0)// ���ϴ��ļ������
		{
			path = UploadPath.getLinetransportPath();// ��ͬ�ĵط�ȡ��ͬ���ϴ�·��
			fileName = file.getOriginalFilename();
			fileName = carrierId + "_" + fileName;// �ļ���
			File targetFile = new File(path, fileName);
			try { // ���� �ļ�
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		//û���ϴ��ļ������path �� filenNameĬ��Ϊnull
		
		//////////////////////////////////////////////
		
		boolean flag = linetransportService.updateLine(id, lineName,
				startPlace, endPlace, onWayTime, type, refPrice, remarks,
				carrierId,path,fileName);//change
		if (flag == true) {
			try {
				response.sendRedirect("linetransport?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// �˴�Ӧ�ü�¼��־
				System.out.println("linetransport���º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_line");
		return mv;

	}

	@RequestMapping(value = "linetransportdelete", method = RequestMethod.GET)
	/**
	 * ɾ������
	 */
	public ModelAndView deleteLine(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		boolean flag = linetransportService.deleteLine(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("linetransport?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("linetransportɾ�����ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("mgmt_r_line");
		return mv;

	}

	@RequestMapping(value = "downloadlinedetailprice", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView downloadLineDetailPrice(@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request, HttpServletResponse response) {
		Linetransport linetransportInfo = linetransportService.getLinetransportInfo(id);
		try {
			String file = linetransportInfo.getDetailPrice();
			/*File tempFile =new File(file.trim());  	          
	        String fileName = tempFile.getName();  			*/
			InputStream is = new FileInputStream(file);
			response.reset(); // ��Ҫ�����response�еĻ�����Ϣ
			response.setHeader("Content-Disposition", "attachment; filename="
					+ file);
			javax.servlet.ServletOutputStream out = response.getOutputStream();
			byte[] content = new byte[1024];
			int length = 0;
			while ((length = is.read(content)) != -1) {
				out.write(content, 0, length);
			}
			out.write(content);
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("�ض���ʧ��");
			e.printStackTrace();
		}
		return mv;

	}
	@Autowired
	CommentService commentService;
	@Resource
	LinetransportService linetransportService;
	@Resource
	CompanyService companyService;
	@Autowired
	FocusService focusService;
	ModelAndView mv = new ModelAndView();
}
