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
	<div id="search">
		<div id="searchFrm">
		<table spellcheck="false">
			<tr>
				<th>经纪人真实姓名:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="realName" id='realName'></input>
				</td>
				<!-- <th>邀请人:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="spreandName" id='spreandName'></input>
				</td> -->
				<th>手机号码:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="mobile" id='mobile'></input>
				</td>
				<th>审核状态:</th>
				<td>
					<select  id="state" name="state" style="width:120px;">
						<option value="0" selected="selected">全部</option>
						<option value="1" >审核成功</option>
						<option value="2">审核中</option>
						<option value="3">审核失败</option>
					</select>
				</td>
				
			</tr>
			<tr>
				<th>城市:</th>
				<td>
					<select  id="cityId" name="cityId" style="width:120px;">
						<option value="0" parentid='0'>全部</option>
						<option value="2" parentid='2'>上海</option>
						<option value="12438" parentid='12438'>北京</option>
					</select>
				</td>
				<th>邀请人手机号:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="recommendedMobile" id='recommendedMobile'></input>
				</td>
				<td></td>
				<th>注册时间:</th>
				<td>
					<input class="easyui-datetimebox"  editable="false"  name="startRegistDate" id="startRegistDate" style="width:150px"></input> -
					<input class="easyui-datetimebox"   editable="false" name="endRegistDate" id="endRegistDate" style="width:150px"></input>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td>
					<input type="button" value="查询" onclick="selectUser()"/>
				</td>
			</tr>
		</table>
		</div>
	</div>
	<div id="redirectDiv" style="display:none">
		<form action="${ctx}/user/getUserById" method="post" id="redirectForm">
			<input name="uid" value="">
		</form>
	</div>
	
	<div class="easyui-tabs" style="width:auto;height:auto">
		<div title="经纪人管理">
			<table id="mainfrom" > </table>
		</div>
		<div title="推广人员管理">
			 <div id="toolbar">
				 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加</a>
			</div>
			<table id="userfrom" > </table>
			
			<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
				<div class="ftitle"></div>
					<form id="fm" method="post" novalidate>
						<div class="fitem">
							<label>手机号码:</label>
							<input name="mobile" class="easyui-validatebox" required="true" type="text" maxlength="11">
							</div>
						<div class="fitem">
							<label>真实姓名:</label>
							<input name="realName" class="easyui-validatebox" required="true" type="text">
						</div>
						<div class="fitem">
							<label>密码:</label>
							<input name="password" type="password" class="easyui-validatebox" required="true">
						</div>
						<div class="fitem">
							<label>区域:</label> 
							<select id="areaId" name="areaId" style="width: 120px;" onchange="areaChange(this);">
								<option value="0" parentid='0'>--请选择--</option>
							</select>
						</div>
					</form>
				</div>
				<div id="dlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
			</div>
		</div>
		
	</div>
	
	<script type="text/javascript">
		
		//window.parent.closedlg();
		var data_from = $('#mainfrom').datagrid({
			url : '${ctx}/user/getUser.rest?type=0',
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
			height: window.screen.availHeight * 0.645,
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [ 
			          {field : 'uid',checkbox : true,width:$(this).width() * 0.05}//显示一个checkbox
					, {field : 'realName',title : '经纪人',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'cityName',title : '城市',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
			        , {field : 'mobile',title : '手机号',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'areaName',title : '工作区域',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'spreadId',title : '邀请码',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'spreadName',title : '邀请人',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'stateStr',title : '审核状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'disableStr',title : '账号状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'createTimeStr',title : '注册时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'opt',title : '操作',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1,
						formatter : function (value,row,index)
						 {
							return "<a href='javascript:void(0);' onclick='view_btn("+row.uid+")'>查看</a>";
							
						 }
					
					}
			] ],
			
			/* onDblClickRow : function(rowIndex, rowData) {
				view_btn(rowData);
			}, */

			onLoadSuccess : function(data) {
				$(".datagrid-view").css("height", "88%");
			}

		});
		
		
		var data_from = $('#userfrom').datagrid({
			url : '${ctx}/user/getUser.rest?type=1',
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
			height: window.screen.availHeight * 0.62,
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [ 
			          {field : 'uid',checkbox : true,width:$(this).width() * 0.1}//显示一个checkbox
			          , {field : 'cityName',title : '城市',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'realName',title : '姓名',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'mobile',title : '手机号',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.2}
					, {field : 'spreadId',title : '邀请码',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'spreadName',title : '邀请人',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'stateStr',title : '审核状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'disableStr',title : '账号状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'createTimeStr',title : '注册时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					/* , {field : 'opt',title : '操作',align : 'center',resizable : true,hidden : false,sortable : false,
						formatter : function (value,row,index)
						 {
							return "<a href='javascript:void(0);' onclick='view_btn("+row.uid+")'>查看</a>";
						 }
					} */
			] ],
			
			/* onDblClickRow : function(rowIndex, rowData) {
				view_btn(rowData);
			}, */

			onLoadSuccess : function(data) {
				$(".datagrid-view").css("height", "88%");
			}

		});

		

		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth * 0.9;
				}
			});
		}
		
		function view_btn(row){
			$("#redirectForm input[name='uid']").val(row);
			$("#redirectForm").submit();
			//window.location.href="";
			/* alert(row);
			$.post("${ctx}/user/getUserById",{uid:row},function(data){
				alert(data.errorCode);
			},function(e){
				alert(e);
			}); */
			
			 /* var url1= "${ctx}/user/getUserById";
			var resultData={};
			$.ajax( {
				url : url1,
				data : {uid:row},
				async:false,
				method : 'POST',
				success : function(data) {
					//data =eval("("+data+")");
					resultData = data;
				},
				error : function(data) {
					alert(data);
				}
			});
			alert(resultData);*/
		}  
		
		 function selectUser(){
			 
			var pars = "{";

			var realName = $("#realName").val();
			if(realName!=null && realName!=""){
				pars += "'realName' : '" + realName + "',";
			}
			/* var spreandName = $("#spreandName").val();
			if(spreandName!=null && spreandName!=""){
				pars += "'spreandName' : '" + spreandName + "',";
			} */
			var cityId = $("#cityId").val();
			if(cityId!=null && cityId!=""){
				pars += "'cityId' : '" + cityId + "',";
			}
			var mobile = $("#mobile").val();
			if(mobile!=null && mobile!=""){
				pars += "'mobile' : '" + mobile + "',";
			}
			var recommendedMobile = $("#recommendedMobile").val();
			if(recommendedMobile!=null && recommendedMobile!=""){
				pars += "'recommendedMobile' : '" + recommendedMobile + "',";
			}
			var startRegistDate = $("#startRegistDate").datetimebox("getValue");
			if(startRegistDate!=null && startRegistDate!=""){
				pars += "'startRegistDate' : '" + startRegistDate + "',";
			}
			var endRegistDate = $("#endRegistDate").datetimebox("getValue");
			if(endRegistDate!=null && endRegistDate!=""){
				pars += "'endRegistDate' : '" + endRegistDate + "',";
			}
			var state = $("#state").val();
			pars += "'state' : '" + state + "',";
			pars += "}";
			pars = eval("(" + pars + ")");
			$('#mainfrom').datagrid('reload', pars);
			$("#realName").val('');
			$("#spreadName").val('');
		}

		function newUser() {
			$('#dlg').dialog('open').dialog('setTitle', '添加');
			$('#fm').form('clear');
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
			添加下拉框的数据
		*/
		function putOption(key,json){
			if(!(json === undefined)){
				var str='';
				var sel=$("#"+key);
				for(var i =0 ; i< json.length ; i++){
					var row = json[i];
					str +='<option value="'+row.areaId+'" parentid="'+row.parentId+'">'+row.name+'</option>';
				}
				sel.html(sel.html() + str);
			}
		}
		
		function saveUser() {
			if($("#fm input[name='mobile']").val()==""){
				$.messager.alert("温馨提示","请输入用户名");
				return false;
			}
			if($("#fm input[name='password']").val()==""){
				$.messager.alert("温馨提示","请输入密码");
				return false;
			}
			if($("#fm input[name='realName']").val()==""){
				$.messager.alert("温馨提示","请输入真实姓名");
				return false;
			}
			
			if(document.getElementById('areaId').value =="0" || document.getElementById('areaId').value ==""){
				$.messager.alert("温馨提示","请选择区域");
				return false;
			}
			var url = "${ctx}/user/addUser";
			var resultData = {};
			$.ajax({
				url : url,
				data : {
					mobile : $("#fm input[name='mobile']").val(),
					password : $("#fm input[name='password']").val(),
					realName : $("#fm input[name='realName']").val(),
					areaId :document.getElementById('areaId').value
				},
				async : false,
				method : 'POST',
				success : function(data) {
					//data =eval("("+data+")");
					resultData = data;
					if (resultData.errorCode == 0) {
						$('#dlg').dialog('close');
						$('#userfrom').datagrid({
							url : '${ctx}/user/getUser.rest?type=1'
						});
					} else {
						$.messager.alert("温馨提示", resultData.message);
					}
				}
			});
		}
		$(function(){
			$('#userfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth ;
				}
			});

			//加载上海 下面的 行政区域
			var json = loadArea('2',false);
			putOption("areaId",json);
		});
		
	</script>
</body>
</html>