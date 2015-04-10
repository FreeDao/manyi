(function($){
	mini.parse();
	var form = new mini.Form("form");
	var path = $("body".attr("path"));
	$.fn.user = {
		submit:function(){
			var data = form.getData();
			form.validate();
			if(form.isValid() == false)return ;
			var jsonData = mini.encode([data]);
			$.ajax({
				url:path+"/",
				data:{data:jsonData},
				cache:false,
				timeout:60000,
				success:function(result){
					
				},
				error:function(xhr,ts){
					if(ts == "timeout"){
						//超时操作
					}else{
						//请求失败
					}
				}
			});
		},
		test:function(){
			
		}
	}
	
})(jQuery)