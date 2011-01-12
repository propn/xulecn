var __gDialog = new DHTMLModalDialog(400,200);

function showAlert(msg,title)
{
	__gDialog.width = 400;
	__gDialog.height = 100;
	var html = '<table  width=100% height=100%><tr><td align="center" id="_tdMsg"></td></tr><tr><td align="center" valign="bottom"><button id="_cancel" onclick="this._Dialog.Close();">Cancel</button></td></tr></table>';
	__gDialog.Display(html,title);
	__gDialog.dv.all["_tdMsg"].innerText = msg;
	__gDialog.dv.all["_cancel"]._Dialog=__gDialog;
}

//process dialog
var __giProgress = 0;
var __gInterval = null;
function __addProgress()
{
	if(this.tbl!=null)
	{
		if(__giProgress>=this.tbl.rows[0].cells.length)
		{
			for(var i=1;i<this.tbl.rows[0].cells.length;i++)
			{
				tbl.rows[0].cells[i].bgColor="white";
			}
			__giProgress=0;
		}
		this.tbl.rows[0].cells[__giProgress].bgColor="#0000cc";
	}
	__giProgress++;
}

function __clearProgress()
{
	if(this.tbl!=null && this.tbl.rows!=null && this.tbl.rows.length>0)
	{
		for(var i=0;i<this.tbl.rows[0].cells.length;i++)
		{
			this.tbl.rows[0].cells[i].bgColor="white";
		}
	}
	__giProgress=0;
	if(__gInterval!=null) window.clearInterval(__gInterval);
}

function __initProgress()
{
	__clearProgress();
	var html = '<TABLE id="_tbl" height="30" cellSpacing="0" cellPadding="0" width="300" border="1"><tr>';
	for(var i=0;i<20;i++)
		html += '<TD width="15" height="100%"></TD>';
	html+='</tr></table>';

	__gDialog.width = 300;
	__gDialog.height = 40;
	
	__gDialog.Display(html,"Calling Remote Function....");
	this.tbl = __gDialog.dv.all["_tbl"];
}

function ProgressDlg_onclose()
{
	__clearProgress();
	var progDlg = this._progresssDlg;
	if(progDlg!=null && progDlg.onCancel!=null && progDlg.isCancel) 
	{
		progDlg.onCancel(progDlg.state);
		progDlg.remoteCall.cancel();
	}
	progDlg.isCancel = false;
}

function ProgressDlg_onerror(ex,procDlg)
{
	__clearProgress();
	procDlg.isCancel = false;
	__gDialog.Close();
	//showAlert("Remote Call Error",ex);
	if(procDlg.onError!=null)
		procDlg.onError(ex,procDlg.state);
}

function ProgressDlg_oncomplete(retVal,procDlg)
{
	__clearProgress();
	procDlg.isCancel = false;
	__gDialog.Close();
	if(procDlg.onComplete!=null)
		procDlg.onComplete(retVal,procDlg.state);
}

function ProgressDlg_callRemoteXmlAsync()
{
	__initProgress();
	__gInterval = window.setInterval(__addProgress,500);	
	__gDialog.onClose = ProgressDlg_onclose;
	__gDialog._progresssDlg = this;
	
	var remoteCall = new RemoteCall();
	
	remoteCall.serviceName = this.serviceName;
	remoteCall.funcName = this.funcName;
	remoteCall.onComplete = ProgressDlg_oncomplete;
	remoteCall.onError = ProgressDlg_onerror;
	remoteCall.state  = this;
	remoteCall.args = (this.args==null?arguments:this.args);
	remoteCall.call();
	
	this.remoteCall = remoteCall;
}

function ProgressDlg()
{
//private
	this.tbl = null;
	this.remoteCall = null;
	this.isCancel = true;
	
//input
	this.serviceName = null;
	this.funcName = null;
	this.args = null;
	this.onComplete = null;
	this.onError = null;
	this.onCancel = null;
	
	this.call = ProgressDlg_callRemoteXmlAsync;
}


function callRemoteFuncAsync(serviceName,funcName,
		oncomplete_callback,onerror_callback,oncancel_callback,state/*,arg0,arg1,...*/)
{
	var progDlg = new ProgressDlg();
	progDlg.serviceName = serviceName;
	progDlg.funcName = funcName;
	progDlg.state = state;
	
	progDlg.onCancel = oncancel_callback;
	progDlg.onError = onerror_callback;
	progDlg.onComplete = oncomplete_callback;
	var args = new Array();
	for(var i=6;i<arguments.length;i++)
		args[i-6] = arguments[i];
	progDlg.args = args;
	progDlg.call();
}