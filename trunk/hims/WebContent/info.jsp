<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据报表</title>

<%
String ip=	request.getRemoteAddr();
if(true){
//if("180.166.57.26".equals(ip) || "127.0.0.1".equals(ip)|| "192.168.1.173".equals(ip)){
	String p = request.getParameter("p");
	if("520Manyi".equals(p)){
		//ok
	}else{
		response.getWriter().print("密码不正确");
		return ;
	}
}else{
	response.getWriter().print(ip+" 呵呵,不是公司的网络,不能访问吆!");
	return ;
}

%>
<link rel="stylesheet" type="text/css" href="${ctx }/themes/default/easyui.css" />
<script type="text/javascript" src="${ctx}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/locale/easyui-lang-zh_CN.js"></script>
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

function areaChangeExt(obj,areaName,id){
	var area = $(obj);
	var parentId =area.val();
	var json =[];
	if(parentId == 1){}else{
		json = loadArea(parentId,false);
	}
	putOptionExt(areaName,json,id);
	if(areaName=='areaId' ){
		putOptionExt('townId','',id);
	}
}


//加载 区域板块
function loadArea(parentId,isAll){
	
	/*
	 * 加载 数据字典/地区
	 */
	var url1= "${ctx}/rest/common/findAreaByParentId.rest";
	var pars = "{\"parentId\":"+parentId+",\"flag\":"+isAll+"}";
	var resultData={};
	$.ajax( {
		"url" : url1,
		"data" :pars ,
		"dataType":"json",
		"async":false,
		"contentType" :'application/json;charset=UTF-8',
		"type" : 'POST',
		"success" : function(data) {
			//data =eval("("+data+")");
			resultData = data.areaList;
		},
		"error" : function(data) {
			
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
		if(key =='exportFinanceOperation-cityType'|| key =='workbyday-cityType'|| key =='workbyday-area' || key == 'cityType'||  key == 'area' ||  key == 'town'||   json == null || json.length == 0 ){
			str ='<option value="0" parentid="0" selected="selected">全部</option>';
		}
		if( key == 'area-select' || key == 'city-select' || key == 'plate-select' || json == null || json.length == 0){
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

function putOptionExt(key,json,id){
	if(!(json === undefined)){
		var str='';
		if(key == 'cityType' || key == 'cityId'||  key == 'areaId' ||  key == 'townId'||   json == null || json.length == 0 ){
			str ='<option value="0" parentid="0" selected="selected">全部</option>';
		}
		var sel=$("#" + id + " select[name='" + key + "']");
		sel.html('');
		for(var i =0 ; i< json.length ; i++){
			var row = json[i];
			str +='<option value="'+row.areaId+'" parentid="'+row.parentId+'">'+row.name+'</option>';
		}
		sel.html(str);
	}
}


	$(function(){
		Date.prototype.format = function(fmt)   
		{   
		  var o = {   
		    "M+" : this.getMonth()+1,                 //月份   
		    "d+" : this.getDate(),                    //日   
		    "h+" : this.getHours(),                   //小时   
		    "m+" : this.getMinutes(),                 //分   
		    "s+" : this.getSeconds(),                 //秒   
		    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
		    "S"  : this.getMilliseconds()             //毫秒   
		  };   
		  if(/(y+)/.test(fmt))   
		    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		  for(var k in o)   
		    if(new RegExp("("+ k +")").test(fmt))   
		  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		  return fmt;   
		} ;
	});

	$(function(){
		$("#publishDate").datebox("setValue", new Date().format("yyyy-MM-dd hh:mm:ss"));  
		var strDate = new Date().format("yyyy-MM-dd");
		$("#csSearch-beginCreateTime").datebox("setValue", strDate + " 09:00:00"); 
		$("#csSearch-endCreateTime").datebox("setValue", new Date().format("yyyy-MM-dd hh:mm:ss"));
	});
	function customWorkSearch() {
		var publishDate =$("#publishDate").datetimebox('getValue');
		$("#customWorkForm").html('正在加载，请稍后...');
		$.ajax({
			type: "post",
			url: "${ctx}/rest/action/customerWork.rest",
			data: "publishDateStr=" + publishDate,
			dataType: "json",
			success: function (data) {
				setCustomerWorkData(data.rows);
			}
		});
	}
	function setCustomerWorkData(dataRows) {
		var publishDate =$("#publishDate").datetimebox('getValue');
		var temp = '<table>';
		temp += '<tr> <th>客服类型</th> <th>1轮</th> <th>2轮</th> <th>3轮</th> <th>4轮</th> <th>5轮</th> <th>时间</th> </tr>';
		for(var i=0; i<dataRows.length; i++) {
			temp += '<tr> <td>'+dataRows[i].customType+'</td> <td>'+dataRows[i].oneNum+'</td> <td>'+dataRows[i].twoNum+'</td> <td>'+dataRows[i].threeNum+'</td> <td>'+dataRows[i].fourNum+'</td> <td>'+dataRows[i].fiveNum+'</td> <td>'+publishDate+'</td> <td>'+dataRows[i].tomNum+'</td><td>'+dataRows[i].yesNum+'</td></tr>';
		}
		temp+='</table>';
		$("#customWorkForm").html(temp);
	}
	function BDSearchWith24() {
		$.ajax({
			type: "post",
			url: "${ctx}/rest/action/bDOperations.rest",
			data: "beginCreateTime=&endCreateTime=",
			dataType: "json",
			success: function (data) {
				setBDSearchWith24Data(data.rows);
			}
		});
	}
	function DBSearch() {
		var beginCreateTime =$("#beginCreateTime").datetimebox('getValue');
		var endCreateTime =$("#endCreateTime").datetimebox('getValue');
		
		var form = getForm();
		form.attr('action','${ctx}/rest/action/bDOperations.rest');  
		var newElement = document.createElement("input");
	    newElement.setAttribute("name","beginCreateTime");
	    newElement.setAttribute("type","hidden");
	    newElement.setAttribute("value",beginCreateTime);
	    form.append(newElement);
	    var _newElement = document.createElement("input");
	    _newElement.setAttribute("name","endCreateTime");
	    _newElement.setAttribute("type","hidden");
	    _newElement.setAttribute("value",endCreateTime);
	    form.append(_newElement);
		$('body').append(form);  
		form.submit();  
		form.remove(); 
	}
		
	function setBDSearchWith24Data(dataRows) {
		var temp = '<table>';
		temp += '<tr><th>用户名</th><th>手机号</th>  <th>中介人新增</th> <th>中介人审核中</th> </tr>';
		for(var i=0; i<dataRows.length; i++) {
			temp += '<tr> <td>'+dataRows[i].realName+'</td><td>'+dataRows[i].mobile+'</td><td>'+dataRows[i].totalNum+'</td> <td>'+dataRows[i].verifyNum+'</td></tr>';
		}
		temp+='</table>';
		$("#BDSearchWithForm").html(temp);
	}
	
	/*
	检测 是否 选择了excel文件
	*/
	function checkbankExcel(){
		var tmp =$("#UserToUserExcel").val() ;
		if(tmp == '' || tmp == null){
			alert('选择文件');
			return false;
		}else{
			return true;
		}
	}

	/*
	检测是否选择时间
	*/
	function userNumFrm(){
		var t1 = $("#exportUserCallCenterCheckNum-startUserDate").datetimebox('getValue');
		var t2 =  $("#exportUserCallCenterCheckNum-endUserDate").datetimebox('getValue');
		if(t1 > t2){
			alert('开始时间不能大于截止时间!');
			return false;
		}else{
			return true;
		}
	}
	
	/*
	检测是否选择时间
	*/
	function userNumFrm1(){
		var t1 = $("#exportFinanceOperation-startUserDate").datetimebox('getValue');
		var t2 =  $("#exportFinanceOperation-endUserDate").datetimebox('getValue');
		if(t1 > t2){
			alert('开始时间不能大于截止时间!');
			return false;
		}else{
			return true;
		}
	}
	
	function timeValidate(id,t1name,t2name){
		var t1 = $("#" + t1name ).datebox('getValue');
		var t2 =  $("#" + t2name ).datebox('getValue');
		if(t1 != '' && t2 != '' && t1 > t2){
			alert('开始时间不能大于截止时间!');
			return false;
		}else{
			return true;
		}
	}
	
</script>
</head>
<body>
	
<script>
function CSSearch() {
	var beginCreateTime =$("#csSearch-beginCreateTime").datetimebox('getValue');
	var endCreateTime =$("#csSearch-endCreateTime").datetimebox('getValue');
	
	if(beginCreateTime != '' && endCreateTime !=''){
		if(beginCreateTime > endCreateTime){
			$.messager.alert('错误','开始时间不能大于截止时间!','error');
			return;
		}
	}
	
	var form = getForm();
	form.attr('action','${ctx}/action/cSSearch');  
	var newElement = document.createElement("input");
    newElement.setAttribute("name","beginCreateTime");
    newElement.setAttribute("type","hidden");
    newElement.setAttribute("value",beginCreateTime);
    form.append(newElement);
    var _newElement = document.createElement("input");
    _newElement.setAttribute("name","endCreateTime");
    _newElement.setAttribute("type","hidden");
    _newElement.setAttribute("value",endCreateTime);
    form.append(_newElement);
	$('body').append(form);  
	form.submit();  
	form.remove(); 
}
function BDRptSearch() {
	var mobile =$("#bdSearchFrmRpt").find('.bdSearchRpt').val();
	if(mobile == ''){
		$.messager.alert('错误','请输入手机号','error');
		return;
	}
	if(isNaN(mobile)){
		$.messager.alert('错误','请输入数字','error');
		return;
	}
	var form = getForm();
	form.attr('action','${ctx}/action/bDRptSearch');  
	var newElement = document.createElement("input");
    newElement.setAttribute("name","mobile");
    newElement.setAttribute("type","hidden");
    newElement.setAttribute("value",mobile);
    form.append(newElement);
	$('body').append(form);  
	form.submit();  
	form.remove();
}
function getForm(){
	var form = $("<form>");
	form.attr('style','display:none');  
	form.attr('target','');  
	form.attr('method','post');
	return form;
}
function BDRptAllSearch() {
	var form = getForm();
	form.attr('action','${ctx}/action/bDRptSearch');  
	var newElement = document.createElement("input");
    newElement.setAttribute("name","mobile");
    newElement.setAttribute("type","hidden");
    newElement.setAttribute("value","");
    form.append(newElement);
	$('body').append(form);  
	form.submit();  
	form.remove();
}
function exportInfo4UserSearch() {
	var number =$("#exportInfo4UserRpt").find('.exportInfo4UserRpt').val();
	if(number == ''){
		$.messager.alert('错误','数字不能为空','error');
		return;
	}
	if(isNaN(number)){
		$.messager.alert('错误','请输入数字','error');
		return;
	}
	var form = getForm();
	form.attr('action','${ctx}/action/exportInfo4UserSearch');  
	var newElement = document.createElement("input");
    newElement.setAttribute("name","number");
    newElement.setAttribute("type","hidden");
    newElement.setAttribute("value",number);
    form.append(newElement);
	$('body').append(form);  
	form.submit();  
	form.remove();
}
function exportUserInfoRptSearch() {
	var number =$("#exportUserInfoRpt").find('.exportUserInfoRpt').val();
	if(number == ''){
		$.messager.alert('错误','请输入数字','error');
		return;
	}
	if(isNaN(number)){
		$.messager.alert('错误','请输入数字','error');
		return;
	}
	var form = getForm();
	form.attr('action','${ctx}/action/exportUserInfoRptSearch');  
	var newElement = document.createElement("input");
    newElement.setAttribute("name","number");
    newElement.setAttribute("type","hidden");
    newElement.setAttribute("value",number);
    form.append(newElement);
	$('body').append(form);  
	form.submit();  
	form.remove();
}
</script>	

	<div>
		<h3>导出房源运营总表</h3>
		<div id="exportUserCallCenterCheckNum">
			<div id="exportUserCallCenterCheckNumRpt">
			<form action="${ctx}/action/exportHouseOperation" method="post" id="importUserToUser-userNumFrm" onsubmit="return userNumFrm();">
				城市:
					<select name="cityType" id="cityType" onchange="areaChange(this,'area')" style="width:130px;">
						<option value="0" selected="selected">全部</option>
						<!-- 
						<option value="2">上海</option>
						<option value="12217">北京</option>
						 -->
					</select>
				区域:<select  id="area" name="areaId" style="width:120px;" onchange="areaChange(this,'town');">
						<option value="0" parentid='0'>全部</option>
					</select>
				时间 :<input class="easyui-datebox" editable="false" id='exportUserCallCenterCheckNum-startUserDate' name="start"  data-options="required:false,showSeconds:true" style="width:150px"> - 
				<input class="easyui-datebox" editable="false" id='exportUserCallCenterCheckNum-endUserDate' name="end"  data-options="required:false,showSeconds:true" style="width:150px"> 
				<input type="submit" value="查询导出"/>
				
			</form>
			</div>
		</div>
	</div>
	<hr/>
	<div>
		<h3>导出中介运营总表</h3>
		<div >
			<div >
			<form action="${ctx}/action/getRegisterExcel" method="post" id="userRegister" >
				城市:
					<select name="cityId" onchange="areaChangeExt(this,'areaId','userRegister')" style="width:130px;">
						<option value="0" selected="selected">全部</option>
					</select>
				区域:<select  name="areaId" style="width:120px;" onchange="areaChangeExt(this,'townId','userRegister');">
						<option value="0" parentid='0'>全部</option>
					</select>
				推荐人手机号:<input name="recommenderMobile" />
				时间 :<input class="easyui-datebox" editable="false" name="startDate"  data-options="required:false,showSeconds:true" style="width:150px"> - 
				<input class="easyui-datebox" editable="false" name="endDate"  data-options="required:false,showSeconds:true" style="width:150px"> 
				<input type="submit" value="查询导出"/>
				
			</form>
			</div>
		</div>
	</div>
	<hr/>
	<div>
		<h3>导出中介发房活跃度表</h3>
		<div >
			<div >
			<form action="${ctx}/action/getPublishExcel" method="post" id="userBD" >
				城市:
					<select name="cityId" onchange="areaChangeExt(this,'areaId','userBD')" style="width:130px;">
						<option value="0" selected="selected">全部</option>
					</select>
				区域:<select  name="areaId" style="width:120px;" onchange="areaChangeExt(this,'townId','userBD');">
						<option value="0" parentid='0'>全部</option>
					</select>
				时间 :<input class="easyui-datebox" editable="false" name="startDate"  data-options="required:false,showSeconds:true" style="width:150px"> - 
				<input class="easyui-datebox" editable="false" name="endDate"  data-options="required:false,showSeconds:true" style="width:150px"> 
				<input type="submit" value="查询导出"/>
				
			</form>
			</div>
		</div>
	</div>
	<hr/>
	<div>
		<h3>导出BD工作统计报表</h3>
		<div >
			<div >
			<form action="${ctx}/action/getBDWorkExcel" method="post" id="userPublish" >
				城市:
					<select name="cityId" onchange="areaChangeExt(this,'areaId','userPublish')" style="width:130px;">
						<option value="0" selected="selected">全部</option>
					</select>
				区域:<select  name="areaId" style="width:120px;" onchange="areaChangeExt(this,'townId','userPublish');">
						<option value="0" parentid='0'>全部</option>
					</select>
				时间 :<input class="easyui-datebox" editable="false" name="startDate"  data-options="required:false,showSeconds:true" style="width:150px"> - 
				<input class="easyui-datebox" editable="false" name="endDate"  data-options="required:false,showSeconds:true" style="width:150px"> 
				<input type="submit" value="查询导出"/>
				
			</form>
			</div>
		</div>
	</div>
	<hr/>
	
	<div>
		<h3>BD工作日常报表</h3>
		<div >
			<div >
			<form action="${ctx}/action/exportBdWorkByDay" method="post" id="workbyday" onsubmit="return timeValidate('workbyday','workbyday-start','workbyday-end');" >
				城市:
					<select name="cityType" id='workbyday-cityType' onchange="areaChange(this,'workbyday-area')" style="width:130px;">
						<option value="0" selected="selected">全部</option>
					</select>
				区域:<select  name="areaId" id="workbyday-area" style="width:120px;" id='workbyday-areaId'>
						<option value="0" parentid='0'>全部</option>
					</select>
				时间 :<input class="easyui-datebox" editable="false" id="workbyday-start" name="start"  data-options="required:false,showSeconds:true" style="width:150px"> - 
				<input class="easyui-datebox" editable="false"  id="workbyday-end" name="end"  data-options="required:false,showSeconds:true" style="width:150px"> 
				<input type="submit" value="查询导出"/>
				
			</form>
			</div>
		</div>
	</div>
	<hr/>
	
	<div>
		<h3>导出中介支付宝绑定表</h3>
		<div >
			<div >
			<form action="${ctx}/action/getAliExcel" method="post" id="userAli" >
				<input type="submit" value="查询导出"/>
			</form>
			</div>
		</div>
	</div>
	<hr/>
	<div></div>
	
	<div>
		<h3>财务运营总表</h3>
		<div id="exportFinanceOperation">
			<div id="exportFinanceOperationRpt">
			<form action="${ctx}/action/exportFinanceOperation" method="post" id="exportFinanceOperation-userNumFrm" onsubmit="return userNumFrm1();">
				城市:
					<select name="cityType" id="exportFinanceOperation-cityType" style="width:130px;">
						<option value="0" selected="selected">全部</option>
						<!-- 
						<option value="2">上海</option>
						<option value="12217">北京</option>
						 -->
					</select>
				时间 :<input class="easyui-datebox" editable="false" id='exportFinanceOperation-startUserDate' name="start"  data-options="required:false,showSeconds:true" style="width:150px"> - 
				<input class="easyui-datebox" editable="false" id='exportFinanceOperation-endUserDate' name="end"  data-options="required:false,showSeconds:true" style="width:150px"> 
				<input type="submit" value="查询导出"/>
				
			</form>
			</div>
		</div>
	</div>
	<hr/>
	<div>
	<!-- 
		
	 -->
	</div>

<script type="text/javascript">
$(document).ready(function(){
	//加载 城市 . parentId = 1 (中国)
	var json = loadArea(1,false);
	putOption("cityType",json);
	putOption("exportFinanceOperation-cityType",json);
	putOption("workbyday-cityType",json);
	putOptionExt("cityId",json,"userRegister");
	putOptionExt("cityId",json,"userPublish");
	putOptionExt("cityId",json,"userBD");
});

</script>
	
</body>
</html>