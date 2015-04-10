/*
 * 加载 数据字典/地区
 */
function loadlist(listName,parentId){
	var url1= "/main/loadlist.rest";
	if(listName.indexOf("area") >=0 ){
		url1 = "/main/loadarealist.rest";
	}
	var resultData={};
	$.ajax( {
		url : url1,
		data : {listName:listName,parentId:parentId},
		async:false,
		success : function(data) {
			resultData = data;
		},
		error : function(data) {
			
		}
	});
	return resultData;
}


