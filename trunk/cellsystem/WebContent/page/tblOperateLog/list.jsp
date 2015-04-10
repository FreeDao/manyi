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
		<link href="../../../css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="../../../css/common.css" rel="stylesheet" type="text/css" />
		<script src="../../../scripts/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../../../scripts/ligerui.min.js" type="text/javascript"></script>
		<script src="../../../scripts/json2.js" type="text/javascript"></script>
		<script src="../../../scripts/common.js" type="text/javascript"></script>
		<script type="text/javascript">
			var menuno=<%=request.getAttribute("menuno")%>+"";
			var permissionValue=<%=request.getAttribute("permissionValue")%>;
		</script>
	</head>
	<body>
		<div id="mainsearch">
			<div class="searchbox onlySearchBox">
				<form id="formsearch" class="l-form"></form>
			</div>
		</div>
		
		<div id="maingrid"></div>
		<script type="text/javascript">
			var crGrid = {
				main : "#maingrid",
				formsearch : "#formsearch",
				init : function() {
				  	var self = this;
					this.grid = jQuery(self.main).ligerGrid({ 
			         	columns: [
							{display:"操作者",width:"20%",name:"doperatorname",align:'left'},
							{display:"操作对象",width:"20%",name:"doperateobject"},
							{display:"操作值",width:"20%",name:"doperatevalue"},
							{display:"操作类型",width:"10%",name:"doperatetype",render:LG.operate},
							{display:"操作结果",width:"10%",name:"doperateresult",render:LG.result},
							{display:"操作时间",width:"20%",name:"doperatetime"}
					   ],
			           dataAction: 'server', 
			           pageSize: 15, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           url:  '/log_rest/loginLogOperate.rest', 
			           //sortName: 'd_id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: true,
			           onDblClickRow:function(data, rowindex, rowobj){
							top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view','查看操作信息', '/log_rest/loginLogOperate.rest?id='+data.did);
						}
			      });
			      self.search();
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
				},
				toolbar : function(item) {
					this[item.id]();
				},
				search : function() {
					jQuery(this.formsearch).ligerForm({
						labelWidth:LG.labelWidthB,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthA,
						labelAlign: "right", 
						fields:[
							{display:"操作者",name:"query.doperatorname",newline:false},
							{display:"操作对象",name:"query.doperateobject",newline:false}]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view','查看操作信息', '/log_rest/loginLogOperate.rest?id=' + selecteds[0].did);
				},
				modify : function() {
				},
				del : function() {
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crGrid.init();
		</script>
	</body>
</html>