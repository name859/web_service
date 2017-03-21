<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
					<td align="right" width="50%">　</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td rowspan="10" width="10"></td>
		<td colspan="2">
			<table width="100%">
				<tr>
					<td width="70%">　</td>
					<td align="right" width="30%">　</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td rowspan="10" width="10"></td>
		<td>
			<table width="100%">
				<tr>
					<td><h3>* 공 플 가 입</h3></td>
				</tr>
				<tr>
					<td>
						<form action="user.do" method="post">
							<input type="hidden" name="act" value="add">
							<table>
								<tr>
									<td width="80">eMail</td><td><input type="text" name="userEmail" placeholder="eMail" minlength="1" maxlength="20" required autofocus></td>
								</tr>
								<tr>
									<td>Password</td><td><input type="password" name="userPw" placeholder="Password" minlength="1" maxlength="20" required></td>
								</tr>
								<tr>
									<td>Nickname</td><td><input type="text" name="userName" placeholder="Nickname" minlength="1" maxlength="20" required></td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											<input type="submit" value=" 가　입 ">
											<input type="reset" value=" 다　시 ">
											<input type="button" value=" 돌 아 가 기 " onclick="history.go(-1)">
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

</body>
</html>