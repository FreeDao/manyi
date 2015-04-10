var iwac = {
	init:function(){
		mini.parse();
		var grid = this.grid = mini.get("datagrid1");
		grid.on("load",function(){
			grid.mergeColumns([
				"name",
				"phoneNumber",
				"userType",
				"action"
			]);
			
		})
		grid.load();
		var userType = {
			1:"新用户",
			2:"老用户"
		},
		applyType = {
			1:"申请约看",
			2:"申请改期",
			3:"取消约看"
		}
		grid.on("drawcell",function(e){
			var record = e.record,
			field = e.field,
			value = e.value,
			column = e.column;
			
			if(column.field == "userType"){
				e.cellHtml = userType[e.value];
			}
			
			if(column.field == "applyType"){
				e.cellHtml = applyType[e.value];
			}
			
			if(column.name == "action"){
				e.cellHtml = '<a class="mini-button"  onclick="onClick" style="width:80px;">处 理</a>';
			}
			
			if(field == "communityName"){
				e.cellHtml = '<a href="javascript:;" target="_block">'+value+'</a>';
			}
		});
	} 
	
}

$(function(){
	iwac.init();
})