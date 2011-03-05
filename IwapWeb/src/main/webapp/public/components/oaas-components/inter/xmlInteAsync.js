
//remote function call

function IsNumberInt(num)
{
	var myMod = num % 1;
	return (myMod == 0);
}

function parseDate(str)
{
	var arrDate = str.split(/[\/\-: ]/);
	if(arrDate.length<6) throw "Invalid DateTime format";
	var d = new Date(arrDate[0],parseInt(arrDate[1])-1,arrDate[2],arrDate[3],arrDate[4],arrDate[5]);
	if(isNaN(d))
		throw "Invalid DateTime format";
	return d;
}

function dateToString(d)
{
	return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
}

function getObjectType(obj)
{
	//n=null,b,i,f,s,d,o,a
	if(obj == null) return 'n';

	//alert(obj.constructor);
	switch(typeof(obj))//no int
	{
	case "number":
		if(IsNumberInt(obj))
			return 'i';
		else
			return 'f';

	case "boolean":
		return 'b';
	case 'string':
		return 's';
	case 'object':
		if(obj.constructor==Date)
			return 'd';
		else if(obj.constructor==Array)
			return 'a';
		else
			return 'o';
	}
}


function packageObject(elm,type,arg)
{
	switch(type)
	{
	case 'n':
		break;
	case 'b':
	case 'i':
	case 'l':
	case 'f':
	case 's':
		elm.text = arg.toString();break;
	case 'd':
		elm.text = dateToString(arg);break;
	case 'o':
		for(var key in arg)
		{
			var child =arg[key];
			var subtype = getObjectType(child);
			//it's empty deal to its minValue in b/i/f
			var childElm = elm.ownerDocument.createElement(subtype+key);
			elm.appendChild(childElm);
			packageObject(childElm,subtype,child);
		}
		break;
	case 'a':
		for(var i=0;i<arg.length;i++)
		{
			var child =arg[i];
			var subtype = getObjectType(child);
			var childElm = elm.ownerDocument.createElement(subtype+"Item");
			elm.appendChild(childElm);
			packageObject(childElm,subtype,child);
		}
		break;
	}
}



function getObjectFromXml(elm,type)
{
	if(type==null) return null;
	switch(type)
	{
	case 'n':
		return null;
	case 'b':
		return (elm.text=="true");
	case 'i':
	case 'l':
		var val = parseInt(elm.text);
		if(isNaN(val))
			throw elm.tagName+" must be an integer";
		return val;
	case 'f':
		var val = parseFloat(elm.text);
		if(isNaN(val))
			throw elm.tagName+" must be an float";
		return val;
	case 's':
		return elm.text;
	case 'd':
		try{
			return parseDate(elm.text);
		}catch(ex)
		{
			throw elm.tagName+"must be in a DateTime format(yyyy-MM-dd HH:mm:ss)";
		}
	case 'o':
		{
			var obj = new Object();
			var nodes = elm.childNodes;
			for(var i=0;i<nodes.length;i++)
			{
				var child = nodes[i];
				if(child.nodeType==1)//NODE_ELEMENT
				{
					var childtype = child.tagName.charAt(0);
					var key = child.tagName.substring(1);
					obj[key] = getObjectFromXml(child,childtype);
				}
			}
			return  obj;
		}
	case 'a':
		{
			var arr = new Array();
			var nodes = elm.childNodes;
			for(var i=0;i<nodes.length;i++)
			{
				var child = nodes[i];
				if(child.nodeType==1)//NODE_ELEMENT
				{
					var childtype = child.tagName.charAt(0);
					var key = child.tagName.substring(1);
					if(key=="Item")
					{
						arr[arr.length] = getObjectFromXml(child,childtype);
					}
				}
			}
			return arr;
		}
	default:
		throw "type '"+type+"' can't be recognized";
	}
}

function RemoteFunc_oncomplete(retVal,obj)
{
	try{
		var xmlDoc = new ActiveXObject("msxml2.DOMDocument");
		if(!xmlDoc.loadXML(retVal))
			throw "The format of remote server return is not an Xml";

		var docElm = xmlDoc.documentElement;
		switch(docElm.tagName)
		{
		case "Output":
			obj.onComplete(getObjectFromXml(docElm,docElm.getAttribute("type")),obj.state);
			return;
		case "Error":
			throw "Remote server returns error: "+ docElm.text;
		default:
			throw "Remote server returns invalid xml";
		}
	}catch(e)
	{
		if(obj.onError!=null)
		{
			obj.onError("Remote function '"+obj.funcName+"' in service '"+obj.serviceName+"' error:"+e,obj.state);
		}
	}
}



function RemoteFunc_callRemoteFunctionAsync()
{

	if(this.serviceName == null)
		throw "serviceName can't be null";
	var url = g_urlMap[this.serviceName];
	if(url == null)
		throw "unable to locate service '"+this.serviceName+"'";
	if(this.funcName == null)
		throw "funcName can't be null";


	//package arguments
	var xmlDoc = new ActiveXObject("msxml2.DOMDocument");
	var docElm = xmlDoc.appendChild(xmlDoc.createElement("Function"));
	docElm.setAttribute("name",this.funcName);
	docElm.setAttribute("serviceName",this.serviceName);

	var args = (this.args==null?arguments:this.args);
	for(var i=0;i<args.length;i++)
	{
		var arg = args[i];
		var elm = xmlDoc.createElement("Param");
		var type = getObjectType(arg);
		elm.setAttribute("type",type);
		packageObject(elm,type,arg);
		docElm.appendChild(elm);
	}

	//send to remote
	var xmlHttp = new XmlHttp();
	this.xmlHttp = xmlHttp;
	xmlHttp.state = this;
	xmlHttp.onComplete = RemoteFunc_oncomplete;
	xmlHttp.onCancel = RemoteFunc_oncancel;
	xmlHttp.onError = RemoteFunc_onerror;
	xmlHttp.sendXml(url,xmlDoc.xml);
}

function RemoteFunc_oncancel(obj)
{
	if(obj.onCancel!=null)
		obj.onCancel(obj.state);
}

function RemoteFunc_onerror(ex,obj)
{
	if(obj.onError!=null)
		obj.onError(ex,obj.state);
}

function RemoteFunc_cancel()
{
	if(this.xmlHttp!=null)
		this.xmlHttp.cancel(this.state);
}

//class RemoteCall
function RemoteCall()
{
	//need user to input this
	this.serviceName = null;
	this.funcName = null;
	this.onComplete = null;
	this.onError = null;
	this.onCancel = null;
	this.state = null;
	this.args = null;

	//private
	this.xmlHttp = null;

	this.cancel = 	RemoteFunc_cancel;
	this.call = RemoteFunc_callRemoteFunctionAsync;
}
