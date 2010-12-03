/**
*  js消息框基类，用来显示增强的js消息框
*  目前的封装比较依赖于模板的形式，而且美工采用了table作显示，有待改进
*  详细使用例子见samples/MessageBox
*
*	Created on 2005-08-02
*   Last Modified 2006-01-05
*	Author: Akira
**/



(function(){ 

	var imagePath = "../../skins/"+themeId+"/messageBox/";   //MessageBox的图片路径
	var template = path_prefix+"public/components/messageBox/messageBox_Template.htm"; //MessageBox的模板路经

	MessageBox = {
		Show : Show,
		setImagePath : function(path){imagePath = path;},
		setTemplate : function(temp){template = temp;}
	}	

	var Buttons = new Array();
	Buttons["OK"] = {text:"确定",action:"MessageBoxAction.MB_OK()"};
	Buttons["Cancel"] = {text:"取消",action:"MessageBoxAction.MB_Cancel()"};
	Buttons["Retry"] = {text:"重试",action:"MessageBoxAction.MB_Retry()"};
	Buttons["Abort"] = {text:"中止",action:"MessageBoxAction.MB_Abort()"};
	Buttons["Ignore"] = {text:"忽略",action:"MessageBoxAction.MB_Ignore()"};
	Buttons["Yes"] = {text:"是",action:"MessageBoxAction.MB_Yes()"};
	Buttons["No"] = {text:"否",action:"MessageBoxAction.MB_No()"};
	Buttons["Log"] = {text:"详细信息", action:"MessageBoxAction.MB_Log()"};
	
	var Icons = new Array();
	Icons["Error"] = "info_error.gif";
	Icons["Warning"] = "info_advise.gif";
	Icons["Asterisk"] = "info_ask.gif";
	Icons["Information"] = "info_cue.gif";
	Icons["Confirm"] = "info_answer.gif";

	DialogResult = 
	{
		OK : "OK",
		Cancel : "Cancel",
		Retry : "Retry",
		Abort : "Abort",
		Ignore : "Ignore",
		Yes : "Yes",
		No : "No"
	}

	MessageBoxButtons = {
		AbortRetryIgnore : __display_Button([Buttons.Abort,Buttons.Retry,Buttons.Ignore]),
		OK : __display_Button([Buttons.OK]),
		OKCancel : __display_Button([Buttons.OK,Buttons.Cancel]),
		RetryCancel : __display_Button([Buttons.Retry, Buttons.Cancel]),
		YesNo : __display_Button([Buttons.Yes, Buttons.No]),
		YesNoCancel : __display_Button([Buttons.Yes, Buttons.No, Buttons.Cancel]),
		LogOK : __display_Button([Buttons.Log, Buttons.OK])
	}
	
	MessageBoxAction = {
		MB_OK : MB_OK,
		MB_Cancel : MB_Cancel,
		MB_Retry : MB_Retry,
		MB_Abort : MB_Abort,
		MB_Ignore : MB_Ignore,
		MB_Yes : MB_Yes,
		MB_No : MB_No,
		MB_Log : MB_Log,
		MB_TIMEOUT : MB_TIMEOUT
	}
	function MB_TIMEOUT(){
		window.returnValue = null;
		self.close();
	}
	
	function MB_Log()
	{
		if(self.__showLog == true)
		{
			self.dialogHeight = parseInt(self.dialogHeight) - 250 + 'px';
			self.__showLog = false;
		}
		else
		{
			self.dialogHeight = parseInt(self.dialogHeight) + 250 + 'px';
			self.__showLog = true;
		}
	}
	function MB_OK()
	{
		window.returnValue = DialogResult.OK;
		self.close();
	}
	function MB_Cancel()
	{
		window.returnValue = DialogResult.Cancel;
		self.close();
	}
	function MB_Retry()
	{
		window.returnValue = DialogResult.Retry;
		self.close();
	}
	function MB_Abort()
	{
		window.returnValue = DialogResult.Abort;
		self.close();
	}
	function MB_Ignore()
	{
		window.returnValue = DialogResult.Ignore;
		self.close();
	}
	function MB_Yes()
	{
		window.returnValue = DialogResult.Yes;
		self.close();
	}
	function MB_No()
	{
		window.returnValue = DialogResult.No;
		self.close();
	}
	function setTemplate(template)
	{
		template = template;
	}
	function Show()
	{
		if(arguments.length > 1)
			return __full_Show.apply(null,arguments);
		else
			return __text_Show.apply(null,arguments);
	}
	
	function __full_Show(owner, text, caption, buttons, icon, defaultButton, info, log, jspTaglibErrors)
	{
		if(buttons == null) buttons = "OK";
		if(icon == null) icon = "Confirm";
		if(Icons[icon] != null) icon = imagePath + Icons[icon];

		var url = template;
		
		if(owner == null)
			return Dialog.showModalDialog(url,435,204,
			{text:text,caption:caption,buttons:buttons,icon:icon,defaultButton:defaultButton,opener:self,info:info,log:log,jspTaglibErrors:jspTaglibErrors});
		else
			return owner.Dialog.showModalDialog(url,435,204,
			{text:text,caption:caption,buttons:buttons,icon:icon,defaultButton:defaultButton,opener:self,info:info,log:log,jspTaglibErrors:jspTaglibErrors});
	}

	function __text_Show(text)
	{
		__full_Show(null, text);
	}

	function __display_Button(buttons)
	{
		var buttonStr = "";
		for(var i = 0; i < buttons.length; i++)
		{
			buttonStr += "<td align='center' name='__msgbox_button' class='button'";

			if(buttons[i].action != null)
				buttonStr += " onclick=\""+buttons[i].action+"\" ";
			if(buttons[i].text.length <= 2)
			{
				buttonStr += " width='34px' ";
			}
			buttonStr += ">"+buttons[i].text+"</td>";

			if(i < buttons.length - 1)
				buttonStr += "<td width='14'>&nbsp;</td>";
		}
		return buttonStr;
	}
})();

