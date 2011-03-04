/*����˵��
option: {width:Number, items:Array, onShow:Function, rule:JSON}
��Ա�﷨(������ʽ)	-- para.items
-> {text:String, icon:String, type:String, alias:String, width:Number, items:Array}		--	�˵���
-> {text:String, icon:String, type:String, alias:String, action:Function }				--	�˵���
-> {type:String}																		--	�˵��ָ���

�Ҽ��˵�����˳��
1�������˵�����
 var testmenu = new ContextMenu();
2�������˵�
testmenu.createMenu(150,applyrule,BeforeContextMenu,rootId,callType); //rootId�������ֲ�ͬDIV��ͬ�˵���callType��ʾ��Ҫ�˵��������¼� 
�� ��������click �Ҽ���� contextmenu
3�����Ӳ˵���
testmenu.addItemObj("��һ��","images/icons/ico1.gif","","1-1",menuAction,null);
4����DIV
testmenu.bind(["target","target2","target3"] );
5�����ò˵�
testmenu.call();  

*/ 
/*�Ҽ��˵������캯��*/
function ContextMenu(){ 
	//�˵�ѡ��
	this.option = new Object();
	//�˵���
	this.items = new Array();  
	//��DIV
	this.divs = new Array();
	//DIV��
	this.divStr = "";
} 
/*����һ���˵�
width �˵���� int
onShow �˵���ʾʱ��ҪӦ�õĹ��� ���� ĳЩ�˵���Ľ��� function
onContextMenu �˵�����ǰ��У�����  function
*/
ContextMenu.prototype.createMenu =function(width,onShow,onContextMenu,rootId,callType){
	 this.option.width = width;
	 this.option.onShow = onShow;
	 this.option.onContextMenu = onContextMenu;
	 this.option.rootId = rootId;
	 this.option.callType = callType;
}

/*����һ���˵����� 
{text: String, icon: String, alias: String, type: "group"|"item"|"splitLine", width:int, items:Array,action:Funtion}
���У� 
text:String �˵��������˵�� �� 
icon: String ͼ���Src��ַ�����û��ͼ�꣬���item����Ҫͼ��,�����ó�none.gif(��images/icons/�п����ҵ�)�� 
alias:String Ψһ��ʶ�˵�� 
type:"group"|"item"|"splitLine" �ֱ�Ϊ�飬��ָ��ߣ���ѡ����"splitLine"�������������������á� 
width:int ���ҽ���type="group"ʱ��Ч���������������Ŀ�ȡ� 			
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
/*������װ�õ��б����Ӳ˵���*/
ContextMenu.prototype.addItems = function(items){
	  this.items = items; 
}
/*�����˵�����԰�DIV�������*/
ContextMenu.prototype.call = function(){
	
	/* if(!this.divs || this.divs.length==0){
	 	alert("���Ȱ�ִ�ж���"); 
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

/*��DIV*/
ContextMenu.prototype.bind = function(divs){
	 this.divs = divs;
}

