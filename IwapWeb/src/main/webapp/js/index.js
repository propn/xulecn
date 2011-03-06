/**
 * 此文件必须在jquery、sys.js、startMenu.js 加载之后加载 此文件中脚本说明：
 * 
 * 
 * 函数接口说明： addTab() //在任务栏添加一个 closeTab() //在任务栏添加一个 hideStartMenu() //隐藏开始菜单
 */

/**
 * @desc 控制动画
 * @return
 */
$.fx.off = !sys_status['animate'];
var MENU_ITEM_HEIGHT = 30, // 菜单项高度
MIN_PNAEL_HEIGHT = 8 * MENU_ITEM_HEIGHT, MAX_PNAEL_HEIGHT = 20 * MENU_ITEM_HEIGHT, SCROLL_HEIGHT = 4 * MENU_ITEM_HEIGHT;

/**
 * @desc 获取中间内容区域的高度
 * @return
 */
function getCenterHeight() {
	var wHeight = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
	// var wHeight = $(window).height();
	var nHeight = $('#banner').is(':visible') ? $('#banner').height() : 0;
	var cHeight = wHeight - nHeight - $('#taskbar').height()
			- $('#footer').height();
	return cHeight;
}

/**
 * @desc 获取任务栏区的宽度
 * @return
 */
function getTaskbarWidth() {
	return $('#taskbar').width() - $('#startmenu').width()
			- $('#control-panel').width()
			- $('#taskbar .banner_hide_wrap').width();
}

/**
 * @desc 隐藏开始菜单
 * @return
 */
function hideStartMenu() {
	
}

/**
 * @desc 隐藏显示左右菜单位置区域
 * @return
 */
function showLeftMenu() {
	$("#leftmenu").show();
	$("#main").css( {
		'margin-left' : '300px'
	});
	$("#main").css( {
		'margin-right' : '0px'
	});
	$('#startmenu').addClass('startmenu_disable');
	$("#rightmenu").hide();
	sys_status['menuPosition'] = 'left';
}

function hideLeftMenu() {
	$("#leftmenu").hide();
	$("#main").css( {
		'margin-left' : '0px'
	});
	$("#main").css( {
		'margin-right' : '0px'
	});
	sys_status['menuPosition'] = 'left';
}

function showRightMenu() {
	$("#rightmenu").show();
	$("#main").css( {
		'margin-right' : '300px'
	});
	$("#main").css( {
		'margin-left' : '0px'
	});
	$('#startmenu').addClass('startmenu_disable');
	$("#leftmenu").hide();
	sys_status['menuPosition'] = 'right';
}

function hideRightMenu() {
	$("#rightmenu").hide();
	$("#main").css( {
		'margin-left' : '0px'
	});
	$("#main").css( {
		'margin-right' : '0px'
	});
	sys_status['menuPosition'] = 'right';
}

/**
 * @desc 窗口改变大小时设置相关区域位置大小的改变
 * @return
 */
function onWindowResize() {
	var h = getCenterHeight();
	// 设置body区的高度
	$("#center").height(h);
	// 设置任务栏的宽度
	$("#tasktabs").width(getTaskbarWidth());
	// 设置开始菜单的高度
	$('#startmenupanel').height(h);
	// 设置开始菜单的位置
	$('#startmenupanel').offset($("#center").offset());
}

/**
 * @desc 隐藏显示Banner区域
 * @return
 */
function toggleBanner() {
	$("#banner").slideToggle(sys_status['animateTime']);
	// 由于动画延时，导致高宽计算置前，不等于实际高宽，所以要添加一个延时设置
	setTimeout((function() {
		// 设置body区的高度
			$("#center").height(getCenterHeight());
		}), sys_status['animateTime']);
	sys_status['showBanner'] = sys_status['showBanner'] ? false : true;
}

/**
 * @desc 设置开始菜单的位置
 * @return
 */
function startMenuPosition() {
	var offset = {};
	if (sys_status['startMenuLock']) {
		if (sys_status['menuPosition'] == 'left') {
			hideLeftMenu();
			showRightMenu();
			offset.top = $("#center").offset().top;
			offset.left = $("#center").width() - $('#startmenupanel').width();
			sys_status['menuPosition'] = (sys_status['menuPosition'] == 'left') ? 'right'
					: 'left';
		} else {
			hideRightMenu();
			showLeftMenu();
			offset = $("#center").offset();
			sys_status['menuPosition'] = (sys_status['menuPosition'] == 'right') ? 'left'
					: 'right';
		}
	} else {
		if (sys_status['menuPosition'] == 'left') {
			offset.top = $("#center").offset().top;
			offset.left = $("#center").width() - $('#startmenupanel').width();
		} else {
			offset = $("#center").offset();
		}
	}
	$('#startmenupanel').offset(offset);
}

/**
 * @desc 开始菜单查询功能
 * @return
 */
function searchMenu(){
	$("#menuSearch").focus(function(){
		var txt_value = $(this).val();
		if(txt_value == this.defaultValue){
			$(this).val("");
		}
	});
	$("#menuSearch").blur(function(){
		var txt_value = $(this).val();
		if(txt_value == ""){
			$(this).val(this.defaultValue);
		}
	});
}

/**
 * @desc 收缩展开开始菜单
 * @return
 */
function openUpMenu(){
	var menuUnfold = $("#application_unfold");
	var menuShrink = $("#application_shrink");
	menuUnfold.click(function(){
		$("#second_menu > li > a.expand").addClass('active');
		$("#second_menu > li > a.expand").parent().children('ul').show();
		$('#second_panel > .second-panel-menu').jscroll();
	});
	menuUnfold.next("span").click(function(){
		$("#second_menu > li > a.expand").addClass('active');
		$("#second_menu > li > a.expand").parent().children('ul').show();
		$('#second_panel > .second-panel-menu').jscroll();
	});
	menuShrink.click(function(){
		$("#second_menu > li > a.expand").removeClass('active');
		$("#second_menu > li > a.expand").parent().children('ul').hide();
		$('#second_panel > .second-panel-menu').jscroll();
	});
	menuShrink.next("span").click(function(){
		$("#second_menu > li > a.expand").removeClass('active');
		$("#second_menu > li > a.expand").parent().children('ul').hide();
		$('#second_panel > .second-panel-menu').jscroll();
	});
}

/**
 * @desc 获取开始菜单二级菜单的内容
 * @param {String}id
 * @return
 */
function getSecondMenuHTML(id) {
	var html = '';
	for ( var i = 0; i < second_array[id].length; i++) {
		var func_id = 'f' + second_array[id][i];
		var func_name = func_array[func_id][0];
		var func_code = func_array[func_id][1];
		var open_window = func_array[func_id][3] ? func_array[func_id][3] : '';
		var bExpand = func_code.substr(0, 1) == "@" && third_array[func_id];
		var onclick = bExpand ? "" : "addTab(" + func_id.substr(1) + ",'"
				+ func_name.replace("'", "\'") + "','"
				+ func_code.replace("'", "\'") + "', true);";

		html += '<li><a id="' + func_id + '" href="javascript:;" onclick="'
				+ onclick + '"' + (bExpand ? ' class="expand"' : '')
				+ ' hidefocus="hidefocus"><span>' + func_name + '</span></a>';
		if (bExpand) {
			html += '<ul>';
			for ( var j = 0; j < third_array[func_id].length; j++) {
				var func_id1 = 'f' + third_array[func_id][j];
				var func_name1 = func_array[func_id1][0];
				var func_code1 = func_array[func_id1][1];
				var open_window1 = func_array[func_id1][3] ? func_array[func_id1][3]
						: '';
				var onclick1 = "addTab(" + func_id1.substr(1) + ",'"
						+ func_name1.replace("'", "\'") + "','"
						+ func_code1.replace("'", "\'") + "',true);";
				html += '<li><a id="' + func_id1
						+ '" href="javascript:;" onclick="' + onclick1
						+ '" hidefocus="hidefocus"><span>' + func_name1
						+ '</span></a></li>';
			}
			html += '</ul>';
		}
		html += '</li>';
	}

	return '<ul id="second_menu">' + html + '</ul>';
};

/**
 * @desc 菜单滚动箭头事件,id为first_menu
 * @param {String}id
 * @return
 */
function initMenuScroll(id) {
	// 菜单向上滚动箭头事件
	$('#' + id + ' > .scroll-up:first').hover(function() {
		$(this).addClass('scroll-up-hover');
		if (id == 'first_panel') {
			$("#first_menu > li > a.active").removeClass('active'); // 恢复一级active的菜单为正常
		}
	}, function() {
		$(this).removeClass('scroll-up-hover');
	});

	// 点击向上箭头
	$('#' + id + ' > .scroll-up:first').click(function() {
		var ul = $('#' + id + ' > ul:first');
		ul.animate( {
			'scrollTop' : (ul.scrollTop() - SCROLL_HEIGHT)
		}, 600);
	});

	// 向下滚动箭头事件
	$('#' + id + ' > .scroll-down:first').hover(function() {
		$(this).addClass('scroll-down-hover');
		if (id == 'first_panel') {
			$("#first_menu > li > a.active").removeClass('active'); // 恢复一级级active的菜单为正常
		}
	}, function() {
		$(this).removeClass('scroll-down-hover');
	});

	// 点击向下箭头
	$('#' + id + ' > .scroll-down:first').click(function() {
		var ul = $('#' + id + ' > ul:first');
		ul.animate( {
			'scrollTop' : (ul.scrollTop() + SCROLL_HEIGHT)
		}, 600);
	});
};

/*弹出窗口页面暂时用不到
function openURL(id, name, url, open_window, width, height, left, top) {
	id = !id ? ('w' + (nextTabId++)) : id;
	if (open_window != "1") {
		window.setTimeout(function() {
			jQuery().addTab(id, name, url, true)
		}, 1);
	} else {
		width = typeof (width) == "undefined" ? 780 : width;
		height = typeof (height) == "undefined" ? 550 : height;
		left = typeof (left) == "undefined" ? (screen.availWidth - width) / 2
				: left;
		top = typeof (top) == "undefined" ? (screen.availHeight - height) / 2 - 30
				: top;
		window
				.open(
						url,
						id,
						"height="
								+ height
								+ ",width="
								+ width
								+ ",status=0,toolbar=no,menubar=yes,location=no,scrollbars=yes,top="
								+ top + ",left=" + left + ",resizable=yes");
	}
	jQuery(document).trigger('click');
}
*/

/**
 * @desc 开始菜单加载事件
 * @return
 */
function startMenuInit() {
	$('#startmenu').mouseover(function() {
		$(this).toggleClass('startmenu_mouseover');
	});
	// 鼠标点击导航图标按钮弹出菜单面板
	$('#startmenu')
			.bind('click', function() {
				if ($(this).hasClass('startmenu_disable')) {
					return;
				}
				searchMenu();
				openUpMenu();
				$('#startmenupanel').slideToggle(sys_status['animateTime']);
				if ($('#startmenupanel:visible').length) {
					$('#startmenupanel_overlay').hide();
					$('#startmenupanel').slideUp(300);
					$(this).removeClass('active');
				}
				// 设置导航图标为active状态
					$(this).addClass('active');

					// 遮罩层位置和显示
					$('#startmenupanel_overlay').show();

					// 菜单面板位置
					var top = $('#startmenu').offset().top
							+ $('#startmenu').outerHeight() - 6;
					$('#startmenupanel').css( {
						top : top
					});
					$('#startmenupanel').slideDown('fast');

					// 计算并设置菜单面板的高度,是否显示滚动箭头
					var scrollHeight = $("#first_menu").attr('scrollHeight');
					if ($("#first_menu").height() < scrollHeight) {
						var height = ($('#startmenupanel').offset().top - $(
								'#startmenu').offset().top) * 0.7; // 可用高度为开始菜单和状态栏高差的70%
						height = height - height % MENU_ITEM_HEIGHT; // 可用高度为
																		// MENU_ITEM_HEIGHT
																		// 的整数倍
						// 如果可用高度大于允许的最高高度，则限制
						height = height <= MAX_PNAEL_HEIGHT ? height
								: MAX_PNAEL_HEIGHT;
						// 如果可用高度超过scrollHeight，则设置高度为scrollHeight
						height = height > scrollHeight ? scrollHeight : height;
						$('#first_menu').height(height);
					} else {
						var height = scrollHeight > MIN_PNAEL_HEIGHT ? scrollHeight
								: MIN_PNAEL_HEIGHT;
						$('#first_menu').height(
								$('#startmenupanel').height() - 67);
					}

					if ($("#first_menu").height() >= $("#first_menu").attr(
							'scrollHeight')) {
						$('#first_panel > .scroll-up:first').hide();
						$('#first_panel > .scroll-down:first').hide();
					}

					// 计算并设置二级菜单面板的位置
					var top = $('#first_menu').offset().top
							- $("#startmenupanel").offset().top;
					$('#second_panel').css('top', top - 5);
					$('#second_panel > .second-panel-menu').css('height',
							$('#startmenupanel').height() - 67);

					// 第一次打开时设置二级菜单滚动事件
					if ($('#second_panel > .second-panel-menu > .jscroll-c').length <= 0)
						$('#second_panel > .second-panel-menu').jscroll();
				});

	// 生成一级菜单
	var html = "";
	for ( var i = 0; i < first_array.length; i++) {
		var menu_id = first_array[i];
		if (typeof (func_array['m' + menu_id]) != "object")
			continue;

		var image = !func_array['m' + menu_id][1] ? 'icon_default'
				: func_array['m' + menu_id][1];
		html += '<li><a id="m'
				+ menu_id
				+ '" href="javascript:;" hidefocus="hidefocus"><img src="images/'
				+ image + '.png" align="absMiddle" /> '
				+ func_array['m' + menu_id][0] + '</a></li>';
	}
	$("#first_menu").html(html);
	$("#first_menu").mousewheel(function() {
		$('#first_menu').stop().animate( {
			'scrollTop' : ($('#first_menu').scrollTop() - this.D)
		}, 300);
	});

	// 一级菜单滚动箭头事件
	initMenuScroll('first_panel');

	// 一级菜单hover和click事件
	$("#first_menu > li > a").click(function() {
		// 如果当前一级菜单为active，则返回
			if (this.className.indexOf('active') >= 0)
				return;

			$("#second_menu > li > a.expand").removeClass('active'); // 恢复二级expand菜单为正常
			$("#first_menu > li > a.active").removeClass('active'); // 恢复一级级active的菜单为正常

			// 获取当前一级菜单下属二级菜单的HTML代码，并更新二级菜单面板
			$('#second_panel > .second-panel-menu').html(
					getSecondMenuHTML(this.id));
			$("#" + this.id).addClass('active'); // 将当前一级菜单设为active

			// 二级菜单滚动事件
			$('#second_panel > .second-panel-menu').jscroll();

			// 二级菜单点击展开三级菜单
			$('#second_menu > li > a.expand').click(function() {
				$(this).toggleClass('active');
				$(this).parent().children('ul').toggle();
				$('#second_panel > .second-panel-menu').jscroll();
			});
		});

	if (sys_status['menuExpand'] != ""
			&& typeof (second_array['m' + sys_status['menuExpand']]) == "object") {
		// 展开定义的二级菜单
		$('#m' + sys_status['menuExpand']).addClass('active');
		$('#second_panel > .second-panel-menu').html(
				getSecondMenuHTML('m' + sys_status['menuExpand']));

		// 二级菜单点击展开三级菜单
		$('#second_menu > li > a.expand').click(function() {
			$(this).toggleClass('active');
			$(this).parent().children('ul').toggle();
			$('#second_panel > .second-panel-menu').jscroll();
		});
	} else {
		// 登录时把常用任务菜单项作为二级菜单的内容
		var html = "";
		for ( var i = 0; i < shortcutArray.length; i++) {
			if (typeof (func_array['f' + shortcutArray[i]]) != "object")
				continue;

			var func_id = 'f' + shortcutArray[i];
			var func_name = func_array[func_id][0];
			var func_code = func_array[func_id][1];
			var open_window = func_array[func_id][3] ? func_array[func_id][3]
					: "";

			if (func_code.substr(0, 1) == "@")
				continue;

			var onclick = "addTab(" + func_id.substr(1) + ",'"
					+ func_name.replace("'", "\'") + "','"
					+ func_code.replace("'", "\'") + "', true);";
			html += '<li><a id="' + func_id + '" href="javascript:;" onclick="'
					+ onclick + '" hidefocus="hidefocus"><span>' + func_name
					+ '</span></a></li>';
		}
		html = '<ul id="second_menu">' + html + '</ul>';
		$('#second_panel > .second-panel-menu').html(html);
	}

	$('#second_panel, #second_menu').bind('selectstart', function() {
		return false;
	});
	$("#menu-tabs-addbtn a").bind('click', function(){
		//开始菜单tab标签的个数
		var menuLiLen = $("#menu-tabs ul li").length;
		//限定tab标签个数只能有3个
		if(menuLiLen > 2){
			return false;
		}
		//增加标签
		addMenuTab(1,"菜单标签栏");
	});
	$("#menu-tabs ul li a").live("click", function() {
		var $lis = $("#menu-tabs ul li");
		$lis.children("a").removeClass("menuTabActive");
		// var i = $("#scroll ul li a").index($(this));
		var i = $lis.children("a").index($(this));
		$lis.eq(i).children("a").addClass("menuTabActive");
	});
	// 菜单锁定控制
	$("#menu_unlock").bind('click', function() {
		$(this).toggleClass('menu_lock');
		if (sys_status['menuPosition'] == 'left') {
			if (!sys_status['startMenuLock']) {
				showLeftMenu();
				$('#startmenupanel_overlay').hide();
			} else {
				hideLeftMenu();
				$('#startmenupanel_overlay').show();
				$('#startmenu').removeClass('startmenu_disable');
			}
		} else {
			if (!sys_status['startMenuLock']) {
				showRightMenu();
				$('#startmenupanel_overlay').hide();
			} else {
				hideRightMenu();
				$('#startmenupanel_overlay').show();
				$('#startmenu').removeClass('startmenu_disable');
			}
		}
		sys_status['startMenuLock'] = !sys_status['startMenuLock'];
	});
	// 开始菜单左右
	$("#menu-switch")
			.bind(
					'click',
					function() {
						$(this).toggleClass('menu_switch');
						startMenuPosition();
						sys_status['menuPosition'] = (sys_status['menuPosition'] == 'left') ? 'right'
								: 'left';
					});
	//关闭开始菜单
	$("#menu-close")
			.bind(
					'click',
					function() {
						if (sys_status['startMenuLock']) {
							if (sys_status['menuPosition'] == 'left') {
								$('#startmenupanel').slideUp();
								hideLeftMenu();
								$('#startmenu')
										.removeClass('startmenu_disable');
								$('#menu_unlock').removeClass('menu_lock');
								sys_status['startMenuLock'] = !sys_status['startMenuLock'];
							} else {
								$('#startmenupanel').slideUp();
								hideRightMenu();
								$('#startmenu')
										.removeClass('startmenu_disable');
								$('#menu_unlock').removeClass('menu_lock');
								$("#menu-switch").removeClass('menu_switch');
								$('#startmenupanel_overlay').hide();
								sys_status['startMenuLock'] = !sys_status['startMenuLock'];
								sys_status['menuPosition'] = (sys_status['menuPosition'] == 'left') ? 'right'
										: 'left';
							}
						} else {
							$('#startmenupanel').slideUp();
							$('#startmenupanel_overlay').hide();
							if (sys_status['menuPosition'] == 'right') {
								$("#menu-switch").removeClass('menu_switch');
								sys_status['menuPosition'] = (sys_status['menuPosition'] == 'right') ? 'left'
										: 'right';
							}
						}

					});
	// 点击页面，隐藏各级菜单面板，并清除二级和三级菜单的active状态
	$('#startmenupanel_overlay')
			.click(
					function() {
						if ($('#startmenupanel:visible').length) {
							$('#startmenupanel_overlay').hide();
							if (sys_status['menuPosition'] == 'right') {
								$("#menu-switch").removeClass('menu_switch');
								sys_status['menuPosition'] = (sys_status['menuPosition'] == 'right') ? 'left'
										: 'right';
							}
							$('#startmenupanel').slideUp(300);
							$('#startmenu').removeClass('startmenu_disable');
							$('#startmenu').removeClass('active');
						}
					});
}

/**
 * @desc 功能菜单区设置
 * @return
 */
function dropMenu() {
	// 系统设置下来菜单
	$('#knocker_corner_ico').menu( {
		type : 'click',
		showMenuType : 'down',
		width : 150,
		items : settingMenu
	});
	// 门户切换下来菜单
	$('#sys_corner_ico').menu( {
		type : 'click',
		showMenuType : 'down',
		width : 150,
		items : portalMenu
	});
	// 帮助下拉菜单
	$('#help_corner_ico').menu( {
		type : 'click',
		showMenuType : 'down',
		width : 150,
		items : helpMenu
	});
	// 退出、注销菜单
	$('#quit_corner_ico').menu( {
		type : 'click',
		showMenuType : 'down',
		width : 150,
		items : sysMenu
	});
	// 下拉菜单点击效果
	$("div.control_ico_wrap").hover(function() {
		$(this).addClass('control_ico_wrap_hover');
	}, function() {
		$(this).removeClass('control_ico_wrap_hover');
	});
	$("#control-panel .control_ico_wrap").each(function() {
		$(this).children().eq(0).mousedown(function() {
			$(this).parent().addClass('control_ico_wrap_left');
		}).mouseup(function() {
			$(this).parent().removeClass('control_ico_wrap_left');
		}).mouseover(function() {
			$(this).parent().addClass('control_ico_wrap_hover');
		}).mouseout(function() {
			$(this).parent().removeClass('control_ico_wrap_hover');
		});
		$(this).children().eq(1).mouseover(function() {
			$(this).parent().addClass('control_ico_wrap_hover');
		}).mousedown(function() {
			$(this).parent().addClass('control_ico_wrap_right');
		}).focusout(function() {
			$(this).parent().removeClass('control_ico_wrap_right');
		});
	});
}

/**
 * @desc 添加标签 param: name 标签名称 url 标签对应的页面路径
 * @param {String}name
 * @param {String}url
 * @return
 */
//function addTab(name, url) {
//	// 若参数无效，则直接返回
//	if ($.trim(String(name)) == "" || $.trim(url) == "") {
//		return false;
//	}
//	// 追加tab标签
//	var li_html = [];
//	li_html.push("<li class='on'><a href='javascript:;'><span>");
//	li_html.push(name);
//	li_html.push("</span></a>");
//	li_html.push("<span class='tabClose'>X</span></li>");
//
//	var index = 0;
//	// 取消其他标签的激活状态
//	$("#scroll ul li").each(function(i) {
//		if ($(this).hasClass("on")) {
//			index = i;
//		}
//		$(this).removeClass("on");
//	});
//	// 隐藏其他的内容页
//	$("#main div").css("display", "none");
//
//	// tabNew.data("tabUrl", url);
//
//	// 为新加入的tab标签添加内容页
//	var conHtml = [];
//	conHtml
//			.push("<div><iframe style='width:100%;height:100%;' frameborder='0' src='");
//	conHtml.push(url);
//	conHtml.push("'></iframe></div>");
//
//	// 在之前处于激活状态的标签之后插入添加的标签
//	$("#scroll ul li").eq(index).after(li_html.join(""));
//	// 在对应的内容页后插入新的内容页面
//	$("#main div").eq(index).after(conHtml.join(""));
//}

/**
 * @desc 添加开始菜单标签
 * @param {String}id 标签id
 * @param {String}name 标签名称
 * @return
 */
function addMenuTab(id, name){
	// 若参数无效，则直接返回
	if ($.trim(String(id)) == "" || $.trim(name) == "") {
		return false;
	}
	// 追加tab标签
	var li_html = [];
	li_html.push("<li><a href='javascript:;' class='menuTabActive'><span>");
	li_html.push(name);
	li_html.push("</span></a>");
	li_html.push("<span class='menuTabClose'></span></li>");

	var index = 0;
	// 取消其他标签的激活状态
	$("#menu-tabs ul li").each(function(i) {
		if ($(this).children("a").hasClass("menuTabActive")) {
			index = i;
		}
		$(this).children("a").removeClass("menuTabActive");
	});
	// 隐藏其他的内容页
	//$("#main div").css("display", "none");

	// tabNew.data("tabUrl", url);

	// 为新加入的tab标签添加内容页
	//var conHtml = [];
	//conHtml
			//.push("<div><iframe style='width:100%;height:100%;' frameborder='0' src='");
	//conHtml.push(url);
	//conHtml.push("'></iframe></div>");

	// 在之前处于激活状态的标签之后插入添加的标签
	$("#menu-tabs ul li").eq(index).after(li_html.join(""));
	// 在对应的内容页后插入新的内容页面
	//$("#main div").eq(index).after(conHtml.join(""));
}

$(function() {
	if (!sys_status['showBanner']) {
		$("#banner").hide();
	}
	$(window).bind('resize', function() {
		onWindowResize();
	});
	onWindowResize();

	// 控制banner条隐藏/显示
	$("#banner_hide").bind("click", toggleBanner);

	startMenuInit();

	dropMenu();

});