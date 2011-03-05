/******************************************************************
函数说明：根据传入的错误信息弹出对话页面
函数名称：ErrorHandle
传入参数：
		title：          显示的标题, type = 5 时,才有效;其他类型，可传空值(null)
		type：           页面类型(1/2/3/4/5)，其中 1： 错误  2：告警  3：询问 4:提示 5：自定义
		button_array：   按钮数组,type = 5 时,才有效;其他类型，可传空值(null)
		content_name：   弹出页面显示的内容
		content_message：弹出页面显示的详细内容(type=2/3/4，此项为空)
输出参数:
		无；
返回值：
		type = 3 或 5 ,会有返回值
**********************************************************************/
function ErrorHandle(title,type,button_array,content_name,e)
{
	var returnValue = "";
	var errorMessage = new Object();
	errorMessage.type = type;
	errorMessage.content_name = content_name;
	errorMessage.content_message = e;
	errorMessage.language=g_GlobalInfo.Language;
	errorMessage.charset=g_GlobalInfo.Charset;

	//后台异常优先处理
	//后台异常的处理
	if (e != null)
	{
		if (e.typeName == "object")
		{
			//取得最靠近底层的那个InnerExceptionDto的上一层
			var strace=e.StackTrace;
			var errObj=e;
			if (errObj.InnerExceptionDto==null)
			{
				errObj.InnerExceptionDto=new Object();
			}
			while(errObj.InnerExceptionDto.InnerExceptionDto!=null)
			{
				errObj=errObj.InnerExceptionDto;
			}

			var oException = errObj;
			//switch(oException.ErrorType)

                        switch(oException.Type/1)
			{
				case 1:
					{
						//内部异常,使用“错误”显示
						errorMessage.type = 1;
						errorMessage.content_message ="<table width='100%' border='0'  cellpadding='2' cellspacing='2'>";
						/*
						if (oException.InnerExceptionDto.ErrorTime!=null)
						{
							errorMessage.content_message +="<tr><td width='20%'>Error Time:</td><td>" +oException.InnerExceptionDto.ErrorTime+"</td></tr>";
						}
						else
						{
							errorMessage.content_message +="<tr><td>Error Time:</td><td>" +oException.ErrorTime +"</td></tr>";
						}
						*/
						if (oException.InnerExceptionDto.ErrorCode!=null)
						{
							errorMessage.content_message +="<tr><td width='25%' height='22'>Error Code:</td><td>"+oException.InnerExceptionDto.ErrorCode+"</td></tr>";
						}
						else
						{
							errorMessage.content_message +="<tr><td height='22'>Error Code:</td><td>"+((oException.ErrorCode==null) ? "":oException.ErrorCode)+"\n";
						}

						if (oException.InnerExceptionDto.ErrorMessage!=null)
						{
							errorMessage.content_message +="<tr><td height='22'>Error Message:</td><td>"+oException.InnerExceptionDto.ErrorMessage+"</td></tr>";
						}
						else
						{
							errorMessage.content_message +="<tr><td height='22'>Error Message:</td><td>"+oException.ErrorMessage+"</td></tr>";
						}

						if (oException.InnerExceptionDto.Message!=null)
						{
							errorMessage.content_message +="<tr><td height='22'>Message:</td><td>"+oException.InnerExceptionDto.Message+"</td></tr>";
						}
						else
						{
							errorMessage.content_message +="<tr><td height='22'>Message:</td><td>"+oException.Message+"</td></tr>";
						}
						errorMessage.content_message +="</table>";

						if (strace!=null)
						{
							errorMessage.stack_trace =strace;
						}
						else
						{
							errorMessage.stack_trace =oException.InnerExceptionDto.StackTrace;
						}
						break;
					}
				case 2:
					{
						//告警
						errorMessage.type = 2;

						if (oException.InnerExceptionDto.Message!=null)
						{
							errorMessage.content_name =oException.InnerExceptionDto.Message;
						}
						else
						{
							if (oException.Code == null)
								oException.Code = "";
							errorMessage.content_name =oException.Time+'\r\n'
								 + oException.Code 
								 + ":"
								 + oException.Desc;
						}

						errorMessage.content_message = oException.Trace;
						break;
					}
				case 3:
					{
						//询问
						errorMessage.type = 3;
						if (oException.InnerExceptionDto.Message!=null)
						{
							errorMessage.content_name =oException.InnerExceptionDto.Message;
						}
						else
						{
							errorMessage.content_name =oException.Message;
						}
						errorMessage.content_message = "";
						break;
					}
				case 0:
				case 4:
					{
						//业务异常,使用“提示”显示该错误
						errorMessage.type = 4;
						if (oException.InnerExceptionDto.ErrorMessage!=null)
						{
							errorMessage.content_name =oException.InnerExceptionDto.ErrorMessage;
						}
						else
						{
							errorMessage.content_name =oException.ErrorMessage;
						}
						errorMessage.content_message = "";
						break;
					}
				default:
					{
						//传入的对象，数据不正确
						errorMessage.type = 1;
						if (oException.InnerExceptionDto.ErrorMessage!=null)
						{
							errorMessage.content_message =oException.InnerExceptionDto.ErrorMessage;
						}
						else
						{
							errorMessage.content_message =(oException.Message==null) ? oException.message:oException.Message;
						}
						errorMessage.stack_trace="";
					}
			}
		}
	}

	if(errorMessage.type == 1)
	{
		//弹出“错误”对话页面
		errorMessage.title = ERROR_WINDOW_TITLE1;
		errorMessage.email = g_GlobalInfo.Email;
		returnValue = window.showModalDialog(g_GlobalInfo.WebRoot+"public/pub/ErrorHandle.html",errorMessage,
		"dialogHeight:180px; dialogWidth:320px; center: Yes; help: Yes; resizable: no; status: no;");
	}
	else if(errorMessage.type == 2)
	{
		//弹出“告警”对话页面
		errorMessage.title = ERROR_WINDOW_TITLE2;
		returnValue = window.showModalDialog(g_GlobalInfo.WebRoot+"public/pub/AlarmHandle.html",errorMessage,
		"dialogHeight:180px; dialogWidth:320px;center: Yes; help: Yes; resizable: no; status: no;");
	}
	else if (errorMessage.type == 3)
	{
		//弹出“询问”对话页面
		errorMessage.title = ERROR_WINDOW_TITLE3;
		returnValue = window.showModalDialog(g_GlobalInfo.WebRoot+"public/pub/PromptHandle.html",errorMessage,
		"dialogHeight:180px; dialogWidth:320px;center: Yes; help: Yes; resizable: no; status: no;");
		if(returnValue != null)
		{
			return returnValue;
		}
	}
	else if (errorMessage.type == 4)
	{
		//弹出“提示”对话页面
		errorMessage.title = ERROR_WINDOW_TITLE4;
		returnValue = window.showModalDialog(g_GlobalInfo.WebRoot+"public/pub/InformationHandle.html",errorMessage,
		"dialogHeight:180px; dialogWidth: 320px;center: Yes; help: Yes; resizable: no; status: no;");

	}
	else
	{
		//自定义对话页面
		errorMessage.button_type = button_array;
		errorMessage.title = title;
		returnValue = window.showModalDialog(g_GlobalInfo.WebRoot+"public/pub/CustomHandle.html",errorMessage,
		"dialogHeight:180px; dialogWidth:320px;center: Yes; help: Yes; resizable: no; status: no;");
		return returnValue;
	}
}
