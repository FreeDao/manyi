<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.pkit.model.TblQuestionexercise" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="../static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<script src="../static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/common.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
	</head>
	<body class="l-form">
		<div class="l-group l-group-hasicon">
			<span><%=request.getAttribute("title")%></span>
			<label class="export">题目数量：<%=request.getAttribute("size")%></label>
			<a href="exportdata.do?id=<%=request.getAttribute("id")%>" class="export" style="float: right;"><img src="../static/xcloud/css/img/form/export.png" />导出</a>
		</div>
		<%
			Map<Long,TblQuestionexercise> resultMap=(Map)request.getAttribute("resultMap");
			Map<String,String> countMap=(Map)request.getAttribute("countMap");
			Iterator ite=resultMap.keySet().iterator();
			TblQuestionexercise q=null;
			int k =0;
			while(ite.hasNext()){
				k++;
				q=(TblQuestionexercise) resultMap.get(ite.next());%>
		<div class="l-custom-subjectDetail">
			<div class="l-custom-subjectList-top"></div>
			<table width="95%">
				<tr>
					<th width="90">题目 <%=k %>：</th>
					<td><strong><%=q.getDtitle()%></strong></td>
				</tr>
				<tr>
					<th>类型：</th>
					<%if(q.getDtype()==1) {%>
					<td>单选</td>
					<%}else{ %>
					<td>多选</td>
					<%}%>
				</tr>
				<tr>
					<th valign="top">答案：</th>
					<td>
						<%for(int i=0;i<q.getAnswers().length;i++){
						String m="bg_"+(i%4+1);
						String strNum=null;
						String str=q.getDid().toString()+q.getAnswers()[i].substring(0,1);
						strNum=countMap.get(str+"NUM");
						str=countMap.get(str);
						if(str==null||str.length()<=0){
							str="0%";
						}
						if(strNum==null||strNum.length()<=0){
							strNum="0";
						}
						%>
						<div class="num">
							<span><%=q.getAnswers()[i].substring(0,2)%></span>
								<%=q.getAnswers()[i].substring(2)%>
							<div class="score">
							 	<div class="left"><span style="width: <%=str%>" class="<%=m%>"></span></div>
							 	<div class="right"><%=str+"("+strNum+")"%></div>
							 </div>
						</div>
						<%}%>
					</td>
				</tr>
			</table>
			<div class="l-custom-subjectList-bot"></div>
		</div>
		<div class="br"></div>
			<%}
		%>
		<br/>
		<br/>
		<br/>
		<script type="text/javascript">
			var crForm = {
				main : "#mainform",
				init : function() {
				   LG.setFormDefaultBtn(this.cancel,null);
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				}
			}
			crForm.init();
			
		</script>
	</body>
</html>