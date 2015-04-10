
  var click2= {
        exec:function(editor){
            alert("这是自定义按钮2");
        }
    };
	

CKEDITOR.plugins.add(
	 "code2",
    {
        requires:["dialog"],
        lang:["zh-cn"],
        init:function (a)
        {
            a.addCommand("code2", click2);
	            a.ui.addButton(
                "code2",
	            {
                   label:"EPG模版2",
	               command:"code2",
				   icon:this.path+'moban2.png'
                });
	           // CKEDITOR.dialog.add("code", this.path + "dialogs/code.js");
	        }
	 });
	 