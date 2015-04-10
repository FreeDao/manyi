<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=GRLKw5G6hZaxpGWSHvjrcmic"></script>
<script type="text/javascript" src="script/boot.js"></script>
<script type="text/javascript">
	userId = ${userId}
</script>
<script type="text/javascript" src="script/CustomOverlay.js"></script>
<script type="text/javascript" src="script/pages/mapView.js"></script>
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden; hidden;
	margin: 0;
}

.hint {
	margin-left:200px;
	position: fixed;
	height: 20px;
	z-index: 1000;
	/* filter: alpha(opacity = 50);
	opacity:0.5; */
	text-align: center;
	line-height: 20px;
}
.hint span{
	padding: 5px;
}
</style>
<title>添加普通标注点</title>
</head>
<body path="<%=basePath%>">
	<div class="hint">
		<span style="background: #ED2D2D;color:#ffffff;">预约中（用户约看）</span>
		<span style="background: #892DED;color:#ffffff;">预约中（经纪人预约）</span>
		<span style="background: #ED732D;">取消中</span>
		<span style="background: #2DED5C;">改期中</span>
		<span style="background: #3A2DED;color:#ffffff;">多房源合并</span>
	</div>
	<div id="allmap"
		style="background-color: transparent;font-weight: bold;"></div>
</body>
<!-- <script type="text/javascript" >
	// 百度地图API功能
var map = new BMap.Map("allmap");            // 创建Map实例
var point = new BMap.Point(116.404, 39.915);    // 创建点坐标
map.centerAndZoom(point,15);                     // 初始化地图,设置中心点坐标和地图级别。
map.enableScrollWheelZoom();                            //启用滚轮放大缩小

</script> -->
</html>
