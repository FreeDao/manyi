<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.pkit.model.TblOperator"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id=request.getAttribute("id")+"";
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
			
		</script>
	</head>
	

	
	<body>
	<div>
		<form id="mainform" class="addEditManiForm" method="post" action="update.do" style="margin-left: 30px;"></form>
		<div class="l-clear"></div>
			<div class="br"></div>
	</div> 
		<script type="text/javascript">
			var validateSubmitemployee=true;
			var dparentidTypeData=${requestScope.parentidType};
			var selectedCode='';
				
			var crForm = {
				main : "#mainform",
				mf: null,
				init : function() {
					var self = this;
					this.mainform = $(self.main);
					var PayType = [{ text: '月结', id: '1' },{ text: '年结', id: '0' }];  
					var groupicon = "../static/xcloud/css/img/icon/communication.gif";
				   this.mf= this.mainform.ligerForm({
				        labelAlign: "right", 
				        inputWidth: LG.inputWidthA,
				        labelWidth:LG.labelWidthC,
				        fields : [
				        	{name : "tblUser.did" , type : "hidden"},
							{display:"用户账号",name:"predusername",width:50,space:1,newline:false,options:{value:companycode,disabled:true},group:"基本信息",groupicon:groupicon},
							{name:"tblUser.dusername",width:130,labelWidth:1,space:LG.spaceWidthB,newline:false,validate:{onlyGraphemeOrNum:[5,16]}},
							{display:"用户密码",name:"tblUser.dpassword",newline:false,validate:{onlyGraphemeOrNum:[6,16]},options:{value:123456}},
							{display:"付费类型",name:"dpaytype",space:LG.spaceWidthB,newline:true,type:"select",comboboxName:"dpaytype",options:{valueFieldID:"tblUser.dpaytype",data:PayType,value:1}},
							//{display:"计费账号",name:"tblUser.daccount",newline:false,validate:{onlyGraphemeOrNum:[5,40],ONLYONE:'onlyaccount.do'}},														
							{display:"用户地址",name:"tblUser.daddress",newline:false,validate:{ADDRESS:[0,32]}},
							{display:"用户电话",name:"tblUser.dtel",space:LG.spaceWidthB,newline:true,validate:{TELPHONE:true}},
							{display:"用户状态",name:"dstatus",type:"select",newline:false,comboboxName:"dstatus",options:{valueFieldID:"tblUser.dstatus",selectBoxHeight:60,data:LG.statusTypeData,value:1}},
							{name : "tblUser.dhasbind",type : 'hidden'},
							
							
							{name : "tblEmployee.did",type : 'hidden'},
							{display:"员工姓名",name:"tblEmployee.dname", space:LG.spaceWidthB,type : "text" , newline:true,validate:{required:true,maxlength:64},group:"员工信息",groupicon:groupicon},
							{display:"员工工号",name:"tblEmployee.demployeeno",newline:false,validate:{onlyGraphemeOrNum:[6,16], ONLYONE : "onlyoneemployee.do"}},
							{display:"所属部门",name:"tblEmployee.ddepartmentno",type:'hidden'},
							{display:"所属部门",name:"department"}
														
						],
						toJSON:JSON2.stringify
				   });
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				   this.loadForm();
				   
				   //
				   
				   
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
						deptcode=selectedCode;
					}
				}else{
					/*
					var tmp=document.getElementById(t.attr("id"));
					if(tmp.value == -1){
						t.remove();
						
						deptcode=document.getElementById($("#show > select").last().attr("id")).value;
						//deptcode=$("#show > select").last().val();
					}else{
						deptcode =tmp.value;
					}
					*/
					deptcode=selectedCode;
				}
				if(deptcode != ''){
					this.mainform[0]['tblEmployee.ddepartmentno'].value= deptcode;
				}
				//alert(this.mainform[0]['tblEmployee.ddepartmentno'].value);
				
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
				},
					loadForm : function() {
						LG.loadForm(this.mainform, {url:"loadModify.do?id=<%=id%>",preID:"tblUser\\."},function(data){
							//是否绑定手机
							var hasbind = data.dhasbind;
							var str= "<span class='lable' style='margin-left:30px;'>绑定手机 : </span>";
							if(hasbind == 0){
								str +=" &nbsp; &nbsp; &nbsp; <span id='bindstatus'>未绑定</span> ";
							}else if(hasbind == 1){
								 str +=" &nbsp; &nbsp;  <span id='bindstatus'>已绑定</span>  &nbsp;&nbsp;<button id='bindbtn' onclick = 'unbindPhone()' >解绑</button>";
							}
							str +='</span>';
							var pt = $('#show');
							pt.before(str);
						
						});
				  }
			}
			crForm.init();
			
			$(document).ready(function(){
			
					document.getElementById("tblEmployee.did").value = '${requestScope.tblEmployee.did}';
					document.getElementById("tblEmployee.dname").value = '${requestScope.tblEmployee.dname}';
					document.getElementById("tblEmployee.demployeeno").value = '${requestScope.tblEmployee.demployeeno}';
					document.getElementById("tblEmployee.ddepartmentno").value = ${requestScope.tblEmployee.ddepartmentno};
					$(document.getElementById("tblUser.dusername")).attr("disabled","true");
					$(document.getElementById("tblEmployee.demployeeno")).attr("disabled","true");
					
					
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
				var dc=document.getElementById("tblEmployee.ddepartmentno").value;
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
				if(t != null && t.length >0){
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
							var str= "<select id ='dept_"+deptcode+"' onchange ='deptchange(this)' class='l-text l-text-combobox'  style='width:90px;'>";
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
			
			
		function unbindPhone(){
			LG.showSuccess('确定解绑?',"confirm", function (confirm) {
        		if (confirm){
					var userid=$(document.getElementById('tblUser.did')).val();
					$.post(
						"updateHasbind.do",
						{"tblUser.did":userid},
						function(data,status){
							if(!data.iserror){
								LG.tip('解绑成功.');
								$("#bindstatus").html('未绑定');
								$("#bindbtn").hide();
							}
						},
						"json"
					);
				}
			});
		}
		
		</script>
			<div id ='show' style="display: none;"></div>
		<br/>
		<br/>
		<br/>
	</body>
</html>