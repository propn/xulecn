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
		      colspan+=3;	    
		  }
		  //alert(colspan-1);
		  td.colSpan = colspan-1;
		  td.style.width = width+"%";
		  
		}
		
		function insertItemToTd(td, item){
			if(item.popup){
			  var innerTable = document.createElement("<TABLE cellpadding='0' cellspacing='0'></TABLE>");
			  innerTable.style.width = "100%";

			  var innerTableTr = innerTable.insertRow();
			  var innerTableTd = innerTableTr.insertCell();
			  innerTableTd.appendChild(item);
			  
			  innerTableTd = innerTableTr.insertCell();	 
			  var popupButton = document.createElement("BUTTON");
			  popupButton.style.width = "16px";
			  popupButton.className = "popupButton";
			  
			  popupButton.id = "button_"+item.dataset.id+"_"+item.field;
			  popupButton.onclick = function(){
			    var button=event.srcElement;
			  	Document.fireUserEvent(Document.getElementEventName(button, "onClick"), [button]);
			  };
			  innerTableTd.appendChild(popupButton);
			  innerTableTd.style.width = "16px";
			  
			  td.appendChild(innerTable);
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
		    td.style.verticalAlign = "top";
		    td.style.paddingTop = "4px";
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