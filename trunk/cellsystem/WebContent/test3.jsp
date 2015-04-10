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
	<h2>DataGrid with Toolbar</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>Put buttons on top toolbar of DataGrid.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="" title="DataGrid with Toolbar" style="width:700px;height:250px"
			data-options="rownumbers:true,singleSelect:true,url:'/city/list.rest',fitColumns:true,method:'get',toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'cityName'">名称</th>
				<th data-options="field:'cityNo'">编号</th>
			</tr>
		</thead>
	</table>
	
	
	    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="ftitle">User Information</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>First Name:</label>
                <input name="firstname" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Last Name:</label>
                <input name="lastname" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Phone:</label>
                <input name="phone">
            </div>
            <div class="fitem">
                <label>Email:</label>
                <input name="email" class="easyui-validatebox" validType="email">
            </div>
        </form>
    </div>
    
    
    <!-- 添加 表单的 两个按钮 -->
     <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
    
	<script type="text/javascript">
		var toolbar = [{
			text:'添加',
			iconCls:'icon-add',
			handler:  function newUser(){
	            $('#dlg').dialog('open').dialog('setTitle','New User');
	            $('#fm').form('clear');
	            url = 'save_user.php';
	        }
		},{
			text:'修改',
			iconCls:'icon-edit',
			handler:function(){alert('cut');}
		},'-',{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){alert('delete');}
		}];
		
		  function saveUser(){
	            $('#fm').form('submit',{
	                url: url,
	                onSubmit: function(){
	                    return $(this).form('validate');
	                },
	                success: function(result){
	                    var result = eval('('+result+')');
	                    if (result.errorMsg){
	                        $.messager.show({
	                            title: 'Error',
	                            msg: result.errorMsg
	                        });
	                    } else {
	                        $('#dlg').dialog('close');        // close the dialog
	                        $('#dg').datagrid('reload');    // reload the user data
	                    }
	                }
	            });
	        }
		  
	</script>
</body>
</html>