<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.pkit.util.SafeUtils"%>
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
Object listpageObj =request.getAttribute("listpage");
int listpage=1;
if(listpageObj != null){
	listpage = SafeUtils.getInt(listpageObj);
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
		 
		 var listpage =<%=listpage%>; //记录 列表的当前 页面数
		 //alert(listpage);
		 
			/*==========*/
			var crGrid = {
				main : "#maingrid",
				formsearch : "#formsearch",
				init : function() {
				  	var self = this;
					this.grid = jQuery(self.main).ligerGrid({ 
			         	columns: [
							{display:"上传人",name:"dphoneUsername",width:"30%"},
							{display:"类型",name:"dtype",width:"15%",render: LG.phoneType},
							{display:"上传时间",name:"duploadtime",width:"35%"},
							{display:"状态",name:"dstatus",width:"20%",render: LG.check}
					   ],
					   enabledEdit:false,
			           dataAction: 'server', 
			           pageSize: 15, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[20,50,100],
			           newPage : <%=listpage%>, 
			           url:  'loadPoneList.do?categoryType=<%=categoryType%>&type=<%=type%>',//+"&page="+parseInt(listpage), 
			           //sortName: 'd_id',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-13, checkbox: true
			           ,
			           onDblClickRow:function(data, rowindex, rowobj){
			           var path1='TblContent/view.do?dtype=<%=dtype%>&categoryType=<%=categoryType%>&IsView=1&menuno='
			           	+LG.getPageMenuNo() + '&id=' + data.did;
						var title="查看内容信息";
						top.framework.addTab(LG.getPageMenuNo()+'_'+data.did+"_view", title, path1);
						},
						onSuccess : function(data){
							 var $ = this,B = this.options;
							 //记录 列表的当前 页面数
							 if(!B.newPage){
							 	B.newPage =1;
							 }
							 listpage = B.newPage;
						}
			      });
			      self.search();
			      //加载 权限的 方法
			      if("<%=type%>" == ""){
			      	//type == ''  表示 不是弹出框, 加载权限
			      	LG.loadToolbar(self.grid, function(e){self.toolbar(e)},menuno,permissionValue);
			      }
				},
				toolbar : function(item) {
					this[item.id]();
				},
				search : function() {
					var dstatusData=[{"text":'全部',id:''},{"text":'未审核',id:'1'},{"text":'待上线',id:5},{"text":'已上线',id:10},{"text":'已下线',id:15}];
					var dtypeData =[{"text":'全部',id:''},{text : '图片', id :1 },{text : '视频', id :2 },{text : '文字', id : 3}];
					
					jQuery(this.formsearch).ligerForm({			 	
						labelWidth:LG.labelWidthB,
						inputWidth:LG.inputWidthF,
						space:LG.spaceWidthA,
						labelAlign: "right", 
						fields:[
						   {display:"上传人",name:"dphoneUsername",newline:true},
						   {display:"内容类型",name:"type",newline:false,comboboxName:"type1",type:"select",options:{valueFieldID:"dtype",data:dtypeData,value:dtypeData[0].id}},
						  // {display:"上传人",name:"opaterid",newline:true,type:"select",options:{valueFieldID:"query.duploadperson",url:"findOByCid.do"}},
						   {display:"内容状态",name:"status",newline:false,comboboxName:"dstatus1",type:"select",options:{valueFieldID:"dstatus",data : dstatusData,value : dstatusData[0].id}}
						]
					});
					LG.appendSearchButtons(this.formsearch, this.grid);
				},
				view : function() {
					var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return }
	                if (selecteds.length>1) { LG.tip(LG.INFO.TWO); return }
	                var path1='TblContent/view.do?dtype=<%=dtype%>&categoryType=<%=categoryType%>&IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did;
					var title="查看内容信息";
	                top.framework.addTab(LG.getPageMenuNo()+'_'+selecteds[0].did+'_view', title, path1);
				},
				verify : function() {
					var selecteds = this.grid.getSelecteds();
					if (selecteds.length == 0) {LG.tip(LG.INFO.ONE);return}
					if (selecteds.length > 1) {LG.tip(LG.INFO.TWO);return}
					if(selecteds[0].dstatus!=1){
						LG.tip('请选择未审核的内容.');
						return;
					}
					//alert(listpage);
					//审核信息
					var path1='TblContent/verify.do?dtype=<%=dtype%>&categoryType=<%=categoryType%>&IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did+"&listpage="+listpage;
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
						data:{'ids':ids,'dtype':'<%=dtype%>'},
						url:"<%=path%>/TblContent/onlineContent.do?_t="+_t,
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
						data:{'ids':ids,'dtype':'<%=dtype%>'},
						url:"<%=path%>/TblContent/offlineContent.do?_t="+_t,
						dataType:"json",
						success:function(data){
							if(!data.iserror){
								self.reload();
								var m =win.LG.showSuccess(data.message,null,1000);
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
				dtype : '<%=dtype%>',
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