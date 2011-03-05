function setInnerHTML(element, htmlCode) {
    htmlCode = '<div style="display:none">for IE</div>' + htmlCode;
    htmlCode = htmlCode.replace(/<script([^>]*)>/gi,
                                '<script$1 defer>');
    element.innerHTML = htmlCode;
    element.removeChild(element.firstChild);
}

var path_prefix_array = [];

function LocalAction(){};

LocalAction.switch_recordData = false;

LocalAction.getLocalPathPrefix = function(){
	var href = location.href;
	var indexOfCrmWeb = href.lastIndexOf(SystemContext.appName);
	return href.slice(0, indexOfCrmWeb);	
}

LocalAction.getIncludePage = function(element, page){
 	if(page!=""){
		var fso, ts, s;
		var ForReading = 1; 
		fso = new ActiveXObject("Scripting.FileSystemObject");
		var path = location.pathname.slice(1, location.pathname.lastIndexOf("\\")+1);
		var isFileExists = fso.FileExists(path+page);
		if(!isFileExists){
			for(var i=0; i<path_prefix_array.length; i++){
				isFileExists = fso.FileExists(path_prefix_array[i]+page);
				if(isFileExists){
				  path = path_prefix_array[i];
				  break;
				}
			}
		}
		
		if(isFileExists){
			ts = fso.OpenTextFile(path+page, ForReading); 
			s = ts.ReadAll(); 
			path_prefix_array[path_prefix_array.length] = fso.GetParentFolderName(path+page)+"/";
			setInnerHTML(element, s);
		}			
 	}		
}

LocalAction.getRemoteCallURL = function(localURL){
	return LocalAction.getLocalPathPrefix()+SystemContext.appName+"/xml/"+localURL;	
}

LocalAction.getLocalMenuURL = function(Url){
	if(!Global.onServer && Url.indexOf("/"+SystemContext.appName+"/")!=-1){
		return path_prefix + Url.slice(8);
	}
	else{
		return Url;
	}
}

LocalAction.getLocalTreeListStylePath = function(){
	return LocalAction.getLocalPathPrefix()+SystemContext.appName+"/public/components/oaas-components/htc/treelist/";		
}

LocalAction.saveResponseData = function(data, localURL){
	if(Global.onServer && LocalAction.switch_recordData){
		var fso, f1, ts;
		var ForWriting = 2;
		var url = LocalAction.getLocalPathPrefix()+SystemContext.appName+"/xml/"+localURL;
		fso = new ActiveXObject("Scripting.FileSystemObject");	
		var isFileExists = fso.FileExists(url);
		if(!isFileExists){	
			fso.CreateTextFile (url);
			f1 = fso.GetFile(url);
			ts = f1.OpenAsTextStream(ForWriting, true);				
			ts.Write(data);
			ts.Close();	
		}
	}	
}

LocalAction.getLocalAttrCode = function(element, attrCode){
    var xml = document.createElement("XML");
	xml.id = "__"+attrCode;	            	
	element.insertAdjacentElement("afterEnd", xml);
           	
	var fso, f1, ts, s;
	var ForReading = 1; 
	fso = new ActiveXObject("Scripting.FileSystemObject");			

	var localURL = "dropdown/"+attrCode+".xml";
	var url = LocalAction.getLocalPathPrefix()+SystemContext.appName+"/xml/"+localURL;

	var ts = fso.OpenTextFile(url, ForReading, false, -1); 
	s = ts.ReadAll();
	xml.innerHTML = s;
					
	var code = document.createElement("CODE");
	code.id = attrCode;
	code.staticDataSource = true;
	element.insertAdjacentElement("afterEnd", code);
	makeDropDown(code);	
}

LocalAction.saveDropdownData = function(dropdownId){	
	
	if (Global.onServer && LocalAction.switch_recordData){
		eval("var isXmlExist=(typeof(__" + dropdownId + ")==\"object\")");
		if(isXmlExist){
			eval("var xmlIsland=__" + dropdownId);
			var fso, f1, ts;
			var ForWriting = 2;
			var localURL = "dropdown/"+dropdownId+".xml";
			var url = LocalAction.getLocalPathPrefix()+SystemContext.appName+"/xml/"+localURL;
			fso = new ActiveXObject("Scripting.FileSystemObject");	
			var isFileExists = fso.FileExists(url);
			//if(!isFileExists){	
				fso.CreateTextFile (url);
				f1 = fso.GetFile(url);
				ts = f1.OpenAsTextStream(ForWriting, true);	
				ts.Write('<?xml version="1.0" encoding="utf-8"?>');	
				ts.Write(xmlIsland.innerHTML);
				ts.Close();	
			//}
		}
	}	
}
