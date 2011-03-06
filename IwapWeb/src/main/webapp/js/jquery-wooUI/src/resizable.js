/**
 * resizable - wooUI
 * 基于jQuery1.4.2+
 */
(function($){
	//$.woo = $.woo || {version:2.0};
	$.woo.component.subclass('woo.resizable',{
		options : {
			handles:'n, e, s, w, ne, se, sw, nw, all',
			minWidth: 10,
			minHeight: 10,
			maxWidth: 10000,//$(document).width(),
			maxHeight: 10000,//$(document).height(),
			edge:5,
			/**
			 * 开始resize的回调函数
			 */
			onStartResize: $.noop,
			
			/**
			 * rezise过程中的回调函数
			 * @param {Event} e
			 */
			onResize: $.noop,
			
			/**
			 * 停止rezise的回调函数
			 * @param {Event} e
			 */
			onStopResize: $.noop
		
		},
		_create : function(){
			var self = this, o = this.options;
			this.el.mouseover(function(e){
				var dir = self.getDirection(e);
				//alert(dir)
				if(dir == ''){
					self.el.css('cursor','default');
				}else{
					self.el.css('cursor',dir+'-resize');
				}
			});
			
			
			this._mouseInit();
		},
		
		
		resize : function(e){
			if (this.resizeData.dir.indexOf('e') != -1) {
				var width = this.resizeData.startWidth + e.pageX - this.resizeData.startX;
				width = Math.min(
							Math.max(width, this.options.minWidth),
							this.options.maxWidth
						);
				this.resizeData.width = width;
			}
			if (this.resizeData.dir.indexOf('s') != -1) {
				var height = this.resizeData.startHeight + e.pageY - this.resizeData.startY;
				height = Math.min(
						Math.max(height, this.options.minHeight),
						this.options.maxHeight
				);
				this.resizeData.height = height;
			}
			if (this.resizeData.dir.indexOf('w') != -1) {
				this.resizeData.width = this.resizeData.startWidth - e.pageX + this.resizeData.startX;
				if (this.resizeData.width >= this.options.minWidth && this.resizeData.width <= this.options.maxWidth) {
					this.resizeData.left = this.resizeData.startLeft + e.pageX - this.resizeData.startX;
				}
			}
			if (this.resizeData.dir.indexOf('n') != -1) {
				this.resizeData.height = this.resizeData.startHeight - e.pageY + this.resizeData.startY;
				if (this.resizeData.height >= this.options.minHeight && this.resizeData.height <= this.options.maxHeight) {
					this.resizeData.top = this.resizeData.startTop + e.pageY - this.resizeData.startY;
				}
			}
		},
		
		applySize : function(e){
			if ($.boxModel == true){
				this.el.css({
					width: this.resizeData.width - this.resizeData.deltaWidth,
					height: this.resizeData.height - this.resizeData.deltaHeight,
					left: this.resizeData.left,
					top: this.resizeData.top
				});
			} else {
				this.el.css({
					width: this.resizeData.width,
					height: this.resizeData.height,
					left: this.resizeData.left,
					top: this.resizeData.top
				});
			}
		},
		
		// 获取resize方向
		getDirection : function(e) {
			var dir = '';
			var offset = this.el.offset();
			var width = this.el.outerWidth();
			var height = this.el.outerHeight();
			var edge = this.options.edge;
			if (e.pageY > offset.top && e.pageY < offset.top + edge) {
				dir += 'n';
			} else if (e.pageY < offset.top + height && e.pageY > offset.top + height - edge) {
				dir += 's';
			}
			if (e.pageX > offset.left && e.pageX < offset.left + edge) {
				dir += 'w';
			} else if (e.pageX < offset.left + width && e.pageX > offset.left + width - edge) {
				dir += 'e';
			}
			
			var handles = this.options.handles.split(',');
			for(var i=0; i<handles.length; i++) {
				var handle = handles[i].replace(/(^\s*)|(\s*$)/g, '');
				if (handle == 'all' || handle == dir) {
					return dir;
				}
			}
			return '';
		},
		_mouseStart : function(e){
			var dir = this.getDirection(e);
			if (dir == '') return;
			
			this.resizeData = {
				target: this,
				dir: dir,
				startLeft: $.woo.getStyleValue(this.el,'left'),
				startTop: $.woo.getStyleValue(this.el,'top'),
				left: $.woo.getStyleValue(this.el,'left'),
				top: $.woo.getStyleValue(this.el,'top'),
				startX: e.pageX,
				startY: e.pageY,
				startWidth: this.el.outerWidth(),
				startHeight: this.el.outerHeight(),
				width: this.el.outerWidth(),
				height: this.el.outerHeight(),
				deltaWidth: this.el.outerWidth() - this.el.width(),
				deltaHeight: this.el.outerHeight() - this.el.height()
			};
		},
		_mouseDrag : function(e){
			this.resize(e);
			if (this.options.onResize.call(this.el, e) != false){
				this.applySize(e);
			}
			return false;
		}
	});	
})(jQuery);