<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head path="<%=basePath%>">
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="styles/page.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/boot.js"></script>
<script type="text/javascript" src="script/iwac.common.js"></script>
<script type="text/javascript">userId=${userId}</script>
<script type="text/javascript" src="script/pages/houseSearchPanel.js"></script>
</head>

<body path="<%=basePath%>">
	<a class="mini-button" onclick="iwac.recommend.housePanel()"
                    style="border: 1px #F0C000 solid; background: url(pics/search_bg.png) repeat-x; width: 80px;">推荐房源</a>
	
	<div>
	<h1>看房单</h1>
		<div id="datagrid0" class="mini-datagrid" style="height: auto"
			url="user/recommend/seekhouse/list.do" multiSelect="true" showfooter="false">
			<div property="columns">
				<div type="checkcolumn" field="houseId"></div>
				<div field="district" align="center"
					headeralign="center">区域</div>
				<div field="town" align="center"
					headeralign="center">板块</div>
				<div field="estate"  align="center"
					headeralign="center">小区名称</div>
				<div field="room" headeralign="center" align="center">房号</div>
				<div field="residence" headeralign="center" align="center">
					户型</div>
				<div field="coveredArea" headeralign="center" align="center">
					面积m2</div>
				<div field="decorateType" headeralign="center" align="center" >
					装修</div>
				<div field="houseStatus" headeralign="center" align="center" >
					房源状态</div>
				<div field="rentPrice" headeralign="center" align="center">
					价格（元/月）</div>
				<div field="contact" headeralign="center" align="center">
					房东/手机号</div>
			</div>
		</div>
	</div>
	
	
	
	
	<!--推荐列表-->
	<div>
	<h1>推荐历史</h1>
		<div id="datagrid1" class="mini-datagrid" style="height: auto"
			url="user/recommend/list.do" multiSelect="true" showfooter="false">
			<div property="columns">
				<div type="checkcolumn" field="houseId"></div>
				<div field="district" align="center"
					headeralign="center">区域</div>
				<div field="town" align="center"
					headeralign="center">板块</div>
				<div field="estate"  align="center"
					headeralign="center">小区名称</div>
				<div field="room" headeralign="center" align="center">房号</div>
				<div field="residence" headeralign="center" align="center">
					户型</div>
				<div field="coveredArea" headeralign="center" align="center">
					面积m2</div>
				<div field="decorateType" headeralign="center" align="center" >
					装修</div>
				<div field="rentPrice" headeralign="center" align="center">
					价格（元/月）</div>
				<div field="contact" headeralign="center" align="center">
					房东/手机号</div>
				<div field="createTime" headeralign="center" align="center">
					推荐时间</div>
				<div field="status" headeralign="center" align="center">
					当前状态</div>
				<div field="houseStatus" headeralign="center" align="center">
					房源状态</div>
				<div field="seekhouseStatus" headeralign="center" align="center">
					约看状态</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="script/pages/recommend.js"></script>
</html>
