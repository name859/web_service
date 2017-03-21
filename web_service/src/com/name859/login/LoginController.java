package com.name859.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.name859.dao.DaoFactory;
import com.name859.user.User;

public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected boolean adminCheck(User user) {
		String[] administrator = {"name859@naver.com", "imdct@gmail.com", "gongple@gongple.com"};
		
		boolean adminResult = false;
		for (int i = 0; i < administrator.length; i++) {
			if (user.getUserEmail().equals(administrator[i])) adminResult = true;
		}
		
		return adminResult;
	}
	
	public void init(ServletConfig config) throws ServletException {}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; charset=UTF-8");
		
		String act = request.getParameter("act");
		
		HttpSession session = request.getSession();
		if (act.equals("login")) {
			User user = new User();
			user.setUserEmail(request.getParameter("userEmail"));
			user.setUserPw(request.getParameter("userPw"));
			
			LoginDao loginDao = new DaoFactory().getLoginDao();
			if (loginDao.loginCheck(user)) {
				boolean adminResult = adminCheck(user);
				
				session.setAttribute("user", user);
				session.setAttribute("adminResult", adminResult);
				response.sendRedirect("user.do?act=home");
			} else {
				PrintWriter pw = response.getWriter();
				pw.print("<script>");
				pw.print("alert(\"공플 입장 실패!\");");
				pw.print("history.go(-1);");
				pw.print("</script>");
				pw.close();
			}
		} else if (act.equals("logout")) {
			session.invalidate();
			response.sendRedirect("user.do?act=loginfm");
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	
	public void destroy() {}
	
}
