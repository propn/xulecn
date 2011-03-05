
var STATUS_OK = 200;
var STATUS_CANCEL = -1;

function XmlHttp_sendXmlAsync(url,strXml)
{
	this.xmlHttp = new ActiveXObject("Microsoft.XmlHttp");
	if(strXml==null) this.xmlHttp.open("GET",url,true);
	else this.xmlHttp.open("POST",url,true);
	var index = __findEmptyEntry();
	this.index = index;
	g_arrXmlHttpArray[index] = this;
	this.xmlHttp.onreadystatechange = new Function("__requestDispatch(" + (index) + ");");
	this.xmlHttp.send(strXml);
}


function XmlHttp_cancel()
{
	this.isAbort = true;
	try{
		this.xmlHttp.abort();
	}catch(e)
	{}
	if(this.onCancel!=null)
		this.onCancel(this.state);
}

var g_arrXmlHttpArray = new Array();

function __findEmptyEntry()
{
	for(var i=0;i<g_arrXmlHttpArray;i++)
	{
		if(g_arrXmlHttpArray [i] ==null) return i;
	}
	return g_arrXmlHttpArray.length;
}



function __requestDispatch(iIndex)
{
	var obj = g_arrXmlHttpArray[iIndex];
	if(null != obj && !obj.lockRequest && 4 == obj.xmlHttp.readystate)
	{
		obj.lockRequest = true;
		XmlHttp_onreadystatechange(obj);
		delete g_arrXmlHttpArray[iIndex];
	}
}

function XmlHttp_onreadystatechange(xh)
{
	if(xh.xmlHttp != null  && xh.xmlHttp.readyState == 4 && !xh.isAbort)
	{
		var status = xh.xmlHttp.status;
		try{
			if(status !=STATUS_OK)
				throw "Xml http status return is "+status;
			
			if(xh.onComplete!=null)
				xh.onComplete(xh.xmlHttp.responseText,xh.state);
		}
		catch(e)
		{
			if(xh.onError!=null)
				xh.onError(e,xh.state)
		}
	}
}

//Class XmlHttp
function XmlHttp()
{
//private
	//related to global array
	this.index = null;
	this.lockRequest = false;
	this.isAbort = false;
	
	this.xmlHttp = null;
	this.cancel = XmlHttp_cancel;
	this.sendXml = XmlHttp_sendXmlAsync;

//public
	//event
	this.onComplete = null;
	this.onCancel = null;
	this.onError = null;
	this.state = null;
}
