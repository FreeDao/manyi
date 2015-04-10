<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.pkit.model.TblCategory"%>
   <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id=request.getAttribute("id")+"";
String menuno=request.getAttribute("menuno")+"";
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
	</head>
	<body>
		<form id="mainform" class="addEditManiForm" method="post" action="update.do" style="margin-left: 30px;">
			<input type='hidden' value="<%=menuno %>" id='MenuNo'/>
		</form> 
		<div class="l-clear"></div>
		<div class="l-clear"></div>
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
			<br />
		<br />
		<br />
		<br />
		<br />
		
		<script type="text/javascript">
			var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main);  
					var dparentidTypeData=${requestScope.parentidType};
					var groupicon = "<%=path%>/static/xcloud/css/img/icon/communication.gif";
				    this.mainform.ligerForm({ 
				        labelAlign: "right", 
				        inputWidth: LG.inputWidthB,
				        labelWidth:LG.labelWidthD,
				        fields : [
							{name:"tblVodcategory.did",type:"hidden",id:"id"},
							{display:"栏目名称",name:"tblVodcategory.dname",validate:{required:true,maxlength:64},group:"基本信息",groupicon:groupicon},
							//{display:"栏目编号",name:"tblVodcategory.dcode",validate:{required:true,rangelength:[3,10]}},
							{display:"父节点",name:"dparentid",type:"select",comboboxName:"dparentidName",options:{valueFieldID:"tblVodcategory.dparentid",data:dparentidTypeData}},
							{display:"状态",name:"dstatus",type:"select",comboboxName:"dstatusName",options:{valueFieldID:"tblVodcategory.dstatus",selectBoxHeight:60,data:LG.statusTypeData}}
						],
						toJSON:JSON2.stringify
				   });
				  this.loadForm();
				},
				save : function() {
		            LG.submitForm(this.mainform, function (data) {
		                var win = parent || window;
		                if (data.iserror) {  
		                    win.LG.showError(data.message);
		                }else {
		                    win.LG.showSuccess(LG.INFO.SIX,null,900);
	                        win.LG.closeAndReloadParent(null, LG.getPageMenuNo());
		                }
		            });
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				loadForm : function() {
					LG.loadForm(this.mainform, {
					url:"loadModify.do?id=<%=id%>",
					preID:'tblVodcategory\\.'
					},function(data){
						if(data.dparentid == 0){
							//当前 修改的是父栏目,  其中的 "父栏目"属性不允许修改
							//$("#dparentidName").attr("disabled","disabled");
							var t=$("ul:eq(1)");
							t.hide();
						}
					});
				}
			}
			crForm.init();
			$(document).ready(function(){
				LG.setFormDefaultBtn(crForm.cancel, function(){crForm.save();});
				//$("#dparentidName").attr("disabled","disabled");	
			});
		</script>
		<script src="<%=path %>/static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="<%=path %>/static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
	</body>
</html>