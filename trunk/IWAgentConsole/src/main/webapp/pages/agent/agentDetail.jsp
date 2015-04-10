<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
</head>
<body style="height:100%;" path="<%=basePath%>">
    <div>
      <div><img width="100" height="100" src="${agent.photoUrl}"/><span>姓名:</span><span>${agent.name}</span></div>  
      <div style="margin-left:100px;">
      	<div><span>id：</span><span>${agent.id}</span></div>
      	<div><span>工号：</span><span>${agent.serialNumber}</span></div>
      	<div><span>手机号：</span><span>${agent.mobile}</span></div>
      	<div><span>注册时间：</span><span><fmt:formatDate value="${agent.createTime}" pattern="yyyy年MM月dd日 HH:mm" /></span></div>
      	<div><span>最后修改时间：</span><span><fmt:formatDate value="${agent.updateTime}" pattern="yyyy年MM月dd日 HH:mm" /></span></div>
      </div>  
    </div>
</body>
</html>
