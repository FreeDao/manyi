<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="../static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="../static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<script src="../static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/common.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerForm.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/ligerCheckBoxList.js" type="text/javascript"></script>
		<script type="text/javascript">
			var id=<%=request.getAttribute("id")%>+"";
			var menuno=<%=request.getAttribute("menuno")%>+"";
			var cdn=<%=request.getAttribute("cdn")%>;
			var epg=<%=request.getAttribute("epg")%>;
			var industry=<%=request.getAttribute("industry")%>;
			var tblcompany=<%=request.getAttribute("tblCompany")%>;
			var enabled='<%=request.getAttribute("enabled")%>';
			var disabled='<%=request.getAttribute("disabled")%>';
		</script>
</head>
<body>
		<form id="mainform" class="addEditManiForm" method="post" action="update.do" style="margin-left: 30px;" class="l-custom-checkboxlist"></form>
		<div class="l-clear"></div>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
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
							{name:"did",type:"hidden"},
							{display:"账号",name:"daccount",newline:false,space:LG.spaceWidthB,validate:{onlyGraphemeOrNum:[5,16]},group:"基本信息",groupicon:groupicon,options:{value:tblcompany.daccount,disabled:true}},
							{display:"地区",name:"dareano",newline:false,options:{value:tblcompany.dareano},validate:{NUMBER:[3,4]}},
							{display:"名称",name:"dname",newline:true,space:LG.spaceWidthB,options:{value:tblcompany.dname},validate:{REQUERELENGTH:[4,32]}},
							{display:"地址",name:"daddress",newline:false,options:{value:tblcompany.daddress},validate:{ADDRESS:[0,32]}},
							{display:"联系方式",name:"dtel",newline:true,space:LG.spaceWidthB,options:{value:tblcompany.dtel},validate:{TELPHONE:[4,20]}},
							{display:"行业类别",name:"dindustrytype",newline:false,type:"select",comboboxName:"industrytype",options:{valueFieldID:"dindustrytype",data:industry,value:tblcompany.dindustrytype}},
							{display:"存储空间",name:"dstoragespace",newline:true,space:LG.spaceWidthB,options:{value:tblcompany.dstoragespace},validate:{INT:[5,100]}},
							{display:"CDN资源类型",name:"dcdntype",newline:false,type:"select",comboboxName:"cdntype",options:{valueFieldID:"dcdntype",data:cdn,value:tblcompany.dcdntype}},
							{display:"关联EPG",name:"depgtype",newline:true,space:LG.spaceWidthB,type:"select",comboboxName:"epgtype",options:{valueFieldID:"depgtype",data:epg,value:tblcompany.depgtype}},
							{display:"状态",name:"dstatus",newline:false,type:"select",comboboxName:"status",options:{valueFieldID:"dstatus",selectBoxHeight:60,data:LG.statusTypeData,value:tblcompany.dstatus}},
							{display:"启用时间",name:"denabledtime",newline:true,space:LG.spaceWidthB,type:"date",options:{showTime:true,format:"yyyy-MM-dd hh:mm:ss",value:enabled},validate:{required:true}},
							{display:"停用时间",name:"ddisabledtime",newline:false,type:"date",options:{showTime:true,format:"yyyy-MM-dd hh:mm:ss",value:disabled},validate:{required:true}},
							{display:"资费",newline : true, name : "dgame" ,width:330, options : { value : tblcompany.dgame}},						
						],
						toJSON:JSON2.stringify
				   });
				    $('input:checkbox').ligerCheckBox();
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				   if (LG.getQueryStringByName("id")) {
						this.mainform.attr("action", "update.do?id="+tblcompany.did);
						//this.loadForm();
				   } else {
						this.mainform.attr("action", "save.do");
				   }
				},
				save : function() {
					var begintime=document.getElementById("denabledtime").value;
					var endtime=document.getElementById("ddisabledtime").value;
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
		            LG.submitForm(this.mainform, function (data) {
		            	var win = parent || window;
	                	if (data.iserror) {  
		                    win.LG.showError(data.message);
		                }
		                else {
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
					LG.loadForm(this.mainform, {url:"loadModify.do?id="+id,preID:'tblCompany\\.'},function(data){
				
					});
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
			
			$(document).ready(function(){
				var dganme = $("#dgame").val();
				  //加载 资费类型
			  var vodtypes =  crForm.loadgame();
			  
				var str = '';
				if(vodtypes != null && vodtypes.length >0){
					for(var i = 0 ; i<vodtypes.length ; i++){
						var row =vodtypes[i];
						str +='<span style="white-space: nowrap; float:left; "><label><input type="checkbox" name="dgame" value="'+row.did+'" ';
						if(dganme.indexOf(row.did) >= 0){
							str += ' checked = "checked" ';
						}
						str += '/>'+row.dname+' &nbsp;&nbsp;</label></span>';
					}
				}else{
					str ="暂无类型";
				}
				$("#dgame").parent("div").parent("li").html(str);
			});
		</script>
		<script src="../static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="../static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		<br/>
		<br/>
		<br/>
		<br/>
	</body>
</html>