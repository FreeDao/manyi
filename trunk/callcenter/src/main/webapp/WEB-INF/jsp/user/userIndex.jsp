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
							<label> 密  &nbsp; &nbsp; 码 :</label>
							<input name="password" type="text" class="easyui-validatebox" required="true">
						</div>
						<div class="fitem">
							<label> 城   &nbsp; &nbsp; 市 :</label> 
							<select id="cityType" name="cityType" style="width: 130px;" onchange="areaChange(this,'area');">
								<option value="0" parentid='0'>全部</option>
							</select>
						</div>
						<div class="fitem">
							<label> 区   &nbsp; &nbsp; 域 :</label> 
							<select id="area" name="areaId" style="width: 130px;" onchange="areaChange(this,'town');">
								<option value="0" parentid='0'>全部</option>
							</select>
						</div>
					</form>
				</div>
				<div id="dlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
			</div>
			
			
			
			<div id='main_div' title="编辑" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px;"  closed="true" >
				<span style="color:red">${message }</span>
				  <form  method="post"  id="deit_user">
				  	<input type="hidden" name="uid" value="uid">
				  	 <input type="hidden" id="areaId" name="areaId" value="areaId">
				  	<input type="hidden" id="cityId" name="cityId" value="cityId">
				  	<input type="hidden" id="areaName" name="areaName" value="areaName">
				  	<input type="hidden" id="cityName" name="cityName" value="cityName"/>
					密码：<input id="password" name="password" class='easyui-validatebox validatebox-text validatebox-invalid'  data-options="required:true" /><br/>
					城市：<select name="cityId" id="Province1">
							 <option value="0" selected="selected">全部</option>
						</select><br/>
					区域：<select name="areaId" id="City1">
						<option value="0" selected="selected">全部</option>
					</select><br/>
					
					<a href="javascript:void(0)" onclick="updateUser()" class="easyui-linkbutton">提交</a>
				  </form>
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
					, {field : 'areaName',title : '工作区域',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'realName',title : '姓名',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'mobile',title : '手机号',align : 'center', resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'spreadId',title : '邀请码',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'spreadName',title : '邀请人',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'stateStr',title : '审核状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'disableStr',title : '账号状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'createTimeStr',title : '注册时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					 , {field : 'opt',title : '操作',align : 'center',resizable : true,hidden : false,sortable : false,
						formatter : function (value,row,index)
						 {
							return "<a href='javascript:void(0);' onclick='editUser("+row.uid+")'>编辑</a>";
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

		

		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth * 0.9;
				}
			});
		}
		
		function editUser(){
			$('#formId').form('clear');
			var rows = $('#userfrom').datagrid('getSelections');//得到选中的行
			if (rows.length > 1) {
				$.messager.alert('温馨提示', '不能选择多行进行编辑！', 'info');
				return false;
			}
			/*  if (rows.length <= 0) {
				$.messager.alert('温馨提示', '请选择数据！', 'info');
				return false;
			}  */
			
			$("#main_div").find("input").each(function(p) {
				var self = $(this);
				self.val(rows[0][self.attr("name")]);
				//console.debug
				
			});
			
			/* $("#City1").append("<option value="+$("#deit_user input[name='areaId']").val()+" >"+$("#deit_user input[name='areaName']").val()+"</option>");
			$("#Province1").append("<option value="+$("#deit_user input[name='cityId']").val()+" >" +$("#deit_user input[name='cityName']").val()+ "</option>"); */
			
			$("#main_div").dialog("open");
		}
		
		function updateUser(row){
			if($("#deit_user input[name='password']").val()=="" || $("#deit_user input[name='password']").val()==null){
				$.messager.alert('温馨提示', '请输入密码');
				return false;
			}
			$.post("${ctx}/user/updateUserForBD",{
				uid:$("#deit_user input[name='uid']").val(),
				password:$("#deit_user input[name='password']").val(),
				cityId:$("#deit_user select[name='cityId']").val(),
				areaId:$("#deit_user select[name='areaId']").val()
				},function(data){
				if(data.errorCode!=0){
					$.messager.alert("温馨提示",data.message);
				}else{
					$.messager.alert("温馨提示","修改成功");
					$("#main_div").dialog("close");
					$('#userfrom').datagrid({url : '${ctx}/user/getUser.rest?type=1'});
				}
			});
			
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
			});*/
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
		function putOption(key,json){
			if(json != null){
				var str='';
				if( key =='cityType' || key =='area' || json == null || json.length == 0   ){
					str ='<option value="0" parentid="0" selected="selected">全部</option>';
				}
				var sel=$("#"+key);
				sel.html('');
				if(json != null) {
					for(var i =0 ; i< json.length ; i++){
						var row = json[i];
						/* if((key == 'area' ||  key == 'town') && i == 0){
							continue;
						} */
						str +='<option value="'+row.areaId+'" parentid="'+row.parentId+'">'+row.name+'</option>';
					}
				}
				sel.html(str);
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
			var cityId = document.getElementById('cityType').value;
			cityId =="" ? "0" : cityId;
			/* if(cityId =="0" || cityId ==""){
				$.messager.alert("温馨提示","请选择城市");
				return false;
			} */
			var areaId = document.getElementById('area').value;
			areaId =="" ? "0" : areaId;
			/* if(areaId =="0" || areaId ==""){
				$.messager.alert("温馨提示","请选择区域");
				return false;
			} */
			
			//alert("areaId :"+ areaId + "cityId :"+ cityId);
			//return ;
			var url = "${ctx}/user/addUser";
			var resultData = {};
			$.ajax({
				url : url,
				data : {
					mobile : $("#fm input[name='mobile']").val(),
					password : $("#fm input[name='password']").val(),
					realName : $("#fm input[name='realName']").val(),
					areaId : areaId,
					cityId : cityId
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
		
		
	/*mouseenter*/
	$(function(){
		$("#Province1").one("mouseenter",function(){
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
	</script>
</body>
</html>