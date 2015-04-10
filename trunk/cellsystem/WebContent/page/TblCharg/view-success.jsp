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
			var id=${requestScope.companycode};
			var dmonth='${requestScope.monthString}';
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
					this.grid = jQuery(self.main).ligerGrid({
			         	columns: [
							{display:"企业",width:"20%",name:"dcompanyname",align:'left'}
							,{display:"策略名称",width:"10%",name:"dgamename"}
							,{display:"资费(元)",width:"10%",name:"dgamevalue"}
							,{display:"数量",width:"10%",name:"dnum"}
							,{display:"月份",width:"20%",name:"dmonth",render : function(_d){
								var months = _d.dmonth.split("-");
								return months[0]+"年"+months[1]+"月";
							}}
							,{display:"小计(元)",width:"10%",name:"dsubtotal"}
							,{display:"统计时间",width:"20%",name:"dstattime"}
					   ],
			           dataAction: 'server', 
			           pageSize: 15,
			           usePager : false,
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           url:  'loadView.do?query.dcompanycode='+id+"&query.dmonthString="+dmonth,
			           //sortName: 'd_id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: false
			      });
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},0,0);
				},
				toolbar : function(item) {
					this[item.id]();
				},
				reload : function() {
					this.grid.loadData();
				}
			}
				crGrid.init();
				
		</script>
		
	</body>
</html>