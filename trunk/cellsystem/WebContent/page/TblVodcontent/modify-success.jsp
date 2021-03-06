<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String categoryType=request.getAttribute("categoryType")+"";
String menuno=request.getAttribute("menuno")+"";
String id=request.getAttribute("id")+"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		
	   <script src='<%=path %>/static/xcloud/js/ckplayer/ckplayer.js' type="text/javascript"></script>
	   
	</head>
	<body>
		<form id="mainform" method="post" action="update.do" style="margin-left: 30px;" class="detail-middle l-form">
		<input type='hidden' name ='tblContent.dcategorytype' value="<%=categoryType %>"/>
		<input type='hidden' id ='MenuNo' value="<%=menuno %>"/>
		<input type='hidden' id ='contenttype' value="2"/>
		<input type='hidden' id ='tblContent.did' value="<%=id %>"/>
		
			<div class="l-group l-group-hasicon"><img src="<%=path%>/static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span> >>  <s:property value="#attr.categoryName"/> </div>
			<table id="detail" class="detail-tableSkin detail-tableSkinB">
				<tr>
					<th width="100px">名称：</th>
					<td colspan="3" width="800px">
						<input class="AA" name="tblContent.dname"  validate="{'required':true,'maxlength':60}" />
					</td>
				</tr>
				<!-- 
				<tr>
					<th>类型：</th>
					<td id="dtype">
						<select  class="CC" name="tblContent.dtype" onchange="changeType(this)" id='contenttype'>
							<option value="1" selected="selected">图文</option>
							<option value="2">视频</option>
						</select>
					</td>
					<td width='400px'></td>
					<td></td>
				</tr>
				 -->
				
				<tr>
					<th>导演：</th>
					<td colspan="3">
						<input class="AA" name="tblContent.ddirector"  validate="{'required':true,'maxlength':60}" />
					</td>
				</tr>
				<tr>
					<th>演员：</th>
					<td colspan="3">
						<input class="AA" name="tblContent.dactor"  validate="{'required':true,'maxlength':100}" />
					</td>
				</tr>
				<tr>
					<th>简介信息：</th>
					<td colspan="3">
						<textarea class="l-textarea" style="width: 410px"  name="tblContent.ddesc" rows="3"  validate="{'required':true,'maxlength':100}" ></textarea>
					</td>
				</tr>
				<tr>
					<th>影视类型：</th>
					<td colspan="3">
						<div id='vodtype'></div>
					</td>
				</tr>
				
				<tr>
					<th>海报地址：</th>
					<td colspan="3" width="500px">
						<input class="DD" name="tblContent.dplacardurl" id="dplacardurl" readonly="readonly"/>
						<div class="i-btn-" onclick="relevance('dplacardurl',1)">选择</div>
					</td>
				</tr>
				<!--
				<tr>
					<th>缩略图地址：</th>
					<td colspan="3">
						<input class="DD" name="tblContent.dthumburl" id='dthumburl' readonly="readonly"/>
						<div class="i-btn-" onclick="relevance('dthumburl',1)">选择</div>
					</td>
				</tr> 
				 -->
				
				<tr id='vodtr'>
					<th>视频地址：</th>
					<td colspan="3">
						<input class="DD" name="tblContent.dvodurl" id='dvodurl'  readonly="readonly"/>
						<div class="i-btn-" onclick="relevance('dvodurl',2)">选择</div>
					</td>
				</tr>
				
				<tr id='vodtrPlay'>
					<td colspan="6" width='900px' height="340px">
					<div id="video" style="position:relative;z-index: 0;width:600px;height:400px;float: left;margin-left:20px;"><div id="a1"></div></div>
						<!--
						上面一行是播放器所在的容器名称，如果只调用flash播放器，可以只用<div id="a1"></div>
						-->
					</td>
				</tr>
			</table>
		</form> 
		<br />
		<br />
		<br />
		<br />
		
		<!-- 加载FTP文件列表的内容 -->
		<div id='result' style="display: none;"></div>
			
<script type="text/javascript">
	var  ftpurlid="";
	var filetype="2";//1,图文;2,视频
	var contenttype=null;
	var vodtypes = null;
			
		//视频
	var flashvars={
		f:'',//视频地址
		a:'',//调用时的参数，只有当s>0的时候有效
		s:'0',//调用方式，0=普通方法（f=视频地址），1=网址形式,2=xml形式，3=swf形式(s>0时f=网址，配合a来完成对地址的组装)
		c:'0',//是否读取文本配置,0不是，1是
		x:'',//调用xml风格路径，为空的话将使用ckplayer.js的配置
		i:'',//初始图片地址
		d:'',//暂停时播放的广告，swf/图片,多个用竖线隔开，图片要加链接地址，没有的时候留空就行
		u:'',//暂停时如果是图片的话，加个链接地址
		r:'',//前置广告的链接地址，多个用竖线隔开，没有的留空
		e:'3',//视频结束后的动作，0是调用js函数，1是循环播放，2是暂停播放并且不调用广告，3是调用视频推荐列表的插件，4是清除视频流并调用js功能和1差不多，5是暂停播放并且调用暂停广告
		v:'80',//默认音量，0-100之间
		p:'1',//视频默认0是暂停，1是播放
		h:'0',//播放http视频流时采用何种拖动方法，=0不使用任意拖动，=1是使用按关键帧，=2是按时间点，=3是自动判断按什么(如果视频格式是.mp4就按关键帧，.flv就按关键时间)，=4也是自动判断(只要包含字符mp4就按mp4来，只要包含字符flv就按flv来)
		q:'',//视频流拖动时参考函数，默认是start
		m:'1',//默认是否采用点击播放按钮后再加载视频，0不是，1是,设置成1时不要有前置广告
		o:'',//当m=1时，可以设置视频的时间，单位，秒
		w:'',//当m=1时，可以设置视频的总字节数
		g:'3',//视频直接g秒开始播放
		j:'',//视频提前j秒结束
		//k:'30|60',//提示点时间，如 30|60鼠标经过进度栏30秒，60秒会提示n指定的相应的文字
		//n:'这是提示点的功能，如果不需要删除n的值|提示点测试60秒',//提示点文字，跟k配合使用，如 提示点1|提示点2
		//调用播放器的全部参数列表结束
		//以下为自定义的播放器参数用来在插件里引用的
		my_url:encodeURIComponent(window.location.href)//本页面地址
		//调用自定义播放器参数结束
		};
	
		var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'Transparent'};//这里定义播放器的其它参数如背景色（跟flashvars中的b不同），是否支持全屏，是否支持交互
		var attributes={id:'ckplayer_a1',name:'ckplayer_a1',menu:'false'};
		
			var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main);  
					$("#dtype input:radio").ligerRadio();
					$(".AA,.DD,.EE,.FF").ligerTextBox({width:LG.inputWidthB});
					$(".BB").ligerTextBox({width:LG.inputWidthA});
					$(".CC").ligerComboBox({width:LG.inputWidthA});
					$(".i-btn-").ligerButton({width:25,text:""});
					$(".i-btn-").css({"float":"left"}).prev().css({"float":"left","marginRight":5});
				    LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				    this.loadForm();
				},
				save : function() {
					if($("#contenttype").val()==1){
						if(!htmlisnull()){
							return false;
						}
					}else{
						//添加的内容是视频的时候
						var vodtxt=$("#dvodurl").val();
						if(vodtxt != null && vodtxt != ''){}
						else{
							//没有填写视频地址的时候.
							LG.tip('请您选择视频地址.');
							return false;
						}
					}
					
		            LG.submitForm(this.mainform, function (data) {
		                var win = parent || window;
		                if (data.iserror) {  
		                    win.LG.showError(data.message);
		                }
		                else {
							 win.categoryType=<%=categoryType%>;
		                     win.LG.showSuccess(LG.INFO.SIX,null,900);
		                     win.LG.closeCurrentTab(null);
		                     win.loadlist();
	                         //win.LG.closeAndReloadParent(null, LG.getPageMenuNo());
		                }
		            });
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				ftpwin:null,
				add : function() {
					$("#result").html("");
					$("#result").load('<%=path%>/FTPFileList/ftplist.do?type='+filetype);
				},
				loadvodtype : function(){
					var jsons = '';
					$.ajax({
						async:false,
						data:{'query.dstatus' : 1},
						url:"<%=path%>/TblVodtype/getData.do?_t="+(+(new Date())),
						dataType:"json",
						success:function(data){
							jsons = data.rows;
						},
						error:function(data){
						}
					});
					return jsons;
				},
				loadForm : function() {
					var self = this;
					LG.loadForm(this.mainform, {
						url:"loadModify.do?id=<%=id%>&_t="+(new Date()),
						preID:"tblContent\\."
					},function(data){
						var vodtypes = self.loadvodtype();// 影视类型
						var types = data.dtype;
						
						var str ='';
						if(vodtypes != null && vodtypes.length >0){
							for(var i = 0 ; i<vodtypes.length ; i++){
								var row =vodtypes[i];
								str +='<input type="checkbox" name="tblContent.dtype" value="'+row.did+'" ' ;
								if(types.indexOf(row.did) >= 0){
									//选中状态
									str += 'checked="checked"';
								}
								str += '/>'+row.dname+' &nbsp;&nbsp;&nbsp;';
							}
							$("#vodtype").html(str);
						}else{
							str ="暂无类型";
						}
		
		
						//视频
						$("#vodtr").show();	
						$("#vodtr input").attr('validate',"{'required':true}");
						
						$("#hctr").hide();
						$("#vodtrPlay").show();
					
						vodurl=data.dvodurl;
						flashvars.f = "http://"+$("#ftpip").val()+"/"+vodurl;
						//alert(flashvars.f);
						//submitContents(data.dvodurl);
						swfobject.embedSWF('<%=path%>/static/xcloud/js/ckplayer/ckplayer.swf', 'a1', '600', '400', '10.0.0','<%=path%>/static/xcloud/js/ckplayer/expressInstall.swf', flashvars, params, attributes);
					});
				}
			}
			crForm.init();
			
			/*
				弹出FTPlist列表
			*/
			function relevance(id,type){
				ftpurlid=id;
				filetype=type;
				crForm.add();
			}
			
			/*
				加载 选择的FTP文件的路径 
			*/
			function submitContents(value){
				$("#result").html("");
				//value 得到的是全路径,把IP之后的内容截取出来 入库
				var str1=value.substr(value.indexOf("//")+2);
				str1=str1.substr(str1.indexOf("/")+1);
				$("#"+ftpurlid).val(str1);
				if(filetype==2){
					if(flashvars.f==''){
						
						flashvars.f=value;
						//下面一行是调用播放器了，括号里的参数含义：（播放器文件，要显示在的div容器，宽，高，需要flash的版本，当用户没有该版本的提示，加载初始化参数，加载设置参数如背景，加载attributes参数，主要用来设置播放器的id）
						swfobject.embedSWF('<%=path%>/static/xcloud/js/ckplayer/ckplayer.swf', 'a1', '600', '400', '10.0.0','<%=path%>/static/xcloud/js/ckplayer/expressInstall.swf', flashvars, params, attributes);
					}else{
						flashvars.f=value;
						CKobject._K_('video').innerHTML='<div id="a1"></div>';
					 	swfobject.embedSWF('<%=path%>/static/xcloud/js/ckplayer/ckplayer.swf', 'a1', '600', '400', '10.0.0','<%=path%>/static/xcloud/js/ckplayer/expressInstall.swf', flashvars, params, attributes);
					 }
				 }
			}
			

	
	/*
		图文/视频 切换 
	*/
	function changeType(obj){
		if($(obj).val()==1){
			//图文
			$("#vodtr input").val('');
			//$("#vodtr input").attr('validate','');
			$("#vodtr").hide();
			$("#hctr").show();
			
			$("#vodtrPlay").hide();
			$("#vodtrPlay #a1").html('');
		}else if($(obj).val()==2){
			//视频
			$("#vodtr").show();	
			//$("#vodtr input").attr('validate',"{'required':true}");
			
			$("#hctr").hide();
			$("#vodtrPlay").show();
		}
	}
	
	
</script>
		
	</body>
</html>