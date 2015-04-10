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
		<link href="../static/xcloud/css/ligerui-common.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
		<script src="../static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/common.js" type="text/javascript"></script>
		<script type="text/javascript">
				var menuno=<%=request.getAttribute("menuno")%>+"";
				var permissionValue=<%=request.getAttribute("permissionValue")%>;
		</script>
	</head>
	<body>
		<div id="mainsearch">
			<div class="searchbox onlySearchBox">
				<form id="formsearch" class="l-form"></form>
			</div>
		</div>
		
		<div id="maingrid"></div>
		<script type="text/javascript">
			var crGrid = {
				main : "#maingrid",
				formsearch : "#formsearch",
				init : function() {
				  	var self = this;
					var listdata =self.loadlist();
					this.grid = jQuery(self.main).ligerGrid({ 
			         	columns: [
							{display:"企业",width:"30%",name:"dcompanyname",align:'left'}
							,{display:"月份",width:"40%",name:"dmonth",render : function(_d){
								var months = _d.dmonth.split("-");
								return months[0]+"年"+months[1]+"月";
							}}
							,{display:"总计",width:"30%",name:"dsubtotal"}
					   ],
			           dataAction: 'server', 
			           pageSize: 15, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           //url:  'getData.do',
			           data :  listdata,
			           //sortName: 'd_id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: true,
			           onDblClickRow:function(data, rowindex, rowobj){
							top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view', '查看资费统计', 'TblCharg/view.do?companycode='+data.dcompanycode+"&monthString="+data.dmonth);
						}
			      });
			      
			      //判断是否是 企业管理员登录
			      if('${sessionScope.operator.dcompanyid}' == '0'){
				    self.search();
			      }
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
				},
				loadlist : function(){
					var tmpretus ;
					$.ajax({
						async:false,
						data:{},
						url:"getData.do?_t="+(+(new Date())),
						dataType:"text",
						success:function(data){
							tmpretus =eval("("+data+")");
						},
						error:function(data){
						}
					});
					return tmpretus;
				},
				toolbar : function(item) {
					this[item.id]();
				},
				search : function() {
					jQuery(this.formsearch).ligerForm({
						labelWidth:LG.labelWidthA,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthA,
						labelAlign: "right", 
						fields:[
						   {display:"企业",name:"query.dcompanyname",newline:true}
						]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
					top.framework.addTab(LG.getChildMenuNo(), '新增资费统计', '<%=path%>/TblCharg/add.do?menuno='+LG.getPageMenuNo());
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].dcompanycode+'_view', '查看资费统计', '<%=path%>/TblCharg/view.do?menuno='+LG.getPageMenuNo() + '&companycode='+selecteds[0].dcompanycode+"&monthString="+selecteds[0].dmonth);
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_modify', '修改资费统计', '<%=path%>/TblCharg/modify.do?id=' + selecteds[0].did+"&menuno="+LG.getPageMenuNo());
				},
				off:function(){
					LG.changeStatus(this,"off.do");
				},
				on:function(){
					LG.changeStatus(this,"on.do");
				},
				del : function() {
					LG.deleteData(this);
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crGrid.init();
		</script>
	</body>
</html>