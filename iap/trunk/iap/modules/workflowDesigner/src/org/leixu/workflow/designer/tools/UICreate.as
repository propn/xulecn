package org.leixu.workflow.designer.tools
{
	import com.roguedevelopment.objecthandles.ObjectHandles;
	
	import flash.events.FocusEvent;
	import flash.events.KeyboardEvent;
	
	import mx.controls.Alert;
	import mx.controls.Label;
	import mx.core.UIComponent;
	
	public class UICreate
	{
		public function UICreate(e:UIComponent,oh:ObjectHandles)
		{
			Context=e;
			this.oh=oh;
		}
		private var Context:UIComponent;
		private var oh:ObjectHandles;
		public function createText(value:UIComponent,mouble:UIComponent,ret:Label=null,key_down:Function=null,focus_out:Function=null):UIComponent{
			
				var titleInput:UIComponent = value;
					titleInput.x=mouble.x+oh.x;
					titleInput.y=mouble.y+oh.y;
					titleInput.setStyle("fontSize","12");
					titleInput.width=mouble.width;
					titleInput.height=mouble.height;
					//this.parent.parent.addChild(titleInput);
					
					Context.addChild(titleInput);
					
					titleInput.setFocus();
					if(key_down==null){
					titleInput.addEventListener(KeyboardEvent.KEY_DOWN,function keyDown(e:KeyboardEvent):void{
						if(e.keyCode==13){
							var txt:UIComponent=e.currentTarget as UIComponent;
							if(ret!=null){
								ret.text=txt["text"];
							}
							txt.parent.removeChild(txt);
						}
					});
					}else{
						titleInput.addEventListener(KeyboardEvent.KEY_DOWN,key_down);
					}
					if(focus_out==null){
						titleInput.addEventListener(FocusEvent.FOCUS_OUT,function out(e:FocusEvent):void{
							var txt:UIComponent=e.currentTarget as UIComponent;
							if(ret!=null){
								ret.text=txt["text"];
							}
							txt.parent.removeChild(txt);
						});
					}else{
						titleInput.addEventListener(FocusEvent.FOCUS_OUT,focus_out);
					}
					return titleInput;
			}
	}
}