/**
 * 作者：雷啸
 * 联系方式：
 * 	QQ：466253283
 * 	Email：leixiaodmm@tom.com
 * 	电话：13888586664
 * 注释：
 * 	该类主要提供线的功能 包括 连接线 折线等功能
 * 版权
 *  如果要转载 或用于商业使用 请先征得作者同意 谢谢合作
 **/
package org.leixu.workflow.designer
{
	import com.roguedevelopment.objecthandles.ObjectHandleEvent;
	import com.roguedevelopment.objecthandles.ObjectHandles;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	
	import mx.core.UIComponent;

	public class Line extends UIComponent
	{
		private static var CurrLine:Line;
		private var _startX:Number;
		private var _startY:Number;
		private var _endX:Number;
		private var _endY:Number;
		private var _turning:Array; 
		private var _startNode:ObjectHandles;
		private var _endNode:ObjectHandles;
		public function get StartNode():ObjectHandles{
			return _startNode;
		}
		public function set StartNode(value:ObjectHandles):void{
			_startNode=value;
		}
		//防眼花线~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~飘过~~~~~~~~~~~~~~~
		public function get EndNode():ObjectHandles{
			return _endNode;
		}
		public function set EndNode(value:ObjectHandles):void{
			_endNode=value;
		}
		//防眼花线~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~飘过~~~~~~~~~~~~~~~
		public function get StartX():Number{
			return _startX;
		}
		public function set StartX(value:Number):void{
			_startX=value;
			draw();
		}
		//防眼花线~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~飘过~~~~~~~~~~~~~~~
		public function get StartY():Number{
			return _startY;
		}
		public function set StartY(value:Number):void{
			_startY=value;
			draw();
		}
		//防眼花线~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~飘过~~~~~~~~~~~~~~~
		public function get EndX():Number{
			return _endX;
		}
		public function set EndX(value:Number):void{
			if(isAutoDirection){
				if(StartX>EndX){
					_endX=value+10;
				}else{
					_endX=value-10;
				}
			}else{
				_endX=value;
			}
			draw();
		}
		//防眼花线~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~飘过~~~~~~~~~~~~~~~
		public function get EndY():Number{
			return _endY;
		}
		public function set EndY(value:Number):void{
			if(isAutoDirection){
				if(StartY>EndY){
					_endY=value+10;
				}else{
					_endY=value-10;
				}
			}else{
				_endY=value;
			}
			draw();
		}
		//防眼花线~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~飘过~~~~~~~~~~~~~~~
		public function draw():void{
			this.graphics.clear();
			this.graphics.lineStyle(2,0x000000,3);
			this.graphics.moveTo(StartX,StartY);
			for(var i:int=0;i<_turning.length;i++){
				var ohPoly:ObjectHandles = _turning[i];
				this.graphics.lineTo(ohPoly.x,ohPoly.y);
				this.graphics.moveTo(ohPoly.x,ohPoly.y);
			}
			this.graphics.lineTo(EndX,EndY);
			//if(isAutoDirection){
				this.graphics.beginFill(0xf00f00);
				this.graphics.drawEllipse(EndX-5,EndY-5,10,10);
				
				this.graphics.endFill();
			//}
		}
		private var isAutoDirection:Boolean=true;
		public function DrDirection(e:Rectangle):void{
			isAutoDirection=false;
		}
		public function Line()
		{
			super();
			this._turning = new Array();
			this.addEventListener(MouseEvent.MOUSE_DOWN,MyClickListener);
			this.doubleClickEnabled=true;
			this.addEventListener(MouseEvent.DOUBLE_CLICK,function(e:Event):void{
				
			});
			
		}
		public function MyClickListener(e:MouseEvent):void{
			if(e.shiftKey){
				var ohPoly:ObjectHandles = new ObjectHandles();
				ohPoly.allowHResize=false;
				ohPoly.allowVResize=false;
				ohPoly.width=6;
				ohPoly.height=6;
				ohPoly.x=e.localX-3;
				ohPoly.y=e.localY-3;
				ohPoly.addEventListener(ObjectHandleEvent.OBJECT_MOVING_EVENT,poMove);
				ohPoly.setStyle("backgroundColor","blue");
				_turning.push(ohPoly);
				this.addChild(ohPoly);
			}
		}
		public function poMove(e:ObjectHandleEvent):void{
			this.draw();
		}
	}
}