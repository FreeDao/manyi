<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Custom DataGrid Pager - jQuery EasyUI Demo</title>

<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/easyui/demo.css">
<link rel="stylesheet" type="text/css" href="${ctx}/easyui/jquery.autocomplete.css">
<script type="text/javascript" src="${ctx}/easyui/jquery-1.2.6.js"></script>
<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/easyui/jquery.autocomplete.js"></script>
</head>

<body>

	<div class="easyui-panel" title="小区基础信息" style="width:400px">
        <div style="padding:10px 60px 20px 60px">
        <form id="ff" method="post">
            <table cellpadding="5">
            	<input type="hidden" value="" name="areaId"/>
                <tr>
                    <td>小区名:</td>
                    <td><c:out value="${estateResponse.areaName }" /></td>
                </tr>
                <tr>
                    <td>区域:</td>
                    <td><c:out value="${estateResponse.cityName }" /></td>
                </tr>
                <tr>
                    <td>板块:</td>
                    <td><c:out value="${estateResponse.townName }" /></td>
                </tr>
                <tr>
                    <td>小区地址:</td>
                    <td><c:out value="${estateResponse.areaRoad }" /></td>
                </tr>
                <tr>
                    <td>审核结果:</td>
                    <td>
                    	<input type="radio" name="sourceState" value="0"/>审核成功 
                    	<input type="radio" name="sourceState" value="2"/>审核失败
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
        </div>
        </div>
    </div>
    <input type="text" id="tags" name="tags" /> 
    <input class="ac_input" id="suggest1" type="text">
<script>
function submitForm(){
	$('#ff').action('${ctx}/estate/auditpass');
    $('#ff').form('submit');
}
var cities = [
          	"Aberdeen", "Ada", "Adamsville", "Addyston", "Adelphi", "Adena", "Adrian", "Akron",
          	"Albany", "Alexandria", "Alger", "Alledonia", "Alliance", "Alpha", "Alvada",
          	"Alvordton", "Amanda", "Amelia", "Amesville", "Amherst", "Amlin", "Amsden",
          	"Zoar"
          ];

$().ready(function() {
	$("#suggest1").focus().autocomplete(cities);
});

// $("#tags").autocomplete(
// 	["c++","java", "php", "coldfusion","javascript"], 
// 	{
// 		width: 320, 
// 		max: 4, 
// 		highlight: false, 
// 		multiple: true, 
// 		multipleSeparator: "", 
// 		scroll: true, 
// 		scrollHeight: 300
// 	}
// );




</script>




</body>

</html>