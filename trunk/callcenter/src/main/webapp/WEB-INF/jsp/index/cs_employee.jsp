<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Call Center Manager System.</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/demo.css">
	<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
	<style type="text/css">
		div.header {height:28px;background:rgb(205,205,205);padding:16px;font-size:14px;font-weight:bold;color:gray}
		input.button {width:160px;height:40px;border:1px solid rgb(155,155,155);background:rgb(225,225,225);color:#333;font-weight:bold;font-size:14px;font-family:"微软雅黑";cursor:pointer;border-radius:5px}
	</style>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',border:false" class="header">员工:${sessionScope.login_session.userName},<a href="${ctx}/logout">退出</a></div>
	<div data-options="region:'south',border:false" style="margin-top: 10px;padding-top: 10px;border-top: #ccc solid 2px;height:50px;text-align: center;background:rgb(205,205,205);padding:10px;">manyi corp.</div>
	<div style="overflow:hidden;padding:30px;font-size:14px;font-weight:bold;font-family:微软雅黑" >
		<div style="height: 300px">
			<div style="padding: 60px;"><span style="font-size: 1.2em;">你今天完成的任务数如下:</span></div>
			<div style="text-align: center;">审核 改盘任务${checkNum.checkChange} 个；审核 发布出售任务${checkNum.checkPublishSell}个；审核 发布出租任务${checkNum.checkPublishRent}个；审核 轮询任务${checkNum.checkLoop}个</div>
		</div>
		<div style="text-align: center;vertical-align: middle;">
			<input style="margin-top: 100px" type="button" class="button" onclick="startWork()"  value="开始工作" />
		</div>
		
	</div>
	<script type="text/javascript">
	function startWork(){
		window.location.href="${ctx}/check/cs/startWork";
		
	}
	</script>
</body>
</html>