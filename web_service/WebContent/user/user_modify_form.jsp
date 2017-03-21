<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.name859.user.User" %>
<%
	request.setCharacterEncoding("UTF-8");
	
	String opt = (String)request.getAttribute("opt");
	
	User user = null;
	int searchPage = 0;
	if (opt.equals("user")) {
		user = (User)session.getAttribute("user");
	} else if (opt.equals("admin")) {
		user = (User)request.getAttribute("user");
		searchPage = (Integer)request.getAttribute("searchPage");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<style>
	a:link { text-decoration:none; }
</style>
</head>
<body>

<table align="center">
	<tr>
		<td colspan="4" width="1000"><jsp:include page="/include/include.jsp"><jsp:param name="act" value="title" /></jsp:include></td>
	</tr>
	<tr>
		<td rowspan="10" width="10"></td>
		<td colspan="3">
			<table width="100%">
				<tr>
					<td width="50%"><h2>@ User Management</h2></td>
					<td align="right" width="50%"><jsp:include page="/include/include.jsp"><jsp:param name="act" value="loginInfo" /></jsp:include></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td rowspan="10" width="10"></td>
		<td colspan="2">
			<table width="100%">
				<tr>
					<td width="70%"><jsp:include page="/include/include.jsp"><jsp:param name="act" value="leftMenu" /></jsp:include></td>
					<td align="right" width="30%"><jsp:include page="/include/include.jsp"><jsp:param name="act" value="rightMenu" /></jsp:include></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td rowspan="10" width="10"></td>
		<td>
			<table width="100%">
				<tr>
<%
					if (opt.equals("user")) {
						out.print("<td><h3>* 공 플 정 보 수 정</h3></td>");
					} else if (opt.equals("admin")) {
						out.print("<td><h3>* 공 플 정 보 수 정 ( 관 리 자 )</h3></td>");
					}
%>
				</tr>
				<tr>
					<td>
						<form action="user.do" method="post">
<%
							if (opt.equals("user")) {
								out.print("<input type=\"hidden\" name=\"act\" value=\"mod\">");
							} else if (opt.equals("admin")) {
								out.print("<input type=\"hidden\" name=\"act\" value=\"modad\">");
								out.print("<input type=\"hidden\" name=\"uno\" value=\""+ user.getUserNo() +"\">");
								out.print("<input type=\"hidden\" name=\"sp\" value=\""+ searchPage +"\">");
							}
%>
							<table>
								<tr>
<%
									if (opt.equals("user")) {
										out.print("<td width=\"80\">eMail</td><td><input type=\"text\" name=\"userEmail\" value=\""+ user.getUserEmail() +"\" placeholder=\"eMail\" minlength=\"1\" maxlength=\"20\" required readonly></td>");
									} else if (opt.equals("admin")) {
										out.print("<td width=\"80\">eMail</td><td><input type=\"text\" name=\"userEmail\" value=\""+ user.getUserEmail() +"\" placeholder=\"eMail\" minlength=\"1\" maxlength=\"20\" required></td>");
									}
%>
								</tr>
								<tr>
									<td>Password</td><td><input type="password" name="userPw" value="<%= user.getUserPw() %>" placeholder="Password" minlength="1" maxlength="20" required></td>
								</tr>
								<tr>
									<td>Nickname</td><td><input type="text" name="userName" value="<%= user.getUserName() %>" placeholder="Nickname" minlength="1" maxlength="20" required></td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											<input type="submit" value=" 수　정 ">
<%
											if (opt.equals("admin")) {
												out.print("<input type=\"button\" value=\" 삭　제 \" onclick=\"confirmFunction('정말 삭제하시겠습니까?', 'user.do?act=delad&uno="+ user.getUserNo() +"');\">");
											}
%>
											<input type="reset" value=" 다　시 ">
<%
											if (opt.equals("user")) {
												out.print("<input type=\"button\" value=\" 돌 아 가 기 \" onclick=\"history.go(-1)\">");
											} else if (opt.equals("admin")) {
												out.print("<input type=\"button\" value=\" 목　록 \" onclick=\"history.go(-1)\">");
											}
%>
										</p>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
<%
				if (opt.equals("user")) {
					out.print("<tr>");
					out.print("<td><p><a href=\"javascript:confirmFunction('정말 탈퇴하시겠습니까?', 'user.do?act=del');\"><font color=\"tomato\">[ 공 플 탈 퇴 ]</font></a></p></td>");
					out.print("</tr>");
				}
%>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript" src="<%= request.getContextPath() %>/js/confirm_function.js"></script>

</body>
</html>