﻿(function($) {
	window['LG'] = {};
	LG.statusTypeData = [{ text: '启用', id:1},{ text: '停用', id:0}];
	LG.statusTypeDataL = [{ text: '全部', id:-1},{ text: '启用', id:1},{ text: '停用', id:0}];
	LG.contentstatusTypeData=[{ text: '未审核', id:1},{ text: '待上线', id:5},{ text: '分发中', id:7},{ text: '已上线', id:10},{ text: '已下线', id:15}];
	LG.haschildTypeData=[{text:"是父节点",id:1},{text:'不是父节点',id:0}];
	LG.genderData=[{text:"男",id:1},{text:'女',id:0}];
	LG.dtermainalData=[{text:'电视',id:1},{text:'移动设备',id:5},{text:'PC/电脑',id:10}];
	LG.phoneTypeData =[{text : '图片', id :1 },{text : '视频', id :2 },{text : '文字', id : 3}];
	LG.gamekeydata =[{text : '按照企业月次收费', id :1 },{text : '按照用户月次收费', id :2 }];
	
	
	/**
		把后台的 0:党务公开;1:党群工作 数据转化为 [{text:'电视',id:1},{text:'移动设备',id:5},{text:'PC/电脑',id:10}] 格式
	*/
	LG.getjsonbyenum = function(menustr){
		var str="[";
		var strs=menustr.split(";");
		for(var i =0; i<strs.length ; i++){
			var temps=strs[i].split(":");
			str +="{'id':"+temps[0]+",'text':'"+temps[1]+"'}";
			if(i < strs.length-1){
				str+=",";
			}
		}
		str +="]";
		return eval("("+str+")");
	}
	
	/*
		data: 数据源
		_key: 从数据源中的取出的字段
		_json:  json数据
	*/
	LG.getstrbyjson = function(data,_key,_json){
		if(_json != null && _json.length>0){
			for(var i =0 ; i < _json.length ; i++ ){
				if(_json[i].id == data[_key]){
					return _json[i].text;
				}
			}
		}else{
			return "";
		}
	}
	
	
	
	// 转换状态
	LG.check= function(data) {
		if (data.dstatus == 1) {
			return '未审核';
		} else if (data.dstatus == 5) {
			return '待上线';
		}else if(data.dstatus == 7){
			return "分发中";
		}else if(data.dstatus == 10){
			return "已上线";
		}else if(data.dstatus == 15){
			return "已下线";
		}
	}
	LG.paytype= function(data) {
		if (data.dpaytype == 1) {
			return '月结';
		} else if (data.dpaytype == 0) {
			return '年结';
		}
	}
	LG.status = function(data) {
		if (data.dstatus== 0) {
			return '停用';
		} else if (data.dstatus == 1) {
			return '启用';
		}
	}
	LG.phoneType=function(data){
		if (data.dtype == 1) {
			return '图文';
		} else if (data.dtype == 2) {
			return '视频';
		} else if(data.dtype == 3){
			return "文字";
		}
	}
	
	LG.roleType = function(data) {
		if (data.dtype== 1) {
			return '云平台角色';
		}else if (data.dtype== 2) {
			return '企业角色';
		}
	}
	LG.operate = function(data) {
		if (data.doperatetype== 1) {
			return '查看';
		} else if (data.doperatetype == 2) {
			return '新增';
		}else if (data.doperatetype == 4) {
			return '修改';
		}else if (data.doperatetype == 8) {
			return '删除';
		}else if (data.doperatetype == 16) {
			return '启用';
		}else if (data.doperatetype == 32) {
			return '停用';
		}else if (data.doperatetype == 64) {
			return '审核';
		}else if (data.doperatetype == 128) {
			return '上线';
		}else if(data.doperatetype == 256){
			return '下线';
		}
	}
	LG.result = function(data) {
		if (data.doperateresult== 0) {
			return '失败';
		} else if (data.doperateresult == 1) {
			return '成功';
		}
	}
	LG.loginresult=function(data){
		if (data.dresult== 0) {
			return '失败';
		} else if (data.dresult == 1) {
			return '成功';
		}
	}
	LG.logintype=function(data){
		if (data.dtype== 1) {
			return '登陆';
		} else if (data.dtype == 2) {
			return '退出';
		}
	}
	LG.sex = function(data) {
		if (data.dgender == 0) {
			return '女';
		} else if (data.dgender == 1) {
			return '男';
		}
	}
	LG.contentType = function(data) {
		if (data.dtype == 1) {
			return '图文';
		} else if (data.dtype == 2) {
			return '视频';
		}
	}
	LG.INFO = {
		ONE : "请选择操作项!",
		TWO : "不支持多个操作!",
		THREE : "确定删除?",
		FOUR : "操作执行成功",
		FIVE : "正在删除中...",
		SIX : "保存成功",
		SEVEN:'已是启用状态',
		EIGHT:'已是停用状态',
		NINE:'确定启用吗?',
		TEN:'确定停用吗?',
		ELEVEN:"操作成功"
	}
	
	//单选 按钮 和复选按钮 数据切换事件
	$.extend($.ligerMethos.RadioList, {
			_getText : function() {
				var g = this, p = this.options;
				var val = g._getValue(), text = [];
				$.each(g.data, function() {
					if (this[p.valueField] == val) {
						text.push(this[p.textField])
					}
				});
				return text.join(",");
			},
			_rendered : function() {
				if (this.element) $(this.element).attr("ligeruiid", this.id);
				var g = this, p = this.options;
				if (p.change) {
					g.radioList.click(function(e) {
						var value = g.getValue();
						if (value) p.change(value);
					});
				}
			}
		});
		
		
	// 格式化时间
	LG.YMDDate = function(data) {
		var A = new Date(data.LL);
        return A.getFullYear() + "-" + (A.getMonth() + 1) + "-" + A.getDay();
	}
	// 显示loading
	LG.showLoading = function(message) {
		message = message || "On Loading...";
		$('body').append("<div class='jloading'>" + message + "</div>");
		$.ligerui.win.mask();
	};
	// 隐藏loading
	LG.hideLoading = function(message) {
		$('body > div.jloading').remove();
		$.ligerui.win.unmask( {
			id : new Date().getTime()
		});
	}
	//显示成功|正在保存窗口
	LG.showSuccess = function(message,type,speed) {
		if (type == "confirm" || type == "closeConfirm") {
			$.ligerDefaults.DialogString.yes = "确定";
			$.ligerDefaults.DialogString.no = "取消";
			$.ligerDefaults.Dialog = {cls : "l-custom-confirm " + type,width:380,modal: true}
			return $.ligerDialog.confirm(message, speed);
		} else {
			var manager = $.ligerDialog.open( {
				cls : "l-custom-win l-custom-" + (type || "submit"),
				content : "<div class=\"win-img\">" + (message || '保存成功!') + "</div>",
				allowClose : false
			})
			if (speed != undefined) {
				setTimeout(function() {
					manager.close();
				}, speed || 1000);
			}
			return manager;
		}
	};
	// 显示失败提示窗口
	LG.showError = function(message, callback) {
		if (typeof (message) == "function" || arguments.length == 0) {
			callback = message;
			message = "Operation failed!";
		}
		$.ligerDialog.error(message, 'Prompt information', callback);
	};
	
	//右下角的提示框
	LG.tip = function(message,speed) {
		clearTimeout(LG.tip.te);
		if (LG.wintip) {
			LG.wintip.set('content', message + "<span class='custom-close' onclick='LG.tip.hide()'></span>");
			LG.tip.show();
		} else {
			LG.wintip = $.ligerDialog.tip( {
				cls : "l-custom-tip",
				content : message + "<span class='custom-close' onclick='LG.tip.hide()'></span>",
				height:37,
				width:"100%"
			});
			LG.tip.show();
		}
		LG.tip.te = setTimeout(function() {
			LG.tip.hide();
		}, speed||4000);
	};
	LG.tip.show = function(_h){
		$(LG.wintip.element).animate({height: _h||37}, 500);
	}
	LG.tip.hide = function(_h){
		clearTimeout(LG.tip.te);
		$(LG.wintip.element).animate({height:  _h||0}, 500);
	}
	LG.getPageMenuNo = function() {
		var menuno = $("#MenuNo").val();
		if (!menuno) {
			menuno = getQueryStringByName("MenuNo");
		}
		return menuno;
	};
	LG.getChildMenuNo = function() {
		var num = arguments.callee.num || 0;
		arguments.callee.num = ++num;
		return LG.getPageMenuNo() + "_" + num;
	};
	
	//根据QueryString参数名称获取值
	function getQueryStringByName(name) {
		var result = location.search.match(new RegExp(
				"[\?\&]" + name + "=([^\&]+)", "i"));
		if (result == null || result.length < 1) {
			return "";
		}
		return result[1];
	}
	LG.getQueryStringByName = function(name){
		return getQueryStringByName(name);
	}
	LG.loadToolbar = function(grid, barClick, tableCode,permissionValue) {
		var operateButton=[{
	        "btnicon": "../../css/img/icon/jui-icon-view.png",
	        "btnid": 1,
	        "btnname": "查看",
	        "btnno": "view",
	        "menuno": "t_series",
	        "seqno": "1"
	    },{
	        "btnicon": "../../css/img/icon/jui-icon-add.png",
	        "btnid": 2,
	        "btnname": "新增",
	        "btnno": "add",
	        "menuno": "t_series",
	        "seqno": "1"
	    }, {
	        "btnicon": "../../css/img/icon/jui-icon-edit.png",
	        "btnid": 4,
	        "btnname": "修改",
	        "btnno": "modify",
	        "menuno": "t_series",
	        "seqno": "1"
	    }, {
	        "btnicon": "../../css/img/icon/jui-icon-del.png",
	        "btnid": 8,
	        "btnname": "删除",
	        "btnno": "del",
	        "menuno": "t_series",
	        "seqno": "1"
	    },{
	        "btnicon": "../../css/img/icon/jui-icon-on.png",
	        "btnid": 16,
	        "btnname": "启用",
	        "btnno": "on",
	        "menuno": "t_series",
	        "seqno": "1"
	    },{
	        "btnicon": "../../css/img/icon/jui-icon-off.png",
	        "btnid": 32,
	        "btnname": "停用",
	        "btnno": "off",
	        "menuno": "t_series",
	        "seqno": "1"
	    },{
	        "btnicon": "../../css/img/icon/jui-icon-verify.png",
	        "btnid": 64,
	        "btnname": "审核",
	        "btnno": "verify",
	        "menuno": "t_series",
	        "seqno": "1"
	    },{
	        "btnicon": "../../css/img/icon/jui-icon-verify.png",
	        "btnid": 128,
	        "btnname": "上线",
	        "btnno": "online",
	        "menuno": "t_series",
	        "seqno": "1"
	    },{
	        "btnicon": "../../css/img/icon/jui-icon-verify.png",
	        "btnid": 256,
	        "btnname": "下线",
	        "btnno": "offline",
	        "menuno": "t_series",
	        "seqno": "1"
	    }];
		if (!grid.toolbarManager)
			return;
		if (!operateButton || !operateButton.length)
			return;
		var items = [];
		if((permissionValue&1)==1){
			var o = operateButton[0];
			items[items.length] = {
				click : barClick,
				text : o.btnname,
				id : o.btnno,
                img: o.btnicon,
				disable : true
			};
		}
		if((permissionValue&2)==2){
			var o = operateButton[1];
			items[items.length] = {
				click : barClick,
				text : o.btnname,
				id : o.btnno,
                img: o.btnicon,
				disable : true
			};
		}
		if((permissionValue&4)==4){
			var o = operateButton[2];
			items[items.length] = {
				click : barClick,
				text : o.btnname,
				id : o.btnno,
                img: o.btnicon,
				disable : true
			};
		}
		if((permissionValue&8)==8){
			var o = operateButton[3];
			items[items.length] = {
				click : barClick,
				text : o.btnname,
				id : o.btnno,
                img: o.btnicon,
				disable : true
			};
		}
		if((permissionValue&16)==16){
			var o = operateButton[4];
			items[items.length] = {
				click : barClick,
				text : o.btnname,
				id : o.btnno,
                img: o.btnicon,
				disable : true
			};
		}
		if((permissionValue&32)==32){
			var o = operateButton[5];
			items[items.length] = {
				click : barClick,
				text : o.btnname,
				id : o.btnno,
                img: o.btnicon,
				disable : true
			};
		}
		if((permissionValue&64)==64){
			var o = operateButton[6];
			items[items.length] = {
				click : barClick,
				text : o.btnname,
				id : o.btnno,
                img: o.btnicon,
				disable : true
			};
		}
		if((permissionValue&128)==128){
			var o = operateButton[7];
			items[items.length] = {
				click : barClick,
				text : o.btnname,
				id : o.btnno,
                img: o.btnicon,
				disable : true
			};
		}
		if((permissionValue&256)==256){
			var o = operateButton[8];
			items[items.length] = {
				click : barClick,
				text : o.btnname,
				id : o.btnno,
                img: o.btnicon,
				disable : true
			};
		}
		grid.toolbarManager.set('items', items);
	};
    LG.ajax = function(options) {
    	var p = options || {};
    	var ashxUrl = options.ashxUrl || "Action/System/ajaxhandle.do?";
    	var url = p.url || ashxUrl + $.param( {
    		type : p.type,
    		method : p.method
    	});
    	$.ajax( {
    		cache : false,
    		async : true,
    		url : url,
    		data : p.data,
    		dataType : 'json',
    		type : 'post',
    		beforeSend : function() {
    			LG.loading = true;
    			if (p.beforeSend)
    				p.beforeSend();
    			else
    				LG.showLoading(p.loading);
    		},
    		complete : function() {
    			LG.loading = false;
    			if (p.complete)
    				p.complete();
    			else
    				LG.hideLoading();
    		},
    		success : function(result) {
    			if (!result)
    				return;
    			if (result.errorCode == 0) {
    				if (p.success)
    					p.success(result.data == null ? result :result.data, result.message);
    			} else {
    				if (p.error)
    					p.error(result.message);
    			}
    		},
    		error : function(result, b) {
    			LG.tip('System Error Error Message：' + result.status);
    		}
    	});
    };
    
    
    LG.setFormDefaultBtn = function(cancleCallback, savedCallback) {
    	// 表单底部按钮
    	var buttons = [];
    	if (cancleCallback) {
    		buttons.push({ text: '关闭',className:"cancle", onclick: cancleCallback });
    	}
    	if (savedCallback) {
    		buttons.push({ text: '保存', onclick: savedCallback });
    	}
    	LG.addFormButtons(buttons);
    };
   // 增加表单底部按钮,比如：保存、取消
    LG.addFormButtons = function(buttons) {
    	if (!buttons)
    		return;
    	var formbar = $("body > div.form-bar");
    	if (formbar.length == 0){
    		formbar = $('<div class="form-bar"><div class="form-bar-inner"></div></div>').appendTo('body');
    	}
    	if (!(buttons instanceof Array)) {
    		buttons = [ buttons ];
    	}
    	if (buttons.length<2){
    		$(".form-bar-inner", formbar).css({width:"80px"});
    		buttons[0].text ="关闭";
    	}
    	$(buttons).each(
    		function(i, o) {
    			 var btn = $('<div class="l-dialog-btn"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner"></div></div> ');
    	            $("div.l-dialog-btn-inner:first", btn).html(o.text || "BUTTON");
    			if (o.onclick) {
    				btn.bind('click', function() {
    					o.onclick(o);
    				});
    			}
    			if (o.width) {
    				btn.width(o.width);
    			}
    			if (o.className) {
    				btn.addClass(o.className);
    			}
    			$("> div:first", formbar).append(btn);
    	});
    };
    LG.submitForm = function(mainform, success, error) {
    	if (!mainform)
    		 mainform = (typeof mainform == "string")? $(mainform) : mainform;
    	if (mainform.valid()) {
    		mainform.ajaxSubmit( {
    					dataType : 'json',
    					success : success,
    					beforeSubmit : function(formData, jqForm, options) {
    					$(":checkbox,:radio", jqForm).each(function() {
    						if (!existInFormData(formData, this.name)) {
    							formData.push( {
    								name : this.name,
    								type : this.type,
    								value : this.checked
    							});
    						}
    					});
    					for ( var i = 0, l = formData.length; i < l; i++) {
    						var o = formData[i];
    						if (o.type == "checkbox" || o.type == "radio") {
    							//o.value = $("[name=" + o.name + "]", jqForm)[0].checked ? "true": "false";
    						}
    					}
    				},
    				beforeSend : function(a, b, c) {
    					LG.showLoading('Saving...');
    				},
    				complete : function() {
    					LG.hideLoading();
    				},
    				error : function(result) {
    					LG.tip('System error Error code：' + result.status);
    				}
    			});
    	} else {
    		LG.showInvalid();
    	}
    	function existInFormData(formData, name) {
    		for ( var i = 0, l = formData.length; i < l; i++) {
    			var o = formData[i];
    			if (o.name == name)
    				return true;
    		}
    		return false;
    	}
    };
  //提示 验证错误信息
    LG.showInvalid = function (validator)
    {
        validator = validator || LG.validator;
        if (!validator) return;
        var message = '<div class="invalid">there are  '+ validator.errorList.length +' field verification do not pass, please check!</div>';
        $.ligerDialog.error(message);
    };
    
    
    //填充表单数据
    LG.loadForm = function (mainform, options, callback)
    {
        options = options || {};
        if (!mainform)
            mainform = (typeof mainform == "string")? $(mainform) : mainform;
            if (mainform == null){ return;}
        	var p = $.extend({
            beforeSend: function ()
            {
                LG.showLoading('Loading form data...');
            },
            complete: function ()
            {
                LG.hideLoading();
            },
            success: function (data)
            {
                var preID = options.preID || "";
                //根据返回的属性名，找到相应ID的表单元素，并赋值
                for (var p in data)
                {
                    var ele = $("[name=" + (preID + p) + "]", mainform);
                    //针对复选框和单选框 处理
                    if (ele.is(":checkbox,:radio"))
                    {
                        ele[0].checked = data[p] ? true : false;
                    }
                    else
                    {
                        ele.val(data[p]);
                    }
                }
                //下面是更新表单的样式
                var managers = $.ligerui.find($.ligerui.controls.Input);
                for (var i = 0, l = managers.length; i < l; i++)
                {
                    //改变了表单的值，需要调用这个方法来更新ligerui样式
                    var o = managers[i];
                    o.updateStyle();
                    if (managers[i] instanceof $.ligerui.controls.TextBox)
                        o.checkValue();
                }
                removeFocus();
                if (callback)
                    callback(data);
            },
            error: function (message)
            {
                LG.showError('Data loading failed!<BR>Error Message：' + message);
            }
        }, options);
        LG.ajax(p);
    };
    LG.loadDetail = function (detail, options, callback){
    	
        options = options || {};
        if (!detail)
            detail = detail || $("table:first");
        	var p = $.extend({
        		dataType : 'json',
	            beforeSend: function (){
	                LG.showLoading('Loading form data...');
	            },
	            complete: function (){
	                LG.hideLoading();
	            },
	            success: function (data){
	            	if (callback) {
	            		callback(data);
	            	} else {
	            		data = data|| {};
	            		for ( var p in data) {
	            			var ele = $("[class=" + p + "]", detail);
	            			if (ele.length > 0) {
	            				ele.html(data[p]);
	            			}
	            		}
	            	}
	            },
	            error: function (message){
	                LG.showError('Data loading failed!<BR>Error Message：' + message);
	            }
	        }, options);
        	if (options.data){
        		if (callback) {
            		callback(options.data);
            	} else {
            		_data = options.data || {};
            		for ( var p in _data) {
            			var ele = $("[class=" + p + "]", detail);
            			if (ele.length > 0) {
            				ele.html(_data[p]);
            			}
            		}
            	}
        	}else {
        		LG.ajax(p);
        	}
    };
    // 关闭Tab项,如果tabid不指定，那么关闭当前显示的
    LG.closeCurrentTab = function(tabid) {
    	if (!tabid) {
    		tabid = $("#framecenter > .l-tab-content > .l-tab-content-item:visible").attr("tabid");
    	}
    	if (tab) {
    		tab.removeTabItem(tabid);
    	}
    };
    
    // 关闭Tab项并且刷新父窗口
    LG.closeAndReloadParent = function(tabid, parentMenuNo) {
    	window.isClose = true;
    	LG.closeCurrentTab(tabid);
    	tab.selectTabItem(parentMenuNo);
    	tab.reload(parentMenuNo);
    	/**
    	var menuitem = $("#mainmenu ul.menulist li[menuno=" + parentMenuNo + "]");
    	var parentTabid = menuitem.attr("tabid");
    	var iframe = window.frames[parentTabid];
    	if (tab) {
    		tab.selectTabItem(parentTabid);
    	}
    	if (iframe && iframe.f_reload) {
    		iframe.f_reload();
    	} else if (tab) {
    		tab.reload(parentTabid);
    	}
    	*/
    };
  //附加表单搜索按钮：搜索、高级搜索
    LG.appendSearchButtons = function (form, grid ,newline,flag)
    {
        if (!form) return;
        form = $(form);
        var container;
        if (newline){
        	container = $('<ul><li></li></ul><div class="l-clear"></div>').appendTo(form);
        } else {
        	if ($("ul:last",form).length>0){
        		container = $("ul:last",form).append("<li style='padding-left:5px'>");
        	} else {
        		container = $(form).append("<li style='padding-left:5px'>");
        	}
        }
        LG.addSearchButtons(form, grid, container.find("li:last"), container.find("li:last"),flag);
    };

    //创建表单搜索按钮：搜索、高级搜索
    LG.addSearchButtons = function (form, grid, btn1Container, btn2Container,flag)
    {
        if (!form) return;
        if (btn2Container){
            LG.createButton({
                appendTo: btn2Container,
                text: '重置',
                click: function (){
                	if(flag){}else{
                		var win = parent || window;
	            		try{
	            		if (win.tab){
	            			win.tab.reload(LG.getPageMenuNo());
	            		} else {
	            			location.reload();
	            		}
	            		}catch(e){}
                	}
            		
                }
            });
        }
        if (btn1Container){
            LG.createButton({
                appendTo: btn1Container,
                text: '搜索',
                click: function () {
                	var rule = LG.bulidFilterGroup(form);
                	grid.set('parms', rule);
                	grid.loadData();
                }
            });
        }
    };
  //创建过滤规则(查询表单)
    LG.bulidFilterGroup = function (form)
    {
        if (!form) return null;
        var group = {};
        $(":input", form).not(":submit, :reset, :image,:button, [disabled]")
        .each(function ()
        {
            if (!this.name) return;
            if ($.trim($(this).val()) =="") return;
            var ltype = $(this).attr("ltype");
            var optionsJSON = $(this).attr("ligerui"), options;
//            if (optionsJSON){
//                options = JSON2.parse(optionsJSON);
//            }
            var value = $(this).val();
            var name = this.name;
            //如果是下拉框，那么读取下拉框关联的隐藏控件的值(ID值,常用与外表关联)
            if (ltype == "select" && options && options.valueFieldID)
            {
                value = $("#" + options.valueFieldID).val();
                name = options.valueFieldID;
            }
            group[name] = value;
        });
        return group;
    };
    //创建按钮
    LG.createButton = function (options)
    {
        var p = $.extend({
            appendTo: $('body')
        }, options || {});
        var btn = $('<div class="l-dialog-btn" style="margin-left:0px;float:left;"><div class="l-dialog-btn-l"> </div><div class="l-dialog-btn-r"> </div> <div class="l-dialog-btn-inner"></div></div>');
        if (p.icon)
        {
            btn.removeClass("buttonnoicon");
            btn.append('<div class="button-icon"> <img src="../' + p.icon + '" /> </div> ');
        }
        //绿色皮肤
        if (p.green)
        {
            btn.removeClass("button2");
        }
        if (p.width)
        {
            btn.width(p.width);
        }
        if (p.click)
        {
            btn.click(p.click);
        }
        if (p.text)
        {
            $("div:last", btn).html(p.text);
        }
        if (typeof (p.appendTo) == "string") p.appendTo = $(p.appendTo);
        btn.appendTo(p.appendTo);
    };
    
    LG.changeStatus = function(self,pathUrl){
			var ids = [] , rows =self.grid.getSelecteds();
            if (!rows.length) { LG.tip(LG.INFO.ONE); return }
            var loadMessage=''
            if(pathUrl.indexOf("on")>=0){
            	loadMessage="正在启用...";
            	for ( var i = 0; i < rows.length; i++) {
    				var name=rows[i].dname == null ? rows[i].name:rows[i].dname;
    				if(name==null||name==undefined){
    					name=rows[i].dusername;
    				}
    				if(name==null||name==undefined){
    					name=rows[i].dloginname;
    				}
    				if(name==null||name==undefined){
    					name=rows[i].dversionno;
    				}
    				if(rows[i].dstatus ==1){
    				 	LG.tip("["+name+"]"+LG.INFO.SEVEN);
    					return ;
    				}
    				var id=rows[i].did == null ? rows[i].id : rows[i].did;
    				ids.push(id);
    			}            	
            }else if(pathUrl.indexOf("off")>=0){
            	loadMessage="正在停用...";
            	for ( var i = 0; i < rows.length; i++) {
    				var name=rows[i].dname == null ? rows[i].name : rows[i].dname ;
    				if(name==null||name==undefined){
    					name=rows[i].dusername;
    				}
    				if(name==null||name==undefined){
    					name=rows[i].dloginname;
    				}
    				if(name==null||name==undefined){
    					name=rows[i].dversionno;
    				}
    				if(rows[i].dstatus ==0){
    				 	LG.tip("["+name+"]"+LG.INFO.EIGHT);
    					return ;
    				}
    				var id=rows[i].did == null ? rows[i].id : rows[i].did;
    				ids.push(id);
    			}
            }
			$.ajax( {
				loading : loadMessage,
				url : pathUrl,
				data : {ids : ids.join()},
				async:false,
				success : function(data) {
					if(data.message){
						var m = LG.showSuccess(data.message,null,1600);
						m._setWidth(420);
					}else{
						var resultData=LG.showSuccess(LG.INFO.FOUR,null,1000);
					}
					self.reload();
					//resultData.close();
				},
				error : function(data) {
					LG.showError(data.message);
				}
			});
    };
    
    LG.deleteData = function(self,idTxt){
    	var ids = [] ,rows=self.grid.getSelecteds();
         if (!rows.length) { LG.tip(LG.INFO.ONE); return }
         LG.showSuccess(LG.INFO.THREE,"confirm", function (confirm) {
        if (confirm){
		for ( var i = 0; i < rows.length; i++) {
			if(idTxt == null){
				ids.push(rows[i].id);
			}else{
				ids.push(rows[i][idTxt]);
			}
		}
		$.ajax( {
			url : 'delete.do',
			dataType:'json',
			data : {ids : ids.join(),dtype:self.dtype},
			success : function(data) {
				var msg=data.message;
				if(msg == undefined){
					msg=LG.INFO.FOUR;
					LG.showSuccess(msg,null,1000);
				}else{
					var m=LG.showSuccess(msg,null,1000);
					m._setWidth(380);
				}
				self.reload();
			},
			error : function(data) {
				LG.showError(data.message);
			}
			});
           }
         })
     };
     
     LG.viewSelect = function(selectdata,ele,data,p){
     /*
     	if(p=='dstatus'){
     		if(data.dstatus==1){
     			ele.html('启用');
     		}else if(data.dstatus==0){
     			ele.html('禁用');
     		}
     	}else if(p=='dtermainal'){
     		if(data.dtermainal==1){
     			ele.html('电视');
     		}else if(data.dtermainal==5){
     			ele.html('移动设备');
     		}else if(data.dtermainal==10){
     			ele.html('电脑/PC');
     		}
     	}else if(p=='dparentid'){
     		for(var i =0 ; i<50;i++){
     			if(selectdata[0]){
	     			if(selectdata[0].data[i].id==data.dparentid){
	     				ele.html(selectdata[0].data[i].text);
	     				break;
	     			}
     			}
     		}
     	}
     	*/
     	
     	if(selectdata){
     		for(var i =0; i<selectdata.data.length; i++){
     			if(selectdata.data[i].id==data[p]){
     				ele.html(selectdata.data[i].text);
     				break;
     			}
     		}
     	}else{
     		ele.html(data[p]);
     	}
     };	
    LG.inputWidthA = 200;
    LG.inputWidthB = 420;
    LG.inputWidthC = 462;
    LG.inputWidthD = 960;
    LG.inputWidthE = 494;
    LG.inputWidthF = 120;
    LG.inputWidthG = 813;
    
    LG.labelWidthA = 42;
    LG.labelWidthB = 60; 
    LG.labelWidthC = 70; 
    LG.labelWidthD = 80; 
    LG.labelWidthE = 90;
    LG.labelWidthF = 100; 
    LG.labelWidthG = 110; 
    
    LG.spaceWidthA = 10;
    LG.spaceWidthB = 170;
    
})(jQuery);
$.ligerDefaults.ComboBox.slide = false;
$.ligerDefaults.DateEditor.showTime = true;
$.ligerMethos.DateEditor = {
		_preRender : function() {
	var g = this, p = this.options, val = $(this.element).val();
	if (val) {
		if (!/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})/.test(val)) {
			try {
				val = g.getFormatDate(new Date(val));
			} catch (e) {
				val = $(this.element).val()
			}
		}
		$(this.element).val(val);
	}
}
}
$.ligerDefaults.Grid.sortnameParmName = "query.sortColumns";
$.ligerMethos.Grid = {
    changeSort: function(C, A) {
        var $ = this,
        B = this.options;
        if ($.loading) return true;
        if (B.dataAction == "local") {
            var _ = $.getColumnType(C);
            if (!$.sortedData) $.sortedData = $.filteredData;
            if (B.sortName == C) $.sortedData[B.root].reverse();
            else $.sortedData[B.root].sort(function(A, B) {
                return $._compareData(A, B, C, _)
            });
            if (B.usePager) $.currentData = $._getCurrentPageData($.sortedData);
            else $.currentData = $.sortedData;
            $._showData()
        }
//        B.sortName = C;
        B.sortName = (C||"").replace(/(^[a-zA-Z])/g, "$1_") + " " + A;
        B.sortOrder = A;
        if (B.dataAction == "server") $.loadData(B.where)
    }
}

Date.prototype.Format = function(fmt)   
{ //author: meizz   
	var o = {   
		"M+":this.getMonth()+1,                 //月份   
		"d+":this.getDate(),                    //日   
		"h+":this.getHours(),                   //小时   
		"m+":this.getMinutes(),                 //分   
		"s+":this.getSeconds(),                 //秒   
		"q+":Math.floor((this.getMonth()+3)/3), //季度   
		"S":this.getMilliseconds()             //毫秒   
	};   
	if(/(y+)/.test(fmt))   
	fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	for(var k in o)   
		if(new RegExp("("+ k +")").test(fmt))   
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	return fmt;   
} 


$(document).ready(function(){
	removeFocus();
}); 
function removeFocus(){
	$(".l-text-focus").removeClass("l-text-focus");
	$("body").focus();
	if ($.browser.mozilla) {
		document.activeElement.blur();
	} 
}


