<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>企业云平台管理系统</title>
	<link rel="stylesheet" href="static/xcloud/css/login.css">
	<script type="text/javascript">
		function preSubmit(){
			if(document.getElementById("loginname").value==""||document.getElementById("password").value==""){
				document.getElementById("div1").className="cont  contError";
				document.getElementById("loginError").innerHTML="用户名或密码不能为空";
			}else{
				document.getElementById("form1").submit();
			}
		}
		document.onkeydown=function(event){
			event = event|| window.event;
			if (event.keyCode == 13){
				preSubmit();
			}
	    }
	    function init() {
			var path = "./static/xcloud/js/", script = null;
			var head = document.getElementsByTagName("head")[0];
			var scripts = [ "jquery-1.5.2.min.js", "ligerui.min.js", "common.js" ];
			setTimeout(function() {
				for ( var i = 0; i < scripts.length; i++) {
					script = document.createElement("script");
					script.src = path + scripts[i];
					head.appendChild(script);
				}
			}, 500)
			if(document.readyState=="complete")
		{
		  	document.getElementById("loginname").focus(); 
		}
		}	    
	</script>
</head>
<body onload="init()">
	<div class="login-Wrap">
		<!-- S 中间内容部分 -->
		<div class="login-midWrap">
			<div class="login-box">
				<div class="hd"><img src="static/xcloud/css/img/login/login-title.png" alt="企业云平台管理系统"></div>
				<div class="bd">
					<!-- 
					<ul class="tabs">
						<li class="current"><a href="#">简</a></li>
						<li><a href="#">En</a></li>
					</ul>
					 -->
					<form action="userLogin.do" method="post" id="form1">
					<div class="cont" id="div1">
						<div class="cell"><input class="txt user" type="text" id="loginname" name="loginname" value="" p="pukka"/></div>
						<div class="cell"><input class="txt pwd" type="password" id="password" name="password" value=""/></div>
						<div class="cell cellError"><span id="loginError" class="loginError"></span></div>
						<div class="cell"><input class="btn" id="ss" type="button" value="" onclick="preSubmit();"></div>
					</div>
					</form>
				</div>
			</div>
		</div>
		<!-- E 中间内容部分 -->

		<!-- S 底部信息 -->
		<div class="login-footer">
			<p>版本号：00000v1.0  上海帕科软件有限公司 Copyright(c) 2013</p>
			<p>最佳分辨率：1280 * 720</p>
		</div>
		<!-- E 底部信息 -->
	</div>
</body>
</html>

