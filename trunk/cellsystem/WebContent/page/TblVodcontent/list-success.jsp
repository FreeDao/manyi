<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String categoryType=request.getAttribute("categoryType")+"";
String permissionValue=request.getAttribute("permissionValue")+"";
String menuno=request.getAttribute("menuno")+"";
String dtype=request.getAttribute("dtype")+""; 
Object typeObj =request.getAttribute("type");
String type="";
if(typeObj != null){
	type=typeObj+"";
}
Object contentObj =request.getAttribute("content");
String content="";
if(contentObj != null){
	content=contentObj+"";
}
	%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-common.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		<style type="text/css">
			html{
				overflow: hidden;
			}
		</style>
		<script type="text/javascript">
			var menuno=<%=menuno%>;
			var categoryType=<%=categoryType%>;
			var permissionValue=<%=permissionValue%>;
			var dtype=<%=dtype%>;
		</script>
	</head>
<body>
		<div id="mainsearch" >
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
		  //编辑后事件 (提交顺序)
        function f_onAfterEdit(e)
        {
            if (e.column.name == "dsequence")
            {
            	var data=crGrid.grid.getUpdated();
            	if(data!=undefined){
            	if(data[0]!=undefined){
	            	if(dtype==0){
	            		//是首页,  保证 (有视频存在 必须 是第一位)
	            		if(data[0].dtype==2){
	            			//修改的是视频
	            			if(e.value>1){
	            				LG.tip("首页的视频需放在第一位");
	            				e.value=1;
	            				crGrid.reload();
	            				return false;
	            			}
	            		}
	            	}
	            	var dt={"tblVodcontent.did":data[0].did,"tblVodcontent.dsequence":e.value,"updateSequence":"1"};
		            	$.ajax({
							async:false,
							data:dt,
							url:"<%=path%>/TblVodcontent/update.do",
							dataType:"json",
							success:function(data){
								//alert(data);
								crGrid.reload();
							},
							error:function(data){
								crGrid.reload();
							}
						});
					}
				}
            }
        }
		 
		 
			/*==========*/
			var crGrid = {
				main : "#maingrid",
				formsearch : "#formsearch",
				init : function() {
				  	var self = this;
					this.grid = jQuery(self.main).ligerGrid({ 
			         	columns: [
							{display:"名称",name:"dname",width:"25%",align:'left'},
							//{display:"类型",name:"dtype",width:"16%",render: LG.contentType},
							//{display:"序号",name:"dsequence",width:"16%",type:'int',editor:{type:"int",isNegative:false}},
							{display:"上传时间",name:"duploadtime",width : "32%"},
							{display:"上传人",name:"duploadperson",width:"22%"},
							{display:"状态",name:"dstatus",width:"21%",render: LG.check}
					   ],
					   enabledEdit:true,
					   onBeforeSubmitEdit: f_onBeforeSubmitEdit,
					   onAfterEdit:f_onAfterEdit,
			           dataAction: 'server', 
			           pageSize: 15,
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[15,50,100],
			           url:  'loadList.do?categoryType=<%=categoryType%>&type=<%=type%>&content=<%=content%>', 
			           //sortName: 'd_id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: true
			          ,
			           onDblClickRow:function(data, rowindex, rowobj){
			           var path1='TblVodcontent/view.do?dtype=<%=dtype%>&categoryType=<%=categoryType%>&IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + data.did;
						var title="查看内容信息";
						top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+"_view", title, path1);
						}
			      });
			      self.search();
			      //加载 权限的 方法
			      if("<%=type%>" == ""){
			      	//type == ''  表示 不是弹出框, 加载权限
			      	LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
			      }else{
			      	LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,0);
			      }
				},
				toolbar : function(item) {
					this[item.id]();
				},
				search : function() {
					var dtypeData=[{"text":'全部',id:''},{"text":'图文',id:'1'},{"text":"视频","id":2}];
					var dstatusData=[{"text":'全部',id:''},{"text":'未审核',id:'1'},{"text":'待上线',id:5},{"text":'已上线',id:10},{"text":'已下线',id:15}];
					jQuery(this.formsearch).ligerForm({
						labelWidth:LG.labelWidthB,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthA,
						labelAlign: "right", 
						fields:[
						   {display:"内容名称",name:"query.dname",newline:true},
						  // {display:"内容类型",name:"type",newline:false,type:"select",options:{valueFieldID:"query.dtype",data:dtypeData,value:dtypeData[0].id}},
						  // {display:"上传人",name:"opaterid",newline:true,type:"select",options:{valueFieldID:"query.duploadperson",url:"findOByCid.do"}},
						   {display:"内容状态",name:"status",newline:false,type:"select",options:{valueFieldID:"query.dstatus",data:dstatusData,value:dstatusData[0].id}}
						]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				add : function() {
				
	
						
					//是父栏目的 栏目 不可以 添加内容
					var parentid='';
					var pars ={"query.did":<%=categoryType%>};
					//category 的 parentid 为0 的表示的是父栏目
					$.ajax({
							async:false,
							data: pars,
							url:"<%=path%>/TblVodcategory/getData.do",
							dataType:"json",
							success:function(data){
								if(data!=undefined){
									if(data.rows[0]!=undefined){
										parentid=data.rows[0].dparentid;
										//alert("parentid:"+parentid+",dname:"+data.rows[0].dname);
									}
								}
							},
							error:function(data){
								alert(data);
							}
						});
					
						if(parentid==0){
							//属于父栏目
							LG.tip("父栏目不可绑定内容");
							return ;
						}
					
					
					var path1='TblVodcontent/add.do?dtype=<%=dtype%>&categoryType=<%=categoryType%>&menuno='+LG.getPageMenuNo();
					var title="新增内容信息";
					parent.contentMenuNo=parent.contentMenuNo+1;
					top.framework.addTab(LG.getPageMenuNo()+'_'+parent.contentMenuNo, title, path1);
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                var path1='TblVodcontent/view.do?dtype=<%=dtype%>&categoryType=<%=categoryType%>&IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did;
					var title="查看内容信息";
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view', title, path1);
				},
				modify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
					
					if(selecteds[0].dstatus ==10 || selecteds[0].dstatus ==15){
						LG.tip('上线/下线的内容不可编辑');
						return ;
					}
	                var path1='TblVodcontent/modify.do?dtype=<%=dtype%>&categoryType=<%=categoryType%>&IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did;
					var title="修改内容信息";
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_modify', title, path1+"&_t="+(new Date()));
				},
				verify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
					if(selecteds[0].dstatus!=1){
						LG.tip('请选择未审核的内容.');
						return;
					}
					//审核信息
					var path1='TblVodcontent/verify.do?dtype=<%=dtype%>&categoryType=<%=categoryType%>&IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did;
					var title="审核内容信息";
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_verify', title, path1);
				},
				online:function(){
					var self= this;
					var selecteds = this.grid.getSelecteds();
					var win = parent || window;
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					var ids='';
					for(var i = 0 ; i < selecteds.length ; i++){
						if(selecteds[i].dstatus!=5 && selecteds[i].dstatus!=15){
							LG.tip('请选择待上线/已下线的内容.');
							return;
						}else{
							ids+=selecteds[i].did+",";
						}
					}
					var _t=(new Date())+"";
					$.ajax({
						async:false,
						data:{'ids':ids},
						url:"<%=path%>/TblVodcontent/onlineContent.do?_t="+_t,
						dataType:"json",
						success:function(data){
							if(!data.iserror){
								self.reload();
								var m =win.LG.showSuccess(data.message,null,1000);
								 m._setWidth(380);
							}else{
								LG.tip(data.message);
							}
						},
						error:function(data){
								LG.tip(data.message);
						}
					});
				
				},
				offline:function(){
					var self= this;
					var selecteds = this.grid.getSelecteds();
					var win = parent || window;
					var _t=(new Date())+"";
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					var ids='';
					for(var i = 0 ; i < selecteds.length ; i++){
						if(selecteds[i].dstatus!=10){
							LG.tip('请选择已上线的内容.');
							return;
						}else{
							ids+=selecteds[i].did+",";
						}
					}
					$.ajax({
						async:false,
						data:{'ids':ids},
						url:"<%=path%>/TblVodcontent/offlineContent.do?_t="+_t,
						dataType:"json",
						success:function(data){
							if(!data.iserror){
								self.reload();
								var m =win.LG.showSuccess(data.message,null,1800);
								 m._setWidth(380);
							}else{
								win.LG.showError(data.message);
							}
						},
						error:function(data){
								win.LG.showError(data.message);
						}
					});
					
				},
				del : function() {
					var selecteds = this.grid.getSelecteds();
					if(selecteds.length==0){LG.tip("请选择操作项!"); reutrn ;}
					for(var  i =0;i< selecteds.length; i++){
						if(selecteds[i].dstatus==5||selecteds[i].dstatus==10){
							LG.tip("待上线和已上线状态的内容不能删除!");
							this.grid.loadData();
							return ;
						}
					}
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