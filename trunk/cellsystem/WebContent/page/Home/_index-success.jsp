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
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
	</head>
	<body>
	<div class="detail-middle l-form" style="margin-left: 30px;">
			<div class="l-group l-group-hasicon"><img src="<%=path %>/static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span></div>
			<table id="detail" class="detail-tableSkin detail-tableSkinA" width="60%">
				<tr>
					<th style="width: 60px">模板名称：</th>
					<td class="depgname"></td>
					<!-- 
					<th style="width: 50px">分辨率：</th>
					<td class="depgnmb"></td>
					 -->
					
					<th style="width: 40px">状态：</th>
					<td class="dstatus"></td>
					<td class="did" style="display: none;" id="epgid"></td>
				</tr>
			</table>
		</div>
		<div class="detail-middle l-form" style="margin-left: 30px;">
			<div class="l-group l-group-hasicon"><img src="<%=path %>/static/xcloud/css/img/icon/communication.gif" /><span>内容信息</span></div>
			
			
				<div class="i-forum i-forum-focus i-forum-icon3">
				<div class="i-item">
					<div class="left">
						<a href="javascript:void(0)" class='title'>公告信息</a>
						<div class="l-clear"></div>
						<a href="javascript:submitAffiche(this)" id='btn0'>提交</a>
					</div>
					<div class="right">
						<input type='hidden' id='did0' value=""/>
						<textarea class="l-textarea" id="affiche"></textarea>
						<div class="gonggao" id='showaffiche'><div class="inner"></div><div class="v">&nbsp;</div></div>
					</div>
					<div style="display: none;">
						<input type='hidden' id='did0' value=""/>
						公告信息：<input type='text' value="" id="affiche" style="height: 35; width: 500px;" /> <button value="提交" onclick="submitAffiche(this)" id='btn0'>提交</button>
					</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			
			<div class="i-forum i-forum-focus i-forum-icon1">
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(1)" id='btn1'>关联海报</a>
						<!-- 
						<a href="javascript:viewpic(1)" id='view1'>查看</a>
						 -->
						<a href="javascript:delpic(1)" id='del1'>删除</a>
					</div>
					<div class="right"  id='url1'>
						
					</div>
				</div>
				<div class="l-clear"></div>
				<div class="i-item">
					<div class="left">
						<a href="javascript:showcategory(1)" id='btn1_2'>关联栏目</a>
					</div>
					<div class="right" id='categoryname1'>
						栏目名称
					</div>
					<div id='categoryid1' style='display:none;'></div>
					<div id='did1' style='display:none;'></div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			<div class="i-forum i-forum-focus i-forum-icon1">
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(2)" id='btn2'>关联海报</a>
						<!-- 
						<a href="javascript:viewpic(2)" id='view2'>查看</a>
						 -->
						<a href="javascript:delpic(2)" id='del2'>删除</a>
					</div>
					<div class="right"  id='url2'>
						
					</div>
				</div>
				<div class="l-clear"></div>
				<div class="i-item">
					<div class="left">
						<a href="javascript:showcategory(2)" id='btn2_2'>关联栏目</a>
					</div>
					<div class="right" id='categoryname2'>
						栏目名称
					</div>
					<div id='categoryid2' style='display:none;'></div>
					<div id='did2' style='display:none;'></div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			<div class="i-forum i-forum-focus i-forum-icon1">
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(3)" id='btn3'>关联海报</a>
						<!-- 
						<a href="javascript:viewpic(3)" id='view3'>查看</a>
						 -->
						<a href="javascript:delpic(3)" id='del3'>删除</a>
					</div>
					<div class="right"  id='url3'>
						
					</div>
				</div>
				<div class="l-clear"></div>
				<div class="i-item">
					<div class="left">
						<a href="javascript:showcategory(3)" id='btn3_2'>关联栏目</a>
					</div>
					<div class="right" id='categoryname3'>
						栏目名称
					</div>
					<div id='categoryid3' style='display:none;'></div>
					<div id='did3' style='display:none;'></div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			<div class="i-forum i-forum-focus i-forum-icon1">
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(4)" id='btn4'>关联海报</a>
						<!-- 
						<a href="javascript:viewpic(4)" id='view4'>查看</a>
						 -->
						<a href="javascript:delpic(4)" id='del4'>删除</a>
					</div>
					<div class="right"  id='url4'>
						
					</div>
				</div>
				<div class="l-clear"></div>
				<div class="i-item">
					<div class="left">
						<a href="javascript:showcategory(4)" id='btn4_2'>关联栏目</a>
					</div>
					<div class="right" id='categoryname4'>
						栏目名称
					</div>
					<div id='categoryid4' style='display:none;'></div>
					<div id='did4' style='display:none;'></div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			
	</div>
	
	<div id="result" style="display: none;"></div>
	
	<script type="text/javascript">
		var filetype='';
		var index='';
	
	/*
		添加 公告信息
	*/
	function submitAffiche(obj){
		var affiche=$("#affiche");
		if(affiche.val() == ''){
			LG.tip('请输入公告信息');
			$("#affiche").focus();
		}else{
			if($("#btn0").html() == '修改'){
				$("#btn0").html("保存");
				$("#showaffiche").hide();
				$("#affiche").show();
				return;
			}
			var data= "{'title':'"+affiche.val()+"','picurl':'','id':'','sequence':0,'type':'text'}";
			var pars={ "home.did":$("#did0").val()
							,"home.depgid":$("#epgid").html()
							,"home.dsequence":0
							,"home.dstatus":1
							,"home.ddata":data
							};
			$.post(
				"save.do"
				,pars
				,function(data){
					if(!data.iserror){
						var row=data.data;
						$("#did0").val(row.did);
						$("#btn0").html("修改");
						$("#showaffiche").show();
						$("#affiche").hide();
						$("#showaffiche>.inner").html(affiche.val());
					}
				}
				,"json"
			);
		}
	}
		/*
			加载 海报 按钮
		*/
		function showpic(i){
			index=i;
			filetype=1;//图片
			crFrom.add();
		}
		
		/*
			删除 按钮
		*/
		function delpic(i){
			$("#btn"+i).html('关联海报');
			$("#btn"+i+"_2").html("关联栏目");
			//$("#view"+i).hide();
			$("#del"+i).hide();
			$("#url"+i).html("");
			$("#categoryid"+i).html("");
			$("#categoryname"+i).html("");
		}
		
		/*
			添加 栏目
		*/
		function showcategory(i){
			index=i;
			crFrom.showtree();
		}
		
		/*
			删除一条 记录		
		*/
		function delpic(i){
			var did=$("#did"+i).html();
			if(did != ''){
				$.post(
					"delete.do",
					{'id':did},
					function(data){
						if(data){}
					},
					"json");
			}
			$("#btn"+i).html('关联海报');
			$("#btn"+i+"_2").html("关联栏目");
			//$("#view"+i).hide();
			$("#del"+i).hide();
			$("#url"+i).html("");
			$("#categoryid"+i).html("");
			$("#categoryname"+i).html("");
			$("#did"+i).html("");
		}
		
		
			/*
				加载 选择的FTP文件的路径 
			*/
			function submitContents(value){
				//value 得到的是全路径,把IP之后的内容截取出来 入库
				var str1=value.substr(value.indexOf("//")+2);
				str1=str1.substr(str1.indexOf("/")+1);
				$("#url"+index).html(str1);
				$("#btn"+index).html('修改海报');
				//$("#view"+index).show();
				$("#del"+index).show();
				saveHome();
			}
			
			/*
				保存一条数据,
				保存(同时负责修改)
			*/
			function saveHome(){
				var categoryid = $("#categoryid"+index).html();
				var url=$("#url"+index).html();
				var categoryname = $("#categoryname"+index).html();
				if(url != '' && categoryid != ''){
					//type表示 数据类型: category: 栏目; pic: 图片 ; vod: 视频 ,text 首页公告
					var data= "{'title':'"+categoryname+"','picurl':'"+url+"','id':"+categoryid+",'sequence':"+index+",'type':'category'}";
					var pars={ "home.did":$("#did"+index).html()
									,"home.depgid":$("#epgid").html()
									,"home.dsequence":index
									,"home.dstatus":1
									,"home.ddata":data
									};
					$.post(
						"save.do"
						,pars
						,function(data){
							if(!data.iserror){
								var row=data.data;
								$("#did"+index).html(row.did);
							}
						}
						,"json"
					);
				}
			}
				
			var crFrom = {
				main : "#detail",
				init : function() {
					this.loadDetail($(this.main));
				},
				loadDetail : function(d) {
					LG.loadDetail(d, {url : "<%=path%>/TblEpginfo/findEpgByCompanyId.do"}, this.callback);
				},
				callback : function(data) {
					data = data|| {};
					for ( var p in data) {
						var ele = $("[class=" + p + "]", detail);
						if (ele.length > 0) {
							if(p == 'dstatus'){
								if(data[p] == 1){
									ele.html("启用");
								}else{
									ele.html('禁用');
								}
							}else{
								ele.html(data[p]);
							}
						}
					}
					initHome();
				},
				add : function() {
					$("#result").empty();
					$("#result").load('<%=path%>/FTPFileList/ftplist.do?type='+filetype);
				},
				showtree : function() {
					var self =this;
					if (window.win) {
						window.win.show();
					} else {
						self.ftpwin = $.ligerDialog.open( {
							title : "选择栏目", width:400,height:460,top:20,
							cls : "l-custom-openWin",
							url : '<%=path%>/TblCategory/tree.do',
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
					}
				},
				addRows : function(self) {
				 	//alert(self.dialog.frame.tree.getChecked());
				 	var rows=self.dialog.frame.tree.getChecked();
				 	if (rows.length == 0) { LG.tip(LG.INFO.ONE); return; }
	                if (rows.length>1) { LG.tip(LG.INFO.TWO); return; }
	                var row=rows[0].data;
	                $("#categoryname"+index).html(row.text);
	                var categoryid=row.url.substring(row.url.indexOf("categoryType=")+"categoryType=".length,row.url.length);
	                $("#btn"+index+"_2").html('修改栏目');
	                $("#categoryid"+index).html(categoryid);
	                saveHome();
					self.dialog.hide();
				},
				hideWingrid : function(d) {
					d.hide();
				}
			}
			crFrom.init();
			
			function initHome(){
				var epgid=$("#epgid").html();
				$.post(
					"<%=path%>/Home/loadIndex.do",
					{'epgid':epgid},
					function(data){
							cleardata();
							var rows= data.rows;
							for(var j = 0; j < rows.length ; j++){
								var row = eval("("+rows[j].ddata+")");
								if(rows[j].dsequence == 0){
									$("#did0").val(rows[j].did);
									$("#affiche").val(row.title);
									$("#affiche").hide();
									$("#showaffiche>.inner").html(row.title);
									$("#showaffiche").show();
									$("#btn0").html("修改");
								}else{
									var i=row.sequence;
									$("#btn"+i).html('修改海报');
									$("#btn"+i+"_2").html("修改栏目");
									//$("#view"+i).show();
									$("#del"+i).show();
									$("#url"+i).html(row.picurl);
									$("#categoryid"+i).html(row.id);
									$("#categoryname"+i).html(row.title);
									$("#did"+i).html(rows[j].did);
								}
							}
					},
					"json"
				);
			}
			
			function cleardata(){
				$("#did0").val("");
				$("#affiche").val("");
				$("#btn0").html("提交");
				$("#affiche").show();
				$("#showaffiche").hide();
				$("#showaffiche>.inner").html("");
				for(var i =1; i <5 ; i++){
					$("#btn"+i).html('关联海报');
					$("#btn"+i+"_2").html("关联栏目");
					//$("#view"+i).hide();
					$("#del"+i).hide();
					$("#url"+i).html("");
					$("#categoryid"+i).html("");
					$("#categoryname"+i).html("");
				}
			}
		</script>
	</body>
</html>
