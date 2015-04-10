<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<script type="text/javascript">
			var id=<%=request.getAttribute("id")%>;
			var menuno=<%=request.getAttribute("menuno")%>+"";
		</script>
	</head>
	<body>
		<form id="mainform" class="addEditManiForm" method="post" action="update.do" style="margin-left: 30px;"></form>
		<div class="l-clear"></div>
		<br />
		<div id="privsRole" style="margin: 0 30px;" class="privsRole">
			<div title="添加权限组">
				<div id="privsTree" class="privsTree"></div>
			</div>
			<!-- 
			<div title="该角色所属操作员">
				<div id="roleGrid" style="padding-top: 3px" class="roleGrid"></div>
			</div> 
			-->
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
				        labelWidth:LG.labelWidthA,
				        fields : [
							{name:"tblRole.did",type:"hidden"},
							{name:"permissiongroups",type:"hidden"},
							{display:"角色名称",name:"tblRole.dname",space:160,newline:false,labelWidth:LG.labelWidthB,validate:{REQUERELENGTH:[1,16]},group:"基本信息",groupicon:groupicon},
							{display:"类型",name:"dtype",space:160,newline:false,type:"select",comboboxName:"MType",options:{valueFieldID:"tblRole.dtype",data:[{text:'云平台角色',id:1},{text:'企业角色',id:2}]}},
							{display:"状态",name:"dstatus",space:160,newline:false,type:"select",comboboxName:"MMType",options:{valueFieldID:"tblRole.dstatus",selectBoxHeight:60,data:LG.statusTypeData}},
							{display:"描述",name:"tblRole.ddesc",type:"textarea",labelWidth:LG.labelWidthB,width:LG.inputWidthD,validate:{LENGTH1:[0,16]}}
						],
						toJSON:JSON2.stringify
				   });
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				   if (LG.getQueryStringByName("id")) {
						this.mainform.attr("action", "update.do");
						this.loadForm();
				   } else {
						this.mainform.attr("action", "save.do");
				   }
				},
				save : function() {
					var privNodes=privTree.getChecked();
					var permissionArray=[];
					for(var p in privNodes){
						permissionArray.push(privNodes[p].data.id);
					}
					document.getElementById("permissiongroups").value=permissionArray;
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
					LG.loadForm(this.mainform, {url:"loadModify.do?id="+id,preID:'tblRole\\.'},function(){});
				}
			}
			crForm.init();
			
			// ==============================
			var crPrivsRole = {
				main : "#privsRole",
				privs : "#privsTree",
				//role : "#roleGrid",
				init : function() {
					//this.creRoleGrid();
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
		            	url:"getAllPermissionGroup.do?id="+id
		            });
				}
				/*
				creRoleGrid:function(){
				  	var self = this;
					this.grid = jQuery(this.role).ligerGrid({ 
			         	columns: [
							{display:"登录名",name:"AA"},
							{display:"真实姓名",name:"BB"},
							{display:"性别",name:"SEX",render: LG.sex},
							{display:"部门",name:"DD"},
							{display:"邮箱",name:"EE"},
							{display:"状态",name:"MM",render: LG.status}
					   ],
			           dataAction: 'server', 
			           pageSize: 20, 
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           toolbar: {},
			           isChecked : function(){return true;},
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[20,50,100],
			           url:  '_oper_list.js', sortName: 'id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: true
			      });
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
				},
				toolbar : function(item) {
					this[item.id]();
				},
				add : function() {
					if (window.win) {
						window.win.show();
					} else {
				  		var self = this;
						var panle = $("<div></div>"),gridPanle = $("<div id='abc' style='width:700px;height:300px'></div>");
						panle.append(gridPanle);
						this.wingrid = gridPanle.ligerGrid( {
							columns: [
								{display:"登录名",name:"AA",width:"18%"},
								{display:"真实姓名",name:"BB",width:"18%"},
								{display:"性别",name:"SEX",width:"16%",render: LG.sex},
								{display:"部门",name:"DD",width:"16%"},
								{display:"邮箱",name:"EE",width:"16%"},
								{display:"状态",name:"MM",width:"16%",render: LG.status}
						   ],
							pageSize : 20,checkbox: true,
							url : '_oper_list.js',width : '98%',
							height : 300
						});
						window.win = $.ligerDialog.open( {
							title : "加入分类", width:640,height:370,top:50,
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
					}
				},
				hideWingrid : function(d) {
					d.hide();
				},
				addRows : function(self) {
				 	var row = self.wingrid.getSelectedRows();
            		if (!row) {alert(LG.INFO.ONE); return false; }
            		var newRow = jQuery.extend([], row ||[]);            		
					self.grid.addRows(newRow);
					self.dialog.hide();
				},
				del : function() {
					var self = this, ids = [], rows = this.grid.getSelecteds();
	                if (!rows.length) { LG.tip(LG.INFO.ONE); return }
	                LG.showSuccess(LG.INFO.THREE,"confirm", function (confirm) {
	                    if (confirm){
							for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
							}
							LG.ajax( {
								loading : LG.INFO.FIVE,
								url : 'Action/Contentcountry/Delete.do',
								data : {ids : ids.join()},
								success : function() {
									LG.showSuccess(LG.INFO.FOUR);
									self.reload();
								},
								error : function(message) {
									LG.showError(message);
								}
							});
	                    }
	                });
				},
				reload : function() {
					this.grid.loadData();
				}
				*/
			}
			crPrivsRole.init();
		</script>
		<script src="../static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		<br/>
		<br/>
		<br/>
		<br/>
	</body>
</html>