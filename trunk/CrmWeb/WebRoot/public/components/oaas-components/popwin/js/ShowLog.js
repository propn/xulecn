/*************************************************************************
    File Name:showLog.js
	Create Date:2005-05-10
	Author:xu.yiliang
	create Version:01.00.001
	Create Version Date:2005-09-19
	modify Version:
	modify Version Date:
*************************************************************************/
var packageLogFilePath = "com.ztesoft.bsn.common.decode.DecodeUtil";
var g_filePath = "";
var g_fileCfg = "";

function initPage()
{
  var showDialogDto = window.dialogArguments;
  g_filePath = showDialogDto.filePath;
  g_fileCfg = showDialogDto.fileCfg;

  var logStr = findLogStr(g_fileCfg,g_filePath);
  if(logStr == null)
    return;

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
  var fileName = saveLogFile(g_fileCfg,g_filePath);
  if (fileName != "")
  {
      var features =' dialogWidth=' + 10 +"px"+ '; dialogHeight=' + 10 + "px" + '; directories:yes; localtion:no; menubar:no; status:no; toolbar:no; scroll:yes; resizable:yes';
      var url = "DownLoad.jsp?fileName="+fileName;
      window.open(url,"null",features);
  }
}

/*******************************************
 *�������ܣ�ȡ��log��־
 *���������filePath
 *�� �� ֵ����
*******************************************/
function findLogStr(fileCfg, filePath)
{
  var returnVal = null;
  try{
    returnVal = callRemoteFunction(packageLogFilePath,"findLogStr",fileCfg, filePath);
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

/*******************************************
 *�������ܣ�������־
 *���������logFilePath
 *�� �� ֵ����
*******************************************/
function saveLogFile(logFileCfg, filePath)
{
  var returnVal = null;
  try{
    returnVal = callRemoteFunction(packageLogFilePath,"saveLogFile",logFileCfg,filePath);
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