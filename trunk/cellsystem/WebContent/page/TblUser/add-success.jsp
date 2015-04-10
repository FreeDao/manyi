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
			var companycode="${sessionScope.operator.dcompanyid}";
			var selectedCode='';
			function deptchange(obj){
				var dept = $(obj);
				var t = null;
				if(dept.val() != -1){
					var msg=loaddept(dept.val());
					if(msg == ''){
						t = dept.nextAll("select");
					}else{
						dept.after(msg);
						t=$("#dept_"+dept.val()).nextAll("select");
					}
				}else{
					t = dept.nextAll("select");
				}
				if(t != null && t.length>0){
					t.remove();
				}
				$("#show").html(dept.parent("div").html());
				selectedCode = dept.val();
			}
			
			function loaddept(deptcode){
				var depjson= '';
				$.ajaxSetup({  
				    async : false
				}); 
				$.post(
					"<%=path%>/Dept/loadDeptByParentCode.do",
					{"deptcode":deptcode},
					function(data,status){
						if(status == 'success'){
						var rows = data.rows;
						if(rows != null && rows.length > 0){
							var str= "<select id ='dept_"+deptcode+"' onchange ='deptchange(this)' class='l-text l-text-combobox' style='width:90px;'>";
							str +="<option value = '-1'>请选择</option>";
							for(var i=0 ; i < rows.length ; i ++){
								var code = rows[i].code;
								var name = rows[i].name;
								str +="<option value = '"+code+"'>"+name+"</option>";
							}
							str += "</select>";
							depjson= str;
						}else{
							return "";
						}
					  }
					},
					"json"
				);
				return depjson;
			}
			
			$(document).ready(function(){
			/*
				//首先加载一级部门
				var depjson= '';
				$.ajax({
					type : 'post',
					url : '<%=path%>/Dept/loadDeptByParentCode.do',
					async : false,
					data : {'deptcode': 0}, //加载第一级  (所有的父级栏目)
					success:function(data){
						var str= "<select id ='deptid' onchange ='deptchange(this)' class='l-text l-text-combobox' style='width:90px;'>";
						str +="<option value = '-1'>请选择</option>";
						if(data != null){
							var rows = data.rows;
							if(rows != null && rows.length > 0){
								for(var i=0 ; i < rows.length ; i ++){
									var code = rows[i].code;
									var name = rows[i].name;
									str +="<option value = '"+code+"'>"+name+"</option>";
								}
							}
						}
						str += "</select>";
						depjson = str;
					}
				});
				
				var show = $("#show");
				show.html(depjson);
				
				$("#show > select").val('-1');
				var pt=$("#department").parent("div");
				pt.removeAttr("class");
				pt.removeAttr("style");
				pt.parent("li").attr("style",'text-align:left;');
				$("#department").remove();
				
				pt.html(show.html());
				*/
				
				
				initdept();
			});
			
			
			
			function initdept(){
				//首先加载一级部门
				var depjson= '';
				$.ajax({
					type : 'post',
					url : '<%=path%>/Dept/loadDeptByParentCode.do',
					async : false,
					data : {'deptcode': 0}, //加载第一级  (所有的父级栏目)
					success:function(data){
						var str= "<select id ='deptid' onchange ='deptchange(this)' class='l-text l-text-combobox' style='width:90px;'>";
						str +="<option value = '-1'>请选择</option>";
						if(data != null){
							var rows = data.rows;
							if(rows != null && rows.length > 0){
								for(var i=0 ; i < rows.length ; i ++){
									var code = rows[i].code;
									var name = rows[i].name;
									str +="<option value = '"+code+"'>"+name+"</option>";
								}
							}
						}
						str += "</select>";
						depjson = str;
					}
				});
				
				var show = $("#show");
				show.html(depjson);
				$("#show > select").val('-1');
				var pt=$("#department").parent("div");
				pt.removeAttr("class");
				pt.removeAttr("style");
				pt.parent("li").attr("style",'text-align:left;');
				$("#department").remove();
				
				pt.html(show.html());
				
					
				//加载 其他的所属部门
				//var dc=document.getElementById("tblEmployee.ddepartmentno").value;
				var dc='${sessionScope.deptCode}';
				//alert(dc);
				if(dc != null && dc.length>0){
					var len=dc.length/4;
					for(var i =0 ; i < len; i++){
						if(i ==0 ){
							var deptSlelect=$("#deptid");
							var emp=dc.substr(0,4);
							deptSlelect.val(emp);
							deptSlelect.trigger('onchange');//动态触发select的onchange事件
						}else{
							var emp = dc.substr(0,4*i);
							var deptsel=$("#dept_"+emp);
							deptsel.val(dc.substr(0,(i+1)*4));
							deptsel.trigger('onchange');//动态触发select的onchange事件
						}
					}
				}
				
			}
				
			
		</script>
	</head>
	<body>
	<div>
		<form id="mainform" class="addEditManiForm" method="post" action="save.do" style="margin-left: 30px;"></form>
		<div class="l-clear"></div>
			<div class="br"></div>
			
			<div id ='show' style="display: none;" >
			</div>
	</div> 
	
		<script type="text/javascript">
			var validateSubmitemployee=true;
			var dparentidTypeData=${requestScope.parentidType};
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
							{display:"用户账号",name:"predusername",width:50,space:1,newline:false,options:{value:companycode,disabled:true},group:"基本信息",groupicon:groupicon},
							{name:"tblUser.dusername",width:130,labelWidth:1,space:LG.spaceWidthB,newline:false,validate:{onlyGraphemeOrNum:[5,16],ONLYONE:'onlyusername.do'}},
							{display:"用户密码",name:"tblUser.dpassword",newline:false,validate:{onlyGraphemeOrNum:[6,16]},options:{value:123456}},
							{display:"付费类型",name:"dpaytype",space:LG.spaceWidthB,newline:true,type:"select",comboboxName:"dpaytype",options:{valueFieldID:"tblUser.dpaytype",data:PayType,value:1}},
							//{display:"计费账号",name:"tblUser.daccount",newline:false,validate:{onlyGraphemeOrNum:[5,40],ONLYONE:'onlyaccount.do'}},														
							{display:"用户地址",name:"tblUser.daddress",newline:false,validate:{ADDRESS:[0,32]}},
							{display:"用户电话",name:"tblUser.dtel",space:LG.spaceWidthB,newline:true,validate:{TELPHONE:true}},
							{display:"用户状态",name:"dstatus",type:"select",newline:false,comboboxName:"dstatus1",options:{valueFieldID:"tblUser.dstatus",selectBoxHeight:60,data:LG.statusTypeData,value:1}},
							
							
							{display:"员工姓名",name:"tblEmployee.dname",type : "text",space:LG.spaceWidthB , newline:true,validate:{required:true,maxlength:64},group:"员工信息",groupicon:groupicon},
							{display:"员工工号",name:"tblEmployee.demployeeno",newline: false ,validate:{onlyGraphemeOrNum:[1,16], ONLYONE : "onlyoneemployee.do"}},
							{name:"tblEmployee.ddepartmentno", type:'hidden'},
							{name:"tblEmployee.ddepartment", type:'hidden' },
							{display:"所属部门",name:"department", type:'text' ,newline: true }
							//{display:"所属部门",name:"dstatusdept",type:"select",newline: false,comboboxName:"dstatusdept1",options:{width:110 ,valueFieldID:"tblEmployee.ddepartment",selectBoxHeight:60,data:dparentidTypeData,value:0}}
							
						],
						toJSON:JSON2.stringify
				   });
				   
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				   //var dstatusDiv = $("#dstatus1").ligerComboBox({onSelected : function(key,value){alert(value+','+key);}});
				},
				save : function() {
				var t=$("#show > select").last();
				var deptcode= ''; //得到 用户选择的 部门code
				if(t.attr("id") == 'deptid'){
					//只有一级
					if(selectedCode== '' || selectedCode == -1){
						LG.tip('请选择员工所属部门.');
						return ;
					}else{
						deptcode =selectedCode;
					}
				}else{
					deptcode = selectedCode;
				}
					//alert(deptcode);
				this.mainform[0]['tblEmployee.ddepartmentno'].value= deptcode;
				
		            LG.submitForm(this.mainform, function (data) {
		            	var win = parent || window;
		                if (data.iserror) {
		                    win.LG.showError(data.message);
		                }else {
			                    win.LG.showSuccess(LG.INFO.SIX, null,800);
			                    win.LG.closeAndReloadParent(null, menuno);
					  }
				  });
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				}
			}
			crForm.init();
			
			
		</script>
		<br/>
		<br/>
		<br/>
	</body>
</html>