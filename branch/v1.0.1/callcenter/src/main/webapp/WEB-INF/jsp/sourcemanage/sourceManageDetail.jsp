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
	<div style="text-align: right"><span><a id="updateId" href="#" onclick="$('#w').window('open')" style="display: none">修改</a></span> &nbsp; 
	<c:if test="${sessionScope.login_session.role == 1 ||  sessionScope.login_session.role == 2}">
		<span><a id="deleteHouseId" href="#" onclick="confirmDeleteSourceInfo()">注销房源</a></span>
	</c:if>
	</div>
	<div id="w" class="easyui-window" title="修改信息" data-options="iconCls:'icon-save'" style="width:500px;height:300px;">
		<div class="easyui-panel" title="房源基础信息" style="width:100%px;">
       <div>
            <table cellpadding="5" >
                <tr>
	                <td>房型：</td>
                    <td>
                    	<select id="sbedroomSum" name="sbedroomSum">
                    		<option value="0">0</option>
                    		<option value="1">1</option>
                    		<option value="2">2</option>
                    		<option value="3">3</option>
                    		<option value="4">4</option>
                    		<option value="5">5</option>
                    		<option value="6">6</option>
                    		<option value="7">7</option>
                    		<option value="8">8</option>
                    		<option value="9">9</option>
                    	</select>室
                    	<select id="slivingRoomSum" name="slivingRoomSum">
                    	    <option value="0">0</option>
                    		<option value="1">1</option>
                    		<option value="2">2</option>
                    		<option value="3">3</option>
                    		<option value="4">4</option>
                    		<option value="5">5</option>
                    		<option value="6">6</option>
                    		<option value="7">7</option>
                    		<option value="8">8</option>
                    		<option value="9">9</option>
                    	</select>厅
                    	<select id="swcSum" name="swcSum">
                    	    <option value="0">0</option>
                    		<option value="1">1</option>
                    		<option value="2">2</option>
                    		<option value="3">3</option>
                    		<option value="4">4</option>
                    		<option value="5">5</option>
                    		<option value="6">6</option>
                    		<option value="7">7</option>
                    		<option value="8">8</option>
                    		<option value="9">9</option>
                    	</select>卫
                    	</td>
                    	
                    <td>室号：</td>
                    <td><input id="sroom" name="sroom" value="0" class="easyui-validatebox" missingMessage="必须填写室号" data-options="required:true"/>室</td>
                </tr>
                <tr>
                    <td>面积：</td>
                    <td><input id="sspaceArea" name="sspaceArea" value="0" class="easyui-numberbox" missingMessage="必须填写面积" precision="2" data-options="required:true"/>平米</td>
                    <td colspan="2">&nbsp;</td>
                </tr>
            </table>
        </div>
        </div>
		<div class="easyui-panel" title="租售信息" style="width:100%px;">
        <div>
            <table cellpadding="5">
                 <tr id="ssellTRShowId" style="display: none">
	                <td>出售状态：</td>
                    <td>
                   		<select id="sisSell" name="sisSell">
                    		<option value="0">在售</option>
                    		<option value="1">不售</option>
                    	</select>
                    	
	                <td>到手价：</td>
                    <td><input id="ssellPrice" value="0" class="easyui-numberbox" missingMessage="必须填写价格"  precision="2" data-options="required:true"/>万</td>
                </tr>
               <tr id="srentTRShowId" style="display: none">
	                <td>出租状态：</td>
                    <td>
                    	<select id="sisRent" name="sisRent">
                    		<option value="0">在租</option>
                    		<option value="1">不租</option>
                    	</select>
                    
	                <td>租金：</td>
                    <td><input id="srentPrice" value="0" class="easyui-numberbox" missingMessage="必须填写租金"  precision="2" data-options="required:true"/>元</td>
                </tr>
            </table>
        </div>
        </div>
        <div align="center" style=" margin: 5px">
          <a href="#" onclick="$.messager.alert('提示', '功能未开放！');" class="easyui-linkbutton">保存</a>
          <a href="#" onclick="$('#w').window('close')" class="easyui-linkbutton">取消</a>
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
    
    
     <div class="easyui-panel" title="业主信息" style="width:100%px;">
        <div style="padding:10px 60px 20px 60px">
            <table cellpadding="5">
                <tr>
                    <td>当前有效业主信息</td>
                    <td width="20px">&nbsp;</td>
                    <td>历史所有业主信息</td>
                </tr>
                <tr>
                    <td>
                    	<table id="houseContactTrue">
                    		
                    	</table>
                    </td>
                    <td>&nbsp;</td>
                    <td>
                    	<table id="houseContactFalse">
                    	
                    	</table>
                   		
                    	
                    </td>
                </tr>
            </table>
        </div>
    </div>
    
    <div class="easyui-panel" title="审核记录" style="width:100%px;height:260px">
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
var shouseId = "";
function initPage (houseId) {
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

	//加载 业主信息
	loadSourceHostInfo(houseId);
	
	//加载 审核记录
	loadAuditMessageInfo(houseId);

	//加载     房源修改记录记录
// 	loadSourceEditInfo(houseId);
}


//加载 房源信息
function loadSourceBaseInfo (houseId) {
	$.ajax({ 
        type: "post", 
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

//查看详情
function lookDetail(o) {
// 	alert(o);
}

//加载     房源修改记录记录
function loadSourceEditInfo (houseId) {
	$.ajax({ 
        type: "post", 
        url: "${ctx}/sourcemanage/getSourceEditInfo",
        data: "houseId=" + houseId,
        dataType: "json", 
        success: function (data) {
        	setSourceEditInfo(data);
        }
	});
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
	if (document.getElementById('ssellTRShowId').style.display=="" &&　document.getElementById('srentTRShowId').style.display=="") {
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
	
function confirmDeleteSourceInfo() {
	confirm2("确定注销房源？");
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
initPage('<c:out value="${houseId}" />');
</script>




</body>

</html>