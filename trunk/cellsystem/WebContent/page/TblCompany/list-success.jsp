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
		<div id="maingrid"><div class="l-panel-topbar l-toolbar"></div></div>
		<script type="text/javascript">
			var crGrid = {
				main : "#maingrid",
				formsearch : "#formsearch",
				init : function() {
				  	var self = this;
					this.grid = jQuery(self.main).ligerGrid({ 
			         	columns: [
							{display:"名称",name:"dname",width:"15%",align:'left'},
							{display:"账号",name:"daccount",width:"15%"},
							{display:"行业类别",name:"dindustryname",width:"15%"},
							{display:"状态",name:"dstatus",width:"10%",render: LG.status},							
							{display:"启用时间",name:"denabledtime",width:"22%"},
							{display:"停用时间",name:"ddisabledtime",width:"23%"}
					   ],
			           dataAction: 'server', 
			           pageSize: 15, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           url:'getData.do',
			           //sortName: 'd_id desc',sortOrder:'desc',
			            height: '100%',heightDiff:-13, checkbox: true,
			           onDblClickRow:function(data, rowindex, rowobj){
			                top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view','查看企业信息', 'TblCompany/view.do?id='+data.did);
						}
			      });
			      self.search();
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
			      if((permissionValue&4)==4){
				  		$('#maingrid div.l-toolbar').append('<div class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon l-toolbar-item-disable" onclick="editPassword()"><span>密码重置</span><div class=l-panel-btn-l></div><div class=l-panel-btn-r></div><img src="../static/xcloud/css/img/icon/jui-icon-edit.png"></div>');      
			      }
				},				
				toolbar : function(item) {
					this[item.id]();
				},
				status:function(data){
					if(data.dstatus == 1){
						return '启用';
					}else if(data.dstatus == 0){
						return '禁用';
					}
				},
				YMDDate:function(data){
					if(data.duploaddate.indexOf(" ")>=0){
						return   data.duploaddate.substr(0,data.duploaddate.indexOf(" "));
					}else{
						var A = new Date(data.duploaddate);
      					 return A.getFullYear() + "-" + (A.getMonth() + 1) + "-" + A.getDay();
					}
				},
				DownDate:function(data){
					if(data.dupdowndate.indexOf(" ")>=0){
						return   data.dupdowndate.substr(0,data.dupdowndate.indexOf(" "));
					}else{
						var A = new Date(data.dupdowndate);
      					 return A.getFullYear() + "-" + (A.getMonth() + 1) + "-" + A.getDay();
					}
				},
				search : function() {
					jQuery(this.formsearch).ligerForm({
						labelWidth:LG.labelWidthB,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthA,
						labelAlign: "right", 
						fields:[
						   {display:"名称",name:"query.dname",newline:true },
						   {display:"账号",name:"query.daccount",newline:false},
						   {display:"状态",name:"query.dstatus",newline:false,labelWidth:LG.labelWidthA,type:"select",cssClass:"field",options:{valueFieldID:"query.dstatus",selectBoxHeight:90,data:LG.statusTypeDataL,value:LG.statusTypeDataL[0].id}}
						]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
					top.framework.addTab(LG.getChildMenuNo(), '新增企业信息', 'TblCompany/add.do?menuno='+menuno);
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view','查看企业信息', 'TblCompany/view.do?id=' + selecteds[0].did);
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_modify','修改企业信息', 'TblCompany/modify.do?id=' + selecteds[0].did+"&menuno="+menuno);
				},
				off:function(){
					LG.changeStatus(this,"off.do");
				},
				on:function(){
					LG.changeStatus(this,"on.do");
				},
				del : function() {
					var self=this;
					var ids = [] ,rows=self.grid.getSelecteds();
			         if (!rows.length) { LG.tip(LG.INFO.ONE); return }
			         LG.showSuccess(LG.INFO.THREE,"confirm", function (confirm) {
			        if (confirm){
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].did);
					}
					$.ajax( {
						url : 'logicDelete.do',
						dataType:'json',
						data : {ids : ids.join()},
						success : function(data) {
							var msg=data.message;
							if(msg == undefined){
								msg=LG.INFO.FOUR;
								LG.showSuccess(msg,null,1000);
							}else{
								var m=LG.showSuccess(LG.INFO.ELEVEN,null,1000);
								m._setWidth(380);
							}
							self.reload();
						},
						error : function(data) {
							LG.showError(data.message);
						}
						});
			           }
			         })
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crGrid.init();
			function editPassword(){
				var selecteds = crGrid.grid.getSelecteds();
				if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
				if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
				$.ajax({
					cache : false,
					async : false,
					url : 'editPassword.do',
					data:{'id':selecteds[0].did},
					dataType : 'json',
					type : 'post',
					success:function(data){
						if(data.iserror){
							
						}else{
							LG.showSuccess(data.message,null,1000);
						}
					}		
				});
			}	
		</script>
	</body>
</html>