<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/easyui/demo.css">
		<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/easyui/locale/easyui-lang-zh_CN.js"></script>
		<style type="text/css">
			div#noData span.timer {font-size:14px;font-weight:bold;color:rgb(225,55,55);padding:0px 3px}
		</style>
	</head>
	<body style="margin:0px;padding:0px">
		<jsp:include page="check_workingMode.jsp"></jsp:include>
		<div id="noData">
			<div style="text-align:center;padding-top:160px;font-size:22px;font-weight:bold;color:gray">没有最新的数据哦!</div>
			<div style="text-align:center;padding-top:50px">页面将在<span class="timer"></span>秒后自动刷新.</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function(){
				var waiting = 0, keeping = 30;
				refreshNotifyTime(keeping-waiting++);
				window.setInterval(function(){
					refreshNotifyTime(keeping-waiting++);
					if(waiting>=30){
						waiting = 0;
						window.location.href="${ctx}/check/cs/startWork";
					}
				}, 1000);
				function refreshNotifyTime(time){
					$("div#noData span.timer").html(time);
				}
			});
		</script>
	</body>
</html>