
/*----------------------------------------------------------------------------
|                               XLoadTree 1.11                                |
|-----------------------------------------------------------------------------|
|                         Created by Erik Arvidsson                           |
|                  (http://webfx.eae.net/contact.html#erik)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
| An extension to xTree that allows sub trees to be loaded at runtime by      |
| reading XML files from the server. Works with IE5+ and Mozilla 1.0+         |
|-----------------------------------------------------------------------------|
|                   Copyright (c) 1999 - 2002 Erik Arvidsson                  |
|-----------------------------------------------------------------------------|
| This software is provided "as is", without warranty of any kind, express or |
| implied, including  but not limited  to the warranties of  merchantability, |
| fitness for a particular purpose and noninfringement. In no event shall the |
| authors or  copyright  holders be  liable for any claim,  damages or  other |
| liability, whether  in an  action of  contract, tort  or otherwise, arising |
| from,  out of  or in  connection with  the software or  the  use  or  other |
| dealings in the software.                                                   |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| This  software is  available under the  three different licenses  mentioned |
| below.  To use this software you must chose, and qualify, for one of those. |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Non-Commercial License          http://webfx.eae.net/license.html |
| Permits  anyone the right to use the  software in a  non-commercial context |
| free of charge.                                                             |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Commercial license           http://webfx.eae.net/commercial.html |
| Permits the  license holder the right to use  the software in a  commercial |
| context. Such license must be specifically obtained, however it's valid for |
| any number of  implementations of the licensed software.                    |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| GPL - The GNU General Public License    http://www.gnu.org/licenses/gpl.txt |
| Permits anyone the right to use and modify the software without limitations |
| as long as proper  credits are given  and the original  and modified source |
| code are included. Requires  that the final product, software derivate from |
| the original  source or any  software  utilizing a GPL  component, such  as |
| this, is also licensed under the GPL license.                               |
|-----------------------------------------------------------------------------|
| 2001-09-27 | Original Version Posted.                                       |
| 2002-01-19 | Added some simple error handling and string templates for      |
|            | reporting the errors.                                          |
| 2002-01-28 | Fixed loading issues in IE50 and IE55 that made the tree load  |
|            | twice.                                                         |
| 2002-10-10 | (1.1) Added reload method that reloads the XML file from the   |
|            | server.                                                        |
/ 2003-05-06 | Added support for target attribute                             |
|-----------------------------------------------------------------------------|
| Dependencies: xtree.js - original xtree library                             |
|               xtree.css - simple css styling of xtree                       |
|               xmlextras.js - provides xml http objects and xml document     |
|                              objects                                        |
|-----------------------------------------------------------------------------|
| Created 2001-09-27 | All changes are in the log above. | Updated 2003-05-06 |
----------------------------------------------------------------------------*/
webFXTreeConfig.loadingText = "Loading...";
webFXTreeConfig.loadErrorTextTemplate = "Error loading \"%1%\"";
webFXTreeConfig.emptyErrorTextTemplate = "Error \"%1%\" does not contain any tree items";
/*
 * WebFXLoadTree class
 */
function WebFXLoadTree(sText, sXmlSrc, sAction, sBehavior, sIcon, sOpenIcon, root) {

	// call super
    this.WebFXTree = WebFXTree;
    this.WebFXTree(sText, sAction, sBehavior, sIcon, sOpenIcon, root);
    
	// setup default property values
    this.src = sXmlSrc;
    this.loading = false;
    this.loaded = false;
    this.errorText = "";

	// check start state and load if open

    if (this.open) {

        _startLoadXmlTree(this.src, this);
    } else {
		// and create loading item if not
      this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
      this.add(this._loadingItem);
    }
}
WebFXLoadTree.prototype = new WebFXTree;

// override the expand method to load the xml file
WebFXLoadTree.prototype._webfxtree_expand = WebFXTree.prototype.expand;
WebFXLoadTree.prototype.expand = function () {
    if (!this.loaded && !this.loading) {
		// load
        _startLoadXmlTree(this.src, this);
    }
    this._webfxtree_expand();
};
/*
 * WebFXLoadTreeItem class
 */
function WebFXLoadTreeItem(sText, sXmlSrc, sAction, eParent, sIcon, sOpenIcon, root, data) {
	// call super

    this.WebFXTreeItem = WebFXTreeItem;
    this.WebFXTreeItem(sText, sAction, eParent, sIcon, sOpenIcon,root, data);

    this.nodeId = root.nodeId;
    
    this.loadDataAction = root.loadDataAction;
    this.loadDataActionMethod = root.loadDataActionMethod;
    this.async = root.async;
    this.loadDataLevel = root.loadDataLevel;	
    this.staticObject = root.staticObject; 
    
	// setup default property values
    this.src = sXmlSrc;
    this.loading = false;
    this.loaded = false;
    this.errorText = "";

	// check start state and load if open
    this.open = false;
    if (this.open) {
        _startLoadXmlTree(this.src, this);
    } else {
		// and create loading item if not
        this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
        this.add(this._loadingItem);
    }

}


WebFXLoadTreeItem.prototype = new WebFXTreeItem;

// override the expand method to load the xml file
WebFXLoadTreeItem.prototype._webfxtreeitem_expand = WebFXTreeItem.prototype.expand;
WebFXLoadTreeItem.prototype.expand = function () {
    if (!this.loaded && !this.loading) {
		// load
        _startLoadXmlTree(this.src, this);
    }
    this._webfxtreeitem_expand();
};

// reloads the src file if already loaded
WebFXLoadTree.prototype.reload = WebFXLoadTreeItem.prototype.reload = function () {
	// if loading do nothing
	//alert("this.loaded:"+this.loaded);
    if (this.loaded) {
        var open = this.open;
		// remove
        while (this.childNodes.length > 0) {
            this.childNodes[this.childNodes.length - 1]._remove();
        }
        this.loaded = false;
        this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
        this.add(this._loadingItem);
        //if (open) {
            this.expand();
        //}
    } else {
      //alert("this.open && !this.loading:"+(this.open && !this.loading));

        //if (this.open && !this.loading) {
            _startLoadXmlTree(this.src, this);
        //}
    }
};

WebFXLoadTree.prototype.clear = WebFXLoadTreeItem.prototype.clear = function () {
	// if loading do nothing
	//alert("this.loaded:"+this.loaded);
    if (this.loaded) {
        while (this.childNodes.length > 0) {
            this.childNodes[this.childNodes.length - 1]._remove();
        }
        this.loaded = false;
        this.open = true;
        this.loading = false;
    }
};
/*
 * Helper functions
 */

// creates the xmlhttp object and starts the load of the xml document
function _startLoadXmlTree(sSrc, jsNode) {
  //alert("startLoadXmlTree");
    if(jsNode.root.initial){
      jsNode.root.insertAdjacentHTML("afterEnd", jsNode);
      jsNode.root.initial = false;
      jsNode.root.tree = jsNode;
    }   

    var bIndent = false;
    //if (jsNode.loading || jsNode.loaded) {
    //    return;
    //}
    jsNode.loading = true;
    var callBack = function (reply) {
        var ret = null;
        if (jsNode.staticObject!="") {
            ret = eval(jsNode.staticObject);
        } else {            
            ret = reply.getResult();
        }
        //alert(reply.getSource());
		if(jsNode.childNodes){
	        while (jsNode.childNodes.length > 0) {
	            jsNode.childNodes[jsNode.childNodes.length - 1]._remove();
	        }
        }

        _voToJsTree(ret, jsNode);

        if (jsNode._loadingItem != null) {
            jsNode._loadingItem._remove();
            bIndent = true;
        }
        
     

	    Document.fireUserEvent(Document.getElementEventName(jsNode.root, "onAfterCreate"), [jsNode.root]);
    };

    if (jsNode.staticObject != ""){
      callBack(eval(jsNode.staticObject));
      jsNode.loaded = true;
      jsNode.loading = false;      
    }
    else if (Global.onServer && jsNode.root.loadDataAction!="" && jsNode.root.loadDataActionMethod!="") {
        var ajaxCall = new NDAjaxCall(jsNode.async);
        /*alert(jsNode.root.loadDataAction);
        alert(jsNode.root.loadDataActionMethod);
        alert(jsNode.nodeId);
        alert(jsNode.loadDataLevel);*/
        
	    var parameters = new Array();
	    var ps = jsNode.root.parameters;
		
		parameters[0] = jsNode.nodeId;
		parameters[1] = jsNode.loadDataLevel;
		 
		if(ps){
		    for( var i = 0; i < ps.count(); i ++ ){
		    	var parameterName = ps.indexToName(i);
		    	var parameterType = ps.getDataType( parameterName );
		    	var parameterValue = ps.getValue( parameterName );
		    	if( parameterType == "object" ){
		    		parameters[i+2] = parameterValue ;
		    	}	else{
			    	var value = parseValue( parameterValue, parameterType );
			    	parameters[i+2] = value ;
		    	}
		    } 
	    }     
	    //alert(parameters.length);  
	    
	    var field_arr = jsNode.root.fields.split(",");
	    var target_fields = new Array();
	    if(jsNode.root.idField!=""){
	      target_fields[target_fields.length] = jsNode.root.idField;
	    }
	    if(jsNode.root.nameField!=""){
	      target_fields[target_fields.length] = jsNode.root.nameField;
	    }
	    if(jsNode.root.typeField!=""){
	      target_fields[target_fields.length] = jsNode.root.typeField;
	    }
	    if(jsNode.root.descField!=""){
	      target_fields[target_fields.length] = jsNode.root.descField;
	    }
	    if(jsNode.root.parentIdField!=""){
	      target_fields[target_fields.length] = jsNode.root.parentIdField;
	    }	    	    	    	    
    	
    	for(var i=0; i<field_arr.length; i++){
    	  var flag = true;
    	  for(var j=0; j<target_fields.length; j++){
    	    if(target_fields[j]==System.trimStr(field_arr[i])){
    	      flag = false;
    	      break;    	      
    	    }
    	  }
    	  if(flag){
    	    target_fields[target_fields.length] = System.trimStr(field_arr[i]);
    	  }
    	}
	    
        
        ajaxCall.remoteCall(jsNode.root.loadDataAction, jsNode.root.loadDataActionMethod, parameters, callBack, "tree", target_fields);
        jsNode.loaded = true;
        jsNode.loading = false; 
    }
    else{
        jsNode.loaded = false;
        jsNode.loading = true;
    }

    

    
    /*if (jsNode.loading || jsNode.loaded)
		return;
	jsNode.loading = true;
	var xmlHttp = XmlHttp.create();
	xmlHttp.open("GET", sSrc, true);	// async
	xmlHttp.onreadystatechange = function () {
		if (xmlHttp.readyState == 4) {
			_xmlFileLoaded(xmlHttp.responseXML, jsNode);
		}
	};
	// call in new thread to allow ui to update
	window.setTimeout(function () {
		xmlHttp.send(null);
	}, 10);
  */
}

var imgList = Global.theme_root+"/xtree/icon";

function getIconPath(index){
  if(!index || index=="")
    return "";
  else
    return imgList+index+".gif";
}


function _voToJsTree(items, parent){

	if(!items) return;

	for (var i = 0; i < items.length; i++) {
	  items[i].id = items[i].data[parent.root.idField];
	  items[i].name = items[i].data[parent.root.nameField];
	  items[i].type = items[i].data[parent.root.typeField];
	  items[i].desc = items[i].data[parent.root.descField];
	  items[i].parentId = items[i].data[parent.root.parentIdField];

	    var sText = items[i].name;
	    
	    if(sText=="") continue;
	    
	    var sAction = "";
	    var eParent = "";
	    var node = null;
	    
	    Document.fireUserEvent(Document.getElementEventName(parent.root, "iconRefresh"), [parent.root, items[i]]);
	    Document.fireUserEvent(Document.getElementEventName(parent.root, "openIconRefresh"), [parent.root, items[i]]);

	    var sIcon = items[i].icon;
	    var sOpenIcon = items[i].openIcon;	 	        
	    
	    if (System.isTrue(items[i].type)) {
	        node = parent.add(new WebFXLoadTreeItem(sText, "", sAction, eParent, getIconPath(sIcon), getIconPath(sOpenIcon),parent.root, items[i].data));
	        node.nodeId = items[i].id;

	    } else {	      
        node = parent.add(new WebFXTreeItem(sText, sAction, eParent, getIconPath(sIcon), getIconPath(sOpenIcon), parent.root, items[i].data));

 	      
	    }
	    _voToJsTree(items[i].children, node);
	}  	
}

// Converts an xml tree to a js tree. See article about xml tree format
function _xmlTreeToJsTree(oNode) {
	// retreive attributes
    var text = oNode.getAttribute("text");
    var action = oNode.getAttribute("action");
    var parent = null;
    var icon = oNode.getAttribute("icon");
    var openIcon = oNode.getAttribute("openIcon");
    var src = oNode.getAttribute("src");
    var target = oNode.getAttribute("target");
	// create jsNode
    var jsNode;
    if (src != null && src != "") {
        jsNode = new WebFXLoadTreeItem(text, src, action, parent, icon, openIcon);
    } else {
        jsNode = new WebFXTreeItem(text, action, parent, icon, openIcon);
    }
    if (target != "") {
        jsNode.target = target;
    }

	// go through childNOdes
    var cs = oNode.childNodes;
    var l = cs.length;
    for (var i = 0; i < l; i++) {
        if (cs[i].tagName == "tree") {
            jsNode.add(_xmlTreeToJsTree(cs[i]), true);
        }
    }
    return jsNode;
}

// Inserts an xml document as a subtree to the provided node
function _xmlFileLoaded(oXmlDoc, jsParentNode) {
    if (jsParentNode.loaded) {
        return;
    }
    var bIndent = false;
    var bAnyChildren = false;
    jsParentNode.loaded = true;
    jsParentNode.loading = false;

	// check that the load of the xml file went well
    if (oXmlDoc == null || oXmlDoc.documentElement == null) {
        alert(oXmlDoc.xml);
        jsParentNode.errorText = parseTemplateString(webFXTreeConfig.loadErrorTextTemplate, jsParentNode.src);
    } else {
		// there is one extra level of tree elements
        var root = oXmlDoc.documentElement;

		// loop through all tree children
        var cs = root.childNodes;
        var l = cs.length;
        for (var i = 0; i < l; i++) {
            if (cs[i].tagName == "tree") {
                bAnyChildren = true;
                bIndent = true;
                jsParentNode.add(_xmlTreeToJsTree(cs[i]), true);
            }
        }

		// if no children we got an error
        if (!bAnyChildren) {
            jsParentNode.errorText = parseTemplateString(webFXTreeConfig.emptyErrorTextTemplate, jsParentNode.src);
        }
    }

	// remove dummy
    if (jsParentNode._loadingItem != null) {
        jsParentNode._loadingItem._remove();
        bIndent = true;
    }
    if (bIndent) {
		// indent now that all items are added
        jsParentNode.indent();
    }

	// show error in status bar
    if (jsParentNode.errorText != "") {
        window.status = jsParentNode.errorText;
    }
}

// parses a string and replaces %n% with argument nr n
function parseTemplateString(sTemplate) {
    var args = arguments;
    var s = sTemplate;
    s = s.replace(/\%\%/g, "%");
    for (var i = 1; i < args.length; i++) {
        s = s.replace(new RegExp("%" + i + "%", "g"), args[i]);
    }
    return s;
}

