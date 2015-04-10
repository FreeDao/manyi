<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
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
	
	<table border="1" style="width:100%" >
		<tr><td colspan="2" width="100%" height="50px;" align="center">
			<div><h1>top</h1></div>
		</td>  </tr>
		<tr><td width="15%">
		
		
		<h2>tree</h2>
    <div style="margin:10px 0;"></div>
    <ul class="easyui-tree" data-options="
                url:'/scripts/tree-data.js',
                method:'get',
                animate:true,
                formatter:function(node){
                    var s = node.text;
                    if (node.children && node.children.length >0 ){
                        s += '&nbsp;<span style=\'color:blue\'>(' + node.children.length + ')</span>';
                    }
                    return s;
                }
            ">
            
		</td> <td>
		
		
	
		
		</td> 
		</tr>
		
		<tr><td colspan="2" width="100%" height="30px;" align="center">
			<div><h1>foot</h1></div>
		</td>  </tr>
		
	</table>
	
			<table id="mainfrom"></table>
	
	<div id='view' style="width:400px;height:280px;padding:10px 20px;display: none;">
		名称：<span class='cityName'></span><br/>
		编号：<span class='cityNo'></span>
	</div>
	
	<div id='add' style="width:400px;height:280px;padding:10px 20px;display: none;">
	  <form method="post"  action="/city/save.do">
		名称：<input  name ="cityName" class="easyui-validatebox validatebox-text validatebox-invalid" required="true"/><br/>
		编号：<input name ="cityNo" class="easyui-validatebox validatebox-text validatebox-invalid"  required="true"/>
		</form>
	</div>
	
	<div id='edit' style="width:400px;height:280px;padding:10px 20px;display: none;">
	  <form id="fm" method="post"  action="/city/update.do">
		名称：<input name ="cityName" class="easyui-validatebox validatebox-text validatebox-invalid"  data-options="required:true"/><br/>
		编号：<input name ="cityNo" class="easyui-validatebox validatebox-text validatebox-invalid" data-options="required:true,length:[3,4]"/>
		</form>
	</div>
	
	

    
    
	<script type="text/javascript">
	var frm_main = $('#mainfrom').datagrid({
	       url:'/city/list.rest', 
	       fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
	       striped : true, //True 就把行条纹化
	       pagination : true ,//分页
	       rownumbers : true, //显示行号
	       toolbar: [{
	    	   		text:'添加',
	    	        iconCls:'icon-add',   
	    	        handler:function(){
	    	        	$("#add").show();
	    	        	//新增
	    				$("#add").find("input").val("");
	    				$("#add form").attr("action","save.do");
	    	        	$('#add').dialog({
	    	        	     modal:true  ,
	    	        	     resizable :true,
	    	        	     collapsible : true,
	    	        	     title : '新增',
	    	        	     buttons:[
	    						{
	    						    text:'保存',
	    						    iconCls:'icon-ok',
	    						    handler:function(){
	    						    	
	    						    	//验证 ， 提交 add 表单
	    						    	$("#add #fm").submit();
	    						        $("#add").dialog("close");
	    						    }
	    						 },
	    						 {
	    						    text:'取消',
	    						    iconCls:'icon-cancel',
	    						    handler:function(){
	    						        $("#add").dialog("close");
	    							}
	    						 }
	    	        	        ]
	    	        	  });
	    	        }   
	    	      },
	    	      
	    	      {
		    	   		text:'编辑',
		    	        iconCls:'icon-edit',   
		    	        handler:function(){
		    	        	var rows = $('#mainfrom').datagrid('getSelections');//得到选中的行
		    	        	if(rows.length > 1){  $.messager.alert('温馨提示','不能选择多行进行编辑！','info'); return false;}
		    	        	if(rows.length <= 0){ $.messager.alert('温馨提示','请选择数据！','info'); return false;}
		    	        	$("#edit").show();
		    	        	$("#edit").find(":input[name='cityName']").val(rows[0].cityName);
		    	        	$("#edit").find(":input[name='cityNo']").val(rows[0].cityNo);
		    	        	$('#edit').dialog({  
		    	        	     modal:true  ,
		    	        	     resizable :true,
		    	        	     collapsible : true,
		    	        	     title : '新增',
		    	        	     buttons:[
		    						{
		    						    text:'保存',
		    						    iconCls:'icon-ok',
		    						    handler:function(){
		    						    	//验证 ， 提交 edit 表单
		    						    	$("#edit #fm").submit();
		    						        $("#edit").dialog("close");
		    						    }
		    						 },
		    						 {
		    						    text:'取消',
		    						    iconCls:'icon-cancel',
		    						    handler:function(){
		    						        $("#edit").dialog("close");
		    							}
		    						 }
		    	        	        ]
		    	        	  });
		    	        }   
		    	      }
	    	      ] ,
	       columns:[[   
	            {field:'cityName',title:'名称',sortable:true},   
	            {field:'cityNo',title:'编号',sortable:true}
	        ]],
	        onDblClickRow :function(rowIndex,rowData){
	        	$("#view").show();
	        	$("#view").find(".cityName").html(rowData.cityName);
	        	$("#view").find(".cityNo").html(rowData.cityNo);
	        	$('#view').dialog({   
	        	     modal:true  ,
	        	     resizable :true,
	        	     collapsible : true,
	        	     title : '查看',
	        	     buttons:[
						{
						    text:'好',
						    iconCls:'icon-ok',
						    handler:function(){
						        $("#view").dialog("close");
						    }
						}
	        	        ]
	        	  });
	        }
	 });  
	 
	 
	 
	</script>
</body>
</html>