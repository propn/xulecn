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
				alert("您选择的岗位信息已经是本组织的岗位了，不能重复增加!") ;
				postTableClick();
				return ;
			}
			getPostByOrgId();
			postTableClick();
			alert("操作成功!");
		}else{
			alert("操作失败!");
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

//插入组织岗位实体
function addPost_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加岗位");
		return ;
	}
	actionType = "insert" ;
	postInfo.setReadOnly( false ) ;
	postInfo.clearData();
	postPage.setSelectedPageIndex(0);
}

function editPost_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再编辑岗位");
		return ;
	}
	
	if( postList.getValue("orgPostId") == "" ){
		return ;
	}
		
	/*if( checkOrganizationPostInUsed() ){		需求变动，岗位已经分配后也可以修改
		alert("岗位已经被分配使用,不能修改!");
		return ;
	}*/
	
	actionType = "update" ;
	postInfo.setReadOnly( false ) ;
	postInfo.setFieldReadOnly("positionId",true);
	postPage.setSelectedPageIndex(0);
}

function postPage_beforePageChanged(page,index){
	if( actionType != "" ){
		alert("请先点击'确定'按钮保存数据后在切换到其他的标签页!");
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
		alert("请先保存或者取消当前操作再删除岗位");
		return ;
	}
	if( postList.getValue("orgPostId") == "" ){
		return ;
	}
		
	if( !confirm( "您确定要删除当前岗位吗?" ) ){
		return ;
	}
	var ajaxCall = new NDAjaxCall(true);
	var result = null ;
	var callBack = function( reply ){
		result = reply.getResult() ;
		if( result == 0 ){
			alert("删除成功!");
			getPostByOrgId();
			queryPostRoles();
			postTableClick();
		}else if ( result == 1 ){
			alert("岗位已经被分配使用,不能删除!");
		}else{
			alert("删除失败!");
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
	/*if( checkOrganizationPostInUsed() ){		需求变动，岗位已经被分配后也可以修改
		alert("岗位已经被分配使用,不能修改!");
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
		alert("岗位已经被分配使用,不能修改!");
		return ;
	}
	
	if( !confirm("您确定要删除当前记录吗?")){
		return ;
	}
	var ajaxCall = new NDAjaxCall(true);
	var result = null;
	var callBack = function( reply ){
		result = reply.getResult() ;
		if( result ){
			alert("删除记录成功!");
			queryPostRoles();
		}else{
			alert("删除记录失败!");
		}		
	}
	var orgPostRoleId = postRolesList.getValue("orgPostRoleId");
	ajaxCall.remoteCall("PartyService","deleteOrgRoleAndScopes",[orgPostRoleId],callBack);
}

function OrganizationPostVO(){
	this.orgPostId = "";//组织岗位ID
	this.partyId = "";//参与人Id
	this.positionId = "";//岗位ID
	this.state = "";//状态
	this.stateDate = "";//状态时间
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

//组织树的点击事件,从树上获取orgId,并根据组织id到服务器端查询对应的岗位
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
	
	//填充"岗位角色"面板
	queryPostRoles();
	
	postPage.setSelectedPageIndex(0);
}

//获取和当前岗位相关的"岗位角色"列表
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


