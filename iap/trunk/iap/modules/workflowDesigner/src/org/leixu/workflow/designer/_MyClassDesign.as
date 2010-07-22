/**
 * 作者：雷啸
 * 联系方式：
 * 	QQ：466253283
 * 	Email：leixiaodmm@tom.com
 * 	电话：13888586664
 * 注释：
 * 	组要提供界面功能操作函数
 * 版权
 *  如果要转载 或用于商业使用 请先征得作者同意 谢谢合作
 **/
package org.leixu.workflow.designer
{
	import org.leixu.workflow.designer.EclipseStyle.ESNode;
	import com.roguedevelopment.objecthandles.ObjectHandleEvent;
	import com.roguedevelopment.objecthandles.ObjectHandles;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	
	import mx.containers.TitleWindow;
	import mx.controls.Button;
	import mx.controls.TextArea;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.utils.ObjectUtil;
	
	public class _MyClassDesign
	{
		//对应的视图
		private var _this:MyWorkFlow;
		public function _MyClassDesign(e:MyWorkFlow)
		{
			_this=e;
			e.addEventListener(MouseEvent.MOUSE_MOVE,this_MouseMove);
		}
		public function Oh_MouseDown(e:MouseEvent):void{
			//调整Z轴
			_this.MainPanel.setChildIndex(e.currentTarget as UIComponent,_this.MainPanel.numChildren-1);
			
		}
		public function createNode(e:String):MyCanvas{
			var MyNode:MyCanvas;
			if(e=="StepNode"){
			 	MyNode = new StepNode();
			}else if(e=="StopNode"){
				MyNode = new StopNode();
			}else if(e=="StartNode"){
				MyNode = new StartNode();
			}else if(e=="ServerNode"){
				MyNode = new ServerNode();
			}else if(e=="BrowserNode"){
				MyNode = new BrowserNode();
			}else if(e=="TestNode"){
				MyNode= new ESNode();
			}
			MyNode.percentHeight=100;
			MyNode.percentWidth=100;
			return MyNode;
		}
		public function DrModel(e:MouseEvent):void{
			if(DrClass==null){
				return ;
			}
			DrClass.x=e.stageX-_this.MainPanel.x;
			DrClass.y=e.stageY-_this.MainPanel.y;
			_this.MainPanel.addChild(DrClass);
			DrClass=null;
		}
		public function createShell(e:MyCanvas):ObjectHandles{
			var oh:ObjectHandles = new ObjectHandles();
			oh.addEventListener(MouseEvent.MOUSE_DOWN,Oh_MouseDown);
			oh.addEventListener(MouseEvent.CLICK,drLine);
			oh.addEventListener(ObjectHandleEvent.OBJECT_MOVING_EVENT,OH_MouseMove);
			oh.addEventListener(ObjectHandleEvent.OBJECT_MOVED_EVENT,OH_MouseMoved);
			oh.addEventListener(KeyboardEvent.KEY_DOWN,OH_KeyDown);
			oh.doubleClickEnabled=true;
			oh.addEventListener(MouseEvent.DOUBLE_CLICK,OH_DBClick);
			oh.addChild(e);
			return oh;
		}
		private function OH_KeyDown(e:KeyboardEvent):void{
			((e.currentTarget as ObjectHandles).getChildAt(0) as MyCanvas).setKeyDown(e);
		}
		private function OH_DBClick(e:MouseEvent):void{
			((e.currentTarget as ObjectHandles).getChildAt(0) as MyCanvas).setDBClick(e);
		}
		private function OH_MouseMove(e:ObjectHandleEvent):void{
			DrLinePoint(e);
		}
		private function OH_MouseMoved(e:ObjectHandleEvent):void{
			DrLinePoint(e);
		}
		private function DrLinePoint(e:ObjectHandleEvent):void{
			var oh:ObjectHandles=null;
			var node:MyCanvas=null;
			try{
				oh=e.currentTarget as ObjectHandles;
				node=oh.getChildAt(0) as MyCanvas;
			}catch(e:Error){
			}
			if(node != null){
				var _out:Array=node.getOutput();
				var _in:Array=node.getInput();
				for(var i:int=0;i<_out.length;i++){
					var tline:Line=_out[i] as Line;
					tline.StartX=oh.x+oh.width/2;
					tline.StartY=oh.y+oh.height/2;
					tline.StartNode=oh;

				}
				for(var i:int=0;i<_in.length;i++){
					var tline:Line=_in[i] as Line;
					tline.EndX=oh.x+oh.width/2;
					tline.EndY=oh.y+oh.height/2;
					var r:Rectangle = new Rectangle(oh.x,oh.y,oh.width,oh.height);
					tline.EndNode=oh;
					tline.DrDirection(r);

				}
			}
		}
		//要显示的当前步骤模型
		private var DrClass:ObjectHandles=null;
		public function NewStepNode(e:Event):void{
			DrClass=createShell(createNode("StepNode"));
			DrClass.width=200;
			DrClass.height=140;
		}
		public function NewStopNode(e:Event):void{
			DrClass=createShell(createNode("StopNode"));
			DrClass.width=185;
			DrClass.height=105;
		}
		public function NewStartNode(e:Event):void{
			DrClass=createShell(createNode("StartNode"));
			DrClass.width=185;
			DrClass.height=105;
		}
		public function NewServerNode(e:Event):void{
			DrClass=createShell(createNode("ServerNode"));
			DrClass.width=260;
			DrClass.height=150;
		}
		public function NewBrowserNode(e:Event):void{
			DrClass=createShell(createNode("BrowserNode"));
			DrClass.width=250;
			DrClass.height=130;
		}
		public function TestNode(e:Event):void{
			DrClass=createShell(createNode("TestNode"));
			DrClass.width=180;
			DrClass.height=84;
		}
		//要绘制的连接线
		private var DrLine:Line=null;
		
		public function NewLine(e:Event):void{
			DrLine = new Line();
			DrLine.addEventListener(KeyboardEvent.KEY_DOWN,function key_down(e:KeyboardEvent){
				if(e.keyCode==46){
					(e.currentTarget as UIComponent).parent.removeChild(e.currentTarget as DisplayObject);
				}
			});
		}
		private var curNode:ObjectHandles=null;
		public function drLine(e:Event):void{
			if(DrLine==null){
				return ;
			}
			var Node:MyCanvas=null;
			try{
				Node=(e.currentTarget as ObjectHandles).getChildAt(0) as MyCanvas;
			}catch(e:Error){
				
			}
			if(curNode==null){
				DrLine.StartX=(e.currentTarget as ObjectHandles).width/2+(e.currentTarget as ObjectHandles).x;
				DrLine.StartY=(e.currentTarget as ObjectHandles).height/2+(e.currentTarget as ObjectHandles).y;
				DrLine.EndX=(e.currentTarget as ObjectHandles).width/2+(e.currentTarget as ObjectHandles).x;
				DrLine.EndY=(e.currentTarget as ObjectHandles).height/2+(e.currentTarget as ObjectHandles).y;
				
				
				_this.MainPanel.addChild(DrLine);
				curNode=e.currentTarget as ObjectHandles;
				if(Node!=null){
					Node.AddOutput(DrLine);
					DrLine.StartNode=(e.currentTarget as ObjectHandles);
				}
			}else{
				if(Node!=null){
					Node.AddInput(DrLine);
					DrLine.EndNode=(e.currentTarget as ObjectHandles);
				}
				curNode=null;
				DrLine=null;
			}
		}
		public function this_MouseMove(e:MouseEvent):void{
			if(DrLine==null){
				return ;
			}
			DrLine.EndX=e.stageX-_this.MainPanel.x+3;
			DrLine.EndY=e.stageY-_this.MainPanel.y+3;
		}
		//var b:ByteArray =null;
		//var a:MyMainPanel=null;
		public function Save(e:Event):void{
			var s:Array=_this.MainPanel.getChildren();
			var str:String="";
			for(var i:int=0;i<s.length;i++){
				if(s[i] instanceof ObjectHandles){
					var CurSaveOh:ObjectHandles=s[i] as ObjectHandles;
					var CurMy:MyCanvas=CurSaveOh.getChildAt(0) as MyCanvas;
					var text:String=CurMy.NodeTitle.text;
					var x:String=CurSaveOh.x.toString();
					var y:String=CurSaveOh.y.toString();
					var w:String=CurSaveOh.width.toString();
					var h:String=CurSaveOh.height.toString();
					var outline:Array= CurMy.getOutput();			
					var NodeStr:String="<node label='"+text+"' x='"+x+"' y='"+y+"' width='"+w+"' height='"+h+"' type='"+ObjectUtil.getClassInfo(CurMy).name+"' >\n";
					for(var j:int=0;j<outline.length;j++){
							var ohline:Line=outline[j] as Line;
							var Nm:MyCanvas=ohline.EndNode.getChildAt(0) as MyCanvas
							NodeStr+="<line for='"+Nm.NodeTitle.text+"'></line>\n";
					}
					NodeStr+="</node>\n";
					str+=NodeStr;
				}
			}
			var red:TitleWindow = new TitleWindow();
			red.width=500;
			red.height=350;
			red.showCloseButton=true;
			var textaren:TextArea = new TextArea();
			red.addEventListener(CloseEvent.CLOSE,function(e:Event):void{
				red.parent.removeChild(red);
			});
			red.addEventListener(MouseEvent.MOUSE_DOWN,function(e:MouseEvent):void{
				red.startDrag(false);
			});
			red.addEventListener(MouseEvent.MOUSE_UP,function(e:MouseEvent):void{
				red.stopDrag();
			});
			textaren.text="<LxWorkFlow>\n"+str+"</LxWorkFlow>";
			textaren.percentHeight=100;
			textaren.percentWidth=100;
			red.addChild(textaren);
			_this.addChild(red);
			//Alert.show(str);
		}
		public function getNodeByName(e:String):ObjectHandles{
			var s:Array=_this.MainPanel.getChildren();
			for(var i:int=0;i<s.length;i++){
				if(s[i] instanceof ObjectHandles){
					var CurSaveOh:ObjectHandles=s[i] as ObjectHandles;
					var CurMy:MyCanvas=CurSaveOh.getChildAt(0) as MyCanvas;
					_this.MainPanel.setChildIndex(CurSaveOh,_this.MainPanel.numChildren-1);
					if(CurMy.NodeTitle.text==e){
						return CurSaveOh;
					}
				}
			}
			return null;
		}
		public function Load(e:Event):void{
			var red:TitleWindow = new TitleWindow();
			var btn:Button = new Button();
			red.width=500;
			red.height=350;
			red.showCloseButton=true;
			var textaren:TextArea = new TextArea();
			red.addEventListener(CloseEvent.CLOSE,function(e:Event):void{
				red.parent.removeChild(red);
			});
			red.addEventListener(MouseEvent.MOUSE_DOWN,function(e:MouseEvent):void{
				red.startDrag(false);
			});
			red.addEventListener(MouseEvent.MOUSE_UP,function(e:MouseEvent):void{
				red.stopDrag();
			});
			textaren.percentHeight=100;
			textaren.percentWidth=100;
			red.addChild(textaren);
			red.addChild(btn);
			btn.addEventListener(MouseEvent.CLICK,function(e:Event):void{
				var flexXML:XML=new XML(textaren.text);
				var nodes:XMLList=flexXML.children();
				for(var i:int=0;i<nodes.length();i++){
					var CurXML:XML=nodes[i] as XML;
					var label:String=CurXML.attribute("label").toXMLString();
					var x:String=CurXML.attribute("x").toXMLString();
					var y:String=CurXML.attribute("y").toXMLString();
					var w:String=CurXML.attribute("width").toXMLString();
					var h:String=CurXML.attribute("height").toXMLString();
					var type:String=CurXML.attribute("type").toXMLString().split("::")[1];
					var NodeCanvas:MyCanvas=createNode(type);
					var oclass:ObjectHandles=createShell(NodeCanvas);
					oclass.width=Number(w);
					oclass.height=Number(h);
					oclass.x=Number(x);
					oclass.y=Number(y);
					_this.MainPanel.addChild(oclass);
					NodeCanvas.NodeTitle.text=label;

				}
				var snane:String="";
				for(var i:int=0;i<nodes.length();i++){
					var CurXML:XML=nodes[i] as XML;
					
					var Lines:XMLList=CurXML.child("line");
					for(var j:int=0;j<Lines.length();j++){
						var lineXml:XML=Lines[j] as XML;
						var line:Line = new Line();
						var label:String=CurXML.attribute("label").toXMLString();
						var linefor:String=lineXml.attribute("for").toXMLString();
						var ForNode:ObjectHandles = getNodeByName(label);
						snane=label;
						var ToNode:ObjectHandles=getNodeByName(linefor);
						line.StartX=ForNode.width/2+ForNode.x;
						line.StartY=ForNode.height/2+ForNode.y;
						line.EndX=ToNode.width/2+ToNode.x;
						line.EndY=ToNode.height/2+ToNode.y;
						line.StartNode=ForNode;
						line.EndNode=ToNode;
						(ForNode.getChildAt(0) as MyCanvas).AddOutput(line);
						(ToNode.getChildAt(0) as MyCanvas).AddInput(line);
						_this.MainPanel.addChild(line);
						//Alert.show(ForNode.NodeTitle.text);
					}
				}
				getNodeByName(snane);
				
			});
			
			_this.addChild(red);
		}

	}
}