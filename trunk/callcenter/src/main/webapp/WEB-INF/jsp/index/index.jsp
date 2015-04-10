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
		ul.tree, ul.subtree {margin:0px}
		ul.tree li, ul.subtree li{
			margin: 8px;
			padding: 5px 16px;
			background:rgb(241,241,241);
			border:1px solid rgb(225,225,225);
			border-radius:5px;
			list-style:none;
		}
		ul.tree li a {text-decoration:none;font-size:14px;fong-weight:bold;color:rgb(55,55,155);font-family:"微软雅黑";display: block;}
		ul.subtree li {background:white;margin:8px 0px}
		div.header {height:28px;background:#B3DFDA;padding:16px;font-size:14px;font-weight:bold;color:gray}
		div.header a {text-decoration:none;color:blue}
	</style>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',border:false" class="header">当前用户${sessionScope.login_session.userName},${sessionScope.login_session.role }<a class="exit" href="${ctx}/logout">退出</a></div>
	<div data-options="region:'west',split:true,title:'列表'" style="width:220px">
		
		<!--  1管理员,2客服经理，3客服人员,4地推经理,5地推人员 6财务 -->
		<c:if test="${(sessionScope.login_session.role != 3) || test == 1}">
		<ul class='tree'>
			<c:if test="${ (sessionScope.login_session.role != 6 && sessionScope.login_session.role != 7 && sessionScope.login_session.role != 8) || test == 1}">
			<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/check/checkGrid')">审核管理</a></li>
			</c:if>
			
			<c:if test="${sessionScope.login_session.role == 1 || sessionScope.login_session.role == 4 || test == 1}">
			<!-- BD看房任务列表  -->
			<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/lookHouse/lookHouseGrid')">看房任务列表</a></li>
			</c:if>
			<c:if test="${sessionScope.login_session.role == 1 || sessionScope.login_session.role == 7 || sessionScope.login_session.role == 8 || test == 1}">
			<!-- User看房任务列表  -->
			<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/lookHouse/userLookHouseGrid')">经纪人看房任务</a></li>
			</c:if>
			
			<c:if test="${ ((sessionScope.login_session.role != 5) && (sessionScope.login_session.role != 6) && sessionScope.login_session.role != 7 && sessionScope.login_session.role != 8) || test == 1}">
			<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/building/buildingGrid')">楼栋管理</a></li>
			<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/house/houseGrid')">房源管理</a></li>
			<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/user/userIndex')">经纪人管理</a></li>
			<li><span>小区管理</span>
				<ul class="subtree">
					<li onclick="chinaLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/estate/estateGrid')">小区管理</a></li>
					<li onclick="chinaLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/estate/estateLogGrid')">新增小区管理</a></li>
				</ul>
			</li>
			</c:if>
			<c:if test="${sessionScope.login_session.role == 1 || test == 1}">
				<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/employee/employeeIndex')">用户管理</a></li>
			</c:if>
			<c:if test="${sessionScope.login_session.role == 6 || sessionScope.login_session.role == 1 || test == 1}">
				<!-- 财务相关 -->
				<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/pay/payGrid')">支付管理</a></li>
			</c:if>
			
			<c:if test="${sessionScope.login_session.role == 1 || sessionScope.login_session.role == 2 || sessionScope.login_session.role == 4 || test == 1}">
				<!-- 系统报表  , 经理级别以上的才能访问-->
				<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${himsServicePath}/info.jsp?p=520Manyi')">运营报表</a></li>
			</c:if>
			<c:if test="${sessionScope.login_session.role == 1 || sessionScope.login_session.role == 2 || sessionScope.login_session.role == 4 || test == 1}">
				<!-- 系统报表  , 经理级别以上的才能访问-->
				<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${himsServicePath}/info1.jsp?p=520Manyi')">系统报表</a></li>
			</c:if>
			<c:if test="${sessionScope.login_session.role == 1 || sessionScope.login_session.role == 7 || sessionScope.login_session.role == 8 || test == 1}">
				<!-- 工具箱 , 经理级别以上的才能访问-->
				<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${ctx}/linktool/ihutil')">爱屋工具箱 </a></li>
			</c:if>
			<c:if test="${sessionScope.login_session.role != 7 && sessionScope.login_session.role != 8}">
				<li onclick="parentLi(this)"><a href="javascript:void(0)" onclick="showcontent('${himsServicePath}/userValidation/index')">查询验证码</a></li>
			</c:if>
			<c:if test="${sessionScope.login_session.role == 1}">
				<li  onclick="parentLi(this)"><a href="javascript:void(0);" onclick="showcontent('${himsServicePath}/info3.jsp?p=520Manyi')">导入数据 </a></li>
			</c:if>
		</ul>
		</c:if>
				
	</div>
	<!-- <div data-options="region:'east',split:true,title:'East'" style="width:100px;padding:10px;">east region</div> -->
	<div data-options="region:'south',border:false" style="margin-top: 10px;padding-top: 10px;border-top: #ccc solid 2px;height:50px;text-align: center;background:#B3DFDA;padding:10px;">manyi corp.</div>
	<div data-options="region:'center',title:'欢迎'"  style="overflow:hidden" >
		<c:if test="${sessionScope.login_session.role != 3}" >
			<iframe scrolling="yes" frameborder="0"  src="${ctx}/html/index_welcome.html" style="width:100%;height:100%;"></iframe>
		</c:if>
		<c:if test="${sessionScope.login_session.role == 3}" >
			<iframe scrolling="yes" frameborder="0"  src="${ctx}/check/checkWork_CS" style="width:100%;height:100%;"></iframe>
		</c:if>
		
		
	</div>
	<script type="text/javascript">
		function showcontent(language){
			$("iframe").attr("src",language);
		}
		
		var parentEn = null;
		var chinaEn = null;
		function parentLi(obj){
			var t =$(obj);
			parentEn = t;
			//rgb(241,241,241)
			t.siblings("li").css("background-color","#F0F0F0");
			t.css("background-color","#E6CAFF");
			if(chinaEn != null ){
				chinaEn.css("background-color","#FFF");
			}
		}
		function chinaLi(obj){
			var t =$(obj);
			chinaEn = t;
			t.siblings("li").css("background-color","#FFF");
			t.css("background-color","#E6CAFF");
			if(parentEn != null ){
				parentEn.css("background-color","#F0F0F0");
			}
		}
		
	</script>
</body>
</html>