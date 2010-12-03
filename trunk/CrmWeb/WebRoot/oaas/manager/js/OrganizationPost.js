function page_onLoad(){
	initOrganizationTree();
}

function initOrganizationTree(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.orgTreeView.loadByXML(queryResult );
		
		var rootItems = orgTreeView.rootItems ;
		for( var i = 0 ; i < rootItems.length ; i ++ ){
			var item = rootItems[i] ;
			item.removeChildren();
		}
		clickOrganization();
//		getPostByOrgId(); 
//		postTableClick();		
	}
	var menuCode = Global.getCurrentMenuCode();
	ajaxCall.remoteCall("PartyService","getRootOrganizationListByPrivControl",[menuCode],callBack);
	//ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}

var actionType = "" ;

function cancelCommit_onClick(){
	postInfo.setReadOnly( true ) ;
	actionType = "" ;
}

function doQueryAll_onClick(){
	getPostByOrgId();
}

function doQuery_onClick(){
	var orgId = orgTreeView.selectedItem.partyId ;
	postList.setLoadDataActionMethod( "queryOrgPosition" ) ;
	
	var parameterSet = postList.parameters();
	if( parameterSet._getParameter( "orgId" ) != null ){
		parameterSet.delParameter("orgId");
	}
	if( parameterSet._getParameter( "postName" ) != null ){
		parameterSet.delParameter("postName");
	}
	if( parameterSet._getParameter( "state" ) != null ){
		parameterSet.delParameter("state");
	}
			
	parameterSet.setDataType("orgId", "string") ;
	parameterSet.setValue("orgId",orgId);
	
	parameterSet.setDataType("postName", "string");
	parameterSet.setValue("postName",queryInfo.getValue("postName"));
	
	parameterSet.setDataType("state","string");
	parameterSet.setValue("state",queryInfo.getValue("state"));
	
	Dataset.reloadData( postList ) ;
}

function getPostByOrgId(){
	var orgId = orgTreeView.selectedItem.partyId ;
	postList.setLoadDataActionMethod( "getPositionListByPartyId" );
	var parameterSet = postList.parameters();
	
	if( parameterSet._getParameter( "orgId" ) != null ){
		parameterSet.delParameter("orgId");
	}
	if( parameterSet._getParameter( "postName" ) != null ){
		parameterSet.delParameter("postName");
	}
	if( parameterSet._getParameter( "state" ) != null ){
		parameterSet.delParameter("state");
	}
	
	parameterSet.setDataType("orgId", "string");
	parameterSet.setValue("orgId", orgId ) ;
 
	Dataset.reloadData( postList ) ;
}

function commitPost_onClick(){
	if( actionType == "" ){
		return ;
	}
	
	if( !$("postInfoForm").validate()){
		return ;
	}
	var organizationPostVO = new OrganizationPostVO();
	var partyId = orgTreeView.selectedItem.partyId ;
	var positionId = postInfo.getValue("positionId");
	var state = postInfo.getValue("state");
	var stateDate = postInfo.getValue("stateDate");
	var orgPostId = postInfo.getValue( "orgPostId" ) ;
	organizationPostVO["partyId"] = partyId ;
	
	organizationPostVO["positionId"] = postInfo.getValue("positionId");
	
	organizationPostVO["state"] = state ;
	organizationPostVO["stateDate"] = stateDate ;
	organizationPostVO["orgPostId"] = orgPostId;
	
	var ajaxCall = new NDAjaxCall(true);
	var result = null ;
	var callBack = function( reply ){
		result = reply.getResult() ;
		if( result ){
			if( result == "-1" ){
				alert("��ѡ��ĸ�λ��Ϣ�Ѿ��Ǳ���֯�ĸ�λ�ˣ������ظ�����!") ;
				postTableClick();
				return ;
			}
			getPostByOrgId();
			postTableClick();
			alert("�����ɹ�!");
		}else{
			alert("����ʧ��!");
		}
		postInfo.setReadOnly( true ) ;
		actionType = "" ;		
	}
	
	var arr = new Array();
	arr[0] = organizationPostVO;
	
	if( actionType == "insert" ) {
		ajaxCall.remoteCall("PartyService","insertOrganizationPost",arr,callBack);
	}else if( actionType == "update" ){
		ajaxCall.remoteCall("PartyService","updateOrganizationPost",arr,callBack);
	}
}

//������֯��λʵ��
function addPost_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ���������Ӹ�λ");
		return ;
	}
	actionType = "insert" ;
	postInfo.setReadOnly( false ) ;
	postInfo.clearData();
	postPage.setSelectedPageIndex(0);
}

function editPost_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�����ٱ༭��λ");
		return ;
	}
	
	if( postList.getValue("orgPostId") == "" ){
		return ;
	}
		
	/*if( checkOrganizationPostInUsed() ){		����䶯����λ�Ѿ������Ҳ�����޸�
		alert("��λ�Ѿ�������ʹ��,�����޸�!");
		return ;
	}*/
	
	actionType = "update" ;
	postInfo.setReadOnly( false ) ;
	postInfo.setFieldReadOnly("positionId",true);
	postPage.setSelectedPageIndex(0);
}

function postPage_beforePageChanged(page,index){
	if( actionType != "" ){
		alert("���ȵ��'ȷ��'��ť�������ݺ����л��������ı�ǩҳ!");
		return false ;
	}
	return true ;
}

function checkOrganizationPostInUsed(){
	var orgPostId = postList.getValue("orgPostId");
	var result = false ;
	var ajaxCall = new NDAjaxCall( false ) ;
	var callBack = function( reply ) {
		result = reply.getResult() ;
	}
	
	ajaxCall.remoteCall("PartyService","checkOrganizationPostInUsed",[orgPostId],callBack);
	return result ;
}

function deletePost_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ������ɾ����λ");
		return ;
	}
	if( postList.getValue("orgPostId") == "" ){
		return ;
	}
		
	if( !confirm( "��ȷ��Ҫɾ����ǰ��λ��?" ) ){
		return ;
	}
	var ajaxCall = new NDAjaxCall(true);
	var result = null ;
	var callBack = function( reply ){
		result = reply.getResult() ;
		if( result == 0 ){
			alert("ɾ���ɹ�!");
			getPostByOrgId();
			queryPostRoles();
			postTableClick();
		}else if ( result == 1 ){
			alert("��λ�Ѿ�������ʹ��,����ɾ��!");
		}else{
			alert("ɾ��ʧ��!");
		}		
	}
	
	var orgPositionId = postList.getValue("orgPostId");//postInfo.getValue("orgPostId");
	if( !orgPositionId ){
		return ;
	}
	
	ajaxCall.remoteCall("PartyService","deleteOrganizationPost",[orgPositionId],callBack);
}

function addRole_onClick(){
	if( postList.getValue("orgPostId") == "" ){
		return ;
	}
	/*if( checkOrganizationPostInUsed() ){		����䶯����λ�Ѿ��������Ҳ�����޸�
		alert("��λ�Ѿ�������ʹ��,�����޸�!");
		return ;
	}*/
	
	var arr = new Array();
	arr[0] = "insert";
	arr[1] = postList.getValue( "orgPostId" );
	arr[2] = Global.getCurrentMenuCode();
	var returnValue=window.showModalDialog("OrgRoleDialog.jsp",arr,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryPostRoles();
	}
}

function editRole_onClick(){
	var editId = postRolesList.getValue("orgPostRoleId");
	if( !editId ){
		return ;
	}
	var arr = new Array();
	arr[0] = "update";
	arr[1] = postRolesList.getValue( "orgPostRoleId" );
	var returnValue=window.showModalDialog("OrgRoleDialog.jsp",arr,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryPostRoles();
	}
}

function deleteRole_onClick(){
	if( postList.getValue("orgPostId") == "" ){
		return ;
	}
	if( checkOrganizationPostInUsed() ){
		alert("��λ�Ѿ�������ʹ��,�����޸�!");
		return ;
	}
	
	if( !confirm("��ȷ��Ҫɾ����ǰ��¼��?")){
		return ;
	}
	var ajaxCall = new NDAjaxCall(true);
	var result = null;
	var callBack = function( reply ){
		result = reply.getResult() ;
		if( result ){
			alert("ɾ����¼�ɹ�!");
			queryPostRoles();
		}else{
			alert("ɾ����¼ʧ��!");
		}		
	}
	var orgPostRoleId = postRolesList.getValue("orgPostRoleId");
	ajaxCall.remoteCall("PartyService","deleteOrgRoleAndScopes",[orgPostRoleId],callBack);
}

function OrganizationPostVO(){
	this.orgPostId = "";//��֯��λID
	this.partyId = "";//������Id
	this.positionId = "";//��λID
	this.state = "";//״̬
	this.stateDate = "";//״̬ʱ��
}

function downloadSubItems(){
	var selItem = orgTreeView.selectedItem ;
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
	
	var regionId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
}

//��֯���ĵ���¼�,�����ϻ�ȡorgId,��������֯id���������˲�ѯ��Ӧ�ĸ�λ
function clickOrganization(){ 
	downloadSubItems();
	getPostByOrgId();
	postTableClick();
}

function postTableClick(){ 
	postInfo.clearData();
	postInfo.insertRecord(null) ;
	
	postInfo.disableControls();
	
	postInfo.setValue("orgPostId",postList.getValue("orgPostId"));
	postInfo.setValue( "partyId" , postList.getValue( "partyId" ) ) ;
	postInfo.setValue( "state", postList.getValue("state"));
	postInfo.setValue("stateDate",postList.getValue("stateDate"));
	postInfo.setValue("positionId",postList.getValue("positionId"));
	
	postInfo.enableControls();
	
	postInfo.setReadOnly( true );
	actionType = "";
	
	//���"��λ��ɫ"���
	queryPostRoles();
	
	postPage.setSelectedPageIndex(0);
}

//��ȡ�͵�ǰ��λ��ص�"��λ��ɫ"�б�
function queryPostRoles(){
	var postId = postList.getValue("orgPostId");

	var parameterSet = postRolesList.parameters();
	parameterSet.setDataType("postId","string");
	parameterSet.setValue("postId", postId);
	Dataset.reloadData( postRolesList );
}

function postPage_onPageChanged(page, index){
	if(index==2){
		staffList.clearData();
		var partyId = orgTreeView.selectedItem.partyId;
		if(!partyId) return;
		var positionId = postList.getValue("positionId");
		if(!positionId) return;

		var parameterSet = staffList.parameters();
		parameterSet.setValue("partyId", ""+partyId) ;
		parameterSet.setValue("positionId", ""+positionId);
		Dataset.reloadData(staffList) ;
	}
}


