/******************************************************************
����˵�������ݴ���Ĵ�����Ϣ�����Ի�ҳ��
�������ƣ�ErrorHandle
���������
		title��          ��ʾ�ı���, type = 5 ʱ,����Ч;�������ͣ��ɴ���ֵ(null)
		type��           ҳ������(1/2/3/4/5)������ 1�� ����  2���澯  3��ѯ�� 4:��ʾ 5���Զ���
		button_array��   ��ť����,type = 5 ʱ,����Ч;�������ͣ��ɴ���ֵ(null)
		content_name��   ����ҳ����ʾ������
		content_message������ҳ����ʾ����ϸ����(type=2/3/4������Ϊ��)
�������:
		�ޣ�
����ֵ��
		type = 3 �� 5 ,���з���ֵ
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

	//��̨�쳣���ȴ���
	//��̨�쳣�Ĵ���
	if (e != null)
	{
		if (e.typeName == "object")
		{
			//ȡ������ײ���Ǹ�InnerExceptionDto����һ��
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
						//�ڲ��쳣,ʹ�á�������ʾ
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
						//�澯
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
						//ѯ��
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
						//ҵ���쳣,ʹ�á���ʾ����ʾ�ô���
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
						//����Ķ������ݲ���ȷ
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
		//���������󡱶Ի�ҳ��
		errorMessage.title = ERROR_WINDOW_TITLE1;
		errorMessage.email = g_GlobalInfo.Email;
		returnValue = window.showModalDialog(g_GlobalInfo.WebRoot+"public/pub/ErrorHandle.html",errorMessage,
		"dialogHeight:180px; dialogWidth:320px; center: Yes; help: Yes; resizable: no; status: no;");
	}
	else if(errorMessage.type == 2)
	{
		//�������澯���Ի�ҳ��
		errorMessage.title = ERROR_WINDOW_TITLE2;
		returnValue = window.showModalDialog(g_GlobalInfo.WebRoot+"public/pub/AlarmHandle.html",errorMessage,
		"dialogHeight:180px; dialogWidth:320px;center: Yes; help: Yes; resizable: no; status: no;");
	}
	else if (errorMessage.type == 3)
	{
		//������ѯ�ʡ��Ի�ҳ��
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
		//��������ʾ���Ի�ҳ��
		errorMessage.title = ERROR_WINDOW_TITLE4;
		returnValue = window.showModalDialog(g_GlobalInfo.WebRoot+"public/pub/InformationHandle.html",errorMessage,
		"dialogHeight:180px; dialogWidth: 320px;center: Yes; help: Yes; resizable: no; status: no;");

	}
	else
	{
		//�Զ���Ի�ҳ��
		errorMessage.button_type = button_array;
		errorMessage.title = title;
		returnValue = window.showModalDialog(g_GlobalInfo.WebRoot+"public/pub/CustomHandle.html",errorMessage,
		"dialogHeight:180px; dialogWidth:320px;center: Yes; help: Yes; resizable: no; status: no;");
		return returnValue;
	}
}
