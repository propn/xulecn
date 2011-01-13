
var BOCLASS = "_BUFFALO_OBJECT_CLASS_";

function formSubmitCall(form, datasetInfo, async, callBack){
	
	if(!validForm(form)) return false;	
	
	var action = form.action;
	var idx = action.indexOf(".");	
	var serviceId = action.substring(0,idx);
	var method = action.substring(idx+1,action.length);
	
	var bean = null;	
	if(!(typeof(datasetInfo) == "undefined")){
		bean = extractArrayFromDataset(datasetInfo, form.beanType);
	}else{
		bean = formToBean(form, form.beanType);
	}
	
	var result = null;
	if(!callBack){
		var callBack = function (reply) {		
			result = reply.getResult();	
			//alert( "formSubmitCall result="+ result);
			return result;
	    }
    }
    var ajaxCall = new NDAjaxCall(async);
    ajaxCall.remoteCall(serviceId, method, [bean], callBack);
    
}
//自动适应提交一条或者多条记录。
function extractArrayFromDataset(dataset, beanType){
   var arr=[];
   var record = dataset.getFirstRecord();
   while (record){ 
         var bean = {};         
         for(var i=0; i<dataset.fields.fieldCount; i++)
         {	
			//if(dataset.fields[i].name.indexOf("_")!=-1) continue;
			//if(beanType)bean[BOCLASS]=beanType;	
			//if("string"==dataset.fields[i].dataType){
			//	bean[dataset.fields[i].name]=""+record.getValue(dataset.fields[i].name); 
			//}else{
			//	bean[dataset.fields[i].name]=record.getValue(dataset.fields[i].name); 
			//}
            if (beanType)
				bean[BOCLASS]=beanType;
			 else
			    bean[BOCLASS] = "java.util.Map";	
			 bean[dataset.fields[i].name]=""+record.getValue(dataset.fields[i].name); 

         }
         record = record.getNextRecord();      
         arr[arr.length]=bean;         
   }
   return arr;
}

//当前记录提交方法（暂不使用该方法）
function extractBeanFromDataset(datasetInfo, beanType){
	
	var bean = {};
	if (beanType) {
	 	bean[BOCLASS] = beanType; 
	} else{
		bean[BOCLASS] = "java.util.Map";
	}
		
	for(var i=0; i<datasetInfo.fields.fieldCount; i++){	
		//if("string"==datasetInfo.fields[i].dataType){
			bean[datasetInfo.fields[i].name] = ""+datasetInfo.record.getValue(datasetInfo.fields[i].name);
		//}else{
		//	bean[datasetInfo.fields[i].name] = datasetInfo.record.getValue(datasetInfo.fields[i].name);
		//}
		
	}
	
	return bean;
}

//当前记录提交方法（暂不使用该方法）
function extractBeanFromRecord(datasetInfo, record, beanType){
	
	var bean = {};
	if (beanType) {
	 	bean[BOCLASS] = beanType; 
	} else{
		bean[BOCLASS] = "java.util.Map";
	}
		
	for(var i=0; i<datasetInfo.fields.fieldCount; i++){	
		//if("string"==datasetInfo.fields[i].dataType){
			bean[datasetInfo.fields[i].name] = ""+record.getValue(datasetInfo.fields[i].name);
		//}else{
		//	bean[datasetInfo.fields[i].name] = record.getValue(datasetInfo.fields[i].name);
		//}
		
	}
	
	return bean;
}

function formToBean(form, beanType, ignoreButton) {

	var object = {};
	
	if (beanType) { 
		object[BOCLASS] = beanType; 
	} else{
		object[BOCLASS] = "java.util.Map";
	}
	
	if (typeof(ignoreButton) == "undefined" || ignoreButton == true) {
		ignoreButton = true;
	} else {
		ignoreButton = false;
	}
	
	form = $(form);
	var elements = form.elements;
	for (var i = 0; i < elements.length;i++) {
		var element = elements[i];
		switch (element.type) {
			case "radio" : 
				if (element.checked) { 
					object[element.id]=element.value
				} 
				break;
			case "checkbox" : 
				if (!object[element.id]) {object[element.id] = []};
				if (element.checked) {
					object[element.id].push(element.value);
				}
				break;
			case "select-one" : 
				var value = '', opt, index = element.selectedIndex;
				if (index >= 0) {
					opt = element.options[index];
					value = opt.value;
					if (!value && !('value' in opt)) value = opt.text;
				}
				object[element.id] = value;
			    break;
			case "select-multiple" :
				if (!object[element.id]) {object[element.id] = []};
				for (var j = 0; j < element.options.length; j++) {
					var opt = element.options[j];
					if (opt.selected) {
						var optValue = opt.value;
						if (!optValue && !('value' in opt)) optValue = opt.text;
						object[element.id].push(optValue);
			      	}
			    }
			    break;
			default : 
				if (ignoreButton) {
					if (element.type != "submit" && element.type != "button" 
						&& element.type != "reset") {
						object[element.id] = element.value;
					}
				} else {
					object[element.id] = element.value;
				}
				break;
		}
	}
	
	return object;
}
function __typeof(objClass)
{
	try{
	  if (objClass && objClass.constructor)
	  {
	    var strFun = objClass.constructor.toString();
	    var className = strFun.substr(0, strFun.indexOf('('));
	    className = className.replace('function', '');
	    return className.replace(/(^\s*)|(\s*$)/g, '');
	  }
	}catch(err){
	}

  return typeof(objClass);
}
function cloneVoToDataset(dataset, vo, append){ 
  var arr = null;

  if(vo.constructor==Array || __typeof(vo)=="Array"){
    arr = vo;
  }
  else
    arr = [vo];
  
  dataset.disableControls();
  
  if(!append){
    dataset.clearData();
    //dataset.insertRecord(null);
  }
  dataset.disableEvents();
  
  for(var j=0; j<arr.length; j++){
    dataset.insertRecord();
	  for(var i=0; i<dataset.fields.fieldCount; i++){
	    var fieldName = dataset.fields[i].getName();
	    if(arr[j][fieldName]){
	      dataset.setValue(fieldName, arr[j][fieldName]);
	    }  
	  }
  }
  
  dataset.enableEvents();
  
  if(!append){
	dataset.moveFirst();
	
  }else{
    dataset.setRecord(null);//这个清空很重要，否则没有了记录集切换事件。
    dataset.setRecord(dataset.getLastRecord());
  }
  
  dataset.enableControls();
}
function cloneVoToDataset2(dataset, vo, append){ 
  var arr = null;

  if(vo.constructor==Array || __typeof(vo)=="Array"){
    arr = vo;
  }
  else
    arr = [vo];
  
  
  dataset.disableControls();
  if(!append){    
    dataset.clearData();
    //dataset.insertRecord(null);
  }
  dataset.disableEvents();
  
  for(var j=0; j<arr.length; j++){
    dataset.insertRecord();
	  for(var i=0; i<dataset.fields.fieldCount; i++){
	    var fieldName = dataset.fields[i].getName();
	    if(arr[j][fieldName]){
	      dataset.setValue(fieldName, arr[j][fieldName]);
	    }  
	  }
  }
  
  dataset.enableEvents();

  if(!append){
	dataset.moveFirst();
  } else{
    dataset.setRecord(null);
    dataset.setRecord(dataset.getLastRecord());
  }
  dataset.enableControls();

	
}
var usings = [];

function finishedLayout(){
  if(!Global.finishedLayoutRender){
    Global.finishedLayoutRender = true;

    for(var i=0; i<usings.length; i++){
      var content = window.document.getElementById(usings[i].getAttribute("using"));
      if(content){
	      while( content.hasChildNodes() ){
		      usings[i].appendChild(content.childNodes[0]); 
		    }		      
	    }
		}	
  }	
}  


var array_tabsets = [];
function tabset_getSelectedPageIndex(){
  return this.selectedPageIndex;
}
function tabset_setSelectedPageIndex(index){
  setSelectedPage(this, index);
}
function tabset_getPageVisible(index){
  return _getPageVisible(this, index);
}
function tabset_setPageVisible(index, visible){
  _setPageVisible(this, index, visible);
} 
Tabset = {};
Tabset.initial = function(id){
  var tabset = $(id);
  array_tabsets[array_tabsets.length] = tabset;
  tabset.getSelectedPageIndex = tabset_getSelectedPageIndex;
  tabset.setSelectedPageIndex = tabset_setSelectedPageIndex; 
  tabset.getPageVisible = tabset_getPageVisible; 
  tabset.setPageVisible = tabset_setPageVisible; 
}
Tabset.destroy = function(tabset){
  tabset.getSelectedPageIndex = null;
  tabset.setSelectedPageIndex = null;
  tabset.getPageVisible = null;
  tabset.setPageVisible = null;
}

var tabsetTimer = null;
var currentTabsetTitle = null;

function tabsetScroll(source, direction)
{
	tabsetScrollStop();
	currentTabsetTitle = source.parentElement.previousSibling.childNodes[0];
	direction == "right" ? tabsetMoveRight() : tabsetMoveLeft();
}

function tabsetMoveRight()
{
	if(currentTabsetTitle){
		tabsetMove("right",30);
		tabsetTimer=setTimeout(tabsetMoveRight,10);
	}
}

function tabsetMoveLeft()
{
	if(currentTabsetTitle){
		tabsetMove("left",30);
		tabsetTimer=setTimeout(tabsetMoveLeft,10);
	}
}

function tabsetScrollStop()
{
	clearTimeout(tabsetTimer);
	tabsetTimer = null;
	currentTabsetTitle = null;
}

function tabsetMove(direction,speed)
{
	var mleft = parseInt(currentTabsetTitle.style.marginLeft);
	if (isNaN(mleft))
		mleft = 0;
	if(direction=="right")
	{
		if(currentTabsetTitle.parentElement.clientWidth >= currentTabsetTitle.parentElement.scrollWidth)
		{
			tabsetScrollStop();
			return;
		}
		else
		{	
			var _speed = currentTabsetTitle.parentElement.scrollWidth-currentTabsetTitle.parentElement.clientWidth;
			if(speed>_speed)
			  speed = _speed;
			currentTabsetTitle.style.marginLeft = mleft - speed;
		}
	}
	else
	{
		if(mleft + speed >=0)
		{
			currentTabsetTitle.style.marginLeft = 0;
			tabsetScrollStop();
			return;
		}
		else
		{
			currentTabsetTitle.style.marginLeft = mleft + speed;
		}
	}
}

var array_forms = [];
function form_validate(){
  return Validator.Validate(this, 2);
}
function form_onkeydown(){
  if(document.activeElement.tagName!="BUTTON" && event.keyCode==13 && $(this.submit))
  {
    Document.processActiveElementChanged($(this.submit));
    try{
      $(this.submit).focus();
  	}catch(e){
  	  $(this.submit).fireEvent("onclick");
    }
  }
  event.cancelBubble = true; 
}
Form = {};
Form.initial = function(id){
  var form = $(id);
  array_forms[array_forms.length] = form;
  form.validate = form_validate;
  if(form.submit && form.submit!=""){
    form.onkeydown = form_onkeydown;
  }    
}
Form.destroy = function(form){
  form.validate = null;
  form.submit = null;
  form.onsubmit = null;
  form.onkeydown = null;
  form = null;
}

//标签页是否需加载页面事件
function switchPage(target){
  var result = false;
  if(!System.isTrue(target.open)){
    target.open = true;
    result = true;
  }

  return result;
}  

function winShowPage(targetId){
	
	var tabset = $(targetId);
	if(tabset && !System.isTrue(tabset.getAttribute("isInWindow"))){
		var win = document.createElement("DIV");
		win.style.display = "none";
		win.style.position = "absolute";
		document.body.appendChild(win);
		
		win.id = "win_"+tabset.id;
		win.width = tabset.offsetWidth;
		win.height = tabset.offsetHeight;
		var pos = Document.getAbsPosition(tabset);
		//win.left = parseInt((document.body.offsetWidth-tabset.offsetWidth)/2);
		//win.top = parseInt((document.body.offsetHeight-tabset.offsetHeight)/2);
		win.left = pos[0];
		win.top = pos[1];
		win.title = "详细信息";
		win.className = "window";

		initXWin(win);
		
		var parentDiv = tabset.parentElement;
		if(parentDiv.id=="")
			parentDiv.id = "parent_"+tabset.id;
		win.parentDivId = parentDiv.id;
		win.tabsetId = tabset.id;
		win.oContent.appendChild(tabset);
		win.ShowHide();
	}
}

function minShowPage(tabsetId){
	minMaxPage(tabsetId, "min");
	
	var minButton = $(tabsetId+"_minButton");
	if(minButton){
		if(minButton.src.indexOf("cmnuMinimize.gif")!=-1){
			minButton.src = path_prefix+"/public/skins/bsn/tabset/cmnuRestore.gif";
		}
		else{
			minButton.src = path_prefix+"/public/skins/bsn/tabset/cmnuMinimize.gif";
		}
	}
	
	var maxButton = $(tabsetId+"_maxButton");
	if(maxButton){
		if(maxButton.src.indexOf("cmnuMaximize.gif")==-1){
			maxButton.src = path_prefix+"/public/skins/bsn/tabset/cmnuMaximize.gif";
		}
	}
}

function maxShowPage(tabsetId){
	minMaxPage(tabsetId, "max");
	
	var maxButton = $(tabsetId+"_maxButton");
	if(maxButton){
		if(maxButton.src.indexOf("cmnuMaximize.gif")!=-1){
			maxButton.src = path_prefix+"/public/skins/bsn/tabset/cmnuRestore.gif";
		}
		else{
			maxButton.src = path_prefix+"/public/skins/bsn/tabset/cmnuMaximize.gif";
		}
	}
	
	var minButton = $(tabsetId+"_minButton");
	if(minButton){
		if(minButton.src.indexOf("cmnuMinimize.gif")==-1){
			minButton.src = path_prefix+"/public/skins/bsn/tabset/cmnuMinimize.gif";
		}
	}	
}

function minMaxPage(tabsetId, minMaxFlag){	
	try{
		var tabset = $(tabsetId);
	    var tabsetHolder = tabset.parentElement;	
	    var tabsetTd = tabsetHolder.parentElement;	
	    var tabsetTr = tabsetTd.parentElement;	
		var tabsetTable = tabsetTr.offsetParent;
	
		for(var i=0; i<tabsetTable.rows.length; i++){
			var row = tabsetTable.rows[i];
			if(row!=tabsetTr){
				if(minMaxFlag=="min"){
					row.style.display = "";
				}
				else{
					row.style.display = (row.style.display=="none" ? "" : "none");
				}
			}
		}
		for(var i=0; i<tabsetTr.cells.length; i++){
			var cell = tabsetTr.cells[i];
			if(cell!=tabsetTd){
				if(minMaxFlag=="min"){
					cell.style.display = "";
				}
				else{
					cell.style.display = (cell.style.display=="none" ? "" : "none");
				}
			}
		}
		
		if(minMaxFlag=="max"){
			if(tabsetHolder.style.height!="100%"){
				if(tabsetHolder.getAttribute("origin_height")==null && tabsetHolder.style.pixelHeight!=0){
					tabsetHolder.setAttribute("origin_height", tabsetHolder.style.pixelHeight);
				}
				tabsetHolder.style.height = "100%";			    			
			}
			else{
				if(tabsetHolder.getAttribute("origin_height")!=0){
					tabsetHolder.style.height = tabsetHolder.getAttribute("origin_height");
				}
				else{
					tabsetHolder.style.height = "100%";
				}
			}
			
			if(tabsetHolder.style.width!="100%"){
				if(tabsetHolder.getAttribute("origin_width")==null && tabsetHolder.style.pixelWidth!=0){
					tabsetHolder.setAttribute("origin_width", tabsetHolder.style.pixelWidth);			    				
				}
				tabsetHolder.style.width = "100%";
			}
			else{
				if(tabsetHolder.getAttribute("origin_width")!=0){
					tabsetHolder.style.width = tabsetHolder.getAttribute("origin_width");
				}
				else{
					tabsetHolder.style.width = "100%";
				}
			}
		}
				    		
		var tabsetContent = $(tabsetId+"_tabsetContentDiv")
		if(tabsetContent){
			if(minMaxFlag=="max"){
				tabsetContent.style.display = "";
			}
			else{
				tabsetContent.style.display = (tabsetContent.style.display != "none" ? "none" : "");
			}
		}	
			
		if(minMaxFlag=="min"){
			if(tabsetHolder.style.height!="23px"){
				if(tabsetHolder.getAttribute("origin_height")==null && tabsetHolder.style.pixelHeight!=0){
					tabsetHolder.setAttribute("origin_height", tabsetHolder.style.pixelHeight);			    				
				}
				tabsetHolder.style.height = "23px";
			}
			else{
				if(tabsetHolder.getAttribute("origin_height")!=0){
					tabsetHolder.style.height = tabsetHolder.getAttribute("origin_height");
				}
				else{
					tabsetHolder.style.height = "100%";
				}
			}			    				    		
		}		    		

	}catch(e){}
}

//标签页切换事件
function showPage(target)
{
  var tabset = $(target.tabset);
  
  var count = System.getInt(tabset.pageCount);
  tabset.old_selectedPageIndex = tabset.selectedPageIndex;
  for(var i=0; i<count; i++)
  {
    var title = $(tabset.id+"_"+i);
    var page = $(tabset.id+"_"+i+"_div");
    if(title == target)
    {	        
        if(i==tabset.selectedPageIndex)
          return;
        var result = Document.fireUserEvent(tabset.id+"_beforePageChanged", [tabset, tabset.selectedPageIndex, i]);
        if(result!=false){        
          tabset.selectedPageIndex = i;  
        }
        else
           return;
    }  
  }   
  
  var title = $(tabset.id+"_"+tabset.old_selectedPageIndex);
  var page = $(tabset.id+"_"+tabset.old_selectedPageIndex+"_div");      
     
  page.style.display = "none";     
  title.setAttribute("selected", "false");    
  //title.className = "";     
  
  $(tabset.id+"_"+tabset.old_selectedPageIndex+"_titleLeft").style.backgroundPositionY = "0";
  $(tabset.id+"_"+tabset.old_selectedPageIndex+"_titleCenter").style.backgroundPositionY = "0";
  $(tabset.id+"_"+tabset.old_selectedPageIndex+"_titleRight").style.backgroundPositionY = "0";
  
  var title = $(tabset.id+"_"+tabset.selectedPageIndex);
  var page = $(tabset.id+"_"+tabset.selectedPageIndex+"_div");      
         
  page.style.width = "100%";
  page.style.display = "";       
  title.setAttribute("selected", "true");        
  //title.className = "selected"; 
  
  $(tabset.id+"_"+tabset.selectedPageIndex+"_titleLeft").style.backgroundPositionY = "-20px";
  $(tabset.id+"_"+tabset.selectedPageIndex+"_titleCenter").style.backgroundPositionY = "-20px";
  $(tabset.id+"_"+tabset.selectedPageIndex+"_titleRight").style.backgroundPositionY = "-20px";  
         
  if(tabset.id!="")
    Document.fireUserEvent(tabset.id+"_onPageChanged", [tabset, tabset.selectedPageIndex]);
  if (typeof(Editor.sizeDockEditor)!="undefined") 
    Control.adjustControlsSize();    
}
//脚本切换标签页行为
function setSelectedPage(tabset, index){
    var tabset = $(tabset);
    var count = System.getInt(tabset.pageCount);
    
    if(index<0 || index>count) return;
    if(tabset.selectedPageIndex == index) return;
    tabset.old_selectedPageIndex = tabset.selectedPageIndex;
    tabset.selectedPageIndex = index;
 
    var title = $(tabset.id+"_"+tabset.old_selectedPageIndex);
    var page = $(tabset.id+"_"+tabset.old_selectedPageIndex+"_div");      
     
    page.style.display = "none"; 
    title.setAttribute("selected", "false");       
    //title.className = "";     
  
    $(tabset.id+"_"+tabset.old_selectedPageIndex+"_titleLeft").style.backgroundPositionY = "0";
    $(tabset.id+"_"+tabset.old_selectedPageIndex+"_titleCenter").style.backgroundPositionY = "0";
    $(tabset.id+"_"+tabset.old_selectedPageIndex+"_titleRight").style.backgroundPositionY = "0";  
  
    var title = $(tabset.id+"_"+tabset.selectedPageIndex);
    var page = $(tabset.id+"_"+tabset.selectedPageIndex+"_div");      
         
    page.style.width = "100%";
    page.style.display = "";         
    title.setAttribute("selected", "true");        
    //title.className = "selected"; 

    $(tabset.id+"_"+tabset.selectedPageIndex+"_titleLeft").style.backgroundPositionY = "-20px";
    $(tabset.id+"_"+tabset.selectedPageIndex+"_titleCenter").style.backgroundPositionY = "-20px";
    $(tabset.id+"_"+tabset.selectedPageIndex+"_titleRight").style.backgroundPositionY = "-20px";  
  
    if(tabset.id!="")
      Document.fireUserEvent(tabset.id+"_onPageChanged", [tabset, tabset.selectedPageIndex]);  	
}

function _setPageVisible(tabset, index, visible){
  var count = System.getInt(tabset.pageCount);
  if(index<0 || index>count) return;
  var prefIndex = index - 1;
  if(prefIndex<0){
    prefIndex = prefIndex + 1;
  }
  if(prefIndex>count){
    prefIndex = index;
  }
  
  var title = $(tabset.id+"_"+index);
  
  if(System.isTrue(visible)){
	  title.style.display = "";
	  var tabset = $(tabset);
	  if(tabset){
	  	if(tabset.selectedPageIndex!=index){
		    var selectedTitle = $(tabset.id+"_"+tabset.selectedPageIndex);
		    if(selectedTitle.style.display=="none"){
		      	setSelectedPage(tabset.id, index);
		    }
	    }
	  }
  }
  else{
  	  title.style.display = "none";
	  if(System.isTrue(title.getAttribute("selected"))){
	    var prefTitle = $(tabset.id+"_"+prefIndex);
	    if(prefTitle.style.display!="none"){
	    	setSelectedPage(tabset.id, prefIndex);
	    }
	    else{
	      for(var i=0; i<count; i++){
	        prefTitle = $(tabset.id+"_"+i);
	        if(prefTitle.style.display!="none"){
	          setSelectedPage(tabset.id, i);
	          break;
	        }
	      }
	    }
	  }
  }
}
	
function _getPageVisible(tabset, index){
  var count = System.getInt(tabset.pageCount);
  if(index<0 || index>count) return false;
  
  var title = $(tabset.id+"_"+index);
  return (title.style.display == "");
}

var dragElement = null;
var dragDirection = "";
var startClientX = 0;
var startClientY = 0;

function fnGrap(direction){
	if(event.srcElement.tagName!="TD") return;
	dragElement = event.srcElement;
	dragDirection = direction;
	startClientX = 0;
	startClientY = 0;	
	
	fnInit();
	fnShowDragWindow();
	
	document.attachEvent("onmousemove", fnMove);
	document.attachEvent("onscroll", fnMove);
	document.attachEvent("onmouseup", fnRelease);
	document.attachEvent("onselectstart", fnSelect);	
	
	switch(dragDirection){
		case "top":
		case "bottom":{
			startClientY = event.clientY;
			break;
		}
		case "left":
		case "right":{
			startClientX = event.clientX;
			break;
		}
	}	
}

function fnInit(){	
	if(!dragElement.dragWindow){
		var oDragWindow = document.createElement("<DIV style='display:none;filter:alpha(opacity=20);position:absolute;background-color:black;overflow:hidden;'></DIV>");
		document.body.insertAdjacentElement("beforeEnd", oDragWindow);
		dragElement.dragWindow = oDragWindow;
	}
}

function fnShowDragWindow(){	
	var oDragWindow = dragElement.dragWindow;
	if(oDragWindow){
		var pos = Document.getAbsPosition(dragElement);
		oDragWindow.style.left = pos[0];
		oDragWindow.style.top = pos[1];
		oDragWindow.style.height = dragElement.offsetHeight;
		oDragWindow.style.width = dragElement.offsetWidth;	
		oDragWindow.style.zIndex = 10000;
		oDragWindow.style.display = "block";
	}
}

function fnHideDragWindow(){
	var oDragWindow = dragElement.dragWindow;
	if(oDragWindow){
		oDragWindow.style.display = "none";	
	}
}

function fnMove(){
	if(event.button!=1){
		fnRelease();
		return;
	}
	
	switch(dragDirection){
		case "top":
		case "bottom":{
			dragElement.dragWindow.style.top = event.clientY + document.body.scrollTop;
			break;
		}
		case "left":
		case "right":{
			dragElement.dragWindow.style.left = event.clientX + document.body.scrollLeft;
			break;
		}
	}	
	
}

function fnRelease(){
	fnHideDragWindow();
	
	document.detachEvent("onmousemove", fnMove);
	document.detachEvent("onscroll", fnMove);
	document.detachEvent("onmouseup", fnRelease);
	document.detachEvent("onselectstart", fnSelect);
	
	switch(dragDirection){
		case "top":{
			var distanceY = event.clientY - startClientY;
			dragElement.parentElement.previousSibling.childNodes[0].childNodes[0].style.pixelHeight += distanceY;		
			break;
		}
		case "bottom":{
			var distanceY = event.clientY - startClientY;
			dragElement.parentElement.nextSibling.childNodes[0].childNodes[0].style.pixelHeight -= distanceY;
			break;
		}
		case "left":{
			var distanceX = event.clientX - startClientX;
			dragElement.previousSibling.childNodes[0].style.pixelWidth += distanceX;
			break;		
		}
		case "right":{
			var distanceX = event.clientX - startClientX;
			dragElement.nextSibling.childNodes[0].style.pixelWidth -= distanceX;
			break;
		}
	}
	
	
	dragElement = null;
	dragDirection = "";
	startClientX = 0;
	startClientY = 0;		
}

function fnSelect(){
	return false;
}

function spliter_onClick(){    
 	  var spliterDiv = event.srcElement;
 	  var img = null;
 	  if(spliterDiv.tagName=="IMG"){
 	    img = spliterDiv;
 	    spliterDiv = spliterDiv.parentElement;
 	  }
 	  else{
 	    img = spliterDiv.childNodes[0];
 	  }
 	  if(spliterDiv){
 	    switch(spliterDiv.className){
 	      case "spliterDiv_top":{
 	        var target = spliterDiv.parentElement.previousSibling;
 	        if(target){
 	          target.style.display = (target.style.display=="" ? "none" : "");
 	        }
 	        break;
 	      }
 	      case "spliterDiv_left":{
 	        var target = spliterDiv.previousSibling;
 	        if(target){
 	          target.style.display = (target.style.display=="" ? "none" : "");
 	        }
 	        break;   	      
 	      }	 
  	  case "spliterDiv_right":{
 	        var target = spliterDiv.nextSibling;
 	        if(target){
 	          target.style.display = (target.style.display=="" ? "none" : "");
 	        }
 	        break;   	      
 	      }	  	      
 	      case "spliterDiv_bottom":{
 	        var target = spliterDiv.parentElement.nextSibling;
 	        if(target){
 	          target.style.display = (target.style.display=="" ? "none" : "");
 	        }
 	        break;
 	      }   	              	     
 	    }
 	  } 
 	  
 	  if(img){
 	    if(img.className.indexOf("_folded")!=-1){
 	      img.className=img.className.split("_folded")[0];
 	    }
 	    else{
 	      img.className=img.className+"_folded";
 	    }
 	  }      
}	
function makeControlEditable(control){
  if(control){
    control.onselect=control.onclick=control.onkeyup=
    function(){this.pos=document.selection.createRange();}
  }
}
function insertControlValue(control, value){
  if(control){
	  try{
	    control.pos.text=value;
	  }catch(e){
	    control.value += value;
	  }
  }
}

/*  Validator.js */

ErrMsgs = {
   	require  :  "必填内容不能为空",
   	required  :  "必填内容不能为空", 
  	number   : "数字格式不正确",
	integer : "整数格式不正确",
	doubleNum : "小数填写不正确",
	money: "贷币真写不正确(只能有两位小数)",
	email    :  "电子邮件格式不正确",
   	phone    : "电话号码格式不正确",
   	mobile   : "手机号码格式不正确",
	zip     : "邮编格式不正确",
	phoneNum    : "电话号码格式不正确",
	nbdNum     : "窄带注册拨号上网用户帐号格式不正确",	
	hexNum20     : "十六进制数字格式不正确",	//长度20之内的16进制。
	limit   : "输入长度超出范围",
	limitB  : "输入长度超出范围",
	repeat  : "两次输入信息不一致",
	time	: "时间的格式必须处于00:00:00和23:59:59之间（时间中的:必须为半角格式）",
	dateTime: "日期时间格式必须为0000-00-00 00:00:00 例如1999-11-10 20:30:29"
 }

 Validator = {
	require : /.+/,
	required : /.+/,
	number : /^\d+$/,
	integer : /^[-\+]?\d+$/,
	doubleNum : /^[-\+]?\d+(\.\d+)?$/,
	money : /^\d+(\.\d{1,2})?$/,
	email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
	phone : /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
	mobile : /^((\(\d{2,3}\))|(\d{3}\-))?((13\d{9})|(15\d{9})|(18\d{9}))$/,
	phoneNum : /^[0-9][\-|0-9]{6,20}$/,
	zip : /^[1-9]\d{5}$/,
	nbdNum : /^[a-z|A-Z][a-z|A-Z|0-9]{3,13}$/,
	hexNum20 : /^([0-9|a-f|A-F][0-9|a-f|A-F]){1,10}$/,
	idCard : "this.IsIdCard(value)",
	limit : "this.limitChk(value.length,getAttribute('minLen'),  getAttribute('maxLen'))",
	limitB : "this.limitChk(this.LenB(value), getAttribute('minLen'), getAttribute('maxLen'))",
	repeat : "value == document.getElementsByName(getAttribute('to'))[0].value",
	time   : "this.checkTime(value)",
	dateTime :"this.checkDateTime(value)",
	ErrorItem : [document.forms[0]],
	ErrorMessage : ["填写的信息不正确，请重新填写：\t\t\t\t"],

	Validate : function(theForm, mode){
		
		var obj = theForm || event.srcElement;
		var count = obj.elements.length;
		this.ErrorMessage.length = 1;
		this.ErrorItem.length = 1;
		this.ErrorItem[0] = obj;

		for(var i=0;i<count;i++){
			var checkObj =  obj.elements[i];
			//if(System.isTrue(checkObj.readOnly) && !System.isTrue(checkObj.popup)) continue;
			with(checkObj){
				var _dataType = getAttribute("validType");
				
				if(typeof(_dataType) == "object" || typeof(this[_dataType]) == "undefined")  continue;
				this.ClearState(obj.elements[i]);

				if(getAttribute("require") == "false" && value == "") continue;
				if(getAttribute("required") == "false" && value == "") continue;
				
				var validResult = this.checkValidType(checkObj, _dataType);
				
				if(!validResult)	
				{
					var msg = checkObj.getAttribute("msg");
							
					if( msg==undefined || msg==null || msg == ""){
						msg=ErrMsgs[_dataType];
					}
					this.AddError(i, msg);
				}
				
			}
		}

		if(this.ErrorMessage.length > 1){
			mode = mode || 1;
			var errCount = this.ErrorItem.length;

			for(var i=1;i<errCount;i++){
				if(this.ErrorItem[i].value != "")
					this.ErrorItem[i].style.color = "red";
			}
			
			MessageBox.Show(null, this.ErrorMessage.join(Global.lineDelimiter), null, 'OK', 'Warning');
			try{
				if(this.ErrorItem[1] && this.ErrorItem[1].focus != undefined ){
					this.ErrorItem[1].focus();
				}
			}catch(ee){
			}
			
			return false;
		}
		return true;
	},
		
	checkValidType : function (obj, _dataType){
		var validResult = true;
		with(obj){
			switch(_dataType){
				//js函数的验证的dataType
				case "repeat" :
				case "limit" :
				case "limitB" :
				case "idCard" :
				case "time" :
				case "dateTime" :
					//使用js函数的验证
					validResult = eval(this[_dataType]);
								
					break;
				default :
					//使用正则表达式的验证
					validResult = this[_dataType].test(value);				
					break;
			}
		}
		
		return validResult;		
	
	},
	
	//对单个元素格式检查(提示信息设置和form的检查不一样。)
	datasetValidate : function(_dataset, mode){
		
		return true;
	},

	limitChk : function(len,min, max){
		min = min || 0;
		max = max || Number.MAX_VALUE;
		return min <= len && len <= max;
	},
	
	checkTime:function(time){
		var a = time.match(/^(\d{0,2}):(\d{0,2}):(\d{0,2})$/); 
		if (a == null){
			return false;
		} 
		if(a[1].length<2|| a[2].length<2||a[3].length<2){
			return false;
		}
		if (a[1]>=24 || a[2]>=60 || a[3]>=60) 
		{
			return false;
		}
		return true;
	},
	checkDateTime:function(dateTime){
		var a = dateTime.match(/^(\d{0,4})-(\d{0,2})-(\d{0,2}) (\d{0,2}):(\d{0,2}):(\d{0,2})$/);
		if (a == null){
			return false;
		} 
		if ( a[2]>=13 || a[3]>=32 || a[4]>=24 || a[5]>=60 || a[6]>=60) 
		{
			return false;
		}
		return true;
	},

	LenB : function(str){
		return str.replace(/[^\x00-\xff]/g,"**").length;
	},

	ClearState : function(elem){
		with(elem){
			if(style.color == "red")
				style.color = "";
			var lastNode = parentNode.childNodes[parentNode.childNodes.length-1];
			if(lastNode.id == "__ErrorMessagePanel")
				parentNode.removeChild(lastNode);
		}
	},

	AddError : function(index, str){
		this.ErrorItem[this.ErrorItem.length] = this.ErrorItem[0].elements[index];
		this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length + ":" + str;
	},
	
	IsIdCard : function(idcard){
		var Errors=[
			"验证通过!",
			"身份证号码位数不对!",
			"身份证号码出生日期超出范围或含有非法字符!",
			"身份证号码校验错误!",
			"身份证地区非法!"
		];
	
		var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"} 
	
		var idcard,Y,JYM;
		var S,M;
		var idcard_array = [];
		idcard_array = idcard.split("");
		//地区检验
		if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4];
	
		//身份号码位数及格式检验
		switch(idcard.length){
		case 15:
			if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
				Ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
			} 
			else {
				Ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
			}
			if(Ereg.test(idcard)) return Errors[0];
			else return Errors[2];
			break;
	
		case 18:
			//18位身份号码检测
			//出生日期的合法性检查 
			//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
			//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
			if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
				Ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
			} 
			else {
				Ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
			}
			if(Ereg.test(idcard)){//测试出生日期的合法性
			//计算校验位
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
				+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
				+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
				+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
				+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
				+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
				+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
				+ parseInt(idcard_array[7]) * 1 
				+ parseInt(idcard_array[8]) * 6
				+ parseInt(idcard_array[9]) * 3 ;
			
			y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y,1);//判断校验位
			if(M == idcard_array[17]) return Errors[0]; //检测ID的校验位
			else return Errors[3];
		  }
		  else return Errors[2];
		  break;
	
		default:
		  return Errors[1];
		  break;
			
		}
	}

}

function resetValue(button, datasetId, fieldId){
    var dataset = eval(datasetId);
    if(dataset){
      dataset.setValue(fieldId, "");
      var field = dataset.getField(fieldId);
      if(field && field.keyField && field.keyField!=""){
        dataset.setValue(field.keyField, "");
        Document.fireUserEvent(datasetId+"_"+field.keyField+"_onreset", [dataset, field.keyField]);
      }
    }  
}

/*  IP 控件 */
function keydown(src){
	var e=window.event;
	var code=e.keyCode;
	var cursorPos=getPos(src);
	if(code==8){ //退格
		if(cursorPos==4 || cursorPos==8|| cursorPos==12){
			movenext(src,cursorPos,-1);
			cursorPos-=1;
		}else 
			movenext(src,cursorPos,0);
		var range=setSelect(src,cursorPos-1);
		range.text=" ";
		movenext(src,cursorPos,-1);
		e.returnValue=false;
		return false;
	}else if(code==46) 
		e.returnValue=false;
}
function keypress(src){
	var e=window.event;
	var code=e.keyCode;
	var cursorPos=getPos(src);
	if(code==46){
		if(cursorPos<13) 
			movenext(src,cursorPos,cursorPos<4?(4-cursorPos):(cursorPos<8?(8-cursorPos):(cursorPos<12?(12-cursorPos):(0))));
	}
	if(!keyPressInt()) return false;
	if(cursorPos==3 || cursorPos==7|| cursorPos==11){
		movenext(src,cursorPos,1);
		cursorPos += 1;
	}
	var num = String.fromCharCode(code);
	if(checkInput(src,num,cursorPos)){
		var range=setSelect(src,cursorPos);
		range.text=num;
		if(cursorPos==2 || cursorPos==6|| cursorPos==10)
			movenext(src,cursorPos,2);
		else movenext(src,cursorPos,1);
	}
	e.returnValue=false;
	return false;
}
/*
src 文本控件的引用
num 输入的字符,这里只能是数字
pos 光标位置,根据位置不同校验是否允许输入
*/
function checkInput(src,num,pos){
	/* 在这里进行每个位置的输入合法判断*/
	return true;
}
function setSelect(src,pos){
	var range = src.createTextRange();
	range.moveStart('character',pos); 
	range.moveEnd('character',pos-src.value.length+1); 
	range.select();
	return range;
}
function getPos(obj){
	var ml=obj.value.length;
	var pos=0;
	var rng=document.selection.createRange();
	rng.moveEnd("character",ml);
	try{
		pos=ml-rng.text.length
	}catch(e){}
	return pos;
}
function movenext(src,pos,flag)
{
	var range =src.createTextRange();
	range.moveStart("character",pos+flag);
	range.collapse(true);
	range.select();
}
function keyPressInt(){
	var e=window.event;
	code=e.keyCode;
	if(code >=48 && code <=57) 
		return true;
	else {
		window.event.returnValue=false;return false;
	}
}
function checkPaste(){
	window.event.returnValue=false;
}
//校验IP地址格式 
function checkip(src) 
{ 
	var scount=0; 
	var ip = src.value; 
	var iplength = ip.length; 
	var Letters = "1234567890. "; 
	var ip_blank = "   .   .   .   ";
	
	if(src.value==ip_blank) return true;
	for (i=0; i < src.value.length; i++) 
	{ 
		var CheckChar = src.value.charAt(i); 
		if (Letters.indexOf(CheckChar) == -1) 
		{ 
	    	alert ("IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1"); 
	    	src.value=ip_blank; 
	    	src.focus(); 
	    	return false; 
		} 
	} 

	for (var i = 0;i<iplength;i++) 
		(ip.substr(i,1)==".")?scount++:scount; 
		
	if(scount!=3) 
	{ 
		alert ("IP地址格式不对!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1"); 
	    src.value=ip_blank; 
	    src.focus(); 
		return false; 
	} 
	
	first = ip.indexOf("."); 
	last = ip.lastIndexOf("."); 
	str1 = ip.substring(0,first); 
	subip = ip.substring(0,last); 
	sublength = subip.length; 
	second = subip.lastIndexOf("."); 
	str2 = subip.substring(first+1,second); 
	str3 = subip.substring(second+1,sublength); 
	str4 = ip.substring(last+1,iplength); 
	
	if (str1=="   " || str2=="   " ||str3== "   " ||str4 == "   ") 
	{
		alert("数字不能为空！格式为XXX.XXX.XXX.XXX 例如：192.168.0.1");  
	    src.focus(); 
		return false; 
	} 
	
	var _str1 = parseInt(str1);
	var _str2 = parseInt(str2);
	var _str3 = parseInt(str3);
	var _str4 = parseInt(str4);
	
	var arr_str1 = str1.split("");
	var arr_str2 = str2.split("");
	var arr_str3 = str3.split("");
	var arr_str4 = str4.split("");
	
	var flag = true;
	if (_str1< 0 || _str1 >255 || arr_str1[0]!=" " && arr_str1[1]==" " && arr_str1[2]!=" "){
		flag = false;
		str1 = 255;
	}
	if (_str2< 0 || _str2 >255 || arr_str2[0]!=" " && arr_str2[1]==" " && arr_str2[2]!=" "){
		flag = false;
		str2 = 255;
	}
	if (_str3< 0 || _str3 >255 || arr_str3[0]!=" " && arr_str3[1]==" " && arr_str3[2]!=" "){
		flag = false;
		str3 = 255;
	}
	if (_str4< 0 || _str4 >255 || arr_str4[0]!=" " && arr_str4[1]==" " && arr_str4[2]!=" "){
		flag = false;
		str4 = 255;
	}
	
	if(!flag)
	{	
		alert ("数字范围为0~255！"); 
	    src.value=str1+"."+str2+"."+str3+"."+str4;
	    src.focus(); 
	    return false; 
	} 
	
	return true;
}

/* 日期时间输入校验 */
function isTime(str){ 
	var a = str.match(/^(\d{0,2}):(\d{0,2}):(\d{0,2})$/); 
	if (a == null) return false; 
	if (a[1]>=24 || a[2]>=60 || a[3]>=60) return false; 
	return true; 
} 
function isDateTime(str){ 
	var a = str.match(/^(\d{0,4})-(\d{0,2})-(\d{0,2}) (\d{0,2}):(\d{0,2}):(\d{0,2})$/); 
	if (a == null) return false; 
	if ( a[2]>=13 || a[3]>=32 || a[4]>=24 || a[5]>=60 || a[6]>=60) return false; 
	//year only between 1970 and 2029
	if ( a[1]>=2030 || a[1]< 1970) return false;
	return true; 
} 
function isDate(str){ 
	var a = str.match(/^(\d{0,4})-(\d{0,2})-(\d{0,2})$/); 
	if (a == null) return false; 
	if ( a[2]>=13 || a[3]>=32 || a[4]>=24) return false;	
	//year only between 1970 and 2029
	if ( a[1]>=2030 || a[1]< 1970) return false;
	return true; 
} 

function validateDatetimeFormat(obj,type){
	var valid=false; 
	var formatMsg = ["2006-11-01", "08:30:00", "2006-11-01 08:30:00"];
	var text = obj.value;
	if(text=="") return true;
	/*switch(type){ 
		case 1:valid = isDate(text);break; 
		case 2:valid = isTime(text);break; 
		case 3:valid = isDateTime(text);break; 
		default:valid = false; 
	} */
	//lhw modify 2006-12-15放开对日期的类型限制
	if(type==1||type==3)
	{
		valid=isDate(text)||isDateTime(text);
	}
	else if(type==2)
	{
		valid=isTime(text);
	}	
	if(!valid){
		alert ("格式不对! 请输入格式为："+formatMsg[type-1]); 
  	obj.focus(); 
	}
	return valid;
}

function validateDatetime(obj,type){ 
	var range=obj.createTextRange(); 
	var text = range.text; 
	var selrange = document.selection.createRange(); 
	var seltext = selrange.text; 
	var startpos = 0,endpos = 0; 
	while(selrange.compareEndPoints("StartToStart",range)>0){ 
		selrange.moveStart("character",-1); 
		startpos ++; 
	} 
	while(selrange.compareEndPoints("EndToStart",range)>0){ 
		selrange.moveEnd("character",-1); 
		endpos ++; 
	} 
	if(event.keyCode>=48){ 
		var keytext = String.fromCharCode(event.keyCode); 
		text = text.substring(0,startpos) + keytext + text.substring(endpos,text.length); 
	}else if(event.keyCode == 46){//delete 
		if(startpos == endpos)
			text = text.substring(0,startpos) + text.substring(startpos+1,text.length); 
		else 
			text = text.substring(0,startpos) + text.substring(endpos,text.length); 
	}else if(event.keyCode == 8){ 
		if(startpos == endpos)
			text = text.substring(0,startpos-1) + text.substring(startpos,text.length); 
		else 
			text = text.substring(0,startpos) + text.substring(endpos,text.length); 
	} 
	if(event.keyCode == 45){ 
		event.returnValue = false; 
		return; 
	}  
	var valid; 
	switch(type){ 
		case 1:valid = isDate(text);break; 
		case 2:valid = isTime(text);break; 
		case 3:valid = isDateTime(text);break; 
		default:valid = false; 
	} 
	if(!valid){ 
		event.returnValue = false; 
	} 
}
function setBoxValueToDataset(dataset,fieldName,box){

	if(box.type=="radio"){
		dataset.setValue(fieldName,box.value);
	}else if(box.type=="checkbox"){
		var valueString="";
		var objs = document.getElementsByName(box.name);
		for (var i = 0; i < objs.length; i++) {
	        if (objs[i].checked == true) {
	            valueString+=objs[i].value+"-";
	        }
		}
		if(valueString!=""){
			valueString=valueString.substring(0,valueString.length-1);
			
		}
		dataset.setValue(fieldName,valueString);
		
	}
} 