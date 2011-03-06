/**
 * tabs - tui 基于jQuery1.4.2+
 * 
 * 功能说明： tab头和tab页内容可以分开定义。 tabs的头部导航方便，右键功能包括：刷新、关闭其他、关闭所有、锁定/解除锁定。
 * tab内容要可以加载各种资源，包括dom片段、iframe等。 tab要点击激活才加载内容。 双击关闭tab
 * 
 * 待完善：
 * 
 * 关闭时判断是否有active的tab，如果没有则active前一个 tabs太多时css控制不换行 tabs导航 拖动改变位置、可拖放
 */
(function($) {
	var idSeed = 0;// 组件id种子初始值 add by chengl
	$.woo.component
			.subclass(
					'woo.tabs',
					{
						options : {
							width : 'auto',
							height : 'auto',
							// idSeed: 0,
							idPrefix : 'woo-tabs-',
							plain : false,
							fit : false,
							closable : true,

							border : true,
							scrollIncrement : 100,
							scrollDuration : 400,

							cache : false,// 是否缓存已加载数据

							onLoad : $.noop,

							// tabs头部的容器
							hc : null,
							// tabs内容区的容器
							bc : null,

							/**
							 * tabs组件的tab [{ id:id, title:title, closable:true,
							 * selected:true, content:'' //tab下的内容 },{
							 *  }]
							 */
							items : null,
							/**
							 * 选中tab时触发
							 * 
							 * @param {String}
							 *            title
							 */
							onSelect : $.noop,

							/**
							 * 关闭tab时触发
							 * 
							 * @param {String}
							 *            title
							 */
							onClose : $.noop
						},

						_create : function() {
							this.hc = $(this.options.hc || this.el);
							this.bc = $(this.options.bc || this.el);
							this._inithtml();
						},
						// 激活默认选定的tab
						_init : function() {
							this.setSize();
							this.active();
						},

						id : function() {
							return this.options.idPrefix + (idSeed++);
						},

						// 创建tabs的头部，但不创建每一个tab的内容主体，只有在一个tab被激活的时候，才去创建tab的内容
						_inithtml : function() {
							var self = this;

							var hcChildren = this.hc.children();// 拿到头部的孩子，在头部后面进行追加
																// add by chengl

							this.hc.addClass('tabs-container');
							this.bc
									.append('<div class="tabs-panels'
											+ (!this.options.border ? ' tabs-panels-noborder'
													: '') + '"></div>');
							var buf = [
									'<div class="tabs-header',
									!this.options.border ? ' tabs-header-noborder'
											: '',
									'">',
									'<div class="tabs-scroller-left"></div>',
									'<div class="tabs-scroller-right"></div>',
									'<div class="tabs-wrap">',
									this.options.title ? '<div class="tabs-title">'
											+ this.options.title + '</div>'
											: '', '<ul class="tabs">',

									'</ul>', '</div>', '</div>' ].join('');
							buf = $(buf);

							if (hcChildren.size() > 0) {
								buf.find(".tabs-wrap").append(hcChildren);
							}
							buf.prependTo(this.hc);
							for ( var i = 0, l = this.options.items.length; i < l; i++) {
								this.add(this.options.items[i]);
							}
							this._initEvent();
						},

						/**
						 * 初始化tabs组件的事件
						 */
						_initEvent : function() {
							var self = this, tabs = $('ul.tabs', this.hc);

							// 初始化tab的点击事件
							$('li', this.hc)
									.unbind('.tabs')
									.live(
											'click.tabs',
											function() {
												$('.tabs-selected', self.hc)
														.removeClass(
																'tabs-selected');
												$(this).addClass(
														'tabs-selected');
												$(this).blur();
												$('>div.tabs-panels>div',
														self.bc).css('display',
														'none');

												var wrap = $('.tabs-wrap',
														this.hc);
												var leftPos = self
														._getTabLeftPosition(this);
												var left = leftPos
														- wrap.scrollLeft();
												var right = left
														+ $(this).outerWidth();
												if (left < 0
														|| right > wrap
																.innerWidth()) {
													var pos = Math
															.min(
																	leftPos
																			- (wrap
																					.width() - $(
																					this)
																					.width())
																			/ 2,
																	self
																			._getMaxScrollWidth());
													wrap
															.animate(
																	{
																		scrollLeft : pos
																	},
																	self.options.scrollDuration);
												}

												var tabAttr = $.data(this,
														'tabs.tab');
												var panel = $('#' + tabAttr.id);
												panel.css('display', 'block');

												self.loadContent(this);
												self.fitContent();

												self.options.onSelect.call(
														panel, tabAttr.title);

											}).live('dblclick.tabs',
											function(e) {
												e.stopPropagation();
												self.closeTab(this);
											});
							// 初始化tab的关闭事件
							$('a.tabs-close', tabs).unbind('.tabs').live(
									'click.tabs', function(e) {
										var elem = $(this).parent()[0];
										// var tabAttr = $.data(elem,
										// 'tabs.tab');
										e.stopPropagation();
										self.closeTab(elem);
									});

							var header = $('>div.tabs-header', this.hc);
							$('.tabs-scroller-left, .tabs-scroller-right',
									header).hover(function() {
								$(this).addClass('tabs-scroller-over');
							}, function() {
								$(this).removeClass('tabs-scroller-over');
							});
							// 设置容器的resize事件
							// TODO
							$(this.hc).bind('_resize', function() {
								if (this.options.fit == true) {
									this.setSize();
									this.fitContent();
								}
								return false;
							});
						},

						fitContent : function() {
							// debugger;
							var tab = $(
									'>div.tabs-header ul.tabs li.tabs-selected',
									this.hc);
							if (tab.length) {
								var panelId = $.data(tab[0], 'tabs.tab').id;
								var panel = $('#' + panelId);
								var panels = $('>div.tabs-panels', this.bc);
								if (panels.css('height').toLowerCase() != 'auto') {
									// TODO $.boxModel 已不建议使用
									if ($.boxModel == true) {
										panel.height(panels.height()
												- (panel.outerHeight() - panel
														.height()));
										panel.width(panels.width()
												- (panel.outerWidth() - panel
														.width()));
									} else {
										panel.height(panels.height());
										panel.width(panels.width());
									}
								}
								$('>div', panel).triggerHandler('_resize');
							}
						},

						/**
						 * 初始化交互操作
						 */
						_initAction : function() {

						},

						// 设置tabs滚动按钮是否显示,依赖于tabs的数量和宽度
						_setScrollers : function() {
							var tabsWidth = 0;
							$('ul.tabs li', this.hc).each(function() {
								tabsWidth += $(this).outerWidth(true);
							});

							if (tabsWidth > this.hc.width()) {
								$('.tabs-scroller-left', this.hc).css(
										'display', 'block');
								$('.tabs-scroller-right', this.hc).css(
										'display', 'block');
								$('.tabs-wrap', this.hc).addClass(
										'tabs-scrolling');

								$('.tabs-wrap', this.hc).css('left',
										$.support.boxModel ? 2 : 0);

								var width = this.hc.width()
										- $('.tabs-scroller-left', this.hc)
												.outerWidth()
										- $('.tabs-scroller-right', this.hc)
												.outerWidth();
								$('.tabs-wrap', this.hc).width(width);

							} else {
								$('.tabs-scroller-left', this.hc).css(
										'display', 'none');
								$('.tabs-scroller-right', this.hc).css(
										'display', 'none');
								$('.tabs-wrap', this.hc).removeClass(
										'tabs-scrolling').scrollLeft(0);
								$('.tabs-wrap', this.hc).width(this.hc.width());
								$('.tabs-wrap', this.hc).css('left', 0);

							}
						},

						_getTabLeftPosition : function(tab) {
							var w = 0;
							var b = true;
							$('>div.tabs-header ul.tabs li', this.hc).each(
									function() {
										if (this == tab) {
											b = false;
										}
										if (b == true) {
											w += $(this).outerWidth(true);
										}
									});
							return w;
						},

						// 获取tabs最大的滚动宽度
						_getMaxScrollWidth : function() {
							var header = $('>div.tabs-header', this.hc);
							var tabsWidth = 0; // all tabs width
							$('ul.tabs li', header).each(function() {
								tabsWidth += $(this).outerWidth(true);
							});
							var wrapWidth = $('.tabs-wrap', header).width();
							var padding = parseInt($('.tabs', header).css(
									'padding-left'));

							return tabsWidth - wrapWidth + padding;
						},

						setSize : function() {
							var opts = this.options;
							var cc = $(this.el);
							if (opts.fit == true) {
								var p = cc.parent();
								opts.width = p.width();
								opts.height = p.height();
							}
							cc.width(opts.width);// .height(opts.height);
							/*
							 * cc.width(isNaN(opts.width)?this.hc.width():opts.width)
							 * .height(isNaN(opts.height)?this.hc.height():opts.height);
							 */
							var header = $('>div.tabs-header', this.hc);
							// TODO $.boxModel 已不建议使用
							if ($.boxModel == true) {
								var delta = header.outerWidth()
										- header.width();
								// var delta = header.outerWidth(true) -
								// header.width();
								header.width(cc.width() - delta);
							} else {
								header.width(cc.width());
							}
							// 设置宽度自适应，则与this.el宽度一致
							// 这样在动态更改this.el宽度的时候header宽度也会更改 add by chengl
							header.width("auto");
							if (opts.bc) {
								cc.height(header.height());
							} else {
								cc.height(opts.height);
							}
							// this._setScrollers();

							var panels = $('>div.tabs-panels', this.bc);
							var height = opts.height;
							if (!isNaN(height)) {
								// TODO $.boxModel 已不建议使用
								if ($.boxModel == true) {
									var delta = panels.outerHeight()
											- panels.height();
									panels.css('height', (height
											- header.outerHeight() - delta)
											|| 'auto');
								} else {
									panels.css('height', height
											- header.outerHeight());
								}
							} else {
								panels.height('auto');
							}
							var width = opts.width;
							if (!isNaN(width)) {
								// TODO $.boxModel 已不建议使用
								if ($.boxModel == true) {
									var delta = panels.outerWidth()
											- panels.width();
									// var delta = panels.outerWidth(true) -
									// panels.width();
									panels.width(width - delta);
								} else {
									panels.width(width);
								}
							} else {
								panels.width('auto');
							}

							/*
							 * if ($.parser){ $.parser.parse(container); }
							 */},

						/**
						 * @param {Object}
						 *            option 格式为： { id:id, title:title,
						 *            closable:true, disable:false,
						 *            iconCls:'icon-cls', tabCls:'tab-cls',
						 *            content:'' //tab下的内容 }
						 * @param {Number}
						 *            pos 插入的位置，计数从0开始，默认为最后一个位置
						 */
						add : function(option, pos) {
							option = $.extend({
								id : this.id(),
								title : '',
								href : null,
								tabCls : null,
								iconCls : null,
								closable : this.options.closable,
								selected : false,
								height : this.options.height,
								width : this.options.width

							}, option || {});
							pos = pos || 0;
							// /<li class=""><a href="javascript:void(0)"
							// class="tabs-inner">
							// <span class="tabs-title
							// tabs-closable">Tab2</span>
							// <span class="tabs-icon"></span>
							// </a><a href="javascript:void(0)"
							// class="tabs-close"></a>
							// </li>
							var tabBuf = [
									'<li class="',
									option.selected ? 'tabs-selected' : '',
									option.tabCls ? ' ' + option.tabCls : '',
									'">',
									'<a class="tabs-inner" ',
									'href="javascript:void(0)">',
									'<span class="',
									option.closable ? 'tabs-closable' : '',
									option.iconCls ? ' tabs-with-icon' : '',
									'">',
									option.title,
									'</span>',
									option.iconCls ? '<span class="tabs-icon '
											+ option.iconCls + '"></span>' : '',
									'</a>',
									option.closable ? '<a href="javascript:void(0)" class="tabs-close"></a>'
											: '', '</li>' ];
							var panelBuf = [ '<div id="', option.id,
									'" style="display:none;"></div>' ];

							var hc = $('ul.tabs', this.hc);
							var bc = $('>div.tabs-panels', this.bc);
							var lis = $('>li', hc);
							var panels = $('>div', bc);

							var tab;
							if (pos > 0 && lis.length > 0) {
								tab = $(tabBuf.join('')).insertAfter(
										lis.get(pos - 1));
							} else {
								tab = $(tabBuf.join('')).appendTo(hc);

							}
							if (pos > 0 && panels.length > 0) {
								$(panelBuf.join('')).insertAfter(
										panels.get(pos - 1));
							} else {
								// 设置panel的高宽
								$(panelBuf.join('')).height(
										isNaN(this.options.height) ? '100%'
												: this.options.height).width(
										isNaN(this.options.width) ? '100%'
												: this.options.width).appendTo(
										bc);
							}
							tab.data('tabs.tab', {
								id : option.id,
								title : option.title,
								closable : option.closable,
								url : option.url,// 如果有url，则不加载content
								content : option.content,
								loaded : false,
								cache : option.cache
							});
						},

						active : function(title) {
							if (title) {
								var elem = $(
										'>div.tabs-header li:has(a span:contains("'
												+ title + '"))', this.el)[0];
								if (elem) {
									$(elem).trigger('click');
								}
							} else {
								var tabs = $('>div.tabs-header ul.tabs',
										this.hc);
								if ($('.tabs-selected', tabs).length == 0) {
									$('li:first', tabs).trigger('click');
								} else {
									$('.tabs-selected', tabs).trigger('click');
								}
							}
						},

						loadContent : function(elem) {
							// debugger;
							var data = $.data(elem, 'tabs.tab');
							if (data && data.loaded) {
								return;
							}
							var panel = $('#' + data.id);
							panel.css('display', 'block');
							if (data.url && !data.cache) {
								/*
								 * panel.html(data.url, null, function(){ if
								 * ($.parser){ $.parser.parse(panel); }
								 * this.options.onLoad.apply(this, arguments);
								 * data.loaded = true; });
								 */
								panel
										.html('<iframe style="width:100%;height:100%" frameborder="0" src="'
												+ data.url + '"></iframe>');
							}
							if (!data.url && $.woo.isString(data.content)) {
								panel.html(data.content);
							}
							// 设置为已加载
							data.loaded = true;
						},

						/**
						 * 设置title
						 */
						setTitle : function(title) {
							$('li.tabs-title', this.hc).text(title);
						},

						/**
						 * 获取激活的tab分页
						 */
						getActiveTab : function() {

						},

						/**
						 * 获取指定的分页
						 * 
						 * @param {String}
						 *            id
						 */
						getTab : function(id) {

						},

						/**
						 * 关闭一个分页
						 */
						closeTab : function(elem) {
							// var elem = $('>div.tabs-header li:has(a
							// span:contains("' + title + '"))', container)[0];
							if (!elem)
								return;

							var tabAttr = $.data(elem, 'tabs.tab');
							if (!tabAttr.closable)
								return;
							var panel = $('#' + tabAttr.id);

							if (this.options.onClose.call(panel, tabAttr) == false)
								return;

							var selected = $(elem).hasClass('tabs-selected');
							$.removeData(elem, 'tabs.tab');
							$(elem).remove();
							panel.remove();

							this.setSize();
							if (selected) {
								this.active();
							} else {
								var wrap = $('>div.tabs-header .tabs-wrap',
										this.hc);
								var pos = Math.min(wrap.scrollLeft(), this
										._getMaxScrollWidth());
								wrap.animate({
									scrollLeft : pos
								}, this.options.scrollDuration);
							}
						},

						/**
						 * 判断是否已经存在
						 */
						exists : function(title) {
							// TODO 不能通过title来判断
							return $(
									'>div.tabs-header li:has(a span:contains("'
											+ title + '"))', this.el).length > 0;
						}
					});
})(jQuery);