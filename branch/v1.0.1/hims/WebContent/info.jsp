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
		if(t1 != '' && t2 !=''){
			if(t1 > t2){
				alert('开始时间不能大于截止时间!');
				return false;
			}else{
				return true;
			}
		}else{
			alert('填写参数!');
			return false;
		}
	}
	
</script>
</head>
<body>
	<div>
		<h3>客服工作量</h3>
		<div id="customerSearch">
			<div id="customerSearchFrm">
				<table spellcheck="false">
					<tr>
						<th>时间:</th>
						<td>
							<input class="easyui-datetimebox"  editable="false"  name="publishDate" id="publishDate" style="width:150px"></input> 
						</td>
						<td>
							<input type="button" value="查询" onclick="customWorkSearch();"/>  &nbsp; &nbsp; 
						</td>
					</tr>
				</table>
			</div>
		</div>
	
	</div>
	<div id="customWorkForm" > </div>
	<hr/>
	<div>
		<h3>BD运营报表</h3>
		<div id="bDSearch">
			<div id="bDSearchFrm">
				<table spellcheck="false">
					<tr>
						<th>时间:</th>
						<td>
							<input class="easyui-datetimebox"  editable="false"  name="beginCreateTime" id="beginCreateTime" style="width:150px"></input> 
							-
							<input class="easyui-datetimebox"   editable="false" name="endCreateTime" id="endCreateTime" style="width:150px"></input>
						</td>
						<td>
							<input type="button" value="查询" onclick="DBSearch();"/>  &nbsp; &nbsp; 
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="BDSearchWithForm" > </div>
	
	
	<hr/>
	<div>
		<h3>CS绩效报表</h3>
		<div id="csSearch">
			<div id="csSearchFrm">
				<table spellcheck="false">
					<tr>
						<th>时间:</th>
						<td>
							<input class="easyui-datetimebox"  editable="false"  name="beginCreateTime" id="csSearch-beginCreateTime" style="width:150px"></input> 
							-
							<input class="easyui-datetimebox"   editable="false" name="endCreateTime" id="csSearch-endCreateTime" style="width:150px"></input>
						</td>
						<td>
							<input type="button" value="查询下载" onclick="CSSearch();"/>  &nbsp; &nbsp; 
						</td>
						<td>
							&nbsp; &nbsp; 
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<hr/>
	<div>
		<h3>BD手机号查询报表</h3>
		<div id="bdSearchRpt">
			<div id="bdSearchFrmRpt">
				<table spellcheck="false">
					<tr>
						<th>手机号:</th>
						<td>
							<input type="text" name="mobile" class="bdSearchRpt" style="width:150px"></input> 
						</td>
						<td>
							<input type="button" value="查询下载" onclick="BDRptSearch();"/>  &nbsp; &nbsp; 
						</td>
						<td>
							<input type="button" value="全部下载" onclick="BDRptAllSearch();"/> &nbsp; &nbsp; 
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<hr/>
	<div>
		<h3>反作弊:中介是房东</h3>
		<div id="exportInfo4User">
			<div id="exportInfo4UserRpt">
				<table spellcheck="false">
					<tr>
						<th>中介手机号，房东手机号匹配大于:</th>
						<td>
							<input type="text" name="number" class="exportInfo4UserRpt" style="width:50px"></input> 
						</td>
						<td>
							<input type="button" value="查询下载" onclick="exportInfo4UserSearch();"/>  &nbsp; &nbsp; 
						</td>
						<td>
							&nbsp; &nbsp; 
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<hr/>
	<div>
		<h3>反作弊:房东多套房</h3>
		<div id="exportUserInfo">
			<div id="exportUserInfoRpt">
				<table spellcheck="false">
					<tr>
						<th>房东手机号，房子数量匹配大于:</th>
						<td>
							<input type="text" name="number" class="exportUserInfoRpt" style="width:50px"></input> 
						</td>
						<td>
							<input type="button" value="查询下载" onclick="exportUserInfoRptSearch();"/>  &nbsp; &nbsp; 
						</td>
						<td>
							&nbsp; &nbsp; 
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<iframe name="tmpFrame" id="tmpFrame" width="1" height="1" style="visibility:hidden;position:absolute;display:none"></iframe>
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
	<hr/>
	<div>
		<h3>导入BD与经纪人关联关系</h3>
		<div id="importUserToUser">
			<div id="importUserToUserRpt">
			<form action="${ctx }/action/importUserRelation" method="post"  enctype="multipart/form-data" onsubmit="javascript:return checkbankExcel();">
				Execl文件:<input type="file" name="excel" style="width:300px;" id="UserToUserExcel"/>
				<input type="submit" value="导入"/>
			</form>
			</div>
		</div>
	</div>
	
	<hr/>
	<div>
		<h3>导出客服工作审核情况</h3>
		<div id="exportUserCallCenterCheckNum">
			<div id="exportUserCallCenterCheckNumRpt">
			<form action="${ctx}/action/exportUserCallCenterCheckNum" method="post" id="importUserToUser-userNumFrm" onsubmit="return userNumFrm();">
				时间 :<input class="easyui-datetimebox" editable="false" id='exportUserCallCenterCheckNum-startUserDate' name="start"  data-options="required:false,showSeconds:true" style="width:150px"> - 
				<input class="easyui-datetimebox" editable="false" id='exportUserCallCenterCheckNum-endUserDate' name="end"  data-options="required:false,showSeconds:true" style="width:150px"> 
				<input type="hidden" name="state" value="1"/>
				<input type="hidden" name="type" value="1"/>
				<input type="submit" value="查询导出"/>
				
			</form>
			</div>
		</div>
	</div>
	
	
</body>
</html>