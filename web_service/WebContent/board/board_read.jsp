<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="com.name859.board.model.Board" %>
<%@ page import="com.name859.board.model.BoardConfig" %>
<%@ page import="com.name859.board.model.Comment" %>
<%@ page import="com.name859.dao.DaoFactory" %>
<%@ page import="com.name859.user.User" %>
<%
	request.setCharacterEncoding("UTF-8");
	
	User user = (User)session.getAttribute("user");
	Boolean adminResult = (Boolean)session.getAttribute("adminResult");
	
	Board board = (Board)request.getAttribute("board");
	int searchPage = (Integer)request.getAttribute("searchPage");
	
	BoardConfig boardConfig = (BoardConfig)request.getAttribute("boardConfig");
	
	String opt = (String)request.getAttribute("opt");
	
	int commentNo = (Integer)request.getAttribute("commentNo");
	List<Comment> searchList = (List<Comment>)request.getAttribute("searchList");
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
<%
					if (opt.equals("read")) {
						out.print("<td><h3>* "+ boardConfig.getBoardName() +" ( 읽 기 )</h3></td>");
					} else if (opt.equals("modpwfm") || opt.equals("delpwfm")) {
						out.print("<td><h3>* "+ boardConfig.getBoardName() +" ( 비 번 )</h3></td>");
					}
%>
				</tr>
				<tr>
					<td>
						<form action="board.do" method="post">
<%
							if (opt.equals("read")) {
								out.print("<input type=\"hidden\" name=\"act\" value=\"read\">");
								out.print("<input type=\"hidden\" name=\"opt\" value=\"modpwfm\">");
							} else if (opt.equals("modpwfm")) {
								out.print("<input type=\"hidden\" name=\"act\" value=\"modfm\">");
								out.print("<input type=\"hidden\" name=\"opt\" value=\"fv\">");
							} else if (opt.equals("delpwfm")) {
								out.print("<input type=\"hidden\" name=\"act\" value=\"del\">");
							}
%>
							<input type="hidden" name="bid" value="<%= board.getBoardId() %>">
							<input type="hidden" name="bno" value="<%= board.getBoardNo() %>">
							<input type="hidden" name="sp" value="<%= searchPage %>">
							<table width="100%">
								<tr>
									<td width="80%">[ <%= board.getBoardNo() %> ] <b><%= board.getBoardSubject() %></b></td><td width="20%" align="right"><font color="blue"><%= board.getBoardWriter() %></font></td>
								</tr>
								<tr>
									<td><p><%= board.getBoardTime().substring(0, 19) %> ( <%= board.getBoardIp().split("\\.")[0] %>.<%= board.getBoardIp().split("\\.")[1] %>.*.<%= board.getBoardIp().split("\\.")[3] %> )</p></td><td align="right"><%= board.getBoardHit() %></td>
								</tr>
								<tr>
									<td colspan="2"><p><%= board.getBoardContent().replaceAll("\n", "<br>") %></p></td>
								</tr>
<%
								if (board.getBoardFileName() != null) {
									out.print("<tr>");
									out.print("<td colspan=\"2\"><p>");
									if ((getServletContext().getMimeType(board.getBoardFileName()) == null ? "" : getServletContext().getMimeType(board.getBoardFileName()).substring(0, 5)).equals("image")) {
										out.print("<a href=\"upload/"+ board.getBoardFileRename() +"\" target=\"blank\"><img src=\"upload/"+ board.getBoardFileRename() +"\" height=\"100\" border=\"1\"></a><br>");
									}
									out.print("<a href=\"boardFile.do?fn="+ board.getBoardFileName() +"&fr="+ board.getBoardFileRename() +"\">"+ board.getBoardFileName() +"</a> ( "+ new DecimalFormat("#,##0").format(Math.ceil((double)board.getBoardFileSize()/1024)) +"KB )");
									out.print("</p></td>");
									out.print("</tr>");
								}
								
								if (opt.equals("modpwfm") || opt.equals("delpwfm")) {
									out.print("<tr>");
									out.print("<td colspan=\"2\" align=\"center\">비　　　번 　<input type=\"password\" name=\"boardPw\" maxlength=\"20\" autofocus></td>");
									out.print("</tr>");
								}
%>
								<tr>
									<td colspan="2" align="center">
										<p>
<%
											if (opt.equals("read") && (adminResult == true || board.getUserNo() == user.getUserNo())) {
												out.print("<input type=\"submit\" value=\" 수　정 \"> ");
												out.print("<input type=\"button\" value=\" 삭　제 \" onclick=\"location.href='board.do?act=read&opt=delpwfm&bid="+ board.getBoardId() +"&bno="+ board.getBoardNo() +"&sp="+ searchPage +"'\"> ");
											} else if (opt.equals("modpwfm")) {
												out.print("<input type=\"submit\" value=\" 수　정 \"> ");
											} else if (opt.equals("delpwfm")) {
												out.print("<input type=\"submit\" value=\" 삭　제 \"> ");
											}
											
											if (opt.equals("modpwfm") || opt.equals("delpwfm")) {
												out.print("<input type=\"reset\" value=\" 다　시 \"> ");
												out.print("<input type=\"button\" value=\" 돌 아 가 기 \" onclick=\"history.go(-1)\"> ");
											}
%>
											<input type="button" value=" 목　록 " onclick="location.href='board.do?act=list&bid=<%= board.getBoardId() %>&sopt=bs&sp=<%= searchPage %>'">
										</p>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<td><p>Comment <font color="tomato">(<%= board.getCommentCount() %>)</font></p></td>
				</tr>
				<tr>
					<td>
						<table width="100%">
<%
							for (int i = 0; i < searchList.size(); i++) {
								if (searchList.get(i).getCommentNo() != commentNo) {
									out.print("<tr><td width=\"17%\"><font color=\"gray\">"+ searchList.get(i).getCommentWriter() +"</font>");
									out.print("</td><td><font color=\"gray\">"+ searchList.get(i).getCommentTime().substring(0, 16) +" ( "+ searchList.get(i).getCommentIp().split("\\.")[0] +"."+ searchList.get(i).getCommentIp().split("\\.")[1] +".*."+ searchList.get(i).getCommentIp().split("\\.")[3] +" )</font>");
									if (adminResult == true || searchList.get(i).getUserNo() == user.getUserNo()) {
										out.print("</td><td rowspan=\"2\" width=\"17%\" align=\"right\"><input type=\"button\" value=\" 수　정 \" onclick=\"location.href='board.do?act=read&opt=read&bid="+ board.getBoardId() +"&bno="+ board.getBoardNo() +"&sp="+ searchPage +"&cno="+ searchList.get(i).getCommentNo() +"'\"> ");
										out.print("<input type=\"button\" value=\" 삭　제 \" onclick=\"location.href='board.do?act=codel&bid="+ board.getBoardId() +"&bno="+ board.getBoardNo() +"&sp="+ searchPage +"&cno="+ searchList.get(i).getCommentNo() +"'\">");
									} else {
										out.print("</td><td rowspan=\"2\" width=\"17%\" align=\"right\">　");
									}
									out.print("</td></tr>");
									out.print("<tr><td></td><td>"+ searchList.get(i).getCommentContent().replaceAll("\n", "<br>"));
									out.print("</td></tr>");
									out.print("<tr><td colspan=\"3\">");
									out.print("</td></tr>");
								} else {
									out.print("<form action=\"board.do\" method=\"post\" onsubmit=\"return commentInputCheck();\">");
									out.print("<input type=\"hidden\" name=\"act\" value=\"comod\">");
									out.print("<input type=\"hidden\" name=\"bid\" value=\""+ board.getBoardId() +"\">");
									out.print("<input type=\"hidden\" name=\"bno\" value=\""+ board.getBoardNo() +"\">");
									out.print("<input type=\"hidden\" name=\"sp\" value=\""+ searchPage +"\">");
									out.print("<input type=\"hidden\" name=\"cno\" value=\""+ searchList.get(i).getCommentNo() +"\">");
									if (board.getBoardId() == 1 || board.getBoardId() == 2) {
										out.print("<tr><td width=\"17%\"><input type=\"text\" name=\"commentWriter\" id=\"commentWriter\" value=\""+ searchList.get(i).getCommentWriter() +"\" size=\"15\" maxlength=\"20\" disabled>");
									} else if (board.getBoardId() == 3) {
										out.print("<tr><td width=\"17%\"><input type=\"text\" name=\"commentWriter\" id=\"commentWriter\" value=\""+ searchList.get(i).getCommentWriter() +"\" size=\"15\" maxlength=\"20\">");
									}
									out.print("</td><td><textarea name=\"commentContent\" id=\"commentContent\" rows=\"3\" cols=\"77\" style=\"resize:none;\" maxlength=\"200\" autofocus>"+ searchList.get(i).getCommentContent() +"</textarea>");
									out.print("</td><td align=\"right\"><input type=\"submit\" value=\" 수　정 \"> <input type=\"reset\" value=\" 다　시 \">");
									out.print("</td></tr>");
									out.print("<tr><td colspan=\"3\">");
									out.print("</td></tr>");
									out.print("</form>");
								}
							}
%>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<form action="board.do" method="post" onsubmit="return commentInputCheck();">
							<input type="hidden" name="act" value="coadd">
							<input type="hidden" name="bid" value="<%= board.getBoardId() %>">
							<input type="hidden" name="bno" value="<%= board.getBoardNo() %>">
							<input type="hidden" name="sp" value="<%= searchPage %>">
							<input type="hidden" name="uno" value="<%= user.getUserNo() %>">
							<table width="100%">
								<tr>
<%
									if (board.getBoardId() == 1 || board.getBoardId() == 2) {
										out.print("<td width=\"17%\"><input type=\"text\" name=\"commentWriter\" id=\"commentWriter\" value=\""+ user.getUserName() +"\" size=\"15\" maxlength=\"20\" disabled></td>");
									} else if (board.getBoardId() == 3) {
										out.print("<td width=\"17%\"><input type=\"text\" name=\"commentWriter\" id=\"commentWriter\" value=\""+ user.getUserName() +"\" size=\"15\" maxlength=\"20\"></td>");
									}
%>
									<td><textarea name="commentContent" id="commentContent" rows="3" cols="77" style="resize:none;" maxlength="200"></textarea></td>
									<td width="17%" align="right"><input type="submit" value=" 등　록 "> <input type="reset" value=" 다　시 "></td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript" src="<%= request.getContextPath() %>/js/input_check.js"></script>
<script type="text/javascript">
	function commentInputCheck() {
		var commentWriter = document.getElementById("commentWriter");
		var commentContent = document.getElementById("commentContent");
		if (!inputCheck(commentWriter, "덧글 작성자는", 1, 20, "focus")) return false;
		if (!inputCheck(commentContent, "덧글 내용은", 1, 200, "focus")) return false;
	}
</script>

</body>
</html>