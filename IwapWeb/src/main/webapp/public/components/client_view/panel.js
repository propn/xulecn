

  var panel_container_code = '<table id="tablePanel"><tr><td id="top_left"></td><td id="top_center"><table id="titleTable"><tr><td id="titleTable_left"></td><td id="titleTable_center"><span id="titleDiv"></span></td><td id="titleTable_right"></td></tr></table></td><td id="top_right"></td></tr><tr><td id="center_left"></td><td id="contentDiv"></td><td id="center_right"></td></tr><tr><td id="bottom_left"></td><td id="bottom_center"></td><td id="bottom_right"></td></tr></table>';    
  var layout_border_code = '<table id="layoutPanel"><tr><td colspan="3"><div id="_top" class="layout_top"></div></td></tr><tr height="100%"><td><div id="_left" class="layout_left"></div></td><td width="100%"><div id="_center" class="layout_center"></div></td><td><div id="_right" class="layout_right"></div></td></tr><tr><td colspan="3"><div id="_bottom" class="layout_bottom"></div></td></tr></table>';	 	  	 
  var panel_bar_code = '<table id="barPanel"><tr><td id="top_left"></td><td id="top_center"><table id="titleTable"><tr><td id="titleTable_left"></td><td id="titleTable_center"><span id="titleDiv"></span></td><td id="titleTable_right"></td></tr></table></td><td id="contentDiv"></td><td id="top_right"></td></tr></table>'; 
  var tabset_code = '<table id="tabsetPanel"><tr><td id="tabsetTitleDiv"><table id="tabsetTitle"><tr><td id="tabsetTitle_left">&nbsp;</td><td id="tabsetTitle_right" width="*">&nbsp;</td></tr></table></td></tr><tr><td id="tabsetContentDiv"><div style="width: 100%; height: 100%; overflow: auto;"><table id="tabsetContent"></table></div></td></tr></table>';

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

    if(element._type=="bar"){
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
  
  //处理面板元素的背景图片
  function processPanelNodeBackgroundImage(node, type){    
    switch(node.id){
      case "top_left"          :{node.style.backgroundPosition = "top    left "; node.style.backgroundRepeat = "no-repeat"; break;}
      case "top_center"        :{node.style.backgroundPosition = "top    left "; node.style.backgroundRepeat = "repeat-x" ; break;}
      case "top_right"         :{node.style.backgroundPosition = "top    right"; node.style.backgroundRepeat = "no-repeat"; break;}
      case "center_left"       :{node.style.backgroundPosition = "center left "; node.style.backgroundRepeat = "repeat-y" ; break;}
      case "center_right"      :{node.style.backgroundPosition = "center right"; node.style.backgroundRepeat = "repeat-y" ; break;}
      case "bottom_left"       :{node.style.backgroundPosition = "bottom left "; node.style.backgroundRepeat = "no-repeat"; break;}
      case "bottom_center"     :{node.style.backgroundPosition = "bottom left "; node.style.backgroundRepeat = "repeat-x" ; break;}
      case "bottom_right"      :{node.style.backgroundPosition = "bottom right"; node.style.backgroundRepeat = "no-repeat"; break;}
      case "titleTable_left"   :{node.style.backgroundPosition = "top    left "; node.style.backgroundRepeat = "no-repeat"; break;}
      case "titleTable_center" :{node.style.backgroundPosition = "top    left "; node.style.backgroundRepeat = "repeat-x" ; break;}
      case "titleTable_right"  :{node.style.backgroundPosition = "top    right"; node.style.backgroundRepeat = "no-repeat"; break;}
      case "titleDiv"          :{node.style.backgroundPosition = "top    left "; node.style.backgroundRepeat = "no-repeat"; break;}
    }
    node.style.backgroundImage = 'url('+Global.theme_root+'/layout/'+type+'/'+node.id+'.gif)';
  }

  //面板管理中的节点处理
	function processPanelNode(node, target, element){  
    addUsingDiv(node, target);	      
    appendChildToTarget(node, target); 			    
    processPanelNodeStyle(node, target, element);
	}
	
	function makeLayout(element){
	  if(element.tagName=="layout"||element.className=="layout"){
	    if(element.type=="") return;
      element.insertAdjacentHTML("afterBegin", eval("layout_"+(element.type||"border")+"_code"));        
    }else if(element.className=="formLayout"){
      FormLayout.makeFormLayout(element);
      return;
    }else{   
      if(element.type=="") return;
      
	    if(element.type=="search" || element.type=="pageTitle" || element.type=="pageTitle2" || element.type == "search2")
	      element._type = "bar";
	    else
	      element._type = "container";
	      
	    element.insertAdjacentHTML("afterBegin", eval("panel_"+element._type+"_code")); 	    

      var panel = element.childNodes[0];
      for(var i=0; i<panel.all.length; i++){
        var node = panel.all[i];
        var id = node.id;
        if(id!=""){
          node.className = element.type + "_" + id;
          processPanelNodeBackgroundImage(node, element.type);
        }
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

		  switch(node.position||node.className){	  
		    case "title" : { titleDiv.innerHTML += node.innerHTML; break; }
		    case "content" : { processPanelNode(node, contentDiv, element); break; }		  		  
		    case "top" :
		    case "left" :
		    case "center" :
		    case "right" :
		    case "bottom" : { processLayoutNode(node, eval("_"+(node.position||node.className)), (node.position||node.className)); break; }		
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
    tabset.insertAdjacentHTML("afterEnd", tabset_code);

		processTabSetStyle(tabset);			
		
		var tabsetTitle = tabset.nextSibling.all["tabsetTitle"].tBodies[0].rows[0];
		var tabsetContent = tabset.nextSibling.all["tabsetContent"].tBodies[0];
		var startIndex = tabsetTitle.cells.length-2;
		
	  while( tabset.hasChildNodes() ){
	    var node = tabset.childNodes[0];
	    if(!(node.nodeType == 1 && (node.tagName=="tabpage"||node.className=="tab-page")))
	      tabset.removeChild(node);	      
      initTabSetTitle(tabset, tabsetTitle, node, startIndex);
      initTabSetPage(tabset, tabsetContent, node, startIndex);      
      startIndex++;     
	  } 
	  tabset.setAttribute("pageCount", startIndex);
	  
	  tabset.selectedPageIndex = 0;  
    if(tabset.id!="")
      Document.fireUserEvent(tabset.id+"_onPageChanged", [tabset, 0]);     
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
    var span = window.document.createElement("SPAN");
    if(System.isTrue(node.checkable)){
      var checkbox = window.document.createElement('<input type="checkbox" hideFocus="true" style="width:14px;height:14px;margin-top:-2px;"/>');
      span.appendChild(checkbox);
    }
    link.appendChild(span);
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
      
    var h2s = node.all.tags("h2");
    if(h2s.length>0){
      var title = h2s[0];
      if(title){
        span.innerHTML += title.innerHTML;
        title.style.display = "none";
      }
    }    	        
    td.appendChild(link);     
  }
  function initTabSetTitleStyle(td, index, type){
    
    td.style.backgroundImage = "url("+Global.theme_root+"/tabset/tableft"+type+".gif)";	 
    var link = td.childNodes[0];   
    link.style.backgroundImage = "url("+Global.theme_root+"/tabset/tabright"+type+".gif)";    
    	      	      
    if(index == 0){	        
      td.className="selected";   
    }    
  }
  //标题初始化
  function initTabSetTitle(tabset, target, node, index){
    var td = target.insertCell(index);
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
      td.appendChild(node);
      if(node.target && node.target!=""){
        var src = "";
        if(node.autoLoad && System.isTrue(node.autoLoad)){
          src = node.href;
        }
        var iframe = window.document.createElement('<iframe name="'+node.target+'" src="'+src+'" style="height:100%; width:100%" frameborder="0" allowTransparency="true" scrolling="no"></iframe>');
        node.appendChild(iframe);
      }
      td.style.verticalAlign = "top";	    
      addUsingDiv(node, td); 	  
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
    var _tabset = tabset.nextSibling;
    for(var i=0; i<_tabset.all.length; i++){
      var node = _tabset.all[i];
      var id = node.id;
      if(id!=""){
        node.className = "tabs"+tabset.type+"_"+id;
      }
    }		 
    if(tabset.style.height == ""){
      tabset.nextSibling.style.height = "100%";	    
    }else{	      
      tabset.nextSibling.style.height = tabset.style.height;
    }			      	  
	  tabset.style.display = "none";       
  }  
  
