var isJspPage = true;
	var array_security = [];
	var array_securityId = [];
var need_processElementBlur = false;
function GetArgsFromHrefs(sHref, sArgName)
{
    var args  = sHref.split("?");
    var retval = "";

    if(args[0] == sHref) /*参数为空*/
    {
         return retval; /*无需做任何处理*/
    }
    var str = args[1];
    args = str.split("&");
    for(var i = 0; i < args.length; i ++)
    {
        str = args[i];
        var arg = str.split("=");
        if(arg.length <= 1) continue;
        if(arg[0] == sArgName) return arg[1];
    }
}
/**
功能说明:current_fileprefix
计算当前页面的相对路径前缀字符串.
主要考虑因素是本地运行和服务器端运行的差异
要求以VsopWeb为站点名, 在该站点内部的目录不能再以"VsopWeb"命名
 **/

var themeId = "bsn";

var path_prefix = "";
var onService = false;
var webroot = "VsopWeb\\VsopWeb\\";

var oldDataset = [];

var pathname = location.pathname.substring(1);
if (pathname.indexOf('/') >  - 1)
{
  onService = true;
  pathname = pathname.substring(pathname.indexOf('/') + 1);
  var path_array = pathname.split("/");
  for (var i = 0; i < path_array.length - 1; i++)
    path_prefix += "../";
}
else
{
  onService = false;
  pathname = pathname.substring(pathname.lastIndexOf(webroot) + webroot.length);
  var path_array = pathname.split("\\");
  for (var i = 0; i < path_array.length - 1; i++)
    path_prefix += "../";
}

Global = {
	//是否将浏览器的默认右键菜单失效
	disableSystemContextMenu : false,
	
	//是否将页面内容选择失效
	disableContextSelect: false,
	
	//是否将页面F5刷新失效
	disableContextRefresh: true,
	
	//是否允许按钮快捷键
	disableButtonAccessKey : false,
	
	//在异步调用过程中，是否显示异步调用进度条
	disableAsyncRemoteCallProcessingBar : false,
	
	//是否将Enter键作为Tab键使用，如Editor元素或者是数据表格元素
	processEnterAsTab : false,
	
	//在装载数据时，是否显示等待对话框
	showDialogOnLoadingData : true,
	
	//主题路径设定值
	theme_root : path_prefix + "public/skins/"+themeId,
	
	//系统目录路径设定值
	application_root : "",
	
	//context path
	contextPath : "/VsopWeb",
	
	//应用服务器路径
	serverPath : "/VsopWeb/nfapp",  
	
	onServer : onService,
	
	finishedLayoutRender : false,
	
	lineDelimiter : "<br>",
	
	//数据权限控制全局变量-页面标识
	pageId : "",
	
	//数据权限控制全局变量-客户类型标识
	custTypeId : "",
	
	userInfoBean : new Object()
	
}

Global.getCurrentMenuCode = function(_window){
  if(!_window){
  	_window = window;
  }
  return _window.frameElement.code;
}

Global.getUserInfoBean = function(){
	return window.parent.Global.userInfoBean;
}

var Class = {
  create: function() {
    return function() {
      this.initialize.apply(this, arguments);
    }
  }
}
Object.extend = function(destination, source) {
  for (property in source) {
    destination[property] = source[property];
  }
  return destination;
}

Function.prototype.bind = function(object) {
  var __method = this;
  return function() {
    return __method.apply(object, arguments);
  }
}

function $() {
  var elements = [];

  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    if (typeof element == 'string')
      element = document.getElementById(element);

    if (arguments.length == 1)
      return element;

    elements.push(element);
  }

  return elements;
}
CrmComponent = {
  _import : function(type, path, className)
	{
	  if (type == "js")
	    document.write('<script language="JavaScript" src="' + path_prefix + path + '" charset="GBK"></script>');
	  else if (type == "css")
	    document.write('<link type="text/css" rel="stylesheet" href="' + path_prefix + path + '" charset="GBK"/>');
	  else if (type == "behavior")
	  {
	    document.write('<style>' + className + '{behavior:url(' + path_prefix + path + '); }</style>');
	  }
	  else if (type=="namespace"){
	    document.write('<?import namespace="' + className+ '" implementation="' + path_prefix + path + '" />');
	  }	  
	}
}

if(window.dialogArguments){
  document.write("<style>body{overflow: hidden;}</style>");
}
if(!Global.onServer){
  document.write("<style>body{visibility:hidden;}</style>");
}

var hasImportCalendar = false;

function importLibrayByName(library){
  switch(library){
    case "kernel" :{
      if(!Global.onServer){	
			CrmComponent._import("css",      "public/skins/bsn/skinbsn.css");		
			CrmComponent._import("js",       "public/components/log4js.js");
			CrmComponent._import("js",       "public/components/kernel.js");
			CrmComponent._import("js",       "public/components/buffalo.js");	
			CrmComponent._import("js",       "public/components/form.js");		
			CrmComponent._import("js",       "public/components/datatable.js");
			CrmComponent._import("js",       "public/components/dataset.js");			
			CrmComponent._import("js",       "public/components/dropdown.js");	
			//CrmComponent._import("js",       "public/components/validator.js");			
			CrmComponent._import("js",       "public/components/messageBox/messageBox.js");		
			CrmComponent._import("js",       "public/components/messageBox/dialog.js");
			
			CrmComponent._import("js",       "public/components/client_view/layout.js");
			CrmComponent._import("js",		 "public/components/client_view/dataset.js");
			CrmComponent._import("js",		 "public/components/localAction.js");
			
			CrmComponent._import("behavior", "public/components/client_view/button.htc", ".coolButton");
			//CrmComponent._import("behavior", "public/components/client_view/editor.htc", ".editor, .dockeditor");
			CrmComponent._import("behavior", "public/components/client_view/fieldlabel.htc", ".fieldlabel");	
			//CrmComponent._import("behavior", "public/components/client_view/datalabel.htc", ".datalabel");		
			//CrmComponent._import("behavior", "public/components/client_view/form.htc", ".autoform");
			//CrmComponent._import("behavior", "public/components/client_view/property.htc", ".property");
			CrmComponent._import("behavior", "public/components/client_view/dropdown.htc", "code");
			//CrmComponent._import("behavior", "public/components/client_view/datatable.htc", ".datatable");
			//CrmComponent._import("behavior", "public/components/client_view/panel.htc", ".formLayout, .panel, .layout, .formLayout");
			CrmComponent._import("behavior", "public/components/client_view/spliter.htc", ".spliter_top, .spliter_bottom, .spliter_left, .spliter_right, .spliterDiv");	
						
			CrmComponent._import("namespace","public/components/client_view/dataset.htc", "ui");
			CrmComponent._import("namespace","public/components/client_view/form.htc", "ui");
			CrmComponent._import("namespace","public/components/client_view/property.htc", "ui");
			CrmComponent._import("namespace","public/components/client_view/dropdown.htc", "ui");
			CrmComponent._import("namespace","public/components/client_view/datatable.htc", "ui");
			CrmComponent._import("namespace","public/components/client_view/button.htc", "ui");			
			CrmComponent._import("namespace","public/components/client_view/bar.htc", "ui");
			CrmComponent._import("namespace","public/components/client_view/panel.htc", "ui");
			CrmComponent._import("namespace","public/components/client_view/layout.htc", "ui");
			CrmComponent._import("namespace","public/components/client_view/tabset.htc", "ui");			
			CrmComponent._import("namespace","public/components/client_view/permission.htc", "ui");	
			
			CrmComponent._import("namespace","public/components/client_view/include.htc", "jsp");	

			}
			break;
    }
    
    case "datalabel":{
	    CrmComponent._import("behavior", "public/components/client_view/datalabel.htc", ".datalabel");	
			  	
       break;    
    }      
    
    case "coolButton":{
	   CrmComponent._import("behavior", "public/components/client_view/button.htc", ".coolButton");
			  	
       break;    
    }    
    
    case "treeList":{
	   CrmComponent._import("css",      "public/components/oaas-components/style/oaas.css");	
	   CrmComponent._import("namespace","public/components/oaas-components/htc/treelist/TreeList.htc", "ZTESOFT");
			  	
       break;    
    }
    
     case "treeListImg":{
	   CrmComponent._import("css",      "public/components/oaas-components/style/oaas.css");	
	   CrmComponent._import("namespace","public/components/oaas-components/htc/treelist/TreeListImg.htc", "ZTESOFT");
			  	
       break;    
    }
    case "treeListSort":{
	   CrmComponent._import("css",      "public/components/oaas-components/style/oaas.css");	
	   CrmComponent._import("namespace","public/components/oaas-components/htc/treelist/TreeListSort.htc", "ZTESOFT");
			  	
       break;    
    }
    
    case "tree" :{
      CrmComponent._import("js",       "public/components/extra_view/xtree.js");
      CrmComponent._import("js",       "public/components/extra_view/xloadtree.js");    
      CrmComponent._import("behavior", "public/components/extra_view/tree.htc", "menu");
      
			break;    
    }  
      
    case "calendar" :{
      hasImportCalendar = true;
      CrmComponent._import("js",       "public/components/extra_view/calendar.js");
      
			break;    
    } 
    case "datapilot" :{
      CrmComponent._import("js",       "public/components/extra_view/datapilot.js");
      CrmComponent._import("behavior", "public/components/extra_view/datapilot.htc", ".datapilot");
      CrmComponent._import("js",       "public/components/extra_view/menubutton.js");
      CrmComponent._import("behavior", "public/components/extra_view/menubutton.htc", ".button");
    
			break;    
    }
    case "customerpilot" :{
      CrmComponent._import("js",       "public/components/extra_view/customerpilot.js");
      CrmComponent._import("behavior", "public/components/extra_view/customerpilot.htc", "div.customerpilot");
      
			break;    
    }   
    case "menu" :{
      CrmComponent._import("js",       "public/components/extra_view/menu.js");
      CrmComponent._import("behavior", "public/components/extra_view/menu.htc", ".menu");     

      CrmComponent._import("js",       "public/components/extra_view/menubar.js");
      CrmComponent._import("behavior", "public/components/extra_view/menubar.htc", ".menubar");     
      
      CrmComponent._import("js",       "public/components/extra_view/menubutton.js");
      CrmComponent._import("behavior", "public/components/extra_view/menubutton.htc", ".button");           
			break;    
    }    
    case "mutaSelect" :{
      CrmComponent._import("js",       "public/components/extra_view/mutaSelect.js");
      CrmComponent._import("behavior", "public/components/extra_view/mutaSelect.htc", ".mutaSelect");   
         
      CrmComponent._import("js",       "public/components/extra_view/menubutton.js");
      CrmComponent._import("behavior", "public/components/extra_view/menubutton.htc", ".button");                 
			break;    
    }     
	case "windowPopup" :{
	  CrmComponent._import("js", "public/components/client_view/window.js");
	  break;
	}
  }
}

function JspTaglibError(errorCode, errorMessage, errorResolve, level, stackInfo){
  this.errorCode = errorCode;
  this.errorMessage = errorMessage;
  this.errorResolve = errorResolve;
  this.level = level;  
  this.stackInfo = stackInfo;
}

var jspTaglibErrors = [];

function importI18NResource()
{
	//document.write('<script language="JavaScript" src="' + Global.contextPath+'/servlet/getI18NClientResource?clientUrl='+(window.location.href) + '" charset="GBK"></script>');
}
//blank fun
function initClientI18N(){
}
 importLibrayByName("kernel");
  
  importI18NResource();

var metas = document.all.tags("META");

for(var i=0; i<metas.length; i++){
  if(metas[i].httpEquiv=="library"){
    var libraries = metas[i].content.split(",");
    for(var i=0; i<libraries.length; i++){
      if(libraries[i]!="kernel"){
        	importLibrayByName(libraries[i]);
        }
    }
    break;
  }
}

//window.onload
window.onload = function(){
  
  		if(window.dialogArguments && (typeof(gensoinfo)=="undefined") && (typeof(prefGroup)=="undefined")){
  		  document.body.style.filter = "";
  		}
  		
		Dropdown.getDropDownBtn();
		Editor.initDockEditor();
		if(hasImportCalendar){	 
			makeCalendar();	
			if(dropDownDate){
				Dropdown.getDropDownBox(dropDownDate);
			}
		}
		if(Global.onServer){
			var dropdowns = document.getElementsByTagName("CODE");
			if(dropdowns){
				var dropdowns_length = dropdowns.length;
				
				for(var i=0; i<dropdowns_length; i++){
				  	//var startDate = new Date();
				  	//if(dropdowns[i].id == 'countyType' || dropdowns[i].id == 'SEX' ) alert(dropdowns[i].id);
				  	makeDropDown(dropdowns[i]);
				  	Dropdown.getDropDownBox(_array_dropdown[i]);
				  	//jsDebug.debug("...dropdown : "+dropdowns[i].id+"  :  "+((new Date())-startDate));
				}
			}					
	
			var tables = document.getElementsByTagName("TABLE");
			if(tables){
				var tables_length = tables.length;
				for(var i=0; i<tables_length; i++){
				  var table = tables[i];
				  if(table.className=="datatable"){
				    //var startDate = new Date();	
				    makeDataTable(table, true);  
				    //var time = ((new Date())-startDate);
				    //jsDebug.debug("... table : "+tables[i].id+"  :  "+time);		
				  }			  	  
				}	
	
			}			
		}
		else{
			finishedLayout();
			document.body.style.visibility = "visible";
		}
	    setTimeout("Control.initDocument();", 0);
  
}

function displayError(){		
	var errsLength = jspTaglibErrors.length;
	for(var i=0; i < errsLength; i++){
		jspTaglibErrors[i].stackInfo = System.decode(jspTaglibErrors[i].stackInfo);
	}
	if(errsLength && errsLength>0){
		MessageBox.Show(null, "", null, 'LogOK', "Error", 1, null, null, jspTaglibErrors);
	}	
}

function freshDatasetControls(){
  var array_dataset_length = Document.array_dataset.length;
  for(var i=0; i<array_dataset_length; i++){
    var current_dataset = Document.array_dataset[i];
    if(current_dataset.staticDataSource && current_dataset.xmlFormat=="records"){      
      if(current_dataset.getCount()==0 && System.isTrue(current_dataset.readOnly))
      {
        //nothing-todo
      }
      else{
        //var startDateF = new Date();
      	current_dataset.refreshControls();
      	//jsDebug.debug("......... :  "+current_dataset.id+" "+((new Date())-startDateF));    
      }
    }
  }
}

function freshDatasetControls2(){
  var array_dataset_length = Document.array_dataset.length;
  for(var i=0; i<array_dataset_length; i++){
    var current_dataset = Document.array_dataset[i];
    //页面初始化完毕后，非下拉数据集不用刷新
    if(!current_dataset.staticDataSource && current_dataset.xmlFormat=="records"){
      //var date222 = new Date();
	  current_dataset.refreshControls();
	  //jsDebug.debug("....545454545.... :  "+current_dataset.id+" "+((new Date())-date222));   
    }
  }
}

function copyRecord(dataset, record, isCovert){
	for(var i=0; i<dataset.fields.fieldCount; i++){
			var fieldName=dataset.getField(i).name;
			var field=record.dataset.getField(fieldName);
			if (field) {
				var value=record.getValue(fieldName);
				if (typeof(value)!="undefined"){
					if(isCovert){
						dataset.setValue(fieldName, value);
					}else if(!dataset.getValue(fieldName)){
						dataset.setValue(fieldName, value);
					}
				}
			}
	}

}

function onLogoutSystem(){
	invalidSession();
}

function invalidSession(){
	var callBack = function( reply ){
	}
	var ajaxCall = new NDAjaxCall(false);
	ajaxCall.remoteCall("SignOnService","invalidSession",[],callBack);
	delCookie("JSESSIONID_VSOP");
}
function getCookie(name)//取cookies函数        
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) return unescape(arr[2]); return null;

}

function delCookie(name)//删除cookie
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

function getDateTime(){
    var today = new Date() ;
    var year = today.getFullYear() ;
    var month = today.getMonth() + 1 ;
    var day = today.getDate() ;
    
    if( month < 10 )
    	month = "0" + month ;
    if( day < 10 ) 
    	day = "0" + day ;
    var strToday = year + "-" + month + "-" + day ;
    var hh = today.getHours() ;
    var mm = today.getMinutes() ;
    var ss = today.getTime() % 60000 ;
    ss = ( ss - ( ss % 1000 ) ) / 1000 ;
    if( hh < 10 )
    	hh = "0" + hh;
    var clock = hh + ":" ;
    
    if( mm < 10 )
    	clock += "0" ;
    	
    clock += mm + ":" ;
    
    if( ss < 10 )
    	clock += "0" ;
    	
    clock += ss ;
    
    return strToday + " " + clock ;
}

function getTodayStr(){
    var today = new Date() ; 
    var year = today.getFullYear() ;
    var month = today.getMonth() + 1 ;
    var day = today.getDate() ;
    
    if( month < 10 )
    	month = "0" + month ;
    if( day < 10 ) 
    	day = "0" + day ;
    var strToday = year + "-" + month + "-" + day ;
    return strToday ;
}


function exportExcel(dataset, excelJspFile, popup){
 	//if(dataset.getCount()==0){
 	if( dataset.getRecordCount() == 0 ){
      alert("列表中没有数据,无法导出!");
      return;
    }
    exportExcelNoAlert(dataset, excelJspFile, popup);
}

function exportExcelNoAlert(dataset, excelJspFile, popup){
    
	var excelURL = new Array();
	
	excelURL.push(excelJspFile+"?");

    var ps = dataset.parameters();	
    for( var i = 0; i < ps.count(); i ++ ){
    	var parameterName = ps.indexToName(i);
    	var parameterValue = ps.getValue( parameterName );
    	excelURL.push(parameterName+"="+parameterValue+"&");
    }
    
    if(popup){
    	openDialog("reportExcel.jsp", excelURL.join(""), "", "300px", "150px");
    }
    else{
	    var exportToExcel = window.frames["exportToExcel"];
	
	    if(typeof(exportToExcel)=="undefined" || exportToExcel==null){
	      var frame = document.createElement("IFRAME");
	      frame.id = "exportToExcel";
	      frame.style.display = "none";
	      document.body.insertAdjacentElement("beforeEnd",frame);
	      exportToExcel = window.frames["exportToExcel"];
	    }
	    exportToExcel.location = excelURL.join("");	
	}
}

var put_methods = ["lanId", "businessId", "dealExchId", "masterExchId", "subExchId"];
var put_methods_eventInit = false;
function refreshPutMethod(dataset, index, property){
	var checkbox = $("label_checkbox_numberQuery_putMethod");
	if(!put_methods_eventInit && checkbox){
	  	checkbox.attachEvent("onclick", function(){
	  	  	refreshPutMethod(numberQuery, (event.srcElement.checked ? numberQuery.getValue("putMethod") : -1));
	  	});
	  	put_methods_eventInit = true;	
  	}
	
	if(typeof(property)=="undefined" || property==null)
		property = "display";
		
	for(var i=0; i<put_methods.length; i++){
		if(property=="display"){
			$("label_"+dataset.id+"_"+put_methods[i]).style.display = (i<=index ? "" : "none");
			$("text_"+dataset.id+"_"+put_methods[i]).style.display = (i<=index ? "" : "none");
		}
		else{
			$("label_"+dataset.id+"_"+put_methods[i]).style.visibility = (i<=index ? "visible" : "hidden");
			$("text_"+dataset.id+"_"+put_methods[i]).style.visibility = (i<=index ? "visible" : "hidden");
		}
	}  
}
function getPutAreaId(dataset, index){
	var result = "";
	var checkbox = $("label_checkbox_"+dataset.id+"_putMethod");
	if(checkbox && !checkbox.checked){
		//nothing-todo;
	}
	else{
    	result = dataset.getValue(put_methods[index]);
	}
	return result;
}
function getPutAreaName(dataset, index){
    return $("text_"+dataset.id+"_"+put_methods[index]).value;
}
function PUT_METHOD_onSelect(dropdown, record, editor){
  refreshPutMethod(editor.dataset, record.getValue("value"));
  return true;
}

function breakHear(){
	document.getElementByID("fdsa").innerText = "";
}
function getStaffDefaultDealExchName(){
	var exchName = "";
	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ) {
		exchName = reply.getResult();
	}
	ajaxCall.remoteCall("PartyService","getStaffDefaultDealExchName",[],callBack);
	return exchName ;
}
function getStaffDefaultDealExchId(){
	var exchId = "";
	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ) {
		exchId = reply.getResult();
	}
	ajaxCall.remoteCall("PartyService","getStaffDefaultDealExchId",[],callBack);
	return exchId ;
}
function isPrivilegeInRegion( privCode ,  regionId,  regionType,  privType){
	if( regionId == "" || regionId == null ){
		return false ;
	}
	if( privCode == "" || privCode == null ){
		return false ;
	}
	if( regionType == "" || regionType == null ){
		return false ;
	}
	if( privType == "" || privType == null ){
		return false ;
	}
	var result = false ;
	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ) {
		result = reply.getResult();
	}
	ajaxCall.remoteCall("PrivilegeService","isPrivilegeInRegion",[privCode ,  regionId,  regionType,  privType],callBack);
	return result ;
}
function getStaffOrganizationId() {
	var orgId = "";
	var ajaxCall = new NDAjaxCall( false ) ;
	var callBack = function( reply ) {
		orgId = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getStaffOrganizationId",[],callBack);
	return orgId ;
}
function getStaffOrganizationName() {
	var orgName = "";
	var ajaxCall = new NDAjaxCall( false ) ;
	var callBack = function( reply ) {
		orgName = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getStaffOrganizationName",[],callBack);
	return orgName ;
}
function getStaffLoginInfo() {
	var loginInfo = null ;
	var ajaxCall = new NDAjaxCall( false ) ;
	var callBack = function( reply ) {
		loginInfo = reply.getResult() ;
	}
	ajaxCall.remoteCall("LoginService","getLoginInfo",[],callBack);
	return loginInfo ;
}
function getStaffCompanyName(){
	var ajaxCall = new NDAjaxCall( false ) ;
	var companyName = null ;
	var callBack = function( reply ) {
		companyName = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getStaffCompanyName",[],callBack);
	return companyName ;
}
function getStaffCompanyId(){
	var ajaxCall = new NDAjaxCall( false ) ;
	var companyId = null ;
	var callBack = function( reply ) {
		companyId = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getStaffCompanyId",[],callBack);
	return companyId ;
}

function getDcSystemParam( paramCode ) {
	var ajaxCall = new NDAjaxCall( false ) ;
	var paramValue = null ;
	var callBack = function( reply ) {
		paramValue = reply.getResult() ;
	}
	ajaxCall.remoteCall("StaticAttrService","getDcSystemParam",[paramCode],callBack);
	return paramValue ;
}

function getProjectCode(){
	var ajaxCall = new NDAjaxCall(false);
	var result ;
	var callBack = function( reply ){
		result = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getProjectCode",[],callBack); 
	return result ;
}

function checkIsNumAndLetter (str) {
	var numCollection = '0123456789';
   var strCollection = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
   var strResult = false ;
   var numResult = false ;
   for (i = 0; i < str.length; i++) {
      strSubstr = str.substring(i, i + 1);
      if (strCollection.indexOf(strSubstr) >= 0)
          strResult = true;
   }
   for (i = 0; i < str.length; i++) {
      strSubstr = str.substring(i, i + 1);
      if (numCollection.indexOf(strSubstr) >= 0)
          numResult = true;
   }
   if( strResult && numResult ){
   	return true ;
   }
   return false;
}

//编辑框长度限制 汉字算两个字符长度
function EditValueLenLimit(aEdit,maxLen,aEditTitle)
{
    var len=0;
    for (var i=0;i<aEdit.length;i++){
      if (aEdit.charCodeAt(i)>255)
          len+=2;
      else
          len++;
    }
    if(len > maxLen){
        alert(aEditTitle+"字符长度不能超过:"+maxLen+",(汉字算两个字符长),请检查!");
        return false;
    }
    return true;
}

//定位到DATASET当前页面中某条记录
function locateRecord(dsId,fieldName,fieldValue){
  if(fieldValue==null||fieldValue==""){
    return;
  }
  var rec=dsId.getFirstRecord();
  while(rec){
    var fValue=rec.getValue(fieldName);
    if(fValue==fieldValue){
      break;
    }
    rec=rec.getNextRecord();
  }
  if(rec){
    dsId.setCurrent(rec);
  }
}

//如果DATASET中数据被修改过，则刷新当前页面
function loadCurPage(dsId){
  var isModify=dsId.isModified();
  if(isModify){
    dsId.flushData(dsId.getPageIndex());
    return true;
  }
  return false;
}

//获取DATASET当前记录中某字段的值
function getCurValue(dsId,fieldName){
  var retValue="";
  var rec=dsId.getCurrent();
  if(rec){
    retValue=dsId.getValue(fieldName);
  }
  return retValue;
}

//获取服务器时间
function getServerTime(){
	var curTime="";
	NDAjaxCall.getSyncInstance().remoteCall('CustService','getCurServerTime',[],
		function callBack(reply){ 
			curTime=reply.getResult();
		}
  );
  return curTime;
}

function btnOnClick(btn , elementEventName , datasetId , fieldName){
	if(btn.disabled) 
		return false;
	if(elementEventName){
		
		//if (Document.isUserEventDefined(elementEventName)){
			var fieldValue='';
			if(fieldName.indexOf('_name')>-1){
				fieldValue=fieldName.substr(0,fieldName.length-5);
			}
			Document.fireUserEvent(elementEventName, [datasetId, fieldName,fieldValue]);
		//}

	}else{
		Document.fireUserEvent(Document.getElementEventName(btn, "onClick"), [btn]);
	}
	return false;
}
