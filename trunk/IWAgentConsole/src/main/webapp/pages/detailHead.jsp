<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head path="<%=basePath%>">
<base href="<%=basePath%>"/>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="styles/page.css" rel="stylesheet" type="text/css" />
<link href="styles/box.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/boot.js"></script>
<script type="text/javascript" src="script/iwac.common.js"></script>

</head>

<body style="height:100%;" path="<%=basePath%>">
    <div class="page">
    	<div class="head">
	        	<div >
		            <span  style="font-weight:bold;">
		            	${user.realName}
		            	<c:choose>
		            		<c:when test="${user.gender == 1}">
		            			先生
		            		</c:when>
		            		<c:when test="${user.gender == 2}">
		            			女士
		            		</c:when>
		            	</c:choose>
		            </span>
		            <span  style="margin-left:30px;font-weight:bold;">
		            	${user.mobile}
		            </span>
		            <span style="margin-left:10px;">当前状态：
		            	<span >
		            		${userBizStatus[user.bizStatus]}
		            	</span>
		            	<c:if test="${user.bizStatus == 0}">
			                <a class="mini-button" id="edit" onclick="iwac.user.detail.showEdit()" style="margin-left:10px;width:60px;">修 改</a>
		            	</c:if>
		            </span>
		            <span style="margin-left:30px;">
	            		注册时间：
	            		<fmt:formatDate value="${user.createTime}" pattern="yyyy年MM月dd日 HH:mm" />
	            	</span>
	                <span style="margin-left:50px;">
	                	最后登录时间：
	            		<fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy年MM月dd日 HH:mm" />
	                </span>
	                <span style="margin-left:50px;">
	                	<input type='button' value='查看备注' onclick="iwac.user.detail.showMemo()" style='vertical-align:middle;'/>
	                </span>
	            </div>
              </div>
            </div>
        
        <!--Tabs-->
        <div class="mini-fit" style="height:400px">
			<div id="detailTab" class="mini-tabs" activeIndex="1" style="width:100%;height:100%;">
				 <div title="用户习惯" refreshOnClick="true" url="" name="" showCloseButton="false"></div>
				 <div title="待处理" refreshOnClick="true" url="<%=path%>/user/appointment/process.do?customerId=${user.id}" showCloseButton="false"></div>
				 <div title="约看日程" refreshOnClick="true" url="<%=path%>/appointment/appointmentSearch.do?userId=${user.id}" showCloseButton="false"></div>
				 <div id="recommoned" refreshOnClick="true" title="推荐房源" url="<%=path%>/user/recommend/recommend.do?userId=${user.id}" name="" showCloseButton="false"></div>
			</div>
       </div>
       
    <div id="winAddMemo" class="mini-window" title="添加备注" style="width:400px;height:280px;display: none;" showShadow="true"
	    showFooter="true" showModal="false" allowResize="false" allowDrag="true">
        <textarea id="content" class="mini-textarea" emptyText="用户描述，工作，收入，学历，婚姻 等等" width="100%" height="100%"></textarea>
	    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	        <input type='button' value='提 交' onclick="iwac.user.detail.submit(${user.id})" style='vertical-align:middle;'/>
	        <input type='button' value='取 消' onclick="iwac.user.detail.cancel()" style='vertical-align:middle;border:1px #FFCC33 solid;background:url(pics/cancel_bg.png) repeat-x;'/>
	    </div>
	</div>
	<c:if test="${user.bizStatus == 0}">
		<div id="winEditUserStatus" class="mini-window" title="编辑用户状态" style="width:600px;height:350px;display: none;" showShadow="true"
		    showFooter="true" showModal="false" allowResize="false" allowDrag="true">
	        <div class="box">
		        <div class="hint"> 
				  	<span>
				    	<img  src="pics/icon/warning_16x16.png"  class="icon"/>
				    	<span class="content">一旦变更为以下两种状态，系统将自动取消该用户所有进行中的约看活动。</span>
				    </span>
				</div>
		        <div id="userStatusForm" style="padding-left:10px;margin:0px;margin-top:10px;">
			    	<div class="mini-radiobuttonlist" name="bizStatus" repeatItems="2" repeatLayout = "table" repeatDirection="vertical" value="1" textField="text" valueField="id" url="subs/editUserStatus.txt"></div>
			        <span style="color:red;">*</span> 备注<br>
			        <textarea class="mini-textarea" name="memo" emptyText="请输入备注" width="100%" height="150"></textarea>
			    </div>
		    </div>
		    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		        <input type='button' value='提 交' onclick="iwac.user.detail.editBizStatus(${user.id})" style='vertical-align:middle;'/>
		        <input type='button' value='取 消' onclick="iwac.user.detail.hideWinEditUserStatus()" style='vertical-align:middle;border:1px #FFCC33 solid;background:url(pics/cancel_bg.png) repeat-x;'/>
		    </div>
		</div>
	</c:if>
	
	<div id="winUserMemoList" class="mini-window" title="查看备注" style="width:512px;height:360px;display: none;" showShadow="true"
	    showFooter="false" showModal="false" showCollapseButton="true" allowResize="false" allowDrag="true" showToolbar="true" showCloseButton="true">
	    <div property="toolbar" style="text-align:right;padding:5px;padding-right:15px;">
	        <input id="addMemo" type='button' value='添 加' onclick="iwac.user.detail.addMemo()" style='vertical-align:middle;'/>
	    </div>
        <!--备注-->
                	<div class="mini-fit" style="height:285px;width:500px;">
		                <div id="memoGrid" class="mini-datagrid" style="height:100%;" url="user/memo/list.do?userId=${user.id}" multiSelect="true" showfooter="false">
		                    <div property="columns">
		                        <div field="memo"align="center" headeralign="center">备注内容</div>
		                        <div field="createTime" align="center" headeralign="center">备注时间</div>
		                    </div>
		                </div>
		            </div>
	</div>
</body>
<script type="text/javascript" src="script/pages/detailHead.js?<%=new Date().getTime()%>">
</script>
</html>
