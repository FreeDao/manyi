<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>举报工单</title>

<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/easyui/demo.css">
<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<div class="easyui-panel" title="任务基础信息" style="width:100%px;">
        <div style="padding:10px 60px 20px 60px">
        <form action="${ctx}/check/auditSuccess" id="form1" method="post" target="targetIframe">
        	<input type="hidden" name="houseId" value="<c:out value="${houseId}" />" />
            <table cellpadding="5">
                <tr>
	                <td id="reportMsg">&nbsp;</td>
                    <td width="20">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td id="houseInfoMsg">&nbsp;</td>
                    <td width="20">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td id="remark">&nbsp;</td>
                    <td width="20">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td style="vertical-align: top">备注（选填）：</td>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="3"><textarea id="lookNote" name="lookNote" style="width: 300px; height: 100px"></textarea></td>
                </tr>
                <tr>
                    <td colspan="3">
                  		
                    		<a href="#" onclick="$('#form1').attr('action', '${ctx}/check/auditSuccess');$('#form1').submit();" class="easyui-linkbutton">审核成功</a>
                    	&nbsp; &nbsp; &nbsp; &nbsp; 
                   			<a href="#" onclick="$('#form1').attr('action', '${ctx}/check/auditFail');$('#form1').submit();" class="easyui-linkbutton">审核失败</a>
                   		
                    </td>
                </tr>
            </table>
        </form>
        </div>
    </div>
    <div class="easyui-panel" title="当前业主信息" style="width:100%px;">
        <div style="padding:10px 60px 20px 60px"  id="houseContactTrue">

        </div>
    </div>
    <div class="easyui-panel" title="发布信息的经纪人" style="width:100%px;">
        <div style="padding:10px 60px 20px 60px" id="houseUser">
            
        </div>
    </div>
    <div class="easyui-tabs" style="width:100%px;height:250px">
    	<div title="审核记录" style="padding:10px 60px 20px 40px" id="auditId">
            
        </div>
    </div>
    
    
    <iframe name="targetIframe" style="border: 0px; width: 0px" src="${ctx}/check/getIframeJsp"></iframe>
</body>

<script>

function sayOk(o) {
	if (o == 'success') {
		$.messager.alert('提示', '操作成功！', 'info', 
			function (){
				self.location.href = '${ctx}/check/checkGrid';
			}
		);
	}
}


//加载 房源信息
function initPage (houseId) {
	$.ajax({
		type: "post",
		url: "${ctx}/check/loadAuditData",
		data: "houseId=" + houseId,
		dataType: "json",
		success: function (data) {
			setData(data);

		}
	});
}

function setData(data) {
	$("#reportMsg").text("举报：" + (data.reportMsg==undefined?"":data.reportMsg));
	$("#houseInfoMsg").text(data.houseInfoMsg);
	$("#remark").text("举报理由：" + (data.remark==undefined?"":data.remark));
	
	//设置当前业主信息
	if (data.houseResourceContact != undefined) {
		var temps = '<table>';
		for (var i = 0; i < data.houseResourceContact.length; i ++) {
			temps += '<tr><td>' + data.houseResourceContact[i] + '</td></tr>';
		}
		temps += '</table>';
		$("#houseContactTrue").html(temps);
		
		
	}
	
	//发布经纪人信息
	if (data.userStr != undefined) {
		
		var temps = '<table>';
		for (var i = 0; i < data.userStr.length; i ++) {
			temps += '<tr><td>' + data.userStr[i] + '</td></tr>';
		}
		temps += '</table>';
		$("#houseUser").html(temps);
		
	}
	
	setAuditMessageInfo(data);
}
function setAuditMessageInfo(data) {

	if (data.auditList != undefined) {
		var str = "";
		for (var i = 0; i < data.auditList.length; i++) {
			var o = data.auditList[i];
			str += "<p style=\"font-size:14px\">" + o.finishDate + "</p>";
			str += "<ul>";
			str += "    <li>审核类型：" + o.type + "</li>";
			str += "    <li>备注：" + (o.note==undefined?"":o.note) + "</li>";
			str += "    <li>审核结果：" + o.auditState + "</li>";
// 			str += "    <li><a href='#' onclick='lookDetail(" + o.historyId + ")'>查看详情</a></li>";
			str += "</ul>";
		}
		$("#auditId").html(str);
		
	}
}
//初始化页面
initPage('<c:out value="${houseId}" />');
















</script>
</html>