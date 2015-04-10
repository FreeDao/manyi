<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>link</title>
<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
</head>
<body>
<iframe name="main" frameBorder=0  scrolling=no  width="100%"   height="900" src="${link }"></iframe>
<input id="link_type" type="hidden"  name="type" value="${linkRequest.type}">
<input id="link_cityName" type="hidden" name="type" value="${linkRequest.cityName}">
<input id="link_name" type="hidden"  name="type" value="${linkRequest.name}">
<script>
$(function (){
	//sleep(2000);
	setTimeout("clickBtn()",2000);
	$('iframe').attr("height" ,  document.documentElement.clientHeight-20);
	//console.debug(document.documentElement.clientHeight);
});

function clickBtn() {
	//alert("1");
	var type = $('#link_type').val();
	var cityName = $("#link_cityName").val();
	var name = $("#link_name").val();
	var keywords = '';
	if(cityName == '') {
		keywords = name;
	} else {
		keywords = cityName + " " + name;
	}
	if(type == 1) {
		window.frames[0].document.getElementById("PoiSearch").value = keywords;
		window.frames[0].document.getElementById("poiSearchBtn").click();
	}else if(type == 2) {
		//window.frames[0].document.getElementById("PoiSearch").value=$("#link_cityName").val();
		//window.frames[0].document.getElementById("poiSearchBtn").click();
		window.frames[0].document.getElementById("PoiSearch").value=keywords;
		window.frames[0].document.getElementById("POISearchButton").click();
	}else if(type == 3) {
		
		//window.frames[0].document.getElementById("keywordTxt").value=$("#link_cityName").val();
		//window.frames[0].document.getElementById("keywordTxtForm").click();
	}else if(type == 4) {
		window.frames[0].document.getElementById("input_keyw0").value=name;
		window.frames[0].doSearchSubmitBJ();
	}else if(type == 5) {
		window.frames[0].document.getElementById("localvalue").value=keywords;
		window.frames[0].document.getElementById("localsearch").click();
		
	}
	
	
	
	
}
</script>
</body>
</html>