<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.pkit.model.TreeNode"%>
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
			var perData=<%=request.getAttribute("tblPermissiongroup")%>;
		</script>
	</head>
	<body>
		<div class="detail-middle l-form" style="margin-left: 30px;">
			<div class="l-group l-group-hasicon"><img src="../static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span></div>
			<table id="detail" class="detail-tableSkin detail-tableSkinA">
				<tr>
					<th>权限组名称：</th>
					<td class="dname"></td>
				</tr>
				<tr>
					<th>描述：</th>
					<td class="ddesc"></td>
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
					LG.loadDetail(d, {data : perData}, self.callback);
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
							}
							else if(p=="dcreatetime"){
								ele.html(new Date(data[p].time).Format("yyyy-MM-dd hh:mm:ss"));	
							}else if(p=="dupdatetime"){
								ele.html(new Date(data[p].time).Format("yyyy-MM-dd hh:mm:ss"));							
							}
							else{
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
			<div class="l-group l-group-hasicon"><span>操作权限</span></div>
		</div>
		 
		<div class="l-grid2">
			<div class="l-grid-body l-grid-body2 l-scroll" style="height: auto;">
				<div class="l-grid-body-inner" style="width: 759px;">
					<table cellspacing="0" cellpadding="0" class="l-grid-body-table">
						<tbody>							
							<%
							List<TreeNode> list=(List<TreeNode>)request.getAttribute("nodes");
							if(list!=null&&list.size()>0){
								TreeNode node=null;
								List<TreeNode> children=null;
								TreeNode child=null;
								for(int i=0;i<list.size();i++){
									node=list.get(i);
									children=node.getChildren();
									%>
							<tr class="l-grid-row l-selected">
								<td style="width:100px;  " class="l-grid-row-cell ">
									<div style="width:80px;height:28px;min-height:28px; text-align:right;" class="l-grid-row-cell-inner">&nbsp;&nbsp;&nbsp;&nbsp;<%=node.getText()%></div>
								</td>
								<td style="width:400px" class="l-grid-row-cell ">
									<div style="width:380pxpx;height:28px;min-height:28px; text-align:left;" class="l-grid-row-cell-inner">
								<%for(int j=0;j<children.size();j++){
									child=children.get(j);
								%>								
									<input type="checkbox" disabled="disabled" <%if(child.isIschecked()){%>checked="checked" <%}%>/><%=child.getText()%>&nbsp;&nbsp;
								
							<%}%>
							</div></td></tr>
							<%}}%>							
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