<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:fun="http://java.sun.com/jsp/jstl/functions"
	xmlns:x="http://java.sun.com/jsp/jstl/xml"
	xmlns:sql="http://java.sun.com/jsp/jstl/sql"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:tilesextras="http://tiles.apache.org/tags-tiles-extras"
	xmlns:sc="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">

	<jsp:directive.page contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8" session="true" />

	<jsp:text>
		<![CDATA[ <!doctype html> ]]>
	</jsp:text>

	<html>
<head>
<meta charset="UTF-8" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/themes/icon.css" />
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/scripts/jquery.min.js">
	//content
</script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/scripts/jquery.easyui.min.js">
	//content
</script>

<STYLE type="text/css">
iframe {
	width: 100%;
	height: 98%;
	border: none;
	border-image-source: initial;
	border-image-slice: initial;
	border-image-width: initial;
	border-image-outset: initial;
	border-image-repeat: initial;
}
</STYLE>
<script type="text/javascript">
	function setHeight() {
		var bodyHeight = $(document).innerHeight();
		$('.easyui-layout').height(bodyHeight - 50);
	}

	$(function() {
		setHeight();
		$('.easyui-layout').layout();
	});

	$(window).resize(function() {
		setHeight();
		$('.easyui-layout').layout();
	});
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 100%;">
		<div data-options="region:'north',split:true" style="height: 97%;">
			<tiles:insertAttribute name="header" />
		</div>
		<div data-options="region:'west',split:true" title="系统菜单"
			style="width: 180px;">
			<tiles:insertAttribute name="menu" />
		</div>
		<div
			data-options="region:'center',title:'Happly every Day! ',iconCls:'icon-ok'">
			<tiles:insertAttribute name="body" />
		</div>
		<div data-options="region:'south'">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
	</html>
</jsp:root>
