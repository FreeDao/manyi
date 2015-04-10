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
				<div class="item item_focus"  onclick="changeIndex(1)">
					<div class="group group-3" style="background-image:url('<%=path %>/static/xcloud/css/img/icon/tem-001.jpg');">
						<div class="seat" id='shownav1'>企业</div>
						<div class="seat"  id='shownav2' style="left: 157px">频道</div>
						<div class="seat"  id='shownav3' style="left: 267px">影视</div>
					</div>
					<div class="right">首页布局示例图</div>
				</div>
				
					<div class="item" onclick="changeIndex(5)">
						<img src="<%=path %>/static/xcloud/css/img/icon/tem-002.jpg" />
						<div class="right">企业首页布局示例图</div>
					</div>
					
					<div class="item" onclick="changeIndex(10)">
						<img src="<%=path %>/static/xcloud/css/img/icon/tem-004.jpg" />
						<div class="right">手机首页布局示例图</div>
					</div>
					
					
					<div class="l-clear"></div>
				</div>

</div>
		
	
	
		<div class="detail-middle l-form" style="margin-left: 30px;">	
			<div class="l-group l-group-hasicon">
				<img src="<%=path %>/static/xcloud/css/img/icon/communication.gif" /><span>EPG首页信息</span>
			</div>
			
		<div id='nav_block'>
			<div class="i-forum i-forum-focus i-forum-icon1">
				<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">A.</span>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(1,'nav')" id='nav_btn1'>关联图标</a>
					</div>
					<div class="right" >
						<span id="nav_url1"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:shownav(1,'nav')" id='nav_btn1_2'>修改导航</a>
					</div>
					<div class="right">
						<input type='text' value="" id="nav_categoryname1_val" style="height: 26px; width: 160px;" />
						<div class="gonggao"><div class="inner"  id='nav_categoryname1'></div><div class="v">&nbsp;</div></div>
						<span class="i-forum-close" onclick="delpic(1,'nav')" id='nav_del1' style="position: absolute; right:40px; display: none;"></span>
					</div>
					<div id='nav_categoryid1' style='display:none;'>company</div>
					<div id='nav_did1' style='display:none;'></div>
					<div id='nav_datatype1' style='display:none;'>nav</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			<div class="i-forum i-forum-focus i-forum-icon1">
				<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">B.</span>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(2,'nav')" id='nav_btn2'>关联图标</a>
					</div>
					<div class="right" >
						<span id="nav_url2"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:shownav(2,'nav')" id='nav_btn2_2'>修改导航</a>
					</div>
					<div class="right">
						<input type='text' value="" id="nav_categoryname2_val" style="height: 26px; width: 160px;" />
						<div class="gonggao"><div class="inner"  id='nav_categoryname2'></div><div class="v">&nbsp;</div></div>
						<span class="i-forum-close" onclick="delpic(2,'nav')" id='nav_del2' style="position: absolute; right:40px; display: none;"></span>
					</div>
					<div id='nav_categoryid2' style='display:none;'>channel</div>
					<div id='nav_did2' style='display:none;'></div>
					<div id='nav_datatype2' style='display:none;'>nav</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			
			<div class="i-forum i-forum-focus i-forum-icon1">
				<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">C.</span>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(3,'nav')" id='nav_btn3'>关联图标</a>
					</div>
					<div class="right" >
						<span id="nav_url3"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:shownav(3,'nav')" id='nav_btn3_2'>关联导航</a>
					</div>
					<div class="right">
						<input type='text' value="" id="nav_categoryname3_val" style="height: 26px; width: 160px;" />
						<div class="gonggao"><div class="inner"  id='nav_categoryname3'></div><div class="v">&nbsp;</div></div>
						<span class="i-forum-close" onclick="delpic(3,'nav')" id='nav_del3' style="position: absolute; right:40px; display: none;"></span>
					</div>
					</div>
					<div id='nav_categoryid3' style='display:none;'>vod</div>
					<div id='nav_did3' style='display:none;'></div>
					<div id='nav_datatype3' style='display:none;'>nav</div>
				</div>
				
				
				<div class="l-clear"></div>
			</div>
			
			
			
			
			 
			 
			<!-- 
			<div class="i-forum-top">
				<div class="i-forum">
					<b class="topCor-1"></b>
					<b class="topCor-2"></b>
					<span class="No">A.</span>
					<div class="i-item">
						<div class="left">
							<a href="#">修改导航</a>
						</div>
						<div class="right">
							国窖新闻
						</div>
					</div>
					<b class="botCor-1"></b>
					<b class="botCor-2"></b>
				</div>
				<div class="i-forum">
					<b class="topCor-1"></b>
					<b class="topCor-2"></b>
					<span class="No">B.</span>
					<div class="i-item">
						<div class="left">
							<a href="#">修改导航</a>
						</div>
						<div class="right">
							培训考试
						</div>
					</div>
					<b class="botCor-1"></b>
					<b class="botCor-2"></b>
				</div>
				<div class="i-forum">
					<b class="topCor-1"></b>
					<b class="topCor-2"></b>
					<span class="No">C.</span>
					<div class="i-item">
						<div class="left">
							<a href="#">修改导航</a>
						</div>
						<div class="right">
							国窖文化
						</div>
					</div>
					<b class="botCor-1"></b>
					<b class="botCor-2"></b>
				</div>
				<div class="l-clear"></div>
			</div>
			 -->
			
			
			
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
					<!--
					<div style="display: none;">
						<input type='hidden' id='did0' value=""/>
						公告信息：<input type='text' value="" id="affiche" style="height: 35; width: 500px;" /> <button value="提交" onclick="submitAffiche(this)" id='btn0'>提交</button>
					</div>
					-->
				</div>
				<div class="l-clear"></div>
			</div>
			
			 
			 
			 <div class="i-forum">
				<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">1.</span>
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(1)" id='btn1'>关联LOGO</a>
					</div>
					<div class="right">
						<dl class="i-item-logo">
							<dt id='url1'>泸州老窖LOGO.png</dt>
							<dd>尺寸：190*100像素 </dd>
							<dd>图片格式：PNG/JPG</dd>
						</dl>
						<input type ='hidden' id ='did1'/>
						<div class="l-clear"></div>
					</div>
				</div>
				<b class="botCor-1"></b>
				<b class="botCor-2"></b>
			</div>
			
			
			<div class="i-forum i-forum-focus i-forum-icon1">
			<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">2.</span>
				
				<div class="i-item">
					<div class="left">
					<input type="radio" id='type2' name="type2" checked="checked" onchange="changeBind(2,1);"/>关联栏目 
						&nbsp;	&nbsp;
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
						<input type="radio"  id='type2_2' name="type2" onchange="changeBind(2,2);"/>关联内容
						&nbsp;&nbsp;
						<a href="javascript:showcategory(2)" id='btn2_2'>关联内容</a>
					</div>
					<div class="right" id='categoryname2'>
						内容名称
					</div>
					<div id='categoryid2' style='display:none;'></div>
					<div id='did2' style='display:none;'></div>
					<div id='datatype2' style='display:none;'>category</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			<div class="i-forum i-forum-focus i-forum-icon1">
			
			<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">3.</span>
				
				<div class="i-item">
					<div class="left">
						<input type="radio" id='type3' name="type3" checked="checked" onchange="changeBind(3,1);"/>关联栏目 
						&nbsp;
						&nbsp;
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
					<input type="radio"  id='type3_2' name="type3" onchange="changeBind(3,2);"/>关联内容
					&nbsp;&nbsp;
						<a href="javascript:showcategory(3)" id='btn3_2' >关联内容</a>
					</div>
					<div class="right" id='categoryname3'>
						内容名称
					</div>
					<div id='categoryid3' style='display:none;'></div>
					<div id='did3' style='display:none;'></div>
					<div id='datatype3' style='display:none;'>category</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			
			<div class="i-forum i-forum-focus i-forum-icon1">
			<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">4.</span>
			
				<div class="i-item">
					
					<div class="left">
						<input type="radio" id='type4' name="type4" checked="checked" onchange="changeBind(4,1);"/>关联栏目 
						&nbsp;&nbsp;
					
						<a href="javascript:showpic(4)" id='btn4'>关联海报</a>
						<!-- 
						<a href="javascript:viewpic(4)" id='view4'>查看</a>
						 -->
						 &nbsp;&nbsp;
						<div style="display: none;"></div>
					</div>
					<div class="right">
						<span id='url4'></span>
						<span class="i-forum-close" onclick="delpic(4)" id='del4' style="display: none;"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				<div class="i-item">
					<div class="left">
				<input type="radio"  id='type4_2' name="type4" onchange="changeBind(4,2);"/>关联内容
				&nbsp;&nbsp;
						<a href="javascript:showcategory(4)" id='btn4_2'>关联栏目</a>
					</div>
					<div class="right" id='categoryname4'>
						栏目名称
					</div>
					<div id='categoryid4' style='display:none;'></div>
					<div id='did4' style='display:none;'></div>
					<div id='datatype4' style='display:none;'>category</div>
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
							,"home.dtype":1
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
		添加 公告信息
	*/
	function sbumitLogo(obj){
			var data= "{'title':'','picurl':'"+obj+"','id':'','sequence':1,'type':'logo'}";
			var pars={ "home.did":$("#did1").val()
							,"home.depgid":$("#epgid").html()
							,"home.dsequence":1
							,"home.dstatus":1
							,"home.dtype":1
							,"home.ddata":data
							};
			$.post(
				"save.do"
				,pars
				,function(data){
					if(!data.iserror){
						var row=data.data;
						$("#did1").val(row.did);
						$("#btn1").html("修改LOGO");
					}
				}
				,"json"
			);
	}
	
	var showlogo = 0;
	
		/*
			加载 海报 按钮
		*/
		function showpic(i,type){
			if(type == null){
				if(i ==1){
					showlogo = 1;
				}else{
					showlogo = 0;
				}
				datatype ='';
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
		修改/保存 导航信息
		*/
		function shownav(i, type1){
			var typ = $("#nav_btn"+i+"_2").html();
			if(typ == '保存'){
				$("#nav_btn"+i+"_2").html("修改导航");
				$("#nav_categoryname"+i).parent("div").show();
				$("#nav_categoryname"+i).html($("#nav_categoryname"+i+"_val").val());
				$("#nav_categoryname"+i+"_val").hide();
				index =i;
				datatype ='nav';
				
				saveHome();
			}else{
				$("#nav_categoryname"+i).parent("div").hide();
				$("#nav_categoryname"+i+"_val").val($("#nav_categoryname"+i).html());
				$("#nav_categoryname"+i+"_val").show();
				$("#nav_btn"+i+"_2").html("保存");
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
						$("#btn"+i+"_2").html("关联栏目");
						//$("#view"+i).hide();
						//$("#del"+i).hide();
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
									,"home.dtype":1
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
				ftpwin : null,
				showtree : function() {
					var self =this;
					if (false) {
						self.ftpwin.show();
					} else {
						var scrtop=document.documentElement.scrollTop;
						//alert(scrtop);
						self.ftpwin = $.ligerDialog.open( {
							title : "选择栏目", width:400,height:460,top:20+scrtop,
							cls : "l-custom-openWin",
							url : '<%=path%>/TblCategory/tree.do?content=1&_t='+(+(new Date())),
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
	                
	         		if(datatype != 'nav'){
	         			$("#categoryname"+index).html(row.text);
		                var dtype=row.url.substring(row.url.indexOf("dtype=")+"dtype=".length,row.url.indexOf("&"));
		                var categoryid=row.url.substring(row.url.indexOf("categoryType=")+"categoryType=".length,row.url.length);
		                $("#btn"+index+"_2").html('修改栏目');
		                $("#categoryid"+index).html(categoryid);
		                if(dtype ==20){
			                $("#datatype"+index).html("ygtd");
		                }else {
			                $("#datatype"+index).html("category");
		                }
	         		}else{
						$("#nav_categoryname"+index).html(row.text);
		                var categoryid=row.url.substring(row.url.indexOf("categoryType=")+"categoryType=".length,row.url.length);
		                $("#nav_btn"+index+"_2").html('修改栏目');
		                $("#nav_categoryid"+index).html(categoryid);
	         		}
	                
	                saveHome();
					self.dialog.hide();
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
							url : '<%=path%>/TblContent/content.do?content=1&type=1&_t='+(new Date()),
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
				
				//跑马灯
				$("#did0").val("");
				$("#affiche").val("");
				$("#btn0").html("提交");
				$("#affiche").show();
				$("#showaffiche").hide();
				$("#showaffiche>.inner").html("");
				
				// logo 管理
				$("#url1").html('');
				$("#btn1").html("关联LOGO");
				
				
				//导航图管理
					for(var i = 1; i<4 ; i++){
						$("#nav_btn"+i+"_2").html("修改导航");
						$("#nav_btn"+i).html('关联图标');
						$("#nav_url"+i).html('');
						$("#nav_categoryname"+i).html('');
						$("#nav_categoryname"+i).parent("div").show();
						$("#nav_categoryname"+i+"_val").val('');
						$("#nav_categoryname"+i+"_val").hide();
						$("#nav_did"+i).html('');
					}
					
				//判断 当前的用户 是 云平台的 用户 还是 企业用户
				if(companyid != 0){
					//非  云平台 用户
					//导航图管理
					for(var i = 1; i<4 ; i++){
						$("#nav_btn"+i+"_2").css("visibility","hidden");
						$("#nav_btn"+i).css("visibility",'hidden');
						$("#nav_del"+i).hide();
					}
					//非云平台的 管理员 不需要 管理 导航区
					$("#nav_block").hide();
				}
				
				
				//中间 内容
				for(var i =2; i <5 ; i++){
					$("#btn"+i).html('关联海报');
					$("#btn"+i+"_2").html("关联栏目");
					//$("#view"+i).hide();
					//$("#del"+i).hide();
					$("#del"+i).css("display",'none');
					$("#url"+i).html("");
					$("#categoryid"+i).html("");
					$("#categoryname"+i).html("");
					$("#datatype"+i).html("category");
				}
			}
		</script>
	</body>
</html>
