/*StringBuffer*/
function StringBuffer() {   this.buffer = [];  }
StringBuffer.prototype.append = function(string)
{
	this.buffer.push(string);
	return this;
}
StringBuffer.prototype.toString = function()
{
	return this.buffer.join("");
}

var Buffalo = Class.create();
Buffalo.BOCLASS="_BUFFALO_OBJECT_CLASS_";
Buffalo.ExceptionVO="com.ztesoft.common.exception.ExceptionVO";

Buffalo.prototype = {

	throwError : false, //
	
	initialize: function(gateway, async, events) {
		this.gateway = gateway;
		this.transport = null;
		if (typeof(async) == 'undefined') {
			this.async = true;
		} else {
			this.async = async;
		}
		this.currentCallbackAvailable = false;
		this.currentCallback = new Function();
		this.localURL = "";
		this.setEvents(events);		
	},
	
	getGateway : function() { return this.gateway;},

	setEvents: function(events) {
		this.events = {
			onLoading: Buffalo.Default.showLoading,
			onFinish: new Function(),
			onException: new Function(),
			onError: Buffalo.Default.showError
		};
		Object.extend(this.events, events || {});
	},

	_remoteCall: function(url, burlapCall, callback) {
		this.transport = XmlHttpPool.pick();//XmlHttp.create();//
		old_alert(url + '>>>>>xml========='+burlapCall.xml() )
		this.transport.open((Global.onServer ? "POST" : "GET"), url, this.async);			
		this.transport.send(burlapCall.xml());
		this.currentCallbackAvailable = true;
		this.currentCallback = callback;	
		if (this.async) {
			this.transport.onreadystatechange = this.onStateChange.bind(this);
			this.events["onLoading"](true);
			if(!Global.onServer){
				this.response();
			}
		} else { 
			this.response();
		}
	},

	remoteCall: function(service, params, callback, replyType, fields) {	
		var idx = service.indexOf(".");
		
		var serviceId = service.substring(0,idx);
		var method = service.substring(idx+1,service.length);
		/*
		//old
		var newUrl = this.gateway+"/buffalo/"+serviceId;
		*/	
		//通过nf标志使用bf的worker。通过url参数传递replyType和fields
		var newUrl = this.gateway+"/nf/"+serviceId+"?replyType="+replyType+"&fields="+fields;
				
		var call = new Buffalo.Call(method);
		for (var i = 0; i < params.length; i++) {
			call.addParameter(params[i]);
		}
		this.localURL = serviceId+"."+method+"."+call.xmlString()+".xml";
		if(!Global.onServer){			
			newUrl = LocalAction.getRemoteCallURL(this.localURL);	
		}
		this._remoteCall(newUrl, call, callback);
	},
	
	onStateChange: function(){
		if (this.transport.readyState == 4) {
			this.response();
		}
	},
	
	response : function() {
		if (!Global.onServer || this.transport.status == '200') {
			var data = this.transport.responseText;
			//if (data.indexOf("xmlns:burlap") == -1) {
			//	data.replace("<burlap:reply>", "<burlap:reply xmlns:burlap=\"http://www.amowa.net/buffalo/\">")
			//}
			
			LocalAction.saveResponseData(data, this.localURL);			
			this.events["onLoading"](false);
			this.transport=null;
			
			var reply = new Buffalo.Reply(data, this.throwError);
			
			//没有出错，并且不抛异常的情况下，才执行Callback。
			if(reply.getResult() && reply.getResult().boclass && reply.getResult().boclass==Buffalo.ExceptionVO){
				this.currentCallbackAvailable = false;
				this.currentCallback = null;
			}else{
			    this.currentCallbackAvailable = false;
				this.currentCallback(reply);
				if(!this.currentCallbackAvailable){
					this.currentCallback = null;
				}
			}				
				
			this.events["onFinish"](reply);
			reply.destroy();
		} else {		    
			this.events["onError"](this.transport.responseText);
			this.transport=null;
		}
		
	}

}


Buffalo.Default = {
	loadingPane:null,
	errorPane:null,
	
	showLoading : function(state) {
	  if(Global.disableAsyncRemoteCallProcessingBar==true) return;
		this.loadingPane = $("buffalo_loading");
		
		if (this.loadingPane == null ) {
		    if(!document.body) return false;
		    
			var imgWidth = 240;
			var imgHeith = 100;
			var leftPosition = (document.body.clientWidth-imgWidth)/2;
			var topPosition = (document.body.clientHeight-imgHeith)/2;
			var el = document.createElement('DIV');	
			
			el.setAttribute("id","buffalo_loading");
			el.style.cssText="display:none;z-index: 10000; CURSOR: wait; POSITION: absolute; left:"+leftPosition+"px; top:"+topPosition+"px; width:"+imgWidth+"px; height:"+imgHeith+"px; ";
		    document.body.appendChild(el);
			var flashHtml = "";
			
			flashHtml += "    <table width='100%' height='100%' border='0'>";
			flashHtml += "		 <tr align='center' valign='middle'>";
			flashHtml += "			<td>";
			flashHtml += "			   <table width='240' height='100' border='0' cellpadding='0' cellspacing='0'>";
			flashHtml += "			        <tr>";
			flashHtml += "						<td align='center'>";
			flashHtml += "							<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='240' height='100'>";
			flashHtml += "								<param name='movie' value='"+Global.contextPath+"/public/skins/bsn/loading.swf'>";
			flashHtml += "								<param name='quality' value='high'>";
			flashHtml += "								<param name='wmode' value='transparent'>";
			flashHtml += "								<embed src='"+Global.contextPath+"/public/skins/bsn/loading.swf' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='240' height='100'></embed>";
			flashHtml += "						   </object>";
			flashHtml += "						</td>";
			flashHtml += "			          </tr>";						
			flashHtml += "			     </table>";
			flashHtml += "			   </td>";
			flashHtml += "			</tr>";
			flashHtml += "		</table>";

			el.innerHTML = flashHtml;
		  
		    this.loadingPane = el;
		    el = null;
		    flashHtml = null;
		}
		if (state) {
			this.loadingPane.style.display="block";	
		} else {
			this.loadingPane.style.display="none";			
		}
	},
	
	showError: function(errorStr) {
		this.errorPane = $("buffalo_error");	
	},
	
	showException: function(ex) { /*TODO*/ }
}

function getDomDocumentPrefix() {
	if (getDomDocumentPrefix.prefix)
		return getDomDocumentPrefix.prefix;
	
	var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			// try to create the objects
			o = new ActiveXObject(prefixes[i] + ".DomDocument");
			return getDomDocumentPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}
	
	throw new Error("Could not find an installed XML parser");
}

function getXmlHttpPrefix() {
	if (getXmlHttpPrefix.prefix)
		return getXmlHttpPrefix.prefix;
	
	var prefixes = [ "Microsoft", "MSXML2","MSXML", "MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			// try to create the objects
			o = new ActiveXObject(prefixes[i] + ".XmlHttp");
			return getXmlHttpPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}
	
	throw new Error("Could not find an installed XMLHttp object");
}

function XmlHttp() {}

XmlHttp.create = function () {
	try {
		if (window.ActiveXObject) {
			return new ActiveXObject(getXmlHttpPrefix() + ".XmlHttp");
		}
	}
	catch (ex) {}
	// Fail
	throw new Error("Your browser does not support XmlHttp objects");
};

/*
 * xmlHttp Pool
 * 
 * userage: var xmlhttpObj = XmlHttpPool.pick()
 */
var XmlHttpPoolArr = [];

function XmlHttpPool() {}
XmlHttpPool.pick = function() {
	var xmlhttp = null;
	if(!XmlHttpPoolArr){
		XmlHttpPoolArr = [];
	}
	var size = XmlHttpPoolArr.length;
	for(var i=0; i<size; i++){
	  if(XmlHttpPoolArr[i].readyState == 4 || XmlHttpPoolArr[i].readyState == 0){
	    xmlhttp = XmlHttpPoolArr[i];
        break;
	  }
	}
	if(xmlhttp==null){
	  xmlhttp = XmlHttp.create();
	  XmlHttpPoolArr.push(xmlhttp);
	}	
	return xmlhttp;
}
XmlHttpPool.clear = function(){
    if(XmlHttpPoolArr){
	    var size = XmlHttpPoolArr.length;
	    for(var i=0; i<size; i++){
	      XmlHttpPoolArr[i] = null;
	    }
	    XmlHttpPoolArr = null;
	}
}

function XmlDocument() {}
XmlDocument.create = function () {
	try {
		if (window.ActiveXObject)
			return new ActiveXObject(getDomDocumentPrefix() + ".DomDocument");
	}
	catch (ex) {}
	throw new Error("Your browser does not support XmlDocument objects");
};

Buffalo.Call = Class.create();
Buffalo.Call.prototype = {
	initialize: function(methodname){
		this.method = methodname;
		this.params = [];
	},

	addParameter: function(data){
		if (arguments.length==0) return;
		this.params[this.params.length] = data;
	},

	sbf : new StringBuffer(),
	
	xml: function(){
        sbf = new StringBuffer();
		var xmlstr = "";
		sbf.append("<buffalo-call>");
		sbf.append("<method>").append(this.method).append("</method>");

		//var bDate = new Date();
		for (var i = 0; i < this.params.length; i++) {
		  var data = this.params[i];
		  this.getParamXML(this.dataTypeOf(data),data);
		}
		//var endDate = new Date();

		sbf.append("</buffalo-call>");
        xmlstr = sbf.toString();

		return xmlstr;
	},

	xmlString: function(){
		var xmlarr = [];

		for (var i = 0; i < this.params.length; i++) {
		  var data = this.params[i];
		  if(typeof(data)!="object")
		  	xmlarr.push(data);
		}

		return xmlarr.join("");
	},

	/* Guess the type of an javascript object */
	dataTypeOf: function (o){
    if(o==null) return "null";
		var type = typeof(o);
		type = type.toLowerCase();
		switch(type){
		  case "number":
			if (Math.round(o) == o) type = "i";
			else type = "db";
			break;
		  case "object":
			var con = o.constructor;
			if (con == Date) type = "d";
			else if (con == Array) type = "l";
			else type = "m";
			break;		  
		  case "boolean":
		  	type = "b";
		  	break;
		}
		return type;
	},

	doValueXML: function(type,data){
        var str = data;
		if (typeof(data) == "string") {
			str = str.replace(/&/g,"&amp;");
			str = str.replace(/</g,"&lt;");
			str = str.replace(/>/g,"&gt;");
            type = "s";
			sbf.append("<").append(type).append(">" ).append( str ).append( "</" ).append( type ).append( ">");
		} else {
			sbf.append("<" ).append( type ).append( ">" ).append( data ).append( "</" ).append( type ).append( ">");
		}
		return;
	},

	doBooleanXML:function(data){
		var value = (data==true)?1:0;
        sbf.append("<b>" ).append( value ).append( "</b>");
		return;
	},

	doDateXML: function(data){

        sbf.append("<d>");
        sbf.append(dateToISO8609(data));
        sbf.append("</d>");
        return;
	},

	doArrayXML : function(data){
        sbf.append("<l>");
		sbf.append("<t>" ).append("").append( "</t>");
		sbf.append("<len>" ).append( data.length ).append( "</len>");
		for (var i = 0; i < data.length; i++){
            this.getParamXML(this.dataTypeOf(data[i]),data[i]);
		}
		sbf.append("</l>");
        return;
	},

	doStructXML : function(data){
		var boClass = data[Buffalo.BOCLASS];
		var boType = boClass ? boClass : "java.util.HashMap";
        sbf.append("<m>");
		sbf.append("<t>" ).append( boType ).append( "</t>");

		for (var i in data){
			if (data[i] != boType) {
				if (typeof(data[i]) == "function") continue; /* the function shouldn't transfered. */
				this.getParamXML(this.dataTypeOf(i),i);
				this.getParamXML(this.dataTypeOf(data[i]),data[i]);
			}
		}
		sbf.append("</m>");
        return;
	},

	doNullXML : function() {
       sbf.append("<null></null>");
        return;
	},

	getParamXML: function(type,data){

       switch (type){
			case "d": this.doDateXML(data); break;
			case "l": this.doArrayXML(data); break;
			case "m": this.doStructXML(data); break;
			case "b": this.doBooleanXML(data); break;
			case "null": this.doNullXML(); break;
			case "undefined": this.doNullXML(); break; //from csss
			default: this.doValueXML(type,data); break;
		}
		return ;
	}

}

function dateToISO8609(date){
	var year = new String(date.getYear());
	var month = leadingZero(new String(date.getMonth()+1));
	var day = leadingZero(new String(date.getDate()));
	var time = leadingZero(new String(date.getHours())) + leadingZero(new String(date.getMinutes())) + leadingZero(new String(date.getSeconds()));

	var converted = year+month+day+"T"+time+"Z";
	return converted;
} 

function leadingZero(n){
	if (n.length==1) n = "0" + n;
	return n;
}

Buffalo.Reply = Class.create();
Buffalo.Reply.prototype = {
	initialize: function(sourceXML, throwError) {

		this._source = sourceXML;
				
		this._isFault = false;
		this._type = "null";
		this._objects = [];
		this._objectNodes = [];
		this.throwError = throwError;

		var xmldoc = XmlDocument.create();
		xmldoc.async=false;
		xmldoc.loadXML(sourceXML);

		var root = xmldoc.documentElement;					
		this.dataNode = root.firstChild;
		
		xmldoc = null;
		root = null;

		this._type = this._getType(this.dataNode);
		
		this._result = null;		
	},
	
	destroy: function(){
	  this._source = null;
	  this._objects = null;
	  this._objectNodes = null;
	  this.dataNode = null;
	  this._result = null;
	},
	
	getType: function() { return this._type; },
	
	getResult : function() {
		
	  if(this._result==null){
	  
		    this._result = this.deserialize(this.dataNode);
		    
		  	if(this._result && this._result.boclass && this._result.boclass==Buffalo.ExceptionVO){
				var errVO = this._result;
				var browser=navigator.appName;
				if (browser == "Microsoft Internet Explorer" && document.readyState != "complete") {
					if(jspTaglibErrors)
						jspTaglibErrors[jspTaglibErrors.length]=new JspTaglibError(errVO.errorCode, errVO.errorMessage, errVO.errorResolve, errVO.level, errVO.stackInfo);
				}else{
					//错误信息提示
					var level = errVO.level;
					if(level<3){
						MessageBox.Show(null, "提示信息: "+errVO.errorMessage, null, 'OK', "Warning", 0, null, errVO.stackInfo);
					}else if(level<4){
						MessageBox.Show(null, "错误代码: "+errVO.errorCode+"<br> 错误信息: "+errVO.errorMessage, null, 'LogOK', "Error", 1, null, errVO.stackInfo);
					}else if(level=5){
						MessageBox.Show(null, "错误代码: "+errVO.errorCode+"<br> 错误信息: "+errVO.errorMessage, null, 'YesNo', "Confirm", 1, null, errVO.stackInfo);						
					}else {
						MessageBox.Show(null, "错误代码: "+errVO.errorCode+"<br> 错误信息: "+errVO.errorMessage, null, 'LogOK', "Error", 1, null, errVO.stackInfo);
						//MessageBox.Show(null, "请重新登陆: "+errVO.errorMessage, null, 'LogReIn', "Error", 1, null, errVO.stackInfo);
					}
					
				}
					
				//throwError
				if(this.throwError){
					throw errVO;
				}
				
		  	}
	  }
				    
	  return  this._result;
	},
	
	isFault : function() { return (this._type == "fault"); },
	
	isNull: function() { return (this._type == "null"); },
	
	getSource : function() { return this._source; },
	
	deserialize: function(dataNode) {

		var ret;
		type = this._getType(dataNode);
	
		switch (type) {
			case "b": ret = this.doBoolean(dataNode); break;
			case "d": ret = this.doDate(dataNode); break;
			case "db": ret = this.doDouble(dataNode); break;
			case "i":
			case "lg":
				ret = this.doInt(dataNode);
				break;
			case "l": ret = this.doList(dataNode); break;
			case "m": ret = this.doMap(dataNode); break;
			case "null": ret = this.doNull(dataNode); break;
			case "ref": ret = this.doRef(dataNode); break;
			case "s": ret = this.doString(dataNode);break;
			case "xml": ret = this.doXML(dataNode); break;
			case "fault": ret = this.doFault(dataNode); break;
			default: ;
		}

		return ret;
	},
	
	_getType : function(dataNode) {
		return dataNode.tagName.toLowerCase();
	},
	
	getNodeText :function(dataNode) {
		if (dataNode.childNodes.length == 0) {
			return null;
		} else 
			return dataNode.firstChild.nodeValue;
	},

	doBoolean : function (dataNode) {
		var value = this.getNodeText(dataNode);
		return (value == "1");
	},
	
	doDate : function (dataNode) {

		var dateStr = this.getNodeText(dataNode);
		var year = parseInt(dateStr.substring(0,4),"10");
		var month = parseInt(dateStr.substring(4,6),"10") - 1;
		var day = parseInt(dateStr.substring(6,8),"10");
		var hour = parseInt(dateStr.substring(9,11),"10");
		var minute = parseInt(dateStr.substring(11,13),"10");
		var second = parseInt(dateStr.substring(13,15),"10");
		
		var d = new Date(year, month, day, hour, minute, second);
		return d;
	},
	
	doDouble : function (dataNode) {
		var value = this.getNodeText(dataNode);
		return parseFloat(value);
	},
	
	doInt: function (dataNode) {
		var value = this.getNodeText(dataNode);
		return parseInt(value);
	},
	
	doList: function (dataNode) {
		var arr = [];
		this._objects[this._objects.length] = arr;
		var children = dataNode.childNodes;
		for (var i=2; i < children.length; i++) {
			arr[arr.length] = this.deserialize(children[i]);
		}

		return arr;
	},

	doMap: function (dataNode) {
	
		var obj = {};
		this._objects[this._objects.length] = obj;

		var attrs = dataNode.childNodes;
		
		if(attrs.length>0)
		  obj.boclass = attrs[0].text;		
		for (var i = 1; i < attrs.length; i+=2) {
			if (attrs[i+1].hasChildNodes() ) {
				obj[this.getNodeText(attrs[i])] = this.deserialize(attrs[i+1]);
			} else {
				obj[this.getNodeText(attrs[i])] = attrs[i+1].text;
			}
		}
		
		return obj;
	},
	
	doNull: function (dataNode) { return null;	},
	
	doRef: function (dataNode) {
		var value = this.getNodeText(dataNode);
		var idx = parseInt(value);
		
		return this._objects[idx];
	},
	
	doString: function (dataNode) {
		var value = this.getNodeText(dataNode);
		if (value == null) {
			return "";
		}
		return (value);
	},

	doXML : function (dataNode) {
		var value = this.getNodeText(dataNode);
        value = unescape(value);
        //针对dataset类型，返回xml整个records节点。
		if(value==null || value=="null"){

		  if(dataNode.firstChild && dataNode.firstChild.tagName == "records")
		    value = dataNode.firstChild.xml;
		    
		}
		return value;
	},
	
	doFault : function (dataNode) {
		var code = this.getNodeText(dataNode.childNodes[1]);
		var msg = this.getNodeText(dataNode.childNodes[3]);
		var detail = this.deserialize(dataNode.childNodes[5]);
		return new Buffalo.Fault(code, msg, detail);
	}
}

var NDAjaxCall = Class.create();

NDAjaxCall.prototype = {

	ajaxObj : null,
	async : null,
	throwError : false, 

	initialize: function(async) {
		
		if(typeof(async)=="undefined") async = true;
		this.async = async;
		this.ajaxObj = new Buffalo(Global.serverPath, async);
		
	},

	_remoteCall : function (serviceName, methodName, params, callBack, replyType, fields){   
	    //构造replyType参数，有两个类型 （vo 或者 dataset ） 如果replyType没有设置，默认成vo。
	    if(replyType==undefined || replyType == null ||  replyType == "")
	    	replyType="vo";
	    
	    //构造fields参数	分隔符 ,
	    var fieldsStr = "";
	    
	    if(fields == undefined){
	    	fieldsStr = "";
	    }else if(fields.length != undefined && fields.length != 0){
	    	fieldsStr = fields.join(",");
		}
		
		if(this.throwError != undefined){
			this.ajaxObj.throwError = this.throwError;
		}   
		
		this.ajaxObj.remoteCall(serviceName+"."+methodName, params, callBack, replyType, fieldsStr); 	  		
	},	
	
	setThrowError : function (throwErr){
		this.throwError = throwErr;
		return this;
	}
	
}


NDAjaxCall.prototype.remoteCall = function(serviceName, methodName, params, callBack, replyType, fields) {
  return NDAjaxCall.__remoteCall(this, serviceName, methodName, params, callBack, replyType, fields);
}
NDAjaxCall.__remoteCall = function(ndajax, serviceName, methodName, params, callBack, replyType, fields) {
  var browser=navigator.appName;
  if (System.isTrue(ndajax.async) && browser == "Microsoft Internet Explorer" && document.readyState != "complete") { 
    window.setTimeout (function(){ NDAjaxCall.__remoteCall(ndajax, serviceName, methodName, params, callBack, replyType, fields); }, 5);
    return false;
  }
  return ndajax._remoteCall(serviceName, methodName, params, callBack, replyType, fields);
}	


NDAjaxCall.getAsyncInstance = function(){
	return new NDAjaxCall(true);
}

NDAjaxCall.getSyncInstance = function(){
	return new NDAjaxCall(false);
}
