<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.name859.user.User" %>
<%
	request.setCharacterEncoding("UTF-8");
	
	String searchOpt = (String)request.getAttribute("searchOpt");
	String searchVal = (String)request.getAttribute("searchVal");
	int searchPage = (Integer)request.getAttribute("searchPage");
	List<User> searchList = (List<User>)request.getAttribute("searchList");
	int viewPage = (Integer)request.getAttribute("viewPage");
	int totalPage = (Integer)request.getAttribute("totalPage");
	int startPage = (Integer)request.getAttribute("startPage");
	int endPage = (Integer)request.getAttribute("endPage");
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
					<td><h3>* 관 리 자</h3></td>
				</tr>
				<tr>
					<td align="right">
						<a href="user.do?act=reset"><font color="tomato">[ Reset! User No. ]</font></a>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="1" bordercolor="powderblue">
							<tr align="center">
								<td>No.</td><td width="22%">eMail</td><td width="22%">Password</td><td width="22%">Nickname</td><td width="22%">Time</td><td width="15">Delete</td>
							</tr>
<%
							for (int i = 0; i < searchList.size(); i++) {
								out.print("<tr><td align=\"center\">"+ searchList.get(i).getUserNo());
								out.print("</td><td>　<a href=\"user.do?act=modfm&opt=admin&uno="+ searchList.get(i).getUserNo() +"&sp="+ searchPage +"\">"+ searchList.get(i).getUserEmail());
								out.print("</a></td><td>　"+ searchList.get(i).getUserPw());
								out.print("</td><td>　<a href=\"user.do?act=modfm&opt=admin&uno="+ searchList.get(i).getUserNo() +"&sp="+ searchPage +"\">"+ searchList.get(i).getUserName());
								out.print("</a></td><td align=\"center\">"+ searchList.get(i).getUserTime());
								out.print("</td><td><input type=\"button\" value=\" Delete \" onclick=\"confirmFunction('정말 삭제하시겠습니까?', 'user.do?act=delad&uno="+ searchList.get(i).getUserNo() +"');\">");
								out.print("</td></tr>");
							}
%>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center">
						<p>
<%
							String link = "<a href=\"user.do?act=list&sopt="+ searchOpt +"&sval="+ searchVal +"&sp=";
							
							if (searchPage <= viewPage) {
								out.print("<< ");
							} else {
								out.print(link + 1 +"\"><<</a> ");
							}
							
							if (searchPage <= viewPage) {
								out.print("< ");
							} else {
								out.print(link + (startPage - viewPage) +"\"><</a> ");
							}
							
							for (int i = startPage - 1; i < endPage; i++) {
								if (searchPage == i + 1) {
									out.print("["+ (i + 1) +"] ");
								} else {
									out.print(link + (i + 1) +"\">["+ (i + 1) +"]</a> ");
								}
							}
							
							if (endPage == totalPage) {
								out.print("> ");
							} else {
								out.print(link + (endPage + 1) +"\">></a> ");
							}
							
							if (endPage == totalPage) {
								out.print(">>");
							} else {
								if (totalPage % viewPage == 0) {
									out.print(link + (totalPage - viewPage + 1) +"\">>></a>");
								} else {
									out.print(link + (totalPage - totalPage % viewPage + 1) +"\">>></a>");
								}
							}
%>
						</p>
					</td>
				</tr>
				<tr>
					<td align="center">
						<form action="user.do" method="post">
							<input type="hidden" name="act" value="list">
							<input type="hidden" name="sp" value="1">
<%
							if (searchOpt.equals("ue")) {
								out.print("<label><input type=\"radio\" name=\"sopt\" value=\"ue\" checked> eMail</label> ");
								out.print("<label><input type=\"radio\" name=\"sopt\" value=\"un\"> Nickname</label>");
							} else if (searchOpt.equals("un")) {
								out.print("<label><input type=\"radio\" name=\"sopt\" value=\"ue\"> eMail</label> ");
								out.print("<label><input type=\"radio\" name=\"sopt\" value=\"un\" checked> Nickname</label>");
							}
%>
							<input type="text" name="sval" value="<%= searchVal %>" maxlength="20">
							<input type="submit" value=" Search ">
							<input type="button" value="　All　" onclick="location.href='user.do?act=list&sopt=ue&sp=1'">
						</form>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript" src="<%= request.getContextPath() %>/js/confirm_function.js"></script>

</body>
</html>