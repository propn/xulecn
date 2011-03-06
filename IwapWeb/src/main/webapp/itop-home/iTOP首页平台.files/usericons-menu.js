// 右边菜单设置
$(function() {
			// 系统设置下来菜单
			$('#settingLink').menu({
						type : 'click',
						showMenuType : 'down',
						width : 150,
						items : settingMenu
					});
			// 门户切换下来菜单
			$('#portalLink').menu({
						type : 'click',
						showMenuType : 'down',
						width : 150,
						items : portalMenu
					});
			// 帮助下拉菜单
			$('#helpLink').menu({
						type : 'click',
						showMenuType : 'down',
						width : 150,
						items : helpMenu
					});
			// 退出、注销菜单
			$('#sysLink').menu({
						type : 'click',
						showMenuType : 'down',
						width : 150,
						items : sysMenu
					});
		});