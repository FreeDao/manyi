<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="../../../css/ligerui-common.css" rel="stylesheet" type="text/css" />
		<link href="../../../css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<link href="../../../css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="../../../css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
		<script src="../../../scripts/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../../../scripts/ligerui.min.js" type="text/javascript"></script>
		<script src="../../../scripts/common.js" type="text/javascript"></script>
		<script type="text/javascript">
				var menuno=<%=request.getAttribute("menuno")%>+"";
				var permissionValue=<%=request.getAttribute("permissionValue")%>;
				permissionValue= 63;
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
							{display:"名称",width:"50%",name:"cityName",align:'left'},
							{display:"编号",width:"50%",name:"cityNo"}							
					   ],
			           dataAction: 'server', 
			           pageSize: 15, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           url:  'list.rest', 
			           //sortName: 'd_id',sortOrder:'desc',
			           width: '98%', height: '100%',
			           heightDiff:-13, checkbox: true,
			           onDblClickRow:function(data, rowindex, rowobj){
							//top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view', '查看CDN信息', '/city/view.do?id='+data.cityName);
			        	   window.location.href='<%=path%>/city/view.do?id=' + selecteds[0].cityNo;
						}
			      });
			     self.search();
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
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
						   {display:"名称",name:"query.cityName",newline:true}
						]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
					//top.framework.addTab(LG.getChildMenuNo(), '新增CDN信息', '<%=path%>/TblCdninfo/add.do?menuno='+LG.getPageMenuNo());
					window.location.href='<%=path%>/city/add.do';
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                //top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view', '查看CDN信息', '<%=path%>/TblCdninfo/view.do?menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did);
	                window.location.href='<%=path%>/city/view.do?id=' + selecteds[0].cityNo;
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
	                //top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_modify', '修改CDN信息', '<%=path%>/TblCdninfo/modify.do?id=' + selecteds[0].did+"&menuno="+LG.getPageMenuNo());
					  window.location.href='<%=path%>/city/modify.do?id=' + selecteds[0].cityNo;
				},
				off:function(){
					LG.changeStatus(this,"off.do");
				},
				on:function(){
					LG.changeStatus(this,"on.do");
				},
				del : function() {
					LG.deleteData(this,"cityNo");
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crGrid.init();
		</script>
	</body>
</html>