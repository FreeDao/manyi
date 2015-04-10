<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/easyui/demo.css">
<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/jqueryui/css/ui-lightness/jquery-ui-1.10.4.min.css">
<script type="text/javascript" src="${ctx}/jqueryui/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="${ctx}/jqueryui/jquery-ui.js"></script>


</head>

<body>

	<div class="easyui-panel" title="小区基础信息" style="width:400px">
        <div style="padding:10px 60px 20px 60px">
        <form id="ff" method="post">
            <table cellpadding="5">
            	<input type="hidden" value="" name="areaId"/>
            	<tr>
                    <td>区域：</td>
                    <td>
                    	<select >
                    		<option value='asdf'>asd</option>
                    		<option value='asdf'>asd</option>
                    		<option value='asdf'>asd</option>
                    	</select>
                    </td>
                </tr>
            	<tr>
                    <td>板块：</td>
                    <td>
                  		<select >
                    		<option value='asdf'>asd</option>
                    		<option value='asdf'>asd</option>
                    		<option value='asdf'>asd</option>
                    	</select>
                    </td>
                </tr>
            	<tr>
                    <td>主小区:</td>
                    <td><input type="text" name="estateName"/></td>
                </tr>
            	
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
    
    

	 
<script>

$(document).ready(function() {
	$("input[name='estateName']").autocomplete({
		source: '${ctx}/estate/getEstateByName',
		minLength: 2
		
	});
	
	$("input[name='estateName']").bind("autocompleteselect", function(e, ui) {
		getSubEstateList(ui.item.value);
	});
	
});


function getSubEstateList(estateName) {
	$.ajax({ 
        type: "post", 
        url: "${ctx}/estate/getSubEstateList4EstateName",
        data: "1=1",// + estateName,
        dataType: "json", 
        success: function (data) {
        	alert(data);
//         	setSourceBaseInfo(data, houseId);
        } 
	});
	
}
 


</script>




</body>

</html>