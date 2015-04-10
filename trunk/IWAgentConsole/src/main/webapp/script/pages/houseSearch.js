/**
 * @author carvink
 */
window.iwac = window.iwac||{};
iwac.house=$.extend(iwac.house||{},{
	/**
	 * 初始化页面以及数据
	 */
	init:function(){
		mini.parse();
		var district = this.district = mini.get("district");
		district.select(0);
		this.town = mini.get("town");
		//this.initTwown();
		
		var grid = this.grid = mini.get("datagrid1");
		grid.load();
		var decorateType = {
			0:'',
			1:'毛胚',
			2:'简单装修',
			3:'精装修',
			4:'豪华装修'
		}
		grid.on("load",function(e){
			var responseText = e.xhr.responseText;
			if(iwac.common.checkLogin(responseText)){
				iwac.common.fwd($("body").attr("path"));
			}
		});
		
		/*var bizStatus = {
			1:"找房中",
			2:"已租房",
			3:"不需要租房"
		};
		*/
		grid.on("drawcell",function(e){
			if(e.column.field == "decorateType"){
				e.cellHtml = decorateType[e.value];
			}
		});
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
		this.district.select(0);
	},
	districtValueChange:function(){
		this.town.load($('body').attr('path')+"house/area/list.do?parentId="+this.district.getValue());
		this.town.select(0);
	},
	cancel:function(){
		if(window.CloseOwnerWindow){
			window.CloseOwnerWindow('close');
		}
	},
	submit:function(){
		var rows =  this.grid.getSelecteds();
		if(rows.length>0){
			if(window.CloseOwnerWindow){
				window.CloseOwnerWindow(rows);
			}
		}else{
			mini.alert("请至少选择一个房源");
		}
	}
	
});


$(function(){
	iwac.house.init();
})