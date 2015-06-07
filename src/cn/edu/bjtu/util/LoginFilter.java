package cn.edu.bjtu.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * ��¼����
 * 
 * @author RussWest0
 * @date 2015��6��2�� ����8:36:01
 */
public class LoginFilter extends OncePerRequestFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//System.out.println("���ڹ���login");
		// �����˵�uri
		String[] notFilter = new String[] { "login", "loginForm", "register","adminLogin" ,
				"js" ,"images", "css", "Focus", "foucs", 
				"focusNum", "homepage", "linetransport", "footer", "cityline",
				"car", "warehouse", "company", "goodsform", "allcomplaint",
				"city"
		};

		// �����uri
		String uri = request.getRequestURI();
		//�ոս�����ҳ�����
		if(uri.equals("/DaTian/")){
			filterChain.doFilter(request, response);
			return;
		}
		// �Ƿ����
		boolean doFilter = true;
		for (String s : notFilter) {
			if (uri.indexOf(s) != -1) {
				// ���uri�а��������˵�uri���򲻽��й���
				doFilter = false;
				break;
			}
		}
		if(doFilter == true)
			System.out.println(">>>>" + uri + ">>>>" + doFilter);
		//filterChain.doFilter(request, response);
		if (doFilter) {
			// ִ�й���
			// ��session�л�ȡ��¼��ʵ��
			Object obj = request.getSession().getAttribute(Constant.USER_NAME);
			if (null == obj) {
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				//��ת����¼ҳ��
				response.sendRedirect("loginForm");
				return;
			} else {
				// ���session�д��ڵ�¼��ʵ�壬�����
				filterChain.doFilter(request, response);
			}
		} else {
			// �����ִ�й��ˣ������
			Object obj = request.getSession().getAttribute(Constant.USER_NAME);
			//System.out.println(request.getQueryString());
			if(request.getQueryString()!=null && request.getQueryString().indexOf("flag=1") != -1 && obj == null)//�û�δ��¼��������ʸ�����Ϣ
			{
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				//��ת����¼ҳ��
				response.sendRedirect("loginForm");
				return;
			}
			filterChain.doFilter(request, response);
		}
	}

}
