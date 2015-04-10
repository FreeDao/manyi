/**
 * @author carvink
 */
window.iwac = window.iwac||{};
iwac.recommend=$.extend(iwac.recommend||{},{
	/**
	 * 初始化页面以及数据
	 */
	init:function(){
		mini.parse();
		this.path = $("body").attr("path");
		//推荐历史列表
		var grid = this.grid = mini.get("datagrid1");
		//看房单
		var grid2 = this.grid2 = mini.get("datagrid0");
		grid2.load({
			sortField:'iws.id',
			sortOrder:'DESC',
			userId:userId
		});
			grid.load({
			sortField:'iwr.id',
			sortOrder:'DESC',
			userId:userId
		});
		var decorateType = {
			0:'',
			1:'毛胚',
			2:'简单装修',
			3:'精装修',
			4:'豪华装修'
		},status={
			1:'已删除',
			2:'已约看',
			3:'已下架'
		},houseStatus={
			1:'出租',
			2:'出售',
			3:'即租又售',
			4:'即不租也不售'
		}
		
		
		grid2.on("load",function(e){
			var responseText = e.xhr.responseText;
			if(iwac.common.checkLogin(responseText)){
				iwac.common.fwd($("body").attr("path"));
			}
		});
		
		
		grid2.on("drawcell",function(e){
			if(e.column.field == "decorateType"){
				e.cellHtml = decorateType[e.value];
			}else if(e.column.field == "houseStatus"){
				e.cellHtml = houseStatus[e.value];
			}
		});
		
		
		grid.on("load",function(e){
			var responseText = e.xhr.responseText;
			if(iwac.common.checkLogin(responseText)){
				iwac.common.fwd($("body").attr("path"));
			}
		});
		
		
		grid.on("drawcell",function(e){
			if(e.column.field == "decorateType"){
				e.cellHtml = decorateType[e.value];
			}else if(e.column.field == "status"){
				e.cellHtml = status[e.value];
			}else if(e.column.field == "createTime"){
				if(e.value)
					e.cellHtml = mini.formatDate(new Date(e.value),'yyyy-MM-dd HH:mm') ;
			}else if(e.column.field == "houseStatus"){
				e.cellHtml = houseStatus[e.value];
			}
		});
	},
	housePanel:function(){
		iwac.housePanel.open({
			title:'添加房源',
			scope:this,
			ondestroy:function(rows){
				console.log(rows);
				if(rows == 'close')return;
				if(!userId)mini.alert("推荐用户错误，请重试");
				var houseIdArray = [];
				$.each(rows,function(){
					houseIdArray.push(this.houseId);
				});
				iwac.common.mask("正在保存数据...");
				$.ajax({
					url:this.path+"user/recommend/add.do",
					type:'post',
					data:{
						userId:userId,
						houseIds:houseIdArray.join(",")
					},
					timeout:'60000',
					success:function(data){
						iwac.common.unmask();
						if(data){
							if(data.errorCode == 0){
								if(data.message){
									mini.alert(data.message);
								}
								//刷新当前页面
								//iwac.common.refresh();
								iwac.recommend.grid2.reload();
								iwac.recommend.grid.reload();
								
							}else{
								mini.alert(data.message);
							}
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
			}
		});
	}
	
	
});


$(function(){
	iwac.recommend.init();
})