<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.name859.user.User" %>
<%
	request.setCharacterEncoding("UTF-8");
	
	User user = (User)session.getAttribute("user");
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
					<td width="50%"><h2>@ User Management + Board Service</h2></td>
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
					<td><h3>* H O M E</h3></td>
				</tr>
				<tr>
					<td><p><img src="<%= request.getContextPath() %>/image/home.jpg"><br><br>eMail　:　<%= user.getUserEmail() %></p></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>