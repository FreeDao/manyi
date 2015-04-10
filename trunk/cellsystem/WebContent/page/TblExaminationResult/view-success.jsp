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
			var id=<%=request.getAttribute("id")%>;
			var resultList=<%=request.getAttribute("resultList")%>;
			if(resultList==null){
				resultList={"firstPage":true,"firstResult":0,"hasNextPage":false,"hasPreviousPage":false,"lastPage":true,"lastPageNumber":1,"linkPageNumbers":[1],"nextPageNumber":2,"pageSize":1,"previousPageNumber":0,"rows":[{}],"thisPageFirstElementNumber":1,"thisPageLastElementNumber":1,"thisPageNumber":1,"total":0};				
			}
		</script>
	</head>
	<body>
		<div id="mainsearch" style="width: 98%;display: none">
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
							{display:"员工姓名",name:"dname",width:"25%"},
							{display:"工号",name:"demployeeno",width:"25%"},
							{display:"所属部门",name:"ddepartment",width:"25%"},
							{display:"考试分数",name:"dscore",width:"25%"}
					   ],
			           dataAction: 'server', 
			           pageSize: resultList.rows.length, 
			           toolbar: {},
			           usePager:false,
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[resultList.rows.length],
			           data:resultList,
			           sortName: 'dscore',sortOrder:'desc',
			           width: '100%', height: '100%',heightDiff:-13, checkbox: false
			      });
			      //self.search();
			      //LG.loadToolbar(self.grid, function(e){self.toolbar(e)},LG.getPageMenuNo());
			      this.loadToolbar(self.grid, function(e){self.toolbar(e)},LG.getPageMenuNo());// 测试用
				},
				loadToolbar : function(grid, barClick, tableCode) {
					var data = {
			            "data": [{
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
						labelWidth:LG.labelWidthB,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthA,
						labelAlign: "right", 
						fields:[
                            {display:"产品名称",name:"name",newline:true},
                            {display:"产品类型",name:"type",newline:false,type:"select",comboboxName:"typename",options:{valueFieldID:"type",data:[{text:'全部',id:'-1'},{ text: 'Package', id: '2' },{ text: 'PortalPackage', id: '3' }]}},
                            {display:"SP名称",name:"spid",newline:false,type:"select",comboboxName:"spname",options:{valueFieldID:"spid",url:"Action/Sp/MySelect.do"}}
                        ]
                    });
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				exportdata : function() {
					window.location.href="<%=path%>/TblExaminationResult/exportdata.do?id="+ id;
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crGrid.init();
		</script>
	</body>
</html>