function page_onLoad(){
	getStaffByOrgId();//ͨ���������ݽ�������֯ID��ѯ����֯�µ�����Ա��
	var staffIds = window.dialogArguments[3];
	if( staffIds != null && staffIds != "" && staffIds != "undefined"){
		var staff = new Staff() ;
		staff["partyRoleId"] = staffIds;
		var parameterSet = selectedStaffList.parameters();
		parameterSet.setDataType("condition", "object");
		parameterSet.setValue("condition", staff ) ;
		parameterSet.setValue("pageIndex","1");
	
		Dataset.reloadData( selectedStaffList ) ;
	}
	
	if( window.dialogArguments[2] == "1" ){//��ѡ
		document.all.removeOne.disabled = true;
		document.all.addAll.disabled = true;
		document.all.removeAll.disabled = true;
	}
}

//��ѯ��ť����Ӧ�¼�
function doQuery_onClick(){
	getStaffByOrgId();
}

//��ѯȫ��Ա����ť����Ӧ�¼�
function doQueryAll_onClick(){
	queryInfo.clearData();
	getStaffByOrgId();
}

//ִ�в�ѯ
function getStaffByOrgId(){ 
	var orgId = window.dialogArguments[0] ;
	var scope = window.dialogArguments[1]; 
	
	var staff = new Staff() ;
	staff["orgPartyId"] = orgId ;//������֯
	staff["scope"] = scope ;//��ѯ��Χ
	staff["staffCode"] = queryInfo.getValue("staffCode" );//Ա������
	staff["partyName"] = queryInfo.getValue("partyName" );//Ա������
	
	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;
	parameterSet.setValue("pageIndex","1");

	Dataset.reloadData( staffList ) ;
}

//�ж�һ����¼�Ƿ��Ѿ���ѡ��
function ifRecordSelected( partyRoleId ) {
	var record = selectedStaffList.getFirstRecord() ;
	while( record ) {
		if( record.getValue("partyRoleId" ) == partyRoleId ){
			return true ;
		}
		record = record.getNextRecord() ;
	}
	return false ;
}

//�Ӵ�ѡԱ���б����ƶ�һ����¼��ѡ���б���
function addOne_onClick(){
	if( !staffList.getCurrent() ){//�����ѡ�б�û�м�¼,����
		return ;
	}
	if( ifRecordSelected( staffList.getValue("partyRoleId"))){
		alert("��ǰԱ���Ѿ���ѡ���б�����!");
		return ;
	}
	selectedStaffList.insertRecord() ;
	for( var i = 0 ; i < staffList.fields.length ; i ++ ){
		selectedStaffList.setValue(i, staffList.getValue(i));
	}
	staffList.moveNext();
	if(window.dialogArguments[2] == "1"){
		document.all.addOne.disabled = true;
		document.all.removeOne.disabled = false;
	}
}
//�Ӵ�ѡԱ���б����ƶ����м�¼��ѡ���б���
function addAll_onClick(){
	var record = staffList.getFirstRecord() ;
	while( record ) {
		if( ifRecordSelected( record.getValue("partyRoleId"))){
			record = record.getNextRecord() ;
			continue ;
		}
		selectedStaffList.insertRecord() ;
		for( var i = 0 ; i < record.fields.length ; i ++ ){
			selectedStaffList.setValue(i, record.getValue(i));
		}
		record = record.getNextRecord() ;
	}
}

//����ѡԱ���б���ɾ����ǰ��¼
function removeOne_onClick(){
	if( !selectedStaffList.getCurrent()){
		return ;
	}
	selectedStaffList.deleteRecord() ;
	if(window.dialogArguments[2] == "1" && !selectedStaffList.getCurrent()){
		document.all.addOne.disabled = false;
		document.all.removeOne.disabled = true;
	}

}

//�����ѡԱ���б�
function removeAll_onClick(){
	selectedStaffList.clearData() ;
}

//ȷ����ť��Ӧ�¼�
function btn_Confirm_onClick(){
	var selectFlag = window.dialogArguments[2] ;//��ѡ��־,1 Ϊ��ѡ, 0 Ϊ��ѡ
	if( selectFlag == "1" ){
		if( selectedStaffList.getCount() > 1 ){
			alert("ֻ��ѡ��һ��Ա��!");
			return ;
		}
	}
	
	var staffArray = new Array();
	var i = 0 ;
	var record = selectedStaffList.getFirstRecord();
	while( record != null ){
		var staff = new Staff();
		staff["id"] = record.getValue("partyRoleId");
		staff["name"] = record.getValue("partyName");
		staff["staffCode"] = record.getValue("staffCode");
		staffArray[i] = staff ;
		i ++ ;
		record = record.getNextRecord() ;
	}
	window.returnValue = staffArray ;
	window.close();
}

//ȡ����ť��Ӧ�¼�
function btn_Cancel_onClick(){
	window.returnValue = null ;
	window.close();
}

function Staff(){
	this.partyRoleId = "" ;//�����˽�ɫID
	this.partyName = "" ;//����������
	this.staffCode = "" ;//����
	this.orgPartyId = "" ;//������֯
	this.scope="";//��ѯ��Χ
}
