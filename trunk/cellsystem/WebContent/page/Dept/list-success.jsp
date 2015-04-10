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
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			var menuno=<%=request.getAttribute("menuno")%>+"";
			var permissionValue=<%=request.getAttribute("permissionValue")%>;
		</script>
		<style type="text/css">
			body,html{
				overflow: hidden;
			}
		</style>
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
							{display:"部门名称",name:"name",align: 'left',width:"40%"},
							{display:"部门编号",name:"code",width : '30%'},
							{display:"状态",name:"status",width:"30%",render:function(data){
								//渲染menu数据.取出data中status字段值,得到statusTypeData中的对应的值
								return self.status(data,"status",LG.statusTypeData);
							}}
					   ],
					   root: "rows",
			           dataAction: 'server',
			           pageSize: 300, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
					   autoCheckChildren:false, 
			           rowHeight : 28,
			           lGridTreeNoChild : true,
			           usePager: false,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[20,50,100],
			           onRowDragDrop:self.onRowDragDrop,
			           url:  'getData.do', sortName: 'id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: true,
			           tree: { columnName: 'name'},
			           rowDraggable:false,
			            onDblClickRow:function(data, rowindex, rowobj){
							top.framework.addTab(menuno+'_'+data.did+'_view','查看部门信息', 'Dept/view.do?id='+data.id);
						}
			      });
			      //self.search();
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
				},
				status:function(data,key,s){
					if(data.status == 0){
						return '停用';
					}else{
						return '启用';
					}
				},
				onRowDragDrop : function(item,x) {
					 var x = item;
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
								{display:"部门名称",name:"query.name",newline:true},
								{display:"状态",name:"query.status",newline:false,labelWidth:LG.labelWidthA,type:"select",options:{valueFieldID:"type",data:[{text:'所有',id:'-1'},{ text: '有效', id: '1' },{ text: '无效', id: '0' }]}}
							]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
					top.framework.addTab(LG.getChildMenuNo(), '新增部门信息', 'Dept/add.do?menuno='+menuno);
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getChildMenuNo(), '查看部门信息', 'Dept/view.do?IsView=1&menuno='+menuno + '&id=' + selecteds[0].id);
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
	                top.framework.addTab(LG.getChildMenuNo(), '修改部门信息', 'Dept/modify.do?IsView=1&menuno='+menuno + '&id=' + selecteds[0].id);
				},
				del : function() {
					LG.deleteData(this);
				},
				off:function(){
					LG.changeStatus(this,"off.do");
				},
				on:function(){
					LG.changeStatus(this,"on.do");
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crGrid.init();
		</script>
	</body>
</html>