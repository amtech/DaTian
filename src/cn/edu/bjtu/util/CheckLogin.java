package cn.edu.bjtu.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * �ж�session���Ƿ���userid�������ж��û��Ƿ���� 
 * @author RussWest0
 *
 */
//��δʹ��
public class CheckLogin {

	public static boolean checkLogin(HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getSession().getAttribute("username").equals("")
				|| request.getSession().getAttribute("userId").equals(""))
		{
			try {
				response.sendRedirect("loginForm");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("��¼��鲿�ֳ���");
				e.printStackTrace();
			}
			return false;
		}
			
		else 
			return true;

	}
}
