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
		<link href="../../../css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="../../../css/common.css" rel="stylesheet" type="text/css" />
		<script src="../../../scripts/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../../../scripts/ligerui.min.js" type="text/javascript"></script>
		<script src="../../../scripts/json2.js" type="text/javascript"></script>
		<script src="../../../scripts/common.js" type="text/javascript"></script>
		<script type="text/javascript">
			var id=<%=request.getAttribute("id")%>+"";
			var menuno=<%=request.getAttribute("menuno")%>+"";
		</script>
			
	</head>
	<body>
		<form id="mainform" class="addEditManiForm" method="post" action="/city/update.do" style="margin-left: 30px;"></form> 
		<script type="text/javascript">
			var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main);  
					var groupicon = "../../../css/img/icon/communication.gif";
				    this.mainform.ligerForm({ 
				        labelAlign: "right", 
				        inputWidth: LG.inputWidthC,
				        labelWidth: LG.labelWidthA,
				        fields : [
							{display:"名称",name:"cityName",newline:false,group:"基本信息",groupicon:groupicon,validate:{REQUERELENGTH:[1,32]}},
							{display:"编号",name:"cityNo",newline:true,validate:{required:true}},
						],
						toJSON:JSON2.stringify
				   });
				   LG.setFormDefaultBtn(self.cancel, function(){self.save();});
				   this.loadForm();
				},
				save : function() {
					/*
		            LG.submitForm(this.mainform, function (data) {
		                var win = parent || window;
		                if (data.iserror) {  
		                    win.LG.showError(data.message);
		                }
		                else {
		                    win.LG.showSuccess(LG.INFO.SIX, null, 900);
		                       win.LG.closeAndReloadParent(null, LG.getPageMenuNo());
		                }
		            });
					*/
					this.mainform.submit();
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				loadForm : function() {
					//LG.loadForm(this.mainform, {url:"/city/view.rest?id="+id,preID:'tblCdninfo\\.'},function(){});
					LG.loadForm(this.mainform, {url:"/city/view.rest?id="+id},function(){});
				}
			}
			crForm.init();
		</script>
		<script src="../../../scripts/jquery.form.js" type="text/javascript"></script>
		<script src="../../../scripts/jquery.validate.min.js" type="text/javascript"></script>
	</body>
</html>