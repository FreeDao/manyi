<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>根据区域 把小区数据导入到excel</title>
<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/common.js"></script>

<script type="text/javascript">
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


function exportData(){
	var areaName = $("#area").val();
	if(areaName == ''){
		$("#msg").html("请选择区域!");
		return ;
	}
	var url1="/main/export.rest";
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

</script>
</head>
<body>

<center style="margin-top: 200px;">
	<h2>根据区域 把小区数据导入到excel</h2>
	<div id='main'></div>
	<div id='msg' style="color:red; font-weight: bold;" ></div>
</center>
</body>
</html>