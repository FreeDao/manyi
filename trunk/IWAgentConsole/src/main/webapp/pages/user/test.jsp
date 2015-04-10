<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=GRLKw5G6hZaxpGWSHvjrcmic"></script>
<style type="text/css">
		body, html,mapContainer {width: 100%;height: 100%;overflow: hidden;hidden;margin:0;}
</style>
</head>

<body >
	<div id="allmap"></div>
</body>
<script type="text/javascript" >
	// 百度地图API功能
var map = new BMap.Map("allmap");            // 创建Map实例
var point = new BMap.Point(116.404, 39.915);    // 创建点坐标
map.centerAndZoom(point,15);                     // 初始化地图,设置中心点坐标和地图级别。
map.enableScrollWheelZoom();                            //启用滚轮放大缩小

</script>
</html>
