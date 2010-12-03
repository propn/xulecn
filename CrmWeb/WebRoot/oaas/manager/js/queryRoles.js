function page_onLoad(){
	initRoles();
}
//初始化TreeList组件的数据显示
function initRoles(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.roleTreeView.loadByXML( queryResult );
		
		//在详细面板对应的Dataset上插入一个空记录
		roleInfo.insertRecord(null);
		globleItem = document.all.roleTreeView.items[0]; 
		fillValues(globleItem);		
	}
	
	ajaxCall.remoteCall("PrivilegeService","getXMLRoleList",[],callBack);
	//ajaxCall.remoteCall("PartyService","getCurrentStaffRoles",[],callBack);//登陆员工只能管理其拥有的角色
    //ajaxCall.remoteCall("PrivilegeService","getRoleList",[],callBack);//
}

function doQueryAll_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再查询角色!");
		return ;
	}
	initRoles();
}

function doQuery_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再查询角色!");
		return ;
	}
	if( queryInfo.getValue("roleName") == "" ){
		return ;
	}
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.roleTreeView.loadByXML( queryResult );
		if( queryResult == "<items/>" ){
			alert("没有符合查询条件的记录!");
		}
		//在详细面板对应的Dataset上插入一个空记录
		roleInfo.insertRecord(null);
		globleItem = document.all.roleTreeView.items[0]; 
		fillValues(globleItem);		
	}
	
	//ajaxCall.remoteCall("PrivilegeService","getXMLRoleListByName",[queryInfo.getValue("roleName")],callBack);
	ajaxCall.remoteCall("PartyService","getCurrentStaffRoleListByName",[queryInfo.getValue("roleName")],callBack);
}

//填充第一行的信息到详细信息面板
function fillValues(item){
	for(var ele in item){
		if(roleInfo.getField(ele)){
			roleInfo.setValue(ele, item[ele]);
		}
	}
}

//表格的点击事件，在详细面板中显示当前被点中的记录的详细信息
function clickRole(){
//	var roleTree = document.all.roleTreeView;
//	var item = roleTree.selectedItem;
//	
//	cleanValues() ;
//	fillValues(item);
//	roleInfo.setReadOnly( true ) ;
//	actionType = "";
//	rolePage_onPageChanged(rolePage, rolePage.getSelectedPageIndex());
}

//清除详细面板上的信息
function cleanValues(){
	roleInfo.clearData();
	roleInfo.insertRecord(null);
}

var actionType = "";



//增加角色信息
function addRole_onClick(){
    $("rolePage").setSelectedPageIndex(0);
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加角色!");
		return ;
	}
	actionType = "insert" ;
	cleanValues();
	roleInfo.setReadOnly(false);
}

function checkRoleInUsed(){
	var selItem = roleTreeView.selectedItem ;
	var ajaxCall = new NDAjaxCall( false ) ;
	var result = false ;
	var callBack = function( reply ) {
		result = reply.getResult() ;
	}
	
	ajaxCall.remoteCall("PrivilegeService","checkRoleInUsed", [selItem.roleId], callBack);
	return result ;
}

//编辑角色信息
function editRole_onClick(){
    $("rolePage").setSelectedPageIndex(0);
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加角色!");
		return ;
	}
	
	//判断角色是否已经被分配使用,如果已经被分配使用,则不允许修改
	/*if( checkRoleInUsed() ){	需求变动，就算角色已经被分配，也能修改
		alert("角色已经被分配使用,不能修改!");
		return ;
	}*/
	actionType = "update" ;
	roleInfo.setReadOnly(false);
}

//获取当前被选中的角色记录的id
function getCurrentRoleId(){
	var roleTree = document.all.roleTreeView;
	var selItem = roleTree.selectedItem;

	if( selItem != null ){
		return selItem.roleId ;
	}else{
		return null ;
	}
}

//删除角色信息
function deleteRole_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加角色!");
		return ;
	}
	if( !confirm( "您确信要删除当前角色记录吗?" )){
		return ;
	}
	
	var currentRoleId = getCurrentRoleId();
	
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		var deleteResult = reply.getResult() ;
		if( deleteResult == 0 ){
			deleteRoleFromTreeList();
			alert("删除角色成功！");
		}else if( deleteResult == 1 ){
			alert("角色已经被分配使用,不能删除!");
		}else {
			alert("删除角色失败!" );
		} 
	}
	ajaxCall.remoteCall("PrivilegeService","deleteRole",[currentRoleId],callBack);
}

//从TreeList上删除一个记录
function deleteRoleFromTreeList(){
	var roleTree = document.all.roleTreeView;
	var selItem = roleTree.selectedItem;
	selItem.remove();
}

function btn_cancelRole_onClick(){
	actionType = "" ;
	roleInfo.setReadOnly(true);
	clickRole();
}

function btn_commitRole_onClick(){
	var record=roleTreeView.selectedItem;
    if (!record) return;
    var back={};
    back['partyRoleId']=record.roleId;
    back['partyName']=record.roleName;
    window.returnValue = back;
    window.close();
}

function refreshDatainfo(id){
	roleInfo.setValue("roleId", id ) ;
}

//在Update当前的角色以后，更新TreeList上对应的记录
function refreshTreeList(){
	var roleTree = document.all.roleTreeView;
	var role = roleTree.selectedItem;
	
	for(var ele in role){
		if(roleInfo.getField(ele)){
			role[ele] = roleInfo.getValue( ele );
		} 
	}
	role.refresh();
}

//在TreeList上增加一个记录 
function addRoleToTreeList(){
	var roleTree = document.all.roleTreeView;
	var role = roleTree.createTreeNode();
	var role1 = new Role();
	
	for( var ele in role1 ){
		if(roleInfo.getField(ele)){
			role[ele] = roleInfo.getValue( ele ) ;
		}
	}
	roleTree.add(role);
	//把该节点选择上，如果批量插入，建议不要选择
	role.setSelected();
}

function Role(){
	this.roleId = "";
	this.roleName  = "";
	this.roleNameShort = "";
	this.roleDesc = "";
	this.state = "" ;
	this.stateDate = "";
	this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.RolesVO' ;
}

function rolePage_beforePageChanged( page, index ){
	if( actionType != "" ){
		alert("请先点击'确定'按钮保存数据以后再切换到其他的标签页!");
		return false ;
	}
	return true ;
}

//在TabPage之间切换的时候触发，当切换到权限页面的时候，通过当前的
//岗位的ID到服务器端获取这个岗位对应的权限信息，显示在TreeList中。
function rolePage_onPageChanged(page,index){
	var currentRoleId = getCurrentRoleId();
	if( currentRoleId == null ) 
		return ;
	if( index == 0 ) 
		return ;
	if( index == 1 ){
		initRolePrivilege();
	}
	
	if( index == 2 ){
		initRelatingRoles();
	}
}

function initRolePrivilege(){
	var currentRoleId = getCurrentRoleId();
	if( currentRoleId == null ) 
		return ;
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.privilegeTreeView.clear();
		document.all.privilegeTreeView.loadByXML(queryResult);
	}
	ajaxCall.remoteCall("PrivilegeService","getPrivXMLItemByRoleId",[currentRoleId,"0"],callBack);
}

function initRelatingRoles(){
	var currentRoleId = getCurrentRoleId();
	var parameters = roleList.parameters();
	parameters.setDataType( "roleId","string");
	parameters.setValue("roleId",currentRoleId);
	Dataset.reloadData( roleList );
}

function deleteRolePrivilege_onClick(){
	if( !document.all.privilegeTreeView.selectedItem.privId ){
		return ;
	}
	/*if( checkRoleInUsed()){		需求变动，就算角色已经被分配使用，也能删除其权限
		alert("角色已经被分配使用,不能再删除其权限!");
		return ;
	}*/
	
	var childItems = document.all.privilegeTreeView.selectedItem.items ;
	if( childItems){
		alert("您要删除的权限有下级权限,请先删除下级权限!");
		return ;
	}
	if( !confirm("确实要删除当前的角色权限吗?")){
		return;
	}
	var currentRoleId = document.all.roleTreeView.selectedItem.roleId ;
	var privilegeId = document.all.privilegeTreeView.selectedItem.privId ;
	var privilegeType = "0";
 	
 	var result = null ;

	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		result = reply.getResult() ;
		if( result ){
			alert("删除角色权限成功!");
			initRolePrivilege();
		}else{
			alert("删除角色权限失败!");
		}		
	}
	
	ajaxCall.remoteCall("PrivilegeService","deleteRolePriv",[currentRoleId,privilegeId,privilegeType],callBack);
}

function getPrivilegeIdsByRoleId( roleId ) {
	var ajaxCall = new NDAjaxCall( false ) ;//同步方式查询数据库
	var privilegeIds = new Array() ;
	var callBack = function( reply ) {
		privilegeIds = reply.getResult() ;
	}
	ajaxCall.remoteCall( "PrivilegeService","getPrivilegeIdsByRoleId", [roleId] , callBack ) ;
	return privilegeIds ;
}
function addRolePrivilege_onClick(){
	var currentRoleId = getCurrentRoleId();
	if( currentRoleId == null ) {
		alert("在给角色分配权限之前请选中一个角色！");
		return ;
	}		
	
	/*if( checkRoleInUsed()){		需求变动，就算角色已经被分配，也可以修改其权限
		alert("角色已经被分配使用,不能再为其分配权限!");
		return ;
	}*/
	var selectedPrivilegeIds = getPrivilegeIdsByRoleId(currentRoleId);
	var returnValue =window.showModalDialog("RolePrivilegeDialog.jsp",[selectedPrivilegeIds,currentRoleId],"dialogHeight: 408pt; dialogWidth: 444pt;  edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 1 ){
		initRolePrivilege();//重新初始化岗位权限列表
	}
}

function relatingRole_onClick(){
	/*if( checkRoleInUsed()){ 需求变动，就算角色已经分配使用，也可以为其分配关联角色
		alert("角色已经被分配使用,不能再为其分配关联角色!");
		return ;
	}*/
	var currentRoleId = getCurrentRoleId();
	var selectedRoles=new Array();
	var currentRecord = roleList.getFirstRecord();// roleList.getCurrent();
	var i = 0 ;
	while( currentRecord ){
		selectedRoles[i] = currentRecord.getValue("roleId");
		currentRecord = currentRecord.getNextRecord();
		i ++ ;
	}
	
	if( currentRoleId == null ) {
		alert("在给角色分配关联角色之前请选中一个角色！");
		return ;
	}
	var dialogArr = new Array();
	dialogArr[0] = currentRoleId ;
	dialogArr[1] = selectedRoles ;
	var returnValue=window.showModalDialog("RoleDialog.jsp",dialogArr,"dialogHeight: 350pt; dialogWidth: 550pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue ){
		var result = null ;
		var ajaxCall = new NDAjaxCall(true);
		var callBack = function( reply ){
			result = reply.getResult() ;
			alert("关联角色成功！");
			initRelatingRoles();
		}
		var arr = new Array();
		arr[0] = currentRoleId ;
		var tmpArr = new Array() ;
		tmpArr[0] = returnValue ;
		arr[1] = tmpArr;
		arr[2] = "1";
		ajaxCall.remoteCall("PrivilegeService","insertRolePriv",arr,callBack);	
	}	
}
function deleteRelation_onClick(){
	if( !roleList.getValue( "roleId")){
		return ;
	}
	
	/*if( checkRoleInUsed()){		需求变动，就算角色已经被分配使用，也可以删除当前的关联关系
		alert("角色已经被分配使用,不能删除当前的关联关系!");
		return ;
	}*/
		
	if( !confirm("确定要删除当前的关联关系吗?")){
		return ;
	}
	
	var currentRoleId = document.all.roleTreeView.selectedItem.roleId ;
	var roleId = roleList.getValue("roleId");
	var privilegeType = "1";
 	
 	var result = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		result = reply.getResult() ;
		if( result ){
			alert("删除关联角色成功!");
			initRelatingRoles();
		}else{
			alert("删除关联角色失败!");
		}		
	}
	ajaxCall.remoteCall("PrivilegeService","deleteRolePriv",[currentRoleId,roleId,privilegeType],callBack);

}