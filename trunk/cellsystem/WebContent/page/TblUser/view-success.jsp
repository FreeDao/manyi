<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.pkit.util.SafeUtils"%>
<%@page import="com.pkit.util.JSONUtil"%>
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
				<th>用户DID：</th>
					<td class="did"></td>
				</tr>
				<tr>
					<th>用户账号：</th>
					<td class="dusername"></td>
					<th width="100px"></th>
					<td></td>
					<th>密码：</th>
					<td class="dpassword"></td>
				</tr>
				<tr>
					<th>终端序列号：</th>
					<td class="dterminalno"></td>
					<th width="20px"></th>
					<td></td>
					<th>计费账号：</th>
					<td class="daccount"></td>
				</tr>
				<tr>
					<th>支付方式：</th>
					<td class="dpaytype"></td>
					<th width="20px"></th>
					<td></td>
					<th>用户地址：</th>
					<td class="daddress"></td>
				</tr>
				<tr>
					<th>联系方式：</th>
					<td class="dtel"></td>
					<th width="20px"></th>
					<td></td>
					<th>用户IP：</th>
					<td class="duserip"></td>
					<th width="20px"></th>
					<td></td>
				</tr>
				<tr>
					<th>状态：</th>
					<td class="dstatus"></td>
					<th width="20px"></th>
					<td></td>
					
					<th>绑定手机：</th>
					<td class="dhasbind"></td>
					<th width="20px"></th>
					<td></td>
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
					LG.loadDetail(d, {url : "loadView.do?id="+id}, self.callback);
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				callback : function(data) {
					data = data|| {};
					for ( var p in data) {
						var ele = $("[class=" + p + "]", detail);
						if(p=="dstatus"){
							if(data[p]==1){
								ele.html("启用");
							}else{
								ele.html("停用");
							}
						}else if(p=="dpaytype"){
							if(data[p]==0){
								ele.html("年结");
							}else{
								ele.html("月结");
							}
						}else if(p == 'dhasbind'){
							if(data[p]==0){
								ele.html("未绑定");
							}else{
								ele.html("已绑定");
							}
						}else{
							ele.html(data[p]);
						}
					}
				}
			}
			crForm.init();
		</script>
		
		<div class="l-clear"></div>
		<div class="l-form" style="margin: 0 20px!important">
			<div class="l-group l-group-hasicon"><span>员工信息</span></div>
		</div>
		 
		<div class="l-grid2">
			<div class="l-grid-body l-grid-body2 l-scroll" style="height: auto;">
				<div class="l-grid-body-inner" style="width: 759px;">
					<table cellspacing="0" cellpadding="0" class="l-grid-body-table">
						<tbody>
							<%
							String parentidType= request.getAttribute("parentidType")+"";
							
							List list=(List)request.getAttribute("employeeList");
							if(list!=null&&list.size()>0){
								Map map=null;							
								for(int i=0;i<list.size();i++){
									map=(Map)list.get(i);
							%>
								<tr class="l-grid-row l-selected">							
								<td style="width:60px;  " class="l-grid-row-cell ">
									<div style="width:52px;height:28px;min-height:28px; text-align:right;" class="l-grid-row-cell-inner">姓名：</div>
								</td>
								<td style="width:164px;  " class="l-grid-row-cell ">
									<div style="width:156px;height:28px;min-height:28px; text-align:left;" class="l-grid-row-cell-inner"><%=map.get("name") %></div>
								</td>
								<td style="width:60px;  " class="l-grid-row-cell ">
									<div style="width:52px;height:28px;min-height:28px; text-align:right;" class="l-grid-row-cell-inner">工号：</div>
								</td>
								<td style="width:164px;  " class="l-grid-row-cell ">
									<div style="width:156px;height:28px;min-height:28px; text-align:left;" class="l-grid-row-cell-inner"><%=map.get("employeeno") %></div>
								</td>
								<td style="width:60px;  " class="l-grid-row-cell ">
									<div style="width:52px;height:28px;min-height:28px; text-align:right;" class="l-grid-row-cell-inner">部门：</div>
								</td>
								<td style="width:164px;  " class="l-grid-row-cell l-grid-row-cell-last">
									<div style="width:156px;height:28px;min-height:28px; text-align:left;" class="l-grid-row-cell-inner">
									<%
									
									Long key = SafeUtils.getLong( map.get("departmentno"));
									%>
									<script type='text/javascript'>
										var parentidType = <%=parentidType%>;
										var key ='<%=key%>';
										for(var i=0 ; i<parentidType.length; i++){
											if(key == parentidType[i].id){
												var tmp =parentidType[i].text;
												document.writeln(tmp);
												break;
											}
										}
									</script>
									</div>
								</td>
							</tr>
							<%}}%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>