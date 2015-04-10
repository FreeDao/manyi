<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 

response.setHeader("Pragma","No-cache"); 

response.setHeader("Cache-Control","no-cache"); 

response.setDateHeader("Expires", 0); 

%> 


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
	
	#addEmployeeForm label , #main_div label{
		width: 130px;
		display: block;
		text-align:right;
		float: left;
	}
	.frm-floot{
		padding-top:8px;
		padding-left:130px;
	} 
	</style>
</head>
<body onresize="resizeGrid();">
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addEmployee()">添加帐号</a>
	<div id="dlg" class="easyui-dialog" title="添加帐号" data-options="iconCls:'icon-save'" style="width:400px;height:350px;padding:10px;">
			<span id="error" style="color:red">${message }</span>
		<form action="${ctx}/employee/addEmployee" method="post" id="addEmployeeForm">
				<label>账户名 ：</label><input type="text" name="userName" id="add_name" class='easyui-validatebox validatebox-text validatebox-invalid ' data-options="required:true"/><br> 
				<label>姓名 ：</label><input type="text" name="realName" id="add_realName" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true"/><br> 
				<label>密码 ：</label><input type="password" name="password" id="add_password" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true" /><br> 
				<label>确认密码 ：</label><input type="password" name="passwordAlign" id="passwordAlign" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true"/><br> 
				<label>角色 ：</label><select name="role" id="add_role" style="width:130px;">
					<option value="3" selected="selected">客服专员</option>
					<option value="2" >客服经理</option>
					<option value="4">地推经理</option>
					<option value="5">地推专员</option>
					<option value="6">财务</option>
					<option value="7">运营经理</option>
					<option value="8">运营专员</option>
				</select><br/>
			<label>ucServerName ：</label><input type="text" name="ucServerName" id="ucServerName" class='easyui-validatebox validatebox-text validatebox-invalid '/><br> 
			<label>ucServerPwd ：</label><input type="text" name="ucServerPwd" id="ucServerPwd" class="easyui-validatebox validatebox-text validatebox-invalid"><br>	
			<label>ucServerGroupId ：</label><input type="text" name="ucServerGroupId" id="ucServerGroupId" class='easyui-validatebox validatebox-text validatebox-invalid'/><br>
			<!-- 城市：<select name="areaId" id="Province">
					<option value="0" selected="selected">全部</option>
				</select><br/>
			区域：<select name="cityId" id="City">
				<option value="0" selected="selected">全部</option>
			</select><br/> -->




			<label> 城 市  ：</label>
			<select id="cityType" name="areaId" style="width: 130px;" onchange="areaChange(this,'area');">
				<option value="0" parentid='0'>全部</option>
			</select> <br/>
			<label> 区 域 ：</label>
			<select id="area" name="cityId" style="width: 130px;" onchange="areaChange(this,'town');">
				<option value="0" parentid='0'>全部</option>
			</select> <br/>
			<div class="frm-floot">
				<a href="javascript:void(0)" onclick="addEmployeeSubmit()" class="easyui-linkbutton">提交</a>
				<a href="javascript:void(0)"  onclick='javascript:$("#dlg").dialog("close");' class="easyui-linkbutton">关闭</a>
			</div>
				
		</form>
	</div>
	<table id="mainfrom" > </table>
	
	<div id='main_div' title="编辑" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px;"  closed="true" >
		<span style="color:red">${message }</span>
	  <form  method="post"  id="deit_employee">
	  	<input type="hidden" name="roleStr" value="role">
	  	<input type="hidden" name="employeeId" value="employeeId">
	  	<input type="hidden" id="areaId" name="areaId" value="areaId">
	  	<input type="hidden" id="cityId" name="cityId" value="cityId">
	  	<input type="hidden" id="areaName" name="areaName" value="areaName">
	  	<input type="hidden" id="cityName" name="cityName" value="cityName"/>
		<label>账户名 ： </label><input  id="userName" name ='userName' class='easyui-validatebox validatebox-text validatebox-invalid'   readonly="readonly"/><br/>
		<label>姓名 ： </label><input  id="realName" name ='realName' class='easyui-validatebox validatebox-text validatebox-invalid'   readonly="readonly" /><br/>
		<label>密码 ：</label><input id="password" name="password" class='easyui-validatebox validatebox-text validatebox-invalid' type="password" data-options="required:true" /><br/>
		<label>确认密码 ：</label><input id="passwordAlign" name="passwordAlign" class='easyui-validatebox validatebox-text validatebox-invalid' type="password" data-options="required:true"/><br/>
		<label>角色 ：</label><select id="role" name="role" style="width:130px;">
					<option value="3">客服专员</option>
					<option value="2" >客服经理</option>
					<option value="4">地推经理</option>
					<option value="5">地推专员</option>
					<option value="6">财务</option>
					<option value="7">运营经理</option>
					<option value="8">运营专员</option>
				</select><br/>
		<label>	ucServerName ：</label><input type="text" name="ucServerName" id="ucServerName" class='easyui-validatebox validatebox-text validatebox-invalid '/><br> 
		<label>ucServerPwd ：</label><input type="text" name="ucServerPwd" id="ucServerPwd" class='easyui-validatebox validatebox-text validatebox-invalid'/><br>
		<label>	ucServerGroupId ：</label><input type="text" name="ucServerGroupId" id="ucServerGroupId" class='easyui-validatebox validatebox-text validatebox-invalid'/><br>
		<label>	城市 ：</label><select name="areaId" id="Province1" style="width:130px;">
					<!--  <option value="0" selected="selected">全部</option> --> 
				</select><br/>
		<label>	区域 ：</label><select name="cityId" id="City1" style="width:130px;">
				<!-- <option value="0" selected="selected">全部</option> --> 
			</select><br/>
			
			<div class="frm-floot">
			<a href="javascript:void(0)" onclick="editEmployeeSubmit()" class="easyui-linkbutton">提交</a>
			<a href="javascript:void(0)"  onclick='javascript:$("#main_div").dialog("close");' class="easyui-linkbutton">关闭</a>
			</div>
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
					, {field : 'cityName',title : '城市',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'areaName',title : '行政区',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'userName',title : '账户名',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'realName',title : '姓名',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'roleStr',title : '角色',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'role',title : '角色',align : 'center', resizable : true,hidden : true,sortable : false,width:$(this).width() * 0.1}
					, {field : 'disabledStr',title : '账户状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'ucServerName',title : 'ucServerName',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'ucServerPwd',title : 'ucServerPwd',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'ucServerGroupId',title : 'ucServerGroupId',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'areaId',title : 'areaId',align : 'center',resizable : true,hidden : true,sortable : false,width:$(this).width() * 0.1}
					, {field : 'cityId',title : 'cityId',align : 'center',resizable : true,hidden : true,sortable : false,width:$(this).width() * 0.1} 
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
			//$("#deit_employee").form('clear');
			//$(':input','#deit_employee').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('select');
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
				
				//$("#City1").append("<option value="+$("#deit_employee input[name='areaId']").val()+" >"+$("#deit_employee input[name='areaName']").val()+"</option>");
				//$("#Province1").append("<option value="+$("#deit_employee input[name='cityId']").val()+" >" +$("#deit_employee input[name='cityName']").val()+ "</option>");
				
				var aid = $("#deit_employee input[name='areaId']").val();
				var cid = $("#deit_employee input[name='cityId']").val();
				
				initArea(cid,aid);
				
			}
			//$("#main_div").show();
			$("#main_div").dialog("open");
		}
		
		//初始化加载 小区 所属区域
		function initArea(cityId,areaId){
			var city_json = loadArea("1",false);// 中国下的城市
			putOption("Province1",city_json,cityId);//加载城市
			if(city_json != null && city_json.length>0){
				var area_json = loadArea(cityId,false);
				putOption("City1",area_json,areaId);//加载行政区
			}
		}
		
		$('#main_div').dialog({
		    onClose:function(){
		    	//window.location.href = "${ctx}/employee/employeeIndex";
		    }
		});
		$('#dlg').dialog({
		    onClose:function(){
		    	/* $("#addEmployeeForm select[name='areaId']").option.val('');
		    	$("#addEmployeeForm select[name='cityId']").option.val(''); */
		    }
		});
		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth * 0.9;
				}
			});
		}

		function addEmployee() {
			newUser();
			$("#addEmployeeForm")[0].reset();
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
					areaId:$("#addEmployeeForm select[name='areaId']").val(),
					cityId:$("#addEmployeeForm select[name='cityId']").val(),
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
			//$("#deit_employee").clear();
			//$(':input','#deit_employee').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
			
			var password = $.trim($("#deit_employee input[name='password']").val());
			var password2 = $.trim($("#deit_employee input[name='passwordAlign']").val());
			if( password != '' || password2 != '') {
				if(  password !=  password2) {
					$.messager.alert('提示','密码输入不一致');
					return false;
				}
			}
			
			$.post("${ctx}/employee/updateEmployee",{
				password:$("#deit_employee input[name='password']").val(),
				role:$("#deit_employee select[name='role']").val(),
				id:$("#deit_employee input[name='employeeId']").val(),
				ucServerName : $("#deit_employee input[name='ucServerName']").val(),
				ucServerPwd : $("#deit_employee input[name='ucServerPwd']").val(),
				ucServerGroupId : $("#deit_employee input[name='ucServerGroupId']").val(),
				areaId:$("#deit_employee select[name='cityId']").val(),
				cityId:$("#deit_employee select[name='areaId']").val(),
				},
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
		
		$(document).ready(function() {
			$('#dlg').dialog('close');
		});
		
		
		
		/*
			添加下拉框的数据
		*/
		/* $(function(){
			$("#Province").one("mouseenter",function(){
				var url="${ctx}/employee/getProvince";
				 $.post(url,function(data){
					 for(var i=0;i<data.list.length;i++){
						$("#Province").append("<option value="+data.list[i].areaId+" >"+data.list[i].name+"</option>");
					 }
				});	 
			});	
		});
		$(function(){
			$("#Province").change(function(){
				$("#City").empty();
				var areaId = $("#Province").val();
				var url="${ctx}/employee/getCity";
				if(areaId!=0){
					$.post(url,{parentId:areaId},function(data){
						 for(var i=0;i<data.list.length;i++){
							$("#City").append("<option value="+data.list[i].areaId+" >"+data.list[i].name+"</option>");
						  }
					});
				}else{
					$("#City").append("<option value="+0+" > 全部 </option>");
				}
			});
		}); */
		
		/*mouseenter*/
		$(function(){
			$("#Province1").one("click",function(){
				$("#Province1").empty();
				var url="${ctx}/employee/getProvince";
				 $.post(url,function(data){
					 $("#Province1").append("<option value="+0+" > 全部 </option>");
					 for(var i=0;i<data.list.length;i++){
						$("#Province1").append("<option value="+data.list[i].areaId+" >"+data.list[i].name+"</option>");
					 }
				});	 
			});	
		});
		$(function(){
			$("#Province1").change(function(){
				$("#City1").empty();
				var areaId = $("#Province1").val();
				var url="${ctx}/employee/getCity";
				if(areaId!=0){
					$.post(url,{parentId:areaId},function(data){
						$("#City1").append("<option value="+0+" > 全部 </option>");
						 for(var i=0;i<data.list.length;i++){
							$("#City1").append("<option value="+data.list[i].areaId+" >"+data.list[i].name+"</option>");
						  }
					});
				}else{
					$("#City1").append("<option value="+0+" > 全部 </option>");
				}
			});
		});
		
		
		
		
		function newUser() {
			//$('#dlg').dialog('open').dialog('setTitle', '添加');
			$('#fm').form('clear');
			//加载 中国下面的 城市  中国ID = 1
			var json = loadArea('1',false);
			putOption("cityType",json);
			if(json != null && json.length >0){
				//加载 行政区
				json = loadArea(json[0].areaId,false);
				putOption("area",json);
			}
		}

		//加载 区域板块
		function loadArea(parentId,isAll){
			
			/*
			 * 加载 数据字典/地区
			 */
			var url1= "${ctx}/house/loadAreaByParentId";
			var resultData={};
			$.ajax( {
				url : url1,
				data : {'parentId' : parentId, 'flag' : isAll},
				async:false,
				method : 'POST',
				success : function(data) {
					//data =eval("("+data+")");
					resultData = data.areaList;
				},
				error : function(data) {
					
				}
			});
			return resultData;
		}
		
		/*
		上海行政区域转化
		*/
		function areaChange(obj,areaName){
			var area = $(obj);
			var parentId =area.val();
			var json =[];
			if(parentId == 1){}else{
				json = loadArea(parentId,false);
			}
			putOption(areaName,json);
			if(areaName=='cityType'){
				putOption('area','');
			}
		}
		
		/*
			添加下拉框的数据
		*/
		function putOption(key,json,selectId){
			if(json != null){
				var str='';
				if( key =='Province1' || key =='City1' || json == null || json.length == 0   ){
					str ='<option value="0" parentid="0" selected="selected">全部</option>';
				}
				var sel=$("#"+key);
				sel.html('');
				if(json != null) {
					for(var i =0 ; i< json.length ; i++){
						var row = json[i];
						str +='<option value="'+row.areaId+'" parentid="'+row.parentId+'" ';
						if(selectId == row.areaId){
							str +=' selected="selected" ';
						}
						str +=' >'+row.name+'</option>';
					}
				}
				sel.html(str);
			}
		}
	</script>
</body>
</html>