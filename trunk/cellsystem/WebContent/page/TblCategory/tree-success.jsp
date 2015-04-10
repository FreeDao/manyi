<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.pkit.model.TblCategory"%>
<%@page import="com.pkit.util.SafeUtils"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String menuno=request.getAttribute("menuno")+"";
Object contentobj=request.getAttribute("content");
String content="1";
if(contentobj != null){
	content = SafeUtils.getString(contentobj);
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
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
	<style type="text/css">
			body{
				overflow-x: hidden;
				background-color: #e9eaed;
			}
			#framecenter iframe{
				width: 100%;height: 100%;
			}
			.l-tree>li>.l-body>.l-checkbox{
				/*display: none;*/
			}
		</style>
		
	</head>
	<body>
		<div id="listTree"></div>
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
							$(".l-layout-left",this.element).css({border:"1px solid #ffffff"});
							this._onResize();
						}
					});
					$(".l-layout-collapse-left").css({border:"1px solid #ffffff"});
				},
				listTree : function() {
					var self = this;
					window.tree = $(this.defaults.tree).ligerTree({ 
						nodeWidth:"130", 
						single : true,
		            	url:"<%=path%>/TblCategory/loadCategoryTree.do?content=<%=content%>&_t="+(+(new Date())),
		            	onAfterAppend:function(){
		            		$(".l-checkbox","li[url*='dtype=20&']").show();
		            	}
		            	
		            });
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
			var lay=new Layout();
			
		</script>
		
	</body>
</html>