<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.pkit.model.TblOperator"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="../static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<script src="../static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/common.js" type="text/javascript"></script>		
		<script src="../static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			var menuno=<%=request.getAttribute("menuno")%>+"";
			var companycode=<%=((TblOperator) request.getSession().getAttribute("operator")).getDcompanyid()%>;
		</script>
	</head>
	<body>
		<form id="mainform" class="addEditManiForm" method="post" action="save.do" style="margin-left: 30px;"></form>
		<div class="l-clear"></div>
		<div class="l-form">
			<div class="l-group l-group-hasicon"><span>关联员工</span></div>
			<div class="l-custom-subjectList" id="employeediv" style="display: none">
				<div class="l-custom-subjectList-top"></div>
				<div id="maingrid"></div>
				<div class="l-custom-subjectList-bot"></div>
			</div>
			<div class="br"></div>
			<a class="l-custom-subjectList-add" onclick="crForm.addNewRow()">添加员工</a>
		</div> 
		<script type="text/javascript">
			var validateSubmitemployee=true;
			var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main);
					var PayType = [{ text: '月结', id: '1' },{ text: '年结', id: '0' }];  
					var groupicon = "../static/xcloud/css/img/icon/communication.gif";
				    this.mainform.ligerForm({
				        labelAlign: "right", 
				        inputWidth: LG.inputWidthA,
				        labelWidth:LG.labelWidthC,
				        fields : [
							{name:"employees",type:"hidden"},
							{display:"用户账号",name:"predusername",width:50,space:1,newline:false,options:{value:companycode,disabled:true},group:"基本信息",groupicon:groupicon},
							{name:"tblUser.dusername",width:130,labelWidth:1,space:LG.spaceWidthB,newline:false,validate:{onlyGraphemeOrNum:[5,16],ONLYONE:'onlyusername.do'}},
							{display:"用户密码",name:"tblUser.dpassword",newline:false,validate:{onlyGraphemeOrNum:[6,16]},options:{value:123456}},
							{display:"付费类型",name:"dpaytype",space:LG.spaceWidthB,newline:true,type:"select",comboboxName:"dpaytype",options:{valueFieldID:"tblUser.dpaytype",data:PayType,value:1}},
							//{display:"计费账号",name:"tblUser.daccount",newline:false,validate:{onlyGraphemeOrNum:[5,40],ONLYONE:'onlyaccount.do'}},														
							{display:"用户地址",name:"tblUser.daddress",newline:false,validate:{ADDRESS:[0,32]}},
							{display:"用户电话",name:"tblUser.dtel",space:LG.spaceWidthB,newline:true,validate:{TELPHONE:true}},
							{display:"用户状态",name:"dstatus",type:"select",newline:false,comboboxName:"dstatus",options:{valueFieldID:"tblUser.dstatus",selectBoxHeight:60,data:LG.statusTypeData,value:1}}							
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
				   this.grid = jQuery("#maingrid").ligerGrid({ 
			         	columns: [ {width : 80,render : function (rowdata, rowindex, value) {
							var h = "";
							if (!rowdata._editing) {
								h += "<a href='javascript:crForm.beginEdit()'>编辑</a> ";
								h += "<a href='javascript:crForm.deleteRow()'>删除</a> ";
							} else {
								h += "<a href='javascript:crForm.endEdit()'>保存</a> ";
							}
							return h;
						}}, {width:60,align :"right",render : function() {return "姓名：";}}, 
							{width:100,align :"left",name : "name",editor: {type:'text'}},
							{width:60,align :"right",render : function() {return "工号：";}},
							{width:100,align :"left",name : "employeeno",editor: {type:'text'}},
							{width:60,align :"right",render : function() {return "部门：";}},
							{width:100,align :"left",name : "department",editor: {type:'text'}}
					   ],
			           dataAction: 'server', 
			           pageSize: 3, 
			           toolbar: null,
			           mouseoverRowCssClass:null,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight userGrid",
			           usePager:false,
			           enabledEdit: true, clickToEdit: false,
			           data:{"rows" : [],"total" : 0},
			           sortName: 'id',sortOrder:'desc',
			           width: '98%', height: 'auto',heightDiff:-70,
			           onBeforeSubmitEdit:function(e){
				           	var rows=crForm.grid.rows;
							var row =e.newdata;
							var record=e.record;
					        var name=row.name;
							var employeeno=row.employeeno;
							var department=row.department;
							validateSubmitemployee=true;
							if(name.length<=0||employeeno.length<=0||department.length<=0){
								LG.tip("保存员工时不允许有空值");
								validateSubmitemployee=false;
								return false;
							}
							if(name.length>10||employeeno.length>40||department.length>20){
								LG.tip("姓名不能超过10个字符,工号不能超过20个字符,部门不能超过20个字符");
								validateSubmitemployee=false;
								return false;
							}
							for(var i=0;i<rows.length;i++){
								var rowData=rows[i];
								if(rowData.__id!=record.__id&&rowData.employeeno==employeeno){
									LG.tip("工号不允许重复！");
									validateSubmitemployee=false;
									return false;
								}
							}
							var isOnly=true;
							$.ajax({
								cache : false,
								async : false,
								url : 'onlyoneemployee.do',
								data:{'value':employeeno},
								dataType : 'json',
								type : 'post',
								success:function(data){
									if(data.iserror){
										LG.tip("工号不能重复,系统中已有该工号");
										isOnly=false;
									}
								}		
							});
							if(!isOnly){
								validateSubmitemployee=false;
								return false;
							}
				           	return true;
				       }
			      });
				},
				save : function() {
					var employeeArray=[];
					var dd=this.grid.getAdded();
					if(dd!=null&&dd!=undefined&&dd.length>0){
						var len=dd.length;
						for(var i=0;i<len;i++){
							if(dd[i]._editing){
								LG.tip("有员工信息未保存,请保存后再提交！");
								return;
							}else{
								employeeArray.push(dd[i].name+"#"+dd[i].employeeno+"#"+dd[i].department);
							}
						}
						document.getElementById("employees").value=employeeArray;
					}
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
				},
				addNewRow : function() {
					document.getElementById("employeediv").style.display="";
					this.grid.endEdit();
					if (validateSubmitemployee){
						var row = this.grid.add();
	            		this.grid.beginEdit(row);
					}					
				},
				deleteRow : function() {
					var rows=crForm.grid.rows;
					var row1=this.grid.getSelectedRow();
					for(var i=0;i<rows.length;i++){
						if(rows[i]._editing){
							LG.tip("在删除前，请保存其他员工信息，以免数据丢失");
							return;
						}
					}
					var row = this.grid.deleteSelectedRow();
					var dfgd=crForm.grid.rows;
					if(dfgd.length==0||(dfgd.length==1&&dfgd[0].__id==row1.__id)){
						document.getElementById("employeediv").style.display="none";
					}
				},
				endEdit : function() {
					var row = this.grid.getSelectedRow();
		            if (!row) { alert('请选择行'); return; }
		            this.grid.endEdit(row);							
				},
				beginEdit:function(){
		            var row = this.grid.getSelectedRow();
		            this.grid.beginEdit(row);
		        }
			}
			crForm.init();
		</script>
		<br/>
		<br/>
		<br/>
	</body>
</html>