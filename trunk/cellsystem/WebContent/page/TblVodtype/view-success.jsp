<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		<script type="text/javascript">
			var id=<%=request.getAttribute("id")%>;
		</script>
	</head>
	<body>
		<div class="detail-middle l-form" style="margin-left: 30px;">
			<div class="l-group l-group-hasicon"><img src="<%=path%>/static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span></div>
			<table id="detail" class="detail-tableSkin detail-tableSkinA">
				<tr>
					<th>名称：</th>
					<td class="dname" width="200"></td>
				</tr>
				<!-- 
				<tr>
					<th>排序：</th>
					<td class="durl" colspan="3"></td>
				</tr>
				 -->
				<tr>
					<th>状态：</th>
					<td class="dstatus" colspan="3" type='select'></td>
				</tr>
				<tr>
					<th>创建时间：</th>
					<td class="dcreatetime"></td>
				</tr>
				<tr>
					<th>更新时间：</th>
					<td class="dupdatetime"></td>
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
					LG.loadDetail(d, {url : "<%=path%>/TblVodtype/loadView.do?id="+id}, self.callback);
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				callback : function(data) {
				selectdata=[
									 {data:[{text:'启用',id:1},{text:'禁用',id:0}],id:'dstatus'}];
									 
					data = data|| {};
					for ( var p in data) {
						var ele = $("[class=" + p + "]", detail);
						if (ele.length > 0) {
							if(ele.attr('type')){
								for(var i = 0 ; i<selectdata.length ; i++){
									if(selectdata[i].id == p){
										LG.viewSelect(selectdata[i],ele,data,p);
										break;
									}
								}
							}else{
								LG.viewSelect(null,ele,data,p);
							}
						}
					}
				}
			}
			crForm.init();
			
		</script>
	</body>
</html>