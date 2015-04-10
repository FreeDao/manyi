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
				<th>城市:</th>
				<td>
					<select name="cityType" id="cityType" style="width:130px;">
						<option value="-1" selected="selected">全部</option>
						<option value="2">上海</option>
						<option value="12217">北京</option>
					</select>
				</td>
				<th>审核类型:</th>
				<td>
					<select name="checkType" id="checkType" style="width:130px;">
						<option value="-1" selected="selected">全部</option>
						<option value="0">发布出售</option>
						<option value="1">发布出租</option>
						<option value="2">改盘</option>
						<option value="3">举报</option>
						<option value="4">客服轮询</option>
						<option value="6">抽查看房</option>
					</select>
				</td>
				<td></td>
				<th>发布人:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="publishName" id='publishName'></input>
				</td>
				<td></td>
				<th>发布时间:</th>
				<td>
					<input class="easyui-datetimebox"  editable="false"  name="startPublishDate" id="startPublishDate" style="width:150px"></input> -
					<input class="easyui-datetimebox"   editable="false" name="endPublishDate" id="endPublishDate" style="width:150px"></input>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<th>客服负责人:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="operCustServiceName" id="operCustServiceName"></input>
				</td>
				<th colspan="2"> 审核状态:</th>
				<td colspan="5" id="operCustServiceState">
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
				<td></td>
			</tr>
			<!-- <tr>
				<th>看房负责人:</th>
				<td>
					<input class="easyui-validatebox textbox" type="text" name="operFloorServiceName" id="operFloorServiceName"></input>
				</td>
				<th colspan="2">看房审核状态:</th>
				<td  colspan="4" id="operFloorServiceState">
					<input type="checkbox" name="operFloorServiceState" id="fs1" value="1"/>
					<label for="fs1">审核中</label>
					<input type="checkbox" name="operFloorServiceState" id="fs2" value="0"/>
					<label for="fs2">审核成功</label>
					<input type="checkbox" name="operFloorServiceState" id="fs3" value="2"/>
					<label for="fs3">审核失败</label>
				</td>
			</tr> -->
			<tr>
				<td>
					<input type="hidden" id="houseIds" value="">
					<!-- <input type="button" value="测试数据" onclick="diableButton(this);"/> -->
					<input type="button" value="查询" onclick="doSearch();"/>  &nbsp; &nbsp; 
					<c:if test="${sessionScope.login_session.role == 1 ||  sessionScope.login_session.role == 4}">
						<input type="button" value="分配" onclick="chk();"/>
					</c:if>
				</td>
			</tr>
		</table>
		</div>
	</div>
	
	<div id="w" class="easyui-dialog" title="分配地推人员" data-options="iconCls:'icon-save'" style="width:400px;height:300px;padding:10px;">
       <div id="floorContent">
            
        </div>
		 
        <div align="center" style=" margin: 5px">
          <a href="#" onclick="distribute2Floor()" class="easyui-linkbutton">确定</a>
          <a href="#" onclick="$('#w').window('close')" class="easyui-linkbutton">取消</a>
        </div>
    </div>
    
    
    
    
    
	<table id="mainfrom" > </table>
	
<!-- 	<input type="button" value="选中的行" onclick="onselect1();"/> -->
	
	<script type="text/javascript">
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
	
	var tmp =null;
	function diableButton(obj){
		tmp = $(obj);
		setTimeout("test1()", 2000);
		tmp.attr('disabled',true);
	}
	function test1(){
		//alert(1);
		if(tmp){
			tmp.attr('disabled',false);
		}
	}
		
		$(document).ready(function() {
			
			
			
			$('#w').window('close');
			var json = loadArea('1',true);
			putOption("cityType",json);
			/* 
			$('#mainfrom').datagrid({
				width : function() {
					return document.body.clientWidth;
				}
			}); 
			*/
		});
		
		function getEmployeeList() {
			$.ajax({ 
		        type: "post", 
		        url: "${ctx}/employee/getEmployeeList4Floor",
		        dataType: "json", 
		        success: function (data) {
					var str = '<table cellpadding="5"><tr><td colspan="2">请选择任务责任人：</td></tr>';
	            
					if (data.floorServices != undefined) {
						for (var i = 0; i < data.floorServices.length; i ++) {
			        		str += '<tr><td width="60" align="right"><input type="radio" name="floorId" value="' + data.floorServices[i].id + '"/></td><td>' + data.floorServices[i].realName + '</td></tr>'
			        	}
					}
					
					$('#floorContent').html(str + '</table>');
		        	
		        }
			});
			
		}
		
		function distribute2Floor(){
			if ($('input[name="floorId"]:checked').val() == undefined) {
				$.messager.alert('提示', '请选择责任人！');
			} else {
				
				$.ajax({ 
			        type: "post", 
			        url: "${ctx}/check/distribute2Floor",
			        data: "houseIds=" + $('#houseIds').val() + "&employeeId=" + $('input[name="floorId"]:checked').val(),
			        dataType: "json", 
			        success: function (data) {
			        	if (data.errorCode == 0) {
			    			$('#w').window('close');
			        		$.messager.alert('提示', '分配成功！', 'info', 
		    					function (){
			    		 		   $('#mainfrom').datagrid('reload');
		    					}
		    				);
			        	}
			        } 
				});
				
				
			}
		}
		
		
		/* function onselect1(){
			//getSelections
			var rows = $('#mainfrom').datagrid('getSelections');//得到选中的行
			if(rows != null && rows.length >0){
				alert(rows[0].houseId);
			}
		} */
	
	
		function chk(){
			var rows = $('#mainfrom').datagrid('getSelections');//得到选中的行
			var s='';    

			if(rows != null && rows.length >0){
				for (var i=0; i<rows.length; i++) {
					if (rows[i].checkType == '3' && rows[i].operServiceStateStr == '审核中') {
						s += rows[i].houseId + ',';  //如果选中，将value添加到变量s中    
					} else {
						$.messager.alert('提示', '请选择举报审核中的信息！');
						return ;
					}
				}
			}
			
			//那么现在来检测s的值就知道选中的复选框的值了    
			if (s=='') {
				$.messager.alert('提示', '你还没有选择任何内容！');
			} else {
				$('#houseIds').val(s);
				getEmployeeList();
				$('#w').window('open');
			}
			
		}    
	//搜索
		function doSearch(){
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
			
			/*
			var floors = document.getElementsByName("operFloorServiceState");
			if(floors != null && floors !='' && floors.length>0){
				for(var i = 0; i <floors.length; i++){
					if(floors[i].checked){
						operFloorServiceState += floors[i].value+",";
					}
				}
				
			}
			
			if(operFloorServiceState.length >0){
				operFloorServiceState = operFloorServiceState.substr(0,operFloorServiceState.length-1);
			}
			*/
			
			
			var pars = "{'checkType' : "+$('#checkType').val();
			var cityType = $('#cityType').val();
			if(cityType != null && cityType !='') {
				pars += " , 'cityType' : '" + cityType + "' ";
			}
			var publishName = $('#publishName').val();
			if(publishName != ''){
				pars +=" ,'publishName' : '"+publishName+"'";
			}
			var startPublishDate =$("#startPublishDate").datetimebox('getValue');
			if(startPublishDate != ''){
				pars +=" ,'startPublishDate' : '"+startPublishDate+"'";
			}
			var endPublishDate= $('#endPublishDate').datetimebox('getValue');
			if(endPublishDate != ''){
				pars +=" ,'endPublishDate' : '"+endPublishDate+"'";
			}
			
			if(startPublishDate != '' && endPublishDate != ''){
				if(startPublishDate > endPublishDate){
					$.messager.alert("温馨提示","起始时间不能大于截止时间!","warning");
					$('#startPublishDate').datetimebox('setValue','');
					$('#endPublishDate').datetimebox('setValue','');
					return ;
				}
			}
			
			
			var operCustServiceName = $('#operCustServiceName').val();
			if(operCustServiceName !=''){
				pars +=" ,'operServiceName' : '"+operCustServiceName+"'";
			}
			/* 
			var operFloorServiceName = $('#operFloorServiceName').val();
			if(operFloorServiceName !=''){
				pars +=" ,'operFloorServiceName' : '"+operFloorServiceName+"'";
			} 
			*/
			if(operCustServiceState != ''){
				pars +=" ,'operServiceState' : '"+operCustServiceState+"'";
			}
			/*
			if(operFloorServiceState != ''){
				pars +=" ,'operFloorServiceState' : '"+operFloorServiceState+"'";
			}
			*/
			pars += "}";
			pars = eval("("+pars+")");
		    $('#mainfrom').datagrid('reload',pars);
		} 
		
		var data_from = $('#mainfrom').datagrid({
			url : '${ctx}/check/checkList',
			//fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
			striped : true, //True 就把行条纹化
			pagination : true,//分页
			rownumbers : true, //显示行号
			pageSize : 20,
			pageNumber : 1,
			pageList : [ 20, 30, 50, 100 ],//列表分页
			loadMsg : '数据正在努力加载中...',
			height: document.body.clientHeight*1.5,
			//width: document.body.clientWidth,
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [
			          {field : 'logId',checkbox : true,width:$(this).width() * 0.05}//显示一个checkbox
					, {field : 'provinceName',title : '城市',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'areaName',title : '行政区',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'estateName',title : '小区名',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'builing',title : '栋座号',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'room',title : '室号',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'checkTypeStr',title : '发布类型',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'publishName',title : '发布人',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'publishDateStr',title : '发布时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'operServiceName',title : '负责人',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'operServiceStateStr',title : '审核状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'opt',  title : '操作',align : 'center',resizable : true,hidden : false,sortable : false ,width:$(this).width() * 0.1,
						formatter : function (value,row,index)
						 {
							//row.houseId;
							if (row.checkType == '3' && row.operServiceStateStr == '审核中' && row.operServiceId == '${sessionScope.login_session.id}') {
								return "<a href='javascript:void(0);' onclick='audit_btn("+row.houseId+")'>审核</a>";
							} else {
								return "";//"<a href='javascript:void(0);' onclick='view_btn("+row.houseId+")'>查看</a>";
							}
						 }
					   }

			] ],
			onDblClickRow : function(rowIndex, rowData) {
				//view_btn(rowData.houseId);
			}

		});

		/*
		审核
		 */
		function audit_btn(houseId) {
			//alert(logId);
// 			window.location.href="${ctx}/check/cs/single?id=" + logId;

			window.location.href="${ctx}/check/toAudit4operFloor?houseId=" + houseId;
		}
		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					//alert(document.body.clientWidth);
					return document.body.clientWidth * 0.9;
				}
			});
			
		}
	</script>
</body>
</html>