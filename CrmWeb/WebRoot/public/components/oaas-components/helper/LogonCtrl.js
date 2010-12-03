//传入全局变量用来做系统判断
var g_StaffDto=null;
if (window.opener!=null)
{
	g_StaffDto=window.opener.g_StaffDto;
}

if (g_StaffDto==null||g_StaffDto.isLogon!="yes")
{
	window.opener=null;
	window.close();
}

/*************************************************************************
函数说明：on_unload
函数要求：卸载窗体时从父窗体中删除窗体记录。
输入参数：无		
输出参数：无；
************************************************************************/
function on_unload()
{
	/*var ss=window.name.split("_");
	var menuId=ss[ss.length-1];//
	eval("window.parent.g_"+menuId+"=0");*/
}

/*function DelCookie(sName,sValue)
{
	document.cookie = sName + "=" + escape(sValue) + "; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
}

function SetCookie(sName,sValue)
{
	var date=new Date();
	document.cookie =sName+"="+escape(sValue)+";expires="+date.toGMTString();
}*/