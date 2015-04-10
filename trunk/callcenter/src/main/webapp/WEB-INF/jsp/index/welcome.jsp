<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Call Center Manager System.</title>
		<style type="text/css">
			body {margin:0px;padding:0px;text-align:center}
			div#header {height:60px;background:rgb(205,205,205);font-size:22px;font-style:italic;font-weight:bold;line-height:60px;color:gray;border-bottom:1px solid rgb(185,185,185)}
			form#loginForm input{border:1px solid rgb(195,195,195);padding:5px;background:white;outline:none}
			form#loginForm div.row {margin:10px 0px}
		</style>
	<script language="JavaScript">  
		function loadTopWindow(){
			if (window.parent!=null && window.parent.document.URL!=document.URL){  
				window.parent.top.location= document.URL;   
			}
		}
	</script>
</head>  
<body onload="loadTopWindow()"> 
		<div id="header">Call Center Manager System Version 1.3</div>
		<div style="width:380px;margin:120px auto;text-align:center;background:rgb(225,225,225);border:1px solid rgb(205,205,205);border-radius:8px">
			<div style="padding:5px;text-align:left;background:rgb(155,155,155);border-radius:5px 5px 0 0;color:white;font-style:italic">用户登录</div>
			<div style=";padding:30px;">
				<form id="loginForm" action="${pageContext.request.contextPath}/employee/login" method="post" style="margin:0px">
					<div class="row">姓名:<input type="text" name="userName" value="" id="userNameInput"></div>
					<div class="row">密码:<input type="password" name="password" value="" ></div>
					<span style="color: red">${message}</span>
					<div class="row"><input type="submit" value="登录" style="width:84px;height:32px;cursor:pointer"></div>
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		document.getElementById("userNameInput").focus();
	</script>
</html>