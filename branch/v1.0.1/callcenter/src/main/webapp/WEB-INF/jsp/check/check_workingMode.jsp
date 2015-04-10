<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<style type="text/css">
		div#check_workingMode {padding:6px;height:48px;font-size:14px;font-weight:bold;font-family:"微软雅黑";border-bottom:1px solid rgb(225,225,225);background:rgb(205,205,205)}
		div#check_workingMode td {font-size:14px;font-weight:bold;font-family:"微软雅黑"}
		input.button {width:120px;height:40px;border:1px solid rgb(155,155,155);background:rgb(225,225,225);color:#333;font-weight:bold;font-size:14px;font-family:"微软雅黑";cursor:pointer;border-radius:5px;margin-left:32px}
	</style>
	<div id="check_workingMode">
		<div title="工作情况" style="margin:0px 50px;line-height:48px">
			<div style="width:80%;text-align:center;float:left;font-size:18px;font-weight:bold;font-family:微软雅黑">
				<b>本次计时：</b><span class="timer" style="color:rgb(255,255,255);margin-right:80px"></span>
				<b>已完成工单：</b>${sessionScope.login_session.finishedTaskNum}
				<!-- <b>待处理工单：</b> -->
			</div>
			<span style="float:right">
				<input type="button" class="button" onclick="endWork()" value="退出工作模式">
			</span>
			<div style="clear:both"></div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			var workingTime = 0;
			refreshWorkingTime(workingTime++);
			window.setInterval(function(){
				refreshWorkingTime(workingTime++);
			}, 1000);
			function refreshWorkingTime(time){
				$('#check_workingMode .timer').html(second2String(time));
			}
			function second2String(seconds){
				var hours = parseInt(seconds/3600);
				var minutes = parseInt((seconds-hours*3600)/60);
				seconds = seconds%60;
				return (hours<10? "0"+hours : hours)+":"+(minutes<10? "0" + minutes : minutes) + ":" + (seconds<10? "0"+seconds : seconds);
			}
		});
		
		function endWork(){
			window.location.href="${ctx}/index";
		}
	</script>