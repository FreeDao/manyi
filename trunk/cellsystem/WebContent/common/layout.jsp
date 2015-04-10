<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>DataGrid with Toolbar - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/css/demo.css">
	<script type="text/javascript" src="/scripts/jquery2.0.min.js"></script>
	<script type="text/javascript" src="/scripts/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>DataGrid dome</h2>
	
	<table border="1" style="width:100% ; height: 90%;" >
		<tr>
			<td height="50px;" align="center">
				<div><h1>top</h1></div>
			</td> 
			<td rowspan="2">
				<table id ='mainfrom'></table>
				<tiles:insertAttribute name="body" />
			</td>
		</tr>
		<tr>
			<td width="15%" height="500">
			<h3>系統菜單</h3>
		    <div style="margin:10px 0;"></div>
		    <ul id="tree_div" class="easyui-tree" data-options="
		                url:'/scripts/tree-data.js',
		                method:'get',
		                animate:true,
		                formatter:function(node){
		                    var s = node.text;
		                    if (node.children && node.children.length >0 ){
		                        s += '&nbsp;<span style=\'color:blue\'>(' + node.children.length + ')</span>';
		                    }
		                    return s;
		                },
		                onClick : function(node){
		                	//$.messager.alert('1',node.menuurl,'info');
		                	window.location.href=node.menuurl;
		                }
		            "></ul>
		            
		</td> 
		</tr>
		<tr>
			<td colspan="2" width="100%" height="30px;" align="center">
				<div><h1>foot</h1></div>
			</td> 
		</tr>
	</table>
	
	</body>
</html>








