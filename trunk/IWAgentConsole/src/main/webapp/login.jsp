<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<base href="<%=basePath%>"/>
  	<script type="text/javascript">	
    	if(window!=window.top){
			window.parent.location.href="<%=basePath%>login.jsp";
		}
    </script>
    <title>爱屋吉屋经纪人后台登录</title>
    <link href="styles/login.css" rel="stylesheet"  type="text/css" />
	<script src="script/boot.js" type="text/javascript"></script>
	<script src="script/login.js" type="text/javascript"></script>
  </head>
  <body path="<%=basePath%>">
  	<!-- 勿删 -->
  	<input type='hidden' value='__login__'/>
	<img class="imgbg"  src="pics/login/bg.jpg"/>
	<div id="loginbox">
		<div id="content" >
           <form id="form" method="post">
	           <table >
	               <tr>
	                   <th style="color:#818181">用户名：</th>
	                   <td colspan="2"><input id="mobile" name="mobile" type="text"  class="textfield"/></td>
	               </tr>
	               <tr>
	                   <th style="color:#818181">密<span style="padding-left:14px">码</span>：</th>
	                   <td colspan="2"><input  id="password" name="password"  type="password" class="textfield"  /></td>
	               </tr>
	               
	               <tr>
	                   <td colspan="2">
	                       <input id="loginButton"  value="" onfocus="this.blur();" type="button"  />
	                   </td>
	               </tr>
	           </table>
           </form>
       </div>
	</div>
</body>
</html>
