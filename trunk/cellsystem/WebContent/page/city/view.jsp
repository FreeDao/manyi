<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<link href="../../../css/ligerui-common.css" rel="stylesheet" type="text/css" />
		<link href="../../../css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<link href="../../../css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="../../../css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
		<script src="../../../scripts/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../../../scripts/ligerui.min.js" type="text/javascript"></script>
		<script src="../../../scripts/common.js" type="text/javascript"></script>
		<script type="text/javascript">
			var id=<%=request.getAttribute("id")%>;
		</script>
	</head>
	<body>
		<div class="detail-middle l-form" style="margin-left: 30px;">
			<div class="l-group l-group-hasicon"><img src="<%=path%>/css/img/icon/communication.gif" />
			<span>基本信息</span></div>
			<table id="detail" class="detail-tableSkin detail-tableSkinA">
				<tr>
					<th>名称：</th>
					<td class="cityName" width="200"></td>
				</tr>
				<tr>
					<th>编号：</th>
					<td class="cityNo" colspan="3"></td>
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
					LG.loadDetail(d, {url : "/city/view.rest?id="+id }, self.callback);
				},
				cancel : function(item) {
					window.location.href='<%=path%>/city/list.do';
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