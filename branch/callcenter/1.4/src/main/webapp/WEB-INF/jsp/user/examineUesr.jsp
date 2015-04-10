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
	<style type="text/css">
		img{max-width:350px;max-height:350px;}
	</style>
	<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
</head>
<body onresize="resizeGrid();">
		<div class="easyui-tabs" style="width:auto;height:600px;">
        <%-- <div title="经纪人账号信息" style="padding:10px">
        	<span>姓名:${result.realName}</span>
        	<span>注册邀请码:${result.spreadId}</span>
        	<span>注册时间:${result.createTimeStr}</span>
        	<span>工作区域:${result.areaName}</span>
        	<span>注册邀请人:${result.spreadName}</span>
        	<span>身份证号:${result.code}</span>
        	<span>手机号:${result.mobile}</span>
        	<span>身份证照片:<img src="${result.codeUrl}" /></span>
        	<span>名片照片：<img src="${result.cardUrl}" /></span>
        </div> --%>
        
        <div title="经纪人账号信息" style="padding:12px">
			<table style="width:100%; border-spacing:  10px;">
				<tr style="width:auto;text-align: center ;" >
					<td style="width:25%"><b>姓名：</b>${result.realName}</td>
					<td style="width:25%"><b>注册邀请码：</b>${result.spreadId}</td>
					<td style="width:25%"><b>工作区域：</b>${result.areaName}</td>
					<td style="width:25%"><b>注册邀请人：</b>${result.spreadName}
					<c:if test="${result.otherInner == 1}">(内部)</c:if>
					<c:if test="${result.otherInner == 0}">(经纪人)</c:if>
					</td>
				</tr>
				
				<tr style="width:auto;text-align: center ;" >
					<td style="width:25%"><b>身份证号：</b>${result.code}</td>
					<td style="width:25%"><b>手机号：</b>${result.mobile}</td>
				</tr>
				
				 <tr style="width:auto;text-align: center;" >
					<td style="width:25%"><b>身份证照片：</b></td>
					<td><img src="${result.codeUrl}" /></td>
					<td style="width:20%"><b>名片照片：</b></td>
					<td><img src="${result.cardUrl}" /></td>
				</tr> 
			</table>
		</div>
    </div>
    
    <div class="easyui-tabs" style="width:auto;height:250px">
        <div title="审核结果" style="padding:10px">
            <form action="${ctx}/user/updateUserState" method="post" id="examineSuccessForm">
            	审核结果：<input type="radio" name="state" value="1" onclick="checkState(this.value)"/>审核成功  <input type="radio" name="state" value="3" onclick="checkState(this.value)"/>审核失败<br/>
            	<span style="display:none" id="dow">
            		所属门店：<input type="text" name="doorName" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true"/><br>
            		门店电话：<input type="text" name="doorNumber" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true"/><br>
            	</span>
            	<span style="display:none" id="fail">
            		 失败原因(必填)：<br>
            		 <input type="text" name="remark" class='easyui-validatebox validatebox-text validatebox-invalid' data-options="required:true"/>
            	</span>
            	<input type="hidden" name="uid" value="${result.uid}"/>
            	<a href="javascript:void(0)" onclick="submitUpdateUserState()" class="easyui-linkbutton">提交</a>
            </form>
        </div>
    </div>
    <script type="text/javascript">
    	function submitUpdateUserState(){
    		var select = $('input:radio[name="state"]:checked').val();
    		if(select==null){
				$.messager.alert("温馨提示", "请选择审核状态");
				return false;
    		}else{
    			if(select==1){
    				if($("#examineSuccessForm input[name='doorName']").val()==""){
    					$.messager.alert("温馨提示","请输入所属门店");
    					return false;
    				}
    				if($("#examineSuccessForm input[name='doorNumber']").val()==""){
    					$.messager.alert("温馨提示","请输入门店电话");
    					return false;
    				}
    			}
    			if(select==3){
    				if($("#examineSuccessForm input[name='remark']").val()==""){
    					$.messager.alert("温馨提示","请输入失败原因");
    					return false;
    				}
    			}
    		}
    		$("#examineSuccessForm").submit();
    	}
    	function checkState(state){
    		if(state==1){
    			$("#dow").css("display","block");
    			$("#fail").css("display","none");
    		}else{
    			$("#dow").css("display","none");
    			$("#fail").css("display","block");
    		}
    	}
    </script>
</body>
</html>