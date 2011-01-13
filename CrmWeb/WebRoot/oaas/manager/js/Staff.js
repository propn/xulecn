var menuCode = "";
function page_onLoad(){
    var s = new Date() ;
	menuCode = Global.getCurrentMenuCode();
	initOrganizationTree();
	//jsDebug.debug("staff page load time="+(new Date()-s));
}

function table_staffList_onclick(){
  	staffTableClick();
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
			if( ( item.orgTypeId == "5" || item.orgTypeId == "6") && item.state == "00A" ){
				item.setFontColor("#4422FF"); 
			}
			item.removeChildren();
		}
		clickOrganization();
		staffInfor.setFieldPopupEnable("orgPartyName", false);
		staffInfor.setFieldPopupEnable("officeName", false);
	}
	
	ajaxCall.remoteCall("PartyService","getRootOrganizationListByPrivControl",[menuCode],callBack);	
//	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}

//��ѯStaff���������Ӧ�ı�����
function doQuery_onClick(){
	staffList.clearData();
	staffList.insertRecord(null);
	staffInfor.clearData();
	staffInfor.insertRecord(null);
	
	var staff = new Staff();
	if( orgTreeView.selectedItem == null ){
		return ;
	}
	staff.orgPartyId = orgTreeView.selectedItem.partyId ;
	staff.staffCode = queryInfo.getValue("staffCode" );
	staff.partyName = queryInfo.getValue("partyName");
	staff.scope = queryInfo.getValue("queryScope");
	
	
	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;
	Dataset.reloadData( staffList ) ;
	
	staffTableClick();
}

//���ò�ѯForm
function doReset_onClick(){
	queryInfo.clearData();
	queryInfo.insertRecord( null ) ;
}

var actionType = "" ;
//����Ա��
function addStaff_onClick(){ 
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ���������Ա��!") ;
		return ;
	}
	staffInfor.clearData();
	staffInfor.insertRecord( null ) ;
	staffInfor.setValue("state","00A");
	staffInfor.setValue("expDate","2029-12-31");
	staffInfor.setFieldReadOnly("state",true);//�����ӵ�Ա����������Ч��.
	actionType = "insert" ;
	staffInfor.setReadOnly( false ) ;
	staffInfor.setFieldPopupEnable("orgPartyName",true);
	staffInfor.setFieldPopupEnable("officeName",true);	
	staffInfoPage.setSelectedPageIndex(0);
	staffInfor.setFieldReadOnly("orgPartyName",true);
	staffInfor.setFieldReadOnly("officeName",true);	
	staffInfor.setFieldReadOnly("devOrgName",true);
	selectedOrgCode = "";
}
//ͨ��������ǰԱ������������һ��Ա��
function addCopyStaff_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ����ٸ���Ա��!") ;
		return ;
	}
	if( !staffList.getCurrent()){
		return ;
	}
	if( staffList.getValue("state") != "00A" ){
		alert("��ǰԱ����״̬������Ч״̬,���ܱ�����!");
		return ;
	} 
	if( staffList.getValue("partyRoleId") == "" ){
		return ; 
	} 
	window.showModalDialog("CopyStaff.jsp",[staffList.getValue("partyRoleId"),staffList.getValue("channelSegmentId")],"dialogHeight: 300pt; dialogWidth: 300pt;");	
	getStaffByOrgId();
	return ;
}
//��������Ա��
function addBatchStaff_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ�������������Ա��!") ;
		return ;
	}
	
	if( orgTreeView.selectedItem.orgTypeId != "5" 
		&& orgTreeView.selectedItem.orgTypeId != "6"){
		alert("��ǰ��֯���ǲ��Ż����,ֻ���ڲ��Ż������������Ա��!");
		return ;
	}
	if( orgTreeView.selectedItem.state != "00A" ){
		alert("��ǰ��֯��״̬������Ч״̬,����������������Ա��!");
		return;
	}
		
	var arr = new Array();
	arr[0] = orgTreeView.selectedItem.partyId ;//��Ա������֯��ID
	var returnValue=window.showModalDialog("BatchAddStaff.jsp",arr,"dialogHeight: 300pt; dialogWidth: 300pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue !=null){
		
		var orgPartyId = orgTreeView.selectedItem.partyId ;
		
		var ajaxCall = new NDAjaxCall(true);
		var result ;
		var callBack = function( reply ){
			result = reply.getResult() ; 
			alert(result );
/*			if( result != null || result!="" ){
				alert("���¹����Ѿ���ϵͳ�д���,���������ʱ�����: " + result );
			}else{
				alert("��������Ա���ɹ�!");
			}*/
			clickOrganization();
		}
		var arr = new Array();
		arr[0] = orgPartyId ; //������֯Id
		arr[1] = returnValue["startStaffCode"];//parseInt(returnValue["startStaffCode"]);//��ʼԱ����
		arr[2] = returnValue["endStaffCode"];//parseInt(returnValue["endStaffCode"]);//����Ա����
		arr[3] = returnValue["staffName"];//Ա������
		arr[4] = returnValue["orgPostId"];//��λ
		arr[5] = returnValue["state"];
		arr[6] = returnValue["effDate"];
		arr[7] ="2029-01-01 00:00:00";
		arr[8] = returnValue["channelSegmentId"];
		arr[9] = "123456";
		arr[10] = returnValue["lanId"];
		arr[11] = returnValue["businessId"];
		arr[12] = returnValue["countyType"];
		arr[13] = returnValue["orgManager"];//add �Ͽ��� 2010/4/19
		arr[14] = returnValue["gender"];
		
		ajaxCall.remoteCall("PartyService","insertBatchStaff",arr,callBack); 
	}
}

function getSuperStaffCode(){
	var returnValue = "" ;
	var ajaxCall = new NDAjaxCall( false ) ;
	var callBack = function( reply ) {
		returnValue = reply.getResult();
	}
	ajaxCall.remoteCall("PartyService","getSuperStaffCode",[],callBack);
	return returnValue ;
}

function ifSuperManager(){
	var returnValue = false ;
	var ajaxCall = new NDAjaxCall( false ) ;
	var callBack = function( reply ) {
		returnValue = reply.getResult();
	}
	ajaxCall.remoteCall("PartyService","ifSuperManager",[],callBack);
	return returnValue ;
}
//���޸�Ա����Ϣ��ʱ��,������������״̬���ַ���,����û�û���޸�����,��
//����״̬�����볤�Ȼᳬ��16Ϊ,���Ա��뱣�������,���ύ��ʱ���ж�������
//�Ƿ�;�����һ��,���һ��,�������ж�����ĳ���,����,��Ҫ�ж����볤����6��15֮��
var oldPassword = "" ;
//�༭Ա��
function editStaff_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ����ٱ༭Ա��!") ;
		return ;
	}
	//if( staffInfor.getValue("staffCode") == "999999" ){
	if( staffInfor.getValue("staffCode") == getSuperStaffCode() ){
		if( !ifSuperManager() ){
			alert("����Ȩ�޸ĳ���������Ϣ!");
			return ;
		}
	}
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	actionType = "update";
	staffInfor.setReadOnly( false ) ;
	staffInfor.setFieldPopupEnable("orgPartyName",true);	
	staffInfor.setFieldReadOnly("orgPartyName",true);
	staffInfor.setFieldPopupEnable("officeName",true);	
	staffInfor.setFieldReadOnly("officeName",true);	
	staffInfor.setFieldReadOnly("staffCode",true);
	staffInfor.setFieldReadOnly("devOrgName",true);
	staffInfoPage.setSelectedPageIndex(0);	
	oldPassword = staffInfor.getValue("password") ;
}

//��Ա���ĵ���¼���Ӧ����
function staffTableClick(){
	if( staffInfoPage.getSelectedPageIndex() != 0 ){
		staffInfoPage.setSelectedPageIndex(0);
		return ;
	}
	
	//���"��Ա��Ϣ"���
	staffInfor.disableControls() ;
	staffInfor.clearData();
	staffInfor.insertRecord( null );
	staffInfor.setReadOnly( true );
	staffInfor.setFieldPopupEnable("orgPartyName", false);
	staffInfor.setFieldPopupEnable("officeName", false);
	var number = 0 ;
	for( var i = 0; i < staffList.fields.length; i ++ ){
		var fieldName = staffList.fields[i].name ;
		staffInfor.setValue( i, staffList.getValue(i) ) ;
		number ++ ;
	}
	//staffInfor.setValue("orgPartyName", orgTreeView.selectedItem.orgName);
	staffInfor.enableControls();
	actionType = "" ;
	getWorkingOfficeDesc( staffInfor.getValue("officeId"));
	getDevUserBelongName( staffInfor.getValue("devUserBelong"));
}
function getDevUserBelongName( orgId ) {
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ) {
		vo = reply.getResult();
		staffInfor.setValue( "devUserBelongName", vo.orgName );
	}
	ajaxCall.remoteCall("PartyService","getOrganization",[orgId],callBack);
}
function getWorkingOfficeDesc( officeId ) {
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ) {
		staffInfor.setValue( "officeName", reply.getResult());
	}
	ajaxCall.remoteCall("PartyService","getOfficeDescByOfficeId",[officeId],callBack);
}
function downloadSubItems(){
	var selItem = orgTreeView.selectedItem ;
	if( selItem == null ){
		return ;
	}
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			
			if( selItem.items ){
				for( var i = 0; i < selItem.items.length ; i ++ ){
					var item = selItem.items[i] ;
					if( ( item.orgTypeId == "5" || item.orgTypeId == "6") && item.state == "00A" ){
						item.setFontColor( "#4422FF" ) ;
					}
				}
			}
		}
	}
	
	var regionId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
}
//��֯���ĵ��ʱ��,�����ϻ�ȡorgId,��������֯id���������˲�ѯ��Ӧ����Ա
function clickOrganization(){
	downloadSubItems();
	getStaffByOrgId();
	staffTableClick();
}

function staffInfoPage_beforePageChanged(page,index){
	if( actionType != ""){
		alert("���ȵ��'ȷ��'��ť�������ݺ����л���������ǩҳ!");
		return false ;
	}
	return true ;
}

function staffInfoPage_onPageChanged(page, index){
	if( index == 0 ){
		if( staffList.getValue("partyRoleId" ) == "" ){
			return ;
		}
		//���"��Ա��Ϣ"���
		staffInfor.clearData();
		staffInfor.insertRecord( null );
	
		staffInfor.disableControls() ;
		
		for( var i = 0; i < staffList.fields.length; i ++ ){
			staffInfor.setValue( i, staffList.getValue(i) ) ;
		}
		
		staffInfor.enableControls();
		
	}else if ( index == 1 ){
		//���"��̬��Ϣ"��� 
		queryPartyIdentification();
	}else if( index == 2 ){
		//���"��Ա��ɫ"���
		queryStaffRoles();
	}else if( index == 3 ){
		//���"��ԱȨ��"���
		queryStaffPrivilege();
		clickStaffPrivilege();
	}else if( index == 4 ){
		//���"��Ա��λ"���
		queryStaffPost();	
	}
}

//ע��Ա��
function deleteStaff_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ�����ע��Ա��!") ;
		return ;
	}
	//if( staffInfor.getValue("staffCode") == "999999" ){
	if( staffInfor.getValue("staffCode") == getSuperStaffCode() ){
		if( !ifSuperManager() ){
			alert("����Ȩע������������Ϣ!");
			return ;
		}
	}
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	if( !confirm("ȷ��Ҫע����ǰԱ����?")){
		return ;
	}
	var partyRoleId = staffList.getValue( "partyRoleId" ) ; 
	var ajaxCall = new NDAjaxCall(true);
	var result ;
	var callBack = function( reply ){
		result = reply.getResult() ;
		if( result ){
			alert("ע��Ա���ɹ�!");
			staffInfor.clearData();
			staffInfor.insertRecord( null ) ;
			clickOrganization();
		}
	}
	ajaxCall.remoteCall("PartyService","deleteStaff",[partyRoleId],callBack); 
}

function cancelCommit_onClick(){	 
	actionType = "" ;
	staffInfor.setReadOnly( true ) ;
	staffTableClick();
}

/*
function getProjectCode(){
	var ajaxCall = new NDAjaxCall(false);
	var result ;
	var callBack = function( reply ){
		result = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getProjectCode",[],callBack); 
	return result ;
}*/

//�ύ��ť��Ӧ�¼�
function commitStaff_onClick(){
	var minPasswordLen ;
	var maxPasswordLen ;
	if( actionType == "" ) {
		return ;
	}
	
	if( !$("staffInforForm").validate()){
		return ;
	}
	if( staffInfor.getValue("limitCount") == "0"){
		alert("��½�����������0!");
		return ;
	}
	//add by xiaoyong  ���ơ��������š�����չ�û�����������ѡ�� ���������͡�Ϊ������������Ĳ��źͰ��� ---20090310
	if (!checkOraPartyNameAndDepartUserBelongName()) {
   		 alert("�������Ż�չ�û������Ĳ�������Ϊ���������,����������!");
		return ;
	}
	//add end;---20090310
	var staffCode = staffInfor.getValue("staffCode");
	var channelSegmentId = staffInfor.getValue("channelSegmentId");
	/**var projectCode = getProjectCode() ;
	if( projectCode == "CHONGQING" ){
		//���칤�ŵ�У�����
		if( channelSegmentId == "12" || channelSegmentId == "-1") {
			if( isNaN( staffCode )){
				alert("�������ű������������͵�!");
				return ;
			}
			if( staffCode.length > 9 ){
				alert("�������ŵĳ��Ȳ��ܴ���9!");
				return ;
			}
		}
	}
	*/

		if(!/^[\da-z]+$/i.test(staffCode)) { //���ֻ�����ĸ
			alert("���ź��зǷ��ַ���");
			return ;
		}
		
		if( staffInfor.getValue("password") != "" ){
			if( !checkIsNumAndLetter( staffInfor.getValue("password"))){
				alert("������������ֺ���ĸ���!");
				return ;
			}
			if(!/^[\da-z]+$/i.test(staffInfor.getValue("password"))) { //���ֻ�����ĸ
				alert("������������ֺ���ĸ���!");
				return ;
			}
		}
		

	if( actionType == "insert" ){//����ǲ������,���������Լ����6~15λ֮��
		if( staffInfor.getValue("password") != "" ){
			maxPasswordLen = getDcSystemParam("MAX_PASSWORD_LENGTH");
			maxPasswordLen = parseInt(maxPasswordLen,10);
			minPasswordLen = getDcSystemParam("MIN_PASSWORD_LENGTH");
			minPasswordLen = parseInt(minPasswordLen,10);
			if( staffInfor.getValue("password").length < minPasswordLen || staffInfor.getValue("password").length > maxPasswordLen ){
				alert("���볤�ȱ�����" + minPasswordLen + "��" + maxPasswordLen + "���ַ�֮��!");
				return ;
			}
		}
	}
	
	if( actionType == "update" ){//����Ǳ༭����
		if( staffInfor.getValue("password") != oldPassword ) {//����û��޸Ĺ�����,�����Լ����������6~15λ֮��
			maxPasswordLen = getDcSystemParam("MAX_PASSWORD_LENGTH");
			maxPasswordLen = parseInt(maxPasswordLen,10);
			minPasswordLen = getDcSystemParam("MIN_PASSWORD_LENGTH");
			minPasswordLen = parseInt(minPasswordLen,10);
			if( staffInfor.getValue("password").length < minPasswordLen || staffInfor.getValue("password").length > maxPasswordLen ){
				alert("���볤�ȱ�����" + minPasswordLen + "��" + maxPasswordLen + "���ַ�֮��!");
				return ;
			}
		}
		//����û�û�и��Ĺ�����,��������������Լ���ж�(��ʱ����������,���ȿ϶�����16λ,����У��û������)
	}
	
	var currentDate = getTodayStr();
	if( actionType == "insert" ){
		if( staffInfor.getValue("effDate") != "" && staffInfor.getValue("effDate") > currentDate ){
			alert("��Ч���ڱ����ڽ�����߽���֮ǰ!");
			return ;
		}
		if( staffInfor.getValue("expDate") != "" && staffInfor.getValue("expDate") < currentDate ){
			alert("ʧЧ���ڱ����ڽ�������֮��!");
			return ;
		}
	}
	if( staffInfor.getValue("expDate") != "" && staffInfor.getValue("effDate") != "" ){
		if( staffInfor.getValue("expDate") <= staffInfor.getValue("effDate")){
			alert("ʧЧ���ڱ��������Ч����!");
			return ;
		}
	}
	
	if( staffList.getCurrent() == null ) 
		staffList.insertRecord( null);
	if ( actionType == "" ) return ;

	if( staffInfor.getValue("orgPartyName") == "" || staffInfor.getValue("orgPartyName") == null ){
		if( orgTreeView.selectedItem.orgTypeId != "5" && orgTreeView.selectedItem.orgTypeId != "6"){
			alert("��ǰ��֯���ǲ��Ż����,ֻ���ڲ��Ż��߰�����������Ա��!");
			return ;
		}
		if( orgTreeView.selectedItem.state != "00A" ){
			alert("��ǰ��֯��״̬������Ч״̬,����������������Ա��!");
			return;
		}
	}
	//�½�һ��staff�����޸ĺ��Ա����Ϣ����Ȼ��������ݸ�service
	var staff = new Staff();
	for( var ele in staff ){
		if( staffInfor.getField( ele )){
			//�������jspҳ���ϵ�ֵ����ֵ��staff��ȥ
			staff[ele] = staffInfor.getValue( ele ) ;
		}
	}
	if( staff["expDate"] == "" || staff["expDate"] == null ){
		staff["expDate"] = "2029-01-01 00:00:00" ;
	}else{
		staff["expDate"] = staff["expDate"] + " 23:59:59";
	}
	staff["effDate"] = staff["effDate"] + " 00:00:00";

	if( staff.orgPartyId == "" || staff.orgPartyId == null || staff.orgParty == "undefined" ){
		staff.orgPartyId = orgTreeView.selectedItem.partyId ;
	}
	
	if( staff.limitCount == "" || staff.limitCount == null || staff.limitCount == "undefined" ){
		staff.limitCount = "3" ;
	}

	var ajaxCall = new NDAjaxCall(true);
	var result ;
	var callBack = function( reply ){
		result = reply.getResult() ; 
			if( actionType == "insert" ){
				staffInfor.setValue("partyRoleId",result);
				staffList.setValue("partyRoleId",result);
				alert("����Ա���ɹ�!");
			}else if( actionType == "update" ){
				alert("�޸�Ա����Ϣ�ɹ�!");
			}
			clickOrganization();
		staffInfor.setReadOnly( true ) ;
		actionType = "";
	}
	
	var arr = new Array();
	staff.businessId = staffInfor.getValue("businessId");	//Ӫҵ��
	staff.countyType = staffInfor.getValue("countyType");	//�����
	arr[0] = staff; 

	staff["partyRoleType"] = "90A";//����������Ϊ�����ڲ�Ա��
	if( actionType == "insert" ){ 
		ajaxCall.remoteCall("PartyService","insertStaff",arr,callBack); 
	}else if( actionType == "update" ){
		//����Ա����Ϣ��ʱ�򣬴��ݸ�service�Ĳ�����staff(����������businessId��countyType)
		ajaxCall.remoteCall("PartyService","updateStaff",arr,callBack);	
	}
	
}

function Identification(){
	this.identId="";
	this.partyId="";
	this.partyRoleId = "";
	this.socialIdType="";
	this.socialId="";
	this.createdDate="";
	this.effDate="";
	this.expDate="";
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
	this.devUserBelong = "";
	this.devUserBelongName = "";
	//this.officeName = "";
	this.officeId = "";
	this.lanId = "";
	this.devOrgId = "";
	this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.StaffVO' ;	
}

function getStaffByOrgId(){
	var selItem = orgTreeView.selectedItem ;
	if( !selItem )
		return ;
	var orgId = selItem.partyId ;

	var staff = new Staff() ;
	staff["orgPartyId"] = orgId ;
	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;
	parameterSet.setValue("pageIndex","1");
	Dataset.reloadData( staffList ) ;
	staffPilot.refreshCustomerPilot;
}

//��ȡ�͵�ǰԱ����ص�"��̬��Ϣ"�б�
function queryPartyIdentification(){
	var partyRoleId = staffList.getValue("partyRoleId");
	if( partyRoleId == "" ) {
		partyIdentification.clearData();
		return ;
	}
	var parameterSet = partyIdentification.parameters();
	parameterSet.setDataType("partyRoleId","string");
	parameterSet.setValue("partyRoleId", partyRoleId);
	Dataset.reloadData( partyIdentification );
}

//��ȡ�͵�ǰԱ����ص�"��Ա��ɫ"�б�
function queryStaffRoles(){
	var partyRoleId = staffList.getValue("partyRoleId");//**************************
	if( partyRoleId == "" ) {
		staffRolesList.clearData();
		return ;
	}
	var parameterSet = staffRolesList.parameters();
	parameterSet.setDataType("partyId","string");
	parameterSet.setValue("partyId", partyRoleId);
	Dataset.reloadData( staffRolesList );
}

//��ȡ�ε�ǰԱ����ص�"��ԱȨ��"�б�
function queryStaffPrivilege(){
	var partyRoleId = staffList.getValue("partyRoleId");
	
/*	var parameterSet = staffPrivilegesList.parameters();
	parameterSet.setDataType("partyId","string");
	parameterSet.setValue("partyId",partyRoleId);
	Dataset.reloadData( staffPrivilegesList ) ;*/
	
	
	if( partyRoleId == null || partyRoleId == "" ){
		staffPrivilegeTreeView.loadByXML("<items/>");
		return ;
	}
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		staffPrivilegeTreeView.clear();
		staffPrivilegeTreeView.loadByXML(queryResult);
	} 
	ajaxCall.remoteCall("PartyService","getStaffPrivXMLItemByPartyRoleId",[partyRoleId],callBack);	 
}

//��ȡ�͵�ǰԱ����ص�"��Ա��λ"�б�
function queryStaffPost(){
	var partyRoleId = staffList.getValue( "partyRoleId" ) ;
	if( partyRoleId == "" ) {
		staffPostsList.clearData();
		return ;
	}
	var parameterSet = staffPostsList.parameters();
	parameterSet.setDataType("partyId","string");
	parameterSet.setValue("partyId",partyRoleId);
	Dataset.reloadData( staffPostsList ) ;
}

//���Ӳ����˶�̬��Ϣ(������ʶ����Ϣ)
function addIdentification_onClick(){
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	var arr = new Array();
	arr[0]= "insert" ;
	arr[1] = staffList.getValue("partyRoleId");//��������˽�ɫID
	var returnValue=window.showModalDialog("IdentificationDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 350pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryPartyIdentification();
	}
}
//�޸Ĳ����˶�̬��Ϣ(������ʶ����Ϣ)
function editIdentification_onClick(){
	if( !partyIdentification.getValue("identId") ){
		return;
	}
	var identification = new Identification(); 
	for( var ele in identification ){
		if( partyIdentification.getField( ele )){
			identification[ele] = partyIdentification.getValue( ele ) ;
		}
	}
	
	var arr = new Array();
	arr[0] = "update" ;
	arr[1] = identification ;
	var returnValue=window.showModalDialog("IdentificationDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 350pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryPartyIdentification();
	}
}
//ɾ�������˶�̬��Ϣ(������ʶ����Ϣ)
function deleteIdentification_onClick(){
	if( !partyIdentification.getValue("identId") ){
		return;
	}
	if( !confirm( "ȷ��Ҫɾ����ǰԱ����̬��Ϣ��?" ) ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall(true);
	var result ;
	var callBack = function( reply ){
		result = reply.getResult() ;
	
		if( result ){
			alert("ɾ����̬��Ϣ�ɹ�!");
			queryPartyIdentification();
		}else{
			alert("ɾ����̬��Ϣʧ��!");
		}		
	}
	var identificationId = partyIdentification.getValue("identId");
	ajaxCall.remoteCall("PartyService","deletePartyIdentification",[identificationId],callBack);
}

//����Ա����ɫ
function addRole_onClick(){
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	var arr = new Array();
	arr[0] = staffList.getValue( "partyRoleId" );
	arr[1] = staffRolesList ;
	arr[2] = menuCode ;
	var returnValue=window.showModalDialog("StaffRoleDialog.jsp",arr,"dialogHeight: 520pt; dialogWidth: 500pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffRoles();
	}
}

//�༭Ա��Ȩ��
/*
function editRole_onClick(){
	if( !staffRolesList.getValue("staffRoleId")){
		return ;
	}
	var arr = new Array();
	arr[0] = "update";
	arr[1] = staffRolesList.getValue("staffRoleId") ;
	var returnValue=window.showModalDialog("StaffRoleDialog.jsp",arr,"dialogHeight: 500pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffRoles();
	}	
}*/
 
//ɾ��Ա����ɫ
function deleteRole_onClick(){
	if( !staffRolesList.getValue("partyRoleId")){
		return ;
	}
	if( !confirm( "ȷ��Ҫɾ����ǰԱ����ɫ��?" ) ){
		return ;
	}
	var staffRoleId = staffRolesList.getValue( "partyRoleId" ) ;
	var roleId = staffRolesList.getValue("roleId");
	var regionId = staffRolesList.getValue("regionId");
	var regionType = staffRolesList.getValue("regionType");
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var result = null ;
	var callBack = function( reply ){
		result = reply.getResult();
		if( result ){
			alert("ɾ��Ա����ɫ�ɹ�!");
			queryStaffRoles();
		}
	}
	ajaxCall.remoteCall( "PartyService","deleteStaffRole",[staffRoleId,roleId,regionId,regionType],callBack);
}

function checkStaffRoles(){
	var partyRoleId = staffList.getValue("partyRoleId") ;
	
	var returnValue = true ;
	
	var callBack = function( reply ) {
		returnValue = reply.getResult() ;
	}
	
	var ajaxCall = new NDAjaxCall( false ) ;
	ajaxCall.remoteCall("PartyService","checkStaffRoles", [partyRoleId] , callBack ) ;
	
	return returnValue ;
}

function addPrivilege_onClick(){
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	if( !checkStaffRoles() ){//У��Ա���Ƿ��Ѿ������˽�ɫ,ϵͳ�������û���κν�ɫ�Ĺ��ŵ�������Ȩ��
		alert("��ǰԱ��û�з����κν�ɫ,���ܸ�û�н�ɫ��Ա����������Ȩ��!");
		return ;
	}
	var selectedPrivilegeVOs = getPrivilegeVosByPartyRoleId() ;//getPrivilegeIdsByPartyRoleId();//��ȡԱ�����е�Ȩ��ID
	var arr = new Array();
	arr[0] = menuCode;
	arr[1] = staffList.getValue( "partyRoleId" );
	arr[2] = selectedPrivilegeVOs ;
	var returnValue=window.showModalDialog("StaffPrivilegeDialog.jsp",arr,"dialogHeight: 500pt; dialogWidth: 550pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffPrivilege();
	}
}

//��ѯ��ǰ������ӵ�е�����Ȩ�޶���(StaffPrivVO)
function getPrivilegeVosByPartyRoleId(){
	var ajaxCall = new NDAjaxCall( false ) ;//ͬ����ʽ�������ݿ�
	var privilegeIds = new Array() ;
	var callBack = function( reply ) {
		privilegeIds = reply.getResult() ;
	}
	var partyRoleId = staffList.getValue("partyRoleId");
	ajaxCall.remoteCall( "PrivilegeService","getPrivilegeVosByPartyRoleId", [partyRoleId] , callBack ) ;
	return privilegeIds ;
}

//��ѯ��ǰ������ӵ�е�����Ȩ��ID
function getPrivilegeIdsByPartyRoleId(){
	var ajaxCall = new NDAjaxCall( false ) ;//ͬ����ʽ�������ݿ�
	var privilegeIds = new Array() ;
	var callBack = function( reply ) {
		privilegeIds = reply.getResult() ;
	}
	var partyRoleId = staffList.getValue("partyRoleId");
	ajaxCall.remoteCall( "PrivilegeService","getPrivilegeIdsByPartyRoleId", [partyRoleId] , callBack ) ;
	return privilegeIds ;
}

/*
function editPrivilege_onClick(){
	if( !staffPrivilegesList.getValue("staffPrivId")){
		return ;
	}
	var arr = new Array();
	arr[0] = "update";
	arr[1] = staffPrivilegesList.getValue("staffPrivId") ;
	var returnValue=window.showModalDialog("StaffPrivilegeDialog.jsp",arr,"dialogHeight: 500pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffPrivilege();
	}	
}*/

function deletePrivilege_onClick(){
	if( !staffPrivilegeTreeView.items){
		return ;
	}
	if( !staffPrivilegeTreeView.selectedItem){
		return ;
	}
	if( staffPrivilegeTreeView.selectedItem.items ){
		alert("��Ҫɾ����Ȩ�޻����¼�Ȩ��,����ɾ���¼�Ȩ��!");
		return ;
	}
	if( !confirm( "ȷ��Ҫɾ����ǰԱ��Ȩ����?" ) ){
		return ;
	}
	var partyRoleId = staffList.getValue( "partyRoleId" ) ;
	var staffRoleId = staffPrivilegesList.getValue( "staffPrivId" ) ;
	var privId = staffPrivilegeTreeView.selectedItem.privId ;
	var regionId = staffPrivilegeRegionList.getValue("regionId");
	var regionType = staffPrivilegeRegionList.getValue("regionType") ;
	if( regionId == "" ){
		regionId = "-1";
	}
	if( regionType == "" ){
		regionType = "-1";
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var result = null ;
	var callBack = function( reply ){
		result = reply.getResult();
		if( result ){
			queryStaffPrivilege();
			clickStaffPrivilege();
			alert("ɾ��ԱȨ�޳ɹ�!");
		}
	}
	ajaxCall.remoteCall( "PartyService","deleteStaffPriv",[partyRoleId,privId,regionId,regionType],callBack);
}

function addPost_onClick(){
	if( staffList.getValue("partyRoleId") == ""){
		return ;
	}
	var arr = new Array();
	arr[0] = "insert";
	arr[1] = staffInfor.getValue("orgPartyId");//orgTreeView.selectedItem.partyId ;//��Ա������֯��ID
	arr[2] = staffList.getValue( "partyRoleId" );//��Ա��PartyRoleId
	
	var postArray = new Array();
	var curRecord = staffPostsList.getCurrent();
	var i = 0 ;
	while( curRecord ){
		postArray[i] = curRecord.getValue("orgPostId");
		i ++ ;
		curRecord = curRecord.getNextRecord();
	}
	arr[3] = postArray;
	var returnValue=window.showModalDialog("StaffPositionDialog.jsp",arr,"dialogHeight: 300pt; dialogWidth: 350pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffPost();
	}
}

function editPost_onClick(){
	if( !staffPostsList.getValue("partyRoleId")){
		return ;
	}
	var arr = new Array();
	arr[0] = "update" ;
	arr[1] = orgTreeView.selectedItem.partyId ;//��Ա������֯��ID
	arr[2] = staffPostsList.getValue("partyRoleId");
	arr[3] = staffPostsList.getValue("orgPostId");
	arr[7] = staffPostsList.getValue("orgPostName");
	arr[4] = staffPostsList.getValue("state");
	arr[5] = staffPostsList.getValue("effDate");
	arr[6] = staffPostsList.getValue("expDate");
	var returnValue=window.showModalDialog("StaffPositionDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 300pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffPost();
	}	
}

function deletePost_onClick(){
	if( !staffPostsList.getValue("partyRoleId")){
		return ;
	}
	if( !confirm("��ȷ��Ҫɾ��Ա����λ��?")){
		return ;
	}
	var partyRoleId = staffPostsList.getValue("partyRoleId");
	var orgPostId = staffPostsList.getValue("orgPostId");
	var result = null ;
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ){
		result = reply.getResult();
		if( result ){
			alert("ɾ��Ա����λ�ɹ�!");
			queryStaffPost();
		}
	}
	var arr = new Array();
	arr[0] = orgPostId;
	arr[1] = partyRoleId ;
	ajaxCall.remoteCall( "PartyService","deleteStaffPost",arr,callBack);
}

function button_staffInfor_staffCode_onClick(){
	var staffCode = "";
	if( actionType == "insert" ){
		if( staffInfor.getValue("staffCode") == "" ){
			alert("�������빤��!");
			return ;
		}	
		
		var projectCode = getProjectCode() ;
		if( projectCode == "CHONGQING" ){
			var channelSegmentId = staffInfor.getValue("channelSegmentId") ;
			if( channelSegmentId == "" ){
				alert("����ѡ��������");
				return ;
			}
			if( channelSegmentId != "12" && channelSegmentId != "-1" ){
				if( selectedOrgCode != ""){
					staffCode = selectedOrgCode + staffInfor.getValue("staffCode") ;
				}else{
					staffCode= orgTreeView.selectedItem.orgCode + staffInfor.getValue("staffCode");
				}
			}else{
				staffCode = staffInfor.getValue("staffCode");
			}
		}else{
			staffCode = staffInfor.getValue("staffCode");
		}
		window.showModalDialog("ValidStaffCode.jsp",staffCode,"dialogHeight: 120pt; dialogWidth: 250pt;");	
	}
}
function button_staffInfor_officeName_onClick(){
	if( actionType == "" ){
		return ;
	}
	var returnValue=window.showModalDialog("../common/OfficeSelectDialog.jsp",null,"dialogHeight: 350pt; dialogWidth: 400pt;");	
	if( returnValue ){
		staffInfor.setValue("officeId", returnValue[0]);
		staffInfor.setValue("officeName", returnValue[1] );
	}
}

function button_staffInfor_devOrgName_onClick(){
	if( actionType != "insert" ){
		if( actionType == "update"){
			alert("���������̰��鲻�ܱ༭!");
		}
		return ;
	}
	var vo = selectOrganization() ;
	if( vo == null ){
		return ;
	}
	staffInfor.setValue("devOrgId", vo["orgId"]);
	staffInfor.setValue("devOrgName", vo["orgName"]);		
}

function button_staffInfor_orgPartyName_onClick(){
	if( actionType == "" ){
		return ;
	}
	var vo = selectOrganization() ;
	if( vo == null ){
		return ;
	}
	staffInfor.setValue("orgPartyId", vo["orgId"]);
	staffInfor.setValue("orgPartyName", vo["orgName"]);		
	selectedOrgCode = vo["orgCode"];
}
function button_staffInfor_devUserBelongName_onClick(){
	if( actionType == "" ){
		return ;
	}
	var vo = selectOrganization() ;
	if( vo == null ){
		return ;
	}
	staffInfor.setValue("devUserBelong",vo["orgId"]);
	staffInfor.setValue("devUserBelongName",vo["orgName"]);
}

function selectOrganization(){
	var para = new Object();
	/*
	 *privilegeType -- Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵�����
	 */
	para["privilegeType"] = "3" ;
	/*
	 *privilegeCode -- 
	 *Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
	 */
	para["privilegeCode"] = menuCode;
	/*
	 *orgType -- 
	 *��֯����ID,5��ʾ����,0��ʾ���Ź�˾,1��ʾʡ��˾,2��ʾ�й�˾,3��ʾ�ֹ�˾,6��ʾ����,
	 *9��ʾ������֯,99��ʾ������������,����ѡ���κ�һ����֯����
	 */
//	para["orgType"] = "5" ;//ֻ��ѡ����
	para["orgType"] = "7" ;//����ѡ���Ż��߰���
	para["selectType"] = "1" ;//1��ʾ��ѡ,2��ʾ��ѡ
	para["oldIds"] = getStaffOrganizationId();
	
	var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0 ) 
		return null ;
	return returnValue[0] ;
}
var selectedOrgCode = "";

function clickStaffPrivilege(){
	var selItem = staffPrivilegeTreeView.selectedItem;
	if( selItem == null ){
		staffPrivilegeRegionList.clearData() ;
		return ;
	}
	var privilegeId = staffPrivilegeTreeView.selectedItem.privId ;
	var partyRoleId = staffList.getValue("partyRoleId");
	staffPrivilegeRegionList.parameters().setDataType("privId","string");
	staffPrivilegeRegionList.parameters().setValue("privId",privilegeId);
	
	staffPrivilegeRegionList.parameters().setDataType("partyRoleId","string");
	staffPrivilegeRegionList.parameters().setValue("partyRoleId",partyRoleId);
	Dataset.reloadData( staffPrivilegeRegionList ) 
}
//add by xiaoyong ---20090310
//��Ա������ҳ�棬���ӹ���ʱ�����ơ��������š�����չ�û�����������ѡ�� ���������͡�Ϊ������������Ĳ��źͰ���
function checkOraPartyNameAndDepartUserBelongName(){
    
    var isOk = true;
   	var orgPartyId = staffInfor.getValue("orgPartyId");   	 
	var devUserBelong = staffInfor.getValue("devUserBelong");	 
    var callBack = function(reply) 
            {
              var returnValues=new Object();
              returnValues=reply.getResult();
              if (returnValues!="")
              {
                  isOk = false;
               }
            } 
    var ajaxCall = new NDAjaxCall( false ) ;
	ajaxCall.remoteCall("PartyService", "judgePartyIsBelongSecietyChannels", [orgPartyId,devUserBelong], callBack);
    return isOk;	 
}
// add end;---20090310