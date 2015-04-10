<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <link href="styles/page.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="script/boot.js"></script>
    <script type="text/javascript" src="script/iwac.common.js"></script>
    <script type="text/javascript" >var userId = ${userId};</script>
    <script type="text/javascript" src="script/pages/appointmentDetailPanel.js"></script>
</head>
<body style="height:100%;" path="<%=basePath%>">
    <div class="page">
        <div class="head" id="search">
            <div style="margin: 15px 25px;">
                <span style="margin-left: 20px;">
                                                 约会状态： <input name="appointmentState" id="appointmentState" class="mini-combobox" style="width: 100px;"
                         textField="text" valueField="id" data="[{id:'0',text:'所有'},{id:'1',text:'待确认'},{id:'2',text:'待看房'},{id:'3',text:'经纪人已签到'},{id:'4',text:'未到未看房'},{id:'5',text:'已到未看房'},{id:'6',text:'已到已看房'},{id:'7',text:'已改期'},{id:'8',text:'已取消'}]" value="0" required="true" allowInput="false" showNullItem="false" />
                </span>
                <span>  
                                                    约会时间:<input id="startTime" style="width:160px" name="startTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm" timeFormat="HH:mm" showTime="true" showOkButton="true" showClearButton="false"/>-<input name="endTime" id="endTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm" timeFormat="HH:mm" showTime="true" showOkButton="true" style="width:160px" showClearButton="false"/>
                </span> 
                <span style="margin-left: 50px;">
                   <a class="mini-button" onclick="iwac.appointment.search()"
                    style="border: 1px #F0C000 solid; background: url(pics/search_bg.png) repeat-x; width: 60px;">搜 索</a>
                   <a class="mini-button" onclick="iwac.appointment.reset()"
                    style="border: 1px #F0C000 solid; background: url(pics/cancel_bg.png) repeat-x; width: 60px; margin-left: 20px;">取 消</a>
                </span>
            </div>
        </div>
    </div>
        <!--列表-->
          <div class="mini-fit" style="height:400px">
                <div id="datagrid1" class="mini-datagrid" idField="id" style="height:100%;" url="appointment/list.do" multiSelect="true" showfooter="false">
                    <div property="columns">
                        <div field="seehouseNumber" align="center" headeralign="center">约会编号</div>
                        <div field="meetAddress" align="center" headeralign="center">约会地点</div>
                        <div field="seeHouseCount" headeralign="center" align="center">看房/套</div>
                        <div field="appointmentState" headeralign="center" align="center">约会状态</div>
                        <div field="appointmentTime" headeralign="center" align="center">约会时间</div>
                        <div field="createTime" headeralign="center" align="center">创建时间</div>
                        <div field="updateTime" headeralign="center" align="center">最后更新时间</div>
                        <div name="action" headeralign="center" align="center">查看</div>
                    </div>
                </div>
          </div>
</body>
<script type="text/javascript" src="script/pages/appointmentSearch.js"></script>
</html>
