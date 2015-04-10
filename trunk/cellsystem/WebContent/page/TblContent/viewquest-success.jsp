<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String categoryType=request.getAttribute("categoryType")+"";
String menuno=request.getAttribute("menuno")+"";
String dtype=request.getAttribute("dtype")+"";
String id=request.getAttribute("id")+"";
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
		<script src="<%=path%>/static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/static/xcloud/js/jquery-jtemplates.js"></script>
		
			<style type="text/css">
			dl{padding-top:1px;}
		</style>
	</head>
		
		
		<body>
		<div style="margin-left: 30px;" class="detail-middle l-form">
		
		<div class="l-group l-group-hasicon"><img src="<%=path%>/static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span> >> <s:property value='#attr.categoryName'/> </div>
		
			<table id="detail" class="detail-tableSkin detail-tableSkinA">
				<tr>
					<th>问卷名称：</th>
					<td class='dname' colspan='3'></td>
				</tr>
				<tr>
					<th>结果公布方式：</th>
					<td>公布方式	</td>
					<th>类型:</th>
					<td>问卷调查 </td>
				</tr>
				
				<tr>
					<th>激活时间：</th>
					<td class='dstarttime'>
					</td>
					<th>截止时间：</th>
					<td class='dstoptime'>
					</td>
				</tr>
			</table>
			<div class="br"></div>
			<div class="l-custom-subjectList-title">问卷列表</div>
			<div class="br"></div>
			<div id="listcontent">
				
			</div>
		
			<br />
			<br />
			<br />
		
		
		</div>	
		<br />
		<br />
		<br />
		

<textarea  id="listtemplate2" style="display: none;">
	<div id="nexist-list{$T.nexistNo}"  class='nexistdiv'>
	<div class="l-custom-subjectList">
				<div class="l-custom-subjectList-top"></div>
					<dl style='overflow: hidden;'>
						<dt style='min-height: 110px;'>
							第{$T.nexistNo}题
						</dt>
						<dd>
							<table>
								<tr>
									<th>题目：</th>
									<td><strong>{$T.data.dtitle}</strong></td>
								</tr>
								<tr>
									<th>类型：</th>
									<td>单选</td>
								</tr>
								<tr>
									<th valign="top">答案：</th>
									<td>
										{#foreach $T.data.dcontent as row}  
											<div class="num">{$T.row}</div>
										{#/for}  
										
									</td>
								</tr>
							</table>
						</dd>
					</dl>
				<div class="l-custom-subjectList-bot"></div>
			</div>
			<div class="br"></div>
	</div>
</textarea>


<br/>
<br/>
<br/>

<script type='text/javascript'>
		var crFormshow = {
				main : "#detail",
				init : function() {
					var detail = $(this.main);
					this.loadDetail(detail);
					 LG.setFormDefaultBtn(this.cancel,null);
				},
				loadDetail : function(d) {
					var self = this;
					LG.loadDetail(d, {url : "loadquestView.do?id=<%=id%>"}, self.callback);
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				callback : function(data) {
					data = data|| {};
					for ( var p in data) {
						var ele = $("[class=" + p + "]", detail);
						if (ele.length > 0) {
							ele.html(data[p]);
							//LG.viewSelect(selectdata,ele,data,p);
						}
					}
				}
			}
			crFormshow.init();
			
			
			var nexistNo=1,showNo=1;
			$(document).ready(function(){
				$.ajax({
					async:false,
					type:'post',
					url:'<%=path%>/TblContent/loadQuestionErcises.do',
					data:{"id":<%=id%>},
					dataType:'json',
					success:function(data){
						//alert(data);
						if(data){
							for(var i=0; i < data.total; i++){
								nexistNo=i+1;
								showNo=i+1;
								var temp=data.rows[i];
								var con=temp.dcontent;
								con=con.split("#*");
								var profile={nexistNo:nexistNo,showNo:showNo,data:{did:temp.did,dtpye:temp.dtype,dcontent:con,dtitle:temp.dtitle}};
								//调用方法 
								var txt=$("#listcontent").html();
								$("#listcontent").setTemplateElement("listtemplate2"); 
								$("#listcontent").processTemplate(profile);
								$("#listcontent").html(txt+$("#listcontent").html());
							}
							renderInput();
						}
					},
					error:function(data){
						alert(data.requestText);
					}
				});
				
			});
			
			
			/*渲染ligerUI控件*/
			function renderInput(){
				$(".AA").ligerTextBox({width:LG.inputWidthB});
				$(".BB").ligerTextBox({width:LG.inputWidthA});
				$(".CC,.DD").ligerDateEditor({width:LG.inputWidthA});
				$(".FF").ligerComboBox({width:LG.inputWidthA});
				$("input:radio").ligerRadio(); 
			}
			
		</script>
		
	</body>
</html>