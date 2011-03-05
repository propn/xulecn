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
		alert("请输入数字类型记录条数!") ;
		button.pilot.pageSize.value = "";
		button.pilot.pageSize.focus();
		return ;
	}
	
	var dataset = button.pilot.dataset;
	var value = System.getInt( button.pilot.pageSize.value ) ;//每页的大小
	if( value <= 0 ){
		alert("请输入大于0的数字!") ;
		return ;
	}
	if(value > 30){
		alert("系统不建议使用超过30条记录的分页，请使用更小的数字！");
		return ;
	}
	dataset.pageSize = value ;
	var pageIndex = System.getInt(button.pilot.editorGoTo.value);//当前页
	
	if(dataset.recordCount<=0)
	  return;
	dataset.paginateFlushData(pageIndex);
	
}

function buttonGoTo_onClick(button) {
	if(isNaN( button.pilot.editorGoTo.value )){
		alert("请输入数字类型的页数!") ;
		button.pilot.editorGoTo.value = "";
		button.pilot.editorGoTo.focus();
		return ;
	}
	var dataset = button.pilot.dataset;
	var value = System.getInt(button.pilot.editorGoTo.value);
	if( value <= 0 ){
		alert("请输入大于0且小于" + dataset.getPageCount() + "的数字!");
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
				alert("超出页数范围!");
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
    //不实现统计功能的方式，这个实现方式服务器端不统计总纪录数，不计算总页数，
    //总纪录数和总页数都是-1，适用于用户不关心总纪录数且服务器端执行统计的时候
    //需要消耗较大资源的情况。
    //这个类型的分页组件不显示总纪录数和总页数，也不出现“最后一页”的超链接。同时
    //服务器端实现的时候要注意捕捉异常，因为没有统计纪录总数，有可能ResoutSet会超出
    //限制
    pilot.innerHTML =		
  
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">首页</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">上页</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">下页</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;"></a> '
    +'</td>'
	+'<td align=right nowrap>&nbsp;&nbsp;&nbsp;&nbsp;<B><span id="recordSize" style="color:#FF6600">0</span></B>每页<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;'
    +'</B>&nbsp;<B><span id="pageIndex" style="color:#FF6600">0</span></B><B><span id="pageCount">0</span></B>'
    +'&nbsp;转到<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';
  }
  else if(pilot.type=="short"){
  
    pilot.innerHTML =		
     
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">首页</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">上页</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">下页</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;">尾页</a> '
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'     
	+'<td align=right nowrap>&nbsp;共<B><span id="recordSize" style="color:#FF6600">0</span></B> 条&nbsp;&nbsp;每页&nbsp;<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;</B>&nbsp;'
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'     
	+'<td align=right nowrap>'     
    +'页次<B><span id="pageIndex" style="color:#FF6600">0</span></B>/<B><span id="pageCount">0</span></B>'
    +'&nbsp;转到&nbsp;<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';   
    
  }
  if(pilot.type=="newShort"){//展示 上一页，下一页 每页条数
  
    pilot.innerHTML =		
     
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;" style="display:none;">首页</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">上页</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">下页</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;" style="display:none;">尾页</a> '
    +'</td>'
    +'<td align=right nowrap>&nbsp;&nbsp;&nbsp;每页&nbsp;<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;</B>&nbsp;'
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center" style = "display:none">'
    +'<tr>'    
    +'<td align=right nowrap>&nbsp;共<B><span id="recordSize" style="color:#FF6600" >0</span></B> 条'
    +'</td>' 
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center" style="display:none;">'
    +'<tr>'     
	+'<td align=right nowrap>'     
    +'页次<B><span id="pageIndex" style="color:#FF6600">0</span></B>/<B><span id="pageCount">0</span></B>'
    +'&nbsp;转到&nbsp;<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';   
    
  }
  else if(pilot.type=="veryshort"){
  
    pilot.innerHTML =		
     
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">首页</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">上页</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">下页</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;">尾页</a> '
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center" style="display:none">'
    +'<tr>'     
	+'<td align=right nowrap>&nbsp;共<B><span id="recordSize" style="color:#FF6600">0</span></B> 条&nbsp;&nbsp;每页&nbsp;<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;</B>&nbsp;'
    +'</td>'
	+'</tr>'
    +'</table>'   
	+'<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center" style="display:none">'
    +'<tr>'     
	+'<td align=right nowrap>'     
    +'页次<B><span id="pageIndex" style="color:#FF6600">0</span></B>/<B><span id="pageCount">0</span></B>'
    +'&nbsp;转到&nbsp;<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';   
    
  }
  else{  
  
    pilot.innerHTML =		
  
	 '<table class="customerpilot" height="16" border="0" cellPadding="0" cellSpacing="0" align="center">'
    +'<tr>'
    +'<td align="right" valign="bottom" nowrap>'
    +'<a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">首页</a> '
    +'<a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">上页</a> '
    +'<a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">下页</a> '
    +'<a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;">尾页</a> '
    +'</td>'
	+'<td align=right nowrap>&nbsp;共<B><span id="recordSize" style="color:#FF6600">0</span></B> 条&nbsp;&nbsp;每页<B><span id="pageSizeInfor">'
	+'<input name="pageSize" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">'
    +'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;"></span>&nbsp;'
    +'</B>&nbsp;页次<B><span id="pageIndex" style="color:#FF6600">0</span></B>/<B><span id="pageCount">0</span></B>'
    +'&nbsp;转到<input name="editorGoTo" type="text" class="textarea" value="16" style="border:1px solid #7FB8FF; font-size:12px;height:18;" size="3">' 
	+'&nbsp;<input type="image" src="'+path_prefix+'/public/skins/bsn/go_bg.gif" width="31" height="16" border="0" align="absmiddle" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">&nbsp;</td>'
	+'</tr>'
    +'</table>';
    
  }
 

/*
	 '<table width="100%" height="40px" border="0" cellspacing="0" cellpadding="0">'
    +'  <tr>'
    +'    <td>每页<input id="pageSize" type="text" class="textarea" style="border:1px solid #CCCCCC; font-size:12px;height:18;" size="4">条&nbsp;'
    +'      <a href="#" id="buttonGoTo1" onclick="if(this.disabled) return;buttonSetPageSize_onClick(this);return false;">确定</a>'
    +'    </td>'
    +'    <td>共&nbsp;<span id="recordSize"></span>&nbsp;条</td>'
    +'    <td>第 <span id="pageIndex"></span> / <span id="pageCount"></span> 页</td>'
    +'    <td>转到第<input id="editorGoTo" type="text" class="textarea" style="border:1px solid #CCCCCC; font-size:12px;height:18;" size="4">页&nbsp;'
    +'      <a href="#" id="buttonGoTo2" onclick="if(this.disabled) return;buttonGoTo_onClick(this);return false;">确定</a>'
    +'    </td>'
    +'    <td><a href="#" id="buttonFirstPage" onclick="if(this.disabled) return;buttonFirstPage_onClick(this);return false;">首页</a></td>'
    +'    <td><a href="#" id="buttonPrevPage" onclick="if(this.disabled) return;buttonPrevPage_onClick(this);return false;">上一页</a></td>'
    +'    <td><a href="#" id="buttonNextPage" onclick="if(this.disabled) return;buttonNextPage_onClick(this);return false;">下一页</a></td>'
    +'    <td><a href="#" id="buttonLastPage" onclick="if(this.disabled) return;buttonLastPage_onClick(this);return false;">尾页</a></td>'
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