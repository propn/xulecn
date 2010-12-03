Menu = {
	top_menuItem : null,
	current_menuItem : null,
	stored_frame : null,
	stored_item : null,
	array_menu : new Array(),

	//显示菜单
	showMenu : function(menu){
		if (Menu.top_menuItem){
			Menu.hideMenu();
			return;
		}
	
		menu.showMode="popup";
		Menu.showSubMenu(menu.topItem, "popup", null, true);//显示子菜单
		Menu.stored_frame=menu.topItem.subFrame;
		setTimeout("Menu.stored_frame.focus();", 0);
		Menu.top_menuItem=menu.topItem;
	},
	
	//子菜单的显示
	showSubMenu : function(menuItem, locateMode, element, animate){
	
		function prepareMenu(){
			if (menuItem.items.length<1) return;//没有子菜单，直接返回
		
			var frame=menuItem.subFrame;
			if (!frame){
				//生成显示子菜单的frame对象
				frame=document.createElement("<div extra=menuframe class=\"menuframe\" style=\"position:absolute; visibility:hidden; " +
					"z-index: "+menuItem.zIndex+"\"></div>");
				document.body.appendChild(frame);
				with (frame){
					innerHTML="<table width=128px border=0 cellspacing=0 cellpadding=3 rules=all class=menu></table>";
					onmouseover=Menu.onmouseover;
					onmousedown=Menu.onmousedown;
				}
		
				//插入一个菜单项
				var row=frame.firstChild.insertRow();
				row.extra="menuitem";
				var cell=row.insertCell();
				cell.width="16px";
				cell=row.insertCell();
				cell.noWrap=true;
				cell=row.insertCell();
				cell.width="9px";
				frame.repeatrow=row.cloneNode(true);
		
				frame.menuItem=menuItem;
				menuItem.subFrame=frame;
			}
		
			var tBody=frame.firstChild.tBodies[0];
			for (var i=tBody.rows.length-1; i>=0; i--)
				tBody.rows[i].removeNode(true);
		
			var row, cell;
			for(var i=0; i<menuItem.items.length; i++){
				var item=menuItem.items[i];
				Document.fireUserEvent(Document.getElementEventName(menuItem.menu, "onRefreshItem"), [menuItem.menu, item]);
				if (!item.visible) continue;
		
				row=frame.repeatrow.cloneNode(true);
				frame.firstChild.tBodies[0].insertAdjacentElement("beforeEnd", row);
		
				row.className=(item.enabled)?"":"row_disabled";
				if (item.icon) row.cells[0].innerHTML="<img src=\""+item.icon+"\">";
				row.cells[1].innerHTML=item.label;
				if (item.toolTip) row.title=item.toolTip;
				if (item.items.length>0) row.cells[2].innerHTML="<label style=\"font-family: Webdings; font-size: 7pt\">4</label>";
				item.row=row;
				item.frame=frame;
				row.menuItem=item;
			}
			return frame;
		}	
		
		function locateMenu(frame, locateMode, element){
			switch (locateMode){
			case "popup":
				var tmp_left, tmp_top;
				if ( event.x + frame.offsetWidth > document.body.clientWidth - 2 )
					tmp_left=event.x-frame.offsetWidth+5;
				else
					tmp_left=event.x-5;
		
				if (event.y+frame.offsetHeight>document.body.clientHeight-1)
					tmp_top=event.y-frame.offsetHeight+6;
				else
					tmp_top=event.y-4;
		
				frame.style.posLeft=tmp_left+document.body.scrollLeft;
				frame.style.posTop=tmp_top+document.body.scrollTop;
				break;
			case "button":
				var pos=Document.getAbsPosition(element, document.body);
		
				if (pos[0]+frame.offsetWidth>document.body.clientWidth-2)
					frame.style.posLeft=pos[0]+element.offsetWidth-frame.offsetWidth-2;
				else
					frame.style.posLeft=pos[0];
		
				if (pos[1]+element.offsetHeight+frame.offsetHeight>document.body.clientHeight-1)
					frame.style.posTop=pos[1]-frame.offsetHeight-1;
				else
					frame.style.posTop=pos[1]+element.offsetHeight+2;
				break;
			case "submenu":
				var pos=Document.getAbsPosition(element, document.body);
		
				if (pos[0]+element.offsetWidth+frame.offsetWidth>document.body.clientWidth-2)
					frame.style.posLeft=pos[0]-frame.offsetWidth;
				else
					frame.style.posLeft=pos[0]+element.offsetWidth;
		
				if (pos[1]+frame.offsetHeight>document.body.clientHeight-1)
					frame.style.posTop=pos[1]+element.offsetHeight-frame.offsetHeight+1;
				else
					frame.style.posTop=pos[1]+1;
				break;
			}
		}	
		
		var frame=prepareMenu();
		if (!frame) return;
	
		locateMenu(frame, locateMode, element);
  	frame.style.visibility="visible";
		return frame;
	},
	
	_hideMenu : function(menuItem){
		if (menuItem.currentMenuItem) 
			Menu._hideMenu(menuItem.currentMenuItem);
			
		if (menuItem.parentItem) 
			menuItem.parentItem.currentMenuItem=null;
			
		if (menuItem==Menu.current_menuItem) 
			Menu.current_menuItem=null;
			
		if (menuItem==Menu.top_menuItem){
			if (Menu.top_menuItem.menu.showMode=="menubar"){
				Menu.top_menuItem.cell.style.backgroundImage = "url("+Global.theme_root+"/button.gif)";
				Menu.top_menuItem.cell.className="button";
				var menubar=Table.getTableByCell(Menu.top_menuItem.cell);
				menubar.setAttribute("menuOpened", false);
			}
			Menu.top_menuItem=null;
		}
	
		var frame=menuItem.subFrame;
		if (!frame) return;
		if (frame.style.visibility!="visible") return;
		frame.style.visibility="hidden";
	},	
	
	hideMenu : function(){
		if (!Menu.top_menuItem) 
			return;
		Menu._hideMenu(Menu.top_menuItem);
	},	
	
	findMenuItemHolder : function(element){
		while (element){
			if (element.getAttribute("extra")=="menuitem")
				return element;
			element=element.parentElement;
		}
	},	
	
	onmouseover : function() {
		var element=Menu.findMenuItemHolder(event.srcElement);
	
		if (element){
			var menuItem=element.getAttribute("menuItem");
			if (menuItem==Menu.current_menuItem) return;
			Menu.current_menuItem=menuItem;
	
			if (menuItem){
				if (menuItem.enabled){
					element.className="row_selected";
					Menu.showSubMenu(menuItem, "submenu", element, true);
				}
	
				var currentSlideItem=menuItem.parentItem.currentMenuItem;
				var newSlideItem=null;
				if (currentSlideItem){
					if (currentSlideItem!=menuItem){
						Menu._hideMenu(currentSlideItem);
					}
					if (currentSlideItem.parentItem==menuItem.parentItem){
						currentSlideItem.row.className=(currentSlideItem.enabled)?"":"row_disabled";
						menuItem.parentItem.currentMenuItem=menuItem;
					}
				}
				else{
					menuItem.parentItem.currentMenuItem=menuItem;
				}
			}
		}
	},	
	
	onmousedown : function() {
		var frame=Menu.current_menuItem.frame;
	
		if (event.button!=2){
			var element=Menu.findMenuItemHolder(event.srcElement);
	
			if (element){
				var menuItem=element.getAttribute("menuItem");
				if (menuItem && System.isTrue(menuItem.enabled))
					Menu.processMenuItemClick(menuItem.menu, menuItem);
			}
		}
		Menu.hideMenu();
	},	
	
	processMenuItemClick : function(menu, menuItem){
		try{
			Menu.hideMenu();//隐藏菜单
			var event_name=Document.getElementEventName(menu, "onItemClick");
			if (Document.isUserEventDefined(event_name)){//如果用户定义了onItemClick事件，则触发这个事件
				var event_result=Document.fireUserEvent(event_name, [menu, menuItem]);
				if (event_result)
					throw event_result;
			}
			
			//command属性优先于path属性 
			if (menuItem.command) {//如果菜单项关联到一个Command对象，则执行这个Command 对象的execute方法
				eval("var command = " + menuItem.command); 
				command.execute();
			}else if (System.getValidStr(menuItem.path)!=""){//如果菜单项关联到一个url路径，则链接到这个路径
				open(menuItem.path, menu.target); 
			}
		}
		catch(e){
			System.processException(e);
		}
	}	
}

ButtonMenu = {
	show : function(menu, button){
		if (Menu.top_menuItem){
			Menu.hideMenu();
			return;
		}
	
		menu.showMode="button";
		Menu.showSubMenu(menu.topItem, "button", button, true);
		Menu.stored_frame=menu.topItem.subFrame;
		setTimeout("Menu.stored_frame.focus();", 0);
		Menu.top_menuItem=menu.topItem;
	}
}

ContextMenu = {
	show : function(){
		if(event.srcElement.contextMenu){
			Menu.showMenu(event.srcElement.contextMenu);						
			event.returnValue=false;
			return;			
		}
		if (typeof(Menu.array_menu)=="undefined") return;
		for(var i=0; i<Menu.array_menu.length; i++){
			var strHolder=Menu.array_menu[i].popupContainer;
			if (System.getValidStr(strHolder)!=""){
				var arrayHolder=strHolder.split(",");
				for(var j=0; j<arrayHolder.length; j++){
					if (arrayHolder[j]=="") continue;
					var needPopup;
					eval("needPopup=Document.isChild(event.srcElement,"+arrayHolder[j]+")");
					if (needPopup){
						event.srcElement.contextMenu = Menu.array_menu[i];
						Menu.showMenu(event.srcElement.contextMenu);						
						event.returnValue=false;
						return;
					}
				}
			}
		}
	}
}

//创建菜单对象
function makeMenu(menu){	  
	menu.topItem=createMenuItem(menu, null);//创建顶层菜单
	menu.staticMenu = System.isTrue( menu.staticMenu );
	Menu.array_menu[Menu.array_menu.length]=menu;
	
	initMenu(menu);	
}

function createMenu(id) {
	var menu=new Object();
	menu.id=id;
	menu.topItem=createMenuItem(menu, null);	
	return menu;
}

function createMenuItem(menu, parentItem) {
	var item=new Object();	
	item.items=new Array();
	item.menu=menu;
	if (parentItem){
		item.level=parentItem.level+1;
		item.zIndex=parentItem.zIndex+1;
		item.parentItem=parentItem;
		parentItem.items[parentItem.items.length]=item;
	}
	else{
		item.level=0;
		item.zIndex=10000;
	}
	return item;
}

function initMenu(menu){
	eval("var isXmlExist=(typeof(__"+menu.id+")==\"object\")");
	if( menu.staticMenu ){
		if (isXmlExist) {
			eval("var xmlIsland=__"+menu.id);
			root=xmlIsland.documentElement;
			if (!root) return;
			initMenuItems(menu, menu.topItem, root);
			//Document.xml_list[Document.xml_list.length]=xmlIsland;
		}
		menu.menuItems=null;
		Menu.array_menu[Menu.array_menu.length]=menu;
	}else{
		var ajaxCall = new NDAjaxCall(true);
		if (isXmlExist) {
			eval("var xmlIsland=__"+menu.id);
			var callBack = function (reply) {
				var xml = reply.getResult();
				xmlIsland.loadXML( xml ) ;
				var root = xmlIsland.documentElement;
				if( !root ) return;
				initMenuItems( menu, menu.topItem, root );
				//Document.xml_list[Document.xml_list.length] = xmlIsland;
				Document.fireUserEvent(Document.getElementEventName(menu, "onInit"), [menu]);
			}
			//调用服务，获取数据
			ajaxCall.remoteCall( menu.loadDataAction, menu.loadDataActionMethod, [menu.id], callBack,"vo","" );
		}
	}
}

//初始化所有的菜单项
function initMenuItems(menu, parentItem, node){		
	var itemNodes = node.childNodes;
	for (var i=0; i<itemNodes.length; i++) {
		var itemNode = itemNodes.item(i);			
		var newItem=createMenuItem(menu, parentItem);//创建一个菜单项，和它的上级节点关联
		newItem.name=itemNode.getAttribute("name");	
		newItem.label=itemNode.getAttribute("label");
		newItem.enabled=System.isTrue(itemNode.getAttribute("enabled"));
		newItem.visible=System.isTrue(itemNode.getAttribute("visible"));
		newItem.icon=itemNode.getAttribute("icon");
		newItem.path=itemNode.getAttribute("path");
		newItem.command=itemNode.getAttribute("command");
		newItem.tag=itemNode.getAttribute("tag");

		initMenuItems(menu, newItem, itemNode);
	}
}

