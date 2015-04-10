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
		.verifyEstate{
			margin-left:5px;
			margin-top:5px;
		}
		.verifySpan{
			font-weight:bold;
		}
	</style>
</head>
<body onresize="resizeGrid();">
	<div id="verifyEstate" class="easyui-dialog" style="width:505px;height:320px"
		data-options="title:'审核',modal:true,closed:true,cache:false,
			buttons:[{
				text:'审核通过',
				iconCls:'icon-ok',
				handler:function(){
					verifyOK();
				}
			},{
				text:'审核失败',
				iconCls:'icon-no',
				handler:function(){
					verifyNO();
				}
			}]">
		<span id="error" style="color:red">${message }</span>
		<div>
			<div class="verifyEstate">
				<span class="verifySpan">小区名:</span>
				<span id="verifyEstateName"></span>
			</div>
			<div class="verifyEstate">
				<span class="verifySpan">区域:</span>
				<span id="verifyCityName"></span>
			</div>
			<div class="verifyEstate">
				<span class="verifySpan">板块:</span>
				<span id="verifyTownName"></span>
			</div>
			<div class="verifyEstate">
				<span class="verifySpan">小区地址:</span>
				<span id="verifyEstateRoadName"></span>
			</div>
			<div class="verifyEstate">
				<span class="verifySpan" style="vertical-align:top;">理由:</span>
				<span>
					<input type="hidden" id="verifyEstateId" name="verifyEstateId" value=""/>
					<textarea id="verifyReason" rows="10" cols="60" style="color:#DFDFDF" onmousedown="verifyEvent()" >输入审核通过或者失败理由</textarea>
				</span>
			</div>
		</div>
	</div>
	<div id="search">
		<div id="searchFrm">
		<table spellcheck="false">
			<tr>
				<th>小区名称:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="estateName" id='estateName' style="width:160px;"></input>
				</td>
				<td style="width: 120px;" ></td>
				<th>审核状态:</th>
				<td>
					<select  id="sourceState" name="sourceState" style="width:120px;">
						<option value="0" selected="selected">全部</option>
						<option value="2">未审核</option>
						<option value="1">审核成功</option>
						<option value="3">审核失败</option>
					</select>
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
			</tr>
		</table>
		</div>
	</div>
	
	<table id="mainfrom" > </table>
	
	<script type="text/javascript">
	/*
	/**
	初始化一些信息
	*/
	function initTextArea() {
		document.getElementById("verifyReason").value = "输入审核通过或者失败理由";
		document.getElementById("verifyReason").style.color="#DFDFDF";
		$('#error').text("");
	}
	/**
	*
	*/
	function initVerifyEstateMessage(row) {
		$('#verifyEstateName').text(row.estateName);
		$('#verifyCityName').text(row.areaName);
		$('#verifyTownName').text(row.townName);
		$('#verifyEstateRoadName').text(row.road);
	}
	function CreateObject(logId,estateName,areaName,townName,road)  
	{  
	  this.logId = logId;
	  this.estateName =estateName;  
	  this.areaName = areaName;  
	  this.townName = townName;  
	  this.road = road;
	}  
	function packageObject(row) {
		//if(row.logId)
		var object = CreateObject(row.logId,row.estateName,row.areaName,row.townName,row.road);
		return object;
	}
	/**
	*审核成功
	*/
	function verifyOK() {
		$.post("${ctx}/estate/estateVerify", {
			estateId : $("#verifyEstateId").val(),
			remark :  $("#verifyReason").val(),
			verify : 'ok'
		}, function(data) {
			if (data.errorCode != 0) {
				$("#error").html(data.message);
			} else {
				$.messager.alert('提示','审核成功');
				$('#verifyEstate').dialog('close');
				initTextArea();
				$('#mainfrom').datagrid({url:'${ctx}/estate/estateLogList'});
			}
		});
	}
	/**
	*审核失败
	*/
	function verifyNO() {
		$.post("${ctx}/estate/estateVerify", {
			estateId : $("#verifyEstateId").val(),
			remark :  $("#verifyReason").val(),
			verify : 'no'
		}, function(data) {
			if (data.errorCode != 0) {
				$("#error").html(data.message);
			} else {
				$.messager.alert('提示','审核失败成功');
				$('#verifyEstate').dialog('close');
				initTextArea();
				$('#mainfrom').datagrid({url:'${ctx}/estate/estateLogList'});
			}
		});
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
		if(areaName=='area' ){
			putOption('town','');
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
	function verifyEvent() {
		var verifyValue = document.getElementById("verifyReason").value;
		if("输入审核通过或者失败理由"==verifyValue) {
			document.getElementById("verifyReason").value = "";
			document.getElementById("verifyReason").style.color="#000000";
		}

	}
	
	/*
		添加下拉框的数据
	*/
	function putOption(key,json){
		if(json != null){
			var str='';
			if(key == 'cityType'||  key == 'area' ||  key == 'town'||   json == null || json.length == 0 ){
				str ='<option value="0" parentid="0" selected="selected">全部</option>';
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
	var flag = false;	
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
			var sellState = $("#sourceState").val();
			if(sellState != null && sellState != '' && sellState !=0){
				pars +="'sourceState' : '"+sellState+"',";
			}
			if(pars.length >1){
				pars = pars.substr(0,pars.length-1);
			}
			pars += "}";
			pars = eval("("+pars+")");
		    $('#mainfrom').datagrid('reload',pars);
		} 
		
		var data_from = $('#mainfrom').datagrid({
			url : '${ctx}/estate/estateLogList',
			//fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
			striped : true, //True 就把行条纹化
			pagination : true,//分页
			rownumbers : true, //显示行号
			nowrap :  false,
			pageSize : 20,
			pageNumber : 1,
			pageList : [ 20, 30, 50, 100 ],//列表分页
			loadMsg : '数据正在努力加载中...',
			height: document.body.clientHeight * 1.35,
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [
			          {field : 'logId',title : '编号',checkbox : true,width:$(this).width() * 0.1}//显示一个checkbox
					, {field : 'estateName',title : '小区名',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.2}
					, {field : 'road',title : '小区地址',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'cityName',title : '城市',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'areaName',title : '行政区',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'townName',title : '片区',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'sourceStateStr',title : '审核状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'publishName',title : '发布人',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'publishDateStr',title : '发布时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'opt',  title : '操作',align : 'center',resizable : true,hidden : false,sortable : false ,width:$(this).width() * 0.05,
						formatter : function (value,row,index)
						 {
							if(row.sourceStateStr=="未审核") {
								return "<a href='javascript:void(0);' onclick='view_btn1("+row.logId+");'>审核</a>";
							}
						 }
					   }

			] ],
			onClickRow : function(rowIndex, rowData) {
				if(rowData.sourceStateStr=="未审核" && flag) {
					view_btn(rowData); 
				}
			},
			onDblClickRow : function(rowIndex, rowData) {
				if(rowData.sourceStateStr=="未审核") {
					view_btn(rowData); 
				}
			}

		});
		function view_btn1(logId) {
			initTextArea();
			flag = true;
		}
		/*
		查看
		 */
		function view_btn(row) {
			flag = false;
			document.getElementById("verifyEstateId").value=row.logId;
			//初始化信息
			initVerifyEstateMessage(row);
			$('#verifyEstate').dialog('open');
		}

		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth * 0.9;
				}
			});
		}
		$(function(){
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth ;
				}
			});
			//加载上海 下面的 行政区域
			var json = loadArea('1',false);
			putOption("cityType",json);
		});
		
	
	</script>
</body>
</html>