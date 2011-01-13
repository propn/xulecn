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

  logStr = logStr.replace(/ /g,"");//去掉空格
  document.all.td_detailLog.innerText = logStr;
}

/*************************************************************************
函数说明：点击保存文件按钮
函数要求：
输入参数：无
输出参数：无
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
 *函数功能：取得log日志
 *输入参数：filePath
 *返 回 值：无
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
 *函数功能：保存日志
 *输入参数：logFilePath
 *返 回 值：无
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