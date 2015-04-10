<%@page import="com.mysql.jdbc.util.BaseBugReport"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>爱屋吉屋经纪人后台</title>
    <link href="script/miniui/themes/default/miniui.css" rel="stylesheet"  type="text/css" />
    <link href="styles/main.css" rel="stylesheet"  type="text/css" />
	<link href="script/miniui/themes/icons.css" rel="stylesheet"  type="text/css" />
	<script type="text/javascript" charset="utf-8" src="script/jquery-1.6.2.min.js"></script>
	<script src="script/miniui/miniui.js" type="text/javascript"></script>
	<script src="<%=basePath%>agent/loginInfo.do?<%=new Date().getTime()%>" type="text/javascript"></script>
  </head>
  <body>
	<div id="layout_root" class="mini-layout" style="width:100%;height:100%;">
		<!-- north start -->
		<div class="header" region="north" height="70" showSplit="false" showHeader="false">
			<h1 style="margin:0;padding:15px;cursor:default;font-family:微软雅黑,黑体,宋体;">爱屋吉屋经纪人工作平台</h1>
			<div style="position:absolute;top:18px;right:40px;">
				<a class="mini-button mini-button-iconTop" iconCls="icon-edit" onclick="userInfo()" id="agentName" plain="true"></a> <a
					class="mini-button mini-button-iconTop" iconCls="icon-close" onclick="logout()" plain="true">注销</a>
			</div>
		</div>
		<!--  north end -->
		<!-- south start -->
		<div showHeader="false" region="south" showSplit="false" height="25">
			<div style="line-height:20px;text-align:center;cursor:default">Copyright © 爱屋吉屋</div>
		</div>
		<!-- south end -->
		<!-- center start -->
		<div title="center" region="center" style="border:0;" bodyStyle="overflow:hidden;">
			<div class="mini-splitter" style="width:100%;height:100%;" borderStyle="border:0;">
				<div size="180" maxSize="250" minSize="100" showCollapseButton="true" style="border:0;">
					<!--OutlookTree-->
					<div id="leftTree" class="mini-outlooktree" url="subs/tree.txt" onnodeclick="onNodeClick" textField="text" idField="id"
						parentField="pid" expandOnLoad="true"></div>
				</div>
				<div showCollapseButton="false" style="border:0;">
					<!--Tabs-->
					<div id="mainTabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
						 <div title="待处理约看" url="pages/agent/agentUnproc.jsp" name="tab$agentUnproc"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- center end -->
	</div>
	<script type="text/javascript">
		mini.parse();
		var tabs = mini.get("mainTabs");
		function showTab(node) {
			var id = "tab$" + node.id;
			// var filepath = "../../module/" + node.pid + "/"; //文件夹路径
			var tab = tabs.getTab(id);
			if (!tab) {
				tab = {};
				tab.name = id;
				tab.title = node.text;
				tab.showCloseButton = true;
				//这里拼接了url，实际项目，应该从后台直接获得完整的url地址
				tab.url = node.url; //mini_JSPath + filepath + node.id + ".jsp";
				tabs.addTab(tab);
			}
			tabs.activeTab(tab);
		}

		function onNodeClick(e) {
			var node = e.node;
			var isLeaf = e.isLeaf;
			if (isLeaf) {
				showTab(node);
			}
		}

		function userInfo() {
			mini.open({
				url:"<%=basePath%>agent/detail.do",
				title:"我的详情",
				width:400,
				height:300
			});
		}

		function logout() {
			window.location.href = "<%=basePath%>agent/logout.do";
		}
		
		$(function(){
			$("#agentName").html('<span style="" class="mini-button-text  mini-button-icon icon-edit">'+agentUser.name+'</span>');
		})
	</script>
</body>
</html>
