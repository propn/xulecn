/*************************************************************************
    File Name:showCommLog.js
	Create Date:2006-02-19
	Author:xu.yiliang
	create Version:01.00.001
	Create Version Date:2006-02-19
	modify Version:
	modify Version Date:
*************************************************************************/
var packageLogFilePath = "com.ztesoft.bsn.common.decode.DecodeUtil";
var g_fileCfg = ""; //��־����·������

function initPage()
{
  var showDialogDto = window.dialogArguments;
  var logStr = showDialogDto.logStr;
  g_fileCfg = showDialogDto.fileCfg;

  logStr = logStr.replace(/ /g,"");//ȥ���ո�

  document.all.td_detailLog.innerText = logStr;
}

/*************************************************************************
����˵������������ļ���ť
����Ҫ��
�����������
�����������
************************************************************************/
function saveFile()
{
  var fileName = saveLogFile(document.all.td_detailLog.innerText,g_fileCfg);
  if (fileName != "")
  {
      var features =' dialogWidth=' + 10 +"px"+ '; dialogHeight=' + 10 + "px" + '; directories:yes; localtion:no; menubar:no; status:no; toolbar:no; scroll:yes; resizable:yes';
      var url = "DownLoadWithDelete.jsp?fileName="+fileName;
      window.open(url,"null",features);
  }
}

/*******************************************
 *�������ܣ�������־
 *���������logFilePath
 *�� �� ֵ����
*******************************************/
function saveLogFile(str,logFileCfg)
{
  var returnVal = null;
  try{
    returnVal = callRemoteFunction(packageLogFilePath,"createCommLogFile",str,logFileCfg);
  }
  catch(e)
  {
    if(e.Type == '1')
    {
      ErrorHandle(null,1,1,"",e.Time+"<br>"+e.Code+":"+e.Desc);
    }
    if(e.Type == '2')
    {
      ErrorHandle(null,2,1,e.Time+"\n"+e.Code+":"+e.Desc,"");
    }
    return false;
  }
  return returnVal;
}