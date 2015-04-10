window.iwac = window.iwac||{};
iwac.common = {
	/**
	 * 名字空间
	 * @param {String} ns
	 * @return {}
	 */
	namespace:function(ns){
		var objects = ns.split(".");
		var object = window[objects[0]] = window[objects[0]]||{};
		Array.prototype.slice
		$.each(objects.splice(1,objects.length),function(){
			object = object[this] =object[this]||{}; 
		});
		return object;
	},
	/**
	 * namespace的简写
	 * @param {String} ns
	 * @return {}
	 */
	ns:function(ns){
		return this.namespace(ns);
	},
	refresh:function(win){
    	win = win || window;
		win.location.href = win.location.href;
    },
    forward:function(url,win){
    	win = win || window;
		win.location.href = url;
    },
    fwd:function(url,win){
    	this.forward(url,win);
    },
    /**
     * 检测content中是否包含需要登录的信息（解决ajax请求中，未登录需要跳转到登陆页）
     * @param {} content 后端返回给前端的返回值（特指登录页面的源码）
     * @return {}
     */
    checkLogin:function(content){
    	var checkItem = /<input\s*type\s*=\s*'hidden'\s*value\s*=\s*'__login__'\s*\/>/;
    	return checkItem.test(content);
    },
	rootPath:function(){
		
	},
	mask:function(text){
		mini.mask({
			el:document.body,
			cls:'mini-mask-loading',
			html:text
		});
	},
	unmask:function(){
		mini.unmask();
	}
}


