iwac.common.ns("iwac.user.login");
/**
 * @author carvink
 */
iwac.user.login = {
	/**
	 * 初始化页面以及数据
	 */
	init:function(){
		$("#mobile").focus();
		this.path = $("body").attr("path");
		this.logined();
		$("#loginButton").click(function(){
			iwac.user.login.submit();
		});
		
		$("#form").keypress(function(e){
			if(e.keyCode == 13){
				iwac.user.login.submit();
			}
		});
	},
	/**
	 * 提交登录
	 * @return {Boolean}
	 */
	submit:function(){
		var mobile = $("#mobile"),password = $("#password");
		if($.trim(mobile.val()) == ''){
			mini.alert('手机号不能为空');
			return false;
		}else if($.trim(password.val()) == ''){
			mini.alert('密码不能为空');
			return false;
		}
		iwac.common.mask("正在登录...");
		var path = this.path;
		$.ajax({
			url:path+"agent/login.do",
			data:{
				mobile:mobile.val(),
				password:password.val()
			},
			type:'post',
			success:function(data){
				iwac.common.unmask();
				console.log(data);
				if(data){
					if(data.errorCode == 0){
						iwac.common.fwd(path);
					}else{
						mini.alert(data.message);
					}
				}else{
					mini.alert("手机号或者密码错误");
				}
			},
			error:function(){
				iwac.common.unmask();
				mini.alert("登录失败，请重试");
			},
			dataType:'json',
			timeout:'60000'
		});
		return false;
	},
	/**
	 * 检测是否登录过
	 */
	logined:function(){
		var path = this.path;
		$.post($("body").attr("path")+"agent/logined.do",null,function(data){
			if(data){
				if(data.errorCode == 0){
					iwac.common.fwd(path);
				}
			}
		});
	}
};


$(function(){
	iwac.user.login.init();
});