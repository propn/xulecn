/**
/**
 * gmap - wooUI
 * 基于jQuery1.4.2+
 *
 * Google Map 封装，使用该面板之前，要先注册使用 Google 地图 API，地址为：http://ditu.google.com/intl/zh-CN/apis/maps/signup.html ，
 * 注册后得到一个key。
 * 然后将Google Map API加载，将上面步骤注册得到的key代入到下面的URL中
 * 例子：
 * 
 * <script src="http://ditu.google.com/maps?file=api&amp;v=2.x&amp;key=[map key]" type="text/javascript"></script>
 * 
 * http://www.toone.com.cn:9366
 * 的key为ABQIAAAAuHoeniZGOZ21TWjx9IMgexQXOzWZV_vhojgVgupPRCsCR1Qx_hRiHfC_lelL5gDvVQp2ybFiK5toPw
 * 
 * // Note: you will need to replace the sensor parameter below with either an explicit true or false value.
  <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true_or_false&amp;key=ABQIAAAAuHoeniZGOZ21TWjx9IMgexQXOzWZV_vhojgVgupPRCsCR1Qx_hRiHfC_lelL5gDvVQp2ybFiK5toPw" type="text/javascript"></script>
  <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAuHoeniZGOZ21TWjx9IMgexQXOzWZV_vhojgVgupPRCsCR1Qx_hRiHfC_lelL5gDvVQp2ybFiK5toPw" type="text/javascript"></script>
 *
 * 依赖于以下组件:
 *   resizable
 *   draggable
 *   panel
 */

(function($){
	$.woo = $.woo || {version:2.0};
	$.woo.gmap = {
	defaults : {
		plain : true,
		zoomLevel : 4,
		yaw : 180,
		pitch : 0,
		gmapType : 'map',			//'map',普通地图；'panorama' 街道视图全景
		
									//G_NORMAL_MAP （默认地图类型）普通的街道地图。
					  				//G_SATELLITE_MAP 卫星图像。
									//G_HYBRID_MAP 卫星图像上的主要街道透明层。
								  	//G_PHYSICAL_MAP 带有自然特征（如地形和植被）的地图。	 
								  	//G_MOON_ELEVATION_MAP 月球表面按海拔进行彩色编码的带阴影地形地图。
									//G_MOON_VISIBLE_MAP 显示来自月球轨道的照片。
									//G_MARS_ELEVATION_MAP 显示火星表面按海拔进行彩色编码的带阴影地形地图。
									//G_MARS_VISIBLE_MAP 显示来自火星轨道的照片。
									//G_MARS_INFRARED_MAP 显示火星表面带阴影的红外线地图，其中较热地区以亮色显示，较冷地区以暗色显示。 
									//G_SKY_VISIBLE_MAP 显示覆盖整个天体的天空拼接。 
									//G_SATELLITE_3D_MAP 结合 Google 地球浏览器插件，此地图类型可以通过卫星图像显示地球的完全交互式三维模型。会检测浏览器是否安装了地球插件。
									//G_DEFAULT_MAP_TYPES 上述前三种预定义地图类型数组（G_NORMAL_MAP、G_SATELLITE_MAP 和 G_HYBRID_MAP）。
									//G_MOON_MAP_TYPES 上述两种月球类型数组（G_MOON_ELEVATION_MAP 和 G_MOON_VISIBLE_MAP）。
									//G_MARS_MAP_TYPES 上面定义的三种火星地图类型数组（G_MARS_ELEVATION_MAP、G_MARS_VISIBLE_MAP 和 G_MARS_INFRARED_MAP）。
									//G_SKY_MAP_TYPES 上面定义的一种天空地图类型数组 (G_SKY_VISIBLE_MAP)。
		 
		border : false,
		show3dMap : true,		//显示三维地球
		showPhysicalMap : true, //显示自然地形图
		mapConfOpts: ['enableScrollWheelZoom','enableDoubleClickZoom','enableDragging'],
	    mapControls: ['GLargeMapControl','GMapTypeControl','NonExistantControl'],
	    promptInfo:"information",
	    noInternetInfo:"Can't load google map information, please check your internet.",
	    

		defaultView:"map" //"map","satellite","mix","earth"
	}
	};
	
	function Gmap(){
		
	};
	$.extend(Gmap.prototype,{
		init : function(container,options){
			
			if(!GMap2){
				alert(options.promptInfo, options.noInternetInfo);
				return;
			}
			var width = $(container).width();
			var height = $(container).height();
			
			if(this.gmapType === 'map'){//使用普通视图
				this.gmap = new GMap2(this.body.dom);
			}
			if(this.gmapType === 'panorama'){//使用街道视图全景
				this.gmap = new GStreetviewPanorama(this.body.dom);
			}
			this.gmap.enableGoogleBar();
			this.gmap.enableScrollWheelZoom();
		},
		setSize : function(){},
		getMap : function(){},
		/**
	     * 方法描述： 获取地图中心点
	     * @return {}
	     */
		getCenter : function(){},
		/**
	     * 方法描述： 获取地图中心点的经纬度
	     * @return {}
	     */
		getCenterLatLng : function(){},
		/**
	     * 方法描述： 添加标注
	     * @param {} markers
	     */
		addMarkers : function(){},
		
		addMarker : function(){},
		/**
	     * 方法描述： 添加地图控制
	     */
		addMapControls : function(){},
		addMapControl : function(){},
		addOptions : function(){},
		addOption : function(){},
		geoCodeLookup : function(){},
		addAddressToMap : function(){}
		
	});
	
	$.fn.window = function(options, param){
		if (typeof options == 'string'){
			switch(options){
			case 'options':
				return $.data(this[0], 'window').options;
			case 'window':
				return $.data(this[0], 'window').window;
			case 'setTitle':
				return this.each(function(){
					$(this).panel('setTitle', param);
				});
			case 'open':
				return this.each(function(){
					$(this).panel('open', param);
				});
			case 'close':
				return this.each(function(){
					$(this).panel('close', param);
				});
			case 'destroy':
				return this.each(function(){
					$(this).panel('destroy', param);
				});
			case 'refresh':
				return this.each(function(){
					$(this).panel('refresh');
				});
			case 'resize':
				return this.each(function(){
					$(this).panel('resize', param);
				});
			case 'move':
				return this.each(function(){
					$(this).panel('move', param);
				});
			case 'maximize':
				return this.each(function(){
					$(this).panel('maximize');
				});
			case 'minimize':
				return this.each(function(){
					$(this).panel('minimize');
				});
			case 'restore':
				return this.each(function(){
					$(this).panel('restore');
				});
			case 'collapse':
				return this.each(function(){
					$(this).panel('collapse', param);
				});
			case 'expand':
				return this.each(function(){
					$(this).panel('expand', param);
				});
			}
		}
		
		options = options || {};
		return this.each(function(){
			init(this, options);
			setProperties(this);
		});
	};
	$.fn.resizable = function(options){
		var el = this.data('resizable');
		if(el){
			return el;
		}
		options = $.extend({},$.woo.resizable.defaults, options);
		if (options.disabled == true) {
			return;
		}
		
		 this.each(function(){
		 	el = new Resizable($(this),options);
		 	$(this).data('resizable',el);
		});
		return options.api ? el: this; 
	};
	
})(jQuery);