<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  	<link href="../script/miniui/themes/default/miniui.css" rel="stylesheet"  type="text/css" />
    <link href="../styles/main.css" rel="stylesheet"  type="text/css" />
	<link href="../styles/main.css" rel="stylesheet"  type="text/css" />
	<link href="../script/miniui/themes/icons.css" rel="stylesheet"  type="text/css" />
	<script type="text/javascript" charset="utf-8" src="../script/jquery-1.6.2.min.js"></script>
	<script src="../script/miniui/miniui.js" type="text/javascript"></script>
  <body>
      <a href="javascript:;"  onClick="test1()">123</a><br>
  </body>
  	  <script type="text/javascript">
		mini.parse();
		function test1(){
			var tab = { title: "标签名字",ulr:"", showCloseButton: true };
			var tabs = parent.tabs;
			tabs.addTab(tab);
		}
	  </script>
  
</html>
