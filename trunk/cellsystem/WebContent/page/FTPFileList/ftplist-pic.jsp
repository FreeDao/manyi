<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.pkit.model.FtpUser"%>
<%@page import="com.pkit.util.XCloudConfig"%>
<%@page import="com.pkit.util.SafeUtils"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
FtpUser ftpuser=(FtpUser)request.getAttribute("ftpuser");

int type=SafeUtils.getInt(request.getAttribute("type"));

String ftpIP=request.getAttribute("ftpIP")+"";
int ipLen=ftpIP.length();
	
String ftpUrl="ftp://"+ftpuser.getUserid()+":"+ftpuser.getUserpassword()+"@"+ftpIP;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<style type="text/css">
		*{
//		text-align: center;
		}
		
		
	</style>
	
	<!-- 
	<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
	 -->
		
	<script type="text/javascript">
		var ftpurl="<%=ftpUrl%>";		
		$(document).ready(function() {
			flag=true;
			if('<%=type%>' ==2 ){
				$("#showBtn").hide();
				$("#backdiv").css('margin-left','110px');
			}
			//alert($("#showBtn").css("display"));
			changePathToNextDirectory(ftpurl);
			if('<%=type%>'=='2')
			{
				flag=false;
				$(this).parent().removeClass("menu-btn-focus");
				$(".ftpCon").addClass("smallImg");
				$(".title").css("text-align","left");
			}
		});
		
		 String.prototype.endWith=function(s){
		  if(s==null||s==""||this.length==0||s.length>this.length)
		     return false;
		  if(this.substring(this.length-s.length)==s)
		     return true;
		  else
		     return false;
		  return true;
		 }
		 
		/*
			单击 文件 列表
		*/
		function select(fileName)
		{
			//var value=ftpurl+"/"+fileName;
			ftpurl=ftpurl.substring(ftpurl.indexOf("<%=ftpIP%>")+<%=ipLen%>+1,ftpurl.length);
			if(!ftpurl.endWith("/")){
				ftpurl+="/";
			}
			var value="http://<%=ftpIP%>/<%=ftpuser.getHomedirectory()%>/"+ftpurl+fileName;
			//parent.submitContents(value);
			//parent.crForm.ftpwin.hide();
			
			submitContents(value);
			crGrid.ftpwin.hide();
			$("#result").html("");
		}
		
	
		/*
			双击 文件夹
		*/
		function doubleSelect(fileName){
			ftpurl=ftpurl+"/"+fileName;
			changePathToNextDirectory(ftpurl);
			$("#backtoparent").attr("disabled",false);
		}
		
		function backtoparent(){
			var ftpurl_cruuent="<%=ftpUrl%>";
			if(ftpurl==ftpurl_cruuent){
				return;
			}else{
				var index=ftpurl.lastIndexOf("/");
				ftpurl=ftpurl.substring(0,index);
				if(ftpurl==ftpurl_cruuent){
					$("#backtoparent").attr("disabled",true);
				}
				changePathToNextDirectory(ftpurl);
			}			
		}
		
		function changePathToNextDirectory(ftpurl){
			var params={ftpurl:ftpurl,type:<%=type%>};
			$("#ftpurl_span").empty();
			$("#ftpurl_span").html(ftpurl);
	  		$.ajax({
				url:'<%=path%>/FTPFileList/findFtpFile.do',  //后台处理程序
				cache:false,
				type: "POST",
				async:false,
				contentType: "application/x-www-form-urlencoded",
				data:jQuery.param(params),  //要传递的数据	
				error : function(){
				},
				success : function(data){
				
					/*
					var html="<table width='100%' style='z-index:-1'>";
				  	for(var i=0;i<data.length;i++){
						html+="<tr onmouseover='this.bgColor=\"#fdeccf\";' onmouseout='this.bgColor=\"\";'>";
						if(data[i][0]=="0"){
							html+="<td><img src='<%=path%>/static/xcloud/images/file.gif'></td><td width='95%' align='left' onclick='select(this.innerHTML);'>";
						}else{
							html+="<td><img src='<%=path%>/static/xcloud/images/folder.gif'></td><td width='95%' align='left' ondblclick='doubleSelect(this.innerHTML);'>";
						}
						html+=data[i][1]+"</td></tr>";
					}
					html+="</table>";
					$("#fileList_div").html(html);
					*/
					
					var html='';
					for(var i=0;i<data.length;i++){
						if(i%4==0){
							html+='<div class="row"  style="clear: both;">';
						}
						
						if(data[i][0]=="0"){
							//文件
							html+='<div class="item" onclick="select(this.title);" title="'+data[i][1]+'"><div class="corTop"></div><div class="con">';
							//data[i][1] 文件名字
							var temp1=ftpurl.substring(ftpurl.indexOf("<%=ftpIP%>")+<%=ipLen%>+1,ftpurl.length);
							temp1 = "http://<%=ftpIP%>/<%=ftpuser.getHomedirectory()%>/"+temp1;
							html+='<div class="img"><img src="'+temp1+'/'+data[i][1]+'" /></div>';
						}else{
							//文件夹
							html+='<div class="item" ondblclick="doubleSelect(this.title);" title="'+data[i][1]+'"><div class="corTop"></div><div class="con">';
							html+='<div class="img folder"></div>';
						}
						html+='<div class="title" style="text-align: center;">'+data[i][1]+'</div></div><div class="corBot"></div></div>';
						
						if((i+1)%4==0){
							html+='</div>';
						}
					}
					
					//$(".ftpCon").html('');
					//$(".ftpCon").html(html);
					$(".ftpCon").empty();
					$(".ftpCon").append(html);
					if(flag){
						$(".title").css("text-align","center");
					}else{
						$(".title").css("text-align","left");
					}
				}
			});		
		}
	</script>
</head>

<body>
<!-- 
	<div style="text-align:center">FTP文件列表</div>
	<div >
		<table width="100%">
			<tr><td align="left">Ftp地址：<span id="ftpurl_span"></span></td>
			<td align="right"><input id="backtoparent" type="button" onclick="backtoparent();" value="返回上一级" disabled="disabled"></td>
			</tr>
		</table>
	</div>
	<div id="fileList_div" style="text-align:center; width:100%; height:300px; border:1px solid #000; overflow:scroll">
		
	</div>
 -->
	
	<div class="ftpConter" id="ftpConter">
		<div class="ftpMenu">
			<div class="f-left">FTP文件列表</div>
			<div class="f-right">
				<div class="menu-btn-return" onclick="backtoparent()" id="backdiv">返回上一级</div>
				<div class="menu-btn menu-btn-focus" id='showBtn'>
					<span class="img">图文</span>
					<span class="list">列表</span>
				</div>
			</div>
			<div class="ftpUrl"><span>Ftp地址：</span><span id='ftpurl_span'> ftp://1008:123456@192.168.2.85 </span></div>
		</div>
			
		<div class="ftpCon" id='ftpCon'>
		</div>
			
	</div>
		
		<script type="text/javascript">
		var flag=false;
			var crGrid = {
				main : "#detail",
				init : function() {
					this.linkedColumn();
					$(".menu-btn .img").click(function(){
						$(this).parent().addClass("menu-btn-focus");
						$(".ftpCon").removeClass("smallImg");
						$(".title").css("text-align","center");
						flag=true;
					})
					$(".menu-btn .list").click(function(){
						$(this).parent().removeClass("menu-btn-focus");
						$(".ftpCon").addClass("smallImg");
						$(".title").css("text-align","left");
						flag=false;
					})
				},
				ftpwin : null,
				linkedColumn : function() {
					var scrtop=document.documentElement.scrollTop;
					this.ftpwin=$.ligerDialog.open( {
						title : "",
						top : 50+scrtop,
						width : 640,
						height : 450,
						target : $("#ftpConter"),
						cls : "l-custom-openWin l-custom-ftpWin"
					});
				}
			}
			crGrid.init();
		</script>
		
		
</body>
</html>