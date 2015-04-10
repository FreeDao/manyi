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
</head>
<body style="height:100%;" path="<%=basePath%>">
    <div class="page">
        <div class="head" id="search">
            <div style="margin: 15px 25px;">
                <span>姓名：</span> 
                <span> 
                    <input name="realName" id="realName" class="mini-textbox" emptyText="请输入姓名" style="width: 70px;" /> 
                    <input id="gender" name="gender" class="mini-combobox" style="width: 50px;" textField="text" valueField="id" 
                    url="subs/gender.txt" value="0" required="true" allowInput="false" showNullItem="false" />
                </span> 
                <span style="margin-left: 20px;">
                                                手机号：<input name="mobile" id="mobile" class="mini-textbox" emptyText="请输入手机号" />
                </span>
                <span style="margin-left: 20px;">
                                                 用户状态： <input name="bizStatus" id="bizStatus" class="mini-combobox" style="width: 100px;"
                         textField="text" valueField="id" url="subs/userBizStatus.txt" value="-1" required="true" allowInput="false" showNullItem="false" />
                </span>
                <span style="margin-left: 20px;">
                                                最后登录时间： <input id="lastLoginTimeSearch" name="lastLoginTimeSearch"  class="mini-combobox" style="width: 100px;" 
                   textField="text" valueField="id" url="subs/lastLoginTime.txt" value="0" required="true" allowInput="false" showNullItem="false" />
                </span>
                <span style="margin-left: 20px;"> 
                                                收藏过房源： <input id="collectionNum" name="collectionNum" class="mini-combobox"
                    style="width: 60px;" textField="text" valueField="id" url="subs/general.txt" value="0" required="true"
                    allowInput="false" showNullItem="false" />
                </span>
            </div>
            <div style="margin: 15px 25px;">
                <span>  
                                                    看过房:<input id="sawNum" name="sawNum" class="mini-combobox" style="width: 60px;" textField="text"
                         valueField="id" url="subs/general.txt" value="0" required="true" allowInput="false" showNullItem="false" />
                </span> 
                <span style="margin-left: 20px;"> 
                                            看房单有房源： <input id="inchartNum" name="inchartNum" class="mini-combobox"
                    style="width: 60px;" textField="text" valueField="id" url="subs/general.txt" value="0" required="true"
                    allowInput="false" showNullItem="false" />
                </span> 
                <span style="margin-left: 20px;"> 
                                                推荐过房源： <input id="recommendNum" name="recommendNum" class="mini-combobox"
                    style="width: 60px;" textField="text" valueField="id" url="subs/general.txt" value="0" required="true"
                    allowInput="false" showNullItem="false" />
                </span> 
                <!-- <span style="margin-left: 20px;"> 
                                                存在待处理申请： <input id="waitdealNum" name="waitdealNum" class="mini-combobox"
                    style="width: 60px;" textField="text" valueField="id" url="subs/general.txt" value="0" required="true"
                    allowInput="false" showNullItem="false" />
                </span>  -->
                <span style="margin-left: 50px;">
                   <a class="mini-button" onclick="iwac.user.search()"
                    style="border: 1px #F0C000 solid; background: url(pics/search_bg.png) repeat-x; width: 60px;">搜 索</a>
                   <a class="mini-button" onclick="iwac.user.reset()"
                    style="border: 1px #F0C000 solid; background: url(pics/cancel_bg.png) repeat-x; width: 60px; margin-left: 20px;">取 消</a>
                </span>
            </div>
        </div>
    </div>
        <!--列表-->
          <div class="mini-fit" style="height:400px">
                <div id="datagrid1" class="mini-datagrid" style="height:100%;" url="user/list.do" multiSelect="true" showfooter="false">
                    <div property="columns">
                        <div type="indexcolumn" ></div>
                        <div field="realName"  align="center" headeralign="center" width="50">姓名</div>
                        <div field="gender"  align="center" headeralign="center" width="50">性别</div>
                        <div field="mobile" align="center" headeralign="center">手机号</div>
                        <div field="applyedNum" align="center" headeralign="center">申请约看的</div>
                        <div field="sawNum" headeralign="center" align="center">已看的</div>
                        <div field="inchartNum" headeralign="center" align="center">看房单房源</div>
                        <div field="recommendNum" headeralign="center" align="center">推荐房源</div>
                        <div field="collectionNum" headeralign="center" align="center">收藏房源</div>
                        <div field="createTime" headeralign="center" align="center">注册时间</div>
                        <div field="lastLoginTime" headeralign="center" align="center">最后登录</div>
                        <div field="bizStatus" headeralign="center" align="center">当前状态</div>
                        <!-- <div field="waitdealNum" headeralign="center" align="center">待处理申请</div> -->
                        <div name="action" headeralign="center" align="center">操作</div>
                    </div>
                </div>
          </div>
</body>
<script type="text/javascript" src="script/pages/userSearch.js"></script>
</html>
