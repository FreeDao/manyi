<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.pkit.model.TblOperator"%>
<%
String path = request.getContextPath();
String indextype=request.getAttribute("indextype")+"";

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
		
	<body>
	
	
	
	<div>
			<div class="detail-middle l-form" style="margin-left: 30px;">
				<div class="l-group l-group-hasicon"><img src="<%=path %>/static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span></div>
				
				<table id="detail" class="detail-tableSkin detail-tableSkinA" width="60%">
				<tr>
					<th style="width: 60px">模板名称：</th>
					<td class="depgname" width="240"></td>
					
					<!-- 
					<th style="width: 40px">状态：</th>
					<td class="dstatus"></td>
					 -->
					<td class="did" style="display: none;" id="epgid"></td>
				</tr>
			</table>
			
				<div class="layoutImg">
					<div class="item item_focus">
						<div class="group group-3">
							<div class="seat" id='shownav1'>企业</div>
							<div class="seat"  id='shownav2' style="left: 157px">频道</div>
							<div class="seat"  id='shownav3' style="left: 267px">影视</div>
						</div>
						<div class="right">首页布局示例图</div>
					</div>
					
					<div class="l-clear"></div>
				</div>
				
			</div>	
			<div class="l-form" style="margin-left: 30px;">
				<div class="l-group l-group-hasicon"><img src="<%=path %>/static/xcloud/css/img/icon/communication.gif" /><span>EPG首页信息</span></div>
			</div>
			<div class="detail-middle l-form index3" style="margin-left: 30px;">
	
				<div class="i-forum">
				<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">A.</span>
				
			
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:shownav(1,'nav')" id='nav_btn1_2'>修改导航</a>
					</div>
					<div class="right">
						<div id='nav_categoryname1'></div>
						<input type='text' value="" id="nav_categoryname1_val" style="height: 26px; width: 160px;" />
						<!--
						<span class="i-forum-close" onclick="delpic(1,'nav')" id='nav_del1' style="position: absolute; right:40px; display: none;"></span>
						  -->
					</div>
					<div id='nav_categoryid1' style='display:none;'>company</div>
					<div id='nav_did1' style='display:none;'></div>
					<div id='nav_datatype1' style='display:none;'>nav</div>
				</div>
				<div class="l-clear"></div>
				
					<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(1,'nav',1)" id='nav_btn1_1'>修改默认图标</a>
					</div>
					<div class="right" >
						<span id="nav_url1_1"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				
					<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(1,'nav',2)" id='nav_btn1_2'>修改焦点图标</a>
					</div>
					<div class="right" >
						<span id="nav_url1_2"></span>
					</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			<div class="i-forum">
				<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">B.</span>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:shownav(2,'nav')" id='nav_btn2_2'>修改导航</a>
					</div>
					<div class="right">
						<div id='nav_categoryname2'></div>
						<input type='text' value="" id="nav_categoryname2_val" style="height: 26px; width: 160px;" />
						<!-- 
						<span class="i-forum-close" onclick="delpic(2,'nav')" id='nav_del2' style="position: absolute; right:40px; display: none;"></span>
						 -->
					</div>
					<div id='nav_categoryid2' style='display:none;'>channel</div>
					<div id='nav_did2' style='display:none;'></div>
					<div id='nav_datatype2' style='display:none;'>nav</div>
				</div>
				<div class="l-clear"></div>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(2,'nav',1)" id='nav_btn2_1'>修改默认图标</a>
					</div>
					<div class="right" >
						<span id="nav_url2_1"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(2,'nav',2)" id='nav_btn2_2'>修改焦点图标</a>
					</div>
					<div class="right" >
						<span id="nav_url2_2"></span>
					</div>
				</div>
				<div class="l-clear"></div>
			</div>
			
			
			<div class="i-forum">
				<b class="topCor-1"></b>
				<b class="topCor-2"></b>
				<span class="No">C.</span>
				
				
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:shownav(3,'nav')" id='nav_btn3_2'>关联导航</a>
					</div>
					<div class="right">
						<div id='nav_categoryname3'></div>
						<input type='text' value="" id="nav_categoryname3_val" style="height: 26px; width: 160px;" />
						<!-- 
						<span class="i-forum-close" onclick="delpic(3,'nav')" id='nav_del3' style="position: absolute; right:40px; display: none;"></span>
						 -->
					</div>
					</div>
					<div id='nav_categoryid3' style='display:none;'>vod</div>
					<div id='nav_did3' style='display:none;'></div>
					<div id='nav_datatype3' style='display:none;'>nav</div>
					<div class="l-clear"></div>
					
					<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(3,'nav',1)" id='nav_btn3_1'>修改默认图标</a>
					</div>
					<div class="right" >
						<span id="nav_url3_1"></span>
					</div>
				</div>
				<div class="l-clear"></div>
				
				<div class="i-item">
					<div class="left">
						<a href="javascript:showpic(3,'nav',2)" id='nav_btn3_2'>修改焦点图标</a>
					</div>
					<div class="right" >
						<span id="nav_url3_2"></span>
					</div>
				</div>
				<div class="l-clear"></div>
			</div>
				
				 
			</div>
			
		</div>
		<br /><br /><br />
		
	
	<div id="result" style="display: none;"></div>
	
	<script type="text/javascript">
		var filetype=''; // 文件类型, 图片:1
		var index=''; //导航标志
		var type ='1'; // 加载内容 : 2 / 加载栏目 : 1
		var datatype = '' ; // 入库的 内容的 data的类型 category, content, text, ygtd,nav
		var showlogo = 0;
		var seq = 1; //选择 导航图的  图标 1: 默认的图标; 2: 焦点图标
	 
		/*
			加载 海报 按钮
			i : 当行图 当前位置
			type: nav / category /content
			seq : 选择 导航图的  图标 1: 默认的图标; 2: 焦点图标 
		*/
		function showpic(i,type,seq1){
			seq = seq1;
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
		修改/保存 导航信息
		*/
		function shownav(i, type1){
			var typ = $("#nav_btn"+i+"_2").html();
			if(typ == '保存'){
				$("#nav_btn"+i+"_2").html("修改导航");
				$("#nav_categoryname"+i).show();
				$("#nav_categoryname"+i).html($("#nav_categoryname"+i+"_val").val());
				$("#nav_categoryname"+i+"_val").hide();
				$("#shownav"+i).html($("#nav_categoryname"+i+"_val").val());
				index =i;
				datatype ='nav';
				
				saveHome();
			}else{
				$("#nav_categoryname"+i).hide();
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
						$("#nav_btn"+i).html('修改默认图标');
						$("#nav_url"+i).html('');
						$("#nav_categoryname"+i).html('');
						$("#nav_categoryname"+i).show();
						$("#nav_categoryname"+i+"_val").val('');
						$("#nav_categoryname"+i+"_val").hide();
						$("#nav_did"+i).html('');
						$("#nav_del"+i).css("display",'none');
						$("#shownav"+i).html("");
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
					$("#nav_url"+index+"_"+seq).html(str1);
					//$("#nav_btn"+index).html('修改海报');
					saveHome();
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
					url="['"+$("#nav_url"+index+"_1").html()+"','"+$("#nav_url"+index+"_2").html()+"']";
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
					var data= "{'title':'"+categoryname+"','picurl':\""+url+"\",'id':'"+categoryid+"','sequence':"+index+",'type':'"+datatype1+"'}";
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
					$("#result").load('<%=path%>/FTPFileList/ftplist.do?type='+filetype);
				}
				
				
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
								var i=row.sequence;
								if(row.type == 'nav'){
									//导航图
									$("#nav_btn"+i+"_2").html("修改导航");
									//$("#nav_btn"+i).html('修改图标');
									var picurl = eval("("+row.picurl+")");
									$("#nav_url"+i+"_1").html(picurl[0]);
									$("#nav_url"+i+"_2").html(picurl[1]);
									$("#nav_categoryid"+i).html(row.id);
									$("#nav_categoryname"+i).html(row.title);
									$("#nav_did"+i).html(rows[j].did);
									$("#nav_datatype"+i).html(row.type);
									$("#nav_del"+i).show();
									$("#shownav"+i).html(row.title);
								}
									
							}
							}
						},
					"json"
				);
			}
			
			function cleardata(){
				
				//导航图管理
				for(var i = 1; i<4 ; i++){
					$("#shownav"+i).html("");
					$("#nav_btn"+i+"_2").html("修改导航");
					$("#nav_btn"+i).html('修改默认图标');
					$("#nav_url"+i+"_1").html('');
					$("#nav_url"+i+"_2").html('');
					$("#nav_categoryname"+i).html('');
					$("#nav_categoryname"+i).show();
					$("#nav_categoryname"+i+"_val").val('');
					$("#nav_categoryname"+i+"_val").hide();
					$("#nav_did"+i).html('');
				}
					
			}
		</script>
	</body>
</html>