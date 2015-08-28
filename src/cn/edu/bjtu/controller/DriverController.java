package cn.edu.bjtu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.DriverService;
import cn.edu.bjtu.vo.Driverinfo;

/**
 * ˾����صĿ�����
 * @author russwest
 * @date   2015��8��28�� ����2:05:14
 */
@Controller
public class DriverController {
	@Autowired
	DriverService driverService;
	
	ModelAndView mv = new ModelAndView();
	/**
	 * ��ȡ˾���б�
	 * @param flag
	 * @return
	 */
	@RequestMapping("driver")
	@Deprecated
	public ModelAndView getAllDriver(@RequestParam int flag,
			HttpServletRequest request, HttpServletResponse response) {
		// ��session��ȡ��id��ѯ
		if (flag == 0) {// ���е�˾����Ϣ
			List driverList = driverService.getAllDriver();
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_driver");
		} else if (flag == 1) {// ��˾˾���б�
			// ������sessionȡid
			String carrierId = (String) request.getSession().getAttribute(
					"userId");
			// String carrierId = "C-0002";// ɾ��
			List driverList = driverService.getCompanyDriver(carrierId);
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_driver");
		}

		return mv;
	}

	/**
	 * ˾����Ϣ����
	 * @param driverId
	 * @param flag
	 * @return
	 */
	@RequestMapping("driverdetail")
	public ModelAndView getDriverInfo(@RequestParam String driverId,
			@RequestParam int flag) {
		Driverinfo driver = driverService.getDriverInfo(driverId);
		mv.addObject("driver", driver);
		if (flag == 1) {// ��Ӧ˾������
			mv.setViewName("mgmt_r_driver4");
		} else if (flag == 2)// ��Ӧ˾������
		{
			mv.setViewName("mgmt_r_driver3");
		}

		return mv;
	}
}
