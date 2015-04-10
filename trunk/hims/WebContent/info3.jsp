<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据报表</title>

<%
String ip=	request.getRemoteAddr();
if(true){
//if("180.166.57.26".equals(ip) || "127.0.0.1".equals(ip)|| "192.168.1.173".equals(ip)){
	String p = request.getParameter("p");
	if("520Manyi".equals(p)){
		//ok
	}else{
		response.getWriter().print("密码不正确");
		return ;
	}
}else{
	response.getWriter().print(ip+" 呵呵,不是公司的网络,不能访问吆!");
	return ;
}

%>
<link rel="stylesheet" type="text/css" href="${ctx }/themes/default/easyui.css" />
<script type="text/javascript" src="${ctx}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">

/*
检测 是否 选择了excel文件
*/
function checkbankExcel(id){
	var tmp =$("#"+id).val() ;
	if(tmp == '' || tmp == null){
		$.messager.alert("温馨提示",'选择文件',"warning");
		return false;
	}else{
		 if (!/\.(xls|XLS)$/.test(tmp)) {  
        	$.messager.alert("温馨提示","请选择Excel文件(xls格式).","warning"); 
        	 $("#"+id).val("");
            return false;  
        }  
		return true;
	}
}

</script>
</head>
<body>
<div>
	<form action="${ctx }/action/importSubEstate" method="post"  enctype="multipart/form-data" onsubmit="javascript:return checkbankExcel('subeExcel');">
		Execl文件:<input type="file" name="excel" style="width:300px;" id="subeExcel"/>
		<input type="submit" value="上传子划分"/>
	</form>
	<hr/>
	<form action="${ctx }/action/importBilding" method="post"  enctype="multipart/form-data" onsubmit="javascript:return checkbankExcel('buildingExcel');">
		Execl文件:<input type="file" name="excel" style="width:300px;" id="buildingExcel"/>
		<input type="submit" value="上传楼栋"/>
	</form>
	<hr/>
	<form action="${ctx }/action/importHouse" method="post"  enctype="multipart/form-data" onsubmit="javascript:return checkbankExcel('houseExcel');">
		Execl文件:<input type="file" name="excel" style="width:300px;" id="houseExcel"/>
		<input type="submit" value="上传房源"/>
	</form>
	<hr/>
</div>
	
</body>
</html>