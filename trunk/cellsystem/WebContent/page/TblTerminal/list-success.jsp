<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="../static/xcloud/css/ligerui-common.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<script src="../static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/common.js" type="text/javascript"></script>
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
							{display:"名称",name:"dname",width:"20%",align:'left'},
							{display:"版本名称",name:"dversionname",width:"20%"},
							{display:"大小",name:"dsize",width:"10%"},
							{display:"开发方",name:"dcreate",width:"15%"},
							{display:"应用提供商",name:"dsupplier",width:"15%"},
							{display:"状态",name:"dstatus",render: LG.status,width:"20%"}
					   ],
			           dataAction: 'server', 
			           pageSize: 15, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           url:  'getData.do', 
			           //sortName: 'd_id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: true,
			           onDblClickRow:function(data, rowindex, rowobj){
							top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view', '终端管理详细', 'TblTerminal/view.do?id='+data.did);
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
						labelWidth:LG.labelWidthA,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthA,
						labelAlign: "right", 
						fields:[
								{display:"名称",name:"query.dname",newline:true},
								{display:"版本名称",name:"query.dversionname",newline:false,labelWidth:LG.labelWidthB},
								{display:"状态",name:"query.dstatus",newline:false,type:"select",options:{valueFieldID:"query.dstatus",selectBoxHeight:90,data:LG.statusTypeDataL}}
							]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
					top.framework.addTab(LG.getChildMenuNo(), '新增终端信息', 'TblTerminal/add.do?MenuNo='+LG.getPageMenuNo());
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view', '终端管理详细', 'TblTerminal/view.do?IsView=1&MenuNo='+LG.getPageMenuNo() + '&id=' + selecteds[0].did);
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_modify','修改终端信息', 'TblTerminal/modify.do?IsView=1&MenuNo='+LG.getPageMenuNo() + '&id=' + selecteds[0].did);
				},
				off:function(){
					LG.changeStatus(this,"off.do");
				},
				on:function(){
					LG.changeStatus(this,"on.do");
				},
				del : function() {
					LG.deleteData(this);
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crGrid.init();
		</script>
	</body>
</html>