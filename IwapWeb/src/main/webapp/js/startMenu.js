/**
 * startMenu - wooUI
 * 基于jQuery1.4.2+
 */
(function($){
	//控制开始菜单状态
	/*
		开始菜单状态包括：show、hide、lock、unlock、right、left
		动作（action）包括：show、hide、lock、unlock、toright、toleft、close
		lock状态下，show、hide两种action无效
		close动作将会清除lock状态，show、hide两种action有效
	*/
	var defaults = {
		display:false,
		lock:false,
		position:'LEFT'	//LEFT or RIGHT
	}
	

	function init(menuBtn){
		setSize();
		setPosition();
		$(menuBtn).click(function(){
			toogle(this);
			//$(this).focus();
		});
		/*.focusout(function(){
			//toogle(this);
		});*/
		$('#menu-btns').bind('click',function(e){
			var target = $(e.target);
			if(target.hasClass('menu-close')){
				close(target);
			}else if(target.hasClass('menu-lock')){
				lock(target);
			}else if(target.hasClass('menu-switch')){
				switchMenu(target);
			}
		});
		/*$(document).bind('click',function(e){
			debugger;
			if($(e.target).parent('#startmenupanel').length == 0){
				alert('not in');
			}
		});*/
		$('#startmenupanel_overlay').click(function(e){
			debugger;
			//toogle(menuBtn);
			//$(this).hide();
		});
	}
	
	function setSize(option/*{}*/){
		var panels = $.data($('body')[0], 'layout').panels;
		var height = panels.center.height();
		$('#startmenupanel').height(height);
		//$("body").layout('panel');
		
	}
	
	function setPosition(option/*{}*/){
		var panels = $.data($('body')[0], 'layout').panels;
		var offset;
		if(sys_status['startMenuLock']){
			offset = panels[sys_status['menuPosition']].offset();
		}else{
			if(sys_status['menuPosition'] == 'west'){
				offset = panels.center.offset();
			}else{
				offset = {
					top : panels.center.offset().top,
					left : panels.center.width() - $('#startmenupanel').width()
				}
			}
		}
		$('#startmenupanel').offset(offset);
		//$("body").layout('panel');
		
	}

	function toogle(target){
		if($(target).hasClass('start-menu-disable')){
			return;
		}
		$('#startmenupanel').toggle(sys_status['animate']?sys_status['animateTime']:0);
		if($('#startmenupanel').is(':visible')){
			$('#startmenupanel_overlay').show();
		}
	}
	function lock(target){
		$(target).toggleClass('menu-unlock');
		$('#startmenu').toggleClass('start-menu-disable');
		sys_status['startMenuLock'] =sys_status['startMenuLock']?false : true;
		//defaults['lock'] = defaults['lock']?false:true;
		//var panel = sys_status['menuPosition'] == 'LEFT'?'#menu-left':'#menu-right'
		//$(selector).toggle(sys_status['animate']?sys_status['animateTime']:0);
		
		$('body').layout(!sys_status['startMenuLock']?'hide':'show',sys_status['menuPosition']);
	}
	function switchMenu(target){
		if(sys_status['startMenuLock']){
			$('body').layout('hide',sys_status['menuPosition']);
			$('body').layout('show',sys_status['menuPosition'] == 'west'?'east':'west');	
		}
		sys_status['menuPosition'] = sys_status['menuPosition'] == 'west'?'east':'west';
		setPosition();
		$(target).toggleClass('menu-switch-right');
		//var selector = sys_status['menuPosition'] == 'LEFT'?'#menu-left':'#menu-right'
		/*$('#menu-left').toggle(sys_status['animate']?sys_status['animateTime']:0);
		$('#menu-right').toggle(sys_status['animate']?sys_status['animateTime']:0);
		setStartMenuPosition();*/
		/*
		if(sys_status['menuPosition'] == 'LEFT'){
			$('#menu-left').hide(sys_status['animate']?sys_status['animateTime']:0);
			$('#menu-right').show(sys_status['animate']?sys_status['animateTime']:0);
			sys_status['menuPosition'] = 'RIGHT';
		}else if(sys_status['menuPosition'] == 'RIGHT'){
			$('#menu-left').show(sys_status['animate']?sys_status['animateTime']:0);
			$('#menu-right').hide(sys_status['animate']?sys_status['animateTime']:0);
			sys_status['menuPosition'] = 'LEFT';
		}
		setStartMenuPosition();*/
		//sys_status['menuPosition'] = sys_status['menuPosition'] == 'LEFT'?'RIGHT':'LEFT';
		//$(target).toggleClass('menu-switch-right');
		/*if(!sys_status['startMenuLock']){
			lock('#menu-btns a.menu-lock');
		}*/
	}

	function close(target){
		//面板关闭
		$('#startmenupanel').hide(sys_status['animate']?sys_status['animateTime']:0);
		//开始菜单按钮可用
		$('#startmenu').removeClass('start-menu-disable');
		//菜单左右位置的占用空间隐藏
		//$('#menu-left,#menu-right').hide(sys_status['animate']?sys_status['animateTime']:0);
		$('body').layout('hide',sys_status['menuPosition']);
		//取消菜单锁定状态
		sys_status['startMenuLock'] = false;
		$('#menu-btns a.menu-lock').removeClass('menu-unlock');
	}
	$.fn.startMenu = function(options,param){
		if (typeof options == 'string'){
			switch(options){
			
			case 'resize':
				return this.each(function(){
					setSize(this, param);
				});
			case 'repositoin':
				return this.each(function(){
					setPosition(this, param);
				});
			}
		}
		init(this);
	};
})(jQuery);