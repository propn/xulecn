function buttonFirstPage_onClick(button) {
	var dataset = button.pilot.dataset;
	var pageIndex=dataset.getPageIndex();
	if (pageIndex!=1){
	  dataset.paginateFlushData(1);
	}
}

function buttonPrevPage_onClick(button) {
	var dataset = button.pilot.dataset;
	var pageIndex=dataset.getPageIndex();
	if (pageIndex>1) {
	  dataset.paginateFlushData(pageIndex-1);
	}
}

function buttonNextPage_onClick(button) { 
	var dataset = button.pilot.dataset;
	var pageIndex=dataset.getPageIndex();
	var pageCount=dataset.getPageCount();
	
	if(button.pilot.type=="newShort"){
		if (dataset.getPageSize()==dataset.getCount()) {
		 	dataset.paginateFlushData(pageIndex+1);
		}
	}
	else{
		if( pageCount == -1 ){
			dataset.paginateFlushData(pageIndex+1);
		}else if (pageIndex<pageCount) {
		  dataset.paginateFlushData(pageIndex+1);
		}
	}
	
}

function buttonLastPage_onClick(button) {
	var dataset = button.pilot.dataset;
	var pageCount=dataset.getPageCount();
	var pageIndex=dataset.getPageIndex();
	if (pageIndex!=pageCount){
	  dataset.paginateFlushData(pageCount);
	}
}

function buttonSetPageSize_onClick( button ){
	if(isNaN( button.pilot.pageSize.value )){
		alert("�������������ͼ�¼����!") ;
		button.pilot.pageSize.value = "";
		button.pilot.pageSize.focus();
		return ;
	}
	
	var dataset = button.pilot.dataset;
	var value = System.getInt( button.pilot.pageSize.value ) ;//ÿҳ�Ĵ�С
	if( value <= 0 ){
		alert("���������0������!") ;
		return ;
	}
	if(value > 30){
		alert("ϵͳ������ʹ�ó���30����¼�ķ�ҳ����ʹ�ø�С�����֣�");
		return ;
	}
	dataset.pageSize = value ;
	var pageIndex = System.getInt(button.pilot.editorGoTo.value);//��ǰҳ
	
	if(dataset.recordCount<=0)
	  return;
	dataset.paginateFlushData(pageIndex);
	
}

function buttonGoTo_onClick(button) {
	if(isNaN( button.pilot.editorGoTo.value )){
		alert("�������������͵�ҳ��!") ;
		button.pilot.editorGoTo.value = "";
		button.pilot.editorGoTo.focus();
		return ;
	}
	var dataset = button.pilot.dataset;
	var value = System.getInt(button.pilot.editorGoTo.value);
	if( value <= 0 ){
		alert("���������0��С��" + dataset.getPageCount() + "������!");
		return ;
	}
	

	var pageIndex=dataset.getPageIndex();
	var pageCount=dataset.getPageCount();
	if( pageCount == -1 ){
		dataset.paginateFlushData(value);
	}else{
		if (value!=pageIndex && value>=1 && value<=pageCount){
			dataset.paginateFlushData(value);
		}else{
			if( value > pageCount ){
				alert("����ҳ����Χ!");
			}
		}
	}
}

function refreshCustomerPilot(pilot){
  var dataset = pilot.dataset;
  if(!dataset) return;
  var pageSize=dataset.getPageSize();

  pilot.pageSize.innerText = pageSize;
    
  var pageIndex=dataset.getPageIndex();
  var pageCount=dataset.getPageCount();
  pilot.pageIndex.innerText  = pageIndex;
  if( pageCount == "-1" ){
  	pilot.pageCount.innerText = "";
  }else{
	  pilot.pageCount.innerText  = pageCount;  
  }
  if( dataset.recordCount == -1 ) {
    pilot.recordSize.innerText = "";
  }else{
  	pilot.recordSize.innerText = dataset.recordCount;
  }
  pilot.editorGoTo.value = pageIndex;

  pilot.buttonFirstPage.disabled=(pageIndex==1);
  pilot.buttonPrevPage.disabled=(pageIndex==1);
  pilot.buttonNextPage.disabled=(pageIndex==pageCount);
  pilot.buttonLastPage.disabled=(pageIndex==pageCount);
  
  if(pilot.type=="newShort"){
  	if(dataset.getCount()<pageSize){
  		pilot.buttonNextPage.disabled = true;
  	}
  }
  
}

function destroyCustomerPilot(pilot){
  pilot.window = null;
  var controlList = ["pageSize", "buttonGoTo1", "recordSize", "pageIndex", "pageCount", "editorGoTo", "buttonGoTo2", "buttonFirstPage", "buttonPrevPage", "buttonNextPage", "buttonLastPage"];
  
  for(var i=0; i<controlList.length; i++){
    if(pilot[controlList[i]]){
	    pilot[controlList[i]].pilot = null;
	    pilot[controlList[i]] = null;
    }
  }
     
  if(pilot.dataset)
    pilot.dataset.pilot = null;
  pilot.dataset = null;
  pilot = null;
}

function initCustomerPilot(pilot){	

  pilot.extra = "customerpilot";
  Control.initElementDataset(pilot);  
  pilot.window = window;
  
  if( pilot.type == "noCount" ){
    //��ʵ��ͳ�ƹ��ܵķ�ʽ�����ʵ�ַ�ʽ�������˲�ͳ���ܼ�¼������������ҳ����
    //�ܼ�¼������ҳ������-1���������û��������ܼ�¼���ҷ�������ִ��ͳ�Ƶ�ʱ��
    //��Ҫ���Ľϴ���Դ�������
    //������͵ķ�ҳ�������ʾ�ܼ�¼������ҳ����Ҳ�����֡����һҳ���ĳ����ӡ�ͬʱ
    //��������ʵ�ֵ�ʱ��Ҫע�Ⲷ׽�쳣����Ϊû��ͳ�Ƽ�¼�������п���ResoutSet�ᳬ��
    //����
    pilot.innerHTML =		
  
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;"></a> '
    +'</td>'
	+'<td align=right nowrap>&nbsp;&nbsp;&nbsp;&nbsp;<B><span id="recordSize" style="color:#FF6600">0</span></B>ÿҳ<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;'
    +'</B>&nbsp;<B><span id="pageIndex" style="color:#FF6600">0</span></B><B><span id="pageCount">0</span></B>'
    +'&nbsp;ת��<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';
  }
  else if(pilot.type=="short"){
  
    pilot.innerHTML =		
     
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;">βҳ</a> '
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'     
	+'<td align=right nowrap>&nbsp;��<B><span id="recordSize" style="color:#FF6600">0</span></B> ��&nbsp;&nbsp;ÿҳ&nbsp;<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;</B>&nbsp;'
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'     
	+'<td align=right nowrap>'     
    +'ҳ��<B><span id="pageIndex" style="color:#FF6600">0</span></B>/<B><span id="pageCount">0</span></B>'
    +'&nbsp;ת��&nbsp;<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';   
    
  }
  if(pilot.type=="newShort"){//չʾ ��һҳ����һҳ ÿҳ����
  
    pilot.innerHTML =		
     
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;" style="display:none;">��ҳ</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;" style="display:none;">βҳ</a> '
    +'</td>'
    +'<td align=right nowrap>&nbsp;&nbsp;&nbsp;ÿҳ&nbsp;<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;</B>&nbsp;'
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center" style = "display:none">'
    +'<tr>'    
    +'<td align=right nowrap>&nbsp;��<B><span id="recordSize" style="color:#FF6600" >0</span></B> ��'
    +'</td>' 
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center" style="display:none;">'
    +'<tr>'     
	+'<td align=right nowrap>'     
    +'ҳ��<B><span id="pageIndex" style="color:#FF6600">0</span></B>/<B><span id="pageCount">0</span></B>'
    +'&nbsp;ת��&nbsp;<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';   
    
  }
  else if(pilot.type=="veryshort"){
  
    pilot.innerHTML =		
     
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;">βҳ</a> '
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center" style="display:none">'
    +'<tr>'     
	+'<td align=right nowrap>&nbsp;��<B><span id="recordSize" style="color:#FF6600">0</span></B> ��&nbsp;&nbsp;ÿҳ&nbsp;<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;</B>&nbsp;'
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center" style="display:none">'
    +'<tr>'     
	+'<td align=right nowrap>'     
    +'ҳ��<B><span id="pageIndex" style="color:#FF6600">0</span></B>/<B><span id="pageCount">0</span></B>'
    +'&nbsp;ת��&nbsp;<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';   
    
  }
  else{  
  
    pilot.innerHTML =		
  
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">��ҳ</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;">βҳ</a> '
    +'</td>'
	+'<td align=right nowrap>&nbsp;��<B><span id="recordSize" style="color:#FF6600">0</span></B> ��&nbsp;&nbsp;ÿҳ<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;'
    +'</B>&nbsp;ҳ��<B><span id="pageIndex" style="color:#FF6600">0</span></B>/<B><span id="pageCount">0</span></B>'
    +'&nbsp;ת��<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';
    
  }
 

/*
	 '<table width="100%" height="40px" border="0" cellspacing="0" cellpadding="0">'
    +'  <tr>'
    +'    <td>ÿҳ<input id="pageSize" type="text" class="textarea" style="border:1px solid #CCCCCC; font-size:12px;height:18;" size="4">��&nbsp;'
    +'      <a href="#" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;">ȷ��</a>'
    +'    </td>'
    +'    <td>��&nbsp;<span id="recordSize"></span>&nbsp;��</td>'
    +'    <td>�� <span id="pageIndex"></span> / <span id="pageCount"></span> ҳ</td>'
    +'    <td>ת����<input id="editorGoTo" type="text" class="textarea" style="border:1px solid #CCCCCC; font-size:12px;height:18;" size="4">ҳ&nbsp;'
    +'      <a href="#" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">ȷ��</a>'
    +'    </td>'
    +'    <td><a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">��ҳ</a></td>'
    +'    <td><a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">��һҳ</a></td>'
    +'    <td><a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">��һҳ</a></td>'
    +'    <td><a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;">βҳ</a></td>'
    +'  </tr>'
    +'</table>';
*/
  
  var controlList = ["pageSize", "buttonGoTo1", "recordSize", "pageIndex", "pageCount", "editorGoTo", "buttonGoTo2", "buttonFirstPage", "buttonPrevPage", "buttonNextPage", "buttonLastPage"];
  
  for(var i=0; i<controlList.length; i++){
    pilot[controlList[i]] = pilot.all[controlList[i]];
    pilot[controlList[i]].pilot = pilot;
  }
   
  var dataset = pilot.dataset;
  dataset.pilot = pilot;
  
  dataset._old_flushData = dataset.flushData;
  dataset.flushData = function(pageIndex){
    this._old_flushData(pageIndex);
    refreshCustomerPilot(this.pilot);
  }
  
  dataset._old_fetchData = dataset.fetchData;
  dataset.fetchData = function(pageIndex, callback){
    this._old_fetchData(pageIndex, callback);
    refreshCustomerPilot(this.pilot);
  }  
  
  refreshCustomerPilot(pilot);
  
  
  

}