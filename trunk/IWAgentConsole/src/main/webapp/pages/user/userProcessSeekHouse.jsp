<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

    <span>待预约|已变更列表</span>
		<a class="mini-button" onclick="reloadUnprocGrid()"
		                    style="border: 1px #F0C000 solid; width: 60px;">刷新</a>
		<a class="mini-button" onclick="mapView()"
		                    style="border: 1px #F0C000 solid; width: 80px;">查看地图</a>
    <div id="waitDealgrid" class="mini-datagrid" showPager="false" style="height:300px;" allowResize="true"
         idField="id" multiSelect="true">
        <div property="columns">
           <div type="indexcolumn" ></div>
            <div type="checkcolumn" ></div> 
            <div field="bankuai" width="120" headerAlign="center" allowSort="true">板块</div>    
            <div field="xiaoqu" width="120" headerAlign="center" allowSort="true">小区</div>    
 
            <div field="fanghao" width="120">房号</div>
            <div field="huxing" width="100">户型</div>
            <div field="jiage" dataType="currency" currencyUnit="￥" align="right" width="100" allowSort="true">价格</div>
            <div field="wishTime" width="100">希望约看时间</div>
            <div field="lianxifs" width="100" headerAlign="center">联系方式</div>    

             <div field="yiyou" width="100" renderer="onRendererYuyue">已有预约</div>
             <div field="state" width="100" renderer="onStateRenderer">预约情况</div>
             <div field="caozuo" width="100" renderer="onRendererCaozuo">操作</div>
             <div field="memo" width="100">备注</div>
        </div>
    </div>
    <!-- 添加约看部分 -->
   <div id="ck1" name="product" class="mini-checkbox" checked="false" readOnly="false" text="添加直接约看的房源" onvaluechanged="onValueChanged"></div>
   <div id="addPanel" class="mini-panel" title="直接约看的房源面板" style="width:100%;height:200px;" expanded="false"
    showToolbar="true" showCollapseButton="false" showFooter="true" allowResize="true" collapseOnTitleClick="false">
    
       <div property="toolbar" >
        <input type='button' value='添加房源' style='vertical-align:middle;' onclick="openSelectHousees()"/>
       </div>
		       <div id="directgrid" class="mini-datagrid" style="height:300px;" showPager="false" allowResize="false"
		          idField="id" multiSelect="true">
			        <div property="columns">
			            <div type="indexcolumn" ></div>
			            <div type="checkcolumn" ></div>    
			            <div field="bankuai" width="120" headerAlign="center" >板块</div>    
			            <div field="xiaoqu" width="120" headerAlign="center" >小区</div>    
			            <div field="fanghao" width="120">房号</div>
			            <div field="huxing" width="100">户型</div>
			            <div field="jiage" dataType="currency" currencyUnit="￥" align="right" width="100" >价格</div>
			            <div field="lianxifs" width="100" headerAlign="center">联系方式</div>
			            <div field="yiyou" width="100" renderer="onRendererYuyue">已有预约</div>
                        <div field="state" width="100" renderer="onStateRenderer">预约情况</div>
                        <div field="caozuo" width="100" renderer="onRendererCaozuo">操作</div>   
                        <div field="memo" width="100">备注</div> 
			        </div>
	           </div>
   </div>
   
    <div>
            <a class="mini-button" style='vertical-align:right;' onclick="sendHouseWin()" >推送约看时间</a>
    </div> 
   <!-- 添加约看部分 -->
    <span>已取消列表</span>
       <a class="mini-button" onclick="reloadCancelGrid()"
                    style="border: 1px #F0C000 solid; width: 60px;">刷新</a>

    <div id="cancelDealgrid" class="mini-datagrid" style="height:300px;" allowResize="true"
          showPager="false" idField="id" multiSelect="true">
        <div property="columns">
           <div type="indexcolumn" ></div>
            <div type="checkcolumn" ></div> 
            <div field="bankuai" width="120" headerAlign="center" allowSort="true">板块</div>    
            <div field="xiaoqu" width="120" headerAlign="center" allowSort="true">小区</div>    
 
            <div field="fanghao" width="120">房号</div>
            <div field="huxing" width="100">户型</div>
            <div field="jiage" dataType="currency" currencyUnit="￥" align="right" width="100" allowSort="true">价格</div>
            <div field="wishTime" width="100">希望约看时间</div>

            <div field="lianxifs" width="100" headerAlign="center">联系方式</div>    

             <div field="seeHouseTime" width="100">看房时间</div>
             <div field="caozuo" width="100" renderer="onRendererFangdong">联系房东</div>
        </div>
    </div>

<!-- 隐藏的已有约会窗口 -->
<div id="havewin" class="mini-window" title="已有约会" style="width:630px;height:250px;" 
    showMaxButton="false" showCollapseButton="false" showShadow="true"
    showToolbar="true" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
    
      <div id="havingAppointMentgrid" class="mini-datagrid" style="width:600px;height:300px;" allowResize="true"
          idField="id" multiSelect="true">
        <div property="columns">
            <div type="indexcolumn"></div>
            <div field="appointmentTime" width="200" headerAlign="center" allowSort="true">约看时间</div>    
            <div field="status" width="200" headerAlign="center" renderer="onStateAppointRenderer">是否确认</div>    
            <div field="agentName" width="200" headerAlign="center" allowSort="true">经纪人姓名</div>    
        </div>
    </div>
</div>
<!-- 推送消息隐藏窗口 -->
<div id="sendwin" class="mini-window" title="推送约看时间" style="width:420px;height:200px;" 
    showMaxButton="false" showCollapseButton="false" showShadow="true"
    showToolbar="true" showFooter="true" showModal="true" allowResize="false" allowDrag="true">
    
		<div id="sendForm1" >
		        <input id="houseIds" name="houseIds" class="mini-hidden" />
		        <table>
		            <tr>
		                <td>
		                    <label for="textbox1$text">汇总：</label>
		                </td>
		                <td>
		                    <input id="countMsg"  name="username" class="mini-textbox asLabel" readOnly="true" value="textbox" required="true" />
		                </td>
		            </tr>
		            <tr>
		                <td>
		                    <label for="date1$text">约看时间选择：</label>
		                </td>
		                <td>
		                  <input id="appointmentTime" class="mini-datepicker" style="width:150px;" showTime="true" format="yyyy-MM-dd H:mm" required="true" />    
		                </td>
		            </tr>
		            <tr>
		                <td>
		                    <label for="addr$text">碰头地点：</label>
		                </td>
		                <td>
		                    <input id="appointmentAddr"  name="appointmentAddr" class="mini-textbox" emptyText="不超过15个字" maxLength="15" style="width:300px;"  required="true" />
		                </td>
		            </tr>
		            <tr>
		                <td>
		                    <label for="usermemo$text">给用户留的备注：</label>
		                </td>
		                <td>
		                    <input id="usermemo"  name="usermemo"  emptyText="不超过20个字" maxLength="20" style="width:300px;" class="mini-textbox" />
		                </td>
		            </tr>
		          </table>
		 </div>
		<div property="footer" style="text-align:center;padding:5px;">
	        <input type='button' value='提交' onclick="submitWindowSend()" style='vertical-align:middle;'/>
	        <input type='button' value='取消' onclick="hideWindowSend()" style='vertical-align:middle;'/>
	    </div>
</div>


<!-- 记录约看弹出框 -->
<div id="recordwin" class="mini-window" title="联系结果" style="width:410px;height:250px;" 
    showMaxButton="false" showCollapseButton="false" showShadow="true"
    showToolbar="true" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
    
      <div id="radilbttuonl" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" repeatDirection="Horizontal"  onvaluechanged="onRadioValueChanged"
       textField="text" valueField="id" value="0"  data="[{ 'id': '1', 'text': '改房源已出租' },{ 'id': '2', 'text': '改房源已停止出售' },{ 'id': '3', 'text': '无法联系到房东' },{ 'id': '4', 'text': '与房东约定时间' }]">

      </div>
     <textarea id="mtextArea" class="mini-textarea" readOnly="true" emptyText="与房东约定时间备注" style="width:300px;height:100px;"></textarea>
    <div property="footer" style="text-align:center;padding:5px;">
        <input type='button' value='提交' onclick="submitWindowRecord()" style='vertical-align:middle;'/>
        <input type='button' value='取消' onclick="hideWindowRecord()" style='vertical-align:middle;'/>
    </div>
</div>
<!-- 隐藏的已有约会窗口 -->

    <script type="text/javascript">
        mini.parse();
        
       //更新两个面板数据
        var waitDealgrid = mini.get("waitDealgrid");
          waitDealgrid.setUrl(bootPATH+'userSeekHouse/getWaitDealSeeHouseList.do');
         waitDealgrid.load({customerId:'${customerId}',type:0});//客户id 和推送类型
        
        //已取消列表数据加载
        var cancelDealgrid = mini.get("cancelDealgrid");
        cancelDealgrid.setUrl(bootPATH+'userSeekHouse/getCancelListSeeHouseList.do');
        cancelDealgrid.load({custemerId:'${customerId}'});
        
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
       	 
            return '<a class="mini-button mini-button-iconTop" iconCls="icon-add" onclick="haveAppointment('+e.record.houseId+')" >已有约看</a>';
        }
        
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
        	
        //约会状态
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
        
        
        /***
         * 联系房东弹出框
         ***/
        function onRendererFangdong(e) {
            return '<a class="mini-button mini-button-iconTop" iconCls="icon-add" onclick="confirmFangdong('+e.record.id+')" >处理</a>';
        }
        
        /***
        * 你确认已经和房东沟通过，获取到
        **/
        function confirmFangdong(id){
            mini.confirm("你确认已经和房东沟通过了么？", "",
                function (action) {
                    if (action == "ok") {
            	        $.ajax({
         	               type: "post",
         	               url: bootPATH+"userSeekHouse/cancelSeekHouse.do",
         	               data: {id:id},
         	               dataType: "json",
         	               success: function(data){
         	            	 mini.alert('取消成功！');
         	            	 cancelDealgrid.reload();
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
        
        /***
        * 已有约看弹出框
        ***/
        function haveAppointment(houseId){
        	var win = mini.get("havewin");
            win.show();
            var cancelDealgrid = mini.get("havingAppointMentgrid");
            cancelDealgrid.setUrl(bootPATH+'userSeekHouse/getWillAppointmentList.do');
            cancelDealgrid.load({houseId:houseId});
        }
        
        var _wailprocessSelectId='';
        /***
        * 记录联系弹出框
        ***/
        function recordContact(id){
        	
	       	 _wailprocessSelectId=id;
	       	 var win = mini.get("recordwin");
	       	 win.show();
	     	 var rbl=mini.get("radilbttuonl");
	     	 rbl.setValue(0);
			 var texta=mini.get('mtextArea');
			 texta.setValue('');
        }
        
        /***
        * 提交房东联系结果
        ***/
       function  submitWindowRecord(){
	       	var rbl=mini.get("radilbttuonl");
			var texta=mini.get('mtextArea');
	       	var txt;
	       	if(rbl.getValue()==4){//如果选与房东约定时间，存文本的信息。
	       		    txt=texta.getValue();
	       	}else{//取选项文本值
	       		var item = rbl.getSelected();
	       		   txt=item.text;
	       	};
	       	//获取选中行的数据
	       	//var cancelData=.getData();
	       	var srow = mini.get("waitDealgrid").findRow(function(row){
	       	    if(row.id == _wailprocessSelectId) return true;
	       	});
	       	var sendData={};
	       	sendData['id']=_wailprocessSelectId;
	       	sendData['memo']=encodeURI(txt);
	       	sendData['type']=rbl.getValue();
	       	sendData['houseId']=srow.houseId;
	       	sendData['lianxifs']=srow.lianxifs;
	        $.ajax({
	               type: "post",
	               url: bootPATH+"userSeekHouse/recordContact.do",
	               data: {params:mini.encode(sendData)},
	               dataType: "json",
	               success: function(result){
	      	       	 var win = mini.get("recordwin");
	            	   mini.alert('保存成功！');
	            	   waitDealgrid.reload();
	            	   win.hide();
	               },
	               error:function(){
	               	 mini.alert('error');
	               }
	           });
        }
        /**
        *关闭联系房东窗口
        *
        */
        function hideWindowRecord(){
	        	var rbl=mini.get("radilbttuonl");	
		       	rbl.setValue(0);
		       	var texta=mini.get('mtextArea');
		       	texta.setValue('');
		          	var win = mini.get("recordwin");
		       	win.hide();
       }
       /***
       * 展开直接添加房源面板
       **/
       function onValueChanged(e){
	         	var checked = this.getChecked();
	      	    var panel = mini.get("addPanel");
	           if(checked){//加载数据
	        	    panel.setExpanded(true);
	        	    var directGrid = mini.get("directgrid");
	        	    directGrid.setUrl(bootPATH+"userSeekHouse/getWaitDealSeeHouseList.do");
	        	    directGrid.load({customerId:'${customerId}',type:3});
	        	    
	           }else{
	           	   panel.setExpanded(false);
	           }
       }
       /**
       * 联系选项改变
       */
       function onRadioValueChanged(e){
	       	var texta=mini.get('mtextArea');
	       	if(this.getValue()==4){
	       		texta.setReadOnly(false);
	       	}else{
	       		texta.setReadOnly(true);
	           	texta.setValue('');
	       	}
       }
       /***
       * 打开选择房源窗口 , 并保存选择的房源
       */
       function openSelectHousees(){
    	   
    	
    	var waitDealgrid=mini.get("waitDealgrid");
    	var directGrid = mini.get("directgrid");
    	
    	var houseIds=new Array();
    	
    	iwac.housePanel.open({
	   		title:'选择房源',
	   		ondestroy:function(rows){
	   			    if(rows=='close'){
	   			    	return;
	   			    }
	   			    
	   				for(var v=0;v<rows.length;v++){ 
	   					if(
	   					(
	   					waitDealgrid.findRows(function(row){
	   					    if(row.houseId ==rows[v].houseId) return true;
	   					}).length==0)
	   					&&
	   					(
	   					directGrid.findRows(function(row){
	   					    if(row.houseId ==rows[v].houseId) return true;
	   					}).length==0)
	   					){
	   						houseIds.push(rows[v].houseId);
	   					};
	   				}
	   				if(houseIds.length==0){
	   					mini.alert('请确认你选择的房源是否已存在待约列表中！');
	   					return;
	   				}
	   				

	   		        $.ajax({
	 	               type: "post",
	 	               url: bootPATH+"userSeekHouse/addRecommand.do",
	 	               data: {ids:houseIds.join(','),customerId:'${customerId}'},
	 	               dataType: "json",
	 	               success: function(result){
	 	            	   mini.alert('保存成功！');
	 	            	  directGrid.reload();
	 	               },
	 	               error:function(){
	 	               	 mini.alert('error');
	 	               }
	 	           });
	   		}
      	});
       }
       /**
       *弹出 推送窗口
       */
       function sendHouseWin(){
        	var waitDealgrid=mini.get("waitDealgrid");
         	var directGrid = mini.get("directgrid");
    	   var waitDealgridselectedrows=waitDealgrid.getSelecteds();
    	   var directGridselectedrows=directGrid.getSelecteds();
    	   if(waitDealgridselectedrows.length==0&&directGridselectedrows.length==0){
    		   mini.alert("请选择房源！");
    		   return ;
    	   }
    	   mini.get("countMsg").setValue('你选择了'+(directGridselectedrows.length+waitDealgridselectedrows.length)+'套房源');
    	   var win = mini.get("sendwin");
           win.show();
       }
       
       /***
        * 提交推送信息
        ***/
       function  submitWindowSend(){
	        var win = mini.get("sendwin");
	       	
            var form = new mini.Form("#sendForm1");
            form.validate();
            if(form.isValid() == true){
                var data = form.getData(false, false);
                
            	var waitDealgrid=mini.get("waitDealgrid");
            	var directGrid = mini.get("directgrid");
         	   //获取选择推送的houseId
         	   var houseIds='';
        	   var waitSelectedrows=waitDealgrid.getSelecteds();
        	   var directSelectedrows=directGrid.getSelecteds();
        	   for(var v=0;v<waitSelectedrows.length;v++) { 
        		   houseIds+=waitSelectedrows[v].houseId+',';
        	   }
        	   for(var v=0;v<directSelectedrows.length;v++) { 
        		   houseIds+=directSelectedrows[v].houseId+',';
        	   }
        	   
        	   var appointmentTime = mini.formatDate (mini.get("appointmentTime").getValue(), "yyyy-MM-dd HH:mm");
        	   data['houseIds']=houseIds;
        	   data['customerId']='${customerId}';
        	   data['appointmentTime']=appointmentTime+":00";
        	   data['usermemo']= encodeURI(mini.get("usermemo").getValue());
        	   data['appointmentAddr']=encodeURI(mini.get("appointmentAddr").getValue());
        	   
    	        $.ajax({
 	               type: "post",
 	               url: bootPATH+"userSeekHouse/addApponitment.do",
 	               data:{params:mini.encode(data)},
 	               dataType: "json",
 	               success: function(result){
 	            	  if(result.errorCode==0){
 	            		 win.hide();
 		        	    var directGrid = mini.get("directgrid");
 		        	    directGrid.load({customerId:'${customerId}',type:3});
 	            	  }
 	            	 waitDealgrid.reload();
 	            	 mini.alert("约会添加成功！");
 	               },
 	               error:function(){
 	            	  mini.alert('error');
 	               }
 	           });
            }else{
            	mini.alert("请正确填写表单！");
            	return;
            }
            
        }
       /**
        *关闭联系房东窗口
        *
        */
        function hideWindowSend(){
	            mini.get("appointmentAddr").setValue('');
	            mini.get("usermemo").setValue('');
	            mini.get("appointmentTime").setValue('');
		        var win = mini.get("sendwin");
		       	win.hide();
       }
       
       function reloadUnprocGrid(){
    	   waitDealgrid.reload();
       }
       
       function reloadCancelGrid(){
    	   cancelDealgrid.reload();
       }
       
       function mapView(){
       		mini.open({
       			url:'<%=basePath%>seekHouse/map/view.do?userId=${customerId}',
       			width:'1000',
       			height:'700',
       			title:'地图',
       			allowResize:true,
       			allowDrag:true,
       			showMaxButton:true,
       			showModal:false
       		});
       }
       
       
    </script>

</body>
</html>