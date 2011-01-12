   function callRemoteFunction(serviceName,funcName){
	   	var xmlDoc = new ActiveXObject("msxml2.DOMDocument");
		  URL="/EOMSPROJ/exBuizLogic.do";
			var docElm = xmlDoc.appendChild(xmlDoc.createElement("Function"));
			docElm.setAttribute("serviceName",serviceName);

			docElm.setAttribute("name",funcName);
			for(var i=2;i<arguments.length;i++)
			{
				var arg = arguments[i];
				var elm = xmlDoc.createElement("Param");
				var type = getObjectType(arg);
				elm.setAttribute("type",type);
				packageObject(elm,type,arg);
				docElm.appendChild(elm);
			}


			if(URL==null){
			}
			var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			xmlhttp.Open("POST",URL,false);

			xmlhttp.Send(xmlDoc.xml)
                       //alert(xmlDoc.xml);
			if (xmlhttp.status==200){
	       	retVal= xmlhttp.responseText;
	       	//unpackage return value

					xmlDoc = new ActiveXObject("msxml2.DOMDocument");
					xmlDoc.async = false;
					if(!xmlDoc.loadXML(retVal))
						throw "The format of remote server return is not an Xml";

					docElm = xmlDoc.documentElement;

					//alert(xmlDoc.xml);
					switch(docElm.tagName)
					{
						case "Output":

							var outputobj= getObjectFromXml(docElm,docElm.getAttribute("type"));

							return outputobj;
						case "Error":
							throw "Remote server returns error: "+ docElm.text;
						default:
							throw "Remote server returns invalid xml";
					}
			}else{
				alert("htc report...\n\nxmlhttp.status:"+xmlhttp.status +"\n\n\nxmlhttp.responseText:"+xmlhttp.responseText)
				return null;
			}

  }

  <!--private -->

  function IsNumberInt(num)
{
	var myMod = num % 1;
	return (myMod == 0);
}

function parseDate(str)
{
	var arrDate = str.split(/[\/\-: ]/);
	if(arrDate.length<6) throw "Invalid DateTime format";
	var d = new Date(arrDate[0],parseInt(arrDate[1]-1),arrDate[2],arrDate[3],arrDate[4],arrDate[5]);
	if(isNaN(d))
		throw "Invalid DateTime format";
	return d;
}
function parseDateStr(str){
  var yearStr =	str.substring(0,4);
  var monthStr  = str.substring(5,7);
  var dayStr  =  str.substring(8,10);
  var dDay = new Date(yearStr,monthStr-1,dayStr);
   return dDay;
}


function dateToString(d)
{
	return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
}

function getObjectType(obj)
{
	//n=null,b,i,f,s,d,o,a
	if(obj == null) return 'n';
  if(obj.constructor.toString().indexOf("Date")>0) return 'd';
    if(obj.constructor.toString().indexOf("Array")>0) return 'a';
	switch(obj.constructor)
	{
	case Number:
		if(IsNumberInt(obj))
			return 'i';
		else
			return 'f';
			break;
	case Boolean:
		return 'b';
		break;
	case String:
		return 's';
		break;
	case Date:

		return 'd';
		break;
	case Array:
		return 'a';
		break;
	default:
		return 'o';
		break;
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
	case 's':
		return elm.text;
	case 'S':
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

/* 通过设置返回参数对象

参数�\uFFFDParameter.name
返回:
 Parameter 对象
    .name
    .value
    .comments
测试代码:
  var returnValue = getParameterObj("com.ztesoft.eoms.faultmanager.flowId");
  alert(returnValue.value);
  alert(returnValue.comments);
 */
function getParameterObj(paramName){
  return callRemoteFunction("com.zterc.uos.client.ParameterManager","findParameter",paramName);
}
