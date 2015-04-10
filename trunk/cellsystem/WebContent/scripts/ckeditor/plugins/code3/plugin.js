  
	
  var click3= {
        exec:function(editor){
            alert("这是自定义按钮3");
        }
    };

CKEDITOR.plugins.add(
	 "code3",
    {
        requires:["dialog"],
        lang:["zh-cn"],
        init:function (a)
        {
            a.addCommand("code3", click3);
	            a.ui.addButton(
                "code3",
	            {
                   label:"EPG模版3",
	               command:"code3",
				   icon:this.path+'moban3.png'
                });
	           // CKEDITOR.dialog.add("code", this.path + "dialogs/code.js");
	        }
	 });