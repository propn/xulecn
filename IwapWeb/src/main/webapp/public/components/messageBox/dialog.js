/**
	Class Name :Dialog Class
	Create on  :2003.8.19
	Collect By : Lee
	Depend on  : valid Function , SysErr Function
	Exception throws : valid Class is not exists，SysErr is not exixts

	Modified by Akira 2006-01-06
*/
(function()
{
	Dialog = {
		showModelessDialog		: showWebDialog ,
		showModalDialog   : showBlockWebDialog
	};

	function showWebDialog(url, w, h, userArgs) {  //线程非中止方式，不会中断脚本
		
		if(!w) w = 503;
		if(!h) h = 385;
		
		var dwh = "dialogWidth:" + w + "px; dialogHeight:" + h + "px;";
		var ret =  showModelessDialog(url, {wnd:window,userArgs:userArgs}, 
			"status:no;center:yes;help:no;minimize:no;maximize:no;border:thin;" + dwh);
		return ret;
	};

	function showBlockWebDialog(url, w, h, userArgs){    //线程中止方式的对话框会中断脚本 
		
		if(!w) w = 503;
		if(!h) h = 385;
		var dwh = "dialogWidth:" + w + "px; dialogHeight:" + h + "px;";
		var ret = showModalDialog(url, {wnd:window,userArgs:userArgs}, 
			"status:no;center:yes;help:no;minimize:no;maximize:no;border:thin;" + dwh);
		return ret;
	};
})();
	