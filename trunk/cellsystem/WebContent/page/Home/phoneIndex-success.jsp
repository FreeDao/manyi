<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.pkit.model.TblOperator"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String indextype=request.getAttribute("indextype")+"";
TblOperator operator= (TblOperator)request.getSession().getAttribute("operator");
Long companyid = operator.getDcompanyid();//得到 用户所在企业的code
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
		
		<script type="text/javascript">
			var companyid =<%=companyid%>;
		</script>
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
					<!-- 
					<th style="width: 40px">状态：</th>
					<td class="dstatus"></td>
					 -->
					<td class="did" style="display: none;" id="epgid"></td>
				</tr>
			</table>
			<div class="layoutImg">
				<div class="item "  onclick="changeIndex(1)">
					<div class="group group-3" style="background-image:url('<%=path %>/static/xcloud/css/img/icon/tem-001.jpg');">
						<div class="seat" id='shownav1'>企业</div>
						<div class="seat"  id='shownav2' style="left: 157px">频道</div>
						<div class="seat"  id='shownav3' style="left: 267px">影视</div>
					</div>
					<div class="right">首页布局示例图</div>
				</div>
				<!-- 
					<div class="item item_focus" onclick="changeIndex(1)">
						<img src="<%=path %>/static/xcloud/css/img/icon/tem-001.jpg" />
						<div class="right">首页布局示例图</div>
					</div>
				 -->
					<div class="item" onclick="changeIndex(5)">
						<img src="<%=path %>/static/xcloud/css/img/icon/tem-002.jpg" />
						<div class="right">企业首页布局示例图</div>
					</div>
					
					<div class="item item_focus" onclick="changeIndex(10)">
						<img src="<%=path %>/static/xcloud/css/img/icon/tem-004.jpg" />
						<div class="right">手机首页布局示例图</div>
					</div>
					
					<div class="l-clear"></div>
				</div>

</div>
		
	
	
		<div class="detail-middle l-form" style="margin-left: 30px;">	
			<div class="l-group l-group-hasicon">
				<img src="<%=path %>/static/xcloud/css/img/icon/communication.gif" /><span>手机首页信息</span>
			</div>
	
			
			
			
			<div class="i-forum i-forum-focus i-forum-icon1">
			<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">1.</span>
			
				<div class="i-item">
					
					<div class="left">
					
						<a href="javascript:showpic(1)" id='btn1'>关联海报</a>
						<!-- 
						<a href="javascript:viewpic(1)" id='view1'>查看</a>
						 -->
						 &nbsp;&nbsp;
						<div style="display: none;"></div>
					</div>
					<div class="right">
						<span id='url1'></span>
						<span class="i-forum-close" onclick="delpic(1)" id='del1' style="display: none;"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				<div class="i-item">
					<div class="left">
						<a href="javascript:showcategory(1)" id='btn1_2'>关联内容</a>
					</div>
					<div class="right" id='categoryname1'>
						内容名称
					</div>
					<div id='categoryid1' style='display:none;'></div>
					<div id='did1' style='display:none;'></div>
					<div id='datatype1' style='display:none;'>content</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			
			<div class="i-forum i-forum-focus i-forum-icon1">
			<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">2.</span>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(2)" id='btn2'>关联海报</a>
						<!-- 
						<a href="javascript:viewpic(2)" id='view2'>查看</a>
						 -->
					</div>
					<div class="right" >
						<span id="url2"></span>
						<span class="i-forum-close" onclick="delpic(2)" id='del2' style="display: none;"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:showcategory(2)" id='btn2_2'>关联内容</a>
					</div>
					<div class="right" id='categoryname2'>
						内容名称
					</div>
					<div id='categoryid2' style='display:none;'></div>
					<div id='did2' style='display:none;'></div>
					<div id='datatype2' style='display:none;'>content</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			<div class="i-forum i-forum-focus i-forum-icon1">
			
			<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">3.</span>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(3)" id='btn3'>关联海报</a>
						<!-- 
						<a href="javascript:viewpic(3)" id='view3'>查看</a>
						 -->
						 &nbsp;&nbsp;&nbsp;
						<a href="javascript:delpic(1)" id='del1' style="display: none;"></a>
					</div>
					<div class="right" >
						<span id="url3"></span>
						<span class="i-forum-close" onclick="delpic(3)" id='del3' style="display: none;"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				<div class="i-item">
					<div class="left">
						<a href="javascript:showcategory(3)" id='btn3_2' >关联内容</a>
					</div>
					<div class="right" id='categoryname3'>
						内容名称
					</div>
					<div id='categoryid3' style='display:none;'></div>
					<div id='did3' style='display:none;'></div>
					<div id='datatype3' style='display:none;'>content</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			
			
			
	</div>
	<div class="l-y-line"></div>	
	
	<div id="result" style="display: none;"></div>
	
	<script type="text/javascript">
		var filetype=''; // 文件类型, 图片:1
		var index=''; //导航标志
		var type ='1'; // 加载内容 : 2 / 加载栏目 : 1
		var datatype = '' ; // 入库的 内容的 data的类型 category, content, text, ygtd,nav
	
	function changeBind(i,typ){
		//alert(i+","+type);
		type= typ;
		if(typ == 1){
			$("#btn"+i+"_2").html("关联栏目");
			$("#datatype"+i).html("category");
		}else if(type == 2){
			$("#btn"+i+"_2").html("关联内容");
			$("#datatype"+i).html("content");
		}
		$("#categoryid"+i).html("");
		$("#categoryname"+i).html("");
	}
	
/*
		 跳转不同的 首页管理
	*/
	function changeIndex(type){
		var path = '';
		if(type ==1 ){
			path="<%=path %>/Home/mainIndex.do";
		}else if(type ==5){
			path="<%=path %>/Home/companyIndex.do";
		}else if(type == 10){
			//手机首页
			path="<%=path %>/Home/phoneIndex.do";
		}
		window.location.href=path;
	}
	
	
	var showlogo = 0;
	
		/*
			加载 海报 按钮
		*/
		function showpic(i,type){
			if(type == null){
				showlogo = 0;
				datatype ='content';
			}else{
				datatype ='nav';
			}
			index=i;
			filetype=1;//图片
			crFrom.add();
		}
		
		/*
			添加 栏目/内容
		*/
		function showcategory(i,type1){
			index=i;
			if(type1 != null){
				//加载 导航区的 栏目
				crFrom.showtree();
				datatype ='nav';
			}else{
				var datatype1 = $("#datatype"+i).html();
				datatype =datatype1;
				if(datatype1 == 'category' || datatype1 == 'ygtd'){
					//加载 栏目
					crFrom.showtree();
				}else if(datatype1 == 'content'){
					//加载 内容
					crFrom.showcontent();
				}
			}
		}
		
		
		/*
			删除一条 记录		
		*/
		function delpic(i,type1){
		
			 LG.showSuccess(LG.INFO.THREE,"confirm", function (confirm) {
			 	if(confirm){
			 		var did=$("#did"+i).html();
					if(type1 == 'nav'){
						did=$("#nav_did"+i).html();
					}
					
					if(did != ''){
						$.post(
							"delete.do",
							{'id':did},
							function(data){
								if(data){}
							},
							"json");
					}
					if(type1 == 'nav'){
						$("#nav_btn"+i+"_2").html("修改导航");
						$("#nav_btn"+i).html('关联图标');
						$("#nav_url"+i).html('');
						$("#nav_categoryname"+i).html('');
						$("#nav_categoryname"+i).parent("div").show();
						$("#nav_categoryname"+i+"_val").val('');
						$("#nav_categoryname"+i+"_val").hide();
						$("#nav_did"+i).html('');
						$("#nav_del"+i).css("display",'none');
					}else{
						$("#btn"+i).html('关联海报');
						$("#btn"+i+"_2").html("关联内容");
						$("#del"+i).css("display",'none');
						$("#url"+i).html("");
						$("#categoryid"+i).html("");
						$("#categoryname"+i).html("");
						$("#did"+i).html("");
					}
			 	}
			 });
			 
			
		}
		
		
			/*
				加载 选择的FTP文件的路径 
			*/
			function submitContents(value){
				//value 得到的是全路径,把IP之后的内容截取出来 入库
				var str1=value.substr(value.indexOf("//")+2);
				str1=str1.substr(str1.indexOf("/")+1);
				if(datatype  == 'nav'){
					$("#nav_url"+index).html(str1);
					$("#nav_btn"+index).html('修改海报');
					saveHome();
				}else{
					$("#url"+index).html(str1);
					if(showlogo == 1){
						//保存 logo
						sbumitLogo(str1);
					}else{
						$("#btn"+index).html('修改海报');
						//$("#view"+index).show();
						//$("#del"+index).show();
						$("#del"+index).css("display",'inline');
						saveHome();
					}
				}
			}
			
			/*
				保存一条数据,
				保存(同时负责修改)
			*/
			function saveHome(){
			
				var datatype1= $("#datatype"+index).html();
				var categoryid = $("#categoryid"+index).html();
				var url=$("#url"+index).html();
				var categoryname = $("#categoryname"+index).html();
				var homedid=$("#did"+index).html();
				var epgid=$("#epgid").html();
				if(datatype =='nav'){
					datatype1= $("#nav_datatype"+index).html();
					categoryid = $("#nav_categoryid"+index).html();
					url=$("#nav_url"+index).html();
					categoryname = $("#nav_categoryname"+index).html();
					homedid = $("#nav_did"+index).html();
					epgid =1;
				}
				
				if( datatype == 'nav'){
					if(categoryname == '' || categoryname == null || url  =='' || url ==null){
						return;
					}
					$("#nav_del"+index).show();
				}
				
				if(url != null && categoryid != null && url != '' && categoryid != ''){
					//type表示 数据类型: category: 栏目; pic: 图片 ; vod: 视频 ,text 首页公告
					var data= "{'title':'"+categoryname+"','picurl':'"+url+"','id':'"+categoryid+"','sequence':"+index+",'type':'"+datatype1+"'}";
					var pars={ "home.did":homedid
									,"home.depgid":epgid
									,"home.dsequence":index
									,"home.dstatus":1
									,"home.dtype":10
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
					$("#result").load('<%=path%>/FTPFileList/ftplist.do?type='+filetype+"&_t="+(+(new Date())));
				},
				hideWingrid : function(d) {
					d.hide();
				},
				
				contentshow : null,
				showcontent : function(){
				
					var self =this;
					if (false) {
						self.contentshow.show();
					} else {
						var scrtop=document.documentElement.scrollTop;
						self.contentshow= $.ligerDialog.open( {
							title : "选择栏目", width:800,height:500,top:5+scrtop,
							cls : "l-custom-openWin",
							url : '<%=path%>/TblContent/content.do?content=2&type=1&_t='+(new Date()),
							buttons : [ {
								text : '选择',
								onclick : function (item, dialog){
									self.dialog1 = dialog;
									self.addRows1(self);
								}
							}, {
								text : '取消',
								onclick : function(item, dialog) {
									self.hideWingrid1(dialog);
								}
							} ]
						});
					}
					
				},
				addRows1 : function(self) {
				 	//alert(self.dialog.frame.tree.getChecked());
				 	var rows=self.dialog1.frame.contentlist.crGrid.grid.getSelecteds();
				 	if (rows.length == 0) { LG.tip(LG.INFO.ONE); return; }
	                if (rows.length>1) { LG.tip(LG.INFO.TWO); return; }
	                var row=rows[0];
	                $("#categoryname"+index).html(row.dname);
	                var categoryid=row.did;
	                $("#btn"+index+"_2").html('修改内容');
	                $("#categoryid"+index).html(categoryid);
	                $("#datatype"+index).html("content");
	                //alert( $("#categoryname"+index).html()+","+$("#categoryid"+index).html());
	                saveHome();
					self.dialog1.hide();
				},
				hideWingrid1 : function(d) {
					d.hide();
				},
				
				
			}
			crFrom.init();
			
			function initHome(){
				var epgid=$("#epgid").html();
				$.post(
					"<%=path%>/Home/loadIndex.do",
					{'epgid':epgid,"indextype":<%=indextype%>},
					function(data){
							cleardata();
							if(data != null){
							var rows= data.rows;
							for(var j = 0; j < rows.length ; j++){
								var row = eval("("+rows[j].ddata+")");
								if(row.type == 'text'){
									//跑马灯
									$("#did0").val(rows[j].did);
									$("#affiche").val(row.title);
									$("#affiche").hide();
									$("#showaffiche>.inner").html(row.title);
									$("#showaffiche").show();
									$("#btn0").html("修改");
								}else if(row.type == 'logo'){
									//logo
									$("#did1").val(rows[j].did);
									$("#url1").html(row.picurl);
									$("#btn1").html("修改LOGO");
								}else{
									var i=row.sequence;
									
									if(row.type == 'nav'){
										//导航图
										$("#nav_btn"+i+"_2").html("修改导航");
										$("#nav_btn"+i).html('修改图标');
										$("#nav_url"+i).html(row.picurl);
										$("#nav_categoryid"+i).html(row.id);
										$("#nav_categoryname"+i).html(row.title);
										$("#nav_did"+i).html(rows[j].did);
										$("#nav_datatype"+i).html(row.type);
										if(companyid == 0){
											$("#nav_del"+i).show();
										}else{
											$("#nav_del"+i).hide();
										}
										$("#navshow"+i).html(row.title);
									}else{
										//页面中间的内容
										if(row.type =='category' || row.type == 'ygtd'){
											$("#btn"+i+"_2").html("修改栏目");
											$("#type"+i).attr("checked","checked");
											$("#type"+i+"_2").removeAttr("checked");
										}else if(row.type == 'content'){
											$("#btn"+i+"_2").html("修改内容");
											$("#type"+i+"_2").attr("checked","checked");
											$("#type"+i).removeAttr("checked");
										}
										$("#btn"+i).html('修改海报');
										//$("#view"+i).show();
										//$("#del"+i).show();
										$("#del"+i).css("display",'inline');
										$("#url"+i).html(row.picurl);
										$("#categoryid"+i).html(row.id);
										$("#categoryname"+i).html(row.title);
										$("#did"+i).html(rows[j].did);
										$("#datatype"+i).html(row.type);
									}
									
								}
							}
						}
					},
					"json"
				);
			}
			
			function cleardata(){
				
				//中间 内容
				for(var i =1; i <4 ; i++){
					$("#btn"+i).html('关联海报');
					$("#btn"+i+"_2").html("关联内容");
					//$("#view"+i).hide();
					//$("#del"+i).hide();
					$("#del"+i).css("display",'none');
					$("#url"+i).html("");
					$("#categoryid"+i).html("");
					$("#categoryname"+i).html("");
					$("#datatype"+i).html("content");
				}
			}
			
		</script>
	</body>
</html>
