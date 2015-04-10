<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div>
		<div id="uploadBlock">
		
			<div id="change_1" class="change-demo" args="1" idNum="1111">
				<div>id:123122</div>
				<div><img alt="" src="" style="height:160px;width:160px"></img></div>
				<input type="file" name="upload"    onchange="uploadCommon(this)"  />
				<input type="hidden"  name="imgKey"  value="${imgKey}"    />
				<div><span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png" style="height:30px;width:30px" /></span><span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span><span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png" style="height:30px;width:30px" /></span></div>
			</div>
			<div id="change_2" class="change-demo"   args="2" idNum="1112">
				<div >id:123123</div>
				<div><img alt="" src="" style="height:160px;width:160px"></img></div>
				<input type="file" name="upload"  onchange="uploadCommon(this)"/>
				<div><span onclick="swapUp(this)"><img alt="1" src="${ctx}/imgs/up.png" style="height:30px;width:30px" /></span><span onclick="removeCurrUpload(this)"><a href="javascript:void(0);" >删除</a></span><span onclick="swapDown(this)"><img alt="2" src="${ctx}/imgs/down.png" style="height:30px;width:30px" /></span></div>
			</div>
			
		</div>
		<input type="button" name="appendUploadDiv" value="添加" onclick="addCurrUpload(this)" />
		<iframe id="pic_hidden_frame" name="pic_hidden_frame" style="display:none"></iframe>
	</div>
</body>
<script >
function uploadCommon(currDom) {
	var parentDom = $(currDom).parent();
	console.debug($(currDom).val());
	var uploadPath = $(currDom).val();
	if(uploadPath == null || uploadPath == '') {
		alert("请选择图片");
		return ;
	} 
	var form = getForm();
	form.attr('action','${ctx}/common/uploadCommon');
	form.attr("enctype","multipart/form-data");
	form.attr("target","pic_hidden_frame");
	form.attr("method","POST");
	
	var returnFunc = document.createElement("input");
	returnFunc.setAttribute("name","returnParentFunction");
	returnFunc.setAttribute("type","hidden");
	returnFunc.setAttribute("value","callbackFunction");
	var uploadType = document.createElement("input");
	uploadType.setAttribute("name","uploadType");
	uploadType.setAttribute("type","hidden");
	uploadType.setAttribute("value","0");
	var idNum = document.createElement("input");
	idNum.setAttribute("name","id");
	idNum.setAttribute("type","hidden");
	idNum.setAttribute("value",$(parentDom).attr('id'));
	
	form.append(returnFunc);
	form.append(uploadType);
	form.append(idNum);
	var currCopy = $(currDom).clone(true);
	currCopy.insertBefore($(currDom));
	console.debug($(currDom));
	form.append($(currDom));
	$('body').append(form);
	form.submit();
	form.remove();
}
$(function (){
	// swap(1);
});
function callbackFunction(data) {
	if(data != null) {
		if(data.returnStatus == "error") {
			alert(data.returnDescription);
		}else if(data.returnStatus == "success") {
			//alert(data.returnLongPath)
			$($("#"+data.id +" img").get(0)).attr("src",data.returnLongPath);
			$($("#"+data.id +" input[name='imgKey']")).val(data.returnKey);
		}
	}else {
		alert(data);
	}
}
function swap(id_1,id_2){
	if(id_1 == null || id_2 == null){
		alert("不可移动");
		return; 
	}
	var $div1 = $("#"+id_1);
    var $div3 = $("#"+id_2);
    var $temobj1 = $("<div></div>");
    var $temobj2 = $("<div></div>");
    $temobj1.insertBefore($div1);
    $temobj2.insertBefore($div3);
    $div1.insertAfter($temobj2);
    $div3.insertAfter($temobj1);
    $temobj1.remove();
    $temobj2.remove();

}
function swapUp(currDom){
	var parentDom = $(currDom).parent().parent();
	var up = parentDom.prev();
	//console.info(parentDom.attr('id'));
	//console.info(up.attr("id"));
	swap(parentDom.attr('id'),up.attr('id'));
}
function swapDown(currDom){
	var parentDom = $(currDom).parent().parent();
	var down = parentDom.next();
	//console.info(parentDom.attr('id'));
	//console.info(down.attr("id"));
	swap(parentDom.attr('id'),down.attr('id'));
}
function removeCurrUpload(currDom) {
	var parentDom = $(currDom).parent().parent();
	parentDom.remove();
}

function addCurrUpload(currDom){
	var sum = $("#uploadBlock .change-demo" ).size();
	for (var i = 0; i < sum; i++) {
		console.debug($("#uploadBlock .change-demo" ));
		var num= $($("#uploadBlock .change-demo" ).get(i)) .attr('args');
		if(num > sum) sum =num;
		
	}
	//alert(sum);
	sum++;
	var html = "<div id='change_" + sum+ "' class='change-demo'   args='" + sum+ "'><div>" + sum+ "<img alt='' src='' style='height:160px;width:160px'></img></div>"
		+"<input type='file' name='upload'  onchange='uploadCommon(this)'  />"
		+ "<input type='hidden'  name='imgKey'  value='0'    />"
		+"<div><span onclick='swapUp(this)'><img alt='1' src='${ctx}/imgs/up.png' /></span><span onclick='removeCurrUpload(this)'><a href='javascript:void(0);' >删除</a></span><span onclick='swapDown(this)'><img alt='2' src='${ctx}/imgs/down.png' /></span></div></div>";
	var parentDom = $("#uploadBlock").append(html);
}


function getForm(){
	var form = $("<form>");
	form.attr('style','display:none');  
	form.attr('target','');  
	form.attr('method','post');
	return form;
}


</script>
</html>