<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String menuno=request.getAttribute("menuno")+"";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		
		
	   
	</head>
	<body>
		<form id="mainform" method="post" action="save.do" style="margin-left: 30px;" class="detail-middle l-form">
		<input type='hidden' id ='MenuNo' value="<%=menuno %>"/>
			<div class="l-group l-group-hasicon"><img src="<%=path%>/static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span> </div>
			<table id="detail" class="detail-tableSkin detail-tableSkinB">
				<tr>
					<th width="100px">版本名称：</th>
					<td colspan="3" width="800px">
						<input class="AA" name="tblapkmanager.dversionno"  validate="{'REQUERELENGTH':[2,64]}" />
					</td>
				</tr>
				<tr>
					<th width="100px">版本号：</th>
					<td colspan="3" width="800px">
						<input class="AA" name="tblapkmanager.dversioncode"  validate="{'digits':true}" />
					</td>
				</tr>
				<tr>
					<th>应用地址：</th>
					<td colspan="3" width="500px">
						<input class="DD" name="tblapkmanager.ddownloadurl" id="dplacardurl" />
						<div class="i-btn-" onclick="change1();">选择</div>
					</td>
				</tr>
				<tr>
					<th>应用描述：</th>
					<td colspan="5" width='900px'>
						<textarea id="txt"   rows="10" cols="120" name="tblapkmanager.ddescription"></textarea>
					</td>
				</tr>
			</table>
			<br />
		<br />
		<br />
		<br />
		<div class="l-clear"></div>
		<div class="l-clear"></div>
		</form> 
		<br />
		<br />
		<br />
		<br />
		<div class="l-clear"></div>
		<div class="l-clear"></div>
		
	<!-- 加载FTP文件列表的内容 -->
		<div id='result' style="display: none;"></div>
		
	<script type="text/javascript">	
		function change1(){
			crForm.add();
		}
	
			var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main);  
					$(".AA,.DD,.EE,.FF").ligerTextBox({width:LG.inputWidthB});
					$(".i-btn-").ligerButton({width:25,text:""});
					$(".i-btn-").css({"float":"left"}).prev().css({"float":"left","marginRight":5});
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				},
				save : function() {
					var url = $("#dplacardurl").val();
					if(url == '' || url == null){
						LG.tip('请选择APP所在路径');
						return ;
					}
		            LG.submitForm(this.mainform, function (data) {
		                var win = parent || window;
		                if (data.iserror) {
		                    win.LG.showError(data.message);
		                }
		                else {
		                     win.LG.showSuccess(LG.INFO.SIX,null,900);
	                         //win.LG.closeAndReloadParent(null,<%=menuno%>);
	                          win.LG.closeAndReloadParent(null, LG.getPageMenuNo());
		                }
		            });
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				ftpwin:null,
				add : function() {
					$("#result").html("");
					$("#result").load('<%=path%>/FTPFileList/ftplist.do?type=3&_t='+(+(new Date())) , function(data){
						//$("#result").html(data);
					});
				}
			}
			crForm.init();
			
			/*
				加载 选择的FTP文件的路径 
			*/
			function submitContents(value){
				$("#dplacardurl").val(value);
			}
			
		
		</script>
		
		<script src="<%=path%>/static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
	
	</body>
</html>