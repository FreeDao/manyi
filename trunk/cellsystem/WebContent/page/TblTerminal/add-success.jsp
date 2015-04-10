<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		<form id="mainform" class="addEditManiForm" method="post" action="save.do" style="margin-left: 30px;"></form>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<div class="l-clear"></div> 
		<script type="text/javascript">
			var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main);  
					var groupicon = "<%=path%>/static/xcloud/css/img/icon/communication.gif";
				    this.mainform.ligerForm({ 
				        labelAlign: "right", 
				        inputWidth: LG.inputWidthB,
				        labelWidth:LG.labelWidthD,
				        fields : [
				       	   {display:"名称",name:"tblTerminal.dname",validate:{REQUERELENGTH:[2,32]},group:"基本信息",groupicon:groupicon},
							{display:"应用地址",name:"tblTerminal.dappurl",validate:{REQUERELENGTH:[2,32]}},
							{display:"版本号",name:"tblTerminal.dversionnumber",validate:{REQUERELENGTH:[2,32]}},
							{display:"版本名称",name:"tblTerminal.dversionname",validate:{REQUERELENGTH:[2,32]}},
							{display:"大小",name:"tblTerminal.dsize",validate:{INT:[1,512]}},
							{display:"开发方",name:"tblTerminal.dcreate",validate:{REQUERELENGTH:[2,32]}},
							{display:"应用提供商",name:"tblTerminal.dsupplier",validate:{LENGTH1:[2,32]}},
							{display:"应用描述",name:"tblTerminal.ddesc",validate:{LENGTH1:[4,64]}},
							{display:"状态",name:"dstatus",type:"select",comboboxName:"dstatusName",options:{valueFieldID:"tblTerminal.dstatus",selectBoxHeight:60,data:LG.statusTypeData,value:LG.statusTypeData[0].id}}
							
						],
						toJSON:JSON2.stringify
				   });
				   LG.setFormDefaultBtn(self.cancel, function(){self.save();});
				   if (LG.getQueryStringByName("id")) {
						this.mainform.attr("action", "save.do");
						this.loadForm();
				   } else {
						this.mainform.attr("action", "save.do");
				   }
				},
				save : function() {
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
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				loadForm : function() {
					LG.loadForm(this.mainform, {url:"_detail.js"},function(){});
				}
			}
			crForm.init();
		</script>
		<script src="<%=path %>/static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="<%=path %>/static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<div class="l-clear"></div>
	</body>
</html>