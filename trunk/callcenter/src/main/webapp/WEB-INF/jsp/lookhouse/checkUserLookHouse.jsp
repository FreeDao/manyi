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
</head>

<body>

	<div class="easyui-panel" title="经纪人看房任务信息" style="width:100%px;height:120px">
         <div style="padding:10px 60px 20px 60px" id="taskpanel">
             <table cellpadding="5">
                <tr>
                <th>任务ID:</th><td id="taskId"></td>
                <th>任务状态:</th><td id='tastStatus'></td>
                <th>经纪人:</th><td id='taskUser'></td>
                <th>任务领取时间:</th><td id='createTime'></td>
                <th>照片上传时间:</th><td id='uploadTime'></td>
                <th>审核时间:</th><td id="finishDate"></td>
                </tr>
             </table>
        </div>
    </div>
    

	<div class="easyui-panel" title="房源基础信息" style="width:100%px;">
        <div style="padding:10px 60px 20px 60px">
            <table cellpadding="5">
                <tr>
	                <td>小区：</td>
                    <td id="estateName">&nbsp;</td>
                    <td width="20px">&nbsp;</td>
                    <td>区域板块：</td>
                    <td id="cityTownName">&nbsp;</td>
                    <td width="20px">&nbsp;</td>
                    <td>房源编号：</td>
                    <td id="houseId">&nbsp;</td>
                </tr>
                <tr>
                    <td>栋座：</td>
                    <td id="building">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>房型：</td>
                    <td id="houseType">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>当前审核状态：</td>
                    <td id="presentStatus">&nbsp;</td>
                </tr>
                <tr>
                    <td>室号：</td>
                    <td id="room">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>面积：</td>
                    <td id="spaceArea">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </div>
    </div>
    
    
    
    <div class="easyui-panel" title="租售信息" style="width:100%px;">
        <div style="padding:10px 60px 20px 60px">
            <table cellpadding="5">
                <tr id="sellTRShowId" style="display:none">
                    <td>出售状态：</td>
                    <td id="sellId">&nbsp;</td>
                    <td width="20px">&nbsp;</td>
                    <td>到手价：</td>
                    <td id="sellPrice">&nbsp;</td>
                </tr>
                <tr id="rentTRShowId" style="display:none">
                    <td>出租状态：</td>
                    <td id="rentId">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>租金：</td>
                    <td id="rentPrice">&nbsp;</td>
                </tr>
            </table>
        </div>
    </div>
    
     <div class="easyui-panel" title="配套信息" style="width:100%px;">
        <div style="padding:10px 60px 20px 60px">
            <table cellpadding="5">
                <tr>
                    <td>装修情况：</td>
                    <td width="20px">&nbsp;</td>
                    <td id="decorateTypeId"></td>
                </tr>
                 <tr>
                    <td>基础配套：</td>
                    <td width="20px">&nbsp;</td>
                    <td id="baseSupportId"></td>
                </tr>
                 <tr>
                    <td>高端配套：</td>
                    <td width="20px">&nbsp;</td>
                    <td id="seniorSupportId"></td>
                </tr>
		
            </table>
        </div>
    </div>
    
    <div class="easyui-panel" title="照片" style="width:100%px;">
        <div style="padding:10px 60px 20px 60px" id="imageTableId">
            
        </div>
    </div>
  
  
  <div class="easyui-panel" title="审核任务" style="width:100%px;height:230px; display: none;"  id="checktaskpanel">
         <div style="padding:10px 60px 20px 60px">
             <table cellpadding="5">
             	<thead>审核结果</thead>
                <tr>
                <td><input type="radio" name="status" id='ck1' value="3" onchange="ckChange(3)"/><label for="ck1">审核成功</label></td>
                <td><input type="radio" name="status" id='ck2' value="4" onchange="ckChange(4)"/><label for="ck2" >审核失败</label></td>

                </tr>
                <tr>
                	<td colspan="2" style="width:300px;">
                		<div  style="display: none;">
                		装修情况:<select name="decorateType" id="decorateType">
                			<option value="1" selected="selected">毛坯</option>
                			<option value="3">简装</option>
                			<option value="4">精装</option>
                			<option value="5">豪装</option>
                		</select>
                		总层高:<input class="easyui-numberbox" min='1' max='10000' maxlength="4" type='text' id="layers" name="layers" style="width:100px;"/> <br/>
                		简 述 : <textarea rows="4" cols="40" name="note" id="success-note"></textarea>
                		</div>
                		<div  style="display: none;">失败原因:<textarea rows="4" cols="40" name="note" id="note"></textarea></div>
                	</td>
                </tr>
                <tr>
                	<td><input type="button" onclick="submitCheck();" value="提交"/> </td>
                </tr>
             </table>
        </div>
    </div>
    
    <div class="easyui-panel" title="审核记录" style="width:100%px;height:260px;display: none;" >
         <div style="padding:10px 60px 20px 60px" id="auditId">
            
        </div>
    </div>
     
    
  <!--   <div class="easyui-tabs" style="width:100%px;height:260px">
        <div title="审核记录" style="padding:10px 60px 20px 40px" id="auditId">
            
        </div>
        <div title="房源修改记录" style="padding:10px 60px 20px 40px" id="sourceEdit">
           
        </div>
    </div> -->
    
<script>
function ckChange(t){
	if(t == 3){
		//成功
		$("#layers").parent().show();
		$("#note").parent().hide();
	}else if(t==4){
		//失败
		$("#note").parent().show();
		$("#layers").parent().hide();
	}
}
function submitCheck(){
	var userTaskIdSubmit =  '<c:out value="${id}" />';
	if(userTaskIdSubmit == '') {
		$.messager.alert("温馨提示","初始化失败，请重新加载","warning");
		return false;
	}
	
	var status = $("#checktaskpanel input[name='status']:checked").val();
	if(undefined == status|| status =='' ){
		$.messager.alert("温馨提示","请选择审核结果!","warning");
		return false;
	}
	var layers = $("#layers").val();
	var note = $("#note").val();
	var decorateType = document.getElementById("decorateType").value;
	if(status== 3){
		note = $("#success-note").val();
		//成功
		 if(layers == ''){
			//$.messager.alert("温馨提示","请填写总层高!","warning");
			//return false;
			 layers =0;
		} 
	}else if(status==4){
		//失败
		if(note == ''){
			$.messager.alert("温馨提示","请填写失败原因!","warning");
			return false;
		}
		layers = 0;
	}
	if(note == null){
		note = '';
	}
	//ajax  q请求  status: 结果状态; layers: 总层高 (成功) ; note 失败理由
	$.ajax({ 
		type: "post", 
		url: "${ctx}/lookHouse/userTaskSubmit",
		data: "id=" + userTaskIdSubmit + "&status=" + status + "&layers=" + layers + "&note=" + note+"&decorateType="+decorateType ,
		dataType: "json", 
		success: function (data) {
			if(data == null || data.errorCode == null ) {
				$.messager.alert("温馨提示","提交发生异常!","error");
			}
			if(data.errorCode == 0) {
				$.messager.alert("温馨提示","提交成功","info");
				//window.parent.closeDialog();
			}else if(data.errorCode == 1) {
				$.messager.alert("温馨提示","已经提交过","error");
			}else if(data.errorCode == 2) {
				$.messager.alert("温馨提示","不处在有效审核状态","error");
			}else {
				$.messager.alert("温馨提示",data.message,"error");
			}
		}
	});
}

var shouseId = "";
function initPage (id,houseId) {
	shouseId = houseId;

	$(document).ready(function() {
		$('#w').window('close');
	});
		
	if (houseId == '') {
		$.messager.alert('提示', '初始化页面失败，请联系管理员！');
// 		alert('初始化页面失败，请联系管理员！');
		return;
	}

	
	
	//加载 房源信息  and 租售信息
	loadSourceBaseInfo(houseId);
	
	//加载user task 基础信息
	loadUserTask(id);

	//加载 业主信息
	//loadSourceHostInfo(houseId);
	
	//加载 审核记录
	//loadAuditMessageInfo(houseId);

	//加载 装修情况
	loadHouseSuppoert(id);// task Id
	
	//加载 缩略图 图片
	
	loadHouseImage(id);// task Id

	//加载     房源修改记录记录
// 	loadSourceEditInfo(houseId);
}


//加载 房源信息
function loadSourceBaseInfo (houseId) {
	$.ajax({ 
        type: "post", 
        async:false,
        url: "${ctx}/sourcemanage/getSourceBaseInfo",
        data: "houseId=" + houseId,
        dataType: "json", 
        success: function (data) {
        	setSourceBaseInfo(data);
        	
        	//加载 租售信息
        	setSourceRentAndSellInfo(data);
        } 
	});
}

function setSourceBaseInfo (data) {

	$("#estateName").text(data.estateName);
	$("#cityTownName").text(data.cityName + "-" + data.townName);
	$("#houseId").text(data.houseId);
	$("#building").text(data.building + "号");
	$("#houseType").text(data.bedroomSum + "室" + data.livingRoomSum + "厅" + data.wcSum + "卫");
	setHouseTpye(data.bedroomSum, data.livingRoomSum, data.wcSum);
	$("#spaceArea").text(data.spaceArea + "平米");
	$("#sspaceArea").numberbox('setValue', data.spaceArea);
	$("#room").text(data.room + "室");
	$("#sroom").val(data.room);
	
	if (data.isAuditing) {
		$("#presentStatus").html("<font color=#ff0000>" + data.actionTypeStr + '</font>&nbsp; &nbsp; <!--<a href="#" onclick="view_btn('+ data.houseId +')">查看详情</a>-->');
	} else {
		$("#presentStatus").html('--');
		//设置修改按钮的显示
		$('#updateId').show();
	}
	
}
function view_btn (o) {
// 	alert(o);
}

//初始化修改
function setHouseTpye(f, s, t) {
	
	var count = $("#sbedroomSum option").length;
	for (var i = 0; i < count; i++) {
		if ($("#sbedroomSum").get(0).options[i].text == f) {
			$("#sbedroomSum").get(0).options[i].selected = true;
			break;
		}
	}
	count = $("#slivingRoomSum option").length;
	for (var i = 0; i < count; i++) {
		if ($("#slivingRoomSum").get(0).options[i].text == s) {
			$("#slivingRoomSum").get(0).options[i].selected = true;
			break;
		}
	}
	count = $("#swcSum option").length;
	for (var i = 0; i < count; i++) {
		if ($("#swcSum").get(0).options[i].text == t) {
			$("#swcSum").get(0).options[i].selected = true;
			break;
		}
	}
}

//设置在售
function setSellTableData(o) {
	
	var count = $("#sisSell option").length;
	for (var i = 0; i < count; i++) {
		if ($("#sisSell").get(0).options[i].text == o) {
			$("#sisSell").get(0).options[i].selected = true;
			break;
		}
	}
	
}
//设置在租
function setRentTableData(o) {
	
	var count = $("#sisRent option").length;
	for (var i = 0; i < count; i++) {
		if ($("#sisRent").get(0).options[i].text == o) {
			$("#sisRent").get(0).options[i].selected = true;
			break;
		}
	}
}

//设置租售信息
function setSourceRentAndSellInfo (data) {
	$("#sellTRShowId").hide();
	$("#rentTRShowId").hide();
	
	//在售
	if (data.houseState == '2') {
		
		$("#sellId").text("在售");
		$("#sellPrice").text((data.sellPrice==undefined?"0":data.sellPrice) + "万");
		$("#ssellPrice").numberbox('setValue', data.sellPrice);

		setSellTableData("在售");
		
		$("#sellTRShowId").show();
		
		setRentTableData("不租");
		$("#srentPrice").numberbox('setValue', "0");

		
	//在租
	} else if (data.houseState == '1') {
		$("#rentId").text("在租");
		$("#rentPrice").text((data.rentPrice==undefined?"0":data.rentPrice) + "元");
		$("#srentPrice").numberbox('setValue', data.rentPrice);
		
		setRentTableData("在租");
		
		$("#rentTRShowId").show();
		
		setSellTableData("不售");
		$("#ssellPrice").numberbox('setValue', "0");
		
	//在售在租	
	} else if (data.houseState == '3') {
		$("#sellId").text("在售");
		$("#sellPrice").text((data.sellPrice==undefined?"0":data.sellPrice) + "万");
		$("#ssellPrice").numberbox('setValue', (data.sellPrice==undefined?"0":data.sellPrice));

		setSellTableData("在售");
		
		$("#sellTRShowId").show();
		
		setRentTableData("在租");
		
		$("#rentId").text("在租");
		$("#rentPrice").text((data.rentPrice==undefined?"0":data.rentPrice) + "元");
		$("#srentPrice").numberbox('setValue', (data.rentPrice==undefined?"0":data.rentPrice));
		
		$("#rentTRShowId").show();
		
	//不售不租		
	} else if (data.houseState == '4') {
		$("#sellId").text("不售");
		$("#sellPrice").text("--");
		$("#ssellPrice").numberbox('setValue', "0");

		setSellTableData("不售");
		
		$("#sellTRShowId").show();
		
		setRentTableData("不租");
		
		$("#rentId").text("不租");
		$("#rentPrice").text("--");
		$("#srentPrice").numberbox('setValue', "0");
		
		$("#rentTRShowId").show();
	}
	$("#ssellTRShowId").show();
	$("#srentTRShowId").show();

}

//加载  user 看房 基础信息
function loadUserTask (id) {
	$.ajax({ 
        type: "post", 
        async:false,
        url: "${ctx}/lookHouse/getUserTaskById",
        data: "id=" + id,
        dataType: "json", 
        success: function (data) {
        	setUserTask(data, id);
        }
	});
}

//设置 user 看房 基础信息
function setUserTask(data){
	if(data.taskStatus == 2){
		// 待审核
		$("#checktaskpanel").show();
		
		$("#houseType").text(data.bedroomSum + "室" + data.livingRoomSum + "厅" + data.wcSum + "卫");
	}
	$("#taskId").html(data.id);
	$("#taskUser").html(data.userName);
	$("#tastStatus").html(data.taskStatusStr);
	$("#createTime").html(data.createTimeStr);
	$("#finishDate").html(data.finishDateStr);
	$("#uploadTime").html(data.uploadPhotoTimeStr);
	if(data.taskStatus == 3 || data.taskStatus == 4 ){
		//审核了才有审核记录
		$("#auditId").parent().show();
		setAuditMessageInfo(data);
	}
}

//加载 业主信息
function loadSourceHostInfo (houseId) {
	$.ajax({ 
        type: "post", 
        url: "${ctx}/sourcemanage/getSourceHostInfo",
        data: "houseId=" + houseId,
        dataType: "json", 
        success: function (data) {
        	setSourceHostInfo(data, houseId);
        }
	});
}

//设置业主信息
function setSourceHostInfo (data) {

	//历史
	if (data.houseContactFalse != undefined) {
		var temps = '';
		for (var i = 0; i < data.houseContactFalse.length; i ++) {
			temps += '<tr><td>' + data.houseContactFalse[i] + '</td></tr>';
		}
		$("#houseContactFalse").html(temps);
	}
	//当前
	if (data.houseContactTrue != undefined) {
		var temps = '';
		for (var i = 0; i < data.houseContactTrue.length; i ++) {
			temps += '<tr><td>' + data.houseContactTrue[i] + '</td></tr>';
		}
		$("#houseContactTrue").html(temps);
	}
}

//加载 审核记录
function loadAuditMessageInfo (houseId) {
	$.ajax({ 
        type: "post", 
        url: "${ctx}/sourcemanage/getAuditMessageInfo",
        data: "houseId=" + houseId,
        dataType: "json", 
        success: function (data) {
        	setAuditMessageInfo(data);
        }
	});
}

function setAuditMessageInfo(data) {

	if (data != undefined) {
		var o =data;
		var str = "";
			str += "<p style=\"font-size:14px\">" + o.finishDateStr + "</p>";
			str += "<ul>";
			str += "    <li>审核结果：" + o.taskStatusStr + "</li>";
			str += "    <li>总层高 ：" + o.layers + "</li>";
			str += "    <li>备注：" + (o.note == undefined ? "" : o.note)+ "</li>";
			// 			str += "    <li><a href='#' onclick='lookDetail(" + o.historyId + ")'>查看详情</a></li>";
			str += "</ul>";
			$("#auditId").html(str);

		}
	}

	//查看详情
	function lookDetail(o) {
		// 	alert(o);
	}

	//加载     房源修改记录记录
	function loadSourceEditInfo(houseId) {
		$.ajax({
			type : "post",
			url : "${ctx}/sourcemanage/getSourceEditInfo",
			data : "houseId=" + houseId,
			dataType : "json",
			success : function(data) {
				setSourceEditInfo(data);
			}
		});
	}

	//加载     装修信息
	function loadHouseSuppoert(usertaskid) {
		$.ajax({
			type : "post",
			url : "${ctx}/lookHouse/getHouseSuppoert",
			data : "houseId=" + usertaskid,
			dataType : "json",
			success : function(data) {
				setHouseSuppoert(data);
			}
		});
	}

	function setHouseSuppoert(data) {
		if (data.houseSupporting != undefined) {
			//		装修情况：
			$("#decorateTypeId").html(data.houseSupporting.decorateTypeStr);

			//if (data.houseSupporting.decorateTypeStr != "毛坯") {
				//		基础配套：
				$("#baseSupportId").html(data.houseSupporting.baseSupport);
				//		高端配套：
				$("#seniorSupportId").html(data.houseSupporting.seniorSupport);
			//}

		}
	}

	//加载    图片
	function loadHouseImage(houseId) {
		$.ajax({
			type : "post",
			url : "${ctx}/lookHouse/getHouseImageByUserTaskId",
			data : "id=" + houseId,// 这里的ID是 user task id
			dataType : "json",
			success : function(data) {
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
				str += "	<div style=\"margin-bottom: 5px\">" + o.imgDes+ "</div>";
				str += "	<div>";
				str += " 	<img src=\"" + o.thumbnailUrl + "\" />";
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
	
	
function setSourceEditInfo(data) {
	if (data.houseHisList != undefined) {
		var str = "";
		for (var i = 0; i < data.houseHisList.length; i++) {
			var o = data.houseHisList[i];
			str += "<p style=\"font-size:14px\">" + o.addTime.substring(0, 19) + "</p>";
			str += "<ul>";
			str += "    <li>发布类型：" + o.logText + "</li>";
			str += "    <li>修改人：" + o.userName + "</li>";
			str += "</ul>";
		}
		$("#sourceEdit").html(str);
		
	}
}
function sethouseState(isSell, isRent) {
	//同时显示出租状态、出售状态
	if (document.getElementById('ssellTRShowId').style.display=="" && document.getElementById('srentTRShowId').style.display=="") {
		if (isSell==0 && isRent == 0) {
			return 3;
		} else if (isSell==1 && isRent == 0) {
			return 1;
		} else if (isSell==0 && isRent == 1) {
			return 2;
		} else if (isSell==1 && isRent == 1) {
			return 4;
		}
	} else 	if (document.getElementById('ssellTRShowId').style.display=="") {
		if (isSell==0) {
			return 2;
		} else {
			return 4;
		}
	} else 	if (document.getElementById('srentTRShowId').style.display=="") {
		if (isRent==0) {
			return 1;
		} else {
			return 4;
		}
	}
	
}

function saveMeaage() {
	var bedroomSum = document.getElementById('sbedroomSum').value;
	var livingRoomSum = document.getElementById('slivingRoomSum').value;
	var wcSum = document.getElementById('swcSum').value;
	
//	室号
	var room = $('#sroom').val();
// 	面积：
	var spaceArea = $('#sspaceArea').val();
	
// 	出售状态：
	var isSell = $('#sisSell').val();
	var sellPrice = $('#ssellPrice').val();
	
// 	出租状态：
	var isRent = $('#sisRent').val();
	var rentPrice = $('#srentPrice').val();
	

	if (room == '') {
		return ;
	} else if (spaceArea == '') {
		return ;
	} else if (document.getElementById('ssellTRShowId').style.display=="" && sellPrice == '') {
		return ;
	} else if (document.getElementById('srentTRShowId').style.display=="" && rentPrice == '') {
		return ;
	}
	var houseState = sethouseState(isSell, isRent);

	$.post("${ctx}/sourcemanage/updateSourceManage", {
		houseId:shouseId,
		bedroomSum:bedroomSum,
		livingRoomSum:livingRoomSum,
		wcSum:wcSum,
		room:room,
		spaceArea:spaceArea,
		houseState:houseState,
		sellPrice:sellPrice,
		rentPrice:rentPrice
		}, function(data) {
			if (data.errorCode != 0) {
				$.messager.alert('提示', data.message);
			} else {
				$('#w').window('close');
				$.messager.alert('提示', '修改成功！', 'info', 
					function (){
						//加载 房源信息   and  租售信息
						loadSourceBaseInfo(shouseId);
			
						//加载     房源修改记录记录
			// 			loadSourceEditInfo(shouseId);
					}
				);
			}	
		}, "json");
}
	
// 	删除房源
function deleteSourceInfo() {
	$.post("${ctx}/sourcemanage/deleteSourceInfo", {
			houseId : shouseId
		}, function(data) {
			$.messager.alert('提示', '注销成功！', 'info', 
				function (){
					window.location.href = "${ctx}/house/houseGrid";
				}
			);
		}
	);
}

function confirm2(msg, control) {
	$.messager.confirm("提示", msg, function (r) {
	if (r) {
		$(control).attr("onclick", "");
		deleteSourceInfo();
		control.click();
	}
	});
	return false;
}


//初始化页面
initPage('<c:out value="${id}" />','<c:out value="${houseId}" />');
</script>




</body>

</html>