var Button = {    
	refreshButtonColor : function(button){
		if (System.isTrue(button.getAttribute("down"))){
			button.className="button_down";
		}
		else{
			button.className="button";
		}
	},	
	setButtonDown : function(button, down){
		button.down=System.isTrue(down);
		Button.refreshButtonColor(button);
	},
  onmousedown : function(){
		var button=event.srcElement;
		Document.fireUserEvent(Document.getElementEventName(button, "onMouseDown"), [button]);
		var menu=button.getAttribute("menu");	
		if (typeof(menu)=="string" && menu!=""){
			eval("menu="+menu);
			button.menu=menu;
		}
		if (menu){
			ButtonMenu.show(menu, button);
		}
	},
  onmouseup : function(){
		var button=event.srcElement;
		if (System.isTrue(button.getAttribute("allowPushDown"))){
			var down=button.getAttribute("down");
		  Button.setButtonDown(button, !down);
		}
		Document.fireUserEvent(Document.getElementEventName(button, "onMouseUp"), [button]);
	},
  onmouseover : function(){
		var button=event.srcElement;
		if (button.disabled || button.down) return;
		button.className="button_over";
		Document.fireUserEvent(Document.getElementEventName(button, "onMouseEnter"), [button]);
	},
	onmouseout : function(){
		var button=event.srcElement;
		if (button.disabled) return;
		Button.refreshButtonColor(button);
		Document.fireUserEvent(Document.getElementEventName(button, "onMouseLeave"), [button]);
	},
  onclick : function(){
		var button=event.srcElement;
		if (button.command){
			eval("var command = " + button.command);
			command.execute();
		}
		else{
			Document.fireUserEvent(Document.getElementEventName(button, "onClick"), [button]);
		}
		//为了使得A标签在点击时失去连接效果，必须将返回值设为false.
		return false;
	}
}

function initButton(button) {
	
	if (!button.onmousedown) {
		button.onmousedown=Button.onmousedown;
	}
	if (!button.onmouseup) {
		button.onmouseup=Button.onmouseup;
	}
	if (!button.onmouseover) {
	 button.onmouseover=Button.onmouseover;
	}
	if (!button.onmouseout) {
		button.onmouseout=Button.onmouseout;
	}
	if (!button.onclick) {
		button.onclick=Button.onclick;
	}
  button.hideFocus = true; 
  //button.title = System.getDecodeStr(button.toolTip);
  Button.setButtonDown(button, button.getAttribute("down"));
}