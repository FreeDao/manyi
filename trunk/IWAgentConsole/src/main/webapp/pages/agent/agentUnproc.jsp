<%@page import="com.manyi.iw.agent.console.entity.Agent"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Agent agent = new Agent();
agent.setId(12l);
//session.setAttribute("agent", agent);
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
                <span>用户类型:</span>
                 <input id="ck_new_user" class="mini-checkbox" checked="true"  text="新用户" />
                 <input id="ck_old_user" class="mini-checkbox" checked="true"  text="老用户" />
                <span style="margin-left: 50px;">
                   <a class="mini-button" onclick="iwac.agentUnproc.search()"
                    style="border: 1px #F0C000 solid; background: url(pics/search_bg.png) repeat-x; width: 60px;">搜 索</a>
                   <a class="mini-button" onclick="iwac.agentUnproc.reload()"
                    style="border: 1px #F0C000 solid; width: 60px;">刷新</a>
                </span>
            </div>
        </div>
    </div>


   <div class="mini-fit" style="height:400px">
        <div id="datagrid1" class="mini-datagrid" style="height:100%;" url="agent/agentUnproc.do" showfooter="true" showPager="false">
            <div property="columns">
                <div type="indexcolumn" ></div>
                <div field="realname" align="center" headeralign="center">姓名</div>
                <div field="gender" align="center" headeralign="center">性别</div>
                <div field="mobile" align="center" headeralign="center">手机号</div>
                <div field="userType" headeralign="center" align="center">用户类型</div>
                <div field="unprocCount" headeralign="center" align="center">待处理数</div>
                <div field="lastLoginTime" headeralign="center" align="center">最后登录时间</div>
                <div name="action" headeralign="center" align="center">操作</div>
            </div>
     </div> 
    </div>
</body>
  <script type="text/javascript">
	window.iwac = window.iwac||{};
	iwac.agentUnproc = {
		/**
		 * 初始化页面以及数据
		 */
		init:function(){
			mini.parse();
			var grid = this.grid = mini.get("datagrid1");
			grid.load({userType:'-1'});
			var userType = {
					0:"新用户",
					1:"老用户"
				};
			var gender = {
					0:"未知",
					1:"先生",
					2:"女士"
				};
			var data;
			grid.on("drawcell",function(e){
				var record = e.record,
				field = e.field,
				value = e.value,
				column = e.column;
				if(!data){
					data = grid.getResultObject();
				}
				
				if(column.field == "gender"){
					e.cellHtml = gender[e.value];
				}
				
				if(column.field == "userType"){
					e.cellHtml = userType[e.value];
				}
				if(column.field == "lastLoginTime"){
					if(e.value)
						e.cellHtml = mini.formatDate(new Date(e.value),"yyyy-MM-dd HH:mm:ss");
				}
				
				if(column.name == "action"){
					e.cellHtml = '<a class="mini-button"  onclick="iwac.agentUnproc.detail(\''+e.record.userId+'\',\''+e.record.realname+'\')" style="width:80px;">查 看</a>';
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
		search:function(){
			var ck_new_user_value = mini.get("ck_new_user").getChecked();
			var ck_old_user_value = mini.get("ck_old_user").getChecked();
			var searchUserType = -1;
			if(ck_new_user_value&&!ck_old_user_value){
				searchUserType = 0;
			}else if(!ck_new_user_value&&ck_old_user_value){
				searchUserType = 1;
			}
			this.grid.load({userType:searchUserType});
		},
		reload:function(){
			this.grid.reload();
		}
	};
	
	$(function(){
		iwac.agentUnproc.init();
	});
	
	/*
	function test1(){
		var tab = { title: "标签名字",ulr:"", showCloseButton: true };
		var tabs = parent.tabs;
		tabs.addTab(tab);
	}*/
  </script>
</html>
