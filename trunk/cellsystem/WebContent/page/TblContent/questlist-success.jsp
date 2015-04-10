<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String categoryType=request.getAttribute("categoryType")+"";
String menuno=request.getAttribute("menuno")+"";
String dtype=request.getAttribute("dtype")+"";
String contentid=request.getAttribute("contentid")+"";

Object updatecontObj=request.getAttribute("updatecont");
String updatecont="";
if(updatecontObj!=null){
	updatecont=updatecontObj+"";
}
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		
		
		<style type="text/css">
			.l-grid-body .l-grid-body2 .l-scroll{
				width:853px;			
			}
			.l-grid-header .l-grid-header2{
				width:853px;
			}
		</style>
	</head>
	<body>
		<div style="margin-left: 30px;" class="detail-middle l-form">
			<dl class="l-custom-tab">
			
			<!-- //修改 培训内容, 可以关联考卷
						path1='TblContent/modifyexistcontent.do?dtype=<%=dtype%>&categoryType=<%=categoryType%>&IsView=1&menuno='+LG.getPageMenuNo() + '&id=' + selecteds[0].did; -->
						
				<dt>
					<%if(!"".equals(updatecont)){ %>
					<a href="modifyexistcontent.do?dtype=<%=dtype%>&categoryType=<%=categoryType %>&menuno=<%=menuno %>&id=<%=contentid %>&contentid=<%=contentid %>" >培训内容</a>
					<%}else{ %>
					<a href="addexistcontent.do?dtype=<%=dtype%>&categoryType=<%=categoryType %>&menuno=<%=menuno %>">新增内容</a>
					<%} %>
				</dt>
				<dd>
				<!-- 
				<a href="addrelation.do?dtype=<%=dtype%>&categoryType=<%=categoryType %>&menuno=<%=menuno %>&contentid=<%=contentid %>">关联试卷</a>
				 -->
				 <a href="javascript:void(0);">关联试卷</a>
				</dd>
			</dl>
			<div id="switch-btn" class="switch-btn">
				<input  type="radio" value="0" name="switch" id="rbtnl_0" url="addrelation.do?dtype=<%=dtype%>&categoryType=<%=categoryType %>&menuno=<%=menuno %>&contentid=<%=contentid %>" /><label for="rbtnl_0">新建试卷</label>
				<input type="radio" value="1" name="switch" id="rbtnl_1" url="" checked="checked"/><label for="rbtnl_1">关联已有试卷</label>
			</div>
			
			<div>
					关联考卷:
								
					<%
					Object tblcpid=request.getAttribute("categoryname");
					Object categoryname=request.getAttribute("categoryname");
					Object papername=request.getAttribute("papername");
					Object paperid=request.getAttribute("paperid");
					if(papername == null){
				%>
					<a href="javascript:void(0);" onclick="relevancePaper();" id='groombtn' style="margin-right:10px; margin-left:6px;">关联考卷</a>
					<a href="javascript:void(0);" onclick="deletePaper();" id='groombtn2' style="margin-right:20px; display: none;" >删除</a>
					<input type="hidden" id="tblcpid"/>
					<input type="hidden" id="groomid"/>
					<span id="groomshow"></span>
				<%
					}else{
				%>
					<a href="javascript:void(0);" onclick="relevancePaper();" id='groombtn' style="margin-right:10px; margin-left:6px;">修改</a> &nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" onclick="deletePaper();" id='groombtn2' style="margin-right:20px;">删除</a>
					<input type="hidden" id="tblcpid" value="<%=tblcpid == null ? "" : tblcpid %>"/>
					<input type="hidden" id="groomid" value="<%=paperid == null ? "" : paperid%>"/>
					<span id="groomshow"> <%=categoryname ==null ? "在线考试" : categoryname%>  >> <%= papername%> </span>
					
				<%} %>
			</div>
			<div id="maingrid"></div>
			
		
		</div>	
		<br />
		<br />
		<br />
		<script type="text/javascript">
		
			var updatecont='<%=updatecont%>';
			
			/*
				关联 考试考卷
			*/
			function relevancePaper(){
				crForm.groom();
				document.getElementById("rbtnl_0").disabled = true;
				
			}
			
			/*
			删除已有的关联 			
			*/
			function deletePaper(){
			   if(updatecont != ''){
					//修改的话. 需要把关联关系删除
					$.post(
						"<%=path%>/TblContentpaper/delete.do",
						{'query.dcontentid':<%=contentid%>},
						function(data){
							//alert(data);
							if(data.iserror){
								return;
							}
						},
						"json"
					);
				}
				$("#tblcpid").val('');
				$("#groomid").val('');
				$("#groombtn").html("关联考卷");
				$("#groomshow").html('');
				$("#groombtn2").hide();
				//$("#rbtnl_0').attr("disabled",false);
				document.getElementById("rbtnl_0").disabled = false;
			
			}
			

			var crForm = {
				main : "#maingrid",
				formsearch : "#formsearch",
				init : function() {
					var self = this;
					$("#switch-btn input").ligerRadio();
					$("#switch-btn input").change(function(){
						if ($(this).attr("checked")){
							document.location.href = $(this).attr("url");
						}
					});
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				},
				groom:function() {
					//弹出 选择 考卷的 页面弹框
					if (window.win1) {
						window.win1.show();
					} else {
				  		var self = this;
						var panle = $("<div></div>"),gridPanle = $("<div style='width:700px;'></div>");
						var search = $('<div class="openWinSearch"><form class="l-form"></form></div>');
						panle.append(search);
						panle.append(gridPanle);
						this.wingrid = gridPanle.ligerGrid( {
							columns: [
								{display:"考卷名称",name:"dname",width:'220',isSort:false},
								{display:"类型",name:"dtype",width:'80',render: LG.contentType,isSort:false},
								{display:"上传时间",name:"duploadtime",width:'150',isSort:false},
								//{display:"上传人",name:"duploadperson",width:'120',isSort:false},
								{display:"状态",name:"dstatus",width:'100',isSort:false,render: LG.check}
						   ],
							pageSize : 20,checkbox: true,
							url : '<%=path%>/TblContent/loadExist.do',width : '98%',
							height:"230"
						});
						self.search($("form", search), this.wingrid);
						window.win1 = $.ligerDialog.open( {
							title : "加入分类", width:640,height:370,top:10,
							cls : "l-custom-openWin",
							target : panle,
							buttons : [ {
								text : '选择',
								onclick : function (item, dialog){
									self.dialog = dialog;
									self.addRows(self);
								}
							}, {
								text : '取消',
								onclick : function(item, dialog) {
									self.hideWingrid(dialog);
								}
							} ]
						});
						$(".l-dialog-btn-inner:first",".openWinSearch").unbind('click');
						$(".l-dialog-btn-inner:first",".openWinSearch").click(function(){
							//document.getElementById('query.dname').value='';
							$("input",".openWinSearch").val('');
							$(".l-dialog-btn-inner:last",".openWinSearch").click();
							//wingrid.loadData();
						});
					}
				},
				addRows : function(self) {
				 	var row = self.wingrid.getSelectedRows();
            		if (!row) {LG.tip(LG.INFO.ONE); return false; }
	                if (row.length>1) { LG.tip(LG.INFO.TWO); return false; }
	                
	                //var yy = $.ligerui.get("categoryid");
					//		var c = yy.get("text");
	                
	                //得到考卷的id
	                $("#groomid").val(row[0].dmapping);
	                $("#groomshow").html( " <%=categoryname ==null ? "在线考试" : categoryname%> >> "+row[0].dname);
	                $("#groombtn").html("修改");
	                $("#groombtn2").show();//显示删除按钮
	                
					self.dialog.hide();
				},
				hideWingrid : function(d) {
					d.hide();
				},
				search : function(search,grid) {
					var self = this;
					jQuery(search).ligerForm({
				        inputWidth: LG.inputWidthA,
				        labelWidth:LG.labelWidthB,
						fields:[
						  {display:"考卷名称",name:"query.dname",newline:true,type:"text",attr:{"op":"like"}}
						]
					});
					LG.appendSearchButtons(search, grid ,false,true);
				},
				save : function() {
	                 //添加的问卷跟内容 进行关联
	                 if($("#groomid").val()!=null && $("#groomid").val()!=''){
						var contentdata={'tblContentpaper.dcontentid':<%=contentid%>,'tblContentpaper.dpaperid':$("#groomid").val()};
						///TblContentpaper/save.do  该方法, 在添加之前会 判断. 存在dcontentid的话. 就会去修改这条记录,不会新增一条记录
						$.post(
							"<%=path%>/TblContentpaper/save.do",
							contentdata,
							function(data){
								var win = parent || window;
								if(data.iserror){
									 win.LG.showError(data.message);
									return ;
								}else{
									 win.categoryType=<%=categoryType%>;
									 win.dtype=<%=dtype%>;
									 //alert("win.categoryType:"+win.categoryType+" win.dtype:"+ win.dtype);
									 win.LG.showSuccess(LG.INFO.SIX,null,1000);
									  win.LG.closeCurrentTab(null);
		            	 			 win.loadlist();
		                        	 //win.LG.closeAndReloadParent(null, <%=menuno%>);
								}
							},
							"json"
						);
					}else{
						//LG.tip('请选择考卷.');
						var win = parent || window;
						win.categoryType=<%=categoryType%>;
						 win.dtype=<%=dtype%>;
						// alert("win.categoryType:"+win.categoryType+" win.dtype:"+ win.dtype);
						 win.LG.showSuccess(LG.INFO.SIX,null,1000);
						 win.LG.closeCurrentTab(null);
		            	 win.loadlist();
	                     //win.LG.closeAndReloadParent(null, <%=menuno%>);
					}
				},
				cancel : function(item) {
					 var win = parent || window;
					 win.categoryType=<%=categoryType%>;
					 win.dtype=<%=dtype%>;
	            	win.LG.closeCurrentTab(null);
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crForm.init();
			
			$(document).ready(function(){
				//初始化的时候. 判断 当前的页面是  修改 页面 还是  新增页面
				if(updatecont!=''){
					//修改页面
					if($("#groomid").val()!=null && $("#groomid").val()!=''){
						//$("#rbtnl_0').attr("disabled",true);
						document.getElementById("rbtnl_0").disabled = true;
					}else{
						//$("#rbtnl_0').attr("disabled",false);
						document.getElementById("rbtnl_0").disabled = false;
					}
				}else{
					//$("#rbtnl_0').attr("disabled",false);
					document.getElementById("rbtnl_0").disabled = false;				
				}
				
			});
			
		</script>
	</body>
</html>

