  var panel_titleList_code = '<table class="titleList_tablePanel"><tr><td class="titleList_top_left"></td><td class="titleList_top_center"><table class="titleList_titleTable"><tr><td class="titleList_titleTable_left"></td><td class="titleList_titleTable_center"><span id="titleDiv" class="titleList_titleDiv"></span></td><td class="titleList_titleTable_right"></td></tr></table></td><td class="titleList_top_right"></td></tr><tr><td class="titleList_center_left"></td><td id="contentDiv" class="titleList_contentDiv"></td><td class="titleList_center_right"></td></tr><tr><td class="titleList_bottom_left"></td><td class="titleList_bottom_center"></td><td class="titleList_bottom_right"></td></tr></table>';    
  var panel_modalDialog_code = '<table class="modalDialog_tablePanel"><tr><td class="modalDialog_top_center"><table class="modalDialog_titleTable"><tr><td class="modalDialog_titleTable_left"></td><td class="modalDialog_titleTable_center"><span id="titleDiv" class="modalDialog_titleDiv"></span></td><td class="modalDialog_titleTable_right"></td></tr></table></td></tr><tr><td id="contentDiv" class="modalDialog_contentDiv"></td></tr><tr></td><td class="modalDialog_bottom_center"></td></tr></table>';    

  var layout_code = '<table id="layoutPanel" cellspacing="0" cellpadding="0" border-collapse="collapse"></table>';	 	  	 
  var bar_code = '<table class="search_barPanel"><tr><td class="search_top_left">&nbsp;</td><td class="search_top_center"><table class="search_titleTable"><tr><td class="search_titleTable_left"></td><td class="search_titleTable_center"><div id="titleDiv" class="search_titleDiv"></div></td><td class="search_titleTable_right"></td></tr></table></td><td id="contentDiv" class="search_contentDiv"></td><td class="search_top_right">&nbsp;</td></tr></table>'; 
  var tabpane_code = '<div><table class="tabsetPanel"><tr><td class="tabsetTitleDiv tabsetTitleDiv_top"><table class="tabsetTitleDiv_innerDiv" cellpadding="0" cellspacing="0"><tr><td><table id="tabsetTitle" class="tabsetTitle"><tr><td class="tabsetTitle_left">&nbsp;</td><td class="tabsetTitle_right" width="*">&nbsp;</td></tr></table></td></tr></table></td></tr><tr><td class="tabsetContentDiv tabsetContentDiv_top"><table id="tabsetContent" class="tabsetContent"></table></td></tr></table></div>';

	//如果布局元素具有引用其他内容定义区域的属性，则将其添加到usings数组当中
	function addUsingDiv(node, target){
    if(node && node.use && node.use!=""){
      target.using = node.use;
      usings[usings.length] = target;
    } 	  
	}
	
	//将当前布局元素node的全部孩子节点条件到目标布局元素target中
	function appendChildToTarget(node, target){
    while( node.hasChildNodes() ){
      addUsingDiv(node.childNodes[0], node.childNodes[0]); 	    	      
      target.appendChild(node.childNodes[0]); 
    }		
	}
	
	//处理布局元素的高度和宽度样式
	function processLayoutNodeStyle(node, target, type){
	    if(node.style.cssText!="")
	    	target.style.cssText += node.style.cssText;
    switch(type){
      case "left" :
      case "right" :{
	      if(node.style.width!="")
	        target.style.width = node.style.width;	
	      break;	      
      }
      case "top" :
      case "bottom" :{        
        if(node.style.height!="")
          target.style.height = node.style.height;	
        break;
      }
    }	
  }  
	
	//布局管理中的节点处理
	function processLayoutNode(node, target, type){
	  addUsingDiv(node, target);	      
    appendChildToTarget(node, target);    		
    processLayoutNodeStyle(node, target, type);
  }		

	//处理面板元素的高度和宽度样式
	function processPanelNodeStyle(node, target, element){  

    if(element.tagName=="bar"){
      element.style.height = "";
    }else{
	    if(element.style.height == ""){
	      element.childNodes[0].style.height = "100%";	
	      element.childNodes[0].style.overflow = "auto";    
	    }else{	      
	      target.style.height = element.style.height;
        target.style.overflow = "auto";
      }
    }  
  }

  //面板管理中的节点处理
	function processPanelNode(node, target, element){  
    addUsingDiv(node, target);	      
    appendChildToTarget(node, target); 			    
    processPanelNodeStyle(node, target, element);
	}
	
	function makeLayout(element){
		if(element.className=="formLayout"){
	      FormLayout.makeFormLayout(element);
	      return;
	    }else{
	    
	      var target_code = null;
	      if(element.tagName=="layout"){
	      	var layoutTable = document.createElement(layout_code);
	      	element.insertAdjacentElement("afterBegin", layoutTable); 
	      	
	      	var withLeft = false;
	      	var withRight = false;
	      	var withTop = false;
	      	var withBottom = false;
	      	
	      	var withLeftSlider = true;
	      	var withRightSlider = true;
	      	var withTopSlider = true;
	      	var withBottomSlider = true;
	      	
	      	var withLeftSpliter = false;
	      	var withRightSpliter = false;
	      	var withTopSpliter = false;
	      	var withBottomSpliter = false;
	      	
	      	var withLeftFolded = false;
	      	var withRightFolded = false;
	      	var withTopFolded = false;
	      	var withBottomFolder = false;
	      	
	      	for(var i=1; i<element.childNodes.length; i++){
	        	var node = element.childNodes[i];
	        	if(node.position=="left"){
		    		withLeft = true;
		    		withLeftSlider = node.withSlider=="false" ? false : true;
		    		withLeftSpliter = (System.isTrue(node.withSpliter));
		    		withLeftFolded = (System.isTrue(node.folded));
			  	}
			  	else if(node.position=="right"){
			    	withRight = true;
			    	withRightSlider = node.withSlider=="false" ? false : true;
			    	withRightSpliter = (System.isTrue(node.withSpliter));
			    	withRightFolded = (System.isTrue(node.folded));
			  	}
			  	else if(node.position=="top"){
			    	withTop = true;
			    	withTopSlider = node.withSlider=="false" ? false : true;
			    	withTopSpliter = (System.isTrue(node.withSpliter));
			  		withTopFolded = (System.isTrue(node.folded));
			  	}
			  	else if(node.position=="bottom"){
			    	withBottom = true;
			    	withBottomSlider = node.withSlider=="false" ? false : true;
			    	withBottomSpliter = (System.isTrue(node.withSpliter));
			  		withBottomFolded = (System.isTrue(node.folded));
			  	}	
		   	}
		   	var panelTemplate = layoutTable;
		   	if(!withLeft && !withRight)
		   		panelTemplate.style.tableLayout = "fixed";
		   	
		   	var colspan = 1;
		   	if(withLeft) colspan++;
		   	if(withLeft && withLeftSlider) colspan++;
		   	if(withRight && withRightSlider) colspan++;
		   	if(withRight) colspan++;
		   	
		   	if(withTop){
		   	  var row = panelTemplate.insertRow();
		   	  row.style.display = (withTopFolded ? "none" : "");
		   	  
		   	  var cell = row.insertCell();
			  cell.colSpan = colspan;
		   	  var div = document.createElement("DIV");
		   	  div.id = "_top";
		   	  div.className = "layout_top";
		   	  cell.appendChild(div);		   	  
		   	  
		   	  if(withTopSlider){
			   	  var row = panelTemplate.insertRow();
			   	  var cell = row.insertCell();
				  cell.colSpan = colspan;
			   	  cell.className = "spliterDiv_top"
			   	  cell.onmousedown = function(){fnGrap("top");};			   	  
			   	  
			   	  if(withTopSpliter){
			   	  	var img = document.createElement("IMG");
			   	  	img.src = path_prefix+"/public/skins/bsn/layout/spliter.gif";
			   	  	img.className = "spliter_top"+(withTopFolded ? "_folded" : "");
			   	  	img.onclick = function(){spliter_onClick();};
			   	  	cell.appendChild(img);
		   	  	  }
		   	  }
		   	}
		   	
		   	var row = panelTemplate.insertRow();
		   	row.style.height = "100%";
		   	
		   	if(withLeft){
		   		var cell = row.insertCell();
		   		var div = document.createElement("DIV");
		   		div.id = "_left";
		   		div.className = "layout_left";
		   		cell.appendChild(div);
		   		
		   		cell.style.display = (withLeftFolded ? "none" : "");
		   		
		   		if(withLeftSlider){
		   			var cell = row.insertCell();
		   			cell.className = "spliterDiv_left"
			   	    cell.onmousedown = function(){fnGrap("left");};	 

			   	    if(withLeftSpliter){
				   	  	var img = document.createElement("IMG");
				   	  	img.src = path_prefix+"/public/skins/bsn/layout/spliter.gif";
				   	  	img.className = "spliter_left"+(withLeftFolded ? "_folded" : "");
				   	  	img.onclick = function(){spliter_onClick();};
				   	  	cell.appendChild(img);
		   	  	    }			   	     		
		   		}
		   	}
		   	
		   	var cell = row.insertCell();
		   	cell.style.width = "100%";
		   	var div = document.createElement("DIV");
		   	div.id = "_center";
		   	div.className = "layout_center";
		   	cell.appendChild(div);
		   	
		   	if(withRight){
		   		if(withRightSlider){
		   			var cell = row.insertCell();
		   			cell.className = "spliterDiv_right"
			   	    cell.onmousedown = function(){fnGrap("right");};	
			   	    
			   	    if(withRightSpliter){
				   	  	var img = document.createElement("IMG");
				   	  	img.src = path_prefix+"/public/skins/bsn/layout/spliter.gif";
				   	  	img.className = "spliter_right"+(withRightFolded ? "_folded" : "");
				   	  	img.onclick = function(){spliter_onClick();};
				   	  	cell.appendChild(img);
		   	  	    }
		   		}
		   		var cell = row.insertCell();
		   		var div = document.createElement("DIV");
		   		div.id = "_right";
		   		div.className = "layout_right";
		   		cell.appendChild(div);		
		   		
		   		cell.style.display = (withRightFolded ? "none" : "");   			
		   	}
		   	
		   	if(withBottom){
		   	  if(withBottomSlider){
			   	  var row = panelTemplate.insertRow();
			   	  var cell = row.insertCell();
				  cell.colSpan = colspan; 
			   	  cell.className = "spliterDiv_bottom"
			   	  cell.onmousedown = function(){fnGrap("bottom");};
			   	  
			   	  if(withBottomSpliter){
			   	  	var img = document.createElement("IMG");
			   	  	img.src = path_prefix+"/public/skins/bsn/layout/spliter.gif";
			   	  	img.className = "spliter_bottom"+(withBottomFolded ? "_folded" : "");
			   	  	img.onclick = function(){spliter_onClick();};
			   	  	cell.appendChild(img);
		   	  	  }
		   	  }		   	
		   	  
		   	  var row = panelTemplate.insertRow();
		   	  row.style.display = (withBottomFolded ? "none" : "");
		   	  
		   	  var cell = row.insertCell();
			  cell.colSpan = colspan;
			  
		   	  var div = document.createElement("DIV");
		   	  div.id = "_bottom";
		   	  div.className = "layout_bottom";
		   	  cell.appendChild(div);		   	     	  
		   	}		   	
	      }
	      else if(element.tagName=="bar"){
	      	target_code = bar_code;
	      	element.insertAdjacentHTML("afterBegin", target_code);  
	      }
	      else if(element.tagName=="panel"){
	        if(element.type=="titleList" || element.type=="titleTable"){
	          target_code = panel_titleList_code;
	        }
	        else if(element.type=="modalDialog"){
	          target_code = panel_modalDialog_code;
	        }
	        element.insertAdjacentHTML("afterBegin", target_code);  
	      } 
	           
	    }
	    
	    var panelTemplate = element.childNodes[0];	    
	    var titleDiv = panelTemplate.all["titleDiv"];
	    var contentDiv = panelTemplate.all["contentDiv"];
	    
	    var _top = panelTemplate.all["_top"];
	    var _left = panelTemplate.all["_left"];
	    var _center = panelTemplate.all["_center"];
	    var _right = panelTemplate.all["_right"];
	    var _bottom = panelTemplate.all["_bottom"];                    
	    
	    if(titleDiv && element.desc && element.desc!="")
	      titleDiv.innerText = element.desc;

	    for(var i=1; i<element.childNodes.length; i++){
	      var node = element.childNodes[i];
	      if(node.nodeType!=1) continue;        
	
		  switch(node.position){	  	  		  
		    case "top" :
		    case "left" :
		    case "center" :
		    case "right" :
		    case "bottom" : { processLayoutNode(node, eval("_"+node.position), node.position); break; }		
			}		  	  			 
		
		  if(node.tagName=="title"){
		    titleDiv.innerHTML += node.innerHTML;
		  }
		  else if(node.tagName=="content"){
		    processPanelNode(node, contentDiv, element);
		  }				 			  				  
		  
		  node.style.display = "none";
	    }
	    
  }
  


/*********************
  标签页控件
**********************/
  
  function makeTabset(tabset){
    tabset.insertAdjacentHTML("afterEnd", tabpane_code);
    tabsetDiv = tabset.nextSibling;
    tabsetDiv.id = tabset.id;
    
	processTabSetStyle(tabset);			
		
	var tabsetTitle = tabsetDiv.all["tabsetTitle"].rows[0];
	var tabsetContent = tabsetDiv.all["tabsetContent"];
	var tabsetTitleDivRow = tabsetDiv.all["tabsetTitle"].parentElement.parentElement;
	var tabsetContentDiv = tabsetDiv.childNodes[0].rows[0].cells[0];
	var startIndex = tabsetTitle.cells.length-2;   
   	
	while( tabset.hasChildNodes() ){
	    var node = tabset.childNodes[0];
	    if(node.nodeType != 1)
	      tabset.removeChild(node);	    
        initTabSetTitle(tabset, tabsetTitle, node, startIndex);
        initTabSetPage(tabset, tabsetContent, node, startIndex);      
        startIndex++;     
	} 
	tabsetDiv.setAttribute("pageCount", startIndex);	  
	tabsetDiv.selectedPageIndex = 0;      
   

    if(System.isTrue(tabset.scroll)){    	
    	var td = tabsetTitleDivRow.insertCell(tabsetTitleDivRow.cells.length);    	
    	td.className = "tabsetTitle_control";
    	
    	var span = document.createElement("SPAN");
    	td.appendChild(span);
    	span.title = "左移标签页";
    	span.onmousedown = function(){tabsetScroll(this, "left");};
    	span.onmouseup = function(){tabsetScrollStop();};
    	span.innerText = "<";
    	
    	var text = document.createTextNode(" ");
    	td.appendChild(text);
    	
    	var span = document.createElement("SPAN");
    	td.appendChild(span);
    	span.title = "右移标签页";
    	span.onmousedown = function(){tabsetScroll(this, "right");};
    	span.onmouseup = function(){tabsetScrollStop();};
    	span.innerText = ">";    	
    }
    
    if(System.isTrue(tabset.minimizable)){
    	var td = tabsetTitleDivRow.insertCell(tabsetTitleDivRow.cells.length);    	
    	td.className = "tabsetTitle_control_min";
    	
    	var img = document.createElement("IMG");
    	td.appendChild(img);
    	img.title = "最小化";
    	img.id = tabset.id+"_minButton";
    	img.tabsetId = tabset.id;
    	img.src = path_prefix + "/public/skins/bsn/tabset/cmnuMinimize.gif";
    	img.style.width = "13px";
    	img.style.height = "11px";
    	img.style.border = "0";
    	img.alt = "";
    	img.align = "center";
    	img.onclick = function(){minShowPage(this.tabsetId);};
    }
    
    if(System.isTrue(tabset.maximizable)){
        tabsetContentDiv.tabsetId = tabset.id;
    	tabsetContentDiv.ondblclick = function(){maxShowPage(this.tabsetId);};
    	
    	var td = tabsetTitleDivRow.insertCell(tabsetTitleDivRow.cells.length);    	
    	td.className = "tabsetTitle_control_max";
    	
    	var img = document.createElement("IMG");
    	td.appendChild(img);
    	img.title = "最大化";
    	img.id = tabset.id+"_maxButton";
    	img.tabsetId = tabset.id;
    	img.src = path_prefix + "/public/skins/bsn/tabset/cmnuMaximize.gif";
    	img.style.width = "13px";
    	img.style.height = "11px";
    	img.style.border = "0";
    	img.alt = "";
    	img.align = "center";
    	img.onclick = function(){maxShowPage(this.tabsetId);};
    }      
   
    //if(tabset.id!="")
    //	Document.fireUserEvent(tabset.id+"_onPageChanged", [tabset, 0]);     
    Tabset.initial(tabset.id);    
  }  


	
	/* 标签标题处理 */
 
  //初始化标题id
  function initTabSetTitleId(tabset, td, index){
    td.id = tabset.id + "_" + index;
  }
  //初始化标题行为
  function initTabSetTitleAction(td){
    td.onclick = function(){
      showPage(this);  
    };
  }
  //初始化标题描述
  function initTabSetTitleDesc(td, node, index){

    var link = window.document.createElement("A");
    td.appendChild(link);
    
    var span = window.document.createElement("SPAN");
    link.appendChild(span);
    span.className = "tabsetTitleTdLeft";
    span.id = td.id+"_titleLeft";
    if(index==0)
    	span.style.backgroundPositionY = "-20px";
    span.innerText = " ";
    
    var span = window.document.createElement("SPAN");
    link.appendChild(span);
    span.id = td.id+"_titleCenter";
    if(index==0)
    	span.style.backgroundPositionY = "-20px";
    span.className = "tabsetTitleTdCenter";
    
    if(System.isTrue(node.checkable)){
      var checkbox = window.document.createElement('<input type="checkbox" hideFocus="true" style="width:14px;height:14px;margin-top:-2px;"/>');
      span.appendChild(checkbox);
    }
    
    if(node.target && node.target!=""){
      span.innerHTML += node.desc;
      link.href = node.href;
      link.target = node.target;
      link.hideFocus = true;
      link.style.textDecoration = "none";
      link.setAttribute("open", node.autoLoad && System.isTrue(node.autoLoad));
      link.onclick = function(){return switchPage(this);};
    }
    else{
      if(node.desc && node.desc!="")
        span.innerHTML += node.desc;
    } 	   
    
    var span = window.document.createElement("SPAN");
    link.appendChild(span);
    span.className = "tabsetTitleTdRight";
    span.id = td.id+"_titleRight";
    if(index==0)
    	span.style.backgroundPositionY = "-20px";
    span.innerText = " ";         
         
  }
  function initTabSetTitleStyle(td, index, type){	      	      
    if(index == 0){	        
      td.setAttribute("selected", "true");
    }    
  }
  //标题初始化
  function initTabSetTitle(tabset, target, node, index){
    var td = target.insertCell(index+1);
    initTabSetTitleAction(td);
    initTabSetTitleId(tabset, td, index);    
    initTabSetTitleDesc(td, node, index);
    initTabSetTitleStyle(td, index, tabset.type); 
    td.tabset = tabset.id;
    return td;
	}
	
	/* 标签页处理 */
	
	//初始化页ID
	function initTabSetPageId(tabset, tr, index){
	  tr.id = tabset.id+"_"+index+"_div";
	}
	//初始化页风格
	function initTabSetPageStyle(tr, index){
    if(index != 0){
      tr.style.display = "none";	        
    }
	}	
	//初始化页节点内容
	function processTabSetNode(tr, node){
      var td = tr.insertCell();	
      td.style.verticalAlign = "top";	 
      td.style.padding = "0px";
      if(node.target && node.target!=""){
        var src = "";
        if(node.autoLoad && System.isTrue(node.autoLoad)){
          src = node.href;
        }
        td.appendChild(node);
        var iframe = window.document.createElement('<iframe name="'+node.target+'" src="'+src+'" style="height:100%; width:100%" frameborder="0" allowTransparency="true" scrolling="no"></iframe>');
        node.appendChild(iframe);
        addUsingDiv(node, td); 
      }
      else{
	      var div = document.createElement("DIV");
	      div.style.overflow = "auto";
	      div.style.width = "100%";
	      div.style.height = "100%";
	      td.appendChild(div); 
	      div.appendChild(node);
	      addUsingDiv(node, div); 
      }   
	}
	
	//页初始化
	function initTabSetPage(tabset, target, node, index){
    var tr = target.insertRow();
    initTabSetPageId(tabset, tr, index);
    initTabSetPageStyle(tr, index);
    processTabSetNode(tr, node);    
	}
	
  //整体风格控制
  function processTabSetStyle(tabset){		 
    if(tabset.style.height == ""){
      tabset.nextSibling.style.height = "100%";	    
    }else{	      
      tabset.nextSibling.style.height = tabset.style.height;
    }			      	  
	tabset.style.display = "none";       
  }  
  
FormLayout = {
 makeFormLayout : function(autoform){

	function makeRadioOrCheckboxLabelContainer(){
		var inputLayout = autoform.inputLayout;		
		if(!inputLayout || inputLayout=="") inputLayout = "56%";
							 
		var radioLayout = autoform.radioLayout;
		if(!radioLayout || radioLayout==""){
		  if(inputLayout)
		    radioLayout = inputLayout;
		  else
		    radioLayout = "56%";
		}  
		var all = autoform.getElementsByTagName("*");  
		for (var i = 0; i < all.length; i++)
		{
			var item = all[i];
			var tagName = item.tagName;
			var type = item.type;
			
		  if ((tagName == "INPUT") && ((type == "radio") || (type == "checkbox")))
		  {
		    var container = document.createElement("<label for='checkbox_radio'></label>");
		    
				with(item.style){
					width = "15px";
				  height = "15px";
				  marginTop = "3px";
				  marginBottom = "10px";
		    }
		    item.hideFocus = true;
		    item.insertAdjacentElement("afterEnd", container);
		    container.appendChild(item);     
		  	if(item.layout && item.layout!="")
		  		container.style.width = item.layout;
		    else        
		      container.style.width = radioLayout;
		    container.style.styleFloat = "left"; 
		    container.hideFocus = true;
		    i++;       
		  }    
		} 
	} 
	
	function makeLabelInputFloat(){
		var labelLayout = autoform.labelLayout;
		var labelAlign = autoform.labelAlign;
		var labelPadding = autoform.labelPadding;
		
		var inputLayout = autoform.inputLayout;		
		var textareaLayout = autoform.textareaLayout;  
		
		if(!labelLayout || labelLayout=="") labelLayout = "38%";
		if(!labelAlign || labelAlign=="") labelAlign = "center";
		if(!labelPadding || labelPadding=="") labelPadding = "0px";
		
		if(!inputLayout || inputLayout=="") inputLayout = "56%";		
		if(!textareaLayout || textareaLayout=="") textareaLayout = "57%";    
		
		var all = autoform.getElementsByTagName("*"); 
		for (var i = 0; i < all.length; i++)
		{
			var item = all[i];
			var tagName = item.tagName;
			var type = item.type;

		  if ((tagName=="LABEL") && (!item.htmlFor || item.htmlFor!="checkbox_radio"))
		  {		    
		  	if(item.labelLayout && item.labelLayout!="")
		  		item.style.width = item.labelLayout;
		    else     	
		      item.style.width = labelLayout;
		      
		    item.style.styleFloat = "left";
		    item.style.textAlign = labelAlign;
		    if(labelAlign.toLowerCase()=="left"){
		      item.style.paddingLeft = labelPadding;
		    }else{
		      item.style.paddingRight = labelPadding;	
		    }       
		  }
		  else if ((tagName == "INPUT") && ((type == "text") || (type == "password") || (type == "file")) )
		  {
		  	if(item.inputLayout && item.inputLayout!="")
		  		item.style.width = item.inputLayout;
		    else     	
		      item.style.width = inputLayout;
		    item.style.styleFloat = "left";     
		  }
		  else if (tagName == "TEXTAREA")
		  {
		  	if(item.textareaLayout && item.textareaLayout!="")
		  		item.style.width = item.textareaLayout;
		    else     	
		      item.style.width = textareaLayout;
		    item.style.styleFloat = "left";  
		  }
		  
	    if(item.style.width == "0" || item.style.width == "0%"){
	      item.style.display = "none";
	    }		  
		}
	}  	
	function makeLabelInputInTableCell(){
		
		function getRealWidth(layout){
   	      return parseInt(layout.split("%")[0]);	
		}
		
		function insertCell(table, currentWidth, item, type){
		
		  var tr = null;
		  
	
		  currentWidth = getRealWidth(currentWidth);
		  if(increasingWidth+currentWidth>100 || increasingWidth==0){
		    tr = table.insertRow();
		    increasingWidth = currentWidth;
		  }
		  else{
  	        tr = table.rows[table.rows.length-1];
		    increasingWidth += currentWidth;
		  }
	
		  var td = tr.insertCell();
		  td.style.width = currentWidth+"%";  
	      //td.style.border = "1px solid red";
	      
	      if(type=="input"){
	        var _td = tr.insertCell();
	        if(item.dataset){
	          _td.id = "_label_"+item.dataset.id+"_"+item.field;
	        }
	      }
	      
		  return td;		  
		}
		
		function reArrayInputColspan(td, currentWidth, labelLayout, inputLayout){
		  var colspan = 2;  
		  
		  currentWidth = getRealWidth(currentWidth);
		  labelLayout = getRealWidth(labelLayout);
		  inputLayout = getRealWidth(inputLayout);
		  		 
		  var width = inputLayout;

		  while(width<currentWidth){
		      width += labelLayout+inputLayout;		      
		      colspan+=4;	    
		  }
		  //alert(colspan-1);
		  td.colSpan = colspan-1;
		  td.style.width = width+"%";
		  
		}
		
		function insertItemToTd(td, item){
			if(item.popup){
			  var innerTable = document.createElement("<TABLE cellpadding='0' cellspacing='0' border-collapse='collapse' width='100%'></TABLE>");
			  td.appendChild(innerTable);

			  var innerTableTr = innerTable.insertRow();
			  var innerTableTd = innerTableTr.insertCell();
			  innerTableTd.appendChild(item);
			  
			  innerTableTd = innerTableTr.insertCell();	 
			  innerTableTd.style.width = "17px";
			  innerTableTd.style.paddingLeft = "1px";
			  
			  var popupButton = document.createElement("BUTTON");
			  popupButton.style.width = "16px";
			  popupButton.title = "搜索";
			  popupButton.className = "popupButton";			  
			  popupButton.id = "button_"+item.dataset.id+"_"+item.field;
			  popupButton.onclick = function(){
			    var button=event.srcElement;
			  	Document.fireUserEvent(Document.getElementEventName(button, "onClick"), [button]);
			  };			  
			  innerTableTd.appendChild(popupButton);
			  			  
			  innerTableTd = innerTableTr.insertCell();	 
			  innerTableTd.style.width = "16px";	  
			  
			  var resetButton = document.createElement("BUTTON");
			  resetButton.style.width = "17px";
			  resetButton.title = "清空";
			  resetButton.className = "resetButton";			  
			  resetButton.id = "button_"+item.dataset.id+"_"+item.field;
			  resetButton.dataset = item.dataset.id;
			  resetButton.field = item.field;
			  resetButton.onclick = function(){
			    var button=event.srcElement;
			  	resetValue(button, button.dataset, button.field);
			  };			  
			  innerTableTd.appendChild(resetButton);			  

			}
			else{			
			  td.appendChild(item);
			}		
		}
		
		var labelLayout = autoform.labelLayout;
		var labelAlign = autoform.labelAlign;
		var labelPadding = autoform.labelPadding;
		
		var inputLayout = autoform.inputLayout;		
		var textareaLayout = autoform.textareaLayout;  
		
		if(!labelLayout || labelLayout=="") labelLayout = "38%";
		if(!labelAlign || labelAlign=="") labelAlign = "center";
		if(!labelPadding || labelPadding=="") labelPadding = "0px";
		
		if(!inputLayout || inputLayout=="") inputLayout = "56%";		
		if(!textareaLayout || textareaLayout=="") textareaLayout = "57%";  
		
		var table = document.createElement("TABLE");
	    table.style.width = "100%";
	    table.style.border = "0";
	    table.cellPadding="0";
	    table.cellSpacing="0";
	    table.style.borderCollapse="collapse";
		  
		var increasingWidth = 0;
		var _width = "";
		
		autoform.insertAdjacentElement("afterBegin", table);
		
		while(autoform.childNodes.length>1){
			var item = autoform.childNodes[1];
			
			if(item.nodeType!=1){
			  autoform.removeChild(item);
			  continue;
			}
			
			var tagName = item.tagName;
			var type = item.type;

		  if ((tagName=="LABEL") && (!item.htmlFor || item.htmlFor!="checkbox_radio"))
		  {		    
		    
		  	if(item.labelLayout && item.labelLayout!="")
		  	  _width = item.labelLayout;
		    else     	
		      _width = labelLayout;				    
		    
		    var td = insertCell(table, _width, item, "label"); 
		    td.appendChild(item);
		    td.style.textAlign = labelAlign;
		    if(labelAlign.toLowerCase()=="left"){
		      td.style.paddingLeft = labelPadding;
		    }else{
		      td.style.paddingRight = labelPadding;	
		    }       
		  }
		  else if ((tagName == "INPUT") && ((type == "text") || (type == "password") || (type == "file")) )
		  {
		  	if(item.inputLayout && item.inputLayout!="")
		  	  _width = item.inputLayout;
		    else     	
		      _width = inputLayout;
	    
			var td = insertCell(table, _width, item, "input");
			reArrayInputColspan(td, _width, labelLayout, inputLayout);
			item.style.width = "100%";
			
            insertItemToTd(td, item);
            
            var tr = table.rows[table.rows.length-1];
            var td = tr.insertCell(tr.cells.length);
            if(item.dataset.getField(item.field).required){
            	td.className = "require_td";
    			td.innerHTML = "<span class='require'>*</span>";
    		}
    		else{
    			td.className = "not_require_td";
    			td.innerHTML = " ";		
		  	}		
		  }
		  else if (tagName == "TEXTAREA")
		  {
		  	if(item.textareaLayout && item.textareaLayout!="")
		  	  _width = item.textareaLayout;
		    else     	
		      _width = textareaLayout; 
		    
			var td = insertCell(table, _width, item, "input");
			reArrayInputColspan(td, _width, labelLayout, inputLayout);
			item.style.width = "100%";
			insertItemToTd(td, item);
			
            var tr = table.rows[table.rows.length-1];
            var td = tr.insertCell(tr.cells.length);
            if(item.dataset.getField(item.field).required){
            	td.className = "require_td";
    			td.innerHTML = "<span class='require'>*</span>";
    		}
    		else{
    			td.className = "not_require_td";
    			td.innerHTML = " ";		
		  	}
		  }
		  
	      if(item.style.width == "0" || item.style.width == "0%"){
	        item.style.display = "none";
	      }		  
		}
		
	} 	
 	makeRadioOrCheckboxLabelContainer();
 	if(autoform.type=="table"){
 	  makeLabelInputInTableCell();
 	}
 	else{
 	  makeLabelInputFloat();
 	}
 	autoform.onsubmit = function(){return false;}
 }
}