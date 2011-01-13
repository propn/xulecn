var menuCode = "";
function page_onLoad(){
	menuCode = Global.getCurrentMenuCode();
	initOrganization();
}

function initOrganization(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.organizationTreeView.loadByXML(queryResult );
		if( document.all.organizationTreeView.items == null || document.all.organizationTreeView.items.length == 0 ){
			alert("��û���κ�����Ȩ��!");
			return ;
		}
		clickOrganization();
	}
	ajaxCall.remoteCall("PartyService","getRootOrganizationListByPrivControl",[menuCode],callBack);	
//	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}

function clearValues(){
	commonInfor.clearData() ;
	contactInfor.clearData() ;
	commonInfor.insertRecord( null ) ;
	contactInfor.insertRecord( null ) ;
}

//�༭��֯
function editOrganization_onClick(){
	if( actionType != "" ){
		alert( "���ȱ������ȡ����ǰ�����ٱ༭��֯!" ) ;
		return ;
	}
	actionType = "update" ;
	commonInfor.setReadOnly( false ) ;
	commonInfor.setFieldReadOnly("orgTypeId",true);
	commonInfor.setFieldReadOnly("parentPartyName",true);
	commonInfor.setFieldReadOnly("orgLevel", true);
	commonInfor.setFieldReadOnly("orgType", true);
	contactInfor.setReadOnly( false ) ;
	
	if( organizationTreeView.selectedItem.orgTypeId == "6" ){//����ǰ���
		commonInfor.setFieldReadOnly("orgType", false);	
		
		//added 2008-11-06
		departmentInfo.setReadOnly( false ) ;
		departmentInfo.setFieldPopupEnable("superPartyName",true);
		departmentInfo.setFieldPopupEnable("regionName",true);	//added end here
	}
	if( organizationTreeView.selectedItem.orgTypeId == "5" ){//����ǲ���
		commonInfor.setFieldReadOnly("orgType", false);
		departmentInfo.setReadOnly( false ) ;
		departmentInfo.setFieldPopupEnable("superPartyName",true);
		departmentInfo.setFieldPopupEnable("regionName",true);		
	}
	mainPage.setSelectedPageIndex(0);
}

//��TreeList��ɾ��һ����¼
function deleteOrganizationFromTreeList(){
	var organizationTree = document.all.organizationTreeView;
	var selItem = organizationTree.selectedItem;
	selItem.remove();
}

//ɾ����֯
function deleteOrganization_onClick(){
	if( actionType != "" ){
		alert( "���ȱ������ȡ����ǰ������ɾ����֯!" ) ;
		return ;
	}
	if( confirm( "��ȷ��Ҫɾ����ǰ��֯��?" )){
		var currentPrivilegeId = getCurrentOrganizationId();
		
		var ajaxCall = new NDAjaxCall(true);
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;
			if( deleteResult > 0){
				deleteOrganizationFromTreeList();
				alert( "ɾ����֯�ɹ���" );
			}
			else if( deleteResult ==0){
				alert( "ɾ����֯ʧ��!" );
			}
			else if(deleteResult ==-3){
				alert("��֯�ڵ����滹���¼��ڵ�,����ɾ��!");
			}
			else if(deleteResult ==-4){
				alert("��֯���滹��Ա��,����֯����ɾ��!");
			}
		}
		ajaxCall.remoteCall("PartyService","deleteOrganization",[currentPrivilegeId],callBack);
	}
}

//����һ����֯
function addOrganization(parentOrgId){
	mainPage.setSelectedPageIndex(0);
	clearValues();
	commonInfor.setReadOnly(false);
	commonInfor.setFieldReadOnly( "parentPartyName", true );
	commonInfor.setFieldReadOnly("orgLevel", true);
	commonInfor.setFieldReadOnly("orgType",true);
	divDepartmentInfo.style.display = "none" ;
	contactInfor.setReadOnly( false ) ;
	
	if( parentOrgId != null ){//�������һ���ϼ���֯���������¼���֯��
		var item = getPartyById(parentOrgId) ;
		commonInfor.setValue("parentPartyId", item.partyId ) ;
		commonInfor.setValue("parentPartyName",item.orgName);
		
		var orgTypeId = item.orgTypeId ;
		if( "0" == orgTypeId ){//�ϼ���֯�Ǽ��Ź�˾
			commonInfor.setValue("orgTypeId","1");//Ҫ���ӵ���ʡ��˾ 
		}else if( "1" == orgTypeId ){//�ϼ���֯��ʡ��˾
			commonInfor.setValue("orgTypeId","2");//Ҫ���ӵ����й�˾
		}else if( "2" == orgTypeId ){//�ϼ���֯���й�˾
			commonInfor.setValue("orgTypeId","3");//Ҫ���ӵ���Ӫҵ��
		}else if("3" == orgTypeId ){//�ϼ���Ӫҵ��
			commonInfor.setValue("orgTypeId","4");//Ҫ���ӵ���Ӫ����
		}else if( "4" == orgTypeId ){//�ϼ���֯�Ƿֹ�˾
			commonInfor.setValue("orgTypeId","5");//Ҫ���ӵ��ǲ���
			commonInfor.setFieldReadOnly("orgType",false);
			divDepartmentInfo.style.display = "block" ;
			departmentInfo.setReadOnly( false ) ;
			departmentInfo.setFieldReadOnly("regionName",true);
			departmentInfo.setFieldReadOnly("superPartyName",true);		
			if( actionType == "insert" ){
				departmentInfo.clearData();
				departmentInfo.insertRecord(null);
				departmentInfo.setFieldPopupEnable("regionName",true);
				departmentInfo.setFieldPopupEnable("superPartyName",true);
			}			
		}else if( "5" == orgTypeId ){//�ϼ���֯�ǲ���
			commonInfor.setValue("orgTypeId","6");//Ҫ���ӵĿ����ǲ���,Ҳ�����ǰ���,Ĭ��Ϊ����
			commonInfor.setFieldReadOnly("orgType",false);
			
			//add 2008-11-06
			divDepartmentInfo.style.display = "block" ;
			departmentInfo.setReadOnly( false ) ;
			departmentInfo.setFieldReadOnly("regionName",true);
			departmentInfo.setFieldReadOnly("superPartyName",true);		
			if( actionType == "insert" ){
				departmentInfo.clearData();
				departmentInfo.insertRecord(null);
				departmentInfo.setFieldPopupEnable("regionName",true);
				departmentInfo.setFieldPopupEnable("superPartyName",true);
			}//add end here
			
		}else if( "9" == orgTypeId ){//�ϼ���֯��������֯
			commonInfor.setValue("orgTypeId","9");//Ҫ���ӵ��¼���ֻ֯����������֯
		}
		if( orgTypeId != "5" ){//����ϼ���֯�����ǲ���,�������û��Լ�ѡ����֯����
			commonInfor.setFieldReadOnly("orgTypeId",true);
		}else{//����ϼ���֯�ǲ���,�������û�ѡ����֯����Ϊ���Ż��߰���.
			commonInfor.setFieldReadOnly("orgTypeId",false);
		}
	}
}

//���Ӹ���֯
function addRootOrganization_onClick(){
	if( actionType != "" ){
		alert( "���ȱ������ȡ����ǰ������������֯!" ) ;
		return ;
	}
	actionType = "insert";
	nodeType = "root" ;
	addOrganization( null ) ;
}

//�����¼���֯
function addSubOrganization_onClick(){
	var orgTypeId = organizationTreeView.selectedItem.orgTypeId ;
	if( orgTypeId == "6" ){
		alert("��ǰ��֯�ǰ���,�����������������¼���֯!");
		return ;
	}
	if( actionType != "" ){
		alert( "���ȱ������ȡ����ǰ������������֯!" ) ;
		return ;
	}
	actionType = "insert" ;
	nodeType = "child" ;
	addOrganization( getCurrentOrganizationId() );
}

function inputNext_onClick(){
	if( !$("commonInforForm").validate()){
		return ;
	}
	//if( commonInfor.getValue("orgTypeId") == "5" ){
	if( commonInfor.getValue("orgTypeId") == "5"||commonInfor.getValue("orgTypeId") == "6" ){//modified 2008-11-06
		if( !$("departmentInfoForm").validate()){
			return ;
		}
	}
	//add by xiaoyong �������ӻ��޸İ���ʱ�����������͡�����ѡ�����������--20090309
	if(commonInfor.getValue("orgTypeId") == "6"&&departmentInfo.getValue("departType")=="05"){
	    alert("���ӻ��޸İ���ʱ�����������͡�����ѡ�����������,������ѡ�񣡣�");	
		return ;	
	}
	//add end;--20090309
	mainPage.setSelectedPageIndex(1);
}

function commitOrganization_onClick(){
	if( actionType == "" )
		return ;

	if( !$("contactInforForm").validate()){
		return ;
	}
	if( contactInfor.getValue("postcode").length != 6 ){
		alert("�������볤�ȱ�������λ!");
		return ;
	}
	
	commonInfor.setReadOnly( true ) ;
	contactInfor.setReadOnly( true ) ;
	departmentInfo.setReadOnly( true ) ;
	
	//��ʼ��Organization����
	var organizationObj = new OrganizationInfo() ;
	for( var ele in organizationObj ){
		if( commonInfor.getField( ele )){
			organizationObj[ele] = commonInfor.getValue( ele ) ;
		}
	}
	organizationObj["addDescription"] = contactInfor.getValue("addDescription");
	//organizationObj["orgClass"] = "00" ;//orgClass;//��֯���� : 00--�ڲ���֯; 01--�������; 02--�Ե���Ӫ��; 03--������;-1--δ֪����.
	
	if( nodeType == "root" ){
		organizationObj["parentPartyId"] = "-1";//����Ǹ��ڵ�,���ϼ���֯��IDΪ-1
	}
	
	var orgTypeId = commonInfor.getValue("orgTypeId");
	if( orgTypeId == "0" ){//���Ź�˾
		organizationObj.orgLevel = "1";
	}else if( orgTypeId == "1"){//ʡ��˾
		organizationObj.orgLevel = "2";
	}else if( orgTypeId == "2"){//������
		organizationObj.orgLevel = "3";
	}else if( orgTypeId == "3"){//Ӫҵ��
		organizationObj.orgLevel = "4";	
	}else if( orgTypeId == "4"){//Ӫ����
		organizationObj.orgLevel = "5";	
	}else if( orgTypeId == "5"){//����
		organizationObj.orgLevel = "6";	
	}else if( orgTypeId == "6"){//����
		organizationObj.orgLevel = "7";	
	}else if( orgTypeId == "9"){//������֯
		organizationObj.orgLevel = "9";	
	}
	
	//��ʼ��Address����
	var contactObj = new AddressInfo() ;
	for( var ele in contactObj ){
		if( contactInfor.getField( ele ) ) {
			contactObj[ele] = contactInfor.getValue( ele ) ;
		}
	}

	//��ʼ��Department����
	//if( commonInfor.getValue("orgTypeId") == "5" ){
	if( commonInfor.getValue("orgTypeId") == "5" ||commonInfor.getValue("orgTypeId") == "6"){//modified 2008-11-06
		var departmentVo = new DepartmentVO();
		for( var ele in departmentVo ){
			departmentVo[ele] = departmentInfo.getValue( ele ) ;
		}
		departmentVo["termFlag"] = "-1";
	}
	
	var ajaxCall = new NDAjaxCall(true);
	var result ;
	
	var callBack = function( reply ){
		result = reply.getResult() ;
		if(result=="-1"){
		   alert("��֯�����ظ������������!!!");
		}
		else{
		if( actionType == "insert" ){//���Ӳ���
			//�������˷��������ӵ�����
			commonInfor.setValue("partyId", result ) ;
			contactInfor.setValue("partyId", result ) ;
			getAddressIdByPartyId( result );
			addOrgToTreeList();
		}else if ( actionType == "update" ){//�༭����
			//initOrganization();//ˢ��ҳ������
			refreshTreeList();
			//locateRecord(result);//��λ�����µļ�¼��
		}
		alert("�����ɹ�!");
		}
		actionType = "" ;
		nodeType = "" ;		
	}
	
	var arr = new Array();
	arr[0] = organizationObj;	
	arr[1] = contactObj ;
	organizationObj[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.OrganizationVO' ;		
	contactObj[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.AddrVO' ;				
	if( actionType == "insert" ){
		//if( commonInfor.getValue("orgTypeId") == "5" ){
		if( commonInfor.getValue("orgTypeId") == "5" || commonInfor.getValue("orgTypeId") == "6"){//modified 2008-11-06
			departmentVo[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.MpDepartVO' ;	
			arr[2] = departmentVo;
			ajaxCall.remoteCall("PartyService","insertOrganizationWithDepartment",arr,callBack);
		}else{
			ajaxCall.remoteCall("PartyService","insertOrganization",arr,callBack);
		}
	}else if( actionType == "update" ){
		//if( commonInfor.getValue("orgTypeId") == "5" ){
		if( commonInfor.getValue("orgTypeId") == "5" || commonInfor.getValue("orgTypeId") == "6"){//modified 2008-11-06
			departmentVo[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.MpDepartVO' ;	
			arr[2] = departmentVo ;
			ajaxCall.remoteCall("PartyService","updateOrganizationWithDepartment",arr,callBack);
		}else{
			ajaxCall.remoteCall("PartyService","updateOrganization",arr,callBack);
		}
	}
}
function getAddressIdByPartyId( id ) {
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ) {
		contactInfor.setValue("addrId",reply.getResult());
		commonInfor.setValue("addrId",reply.getResult()) ;
	}
	ajaxCall.remoteCall("PartyService","getAddressIdByPartyId",[id],callBack);
}
function refreshTreeList(){
	var orgTree = document.all.organizationTreeView;
	var org = orgTree.selectedItem;
	
	for(var ele in org){
		if(commonInfor.getField(ele)){
			org[ele] = commonInfor.getValue( ele );
		} 
	}
		
	org.refresh();
}

//�������ӵĽڵ����ӵ�����
function addOrgToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
}
//����������һ���ӽڵ�
function addChildToTreeList(){
	var orgTree = document.all.organizationTreeView;
	var selItem = orgTree.selectedItem;
	var org = orgTree.createTreeNode();
				//alert('j2'+$("text_commonInfor_orgTypeId").getValue());
	var orgTypeName = document.getElementById("text_commonInfor_orgTypeId").value;//$("text_commonInfor_orgTypeId").value; 
	org.orgTypeName = orgTypeName ;
	for(var ele in selItem){
		if(commonInfor.getField(ele)){
			org[ele] = commonInfor.getValue( ele );
		} 
	}
	org.parentPartyId = selItem.partyId ;
	selItem.add(org);
	org.setSelected();
}
//����������һ�����ڵ�
function addRootToTreeList(){
	var orgTree = document.all.organizationTreeView;
	var org = orgTree.createTreeNode();
	
	var orgTypeName = $("text_commonInfor_orgTypeId").value;
	org.orgTypeName = orgTypeName ;
	var org1 = new OrganizationInfo();
	 
	for(var ele in org1){
		if(commonInfor.getField(ele)){
			org[ele] = commonInfor.getValue( ele );
		} 
	}
	
	orgTree.add(org);
	//�Ѹýڵ�ѡ���ϣ�����������룬���鲻Ҫѡ��
	org.setSelected();
}

function cancelCommit1_onClick(){
	cancelCommit_onClick();
}

function cancelCommit_onClick(){
	if( actionType == "" )
		return ;
		
	clickOrganization() ;
	actionType = "" ;
	nodeType = "" ;
	commonInfor.setReadOnly( true );
	contactInfor.setReadOnly( true ) ;
	departmentInfo.setReadOnly( true ) ;
}

//��λ��ID�ƶ�����֯��¼��
function locateRecord( id ){
	var organizationTree = document.all.organizationTreeView;
	var items = organizationTree.items ;
	for( var i = 0; i < items.length; i ++ ){
		if( items.partyId == id ){
			items[i].setSelected;
			clickOrganization();
		}
	}
}

/*
function addOrganizationToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}

function addChildToTreeList(){
	var organizationTree = document.all.organizationTreeView;
	var selItem = organizationTree.selectedItem;
	var org = organizationTree.createTreeNode();
	
	for(var ele in selItem){
		if(commonInfor.getField(ele)){
			org[ele] = commonInfor.getValue( ele );
		} 
	}
	
	selItem.add(org);
	org.setSelected();
}

function addRootToTreeList(){
	var organizationTree = document.all.organizationTreeView;
	var organization = organizationTree.createTreeNode();
	
	var organization1 = new OrganizationInfo();
	 
	for(var ele in organization1){
		if(commonInfor.getField(ele)){
			organization[ele] = commonInfor.getValue( ele );
		} 
	}

	organizationTree.add(organization);
	organization.setSelected();
}
*/

function refreshDatainfo(id){
	commonInfor.setValue("partyId", id ) ;
	contactInfor.setValue("partyId", id ) ;	
}

var actionType = "";
var nodeType = "";

//��ȡ��ǰ��ѡ�е���֯������
function getCurrentOrganizationId(){
	var selItem = organizationTreeView.selectedItem;
	return selItem.partyId ;
}

function getCurrentPartyId(){
	var selItem = organizationTreeView.selectedItem;
	if( selItem.partyId == null || selItem.partyId == "" ) {
		return ;
	}
	
	return selItem.partyId ;
}
function mainPage_beforePageChanged( page, fromIndex, toIndex ){
	//�����ǰ���Ǵ��ڱ༭״̬,��ֱ���˳�
	if( actionType == "" ) {
		return true ;
	}
	
	if( actionType != "" && toIndex  == 2 ){
		alert("���ȱ��泣����Ϣ����ϵ��Ϣ,���л�����չ��Ϣ��ǩҳ!");
		return false ;
	}
	
	if( actionType != "" && fromIndex == "2" ){
		alert("���ȱ���������Ϣ���л���������ǩҳ!");
		return false ;
	}
	
	if( fromIndex == 0 ){//��ǰ��"����"��ǩҳ
		if( !$("commonInforForm").validate()){//У�鳣����Ϣ������Ϸ���
			return false ;
		}
		//if( commonInfor.getValue("orgTypeId") == "5" ){
		if( commonInfor.getValue("orgTypeId") == "5"|| commonInfor.getValue("orgTypeId") == "6" ){//modified 2008-11-06
			if( !$("departmentInfoForm").validate()){
				return false ;
			}
		}
	}
	
	if( fromIndex == 1 ){//��ǰ����ϵ��Ϣ��ǩҳ
		if( !$("contactInforForm").validate()){//У����ϵ��Ϣ������Ϸ���
			return false ;
		}
	}
	return true ;
}

function mainPage_onPageChanged( page, index ){
	/*
	if( actionType == "insert" || actionType == "update" ) {
		return ;
	}
	var partyId = getCurrentPartyId();
	if( index == 0 ){
	
	}else if ( index == 1 ){
		//��ʼ����ϵ��Ϣ��壬�����֯����ϵ��ַ��Ϣ
		var addressInfo = new AddressInfo() ;
		addressInfo = getRemoteObject( "PartyService","getAddressInfoByPartyId",partyId);
		
		if( addressInfo != null ){ 
			contactInfor.clearData(); 
			contactInfor.insertRecord( null ) ;
			var addObj = addressInfo;
			for( var ele in addObj ){
				if( contactInfor.getField( ele )){
					contactInfor.setValue( ele, addObj[ ele ] ) ; 
				}
			}
			contactInfor.setValue("addDescription",commonInfor.getValue("addDescription"));
		}	
	}else if ( index == 2 ){
		//��ʼ���߼���ַ�б�
		queryLogicalAddrList();	
	}*/
	if( actionType == "insert" ) {
		return ;
	}
	if( index == 1 ){
		var addObj = new AddressInfo() ;
		var selItem = organizationTreeView.selectedItem ;
		if( selItem == null ){
			return ;
		}
		var partyId = selItem.partyId ;
		addObj = getRemoteObject( "PartyService","getAddressInfoByPartyId",partyId);
		
		if( addObj != null ){ 
			contactInfor.clearData(); 
			contactInfor.insertRecord( null ) ;
			
			contactInfor.disableControls() ;
			
			for( var ele in addObj ){
				if( contactInfor.getField( ele )){
					contactInfor.setValue( ele, addObj[ ele ] ) ; 
				}
			}
			contactInfor.setValue("addDescription",addressDescription);
			
			contactInfor.enableControls();
		}	
	}
	if( index == 2 ){
		queryLogicalAddrList();
	}
}

function DepartmentVO(){
	this.partyId = "";
	this.departType = "";
	this.termFlag = "";
	this.paySeatType = "";
	this.regionId = "";
	this.regionName = "";
	this.superPartyId = "";
	this.superPartyName = "";
}
 
function displayDepartmentInfo(){
	var partyId = organizationTreeView.selectedItem.partyId ;
	
	var ajaxCall = new NDAjaxCall( false );
	
	var callBack = function( reply ){
		var departmentVo = new DepartmentVO() ;
		departmentVo = reply.getResult() ;
		departmentInfo.clearData();
		departmentInfo.insertRecord( null );
		
		departmentInfo.disableControls(); 
		
		for( var ele in departmentVo ){
			if( departmentInfo.getField( ele )){
				departmentInfo.setValue( ele, departmentVo[ele] );
			}
		}
		departmentInfo.enableControls();
	}
	
	ajaxCall.remoteCall( "PartyService","getMpDepartByPartyId",[partyId], callBack ) ;
}

function downloadSubItems(){
	var selItem = organizationTreeView.selectedItem ;
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

var addressDescription = "";
/*
����֯�б��ϵ����¼
���ݵ�ǰ������ļ�¼��ʼ��������Ϣ��壬
��ʼ����ϵ��Ϣ��壬
*/ 
function clickOrganization(){ 
	var selItem = organizationTreeView.selectedItem; 
	if( selItem.partyId == null || selItem.partyId == "" ) {
		return ;
	}
	downloadSubItems() ;
	var partyId = selItem.partyId ;
	
	//���������ϢDataset
	commonInfor.clearData();
	commonInfor.insertRecord(null);
	//�����ϵ��ϢDataset
	contactInfor.clearData();
	contactInfor.insertRecord( null );
	//��ʼ��������Ϣ��壬�����֯�Ļ�����Ϣ
	var organizationInfo = new OrganizationInfo();
	organizationInfo = getRemoteObject( "PartyService","getOrganization",partyId);
	addressDescription = organizationInfo["addDescription"];
	
	if( organizationInfo != null ){
		commonInfor.clearData();
		commonInfor.insertRecord(null);
		var orgObj = organizationInfo;
		
		commonInfor.disableControls() ;
		
		for( var ele in orgObj ){
			if( commonInfor.getField( ele )){
				commonInfor.setValue( ele, orgObj[ele] ) ;
			}
		}
		
		var parentParty = getPartyById( selItem.parentPartyId ) ;
		if( parentParty ){
			commonInfor.setValue("parentPartyName", parentParty.orgName);
		}
		
		commonInfor.enableControls();
	}
	
	//if( commonInfor.getValue("orgTypeId") != "5" ){
	if((commonInfor.getValue("orgTypeId")!="5")&&(commonInfor.getValue("orgTypeId")!="6")){//modified 2008-11-06
		divDepartmentInfo.style.display = "none";
		departmentInfo.setReadOnly(true) ;
		departmentInfo.setFieldPopupEnable("regionName",false);
		departmentInfo.setFieldPopupEnable("superPartyName",false);
	}else{
		divDepartmentInfo.style.display = "block" ;
		departmentInfo.setReadOnly(true) ;
		displayDepartmentInfo() ;
		departmentInfo.setFieldPopupEnable("regionName",false);
		departmentInfo.setFieldPopupEnable("superPartyName",false);
	}

	//��ʼ���߼���ַ�б�
	//queryLogicalAddrList();
	
	commonInfor.setReadOnly( true );
	contactInfor.setReadOnly( true ) ;
	actionType = "" ;
	nodeType = "";
	mainPage.setSelectedPageIndex(0); 
}

function queryLogicalAddrList(){
	var selItem = organizationTreeView.selectedItem;
	if( selItem.partyId == null || selItem.partyId == "" ) {
		return ;
	}
	
	var partyId = selItem.partyId ;
	var parameters = logicalAddrList.parameters();
	parameters.setDataType("orgId","string");
	parameters.setValue("orgId",partyId);
	Dataset.reloadData(logicalAddrList);	
}
//������֯�����ȡ����֯�����µ���֯����
function getOrganizationTypesByClass(){
	var parameterSet = orgTypeInfo.parameters();
	parameterSet.setDataType("orgClass", "string");
	//parameterSet.setValue("orgClass", orgClass) ;
	parameterSet.setValue("orgClass", "00") ;
	Dataset.reloadData( orgTypeInfo ) ;
}

function getPartyById(partyId ){
	var organizationTree = document.all.organizationTreeView;
	var items = organizationTree.items;
	for( var i = 0 ; i < items.length ; i ++ ){
		if( items[i].partyId == partyId ){
			return items[i] ;
		}
	}
}

function AddressInfo(){
	this.addrId = "" ;
	this.provinceName = "" ;
	this.cityName = "" ;
	this.countyName = "" ;
	this.streetName = "" ;
	this.streetNbr = "" ;
	this.deta = "" ;
	this.postcode = "" ;
	this.alias = "" ;
	this.addDescription = "" ;
	this.bankBranchId = "" ;
	this.bankAccNo = "" ;
	this.incrTaxNo = "" ;
}

function OrganizationInfo() {
	this.partyId="";				//������ID
	this.orgName="";			//����������
	this.effDate="";				//��Чʱ��
	this.state="";					//״̬
	this.stateDate="";			//״̬ʱ��
	this.addDescription="";	//��ַ����
	this.parentPartyId="";	//�ϼ�ID
	this.orgCode="";			//��֯����
	this.orgLevel="";			//��֯����
	this.orgTypeId="";		//��֯����ID(����,����......) 
	//this.orgTypeName = "";//��֯��������
	this.orgContent="";		//��֯����
	this.orgClass="";			//��֯����
	this.orgType = "";////�Ʒ���֯����(92B:�������,'92A':�����ڲ���֯)
	this.addrId = "";//��ַID
//	this.expDate="";//ʧЧʱ��()
	this.orgManager="";//�Ƿ���֯����
	this.partnerType="";//�����������
}

//�ӷ������˻�ȡ��֯���ߵ�ֵַ������Ϣ
function getRemoteObject( actionName, methodName, partyId ){
	var ajaxCall = new NDAjaxCall(false);//�첽��ʽ��ѯ������������
	var obj ;
	var callBack = function( reply ){
		obj = reply.getResult() ;
	}
	ajaxCall.remoteCall(actionName,methodName,[partyId],callBack);
	return obj ;
}

//�����߼���ַ�ĵ���¼�
function addLogicAddr_onClick(){
	if( actionType != "" ){
		alert( "���ȱ������ȡ����ǰ������ִ�����Ӳ���!") ;
		return ;
	}
	var addressId = contactInfor.getValue("addrId");
	if( !addressId ){
		alert("������д��ϵ��Ϣ�������Ժ���������߼���ַ!");
		return ;
	}
	actionType = "insert" ;
	logicalAddrInfo.setReadOnly(false);
	
	logicalAddrInfo.clearData();
	logicalAddrInfo.insertRecord( null ) ;
}
//�༭�߼���ַ�ĵ���¼�
function editLogicAddr_onClick(){
	if( actionType != "" ){
		alert( "���ȱ������ȡ����ǰ������ִ�б༭����!") ;
		return ;
	}
	var logicalAddrId = logicalAddrList.getValue("logicalAddrId");
	if( !logicalAddrId ){
		alert("��ѡ��Ҫ�༭���߼���ַ��¼!");
		return;
	}
	actionType = "update" ;
	logicalAddrInfo.setReadOnly(false);
}
//ɾ���߼���ַ�ĵ���¼�
function deleteLogicAddr_onClick(){
	if( actionType != "" ){
		alert( "���ȱ������ȡ����ǰ������ִ��ɾ������!") ;
		return ;
	}
	var logicalAddrId = logicalAddrList.getValue("logicalAddrId");
	if( !logicalAddrId ){
		alert("��ѡ����Ҫɾ�����߼���ַ��¼!");
		return;
	}
	if(confirm("ȷ��Ҫɾ����ǰ���߼���ַ��?")){
		var ajaxCall = new NDAjaxCall(true);
		var obj ;
		var callBack = function( reply ){
			obj = reply.getResult() ;
			if( obj  ){
				alert("ɾ���ɹ�!");
				queryLogicalAddrList();
			}else{
				alert("ɾ��ʧ��!");
			}
		}
		ajaxCall.remoteCall("PartyService","deleteLogicalAddr",[logicalAddrId],callBack);
	}
}
//�ύ��ť�ĵ���¼�
function commitLogicAddr_onClick(){
	if( logicalAddrInfo.getValue("logicalAddrDeta") == "" ){
		alert("����д�߼���ַ��ϸ��Ϣ!");
		return ;
	}
	var ajaxCall = new NDAjaxCall(true);
	var obj ;
	
	var callBack = function( reply ){
		obj = reply.getResult() ;
		if( actionType == "insert"){
			alert("�����߼���ַ�ɹ�!");
		}else if( actionType == "update" ){
			alert("�޸��߼���ַ�ɹ�!");
		}
		queryLogicalAddrList();
		clickLogicalAddressList();
		
		actionType = "" ;
		logicalAddrInfo.setReadOnly(true);
	}
	
	var logicAddr = new LogicalAddress();
	logicAddr["addrId"] = contactInfor.getValue("addrId");
	logicAddr["logicalAddrId"] = logicalAddrInfo.getValue("logicalAddrId");
	logicAddr["logicalAddrType"] = logicalAddrInfo.getValue("logicalAddrType");
	logicAddr["logicalAddrDeta"] = logicalAddrInfo.getValue("logicalAddrDeta");
	logicAddr[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.LogicalAddrVO' ;	
	if( actionType == "insert"){
		ajaxCall.remoteCall("PartyService","insertLogicalAddr",[logicAddr],callBack);
	}else if( actionType == "update"){
		ajaxCall.remoteCall("PartyService","updateLogicalAddr",[logicAddr],callBack);
	}
}
//ȡ����ť�ĵ���¼�
function cancelLogicAddr_onClick(){
	if( actionType == "" ){
		return ;
	}
	actionType = "" ;
	logicalAddrInfo.setReadOnly(true);
}

function LogicalAddress(){
	this.logicalAddrId = "";
	this.addrId = "";
	this.logicalAddrType = "";
	this.logicalAddrDeta = "";
}

function clickLogicalAddressList(){
	logicalAddrInfo.setValue("logicalAddrId",logicalAddrList.getValue("logicalAddrId"));
	logicalAddrInfo.setValue("addrId",logicalAddrList.getValue("addrId"));
	logicalAddrInfo.setValue("logicalAddrType",logicalAddrList.getValue("logicalAddrType"));
	logicalAddrInfo.setValue("logicalAddrDeta",logicalAddrList.getValue("logicalAddrDeta"));	
	logicalAddrInfo.setReadOnly( true );
}

//ѡ��Ӫҵ��
function button_departmentInfo_regionName_onClick(){
	if( actionType == "" ){
		return ;
	}
	var param = new Object();
	var menuCode = Global.getCurrentMenuCode();
	param["privilegeType"] = "3";//Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
	
	//Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
	param["privilegeCode"] = menuCode ;			//�˵�����
	param["regionLevel"] = "97D" ;	//Ӫҵ��
	param["selectType"] = "1" ;//��ѡ��ѡ��־,1 Ϊ��ѡ,2 Ϊ��ѡ
	var loginInfo = getStaffLoginInfo();
	param["regionIds"] = loginInfo.businessId ;
	var returnValue=window.showModalDialog("../common/ResourceRegionSelect.jsp",param,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0){return;}
	departmentInfo.setValue("regionId", returnValue[0].regionId);
	departmentInfo.setValue("regionName", returnValue[0].regionName);
}



//ѡ���ϼ�����
function button_departmentInfo_superPartyName_onClick(){
	if( actionType == "" ){
		return ;
	}

	var para = new Object();
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
	para["orgType"] = "5" ;//ֻ��ѡ����
	para["selectParent"] = "2" ;
	para["selectType"] = "1" ;//1��ʾ��ѡ,2��ʾ��ѡ
	para["oldIds"] = getStaffOrganizationId();

	var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0 ) return ;
	departmentInfo.setValue("superPartyId", returnValue[0]["orgId"] );
	departmentInfo.setValue("superPartyName",returnValue[0]["orgName"]);
}

function orgTypeDropdown_onSelect( dropdown, record, editor){
	var newOrgTypeId = parseFloat( record.getValue("value") );//��ѡ�����֯����ID

	//�����ǰ�Ĳ��������ǲ���,��----
	if( actionType == "insert") {
		if( nodeType == "root" ){//������ӵ��Ǹ��ڵ�,��ֻ��ѡ������Ϊ���Ź�˾����������֯
			if( (newOrgTypeId != 9) && (newOrgTypeId != 0 )){
				alert("����֯������ֻ��ѡ���Ź�˾����������֯!");
				return false ;
			}
		}else{//������� �� ���¼���֯
			var selItem =organizationTreeView.selectedItem ;
			if( selItem == null ){//���û��ѡ���κ��ϼ���֯,��ֱ�ӷ���
				alert("��ѡ���ϼ���֯!");
				return false;
			}
			
			var parentOrgTypeId = "" ;	//�ϼ��ڵ����֯����ID
			parentOrgTypeId = parseFloat( selItem.orgTypeId );//��ǰ��ѡ�еĽڵ���������ӵĽڵ���ϼ��ڵ�

			if( parentOrgTypeId == 9 || newOrgTypeId == 9 ){
				if( parentOrgTypeId != newOrgTypeId ){
					alert("����Ϊ������֯����֯�����¼���ֻ֯����������֯!");
					return false;
				}
			}			
			//����ϼ���֯�ǲ���,���¼���֯���ܴ����ϼ���֯(���Ե����ϼ���֯,Ҳ�ǲ���)
			if( newOrgTypeId < parentOrgTypeId ){//�����ڲ���֯���¼���֯���ܴ����ϼ���֯
				alert("�¼���֯���ܴ����ϼ���֯!");
				return false ;
			} 
		}
	}
	
	//if( newOrgTypeId == 5 ){
	if( newOrgTypeId == 5 || newOrgTypeId == 6){//modified 2008-11-06
		divDepartmentInfo.style.display = "block" ; 
		departmentInfo.setReadOnly( false ) ;
		departmentInfo.setFieldReadOnly("regionName",true);
		departmentInfo.setFieldReadOnly("superPartyName",true);		
		if( actionType == "insert" ){
			departmentInfo.clearData();
			departmentInfo.insertRecord(null);
			departmentInfo.setFieldPopupEnable("regionName",true);
			departmentInfo.setFieldPopupEnable("superPartyName",true);
		}
	}else{
		divDepartmentInfo.style.display = "none";
	}
	return true ;
}
 
