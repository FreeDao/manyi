/**
 * @author carvink
 */
window.iwac = window.iwac||{};
iwac.mapView=$.extend(iwac.mapView||{},{
	/**
	 * 初始化页面以及数据
	 */
	init:function(){
		this.path = $("body").attr("path");
		this.loadData();
	},
	loadData:function(){
		var path = this.path;
		iwac.common.mask("正在加载数据...");
		$.ajax({
			url:path+"seekHouse/position/list.do",
			type:'post',
			data:{
				userId:userId
			},
			timeout:'60000',
			success:function(data){
				iwac.common.unmask();
				if(data){
					var arrayData = [];
					if($.isArray(data)){
						arrayData = data;
					}else{
						arrayData.push(data);
					}
					var x=0,y=0,z=0;
					//遍历列表，将1、7（预约中、改期中）归为一类，8（取消中）归为另一类，再为两类数据进行标注序号，后面会在地图上标注出来
					$.each(arrayData,function(){
						if(this.status == 1 && this.recommendSource == 3){
							this.serialNumber = ++z;
						}else if(this.status == 1 || this.status == 7){
							this.serialNumber = ++x;
						}else{
							this.serialNumber = ++y;
						}
					});
					var pointArray = [];
					var map = new BMap.Map("allmap");            // 创建Map实例
					var ll = {};
					$.each(arrayData,function(i){
						//将相同经纬度的房源进行归并，若房源的经纬度不是同时都有值，那么将不会标注在地图上，但是，这种房源还是会占有列表中的序号
						if(this.longitude && this.latitude){
							//以经度_纬度作用ll的属性名，这样，相同经纬度的数据很容易就归并在一起
							var tmp = this.longitude+"_"+this.latitude; 
							if(!ll[tmp]){
								ll[tmp]=[];
							}
							ll[tmp].push(this);
							var point = new BMap.Point(this.longitude,this.latitude);
							pointArray.push(point);
						}
					});
					for(var count in ll){
						var objects = ll[count];
						if(objects){
							var custext,icon,iconColor,labelText,labelStyle={color:'#ffffff',fontSize:'14px',fontWeight:'bold',borderWidth:0,backgroundColor:'transparent'};
							var firstObject = objects[0];
							var point = new BMap.Point(firstObject.longitude,firstObject.latitude);
							if(ll[count].length>1){
								//多个房源经纬度相同，进行合并，用自定义蓝色标注
								iconColor = 'blue';
								var ss = [];
								$.each(objects,function(){
									ss.push(this.serialNumber);
								})
								custext = ss.join(",");
								labelText = objects.length;
							}else{
								custext = firstObject.serialNumber;
								labelText = 1;
								//预约中(用户约看)用百度地图默认的红色的标注，所以不需要有任何修改
								
								//预约中(经纪人预约)，用自定义紫色的标注
								if(firstObject.status ==1 && firstObject.recommendSource == 3){
									iconColor = 'violet';
								}
								//改期中，用自定义绿色的标注
								else if(firstObject.status == 7){
									iconColor = 'green';
								//取消中，用自定义的橙色标注
								}else if(firstObject.status == 8){
									iconColor = 'orange';
								}
							}
							var marker = new BMap.Marker(point,iconColor?{
								icon:new BMap.Icon(path+'pics/icon/marker_'+iconColor+'_sprite_25x25.png', new BMap.Size(25, 25), {
							    	anchor: new BMap.Size(25, 25)
								})
							}:null);
							
							var label = new BMap.Label(labelText,{
								offset:new BMap.Size(4,2)
							});
							label.setStyle(labelStyle);
							marker.setLabel(label);
							map.addOverlay(marker);
							//自定义覆盖物，用来显示房源在列表上的序号
							var co = new CustomOverlay(point,custext);
							map.addOverlay(co);
						}
						
					}
					map.enableScrollWheelZoom();
					//添加导航控件
					map.addControl(new BMap.NavigationControl());
					if(pointArray.length>0){
						map.setViewport(pointArray);
					}else{
						mini.alert("无房源或者所有房源无经纬度信息，地图无法展示。",null,function(){
							if(window.CloseOwnerWindow){
								window.CloseOwnerWindow('close');
								return;
							}
						});
					}
				}
			},
			error:function(x,s){
				iwac.common.unmask();
				if(s == "parsererror"){
					if(iwac.common.checkLogin(x.responseText)){
						iwac.common.fwd($("body").attr("path"));
					}
				}else if(s == "timeout"){
					mini.alert("请求超时，请重试");
					return;
				}
				mini.alert("操作失败，请重试");
			},
			dataType:'json'
		});
	}
	
	
});


$(function(){
	iwac.mapView.init();
	
})