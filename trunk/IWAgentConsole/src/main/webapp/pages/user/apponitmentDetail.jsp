<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head path="<%=basePath%>">
<base href="<%=basePath%>"/>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="styles/page.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/boot.js"></script>
<script type="text/javascript" src="script/pages/houseSearchPanel.js"></script>
</head>
<body path="<%=basePath%>">

   <div id="addPanel" class="mini-panel" title="看房时间 :<fmt:formatDate value='${appointment.appointmentTime}' type='both' pattern='yyyy-MM-dd, h:m'/>" style="width:100%;height:600px;" expanded="true"
    showToolbar="true" showCollapseButton="false" showFooter="true" allowResize="false" collapseOnTitleClick="false">
    
		        <table>
		            <tr>
		                <td>
		                    <label>状态：</label>
		                </td>
		                <td>
		                    <input id="combo1" class="mini-combobox" style="width:150px;" textField="text" valueField="id" 
		                    
		                     value="${appointment.appointmentState}"  required="true" allowInput="true" readOnly="true" showNullItem="true" data="[{ id: 1, text: '待确认' }, { id: 2, text: '待看房' },{ id: 3, text: '经纪人已签到' },{ id: 4, text: '未到未看房' },{ id: 5, text: '已到未看房'}, { id: 6, text: '已到已看房'}, { id: 7, text: '已改期 '}, { id: 8, text: '已取消'}]"/>
		                      <c:choose>  
                                 <c:when test="${appointment.appointmentState <=1}">  
                                   <a class="mini-button" style='vertical-align:middle;' onclick="confirmAppointment()" >确认约会 </a> 
                                </c:when>  
		                      </c:choose>  
		                   
		                </td>
		            </tr>
		            <tr>
		                <td>
		                    <label >碰头地点：</label>
		                </td>
		                <td>
		                    <input id="countMsg"  name="username" class="mini-textbox asLabel" style="width:300px" readOnly="true" value="${appointment.meetAddress}" required="true" />
		                </td>
		            </tr>
		            <tr>
		                <td>
		                    <label >备注：</label>
		                </td>
		                <td>
		                    <input id="countMsg"  name="username" class="mini-textbox asLabel" style="width:500px" readOnly="true" value="${appointment.memo}" required="true" />
		                </td>
		            </tr>
		         </table>
	       <div id="seekhouseGrid" class="mini-datagrid" style="width:1300px;height:500px;" showPager="false" allowResize="false"
	          idField="id" multiSelect="true">
		        <div property="columns">
		                <div type="checkcolumn" ></div>    
			            <div field="bankuai" width="120" headerAlign="center" >板块</div>    
			            <div field="xiaoqu" width="120" headerAlign="center" >小区</div>    
			            <div field="fanghao" width="120">房号</div>
			            <div field="huxing" width="100">户型</div>
			            <div field="jiage" dataType="currency" currencyUnit="￥" align="right" width="100" >价格</div>
			            <div field="lianxifs" width="100" headerAlign="center">联系方式</div>
			            <div field="state" width="100" renderer="onStateRenderer">预约情况</div>
                        <div field="memo" width="100">备注</div> 
		        </div>
           </div>

	    <div property="footer">
                 <c:choose>
                      <c:when test="${appointment.appointmentState==2}">  
                         	        <a class="mini-button" style='vertical-align:middle;' onclick="opencheckinWin()">签到</a>
                         	        <a class="mini-button" style='vertical-align:right;' onclick="showChangeWin()" >改期</a>
	                                <a class="mini-button" style='vertical-align:right;' onclick="showCancelWin()" >取消</a>
                       </c:when> 
                       <c:when test="${appointment.appointmentState==1}">
                       	            <a class="mini-button" style='vertical-align:right;' onclick="showChangeWin()" >改期</a>
	                                <a class="mini-button" style='vertical-align:right;' onclick="showCancelWin()" >取消</a>  
                      </c:when> 
                       <c:when test="${appointment.appointmentState==3}">
                        <a class="mini-button" id="recordbutton" style='vertical-align:middle;' onclick="sendHouseRecordWin()" >填写填写记录</a>
                      </c:when>
                </c:choose>  
               <a class="mini-button" id="recordbuttonBackup" style='vertical-align:middle;'  onclick="sendHouseRecordWin()" >填写填写记录</a>
               
	    </div> 
   </div>
   
<!-- 填写带看记录窗口 -->
<div id="seeRecordwin" class="mini-window" showCloseButton="false" title="带看记录" style="width:500px;height:420px;" 
    showMaxButton="false" showCollapseButton="false" showShadow="true" 
    showToolbar="true" showFooter="true" showModal="true" allowResize="false" allowDrag="true">
		<div>
		          <table>
		            <tr>
			            <td>
			              <div  id="total" style="font-size:16px;">共4套房源</div>
			            </td>
			            <td>
			               <div  id="ifSubmit" style="font-size:16px;color:#FF0000"></div>
			            </td>
		            </tr>
		            <tr>
			            <td>
			              <div id="seqIndex"  style="font-size:16px;">当前第4套房源</div>
			            </td>
		            </tr>
		            <tr>
			            <td>
			             <div id="houseInfo">汤臣豪庭2期    12号602    3房2厅2卫  8900元  房东：刘女士  13928176521 </div>
			            </td>
		            </tr>
		            <tr>
		              <td>
		                  <label >是否完成看房：</label>
					      <div id="radilbttuonlState" class="mini-radiobuttonlist" repeatItems="1"  style='vertical-align:right;'  repeatLayout="table" repeatDirection="vertical"  onvaluechanged="onRadioValueChangedState"
					       textField="text" valueField="id" value="5"  data="[{ 'id': '5', 'text': '是' },{ 'id': '6', 'text': '否' }]">
					      </div>
		               </td>
		              <td>
		              </td>
		            </tr>
		            <tr>
			             <td>
						          <div  id="finishFeeling"> 
					                    <div>用户看完房感受</div>
						               	<table>
						                 <tr>
							                 <td>
										         <div id="radilbttuonlFeeling" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" repeatDirection="" 
											       textField="text" valueField="id" value="0"  data="[{ 'id': '0', 'text': '兴趣强烈，打算租这套房' },{ 'id': '1', 'text': '有明显兴趣，但会继续看房' },{ 'id': '2', 'text': '兴趣一般，要继续看房' },{ 'id': '3', 'text': '看不出来感受' },{ 'id': '4', 'text': '不喜欢这套房' }]">
											      </div>
							                 </td>
						                 </tr>
						                 <tr>
							                 <td>
							                                                             备注
							                 </td>
						                 </tr>
						                 <tr>
							                 <td>
							                      <textarea id="mtextAreafinish" class="mini-textarea"  emptyText="看房记录集备注" style="width:300px;height:100px;"></textarea>
							                 </td>
						                 </tr>
						                </table>
					              </div>
					               
					               <div id="noseeReason" style="display:none"> 
					                
					                       <div>未完成看房的原因</div>
							               	<table>
							                 <tr>
								                 <td>
											         <div id="radilbttuonlNoseeReason" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" repeatDirection="" 
												       textField="text" valueField="id" value="0"  data="[{ 'id': '0', 'text': '用户没来' },{ 'id': '1', 'text': '房东没来' },{ 'id': '2', 'text': '用户已有其他意向房源' },{ 'id': '3', 'text': '用户与房东都没有来' },{ 'id': '4', 'text': '其他' }]">
												      </div>
								                 </td>
							                 </tr>
							                </table>
					                       <div>后续工作</div>
							               	<table>
							                 <tr>
								                 <td>
											         <div id="radilbttuonlAfter" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" repeatDirection="" 
												       textField="text" valueField="id" value="7"  data="[{ 'id': '7', 'text': '提交改期，以后再约看' },{ 'id': '8', 'text': '不再越看' }]">
												      </div>
								                 </td>
							                 </tr>
							                </table>
							                
							                <table>
							               
							                <tr>
								                 <td>
								                                                             备注
								                 </td>
							                 </tr>
							                 <tr>
								                 <td>
								                      <textarea id="mtextAreaNosee" class="mini-textarea"  emptyText="看房记录集备注" style="width:300px;height:100px;"></textarea>
								                 </td>
							                 </tr>
							                </table>
					              </div>
			             
			             </td>
		             </tr>
		          </table>
		 </div>
		<div property="footer" style="text-align:center;padding:5px;">
	        <input type='button' value='上一套' onclick="preHouse()" style='vertical-align:middle;'/>
	        <input type='button' value='下一套' onclick="nextHouse()" style='vertical-align:middle;'/>
	        <input type='button' value='提交' onclick="submitWindowSend()" style='vertical-align:middle;'/>
	        <input type='button' value='关闭' onclick="closeSeeRecordWin()" style='vertical-align:middle;'/>
	    </div>
</div>
<!-- 填写带看记录窗口 -->


<!-- 用户签到窗口 -->
<div id="chekinWin" class="mini-window" title="带看记录" style="width:500px;height:220px;" 
    showMaxButton="false" showCollapseButton="false" showShadow="true"
    showToolbar="true" showFooter="true" showModal="true" allowResize="false" allowDrag="true">
      <div id="checkinbox" class="mini-checkboxlist" repeatItems="3" repeatLayout="table"
        textField="text" valueField="id" value="agent" data="[{ 'id': 'agent', 'text': '经纪人签到' },{ 'id': 'user', 'text': '用户已签到' }]" >
      </div>
      <div style="font-color:red">
                       说明：如果用户已到,但不选中签到框代表用户未到， 确认后，看房记录按钮不可操作。
      </div>
     <div property="footer" style="text-align:center;padding:5px;">
	        <input type='button' value='提交' onclick="submitCheckin()" style='vertical-align:middle;'/>
	        <input type='button' value='关闭' onclick="mini.get('chekinWin').hide();" style='vertical-align:middle;'/>
	  </div>
</div>
<!-- 下一步操作 -->
<div id="afterWin" class="mini-window" title="下一部操作" style="width:500px;height:220px;" 
    showMaxButton="false" showCollapseButton="false" showShadow="true"
    showToolbar="true" showFooter="true" showModal="true" allowResize="false" allowDrag="true">
      <div id="radioCheckboxlist" class="mini-radiobuttonlist" repeatItems="3" repeatLayout="table" 
        textField="text" valueField="id" value="7" data="[{ 'id': '7', 'text': '改期约会' },{ 'id': '8', 'text': '取消约会' }]" >
      </div>
     <div property="footer" style="text-align:center;padding:5px;">
	        <input type='button' value='提交' onclick="submitAfterSee()" style='vertical-align:middle;'/>
	  </div>
</div>
    <script type="text/javascript">
        mini.parse();
        var appointment_state='${appointment.appointmentState}';
        mini.get("recordbuttonBackup").hide();
       //更新两个面板数据
          var seekhouseGrid = mini.get("seekhouseGrid");
          seekhouseGrid.setUrl(bootPATH+'userAppointment/getAppointmentSeekhouseList.do');
          seekhouseGrid.load({state:'${appointment.appointmentState}', appointmentId:'${appointment.id}'});
          if(appointment_state=='6'){//已到已看房才有这个逻辑
              seekhouseGrid.on("load",function(e){
            	  var data= seekhouseGrid.getData();
            	  for(var i=0;i<data.length;i++){
            		  if(data[i].state==4){//若果没有待登，就显示填写按钮
            	          mini.get("recordbuttonBackup").show();
            		      break;
            		  }
            	  }
              });
          }

        /***
         * 记录约看按钮
         ***/
        function onRendererCaozuo(e) {
        	
            return '<a class="mini-button mini-button-iconTop" iconCls="icon-add" onclick="recordContact('+e.record.id+')" >记录联系</a>';
            
        }
        /***
        * 查看已有预约按钮
        ***/
        function onRendererYuyue(e) {
       	 
            return '<a class="mini-button mini-button-iconTop" iconCls="icon-add" onclick="haveAppointment('+e.record.id+')" >已有约看</a>';
        }
        //

        //约看状态
        var states = [{ id: 1, text: '预约中' }, { id: 2, text: '待确认' },{ id: 3, text: '待看房' },{ id: 4, text: '待登记' },{ id: 5, text: '已看房'}, { id: 6, text: '未看房'}, { id: 7, text: '改期中'}, { id: 8, text: '取消中'}, { id: 9, text: '已下架'}]; 
        /**
        * 渲染状态
        */
        function onStateRenderer(e) {
            for (var i = 0, l = states.length; i < l; i++) {
                var g = states[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        var states_appointment = [{ id: 1, text: '待确认' }, { id: 2, text: '待看房' },{ id: 3, text: '经纪人已签到' },{ id: 4, text: '未到未看房' },{ id: 5, text: '已到未看房'}, { id: 6, text: '已到已看房'}, { id: 7, text: '已改期 '}, { id: 8, text: '已取消'}]; 
        /**
        * 渲染状态
        */
        function onStateAppointRenderer(e) {
            for (var i = 0, l = states_appointment.length; i < l; i++) {
                var g = states_appointment[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        
        /**
         * 渲染约会状态
         */
        function getStateText(id){
        	
            for (var i = 0, l = states_appointment.length; i < l; i++) {
                var g = states_appointment[i];
                if (id== e.value) return g.text;
            }
            return "";
        }
        
        var _dealselectIdx=0;
        var _dealselectkey=0;
        /***
        * 弹出 带看房记录窗口
        *
        */
        function sendHouseRecordWin(){
        	var seekhouseGridData=seekhouseGrid.getData();
        	var len=seekhouseGridData.length;
        	_dealselectkey=seekhouseGridData[_dealselectIdx].id;
             $("#total").text('共'+len+'套房源');
             $("#seqIndex").text('当前第1套房源');
             $("#houseInfo").text(seekhouseGridData[_dealselectIdx].bankuai+','+seekhouseGridData[_dealselectIdx].xiaoqu+','+seekhouseGridData[_dealselectIdx].fanghao+','+seekhouseGridData[_dealselectIdx].huxing+','+seekhouseGridData[_dealselectIdx].jiage+','+seekhouseGridData[_dealselectIdx].lianxifs);
        	var win = mini.get("seeRecordwin");
            win.show();
        	
        }
        /*
        * 下一套房源
        */
        function  nextHouse(){
        	_dealselectIdx++;
         	var seekhouseGridData=seekhouseGrid.getData();
        	if(_dealselectIdx==seekhouseGridData.length){
     		   mini.alert("已是最后一套房源！");
     			_dealselectIdx--;
    		   return ;
        	}
        	_dealselectkey=seekhouseGridData[_dealselectIdx].id;
        	
         	$('#ifSubmit').text('');
         	for(var i=0;i<_havenRecord.length;i++){
         		if(_havenRecord[i]==_dealselectkey){
         			$('#ifSubmit').text('【已填写】');
         			break;
         		}
         	}
            $("#houseInfo").text(seekhouseGridData[_dealselectIdx].bankuai+','+seekhouseGridData[_dealselectIdx].xiaoqu+','+seekhouseGridData[_dealselectIdx].fanghao+','+seekhouseGridData[_dealselectIdx].huxing+','+seekhouseGridData[_dealselectIdx].jiage+','+seekhouseGridData[_dealselectIdx].lianxifs);
        	$("#seqIndex").text('当前第'+(_dealselectIdx+1)+'套房源');

        }
        /*
        * 前一套房源
        */
        function  preHouse(){
        	_dealselectIdx--;
        	if(_dealselectIdx<0){
        		mini.alert("已是第一套房源！");
        		_dealselectIdx++;
    		   return ;
        	}
        	//显示是否已填写
         	var seekhouseGridData=seekhouseGrid.getData();
         	_dealselectkey=seekhouseGridData[_dealselectIdx].id;
         	
         	$('#ifSubmit').text('');
         	for(var i=0;i<_havenRecord.length;i++){
         		if(_havenRecord[i]==_dealselectkey){
         			$('#ifSubmit').text('【已填写】');
         			break;
         		}
         	}
            $("#houseInfo").text(seekhouseGridData[_dealselectIdx].bankuai+','+seekhouseGridData[_dealselectIdx].xiaoqu+','+seekhouseGridData[_dealselectIdx].fanghao+','+seekhouseGridData[_dealselectIdx].huxing+','+seekhouseGridData[_dealselectIdx].jiage+','+seekhouseGridData[_dealselectIdx].lianxifs);
        	$("#seqIndex").text('当前第'+(_dealselectIdx+1)+'套房源');
        	
        	//
        	mini.get("radilbttuonlState").setValue(5);
       		$("#noseeReason").hide();
       		$("#finishFeeling").show();
       		
        }

       /**
       * 联系选项改变 是否看房完成
       */
       function onRadioValueChangedState(e){
	       	if(this.getValue()==5){//已完成看房
	       		$("#noseeReason").hide();
	       		$("#finishFeeling").show();
	       	}else{//未完成
	       		$("#finishFeeling").hide();
	       		$("#noseeReason").show();
	       	}
       }
       
       /*
       *
       * 弹出改期窗口 todo 
       */
       function showChangeWin(){
  
                mini.confirm("你确认已经和客户沟通过了么？", "",
                    function (action) {
                        if (action == "ok") {
                	        $.ajax({
             	               type: "post",
             	               url: bootPATH+"userAppointment/changeTimeAppointment.do",
             	               data: {appointmentId:'${appointment.id}'},
             	               dataType: "json",
             	               success: function(data){
             	            	 mini.alert('改期成功！');
             	            	// cancelDealgrid.reload();注意相关按钮不可用
             	               },
             	               error:function(){
             	            	  mini.alert('error');
             	               }
             	           });
                            return;
                        }
                    }
                );
       }
       
       /*
       *
       * 弹出取消窗口
       */
       function showCancelWin(){

                mini.confirm("你确认已经和客户沟通过了么？", "",
                    function (action) {
                        if (action == "ok") {
                	        $.ajax({
             	               type: "post",
             	               url: bootPATH+"userAppointment/cancelAppointment.do",
             	               data: {appointmentId:'${appointment.id}'},
             	               dataType: "json",
             	               success: function(data){
             	            	 mini.alert('取消成功！');
             	            	// cancelDealgrid.reload();;注意相关按钮不可用
             	               },
             	               error:function(){
             	            	  mini.alert('error');
             	               }
             	           });
                            return;
                        }
                    }
                );
       }
       
       /**
       *打开签到窗口
       *
       */
       
       function opencheckinWin(){
         	var win = mini.get("chekinWin");
             win.show();
       }
       /**
       * 提交用户签到
       */
       function submitCheckin(){
           var obj = mini.get("checkinbox");
           if(obj.getValue().indexOf('agent')<0){
        	   mini.alert('经纪人项必选!');
        	   return;
           }else{
        	   
	       	   if(obj.getValue().indexOf('user')<0){//选用户未到弹出 后续操作
	       		   var objwin = mini.get("afterWin");
	       		   objwin.show();
	       		   return ;
	       	   }
	   	        $.ajax({
		               type: "post",
		               url: bootPATH+"userAppointment/agentCheckIn.do",
		               data: {appointmentId:'${appointment.id}',ifUserCheckIn:1,flag:1,state:1},
		               dataType: "json",
		               success: function(data){
		            	   mini.alert('签到成功！');
		            	 //$('#chekinWin').show();
		            	 seekhouseGrid.load({state:'3', appointmentId:'${appointment.id}'});
		                 window.location.href= window.location.href;
		            	
		               },
		               error:function(){
		            	  mini.alert('error');
		               }
		           });
	   	           mini.get('chekinWin').hide();
           };
       }
       
       
       var _havenRecord=new Array();
       /***
       * 用户提交看房记录 数新列表 记录下填写记录
       */
       function submitWindowSend(){
    	   //是否完成看房
    	   //看房感受和 未看房原 因获取，
    	   //备注获取
    	   
    	   if(mini.get('radilbttuonlState').getValue()=='5'){//完成看房
    		   
    		 var txt=encodeURI(mini.get('mtextAreafinish').getValue());
    	   
   	        $.ajax({
	               type: "post",
	               url: bootPATH+"userAppointment/finishState.do",
	               data: {id:_dealselectkey,noseeValue:mini.get('radilbttuonlFeeling').getValue(),memo:txt,customerId:'${appointment.userId}',appointmentId:'${appointment.id}'},
	               dataType: "json",
	               success: function(data){
	            	   var fg=true;
	            	   for(var i=0;i<_havenRecord.length;i++){
	            		   if(_havenRecord==_dealselectkey){
	            			   fg=false;
	            			   break;
	            		   }
	            	   }
	            	   if(fg){
	            	     _havenRecord.push(_dealselectkey);//记录下处理的记录
	            	   }
	            	 mini.alert('处理成功！');
	            	 seekhouseGrid.load({state:'${appointment.appointmentState}', appointmentId:'${appointment.id}'});
	               },
	               error:function(){
	            	  mini.alert('error');
	               }
	           });
    	   }else{
    		  var txt= encodeURI(mini.get('mtextAreaNosee').getValue());
   	        $.ajax({
	               type: "post",
	               url: bootPATH+"/userAppointment/afterNoFinishState.do",
	               data: {id:_dealselectkey,state:mini.get('radilbttuonlAfter').getValue(),noseeValue:mini.get('radilbttuonlNoseeReason').getValue(),memo:txt},
	               dataType: "json",
	               success: function(data){
	            	   var fg=true;
	            	   for(var i=0;i<_havenRecord.length;i++){
	            		   if(_havenRecord==_dealselectkey){
	            			   fg=false;
	            			   break;
	            		   }
	            	   }
	            	   if(fg){
	            	     _havenRecord.push(_dealselectkey);//记录下处理的记录
	            	   }
	            	 mini.alert('处理成功！');
	            	 seekhouseGrid.load({state:'${appointment.appointmentState}', appointmentId:'${appointment.id}'});
	               },
	               error:function(){
	            	  mini.alert('error');
	               }
	           });
    	   }
       }
       /***
       * 经纪人帮用户确认
       *
       */
       function confirmAppointment(){
  	        $.ajax({
	               type: "post",
	               url: bootPATH+"/userAppointment/userConfirm.do",
	               data: {apponitmentId:'${appointment.id}'},
	               dataType: "json",
	               success: function(data){
	            	 mini.alert('操作成功！');
	            	//刷新页面
	            	 window.location.href= window.location.href;
	               },
	               error:function(){
	            	  mini.alert('error');
	               }
	           });

    	   
       }
       
       /***
       *
       * 用户未到提交操作 处理
       */
       function submitAfterSee(){
    	   
  	        $.ajax({
	               type: "post",
	               url: bootPATH+"userAppointment/agentCheckIn.do",
	               data: {appointmentId:'${appointment.id}',ifUserCheckIn:1,flag:2,state:mini.get('radioCheckboxlist').getValue()},
	               dataType: "json",
	               success: function(data){
	            	   mini.alert('签到成功！');
	            	// $('#recordbutton').show();取消和改期的不应该填写看房记录
	            	   //刷新列表重新去数据
	            	 seekhouseGrid.load({state:'3', appointmentId:'${appointment.id}'});
	            	 mini.get('afterWin').hide();
	            	 mini.get('chekinWin').hide();
	            	 window.location.href= window.location.href;
	               },
	               error:function(){
	            	  mini.alert('error');
	               }
	           });
    	   
       }
       
       /**
       * 判断是否填写完
       */
       function closeSeeRecordWin(){
    	   //如果所有的都是未看房，修改约会状态
    	   
    	   var flag=true;
    	  var dealData= seekhouseGrid.getData();
    	//  if(_havenRecord.length<dealData.length){
    	//	  mini.alert('请填完所有的房源！');
    	//	  return;
    	//  }
      	   for(var v=0;v<dealData.length;v++) {
    		      if(dealData[v].state!=6){//如果有一不是未看房，就不修改状态
    		    	  flag=false;	
    		      }
    	   }
      	   //如果都是
      	   if(flag){//如果都是未看房，更新
     	        $.ajax({
 	               type: "post",
 	               url: bootPATH+"userAppointment/appointmetnNoSeeState.do",
 	               data: {appointmentId:'${appointment.id}'},
 	               dataType: "json",
 	               success: function(data){
 	            	  window.location.href= window.location.href;
 	               },
 	               error:function(){
 	            	  mini.alert('error');
 	               }
 	           });
      	   }
 	       	var win = mini.get("seeRecordwin");
            win.hide();
      	
       }
    </script>

</body>
</html>