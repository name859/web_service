package com.name859.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.name859.dao.DaoFactory;
import com.name859.util.Common;

public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; charset=UTF-8");
		
		String act = request.getParameter("act");
		
		Common comm = new Common();
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();
		if (!act.equals("loginfm") && !act.equals("addfm") && !act.equals("add")) {
			if (session.getAttribute("user") == null) {
				comm.alert(pw, "세션이 종료되었습니다!", "user.do?act=loginfm");
				return;
			}
		}
		
		User user = new User();
		if (act.equals("mod") || act.equals("del")) {
			user = (User)session.getAttribute("user");
		} else if (act.equals("modfmad") || act.equals("modad") || act.equals("delad")) {
			user.setUserNo(Integer.parseInt(request.getParameter("uno")));
		}
		
		int searchPage = request.getParameter("sp") == null ? 0 : Integer.parseInt(request.getParameter("sp"));
		
		UserDao userDao = new DaoFactory().getUserDao();
		RequestDispatcher rd = null;
		if (act.equals("loginfm")) {
			rd = request.getRequestDispatcher("/login_form.jsp");
			rd.forward(request, response);
		} else if (act.equals("addfm")) {
			rd = request.getRequestDispatcher("/user/user_add_form.jsp");
			rd.forward(request, response);
		} else if (act.equals("home")) {
			rd = request.getRequestDispatcher("/home.jsp");
			rd.forward(request, response);
		} else if (act.equals("modfm")) {
			String opt = request.getParameter("opt");
			request.setAttribute("opt", opt);
			
			if (opt.equals("admin")) {
				user.setUserNo(Integer.parseInt(request.getParameter("uno")));
				user = userDao.userSearch(user);
				
				request.setAttribute("user", user);
				request.setAttribute("searchPage", searchPage);
			}
			
			rd = request.getRequestDispatcher("/user/user_modify_form.jsp");
			rd.forward(request, response);
		} else if (act.equals("list")) {
			String searchOpt = request.getParameter("sopt");
			String searchVal = request.getParameter("sval") == null ? "" : request.getParameter("sval");
			
			int viewRecord = 5;
			int viewPage = 5;
			int searchListCount = userDao.userSearchListCount(searchOpt, searchVal);
			List<User> searchList = userDao.userSearchList(searchOpt, searchVal, searchPage, viewRecord);
			
			int totalPage = (searchListCount - 1) / viewRecord + 1;
			int startPage = (searchPage - 1) / viewPage * viewPage + 1;
			int endPage = startPage + viewPage - 1;
			if (endPage > totalPage) endPage = totalPage;
			
			rd = request.getRequestDispatcher("/user/user_list.jsp");
			request.setAttribute("searchOpt", searchOpt);
			request.setAttribute("searchVal", searchVal);
			request.setAttribute("searchPage", searchPage);
			request.setAttribute("searchList", searchList);
			request.setAttribute("viewPage", viewPage);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			rd.forward(request, response);
		}
		
		if (act.equals("add") || act.equals("mod") || act.equals("modad")) {
			user.setUserEmail(request.getParameter("userEmail"));
			user.setUserPw(request.getParameter("userPw"));
			user.setUserName(request.getParameter("userName"));
			
			if (act.equals("add")) {
				if (!userDao.emailCheck(user)) {
					userDao.userAdd(user);
					
					comm.alert(pw, "공플 가입!", "user.do?act=loginfm");
				} else {
					comm.alert(pw, "이미 사용중인 eMail입니다!", "javascript:history.go(-1)");
				}
			} else if (act.equals("mod") || act.equals("modad")) {
				userDao.userModify(user);
				
				if (act.equals("mod")) {
					comm.alert(pw, "공플 정보 수정!", "user.do?act=home");
				} else if (act.equals("modad")) {
					comm.alert(pw, "공플 정보 수정!", "user.do?act=list&sopt=ue&sp="+ searchPage);
				}
			}
		} else if (act.equals("del") || act.equals("delad")) {
			userDao.userDelete(user);
			
			if (act.equals("del")) {
				response.sendRedirect("user.do?act=loginfm");
			} else if (act.equals("delad")) {
				response.sendRedirect("user.do?act=list&sopt=ue&sp=1");
			}
		} else if (act.equals("reset")) {
			userDao.userNoReset();
			
			comm.alert(pw, "Reset! User No.", "javascript:history.go(-1)");
		}
		
		if (pw != null) pw.close();
	}
	
}
