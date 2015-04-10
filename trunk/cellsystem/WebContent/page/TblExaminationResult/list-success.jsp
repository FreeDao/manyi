<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<link href="../static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
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
					this.grid = jQuery(self.main).ligerGrid({ 
			         	columns: [
							{display:"试卷名称",name:"dname",width:"25%",align:'left'},
							{display:"考生人数",name:"dparticipants",width:"25%"},
							{display:"通过人数",name:"dpass",width:"25%"},
							{display:"通过率(%)",name:"dpassrate",width:"25%"}
					   ],
			           dataAction: 'server', 
			           pageSize: 15, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           url:  'getData.do', 
			           //sortName: 'd_id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-3, checkbox: true,
			           onDblClickRow:function(data, rowindex, rowobj){
							top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view','查看考试统计', 'TblExaminationResult/view.do?id='+data.did);
						}
			      });
				  self.search();
				  if((permissionValue&1)==1){
				      this.loadToolbar(self.grid, function(e){self.toolbar(e)},LG.getPageMenuNo());
				  }
				},
				loadToolbar : function(grid, barClick, tableCode) {
					var data = {
			            "data": [{
			                "btnicon": "../static/xcloud/css/img/icon/jui-icon-add.png",
			                "btnid": 81,
			                "btnname": "查看",
			                "btnno": "view",
			                "menuno": "t_series"
			            }, {
			                "btnicon": "../static/xcloud/css/img/icon/jui-icon-edit.png",
			                "btnid": 82,
			                "btnname": "导出",
			                "btnno": "exportdata",
			                "menuno": "t_series"
			            }],
			            "iserror": false,
			            "message": "success"
			        }
					var items = [], data = data.data;;
					for ( var i = 0, l = data.length; i < l; i++) {
						var o = data[i];
						items[items.length] = {
							click : barClick,
							text : o.btnname,
							id : o.btnno,
			                img: o.btnicon,
							disable : true
						};
						items[items.length] = {line : true};
					}
					grid.toolbarManager.set('items', items);
				},
				toolbar : function(item) {
					this[item.id]();
				},
				search : function() {
					jQuery(this.formsearch).ligerForm({
						labelWidth:80,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthA,
						labelAlign: "right", 
						fields:[
                           {display:"试卷名称",name:"query.dname",newline:true}
                        ]
                    });
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view','查看考试统计', 'TblExaminationResult/view.do?id=' + selecteds[0].did);
	                
				},
				exportdata : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
					window.location.href="<%=path%>/TblExaminationResult/exportdata.do?id="+ selecteds[0].did;
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crGrid.init();
		</script>
	</body>
</html>