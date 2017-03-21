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
					<td><h3>* 공 플 입 장</h3></td>
				</tr>
				<tr>
					<td>
						<form action="login.do" method="post">
							<input type="hidden" name="act" value="login">
							<table>
								<tr>
									<td width="80">eMail</td><td><input type="text" name="userEmail" placeholder="eMail" maxlength="20" autofocus></td>
								</tr>
								<tr>
									<td>Password</td><td><input type="password" name="userPw" placeholder="Password" maxlength="20"></td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											<input type="submit" value=" 입　장 ">
											<input type="reset" value=" 다　시 ">
										</p>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<td>
						<p>
							<a href="user.do?act=addfm">[ 공 플 가 입 ]</a>
							<a href="login.do?act=login&userEmail=name859@naver.com&userPw=859"><font color="tomato">[ name859 LOGIN ]</font></a>
						</p>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>