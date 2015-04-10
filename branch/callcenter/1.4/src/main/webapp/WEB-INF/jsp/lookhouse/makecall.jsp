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
		textarea {width:350px;height:80px;outline:none;font-size:14px;font-weight:bold;}
		#callHistory td {padding:10px 10px;}
	</style>
</head>
<body onresize="resizeGrid();" style="margin:0px;padding:0px">
	<%-- - //角色 1管理员,2客服经理，3客服人员,4地推经理,5地推人员 6财务  -> --%>
	<c:if test="${ sessionScope.login_session.role > 0 || test == 1}">
	<div style="padding:10px">
		<div style="padding:5px;font-size:22px;font-weight:bold;font-family:微软雅黑">预约任务&gt;&gt;&nbsp;&nbsp;${css.houseResource.actionTypeStr}${css.houseResource.houseStateStr}</div>
		<div class="line2"></div>
		<div style="padding:12px">
			<table style="width:100%; border-spacing:  10px;">
				<tr style="width:auto;text-align: center ;" >
					<td style="width:25%"><strong>${css.house.subEstateName } - <span title="栋座号"><c:if test="${css.house.building == '0'}">独栋 </c:if><c:if test="${css.house.building != '0'}">${css.house.building } </c:if></span>- <span title="室号">${css.house.room }</span></strong></td>
					<td style="width:25%"><b>区域板块：</b>${css.house.provinceName }-${css.house.cityName }-${css.house.townName }</td>
					<td style="width:25%"><c:if test="${css.houseResource.actionType != 4}"><b>经纪人：</b>${css.houseResource.userName} - ${css.houseResource.userMobile} <a class="lnk-button" href="javascript:void(0)" onclick="makeCall('${ctx}/check/cs/makeCall?mobile=${css.houseResource.userMobile}')">拨打</a></c:if></td>
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
					<td style="width:25%"><%-- <span><b>第${css.houseResource.checkNum + 1}次审核</b></span> --%></td>
					<td style="width:25%"></td>
					<td style="width:25%"></td>
				</tr>
			</table>
		</div>
		<div class="line2"></div>
	</div>
	</c:if>
	<c:if test="${ (sessionScope.login_session.role > 0 && sessionScope.login_session.role < 5) || test == 1}">
	<div style="width:auto;height:auto">
		<c:if test="${ (sessionScope.login_session.role > 0 && sessionScope.login_session.role < 5) || test == 1}">
		<div  style="width:auto;height:auto;padding:20px 60px">
			<div>
				<c:if test="${css.bdTaskResponse.taskStatus== 1 || css.bdTaskResponse.taskStatus== 3 }">
			<form id="submitCheck-form" name="submitCheck-form" method="post" action="${ctx}/lookHouse/submit">
				<input type="hidden" name="id" value="${css.bdTaskResponse.id }" style="width: 20px" />
				<table style="width:100%">
					<tr style="width:auto;">
						<td style="width:30%;vertical-align:text-top">
							<div><b>联系结果:</b><input type="radio"  name="status" onclick="showDistrBlock(1)" value="4" >安排看房</input>
							<input type="radio"  name="status" onclick="showDistrBlock(0)" value="3" >再预约</input>
							<input type="radio"   name="status" onclick="showDistrBlock(0)" value="2" >预约失败</input></div>
							<br/>
							<div id="distrBlock"style="display:none">
							<div>看房责任人：<select id="lookhouseEmpId" name="lookhouseEmpId" onchange="showCalender()">
															<option value="-1" >请选择</option>
															<c:forEach items="${css.empList }" var="emp" varStatus="status">
															<option value="${emp. employeeId}" >${emp.realName}-${emp.userName}-${emp.cityName}-${emp.areaName}</option>
															</c:forEach>
														</select>
							</div>
							<div>看房时间 :<input class="easyui-datebox" editable="false"  name="lookDate"  id="lookDate" data-options="required:false,showSeconds:true" style="width:150px"> - 
							<select id="lookTime" name="lookTime" >
								<option value="-1" >请选择</option>
								<option value="00:00:00" >00:00:00</option>
								<option value="00:30:00" >00:30:00</option>
								<option value="01:00:00" >01:00:00</option>
								<option value="01:30:00" >01:30:00</option>
								<option value="02:00:00" >02:00:00</option>
								<option value="02:30:00" >02:30:00</option>
								<option value="03:00:00" >03:00:00</option>
								<option value="03:30:00" >03:30:00</option>
								<option value="04:00:00" >04:00:00</option>
								<option value="04:30:00" >04:30:00</option>
								<option value="05:00:00" >05:00:00</option>
								<option value="05:30:00" >05:30:00</option>
								<option value="06:00:00" >06:00:00</option>
								<option value="06:30:00" >06:30:00</option>
								<option value="07:00:00" >07:00:00</option>
								<option value="07:30:00" >07:30:00</option>
								<option value="08:00:00" >08:00:00</option>
								<option value="08:30:00" >08:30:00</option>
								<option value="09:00:00" >09:00:00</option>
								<option value="09:30:00" >09:30:00</option>
								<option value="10:00:00" >10:00:00</option>
								<option value="10:30:00" >10:30:00</option>
								<option value="11:00:00" >11:00:00</option>
								<option value="11:30:00" >11:30:00</option>
								<option value="12:00:00" >12:00:00</option>
								<option value="12:30:00" >12:30:00</option>
								<option value="13:00:00" >13:00:00</option>
								<option value="13:30:00" >13:30:00</option>
								<option value="14:00:00" >14:00:00</option>
								<option value="14:30:00" >14:30:00</option>
								<option value="15:00:00" >15:00:00</option>
								<option value="15:30:00" >15:30:00</option>
								<option value="16:00:00" >16:00:00</option>
								<option value="16:30:00" >16:30:00</option>
								<option value="17:00:00" >17:00:00</option>
								<option value="17:30:00" >17:30:00</option>
								<option value="18:00:00" >18:00:00</option>
								<option value="18:30:00" >18:30:00</option>
								<option value="19:00:00" >19:00:00</option>
								<option value="19:30:00" >19:30:00</option>
								<option value="20:00:00" >20:00:00</option>
								<option value="20:30:00" >20:30:00</option>
								<option value="21:00:00" >21:00:00</option>
								<option value="21:30:00" >21:30:00</option>
								<option value="22:00:00" >22:00:00</option>
								<option value="22:30:00" >22:30:00</option>
								<option value="23:00:00" >23:00:00</option>
								<option value="23:30:00" >23:30:00</option>
								
							</select>
							</div>
							</div>
							<br/>
							<div>联系业主备注（选填）</div>
							<textarea name="note"></textarea>
							<br/>
							<input type="button" class="button" onclick="submitCheckForm()" value="提交" />
						</td>
						<td style="width:50%">
						<div id="iframeDiv" style="display:none">
						<iframe style="width:1000px;height:800px"></iframe>
						</div>
						</td>
					</tr>
					<tr><td><div style="height:20px"></div></td></tr>
					<tr>
						<td>
								<!-- <input type="button" class="button" onclick="submitCheckForm(1)" value="审核成功" />
								<input type="button" class="button" onclick="submitCheckForm(2)" value="再审核"/>
								<input type="button" class="button" onclick="submitCheckForm(3)" value="审核失败" /> -->
						</td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</form>
			</c:if>
			</div>
			  
		</div>
		</c:if>
		<c:if test="${css.makeCallHistories != null }">
		<div class="easyui-panel" title="预约记录" style="width:auto">
			<div style="padding:10px 60px 20px 60px">
				<table id="callHistory" border="1" cellspacing="0"  cellpadding="0" >
					<c:forEach items="${css.makeCallHistories }" var="call">
					<tr >
						<td ><b>联系时间 :</b></td><td><fmt:formatDate value="${call.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><b>联系结果:</b></td><td><c:if test="${call.taskStatus == 4}">${call.taskStatusStr},<fmt:formatDate value="${call.taskDate}" pattern="yyyy-MM-dd HH:mm:ss"/> ${call.employeeName}</c:if><c:if test="${call.taskStatus != 4}">${call.taskStatusStr}</c:if></td>
						<td><b>备注:</b></td><td>${call.note}</td>
						<td><b>操作人:</b></td><td>${call.phoneMakerName}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		</c:if>
		<div class="easyui-panel" title="拍摄时间与位置信息" style="width:auto">
	        <div style="padding:10px 60px 20px 60px">
	            <table>
					<tr>
						<td>拍摄时间：</td>
						<td id="finishDate"></td>
					</tr>
					<tr>
						<td>位置信息：</td>
						<td id="lbs"></td>
					</tr>
				</table>
	        </div>
	    </div>
		<div class="easyui-panel" title="照片" style="width:auto">
	        <div style="padding:10px 60px 20px 60px" id="imageTableId">
	            
	        </div>
	    </div>
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

function showDistrBlock(stat) {
	if(stat == 1) {
		$("#distrBlock").show();
	}else if(stat == 0){
		$("#distrBlock").hide();
	}
}

function showCalender() {
	
	if($("#lookhouseEmpId").val() != null &&  $("#lookhouseEmpId").val() > 0) {
		$("#iframeDiv").show();
		$("iframe").attr("src","${ctx}/lookHouse/fullCal?bdId=" + $("#lookhouseEmpId").val());
	}	
	else {
		$("#iframeDiv").hide();
	}
}
function submitCheckForm(){
	
	var status = $("#submitCheck-form input[name='status']:checked").val();
	debugger;
	if(status == null ) {
		$.messager.alert('温馨提示', "请选择预约结果","warning");
		return ;
	}
	
	var textNote = $("#submitCheck-form textarea[name='note']").val();
	if(textNote != null && textNote.length > 255) {
		$.messager.alert('温馨提示', "备注长度过长，请少于255个字符","warning");
		return;
	}
	if(status == 4) {
		
		var lookhouseEmpId =  $("#submitCheck-form select[name='lookhouseEmpId']").val();
		if(lookhouseEmpId == null || lookhouseEmpId =='-1') {
			$.messager.alert('温馨提示', "看房负责人不能为空","warning");
			return ;
		}
		
		if($("#lookDate").datetimebox('getValue')== null || $("#lookDate").datetimebox('getValue') == ''  ) {
			$.messager.alert('温馨提示', "日期不能为空","warning");
			return ;
		}
		var lookTime =  $("#submitCheck-form select[name='lookTime']").val();
		if(lookTime == null || lookTime =='-1') {
			$.messager.alert('温馨提示', "时间不能为空","warning");
			return ;
		}
	}
	
	//$("#submitCheck-form input[name='status']").val(status);
	//$.messager.alert("温馨提示","提交中，如提交时间过长，请刷新页面");
	$('#submitCheck-form').form({
		success:function(data){
			//$.messager.alert('温馨提示', "正在审核中");
			if(data=="success"){
				//window.location.href="${ctx}/lookHouse/lookHouseGrid";
				window.parent.closeDialog();
				return;
			}else {
				if(data=="hasFinshed") {
					$.messager.alert('温馨提示', "已经被预约完成了，请重新加载" ,'error');
					return;
				}else if(data=="hasOwnedTask") {
					$.messager.alert('温馨提示', "当前已经有人在选择的时间上将任务分配给此人了，如日历没显示请刷新" ,'error');
					return;
				}
				$.messager.alert('温馨提示', data ,'error');
			}
		}
	});
	$('#submitCheck-form').submit();
	
	
}

function makeCall(url){
	$.get(url, {},function(data){
		if(data.indexOf("error") == 0){
			//$.messager.alert('温馨提示', "发生错误",'error');
		}
	});
}

function initPage (houseId) {
	//加载 缩略图 图片
	loadHouseImage(houseId);
	//加载 拍摄时间与位置信息
	loadBdtaskFinishAndLBS(houseId);
}

//加载    图片
function loadHouseImage(houseId) {
	$.ajax({ 
        type: "post", 
        url: "${ctx}/sourcemanage/getHouseImage",
        data: "houseId=" + houseId,
        dataType: "json", 
        success: function (data) {
        	setHouseImage(data);
        }
	});
} 

function setHouseImage(data) {
	if (data.houseImageList != undefined) {
		
		var length = data.houseImageList.length;
		
		var str = "";
		for (var i = 1; i < data.houseImageList.length + 1; i++) {
			var o = data.houseImageList[i - 1];
			
			//if (i % 3 == 1) {
				str += "<tr>";
			//}
			
			str += "<td>";
			str += "	<div style=\"margin-bottom: 5px\">" + o.imgDes + "</div>";
			str += "	<div>";
			str += "		<img src=\"<%=com.manyi.fyb.callcenter.utils.Constants.ALIYUN_IMAGE_PATH_PREFIX %>/" + o.thumbnailUrl + "\" />";
			str += "	</div>";
			str += "</td>";
			
			//if (i % 3 == 0) {
				str += "</tr>";
			//}
		}
		
		//if (length % 3 == 1) {
		//	str += "<td>&nbsp;</td><td>&nbsp;</td></tr>";
		//} else if (length % 3 == 2) {
		//	str += "<td>&nbsp;</td></tr>";
		//}
		
		$("#imageTableId").html(str == ""?"":"<table cellpadding=\"10\">" + str + "</table>");
		
	}
}

//加载    拍摄时间与位置信息
function loadBdtaskFinishAndLBS(houseId) {
	$.ajax({ 
        type: "post", 
        url: "${ctx}/sourcemanage/getBdtaskFinishAndLBS",
        data: "houseId=" + houseId,
        dataType: "json", 
        success: function (data) {
        	setBdtaskFinishAndLBS(data);
        }
	});
} 

function setBdtaskFinishAndLBS(data) {
	if (data.bdtaskDateAndLBS != undefined) {
		
		var o = data.bdtaskDateAndLBS;
		
		$("#finishDate").html(o.finishDate);//完成时间
		$("#lbs").html("经度 " + o.longitude + "  纬度 " + o.latitude);// 坐标 经度longitude  ;// 坐标 纬度latitude
		
	}		
}
initPage('${css.house.houseId }');

</script>
</html>