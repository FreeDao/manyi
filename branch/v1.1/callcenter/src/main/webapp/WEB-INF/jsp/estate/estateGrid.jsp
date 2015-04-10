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
	<link rel="stylesheet" type="text/css" href="${ctx}/jqueryui/loadmask/loadmask.css">
	<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/jqueryui/loadmask/loadmask.js"></script>
	
</head>
<body onresize="resizeGrid();">
	
	<div id="search">
		<div id="searchFrm">
		<table spellcheck="false">
			<tr>
				<th>小区名称:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="estateName" id='estateName' style="width:160px;"></input>
				</td>
				<td style="width: 120px;" ></td>

				
			</tr>
			
			<tr>
				<th>城市:</th>
				<td>
					<select name="cityType" id="cityType" onchange="areaChange(this,'area')" style="width:130px;">
						<option value="0" selected="selected">全部</option>
						<!-- 
						<option value="2">上海</option>
						<option value="12217">北京</option>
						 -->
					</select>
				</td>
			</tr>
				
			<tr>
				<th>区域:</th>
				<td>
					<select  id="area" name="areaId" style="width:120px;" onchange="areaChange(this,'town');">
						<option value="0" parentid='0'>全部</option>
					</select>
				</td>
				<td style="width:120px;"></td>
			</tr>
			<tr>
				<th>板块:</th>
				<td>
					<select  id="town" name="townId" style="width:120px;">
						<option value="0" parentid='0'>全部</option>
					</select>
				</td>
			
			</tr>
			<tr>
				<td>
					<input type="button" value="查询" onclick="doSearch();"/>
				</td>
				<td>
					<input type="button" value="增加" onclick="doAdd();"/>
				</td>
			</tr>
		</table>
		</div>
	</div>
	
	<div id="add-user-div1" class="easyui-dialog add-user-div" title="添加小区" data-options="iconCls:'icon-save',modal:true," style="width:400px;height:300px;padding:10px;padding:3px 2px;border-bottom:1px solid #ccc;">
		<span id="error" style="color:red">${message }</span>
		<form action="${ctx}/estate/addEstate" method="post" id="addEstateForm">
			<table>
				<tr>
					<td>小区名称 ：</td>
					<td><input type="text" name="estateName" style="width:200px;" id="add_estateName" class='easyui-validatebox validatebox-text validatebox-invalid ' data-options="required:true"/> </td>
				</tr>
				<tr>
					<td>城市 ：</td>
					<td>
						<select  id="city-select" name="city" style="width:200px;" onchange="areaChangeDiag(this,'area-select');">
						 <option value="0" parentid='0'>-请选择-</option>
					    </select>
					</td>
				</tr>
				<tr>
					<td>区域 ：</td>
					<td>
						<select  id="area-select" name="area" style="width:200px;" onchange="areaChangeDiag(this,'plate-select');">
						 <option value="0" parentid='0'>-请选择-</option>
					    </select>
					</td>
				</tr>
				<tr>
					<td>板块 ：</td>
					<td>
						<select  id="plate-select" name="plate" style="width:200px;">
							<option value="0" parentid='0'>-请选择-</option>
					  	</select>
					</td>
				</tr>
				<tr>
					<td>小区地址 ：</td>
					<td><input type="text" name="areaRoad" id="area_road" style="width:200px;" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true"/>(多条以"/"分割)</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="button" value="提交" class="add-user-submit" onclick="addUserSubmit()"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div class="easyui-dialog edit-user-div" title="编辑小区" data-options="iconCls:'icon-save',modal:true," style="width:400px;height:300px;padding:10px;padding:3px 2px;border-bottom:1px solid #ccc;">
		<span id="error" style="color:red">${message }</span>
		<form action="${ctx}/estate/addEstate" method="post" id="editEstateForm">
			<table>
				<tr>
					<td>小区名称 ：</td>
					<td>
						<input type="text" name="editEstateName" style="width:200px;" id="add_estateName" class='editEstateName easyui-validatebox validatebox-text validatebox-invalid '/> 
						<input type="hidden" id="orgEstateName" class="orgEstateName" value="">
						<input type="hidden" id="orgEstateId" class="orgEstateId" value="">
					</td>
				</tr>
				<tr>
					<td>城市 ：</td>
					<td>
						<select  id="edit-city-select" name="area" style="width:200px;" onchange="areaChangeDiag(this,'area-select');">
					    </select>
					</td>
				</tr>
				<tr>
					<td>区域 ：</td>
					<td>
						<select  id="edit-area-select" name="area" style="width:200px;" onchange="areaChangeDiag(this,'plate-select');">
					    </select>
					</td>
				</tr>
				<tr>
					<td>板块 ：</td>
					<td>
						<select  id="edit-plate-select" name="plate" style="width:200px;">
					  	</select>
					</td>
				</tr>
				<tr>
					<td>小区地址 ：</td>
					<td><input type="text" name="editAreaRoad" id="edit_area_road" style="width:200px;" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true"/>(多条以"/"分割)</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="button" value="提交" class="edit-user-submit" onclick="editUserSubmit()"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<table id="mainfrom" > </table>
	
	<script type="text/javascript">
	
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
		if(areaName=='area' ){
			putOption('town','');
		}
	}
	
	function areaChangeDiag(obj,areaName){
		var area = $(obj);
		var parentId =area.val();
		var json =[];
		if(parentId == 1){}else{
			json = loadArea(parentId,false);
		}
		putOption(areaName,json);
		if(areaName=='area-select' ){
			putOption('plate-select','');
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
		添加下拉框的数据
	*/
	function putOption(key,json){
		if(!(json === undefined)){
			var str='';
			if(key == 'cityType'||  key == 'area' ||  key == 'town'||   json == null || json.length == 0 ){
				str ='<option value="0" parentid="0" selected="selected">全部</option>';
			}
			if( key == 'area-select' || key == 'city-select' || key == 'plate-select' || json == null || json.length == 0){
				str ='<option value="0" parentid="0" selected="selected">-请选择-</option>';
			}
			var sel=$("#"+key);
			sel.html('');
			for(var i =0 ; i< json.length ; i++){
				var row = json[i];
				str +='<option value="'+row.areaId+'" parentid="'+row.parentId+'">'+row.name+'</option>';
			}
			sel.html(str);
		}
	}
	
	$(document).ready(function() {
		$('.add-user-div').dialog('close');
		$('.edit-user-div').dialog('close');
	});
	
	function doAdd() {
		$('.add-user-div').dialog('open');
		//加载 城市 . parentId = 1 (中国)
		var json = loadArea('1',false);
		putOption("city-select",json);
		putOption("area-select",[]);
		putOption("plate-select",[]);
	}
	function initEstateDialog() {
		document.getElementById("add_estateName").value = "";
		document.getElementById("area_road").value = "";
		//$('#area-select option:last').attr('selected','selected'); 
	}
	//增加
	function addUserSubmit(){
		if(  $("#addEstateForm input[name='estateName']").val() == '' || $("#addEstateForm input[name='estateName']").val() == '0' ) {
			$.messager.alert('提示','请输入小区名称','error');
			return false;
		}
		if(  $("#addEstateForm select[name='city']").val() == '' || $("#addEstateForm select[name='city']").val() == '0'   ) {
			$.messager.alert('提示','请输入城市','error');
			return false;
		}
		if(  $("#addEstateForm select[name='area']").val() == '' || $("#addEstateForm select[name='area']").val() == '0'   ) {
			$.messager.alert('提示','请输入区域','error');
			return false;
		}
		if(  $("#addEstateForm select[name='plate']").val() == '' || $("#addEstateForm select[name='plate']").val() == '0'  ) {
			$.messager.alert('提示','请输入板块','error');
			return false;
		}
		if( $("#addEstateForm input[name='areaRoad']").val() == '' || $("#addEstateForm input[name='areaRoad']").val() == '0'  ){
			$.messager.alert('提示','请输入小区地址','error');
			return false;
		}else{
			$("#addEstateForm").mask('<span style="padding-right:10px">提交中... </span>',null,true);
			$.post("${ctx}/estate/addEstate", {
				estateName : $("#addEstateForm input[name='estateName']").val(),
				townId : $("#addEstateForm #plate-select").find("option:selected").val(),
				areaId : $("#addEstateForm #area-select").find("option:selected").val(),
				areaRoad : $("#addEstateForm input[name='areaRoad']").val()
			}, function(data) {
				$("#addEstateForm").unmask();
				if (data.errorCode != 0) {
					$.messager.alert('提示',data.message,'error');
				} else {
					//初始化Add Esteate Dialog 
					initEstateDialog();
					$('#add-user-div1').dialog('close'); 
					$.messager.alert('提示','添加成功');
					//datagrid 重新reload
					$('#mainfrom').datagrid({url:'${ctx}/estate/estateList'});
				}
			});
		}
	}
	
	//搜索
	function doSearch(){
		var pars="{";
		
		var cityType = $("#cityType").val();
		if(cityType != null && cityType !=''){
			pars +="'cityType' : '"+cityType+"',";
			
		}
		
		var areaId = $("#area").val();
		if(areaId !=0 && areaId !=''){
			pars +="'parentId' : '"+areaId+"',";
		}
		
		var townId = $("#town").val();
		if(townId !=0 && townId !=''){
			pars +="'areaId' : '"+townId+"',";
		}
		/* 
		var townId = $("#town").val();
		if(townId !=0 && townId !=''){
			pars +="'areaId' : '"+townId+"',";
			var p=$("#town option:selected").attr("parentid");//得到选中的option的 parentid	
			pars +="'parentId' : '"+ p +"',";
		} */
		
		var estateName=$("#estateName").val();
		if(estateName != null && estateName !=''){
			pars +="'estateName' : '"+estateName+"',";
		}
		if(pars.length >1){
			pars = pars.substr(0,pars.length-1);
		}
		pars += "}";
		pars = eval("("+pars+")");
	    $('#mainfrom').datagrid('reload',pars);
	} 
		
		var data_from = $('#mainfrom').datagrid({
			url : '${ctx}/estate/estateList',
			//fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
			striped : true, //True 就把行条纹化
			pagination : true,//分页
			rownumbers : true, //显示行号
			pageSize : 20,
			pageNumber : 1,
			pageList : [ 20, 30, 50, 100 ],//列表分页
			loadMsg : '数据正在努力加载中...',
			height: document.body.clientHeight * 0.88,
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [
			          {field : 'estateId',title : '小区编号',checkbox : true,width:$(this).width() * 0.1}//显示一个checkbox
					, {field : 'estateName',title : '小区名',align : 'center',resizable : true,hidden : false,editor:'text',sortable : false,width:$(this).width() * 0.1}
					, {field : 'cityName',title : '城市',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'areaName',title : '行政区',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'townName',title : '片区',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'sellNum',title : '在售房源',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'rentNum',title : '在租房源',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'houseNum',title : '房源总数',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'publishDateStr',title : '创建时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'opt',  title : '操作',align : 'center',resizable : true,hidden : false,sortable : false ,width:$(this).width() * 0.1,
						formatter : function(value,row,index){
			                if (row.editing){
			                    var s = '<a href="#" onclick="saverow(this)">保存</a> ';
			                    var c = '<a href="#" onclick="cancelrow(this)">取消</a>';
			                    return s+c;
			                } else {
			                    var e = '<a href="#" onclick="editrow(this)">编辑</a> ';
			                  //  var d = '<a href="#" onclick="deleterow(this)">删除</a>';
			                    return e;
			                }
			            }
					  }
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				//alert(rowData.estateId);
			}
		});
		
		function getRowIndex(target){
		    var tr = $(target).closest('tr.datagrid-row');
		    return parseInt(tr.attr('datagrid-row-index'));
		}
		function editrow(target){
		    //$('#mainfrom').datagrid('beginEdit', getRowIndex(target));
		    $('.edit-user-div').dialog('open');
		    var orgEstateName=$(target).closest('tr.datagrid-row').find('td:eq(1)').text();
		    var orgcity=$(target).closest('tr.datagrid-row').find('td:eq(2)').text();
		    var orgtown=$(target).closest('tr.datagrid-row').find('td:eq(3)').text();
		    var orgArea=$(target).closest('tr.datagrid-row').find('td:eq(4)').text();
		    var _estateId=$(target).closest('tr.datagrid-row').find('td:eq(0)').find('input').val();
		    $('.edit-user-div').find('.orgEstateName').val(orgEstateName);
		    $('.edit-user-div').find('.editEstateName').val(orgEstateName);
		    $('.edit-user-div').find('.orgEstateId').val(_estateId);
		    //显示之前先remove option列表
		     $("#edit-city-select option").remove();   
		    $("#edit-city-select").append("<option value='0'>"+orgcity+"</option>");
		    $("#edit-area-select option").remove();   
		    $("#edit-area-select").append("<option value='0'>"+orgtown+"</option>");
		    $("#edit-plate-select option").remove();  
		    $("#edit-plate-select").append("<option value='0'>"+orgArea+"</option>");
		    $.post("${ctx}/estate/detailEstate", {
		    	areaId : _estateId,
			}, function(data) {
				if (data.errorCode != 0) {
					$.messager.alert('错误',data.message,'error');
				} else {
					$('#edit_area_road').val(data.estateRoad);
				}
			});
		}
		
		function editUserSubmit(target){
			/* var orgEstateName=$('.edit-user-div').find('.orgEstateName').val(); */
			var estateId=$('.edit-user-div').find('.orgEstateId').val();
		    if(  $("#editEstateForm input[name='editEstateName']").val() == '' || $("#editEstateForm input[name='editEstateName']").val() == '0' ) {
				$.messager.alert('错误','请输入小区名称','error');
				return false;
			}
		   /*  if(orgEstateName == $("#editEstateForm input[name='editEstateName']").val()){
		    	$.messager.alert('错误','小区名称未变化','error');
				return false;
		    } */
			if( $("#editEstateForm input[name='editAreaRoad']").val() == '' || $("#editEstateForm input[name='editAreaRoad']").val() == '0'  ){
				$.messager.alert('错误','请输入小区地址','error');
				return false;
			}else{
				$("#editEstateForm").mask('<span style="padding-right:10px">提交中... </span>',null,true);
				$.post("${ctx}/estate/editEstate", {
					estateName : $("#editEstateForm input[name='editEstateName']").val(),
					areaRoad : $("#editEstateForm input[name='editAreaRoad']").val(),
					areaId : estateId,
				}, function(data) {
					$("#editEstateForm").unmask();
					if (data.errorCode != 0) {
						$.messager.alert('错误',data.message,'error');
					} else {
						$.messager.alert('提示','修改成功','info',function(){
							$('.edit-user-div').dialog('close'); 
						});
						//datagrid 重新reload
						$('#mainfrom').datagrid({url:'${ctx}/estate/estateList'});
					}
				});
			}
		}
			
		function deleterow(target){
		    $.messager.confirm('Confirm','Are you sure?',function(r){
		        if (r){
		            $('#mainfrom').datagrid('deleteRow', getRowIndex(target));
		        }
		    });
		}
		function saverow(target){
		    $('#mainfrom').datagrid('endEdit', getRowIndex(target));
		}
		function cancelrow(target){
		    $('#mainfrom').datagrid('cancelEdit', getRowIndex(target));
		}

		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth * 0.9;
				}
			});
		}
		$(function(){
			/* $('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth ;
				}
			}); */
			//加载 城市 . parentId = 1 (中国)
			var json = loadArea('1',false);
			putOption("cityType",json);
			//putOption("area-select",json);
		});
		
		
	</script>
</body>
</html>