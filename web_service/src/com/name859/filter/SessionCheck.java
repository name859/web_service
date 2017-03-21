package com.name859.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionCheck implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		response.setContentType("text/html; charset=UTF-8");
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		if (session.getAttribute("user") == null) {
			PrintWriter pw = response.getWriter();
			pw.print("<script>");
			pw.print("alert(\"세션이 종료되었습니다!\");");
			pw.print("location.href=\"user.do?act=loginfm\";");
			pw.print("</script>");
			pw.close();
			
			return;
		}
		
		chain.doFilter(request, response);
	}
	
	public void destroy() {}
	
}
