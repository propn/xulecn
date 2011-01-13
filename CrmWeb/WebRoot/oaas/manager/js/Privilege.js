function page_onLoad(){
	initPrivilege();
}

function btn_queryPrivilege_onClick(){
 	var queryResult = null ;
 	var privName = privNameInfo.getValue("privName");

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.privilegeTreeView.loadByXML(queryResult);
		privilegeInfo.insertRecord(null);
		globleItem = document.all.privilegeTreeView.items[0];
		fillValues(globleItem); 
	}
	if(privName==null||privName=='')initPrivilege();
	else
	   ajaxCall.remoteCall("PrivilegeService","getPrivilegeListWithRegionRelatByName",[privName],callBack);
}

//初始化TreeList组件的数据显示
function initPrivilege(){ 
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.privilegeTreeView.loadByXML(queryResult);
		//在详细面板对应的Dataset上插入一个空记录
		privilegeInfo.insertRecord(null);
		
		globleItem = document.all.privilegeTreeView.items[0];
		fillValues(globleItem); 
		//queryPrivilegeData();
	}
	
	//ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",["-1"],callBack);
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListWithRegionRelatByParentId",["-1"],callBack);//对于权限的管理，登陆员工只能管理其拥有的权限
}

function clickPriv(){
	
}

//填充第一行的信息到详细信息面板
function fillValues(item){
	for(var ele in item){
		if(privilegeInfo.getField(ele)){
			privilegeInfo.setValue(ele, item[ele]);
		}
	}					
}

//清除详细面板上的信息
function cleanValues(){
	privilegeInfo.clearData();
	privilegeInfo.insertRecord(null);
}

function downloadSubItems(){
	var selItem = privilegeTreeView.selectedItem ;
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
		}
	}
	
	var privId = selItem.privId ;
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",[privId], callBack);
}

/*function queryPrivilegeData(){
	dataPrivilegeList.clearData();
	var privilegeType = privilegeTreeView.selectedItem.privType ;
	var privilegeId = privilegeTreeView.selectedItem.privId ;
	dataPrivilegeList.parameters().setDataType("privilegeId","string");
	dataPrivilegeList.parameters().setValue("privilegeId",privilegeId);
	dataPrivilegeList.parameters().setDataType("privilegeType","string");
	dataPrivilegeList.parameters().setValue("privilegeType",privilegeType);
	
	Dataset.reloadData( dataPrivilegeList ) ;
}*/

//表格的点击事件，在详细面板中显示当前被点中的记录的详细信息
function clickPrivilege(){
	var privilegeTree = document.all.privilegeTreeView;
	var selItem = privilegeTree.selectedItem;
	
	downloadSubItems() ;
	
	cleanValues() ;//清除Dataset中的旧数据
	fillValues(selItem);//给Dataset赋值
	privilegeInfo.setReadOnly( true ) ;
	actionType = "";
	nodeType = "";

	var parentItem = selItem.getParentItem() ;
	if( parentItem != null ){
		privilegeInfo.setValue("parentPrivilegeName",parentItem.privName); 
	}
	mainPage.setSelectedPageIndex(0);
	window.frames["dynaFrame"].location = "BlankDataPrivilegeView.jsp";
}

function changeDataPrivilegeView(){
	var privType = privilegeTreeView.selectedItem.privType ;
	var privId = privilegeTreeView.selectedItem.privId ;
	
	var getValMode = "" ;
	var url = "";
	var addDataPrivilegeDialogURL = "";
	var record = dataPrivilegeRuleList.getFirstRecord() ;
	while( record ) {
		if( privType == record.getValue("privType") ){
			getValMode = record.getValue("getValMode") ;
			url = record.getValue("transSql") ;
			addDataPrivilegeDialogURL = record.getValue("getValSql");
			break ;
		}
		record = record.getNextRecord() ;
	}
	
	var privilegeType = privilegeTreeView.selectedItem.privType ;
	var privilegeId = privilegeTreeView.selectedItem.privId ;
	if( "0" == getValMode ){
		window.frames["dynaFrame"].location = "BaseDataPrivilegeView.jsp?privilegeType=" + privilegeType + "&privilegeId=" + privilegeId + "&addDataPrivilegeDialogURL=" + addDataPrivilegeDialogURL;
	}else if( "1" == getValMode ){
		window.frames["dynaFrame"].location = url + "?privilegeType=" + privilegeType + "&privilegeId=" + privilegeId + "&addDataPrivilegeDialogURL=" + addDataPrivilegeDialogURL; 
	}else if( "2" == getValMode ){//如果是2,则表示当前权限不需数据光联
		return false ;
	}
	return true ;
}

//获取当前被选中的权限记录的id
function getCurrentPrivilegeId(){
	var privilegeTree = document.all.privilegeTreeView;
	var selItem = privilegeTree.selectedItem;
	if( selItem != null ){
		return selItem.privId ;
	}else{
		return null ;
	}
}

//通过上级权限ID获取上级权限对象
function getParentPrivilegeById( parentId ){
	var privilegeTree = document.all.privilegeTreeView;
	var items = privilegeTree.items;
	for( var i = 0 ; i < items.length ; i ++ ){
		if( items[i].privId == parentId ){
			return items[i] ;
		}
	}
}

//增加权限
function addPrivilege(parentPrivilegeId){
	cleanValues();
	mainPage.setSelectedPageIndex(0);
	if( parentPrivilegeId != null ){//如果是在一个上级权限下面增加子权限。
		var item = getParentPrivilegeById(parentPrivilegeId) ;
		privilegeInfo.setValue("parentPrivilegeName", item.privName ) ;//设置上级权限的名称
		privilegeInfo.setValue("parentPrgId",item.privId);//设置上级权限的ID
		privilegeInfo.setValue("privType",item.privType);
	}
	privilegeInfo.setReadOnly(false);
	if( nodeType == "root"){
		privilegeInfo.setFieldReadOnly("parentPrivilegeName",true);
	}else if( nodeType == "child" ){ 
		privilegeInfo.setFieldReadOnly("parentPrivilegeName",true);	
		privilegeInfo.setFieldReadOnly("privType",true);
	}
}

//增加一个子权限
function addSubPrivilege_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加权限!");
		return ;
	}
	actionType = "insert";
	nodeType = "child";
	addPrivilege( getCurrentPrivilegeId() ) ;
}

//增加一个根权限
function addRootPrivilege_onClick(){ 
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加权限!");
		return ;
	}
	actionType = "insert";
	nodeType = "root" ;
	addPrivilege( null ) ;
}

function deletePrivilege_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加权限!");
		return ;
	}
	if( !confirm( "您确信要删除当前权限记录吗?" )){ 
		return ;
	}
	
	var currentPrivilegeId = getCurrentPrivilegeId();
	
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		var deleteResult = reply.getResult() ;
		if( deleteResult == 0 ){
			deletePrivilegeFromTreeList();//将当前记录从TreeList上删除
			alert("删除权限成功！");
		}else if( deleteResult == 1 ){
			alert("权限已经被分配使用,不能删除!");
		}else {
			alert("删除权限失败，可能有下级节点。" );
		}
	}
	
	ajaxCall.remoteCall("PrivilegeService","deletePrivilege",[currentPrivilegeId],callBack);
}

//函数名：fucPWDchk
//功能介绍：检查是否含有非数字或字母
//参数说明：要检查的字符串
//返回值：0：含有 1：全部为数字或字母
function fucPWDchk(str)
{
  var strSource ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  var ch;
  var i;
  var temp;

  for (i=0;i<=(str.length-1);i++)
  {

    ch = str.charAt(i);
    temp = strSource.indexOf(ch);
    if (temp==-1)
    {
     return 0;
    }
  }
  if (strSource.indexOf(ch)==-1)
  {
    return 0;
  }
  else
  {
    return 1;
  }
}


//提交按钮响应事件，调用服务器端服务插入记录
function btn_commitPrivilege_onClick(){
	if( actionType == "" ){
		return ;
	}
	
	if(!$("privilegeInfoForm").validate()){
		return ;
	}
	if(fucPWDchk(privilegeInfo.getValue("privCode"))==0){
	    alert("权限编码只能为6位的数字或字母!");
	    return;
	}
	
	
	var ajaxCall = new NDAjaxCall(true);
	

	var privilege = new Privilege();
	//将Dataset里面的数据赋值给菜单对象
	for(var ele in privilege){ 
		if(privilegeInfo.getField(ele)){
			privilege[ele] = privilegeInfo.getValue( ele );
		} 
	}
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result ) {
			if( actionType == "insert" ){//增加操作
				//服务器端返回新增加的权限的ID
				refreshDatainfo(result);//完成增加以后，必须重新初始化关联的Dataset里面的响应信息			
				addPrivilegeToTreeList(); 
				alert("增加权限成功!");
			}else if ( actionType == "update" ){//编辑操作
				refreshTreeList(); 
				alert("编辑权限成功!");
			}
		}

		privilegeInfo.setReadOnly(true);	
		actionType = "" ;
		nodeType = "" ;
				
	}
	
	//检测权限编码是否存在
   var checkPrivCode=function(reply){
	var result=reply.getResult();
	if(result==true){
	     alert("权限编码已经存在，请输入另外一个编码！");
	     return;
	    }
	    else{
	    
	var arr = new Array();
	arr[0] = privilege;
	if( actionType == "insert" ){//增加操作
		if( nodeType == "root" ){
			privilege["parentPrgId"] = "-1";
		}
		ajaxCall.remoteCall("PrivilegeService","insertPrivilege",arr,callBack);
	}else if ( actionType == "update" ){//编辑操作
		ajaxCall.remoteCall("PrivilegeService","updatePrivilege",arr,callBack);
	}
	    }
	}
	ajaxCall.remoteCall("PrivilegeService","hasPrivlegeByCode",[privilegeInfo.getValue("privCode")],checkPrivCode);
}

//在Update当前的权限以后，更新TreeList上对应的记录
function refreshTreeList(){
	var privilegeTree = document.all.privilegeTreeView;
	var privilege = privilegeTree.selectedItem;
	
	for(var ele in privilege){
		if(privilegeInfo.getField(ele)){
			privilege[ele] = privilegeInfo.getValue( ele );
		} 
	}
	privilege.refresh();
}

function refreshDatainfo(id){
	privilegeInfo.setValue("privId", id ) ;
}

function addChildToTreeList(){
	var privilegeTree = document.all.privilegeTreeView;
	var selItem = privilegeTree.selectedItem;
	var privilege = privilegeTree.createTreeNode();
	
	for(var ele in selItem){
		if(privilegeInfo.getField(ele)){
			privilege[ele] = privilegeInfo.getValue( ele );
		} 
	}
	
	selItem.add(privilege);
	privilege.setSelected();
}

function addRootToTreeList(){
	var privilegeTree = document.all.privilegeTreeView;
	var privilege = privilegeTree.createTreeNode();
	
	var privilege1 = new Privilege();
	 
	for(var ele in privilege1){
		if(privilegeInfo.getField(ele)){
			privilege[ele] = privilegeInfo.getValue( ele );
		} 
	}

	//
	privilegeTree.add(privilege);
	//把该节点选择上，如果批量插入，建议不要选择
	privilege.setSelected();
}

var actionType = "" ;//全局变量；操作类型，可以为update:编辑；insert:增加
var nodeType = "";

//编辑权限
function editPrivilege_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再编辑权限!");
		return ;
	}
	mainPage.setSelectedPageIndex(0);
	actionType = "update" ;
	privilegeInfo.setReadOnly(false);
	privilegeInfo.setFieldReadOnly("privType",true);
	privilegeInfo.setFieldReadOnly("parentPrivilegeName",true);
}

//在TreeList上增加一个记录 
function addPrivilegeToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}

//从TreeList上删除一个记录
function deletePrivilegeFromTreeList(){
	var privilegeTree = document.all.privilegeTreeView;
	var selItem = privilegeTree.selectedItem;
	selItem.remove();
}

function btn_cancel_onClick(){
	actionType = "";
	nodeType = "";
	privilegeInfo.setReadOnly(true);	
	clickPrivilege();
}

//权限对象
function Privilege(){
	this.privId = "";
	this.privName = "";
	this.privCode = "";
	this.parentPrgId = "";
	this.privDesc = "";
	this.privType = "";
	this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.PrivVO' ;
}

function button_privilegeInfo_parentPrivilegeName_onClick(){
	if( actionType == "" || nodeType == "root" ){
		return ;
	}
	var returnValue=window.showModalDialog("PrivilegeDialog.jsp",null,"dialogHeight: 408pt; dialogWidth: 444pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue != null ){
		privilegeInfo.setValue("parentPrgId",returnValue["privilegeId"]) ;
		var parentPrivilege = getParentPrivilegeById( returnValue["privilegeId"] ) ;
		privilegeInfo.setValue("parentPrivilegeName",parentPrivilege.privName) ;
	}
}

/*function btn_deleteDataPrivilege_onClick(){
	var obj = new Object() ;
	obj.privId = privilegeTreeView.selectedItem.privId ;
	obj.dataPkey1 = dataPrivilegeList.getValue("dataPkey1");
	obj.dataPkey2 = dataPrivilegeList.getValue("dataPkey2");
	obj.dataPkey3 = dataPrivilegeList.getValue("dataPkey3");
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ) {
		alert("删除成功!");
		queryPrivilegeData();
	}
	ajaxCall.remoteCall("PrivilegeService","deleteDataPrivilege",[obj],callBack);
}
*/
function mainPage_beforePageChanged(page,index){
	if( actionType != ""){
		alert("请先点击'确定'按钮保存数据后再切换到其他标签页!");
		return false ;
	}
	return true ;
}

function mainPage_onPageChanged(page, index ){
	if( index == 1 ){
		if( !changeDataPrivilegeView() ) {
			alert("当前的权限类型不需关联数据!");
			mainPage.setSelectedPageIndex( 0 ) ;
		}
	}
}

function btn_addDataPrivilege_onClick(){
	var privType = privilegeTreeView.selectedItem.privType ;
	var privId = privilegeTreeView.selectedItem.privId ;
	
	var getValMode = "" ;
	var url = "";
	var record = dataPrivilegeRuleList.getFirstRecord() ;
	while( record ) {
		if( privType == record.getValue("privType") ){
			getValMode = record.getValue("getValMode") ;
			url = record.getValue("getValSql" ) ;
			break ;
		}
		record = record.getNextRecord() ;
	}
	var returnValue = null ;
	if( getValMode == "0" ){
		returnValue = window.showModalDialog("PrivilegeDataDialog.jsp",[privId,privType],"dialogHeight: 408pt; dialogWidth: 344pt;");
	}else if( getValMode == "1" ){
		returnValue = window.showModalDialog(url,[privId],"dialogHeight: 408pt; dialogWidth: 344pt;");
	}
	
	/*if( returnValue != null ){
		var record = dataPrivilegeList.getFirstRecord() ;
		while( record ) {
			if( record.getValue( "dataPkey1" ) == returnValue.dataPeky1 &&
			record.getValue( "dataPkey2" ) == returnValue.dataPeky2 &&
			record.getValue( "dataPkey3" ) == returnValue.dataPeky3 ){
				alert("您选择的权限关联数据已经存在!");
				return ;
			}
			record = record.getNextRecord() ;
		}
	
		returnValue.privId = privilegeTreeView.selectedItem.privId ;
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ) { 
			alert("增加成功!"); 
			queryPrivilegeData();
		}
		ajaxCall.remoteCall("PrivilegeService","addDataPrivilege",[returnValue],callBack);
	}*/
}
