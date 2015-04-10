iwac.common.ns("iwac.user.detail");
/**
 * @author carvink
 */
iwac.user.detail = $.extend(iwac.user.detail||{},{
	/**
	 * 初始化页面以及数据
	 */
	init:function(){
		mini.parse();
		var grid = this.grid = mini.get("memoGrid");
		grid.load();
		grid.on("load",function(e){
			var responseText = e.xhr.responseText;
			if(iwac.common.checkLogin(responseText)){
				iwac.common.fwd($("body").attr("path"));
			}
		});
		grid.on("drawcell",function(e){
			var column = e.column;
			if(column.field == "createTime"){
				if(e.value)
					e.cellHtml = mini.formatDate(new Date(e.value),'yyyy-MM-dd HH:mm') ;
			}
		});
		this.path = $("head").attr("path");
		this.winUserMemoList = mini.get("winUserMemoList");
		this.winAddMemo = mini.get("winAddMemo");
		//用户备注列表窗口关闭的时候，如果添加备注窗口时显示状态，也应该随之关闭
		this.winUserMemoList.on('buttonclick',function(){
			iwac.user.detail.winAddMemo.hide();
		});
		this.winEditUserStatus = mini.get("winEditUserStatus");
	},
	showMemo:function(){
		this.winUserMemoList.show();
	},
	/**
	 * 显示添加备注弹窗
	 */
	addMemo:function(){
		this.winAddMemo.showAtEl(document.getElementById("addMemo"),{
			xAlign:'outright',
			yAlign:'top'
		});
	},
	submit:function(userId){
		console.log(window.top.agentUser);
		var content = mini.get("content").getValue();
		console.log($("head").attr("path"));
		var grid = this.grid;
		var winAddMemo = this.winAddMemo;
		iwac.common.mask("正在保存数据...");
		$.ajax({
			url:this.path+"user/memo/add.do",
			type:'post',
			data:{
				memo:content,
				userId:userId
			},
			timeout:'60000',
			success:function(data){
				iwac.common.unmask();
				if(data){
					if(data.errorCode == 0){
						grid.load();
						mini.get("content").setValue('');
						winAddMemo.hide();
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
	},
	/**
	 * 添加备注弹窗取消按钮触发事件
	 */
	cancel:function(){
		mini.get("content").setValue('');
		this.winAddMemo.hide();
	},
	editBizStatus:function(userId){
		var form =  new mini.Form("#userStatusForm");
		data = form.getData();
		data.userId = userId;
		iwac.common.mask("正在保存数据...");
		$.ajax({
			url:this.path+"user/editBizStatus.do",
			type:'post',
			data:data,
			timeout:'60000',
			success:function(data){
				iwac.common.unmask();
				console.log(data);
				if(data){
					if(data.errorCode == 0){
						window.location.href = window.location.href;
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
	},
	/**
	 * 显示编辑状态框
	 */
	showEdit:function(){
		this.winEditUserStatus.showAtEl($("#edit")[0],{
			xAlign:'outright',
			yAlign:'top'
		});
	},
	hideWinEditUserStatus:function(){
		this.winEditUserStatus.hide();
	},
	/**
	 * 重置
	 */
	reset:function(){
		var form = new mini.Form("#search");
		form.reset();
	}
	
})


$(function(){
	iwac.user.detail.init();
})