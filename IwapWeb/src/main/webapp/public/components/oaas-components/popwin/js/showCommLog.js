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
var g_fileCfg = ""; //日志保存路径配置

function initPage()
{
  var showDialogDto = window.dialogArguments;
  var logStr = showDialogDto.logStr;
  g_fileCfg = showDialogDto.fileCfg;

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
  var fileName = saveLogFile(document.all.td_detailLog.innerText,g_fileCfg);
  if (fileName != "")
  {
      var features =' dialogWidth=' + 10 +"px"+ '; dialogHeight=' + 10 + "px" + '; directories:yes; localtion:no; menubar:no; status:no; toolbar:no; scroll:yes; resizable:yes';
      var url = "DownLoadWithDelete.jsp?fileName="+fileName;
      window.open(url,"null",features);
  }
}

/*******************************************
 *函数功能：保存日志
 *输入参数：logFilePath
 *返 回 值：无
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