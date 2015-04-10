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

<h2>City</h2>



<c:forEach items="${tblemployees}" var="tbl">
${tbl.empNo } / ${tbl.empName } / ${tbl.password} /${tbl.tel }<br/>
</c:forEach>
</jsp:root>