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
	</head>
	<body>
		<form id="mainform" class="addEditManiForm" method="post" action="/city/save.do" style="margin-left: 30px;"></form> 
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
				},
				save : function() {
					/*
		            LG.submitForm(this.mainform, function (data) {
		                var win = parent || window;
		                if (data.errorCode !=0 ) {  
		                    win.LG.showError(data.message);
		                }
		                else {
		                	window.location.href='<%=path%>/city/list.do';
		                }
		            });
					*/
					
					
					this.mainform.submit();
					
				},
				cancel : function(item) {
					window.location.href='<%=path%>/city/list.do';
				}
			}
			crForm.init();
		</script>
		<script src="../../../scripts/jquery.form.js" type="text/javascript"></script>
		<script src="../../../scripts/jquery.validate.min.js" type="text/javascript"></script>
	</body>
</html>