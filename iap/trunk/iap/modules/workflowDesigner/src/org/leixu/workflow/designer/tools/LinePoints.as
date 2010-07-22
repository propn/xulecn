package org.leixu.workflow.designer.tools
{
	import flash.geom.Point;
	
	public class LinePoints
	{
		public function LinePoints()
		{
		}
		/*public static function getPoint(lineStart:Point,lineEnd:Point,moubleStart:Point,moubleEnd:Point):Point{
			var x:Number=getX(lineStart.x,lineEnd.x,moubleStart.x,moubleEnd.x,lineStart.y,lineEnd.y,moubleStart.y,moubleEnd.y);
			var y:Number=getX(lineStart.x,lineEnd.x,moubleStart.x,moubleEnd.x,lineStart.y,lineEnd.y,moubleStart.y,moubleEnd.y);
			return new Point(x,y);
			
		}
		private static function getX(x1:Number,x2:Number,x3:Number,x4:Number,y1:Number,y2:Number,y3:Number,y4:Number):Number{
			return  ((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4) * (x1 * y2 - x2 * y1))/((x3 - x4) * (y1 - y2) - (x1 - x2) * (y3 - y4));
		}
		private static function getY(x1:Number,x2:Number,x3:Number,x4:Number,y1:Number,y2:Number,y3:Number,y4:Number):Number{
			return   ((y1 - y2) * (x3 * y4 - x4 * y3) - (x1 * y2 - x2 * y1) * (y3 - y4))/((y1 - y2) * (x3 - x4) - (x1 - x2) * (y3 - y4));
		}*/
		public static function getPoint(lineStart:Point,lineEnd:Point,moubleStart:Point,moubleEnd:Point):Point{
			  var a1:Number=lineEnd.y-lineStart.y;  
			  var b1:Number=lineStart.x-lineEnd.x;  
			  var c1:Number=lineStart.x*lineEnd.y-lineEnd.x*lineStart.y;  
			  //L2:   a2x+b2y=c2  
			  var a2:Number=moubleEnd.y-moubleStart.y;  
			  var b2:Number=moubleStart.x-moubleEnd.x;  
			  var c2:Number=moubleStart.x*moubleEnd.y-moubleEnd.x*moubleStart.y;  
			   
			  var detab:Number=a1*b2-a2*b1;  
			  if(detab==0)  
			  {  
			  	var r:Number;  
			  	if(a2!=0){
			  	    r=a1/a2;  
			  	}else{
			  		r=b1/b2;
			  	}     
			   
			  }  
			  var x:Number=(c1*b2-c2*b1)/detab;  
			  var y:Number=(a1*c2-a2*c1)/detab;   
			  return new Point(x,y);
		}
	}
}	