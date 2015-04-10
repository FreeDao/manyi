<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String categoryType=request.getAttribute("categoryType")+"";
String menuno=request.getAttribute("menuno")+"";
String dtype=request.getAttribute("dtype")+"";
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerForm.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerRadioList.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
		
		<script type="text/javascript" src="<%=path%>/static/xcloud/js/jquery-jtemplates.js"></script>
		
		
	</head>
	
	<body>
		<form id="mainform" method="post" action="<%=path %>/TblQuestionexam/update.do" style="margin-left: 30px;" class="detail-middle l-form" >
		<input type='hidden' name ='categoryType' value="<%=categoryType %>"/>
		<input type='hidden' id ='MenuNo' value="<%=menuno %>" name='menuno'/>
		
			<div class="l-group l-group-hasicon"><img src="<%=path%>/static/xcloud/css/img/icon/communication.gif" /><span>基本信息</span><s:property value="#attr.categoryName"/> </div>
			<table id="detail" class="detail-tableSkin detail-tableSkinB">
				<tr>
					<th>考试名称：</th>
					<td style="width: 440px">
						<input class="EE" name="tblQuestionexam.dname" value="${requestScope.exam.dname }" validate="{'required':true}"/>
						<input type='hidden' name="tblQuestionexam.did" value="${requestScope.exam.did }"/>
						<input type='hidden' name="tblQuestionexam.dtype" value="2"/>
					</td>
					<th>时长(分)：</th>
					<td>
						<input class="BB" name="tblQuestionexam.dduration"  value="${requestScope.exam.dduration }" validate="{'required':true,'digits':true}"/>
					</td>
				</tr>
				<tr>
					<th>激活时间：</th>
					<td>
						<input class="CC" name ='tblQuestionexam.dstarttime' value="${requestScope.exam.starttime }" validate="{'required':true}"/>
					</td>
					<th>截止时间：</th>
					<td>
						<input class="DD" name= 'tblQuestionexam.dstoptime' value="${requestScope.exam.stoptime }" validate="{'required':true}"/>
					</td>
				</tr>
				<tr>
				<th>通过分数:</th>
					<td><input class="BB" name="tblQuestionexam.dpassvalue"  value="${requestScope.exam.dpassvalue }" validate="{'required':true,'digits':true}"/> </td>
					
					<th>关联培训内容：</th>
					<td id="relevance">
					<%
						Object contentname=request.getAttribute("contentname");
						Object contentid=request.getAttribute("contentid");
						Object categoryname=request.getAttribute("categoryname");
						Object tblcpid=request.getAttribute("tblcpid");
						if(contentname==null){
					%>
						<a href="javascript:void(0);" onclick="relevance();" id='groombtn' >关联培训</a>&nbsp;&nbsp;
						<a href="javascript:void(0);" onclick="deleterel();" id='groombtn2' style="margin-right:30px; display: none;">删除</a>
						<input type="hidden" id="tblcpid" value=""/>
						<input type="hidden" id="groomid"/>
						<span id="groomshow"></span>
						<%}else{ %>
						<a href="javascript:void(0);" onclick="relevance();" id='groombtn' >关联培训</a>&nbsp;&nbsp;
						<a href="javascript:void(0);" onclick="deleterel();" id='groombtn2' style="margin-right:30px;">删除</a>
						<input type="hidden" id="tblcpid" value="<%=tblcpid==null?"":tblcpid %>"/>
						<input type="hidden" id="groomid" value="<%=contentid==null?"":contentid %>"/>
						<span id="groomshow">  <%=categoryname  %>  >> <%= contentname == null ? "" : contentname%> </span>
						<%} %>
					</td>
				</tr>
				<tr>
					<th>考前必读：</th>
					<td colspan="3">
						<textarea style="width:700px" id="address" class="l-textarea" rows="4" cols="100" name='tblQuestionexam.dread' validate="{'required':true}" >${requestScope.exam.dread }</textarea>
					</td>
				</tr>
			</table>
				<input type="reset" id="frmrest" style="display: none;"/>
		</form>
		 
		  <div  style="margin-left: 30px;">
				<div class="br"></div>
			<div class="l-custom-subjectList-title">考题列表</div>
			<div class="br"></div>
			<div id="listcontent">
				
			</div>
			
			<a class="l-custom-subjectList-add" onclick="addNexist()">添加试题</a>
			<br />
			<br />
			<br />
			<br />
			<br />
		
			<br />
			<br />
			<br />
		</div>

	
	<textarea  id="listtemplate1" style="display: none;">
		<div id="nexist-list{$T.nexistNo}" class='nexistdiv'>
        <div class="l-custom-subjectList"  width="800px;">
            <div class="l-custom-subjectList-top"></div>
            <form action="<%=path %>/TblExamexercise/save.do" method="post" style="width:90%;">
            <input type='hidden'  value="{$T.isedit}" id="isedit"/>
            <input type='hidden' name ="tblExamexercise.did" value="{$T.data.did}" id="id"/>
            <input type='hidden' id="nexistNo" value="{$T.nexistNo}"/>
            <input type='hidden' id="sequence" value="{$T.showNo}"/>
            <dl>
                <dt style="text-align: center;">
                    <strong>
                        No <span id="showNo">{$T.showNo}</span>.
                    </strong>
                    <br />
                    <a href="javascript:void(0);" id= 'nexistSave' onclick="submitF({$T.nexistNo})">
                    	保存
                    </a>
                    <br/>
                     <a href="javascript:void(0);" id= 'nexistDelete' onclick="submitD({$T.nexistNo})">
                  	 删除
                    </a>
                </dt>
                <dd>
                    <table width="90%" class="detail-tableSkin"> 
                        <tr>
                            <th  width="9%">
                         	题目：
                            </th>
                            <td>
                                <input class="ZZ" name="tblExamexercise.dtitle" value="{$T.data.dtitle}" validate="('required':true,'maxlength':64)"/>
                            </td>
                        </tr>
                        <tr>
                           <th  width="9%">类型：</th>
                            <td style="padding: 10px 0 5px 7px;">
                            <table>
                            	<tr>
                            		<td>
                            			<select class="" name="tblExamexercise.dtype" onchange="" title="{$T.data.dtype}">
		                                    <option value="1" selected="selected">单选</option>
		                                   <!--  <option value="2">
		                                        	复选
		                                     	  </option>
		                                    -->
		                                </select>
                            		</td>
                            		<th>分数：</th>
                            		<td>
                            			{#if $T.data}
                            				<input type="text" class="GG" name="tblExamexercise.dvalue" title="{$T.data.dvalue}" value="{$T.data.dvalue}" validate="('required':true,'digits':true)"/>
                            			{#else}
                            				<input type="text" class="GG" name="tblExamexercise.dvalue" title="{$T.data.dvalue}" value="10" validate="('required':true,'digits':true)"/>
                            			{#/if}
                            		</td>
                            	</tr>
                            </table>
                           </td>
                             
                        </tr>
                        <tr>
                            <th valign="top">
                                答案：
                            </th>
                            <td>
                                <div id="num-list{$T.nexistNo}">
	                                {#if $T.data}
	                                	{#foreach $T.data.dcontent as row}  
											<div class="num">
			                                   <span style="margin-right:8px;width: 12px;white-space:nowrap;">
				                                    {#if 4 <  $T.row.i}
			                                    		<div class="l-tab-close l-tab-close1" style="margin-left:-2px;display:none" onclick="deleteAn({$T.nexistNo})"></div>
				                                    {#/if}
			                                        <span class='aaa'>{$T.row.code}  .</span>
			                                    </span>
			                                     {#if 5 >  $T.row.i}
			                                    	 <input class="AA" name="dcontent{$T.row.code}" value="{$T.row.text}" validate="('required':true,'maxlength':100)" onchange="changeAn(this)"/>
			                                    {#else}
				                                    <input class="AA" name="dcontent{$T.row.code}" value="{$T.row.text}" validate="('required':true,'maxlength':100)" onchange="changeAn(this)" onfocus="focusAn(this)" onblur="blurAn(this)"/>
			                                    {#/if}
			                                     {#if $T.row.code ==$T.data.drightanswer}
				                                     <input type="radio" name="tblExamexercise.drightanswer" value="{$T.row.code}" checked="checked"  />
				                                {#else}
				                                	<input type="radio" name="tblExamexercise.drightanswer" value="{$T.row.code}"/>
			                                    {#/if}
			                                     <input type='hidden' name='dcontent' value="{$T.row.text}" class="dcontent" />
			                                </div>
										{#/for} 
	                                {#else}
	                                <div class="num">
	                                    <span style="margin-right:8px;">
	                                        A.
	                                    </span>
	                                    <input class="AA" name="dcontentA" validate="('required':true,'maxlength':100)"   type='text' onchange="changeAn(this)"/>
	                                    <input type="radio" name="tblExamexercise.drightanswer" value="A" checked="checked"/>
	                                    <input type='hidden' name='dcontent' class="dcontent"/>
	                                </div>
	                                <div class="num">
	                                    <span style="margin-right:8px;">
	                                        B.
	                                        </span>
	                                    <input class="AA" name="dcontentB"  validate="('required':true,'maxlength':100)" type='text' onchange="changeAn(this)"/>
	                                      <input type="radio" name="tblExamexercise.drightanswer" value="B"/>
	                                       <input type='hidden' name='dcontent' class="dcontent"/>
	                                </div>
	                                <div class="num">
	                                    <span style="margin-right:8px;">
	                                        C.
	                                    </span>
	                                    <input class="AA" name="dcontentC"  validate="('required':true,'maxlength':100)" type='text' onchange="changeAn(this)"/>
                                     	 <input type="radio" name="tblExamexercise.drightanswer" value="C"/>
                                      	<input type='hidden' name='dcontent'  class="dcontent"/>
	                                </div>
	                                <div class="num">
	                                    <span style="margin-right:8px;" class="aaa">
	                                        D.
	                                    </span>
	                                    <input class="AA" name="dcontentD" validate="('required':true,'maxlength':100)" type='text' onchange="changeAn(this)"/>
	                                      <input type="radio" name="tblExamexercise.drightanswer" value="D"/>
	                                      <input type='hidden' name='dcontent' class="dcontent"   />
	                                </div>
	                                	
	                                {#/if}
	                                
                                </div>
                                <div class="br"></div>
                                <a class="l-custom-subjectList-add">
                                    <span onclick="addAn({$T.nexistNo})" id="addAn">添加答案</span>
                                </a>
                            </td>
                        </tr>
                    </table>
                </dd>
            </dl>
            </form>
            <div class="l-custom-subjectList-bot">
            </div>
        </div>
        <div class="br"></div>
	</div>
	<div class="br" style="clear: both;margin-top: 20px"></div>
</textarea>


<textarea  id="listtemplate2" style="display: none;">
	<div id="nexist-list{$T.nexistNo}" class='nexistdiv'>
	<div class="l-custom-subjectList" >
				<div class="l-custom-subjectList-top"></div>
					<dl>
						<dt>
						<form action="save.do" method="post" width="95%">
							<input type='hidden' id = 'id' value="{$T.data.did}"/>
							 <input type='hidden' id="nexistNo" value="{$T.nexistNo}"/>
							 <input type='hidden' id="sequence" value="{$T.showNo}"/>
							 <input type='hidden' name="tblExamexercise.dvalue" id="dvalue" value="{$T.data.dvalue}"/>
						</form>
							<strong>No <span id='showNo'>{$T.showNo}</span>.</strong><br />
		                   <a href="javascript:void(0);" id ="nexistEdit" onclick="submitE({$T.nexistNo})">
		                   		编辑
		                   </a>
		                    <br />
		                    <a href="javascript:void(0);" id ="nexistDelete"  onclick="submitD({$T.nexistNo})">
		                    	删除
		                   </a>
                  		   <br />
						</dt>
						<dd>
							<table>
								<tr>
									<th >题目：</th>
									<td width="230"><strong><div class="title">{$T.data.dtitle}</div></strong></td>
									<th>分数：</th>
                            		<td  width="100"><strong>{$T.data.dvalue}</strong></td>
                            		<th>正确答案：</th>
                            		<td  width="100"><strong>{$T.data.drightanswer}</strong></td>
								</tr>
								<tr>
									<th>类型：</th>
									<td colspan="3">单选</td>
								</tr>
								<tr>
									<th valign="top">答案：</th>
									<td colspan="5">
										{#foreach $T.data.dcontent as row}  
											<div class="num">{$T.row}</div>
										{#/for}  
										
									</td>
								</tr>
							</table>
						</dd>
					</dl>
				<div class="l-custom-subjectList-bot"></div>
			</div>
			<div class="br"></div>
	</div>
	<div class="br" style="clear: both;margin-top: 20px"></div>
</textarea>
			
	<script type='text/javascript'>
	
			var nexistNo=1,showNo=1,currNo=1;
			var dt1=null;
			$(document).ready(function(){
				
				$.ajax({
					async:false,
					type:'post',
					url:'<%=path%>/TblContent/loadExistErcises.do',
					data:{"id":'${requestScope.exam.did}'},
					dataType:'json',
					success:function(data){
						if(data){
							for(var i=0; i < data.total; i++){
								nexistNo=i+1;
								showNo=i+1;
								var temp=data.rows[i];
								var con=temp.dcontent;
								con=con.split("#*");
								var profile={nexistNo:nexistNo,showNo:showNo,data:{did:temp.did,dtpye:temp.dtype,dcontent:con,dtitle:temp.dtitle,drightanswer:temp.drightanswer,dvalue:temp.dvalue}};
								//调用方法 
								var txt=$("#listcontent").html();
								$("#listcontent").setTemplateElement("listtemplate2"); 
								$("#listcontent").processTemplate(profile);
								$("#listcontent").html(txt+$("#listcontent").html());
							}
							renderInput();
						}
					},
					error:function(data){
						alert(data.requestText);
					}
				});
				
			});
			
			
			
			/*渲染ligerUI控件*/
			function renderInput(){
				$(".ZZ").ligerTextBox({width:LG.inputWidthA+160});
				$(".EE").ligerTextBox({width:LG.inputWidthA+50});
				$(".AA").ligerTextBox({width:LG.inputWidthA+80});
				$(".BB").ligerTextBox({width:LG.inputWidthA+50});
				$(".GG").ligerTextBox({width:LG.inputWidthA-10});
				$(".CC,.DD").ligerDateEditor({width:LG.inputWidthA+50});
				$(".FF").ligerComboBox({width:LG.inputWidthA+50});
				$("input:radio").ligerRadio();  
				
					var currFrm = $("form","#nexist-list"+currNo);
				if(currFrm != null && currFrm.length >0){
					$("input[validate]",currFrm).each(function(){
						var validate = $(this).attr("validate")||"";
						validate = validate.replace("(","{");
						validate = validate.replace(")","}");
						$(this).attr("validate",validate);
					});
					bindEvent(currFrm);
				}
				
			}
			
			/*
				添加 题目 的答案选项 
			*/
			function addNexist(){
				//添加一套 新的 题目之前. 把上一题 给保存起来
				var idtxt=$("div#nexist-list"+(nexistNo)+" #id").val();
				var isedit=$("div#nexist-list"+(nexistNo)+" #isedit").val();
				if(idtxt==''||isedit=='isedit'){
					if(!submitF(nexistNo)){
						return ;
					}
				}
				//添加新题目
				nexistNo++;//唯一标志
				showNo++;//题目编号
				currNo=nexistNo;
				var profile={nexistNo:nexistNo,showNo:showNo}; 
				//调用方法 
				var txt=$("#listcontent").html();
				$("#listcontent").setTemplateElement("listtemplate1"); 
				$("#listcontent").processTemplate(profile);
				$("#listcontent").html(txt+$("#listcontent").html());
				renderInput();
			}
			
			/*
				添加 答案, 按钮
			*/
			function addAn(num){
				var lastdiv=$("div#num-list"+num+" > div:last-child");
				var lastspan=lastdiv.find(".aaa");
				var code=$.trim(lastspan.html()).substring(0,1);
				var codeAscii=code.charCodeAt();//字符转为 ascii
				if(codeAscii<"Z".charCodeAt()){
					codeAscii++;
					var txt=String.fromCharCode(codeAscii); //acsii 转化为字符
						var str= $('<div class="num">&nbsp;<span style="margin-right:8px;width: 12px;white-space:nowrap;"><div class="l-tab-close l-tab-close1" style="margin-left:-2px;display:none" onclick="deleteAn('+num+')"></div><span class="aaa">'+txt+
						'.</span></span>&nbsp;&nbsp;<input type="text" class="AA" name="dcontent'+txt+'" validate="{\'required\':true}" onchange="changeAn(this)" />'+
						'&nbsp;'+
						'<input type="radio" class="radioInt" name="tblExamexercise.drightanswer" value="'+txt+'"/><input type="hidden" name="dcontent" class="dcontent" /></div>');
					lastdiv.after(str);
					$(".AA",str).focus(function(){
						$(".l-tab-close",str).show();
						$(".aaa",str).hide();
					}).blur(function(){
						setTimeout(function(){
							$(".l-tab-close",str).hide();
							$(".aaa",str).show();	
						},200);
					});
					$(str).find("input:radio").ligerRadio(); 
					$(str).find("input:text").ligerTextBox({width:LG.inputWidthB});
					$(str).find(".radioInt").ligerRadio(); 
					renderInput();
				}
			}
			
			/*
				删除 答案, 按钮
			*/
			function deleteAn(num){
				var lastdiv=$("div#num-list"+num+" > div:last-child");
				$(".AA",lastdiv).ligerHideTip();
				lastdiv.remove();
			}
			
			function focusAn(obj){
				var par=$(obj).parents("div.num");
				$(".l-tab-close",par).show();
				$(".aaa",par).hide();
			}
			
			function blurAn(obj){
				setTimeout(function(){
					var par=$(obj).parents("div.num");
					$(".l-tab-close",par).hide();
					$(".aaa",par).show();
				},200);
			}
			/**
				提交表单 保存 按钮
				(
				完成 两个工作
				1. 第一次 提交保存 
				2. 第二次 编辑保存
				)
			*/
			function submitF(num){
				currNo=num;
				var currForm=$("#nexist-list"+num+" form");
		
				$.metadata.setType("attr", "validate");
					if(!currForm.valid()){return false;}
				var sNo=$("#nexist-list"+num+" #showNo").html();
				var idtxt=currForm.find("#id").val();
				var tempNo=nexistNo;
				var url1='';
				if(idtxt!=null&&idtxt!=''){
					url1="<%=path %>/TblExamexercise/update.do";
					tempNo=num;
				}else{
					url1="<%=path %>/TblExamexercise/save.do";
				}
				currForm.ajaxForm({
					async:false,
					type:'POST',
					url:url1,
					success:function(data){
						$("[validate]", currForm).each(function(){
							$(this).ligerHideTip();
						});
						var temp=data.data;
						var con=temp.dcontent;
						con=con.split("#*");
						var profile={nexistNo:tempNo,showNo:sNo,data:{did:temp.did,dtpye:temp.dtype,dcontent:con,dtitle:temp.dtitle,dvalue:temp.dvalue,drightanswer:temp.drightanswer}};
						$("#nexist-list"+num).setTemplateElement("listtemplate2"); 
						$("#nexist-list"+num).processTemplate(profile); 
					
						renderInput();
					},
					error:function(data){
						 win.LG.showError(data.message);
					}
				});
				currForm.submit();	
				return true;
			}
			
			
			/**
				提交表单 编辑 按钮
			*/
			function submitE(num){
				currNo=num;
				var dt={id:$("#nexist-list"+num+" #id").val()};
				var sNo= $("#nexist-list"+num+" #showNo").html();
				$.ajax({
					async: false,
					type:'POST',
					dataType:'json',
					data:dt,
					url:"<%=path %>/TblExamexercise/loadModify.do",
					success:function(data){
						var temp=data.data;
						var con=temp.dcontent;
						var attrs=con.split("#*");
						con="";
						for(var i =0 ; i<attrs.length; i++){
							var at=attrs[i].split(".");
							var _i=i+1;
							con+="{i:"+_i+",code:\'"+at[0]+"\' ,text:\'"+at[1]+"\'},";
						}
						con=con.substring(0,con.length-1);
						con=eval("["+con+"]");
						var profile={nexistNo:num,isedit:'isedit',showNo:sNo,data:{did:temp.did,dtype:temp.dtype,dcontent:con,dtitle:temp.dtitle,dvalue:temp.dvalue,drightanswer:temp.drightanswer,dquestionid:temp.dquestionid}};
						$("#nexist-list"+num).setTemplateElement("listtemplate1"); 
						$("#nexist-list"+num).processTemplate(profile); 
						renderInput();
						
							//编辑 按钮 按下之后 去除其他的 表单的 验证效果
						var frms=$('#listcontent').find("form");
						for(var i = 0; i<frms.length; i++){
								var frm=$(frms[i]);
								var isedit=frm.find("#isedit").val();
								if(isedit!='' &&isedit!='isedit'){
									//记录已经 保存的选项的ID
								}else{
									$("[validate]", $(frm)).each(function() {
										$(this).ligerHideTip();
									});
								}
						} 
					},
					error:function(data){
						 win.LG.showError(data.message);
					}
				});
			}

			/*
				提交表单 删除 按钮
				1.这条记录尚未保存入库, 直接删掉页面上的内容
				2.这条记录已经保存入库, 删掉页面上的内容 的同时,还需要删除数据库中内容
			*/			
			function submitD(num){
				currNo=num;
				if(showNo>1){
					$("#listcontent .AA").ligerHideTip();
					$("#listcontent .ZZ").ligerHideTip();
					var currForm=$("#nexist-list"+num+" form");
					$("[validate]", currForm).each(function(){
							$(this).ligerHideTip();
					});
					var sNo=$("#nexist-list"+num+" #showNo").html();
					var idtxt=currForm.find("#id").val();
					$("#nexist-list"+num).remove();
					var childs=$("#listcontent").children("div.nexistdiv");//得到全部的选项列表
					for(var i=0;i<childs.length;i++){
						var listdiv=$("#"+childs[i].id);
						var tempNo=listdiv.find("#showNo").html();
						if(tempNo>1&&tempNo>sNo){
							tempNo--;
							listdiv.find("#showNo").html(tempNo);
							listdiv.find("#sequence").val(tempNo);
						}
					}
					showNo--;
					if(idtxt!=null&&idtxt!=''){
						var url1='<%=path %>/TblExamexercise/delete.do';
						var dt={ids:idtxt};
						// id 不为空, 那么需要 删除数据库中的数据
						$.ajax({
							async: false,
							type:'POST',
							dataType:'json',
							data:dt,
							url:url1,
							success:function(data){
								
							},
							error:function(data){
							 win.LG.showError(data.message);
							}
						});
					}
				}else{
					LG.tip("该考卷至少需保证一道题目.");
				}
			}
			
			
			/*
				关联 培训内容
			*/
			function relevance(){
				crForm.add();
			}
			
			/*
				删除 关联的 培训
			*/
			function deleterel(){
				$("#groombtn").html('关联培训');
				$("#groombtn2").css("display",'none');
				$("#groomid").val('');
				$("#groomshow").html('');
			}
			
			var crForm = {
				main : "#mainform",
				init : function() {
					var self = this;
					this.mainform = $(self.main); 
					$("#switch-btn input").ligerRadio();
					$("#switch-btn input").change(function(){
						if ($(this).attr("checked")){
							document.location.href = $(this).attr("url");
						}
					});
					 renderInput();
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				},
				save : function() {
					$.metadata.setType("attr", "validate");
				
					var  mainFrm=$("#mainform");
					
					//验证 form表单
					if(!mainFrm.valid()){return false;}

						//验证 用户 输入 的   开始时间 和  截至时间
					var starttime=$(".CC",mainFrm).val();
					var endtime=$(".DD",mainFrm).val();
					if(starttime>endtime){
						LG.tip('激活时间不可大于截止时间');
						return false; 
					}
					
						
					var flag=true;
					var sumnum = 0;//总分数
					
					$('#listcontent').find("form").each(function(){
							var currForm=$(this);
							sumnum += parseInt(currForm[0]["tblExamexercise.dvalue"].value);
							if(!currForm.valid()){
								flag=false; 
								return;
							}
						});
					//验证不通过
					if(!flag){
						return false;
					}
					//总分数 小于 通过分数
					var passvalue = parseInt(mainFrm[0]['tblQuestionexam.dpassvalue'].value); 
					if(sumnum < passvalue){
						LG.tip("总分数不能小于通过分数");
						return false;
					}
					
					
					mainFrm.ajaxForm({
						async:false,
						type:'POST',
						url:"<%=path%>/TblQuestionexam/update.do",
						success:function(data){
							//添加 在线考试  成功
							var examid=data.data.dmapping;//data.data返回的是content, examid表示的是在线考试基本信息的id, 对应content表中的mapping
							var paperid=data.data.did;// paperid表示 在线考试内容表中的信息的id(contentid)
							//提交成功之后, 需要把选项入库, 或者 是更新选项的考卷的ID	
							var frms=$('#listcontent').find("form");
							var selectids='';
							var sequences="";
							for(var i = 0; i<frms.length; i++){
								var frm=$(frms[i]);
								var idtxt=frm.find("#id").val();
								var isedit=frm.find("#isedit").val();
								sequences+=frm.find("#sequence").val()+",";
								if(idtxt!=null&&idtxt!=''&&isedit!='isedit'){
									//记录已经 保存的选项的ID
									selectids+=idtxt+",";
								}else{
									//提交 尚未 保存的 选项
									var url2="";
									if(isedit=='isedit'){
										//修改
										url2="<%=path%>/TblExamexercise/update.do";
									}else{
										//添加
										url2="<%=path%>/TblExamexercise/save.do";
									}
									frm.ajaxForm({
										async:false,
										type:'POST',
										url:url2,
										success:function(data){
											selectids+=data.data.did+",";
										},
										error:function(data){
											 win.LG.showError(data.message);
											return ;
										}
									});
									frm.submit();
								}
							}
							
							//设置 为同步
							$.ajaxSetup({
							   async:false
							  });
							  
							//处理全部的选项, 把全部的选项 的 问卷的ID(FK) 修改为 当前的ID
							$.post(
								"<%=path%>/TblExamexercise/updateList.do",
								{ids:selectids,examid:examid},
								function(data){
									//alert(data);
									if(data.iserror){
										return;
									}
								},
								"json"
							);
							
							//编辑 全部选项的顺序
								$.post(
								"<%=path%>/TblExamexercise/updateSequenceList.do",
								{'ids':selectids,'sequences':sequences},
								function(data){
									//alert(data);
									if(data.iserror){
										return;
									}
								},
								"json"
							);
							
							
							var contentid=$("#groomid").val();
							if(contentid!=undefined&&contentid!=''){
								//内容添加完毕 , 添加 培训内容和在线考试的 关联关系
								var tblcpid=$("#tblcpid").val();
								var url1="";
								var data1={};
								if(tblcpid!=null&&tblcpid!=''){
									//修改处理
									url1='<%=path%>/TblContentpaper/update.do';
									data1={"tblContentpaper.dpaperid":examid,"tblContentpaper.dcontentid":contentid,"tblContentpaper.did":tblcpid};
								}else{
									url1='<%=path%>/TblContentpaper/save.do';
									data1={"tblContentpaper.dpaperid":examid,"tblContentpaper.dcontentid":contentid};
								}
								$.post(
									url1,
									data1,
									function(data){
										if(data.iserror){
											return;
										}
									},
									"json"
								);
							}
							
							//在线考试添加成功
							 //examid 考卷的id
							 var win = parent || window;
							 win.categoryType=<%=categoryType%>;
							 win.dtype=<%=dtype%>;
							 
							win.LG.showSuccess(LG.INFO.SIX,null,900);
							win.LG.closeCurrentTab(null);
		            		win.loadlist();
	                       	//win.LG.closeAndReloadParent(null, <%=menuno%>);
							 
						},
						error:function(data){
							var win = parent || window;
							 win.LG.showError(data.message);
						}
					});
					mainFrm.submit();
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				
				add : function() {
				$.ajax({
						async:false,
						type:'post',
						contentType:"text/json",
						dataType:'json',
						url: '<%=path%>/TblCategory/findCategoryByGroom.do',
						success:function(data){
							dt1=data;
						},
						error:function(data){
						}
					});
					
					
					if (window.win) {
						window.win.show();
					} else {
				  		var self = this;
						var panle = $("<div></div>"),gridPanle = $("<div style='width:700px;'></div>");
						var search = $('<div class="openWinSearch"><form class="l-form"></form></div>');
						panle.append(search);
						panle.append(gridPanle);
						this.wingrid = gridPanle.ligerGrid( {
							columns: [
								{display:"名称",name:"dname",width:'100',isSort:false},
								{display:"类型",name:"dtype",width:'80',render: LG.contentType,isSort:false},
								{display:"上传时间",name:"duploadtime",width:'150',isSort:false},
								{display:"上传人",name:"duploadperson",width:'120',isSort:false},
								{display:"状态",name:"dstatus",width:'100',isSort:false,render: LG.status}
						   ],
							pageSize : 20,checkbox: true,delayLoad :true,
							url : '<%=path%>/TblContent/findContentByCategoryId.do',width : '98%',
							height:"230"
						});
						self.search($("form", search), this.wingrid);
						window.win = $.ligerDialog.open( {
							title : "加入分类", width:640,height:370,top:50,
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
							$("input.l-text-field",".openWinSearch").val('');
							$(".l-dialog-btn-inner:last",".openWinSearch").click();
						});
						
						
					}
				},
				addRows : function(self) {
				 	var row = self.wingrid.getSelectedRows();
            		if (!row) {LG.tip(LG.INFO.ONE); return false; }
	                if (row.length>1) { LG.tip(LG.INFO.TWO); return false; }
	                
	                //var yy = $.ligerui.get("categoryid");
					//var c = yy.get("text");
	                
	                var id = $("input[name=dcategorytype]:last",window.win.element).val();
	                var text = $(":radio[value="+id+"]",window.win.element).next().text();
	                
	                //得到培训内容的id
	                $("#groomid").val(row[0].did);
	                $("#groomshow").html( text+">> "+row[0].dname);
	                $("#groombtn").html("修改");
	                $("#groombtn2").css("display",'inline');
	                
	                
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
						  // {display: "栏目设置", name:"JJ",type: "radioList",options:{onRendered:function(){self.rendered(grid,this)},data:[{id:1,name:'用户管理'},{id:2,name:'企业管理'},{id:3,name:'栏目管理'}],textField: 'name' }},
						  {display: "栏目设置", name:"dcategorytype",type: "radioList",width:380,options:{change:function(id){
						  			//id得到的是 单选按钮的值
						  			grid.loadServerData({'query.dcategorytype':id});
						  				
									},onRendered:function(){self.rendered(grid,this)},data:dt1,textField: 'name'}
						  },
						  {display:"培训名称",name:"query.dname",newline:true,type:"text"}
						]
					});
					LG.appendSearchButtons(search, grid ,false,true);
				},
				rendered : function(grid,radio) {
				
					var id = radio.data[0].id;
					radio.setValue(id);
					
					//grid.loadData(true);
					grid.loadServerData({'query.dcategorytype':id});
				}
				
			}
			crForm.init();
			
			/*
				选项 内容 切换时调用
			*/
			function changeAn(obj){
				var an=$(obj).parents("div.num").find(".dcontent");
				an.val($(obj).val());
			}
			
		</script>
		
		<script src="<%=path%>/static/xcloud/js/jquery.form.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/jquery.validate.min.js" type="text/javascript"></script>
		
	</body>
</html>