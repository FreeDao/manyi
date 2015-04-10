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
				<th>房源ID:</th>
				<td><input class="easyui-validatebox textbox" type="text" name="houseId" id='houseId' style="width:110px;"></input></td>
				<td colspan="5"></td>
			</tr>
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
					<input class="easyui-validatebox textbox" type="text" name="estateName" id='estateName' style="width:160px;"></input>
				</td>
				<th>租售状态:</th>
				<td>
					<select  id="sellState" name="sellState" style="width:120px;">
						<option value="0" selected="selected">全部</option>
						<option value="1">出租</option>
						<option value="2">出售</option>
						<option value="3">租售</option>
						<option value="4">不租不售</option>
					</select>
				</td>
				
				<td style="width: 100px;"></td>
				
				<th style='border-left: 1px #000 solid;'>租售状态:</th>
				<td id='check-houseState'>
					<input type="radio" name="houseState" value="2" id="checkHouseState2" checked="checked"/>
					<label for="checkHouseState2">出售</label>
					<input type="radio" name="houseState" value="1" id="checkHouseState1"/>
					<label for="checkHouseState1">出租</label>
				</td>
				
				<th style="padding-left:30px;">出售价格:</th>
				<td>
					<select name='sellPriceStart' id='check-sellPriceStart'>
						<option value="0" selected="selected">不限</option>
						<option value="50">50万</option>
						<option value="100">100万</option>
						<option value="150">150万</option>
						<option value="200">200万</option>
						<option value="250">250万</option>
						<option value="300">300万</option>
						<option value="350">350万</option>
						<option value="500">500万</option>
						<option value="1000">1000万</option>
					</select>
					-
					<select name='sellPriceEnd' id='check-sellPriceEnd'>
						<option value="0" selected="selected">不限</option>
						<option value="50">50万</option>
						<option value="100">100万</option>
						<option value="150">150万</option>
						<option value="200">200万</option>
						<option value="250">250万</option>
						<option value="300">300万</option>
						<option value="350">350万</option>
						<option value="500">500万</option>
						<option value="1000">1000万</option>
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
				<!-- <th>出租状态:</th>
				<td>
					<select  id="rentState" name="rentState" style="width:120px;">
						<option value="0">全部</option>
						<option value="1">出租</option>
						<option value="2">不租</option>
						<option value="3">已租</option>
					</select>
				</td> -->
				
				<th colspan="1"> 是否有照片:</th>
				<td colspan="1" id="picNum-td">
					<select  id="picNum" name="picNum" style="width:120px;">
						<option value="0" selected="selected">全部</option>
						<option value="1">无照片</option>
						<option value="2">有照片</option>
					</select>
				</td>
				
				<td colspan="3" style="width: 100px;"></td>
				<th style='border-left: 1px #000 solid;'>城市:</th>
				<td>
					<select name="cityType" id="check-cityType" onchange="areaChange(this,'check-area')" style="width:120px;">
						<option value="0" selected="selected">全部</option>
						<!-- 
						<option value="2">上海</option>
						<option value="12217">北京</option>
						 -->
					</select>
				</td>
				
				
				<th style="padding-left:30px;">出租价格:</th>
				<td>
					<select name='rentPriceStart' id='check-rentPriceStart'>
						<option value="0" selected="selected">不限</option>
						<option value="500">500元</option>
						<option value="1000">1000元</option>
						<option value="1500">1500元</option>
						<option value="2000">2000元</option>
						<option value="2500">2500元</option>
						<option value="3000">3000元</option>
						<option value="4000">4000元</option>
						<option value="8000">8000元</option>
					</select>
					-
					<select name='rentPriceEnd' id='check-rentPriceEnd'>
						<option value="0" selected="selected">不限</option>
						<option value="500">500元</option>
						<option value="1000">1000元</option>
						<option value="1500">1500元</option>
						<option value="2000">2000元</option>
						<option value="2500">2500元</option>
						<option value="3000">3000元</option>
						<option value="4000">4000元</option>
						<option value="8000">8000元</option>
					</select>
				</td>
				
				
			</tr>
			<tr>
				<th>板块:</th>
				<td>
					<select  id="town" name="townId" style="width:120px;">
						<option value="0" parentid='0'>全部</option>
					</select>
				</td>
				
				<th colspan="1"> 审核状态:</th>
				<td colspan="1" id="operCustServiceState">
					<!-- 1审核中3再审核 4定时再审核 5审核满三次 0审核成功 2审核失败 -->
					<input type="checkbox" name="operCustServiceState" id="cs1" value="2"/>
					<label for="cs1">审核中</label>
					<!-- 
					<input type="checkbox" name="operCustServiceState" id="cs2" value="2"/>
					<label for="cs2">再审核</label>
					<input type="checkbox" name="operCustServiceState" id="cs3" value="4"/>
					<label for="cs3">定时再审核</label>
					<input type="checkbox" name="operCustServiceState" id="cs4" value="5"/>
					<label for="cs4">审核满三次</label> 
					-->
					<input type="checkbox" name="operCustServiceState" id="cs5" value="1"/>
					<label for="cs5">审核成功</label>
					<input type="checkbox" name="operCustServiceState" id="cs6" value="3"/>
					<label for="cs6">审核失败</label>
				</td>
				
				<td colspan="3" style="width: 100px;"></td>
				<th  style='border-left: 1px #000 solid;'>区域:</th>
				<td>
					<select  id="check-area" name="areaId" style="width:120px;" onchange="areaChange(this,'check-town');">
						<option value="0" parentid='0'>全部</option>
					</select>
				</td>
				
				
				<th>面积:</th>
				<td>
					<select name='spaceAreaStart' id='check-spaceAreaStart'>
						<option value="0" selected="selected">不限</option>
						<option value="50">50平米</option>
						<option value="70">70平米</option>
						<option value="90">90平米</option>
						<option value="110">110平米</option>
						<option value="130">130平米</option>
						<option value="150">150平米</option>
						<option value="200">200平米</option>
						<option value="300">300平米</option>
					</select>
					-
					<select name='spaceAreaEnd' id='check-spaceAreaEnd'>
						<option value="0" selected="selected">不限</option>
						<option value="50">50平米</option>
						<option value="70">70平米</option>
						<option value="90">90平米</option>
						<option value="110">110平米</option>
						<option value="130">130平米</option>
						<option value="150">150平米</option>
						<option value="200">200平米</option>
						<option value="300">300平米</option>
					</select>
				</td>
				
			</tr>
			<tr>
				<td>
					<input type="button" value="查询" onclick="doSearch();"/>
				</td>
				
				<td colspan="6" style="width: 100px;"></td>
				
				<th  style='border-left: 1px #000 solid;'>板块:</th>
				<td>
					<select  id="check-town" name="townId" style="width:120px;">
						<option value="0" parentid='0'>全部</option>
					</select>
				</td>
				
				<th style="padding-left:30px;">发布时间:</th>
				<td>
					<input class="easyui-datebox" editable="false" name="publishDateStart" id='check-publishDateStart' data-options="required:false,showSeconds:true" style="width:100px"> - 
					<input class="easyui-datebox" editable="false" name="publishDateEnd" id="check-publishDateEnd" data-options="required:false,showSeconds:true" style="width:100px">
				</td>
				
			</tr>
			<tr>
				<td>
					<input type="button" value="安排拍照" onclick="doPhoto(this);"/>
				</td>
				
				<td  colspan="6" style="width: 100px;"></td>
				
				<th  style='border-left: 1px #000 solid; '>抽查数量:</th>
				<td>
					<input class="easyui-numberbox" min='1' max='1000' maxlength="3" type='text' id="check-num" name="checkNum" style="width:50px;"/>
					<input type="button" onclick="checkHouse();" value="随机抽查"/>
					<span id="check-result"></span>
				</td>
				
			</tr>
			<tr>
				<td>
				</td>
				
				<td  colspan="6" style="width: 100px;"></td>
				
				<th style='padding-right: 120px;'></th>
				<td>
			
				</td>
				
			</tr>
		</table>
		</div>
	</div>
	<hr/>
	
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
		if(areaName=='area'){
			putOption('town','');
		}
		if(areaName=='check-area' ){
			putOption('check-town','');
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
			if( key == 'cityType'||  key == 'area' ||  key == 'town' ||  key == 'check-area' ||  key == 'check-town' ||   json == null || json.length == 0   ){
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
	
	//随机抽查看房
	function checkHouse(){
		var pars="{";
		var cityType = $("#check-cityType").val();
		if(cityType != null && cityType !=''){
			pars +="\"cityType\" : \""+cityType+"\",";
		}
		var areaId = $("#check-area").val();
		if(areaId !=0 && areaId !=''){
			pars +="\"parentId\" : \""+areaId+"\",";
		}
		
		var townId = $("#check-town").val();
		if(townId !=0 && townId !=''){
			pars +="\"areaId\" : \""+townId+"\",";
		}
		
		var sellPriceStart = document.getElementById("check-sellPriceStart").value;
		if(sellPriceStart !='' && sellPriceStart !='0' && sellPriceStart != null){
			pars +="\"sellPriceStart\" : \""+sellPriceStart+"\",";
		}
		var sellPriceEnd = document.getElementById("check-sellPriceEnd").value;
		if(sellPriceEnd !='' && sellPriceEnd !='0' && sellPriceEnd != null){
			pars +="\"sellPriceEnd\" : \""+sellPriceEnd+"\",";
		}
		
		sellPriceStart = parseInt(sellPriceStart);
		sellPriceEnd = parseInt(sellPriceEnd);
		if( sellPriceEnd < sellPriceStart && sellPriceEnd != 0 ){
			$.messager.alert('温馨提示', '起始售价不能大于结束售价!','warning');
			return false;
		}
		
		var rentPriceStart = document.getElementById("check-rentPriceStart").value;
		if(rentPriceStart !='' && rentPriceStart !='0' && rentPriceStart != null){
			pars +="\"rentPriceStart\" : \""+rentPriceStart+"\",";
		}
		var rentPriceEnd = document.getElementById("check-rentPriceEnd").value;
		if(rentPriceEnd !='' && rentPriceEnd !='0' && rentPriceEnd != null){
			pars +="\"rentPriceEnd\" : \""+rentPriceEnd+"\",";
		}
		
		rentPriceStart = parseInt(rentPriceStart);
		rentPriceEnd = parseInt(rentPriceEnd);
		if( rentPriceEnd < rentPriceStart && rentPriceEnd != 0){
			$.messager.alert('温馨提示', '起始租价不能大于结束租价!','warning');
			return false;
		}
		
		var spaceAreaStart = document.getElementById("check-spaceAreaStart").value;
		if(spaceAreaStart !='' && spaceAreaStart !='0' && spaceAreaStart != null){
			pars +="\"spaceAreaStart\" : \""+spaceAreaStart+"\",";
		}
		var spaceAreaEnd = document.getElementById("check-spaceAreaEnd").value;
		if(spaceAreaEnd !='' && spaceAreaEnd !='0' && spaceAreaEnd != null){
			pars +="\"spaceAreaEnd\" : \""+spaceAreaEnd+"\",";
		}
		
		spaceAreaStart = parseInt(spaceAreaStart);
		spaceAreaEnd = parseInt(spaceAreaEnd);
		if( spaceAreaEnd < spaceAreaStart && spaceAreaEnd != 0){
			$.messager.alert('温馨提示', '起始面积不能大于结束面积!','warning');
			return false;
		}
		
		var t =$("#check-publishDateStart").datetimebox("getValue");
		if(t != null && t !=''){
			pars +=" \"publishDateStart\" : \"" + t+"\" ,";
		}
		
		var t1 =$("#check-publishDateEnd").datetimebox("getValue");
		if(t1 != null && t1 !=''){
			pars +=" \"publishDateEnd\" : \"" + t1+"\" ,";
		}

		if (t != '' && t1 != '') {
			if (t > t1) {
				$.messager.alert("温馨提示", "起始时间不能大于截止时间!", "warning");
				$('#check-publishDateStart').datetimebox('setValue', '');
				$('#check-publishDateEnd').datetimebox('setValue', '');
				return false;
			}
		}
		
		
		var houseState= $("#check-houseState input[name='houseState']:checked").val();
		if(houseState !='' && houseState != null){
			pars +="\"houseState\" : \""+houseState+"\",";
		}
		var checkNum = $("#check-num").numberbox("getValue");
		if(checkNum != null && checkNum !=''){
			pars +="\"checkNum\" : \""+checkNum+"\",";
		}else{
			$.messager.alert('温馨提示', '请输入看房抽查数量!','warning');
			$("#checkNum").focus();
			return ;
		}
		
		if(pars.length >1){
			pars = pars.substr(0,pars.length-1);
		}
		pars += "}";
		//pars = eval("("+pars+")");
		//alert(pars);
		//return ;
		
		var url1= "${ctx}/lookHouse/randomBdTask";
		$.ajax( {
			url : url1,
			data :pars ,
			dataType:"json",
			type : "POST",
			contentType :'application/json;charset=UTF-8',
			success : function(data) {
				//data =eval("("+data+")");
				if(data.errorCode == 0){
					$.messager.alert('提示', '成功随机抽查看房任务!','info');
					$("#check-result").html("已按条件随机选取"+data.message+"套房源进行看房审核！");
					$('#mainfrom').datagrid('reload',pars);
				}else{
					$.messager.alert('提示', data.message,'error');
				}
			},
			error : function(data) {
				
			}
		});
		
	}
		
	//搜索
		function doSearch(){
			var pars="{";
			
			var houseId = $("#houseId").val();
			if(houseId != null && houseId !=''){
				pars +="'houseId' : '"+houseId+"',";
				
			}
			var cityType = $("#cityType").val();
			if(cityType != null && cityType !=''){
				pars +="'cityType' : '"+cityType+"',";
				
			}
			

			var picNum = $("#picNum").val();
			if(picNum !=0 && picNum !=''){
				pars +="'picNum' : '"+picNum+"',";
			}
			
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
			var sellState = $("#sellState").val();
			if(sellState != null && sellState != '' && sellState !=0){
				pars +="'sellState' : '"+sellState+"',";
			}
			var rentState = $("#rentState").val();
			if(rentState != null && rentState != '' && rentState !=0){
				pars +="'rentState' : '"+rentState+"',";
			}
			
			var operCustServiceState = '';
			//var operFloorServiceState = '';
			var custs = document.getElementsByName("operCustServiceState");
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
				pars +=" 'operServiceState' : '"+operCustServiceState+"',";
			}
			if(pars.length >1){
				pars = pars.substr(0,pars.length-1);
			}
			pars += "}";
			pars = eval("("+pars+")");
		    $('#mainfrom').datagrid('reload',pars);
		} 
		
		var data_from = $('#mainfrom').datagrid({
			url : '${ctx}/house/houseList',
			//fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
			striped : true, //True 就把行条纹化
			pagination : true,//分页
			rownumbers : true, //显示行号
			pageSize : 20,
			pageNumber : 1,
			pageList : [ 20, 30, 50, 100 ],//列表分页
			loadMsg : '数据正在努力加载中...',
			height: document.body.clientHeight * 2.9,
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [
			          {field : 'ck',checkbox : true,width:$(this).width() * 0.05}//显示一个checkbox
					, {field : 'houseId',title : '房源编号',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'cityName',title : '城市',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.04}
					, {field : 'areaName',title : '行政区',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'townName',title : '片区',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'estateName',title : '小区名',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'builing',title : '栋座号',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'room',title : '室号',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'sellStateStr',title : '租售状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'rentStateStr',title : '出租状态',align : 'center',resizable : true,hidden : true,sortable : false,width:$(this).width() * 0.1}
					, {field : 'sellPublishDateStr',title : '发布出售时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'rentPublishDateStr',title : '发布出租时间',align : 'center',resizable : true,hidden : true,sortable : false,width:$(this).width() * 0.1}
					, {field : 'checkTypeStr',title : '当前审核类型',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'operServiceStateStr',title : '当前审核状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'taskStateStr',title : 'BD看房状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.06}
					, {field : 'userTaskStateStr',title : '中介看房状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.06}
					, {field : 'picNum',title : '照片情况',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'opt',  title : '操作',align : 'center',resizable : true,hidden : false,sortable : false ,width:$(this).width() * 0.05,
						formatter : function (value,row,index)
						 {

							var str="<a href='javascript:void(0);' onclick='view_btn("+row.houseId+")'>查看</a> ";
							if(row.checkType ==1 && ( row.userTaskState >2 ||  row.userTaskState == 0 ) && ( row.taskState >4 ||  row.taskState == 0 ) && (row.operServiceState == 1 || row.operServiceState== 3) ){
								str+=" <a href='${ctx}/house/editHouseShow?houseId="+row.houseId+"' target='bank'>编辑</a>";
							}else{
								str+=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
							}
							return str;
									
						 }
					   }

			] ],
			onDblClickRow : function(rowIndex, rowData) {
				//alert(rowData.houseId);
				view_btn(rowData.houseId);
			}

		});

		/* 
		安排拍照
		*/
		function doPhoto(obj){
			var rows = $('#mainfrom').datagrid('getSelections');//得到选中的行
			var ids='';    
			if(rows != null && rows.length >0){
				
				for (var i=0; i<rows.length; i++) {
					var houseid = rows[i].houseId;
					if (rows[i].taskState != 0) {
						$.messager.alert('提示', houseid+',房源已开启看房拍照任务.','warning');
						return ;
					}
					if (rows[i].userTaskState != 0) {
						$.messager.alert('提示', houseid+',中介已领取看房拍照任务.','warning');
						return ;
					}
					if(rows[i].sellState == 4){
						//不租不售	
						$.messager.alert('提示', houseid+',不租不售的房源不必看房拍照.','warning');
						return ;
					}
					ids +=  houseid + ',';  //如果选中，将value添加到变量s中    
				}
				
				diableButton(obj);
				
				ids = ids.substr(0,ids.length-1);
				//实现 分配的逻辑	
				var url1= "${ctx}/lookHouse/addBdTask";
				var pars = "{\"houseIds\":\""+ids+"\"}";
				$.ajax( {
					url : url1,
					data :pars ,
					dataType:"json",
					type : "POST",
					contentType :'application/json;charset=UTF-8',
					success : function(data) {
						
						//data =eval("("+data+")");
						if(data.errorCode == 0){
							$.messager.alert('提示', '成功启动看房任务!','info');
							$('#mainfrom').datagrid('reload',pars);
							
						}else{
							$.messager.alert('提示', data.message,'error');
						}
						test1();
					},
					error : function(data) {
						test1();
					}
				});
			}
		}
		
		var tmp =null;
		function diableButton(obj){
			tmp = $(obj);
			//setTimeout("test1()", 3000);
			tmp.attr('disabled',true);
		}
		function test1(){
			//alert(1);
			if(tmp){
				tmp.attr('disabled',false);
			}
		}
		 
		/*
		查看
		 */
		function view_btn(houseId) {
// 			这里需要houseId
			window.location.href="${ctx}/sourcemanage/sourceManageDetail?houseId=" + houseId;
		}

		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth * 0.9;
				}
			});
		}

	$(document).ready(function(){
		//加载 城市 . parentId = 1 (中国)
		var json = loadArea('1',true);
		putOption("cityType",json);
		putOption("check-cityType",json);
		
		//默认加载 第一个 下面的内容
		if(json != null && json.length >0){
			json = loadArea(json[0].areaId,false);
			putOption("check-area",json);
		}
	});
		
	
	</script>
</body>
</html>