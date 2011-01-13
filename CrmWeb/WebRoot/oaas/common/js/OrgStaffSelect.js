function page_onLoad(){
	initOrganizationTree();
}

function initOrganizationTree(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.orgTreeView.loadByXML(queryResult );
		getStaffByOrgId();
	}
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}

function getStaffByOrgId(){
	var orgId = orgTreeView.selectedItem.partyId ;
	var staff = new Staff() ;
	staff["orgPartyId"] = orgId ;
	staff["state"] = "00A";
	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;  
	parameterSet.setValue("pageIndex","1");
	Dataset.reloadData( staffList ) ;    
}

//��֯���ĵ��ʱ��,�����ϻ�ȡorgId,��������֯id���������˲�ѯ��Ӧ����Ա
function clickOrganization(){
	var selItem = orgTreeView.selectedItem ;
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( false ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
		}
		getStaffByOrgId();
	}
	
	var regionId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
}
//��ѯStaff���������Ӧ�ı�����
function doQuery_onClick(){
	var staff = new Staff();
	staff.orgPartyId = orgTreeView.selectedItem.partyId ;
	staff.staffCode = queryInfo.getValue("staffCode" );
	staff.partyName = queryInfo.getValue("partyName");
	staff.scope = queryInfo.getValue("queryScope");
	staff.state = "00A";

	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;
	Dataset.reloadData( staffList ) ;
}
//���ò�ѯForm
function doReset_onClick(){
	queryInfo.clearData();
	queryInfo.insertRecord( null ) ;
}

function confirm_onClick(){
	var orgNode = orgTreeView.selectedItem ;
	var selectType = window.dialogArguments ;//��ѡ��־,1 Ϊ��ѡ, 0 Ϊ��ѡ
	var record = staffList.getFirstRecord();
	var i = 0 ;
	var staffArray = new Array();
	while( record != null ){
		if( System.isTrue( record.getValue("select") ) ){
			var staff = new Staff();
			staff["id"] = record.getValue("partyRoleId");
			staff["name"] = record.getValue("partyName");
			staff["staffCode"] = record.getValue("staffCode");
			staff["orgId"] = record.getValue("orgPartyId");
			staff["orgName"] = record.getValue("orgPartyName");

			staffArray[i] = staff ;
			i ++ ;
			if( selectType == "1" && i > 1 ){
				alert("ֻ��ѡ��һ����Ա!");
				return;
			}
		}
		record = record.getNextRecord() ;
	}
	window.returnValue = staffArray;
	window.close();
	/*if( selectType == "1" ){
		window.returnValue = staffArray[0] ;
	}else{
		window.returnValue = staffArray ;
	}
	window.close();*/
}

function cancel_onClick(){
	window.returnValue = null ;
	window.close();
}

function Staff(){
	this.partyRoleId = "" ;
	this.partyName = "" ;
	this.staffCode = "" ;
	this.staffDesc = "" ;
	this.password = "" ;
	this.officeId = "";
	this.pwdvalidtype = "" ;
	this.limitCount = "" ;
	this.partyId = "" ;
	this.orgPartyId = "" ;
	this.orgPartyName = "";
	this.partyRoleType = "" ;
	this.orgManager = "" ;
	this.createDate = "" ;
	this.expDate = "" ;
	this.effDate = "" ;
	this.state = "" ;
	this.stateDate = "" ;
	this.gender = "" ;
	this.birthPlace = "" ;
	this.birthDate = "" ;
	this.maritalStatus = "" ;
	this.skill = "" ;
	this.scope="";//��ѯ��Χ
	this.channelSegmentId="";
}