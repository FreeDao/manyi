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
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		<script type="text/javascript">
			var id=<%=request.getAttribute("id")%>+"";
			var menuno=<%=request.getAttribute("menuno")%>+"";
		</script>
			
	</head>
	<body>
		<form id="mainform" class="addEditManiForm" method="post" action="update.do" style="margin-left: 30px;"></form> 
		<script type="text/javascript">
			var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main);  
					var groupicon = "<%=path%>/static/xcloud/css/img/icon/communication.gif";
				    this.mainform.ligerForm({ 
				        labelAlign: "right", 
				        inputWidth: LG.inputWidthC,
				        labelWidth: LG.labelWidthA,
				        fields : [
				        	{name:"tblVodtype.did",type:"hidden"},
							{display:"名称",name:"tblVodtype.dname",newline:false,group:"基本信息",groupicon:groupicon,validate:{REQUERELENGTH:[1,32]}}
							//,{display:"排序",name:"tblVodtype.dsequence",newline:true,validate:{required:true,url:true}}
							,{display:"状态",name:"dstatus",newline:true,type:"select",comboboxName:"MMType",options:{valueFieldID:"tblVodtype.dstatus",selectBoxHeight:60,data:LG.statusTypeData}}
						],
						toJSON:JSON2.stringify
				   });
				   LG.setFormDefaultBtn(self.cancel, function(){self.save();});
				   if (LG.getQueryStringByName("id")) {
						this.mainform.attr("action", "update.do");
						this.loadForm();
				   } else {
						this.mainform.attr("action", "update.do");
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
					LG.loadForm(this.mainform, {url:"<%=path%>/TblVodtype/loadModify.do?id="+id,preID:'tblVodtype\\.'},function(){});
				}
			}
			crForm.init();
		</script>
		<script src="<%=path%>/static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
	</body>
</html>