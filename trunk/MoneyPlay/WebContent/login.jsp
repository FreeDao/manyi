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

<h2>User login Demo</h2>

<form:form method="post" action="./loginSubmit">
	<h4 style="color: red"><form:errors /></h4>
	
	<table>
	<tr>
		<td><form:label path="loginName">Login Name：</form:label></td>
		<td><form:input path="loginName" /></td> 
	</tr>
	<tr>
		<td><form:label path="password">Password：</form:label></td>
		<td><form:input path="password" /></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="Enter"/>
		</td>
	</tr>
</table>	
	
</form:form>


</jsp:root>