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
		
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
<table border="1" cellpadding="2" cellspacing="2" align="center">
	<tr>
		<td height="30" colspan="2"><tiles:insertAttribute name="header" />
		</td>
	</tr>
	<tr>
		<td height="250"><tiles:insertAttribute name="menu" /></td>
		<td width="350"><tiles:insertAttribute name="body" /></td>
	</tr>
	<tr>
		<td height="30" colspan="2"><tiles:insertAttribute name="footer" />
		</td>
	</tr>
</table>
</body>
</html>
</jsp:root>
