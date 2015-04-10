<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html  >
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
				<th>楼栋ID:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="id" id='id' style="width:160px;"></input>
				</td>
				<th>小区ID:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="estateId" id='estateId' style="width:160px;"></input>
				</td>
				<th>子划分ID:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="subEstateId" id='subEstateId' style="width:160px;"></input>
				</td>
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
	
	//搜索
	function doSearch(){
		var pars="{";
		
		var id = $("#id").val();
		if(id != null && id !=''){
			pars +="'id' : '"+id+"',";
			
		}
		var subEstateId = $("#subEstateId").val();
		if(subEstateId != null && subEstateId !=''){
			pars +="'subEstateId' : '"+subEstateId+"',";
			
		}
		var estateId = $("#estateId").val();
		if(estateId != null && estateId !=''){
			pars +="'estateId' : '"+estateId+"',";
			
		}
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
		
		if(pars.length >1){
			pars = pars.substr(0,pars.length-1);
		}
		pars += "}";
		pars = eval("("+pars+")");
	    $('#mainfrom').datagrid('reload',pars);
	} 
		
		var data_from = $('#mainfrom').datagrid({
			url : '${ctx}/building/buildingList',
			//fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
			striped : true, //True 就把行条纹化
			pagination : true,//分页
			rownumbers : true, //显示行号
			pageSize : 20,
			pageNumber : 1,
			pageList : [ 20, 30, 50, 100 ],//列表分页
			loadMsg : '数据正在努力加载中...',
			height: document.body.clientHeight*3.2,
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [
			          {field : 'pk',checkbox : true,width:$(this).width() * 0.03}//显示一个checkbox
					, {field : 'id',title : '栋座ID',align : 'center',resizable : true,hidden : false,editor:'text',sortable : false,width:$(this).width() * 0.05}
					, {field : 'name',title : '栋座编号',align : 'center',resizable : true,hidden : false,editor:'text',sortable : false,width:$(this).width() * 0.05}
					, {field : 'estateId',title : '小区ID',align : 'center',resizable : true,hidden : false,editor:'text',sortable : false,width:$(this).width() * 0.05}
					, {field : 'estateName',title : '小区名称',align : 'center',resizable : true,hidden : false,editor:'text',sortable : false,width:$(this).width() * 0.1}
					, {field : 'subEstateId',title : '子划分ID',align : 'center',resizable : true,hidden : false,editor:'text',sortable : false,width:$(this).width() * 0.05}
					, {field : 'subEstateName',title : '子划分名称',align : 'center',resizable : true,hidden : false,editor:'text',sortable : false,width:$(this).width() * 0.1}
					, {field : 'sellNum',title : '在售房源',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'rentNum',title : '在租房源',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'houseNum',title : '房源总数',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'opt',  title : '操作',align : 'center',resizable : true,hidden : false,sortable : false ,width:$(this).width() * 0.1,
						formatter : function(value,row,index){
							 var e = '<a href="${ctx}/building/editBuildingShow?id='+row.id+'" target="blank">查看详情</a> ';
			                 return e;
			            }
					  }
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				//alert(rowData.estateId);
			}
		});
		function doAdd() {
			$.messager.alert('提示','功能未开放.');
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