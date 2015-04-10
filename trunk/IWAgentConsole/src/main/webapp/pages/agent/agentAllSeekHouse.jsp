<%@page import="com.manyi.iw.agent.console.entity.Agent"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head path="<%=basePath%>">
    <base href="<%=basePath%>"/>
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <link href="styles/page.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="script/boot.js"></script>
  </head>
<body style="height:100%;" path="<%=basePath%>">
    <div class="page">
        <div class="head" id="search">
            <div style="margin: 15px 25px;">
            	<span>姓名:</span>
            		<input name="realname" id="realname" class="mini-textbox"  style="width: 70px;" /> 
            	<span style="margin-left: 20px;">性别:</span>	
            		<input id="gender" name="gender" class="mini-combobox"
                    style="width: 60px;" textField="text" valueField="id"
                    data="[{ 'id': '-1', 'text': '不限' },{ 'id': '1', 'text': '先生' },{ 'id': '2', 'text': '女士' }]" 
							value="-1" required="true" allowInput="false" showNullItem="false" />	
				<span style="margin-left: 20px;">手机号:</span>
            		<input name="mobile" id="mobile" class="mini-textbox" /> 			
            	<span style="margin-left: 20px;">约看状态:</span>
            		<input name="state" class="mini-combobox"
                    style="width: 60px;" textField="text" valueField="id"
                    data="[{'id':'0', 'text': '不限' },{ 'id': '1', 'text': '预约中' },{ 'id': '2', 'text': '待确认' },
							{ 'id': '3', 'text': '待看房' },{ 'id': '4', 'text': '待登记' },
							{ 'id': '5', 'text': '已看房' },{ 'id': '6', 'text': '未看房' },
							{ 'id': '7', 'text': '改期中' },{ 'id': '8', 'text': '取消中' }]" 
							value="0" required="true" allowInput="false" showNullItem="false" />
				<span style="margin-left: 20px;">用户类型：</span>
					<input id="userType" name="userType" class="mini-combobox"
                    style="width: 60px;" textField="text" valueField="id"
                    data="[{ 'id': '-1', 'text': '不限' },{ 'id': '0', 'text': '新用户' },{ 'id': '1', 'text': '老用户' }]" 
							value="-1" required="true" allowInput="false" showNullItem="false" />	
				 <span style="margin-left: 20px;">提交时间：</span>
					<input name="createTimeStart" class="mini-datepicker" style="width:200px;" 
      							  format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
      					--
      				<input name="createTimeEnd" class="mini-datepicker" style="width:200px;" 
      							  format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
				</div>
				<div style="margin: 15px 25px;">
					<span> 位置：
					 	<input id="district" name="district"
						class="mini-combobox" url="house/area/list.do?parentId=2"
						style="width: 80px;" textField="text" valueField="id"
						allowInput="false" showNullItem="false"
						onvaluechanged="iwac.agentAllSH.districtValueChange()" />
						<input id="town" name="town" class="mini-combobox" style="width: 100px;"
							textField="" valueField="" url="" allowInput="false"
							showNullItem="false" /> 
						<input name="estateName" class="mini-textbox" emptyText="小区名称" />
					</span>
					<span> 户型：
					<input id="bedroomSum" name="bedroomSum" class="mini-combobox"
					style="width: 80px;" textField="text" valueField="id"
					url="subs/bedroomSum.txt" value="0"  allowInput="false"
					showNullItem="false" /> 
					<input id="livingRoomSum" name="livingRoomSum" class="mini-combobox"
					style="width: 80px;" textField="text" valueField="id"
					url="subs/livingRoomSum.txt" value="0"  allowInput="false"
					showNullItem="false" />
					<input id="wcSum" name="wcSum" class="mini-combobox"
					style="width: 80px;" textField="text" valueField="id"
					url="subs/wcSum.txt"  value="0" allowInput="false"
					showNullItem="false" />
					</span>
					<span style="margin-left: 20px;"> 楼层：
						<input name="floor" id="floor" class="mini-textbox"  style="width: 50px;" /> 
					</span>
					<span style="margin-left: 20px;"> 装修： 
						<input id="decorateType" name="decorateType" class="mini-combobox" style="width: 100px;"
							textField="text" valueField="id" url="subs/decorateType.txt"
							value="0" required="true" allowInput="false" showNullItem="false" />
					</span>
					<span style="margin-left: 20px;">租金：
						<input id="minPrice" name="minPrice" class="mini-textbox" style="width:60px;" emptyText="最低价" /> -
						<input id="maxPrice" name="maxPrice" class="mini-textbox" style="width:60px;" emptyText="最高价" />
					</span>
					
					<span style="margin-left: 50px;">
	                   <a class="mini-button" onclick="iwac.agentAllSH.search()"
	                    style="border: 1px #F0C000 solid; width: 60px;">搜 索</a>
	                   <a class="mini-button" onclick="iwac.agentAllSH.reset()"
	                    style="border: 1px #F0C000 solid; width: 60px;">重置</a>
                	</span>
				</div>
            </div>
        </div>


	<div class="mini-fit" style="background:red;height:400px">
		<div id="datagrid1" class="mini-datagrid" style="height:100%;"
			url="agent/agentAllSeekHouse.do" showfooter="false">
			<div property="columns">
				<div type="indexcolumn"></div>
				<div field="realname" align="center" headeralign="center">姓名</div>
				<div field="gender" align="center" headeralign="center" width="50">性别</div>
				<div field="mobile" align="center" headeralign="center">手机号</div>
				<div field="userType" headeralign="center" align="center" width="60">用户类型</div>
				<div field="district" headeralign="center" align="center">区域</div>
				<div field="town" headeralign="center" align="center">板块</div>
				<div field="estate" headeralign="center" align="center">小区</div>
				<div field="building" headeralign="center" align="center" width="60">栋座号</div>
				<div field="room" headeralign="center" align="center" width="60">室号</div>
				<div field="bedroomSum" headeralign="center" align="center" width="60">房数</div>
				<div field="livingRoomSum" headeralign="center" align="center" width="60">厅数</div>
				<div field="wcSum" headeralign="center" align="center" width="60">卫数</div>
				<div field="floor" headeralign="center" align="center" width="60">楼层</div>
				<div field="rentPrice" headeralign="center" align="center">价格</div>
				<div field="createTime" headeralign="center" align="center">创建时间</div>
				<div field="state" headeralign="center" align="center">当前状态</div>
				<div name="action" headeralign="center" align="center">操作</div>
			</div>
		</div>
	</div>	
	
</body>
	<script type="text/javascript">
			mini.parse();
			window.iwac = window.iwac || {};
			iwac.agentAllSH = $.extend(iwac.agentAllSH || {}, {
				/**
				 * 初始化页面以及数据
				 */
				init : function() {
					mini.parse();
					var district = this.district = mini.get("district");
					district.select(0);
					this.town = mini.get("town");

					var grid = this.grid = mini.get("datagrid1");
					grid.load();
					var decorateType = {
						0 : '',
						1 : '毛胚',
						2 : '简单装修',
						3 : '精装修',
						4 : '豪华装修'
					}
					var gender = {
							0:"未知",
							1:"先生",
							2:"女士"
						};
					var userType = {
							0:"新用户",
							1:"老用户"
						};
					
	/* 				{ 'id': '1', 'text': '预约中' },{ 'id': '2', 'text': '待确认' },
					{ 'id': '3', 'text': '待看房' },{ 'id': '4', 'text': '待登记' },
					{ 'id': '5', 'text': '已看房' },{ 'id': '6', 'text': '未看房' },
					{ 'id': '7', 'text': '改期中' },{ 'id': '8', 'text': '取消中' } */
					var userType = {
							0:"新用户",
							1:"老用户"
						};
					var state = {
							1:"预约中",
							2:"待确认",
							3:"待看房",
							4:"待登记",
							5:"已看房",
							6:"未看房",
							7:"改期中",
							8:"取消中"
						};
					
					/*var bizStatus = {
						1:"找房中",
						2:"已租房",
						3:"不需要租房"
					};
					 */
					grid.on("drawcell", function(e) {
						if (e.column.field == "decorateType") {
							e.cellHtml = decorateType[e.value];
						}else if (e.column.field == "gender") {
							e.cellHtml = gender[e.value];
						}else if (e.column.field == "userType") {
							e.cellHtml = userType[e.value];
						}else if (e.column.field == "state") {
							e.cellHtml = state[e.value];
						}else if(e.column.name == "action"){
							e.cellHtml = '<a class="mini-button"  onclick="iwac.agentAllSH.detail(\''+e.record.userId+'\',\''+e.record.realname+'\')" style="width:80px;">查 看</a>';
						}else if(e.column.field == "lastLoginTime"){
							if(e.value)
								e.cellHtml = mini.formatDate(new Date(e.value),"yyyy-MM-dd HH:mm:ss");
						}
						
					});
				},
				detail:function(userId,realName){
					var tab = { title: realName+"--详情",url:$("body").attr("path")+'user/detail.do?userId='+userId,showCloseButton: true };
					var tabs = parent.tabs;
					tabs.addTab(tab);
					tabs.activeTab(tab);
				},
				/**
				 * 搜索
				 */
				search : function() {
					var searchForm = new mini.Form("#search");
					var searchData = searchForm.getData(true,true);
					this.grid.load(searchData);
				},
				/**
				 * 重置
				 */
				reset : function() {
					var form = new mini.Form("#search");
					form.reset();
					this.district.select(0);
				},
				districtValueChange : function() {
					this.town.load($('body').attr('path')
							+ "house/area/list.do?parentId="
							+ this.district.getValue());
					this.town.select(0);
				}
			});

			$(function() {
				iwac.agentAllSH.init();
			})

			/*
			function test1(){
				var tab = { title: "标签名字",ulr:"", showCloseButton: true };
				var tabs = parent.tabs;
				tabs.addTab(tab);
			}*/
		</script>
</html>
