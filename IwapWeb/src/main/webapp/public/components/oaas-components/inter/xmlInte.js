//add by shim 异步调用
var xmlHttpResp = '';
//返回的数据
var returnXml = '';
//错误信息
var p_errorMsg = '';
function IsNumberInt(num)
{
	var myMod = num % 1;
	return (myMod == 0);
}

function parseDate(str)
{
	var arrDate = str.split(/[\/\-: ]/);
	if(arrDate.length<6) throw "Invalid DateTime format";

	if(arrDate[1].indexOf('0')==0)
	{
		arrDate[1]=arrDate[1].substr(1,1);
	}
	var tempMonth=parseInt(arrDate[1],10);
	var d = new Date(arrDate[0],tempMonth-1,arrDate[2],arrDate[3],arrDate[4],arrDate[5]);

	if(isNaN(d))
		throw "Invalid DateTime format";
	return d;
}
/*
	2005-07-05 修改 shiming
	返回格式化的日期时间格式；由'2005-5-6 1:12:2'改成'2005-05-06 01:12:02'
*/
function dateToString(d)
{
	if(d==null) return "";
	//return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	var strMonth=getFormatDateTime(d.getMonth()+1);
	var strDay=getFormatDateTime(d.getDate());
	var  strHours=getFormatDateTime(d.getHours());
	var strMinutes=getFormatDateTime(d.getMinutes());
	var strSeconds=getFormatDateTime(d.getSeconds());
	strSeconds=strSeconds.substr(strSeconds.length-2,2);
	return d.getFullYear()+"-"+strMonth+"-"+strDay+" "+strHours+":"+strMinutes+":"+strSeconds;
}

/*
	add by xu.yiliang 20050914
	返回格式化的日期时间格式；由'2005-5-6 1:12:2'改成'2005-05-06 01:12:02', 如果参数为空，返回空字串
*/
function getFormatDate(d)
{
	if(d == null) return "";
	var strMonth=getFormatDateTime(d.getMonth()+1);
	var strDay=getFormatDateTime(d.getDate());
	var  strHours=getFormatDateTime(d.getHours());
	var strMinutes=getFormatDateTime(d.getMinutes());
	var strSeconds=getFormatDateTime(d.getSeconds());
	strSeconds=strSeconds.substr(strSeconds.length-2,2);
	return d.getFullYear()+"-"+strMonth+"-"+strDay+" "+strHours+":"+strMinutes+":"+strSeconds;
}
//获取两位的时间格式
function getFormatDateTime(strDate)
{
	var result='00'+strDate;
	return result.substr(result.length-2,2);
}

function getObjectType(obj)
{
	//n=null,b,i,f,s,d,o,a
	if(obj == null) return 'n';

	//alert(obj.constructor);
	switch(obj.typeName)
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
	case 'date':
		return 'd';
	case 'array':
		return 'a';
	default:
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
			if (key == 'typeName') continue;
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
	case 'B':
		return (elm.text=="true");
	case 'i':
	case 'I':
	case 'l':
	case 'L':
		var val = parseInt(elm.text);
		if(isNaN(val))
			throw elm.tagName+" must be an integer";
		return val;
	case 'f':
	case 'F':
		var val = parseFloat(elm.text);
		if(isNaN(val))
			throw elm.tagName+" must be an float";
		return val;
	case 'u':
	case 'U':
		var val = parseFloat(elm.text);
		if(isNaN(val))
			throw elm.tagName+" must be an double";
		return val;
	case 's':
		return elm.text;
	case 'd':
	case 'D':
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
	case 'v':
		return null;
	default:
		throw "type '"+type+"' can't be recognized";
	}
}

function sendXml(url,strXml)
{
	var xmlHttp = new ActiveXObject("Microsoft.XmlHttp");
	xmlHttp.open("POST",url,false);
	xmlHttp.send(strXml);
	if(xmlHttp.status!=200)
		throw "Network issue or remote server issue";
	else
		return xmlHttp.responseText;
}

function callRemoteFunction(serviceName,funcName)
{

    //var url = g_urlMap;
    //var url = "http://127.0.0.1:8082/BSNWeb/busifacadeservlet";
    var url = g_baseUrlDomain;
	/*var url = g_urlMap[serviceName];
	if(url == null)
		throw "unable to locate service '"+sericeName+"'";
	if(funcName == null)
		throw "funcName must exist";*/


	//package arguments
	var xmlDoc = new ActiveXObject("msxml2.DOMDocument");
	var pi = xmlDoc.createProcessingInstruction("xml", "version=\"1.0\"");
	xmlDoc.appendChild(pi);

	var docElm = xmlDoc.appendChild(xmlDoc.createElement("Function"));
	docElm.setAttribute("name",funcName);
	docElm.setAttribute("serviceName",serviceName);

	for(var i=2;i<arguments.length;i++)
	{
		var arg = arguments[i];
		var elm = xmlDoc.createElement("Param");
		var type = getObjectType(arg);
		elm.setAttribute("type",type);
		packageObject(elm,type,arg);
		docElm.appendChild(elm);
	}

	//send to remote
	var retVal = sendXml(url,xmlDoc.xml).trim();
	//alert(retVal);
	//unpackage return value
	xmlDoc = new ActiveXObject("msxml2.DOMDocument");
	if(!xmlDoc.loadXML(retVal))
		throw "The format of remote server return is not an Xml";

	docElm = xmlDoc.documentElement;

	switch(docElm.tagName)
	{
	case "Output":
		return getObjectFromXml(docElm,docElm.getAttribute("type"));
	case "Error":

	    throw unpackageException(xmlDoc);
		//throw "Remote server returns error: "+ xmlDoc.text;
	default:
		throw "Remote server returns invalid xml";
	}
}

//add by shim 异步发送请求
function sendXmlAsync(url,strXml)
{
	xmlHttpResp = new ActiveXObject("Microsoft.XmlHttp");
	xmlHttpResp.open("POST",url,true);
	xmlHttpResp.onreadystatechange= HandleStateChange;
	xmlHttpResp.send(strXml);
}
//处理返回结果
function HandleStateChange()
{
	try
	{
	if (xmlHttpResp.readyState == 4)
	{
	  if(xmlHttpResp.status==200)
  	  {
		var retVal = xmlHttpResp.responseText;
		//alert(retVal);
		xmlDoc = new ActiveXObject("msxml2.DOMDocument");
		if(!xmlDoc.loadXML(retVal))
			throw "The format of remote server return is not an Xml";
		docElm = xmlDoc.documentElement;
		switch(docElm.tagName)
		{
		case "Output":
		{
			returnXml = getObjectFromXml(docElm,docElm.getAttribute("type"));
			//alert(returnXml);
			AsycOper();
			return;
		}
		case "Error":
		{
		 	try
		 	{
			    throw unpackageException(xmlDoc);
			} catch(e)
			{
				ErrorHandle("",1, 1, p_errorMsg, e);
				document.all.wait_message.style.visibility="hidden";
				return;
			}
		}
			//throw "Remote server returns error: "+ xmlDoc.text;
		default:
			throw "Remote server returns invalid xml";
		}
	    }
	}
	}
	catch(e)
	{
		ErrorHandle("",1, 1, p_errorMsg, e);
	}
}
// add by shim 异步调用
function callRemoteFunctionAsync(serviceName,funcName)
{

    //var url = g_urlMap;
    //var url = "http://127.0.0.1:8082/BSNWeb/busifacadeservlet";
    var url = g_baseUrlDomain;
	/*var url = g_urlMap[serviceName];
	if(url == null)
		throw "unable to locate service '"+sericeName+"'";
	if(funcName == null)
		throw "funcName must exist";*/


	//package arguments
	var xmlDoc = new ActiveXObject("msxml2.DOMDocument");
	var pi = xmlDoc.createProcessingInstruction("xml", "version=\"1.0\"");
	xmlDoc.appendChild(pi);

	var docElm = xmlDoc.appendChild(xmlDoc.createElement("Function"));
	docElm.setAttribute("name",funcName);
	docElm.setAttribute("serviceName",serviceName);

	for(var i=2;i<arguments.length;i++)
	{
		var arg = arguments[i];
		var elm = xmlDoc.createElement("Param");
		var type = getObjectType(arg);
		elm.setAttribute("type",type);
		packageObject(elm,type,arg);
		docElm.appendChild(elm);
	}

	//send to remote
	 sendXmlAsync(url,xmlDoc.xml);


}

function exception() {
	this.ID;
        this.Time;
        this.Type;
	this.Code;
	this.Desc;
	this.Trace;
}

function unpackageException(xmlDoc) {

	var ex   = new exception();

	ex.ID    = xmlDoc.documentElement.childNodes(0).text;
        ex.Time  = xmlDoc.documentElement.childNodes(1).text;
        ex.Type  = xmlDoc.documentElement.childNodes(2).text;
	ex.Code  = xmlDoc.documentElement.childNodes(3).text;
	ex.Desc  = xmlDoc.documentElement.childNodes(4).text;
	ex.Trace = xmlDoc.documentElement.childNodes(5).text;

	return ex;
}

/************************************************************
函数名称:callRemoteHostFunction()
函数功能:指定地址调用远程服务
输入参数:hostUrl 指定地址
       serviceName 服务名
       funcName 方法名
输出参数:xml
返 回 值:无
函数说明：add by yingrui 2006-02-22
************************************************************/
function callRemoteHostFunction(hostUrl,serviceName,funcName){
        var url = hostUrl;
	var xmlDoc = new ActiveXObject("msxml2.DOMDocument");
	var pi = xmlDoc.createProcessingInstruction("xml", "version=\"1.0\"");
	xmlDoc.appendChild(pi);

	var docElm = xmlDoc.appendChild(xmlDoc.createElement("Function"));
	docElm.setAttribute("name",funcName);
	docElm.setAttribute("serviceName",serviceName);

	for(var i=2;i<arguments.length;i++)
	{
		var arg = arguments[i];
		var elm = xmlDoc.createElement("Param");
		var type = getObjectType(arg);
		elm.setAttribute("type",type);
		packageObject(elm,type,arg);
		docElm.appendChild(elm);
	}

	//send to remote
	var retVal = sendXml(url,xmlDoc.xml).trim();
	//alert(retVal);
	//unpackage return value
	xmlDoc = new ActiveXObject("msxml2.DOMDocument");
	if(!xmlDoc.loadXML(retVal))
		throw "The format of remote server return is not an Xml";

	docElm = xmlDoc.documentElement;

	switch(docElm.tagName)
	{
	case "Output":
		return getObjectFromXml(docElm,docElm.getAttribute("type"));
	case "Error":

	    throw unpackageException(xmlDoc);
		//throw "Remote server returns error: "+ xmlDoc.text;
	default:
		throw "Remote server returns invalid xml";
	}
}
