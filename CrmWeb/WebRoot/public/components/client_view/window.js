function initXWin(element){
 //����ȫ�ֱ���
 element.x0=0,element.y0=0,element.x1=0,element.y1=0;
 element.offx=6,element.offy=6;
 element.moveable=false;
 element.hover='orange',element.normal='#336699';//color;
 element.document.index=isNaN(element.document.index)?10000:parseInt(element.document.index)+1;//z-index;
 //����id
 element.id=element.id?element.id:"xWin_"+(new Date()).getTime()+parseInt(Math.random()*100000);
 //��¼��ʾ���
 var tempDisplay=element.style.display;
 //�ı���ʾ���
 element.style.display="block";
 //���ô��ڱ���
 element.w=isNaN(element.width)?element.offsetWidth+10:parseInt(element.width);
 element.h=isNaN(element.height)?element.offsetHeight+30:parseInt(element.height);
 element.l=isNaN(element.left)?element.offsetLeft:parseInt(element.left);
 element.t=isNaN(element.top)?element.offsetTop:parseInt(element.top);
 element.z=element.document.index;
 element.title=element.title?element.title:"Untitled Window";
 element.content=element.innerHTML;
 //��մ���
 element.innerHTML="";
 //���ô�����ʽ
 with(element.style){
  position="absolute";
  width=element.w;
  height=element.h;
  left=element.l;
  top=element.t;
  zIndex=element.z;
  backgroundColor=element.normal;
  color=element.normal;
  cursor="default";
  border=element.normal+" 2px solid";
  padding=0;
  fontSize=12;
  overflow="hidden";
 }
 //���ô��ڷ���
 element.ShowHide=function(dis){
  var bdisplay = (dis==null)?((this.style.display=="none")?"":"none"):dis;
  this.style.display = bdisplay;
  this.nextSibling.style.display = bdisplay;
  if(bdisplay=="none"){
  	if(this.parentDivId!=""){
	  	var parentDiv = document.getElementById(this.parentDivId);
		if(parentDiv){	
			parentDiv.appendChild(this.oContent.childNodes[0]);
			var old_width = parentDiv.getAttribute("old_width");
			var old_height = parentDiv.getAttribute("old_height");
			parentDiv.style.width = old_width;
			parentDiv.style.height = old_height;
		}
	}	
  }
  else{
  	if(this.parentDivId!=""){
	  	var parentDiv = document.getElementById(this.parentDivId);
		if(parentDiv){	
			parentDiv.setAttribute("old_width", parentDiv.style.width);
			parentDiv.setAttribute("old_height", parentDiv.style.height);
			parentDiv.style.width = "0";
			parentDiv.style.height = "0";
		}
	}  	
  }	
  if(this.tabsetId!=""){
	var tabset = $(this.tabsetId);
	tabset.setAttribute("isInWindow", (bdisplay!="none"));
  }
 }
 //������갴���¼�
 element.onmousedown=function(){
  if(this.style.zIndex!=this.document.index){
   this.document.index+=2;
   var idx = this.document.index;
   this.style.zIndex=idx;
   this.nextSibling.style.zIndex=idx-1;
  }
 }
 //���ô��ڱ���
 element.oTitle=element.document.createElement("div");
 //���ô��ڱ�����ʽ
 with(element.oTitle.style){
  backgroundColor=element.normal;
  color="#FFFFFF";
  width=element.w-4;
  height=20;
 }
 //���ڱ�����갴���¼�
 element.oTitle.onmousedown=function(){
  if(event.button==1){
   //����������;
   this.setCapture();
   //�������;
   var win = this.parentNode;
   var sha = win.nextSibling;
   //��¼���Ͳ�λ��;
   element.x0 = event.clientX;
   element.y0 = event.clientY;
   element.x1 = parseInt(win.style.left);
   element.y1 = parseInt(win.style.top);
   //��¼��ɫ;
   element.normal = this.style.backgroundColor;
   //�ı���;
   this.style.backgroundColor = element.hover;
   win.style.borderColor = element.hover;
   this.nextSibling.style.color = element.hover;
   sha.style.left = element.x1 + element.offx;
   sha.style.top  = element.y1 + element.offy;
   element.moveable = true;
  }
 }
 //���ڱ�������ƶ��¼�
 element.oTitle.onmousemove=function(){
  if(element.moveable){
   var win = this.parentNode;
   var sha = win.nextSibling;
   win.style.left = element.x1 + event.clientX - element.x0;
   win.style.top  = element.y1 + event.clientY - element.y0;
   sha.style.left = parseInt(win.style.left) + element.offx;
   sha.style.top  = parseInt(win.style.top) + element.offy;
  }
 }
 //���ڱ�����굯���¼�
 element.oTitle.onmouseup=function(){
  if(element.moveable){
   var win = this.parentNode;
   var sha = win.nextSibling;
   var msg = this.nextSibling;
   win.style.borderColor     = element.normal;
   this.style.backgroundColor = element.normal;
   msg.style.color           = element.normal;
   sha.style.left = this.parentNode.style.left;
   sha.style.top  = this.parentNode.style.top;
   this.releaseCapture();
   element.moveable = false;
  }
 }
 
 //���ô��ڱ�������
 element.oTitleContent=element.document.createElement("span");
 with(element.oTitleContent.style){
  width=element.w-4-12*2;
  height=20;
  paddingLeft=3;
  paddingTop=2;
  margin=0;
  wordBreak="keep-all";
  textOverflow="ellipsis";
  overflow="hidden";
 }
 element.oTitleContent.innerText=element.title;
 //���ô��ڱ�����С����ť
 element.oTitleMButton=element.document.createElement("span");
 with(element.oTitleMButton.style){
  width=12;
  height=20;
  margin=0;
  fontFamily="webdings";
 }
 element.oTitleMButton.innerText=0;
 //���ô��ڱ�����С����ť����¼� & ���ô��ڱ���˫���¼�
 element.oTitleMButton.onclick=element.oTitle.ondblclick=function(){
  obj=this.parentNode.nodeName.toLowerCase()=="div"?this:this.childNodes[1];
  var win = obj.parentNode.parentNode;
  var sha = win.nextSibling;
  var tit = obj.parentNode;
  var msg = tit.nextSibling;
  var flg = msg.style.display=="none";
  if(flg){
   win.style.height  = parseInt(msg.style.height) + parseInt(tit.style.height) + 2*2;
   sha.style.height  = win.style.height;
   msg.style.display = "block";
   obj.innerHTML = "0";
  }else{
   win.style.height  = parseInt(tit.style.height) + 2*2;
   sha.style.height  = win.style.height;
   obj.innerHTML = "2";
   msg.style.display = "none";
  }
 }
 //���ô��ڱ���رհ�ť
 element.oTitleCButton=element.document.createElement("span");
 with(element.oTitleCButton.style){
  width=12;
  height=20;
  margin=0;
  fontFamily="webdings";
 }
 element.oTitleCButton.innerText="r";
 element.oTitleCButton.onclick=function(){this.parentNode.parentNode.ShowHide();}
 //���ô�������
 element.oContent=element.document.createElement("div");
 with(element.oContent.style){
  width="100%";
  height=element.h-20-4;
  margin=0;
  backgroundColor="#EFF7FF";
  padding=3;
  wordBreak="break-all";
  overflow="auto";
 }
 element.oContent.innerHTML=element.content;
 //���ô�����Ӱ
 element.oShadow=element.document.createElement("div");
 with(element.oShadow.style){
  position="absolute";
  width=element.w;
  height=element.h;
  left=element.l;
  top=element.t;
  zIndex=element.z-1;
  backgroundColor="#000000";
  filter="alpha(opacity=40);";
 }
 element.oTitle.appendChild(element.oTitleContent);
 element.oTitle.appendChild(element.oTitleMButton);
 element.oTitle.appendChild(element.oTitleCButton);
 element.appendChild(element.oTitle);
 element.appendChild(element.oContent);
 element.insertAdjacentElement("AfterEnd",element.oShadow);
 
 //�ָ���ʾ���
 element.style.display=tempDisplay;
}