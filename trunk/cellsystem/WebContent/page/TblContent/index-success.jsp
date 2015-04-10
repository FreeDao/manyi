<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.pkit.model.TblCategory"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String menuno=request.getAttribute("menuno")+"";
Object obj=request.getSession().getAttribute("selectCategory");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-layout.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-tab.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
	<style type="text/css">
			body,html{
				overflow: hidden;
				background-color: #e9eaed;
			}
			#framecenter iframe{
				width: 100%;height: 100%;
			}
		</style>
		<script type="text/javascript">
			var contentMenuNo=0;
		</script>
	</head>
	<body>
		<div id="mainbody" class="l-layout-customTree">
			<div position="left" id="mainmenu" title="栏目">
				<div id="listTree"></div>
			</div>
			<div position="center" id="framecenter">
				<iframe frameborder="0" id="con-iframe" ></iframe>	
			</div>
		</div>
		<script type="text/javascript">
		
			function Layout() {
			 	Layout.layout;
				if (this.init)
					this.init();
			}
			Layout.prototype = {
				init : function(options) {
					this.defaults = {
						tree : "#listTree",
						main : "#mainbody", 
						menu : "#mainmenu"
					};
					var self = this;
					jQuery.extend(this.defaults, options || {});
					self.listTree();
					Layout.layout = $(this.defaults.main).ligerLayout( {
						space : 0,
						height : '100%',
						leftWidth : 160,
						onHeightChanged : self.atHeight,
						minLeftWidth : 130,
						onRendered : function(){
							$(".l-layout-left",this.element).css({border:"1px solid #ffffff",overflow:'auto'});
							
							this._onResize();
						}
					});
					$(".l-layout-collapse-left").css({border:"1px solid #ffffff"});
				},
				listTree : function() {
					var self = this;
					var _t=(new Date())+"";
					var indexTree=$(this.defaults.tree).ligerTree({ 
						nodeWidth:"130", 
						single : true,
		            	url:"<%=path%>/TblCategory/loadCategoryTree.do?_t="+_t,
		            	onClick : self.clickTree,
		            	checkbox:false,
		            	onSuccess:function(){
				          	$("li:eq(0)>ul>li:eq(0)>.l-body",self.defaults.tree).addClass("l-selected");
		            	}
		            });
				},
				clickTree : function(node, checked) {
					if(node.data.url != undefined && node.data.url != null){
						$("#con-iframe").attr("src",node.data.url+"&menuno=<%=menuno%>");
					}
				},
				atHeight : function(options) {
					if (Layout.tab)
						Layout.tab.addHeight(options.diff);
					if (Layout.accordion && options.middleHeight - 33 > 0)
						Layout.accordion.setHeight(options.middleHeight - 33);
				},
				addTab : function(tabid, text, url) {
					if (!window.tab)
						return;
						window.tab.addTabItem( {
						tabid : tabid,
						text : text,
						url : url
					});
				}	
			}
			new Layout();
			
			var win = parent || window;
			//win.categoryType=3;
			//win.dtype=10;
			win.loadlist = function(){
				var listpage = win.listpage==null?'1':win.listpage;
				var y=LG.getPageMenuNo();
				var _t=(new Date())+"";
				$("#con-iframe").attr("src","list.do?_t="+_t+"&listpage="+listpage+"&categoryType="+(win.categoryType||'${requestScope.category.did}')+"&menuno=<%=menuno %>&dtype="+(win.dtype||'${requestScope.category.dtype}'));
			} ;
			win.loadlist();
		</script>
	
	<input  id='MenuNo'  type="hidden""/>	
	</body>
	
</html>