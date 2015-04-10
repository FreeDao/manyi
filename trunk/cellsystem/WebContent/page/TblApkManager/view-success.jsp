<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id=request.getAttribute("id")+"";
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="../static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<script src="../static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/common.js" type="text/javascript"></script>
		<script type="text/javascript">
			var id=<%=request.getAttribute("id")%>;
		</script>
	</head>
	<body>
		<div class="detail-middle l-form" style="margin-left: 30px;">
			<div class="l-group l-group-hasicon"><img src="../static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span></div>
			<table id="detail" class="detail-tableSkin detail-tableSkinA">
				<tr>
					<th>应用版本名称：</th>
					<td class="dversionno"></td>
				</tr>
				<tr>
					<th>应用版本号：</th>
					<td class="dversioncode"></td>
				</tr>
				<tr>
					<th>应用下载地址：</th>
					<td class="ddownloadurl"></td>
				</tr>
				<tr>
					<th>应用描述：</th>
					<td class="ddescription"></td>
				</tr>
				<tr>
					<th>二维码地址：</th>
					<td class="dqrcodeimageurl"></td>
				</tr>
				<tr>
					<th>发布时间：</th>
					<td class="duploadtime"></td>
				</tr>
				<tr>
					<th>状态：</th>
					<td class="dstatus"></td>
				</tr>
			</table>
		</div>
		
		
		<script type="text/javascript">
		var crForm = {
				main : "#detail",
				init : function() {
					var detail = $(this.main);
					this.loadDetail(detail);
					 LG.setFormDefaultBtn(this.cancel,null);
				},
				loadDetail : function(d) {
					var self = this;
					LG.loadDetail(d, {url : 'loadView.do?id='+id}, self.callback);
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				callback : function(data) {
					data = data|| {};
					for ( var p in data) {
						var ele = $("[class=" + p + "]", detail);
						if (ele.length > 0) {
							if(p=="dstatus"){
								if(data[p]==0){
									ele.html("停用");
								}else{
									ele.html("启用");
								}								
							}else{
								ele.html(data[p]);
							}
						}
					}
				}
			}
			crForm.init();		
		</script>
	</body>
</html>