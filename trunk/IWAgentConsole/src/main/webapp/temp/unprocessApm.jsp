<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="script/boot.js"></script>
</head>

<body path="<%=path%>">
	<div class="box">
		<div class="hint">
			<span> <img src="pics/icon/warning_16x16.png" class="icon" />
				<span class="content">一旦变更为以下两种状态，系统将自动取消该用户所有进行中的约看活动。</span>
			</span>
		</div>
		<div>
			<form action="" id=""
				style="padding-left: 10px; margin: 0px; margin-top: 10px;">
				<div class="mini-radiobuttonlist" repeatItems="2"
					repeatLayout="table" repeatDirection="vertical" value="1"
					textField="text" valueField="id" url="<%=path%>/subs/editUserStatus.txt"></div>
				<span style="color: red;">*</span> 备注<br> <textarea
						class="mini-textarea" emptyText="请输入备注" width="100%" height="150"></textarea>
					<br><br><br>
								<div style="text-align: center; padding: 10px;">
									<a class="mini-button" onclick="onClick">提 交</a> <span
										style="margin-right: 30px;">&nbsp;</span> <a
										class="mini-button" onclick="onClick"
										style="border: 1px #FFCC33 solid; background: url(pics/cancle_bg.png) repeat-x;">取
										消</a>

								</div>
			</form>
		</div>
	</div>
</body>
</html>