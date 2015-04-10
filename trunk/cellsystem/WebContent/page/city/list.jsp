<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
	
	<div id='view' style="width:400px;height:280px;padding:10px 20px;display: none;">
		名称：<span class='view' id="cityName"></span><br/>
		编号：<span class='view' id="cityNo"></span>
	</div>
	
	<!-- 新增，修改 -->
	<div id='main_div' style="width:400px;height:280px;padding:10px 20px;display: none;">
	  <form  method="post" >
		名称：<input  name ="cityName" class="easyui-validatebox validatebox-text validatebox-invalid"  data-options="required:true"/><br/>
		编号：<input name ="cityNo" class="easyui-validatebox validatebox-text validatebox-invalid"  data-options="required:true,length:[3,4]"/>
	  </form>
    </div>
    
<script type="text/javascript">
	$('#mainfrom').datagrid({
	       url:'/city/list.rest', 
	       fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
	       striped : true, //True 就把行条纹化
	       pagination : true ,//分页
	       rownumbers : true, //显示行号
	       pageSize : 10, pageNumber :1,
	       pageList :[10,30,50,100],//列表分页
	       sortName : 'id', sortOrder : 'desc',
	       loadMsg : '数据正在努力加载中...',
	       selectOnCheck: true,
	       checkOnSelect: true,
	       columns:[[
	                {field:'ck' , checkbox:true },//显示一个checkbox
	 	            {field:'cityName',title:'名称',align:'center',resizable:true,hidden:false,sortable:true},   
	 	            {field:'cityNo',title:'编号',align:'center',resizable:true,hidden:false,sortable:true}
	 	        ]],
	       toolbar:[{
		    	   		text:'添加',
		    	        iconCls:'icon-add',   
		    	        handler:function(){
		    	        	item_btn("add");
		    	        }
	    	       },
	    	       {
		    	   		text:'编辑',
		    	        iconCls:'icon-edit',   
		    	        handler:function(){
		    	        	item_btn("edit");
		    	        }  
		    	   }] ,
	        onDblClickRow :function(rowIndex,rowData){
	        	view_btn(rowData);
	        }
	 });  
	 
	
	 /*
	 查看
	 */
	function view_btn(row){
	  	$("#view").show();
	  	$("#view").find(".view").each(function(p){
    		var self =  $(this);
    		self.html(row[self.attr("id")]);
    	});
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
	 
	/*
	1，新增
	2，修改
	*/
	function item_btn(type){
		if(type == 'edit'){
	    	var rows = $('#mainfrom').datagrid('getSelections');//得到选中的行
	    	if(rows.length > 1){  $.messager.alert('温馨提示','不能选择多行进行编辑！','info'); return false;}
	    	if(rows.length <= 0){ $.messager.alert('温馨提示','请选择数据！','info'); return false;}
	    	$("#main_div").find("input").each(function(p){
	    		var self =  $(this);
	    		self.val(rows[0][self.attr("name")]);
	    	});
	    	$("#main_div form").attr("action","update.do");
		}else if(type == 'add'){
			//新增
			$("#main_div").find("input").val("");
			$("#main_div form").attr("action","save.do");
		}
		
    	$("#main_div").show();
    	$("#main_div").dialog({  
   	     modal:true  ,
   	     resizable :true,
   	     collapsible : true,
   	     title : type == 'add' ? '新增':'修改',
   	     buttons:[
			{
			    text:'保存',
			    iconCls:'icon-ok',
			    handler:function(){
			    	
			    	//验证 ， 提交 表单
			    	$("#main_div form").submit();
			        $("#main_div").dialog("close");
			    }
			 },
			 {
			    text:'取消',
			    iconCls:'icon-cancel',
			    handler:function(){
			        $("#main_div").dialog("close");
				}
			 }
   	        ]
   	  });
	}
	
	 
	</script>
