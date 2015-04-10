<%@ page language="java"  pageEncoding="utf-8"%>
		<div class="l-topmenu">
				<div class="l-topmenu-l">
					<img class="logo" src="../../../css/img/layout/logo.png"/>
				</div>
				
				<div class="l-topmenu-r">
					<span>您好,
					
					<%--
					<a href="javascript:void(0);" onclick="openTab('edit','编辑信息','<%=path%>/TblOperator/modifySelf.do?id=<%=((TblOperator) request.getSession().getAttribute("operator")).getDid()%>');">设置</a>|
					<a href="javascript:void(0);" onclick="openTab('modifypassword','密码修改','<%=path%>/TblOperator/modifyPassword.do');">密码修改</a>|
					 --%>
					<a href="/logout.do" class="l-exit">注销</a>
				</div>
				
			</div>
	










