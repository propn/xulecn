MenuBar = {
	
	refreshBar : function(menubar) {
		var menu=menubar.getAttribute("menu");//获取和menubar关联的menu对象
		if (typeof(menu)=="string" && menu!=""){
			eval("menu="+menu);
			menubar.menu=menu;
		}

		if (menubar.menu){//如果menubar有关联到一个menu对象的话。。。
		
			if(!menubar.table){//如果menubar下面还没有<table>标签，则为其创建一个
				menubar.innerHTML = '<table cellspacing="1"  cellpadding="0" ></table>';
				menubar.table = menubar.firstChild;	
			}
			
			for(var i=0; i<menubar.table.tBodies[0].rows.length; i++){
				var row=menubar.table.tBodies[0].rows[i];
				row.removeNode(true);
			}
	
			var row=menubar.table.tBodies[0].insertRow();
			row.align="center";

			//使用menu对象来生成菜单界面
			for(var i=0; i<menu.topItem.items.length; i++){
				var item=menu.topItem.items[i];
				//触发菜单的刷新事件
				Document.fireUserEvent(Document.getElementEventName(menu, "onRefreshItem"), [menu, item]);
				
				if (!item.visible)	//不可见菜单，忽略
					continue;
	
				var cell=row.insertCell();//插入菜单项
				cell.innerHTML="<button hideFocus=\"true\"></button>";
				var button=cell.firstChild;
	
				button.extra="menuitem";
				button.className="button";
				if (item.icon){
					button.innerHTML="<img src=\""+item.icon+"\" style=\"margin-right: 4px\">"+item.label;
				}
				else{
					button.innerText=item.label;
				}

				button.style.backgroundImage = "url("+Global.theme_root+"/button.gif)";

				//菜单项的鼠标响应事件
				button.onmouseover=MenuBar.btn_onmouseover;
				button.onmouseout=MenuBar.btn_onmouseout;
				button.onclick=MenuBar.btn_onclick;
				
				button.title=item.toolTip;
				button.menuItem=item;
				button.disabled=!System.isTrue(item.enabled);
				item.cell=button;
			}
		}
	},
	
	btn_onmouseover : function() {
		function showBarMenu(menuItem, button){
			if (Menu.top_menuItem) Menu.hideMenu();
		
			menuItem.button=button;
			menuItem.menu.showMode="menubar";
			Menu.showSubMenu(menuItem, "button", menuItem.button, false);
			Menu.top_menuItem=menuItem;
		}		
		var button=Menu.findMenuItemHolder(event.srcElement);
		if (button){
			button.style.backgroundImage = "url()";
			button.className="button_hot";
	
			var menubar=Table.getTableByCell(button);
			if (menubar.getAttribute("menuOpened")) {
				var menuItem=button.getAttribute("menuItem");
				if (menuItem==Menu.current_menuItem) return;
	
				if (menuItem){
					var currentSlideItem=menuItem.parentItem.currentMenuItem;
					var newSlideItem=null;
	
					if (menuItem.items.length>0){
						button.style.backgroundImage = "url()";
						button.className="button_active";
	
						showBarMenu(menuItem, button);
						Menu.stored_frame=menubar;
						setTimeout("Menu.stored_frame.focus();", 0);
					}
	
					if (currentSlideItem){
						if (currentSlideItem!=menuItem){
							Menu._hideMenu(currentSlideItem);
						}
					}
					else{
						menuItem.parentItem.currentMenuItem=menuItem;
					}
	
					menubar.setAttribute("menuOpened", true);
				}
			}
		}
	},
	
	btn_onmouseout : function() {
		var button=Menu.findMenuItemHolder(event.srcElement);
		if (button){
			var menubar=Table.getTableByCell(button);
			var menuItem=button.getAttribute("menuItem");
			if (!menubar.getAttribute("menuOpened") || menuItem.items.length==0) {
				button.style.backgroundImage = "url("+Global.theme_root+"/button.gif)";
				button.className="button";
			}
		}
	},
	
	btn_onclick : function() {
		if (event.button!=2){
			var button=Menu.findMenuItemHolder(event.srcElement);
			if (button){
				var menuItem=button.getAttribute("menuItem");
				if (menuItem && System.isTrue(menuItem.enabled)) Menu.processMenuItemClick(menuItem.menu, menuItem);
	
				if (menuItem.items.length>0){
					var menubar=Table.getTableByCell(button);
					if (!menubar.getAttribute("menuOpened")){
						menubar.setAttribute("menuOpened", true);
						MenuBar.btn_onmouseover();
						return;
					}
				}
			}
		}
	}
}

function initMenuBar(menubar){
	MenuBar.refreshBar(menubar);
}