<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String categoryType=request.getAttribute("categoryType")+"";
String menuno=request.getAttribute("menuno")+"";
String contentid=request.getAttribute("contentid")+"";
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>企业云平台管理系统</title>
		<link href="<%=path%>/static/xcloud/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/common.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-grid.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/static/xcloud/css/ligerui-form.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/static/xcloud/js/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/ligerui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/json2.js" type="text/javascript"></script>
		<script src="<%=path%>/static/xcloud/js/common.js" type="text/javascript"></script>
	</head>
	<body>
		<div style="margin-left: 30px;" class="detail-middle l-form">
			<dl class="l-custom-tab">
				<dt><a href="addquest.do?categoryType=<%=categoryType %>&menuno=<%=menuno %>">新增内容</a></dt>
				<dd><a href="">关联试卷</a></dd>
			</dl>
			<div id="switch-btn" class="switch-btn">
				<input  type="radio" value="0" name="switch" id="rbtnl_0" url="addrelation.do?categoryType=<%=categoryType %>&menuno=<%=menuno %>&contentid=<%=contentid %>" /><label for="rbtnl_0">新建试卷</label>
				<input type="radio" value="1" name="switch" id="rbtnl_1" url="" checked="checked"/><label for="rbtnl_1">关联已有试卷</label>
			</div>
			<div id="mainsearch" style="width: 98%;display: none">
				<div class="searchbox onlySearchBox">
					<form id="formsearch" class="l-form"></form>
				</div>
			</div>
			<div id="maingrid"></div>
		</div>	
		<script type="text/javascript">
			var crForm = {
				main : "#maingrid",
				formsearch : "#formsearch",
				init : function() {
					var self = this;
					$("#switch-btn input").ligerRadio();
					$("#switch-btn input").change(function(){
						if ($(this).attr("checked")){
							document.location.href = $(this).attr("url");
						}
					});
				   LG.setFormDefaultBtn(self.cancel,function(){self.save()});
				   this.grid = jQuery(self.main).ligerGrid({ 
			         	columns: [
							{display:"编号",name:"dcode"},
							{display:"名称",name:"dname"},
							{display:"上传时间",name:"duploaddate"},
							{display:"上传人",name:"duploadperson"},
							{display:"状态",name:"dstatus",render: LG.status}
					   ],
			           dataAction: 'server', 
			           pageSize: 20, 
			           toolbar: {},
			           headerRowHeight: 29,
			           selectRowButtonOnly: false,
			           rowHeight : 28,
			           cssClass : "l-grid-lineheight",
			           pageSizeOptions:[20,50,100],
			           url:  'loadExist.do', sortName: 'did',sortOrder:'desc',
			           width: '98%', height: '100%',heightDiff:-39, checkbox: true
			      });
			      //self.search();
				},
				save : function() {
		            var selecteds = this.grid.getSelecteds();
	                if (selecteds.length == 0) { LG.tip(LG.INFO.ONE); return; }
	                 if (selecteds.length > 1) { LG.tip(LG.INFO.TWO); return; }
	                 //添加的问卷跟内容 进行关联
					var contentdata={'tblContent.did':<%=contentid%>,'tblContent.daction':1,'tblContent.dmapping':selecteds[0].did};
					$.post(
						"<%=path%>/TblContent/update.do",
						contentdata,
						function(data){
							var win = parent || window;
							if(data.iserror){
								 win.LG.showError(data.message);
								return ;
							}else{
								 win.LG.showSuccess(LG.INFO.SIX,null,1000);
								 win.LG.closeCurrentTab(null);
		            		     win.loadlist();
	                        	 //win.LG.closeAndReloadParent(null, <%=menuno%>);
							}
						},
						"json"
					);
				},
				cancel : function(item) {
					var win = parent || window;
	            	win.LG.closeCurrentTab(null);
				},
				reload : function() {
					this.grid.loadData();
				}
			}
			crForm.init();
			
		</script>
	</body>
</html>