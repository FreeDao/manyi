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
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerForm.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerRadioList.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/static/xcloud/js/jquery-jtemplates.js"></script>
		
		<style type="text/css">
			dl{padding-top:1px;}
		</style>
	</head>
	
	<body>
		<form id="mainform" method="post" action="<%=path %>/TblQuestionexam/save.do" style="margin-left: 30px;" class="detail-middle l-form" >
		<input type='hidden' name ='categoryType' value="<%=categoryType %>"/>
		<input type='hidden' id ='MenuNo' value="<%=menuno %>" name='menuno'/>
		
			<div class="l-group l-group-hasicon"><img src="<%=path%>/static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span> >> <s:property value="#attr.categoryName"/> </div>
			<table id="detail" class="detail-tableSkin detail-tableSkinB">
				<tr>
					<th>考试名称：</th>
					<td class='dname'>
					</td>
					<th>时长(分)：</th>
					<td class='dduration'>
					</td>
				</tr>
				<tr>
					<th>激活时间：</th>
					<td class='dstarttime'>
					</td>
					<th>截止时间：</th>
					<td class='dstoptime'>
					</td>
				</tr>
					<tr>
					<th>通过分数：</th>
					<td class='dpassvalue'>
					</td>
					<th>关联的培训：</th>
					<td>
					<%
						Object categoryname=request.getAttribute("categoryname");
						Object contentname=request.getAttribute("contentname");
						if(categoryname!=null){
					%>
						<%=categoryname %> >> <%=contentname %>
					<%}else{ %>
						暂无关联
					<%} %>
					</td>
				</tr>
				<!-- 
				<tr>
					<th>关联培训内容：</th>
					<td colspan="3" id="relevance">
						<a href="javascript:void(0);" onclick="relevance();" id='groombtn' style="margin-right:40px;">关联培训</a>
						<input type="hidden" id="groomid"/>
						<span id="groomshow"></span>
					</td>
				</tr>
				 -->
				<tr>
					<th>考前必读：</th>
					<td colspan="3">
							<p class='dread'>
							</p>
					</td>
				</tr>
			</table>
				<div class="br"></div>
			<div class="l-custom-subjectList-title">考题列表</div>
			<div class="br"></div>
			<div id="listcontent">
				
			</div>
		
			<br />
			<br />
			<br />
		
		</form> 
			<br />
			<br />
			<br />


<textarea  id="listtemplate2" style="display: none;">
	<div id="nexist-list{$T.nexistNo}"  class='nexistdiv'>
	<div class="l-custom-subjectList" >
				<div class="l-custom-subjectList-top"></div>
					<dl  style='overflow: hidden;'>
						<dt>
						<form action="save.do" method="post" width="95%">
							<input type='hidden' id = 'id' value="{$T.data.did}"/>
							 <input type='hidden' id="nexistNo" value="{$T.nexistNo}"/>
						</form>
							<strong>No <span id='showNo'>{$T.showNo}</span>.</strong><br />
                  		   <br />
						</dt>
						<dd>
							<table>
								<tr>
									<th >题目：</th>
									<td width="230"><strong><div class="title">{$T.data.dtitle}</div></strong></td>
									<th>分数：</th>
                            		<td  width="100"><strong>{$T.data.dvalue}</strong></td>
                            		<th>正确答案：</th>
                            		<td  width="100"><strong>{$T.data.drightanswer}</strong></td>
								</tr>
								<tr>
									<th>类型：</th>
									<td colspan="3">单选</td>
								</tr>
								<tr>
									<th valign="top">答案：</th>
									<td colspan="5">
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
	<br/>
</textarea>
			
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
					LG.loadDetail(d, {url : "loadexistView.do?id=<%=id%>"}, self.callback);
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
					url:'<%=path%>/TblContent/loadExistErcises.do',
					data:{"id":'<%=id%>'},
					dataType:'json',
					success:function(data){
						if(data){
							for(var i=0; i < data.total; i++){
								nexistNo=i+1;
								showNo=i+1;
								var temp=data.rows[i];
								var con=temp.dcontent;
								con=con.split("#*");
								var profile={nexistNo:nexistNo,showNo:showNo,data:{did:temp.did,dtpye:temp.dtype,dcontent:con,dtitle:temp.dtitle,drightanswer:temp.drightanswer,dvalue:temp.dvalue}};
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
				$(".BB").ligerTextBox({width:180});
				$(".CC,.DD").ligerDateEditor({width:LG.inputWidthA});
				$(".FF").ligerComboBox({width:180});
				$("input:radio").ligerRadio();  
			}
			
	
		</script>
		
	</body>
</html>