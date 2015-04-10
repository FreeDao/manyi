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
		<div id="maingrid"></div>
		<script type="text/javascript">
			var crGrid = {
				main : "#maingrid",
				formsearch : "#formsearch",
				init : function() {
				  	var self = this;
					this.grid = jQuery(self.main).ligerGrid({ 
			         	columns: [
						{display:"用户账号",name:"dusername",width:"40%",align:'left'},
						{display:"密码",name:"dpassword",width:"40%"},
						//{display:"计费账号",name:"daccount",width:"25%"},
						{display:"状态",name:"dstatus",width:"20%",render: LG.status}
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
							top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view','查看用户信息', 'TblUser/view.do?id='+data.did);
						}
			      	});
			      	self.search();
			      	//LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
			      	this.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
				},
				loadToolbar:function(grid, barClick, tableCode,permissionValue){
					var operateButton=[{
				        "btnicon": "../static/xcloud/css/img/icon/jui-icon-view.png",
				        "btnid": 1,
				        "btnname": "查看",
				        "btnno": "view",
				        "menuno": "t_series",
				        "seqno": "1"
				    },{
				        "btnicon": "../static/xcloud/css/img/icon/jui-icon-add.png",
				        "btnid": 2,
				        "btnname": "新增",
				        "btnno": "add",
				        "menuno": "t_series",
				        "seqno": "1"
				    }, {
				        "btnicon": "../static/xcloud/css/img/icon/jui-icon-edit.png",
				        "btnid": 4,
				        "btnname": "修改",
				        "btnno": "modify",
				        "menuno": "t_series",
				        "seqno": "1"
				    }, {
				        "btnicon": "../static/xcloud/css/img/icon/jui-icon-del.png",
				        "btnid": 8,
				        "btnname": "删除",
				        "btnno": "del",
				        "menuno": "t_series",
				        "seqno": "1"
				    },{
				        "btnicon": "../static/xcloud/css/img/icon/jui-icon-on.png",
				        "btnid": 16,
				        "btnname": "启用",
				        "btnno": "on",
				        "menuno": "t_series",
				        "seqno": "1"
				    },{
				        "btnicon": "../static/xcloud/css/img/icon/jui-icon-off.png",
				        "btnid": 32,
				        "btnname": "停用",
				        "btnno": "off",
				        "menuno": "t_series",
				        "seqno": "1"
				    },{
				        "btnicon": "../static/xcloud/css/img/icon/jui-icon-add.png",
				        "btnid": 64,
				        "btnname": "批量导入",
				        "btnno": "batchSave",
				        "menuno": "t_series",
				        "seqno": "1"
				    }];
					if (!grid.toolbarManager)
						return;
					if (!operateButton || !operateButton.length)
						return;
					var items = [];
					if((permissionValue&1)==1){
						var o = operateButton[0];
						items[items.length] = {
							click : barClick,
							text : o.btnname,
							id : o.btnno,
			                img: o.btnicon,
							disable : true
						};
					}
					if((permissionValue&2)==2){
						var o = operateButton[1];
						items[items.length] = {
							click : barClick,
							text : o.btnname,
							id : o.btnno,
			                img: o.btnicon,
							disable : true
						};
					}
					if((permissionValue&4)==4){
						var o = operateButton[2];
						items[items.length] = {
							click : barClick,
							text : o.btnname,
							id : o.btnno,
			                img: o.btnicon,
							disable : true
						};
					}
					if((permissionValue&8)==8){
						var o = operateButton[3];
						items[items.length] = {
							click : barClick,
							text : o.btnname,
							id : o.btnno,
			                img: o.btnicon,
							disable : true
						};
					}
					if((permissionValue&16)==16){
						var o = operateButton[4];
						items[items.length] = {
							click : barClick,
							text : o.btnname,
							id : o.btnno,
			                img: o.btnicon,
							disable : true
						};
					}
					if((permissionValue&32)==32){
						var o = operateButton[5];
						items[items.length] = {
							click : barClick,
							text : o.btnname,
							id : o.btnno,
			                img: o.btnicon,
							disable : true
						};
					}
					if((permissionValue&2)==2){
						var o = operateButton[6];
						items[items.length] = {
							click : barClick,
							text : o.btnname,
							id : o.btnno,
			                img: o.btnicon,
							disable : true
						};
			      	}
					grid.toolbarManager.set('items', items);
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
								{display:"用户账号",name:"query.dusername",newline:true},
								{display:"状态",name:"query.dstatus",newline:false,labelWidth:LG.labelWidthA,type:"select",options:{valueFieldID:"query.dstatus",selectBoxHeight:90,data:LG.statusTypeDataL,value:LG.statusTypeDataL[0].id}}
							]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
					top.framework.addTab(LG.getChildMenuNo(), '新增用户信息', 'TblUser/add.do?menuno='+menuno);
				},
				batchSave:function(){
					var self =this;
					if (window.win) {
						window.win.show();
					} else {
						self.ftpwin = $.ligerDialog.open( {
							title : "选择FTP文件", width:700,height:460,top:20,
							cls : "l-custom-openWin l-custom-ftpWin",
							url : '<%=basePath%>page/TblUser/batchSave.jsp'
						});
					}
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view','查看用户信息', 'TblUser/view.do?id=' + selecteds[0].did);
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_modify','修改用户信息', 'TblUser/modify.do?menuno='+menuno + '&id=' + selecteds[0].did);
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