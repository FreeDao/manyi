
  var click1= {
        exec:function(editor){
			//alert(document.getElementById("txt").value);
			//var data=  CKEDITOR.instances.txt.getData();//得到当前的Cheditor编辑器中的内容
			//alert(data);
			//startHttpReq("","txt");
			$.ajax({
				url:"",
				dataType:"text",
				success:function(data){},
				error:function(data){}
			});
        }
    };
	

CKEDITOR.plugins.add(
	 "code",
    {
        requires:["dialog"],
        lang:["zh-cn"],
        init:function (a)
        {
            a.addCommand("code", click1);
	            a.ui.addButton(
                "code",
	            {
                   label:"EPG模版1",
	               command:"code",
				   icon:this.path+'moban1.png'
                });
	           // CKEDITOR.dialog.add("code", this.path + "dialogs/code.js");
	        }
	 });
	 