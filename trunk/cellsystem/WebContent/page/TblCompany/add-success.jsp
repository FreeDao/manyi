<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="../static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<script src="../static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/common.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerForm.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerCheckBoxList.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			var menuno=<%=request.getAttribute("menuno")%>+"";
			var category=<%=request.getAttribute("category")%>;
			var cdn=<%=request.getAttribute("cdn")%>;
			var epg=<%=request.getAttribute("epg")%>;
			var industry=<%=request.getAttribute("industry")%>;
		</script>
</head>
<body>
		<form id="mainform" class="addEditManiForm" method="post" action="save.do" style="margin-left: 30px;" class="l-custom-checkboxlist"></form>
		<div class="l-clear"></div>
		<script type="text/javascript">
				var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main);  
					var groupicon = "<%=path%>/static/xcloud/css/img/icon/communication.gif";
				    this.mainform.ligerForm({
				        labelAlign: "right", 
				        inputWidth: LG.inputWidthA,
				        labelWidth:LG.labelWidthE,				        
				        fields : [
							{display:"账号",name:"tblCompany.daccount",newline:false,space:LG.spaceWidthB,validate:{onlyGraphemeOrNum:[5,16],ONLYONE:'onlyone.do'},group:"基本信息",groupicon:groupicon},
							{display:"密码",name:"password",initValue:"123456",newline:false,validate:{onlyGraphemeOrNum:[6,16]},options:{value:123456}},
							{display:"地区区号",name:"tblCompany.dareano",newline:true,space:LG.spaceWidthB,validate:{NUMBER:[3,4]}},
							{display:"名称",name:"tblCompany.dname",newline:false,validate:{REQUERELENGTH:[4,32]}},
							{display:"地址",name:"tblCompany.daddress",newline:true,space:LG.spaceWidthB,validate:{ADDRESS:[0,32]}},
							{display:"联系方式",name:"tblCompany.dtel",newline:false,validate:{TELPHONE:[4,20]}},
							{display:"行业类别",name:"dindustrytype",newline:true,space:LG.spaceWidthB,type:"select",comboboxName:"IndustryType",options:{valueFieldID:"tblCompany.dindustrytype",data:industry,value:industry[0].id}},
							{display:"存储空间",name:"tblCompany.dstoragespace",newline:false,validate:{INT:[5,100]}},
							{display:"CDN资源类型",name:"dcdntype",newline:true,space:LG.spaceWidthB,type:"select",comboboxName:"cdntype",options:{valueFieldID:"dcdntype",data:cdn,value:cdn[0].id}},
							{display:"关联EPG",name:"depgtype",newline:false,type:"select",comboboxName:"epgtype",options:{valueFieldID:"depgtype",data:epg,value:epg[0].id}},
							{display:"启用时间",name:"tblCompany.denabledtime",newline:true,space:LG.spaceWidthB,type:"date",options:{showTime:true,format:"yyyy-MM-dd hh:mm:ss"},validate:{required:true}},
							{display:"停用时间",name:"tblCompany.ddisabledtime",newline:false,type:"date",options:{showTime:true,format:"yyyy-MM-dd hh:mm:ss"},validate:{required:true}},
							{display: "栏目设置", name:"category",width:330,type: "checkBoxList",options:{data:category,textField:'name',
								onRendered:function(){
									var g = this;
									var p = this.options;
									var data = [];
									$.each(this.data, function() {
										data.push(this.id);
									})
									this.setValue(data.join(p.split));
				   					$('input:checkbox').ligerCheckBox();
							}}},
							{display:"资费",newline:false, name : "dtype" ,width:330},
							{display:"状态",labelAlign:"right",name:"dstatus",space:LG.spaceWidthB,labelWidth:LG.labelWidthE,width:LG.inputWidthA,type:"select",comboboxName:"status",options:{valueFieldID:"dstatus",selectBoxHeight:60,data:LG.statusTypeData,value:1}}
						]
				   });
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				   
				   //加载 资费类型
				  var vodtypes =  this.loadgame();
				  
					var str = '';
					if(vodtypes != null && vodtypes.length >0){
						for(var i = 0 ; i<vodtypes.length ; i++){
							var row =vodtypes[i];
							str +='<span style="white-space: nowrap;float:left; "><label><input type="checkbox" name="tblCompany.dgame" value="'+row.did+'"/>'+row.dname+' &nbsp;&nbsp;&nbsp;</label></span>';
						}
					}else{
						str ="暂无类型";
					}
					$("#dtype").parent("div").parent("li").html(str);
					
				},
				save : function() {
					var begintime=document.getElementById("tblCompany.denabledtime").value;
					var endtime=document.getElementById("tblCompany.ddisabledtime").value;
					var now=new Date();
					var nowtime=now.Format("yyyy-MM-dd hh:mm:ss");
					//if(begintime<nowtime||endtime<nowtime){
					//	LG.tip("启用或停用时间不能早于当前时间");
					//	return;
					//}
					
					if(endtime<begintime){
						LG.tip("停用时间不能早于启用时间");
						return;
					}
					var cate=document.getElementById("category").value.split(";");
					if(cate.length<4){
						LG.tip('栏目不能小于4个');
						return;
					}					
					var win = parent || window;
		            LG.submitForm(this.mainform, function (data) {		            	
	                	if (data.iserror) {  
		                    LG.tip(data.message);
		                }else {
		                    win.LG.showSuccess(LG.INFO.SIX, null,800);
		                    win.LG.closeAndReloadParent(null, menuno);
		                }
		            });
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				loadForm : function() {
					LG.loadForm(this.mainform, {url:""},function(){});
				},
				loadgame : function(){
					//加载资费
					var jsons = '';
					$.ajax({
						async:false,
						data:{"query.pageSize" :20 },
						url:"<%=path%>/TblGame/getData.do?_t="+(+(new Date())),
						dataType:"json",
						success:function(data){
							jsons = data.rows;
						},
						error:function(data){
						}
					});
					return jsons;
				}
			}
			crForm.init();
			
		</script>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
	</body>
</html>