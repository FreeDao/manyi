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
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-common.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		<script type="text/javascript">
			var menuno=<%=request.getAttribute("menuno")%>+"";
			var permissionValue=<%=request.getAttribute("permissionValue")%>;
		</script>
		<style type="text/css">
			body,html{
				overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div id="mainsearch">
			<div class="searchbox onlySearchBox">
				<form id="formsearch" class="l-form"></form>
			</div>
		</div>
		
		<div id="maingrid"></div>
		<script type="text/javascript">
		
		/*
		限制 序号的 范围 大于0
		*/
		function f_onBeforeSubmitEdit(e){
			if(e.column.name == 'dsequence'){
		 		if(e.value<0){
		 			e.value=1;
		 			return false;
		 		}
		 		return true;
		 	}
		}
		
		/*
			限制 序号的 范围 大于0
		*/
		 function f_onBeforeEdit(e){
		 	if(e.column.name == 'dsequence'){
		 		if(e.value<0){
		 			e.value=1;
		 			return false;
		 		}
		 		return true;
		 	}
		 }
		  //编辑后事件 (提交顺序)
        function f_onAfterEdit(e)
        {
            if (e.column.name == "dsequence")
            {
            	var data=crGrid.grid.getUpdated();
            	if(data!=undefined){
            	if(data[0]!=undefined){
	            	var dt={"tblCategory.did":e.record.did,"tblCategory.dsequence":e.value,"updateSequence":"1"};
		            	$.ajax({
							async:false,
							data:dt,
							url:"<%=path%>/TblCategory/update.do",
							dataType:"json",
							success:function(data){
								//alert(data);
								//crGrid.reload();
							},
							error:function(data){
								//crGrid.reload();
							}
						});
					}
				}
            }
        }
		 
		 
		
			var crGrid = {
				main : "#maingrid",
				formsearch : "#formsearch",
				dtypeData:function(data){
					var dtype=[{text:'问卷调查',id:1},{text:'培训考试',id:5},{text:'培训内容',id:15},{text:'基础内容',id:10},{text:'手机上传内容',id:20},{text:'企业党建',id:25}];
					for(var i =0 ; i<dtype.length ; i++){
						if(dtype[i].id == data.dtype){
							return dtype[i].text;
						}	
					}
				},
				init : function() {
				  	var self = this;
					this.grid = jQuery(self.main).ligerGrid({
			         	columns: [
							{display:"栏目名称",width:"35%",name:"dname",align:'left'},
							{display:"栏目类型",width:"35%",name:"dtype",render:self.dtypeData},
							//{display:"序号",name:"dsequence",width:"20%",type:'int',editor:{type:"int"}},
							//{display:"栏目编号",width:"20%",name:"dcode"},
							//{display:"上线时间",width:"20%",name:"duploaddate",render:self.YMDDate},
							{display:"状态",width:"30%",name:"dstatus",render:LG.status}
					   ],
					    enabledEdit:false,
					   onBeforeSubmitEdit:f_onBeforeSubmitEdit,
					  // onBeforeEdit: f_onBeforeEdit,
					   onAfterEdit:f_onAfterEdit,
					   autoCheckChildren:false, 
			           dataAction: 'server', 
			           pageSize: 50, 
			           toolbar: {},
			           usePager: false,
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           url:'getData.do?categoryList=1&_t='+(+(new Date())),
			           //sortName: 'D_SEQUENCE',sortOrder:'asc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: true,
			           tree: { columnName: 'dname'},
			           dragFlag:false,
			           onRowDragFun:function(rows, parentRow, nearRow,oldData){
		           		    if (rows.length == 0) { LG.tip(LG.INFO.ONE); return false;}
                			if (rows.length>1) { LG.tip(LG.INFO.TWO); crGrid.reload(); return false;}
			           		//rows :拖拽的行; parentRow:拖拽后的父级菜单; nearRow:下一个节点;原来的数据
			           		//1. 移动的是 一级栏目
			           		if(nearRow == null){
			           			return false;
			           		}
			           		if(parentRow == null && nearRow == null){
			           			return false;
			           		}
			           		var row= rows[0];
			           		if(row.dtype == 0){
			           			 LG.tip('首页不可移动'); return false;
			           		}
			           		if(nearRow != null && nearRow.dtype == 0){
			           			LG.tip('首页必须放在第一位'); return false;
			           		}
			           		if(row.dparentid ==0 && parentRow!= null && parentRow.dparentid == 0){
			           			LG.tip('父栏目不能属于其他栏目');
			           			return false;
			           		}
			           		if(row.dparentid != 0 && parentRow == null){
			           			LG.tip('不能跨出父栏目修改栏目顺序');
			           			return false;
			           		}
			           		if(row.dparentid != 0 && parentRow != null && row.dparentid != parentRow.did){
			           			LG.tip('不能跨父栏目修改栏目顺序');
			           			return false;
			           		}
			           		self.dragFlag=true;
			           		return true;
			           },
			           onRowDragDrop:function(e){
			           	 if(self.dragFlag){
			           		var rows=crGrid.grid.getData();
			           		var ids = "";
			           		for(var i =0 ; i<rows.length ; i++){
			           			if(e.parent != null){
			           				if(e.parent.did == rows[i].dparentid){
			           					//alert(rows[i].dname);
			           					ids+=rows[i].did+",";
			           				}
		           				}else{
		           					if(rows[i].dparentid ==0 ){
		           						//alert(rows[i].dname);
		           						ids+=rows[i].did+",";
		           					}
		           				}
			           		}
			           		$.post(
			           			"update.do",
			           			{"updateSequence":1,"ids":ids},
			           			function(data){
			           				crGrid.grid.loadData();
			           			},
			           			"json"
			           		);
			           		return true;
			           	}
			           },
			           rowDraggable:true,
				       onDblClickRow:function(data, rowindex, rowobj){
							top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+'_view','查看栏目信息', 'TblCategory/view.do?id='+data.did);
						}
			      });
			      //self.search();
			      LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
				},
				toolbar : function(item) {
					this[item.id]();
				},
				YMDDate:function(data){
					if(data.duploaddate.indexOf(" ")>=0){
						return   data.duploaddate.substr(0,data.duploaddate.indexOf(" "));
					}else{
						var A = new Date(data.duploaddate);
      					 return A.getFullYear() + "-" + (A.getMonth() + 1) + "-" + A.getDay();
					}
				},
				DownDate:function(data){
					if(data.dupdowndate.indexOf(" ")>=0){
						return   data.dupdowndate.substr(0,data.dupdowndate.indexOf(" "));
					}else{
						var A = new Date(data.dupdowndate);
      					 return A.getFullYear() + "-" + (A.getMonth() + 1) + "-" + A.getDay();
					}
				},
				search : function() {
					var dtypeData=[{text:'问卷调查',id:1},{text:'培训考试',id:5},{text:'培训内容',id:15},{text:'基础内容',id:10},{text:'手机上传内容',id:20},{text:'企业党建',id:25}];
					var dstatusData=[{"text":'全部',id:''},{"text":'启用',id:'1'},{"text":'停用',id:'0'}];
					jQuery(this.formsearch).ligerForm({
						labelWidth:LG.labelWidthB,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthA,
						labelAlign: "right", 
						fields:[
						   {display:"栏目名称",name:"query.dname",newline:true},
						   {display:"栏目类型",name:"dtype",newline:false,type:"select",options:{valueFieldID:"query.dtype",data:dtypeData,value:dtypeData[0].id}},
						   {display:"状态",name:"dstatus",newline:false,labelWidth:LG.labelWidthA,type:"select",options:{valueFieldID:"query.dstatus",data:dstatusData,value:dstatusData[0].id}}
						]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
					top.framework.addTab(LG.getChildMenuNo(), '新增栏目信息', 'TblCategory/add.do?menuno='+LG.getPageMenuNo());
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view','查看栏目信息', 'TblCategory/view.do?IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did);
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_modify','修改栏目信息', 'TblCategory/modify.do?IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did);
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