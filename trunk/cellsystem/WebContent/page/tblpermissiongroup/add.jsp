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
		<link href="../static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-tab.css" rel="stylesheet" type="text/css" />
		<script src="../static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/common.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			var menuno=<%=request.getAttribute("menuno")%>+"";
		</script>
</head>
<body>
		<form id="mainform" class="addEditManiForm" method="post" action="save.do" style="margin-left: 30px;"></form>
		<div class="l-clear"></div>
		<div id="privsRole" class="privsRole">
			<div title="权限定义">
				<div id="privsTree" class="privsTree"></div>
			</div>
			<div title="所属角色">
				<div id="roleGrid" class="roleGrid"></div>
			</div>
		</div>
		<script type="text/javascript">
				var privTree;
				var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main);  
					var groupicon = "../static/xcloud/css/img/icon/communication.gif";
				    this.mainform.ligerForm({ 
				        labelAlign: "right", 
				        inputWidth: LG.inputWidthA,
				        labelWidth:LG.labelWidthB+15,
				        fields : [
							{name:"tblPermissiongroup.dpermissions",type:"hidden"},
							{name:"roleList",type:"hidden"},
							{display:"权限组名称",width:"33%",name:"tblPermissiongroup.dname",space:LG.spaceWidthB,newline:false,validate:{REQUERELENGTH:[1,16]},group:"基本信息",groupicon:groupicon},
							{display:"状态",width:"33%",name:"dstatus",newline:false,labelWidth:LG.labelWidthA,type:"select",comboboxName:"MMType",options:{valueFieldID:"tblPermissiongroup.dstatus",selectBoxHeight:60,data:LG.statusTypeData,value:1}},
							{display:"描述",width:"34%",name:"tblPermissiongroup.ddesc",type:"textarea",width:LG.inputWidthG,validate:{LENGTH1:[0,16]}}
						],
						toJSON:JSON2.stringify
				   });
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				   if (LG.getQueryStringByName("id")) {
						this.mainform.attr("action", "modify.do");
						this.loadForm();
				   } else {
						this.mainform.attr("action", "save.do");
				   }
				},
				save : function() {
					var privNodes=privTree.getChecked();
					var permissionArray=[];
					var roles=[];
					var roleList=crPrivsRole.grid.rows;
					for(var p in privNodes){
						permissionArray.push(privNodes[p].data.id);
					}
					document.getElementById("tblPermissiongroup.dpermissions").value=permissionArray;
					for(var i=0;i<roleList.length;i++){
						roles.push(roleList[i].did);
					}
					document.getElementById("roleList").value=roles;
		            LG.submitForm(this.mainform, function (data) {
		                var win = parent || window;
		                if (data.iserror) {  
		                    win.LG.showError(data.message);
		                }
		                else {
		                    win.LG.showSuccess(LG.INFO.SIX, null,800);
		                    win.LG.closeAndReloadParent(null, menuno);
		                }
		            });
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				loadForm : function() {
					LG.loadForm(this.mainform, {url:""},function(){});
				}
			}
			crForm.init();
			
			// ==============================
			var crPrivsRole = {
				main : "#privsRole",
				privs : "#privsTree",
				role : "#roleGrid",
				init : function() {
					this.creRoleGrid();
					this.crePrivsTree();
					this.tab = $(this.main).ligerTab( {
						heightDiff : -30,
						changeHeightOnResize : true,
						contextmenu : true
					});
				},
				crePrivsTree:function(){
					privTree=$(this.privs).ligerTree({ 
						nodeWidth:"100%",
		            	url:"getAllSysmenu.do",
		            	onAfterAppend :function(){
	            		$(">li",this.element).each(function(i){
	            			var size = $("ul",this).length;
	            			if (size === 1){
			            		$(this).addClass("tree-layerA");
			            		$(this).css({"border-bottom":"1px solid #DCDEE3"});
			            	}
			            	if (i%2!=0){
			            		$(this).css({"background":"#f6f6f7"});
			            	}
	            		})
		            	}
		            });
				},
				creRoleGrid:function(){
				  	var self = this;
					this.grid = jQuery(this.role).ligerGrid({ 
			           columns: [{display:"角色名称",name:"dname",width: '100%'}],
			           dataAction: 'server', 
			           pageSize: 20, 
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           toolbar: {},
			           isChecked : function(){return true;},
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[20,50,100],
			           url:'',
			           sortName: 'did',
			           sortOrder:'desc',
			           width: '98%', 
			           height: '100%',
			           heightDiff:-13, 
			           checkbox: true
			      });
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,10);
				},
				toolbar : function(item) {
					this[item.id]();
				},					
				add : function() {
					var self = this;
					var panle = $("<div></div>");
					var gridPanle = $("<div style='width:700px;height:300px'></div>");
					panle.append(gridPanle);	
					window.win = $.ligerDialog.open( {
						title : "选择角色", width:640,height:370,top:50,
						cls : "l-custom-openWin",
						target : panle,
						buttons : [ {
							text : '选择',
							onclick : function (item, dialog){
								self.dialog = dialog;
								self.addRows(self);
							}
						}, {
							text : '取消',
							onclick : function(item, dialog) {
								self.hideWingrid(dialog);
							}
						} ]
					});
					this.wingrid = gridPanle.ligerGrid({
						columns:[{display:"角色名称",name:"dname",width:'98%',align:'left'}],
						pageSize : 20,
						checkbox: true,
						method:'post',
						pageSizeOptions:[20,50,100],
						url : 'getRoleList.do',
						width : '98%',
						height : 300
					});
				},
				hideWingrid : function(d) {
					d.hide();
				},
				addRows : function(self) {
				 	var row = self.wingrid.getSelectedRows();
					var gridRow=self.grid.rows;
            		if (row == null || row.length == 0) {LG.tip(LG.INFO.ONE); return false; }
            		if(gridRow==null||gridRow.length==0){
						self.grid.addRows(row);
					}else{
						for(var i=0;i<row.length;i++){
							var has=false;
							for(var j=0;j<gridRow.length;j++){
								if(row[i].did==gridRow[j].did){
									has=true;
									break;
								}
							}
							if(!has){
								self.grid.addRows(row[i]);
							}
	                	}
					}
					self.dialog.hide();
				},
				del : function() {
					var self = this, ids = [], rows = this.grid.getSelecteds();
	                if (!rows.length) { LG.tip(LG.INFO.ONE); return;}
	                LG.showSuccess(LG.INFO.THREE,"confirm", function (confirm) {
	                    if (confirm){
							self.grid.deleteSelectedRow();
	                    }
	                });
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crPrivsRole.init();
		</script>		
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
	</body>
</html>