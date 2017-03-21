<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.name859.board.model.Board" %>
<%@ page import="com.name859.board.model.BoardConfig" %>
<%@ page import="com.name859.dao.DaoFactory" %>
<%
	request.setCharacterEncoding("UTF-8");
	
	Board board = (Board)request.getAttribute("board");
	int searchPage = (Integer)request.getAttribute("searchPage");
	
	BoardConfig boardConfig = (BoardConfig)request.getAttribute("boardConfig");
	
	String opt = (String)request.getAttribute("opt");
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
					<td><h3>* <%= boardConfig.getBoardName() %> ( 수 정 )</h3></td>
				</tr>
				<tr>
					<td>
						<form action="boardFile.do" method="post" name="board" onsubmit="return boardInputCheck();" enctype="multipart/form-data">
							<input type="hidden" name="act" value="mod">
							<input type="hidden" name="opt" value="<%= opt %>">
							<input type="hidden" name="bid" value="<%= board.getBoardId() %>">
							<input type="hidden" name="bno" value="<%= board.getBoardNo() %>">
							<input type="hidden" name="sp" value="<%= searchPage %>">
<%
							if (board.getBoardFileName() != null) {
								out.print("<input type=\"hidden\" name=\"oldBoardFileName\" value=\""+ board.getBoardFileName() +"\">");
								out.print("<input type=\"hidden\" name=\"oldBoardFileRename\" value=\""+ board.getBoardFileRename() +"\">");
								out.print("<input type=\"hidden\" name=\"oldBoardFileSize\" value=\""+ board.getBoardFileSize() +"\">");
							}
%>
							<table>
								<tr>
<%
									if (board.getBoardId() == 1 || board.getBoardId() == 2) {
										out.print("<td width=\"100\">작　성　자</td><td><input type=\"text\" name=\"boardWriter\" value=\""+ board.getBoardWriter() +"\" maxlength=\"20\" disabled></td>");
									} else if (board.getBoardId() == 3) {
										out.print("<td width=\"100\">작　성　자</td><td><input type=\"text\" name=\"boardWriter\" value=\""+ board.getBoardWriter() +"\" maxlength=\"20\"></td>");
									}
%>
								</tr>
								<tr>
									<td>제　　　목</td><td><input type="text" name="boardSubject" size="50" value="<%= board.getBoardSubject() %>" maxlength="100"></td>
								</tr>
								<tr>
									<td>내　　　용</td><td><textarea name="boardContent" rows="20" cols="100" style="resize:none;"><%= board.getBoardContent() %></textarea></td>
								</tr>
								<tr>
<%
									if (opt.equals("fu") || board.getBoardFileName() == null) {
										out.print("<td height=\"25\">파　　　일</td><td><input type=\"file\" name=\"boardFileName\"></td>");
									} else if (opt.equals("fv")) {
										out.print("<td height=\"25\">파　　　일</td><td>"+ board.getBoardFileName() +" <a href=\"board.do?act=modfm&opt=fu&bid="+ board.getBoardId() +"&bno="+ board.getBoardNo() +"&sp="+ searchPage +"&boardPw="+ board.getBoardPw() +"\">[x]</a></td>");
									}
%>
								</tr>
								<tr>
									<td>비　　　번</td><td><input type="password" name="boardPw" value="<%= board.getBoardPw() %>" maxlength="20"></td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											<input type="submit" value=" 수　정 ">
											<input type="reset" value=" 다　시 ">
											<input type="button" value=" 돌 아 가 기 " onclick="history.go(-2)">
											<input type="button" value=" 목　록 " onclick="location.href='board.do?act=list&bid=<%= board.getBoardId() %>&sopt=bs&sp=<%= searchPage %>'">
										</p>
									</td>
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
	function boardInputCheck() {
		var board = document.board;
		if (!inputCheck(board.boardWriter, "게시판 작성자는", 1, 20, "focus")) return false;
		if (!inputCheck(board.boardSubject, "게시판 제목은", 1, 100, "focus")) return false;
		if (!inputCheck(board.boardPw, "게시판 비번은", 1, 20, "select")) return false;
	}
</script>

</body>
</html>