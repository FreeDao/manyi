<%@page import="com.manyi.fyb.callcenter.utils.Constants"%>
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
<script type="text/javascript" src="${ctx}/easyui/common.js"></script>

<style type="text/css">
.estate-edit  ul{
	margin:1px;
	padding:1px;
}
.estate-edit li{
	padding:1px 1px 3px 0px;
	list-style: none; 
	
}
.estate-edit table{
	margin-left:50px;
}
.btn-input img{
	cursor: pointer;
}
</style>
<script type="text/javascript">

function submitFrm(){
	//验证
	var name = $("#name");
	if(name.val() == '' || name.val() == null){
		$.messager.alert('温馨提示','楼栋号不能为空.','error');
		name.focus();
		return false;
	}
	var totalLayers = $("#totalLayers").val();
	if(totalLayers == null || totalLayers ==''){
		$("input[name='totalLayers']").val("0");
	}
	var finishDate = $("#finishDate").val();
	if(finishDate == null || finishDate ==''){
		$("input[name='finishDate']").val("0");
	}
	
	if(!checkJson("building-aliasName")){
		$.messager.alert('温馨提示','别名不能重复.','error');
		return false;
	}
	
	//组装json
	createJsonFromInput("building-aliasName","aliasName");
	
	//组装图片
	var uploadBlockJson = createJsonFromImg("uploadBlock","change-demo",1);
	var uploadBlockOutterJson = createJsonFromImg("uploadBlockOutter","change-demo",2);
	var uploadBlockMailboxJson = createJsonFromImg("uploadBlockMailbox","change-demo",3);
	var uploadBlockElevatorJson = createJsonFromImg("uploadBlockElevator","change-demo",4);
	var imgsJson = (uploadBlockJson == '' ? '' : uploadBlockJson+",")+(uploadBlockOutterJson == '' ? '' : uploadBlockOutterJson+",")+
					(uploadBlockMailboxJson == '' ? '' : uploadBlockMailboxJson+",")+(uploadBlockElevatorJson == '' ? '' : uploadBlockElevatorJson+",");
	if(imgsJson.length>1){
		imgsJson = imgsJson.substring(0, imgsJson.length-1);
	}
	imgsJson ="["+imgsJson+"]";
	$("#imgkeys").val(imgsJson);
	
	//alert('验证完毕');
	//$("#editEstateFrm").submit();
	var frmData = $("#editEstateFrm").serialize();
	var frmUrl=$("#editEstateFrm").attr('action');
	$.ajax({
		url:frmUrl,
		data:frmData,
		type:'post',
		success:function(data){
			alert('编辑完成.');
			window.location.href=window.location.href;
		},
		error:function(data){
			$.messager.alert('温馨提示',data,'error');
		}
	});
}
</script>
</head>
<body>

	<div class="easyui-panel" title="楼栋基础信息" style="width:100%px;height:100%px">
       <div class='estate-edit'>
       <form action="${ctx }/building/editBuilding" method="post" id='editEstateFrm'>
       <div>
       	<input type="hidden" name="id" id="id" value="${building.id }"/> 
       	<input type="hidden" name="aliasName" id="aliasName"/> 
       	<input type="hidden" name="imgkeys" id="imgkeys"/>
       </div>
       <table cellpadding="5" border="1" style="border:#E0ECFF solid 1px; width:80%;" cellspacing="0" >
       	<tr> 
       		<td>楼栋ID: ${building.id}</td> <td>   数据状态 : ${building.statusStr}</td>
       	</tr>
       	<tr>
       		<td style="width:100px;">小区ID:</td><td> ${building.estateId} </td>
       	</tr>
       	<tr>
       		<td>子划分ID:</td><td> ${building.subEstateId} </td>
       	</tr>
       	
       	<tr><td>楼栋号*:</td><td> <input class='validatebox-text '  type="text" name="name" style="width:130px;" value="${building.name }" id="name" /></td></tr>
       	
       	<tr><td>楼栋别名</td><td>
       		<div>
       		<ul id="building-aliasName" class="btn-input">
       		</ul>
       		</div>
       	 </td></tr>
       	 
       	<tr><td>楼栋经纬度:</td><td> <input class="easyui-numberbox" min='0' max='180' data-options="precision:6" type='text' value="${building.longitude }" id="longitude" name="longitude" style="width:100px;"/>
       				<input class="easyui-numberbox" min='0' max='90' data-options="precision:6" type='text' value="${building.latitude }" id="latitude" name="latitude" style="width:100px;"/>
       	</td></tr>
       	
       	<tr><td>地上总层高:</td><td> <input class=' easyui-numberbox validatebox-text ' min="0" type="text" name="totalLayers" style="width:130px;" value="${building.totalLayers }" id="totalLayers" /></td></tr>
       	
       	<tr><td>竣工年代:</td><td> <input class=' easyui-numberbox validatebox-text ' min="1000" max="3000" type="text" name="finishDate" style="width:130px;" value="${building.finishDate }" id="finishDate" /></td></tr>
       	
       	 <tr>
       	 	<td>楼栋内景图:</td>
       	 	<td>
       	 		<div id="uploadBlock">
       	 		<c:forEach items="${building.images}" var="image">
       	 			<c:if test="${image.type == 1}">
		
								<div id="change_${image.id }_${image.orderNum }" class="change-demo" args="${image.orderNum }" idNum="${image.id }">
									<div>id:${image.id }</div>
									<div><img alt="" src="${image.imgKeyStr}" style="height:160px;width:160px"></img></div>
									<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','building1','${building.serialCode }','${building.id }')"  />
									<input type="hidden"  name="imgKey"  value="${image.imgKey}"    />
									<div>
									<span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png"/></span>
									<span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span>
									<span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png"  /></span>
									</div>
								</div>
							
       	 			</c:if>
       	 		</c:forEach>
       	 		<input type="button" name="appendUploadDiv" value="添加内景图" onclick="addCurrUpload('uploadBlock','change',this,'building1','${ctx }','${building.serialCode }','${building.id }')" />
				<iframe id="pic_hidden_frame" name="pic_hidden_frame" style="display:none"></iframe>
				</div>
       	 	</td>
       	 </tr>
       	 <tr>
       	 	<td>楼栋外景图:</td>
       	 	<td>
       	 		<div id="uploadBlockOutter">
       	 		<c:forEach items="${building.images}" var="image">
       	 			<c:if test="${image.type == 2}">
		
								<div id="change_outter_${image.id }_${image.orderNum }" class="change-demo" args="${image.orderNum }" idNum="${image.id }">
									<div>id:${image.id }</div>
									<div><img alt="" src="${image.imgKeyStr}" style="height:160px;width:160px"></img></div>
									<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','building2','${building.serialCode }','${building.id }')"  />
									<input type="hidden"  name="imgKey"  value="${image.imgKey}"    />
									<div>
									<span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png"/></span>
									<span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span>
									<span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png"  /></span>
									</div>
								</div>
							
       	 			</c:if>
       	 		</c:forEach>
       	 		<input type="button" name="appendUploadDiv" value="添加外景图" onclick="addCurrUpload('uploadBlockOutter','change_outter',this,'building2','${ctx }','${building.serialCode }','${building.id }')" />
				</div>
       	 	</td>
       	 <tr>
       	 	<td>楼栋信箱图:</td>
       	 	<td>
       	 		<div id="uploadBlockMailbox">
       	 		<c:forEach items="${building.images}" var="image">
       	 			<c:if test="${image.type == 3}">
		
								<div id="change_mailbox_${image.id }_${image.orderNum }" class="change-demo" args="${image.orderNum }" idNum="${image.id }">
									<div>id:${image.id }</div>
									<div><img alt="" src="${image.imgKeyStr}" style="height:160px;width:160px"></img></div>
									<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','building3','${building.serialCode }','${building.id }')"  />
									<input type="hidden"  name="imgKey"  value="${image.imgKey}"    />
									<div>
									<span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png"/></span>
									<span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span>
									<span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png"  /></span>
									</div>
								</div>
							
       	 			</c:if>
       	 		</c:forEach>
       	 		<input type="button" name="appendUploadDiv" value="添加信箱图" onclick="addCurrUpload('uploadBlockMailbox','change_mailbox',this,'building3','${ctx }','${building.serialCode }','${building.id }')" />
				</div>
       	 	</td>
       	 <tr>
       	 	<td>楼栋电梯图:</td>
       	 	<td>
       	 		<div id="uploadBlockElevator">
       	 		<c:forEach items="${building.images}" var="image">
       	 			<c:if test="${image.type == 4}">
		
								<div id="change_elevator_${image.id }_${image.orderNum }" class="change-demo" args="${image.orderNum }" idNum="${image.id }">
									<div>id:${image.id }</div>
									<div><img alt="" src="${image.imgKeyStr}" style="height:160px;width:160px"></img></div>
									<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','building4','${building.serialCode }','${building.id }')"  />
									<input type="hidden"  name="imgKey"  value="${image.imgKey}"    />
									<div>
									<span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png"/></span>
									<span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span>
									<span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png"  /></span>
									</div>
								</div>
							
       	 			</c:if>
       	 		</c:forEach>
       	 		<input type="button" name="appendUploadDiv" value="添加电梯图" onclick="addCurrUpload('uploadBlockElevator','change_elevator',this,'building4','${ctx }','${building.serialCode }','${building.id }')" />
				</div>
       	 	</td>
       	 </tr>
       	 <tr>
       	 	<td colspan="2">
       	 		<input type="button" value="提交" onclick="submitFrm()"/>
       	 	</td>
       	 </tr>
       	 </table>
       	 </form>
       </div>
    </div>
    
<script>
$(document).ready(function(){
	//初始化别名
	createInputFormJson(${building.aliasName},"building-aliasName","aliasName",'${ctx}');
});
</script>

</body>
</html>