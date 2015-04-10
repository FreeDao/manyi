<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="日程安排">
<meta name="description" content="日程安排">
<title>日程安排</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/fullcalendar.css">
<style type="text/css">
#calendar {
	width: 960px;
	margin: 20px auto 10px auto;
}
</style>
<script src='${ctx}/jqueryui/js/jquery-1.9.1.min.js'></script>
<script src='${ctx}/jqueryui/js/jquery-ui-1.10.2.custom.min.js'></script>
<script src='${ctx}/jqueryui/js/fullcalendar.min.js'></script>

</head>

<body>


	<div id="main-calendar">
		<div id='calendar'></div>
	</div>


	<script type="text/javascript">
		/*
		 *加载 计划任务信息 
		 */
		function initPlan(viewStart, bdId) {
			var resultDate = [];
			var rows = {};
			var pars = "{\"start\" : \"" + viewStart + "\" , \"bdId\" : \""
					+ bdId + "\"}";
			var url1 = "${ctx}/lookHouse/planList";
			$.ajax({
				url : url1,
				data : pars,
				dataType : "json",
				type : "POST",
				async : false,
				contentType : 'application/json;charset=UTF-8',
				success : function(data) {
					//data =eval("("+data+")");
					rows = data.rows;
				},
				error : function(data) {
					//alert(data);
				}
			});
			if (rows != null && rows.length > 0) {
				title1 = rows[0].areaName + "-" + rows[0].bdName;
				for (var i = 0; i < rows.length; i++) {

					var dt = new Date(rows[i].taskDate);
					var y = dt.getFullYear();
					var M = dt.getMonth();
					var d = dt.getDate();
					var h = dt.getHours();
					var m = dt.getMinutes();
					var h2 = h;
					var m2 = 0;
					if (m != 0 && m != 30) {
						m = 0;
					}
					if (m != 0) {
						h2 = h + 1;
					} else {
						m2 = 30;
					}

					// alert(title1+","+rows[i].id+" "+y+" 年  "+M +" 月 "+d +" 日" +h +" 时 "+ m+" 分 ;  h2: "+ h2+ " m2:  "+m2 );
					var item = {
						id : rows[i].id,
						title : rows[i].estateName,
						start : new Date(y, M, d, h, m),
						end : new Date(y, M, d, h2, m2),
						allDay : false,
						editable : false
					};
					resultDate.push(item);
				}
			}
			return resultDate;
		}

		var title1 = "";

		$(function() {

			//加载 初始数据
			var eventJson = [];
			//initPlan(0,1);// 第一个参数 , 初始化0; 第二个参数, BDid

			$('#calendar').fullCalendar(
					{
						header : {
							left : 'prev,next today' //prev: 用于切换到上一月/周/日视图的按钮 next: 用于切换到下一月/周/日视图的按钮
							,
							center : 'title'
						//,right: 'month,agendaWeek,agendaDay'
						//,right :''
						},
						//titleFormat:title1,
						defaultView : 'agendaWeek',//默认视图
						firstDay : 1,//设置一周中显示的第一天是哪天，周日是0，周一是1
						timeFormat : 'H:mm',
						axisFormat : 'H:mm',
						firstHour : 7,//初始滚动条滚动到的时间位置，默认在7点钟的位置
						events : eventJson,
						//函数回调，每次view重新显示时 均会调用下面的函数
						viewDisplay : function(view) {//动态把数据查出，按照月份动态查询
							$("#calendar").fullCalendar('removeEvents');
							var viewStart = $.fullCalendar.formatDate(
									view.start, "yyyy-MM-dd HH:mm:ss");
							//alert(viewStart);
							eventJson = initPlan(viewStart, '${bdId}');//起始时间 , bdId 
							//view.title=title1;
							$.each(eventJson, function(index, term) {
								$("#calendar").fullCalendar('renderEvent',
										term, true);
							});
							//把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示
						}
					});

		});
	</script>
</body>
</html>
