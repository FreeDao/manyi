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
</head>

<body path="<%=basePath%>">
	<div class="page">
		<div class="head" id="search">
			<div style="margin: 15px 25px;">
				<span> 位置： <input id="district" name="district" class="mini-combobox"
					url="house/area/list.do?parentId=2" style="width: 80px;" textField="text" valueField="id"
					  allowInput="false"
					showNullItem="false" onvaluechanged="iwac.house.districtValueChange()"/>
					<input id="town" name="town" class="mini-combobox"
					style="width: 100px;" textField="" valueField=""
					url=""  allowInput="false"
					showNullItem="false" />
					<input id="estate" name="estateName" class="mini-textbox" emptyText="小区名称"/>
				</span>
				<span> 装修： <input id="decorateType" name="decorateType" class="mini-combobox"
					style="width: 60px;" textField="text" valueField="id"
					url="subs/decorateType.txt" value="0" required="true" allowInput="false"
					showNullItem="false" />
				</span>
			</div>
			<div style="margin: 15px 25px;">
				<span> 户型： 
					<input id="livingRoomSum" name="livingRoomSum" class="mini-combobox"
					style="width: 80px;" textField="text" valueField="id"
					url="subs/livingRoomSum.txt" value="0"  allowInput="false"
					showNullItem="false" />
					<input id="bedroomSum" name="bedroomSum" class="mini-combobox"
					style="width: 80px;" textField="text" valueField="id"
					url="subs/bedroomSum.txt" value="0"  allowInput="false"
					showNullItem="false" />
					<input id="wcSum" name="wcSum" class="mini-combobox"
					style="width: 80px;" textField="text" valueField="id"
					url="subs/wcSum.txt"  value="0" allowInput="false"
					showNullItem="false" />
				</span>
				<span>租金：
					<input id="minPrice" name="minPrice" class="mini-textbox" style="width:60px;" emptyText="最低价" />-
					<input id="maxPrice" name="maxPrice" class="mini-textbox" style="width:60px;" emptyText="最高价" />
					<!-- <div id="ck1" name="product" class="mini-checkbox" checked="false" readOnly="false" text="周边有地铁" onvaluechanged="onValueChanged"></div> -->
				</span>
				<span style="margin-left: 50px;"> <a class="mini-button"
					onclick="iwac.house.search()"
					style="border: 1px #F0C000 solid; background: url(pics/search_bg.png) repeat-x; width: 60px;">搜
						索</a> <a class="mini-button" onclick="iwac.house.reset()"
					style="border: 1px #F0C000 solid; background: url(pics/cancel_bg.png) repeat-x; width: 60px; margin-left: 20px;">取
						消</a>
				</span>
			</div>
		</div>
	</div>
		<!--列表-->
		<div>
			<div id="datagrid1" class="mini-datagrid" style="height: auto"
				url="house/list.do" multiSelect="true" showfooter="false">
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
					<!-- <div field="waitdealNum" headeralign="center" align="center">
						地铁</div> -->
				</div>
			</div>
			<div style="text-align: center; padding: 10px;">
				<a class="mini-button" onclick="iwac.house.submit()">提 交</a> 
				<span style="margin-right: 30px;">&nbsp;</span> 
				<a class="mini-button" onclick="iwac.house.cancel()" style="border: 1px #FFCC33 solid; background: url(pics/cancel_bg.png) repeat-x;">取 消</a>
			</div>
		</div>
</body>
<script type="text/javascript" src="script/pages/houseSearch.js"></script>
</html>
