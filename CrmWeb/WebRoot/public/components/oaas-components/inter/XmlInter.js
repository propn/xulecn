
function IsNumberInt(num)
{
	var myMod = num % 1;
	return (myMod == 0);
}

function parseDate(str)
{
	/*
	var arrDate = str.split(/[\/\-: ]/);
	if(arrDate.length<6) throw "Invalid DateTime format";
	var d = new Date(arrDate[0],parseInt(arrDate[1])-1,arrDate[2],arrDate[3],arrDate[4],arrDate[5]);
	if(isNaN(d))
		throw "Invalid DateTime format";
	return d;	
	*/	
	return str;	
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
		if ((elm.text=="True")||(elm.text=="true"))
		{
			return true;
		}
		else
		{
			return false;
		}
	case 'i':
	case 'I':
	case 'l':
	case 'L':
	    if (elm.text == "") return null;
		var val = parseInt(elm.text);
		if(isNaN(val))				
			throw elm.tagName+" must be an integer";
		return val;
	case 'f':
	case 'F':
		if(elm.text=="") return null;
		var val = parseFloat(elm.text);
		if(isNaN(val))				
			throw elm.tagName+" must be an float";
		return val;
	case 's':
		return elm.text;
	case 'd':
	case 'D':
		try{
			if (elm.text=="")
			{
				return null;
			}
			else
			{
				return parseDate(elm.text);
			}
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
	{
		//throw "Network issue or remote server issue";
		//throw "�����̨��δ������Ӧ��״ֵ̬��" + xmlHttp.status;
		//alert(xmlHttp.responseText.toString());
		var oException = new Object();		
		oException.ErrorType = 1;		
		oException.ErrorTime = dateToString(new Date());
		oException.ErrorCode = "C-PUB-00001";
		oException.Message="Network issue or remote server issue";
		oException.ErrorMessage = "xmlHttp.status:"+xmlHttp.status;
		oException.StackTrace=xmlHttp.responseText.toString();
		oException.InnerExceptionDto=new Object();
		throw oException;
	}
	else
	{
		return xmlHttp.responseText;
	}
	
}


/* ͨ��Զ�̷�����ú���
************************************************************
function callRemoteFunction(serviceName,functionName,arguements)
������
		serviceName������ͨ��serviceName�ҵ���Ӧ�ķ���URL
		functionName��Ҫ���õ�Զ�̺�������
		arguments: ��̬����������Զ�̷�����������
���أ�	����Զ�̷���ķ��ض���
����	ͨ���׳����
		ErrorID������Ψһ��ʾ
		ErrorCode���������
		ErrorDescription����������
		Solution���������
�����������£�
		������� -> ������� -> ͨ��serviceName�ҵ�Զ�̷����URL -> 
		�������󣬲��ȴ���Ӧ -> �ж���Ӧ��ȷ�� -> �������ֵ���γɶ��󣬲�����
************************************************************  
*/
	
function callRemoteFunction(serviceName,funcName)
{
	var url = g_urlMap[serviceName];
	if(url == null)
		throw "unable to locate service '"+serviceName+"'";
	if(funcName == null)
		throw "funcName must exist";
		
		
	//package arguments
	var xmlDoc = new ActiveXObject("msxml2.DOMDocument");		
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
	var retVal = sendXml(url,xmlDoc.xml);
	
	//alert(retVal);
	//unpackage return value		
	xmlDoc = new ActiveXObject("msxml2.DOMDocument");
	if(!xmlDoc.loadXML(retVal))
		throw "The format of remote server return is not an Xml";
	
	docElm = xmlDoc.documentElement;
	switch(docElm.tagName)
	{
		case "Output":
			{
				return getObjectFromXml(docElm,docElm.getAttribute("type"));
			}
		case "Error":
			{
				//throw "Remote server returns error: "+ docElm.text;
				//throw "��̨���صĴ�����Ϣ����: "+docElm.text;
				if (docElm.getAttribute("type") == null)
				{
					//���ݾɷ�ʽ�Ĵ���
					throw docElm.text;
				}
				else
				{
					//�µ��쳣���ƵĴ���
					throw getObjectFromXml(docElm,docElm.getAttribute("type"));			
				}
			}		
		default:
			{
				//throw "��̨������Ч��xml";
				throw "Remote server returns invalid xml";
			}
	}
}	
