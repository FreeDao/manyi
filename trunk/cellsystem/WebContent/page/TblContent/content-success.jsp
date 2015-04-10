<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.pkit.model.TblCategory"%>
<%@page import="com.pkit.util.SafeUtils"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String menuno=request.getAttribute("menuno")+"";
String type=request.getAttribute("type")+"";
Object objtype = request.getAttribute("content");
String content ="1";
if(objtype != null){
	content = SafeUtils.getString(objtype);
}
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
		
		<!-- 
		 -->
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		
		
	<style type="text/css">
			body{
				overflow: hidden;
				background-color: #e9eaed;
			}
			#framecenter iframe{
				width: 100%;height: 100%;
			}
		</style>
		
	</head>
	<body>
		<div id="mainbody" class="l-layout-customTree">
			<div position="left" id="mainmenu" title="栏目">
				<div id="listTree"></div>
			</div>
			<div position="center" id="framecenter">
				<iframe frameborder="0" id="con-iframe" name="contentlist"></iframe>	
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
					//$(div.l-layout-header-toggle").hide();
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
							$(".l-layout-left",this.element).css({border:"1px solid #ffffff",overflow:"auto"});
							this._onResize();
						}
					});
					$(".l-layout-collapse-left").css({border:"1px solid #ffffff"});
				},
				listTree : function() {
					var self = this;
					$(this.defaults.tree).ligerTree({ 
						nodeWidth:"130", 
						single : true,
		            	url:"<%=path%>/TblCategory/loadCategoryTree.do?content=<%=content%>",
		            	// 跟一个content 参数 表示 当前 页面是作为选择 列表供 首页管理使用 , 不需要加载 员工天地 相关的内容 , 同时需要有效的内容 
		            	onClick : self.clickTree,
		            	checkbox:false,
		            	onSuccess:function(){
				          	$("li:eq(0)>ul>li:eq(0)>.l-body",self.defaults.tree).addClass("l-selected");
		            	}
		            });
				},
				clickTree : function(node, checked) {
					if(node.data.url != undefined && node.data.url != null){
						$("#con-iframe").attr("src","<%=path%>/TblContent/"+node.data.url+"&type=<%=type%>&menuno=<%=menuno%>&content=1");
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
			$("#con-iframe").attr("src","<%=path%>/TblContent/list.do?content=<%=content%>&type=<%=type%>&categoryType="+(win.categoryType||'${requestScope.category.did}')+"&menuno=<%=menuno %>&dtype="+(win.dtype||'${requestScope.category.dtype}'));
		</script>
		
	</body>
</html>