<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Insert title here</title>
		
		<link href="static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="static/xcloud/css/welcome.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="welcome-tip" style="display: none">
			提示：请使用1280*720以上的分辨率的显示器，操作体验更加友好。
			<span class="welcome-close"></span>
		</div>
		<div class="welcome-box">
			<div class="welcome-title">
				您好！欢迎使用企业云平台管理系统。
			</div>
			<div class="br"></div>
			<dl class="welcome-lsit">
				<dt>待办事项</dt>
				<dd><a href="#">1. EPG管理</a></dd>
				<dd><a href="#">2. 系统角色新增设置</a></dd>
				<dd><a href="#">3. 日志管理备份</a></dd>
				<dd><a href="#">4. 计费价格调整</a></dd>
				<dd><a href="#">5. 权限组成员设置</a></dd>
			</dl>
			<div class="br"></div>
			<dl class="welcome-lsit">
				<dt>新手帮助</dt>
				<dd><a href="#">1. 如何进行模板审核操作？</a></dd>      
				<dd><a href="#">2. 如何进行搜索？ </a></dd>                     
				<dd><a href="#">3. 如何进行添加操作？   </a></dd>          
				<dd><a href="#">4. 如何进行统一内容发布操作？</a></dd>    
				<dd><a href="#">5. 如何新增内容？</a></dd>    
			</dl>
		</div>
		<script src="static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		if(screen.width<1280||screen.height<720){
			jQuery(".welcome-tip").css("display","block");
			setTimeout(function() {
				jQuery(".welcome-tip").animate({marginTop:"-34px"}, 1000);
			}, 5000);
			
			jQuery(".welcome-close").click(function(){
				jQuery(".welcome-tip").animate({marginTop:"-34px"}, 500);
			})
		}
	</script>
	</body>
</html>