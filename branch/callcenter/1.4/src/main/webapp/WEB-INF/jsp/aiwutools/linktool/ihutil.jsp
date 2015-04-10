<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>爱屋小工具</title>

<%
String ip=	request.getRemoteAddr();
%>
<link rel="stylesheet" type="text/css" href="${ctx }/easyui/themes/default/easyui.css" />
<script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
/**
 * 根据关键字 生成 链接地址
 */
function createLinksBykey(){
	var str = $("#link-txt").val();
	var key = $.trim(str);
	if(key == '' || key ==  null){
		$.messager.alert("提示","请输入关键字");
		return ;
	}
	var keys = key.split(";");
	var pars =[];
	if(keys.length >0){
		for(var i =0 ; i<keys.length ; i++){
			var str = keys[i];
			if(str != '' && str != null){
				var tmp = str.split(",");
				var n = tmp[0];
				var cn = ( tmp[1] == null ? "" : tmp[1]); 
				pars.push({'name':$.trim(n),'cityName':$.trim(cn)});
			}
		}
	}
	
	//解析
	$("#link-con").html("");
	for(var i =0 ; i<pars.length ; i++){
		var tmp = "<a target='_bank' href='${ctx}/linktool/outerpage/directUrl?name="+encodeURI(encodeURI(pars[i].name));
		var cn ='';
		if(pars[i].cityName != null && pars[i].cityName != ''){
			cn ="&cityName="+encodeURI(encodeURI(pars[i].cityName));
		}
		tmp += cn;//城市名称
		var str ='';
		
		//搜房
		str =tmp+"&type=4";// 添加 type 类型
		str =str+"'>"+pars[i].cityName+" "+ pars[i].name+" 搜房</a><br/>";
		//百度地图
		str =str+tmp+"&type=1";// 添加 type 类型
		str =str+"'>"+pars[i].cityName+" "+ pars[i].name+" 百度地图</a><br/>";
		//腾讯地图
		str =str+tmp+"&type=2";// 添加 type 类型
		str =str+"'>"+pars[i].cityName+" "+ pars[i].name+" 腾讯地图</a><br/>";
		//高德地图
		str =str+tmp+"&type=3";// 添加 type 类型
		str =str+"'>"+pars[i].cityName+" "+ pars[i].name+" 高德地图</a><br/>";
		//百度API
		str =str+tmp+"&type=5";// 添加 type 类型
		str =str+"'>"+pars[i].cityName+" "+ pars[i].name+" 百度经纬度API</a><br/>";
		
		str +="<br/>";
		$("#link-con").append(str);
	}
	
}
</script>
</head>

<body>

	<div>
		<h3>根据关键字生成链接地址</h3>
		<div id="link-p">
			<div id="link-impl">
				关键词列表:<br/>
				<textarea rows="6" cols="80" id='link-txt'></textarea>
				<br/>
				<span> 例如: 康怡苑,上海;共康四村,上海;万年花城,;中关村购物中心; &nbsp;&nbsp;&nbsp; 注意:城市可以省略(添加城市结果会更加准确)  如以上例子都可以 ,关键字中不要包含,; </span>
				<br/>
				<input type="button" value="查询生成" onclick="createLinksBykey();"/>
				<hr/>
				<div id='link-con'></div>
			</div>
		</div>
	</div>
	
	
</body>
</html>