<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" 
		xmlns:c="http://java.sun.com/jsp/jstl/core"
		xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
		xmlns:fun="http://java.sun.com/jsp/jstl/functions"
		xmlns:x="http://java.sun.com/jsp/jstl/xml"
		xmlns:sql="http://java.sun.com/jsp/jstl/sql"
		xmlns:tiles="http://tiles.apache.org/tags-tiles"
		xmlns:tilesextras="http://tiles.apache.org/tags-tiles-extras"
		xmlns:sc="http://www.springframework.org/tags"
		xmlns:form="http://www.springframework.org/tags/form" version="2.0">
		
	<jsp:directive.page contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8" session="true" />
	
	<script type="text/javascript">
		function showcontent(language){
			$("iframe").attr("src",language);
		}
		
		function houseType(type){
			$("iframe").attr("src",'/residence/list?houseType='+type);
		}
	</script>
	
	

	<ul>
		<li><a href="javascript:void(0)" onclick="showcontent('list.html')">房源信息</a>
			<UL>
				<li onclick="houseType(1);">有效</li>
				<li onclick="houseType(2);">出售</li>
				<li onclick="houseType(3);">出租</li>
				<li onclick="houseType(4);">自住</li>
				<li onclick="houseType(6);">暂缓</li>
				<li onclick="houseType(5);">预订</li>
				<li onclick="houseType(8);">无效</li>
			</UL>
		</li>
		<li><a href="javascript:void(0)" onclick="showcontent('/city/list')">城市信息</a></li>
	</ul>

</jsp:root>

