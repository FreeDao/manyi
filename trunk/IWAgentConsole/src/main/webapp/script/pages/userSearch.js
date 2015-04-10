window.iwac = window.iwac||{};
/**
 * @author carvink
 */
iwac.user = $.extend(iwac.user||{},{
	/**
	 * 初始化页面以及数据
	 */
	init:function(){
		mini.parse();
		var grid = this.grid = mini.get("datagrid1");
		grid.load();
		var bizStatus = {
			0:"找房中",
			1:"已租房",
			2:"不需要租房"
		};
		grid.on("load",function(e){
			var responseText = e.xhr.responseText;
			if(iwac.common.checkLogin(responseText)){
				iwac.common.fwd($("body").attr("path"));
			}
		});
		
		
		var data;
		grid.on("drawcell",function(e){
			var record = e.record,
			field = e.field,
			value = e.value,
			column = e.column;
			if(!data){
				data = grid.getResultObject();
			}
			var gender = {
					0:"未知",
					1:"先生",
					2:"女士"
				};
			var data1 = data.data[e.rowIndex];
			if(column.field == "bizStatus"){
				e.cellHtml = bizStatus[e.value];
			}else if(column.name == "action"){
				if(data1){
					e.cellHtml = '<a class="mini-button"  onclick="iwac.user.detail(\''+data1.id+'\',\''+data1.realName+'\')" style="width:80px;">查看详情</a>';
				}
			}else if(column.field == "createTime" || column.field == "lastLoginTime"){
				if(e.value)
					e.cellHtml = mini.formatDate(new Date(e.value),'yyyy-MM-dd HH:mm') ;
			}else if (e.column.field == "gender") {
				e.cellHtml = gender[e.value];
			}
			if(e.rowIndex == data.data.length-1){
				data=null;
			}
		});
		
	},
	detail:function(userId,realName){
		var title = "详情";
		if(realName){
			title = realName+"--"+title;
		}
		var tab = { title: title,url:$("body").attr("path")+'user/detail.do?userId='+userId,showCloseButton: true };
		var tabs = parent.tabs;
		tabs.addTab(tab);
		tabs.activeTab(tab);
	},
	/**
	 * 搜索
	 */
	search:function(){
		var form = new mini.Form("#search");
		var data1 = form.getData();
		var json = mini.encode(data1);
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
	iwac.user.init();
	/*iwac.housePanel.open({
		title:'查找房源',
		ondestroy:function(rows){
			console.log(rows);
		}
	});*/
});