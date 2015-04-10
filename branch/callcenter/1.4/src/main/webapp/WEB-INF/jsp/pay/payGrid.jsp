 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Custom DataGrid Pager - jQuery EasyUI Demo</title>
	
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui/demo.css">
	<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui/locale/easyui-lang-zh_CN.js"></script>
	
	<style type="text/css">
		#search{
			margin-bottom: 10px;
		}
		#search td,#search th{
			padding-bottom: 10px;
		}
	</style>
</head>
<body onresize="resizeGrid();">
	
	<div id="search">
		<div id="searchFrm">
			<form action="">
				<table spellcheck="false">
					<tr>
						<th>日期选择:</th>
						<td>
							<!-- 
					 <input class="easyui-datetimebox"  name="exportTime" id="exportTime" style="width:150px"></input>
					 --> <input class="easyui-datetimebox" name="start"
							id="exportTime1" style="width: 150px" editable="false"></input> -
							<input class="easyui-datetimebox" name="end" id="exportTime2"
							style="width: 150px" editable="false"></input>
						</td>
						<td style="width: 10px;"></td>
						<th>支付状态:</th>
						<td><select id="payState" name="payState"
							style="width: 120px;">
								<option value="-1" selected="selected">全部</option>
								<option value="0">未付款</option>
								<option value="1">付款成功</option>
								<option value="2">付款失败</option>
						</select></td>
					</tr>
					<tr>
						<th>
							经纪人姓名:
						</th>
						<td>
							<input type='text' name='userName' id='userName'/>
						</td>
						<td style="width: 10px;"></td>
						<th  style="width: 120px;">
							经纪人支付宝帐号:
						</th>
						<td>
							<input type='text' name='userAccount' id='userAccount'/>
						</td>
					</tr>

					<tr>
						<td><input type="button" value="查询" onclick="doSearch();" />
						</td>
						<td><input type="reset" value="重置" onclick="restFrom()"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<div>
		<input type="button" value="导出合并数据" onclick='exportExcel(1)'/> | 
		<form action="${ctx}/pay/importMergeExportExcel" method="post" id='mergerpay-from' style="display: inline;" target="pic_hidden_frame_mergerpay" enctype="multipart/form-data">
			<input type="file" name="payExcel" id='mergerpay-file'/>
		</form>
		<input type="button" value="导入合并数据" onclick='importExcel(this,1)'/>
		<iframe name="pic_hidden_frame_mergerpay" id="pic_hidden_frame_mergerpay" style="display:none"></iframe>
		<span style="margin-left: 40px;">说明: 导出表格,仅仅根据时间范围导出</span>
	</div>
	<hr/>
	<!-- 
	<div>
		<input type="button" value="导出数据" onclick='exportExcel(0)'/> | 
		<form action="${ctx}/pay/importExcel" method="post" id='importFrm' style="display: inline;" target="pic_hidden_frame" enctype="multipart/form-data">
			<input type="file" name="payExcel" id='file'/>
		</form>
		<input type="button" value="导入数据" onclick='importExcel(this,0)'/>
		<iframe name="pic_hidden_frame" id="pic_hidden_frame" style="display:none"></iframe>
	</div>
	<hr/>
	 -->
	
	<div id="statisticsPay-div">
	</div>
	
	<hr/>
	
	<table id="mainfrom" > </table>
	
	<script type="text/javascript">
	var tmp =null;
	function diableButton(obj){
		tmp = $(obj);
		setTimeout("test1()", 2000);
		tmp.attr('disabled',true);
	}
	function test1(){
		//alert(1);
		if(tmp){
			tmp.attr('disabled',false);
		}
	}
	function restFrom(){
		$("#exportTime1").datetimebox("setValue","");
		$("#exportTime2").datetimebox("setValue","");
	}
	function exportExcel(type){
		
		var pars="?_t = "+((new Date()).getTime());
		var t =$("#exportTime1").datetimebox("getValue");
		if(t != null && t !=''){
			pars +="&start=" + t;
		}
		
		var t1 =$("#exportTime2").datetimebox("getValue");
		if(t1 != null && t1 !=''){
			pars +="&end=" + t1;
		}
		if(t != '' && t1 != ''){
			if(t>t1){
				$.messager.alert("温馨提示","起始时间不能大于截止时间!","warning");
				$('#startPublishDate').datetimebox('setValue','');
				$('#endPublishDate').datetimebox('setValue','');
				return ;
			}
		}
		
		//先判断数量
		var url1= "${ctx}/pay/exportExcelCount";
		$.ajax( {
			url : url1,
			data : pars,
			async:false,
			method : 'POST',
			success : function(data) {
				//data =eval("("+data+")");
				if("0" == data){
		            $.messager.alert('温馨提示', '暂无符合要求的数据.','info'); 
		            return ;
				}else{
					if(type == 0){
						// 导出单条数据
						window.location.href ="${ctx}/pay/exportExcel"+pars;
					}else{
						//导出 合并数据
						window.location.href ="${ctx}/pay/payMergeExportExcel"+pars;
					}
				}
			},
			error : function(data) {
				
			}
		});
		
		
	}
	
	function importExcel(obj,type){
		var fileName = $("#file").val();
		if(type == 1){
			//导出 合并数据
			fileName = $("#mergerpay-file").val();
		}
		if (fileName == "" || fileName == null) {
	        $.messager.alert("温馨提示","请选择上传的Excel文件.","warning");  
	        return ;  
	    } else {  
	        if (!/\.(xls|XLS|xlsx|XLSX)$/.test(fileName)) {  
	        	$.messager.alert("温馨提示","请选择Excel文件.","warning"); 
	        	 $("#file").val("");
	            return ;  
	        }  
	    }
		if(type == 0){
			// 导出单条数据
			$("#importFrm").submit();
		}else{
			//导出 合并数据
			$("#mergerpay-from").submit();
		}
		diableButton(obj);
	}
	
	/**
	 当导入之后  返回结果 回调 的函数
	*/
	function returnParentFunction(i){
		if(i == 0){
			$.messager.alert("温馨提示","Excel文件导入成功!","info");
		}else{
			var s ="Excel文件导入失败!";
			if(i =='100010'){
				s ="Excel文件格式不正确,请转化为2007版文件格式!";
			}else if(i == '100011'){
				s ="不能没有支付结果!";
			}else if(i == '100012'){
				s = "Excel文件不正确,请选择对应的文件!";
			}
			$.messager.alert("温馨提示",s,"warning");
		}
		//清空选择的文件
		$("#file").val("");
		 $('#mainfrom').datagrid('reload');
	}
	
	//搜索
		function doSearch(){
			var pars="{";
			var payState = $("#payState").val();
			var t =$("#exportTime1").datetimebox("getValue");
			if(t != null && t !=''){
				pars +="'start' : '" + t+"' , ";
			}
			
			var t1 =$("#exportTime2").datetimebox("getValue");
			if(t1 != null && t1 !=''){
				pars +=" 'end' : '" + t1+"' , ";
			}
			if(t != '' && t1 != ''){
				if(t>t1){
					$.messager.alert("温馨提示","起始时间不能大于截止时间!","warning");
					return ;
				}
			}
			var userName = $("#userName").val();
			if(userName != null && userName !=''){
				pars +="'userName' : '"+userName+"',";
			}
			
			var userAccount = $("#userAccount").val();
			if(userAccount != null && userAccount !=''){
				pars +="'userAccount' : '"+userAccount+"',";
			}
			
			if(payState != null && payState != '' ){
				pars +="'payState' : '"+payState+"',";
			}
			if(pars.length >1){
				pars = pars.substr(0,pars.length-1);
			}
			pars += "}";
			pars = eval("("+pars+")");
		    $('#mainfrom').datagrid('reload',pars);
		    //统计
		    statisticsPay(pars);
		} 
		
		var data_from = $('#mainfrom').datagrid({
			url : '${ctx}/pay/payList',
			//fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
			striped : true, //True 就把行条纹化
			pagination : true,//分页
			rownumbers : true, //显示行号
			pageSize : 20,
			pageNumber : 1,
			pageList : [ 20, 30, 50, 100 ],//列表分页
			loadMsg : '数据正在努力加载中...',
			height: document.body.clientHeight * 3.7,
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [
			          //{field : 'payId',title : 'ID',checkbox : true,width:$(this).width() * 0.5}//显示一个checkbox
			         {field : 'payId',title : '流水号',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'userName',title : '姓名',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.05}
					, {field : 'account',title : '支付宝账户',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'userStateStr',title : '用户状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'userDisableStr',title : '是否禁用',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'addTime',title : '审核时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'paySum',title : '支付金额',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1,formatter : function(val,row){
							return val+"元";
						}}
					, {field : 'payStateStr',title : '支付状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'payTime',title : '支付时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
					, {field : 'remark',title : '失败理由',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.15}

			] ],
			onDblClickRow : function(rowIndex, rowData) {
				//alert(rowData.payId);
			}

		});

		/*
		查看
		 */
		function view_btn(id) {
		}

		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth * 0.9;
				}
			});
		}
		$(function(){
			/* $('#mainfrom').datagrid('resize', {
				width : function() {
					return document.body.clientWidth ;
				}
			}); */
		});
		
	//初始化 数据
	$(document).ready(function(){
		//设置 日期框 中问当天的日期
		/*
		var d =new Date();
		var s =d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		$("#exportTime").datetimebox({
			formatter: formatDateText, 
			value:s
		}); 
		*/
		
		//支付简单 统计信息
		// statisticsPay
		statisticsPay(null);
		
	});	
	
	function statisticsPay(pars){
		var url1= "${ctx}/pay/statisticsPay";
		$.ajax( {
			url : url1,
			data : pars,
			async:false,
			method : 'POST',
			success : function(data) {
				data =eval("("+data+")");
				var str ="总笔数: "+ data.payNum +" | 成功数量: "+data.success +" | 失败数量 : "+data.failed +" | 未支付数量: "+data.ing;
				str +=" <br/> 总金额: "+ data.paySum +" | 已付款金额 : "+data.sucessSum;
				$("#statisticsPay-div").html(str);
			},
			error : function(data) {
				
			}
		});
	}
	
	function formatDateText(d){
		var s =d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		return s;
	}
	
	</script>
</body>
</html>