/*参数说明
option: {width:Number, items:Array, onShow:Function, rule:JSON}
成员语法(三种形式)	-- para.items
-> {text:String, icon:String, type:String, alias:String, width:Number, items:Array}		--	菜单组
-> {text:String, icon:String, type:String, alias:String, action:Function }				--	菜单项
-> {type:String}																		--	菜单分隔线

右键菜单创建顺序
1、创建菜单对象
 var testmenu = new ContextMenu();
2、创建菜单
testmenu.createMenu(150,applyrule,BeforeContextMenu,rootId,callType); //rootId用于区分不同DIV不同菜单项callType表示需要菜单触发的事件 
如 左键点击：click 右键点击 contextmenu
3、增加菜单项
testmenu.addItemObj("第一项","images/icons/ico1.gif","","1-1",menuAction,null);
4、绑定DIV
testmenu.bind(["target","target2","target3"] );
5、调用菜单
testmenu.call();  

*/ 
/*右键菜单对象构造函数*/
function ContextMenu(){ 
	//菜单选项
	this.option = new Object();
	//菜单项
	this.items = new Array();  
	//绑定DIV
	this.divs = new Array();
	//DIV串
	this.divStr = "";
} 
/*创建一个菜单
width 菜单宽度 int
onShow 菜单显示时需要应用的规则 比如 某些菜单项的禁用 function
onContextMenu 菜单弹出前的校验规则  function
*/
ContextMenu.prototype.createMenu =function(width,onShow,onContextMenu,rootId,callType){
	 this.option.width = width;
	 this.option.onShow = onShow;
	 this.option.onContextMenu = onContextMenu;
	 this.option.rootId = rootId;
	 this.option.callType = callType;
}

/*增加一个菜单对象 
{text: String, icon: String, alias: String, type: "group"|"item"|"splitLine", width:int, items:Array,action:Funtion}
其中： 
text:String 菜单项的文字说明 。 
icon: String 图标的Src地址，如果没有图标，如果item不需要图标,请设置成none.gif(在images/icons/中可以找到)。 
alias:String 唯一标识菜单项。 
type:"group"|"item"|"splitLine" 分别为组，项，分割线，当选择是"splitLine"则其他设置项无需设置。 
width:int 当且仅当type="group"时有效，设置新组容器的宽度。 			
*/
ContextMenu.prototype.addItemObj = function(text,icon,type,alias,action,items){
	  var item = new Object();
	  if(text){
	 	 item.text = text;
	  }
	  if(icon){
	 	 item.icon = icon;
	  }
	  if(type){
	 	 item.type = type;
	  }
	  if(alias){
	 	 item.alias = alias;
	  }
	  if(action){
	 	 item.action = action;
	  }
	  if(items){
	 	 item.items = items;
	  } 
	  
	  this.items[this.items.length] = item; 
}
/*传入组装好的列表增加菜单项*/
ContextMenu.prototype.addItems = function(items){
	  this.items = items; 
}
/*启动菜单（针对绑定DIV的情况）*/
ContextMenu.prototype.call = function(){
	
	/* if(!this.divs || this.divs.length==0){
	 	alert("请先绑定执行对象"); 
	 }*/ 
	 for(var i = 0;i<this.divs.length;i++){
	 	var div_id = this.divs[i]; 
	 	if(i<this.divs.length-1){
			this.divStr += "#"+div_id+",";
		}else{
		 	this.divStr += "#"+div_id;
		} 
	 }  
	 this.option.items = this.items;
	 jQ(this.divStr).contextmenu(this.option);
} 

/*绑定DIV*/
ContextMenu.prototype.bind = function(divs){
	 this.divs = divs;
}

