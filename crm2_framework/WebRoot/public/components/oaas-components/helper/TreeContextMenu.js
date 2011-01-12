    /*********************************************************************************
dayLogTreeContextClick
函数说明：右键点击故障单item时发生的事件，显示对该故障单所能执行的操作。
传入参数：
	无
输出参数：
	无
*********************************************************************************/
var DivH = 0;
function treeContextClick(ShowDiv,treeview)
{
       //初始化隐藏菜单

  	hideDivMenu(ShowDiv);
  	var srcEle = getSrc(event.srcElement);
	  try{
		  var x = event.clientX;
		  var y = event.clientY;
      if(ShowDiv!=''){
         with(document.all(ShowDiv).style){
			     position = "absolute";
			     if(event.clientX+150>document.body.clientWidth)
             left=event.clientX+document.body.scrollLeft-150;
           else
             left=event.clientX+document.body.scrollLeft;
           if(event.clientY+DivH > document.body.clientHeight)
             top=event.clientY+document.body.scrollTop-DivH;
           else
             top=event.clientY+document.body.scrollTop;
    			 visibility = "visible";
			     //top = y + document.body.scrollTop-5;
			     //left =  x + document.body.scrollLeft;
			     width=160;
		     }
		    window.event.cancelBubble = true;
		    event.returnValue= false;
      }
      return srcEle;
	}
	catch(e)
	{	if(e.message)
		ErrorHandle("错误信息","1","2","显示右键菜单出错",e.message);
		else
		ErrorHandle("错误信息","1","2","显示右键菜单出错",e);
		return;
	}
}

/*右键弹出菜单 ,有条件conditionStr为空判断*/
function contextMenuClick(showDiv,conditionStr){
  	contextMenuHide(showDiv);
	  try{
      if(conditionStr!=''){
         with(document.all(showDiv).style){
			     display="";
			     top = event.clientY + document.body.scrollTop-5;
			     left =  event.clientX + document.body.scrollLeft;
		     }
		     window.event.cancelBubble = true;
		     event.returnValue= false;
      }
	  }catch(e){
      if(e.message)
		    ErrorHandle("错误信息","1","2","显示右键菜单出错",e.message);
		  return;
	  }
}

/*********************************************************************************
函数名称：createPopMenu
函数说明：生成弹出式的菜单，初始时隐藏，
传入参数：
	无
输出参数：
	无
*********************************************************************************/
function createPopMenu(menuArray,divName,divWidth)
{
  if(divWidth==null||divWidth==''){
    divWidth=160;//默认宽度
  }
  var xml="<?xml version=\"1.0\" encoding=\"gb2312\" ?><Itemlist>";
  for(var i=0;i<menuArray.length;i++){
   	name=menuArray[i].name;
    cmd = menuArray[i].cmd;
    img = (menuArray[i].img)?menuArray[i].img:"icon.folder.gif";

    xml+="<Item img=\""+img+"\" name=\""+name+"\" cmd=\""+cmd+"\" />";
    DivH +=20;
  }
	xml+="</Itemlist>";
	xmldoc= new ActiveXObject("Microsoft.XMLDOM");//创建xml文档对象
	xmldoc.async=false;
	xmldoc.loadXML(xml);//载入菜单数据

	strShow = "<table border=\"0\" height=\"20\" cellpadding=\"0\" cellspacing=\"0\" width=\""+divWidth+"\" style='BORDER-BOTTOM:#000000 1px solid; BORDER-LEFT:#000000 1px solid; BORDER-RIGHT:#000000  1px solid; BORDER-TOP:#000000 1px solid'>";

	var parentMenuItems = xmldoc.selectNodes("//Itemlist/Item");
	var xmlElement = parentMenuItems.nextNode();
	while (xmlElement != null){
		strShow+="<tr style=\"cursor:hand;\"><td><table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=3 cellspacing=0 bgcolor=\"#ffffff\">"+
		         "<tr  onclick=\""+ xmlElement.getAttribute("cmd")+"\">"+
		         "<td class=\"ltdexit\" width=\"16\"><img border=\"0\" src=\"../htc/TreeListView/images/"+xmlElement.getAttribute("img")+"\" width=\"16\" height=\"16\" style=\"POSITION: relative\"></img></td>"+
		         "<td class=\"mtdexit\" onmouseover=this.className='cRowMouseOver' onmouseout=this.className='mtdexit'>"+xmlElement.getAttribute("name")+"</td>"+
		         "</tr></table></td><td></td></tr>";
		xmlElement = parentMenuItems.nextNode();
	}
	strShow+="</table>";
  //初始div
  var divElem = document.createElement("div");
	divElem.id=divName;
	divElem.className="innerDiv";
	divElem.innerHTML=strShow;
	with(divElem.style){
	  visibility = "hidden";
		position = "absolute";
		top = 100;
		left = 100;
		width=divWidth;
	}

	document.body.appendChild(divElem);
	menuArray=null;
}

function contextMenuCreate(menuArray,divName,divWidth,isMulti){
  if(divWidth==null||divWidth=='')divWidth=160;
  //
	var strShow = "<table border=\"0\" height=\"20\" cellpadding=\"0\" cellspacing=\"0\" width=\""+divWidth+"\" style='BORDER-BOTTOM:#000000 1px solid; BORDER-LEFT:#000000 1px solid; BORDER-RIGHT:#000000  1px solid; BORDER-TOP:#000000 1px solid'>";
  var arrLen= menuArray.length;
  if(arrLen%3!=0||arrLen==0){alert("数据格式错误，请检查");return;}
  for(var i=0;i<arrLen;i+=3){
  /*
		strShow +="<tr onclick=\""+ menuArray[i+1] +"\" style=\"cursor:hand;\">"+
		          "<td class=\"ltdexit\" width=\"16\"><img border=\"0\" src=\"../htc/TreeListView/images/"+ ((menuArray[i+2]!="")?menuArray[i+1]:"icon.folder.gif") +"\" width=\"16\" height=\"16\" style=\"POSITION: relative\"></td>"+
		          "<td class=\"mtdexit\" onmouseover=\"this.className='cRowMouseOver'\" onmouseout=\"this.className='mtdexit'\">"+ menuArray[i] +"</td>"+
		          "</tr>";
	 */
		strShow +="<tr style=\"cursor:hand;\"><td><table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=3 cellspacing=0 bgcolor=\"#ffffff\">"+
		         "<tr  onclick=\""+ menuArray[i+1] +"\">"+
		         "<td class=\"ltdexit\" width=\"16\"><img border=\"0\" src=\"../htc/TreeListView/images/"+ ((menuArray[i+2]!="")?menuArray[i+1]:"icon.folder.gif") +"\" width=\"16\" height=\"16\" style=\"POSITION: relative\"></img></td>"+
		         "<td class=\"mtdexit\" onmouseover=this.className='cRowMouseOver' onmouseout=this.className='mtdexit'>"+ menuArray[i] +"</td>"+
		         "</tr></table></td><td></td></tr>";
	}
	strShow += "</table>";
	//生成div
	var divElem = document.createElement("div");
	divElem.id=divName;
	divElem.className="innerDiv";
	divElem.innerHTML=strShow;
	with(divElem.style){
		display = "none";
		position = "absolute";
		top = 100;
		left = 100;
		width=divWidth;
	}
	document.body.appendChild(divElem);
	menuArray=null;
	//初始化body事件限制,如果时多个菜单，不初始化限制
	if(!isMulti){
    document.body.oncontextmenu=new Function("return false;");
    document.body.onclick = function(){contextMenuHide(divName);}
    document.body.onscroll = function(){contextMenuHide(divName);}
  }
}
/*
功能 分析菜单数组
返回 分析后的数组
*/
function sliceMenuArr(splitStr,originArr){
  var returnArr=new Array();
  var theArrValue=0;
  var tmpArr = splitStr.split(",");
  for(var i=0;i<tmpArr.length;i++){
    theArrValue=tmpArr[i];
    returnArr[i*3]  = originArr[theArrValue*3];
    returnArr[i*3+1]= originArr[theArrValue*3+2];
    returnArr[i*3+2]= originArr[theArrValue*3+2];
  }
  return returnArr;
}
/*替换内部的菜单选项*/
function contextMenuReplace(menuArray,divName){
  var arrLen= menuArray.length;
  if(arrLen%3!=0||arrLen==0){alert("数据格式错误，请检查");return;}
  var strShow = "<table border=\"0\" height=\"20\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style='BORDER-BOTTOM:#000000 1px solid; BORDER-LEFT:#000000 1px solid; BORDER-RIGHT:#000000  1px solid; BORDER-TOP:#000000 1px solid'>";
  for(var i=0;i<arrLen;i+=3){
		strShow +="<tr style=\"cursor:hand;\"><td><table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=3 cellspacing=0 bgcolor=\"#ffffff\">"+
		          "<tr  onclick=\""+ menuArray[i+1] +"\">"+
		          "<td class=\"ltdexit\" width=\"16\"><img border=\"0\" src=\"../htc/TreeListView/images/"+ ((menuArray[i+2]!="")?menuArray[i+1]:"icon.folder.gif") +"\" width=\"16\" height=\"16\" style=\"POSITION: relative\"></img></td>"+
		          "<td class=\"mtdexit\" onmouseover=this.className='cRowMouseOver' onmouseout=this.className='mtdexit'>"+ menuArray[i] +"</td>"+
		          "</tr></table></td><td></td></tr>";
	}
	strShow += "</table>";
	document.all(divName).innerHTML = strShow;
	strShow=null;
}
/*********************************************************************************
函数名称：hideDivMenu
函数说明：点击页面其它部位时，下拉菜单隐藏起来。
传入参数：
	无
输出参数：
	无
*********************************************************************************/
function hideDivMenu(divElem){
  if(eval(divElem)) document.all(divElem).style.visibility = "hidden";
}
function contextMenuHide(divElem){
  if(eval(divElem)) document.all(divElem).style.display = "none";
}
/* 获取表格object */
function getSrc(elm){
		if(elm==null) return null;
		if (elm.tagName=="TR" ||elm.tagName=="TBODY" || elm.tagName=="TABLE" || elm.tagName=="DIV")
			return elm;
		else
			return getSrc(elm.parentElement);
	}