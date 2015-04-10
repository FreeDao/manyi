<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>根据区域 把小区数据导入到excel</title>

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

<script type="text/javascript" src="${ctx }/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/scripts/common.js"></script>
<script type="text/javascript" src="${ctx }/scripts/jquery.easyui.min.js"></script>

<script type="text/javascript">
/*
$(document).ready(function(){
	//加载 上海 区域 信息
	var  data =loadlist("area_province",'1');
	var shList = data.rows;
	var str='区域:<select name="areaName" id="area">';
	for(var i=0 ;  shList != null && i< shList.length ; i++){
		var sh = shList[i];
		sh.name = sh.name.substr(0,2);
		str+='<option value="'+sh.name+'">'+sh.name+'</option>';
	}
	str +='</select> ';
	str+=' <input type="button" onclick="exportData();" value="导出数据"/>';
	$("#main").html(str);
});
*/

function exportData(){
	var areaName = $("#area").val();
	if(areaName == ''){
		$("#msg").html("请选择区域!");
		return ;
	}
	var url1="${ctx }/main/export.rest";
	$.ajax( {
		url : url1,
		data : {areaName:areaName},
		type :'post',
		async:false,
		success : function(data) {
			$("#msg").html(data.message);
		},
		error : function(data) {
			
		}
	});
}

function btn(obj){
	$(obj).css("disabled","disabled");
}

function btnMoney(obj){
	//点击结算
	$("#type").val(obj);
	if(userNumFrm()){
		document.getElementById("userNumFrm").submit();
	}
}

function checkbankExcel(){
	var tmp =$("#bankExcel").val() ;
	if(tmp == '' || tmp == null){
		alert('选择文件');
		return false;
	}else{
		return true;
	}
}

</script>
</head>
<body>

<!-- <center style="margin-top: 200px;"> -->
<center style="margin-top: 50px;">
<div>
	<h4>导出  联系人拥有的房子的总套数大于7的Excel数据</h4>
	
	<form action="${ctx }/action/exportHourseHostInfo" method="post" id="hourseHostInfo" onsubmit="return checkCount();">
		联系人拥有的房子的总套数大于<input type="text" name='hourceCount' class="hourceCount" style="width:30px;" />
		<input type="submit" value="开始导出" onclick='btn(this);'/>
	</form>
	<script>
		function checkCount(){
			if($("#hourseHostInfo .hourceCount").val() != ''){
				return true;
			}else{
				alert('填写参数');
				return false;
			}
		}
	</script>
	<hr/>
	<br/>
</div>


<div>
	注:导入导出数据 日志路径
	d:/SourceInfo
</div>
	<hr/>
	<div>
	导出待审核的房源信息
	<form action="${ctx }/action/exportSourceInfo" method="post">
		开始时间 :<input class="easyui-datetimebox" id='startDate' name="start"  data-options="required:true,showSeconds:true" style="width:150px"><br/>
		结束时间 :<input class="easyui-datetimebox" id='endDate' name="end"  data-options="required:true,showSeconds:true" style="width:150px"><br/>
		房源审核状态:<select name="state">
			<option value="-1">全部</option>
			<option value="1" selected="selected">未审核</option>
			<option value="0">审核成功</option>
			<option value="2">审核失败</option>
		</select>
		<br/>
		导入类型:<select name="type">
		<!-- 	<option value="-1">全部</option> -->
			<option value="1">出售</option>
			<option value="2">出租</option>
		</select>
		<br/><br/>
		<input type="submit" value="导出数据" onclick='btn(this);'/>
	</form>
	<hr/>
	导入审核完成的房源信息
	<form action="${ctx }/action/improtSourceInfo.rest" method="post"  enctype="multipart/form-data">
		<!-- <input type="text" name='filePath' style="width:300px;" /> -->
		Execl文件:<input type="file" name="excel" style="width:500px;"/>
		<input type="submit" value="导入" onclick='btn(this);'/>
	</form>
	<hr/>
	审核通过 经纪人
	
	<form action="${ctx }/rest/action/checkUser.rest" method="post">
	
		<input type="text" name='mobile' style="width:300px;" />
		<input type="submit" value="审核通过" onclick='btn(this);'/>
	</form>
	
	<hr/>
	
	导入经纪人银行帐号信息(此人必须注册)
	<form action="${ctx }/action/improtUserBank" method="post"  enctype="multipart/form-data" onsubmit="javascript:return checkbankExcel();">
		<!-- <input type="text" name='filePath' style="width:300px;" /> -->
		Execl文件:<input type="file" name="excel" style="width:500px;" id="bankExcel"/>
		<input type="submit" value="导入" onclick='btn(this);'/>
	</form>
	<hr/>
	
	导出   经纪人发布出售 房源数量
	
	<form action="${ctx }/action/exportUserNum" method="post" id="userNumFrm" onsubmit="return userNumFrm();">
		开始时间 :<input class="easyui-datetimebox" id='startUserDate' name="start"  data-options="required:true,showSeconds:true" style="width:150px"><br/>
		结束时间 :<input class="easyui-datetimebox" id='endUserDate' name="end"  data-options="required:true,showSeconds:true" style="width:150px"><br/>
		
		<input type="hidden" name="type" id='type' value="1"/>
		<input type="button" value="导出发布出售房源数量" onclick='btnMoney(1);'/> | 
		<input type="button" value="导出发结算清单" onclick='btnMoney(2);'/>
		
	</form>
	<script>
		function userNumFrm(){
			if($("#startUserDate").datetimebox('getValue') != '' && $("#endUserDate").datetimebox('getValue') !=''){
				return true;
			}else{
				alert('填写参数');
				return false;
			}
		}
	</script>
	</div>
	
		
	<hr/>
	
	<hr/>
	<H2>导入 初始数据</H2>
	<div>
		注:导入数据 日志路径
		D:\\fangyou_data\\userimg\\import_log
	</div>
	<div>
	<h4>以下的数据导入功能 支持 xls 格式.导入时请转化格式,下面填写的路径为xls的全路径</h4>
	导入小区信息
	<form action="${ctx }/action/improtInitEstate.rest" method="post"  enctype="multipart/form-data">
			<!-- <input type="text" name='filePath' style="width:300px;" /> -->
		Execl文件:<input type="file" name="excel" style="width:500px;"/>
		<input type="submit" value="提交" onclick='btn(this);'/>
	</form>
	导入初始房源信息(不需要审核的数据信息)
	<form action="${ctx }/action/improtInitSourceInfo.rest" method="post"  enctype="multipart/form-data">
		<!-- <input type="text" name='filePath' style="width:300px;" /> -->
		Execl文件:<input type="file" name="excel" style="width:500px;"/>
		<input type="submit" value="提交" onclick='btn(this);' />
	</form>
	<hr/>
</center>

<script type="text/javascript">
</script>
</body>
</html>