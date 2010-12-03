function page_onLoad(){
	initPartnerTree() ;
}

function initPartnerTree(){
	var callBack = function( reply ){
		var returnXML = reply.getResult() ;
		partnerTree.loadByXML( returnXML ) ;
		clickPartner();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var queryVo = new PartnerQueryVO() ;
	queryVo.superPartnerId = "-1" ;
	ajaxCall.remoteCall( "PartnerService", "queryPartner", [queryVo], callBack ) ;
}

var actionType = "";
var nodeType = "";
function editPartner_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�����ٱ༭�������!");
		return ;
	}
	var selItem = partnerTree.selectedItem;
	if ( selItem == null ){
		return ;
	}
	if( selItem.partnerId == "" || selItem.partnerId == null ){
		return ;
	}
	actionType = "update" ;
	partnerInfo.setReadOnly( false ) ;

	partnerInfo.setFieldPopupEnable("orgName",true);
	partnerInfo.setFieldPopupEnable("staffName",true);	
	
	partnerInfo.setFieldReadOnly("orgName",true);
	partnerInfo.setFieldReadOnly("staffName",true);	
		
	mainPage.setSelectedPageIndex(0);
}
//����һ���������
function addRootPartner_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ���������Ӻ������!");
		return ;
	}
	nodeType = "root" ;	
	addPartner() ;
}
//�����¼��������
function addChildPartner_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ���������Ӻ������!");
		return ;
	}
	nodeType = "child" ;
	addPartner() ;	
}
//���Ӻ������
function addPartner(){
	actionType = "insert" ;
	partnerInfo.setReadOnly( false ) ;
	partnerInfo.clearData();
	partnerInfo.insertRecord( null ) ;

	partnerInfo.setFieldPopupEnable("orgName",true);
	partnerInfo.setFieldPopupEnable("staffName",true);
	
	partnerInfo.setFieldReadOnly("orgName",true);
	partnerInfo.setFieldReadOnly("staffName",true);
	
	mainPage.setSelectedPageIndex(0);
}
function savePartner_onClick(){
	if( !$("partnerInfoForm").validate()){
		return ;
	}	
	var saveVO =  new PartnerInfoVO() ;
	for( var ele in saveVO ){
		if( partnerInfo.getField( ele ) ){
			saveVO[ele] = partnerInfo.getValue( ele ) ;
		}
	}
	if( nodeType == "root" ){
		saveVO["superPartnerId"] = "-1" ;
	}else if( nodeType == "child"){
		var selItem = partnerTree.selectedItem ;
		saveVO["superPartnerId"] = selItem.partnerId ;
	}
	
	var callBack = function( reply ) {
		if( actionType == "insert" ){
			alert("���Ӻ������ɹ�!");
			partnerInfo.setValue("partnerId",reply.getResult());
			addPartnerToTreeList() ;
		}else if( actionType == "update" ){
			alert("�޸ĺ������ɹ�!");
			refreshTreeList();
		}
		//initPartnerTree();
		actionType = "" ;
		nodeType = "" ;
		partnerInfo.setReadOnly( true ) ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	if( actionType == "insert" ) {
		ajaxCall.remoteCall( "PartnerService", "insertPartner",[saveVO],callBack);
	}else if( actionType == "update") {
		ajaxCall.remoteCall( "PartnerService", "updatePartner",[saveVO],callBack);
	}
}
function refreshTreeList(){
	var partner = partnerTree.selectedItem;

	for(var ele in partner){
		if(partnerInfo.getField(ele)){
			partner[ele] = partnerInfo.getValue( ele );
		} 
	}
		
	partner.refresh();
}
function addPartnerToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}
function addRootToTreeList(){
	var partner = partnerTree.createTreeNode();
	 
	 var partner1 = new PartnerInfoVO();
	for(var ele in partner1){
		if(partnerInfo.getField(ele)){
			partner[ele] = partnerInfo.getValue( ele );
		} 
	}
	partnerTree.add(partner);
	partner.setSelected();
}
function addChildToTreeList(){
	var partner = partnerTree.createTreeNode();
	var selItem = partnerTree.selectedItem;
	
	for(var ele in selItem){
		if(partnerInfo.getField(ele)){
			partner[ele] = partnerInfo.getValue( ele );
		} 
	}
	selItem.add(partner);
	partner.setSelected();
}
function cancelPartner_onClick(){
	actionType = "" ;
	nodeType = "" ;
	partnerInfo.setReadOnly(true) ;
	clickPartner();
}

//��ǩҳ�л�ǰ���¼�,�жϵ�ǰ����Ƿ������û��л���ǩҳ
function mainPage_beforePageChanged(page,index){
	if( actionType != ""){
		alert("���ȱ����ȡ�������л���������ǩҳ!");
		return false ;
	}
	return true ;
}

//�л���ǩҳ�¼�,�����������
function mainPage_onPageChanged(page, index){
	if( actionType == ""){
		if( index == 0 ){
			cancelPartner_onClick();
		}
		if( index == 1 ){
			loadConferList();
		}
		if( index == 2 ) {
			loadDepartRelatList() ;
		}
	}
}

//���غ��������صĺ�ͬ�б�
function loadConferList(){
	var selItem = partnerTree.selectedItem ;
	if( selItem == null ){
		return ;
	}
	conferList.parameters().setDataType( "partnerId","string");
	conferList.parameters().setValue("partnerId",partnerTree.selectedItem.partnerId);
	Dataset.reloadData( conferList ) ;
	table_conferList_onclick();
}

//���غ��������صĹ��������б�
function loadDepartRelatList(){
	var selItem = partnerTree.selectedItem ;
	if( selItem == null ){
		return ;
	}
	departRelatList.parameters().setDataType("partnerId", "string") ;
	departRelatList.parameters().setValue("partnerId", partnerTree.selectedItem.partnerId);
	Dataset.reloadData( departRelatList ) ;
	//table_departRelatList_onclick();
}

//���Ӻ�ͬ��ť��Ӧ�¼�
function insertConferInfo_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ��������Ӻ�ͬ!");
		return ;
	}
	actionType = "insert" ;
	conferInfo.setReadOnly( false ) ;
	conferInfo.clearData() ;
	conferInfo.insertRecord( null ) ;
}

//�༭��ͬ��ť��Ӧ�¼�
function editConferInfo_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ����ٱ༭��ͬ!");
		return ;
	}
	if( conferList.getValue("partnerConfId") == "" || conferList.getValue("partnerConfId") == null ){
		return ;
	}
	actionType = "update" ;
	conferInfo.setReadOnly( false ) ;
}

//�����ͬ��ť��Ӧ�¼�
function saveConfer_onClick(){
	if( !$("conferInfoForm").validate()){
		return ;
	}
	
	var conferVo = new PartnerConferInfoVO();
	for( var ele in conferVo ){
		if( conferInfo.getField( ele )){
			conferVo[ele] = conferInfo.getValue( ele ) ;
		}
	}
	
	conferVo["partnerId"] = partnerTree.selectedItem.partnerId ;
	
	var callBack = function( reply ){
		if( actionType == "insert" ){
			alert("�����ͬ�ɹ�!") ;
		}else if( actionType =="update") {
			alert("�޸ĺ�ͬ�ɹ�!") ;
		}
		actionType = "" ;
		conferInfo.setReadOnly(true);
		loadConferList();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	if( actionType == "insert" ){
		ajaxCall.remoteCall( "PartnerService","insertPartnerConferInfo",[conferVo], callBack ) ;
	}else if( actionType == "update"){
		ajaxCall.remoteCall( "PartnerService","updatePartnerConferInfo",[conferVo], callBack ) ;
	}
}

//ȡ����ͬ��ť��Ӧ�¼�
function cancelConfer_onClick(){
	actionType = "" ;
	conferInfo.setReadOnly( true ) ;
	table_conferList_onclick() ;
}

//��ͬ�б�ĵ���¼�
function table_conferList_onclick(){
	for( var i = 0 ; i < conferList.fields.length ; i ++ ){
		conferInfo.setValue( i , conferList.getValue( i ) ) ;
	}
}

/*
//���������б�ĵ���¼�
function table_departRelatList_onclick(){
	for( var i = 0 ; i < departRelatInfo.fields.length ; i ++ ){
		departRelatInfo.setValue( i, departRelatList.getValue( i ) ) ;
	}
}
*/

//���ӹ������Ű�ť����Ӧ�¼�
function insertDepartRelat_onClick(){
	var arr = new Array() ;
	arr[0] = "05";
	arr[1] = "1" ;//��ѡ
	var returnValue=window.showModalDialog("../common/DepartmentSelect.jsp",arr,"dialogHeight: 400pt; dialogWidth: 400pt");
	if( returnValue == null || returnValue.length == 0){
		return ;
	}
	var departVo = new PartnerDeptRelatVO();
	departVo["partnerId"] = partnerTree.selectedItem.partnerId ;
	departVo["partyId"] = returnValue[0]["partyId"] ;
	
	//�ж��û�ѡ�еĲ����Ƿ��Ѿ���departRelatList���ݼ���
	var record = departRelatList.getFirstRecord();
	while ( record ){
		if( record.getValue("partyId") == departVo["partyId"] ){
			alert("��ѡ��Ĳ����Ѿ���������!");
			return ;
		}
		record = record.getNextRecord();
	}
	
	var callBack = function( reply ){
		alert("����������ųɹ�!");
		departRelatInfo.setReadOnly( true ) ;
		loadDepartRelatList();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	ajaxCall.remoteCall( "PartnerService","insertPartnerDepartRelat",[departVo], callBack ) ;
}

//ɾ���������Ű�ť����Ӧ�¼�
function deleteDepartRelat_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ������ɾ���������!");
		return ;
	}
	if( departRelatList.getValue("partyId") == "" || departRelatList.getValue("partyId") == null ){
		return ;
	}
	if( !confirm( "��ȷ��Ҫɾ����ǰ��¼��?")){
		return ;
	} 
	
	var callBack = function( reply ) {
		alert("ɾ���������ųɹ�!");
		loadDepartRelatList();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var arr = new Array() ;
	arr[0] = departRelatList.getValue("partnerId");
	arr[1] = departRelatList.getValue("partyId");
	ajaxCall.remoteCall("PartnerService","deletePartnerDepartRelat", arr,callBack);
}
/*
//����������ŵİ�ť��Ӧ�¼�
function saveDepartRelat_onClick(){
	var departVo = new PartnerDeptRelatVO();
	for( var ele in departVo ){
		departVo[ele] = departRelatInfo.getValue( ele ) ;
	}
	
	departVo["partnerId"] = partnerTree.selectedItem.partnerId ;
	
	var callBack = function( reply ){
		if( actionType == "insert" ){
			alert("����������ųɹ�!");
		}else if( actionType == "update" ){
			alert("�޸Ĺ������ųɹ�!");
		}
		actionType = "" ;
		departRelatInfo.setReadOnly( true ) ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	if( actionType == "insert" ) {
		ajaxCall.remoteCall( "PartnerService","insertPartnerDepartRelat",[departVo], callBack ) ;
	}else if ( actionType == "update" ) {
		ajaxCall.remoteCall( "PartnerService","updatePartnerDepartRelat",[departVo], callBack ) ;	
	}
}

//ȡ���������Ű�ť����Ӧ�¼�
function cancelDepartRelat_onClick(){
	actionType = "" ;
	departRelatInfo.setReadOnly( true ) ;
}
*/

//��ѯ��ť��Ӧ�¼�
function queryPartner_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�����ٲ�ѯ�������!");
		return ;
	}
	var queryVo = new PartnerQueryVO() ;
	for( var ele in queryVo ){
		if( queryInfo.getField( ele )){
			queryVo[ele] = queryInfo.getValue( ele ) ;
		}
	}
	
	var callBack = function( reply ){
		var returnXML = reply.getResult() ;
		partnerTree.loadByXML( returnXML ) ;
		clickPartner();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	ajaxCall.remoteCall( "PartnerService", "queryPartner", [queryVo], callBack ) ;
}

//��ѯ���ð�ť��Ӧ�¼�
function queryReset_onClick(){
	queryInfo.clearData() ;
	queryInfo.insertRecord( null ) ;
}

//��������������¼�
function clickPartner(){
	//��ȡ��ǰ�ڵ��µ������¼��ڵ�.
	downloadSubItems() ;
	actionType = "" ;
	nodeType = "" ;
	partnerInfo.setReadOnly(true) ;
	var selItem = partnerTree.selectedItem ;
	
	partnerInfo.disableControls();
	for( var ele in selItem ){
		if( partnerInfo.getField( ele )){
			partnerInfo.setValue( ele, selItem[ele] ) ;
		}	
	}
	partnerInfo.setFieldPopupEnable("orgName",false);
	partnerInfo.setFieldPopupEnable("staffName",false);
	partnerInfo.enableControls();
	
	mainPage.setSelectedPageIndex(0);
}

//���û�������������ʱ��,�жϵ�ǰ�ڵ��Ƿ����¼��ڵ�,���û��,
//���������˲鿴�Ƿ����¼��ڵ�,����������˷����˸ýڵ���¼�
//�ڵ�����,����Щ���ݽ�����Ϊ������¼��ڵ�,��ӵ�������.
function downloadSubItems(){
	var selItem = partnerTree.selectedItem ;
	if( selItem == null ) 
		return ;
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
	
	var parentId = selItem.partnerId ;
	var queryVO = new PartnerQueryVO() ;
	queryVO.superPartnerId = parentId ;
	ajaxCall.remoteCall("PartnerService","queryPartner",[queryVO], callBack);
}

//����������ݼ� �����Ű�ť ��ť����¼�
function button_partnerInfo_orgName_onClick(){
	var orgInfo = getOrganization();
	if( orgInfo == null ){
		return ;
	}
	partnerInfo.setValue( "orgId", orgInfo["orgId"] ) ;
	partnerInfo.setValue( "orgName", orgInfo["orgName"] ) ;
	partnerInfo.setValue("staffId","");
	partnerInfo.setValue("staffName","");
}

//��ѯ��Ϣ���ݼ� �����Ű�ť ����¼�
function button_queryInfo_orgName_onClick(){
	var orgInfo = getOrganization();
	if( orgInfo == null ){
		return ;
	}
	queryInfo.setValue( "orgId", orgInfo["orgId"] ) ;
	queryInfo.setValue( "orgName", orgInfo["orgName"] ) ;
}

function button_queryInfo_staffName_onClick(){
	var orgId = queryInfo.getValue("orgId") ;
	if( orgId == null ||orgId == ""){
		alert("����ѡ�й�����!");
		return;
	}
	var staffInfo = getStaff( orgId ) ;
	if( staffInfo == null ){
		return ;
	}
	if( staffInfo[0] == null ){
		return ;
	}
	queryInfo.setValue("staffId", staffInfo[0]["id"] ) ;
	queryInfo.setValue("staffName",staffInfo[0]["name"]);
}

//������� ���ݼ� �����˰�ť����¼�
function button_partnerInfo_staffName_onClick(){
	var orgId = partnerInfo.getValue("orgId") ;
	if( orgId == null ||orgId == ""){
		alert("����ѡ�й�����!");
		return;
	}
	var staffInfo = getStaff( orgId ) ;
	
	if( staffInfo == null || staffInfo[0] == null ){
		partnerInfo.setValue("staffId", "" ) ;
		partnerInfo.setValue("staffName","");		
		return ;
	}
	partnerInfo.setValue("staffId", staffInfo[0]["id"] ) ;
	partnerInfo.setValue("staffName",staffInfo[0]["name"]);
}

function getStaff(orgId){
	return returnValue=window.showModalDialog("../common/StaffSelect.jsp",[orgId,"0","1"],"dialogHeight: 458pt; dialogWidth: 544pt");
}

//������֯ѡ�����,��ȡ�û�ѡ�е���֯.
function getOrganization(){
	var para = new Object() ;
	var menuCode = Global.getCurrentMenuCode();
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
	para["orgType"] = "5" ;
	para["selectType"] = "1" ;//1��ʾ��ѡ,2��ʾ��ѡ
	para["oldIds"] = getStaffOrganizationId();
	
	var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0) return null ;
	return returnValue[0];
}

function PartnerQueryVO(){
	this.partnerCode = "" ;
	this.partnerType  = "" ;
	this.cooperateType = "" ;
	this.partnerGrade = "" ;
	this.orgId = "" ;
	this.staffId = "" ;
	this.superPartnerId = "";
}

function PartnerInfoVO(){
	this.partnerId = "";
	this.partyRoleId = "";
	this.partnerCode = "";
	this.partnerType = "";
	this.partnerDesc = "";
	this.corpAgent = "";
	this.addr = "";
	this.applyDate = "";
	this.linkman = "";
	this.corporateLicenceNo = "";
	this.spcpLicenceNo = "";
	this.bankPermit = "";
	this.companySize = "";
	this.companyBalns = "";
	this.cooperateType = "";
	this.devPlan = "";
	this.partnerGrade = "";
	this.state = "";
	this.orgId = "";
	this.orgName = "";
	this.staffName = "";
	this.staffId = "";
	this.superPartnerId = "";
	this.pathCode = "";
}

function PartnerConferInfoVO(){
	this.partnerConfId = "";
	this.partnerId = "";
	this.conferCode = "";
	this.partnerConferType = "";
	this.signDate = "";
	this.expDate = "";
	this.cooperateType = "";
	this.conferContence = "";
	this.balnsRule = "";
	this.partnerDroitDuty = "";
	this.droitDuty = "";
	this.abortCondiction = "";
	this.breakDuty = "";
	this.state = "";
}

function PartnerDeptRelatVO(){
	this.partyId = "";
	this.partnerId = "";
}