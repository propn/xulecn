//����ȫ�ֱ���������ϵͳ�ж�
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
����˵����on_unload
����Ҫ��ж�ش���ʱ�Ӹ�������ɾ�������¼��
�����������		
����������ޣ�
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