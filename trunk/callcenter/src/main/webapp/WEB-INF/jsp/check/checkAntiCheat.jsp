<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div><span style="color:red">该房源疑似作弊的房子如下</span></div>
<table >
		<tr>
		<td>小区</td><td>楼栋</td><td>室号</td><td>租售状态</td>
		</tr>
		<c:forEach items="${anti.list }" var="list" varStatus="status">
		<tr>
		<td>${list.estateName }</td><td><c:if test="${list.building == '0'}">独栋 </c:if><c:if test="${list.building != '0'}">${list.building } </c:if></td><td>${list.room }室</td><td>${list.houseStateStr }</td>
		</tr>
		</c:forEach>
</table>

	
