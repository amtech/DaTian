package cn.edu.bjtu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginValidationFilter
 */
@WebFilter("/LoginValidationFilter")
/**   
 * ���ڼ���û��Ƿ��½�Ĺ����������δ��¼�����ض���ָ�ĵ�¼ҳ��    
 * ���ò���   
 * checkSessionKey ������� Session �б���Ĺؼ���    
 * redirectURL ����û�δ��¼�����ض���ָ����ҳ�棬URL������ ContextPath    
 * notCheckURLList ��������URL�б��Էֺŷֿ������� URL �в����� ContextPath   
 */
public class LoginValidationFilter implements Filter {

	protected FilterConfig filterConfig = null;
	private String redirectURL = null;
	private List notCheckURLList = new ArrayList();
	private String sessionKey = null;

	/**
	 * Default constructor.
	 */
	public LoginValidationFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		notCheckURLList.clear();
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		/*
		 * if(req.getSession().getAttribute("username").equals("") ||
		 * req.getSession().getAttribute("userId").equals("")) {
		 * res.sendRedirect("loginForm");//��ת����¼ҳ�� } else // pass the request
		 * along the filter chain chain.doFilter(request, response);
		 */
		/*HttpSession session = req.getSession();
		if (sessionKey == null) {
			chain.doFilter(req, res);
			return;
		}*/
		/*if ((!checkRequestURIIntNotFilterList(req))
				&& session.getAttribute(sessionKey) == null) {
			res.sendRedirect(redirectURL);
			return;
		}*/
		/*if(req.getSession().getAttribute("username").equals("") || 
				req.getSession().getAttribute("userId").equals(""))
		{
			res.sendRedirect("loginForm");
			return;
		}*/
		if(req.getSession().getAttribute("username")==null || req.getSession().getAttribute("userId")==null)
		{
			res.sendRedirect("loginForm");
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		filterConfig = fConfig;
		redirectURL = filterConfig.getInitParameter("redirectURL");
		sessionKey = filterConfig.getInitParameter("checkSessionKey");

		String notCheckURLListStr = filterConfig
				.getInitParameter("notCheckURLList");

		if (notCheckURLListStr != null) {
			StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");
			notCheckURLList.clear();
			while (st.hasMoreTokens()) {
				notCheckURLList.add(st.nextToken());
			}
		}
	}

	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		/*String uri = request.getServletPath()
				+ (request.getPathInfo() == null ? "" : request.getPathInfo());*/
		String uri=request.getPathInfo()+"";
		return notCheckURLList.contains(uri);
	}

}
