function page_onLoad(){
	initRoles();
}
//��ʼ��TreeList�����������ʾ
function initRoles(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.roleTreeView.loadByXML( queryResult );
		
		//����ϸ����Ӧ��Dataset�ϲ���һ���ռ�¼
		roleInfo.insertRecord(null);
		globleItem = document.all.roleTreeView.items[0]; 
		fillValues(globleItem);		
	}
	
	ajaxCall.remoteCall("PrivilegeService","getXMLRoleList",[],callBack);
	//ajaxCall.remoteCall("PartyService","getCurrentStaffRoles",[],callBack);//��½Ա��ֻ�ܹ�����ӵ�еĽ�ɫ
    //ajaxCall.remoteCall("PrivilegeService","getRoleList",[],callBack);//
}

function doQueryAll_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�����ٲ�ѯ��ɫ!");
		return ;
	}
	initRoles();
}

function doQuery_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�����ٲ�ѯ��ɫ!");
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
			alert("û�з��ϲ�ѯ�����ļ�¼!");
		}
		//����ϸ����Ӧ��Dataset�ϲ���һ���ռ�¼
		roleInfo.insertRecord(null);
		globleItem = document.all.roleTreeView.items[0]; 
		fillValues(globleItem);		
	}
	
	//ajaxCall.remoteCall("PrivilegeService","getXMLRoleListByName",[queryInfo.getValue("roleName")],callBack);
	ajaxCall.remoteCall("PartyService","getCurrentStaffRoleListByName",[queryInfo.getValue("roleName")],callBack);
}

//����һ�е���Ϣ����ϸ��Ϣ���
function fillValues(item){
	for(var ele in item){
		if(roleInfo.getField(ele)){
			roleInfo.setValue(ele, item[ele]);
		}
	}
}

//���ĵ���¼�������ϸ�������ʾ��ǰ�����еļ�¼����ϸ��Ϣ
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

//�����ϸ����ϵ���Ϣ
function cleanValues(){
	roleInfo.clearData();
	roleInfo.insertRecord(null);
}

var actionType = "";



//���ӽ�ɫ��Ϣ
function addRole_onClick(){
    $("rolePage").setSelectedPageIndex(0);
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ���������ӽ�ɫ!");
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

//�༭��ɫ��Ϣ
function editRole_onClick(){
    $("rolePage").setSelectedPageIndex(0);
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ���������ӽ�ɫ!");
		return ;
	}
	
	//�жϽ�ɫ�Ƿ��Ѿ�������ʹ��,����Ѿ�������ʹ��,�������޸�
	/*if( checkRoleInUsed() ){	����䶯�������ɫ�Ѿ������䣬Ҳ���޸�
		alert("��ɫ�Ѿ�������ʹ��,�����޸�!");
		return ;
	}*/
	actionType = "update" ;
	roleInfo.setReadOnly(false);
}

//��ȡ��ǰ��ѡ�еĽ�ɫ��¼��id
function getCurrentRoleId(){
	var roleTree = document.all.roleTreeView;
	var selItem = roleTree.selectedItem;

	if( selItem != null ){
		return selItem.roleId ;
	}else{
		return null ;
	}
}

//ɾ����ɫ��Ϣ
function deleteRole_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ���������ӽ�ɫ!");
		return ;
	}
	if( !confirm( "��ȷ��Ҫɾ����ǰ��ɫ��¼��?" )){
		return ;
	}
	
	var currentRoleId = getCurrentRoleId();
	
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		var deleteResult = reply.getResult() ;
		if( deleteResult == 0 ){
			deleteRoleFromTreeList();
			alert("ɾ����ɫ�ɹ���");
		}else if( deleteResult == 1 ){
			alert("��ɫ�Ѿ�������ʹ��,����ɾ��!");
		}else {
			alert("ɾ����ɫʧ��!" );
		} 
	}
	ajaxCall.remoteCall("PrivilegeService","deleteRole",[currentRoleId],callBack);
}

//��TreeList��ɾ��һ����¼
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

//��Update��ǰ�Ľ�ɫ�Ժ󣬸���TreeList�϶�Ӧ�ļ�¼
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

//��TreeList������һ����¼ 
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
	//�Ѹýڵ�ѡ���ϣ�����������룬���鲻Ҫѡ��
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
		alert("���ȵ��'ȷ��'��ť���������Ժ����л��������ı�ǩҳ!");
		return false ;
	}
	return true ;
}

//��TabPage֮���л���ʱ�򴥷������л���Ȩ��ҳ���ʱ��ͨ����ǰ��
//��λ��ID���������˻�ȡ�����λ��Ӧ��Ȩ����Ϣ����ʾ��TreeList�С�
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
	/*if( checkRoleInUsed()){		����䶯�������ɫ�Ѿ�������ʹ�ã�Ҳ��ɾ����Ȩ��
		alert("��ɫ�Ѿ�������ʹ��,������ɾ����Ȩ��!");
		return ;
	}*/
	
	var childItems = document.all.privilegeTreeView.selectedItem.items ;
	if( childItems){
		alert("��Ҫɾ����Ȩ�����¼�Ȩ��,����ɾ���¼�Ȩ��!");
		return ;
	}
	if( !confirm("ȷʵҪɾ����ǰ�Ľ�ɫȨ����?")){
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
			alert("ɾ����ɫȨ�޳ɹ�!");
			initRolePrivilege();
		}else{
			alert("ɾ����ɫȨ��ʧ��!");
		}		
	}
	
	ajaxCall.remoteCall("PrivilegeService","deleteRolePriv",[currentRoleId,privilegeId,privilegeType],callBack);
}

function getPrivilegeIdsByRoleId( roleId ) {
	var ajaxCall = new NDAjaxCall( false ) ;//ͬ����ʽ��ѯ���ݿ�
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
		alert("�ڸ���ɫ����Ȩ��֮ǰ��ѡ��һ����ɫ��");
		return ;
	}		
	
	/*if( checkRoleInUsed()){		����䶯�������ɫ�Ѿ������䣬Ҳ�����޸���Ȩ��
		alert("��ɫ�Ѿ�������ʹ��,������Ϊ�����Ȩ��!");
		return ;
	}*/
	var selectedPrivilegeIds = getPrivilegeIdsByRoleId(currentRoleId);
	var returnValue =window.showModalDialog("RolePrivilegeDialog.jsp",[selectedPrivilegeIds,currentRoleId],"dialogHeight: 408pt; dialogWidth: 444pt;  edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 1 ){
		initRolePrivilege();//���³�ʼ����λȨ���б�
	}
}

function relatingRole_onClick(){
	/*if( checkRoleInUsed()){ ����䶯�������ɫ�Ѿ�����ʹ�ã�Ҳ����Ϊ����������ɫ
		alert("��ɫ�Ѿ�������ʹ��,������Ϊ����������ɫ!");
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
		alert("�ڸ���ɫ���������ɫ֮ǰ��ѡ��һ����ɫ��");
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
			alert("������ɫ�ɹ���");
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
	
	/*if( checkRoleInUsed()){		����䶯�������ɫ�Ѿ�������ʹ�ã�Ҳ����ɾ����ǰ�Ĺ�����ϵ
		alert("��ɫ�Ѿ�������ʹ��,����ɾ����ǰ�Ĺ�����ϵ!");
		return ;
	}*/
		
	if( !confirm("ȷ��Ҫɾ����ǰ�Ĺ�����ϵ��?")){
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
			alert("ɾ��������ɫ�ɹ�!");
			initRelatingRoles();
		}else{
			alert("ɾ��������ɫʧ��!");
		}		
	}
	ajaxCall.remoteCall("PrivilegeService","deleteRolePriv",[currentRoleId,roleId,privilegeType],callBack);

}