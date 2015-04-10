<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Custom DataGrid Pager - jQuery EasyUI Demo</title>
	
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/demo.css">
	<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<style type="text/css">
		img{max-width:350px;max-height:350px;}
	</style>
</head>
<body onresize="resizeGrid();">
		<div class="easyui-tabs" style="width:auto;height:600px">
        <%-- <div title="经纪人账号信息" style="padding:10px">
        	<span>姓名:${result.realName}</span>
        	<span>注册邀请码:${result.spreadId}</span>
        	<span>注册时间:${result.createTimeStr}</span>
        	<span>工作区域:${result.areaName}</span>
        	<span>注册邀请人:${result.spreadName}</span>
        	<span>身份证号:${result.code}</span>
        	<span>手机号:${result.mobile}</span>
        	<span>身份证照片:${result.codeUrl }</span>
        	<span>名片照片：${result.cardUrl }</span>
        	
        	<span>审核结果：${result.stateStr }</span>
        	
        </div> --%>
        
        
        <div title="经纪人账号信息" style="padding:12px">
			<table style="width:100%; border-spacing:  10px;">
				<tr style="width:auto;text-align: center ;" >
					<td style="width:25%"><b>姓名：</b>${result.realName}</td>
					<td style="width:25%"><b>注册邀请码：</b>${result.spreadId}</td>
					<td style="width:25%"><b>工作区域：</b>${result.areaName}</td>
					<td style="width:25%"><b>注册邀请人：</b>${result.spreadName}
					<c:if test="${result.otherInner == 1}">(内部)</c:if>
					<c:if test="${result.otherInner == 0}">(经纪人)</c:if>
					</td>
				</tr>
				
				<tr style="width:auto;text-align: center ;" >
					<td style="width:25%"><b>身份证号：</b>${result.code}</td>
					<td style="width:25%"><b>手机号：</b>${result.mobile}</td>
				</tr>
				
				 <tr style="width:auto;text-align: center;" >
					<td style="width:25%"><b>身份证照片：</b></td>
					<td><img src="${result.codeUrl}" /></td>
					<td style="width:20%"><b>名片照片：</b></td>
					<td><img src="${result.cardUrl}" /></td>
				</tr> 
				
				<tr style="width:auto;text-align: center;" >
					<td style="width:25%"><b>审核结果：</b>${result.stateStr }</td>
				</tr> 
			</table>
		</div>
		
		<div title="操作记录" style="padding:10px">
            <table id="mainfrom" > </table>
        </div>
    </div>
    
    <!-- <div class="easyui-tabs" style="width:auto;height:250px">
        <div title="操作记录" style="padding:10px">
            
        </div>
    </div> -->
    <script type="text/javascript">
    var data_from = $('#mainfrom').datagrid({
		url : '${ctx}/user/getUserHistory?uid=${result.uid }',
		//fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
		striped : true, //True 就把行条纹化
		pagination : true,//分页
		rownumbers : true, //显示行号
		pageSize : 30,
		pageNumber : 1,
		pageList : [ 10, 30, 50, 100 ],//列表分页
		sortName : 'hid',
		sortOrder : 'desc',
		loadMsg : '数据正在努力加载中...',
		selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
		checkOnSelect : true, //点击 checkbox 同时触发 点击 行
		columns : [ [ 
		          {field : 'id',checkbox : true,width:$(this).width() * 0.1}//显示一个checkbox
				, {field : 'operatorName',title : '操作人',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
				, {field : 'addTimeStr',title : '操作时间',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
				, {field : 'userName',title : '经纪人',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
				, {field : 'actionTypeStr',title : '操作类型',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
		] ],
		
		/* onDblClickRow : function(rowIndex, rowData) {
			view_btn(rowData);
		}, */

		onLoadSuccess : function(data) {
			$(".datagrid-view").css("height", "88%");
		}

	});
    </script>
</body>
</html>