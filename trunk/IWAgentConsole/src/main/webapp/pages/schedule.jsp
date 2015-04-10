<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head path="<%=basePath%>">
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="styles/page.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/boot.js"></script>
<script type="text/javascript" src="script/iwac.common.js"></script>
<link rel="stylesheet" type="text/css" href="script/fullCalendar/css/fullcalendar.css"/>
<script src='script/fullCalendar/js/jquery-ui-1.10.2.custom.min.js'></script>
<script src='script/fullCalendar/js/fullcalendar.min.js'></script>
<script type="text/javascript" src="script/pages/appointmentDetailPanel.js"></script>
<script src='script/pages/schedule.js'></script>
<style type="text/css">
	html,body{
		padding:0px;
		margin:0px;
	}
	
	#calendar{width:960px; margin:20px auto 10px auto}
	.color1{
		background-color: #F26C6C;
		color:#333333;
		cursor: pointer;
		font-size: 12px;
	}
	
	.color2{
		background-color: #2DED5C;
		color:#333333;
		cursor: pointer;
		font-size: 12px;
	}
	
	.color3{
		background-color: #3A2DED;
		color:#333333;
		cursor: pointer;
		font-size: 12px;
	}
</style>
</head>

<body path="<%=basePath%>" >
	
	<div style="margin-left:50px;width:957px;border:1px solid #DDDDDD;background-color: #F7F7F7;">
		<span style="background-color:#F69898;padding:4px;">&nbsp;待确认、待看房&nbsp;</span><span style="background-color:#6CF28D;padding:4px;">&nbsp;已改期、已取消&nbsp;</span><span style="background-color:#756CF2;padding:4px;">&nbsp;经纪人已签到、未到未看房、已到未看房、已到已看房&nbsp;</span>
	</div>
	<div id="main" style="width:1060px">
	   <div id='calendar'></div>
	</div>
	
</body>
</html>
