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
		<link href="../static/xcloud/css/ligerui-common.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
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
							{display:"名称",name:"depgname",width:"40%",align:'left'},
							{display:"状态",name:"dstatus",render:LG.status,width:"10%"},							
							{display:"模板文件地址",name:"depgurl",width:"50%"}
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
							top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view','查看EPG信息', 'TblEpginfo/view.do?id='+data.did);
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
						   {display:"名称",name:"query.depgname",newline:true},
						   {display:"状态",name:"query.dstatus",newline:false,type:"select",options:{valueFieldID:"query.dstatus",selectBoxHeight:90,data:LG.statusTypeDataL}}
						]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
					top.framework.addTab(LG.getChildMenuNo(), '新增EPG信息', '<%=path%>/TblEpginfo/add.do?menuno='+LG.getPageMenuNo());
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view','查看EPG信息', '<%=path%>/TblEpginfo/view.do?IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did);
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_modify','修改EPG信息', '<%=path%>/page/TblEpginfo/modify-success.jsp?menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did);
				},
				del : function() {
					var self = this, ids = [], rows = this.grid.getSelecteds();
	                if (!rows.length) { LG.tip(LG.INFO.ONE); return }
	                for ( var i = 0; i < rows.length; i++) {
						if(rows[i].did == 0){
							LG.tip(rows[i].depgname +" 不能删除,系统管理使用.");
							return ;
						}
					}
	                LG.showSuccess(LG.INFO.THREE,"confirm", function (confirm) {
	                    if (confirm){		                    
							for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].did);
							}
							$.ajax( {
								url : '<%=path%>/TblEpginfo/delete.do',
								data : {ids : ids.join()},
								success : function(data) {
									if(data.iserror){
										LG.tip("以下EPG："+data.message+"已被使用,未进行删除！");
										self.reload();
									}else{
										LG.showSuccess(LG.INFO.FOUR, null,800);
										self.reload();
									}
								}
							});
	                    }
	                });
				},
			on:function() {
					var self = this, ids = [], rows = this.grid.getSelecteds();
			        if (!rows.length) { LG.tip(LG.INFO.ONE); return }
							for ( var i = 0; i < rows.length; i++) {
								var name=rows[i].depgname;
								if(rows[i].dstatus ==1){
								 	LG.tip("["+name+"]"+LG.INFO.SEVEN);
									return ;
								}
								ids.push(rows[i].did);
							}
							$.ajax( {
								url : '<%=path%>/TblEpginfo/on.do?date='+new Date(),
								data : {ids : ids.join()},
								success : function(data) {
									LG.showSuccess(LG.INFO.FOUR, null,800);
									self.reload();
								}
							});
				},	
			off:function() {
					var self = this, ids = [], rows = this.grid.getSelecteds();
			        if (!rows.length) { LG.tip(LG.INFO.ONE); return }
			            	for ( var i = 0; i < rows.length; i++) {
								var name=rows[i].depgname;
								if(rows[i].dstatus ==0){
								 	LG.tip("["+name+"]"+LG.INFO.EIGHT);
									return ;
								}
								ids.push(rows[i].did);
							}
							$.ajax( {
								url : '<%=path%>/TblEpginfo/off.do?date='+new Date(),
								data : {ids : ids.join()},
								success : function(data) {
									if(data.iserror){
										LG.tip("以下EPG："+data.message+"已被使用,未进行停用！");
										self.reload();
									}else{
										LG.showSuccess(LG.INFO.FOUR, null,800);
										self.reload();
									}
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