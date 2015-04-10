<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>		
		
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/static/xcloud/js/jquery.form.js"></script>
		
		
		<style type='text/css'>
		.dhhhhd{
			visibility: visible;
		}
		
	</style>
	<script type='text/javascript'>
		function subfrm(){
			var mainform = $("#frm");
			mainform.valid =function(){
				return true;
			} 
			var txt=$("#textfield").val();
			if (!/\.(xls|xlsx)$/.test(txt.toLocaleLowerCase())){
				$(".tip").css("visibility","visible");
				$("#textfield").val("");
				return ;
			}
			var loading=$("#loading");
			loading.css("display","block");
			
			$("#frm").ajaxSubmit({
	            url: "<%=path%>/TblUser/batchSave.do",
	            dataType: "text",
	            success: function (data) {
	            	 var win = parent || window;
	            	 data = eval("("+data+")");
	            	 //alert(data.iserror);
	                if(data.iserror){
	                	LG.tip(data.message);
	                }else{
	                	win.LG.showSuccess(data.message,null,1500);
	                	//setTimeout("closeFtp",1000);
	                	closeFtp();
	                	parent.crGrid.reload();
	                }
	                loading.css("display","none");
	            },
	            error:function(response){
	            	//alert(response.responseText);
	            	loading.css("display","none");
	            }
	        });
		}
		
		function closeFtp(){
			crGrid.hiddenWin();
		}
		
	$(document).ready(function () {
	
    	$("#btnConfirm").click(function () {
    });

})

	</script>
	
	</head>
	<body> 
	
	<!-- 
		<div class="ftpConter" id="ftpConter">
			<div class="ftpMenu">
				<div class="f-left">批量导入</div>
				<div class="f-left"><a href="<%=path%>/TblUser/downLoadExample.do">样例下载</a></div>
			</div>			
			<div class="ftpCon" id='ftpCon'>
				<div class="row">
				<form id="mainform" encType="multipart/form-data" class="addEditManiForm" method="post" action="<%=path%>/TblUser/batchSave.do" style="margin-left: 30px;">
					<input id="excelFile" name="excelFile" value="1" type="file" style="width: 398px;" class="l-text-field"/>
					<input type="submit" value="上传">
				</form>
				</div>		
			</div>			
		</div>	
	 -->
		
		
		<div class="ftpConter" id="ftpConter">
			<div class="ftpMenu">
				<div class="f-left">批量导入</div>
			</div>
			
			<div class="ftpCon">
				<div class="uploadFile">
					<div class="left">*导入内容表：</div>
					<div class="right">
						<div class="file-box">
							<form enctype="multipart/form-data" method="post" action="<%=path%>/TblUser/batchSave.do" id='frm'>
								<div class="tip" style='visibility: hidden;'><span class="l-error">导入的模板格式有误！</span></div>
								<input id="textfield"   class="txt" />
								<a href="javascript:void(0);" class="btn">本地导入</a>
								<input type="file" class="file" onchange="document.getElementById('textfield').value=this.value" size="28" name="excelFile" />
							</form>
						</div>
						<div class="download">请按模板格式导入内容表  模板点击此处下载 <a href="<%=path%>/TblUser/downLoadExample.do">表格模板</a></div>
					</div>
					<div class="form-bar-inner">
						<div class="l-dialog-btn cancle">
							<div class="l-dialog-btn-l"></div>
							<div class="l-dialog-btn-r"></div>
							<div class="l-dialog-btn-inner" onclick="closeFtp()">关闭</div>
						</div>
						<div class="l-dialog-btn">
							<div class="l-dialog-btn-l"></div>
							<div class="l-dialog-btn-r"></div>
							<div class="l-dialog-btn-inner" id='btnConfirm' onclick="subfrm()">保存</div>
						</div>
					</div>

				<!-- 遮罩层 -->
				<div class="l-tab-loading"  id='loading' ></div>
				
				</div>
				
			</div>
		</div>
		
			
			<script type="text/javascript">
			var crGrid = {
				init : function() {
					this.linkedColumn();
					$(".menu-btn .img").click(function(){
						$(this).parent().addClass("menu-btn-focus");
						$(".ftpCon").removeClass("smallImg");
					})
					$(".menu-btn .list").click(function(){
						$(this).parent().removeClass("menu-btn-focus");
						$(".ftpCon").addClass("smallImg");
					})
				},
				uploadwin : null,
				linkedColumn : function() {
				this.uploadwin=$.ligerDialog.open( {
						title : "",
						top : 50,
						width : 640,
						height : 450,
						target : $("#ftpConter"),
						cls : "l-custom-openWin l-custom-ftpWin"
					});
				},
				hiddenWin:function(){
					this.uploadwin.hide();
				}
			}
			 crGrid.init();
		</script>
	</body>
</html>
