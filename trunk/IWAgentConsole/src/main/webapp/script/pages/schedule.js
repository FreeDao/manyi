window.iwac = window.iwac||{};
/**
 * @author carvink
 */
iwac.schedule = $.extend(iwac.schedule||{},{
	/**
	 * 初始化页面以及数据
	 */
	init:function(){
		var date = new Date();
		console.log(date);
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		
		
		
		this.appointmentStatus = {
			0:'此约会异常',
			1:"待确认",
			2:"待看房",
			3:"经纪人已签到",
			4:"未到未看房",
			5:"已到未看房",
			6:"已到已看房",
			7:"已改期",
			8:"已取消"
		};
		
		
		var path = $("body").attr("path");
		this.calendar =  $('#calendar').fullCalendar({
			header: {
				left: 'prev,next',
				center: 'title',
				right:null
				//right: 'month,agendaWeek,agendaDay'
			},
			buttonText: {
	          prev: '上一周',
	          next: '下一周'
        	},
			firstDay:1,
			editable: false,
			allDaySlot:false,
			slotMinutes:20,
			timeFormat: 'H:mm',
			axisFormat: 'H:mm',
			//默认以什么视图显示 month 、basicWeek 、basicDay 、agendaWeek 、agendaDay 
			defaultView:'agendaWeek',
			
			events:function(start,end,callback){
				iwac.common.mask("正在加载数据...");
				$.ajax({
					url:path+"appointment/list.do",
					type:'post',
					data:{
						pageSize:100,
						pageIndex:0,
						startTime:$.fullCalendar.formatDate(start,"yyyy-MM-dd HH:mm"),
						endTime:$.fullCalendar.formatDate(end,"yyyy-MM-dd HH:mm")
					},
					timeout:'60000',
					success:function(data){
						iwac.common.unmask();
						if(data){
							console.log(data);
							var event = [];
							if(data.data.length>0){
								$.each(data.data,function(){
									var appointmentTime = new Date(this.appointmentTime);
									var endTime = new Date(this.appointmentTime);
									endTime.setHours(endTime.getHours()+2);
									
									var className,status=this.appointmentState;
									if(status == 1 || status == 2){
										className = 'color1';
									}else if(status == 7 || status ==8){
										className = 'color2';
									}else{
										className = 'color3';
									}
									event.push({
										title:iwac.schedule.appointmentStatus[status]+"("+this.seeHouseCount+"套)",
										id:this.id,
										allDay:false,
										start:$.fullCalendar.formatDate(appointmentTime,"yyyy-MM-dd HH:mm:ss"),
										end:$.fullCalendar.formatDate(endTime,"yyyy-MM-dd HH:mm:ss"),
										editable:false,
										borderColor:'#F7AB00',
										className:className,
										appointment:this
									});
								});
								callback(event);
							}
							//iwac.schedule.data = data.data;
						}
					},
					error:function(x,s){
						iwac.common.unmask();
						if(s == "parsererror"){
							if(iwac.common.checkLogin(x.responseText)){
								iwac.common.fwd($("body").attr("path"));
							}
						}else if(s == "timeout"){
							mini.alert("请求超时，请重试");
							return;
						}
						mini.alert("操作失败，请重试");
					},
					dataType:'json'
				});
				
			},
			eventClick:function(calEvent, jsEvent, view){
				iwac.schedule.appointmentDetail(calEvent.id);
				console.log(calEvent);
				console.log(jsEvent);
				console.log(view);
				
			}
		});
		
		
		
		//console.log(calendar);
	},
	appointmentDetail:function(id){
		console.log(iwac.appointment);
		iwac.appointment.detailPanel.open({
			title:'约会详情',
			appointmentId:id,
			ondestroy:function(){
				iwac.schedule.calendar.fullCalendar('refetchEvents');
			}
		});
	},
	getFirstDateOfWeek:function(){
		var firstDateOfWeek;  
		this.date.setDate(this.date.getDate() + 1 - this.date.getDay());   
		firstDateOfWeek = this.date;  
		return firstDateOfWeek;
	},
	getLastDateOfWeek:function(){
		var lastDateOfWeek;  
		this.date.setDate(this.date.getDate() +7 - this.date.getDay());  
		lastDateOfWeek = this.date;  
		return lastDateOfWeek; 
	}
});


$(function(){
	iwac.schedule.init();
});