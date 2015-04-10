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
/**
 * 添加一个 上传图片控件
 */
function addCurrUpload(id,subid,currDom,uploadType){
	var sum = $("#"+id+" .change-demo" ).size();
	for (var i = 0; i < sum; i++) {
		console.debug($("#"+id+" .change-demo" ));
		var num= $($("#"+id+" .change-demo" ).get(i)) .attr('args');
		if(num > sum) sum =num;
		
	}
	//$.messager.alert('提示',sum);
	sum++;
	var html = "<div id='"+subid+"_" + sum+ "' class='change-demo'   args='" + sum+ "'><div>" + sum+ "<img alt='' src='' style='height:160px;width:160px'></img></div>"
		+"<input type='file' name='upload'  onchange=\"uploadCommon(this,'${ctx}','" + uploadType +"','${estate.serialCode }','${estate.estateId }')\"  />"
		+ "<input type='hidden'  name='imgKey'  value=''    />"
		+"<div><span onclick='swapUp(this)'><img alt='1' src='${ctx}/imgs/up.png' /></span><span onclick='removeCurrUpload(this)'><a href='javascript:void(0);' >删除</a></span><span onclick='swapDown(this)'><img alt='2' src='${ctx}/imgs/down.png' /></span></div></div>";
	var parentDom = $("#"+id+" input[name='appendUploadDiv']").before(html);
}


/**
 * 检测url是否可以访问, 利用ajax请求返回的http状态是否是200
 */
function checkUrlIsOk(url){
	var falg= true;
	$.ajax({
		'async':false,
		'type' :'get',
		'url': url,
		'success':function(txt,status){
		},
		'error':function(txt,status,xmlReq){
			if(xmlReq.status != 200){
				//不可访问
				falg = false;
			}
		}
	});
	return falg;
}
/*
上海行政区域转化
*/
function areaChange(obj,areaName){
	var area = $(obj);
	var parentId =area.val();
	var json =[];
	if(parentId == 1){}else{
		json = loadArea(parentId,false);
	}
	putOption(areaName,json);
	if(areaName=='area' ){
		putOption('town','');
	}
}

//加载 区域板块
function loadArea(parentId,isAll){
	/*
	 * 加载 数据字典/地区
	 */
	var url1= "${ctx}/house/loadAreaByParentId";
	var resultData={};
	$.ajax( {
		url : url1,
		data : {'parentId' : parentId, 'flag' : isAll},
		async:false,
		method : 'POST',
		success : function(data) {
			//data =eval("("+data+")");
			resultData = data.areaList;
		},
		error : function(data) {
		}
	});
	return resultData;
}
/*
添加下拉框的数据
*/
function putOption(key,json,selectId){
	if(!(json === undefined)){
		var str='';
		//if(key == 'cityType'||  key == 'area' ||  key == 'town'||   json == null || json.length == 0 ){
		//	str ='<option value="0" parentid="0" selected="selected">全部</option>';
		//}
		var sel=$("#"+key);
		sel.html(str);
		for(var i =0 ; i< json.length ; i++){
			var row = json[i];
			str +='<option value="'+row.areaId+'" parentid="'+row.parentId+'" ';
			if(selectId == row.areaId){
				str +=' selected="selected" ';
			}
			str +=' >'+row.name+'</option>';
		}
		sel.html(str);
	}
}

//初始化加载 小区 所属区域
function initArea(){
	var city_str = loadArea("1",false);// 中国下的城市
	putOption("cityType",city_str,"${estate.cityId}");//加载城市
	if(city_str != null && city_str.length>0){
		var area_str = loadArea("${estate.cityId}",false);
		putOption("area",area_str,"${estate.areaId}");//加载行政区
		if(area_str != null && area_str.length>0){
			var plan_str = loadArea("${estate.areaId}",false);
			putOption("town",plan_str,"${estate.townId}");//加载行政区
		}
	}
}

//检测 输入的json 是否 重复
function checkJson(key){
	var keys = $("#"+key).find("input") ;
	var flag= true;
	keys.each(function(p){
		var v1= $(this) ;
		if( v1.val() != ''){
			keys.each(function(p){
				var v2= $(this) ;
				if(v2.val() != '' && v1.val() == v2.val() && v1.attr('id') != v2.attr("id")){
					$(this).focus();
					flag = false;
					return ;
				}
			});
			if(!flag){
				return ;
			}
		}
	});
	return flag;
}

//把input数据数组组装成json字符串
function createJsonFromInput(key,name){
	var tmp="[";
	$("#"+key).find("input").each(function(p){
		if($(this).val() != ''){
			if($(this).attr("pid")){
				tmp +='{\'id\':\''+$(this).attr('pid');
			}else{
				tmp +='{\'id\':\'';
			}
			tmp +='\',\'name\':\''+$(this).val()+'\'},';
		}
	});
	if(tmp.length>1){
		tmp = tmp.substring(0, tmp.length-1);
	}
	tmp +="]";
	$("#"+name).val(tmp);
}
//把用户操作的图片 组装成json字符串
function createJsonFromImg(key,calName,type){
	var index = 0;
	var json = "";
	$("#"+key+" ."+calName).each(function(p){
		var id = $(this).attr("idNum");
		var imgkey = '';
		var tmp = $(this).find("input[name='imgKey']");
		if(tmp && tmp.val()){
			imgkey = tmp.val();
		}
		if(id && id !='' && id !='0'){
			if(imgkey !=''){
				index ++;
			}
			json +='{\'id\':\''+id+'\',\'name\':\''+imgkey+'\',\'type\':\''+type+'\',\'orderNum\':\''+index+'\'},';
		}else{
			if(imgkey !=''){
				index ++;
				//当ID不空null,img也为null的时候,才可以上传提交
				json +='{\'id\':\'\' , \'name\':\''+imgkey+'\',\'type\':\''+type+'\',\'orderNum\':\''+index+'\'},';
			}
		}
	});
	if(json.length>1){
		json = json.substring(0, json.length-1);
	}
	return json;
}
//生成 小区地址input
function createInputFormJson(json,key,name){
	var p =$("#"+key);
	if(json != null && json.length >0){
		for(var i=0; i<json.length;i++){
			var str= '<li><input style="width:160px;" type="text" id="'+name+(i+1)+'" value="'+json[i].name+'" pid="'+json[i].id+'"/> &nbsp; <img alt="添加" src="${ctx}/imgs/add.png"  onclick=\'createAddBun(\"'+key+'\",\"'+name+'\")\'/> ';
			if(i != 0){
				str +=' <img alt="删除" src="${ctx}/imgs/del.png" onclick=\'delAddBtn(\"'+key+'\",\"'+name+(i+1)+'\")\'/>';
			}
			str +='</li>';
			p.append(str);
		}
	}else{
		//先添加一个空的input框
		p.append('<li><input style="width:160px;" type="text" id="'+name+'1" value=""/> &nbsp; <img alt="添加" src="${ctx}/imgs/add.png" onclick=\'createAddBun(\"'+key+'\",\"'+name+'\")\'/></li>');
	}
}
//添加一个文本框
function createAddBun(key,name){
	var p =$("#"+key+" li:last-child");
	var max = p.find("input").attr("id").replace(name,"");
	max = parseInt(max);
	max ++;
	if(max <= 10){
		p.parent("ul").append('<li><input style="width:160px;" type="text" id="'+name+max+'" value=""/> &nbsp; <img alt="添加" src="${ctx}/imgs/add.png" onclick=\'createAddBun(\"'+key+'\",\"'+name+'\")\'/> <img alt="删除" src="${ctx}/imgs/del.png"  onclick=\'delAddBtn(\"'+key+'\",\"'+name+max+'\")\' /></li>');
	}
}
/**
 * 删除add按钮
 */
function delAddBtn(key,id){
	var curr = $("#"+id);
	if(curr.attr("pid") && curr.attr("pid") !=''){
		//删除了 数据库中的 数据  
		$("#"+key).prepend('<input type="hidden" id="'+curr.attr("id")+'" pid="'+curr.attr("pid")+'" value=""/>');
	}
	curr.parent("li").remove();
}
/*
 * 小区 经纬度 去重检验
 */
function checkDouble(){
	$.messager.alert('温馨提示','不要着急,正在完善.','warning');
}
function submitFrm(){
	//验证
	var longitude = $("#longitude");
	if(longitude.val() == '' || longitude.val() == null){
		$.messager.alert('温馨提示','小区经度不能为空.','error');
		longitude.focus();
		return false;
	}
	var latitude = $("#latitude");
	if(latitude.val() == '' || latitude.val() == null){
		$.messager.alert('温馨提示','小区纬度不能为空.','error');
		latitude.focus();
		return false;
	}
	var name = $("#name");
	if(name.val() == '' || name.val() == null){
		$.messager.alert('温馨提示','小区默认名称不能为空.','error');
		name.focus();
		return false;
	}
	var townId = $("#town");
	if(townId.val() == '' || townId.val() == null){
		$.messager.alert('温馨提示','区域板块不能空.','error');
		townId.focus();
		return false;
	}
	
	if(!checkJson("estate-axisname")){
		$.messager.alert('温馨提示','小区别名不能重复.','error');
		return false;
	}
	if(!checkJson("estate-address")){
		$.messager.alert('温馨提示','小区地址不能重复.','error');
		return false;
	}
	if(!checkJson("estate-subestate")){
		$.messager.alert('温馨提示','小区子划分不能重复.','error');
		return false;
	}
	
	//组装json
	createJsonFromInput("estate-axisname","aliasName");
	
	createJsonFromInput("estate-address","address");
	if($("#address").val().length<=2){
		$.messager.alert('温馨提示','小区地址不能为空.','error');
		return false;
	}
	createJsonFromInput("estate-subestate","subestate");
	if($("#subestate").val().length<=2){
		$.messager.alert('温馨提示','小区子划分不能为空.','error');
		return false;
	}
	var uploadBlockJson = createJsonFromImg("uploadBlock","change-demo",1);
	var uploadBlockOutterJson = createJsonFromImg("uploadBlockOutter","change-demo",2);
	var rangeImageJson = createJsonFromImg("rangeImageDiv","change-demo",0);//小区边界图
	var imgsJson = (uploadBlockJson == '' ? '' : uploadBlockJson+",")+(uploadBlockOutterJson == '' ? '' : uploadBlockOutterJson+",")+(rangeImageJson == '' ? '' : rangeImageJson+",");
	if(imgsJson.length>1){
		imgsJson = imgsJson.substring(0, imgsJson.length-1);
	}
	imgsJson ="["+imgsJson+"]";
	$("#imgkeys").val(imgsJson);
	
	//主门街景地址
	var mainUrl = $("#mainGateStreetSceneryLink");
	if(mainUrl.val() != null && mainUrl.val() != '' && !checkUrlIsOk(mainUrl.val())){
		$.messager.alert('温馨提示','主门街景地址不可访问.','error');
		mainUrl.focus();
		return false;
	}
	//alert('验证完毕');
	var frmData = $("#editEstateFrm").serialize();
	var frmUrl=$("#editEstateFrm").attr('action');
	$.ajax({
		url:frmUrl,
		data:frmData,
		type:'post',
		success:function(data){
			$.messager.alert('温馨提示','编辑完成.','info');
		},
		error:function(data){
			$.messager.alert('温馨提示',data,'error');
		}
	});
	
}
</script>
</head>
<body>

	<div class="easyui-panel" title="小区基础信息" style="width:100%px;height:100%px">
       <div class='estate-edit'>
       <form action="${ctx }/estate/editEstate" method="post" id='editEstateFrm'>
       <div>
       	<input type="hidden" name="estateId" id="estateId" value="${estate.estateId }"/> 
       	<input type="hidden" name="aliasName" id="aliasName"/> 
       	<input type="hidden" name="address" id="address"/> 
       	<input type="hidden" name="subestate" id="subestate"/> 
       	<input type="hidden" name="imgkeys" id="imgkeys"/> 
       	<input type="hidden" name="serialCode" id="serialCode" value="${estate.serialCode }"/> 
       </div>
       <table cellpadding="5" border="1" style="border:#E0ECFF solid 1px; width:80%;" cellspacing="0" >
       	<tr> 
       		<td style="width:100px;">小区ID: ${estate.estateId} </td> <td> 数据状态 :　${estate.statusStr}</td>
       	</tr>
       	<tr>
       		<td>小区边界图:</td><td> <%-- <img alt="边界图" src="${estate.estateRangeImgKey}" width="120" height="63"> 
       		<a href="javascript:void(0);" onclick="delRangeImg(${estate.estateId})">删除</a> --%>
       		
       		<div id="rangeImageDiv">
       			<div id="rangeImage_1" class="change-demo" args="1" idNum="1"> 
					<div><img alt="" src="${estate.estateRangeImgKeyStr}" style="height:160px;width:160px"></img></div>
					<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','estate0','${estate.serialCode }','${estate.estateId }')"  />
					<input type="hidden"  name="imgKey"  value="${estate.estateRangeImgKey}"    />
					
				</div>
			</div>
       		</td>
       	</tr>
       	
       	<tr><td>小区经纬度*:</td><td> <input class="easyui-numberbox" min='0' max='180' data-options="precision:6" type='text' value="${estate.longitude }" id="longitude" name="longitude" style="width:100px;"/>
       				<input class="easyui-numberbox" min='0' max='90' data-options="precision:6" type='text' value="${estate.latitude }" id="latitude" name="latitude" style="width:100px;"/>
       				<a href="javascript:void(0);" onclick="checkDouble()">去重检测</a>
       	</td></tr>
       	
       	<tr><td>小区默认名称*:</td><td> <input type="text" name="name" style="width:200px;" value="${estate.name }" id="name" class='validatebox-text ' maxlength="20"/></td></tr>
       	
       	<tr><td>城 市*:</td><td>
       	<select name="cityType" id="cityType" onchange="areaChange(this,'area')" style="width:130px;">
			<option value="0" selected="selected">全部</option>
			<!-- <option value="2">上海</option><option value="12217">北京</option>-->
		</select>
       	</td></tr>
       	
       	<tr><td>区域*:</td><td>
       	<select  id="area" name="areaId" style="width:120px;" onchange="areaChange(this,'town');">
			<option value="0" parentid='0'>全部</option>
		</select>
       	</td></tr>
       	
       	<tr><td>板块*:</td><td>
       	<select  id="town" name="townId" style="width:120px;">
			<option value="0" parentid='0'>全部</option>
		</select>
       	</td></tr>
       	
       	<tr><td>小区别名</td><td>
       		<div >
       			<ul id="estate-axisname" class="btn-input">
       			</ul>
       		</div>
       	 </td></tr>
       	<tr><td>小区地址</td><td>
       		<div>
       		<ul id="estate-address" class="btn-input">
       		</ul>
       		</div>
       	 </td></tr>
       	<tr><td>子划分*</td><td>
       		<div>
       		<ul id="estate-subestate" class="btn-input">
       		</ul>
       		</div>
       	 </td></tr>
       	 
       	<tr><td>主门经纬度:</td><td>
       		<input class="easyui-numberbox" min='0' max='180' data-options="precision:6" type='text' value="${estate.mainGateLongitude }" id="mainGateLongitude" name="mainGateLongitude" style="width:100px;"/>
			<input class="easyui-numberbox" min='0' max='90' data-options="precision:6" type='text' value="${estate.mainGateLatitude }" id="mainGateLatitude" name="mainGateLatitude" style="width:100px;"/>
       	 </td></tr>
       	<tr><td>主门地址:</td><td>
       		<input type="text" name="mainGateAddress" style="width:300px;" value="${estate.mainGateAddress }" id="mainGateAddress" class='validatebox-text '/>
       	 </td></tr>
       	<tr><td>正门街景:</td><td>
       		<input type="text" name="mainGateStreetSceneryLink" style="width:300px;" value="${estate.mainGateStreetSceneryLink }" id="mainGateStreetSceneryLink" class='validatebox-text '/>
       	 </td></tr>
       	<tr><td>建筑年代:</td><td>
       		<input type="text" name="constructDate" min='1000' max='3000' style="width:100px;" value="${estate.constructDate }" id="constructDate" class='easyui-numberbox validatebox-text '/>
       	 </td></tr>
       	<tr><td>小区类型:</td><td>
       		<select  id="type" name="type" style="width:120px;">
       		<!-- 1住宅 2别墅 3住宅别墅混合 -->
       		<option value="0"  selected="selected">--请选择--</option>
       		<option value="1" <c:if test="${estate.type == 1}" >selected="selected"</c:if> >住宅</option>
       		<option value="2" <c:if test="${estate.type == 2}" >selected="selected"</c:if> >别墅</option>
       		<option value="3" <c:if test="${estate.type == 3}" >selected="selected"</c:if> >住宅别墅混合</option>
			</select>
       	 </td></tr>
       	 <tr>
       	 	<td>小区内景图:</td>
       	 	<td>
       	 		<div id="uploadBlock">
       	 		<c:forEach items="${estate.images}" var="image">
       	 			<c:if test="${image.type == 1}">
		
								<div id="change_${image.orderNum }" class="change-demo" args="${image.orderNum }" idNum="${image.id }">
									<div>id:${image.id }</div>
									<div><img alt="" src="${image.imgKeyStr}" style="height:160px;width:160px"></img></div>
									<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','estate1','${estate.serialCode }','${estate.estateId }')"  />
									<input type="hidden"  name="imgKey"  value="${image.imgKey}"    />
									<div>
									<span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png"/></span>
									<span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span>
									<span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png"  /></span>
									</div>
								</div>
							
       	 			</c:if>
       	 		</c:forEach>
       	 		<input type="button" name="appendUploadDiv" value="添加内景图" onclick="addCurrUpload('uploadBlock','change',this,'estate1')" />
				<iframe id="pic_hidden_frame" name="pic_hidden_frame" style="display:none"></iframe>
				</div>
       	 	</td>
       	 </tr>
       	 <tr>
       	 	<td  style="border-top: 1px #000 solid;">小区外景图:</td>
       	 	<td  style="border-top: 1px #000 solid;">
       	 		<div id="uploadBlockOutter">
       	 		<c:forEach items="${estate.images}" var="image">
       	 			<c:if test="${image.type == 2}">
		
								<div id="change_outter_${image.orderNum }" class="change-demo" args="${image.orderNum }" idNum="${image.id }">
									<div>id:${image.id }</div>
									<div><img alt="" src="${image.imgKeyStr}" style="height:160px;width:160px"></img></div>
									<input type="file" name="upload"    onchange="uploadCommon(this,'${ctx}','estate2','${estate.serialCode }','${estate.estateId }')"  />
									<input type="hidden"  name="imgKey"  value="${image.imgKey}"    />
									<div>
									<span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png"/></span>
									<span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span>
									<span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png"  /></span>
									</div>
								</div>
							
       	 			</c:if>
       	 		</c:forEach>
       	 		<input type="button" name="appendUploadDiv" value="添加外景图" onclick="addCurrUpload('uploadBlockOutter','change_outter',this,'estate2')" />
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
	//初始化加载 小区 所属区域
	initArea();
	//初始化别名
	createInputFormJson(${estate.aliasName},"estate-axisname","aliasName");
	//初始化地址
	createInputFormJson(${estate.address},"estate-address","address");
	//初始化子划分
	createInputFormJson(${estate.subestate},"estate-subestate","substate");
});
</script>

</body>
</html>