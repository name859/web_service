<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.name859.board.model.Board" %>
<%@ page import="com.name859.board.model.BoardConfig" %>
<%@ page import="com.name859.dao.DaoFactory" %>
<%
	request.setCharacterEncoding("UTF-8");
	
	Boolean adminResult = (Boolean)session.getAttribute("adminResult");
	
	int boardId = (Integer)request.getAttribute("boardId");
	BoardConfig boardConfig = (BoardConfig)request.getAttribute("boardConfig");
	
	String searchOpt = (String)request.getAttribute("searchOpt");
	String searchVal = (String)request.getAttribute("searchVal");
	int searchPage = (Integer)request.getAttribute("searchPage");
	List<Board> searchList = (List<Board>)request.getAttribute("searchList");
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
					<td width="50%"><h2>@ Board Service</h2></td>
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
					<td>
						<h3>* <%= boardConfig.getBoardName() %></h3>
					</td>
				</tr>
				<tr>
					<td>
<%
						if (adminResult == false && boardId == 1) {
							out.print("　");
						} else {
							if (boardId == 1) {
								out.print("<a href=\"board.do?act=addfm&bid="+ boardId +"\"><font color='tomato'>[ 등 록 ]</font></a>");
							} else {
								out.print("<a href=\"board.do?act=addfm&bid="+ boardId +"\">[ 등 록 ]</a>");
							}
						}
%>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="1" bordercolor="powderblue">
							<tr align="center">
								<td width="5%">No.</td><td width="60%">Subject</td><td width="18%">Writer</td><td width="12%">Date</td><td width="5%">Hit</td>
							</tr>
<%
							for (int i = 0; i < searchList.size(); i++) {
								out.print("<tr><td align=\"center\">"+ searchList.get(i).getBoardNo());
								out.print("</td><td>　<a href=\"board.do?act=read&opt=read&bid="+ searchList.get(i).getBoardId() +"&bno="+ searchList.get(i).getBoardNo() +"&sp="+ searchPage +"\">"+ searchList.get(i).getBoardSubject() +"</a>");
								if (searchList.get(i).getBoardFileName() != null) out.print(" <font color=\"tomato\">(F)</font>");
								if (searchList.get(i).getCommentCount() > 0) out.print(" <font color=\"tomato\">("+ searchList.get(i).getCommentCount() +")</font>");
								out.print("</td><td>　"+ searchList.get(i).getBoardWriter());
								out.print("</td><td align=\"center\">"+ searchList.get(i).getBoardTime().substring(0, 10));
								out.print("</td><td align=\"center\">"+ searchList.get(i).getBoardHit());
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
							String link = "<a href=\"board.do?act=list&bid="+ boardId +"&sopt="+ searchOpt +"&sval="+ searchVal +"&sp=";
							
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
								out.print(link + totalPage +"\">>></a>");
							}
%>
						</p>
					</td>
				</tr>
				<tr>
					<td align="center">
						<form action="board.do" method="post">
							<input type="hidden" name="act" value="list">
							<input type="hidden" name="bid" value="<%= boardId %>">
							<input type="hidden" name="sp" value="1">
							<select name="sopt">
<%
								if (searchOpt.equals("bs")) {
									out.print("<option value=\"bs\" selected>Subject</option>");
									out.print("<option value=\"bc\">Content</option>");
									out.print("<option value=\"bw\">Writer</option>");
								} else if (searchOpt.equals("bc")) {
									out.print("<option value=\"bs\">Subject</option>");
									out.print("<option value=\"bc\" selected>Content</option>");
									out.print("<option value=\"bw\">Writer</option>");
								} else if (searchOpt.equals("bw")) {
									out.print("<option value=\"bs\">Subject</option>");
									out.print("<option value=\"bc\">Content</option>");
									out.print("<option value=\"bw\" selected>Writer</option>");
								}
%>
							</select>
							<input type="text" name="sval" value="<%= searchVal %>" maxlength="20">
							<input type="submit" value=" Search ">
							<input type="button" value="　All　" onclick="location.href='board.do?act=list&bid=<%= boardId %>&sopt=bs&sp=1'">
						</form>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>