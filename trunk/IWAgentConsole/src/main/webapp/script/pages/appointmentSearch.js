window.iwac = window.iwac||{};
/**
 * @author carvink
 */
iwac.appointment = $.extend(iwac.appointment||{},{
	/**
	 * 初始化页面以及数据
	 */
	init:function(){
		mini.parse();
		var grid = this.grid = mini.get("datagrid1");
		/*因为接口原因，如果userId为空，就是查找的与经纪人对应的约会，
		      所以在userId为空的时候，userId为-1，这样经纪人与用户对应起
		      来的约会肯定就查不到*/
		grid.load({userId:userId||-1});
		var appointmentStatus = {
			1:"待确认",
			2:"待看房",
			3:"经纪人已签到",
			4:"未到未看房",
			5:"已到未看房",
			6:"已到已看房",
			7:"已改期",
			8:"已取消"
		};
		grid.on("load",function(e){
			var responseText = e.xhr.responseText;
			if(iwac.common.checkLogin(responseText)){
				iwac.common.fwd($("body").attr("path"));
			}
		});
		
		
		var data;
		grid.on("drawcell",function(e){
			var column = e.column;
			if(!data){
				data = grid.getResultObject();
			}
			var data1 = data.data[e.rowIndex];
			if(column.field == "appointmentState"){
				e.cellHtml = appointmentStatus[e.value];
			}else if(column.name == "action"){
				if(data1){
					e.cellHtml = '<a class="mini-button"  onclick="iwac.appointment.detail(\''+data1.id+'\')" style="width:80px;">查看详情</a>';
				}
			}else if(column.field == "createTime" || column.field == "updateTime"||column.field == "appointmentTime"){
				if(e.value)
					e.cellHtml = mini.formatDate(new Date(e.value),'yyyy-MM-dd HH:mm') ;
			}
			if(e.rowIndex == data.data.length-1){
				data = null;
			}
		});
	},
	detail:function(id){
		console.log(iwac.appointment);
		iwac.appointment.detailPanel.open({
			title:'约会详情',
			appointmentId:id,
			ondestroy:function(){
				iwac.common.refresh();
			}
		});
	},
	/**
	 * 搜索
	 */
	search:function(){
		var form = new mini.Form("#search");
		var data1 = form.getData(true);
		var json = mini.encode(data1);
		/*因为接口原因，如果userId为空，就是查找的与经纪人对应的约会，
		      所以在userId为空的时候，userId为-1，这样经纪人与用户对应起
		      来的约会肯定就查不到*/
		data1.userId = userId||-1
		this.grid.load(data1);
	},
	/**
	 * 重置
	 */
	reset:function(){
		var form = new mini.Form("#search");
		form.reset();
	}
});


$(function(){
	iwac.appointment.init();
});