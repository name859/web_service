<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.name859.user.User" %>
<%
	request.setCharacterEncoding("UTF-8");
	
	User user = (User)session.getAttribute("user");
	Boolean adminResult = (Boolean)session.getAttribute("adminResult");
	
	String act = request.getParameter("act");
	
	if (act.equals("title")) {
%>
		<h1><a href="user.do?act=home"><font color="black">Gongple</font></a></h1>
<%
	} else if (act.equals("loginInfo")) {
%>
		<font color="blue"><%= user.getUserName() %></font>님 입장하였습니다!　<input type="button" value=" 나 가 기 " onclick="location.href='login.do?act=logout'">
<%
	} else if (act.equals("leftMenu")) {
%>
		<a href="user.do?act=home">[ H O M E ]</a>
		<a href="board.do?act=list&bid=1&sopt=bs&sp=1">[ 공 지 사 항 ]</a>
		<a href="board.do?act=list&bid=2&sopt=bs&sp=1">[ 공 플 게 시 판 ]</a>
		<a href="board.do?act=list&bid=3&sopt=bs&sp=1">[ 자 유 게 시 판 ]</a>
<%
		if (adminResult == true) {
			out.print("<a href=\"user.do?act=list&sopt=ue&sp=1\"><font color='tomato'>[ 관 리 자 ]</font></a>");
		}
	} else if (act.equals("rightMenu")) {
%>
		<a href="user.do?act=modfm&opt=user">[ 공 플 정 보 수 정 ]</a>
<%
	}
%>