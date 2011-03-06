/**
 * portal - tui 基于jQuery1.4.2+
 * 
 * 功能说明： portal 每一列/每个ul jquery 对象上产生一个portal组件
 * 
 * @缺少功能： 1.portal数据以json格式传入
 * 
 */
(function($) {
	// $.woo.addCssStyle('../../themes/default/portal.css');
	var portalEl;// 由于跨两个组件，需要记录原始渲染的元素
	/**
	 * portal组件意义在于动态创建dom节点
	 */
	$.woo.component.subclass('woo.portal', {
		options : {
			portlet : 'widget color-white'
		},
		tabsId : [],
		_create : function() {
			var o = this.options, portalEl = el = this.el;
			el.append(this.createContentByItems(o.items));
			$('.column', el).sortable();
			$("li", el).each(function() {
				var liItem = $(this).data('data');
				$(this).children().tabs({
					title : liItem.title,
					items : liItem.items
				});
			});

		},
		/**
		 * 递归通过items ul li组成的content
		 * 
		 * @param {items}
		 *            菜单数组
		 * @param {j}
		 *            递归层次
		 */
		createContentByItems : function(items) {
			var t = this, o = this.options;
			var j = 0;
			var domTemp = $("<div>");
			for ( var i = 0, l = items.length; i < l; i++) {
				var item = items[i];
				var ul = $("<ul  id='column" + (i + 1) + "' class='column'/>");
				var items_ = item.items;

				// 如果有子项
				if (items_) {
					for ( var k = 0, h = items_.length; k < h; k++) {
						var liItem = items_[k];
						var li = $("<li  class='" + o.portlet
								+ "'><div class='widget-head' id='tab" + (++j)
								+ "'></div></li>");
						li.data('data', liItem);
						t.tabsId.push("tab" + j);
						ul.append(li);

					}
				}
				domTemp.append(ul);
			}
			return domTemp.children();
		}
	});

	$.woo.component
			.subclass(
					'woo.sortable',
					{
						options : {
							// getter : "serialize toArray",//貌似没用到的属性

							helper : "original",
							tolerance : "guess",
							// distance : 1,//貌似没用到的属性
							/**
							 * 移动的portlet透明度
							 */
							opacity : 0.8,
							/**
							 * 点击portlet及移动到可放置位置上的样式
							 */
							placeholder : 'widget-placeholder',
							/**
							 * 是否强制放置的位置大小
							 */
							forcePlaceholderSize : true,
							/**
							 * portlet移动放置后的恢复速度
							 */
							revert : 300,
							/**
							 * 移动portlet的响应速度
							 */
							delay : 100,

							containment : 'document',
							scroll : true,
							scrollSensitivity : 20,
							scrollSpeed : 20,

							// cancel : ":input",貌似没用到的属性
							items : '> *',
							zIndex : 1000,
							/**
							 * 是否允许拖动到空列（当某列portlet被拖完后）
							 */
							dropOnEmpty : true,
							appendTo : "parent",// 在创建helper时有用
							// sortIndicator :
							// $.woo.sortable.prototype._rearrange,modify by
							// chengl
							scope : "default",

							/**
							 * portlet Class
							 */
							portlet : 'widget color-white',

							columns : '.column',
							widgetSelector : '.widget',
							handleSelector : '.widget-head',
							contentSelector : '.widget-content'

						},
						_create : function() {
							var o = this.options;

							o.connectWith = $(o.columns);
							var sortableItems = o.items = $('> li', o.columns);
							sortableItems.find(o.handleSelector).css({
								cursor : 'move'
							}).live('mousedown', function(e) {
								sortableItems.css({
									width : ''
								});
								$(this).parent().css({
									width : $(this).parent().width() + 'px'
								});
							}).live('mouseup', function() {
								if (!$(this).parent().hasClass('dragging')) {
									$(this).parent().css({
										width : $(this).parent().width() + 'px' // 2010/11/20
										// amend
									// by
									// zhaob
									// 里面的div宽度等于外部li的宽度就不会出现宽度变化
									// width : ''
									});
								} else {
									$(o.columns).sortable('disable');
								}
							});
							this._createAllMenu();// 创建菜单工具栏
							// add by chengl
							o.sortIndicator = this._rearrange;
							this.containerCache = {};
							this.element.addClass("ui-sortable");

						},
						_init : function() {
							this.refresh();
							this.floating = this.items.length ? (/left|right/)
									.test(this.items[0].item.css('float'))
									: false;
							this.offset = this.element.offset();
							this._mouseInit();
						},
						/**
						 * 创建所有portlet工具栏菜单
						 */
						_createAllMenu : function() {
							var o = this.options, self = this;
							$(o.handleSelector, this.el).each(function() {
								self._createItemMenu(this);
							});

						},
						/**
						 * 创建单个portlet工具栏菜单
						 */
						_createItemMenu : function(menuItem) {
							var o = this.options, self = this;
							$('<a href="#" class="edit">菜单</a>')
									.mousedown(function(e) {
										e.stopPropagation();
									})
									.menu(
											{
												type : 'click',
												showMenuType : 'down',
												width : 100,
												items : [
														{
															text : "面板名称",
															action : function(
																	item, e, el) {
																var title = $(
																		el)
																		.parent()
																		.find(
																				'.tabs-title');
																var input = $("<input type='text' size='10' value='"
																		+ title
																				.text()
																		+ "'/>");
																title
																		.html(
																				input)
																		.focusout(
																				function() {
																					$(
																							this)
																							.html(
																									$input
																											.val());
																				});
															}
														},
														{
															text : "增加标签",
															action : function(
																	item, e, el) {
																var ul = $(el)
																		.parents(
																				'ul.column');
																ul
																		.sortable('addPortlet');
															}
														},
														{
															text : "最大化",
															action : function(
																	item, e, el) {
																var allUl = $('ul.column');
																allUl.hide();// 隐藏所有portal
																var ul = $(el)
																		.parents(
																				'ul.column');
																var oldUlWidth = ul
																		.width();
																var oldUlHeight = ul
																		.outerHeight();
																// 设置当前选择的列高度宽度并显示
																ul
																		.width('100%');
																ul
																		.height('100%');
																ul
																		.children(
																				'li')
																		.width(
																				'97%');// 2010/11/20
																// add
																// by
																// zhaob
																// 最大化时内部li的宽度一起改变
																ul.show();
																// 隐藏该列的所有portlet
																ul.children(
																		'li')
																		.hide();
																// 点击的portlet显示
																var li = $(el)
																		.parents(
																				'li.widget');
																var oldLiHeight = li
																		.height();
																var tab = li
																		.find('.tabs-panels');

																li
																		.find(
																				'iframe')
																		.height(
																				'95%');// 解决出现两条滚动条bug
																tab
																		.height(oldUlHeight);
																li.show();
																// 点击的菜单文字及事件更改
																var menuText = this
																		.find("[type='text']");
																menuText
																		.text('最小化');
																this
																		.unbind('click');
																this
																		.bind(
																				'click',
																				function() {
																					var self = $(this);
																					tab
																							.height("auto");
																					allUl
																							.show();
																					ul
																							.children(
																									'li')
																							.show();
																					// ul.width(oldWidth);
																					ul
																							.width('33.3%');// 这里用oldWidth在分辨率较低的时候出现换行，暂时设置死宽度百分比
																					ul
																							.children(
																									'li')
																							.width(
																									'');// 2010/11/20
																					// add
																					// by
																					// zhaob
																					// 最小化时内部li的宽度还原
																					ul
																							.height(oldUlHeight);
																					menuText
																							.text('最大化');
																					self
																							.unbind('click');
																					self
																							.bind(
																									'click',
																									function() {
																										item.action
																												.apply(
																														self,
																														[
																																item,
																																e,
																																el ]);
																									});
																				});
															}
														},
														{
															text : "关闭面板",
															action : function(
																	item, e, el) {
																if (confirm('确认关闭窗口？')) {
																	$(el)
																			.parents(
																					o.widgetSelector)
																			.animate(
																					{
																						opacity : 0
																					},
																					function() {
																						$(
																								this)
																								.wrap(
																										'<div/>')
																								.parent()
																								.slideUp(
																										function() {
																											$(
																													this)
																													.remove();
																										});
																					});
																}
															}
														} ]
											}).appendTo(menuItem);
							return menuItem;
						},
						refresh : function() {
							this._refreshItems();
							this.refreshPositions();
						},
						/**
						 * this.options.items 指所有portlet jquery对象集合数组
						 * this.options.connectWith 指能被移动到的位置对象集合数组
						 * 此方法主要把组件通过data存储到每个li jquery对象上
						 */
						_refreshItems : function() {
							var items = this.items = [];
							this.containers = [ this ];
							var self = this;
							// this.options.items也支持传入一个函数?
							// queries为一个多维数组[[$(this.options.items,
							// this.element),
							// this],...]，每一个数组项为一个两个成员的数组，第一个成员queries[i][0]为组件的jquery子元素ul对象集合，第二个成员queries[i][1]为组件本身
							var queries = [ [
									$.isFunction(this.options.items) ? this.options.items
											.call(this.element, null, {
												options : this.options,
												item : this.currentItem
											})
											: $(this.options.items,
													this.element), this ] ];

							if (this.options.connectWith) {
								for ( var i = this.options.connectWith.length - 1; i >= 0; i--) {
									var cur = $(this.options.connectWith[i]);
									// cur为每个能被放置的ul jquery对象 inst为每个portal组件
									var inst = cur.data('sortable');

									if (inst && inst != this
											&& !inst.options.disabled) {
										queries
												.push([
														$
																.isFunction(inst.options.items) ? inst.options.items
																.call(inst.element)
																: $(
																		inst.options.items,
																		inst.element),
														inst ]);
										this.containers.push(inst);
									}
								}
								;
							}
							for ( var i = queries.length - 1; i >= 0; i--) {
								queries[i][0].each(function() {
									// $(this)指每个li jquery对象
									$(this)
											.data('sortable-item',
													queries[i][1]);
									items.push({
										item : $(this),
										instance : queries[i][1],
										width : 0,
										height : 0,
										left : 0,
										top : 0
									});
								});
							}
							;
						},
						/**
						 * 刷新位置
						 */
						refreshPositions : function(fast) {
							// 当移动到可以放置的位置时候
							if (this.offsetParent) {
								var po = this.offsetParent.offset();
								this.offset.parent = {
									top : po.top + this.offsetParentBorders.top,
									left : po.left
											+ this.offsetParentBorders.left
								};
							}

							for ( var i = this.items.length - 1; i >= 0; i--) {
								if (this.items[i].instance != this.currentContainer
										&& this.currentContainer
										&& this.items[i].item[0] != this.currentItem[0])
									continue;
								var t = this.options.toleranceElement ? $(
										this.options.toleranceElement,
										this.items[i].item)
										: this.items[i].item;
								if (!fast) {
									this.items[i].width = t[0].offsetWidth;
									this.items[i].height = t[0].offsetHeight;
								}
								var p = t.offset();
								this.items[i].left = p.left;
								this.items[i].top = p.top;
							}
							;
							if (this.options.custom
									&& this.options.custom.refreshContainers) {
								this.options.custom.refreshContainers
										.call(this);
							} else {
								for ( var i = this.containers.length - 1; i >= 0; i--) {
									var p = this.containers[i].element.offset();
									this.containers[i].containerCache.left = p.left;
									this.containers[i].containerCache.top = p.top;
									this.containers[i].containerCache.width = this.containers[i].element
											.outerWidth();
									this.containers[i].containerCache.height = this.containers[i].element
											.outerHeight();
								}
								;
							}
						},
						/**
						 * 捕获鼠标事件，每次点击鼠标的时候促发，返回true，则鼠标事件往下执行，否则不往下执行
						 */
						_mouseCapture : function(e, overrideHandle) {
							if (this.options.disabled
									|| this.options.type == 'static')
								return false;
							this._refreshItems();

							// e.target一般为一个portlet dom对象
							var currentItem = null, self = this;
							$(e.target).parents().each(function() {
								// $(this)指点击的portal所在列的li
								// jquery对象，因为li上已经绑定了对应的portal组件
								if ($.data(this, 'sortable-item') == self) {
									currentItem = $(this);
									return false;
								}
							});
							// 此种情况好像不会发生
							if ($.data(e.target, 'sortable-item') == self) {
								currentItem = $(e.target);
							}
							// 如果点击的不是portlet则直接返回
							if (!currentItem)
								return false;

							if (this.options.handleSelector && !overrideHandle) {
								var validHandle = false;
								$(this.options.handleSelector, currentItem)
										.find("*").andSelf().each(function() {
											if (this == e.target)
												validHandle = true;
										});
								if (!validHandle)
									return false;
							}
							this.currentItem = currentItem;
							this._removeCurrentsFromItems();
							return true;
						},
						createHelper : function(e) {
							var o = this.options;
							// alert(helper==this.currentItem)==true证明helper为当前选中的portlet
							// jquery对象
							var helper = typeof o.helper == 'function' ? $(o.helper
									.apply(this.element[0], [ e,
											this.currentItem ]))
									: (o.helper == "original" ? this.currentItem
											: this.currentItem.clone());

							// 这里没被调用，不知道有啥用
							if (!helper.parents('body').length) {
								$(o.appendTo != 'parent' ? o.appendTo
										: this.currentItem[0].parentNode)[0]
										.appendChild(helper[0]);
							}
							return helper;
						},
						/**
						 * 核心代码：鼠标开始事件
						 */
						_mouseStart : function(e, overrideHandle, noActivation) {
							var o = this.options;
							this.currentContainer = this;
							this.refreshPositions();
							// this.helper===this.currentItem
							this.helper = this.createHelper(e);
							this.margins = {
								left : (parseInt(this.currentItem
										.css("marginLeft"), 10) || 0),
								top : (parseInt(this.currentItem
										.css("marginTop"), 10) || 0)
							};
							this.offset = this.currentItem.offset();
							this.offset = {
								top : this.offset.top - this.margins.top,
								left : this.offset.left - this.margins.left
							};
							this.offset.click = {
								left : e.pageX - this.offset.left,
								top : e.pageY - this.offset.top
							};

							this.offsetParent = this.helper.offsetParent();// 返回第一个匹配元素用于定位的父节点。即父亲节点有style="position:relative",这里应该是ul

							var po = this.offsetParent.offset();
							this.offsetParentBorders = {
								top : (parseInt(this.offsetParent
										.css("borderTopWidth"), 10) || 0),
								left : (parseInt(this.offsetParent
										.css("borderLeftWidth"), 10) || 0)
							};
							this.offset.parent = {
								top : po.top + this.offsetParentBorders.top,
								left : po.left + this.offsetParentBorders.left
							};
							this.updateOriginalPosition = this.originalPosition = this
									._generatePosition(e);
							this.domPosition = {
								prev : this.currentItem.prev()[0],
								parent : this.currentItem.parent()[0]
							};
							this.helperProportions = {
								width : this.helper.outerWidth(),
								height : this.helper.outerHeight()
							};
							if (o.helper == "original") {
								this._storedCSS = {
									position : this.currentItem.css("position"),
									top : this.currentItem.css("top"),
									left : this.currentItem.css("left"),
									clear : this.currentItem.css("clear")
								};
							} else {
								this.currentItem.hide();
							}
							this.helper.css({
								position : 'absolute',
								clear : 'both'
							}).addClass('ui-sortable-helper');
							this._createPlaceholder();
							this._propagate("start", e);
							if (!this._preserveHelperProportions)
								this.helperProportions = {
									width : this.helper.outerWidth(),
									height : this.helper.outerHeight()
								};
							if (o.cursorAt) {
								if (o.cursorAt.left != undefined)
									this.offset.click.left = o.cursorAt.left;
								if (o.cursorAt.right != undefined)
									this.offset.click.left = this.helperProportions.width
											- o.cursorAt.right;
								if (o.cursorAt.top != undefined)
									this.offset.click.top = o.cursorAt.top;
								if (o.cursorAt.bottom != undefined)
									this.offset.click.top = this.helperProportions.height
											- o.cursorAt.bottom;
							}
							if (o.containment) {
								if (o.containment == 'parent')
									o.containment = this.helper[0].parentNode;
								if (o.containment == 'document'
										|| o.containment == 'window')
									this.containment = [
											0 - this.offset.parent.left,
											0 - this.offset.parent.top,
											$(
													o.containment == 'document' ? document
															: window).width()
													- this.offset.parent.left
													- this.helperProportions.width
													- this.margins.left
													- (parseInt(
															this.element
																	.css("marginRight"),
															10) || 0),
											($(
													o.containment == 'document' ? document
															: window).height() || document.body.parentNode.scrollHeight)
													- this.offset.parent.top
													- this.helperProportions.height
													- this.margins.top
													- (parseInt(
															this.element
																	.css("marginBottom"),
															10) || 0) ];
								if (!(/^(document|window|parent)$/)
										.test(o.containment)) {
									var ce = $(o.containment)[0];
									var co = $(o.containment).offset();
									var over = ($(ce).css("overflow") != 'hidden');
									this.containment = [
											co.left
													+ (parseInt($(ce).css(
															"borderLeftWidth"),
															10) || 0)
													- this.offset.parent.left,
											co.top
													+ (parseInt($(ce).css(
															"borderTopWidth"),
															10) || 0)
													- this.offset.parent.top,
											co.left
													+ (over ? Math.max(
															ce.scrollWidth,
															ce.offsetWidth)
															: ce.offsetWidth)
													- (parseInt($(ce).css(
															"borderLeftWidth"),
															10) || 0)
													- this.offset.parent.left
													- this.helperProportions.width
													- this.margins.left
													- (parseInt(
															this.currentItem
																	.css("marginRight"),
															10) || 0),
											co.top
													+ (over ? Math.max(
															ce.scrollHeight,
															ce.offsetHeight)
															: ce.offsetHeight)
													- (parseInt($(ce).css(
															"borderTopWidth"),
															10) || 0)
													- this.offset.parent.top
													- this.helperProportions.height
													- this.margins.top
													- (parseInt(
															this.currentItem
																	.css("marginBottom"),
															10) || 0) ];
								}
							}
							if (!noActivation) {
								for ( var i = this.containers.length - 1; i >= 0; i--) {
									this.containers[i]._propagate("activate",
											e, this);
								}
							}
							if ($.woo.ddmanager)
								$.woo.ddmanager.current = this;
							if ($.woo.ddmanager && !o.dropBehaviour)
								$.woo.ddmanager.prepareOffsets(this, e);
							this.dragging = true;
							this._mouseDrag(e);
							return true;
						},

						plugins : {},
						ui : function(inst) {
							return {
								helper : (inst || this)["helper"],
								placeholder : (inst || this)["placeholder"]
										|| $([]),
								position : (inst || this)["position"],
								absolutePosition : (inst || this)["positionAbs"],
								options : this.options,
								element : this.element,
								item : (inst || this)["currentItem"],
								sender : inst ? inst.element : null
							};
						},
						_propagate : function(n, e, inst, noPropagation) {
							$.woo.plugin.call(this, n, [ e, this.ui(inst) ]);
							if (!noPropagation)
								this.element.triggerHandler(n == "sort" ? n
										: "sort" + n, [ e, this.ui(inst) ],
										this.options[n]);
						},
						serialize : function(o) {
							var items = this
									._getItemsAsjQuery(o && o.connected);
							var str = [];
							o = o || {};
							$(items)
									.each(
											function() {
												var res = ($(this.item || this)
														.attr(
																o.attribute
																		|| 'id') || '')
														.match(o.expression || (/(.+)[-=_](.+)/));
												if (res)
													str
															.push((o.key || res[1]
																	+ '[]')
																	+ '='
																	+ (o.key
																			&& o.expression ? res[1]
																			: res[2]));
											});
							return str.join('&');
						},
						toArray : function(o) {
							var items = this
									._getItemsAsjQuery(o && o.connected);
							var ret = [];
							items.each(function() {
								ret.push($(this).attr(o.attr || 'id'));
							});
							return ret;
						},
						_intersectsWith : function(item) {
							var x1 = this.positionAbs.left, x2 = x1
									+ this.helperProportions.width, y1 = this.positionAbs.top, y2 = y1
									+ this.helperProportions.height;
							var l = item.left, r = l + item.width, t = item.top, b = t
									+ item.height;
							var dyClick = this.offset.click.top, dxClick = this.offset.click.left;
							var isOverElement = (y1 + dyClick) > t
									&& (y1 + dyClick) < b && (x1 + dxClick) > l
									&& (x1 + dxClick) < r;
							if (this.options.tolerance == "pointer"
									|| this.options.forcePointerForContainers
									|| (this.options.tolerance == "guess" && this.helperProportions[this.floating ? 'width'
											: 'height'] > item[this.floating ? 'width'
											: 'height'])) {
								return isOverElement;
							} else {
								return (l < x1
										+ (this.helperProportions.width / 2)
										&& x2
												- (this.helperProportions.width / 2) < r
										&& t < y1
												+ (this.helperProportions.height / 2) && y2
										- (this.helperProportions.height / 2) < b);
							}
						},
						/**
						 * 边缘交叉
						 */
						_intersectsWithEdge : function(item) {
							var x1 = this.positionAbs.left, x2 = x1
									+ this.helperProportions.width, y1 = this.positionAbs.top, y2 = y1
									+ this.helperProportions.height;
							var l = item.left, r = l + item.width, t = item.top, b = t
									+ item.height;
							var dyClick = this.offset.click.top, dxClick = this.offset.click.left;
							var isOverElement = (y1 + dyClick) > t
									&& (y1 + dyClick) < b && (x1 + dxClick) > l
									&& (x1 + dxClick) < r;
							if (this.options.tolerance == "pointer"
									|| (this.options.tolerance == "guess" && this.helperProportions[this.floating ? 'width'
											: 'height'] > item[this.floating ? 'width'
											: 'height'])) {
								if (!isOverElement)
									return false;
								if (this.floating) {
									if ((x1 + dxClick) > l
											&& (x1 + dxClick) < l + item.width
													/ 2)
										return 2;
									if ((x1 + dxClick) > l + item.width / 2
											&& (x1 + dxClick) < r)
										return 1;
								} else {
									var height = item.height;
									var direction = y1
											- this.updateOriginalPosition.top < 0 ? 2
											: 1;
									if (direction == 1
											&& (y1 + dyClick) < t + height / 2) {
										return 2;
									} else if (direction == 2
											&& (y1 + dyClick) > t + height / 2) {
										return 1;
									}
								}
							} else {
								if (!(l < x1
										+ (this.helperProportions.width / 2)
										&& x2
												- (this.helperProportions.width / 2) < r
										&& t < y1
												+ (this.helperProportions.height / 2) && y2
										- (this.helperProportions.height / 2) < b))
									return false;
								if (this.floating) {
									if (x2 > l && x1 < l)
										return 2;
									if (x1 < r && x2 > r)
										return 1;
								} else {
									if (y2 > t && y1 < t)
										return 1;
									if (y1 < b && y2 > b)
										return 2;
								}
							}
							return false;
						},
						_getItemsAsjQuery : function(connected) {
							var self = this;
							var items = [];
							var queries = [];
							if (this.options.connectWith && connected) {
								for ( var i = this.options.connectWith.length - 1; i >= 0; i--) {
									var cur = $(this.options.connectWith[i]);
									for ( var j = cur.length - 1; j >= 0; j--) {
										var inst = $.data(cur[j], 'sortable');
										if (inst && inst != this
												&& !inst.options.disabled) {
											queries
													.push([
															$
																	.isFunction(inst.options.items) ? inst.options.items
																	.call(inst.element)
																	: $(
																			inst.options.items,
																			inst.element)
																			.not(
																					".ui-sortable-helper"),
															inst ]);
										}
									}
									;
								}
								;
							}
							queries
									.push([
											$.isFunction(this.options.items) ? this.options.items
													.call(this.element, null, {
														options : this.options,
														item : this.currentItem
													})
													: $(this.options.items,
															this.element)
															.not(
																	".ui-sortable-helper"),
											this ]);
							for ( var i = queries.length - 1; i >= 0; i--) {
								queries[i][0].each(function() {
									items.push(this);
								});
							}
							;
							return $(items);
						},
						/**
						 * 点击的时候把当前portlet从items中去除？
						 */
						_removeCurrentsFromItems : function() {
							var list = this.currentItem
									.find(":data(sortable-item)");
							for ( var i = 0; i < this.items.length; i++) {
								for ( var j = 0; j < list.length; j++) {
									if (list[j] == this.items[i].item[0])
										this.items.splice(i, 1);// 删除从 i
									// 处开始的1个元素
								}
								;
							}
							;
						},

						destroy : function() {
							this.element.removeClass(
									"ui-sortable ui-sortable-disabled")
									.removeData("sortable").unbind(".sortable");
							this._mouseDestroy();
							for ( var i = this.items.length - 1; i >= 0; i--)
								this.items[i].item.removeData("sortable-item");
						},
						/**
						 * 创建一个可放置位置
						 */
						_createPlaceholder : function(that) {
							var self = that || this, o = self.options;
							if (!o.placeholder
									|| o.placeholder.constructor == String) {
								var className = o.placeholder;
								o.placeholder = {
									element : function() {
										var el = $(
												document
														.createElement(self.currentItem[0].nodeName))
												.addClass(
														className
																|| "ui-sortable-placeholder")[0];
										if (!className) {
											el.style.visibility = "hidden";
											document.body.appendChild(el);
											el.innerHTML = self.currentItem[0].innerHTML;
											document.body.removeChild(el);
										}
										;
										return el;
									},
									update : function(container, p) {
										if (className
												&& !o.forcePlaceholderSize)
											return;
										if (!p.height()) {
											p
													.height(self.currentItem
															.innerHeight()
															- parseInt(
																	self.currentItem
																			.css('paddingTop') || 0,
																	10)
															- parseInt(
																	self.currentItem
																			.css('paddingBottom') || 0,
																	10));
										}
										;
										if (!p.width()) {
											p
													.width(self.currentItem
															.innerWidth()
															- parseInt(
																	self.currentItem
																			.css('paddingLeft') || 0,
																	10)
															- parseInt(
																	self.currentItem
																			.css('paddingRight') || 0,
																	10));
										}
										;
									}
								};
							}
							self.placeholder = $(o.placeholder.element.call(
									self.element, self.currentItem))
							self.currentItem.parent()[0]
									.appendChild(self.placeholder[0]);
							self.placeholder[0].parentNode.insertBefore(
									self.placeholder[0], self.currentItem[0]);
							o.placeholder.update(self, self.placeholder);
						},
						_contactContainers : function(e) {
							for ( var i = this.containers.length - 1; i >= 0; i--) {
								if (this
										._intersectsWith(this.containers[i].containerCache)) {
									if (!this.containers[i].containerCache.over) {
										if (this.currentContainer != this.containers[i]) {
											var dist = 10000;
											var itemWithLeastDistance = null;
											var base = this.positionAbs[this.containers[i].floating ? 'left'
													: 'top'];
											for ( var j = this.items.length - 1; j >= 0; j--) {
												if (!$.woo
														.contains(
																this.containers[i].element[0],
																this.items[j].item[0]))
													continue;
												var cur = this.items[j][this.containers[i].floating ? 'left'
														: 'top'];
												if (Math.abs(cur - base) < dist) {
													dist = Math.abs(cur - base);
													itemWithLeastDistance = this.items[j];
												}
											}
											if (!itemWithLeastDistance
													&& !this.options.dropOnEmpty)
												continue;
											this.currentContainer = this.containers[i];
											itemWithLeastDistance ? this.options.sortIndicator
													.call(
															this,
															e,
															itemWithLeastDistance,
															null, true)
													: this.options.sortIndicator
															.call(
																	this,
																	e,
																	null,
																	this.containers[i].element,
																	true);
											this._propagate("change", e);
											this.containers[i]._propagate(
													"change", e, this);
											this.options.placeholder.update(
													this.currentContainer,
													this.placeholder);
										}
										this.containers[i]._propagate("over",
												e, this);
										this.containers[i].containerCache.over = 1;
									}
								} else {
									if (this.containers[i].containerCache.over) {
										this.containers[i]._propagate("out", e,
												this);
										this.containers[i].containerCache.over = 0;
									}
								}
							}
							;
						},
						_convertPositionTo : function(d, pos) {
							if (!pos)
								pos = this.position;
							var mod = d == "absolute" ? 1 : -1;
							return {
								top : (pos.top
										+ this.offset.parent.top
										* mod
										- (this.offsetParent[0] == document.body ? 0
												: this.offsetParent[0].scrollTop)
										* mod + this.margins.top * mod),
								left : (pos.left
										+ this.offset.parent.left
										* mod
										- (this.offsetParent[0] == document.body ? 0
												: this.offsetParent[0].scrollLeft)
										* mod + this.margins.left * mod)
							};
						},
						_generatePosition : function(e) {
							var o = this.options;
							var position = {
								top : (e.pageY - this.offset.click.top
										- this.offset.parent.top + (this.offsetParent[0] == document.body ? 0
										: this.offsetParent[0].scrollTop)),
								left : (e.pageX - this.offset.click.left
										- this.offset.parent.left + (this.offsetParent[0] == document.body ? 0
										: this.offsetParent[0].scrollLeft))
							};
							if (!this.originalPosition)
								return position;
							if (this.containment) {
								if (position.left < this.containment[0])
									position.left = this.containment[0];
								if (position.top < this.containment[1])
									position.top = this.containment[1];
								if (position.left > this.containment[2])
									position.left = this.containment[2];
								if (position.top > this.containment[3])
									position.top = this.containment[3];
							}
							if (o.grid) {
								var top = this.originalPosition.top
										+ Math
												.round((position.top - this.originalPosition.top)
														/ o.grid[1])
										* o.grid[1];
								position.top = this.containment ? (!(top < this.containment[1] || top > this.containment[3]) ? top
										: (!(top < this.containment[1]) ? top
												- o.grid[1] : top + o.grid[1]))
										: top;
								var left = this.originalPosition.left
										+ Math
												.round((position.left - this.originalPosition.left)
														/ o.grid[0])
										* o.grid[0];
								position.left = this.containment ? (!(left < this.containment[0] || left > this.containment[2]) ? left
										: (!(left < this.containment[0]) ? left
												- o.grid[0] : left + o.grid[0]))
										: left;
							}
							return position;
						},
						/**
						 * 鼠标移动事件
						 */
						_mouseDrag : function(e) {
							this.position = this._generatePosition(e);
							this.positionAbs = this
									._convertPositionTo("absolute");
							// $.woo.plugin.call(this, "sort", [e, this.ui()]);
							// this.positionAbs =
							// this._convertPositionTo("absolute");
							this.helper[0].style.left = this.position.left
									+ 'px';
							this.helper[0].style.top = this.position.top + 'px';
							for ( var i = this.items.length - 1; i >= 0; i--) {
								var intersection = this
										._intersectsWithEdge(this.items[i]);
								if (!intersection)
									continue;
								if (this.items[i].item[0] != this.currentItem[0]
										&& this.placeholder[intersection == 1 ? "next"
												: "prev"]()[0] != this.items[i].item[0]
										&& !$.woo.contains(this.placeholder[0],
												this.items[i].item[0])
										&& (this.options.type == 'semi-dynamic' ? !$.woo
												.contains(this.element[0],
														this.items[i].item[0])
												: true)) {
									this.updateOriginalPosition = this
											._generatePosition(e);
									this.direction = intersection == 1 ? "down"
											: "up";
									this.options.sortIndicator.call(this, e,
											this.items[i]);
									this._propagate("change", e);
									break;
								}
							}
							this._contactContainers(e);
							// if ($.woo.ddmanager)
							// $.woo.ddmanager.drag(this, e);
							// this.element.triggerHandler("sort", [e,
							// this.ui()],
							// this.options["sort"]);
							return false;
						},
						_rearrange : function(e, i, a, hardRefresh) {
							a ? a[0].appendChild(this.placeholder[0])
									: i.item[0].parentNode
											.insertBefore(
													this.placeholder[0],
													(this.direction == 'down' ? i.item[0]
															: i.item[0].nextSibling));
							this.counter = this.counter ? ++this.counter : 1;
							var self = this, counter = this.counter;
							window.setTimeout(function() {
								if (counter == self.counter)
									self.refreshPositions(!hardRefresh);
							}, 0);
						},
						/**
						 * 鼠标停止事件
						 */
						_mouseStop : function(e, noPropagation) {
							if ($.woo.ddmanager && !this.options.dropBehaviour)
								$.woo.ddmanager.drop(this, e);
							if (this.options.revert) {
								var self = this;
								var cur = self.placeholder.offset();
								$(this.helper)
										.animate(
												{
													left : cur.left
															- this.offset.parent.left
															- self.margins.left
															+ (this.offsetParent[0] == document.body ? 0
																	: this.offsetParent[0].scrollLeft),
													top : cur.top
															- this.offset.parent.top
															- self.margins.top
															+ (this.offsetParent[0] == document.body ? 0
																	: this.offsetParent[0].scrollTop)
												},
												parseInt(this.options.revert,
														10) || 500, function() {
													self._clear(e);
												});
							} else {
								this._clear(e, noPropagation);
							}
							return false;
						},
						_clear : function(e, noPropagation) {
							if (!this._noFinalSort)
								this.placeholder.before(this.currentItem);
							this._noFinalSort = null;
							if (this.options.helper == "original")
								this.currentItem.css(this._storedCSS)
										.removeClass("ui-sortable-helper");
							else
								this.currentItem.show();
							if (this.domPosition.prev != this.currentItem
									.prev().not(".ui-sortable-helper")[0]
									|| this.domPosition.parent != this.currentItem
											.parent()[0])
								this._propagate("update", e, null,
										noPropagation);
							if (!$.woo.contains(this.element[0],
									this.currentItem[0])) {
								this._propagate("remove", e, null,
										noPropagation);
								for ( var i = this.containers.length - 1; i >= 0; i--) {
									if ($.woo.contains(
											this.containers[i].element[0],
											this.currentItem[0])) {
										this.containers[i]._propagate("update",
												e, this, noPropagation);
										this.containers[i]._propagate(
												"receive", e, this,
												noPropagation);
									}
								}
								;
							}
							;
							for ( var i = this.containers.length - 1; i >= 0; i--) {
								this.containers[i]._propagate("deactivate", e,
										this, noPropagation);
								if (this.containers[i].containerCache.over) {
									this.containers[i]._propagate("out", e,
											this);
									this.containers[i].containerCache.over = 0;
								}
							}
							this.dragging = false;
							if (this.cancelHelperRemoval) {
								this._propagate("beforeStop", e, null,
										noPropagation);
								this._propagate("stop", e, null, noPropagation);
								return false;
							}
							this._propagate("beforeStop", e, null,
									noPropagation);
							this.placeholder.remove();
							if (this.options.helper != "original")
								this.helper.remove();
							this.helper = null;
							this._propagate("stop", e, null, noPropagation);
							return true;
						},
						addItem : function(item) {
							this.options.items.push(item);
							this._init();
						},
						/**
						 * 动态添加一个protlet tabsData为传入的标签数据
						 */
						addPortlet : function(tabsData) {
							var o = this.options;
							var li = $("<li class='" + o.portlet + "'>");
							var tab = $('<div  class="widget-head  tabs-container" style="cursor: move; width: auto; height: auto;">');
							this._createItemMenu(tab);
							li.append(tab);
							$(this.el).append(li);
							if (tabsData) {
								tab.tabs(tabsData);
							} else {
								tab.tabs({
									title : 'portlet',
									items : [ {
										title : '模拟数据',
										selected : true,
										content : '物质管理'
									}, {
										title : '模拟数据',
										url : 'http://www.toone.com.cn'
									}, {
										title : '模拟数据',
										url : 'http://www.google.cn'
									} ]
								});
							}
							$('.column', portalEl).sortable('addItem', li);
						},
						/**
						 * 请求加载json数据，生成portal
						 */
						request : function(target, ul, param) {
							var opts = $.data(target, 'portal').options;
							if (!opts.url)
								return;
							param = param || {};
							$.ajax({
								type : 'post',
								url : opts.url,
								data : param,
								dataType : 'json',
								success : function(data) {

								}
							});
						}
					});
})(jQuery);