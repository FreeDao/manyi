<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>工单详情</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<style type="text/css">
		input.button {width:120px;height:40px;border:1px solid rgb(155,155,155);background:rgb(225,225,225);color:#333;font-weight:bold;font-size:14px;font-family:"微软雅黑";cursor:pointer;border-radius:5px;margin-left:32px}
		table td {text-align:left;font-weight:normal}
		a.lnk-button {padding:3px 12px;background:rgb(225,225,225);border:1px solid rgb(205,205,205);border-radius:3px;cursor:pointer;font-size:14px;font-weight:bold;font-family:微软雅黑;margin-left:8px}
		div.citem {padding:3px}
		div.line2 {height:2px;overflow:hidden;background:rgb(175,175,175)}
		div.note-word {font-size:22px;font-weight:normal;padding:8px}
		input.txt {height:32px;border:1px solid rgb(205,205,205);background:white;outline:none;text-align:right;font-size:14px;font-weight:bold;width:80px;padding:0px 8px}
		textarea {width:400px;height:80px;outline:none;font-size:14px;font-weight:bold;}
	</style>
</head>
<body onresize="resizeGrid();" style="margin:0px;padding:0px">
	<c:if test="${ custServWorkFlowFlag == 1 || test == 1}">
		<jsp:include page="check_workingMode.jsp"></jsp:include>
	</c:if>
	<%-- - //角色 1管理员,2客服经理，3客服人员,4地推经理,5地推人员 6财务  -> --%>
	<c:if test="${ sessionScope.login_session.role > 0 || test == 1}">
	<div style="padding:10px">
		<div style="padding:5px;font-size:22px;font-weight:bold;font-family:微软雅黑">审核任务&gt;&gt;&nbsp;&nbsp;${css.houseResource.actionTypeStr}${css.houseResource.houseStateStr}</div>
		<div class="line2"></div>
		<div style="padding:12px">
			<table style="width:100%; border-spacing:  10px;">
				<tr style="width:auto;text-align: center ;" >
					<td style="width:25%"><strong>${css.house.subEstateName } - <span title="栋座号"><c:if test="${css.house.building == 0}">独栋 </c:if><c:if test="${css.house.building != 0}">${css.house.building } </c:if></span>- <span title="室号">${css.house.room }</span></strong></td>
					<td style="width:25%"><b>区域板块：</b>${css.house.cityName }-${css.house.townName }</td>
					<td style="width:25%"><c:if test="${css.houseResource.actionType != 4}"><b>经纪人：</b>${css.houseResource.userName}</c:if></td>
				</tr>
				
				<tr style="width:auto;text-align: center;" >
					<td>
					<c:forEach items="${css.contactResponses }" var="host" varStatus="status">
						<c:if test="${ host.status == 0}"></c:if>
						<div class="citem">${host.hostName }-${host.hostMobile }<a class="lnk-button" href="javascript:void(0)" onclick="makeCall('${ctx}/check/cs/makeCall?mobile=${host.hostMobile}')">拨打</a></div>
					</c:forEach>
					</td>
					<td style="width:25%"><b>房源编号：</b>${css.house.houseId }</td>
					<td style="width:25%"><b>发布时间：</b><fmt:formatDate value="${css.houseResource.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr style="width:auto;text-align: center;" >
					<td style="width:25%"><span><b>第${css.houseResource.checkNum + 1}次审核</b></span></td>
					<td style="width:25%"></td>
					<td style="width:25%"></td>
				</tr>
			</table>
		</div>
		<div class="line2"></div>
	</div>
	</c:if>
	<c:if test="${ (sessionScope.login_session.role > 0 && sessionScope.login_session.role < 6) || test == 1}">
	<div style="width:auto;height:auto">
		<c:if test="${ (sessionScope.login_session.role > 0 && sessionScope.login_session.role < 4) || test == 1}">
		<div  style="width:auto;height:auto;padding:20px 60px">
			<div>
			<c:forEach items="${css.contactResponses }" var="host" varStatus="status">
				<div class="note-word">提示语：请问你是<span style="font:bold;font-size:1.2em;color: red;">${css.house.subEstateName } <c:if test="${css.house.building != 0}">- ${css.house.building }栋 </c:if>- ${css.house.room }室</span>的房主<span style="font:bold;font-size:1.2em;color: red;">${host.hostName} </span> 吗？ 请问你的房子是<span style="font:bold;font-size:1.2em;color: red;"><c:if test="${css.houseResource.actionType == 2}">${css.houseResource.stateReasonStr }</c:if><c:if test="${css.houseResource.actionType != 2}">${css.houseResource.houseStateStr }</c:if></span>么？</div>
			</c:forEach>
			<form id="submitCheck-form" name="submitCheck-form" method="post" action="${ctx}/check/cs/submitCheckAll">
				<input type="hidden" name="status" value="2"/>
				<input type="hidden" name="sourceLogId" value="0"/>
				<input type="hidden" name="houseId" value="${css.house.houseId }" style="width: 20px" />
				<table style="width:100%">
					<tr style="width:auto;">
						<td style="width:50%">
							<table>
							<c:if test="${css.houseResource.actionType == 1 && css.houseResource.houseState == 2}">
								<tr>
									<td class="lbl">到手价：</td>
									<td><input class="txt" type="text" name="sellPrice" value="${css.houseResource.sellPrice }" />万</td>
								</tr>
							</c:if>
							<c:if test="${css.houseResource.actionType == 1 && css.houseResource.houseState == 1}" >
								<tr>
									<td class="lbl">租金：</td>
									<td><input class="txt"  type="text" name="rentPrice" value="${css.houseResource.rentPrice }" />元/月</td>
								</tr>
							</c:if>
							<c:if test="${css.houseResource.actionType == 1 && css.houseResource.houseState == 3}" >
								<tr>
									<td class="lbl">到手价：</td>
									<td><input class="txt" type="text" name="sellPrice" value="${css.houseResource.sellPrice }" />万</td>
								</tr>
								<tr>
									<td class="lbl">租金：</td>
									<td><input class="txt" type="text" name="rentPrice" value="${css.houseResource.rentPrice }" />元/月</td>
								</tr>
							</c:if>
							<c:if test="${css.houseResource.actionType == 1 }">
							<tr>
								<td class="lbl">面积：</td><td><input class="txt" type="text" name="spaceArea" value="${css.house.spaceArea }" />平米</td>
							</tr>
							<tr>
								<td class="lbl">房型：</td>
								<td>
									<input class="txt" type="text" name="bedroomSum" value="${css.house.bedroomSum}" style="width: 40px" />室
									<input type="text" class="txt" name="livingRoomSum" value="${css.house.livingRoomSum}" style="width: 40px" />厅
									<input type="text" class="txt" name="wcSum" value="${css.house.wcSum }" style="width: 40px" />卫
								</td>
							</tr>
							</c:if>
							</table>
						</td>
						<td style="width:50%">
							<div>联系业主备注（选填）</div>
							<textarea name="note"></textarea>
						</td>
					</tr>
					<tr><td><div style="height:20px"></div></td></tr>
					<tr>
						<td>
							<c:if test="${css.houseResource.actionType != 4 }">
								<input type="button" class="button" onclick="submitCheckForm(1)" value="审核成功" />
								<input type="button" class="button" onclick="submitCheckForm(2)" value="再审核"/>
								<input type="button" class="button" onclick="submitCheckForm(3)" value="审核失败" />
							</c:if>
							<c:if test="${css.houseResource.actionType == 4 }">
								<input type="button" class="button" onclick="submitCheckForm(3)" value="审核成功" />
								<input type="button" class="button" onclick="submitCheckForm(2)" value="再审核"/>
								<input type="button" class="button" onclick="submitCheckForm(1)" value="审核失败" />
							</c:if>
						</td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</form>
			</div>
			
		</div>
		</c:if>
		
	</div>
	</c:if>
	
		
</body>
<style scoped="scoped">
	.textbox{
		height:20px;
		margin:0;
		padding:0 2px;
		box-sizing:content-box;
	}
</style>
<script>
function resizeGrid() {
	$(".easyui-tabs div").width(  document.body.clientWidth - 43 );
}

$(function(){
	
});


function submitCheckForm(status){
	var textNote = $("#submitCheck-form textarea[name='note']").val();
	if(textNote != null && textNote.length > 255) {
		$.messager.alert('温馨提示', "备注长度过长，请少于255个字符","warning");
		return;
	}
	var textSpaceArea = $("#submitCheck-form input[name='spaceArea']").val();
	if(textSpaceArea != null   ) {
		if( /^-?\d+\.?\d*$/.test(textSpaceArea)) {
			// /^-?\d+\.?\d*$/.test(this.value)
			// /^\d+$/
			if(textSpaceArea > 10000){
				$.messager.alert('温馨提示', "面积不对","warning");
				return;
			}
			
		}else {
			$.messager.alert('温馨提示', "面积不对","warning");
			return;
		}
		
	}
	
	var textRentPrice = $("#submitCheck-form input[name='rentPrice']").val();
	if(textRentPrice != null   ) {
		if( /^-?\d+\.?\d*$/.test(textRentPrice)) {
			
		}else {
			$.messager.alert('温馨提示', "出租价格不对","warning");
			return;
		}
	}
	var textSellPrice = $("#submitCheck-form input[name='sellPrice']").val();
	if(textSellPrice != null   ) {
		if( /^-?\d+\.?\d*$/.test(textSellPrice)) {
			
		}else {
			$.messager.alert('温馨提示', "出售价格不对","warning");
			return;
		}
	}
	
	var text1 = $("#submitCheck-form input[name='bedroomSum']").val();
	var text2 = $("#submitCheck-form input[name='livingRoomSum']").val();
	var text3 = $("#submitCheck-form input[name='wcSum']").val();
	if(text1 != null && text2 != null && text3 != null) {
		if( /^\d+$/.test(text1) && /^\d+$/.test(text2) && /^\d+$/.test(text3)) {
			
		}else {
			$.messager.alert('温馨提示', "房型数据不对","warning");
			return;
		}
		
		
	}
	
	
	
	$("#submitCheck-form input[name='status']").val(status);
	//$.messager.alert("温馨提示","提交中，如提交时间过长，请刷新页面");
	$('#submitCheck-form').form({
		success:function(data){
			//$.messager.alert('温馨提示', "正在审核中");
			if(data=="success"){
				window.location.href="${ctx}/check/cs/startWork";
				console.debug("ok");
			}else {
				$.messager.alert('温馨提示', data ,'error');
			}
		}
	});
	$('#submitCheck-form').submit();
	
	
}

function makeCall(url){
	$.get(url, {},function(data){
		if(data.indexOf("error") == 0){
			$.messager.alert('温馨提示', "发生错误",'error');
		}
	});
}

</script>
</html>