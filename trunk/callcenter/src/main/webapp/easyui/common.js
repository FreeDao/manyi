

/**
 * 添加一个 上传图片控件
 */
function addCurrUpload(id,subid,currDom,uploadType,basepath,serialCode,targetId){
	var sum = $("#"+id+" .change-demo" ).size();
	for (var i = 0; i < sum; i++) {
		console.debug($("#"+id+" .change-demo" ));
		var num= $($("#"+id+" .change-demo" ).get(i)) .attr('args');
		if(num > sum) sum =num;
		
	}
	//$.messager.alert('提示',sum);
	sum++;
	var html = "<div id='"+subid+"_" + sum+ "' class='change-demo'   args='" + sum+ "'><div>" + sum+ "<img alt='' src='' style='height:160px;width:160px'></img></div>"
		+"<input type='file' name='upload'  onchange=\"uploadCommon(this,'"+basepath+"','" + uploadType +"','"+serialCode+"','"+targetId+"')\"  />"
		+ "<input type='hidden'  name='imgKey'  value=''    />"
		+"<div><span onclick='swapUp(this)'><img alt='1' src='"+basepath+"/imgs/up.png' /></span><span onclick='removeCurrUpload(this)'><a href='javascript:void(0);' >删除</a></span><span onclick='swapDown(this)'><img alt='2' src='"+basepath+"/imgs/down.png' /></span></div></div>";
	$("#"+id+" input[name='appendUploadDiv']").before(html);
}


function uploadCommon(currDom,basepath,uploadTypeArgs,serialCodeArgs,id) {
	var parentDom = $(currDom).parent();
	console.debug($(currDom).val());
	var uploadPath = $(currDom).val();
	if(uploadPath == null || uploadPath == '') {
		$.messager.alert('提示',"请选择图片");
		return ;
	} 
	var form = getForm();
	form.attr('action',basepath+'/common/uploadCommon');
	form.attr("enctype","multipart/form-data");
	form.attr("target","pic_hidden_frame");
	form.attr("method","POST");
	
	var returnFunc = document.createElement("input");
	returnFunc.setAttribute("name","returnParentFunction");
	returnFunc.setAttribute("type","hidden");
	returnFunc.setAttribute("value","callbackFunction");
	var uploadType = document.createElement("input");
	uploadType.setAttribute("name","uploadType");
	uploadType.setAttribute("type","hidden");
	uploadType.setAttribute("value",uploadTypeArgs);
	var idDom = document.createElement("input");
	idDom.setAttribute("name","domId");
	idDom.setAttribute("type","hidden");
	idDom.setAttribute("value",$(parentDom).attr('id'));
	
	var idNum = document.createElement("input");
	idNum.setAttribute("name","id");
	idNum.setAttribute("type","hidden");
	idNum.setAttribute("value", id);
	
	var serialCode = document.createElement("input");
	serialCode.setAttribute("name","serialCode");
	serialCode.setAttribute("type","hidden");
	serialCode.setAttribute("value",serialCodeArgs);
	
	form.append(returnFunc);
	form.append(uploadType);
	form.append(idDom);
	form.append(idNum);
	form.append(serialCode);
	var currCopy = $(currDom).clone(true);
	currCopy.insertBefore($(currDom));
	console.debug($(currDom));
	form.append($(currDom));
	$('body').append(form);
	form.submit();
	form.remove();
}
$(function (){
	// swap(1);
});
function callbackFunction(data) {
	if(data != null) {
		if(data.returnStatus == "error") {
			$.messager.alert('提示',data.returnDescription);
		}else if(data.returnStatus == "success") {
			//$.messager.alert('提示',data.returnLongPath)
			$($("#"+data.domId +" img").get(0)).attr("src",data.returnLongPath);
			$($("#"+data.domId +" input[name='imgKey']")).val(data.returnKey);
		}
	}else {
		$.messager.alert('提示',data);
	}
}
function swap(id_1,id_2){
	if(id_1 == null || id_2 == null){
		$.messager.alert('提示',"不可移动");
		return; 
	}
	var $div1 = $("#"+id_1);
    var $div3 = $("#"+id_2);
    var $temobj1 = $("<div></div>");
    var $temobj2 = $("<div></div>");
    $temobj1.insertBefore($div1);
    $temobj2.insertBefore($div3);
    $div1.insertAfter($temobj2);
    $div3.insertAfter($temobj1);
    $temobj1.remove();
    $temobj2.remove();

}
function swapUp(currDom){
	var parentDom = $(currDom).parent().parent();
	var up = parentDom.prev();
	//console.info(parentDom.attr('id'));
	//console.info(up.attr("id"));
	swap(parentDom.attr('id'),up.attr('id'));
}
function swapDown(currDom){
	var parentDom = $(currDom).parent().parent();
	var down = parentDom.next();
	//console.info(parentDom.attr('id'));
	//console.info(down.attr("id"));
	swap(parentDom.attr('id'),down.attr('id'));
}
function removeCurrUpload(currDom) {
	
	var parentDom = $(currDom).parent().parent();
	if(parentDom.attr("idNum") == null || parentDom.attr("idNum") == 0 || parentDom.attr("idNum") == ''){
	}else {
		//删除了 数据库中的 数据  
		parentDom.parent().prepend('<input type="hidden" class="change-demo" idNum="'+parentDom.attr("idNum")+'" value=""/>');
	}
	parentDom.remove();
	
}

function getForm(){
	var form = $("<form>");
	form.attr('style','display:none');  
	form.attr('target','');  
	form.attr('method','post');
	return form;
}



/****************添加文本框相关*********************/

//检测 输入的json 是否 重复
function checkJson(key){
	var keys = $("#"+key).find("input") ;
	var flag= true;
	keys.each(function(p){
		var v1= $(this) ;
		if( v1.val() != ''){
			keys.each(function(p){
				var v2= $(this) ;
				if(v2.val() != '' && v1.val() == v2.val() && v1.attr('id') != v2.attr("id")){
					$(this).focus();
					flag = false;
					return ;
				}
			});
			if(!flag){
				return ;
			}
		}
	});
	return flag;
}


//把input数据数组组装成json字符串
function createJsonFromInput(key,name){
	var tmp="[";
	$("#"+key).find("input").each(function(p){
		if($(this).val() != ''){
			if($(this).attr("pid")){
				tmp +='{\'id\':\''+$(this).attr('pid');
			}else{
				tmp +='{\'id\':\'';
			}
			tmp +='\',\'name\':\''+$(this).val()+'\'},';
		}
	});
	if(tmp.length>1){
		tmp = tmp.substring(0, tmp.length-1);
	}
	tmp +="]";
	$("#"+name).val(tmp);
}
//把用户操作的图片 组装成json字符串
function createJsonFromImg(key,calName,type){
	var index = 0;
	var json = "";
	$("#"+key+" ."+calName).each(function(p){
		var id = $(this).attr("idNum");
		var imgkey = '';
		var tmp = $(this).find("input[name='imgKey']");
		if(tmp && tmp.val()){
			imgkey = tmp.val();
		}
		if(id && id !='' && id !='0'){
			if(imgkey !='' && imgkey != null){
				index ++;
			}
			json +='{\'id\':\''+id+'\',\'name\':\''+imgkey+'\',\'type\':\''+type+'\',\'orderNum\':\''+index+'\'},';
		}else{
			if(imgkey !='' && imgkey != null){
				index ++;
				//当ID不空null,img也为null的时候,才可以上传提交
				json +='{\'id\':\'\' , \'name\':\''+imgkey+'\',\'type\':\''+type+'\',\'orderNum\':\''+index+'\'},';
			}
		}
	});
	if(json.length>1){
		json = json.substring(0, json.length-1);
	}
	return json;
}
//生成 input + 按钮 - 按钮
function createInputFormJson(json,key,name,basepath){
	var p =$("#"+key);
	if(json != null && json.length >0){
		for(var i=0; i<json.length;i++){
			var str= '<li><input style="width:160px;" type="text" id="'+name+(i+1)+'" value="'+json[i].name+'" pid="'+json[i].id+'"/> &nbsp; <img alt="添加" src="'+basepath+'/imgs/add.png"  onclick=\'createAddBtn(\"'+key+'\",\"'+name+'\",\"'+basepath+'\")\'/> ';
			if(i != 0){
				str +=' <img alt="删除" src="'+basepath+'/imgs/del.png" onclick=\'delAddBtn(\"'+key+'\",\"'+name+(i+1)+'\")\'/>';
			}
			str +='</li>';
			p.append(str);
		}
	}else{
		//先添加一个空的input框
		p.append('<li><input style="width:160px;" type="text" id="'+name+'1" value=""/> &nbsp; <img alt="添加" src="'+basepath+'/imgs/add.png" onclick=\'createAddBtn(\"'+key+'\",\"'+name+'\",\"'+basepath+'\")\'/></li>');
	}
}
//添加一个文本框
function createAddBtn(key,name,basepath){
	var p =$("#"+key+" li:last-child");
	var max = p.find("input").attr("id").replace(name,"");
	max = parseInt(max);
	max ++;
	var len = 30;
	if(max < len){
		p.parent("ul").append('<li><input style="width:160px;" type="text" id="'+name+max+'" value=""/> &nbsp; <img alt="添加" src="'+basepath+'/imgs/add.png" onclick=\'createAddBtn(\"'+key+'\",\"'+name+'\",\"'+basepath+'\")\'/> <img alt="删除" src="'+basepath+'/imgs/del.png"  onclick=\'delAddBtn(\"'+key+'\",\"'+name+max+'\",\"'+basepath+'\")\' /></li>');
	}
}
/**
 * 删除add按钮
 */
function delAddBtn(key,id){
	var curr = $("#"+id);
	if(curr.attr("pid") && curr.attr("pid") !=''){
		//删除了 数据库中的 数据  
		$("#"+key).prepend('<input type="hidden" id="'+curr.attr("id")+'" pid="'+curr.attr("pid")+'" value=""/>');
	}
	curr.parent("li").remove();
}
/****************添加文本框相关*********************/


