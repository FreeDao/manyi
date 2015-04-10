<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache,must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<div style="margin-left: auto;margin-right: auto;border: 1px solid black;width:300px;height:160px;margin-top: 300px;">
		<form method="post" action="/mp/loginSubmit">
		<h4 style="color: red" align="center">
			<%-- <form:errors /> --%>
			${msg}
		</h4>

		<table border="0" align="center" >
			<tr>
				<td>用户名：</td>
				<td><input name="userName" type="text"/></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input name="password" type="password"/></td>
			</tr>
			<tr>
				<td align="center" colspan="2"><input type="submit" value="登录" /><input type="reset" value="重置" /></td>
			</tr>
		</table>

	</form>
	</div>
</body>
</html>