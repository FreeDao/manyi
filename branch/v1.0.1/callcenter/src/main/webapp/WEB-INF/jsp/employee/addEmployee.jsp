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
</head>
<body onresize="resizeGrid();">
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addEmployee()">添加帐号</a>
	<div id="dlg" class="easyui-dialog" title="添加帐号" data-options="iconCls:'icon-save'" style="width:400px;height:300px;padding:10px;">
		<span id="error" style="color:red">${message }</span>
		<form action="${ctx}/employee/addEmployee" method="post" id="addEmployeeForm">
				账户名 ：<input type="text" name="userName" id="add_name" class='easyui-validatebox validatebox-text validatebox-invalid ' data-options="required:true"/><br> 
				姓名 ：<input type="text" name="realName" id="add_realName" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true"/><br> 
				密码 ：<input type="password" name="password" id="add_password" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true" /><br> 
				确认密码 ：<input type="password" name="passwordAlign" id="passwordAlign" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true"/><br> 
				角色：<select name="role" id="add_role">
					<option value="3" selected="selected">客服专员</option>
					<option value="2" >客服经理</option>
					<option value="4">地推经理</option>
					<option value="5">地推专员</option>
					<option value="6">财务</option>
				</select><br/>
			ucServerName ：<input type="text" name="ucServerName" id="ucServerName" class='easyui-validatebox validatebox-text validatebox-invalid '/><br> 
			ucServerPwd ：<input type="text" name="ucServerPwd" id="ucServerPwd" class="easyui-validatebox validatebox-text validatebox-invalid"><br>	
			ucServerGroupId ：<input type="text" name="ucServerGroupId" id="ucServerGroupId" class='easyui-validatebox validatebox-text validatebox-invalid'/><br>
			<input type="button" value="提交" onclick="addEmployeeSubmit()"/>
				
		</form>
	</div>
	<table id="mainfrom" > </table>
	
	<div id='main_div' title="编辑" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px;"  closed="true" >
		<span style="color:red">${message }</span>
	  <form  method="post"  id="deit_employee">
	  	<input type="hidden" name="roleStr" value="role">
	  	<input type="hidden" name="employeeId" value="employeeId">
		账户名: <input  id="userName" name ='userName' class='easyui-validatebox validatebox-text validatebox-invalid'   readonly="readonly"/><br/>
		姓名: <input  id="realName" name ='realName' class='easyui-validatebox validatebox-text validatebox-invalid'   readonly="readonly" /><br/>
		密码：<input id="password" name="password" class='easyui-validatebox validatebox-text validatebox-invalid' type="password" data-options="required:true" /><br/>
		确认密码：<input id="passwordAlign" name="passwordAlign" class='easyui-validatebox validatebox-text validatebox-invalid' type="password" data-options="required:true"/><br/>
		角色：<select id="role" name="role">
					<option value="1">管理员</option>
					<option value="3">客服专员</option>
					<option value="2" >客服经理</option>
					<option value="4">地推经理</option>
					<option value="5">地推专员</option>
					<option value="6">财务</option>
				</select><br/>
			ucServerName ：<input type="text" name="ucServerName" id="ucServerName" class='easyui-validatebox validatebox-text validatebox-invalid '/><br> 
			ucServerPwd ：<input type="text" name="ucServerPwd" id="ucServerPwd" class='easyui-validatebox validatebox-text validatebox-invalid'/><br>
			ucServerGroupId ：<input type="text" name="ucServerGroupId" id="ucServerGroupId" class='easyui-validatebox validatebox-text validatebox-invalid'/><br>
			<a href="javascript:void(0)" onclick="editEmployeeSubmit()" class="easyui-linkbutton">提交</a>
	  </form>
    </div>
	<script type="text/javascript">
		
		
		//window.parent.closedlg();
		var data_from = $('#mainfrom').datagrid({
			url : '${ctx}/employee/getEmployee',
			//fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
			striped : true, //True 就把行条纹化
			pagination : true,//分页
			rownumbers : true, //显示行号
			pageSize : 20,
			pageNumber : 1,
			pageList : [ 20, 30, 50, 100 ],//列表分页
			sortName : 'hid',
			sortOrder : 'desc',
			loadMsg : '数据正在努力加载中...',
			height: document.body.clientHeight * 0.88,
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [ 
			          {field : 'employeeId',checkbox : true,width:$(this).width() * 0.1}//显示一个checkbox
					, {field : 'userName',title : '账户名',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'realName',title : '姓名',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'roleStr',title : '角色',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'role',title : '角色',align : 'center', resizable : true,hidden : true,sortable : false,width:$(this).width() * 0.1}
					, {field : 'disabledStr',title : '账户状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'ucServerName',title : 'ucServerName',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'ucServerPwd',title : 'ucServerPwd',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'ucServerGroupId',title : 'ucServerGroupId',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'opt',  title : '操作',align : 'center',resizable : true,hidden : false,sortable : false ,width:$(this).width() * 0.1,
						formatter : function (value,row,index)
						 {
							//alert(row.disabledStr);
							if(row.disabledStr=="启用"){
								return "<a href='javascript:void(0);' onclick='view_btn("+row.employeeId+")'>禁用</a>";
							}else{
								return "<a href='javascript:void(0);' onclick='view_btn("+row.employeeId+")'>启用</a>";
							}
							
						 }
					   }
					

			] ],
			toolbar : [/*  {
				text : '添加',iconCls : 'icon-add',
				handler : function() {
					item_btn("add");
				}
			},  */{
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					item_btn("edit");
				}
			} ],
			/* onDblClickRow : function(rowIndex, rowData) {
				view_btn(rowData);
			}, */

			onLoadSuccess : function(data) {
				$(".datagrid-view").css("height", "88%");
			}

		});

		/*
		禁用  启用
		 */
		 function view_btn(row) {
			$.post("${ctx}/employee/disabledEmployee",{id:row},function(data){
				if(data.errorCode==0){
					$('#mainfrom').datagrid({url:'${ctx}/employee/getEmployee'});
				}
			});
		} 

		/*
		1，新增
		2，修改
		 */
		function item_btn(type) {
			//$("#deit_employee input[name='editSubmit']").attr("value","提交");
			if (type == 'edit') {
				var rows = $('#mainfrom').datagrid('getSelections');//得到选中的行
				if (rows.length > 1) {
					$.messager.alert('温馨提示', '不能选择多行进行编辑！', 'info');
					return false;
				}
				if (rows.length <= 0) {
					$.messager.alert('温馨提示', '请选择数据！', 'info');
					return false;
				}
				
				$("#main_div").find("input").each(function(p) {
					var self = $(this);
					self.val(rows[0][self.attr("name")]);
					//console.debug
					
				});
				
				$("#main_div").find("select").each(function(p) {
					var self = $(this);
					var count=$("#role option").length;
					for (var i = 0; i < count; i++) {
						//console.debug(self.attr("name"))
						//console.debug(rows[0]['role']);
						if ($("#role").get(0).options[i].value == rows[0]['role']) {
							$("#role").get(0).options[i].selected = true;
							break;
						}
					}
				});
				
			}
			//$("#main_div").show();
			$("#main_div").dialog("open");
		}

		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth * 0.9;
				}
			});
		}

		function addEmployee() {
			$('#dlg').dialog('open');
		}
		function addEmployeeSubmit() {
			if(  $("#addEmployeeForm input[name='userName']").val() == '' ) {
				$.messager.alert('提示','请输入账户名');
				return false;
			}
			if(  $("#addEmployeeForm input[name='realName']").val() == '' ) {
				$.messager.alert('提示','请输入姓名');
				return false;
			}
			if(  $("#addEmployeeForm input[name='password']").val() == '' ) {
				$.messager.alert('提示','请输入密码');
				return false;
			}
			if( $("#addEmployeeForm input[name='passwordAlign']").val() == ''  ){
				$.messager.alert('提示','请再次输入密码');
				return false;
			}
			if(  $("#addEmployeeForm input[name='password']").val() !=  $("#addEmployeeForm input[name='passwordAlign']").val()) {
				$.messager.alert('提示','密码输入不一致');
				return false;
			}else{
				$.post("${ctx}/employee/addEmployee", {
					userName : $("#add_name").val(),
					realName : $("#addEmployeeForm input[name='realName']").val(),
					password : $("#addEmployeeForm input[name='password']").val(),
					role : $("#addEmployeeForm select[name='role']").val(),
					ucServerName : $("#addEmployeeForm input[name='ucServerName']").val(),
					ucServerPwd : $("#addEmployeeForm input[name='ucServerPwd']").val(),
					ucServerGroupId : $("#addEmployeeForm input[name='ucServerGroupId']").val(),
				}, function(data) {
					if (data.errorCode != 0) {
						if(data.errorCode==111){
							return false;
						}else{
							$.messager.alert("提示",data.message);
						}
					} else {
						$.messager.alert('提示','添加成功');
						$('#dlg').dialog('close');
						$('#mainfrom').datagrid({url:'${ctx}/employee/getEmployee'});
					}
				});
			}
			

		}
		function editEmployeeSubmit(){
			if(  $("#deit_employee input[name='password']").val() == '' ) {
				$.messager.alert('提示','请输入密码');
				return ;
			}
			if( $("#deit_employee input[name='passwordAlign']").val() == ''  ) {
				$.messager.alert('提示','请再次输入密码');
				return ;
			}
			if(  $("#deit_employee input[name='password']").val() !=  $("#deit_employee input[name='passwordAlign']").val()) {
				$.messager.alert('提示','密码输入不一致');
				return;
			}else{
				$.post("${ctx}/employee/updateEmployee",{
					password:$("#deit_employee input[name='password']").val(),
					role:$("#deit_employee select[name='role']").val(),
					id:$("#deit_employee input[name='employeeId']").val(),
					ucServerName : $("#deit_employee input[name='ucServerName']").val(),
					ucServerPwd : $("#deit_employee input[name='ucServerPwd']").val(),
					ucServerGroupId : $("#deit_employee input[name='ucServerGroupId']").val(),},
					function(data){
					if(data.errorCode!=0){
						$("#error").html(data.message);
					}else{
						$.messager.alert('提示','修改成功');
						$("#main_div").dialog("close");
						$('#mainfrom').datagrid({url:'${ctx}/employee/getEmployee'});
					}
				});
			}
		}
		$(document).ready(function() {
			$('#dlg').dialog('close');
		});
	</script>
</body>
</html>