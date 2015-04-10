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
		<form action="">
		<table spellcheck="false">
			<tr>
				<th>城市:</th>
				<td>
					<select name="cityType" id="cityType" onchange="areaChange(this,'area')" style="width:120px;">
						<option value="0" selected="selected">全部</option>
						<option value="2">上海</option>
						<option value="12217">北京</option>
					</select>
				</td>
				<th>小区名称:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="estateName" id='estateName' style="width:120px;"></input>
				</td>
				<th style="width:100px;">租售状态:</th>
				<td>
					<select  id="houseState" name="houseState" >
						<option value="0" selected="selected">全部</option>
						<option value="1">出租</option>
						<option value="2">出售</option>
						<option value="3">租售</option>
					</select>
				</td>
				
				<th style="width:100px;">发布人:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="userName" id='userName' style="width:120px;"></input>
				</td>
				
			</tr>
			
			<tr>
				<th>区域:</th>
				<td>
					<select  id="area" name="areaId" style="width:120px;" onchange="areaChange(this,'town');">
						<option value="0" parentid='0'>全部</option>
					</select>
				</td>
				<th style="width:100px;"> 看房审核状态:</th>
				<td colspan="6" id="taskState-td">
					<!-- 1任务开启(电话预约中) 2预约失败 3再次预约,4预约成功（看房任务开启） 5看房失败 6看房成功-->
					<input type="checkbox" name="taskState" id="cs1" value="1"/>
					<label for="cs1">未预约</label>
					<input type="checkbox" name="taskState" id="cs2" value="2"/>
					<label for="cs2">预约失败</label>
					<input type="checkbox" name="taskState" id="cs3" value="3"/>
					<label for="cs3">再预约</label>
					<input type="checkbox" name="taskState" id="cs4" value="4"/>
					<label for="cs4">待看房</label>
					<input type="checkbox" name="taskState" id="cs5" value="5"/>
					<label for="cs5">看房失败</label> 
					<input type="checkbox" name="taskState" id="cs6" value="6"/>
					<label for="cs6">看房成功</label>
				</td>
				<td></td>
				
				
			</tr>
			<tr>
				<th>板块:</th>
				<td>
					<select  id="town" name="townId" style="width:120px;">
						<option value="0" parentid='0'>全部</option>
					</select>
				</td>
				
				
				<th colspan="1"> 看房负责人:</th>
				<td colspan="1">
					<input class="easyui-validatebox textbox" type="text" name="employeeName" id='employeeName' style="width:120px;"></input>
				</td>
				<th> 看房时间:</th>
				<td colspan="3">
					<input class="easyui-datetimebox" editable="false" name="taskDateStart" id='taskDateStart' data-options="required:false,showSeconds:true" style="width:130px"> - 
					<input class="easyui-datetimebox" editable="false" name="taskDateEnd" id="taskDateEnd" data-options="required:false,showSeconds:true" style="width:130px">
				</td>
				
				<td></td>
				
			</tr>
			<tr>
				<td>
					<input type="button" value="查询" onclick="doSearch();"/>
				</td>
				<td><input type="reset" value="重置" onclick="restFrom()"/></td>
			</tr>

		</table>
		</form>
		</div>
	</div>
	
	<table id="mainfrom" > </table>
	
	<script type="text/javascript">
	function restFrom(){
		$("#taskDateStart").datetimebox("setValue","");
		$("#taskDateEnd").datetimebox("setValue","");
	}
	
	function closeDialog(){
		$('#temp_div').window('close');
		$('#mainfrom').datagrid('reload');
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
	
	/*
		添加下拉框的数据
	*/
	function putOption(key,json){
		if(json != null){
			var str='';
			if( key == 'cityType'||  key == 'area' ||  key == 'town'||   json == null || json.length == 0   ){
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
		
	//搜索
		function doSearch(){
			var pars="{";
			var cityType = $("#cityType").val();
			if(cityType != null && cityType !=''){
				pars +="'cityType' : '"+cityType+"',";
				
			}
			

			/*
			var picNum = $("#picNum").val();
			if(picNum !=0 && picNum !=''){
				pars +="'picNum' : '"+picNum+"',";
			}
			*/
			
			var areaId = $("#area").val();
			if(areaId !=0 && areaId !=''){
				pars +="'parentId' : '"+areaId+"',";
			}
			
			var townId = $("#town").val();
			if(townId !=0 && townId !=''){
				pars +="'areaId' : '"+townId+"',";
			}
			var estateName=$("#estateName").val();
			if(estateName != null && estateName !=''){
				pars +="'estateName' : '"+estateName+"',";
			}
			
			var empName=$("#employeeName").val();
			if(empName != null && empName !=''){
				pars +="'employeeName' : '"+empName+"',";
			}
			
			var userName=$("#userName").val();
			if(userName != null && userName !=''){
				pars +="'userName' : '"+userName+"',";
			}
			
			var sellState = $("#houseState").val();
			if(sellState != null && sellState != '' && sellState !=0){
				pars +="'houseState' : '"+sellState+"',";
			}

			var operCustServiceState = '';
			//var operFloorServiceState = '';
			var custs = document.getElementsByName("taskState");
			if(custs != null && custs !='' && custs.length>0){
				for(var i = 0; i <custs.length; i++){
					if(custs[i].checked){
						operCustServiceState += custs[i].value+",";
					}
				}
				
			}
			if(operCustServiceState.length >0){
				operCustServiceState = operCustServiceState.substr(0,operCustServiceState.length-1);
			}
			if(operCustServiceState != ''){
				pars +=" 'taskState' : '"+operCustServiceState+"',";
			}
			
			var t =$("#taskDateStart").datetimebox("getValue");
			if(t != null && t !=''){
				pars +=" 'taskDateStart' : '" + t+"' ,";
			}
			
			var t1 =$("#taskDateEnd").datetimebox("getValue");
			if(t1 != null && t1 !=''){
				pars +=" 'taskDateEnd' : '" + t1+"' ,";
			}
	
			if (t != '' && t1 != '') {
				if (t > t1) {
					$.messager.alert("温馨提示", "起始时间不能大于截止时间!", "warning");
					$('#taskDateStart').datetimebox('setValue', '');
					$('#taskDateEnd').datetimebox('setValue', '');
					return;
				}
			}

			if (pars.length > 1) {
				pars = pars.substr(0, pars.length - 1);
			}
			pars += "}";
			pars = eval("(" + pars + ")");
			$('#mainfrom').datagrid('reload', pars);
		}

		var data_from = $('#mainfrom').datagrid(
						{
							url : '${ctx}/lookHouse/lookHouseList',
							//fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
							striped : true, //True 就把行条纹化
							pagination : true,//分页
							rownumbers : true, //显示行号
							pageSize : 20,
							pageNumber : 1,
							pageList : [ 20, 30, 50, 100 ],//列表分页
							loadMsg : '数据正在努力加载中...',
							height : document.body.clientHeight * 4.2,
							selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
							checkOnSelect : true, //点击 checkbox 同时触发 点击 行
							columns : [ [
									{
										field : 'ck',
										checkbox : true,
										width : $(this).width() * 0.05
									}//显示一个checkbox
									,
									{
										field : 'id',
										title : '任务编号',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.05
									},
									{
										field : 'cityName',
										title : '城市',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.05
									},
									{
										field : 'areaName',
										title : '行政区',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.05
									},
									{
										field : 'townName',
										title : '片区',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.05
									},
									{
										field : 'estateName',
										title : '小区名',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.1
									},
									{
										field : 'builing',
										title : '栋座号',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.03
									},
									{
										field : 'room',
										title : '室号',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.03
									},
									{
										field : 'houseStateStr',
										title : '租售状态',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.05
									},
									{
										field : 'employeeName',
										title : '看房负责人',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.05
									},
									{
										field : 'taskDateStr',
										title : '看房时间',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.08
									},
									{
										field : 'taskStateStr',
										title : '当前看房状态',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.05
									},
									{
										field : 'picNum',
										title : '图片情况',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.05
									},
									{
										field : 'userName',
										title : '发布人',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.03
									},
									{
										field : 'publishDateStr',
										title : '发布时间',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.08
									},
									{
										field : 'addTaskDateStr',
										title : '新增时间',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.08
									},
									{
										field : 'opt',
										title : '操作',
										align : 'center',
										resizable : true,
										hidden : false,
										sortable : false,
										width : $(this).width() * 0.03,
										formatter : function(value, row, index) {
											return "<a href='javascript:void(0);' onclick='view_btn("+ row.id + ")'>查看</a>";
										}
									}

							] ],
							onDblClickRow : function(rowIndex, rowData) {
								//alert(rowData.houseId);
								//view_btn(rowData.id);
							}

						});

		/*
		查看
		 */
		function view_btn(taskId) {
			//alert(taskId);
			//window.location.href="${ctx}/lookHouse/single?id="+taskId;
			$('#temp_div').html("");
			$('#temp_div').append("<iframe style='width:1280px;height:500px;' src='${ctx}/lookHouse/single?id="+taskId+"'></iframe>");
			$('#temp_div').window('open');
		}

		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth * 0.9;
				}
			});
		}
		$(function() {
			/* $('#mainfrom').datagrid({
				width : function() {
					return document.body.clientWidth ;
				}
			}); */
			//加载 城市 . parentId = 1 (中国)
			var json = loadArea('1', true);
			putOption("cityType", json);
		});
	</script>
	
	<div id="temp_div" class="easyui-window" closed="true" modal="true" data-options="title:'看房任务详情',iconCls:'icon-save'" style="width:1180px;height:560px;padding:5px;"></div>
	
</body>
</html>