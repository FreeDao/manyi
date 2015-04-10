<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="/js/ligerui.min.js" type="text/javascript"></script>
		<script src="/js/common.js" type="text/javascript"></script>
		<script type="text/javascript">
			var menuno=<%=request.getAttribute("menuno")%>+"";
			var permissionValue=<%=request.getAttribute("permissionValue")%>;
			permissionValue = 63;
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
							{display:"角色名称",name:"dname",width:"40%",align:'left'},
							{display:"类型",name:"dtype",render:LG.roleType,width:"30%"},
							{display:"状态",name:"dstatus",render: LG.status,width:"30%"}
					   ],
			           dataAction: 'server', 
			           pageSize: 15, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           url:  'list.rest',
			           //sortName: 'd_id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: true,
			           onDblClickRow:function(data, rowindex, rowobj){
							top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view','查看角色信息', 'view.do?id='+data.did);
						}
			      });
			      self.search();
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);;
				},
				toolbar : function(item) {
					this[item.id]();
				},
				search : function() {
					jQuery(this.formsearch).ligerForm({
						labelWidth:LG.labelWidthB,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthB,
						labelAlign: "right", 
						fields:[
								{display:"角色名称",name:"query.dname",newline:true},
								{display:"状态",name:"query.dstatus",newline:false,type:"select",options:{valueFieldID:"query.dstatus",selectBoxHeight:90,data:LG.statusTypeDataL}}
							]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
					top.framework.addTab(LG.getChildMenuNo(), '新增角色信息', 'add.do?menuno='+menuno);
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view','查看角色信息', 'view.do?id=' + selecteds[0].did);
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_modify','修改角色信息', 'modify.do?id=' + selecteds[0].did+"&menuno="+menuno);
				},
				off:function(){
					LG.changeStatus(this,"off.do");
				},
				on:function(){
					LG.changeStatus(this,"on.do");
				},
				del : function() {
					var self = this, ids = [], rows = this.grid.getSelecteds();
			        if (!rows.length) { LG.tip(LG.INFO.ONE); return }
			        LG.showSuccess(LG.INFO.THREE,"confirm", function (confirm) {
			            if (confirm){
							for ( var i = 0; i < rows.length; i++) {
								if(rows[i].dinitvalue==1){
									LG.tip(rows[i].dname+"为系统初始化数据，不允许删除！");
									return;
								}else{
									ids.push(rows[i].did);
								}								
							}
							$.ajax( {
								url : 'delete.do',
								data : {ids : ids.join()},
								success : function(data) {
									if(!data.iserror){
										LG.showSuccess(LG.INFO.FOUR, null,800);
										self.reload();
									}
								}
							});
			            }
			        });
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crGrid.init();
		</script>
	</body>
</html>