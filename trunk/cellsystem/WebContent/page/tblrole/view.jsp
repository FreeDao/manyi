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
					<th>角色名称：</th>
					<td class="dname"></td>
				</tr>
				<tr>
					<th>描述：</th>
					<td class="ddesc"></td>
				</tr>
				<tr>
					<th>类型：</th>
					<td class="dtype"></td>
				</tr>
				<tr>
					<th>创建时间：</th>
					<td class="dcreatetime"></td>
				</tr>
				<tr>
					<th>更新时间：</th>
					<td class="dupdatetime"></td>
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
							}else if(p=="dtype"){
								if(data[p]==1){
									ele.html("云平台角色");
								}else{
									ele.html("企业角色");
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
		
		<div class="l-clear"></div>
		<div class="l-form" style="margin: 0 20px!important">
			<div class="l-group l-group-hasicon"><span>拥有权限组</span></div>
		</div>
		 
		<div class="l-grid2">
			<div class="l-grid-body l-grid-body2 l-scroll" style="height: auto;">
				<div class="l-grid-body-inner" style="width: 759px;">
					<table cellspacing="0" cellpadding="0" class="l-grid-body-table">
						<tbody>
							<tr class="l-grid-row l-selected">
							<%
							List list=(List)request.getAttribute("list");
							if(list!=null&&list.size()>0){
								Map map=null;							
								for(int i=0;i<list.size();i++){
									map=(Map)list.get(i);
									if((i+1)%4==0){%>
									</tr><tr class="l-grid-row l-selected">
									<%}%>						
								<td style="width:60px;  " class="l-grid-row-cell ">
									<div style="width:52px;height:28px;min-height:28px; text-align:right;" class="l-grid-row-cell-inner"><input type="checkbox" disabled="disabled" checked="checked"/></div>
								</td>
								<td style="width:164px;  " class="l-grid-row-cell ">
									<div style="width:156px;height:28px;min-height:28px; text-align:left;" class="l-grid-row-cell-inner"><%=map.get("D_NAME")%></div>
								</td>
							<%}}%>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<br/>
		<br/>
		<br/>
		<br/>
	</body>
</html>