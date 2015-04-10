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
//初始化 房屋配置信息
function createSupporting(data){
	var  str='<div id="supportingDiv"><input type="hidden" name="id" value="${house.supporing.id}"/>';
	str +='基础配置 : <input type="checkbox" name="hasTV" id="hasTV" ';
	if(${house.supporing.hasTV} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasTV">电视</label> ';
	
	str +='<input type="checkbox" name="hasRefrigerator" id="hasRefrigerator" ';
	if(${house.supporing.hasRefrigerator} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasRefrigerator">冰箱</label> ';
	
	str +='<input type="checkbox" name="hasWashingMachine" id="hasWashingMachine" ';
	if(${house.supporing.hasWashingMachine} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasWashingMachine">洗衣机</label> ';
	
	str +='<input type="checkbox" name="hasAirConditioner" id="hasAirConditioner" ';
	if(${house.supporing.hasAirConditioner} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasAirConditioner">空调</label> ';
	
	str +='<input type="checkbox" name="hasWaterHeater" id="hasWaterHeater" ';
	if(${house.supporing.hasWaterHeater} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasWaterHeater">热水器</label> ';
	
	str +='<input type="checkbox" name="hasBed" id="hasBed" ';
	if(${house.supporing.hasBed} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasBed">床</label> ';
	
	str +='<input type="checkbox" name="hasSofa" id="hasSofa" ';
	if(${house.supporing.hasSofa} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasSofa">沙发</label> ';
	
	str +='<input type="checkbox" name="hasBathtub" id="hasBathtub" ';
	if(${house.supporing.hasBathtub} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasBathtub">浴缸/淋浴 </label> <br/>';
	
	str +='高端配置 : <input type="checkbox" name="hasCentralHeating" id="hasCentralHeating" ';
	if(${house.supporing.hasCentralHeating} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasCentralHeating">暖气/地暖 </label>';
	
	str +='<input type="checkbox" name="hasCentralAirConditioning" id="hasCentralAirConditioning" ';
	if(${house.supporing.hasCentralAirConditioning} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasCentralAirConditioning">中央空调</label> ';
	
	str +='<input type="checkbox" name="hasCloakroom" id="hasCloakroom" ';
	if(${house.supporing.hasCloakroom} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasCloakroom">衣帽间</label> ';
	
	str +='<input type="checkbox" name="hasReservedParking" id="hasReservedParking" ';
	if(${house.supporing.hasReservedParking} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasReservedParking">车位</label> ';
	
	str +='<input type="checkbox" name="hasCourtyard" id="hasCourtyard" ';
	if(${house.supporing.hasCourtyard} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasCourtyard">院落</label> ';
	
	str +='<input type="checkbox" name="hasGazebo" id="hasGazebo" ';
	if(${house.supporing.hasGazebo} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasGazebo">露台</label> ';
	
	str +='<input type="checkbox" name="hasPenthouse" id="hasPenthouse" ';
	if(${house.supporing.hasPenthouse} ){
		str +='checked="checked"';
	}
	str+='/> <label for="hasPenthouse">阁楼</label> ';
	
	str+='</div>';
	$("#supportingLi").html(str);
}
function submitFrm(){
	//验证

	var floor = $("#floor").val();
	if(floor == null || floor ==''){
		$("input[name='floor']").val("0");
	}
	if(!checkJson("building-aliasName")){
		$.messager.alert('温馨提示','别名不能重复.','error');
		return false;
	}
	
	//组装json
	createJsonFromInput("house-aliasName","aliasName");
	
	//组装图片
	var uploadBlockJson = createJsonFromImg("livingRoom","change-demo","livingRoom");
	var uploadBlockOutterJson = createJsonFromImg("bedRoom","change-demo","bedRoom");
	var uploadBlockMailboxJson = createJsonFromImg("wc","change-demo","wc");
	var imgsJson = (uploadBlockJson == '' ? '' : uploadBlockJson+",")+(uploadBlockOutterJson == '' ? '' : uploadBlockOutterJson+",")+
					(uploadBlockMailboxJson == '' ? '' : uploadBlockMailboxJson+",");
	if(imgsJson.length>1){
		imgsJson = imgsJson.substring(0, imgsJson.length-1);
	}
	imgsJson ="["+imgsJson+"]";
	$("#imgkeys").val(imgsJson);
	
	//alert('验证完毕');
	//$("#editHouseFrm").submit();
	var frmData = $("#editHouseFrm").serialize();
	var frmUrl=$("#editHouseFrm").attr('action');
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

	<div class="easyui-panel" title="房屋基础信息" style="width:100%px;height:100%px">
       <div class='estate-edit'>
       <form action="${ctx }/house/editHouse" method="post" id='editHouseFrm'>
       <div>
       	<input type="hidden" name="houseId" value="${house.houseId }"/> 
       	<input type="hidden" name="aliasName" id="aliasName"/> 
       	<input type="hidden" name="imgkeys" id="imgkeys"/>
       </div>
       <table cellpadding="5" border="1" style="border:#E0ECFF solid 1px; width:80%;" cellspacing="0" >
       	<tr> 
       		<td>房源ID: ${house.houseId}</td> <td>   审核状态 : ${house.statusStr}</td>
       	</tr>
       	<tr>
       		<td style="width:100px;">小区ID:${house.estateId}</td><td>子划分ID: ${house.subEstateId} </td>
       	</tr>
       	
       	<tr><td>房间号:</td><td> ${house.room}</td></tr>
       	
       	<tr><td>房间别名</td><td>
       		<div>
       		<ul id="house-aliasName" class="btn-input">
       		</ul>
       		</div>
       	 </td></tr>
       	 <tr>
       	 	<td>租售状态:</td><td> ${house.houseStateStr}</td>
       	 </tr>
	       	 <c:if test="${house.rentPrice != null }">
	       	 	<tr><td>租价:</td><td> ${house.rentPrice} 元/月</td></tr>
	       	 </c:if>
	       	 <c:if test="${house.sellPrice != null }">
	       	 	<tr><td>售价:</td><td> ${house.sellPrice} 万元</td></tr>
	       	 </c:if>
       	 	
       	 	
       	 	<tr>
       	 	<td>房型:</td><td> ${house.bedroomSum}室  ${house.livingRoomSum}厅  ${house.wcSum}卫 </td>
       	 	</tr><tr>
       	 	<td>面积:</td><td> ${house.spaceArea}</td>
       	 	</tr><tr>
       	 	<td>所在物理层数:</td><td> <input class=' easyui-numberbox validatebox-text ' type="text" name="floor" style="width:160px;" value="${house.floor }" id="floor" /></td>
       	 </tr>
       	
       	 <tr>
       	 	<td>客厅:</td>
       	 	<td>
       	 		<div id="livingRoom">
       	 		<c:forEach items="${house.images}" var="image">
       	 			<c:if test="${image.type == 'livingRoom'}">
		
								<div id="change_${image.id }_${image.orderId }" class="change-demo" args="${image.orderId }" idNum="${image.id }">
									<div>id:${image.id }</div>
									<div><img alt="" src="${image.imgKeyStr}" style="height:160px;width:160px"></img></div>
									<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','house1','${house.serialCode }','${house.houseId }')"  />
									<input type="hidden"  name="imgKey"  value="${image.imgKey}"    />
									<div>
									<span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png"/></span>
									<span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span>
									<span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png"  /></span>
									</div>
								</div>
							
       	 			</c:if>
       	 		</c:forEach>
       	 		<input type="button" name="appendUploadDiv" value="添加客厅图" onclick="addCurrUpload('livingRoom','change',this,'house1','${ctx }','${house.serialCode }','${house.houseId }')" />
				<iframe id="pic_hidden_frame" name="pic_hidden_frame" style="display:none"></iframe>
				</div>
       	 	</td>
       	 </tr>
       	 
       	 <tr>
       	 	<td>卧室:</td>
       	 	<td>
       	 		<div id="bedRoom">
       	 		<c:forEach items="${house.images}" var="image">
       	 			<c:if test="${image.type == 'bedRoom'}">
		
								<div id="change_bedRoom_${image.id }_${image.orderId }" class="change-demo" args="${image.orderId }" idNum="${image.id }">
									<div>id:${image.id }</div>
									<div><img alt="" src="${image.imgKeyStr}" style="height:160px;width:160px"></img></div>
									<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','house2','${house.serialCode }','${house.houseId }')"  />
									<input type="hidden"  name="imgKey"  value="${image.imgKey}"    />
									<div>
									<span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png"/></span>
									<span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span>
									<span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png"  /></span>
									</div>
								</div>
							
       	 			</c:if>
       	 		</c:forEach>
       	 		<input type="button" name="appendUploadDiv" value="添加卧室图" onclick="addCurrUpload('bedRoom','change_bedRoom',this,'house2','${ctx }','${house.serialCode }','${house.houseId }')" />
				</div>
       	 	</td>
       	 </tr>
       	 
       	 <tr>
       	 	<td>卫生间:</td>
       	 	<td>
       	 		<div id="wc">
       	 		<c:forEach items="${house.images}" var="image">
       	 			<c:if test="${image.type == 'wc'}">
		
								<div id="change_wc_${image.id }_${image.orderId }" class="change-demo" args="${image.orderId }" idNum="${image.id }">
									<div>id:${image.id }</div>
									<div><img alt="" src="${image.imgKeyStr}" style="height:160px;width:160px"></img></div>
									<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','house3','${house.serialCode }','${house.houseId }')"  />
									<input type="hidden"  name="imgKey"  value="${image.imgKey}"    />
									<div>
									<span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png"/></span>
									<span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span>
									<span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png"  /></span>
									</div>
								</div>
							
       	 			</c:if>
       	 		</c:forEach>
       	 		<input type="button" name="appendUploadDiv" value="添加卫生间图" onclick="addCurrUpload('wc','change_wc',this,'house3','${ctx }','${house.serialCode }','${house.houseId }')" />
				</div>
       	 	</td>
       	 </tr>
       	 <tr>
       	 	<td>装修情况:</td>
       	 	<td id="supportingLi"></td>
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
	createInputFormJson(${house.aliasName},"house-aliasName","aliasName",'${ctx}');
	//初始化 房屋配置信息
	createSupporting([]);
});
</script>

</body>
</html>