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
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/demo.css">
	<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body onresize="resizeGrid();">
	<c:if test="${ custServWorkFlowFlag == 1 || test == 1}">
		<jsp:include page="check_workingMode.jsp"></jsp:include>
	</c:if>
	<%-- - //角色 1管理员,2客服经理，3客服人员,4地推经理,5地推人员 6财务  -> --%>
	<c:if test="${ sessionScope.login_session.role > 0 || test == 1}">
	<div class="easyui-tabs" style="width:auto;height:auto">
		<div title="发布类型" style="width:auto;height:auto;padding:10px">
			<table style="width:100%; border-spacing:  10px;">
				<tr style="width:auto;text-align: center ;" >
					<td style="width:25%"><b>发布类型：</b>${css.sourceLog.sourceLogTypeStr}</td>
					<td style="width:25%"><b>所属房源：</b>${css.house.subEstateName } - ${css.house.building } - ${css.house.room } <a href="#">查看</a></td>
					<td style="width:25%"><b>客服审核状态：</b>${css.sourceLog.operCSStateStr }</td>
					<td style="width:25%"><b>客服责任人：</b><c:choose><c:when test="${css.sourceLog.operCSName == null || css.sourceLog.operCSName == ''}" > - </c:when><c:otherwise>${css.sourceLog.operCSName }</c:otherwise></c:choose></td>
				</tr>
				
				<tr style="width:auto;text-align: center;" >
					<td style="width:25%"><b>发布人：</b>${css.sourceLog.userName} <a href="#">查看</a></td>
					<td style="width:25%"><b>区域板块：</b>${css.house.cityName }-${css.house.townName }</td>
					<td style="width:25%"><b>看房审核状态：</b>${css.sourceLog.operFSStateStr }</td>
					<td style="width:25%"><b>看房责任人：</b><c:choose><c:when test="${css.sourceLog.operFSName == null || css.sourceLog.operFSName == ''}" > - </c:when><c:otherwise>${css.sourceLog.operFSName }</c:otherwise></c:choose></td>
				</tr>
				
				<tr style="width:auto;text-align: center;" >
					<td style="width:25%"><b>发布时间：</b><fmt:formatDate value="${css.sourceLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td style="width:25%"><b>房源编号：</b>${css.house.houseId }</td>
					<td style="width:25%"></td>
					<td style="width:25%"></td>
				</tr>
				<tr style="width:auto;text-align: center;" >
					<td style="width:25%"><b>改盘理由：</b>${css.sourceLog.remark }</td>
					<td style="width:25%"></td>
					<td style="width:25%"></td>
					<td style="width:25%"></td>
				</tr>
				
			</table>
		</div>
	</div>
	</c:if>
	<c:if test="${ (sessionScope.login_session.role > 0 && sessionScope.login_session.role < 6) || test == 1}">
	<div class="easyui-tabs" style="width:auto;height:auto">
		<c:if test="${ (sessionScope.login_session.role > 0 && sessionScope.login_session.role < 4) || test == 1}">
		<div title="客服审核工单" style="width:auto;height:auto;padding:10px">
			<div>
			<form id="CSForm" name="CSForm" method="post" action="">
			<c:forEach items="${css.sourceHost }" var="host" varStatus="status">
				
				<c:if test="${ host.status == 0}"></c:if>
				<span>${host.hostName }-${host.hostMobile }</span>
				<input type="radio" name="host1Status"  title="业主信息正确" value="1" />业主信息正确
				<input type="radio" name="host1Status" title="不配合、再联系、关机、无人接听、无法接通、来电提醒" value="2" />不配合、再联系、关机、无人接听、无法接通、来电提醒
				<input type="radio" name="host1Status" title="停机、空号、错号" value="3" />停机、空号、错号
				<br/>
				
				<span>${host.hostName }-${host.hostMobile }</span>
				<input type="radio" name="host2Status"  title="业主信息正确" value="1" />业主信息正确
				<input type="radio" name="host2Status" title="不配合、再联系、关机、无人接听、无法接通、来电提醒" value="2" />不配合、再联系、关机、无人接听、无法接通、来电提醒
				<input type="radio" name="host2Status" title="停机、空号、错号" value="3" />停机、空号、错号
				<br/>
				
				<span>${host.hostName }-${host.hostMobile }</span>
				<input type="radio" name="host3Status"  title="业主信息正确" value="1" />业主信息正确
				<input type="radio" name="host3Status" title="不配合、再联系、关机、无人接听、无法接通、来电提醒" value="2" />不配合、再联系、关机、无人接听、无法接通、来电提醒
				<input type="radio" name="host3Status" title="停机、空号、错号" value="3" />停机、空号、错号
				<br/>
			</c:forEach>
				<hr style="width:auto;height:auto"/>
				<span>出售状态：</span>
				<input type="radio" name="sellStatus"  title="出售" value="0" />出售
				<input type="radio" name="sellStatus" title="已售" value="1" />已售
				<input type="radio" name="sellStatus" title="已售" value="2" />不售
				<span>| 出租状态：</span>
				<input type="radio" name="rentStatus"  title="出租" value="0" />出租
				<input type="radio" name="rentStatus" title="已租" value="1" />已租
				<input type="radio" name="rentStatus" title="不租" value="2" />不租
				<br/>
				到手价：<input type="text" name="sellPrice" value="" />万
				| 租金：<input type="text" name="rentPrice" value="" />元/月
				<hr style="width:auto;height:auto" />
				<table style="width:100%">
					<tr style="width:auto;">
						<td style="width:20%">
							<span>面积：<input type="text" name="spaceArea" value="${css.sourceLog.spaceArea }" />平米</span>
							<br/>
							<span>房型：<input type="text" name="bedroomSum" value="${css.sourceLog.bedroomSum}" style="width: 20px" />室
							<input type="text" name="livingRoomSum" value="${css.sourceLog.livingRoomSum}" style="width: 20px" />厅
							<input type="text" name="wcSum" value="${css.sourceLog.wcSum }" style="width: 20px" />卫
							</span>
							<br/>
							<input type="submit" name="" value="提交"/>
							
						</td>
						<td style="width:80%">
							<span>联系业主备注（选填）</span>
							<textarea name="note"></textarea>
						</td>
					<tr>
				</table>
				</form>
			</div>
			
		</div>
		</c:if>
		<c:if test="${ (sessionScope.login_session.role == 1 && sessionScope.login_session.role == 4 && sessionScope.login_session.role == 5) || test == 1}">
		<div  title="看房审核工单" style="width:auto;height:auto;padding:10px">
			<div>
			<table  style="width:100% ;">
				<tr style="width:auto;" >
					<td style="width:49%">
						<span>经纪人：${css.sourceLog.userName}-${css.sourceLog.userMobile}</span>
						<br/>
						<span>联系记录：</span>
						<br/>
						<form id="contactUser-form" method="post" action="${ctx }/check/floorServ/contactUser">
							<textarea name="lookNote" class="easyui-validatebox " style="height:50px" data-options="required:true"></textarea>
							<br/>
							<input type="radio" name="lookStatus"  title="不能看房" value="1" />不能看房
							<input type="radio" name="lookStatus" title="确定时间看房" value="2" />确定时间看房
							<input type="radio" name="lookStatus" title="等待安排看房" value="3" />等待安排看房
							<br/>
							<input type="submit" value="提交"/>
						</form>
						
					</td>
					<td style="width:10%; " >
						<span>暂无看房安排</span>
						<br/>
						<a href="javascript:void(0)" onclick="$('#submitResult-dlg').dialog('open')">提交结果</a>
						
					</td>
					<td style="width:19%; ">
						<input type="submit" value="新增看房安排" />
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#addAMeeting-dlg').dialog('open')">新增看房安排</a>
						
					</td>
				</tr>
			</table>
			</div>
		</div>
		</c:if>
	</div>
	</c:if>
	<c:if test="${ sessionScope.login_session.role == 1 || sessionScope.login_session.role == 2 || sessionScope.login_session.role == 4 || test == 1}" >
	<div class="easyui-tabs" style="width:auto;height:auto">
		<div title="客服审核记录" style="width:auto;height:auto;padding:10px">
			<c:forEach items="${css.sourceLogHistories}" var="history">
			<c:if test="${history.ifLookStatus == 0}" >
			<div>
				<h3>${history.addTime}</h3>
				${history.logText }
				
			</div>
			</c:if>
			</c:forEach>
			
		</div>
		<div title="看房审核记录" style="width:auto;height:auto;padding:10px">
			<c:forEach items="${css.sourceLogHistories}" var="history">
			<c:if test="${history.ifLookStatus == 1}" >
			<div>
				<h3>${history.addTime}</h3>
				${history.logText }
				
			</div>
			</c:if>
			</c:forEach>
			<div>
				<h3>2013-3-2 15:00</h3>
				提交看房结果：看房成功<br/>
				看房记录：房东很配合<br/>
				到手价：修改为470万<br/>
				修改人：陶冶<br/>
			</div>
		</div>
	</div>
	</c:if>
	
	<div id="addAMeeting-dlg" class="easyui-dialog" title="添加看房安排" data-options="iconCls:'icon-save' ,closed: true" style="width:430px;height:auto;padding:10px;">
		<div class="easyui-panel"   style="width:400px">
		<div style="padding:10px 60px 20px 60px">
		<form id="addAMeeting-form" method="post" action="${ctx}/check/floorServ/addAMeeting">
			<table cellpadding="5">
				<tr>
					<td>时间:</td>
					<td><input class="easyui-datetimebox" data-options="required:true" name="addTime" style="width:200px"></input></td>
				</tr>
				<tr>
					<td>备注:</td>
					<td><textarea  class="easyui-validatebox " style="height:50px"  name="note" ></textarea></td>
				</tr>
			</table>
		
		<div style="text-align:center;padding:5px">
			<input type="submit" value="提交"/>
			
		</div>
		</form>
		</div>
	</div>
	</div>
	
	<div id="submitResult-dlg" class="easyui-dialog" title="提交结果" data-options="iconCls:'icon-save' ,closed: true" style="width:430px;height:auto;padding:10px;">
		<div class="easyui-panel"  style="width:400px">
		<div style="padding:10px 60px 20px 60px">
		<form id="submitResult-form" method="post" action="${ctx}/checkfloorServ/submitResult">
			<table cellpadding="5">
			
				<tr>
					<td>Name:</td>
					<td><input class="easyui-validatebox textbox" type="text" name="name" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input class="easyui-validatebox textbox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
				</tr>
				<tr>
					<td>Subject:</td>
					<td><input class="easyui-validatebox textbox" type="text" name="subject" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>Message:</td>
					<td><input class="textbox" name="message"></input></td>
				</tr>
				<tr>
					<td>Language:</td>
					<td>
						<select class="easyui-combobox" name="language"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
		</div>
		</div>
	</div>
	</div>
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
	//console.debug(document.body.clientHeidth * 0.9);
	$(".easyui-tabs div").width(  document.body.clientWidth - 43 );
	//$("#lookfloorCheck_table_BUG").width(  document.body.clientWidth * 100 - 43 );
}
$(function(){
	$('#contactUser-form').form({
		success:function(data){
			$.messager.alert('Info', data);
			if(data=="success"){
				
				console.debug("ok");
			}
		}
	});
	
	$('#addAMeeting-form').form({
		success:function(data){
			$.messager.alert('Info', data);
			if(data=="success"){
				
				console.debug("ok");
			}
		} 
	});
	
	$('#submitResult-form').form({
		success:function(data){
			$.messager.alert('Info', data);
			if(data=="success"){
				console.debug("ok");
			}
		}
	});
});

</script>
</html>