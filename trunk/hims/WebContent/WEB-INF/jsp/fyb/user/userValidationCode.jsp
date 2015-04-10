<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${ctx}/userValidation/userValidationCode" method="post">
手机号:<input name="mobile" value=""/>
<input name="提交" type="submit">
</form>
<div>
<c:forEach items="${list }" var="list" varStatus="status">
<ul><li>${list.mobile }   ${list.verifyCode }  ${list.statusStr}</li></ul>
</c:forEach>
</div>
</body>
</html>