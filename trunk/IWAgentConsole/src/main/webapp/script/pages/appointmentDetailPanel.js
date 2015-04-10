iwac.common.ns("iwac.appointment.detailPanel");
iwac.appointment.detailPanel = $.extend(iwac.appointment.detailPanel||{},{
	open:function(param){
		param = $.extend({
			width:'1000',
			height:'600'
		},param);
		mini.open({
			url:$('body').attr("path")+"appointment/apmDetail.do?appointmentId="+param.appointmentId,
			title:param.title,
			width:param.width,
			height:param.height,
			showMaxButton:true,
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