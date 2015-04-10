window.iwac = window.iwac||{};
iwac.housePanel = $.extend(iwac.housePanel||{},{
	open:function(param){
		param = $.extend({
			width:'1000',
			height:'600'
		},param)
		mini.open({
			url:$('body').attr("path")+"pages/houseSearch.jsp",
			title:param.title,
			width:param.width,
			height:param.height,
			onload:function(){
				if($.isFunction(param.onload)){
					param.onload.call(param.scope||this);
				}
			},
			ondestroy:function(rows){
				if($.isFunction(param.ondestroy)){
					param.ondestroy.call(param.scope||this,rows);
				}
			}
		});
	}
});