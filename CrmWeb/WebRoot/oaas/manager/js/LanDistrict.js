//ҳ������¼�,����ѯ��Ϣ�еĴ���������ֶ�����Ϊֻ��,����ͨ��ѡ��ҳ��ѡ��,���ܼ�������
function page_onLoad(){
	var menuCode = Global.getCurrentMenuCode();
	lanDistrictList.parameters().setValue("districtCode","");
	lanDistrictList.parameters().setValue("districtName","");
	lanDistrictList.parameters().setValue("dealExch","");
	lanDistrictList.parameters().setValue("menuCode",menuCode);
	Dataset.reloadData( lanDistrictList ) ;
	queryInfo.setFieldReadOnly("dealExchName",true);
}

//��ѯ��ť����¼�����Ӧ����.��ѯ����ΪС������,С�����ƺʹ����
function btnQuery_onClick(){
	var menuCode = Global.getCurrentMenuCode();
	lanDistrictList.parameters().setValue("districtCode", queryInfo.getValue("districtCode"));
	lanDistrictList.parameters().setValue("districtName", queryInfo.getValue("districtName"));
	lanDistrictList.parameters().setValue("dealExch", queryInfo.getValue("dealExch"));
	lanDistrictList.parameters().setValue("menuCode",menuCode);
	Dataset.reloadData( lanDistrictList ) ;
}

//��ѯ��ϢForm�����ð�ť����¼���Ӧ����
function btnReset_onClick(){
	queryInfo.clearData() ;
}

//С���б���α�����¼���Ӧ����,ʹ���б��ϵ����ݳ�ʼ����ϸ��ϢForm������
function lanDistrictList_afterScroll(){
	lanDistrictInfo.clearData();
	actionType = "" ;
	lanDistrictInfo.setReadOnly(true) ;
	for( var i = 0 ; i < lanDistrictInfo.fields.length ; i ++ ){
		if( lanDistrictList.getField( lanDistrictInfo.fields[i].name )){
			lanDistrictInfo.setValue( i , lanDistrictList.getValue( i ) );
		}
	}
	//setRegionVO( "dealExch","dealExchName") ;
	//setRegionVO( "subExch","subExchName") ;
}

//ͨ��ID���÷������˵ķ����ȡ���������
/*
function setRegionVO( fieldId, fieldName ) {
	var ajaxCall = new NDAjaxCall( true ) ;
	var vo = new Object();
	var callBack = function( reply ) {
		vo = reply.getResult() ;
		if( vo != null ){
			lanDistrictInfo.setValue(fieldName,vo["regionName"]);
		}
	}
	var id = lanDistrictList.getValue(fieldId);
	ajaxCall.remoteCall( "PartyService","getRegionById",[id],callBack); 
	return vo ;
}
*/
var actionType = "";
function addLanDistrict_onClick(){//����С����ť�ĵ���¼���Ӧ����
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ����������!");
		return ;
	}
	actionType = "insert" ;
	lanDistrictInfo.clearData();
	lanDistrictInfo.setReadOnly(false) ;
	//����ֺ;�վ�ֶ�ֵ����ͨ����������,����ͨ���Ի���ѡ��,�����������ֶ�Ϊֻ��
	lanDistrictInfo.setFieldReadOnly("dealExchName",true);
	lanDistrictInfo.setFieldReadOnly("subExchName",true);	
}

//�༭��ť����¼�����Ӧ����
function editLanDistrict_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�����ٱ༭!");
		return ;
	}
	actionType = "update" ;
	lanDistrictInfo.setReadOnly(false) ;
	//����ֺ;�վ�ֶ�ֵ����ͨ����������,����ͨ���Ի���ѡ��,�����������ֶ�Ϊֻ��
	lanDistrictInfo.setFieldReadOnly("dealExchName",true);
	lanDistrictInfo.setFieldReadOnly("subExchName",true);	
}

//ɾ����ť����¼�����Ӧ����
function deleteLanDistrict_onClick(){
	if( !lanDistrictList.getCurrent() ){
		return ;
	}
	
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ������ɾ��!");
		return ;
	}
	
	if( !confirm( "��ȷ��Ҫɾ����ǰ��¼��?")){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ) {
		alert("ɾ����¼�ɹ�!");
		Dataset.reloadData( lanDistrictList ) ;
	}
	
	var id = lanDistrictList.getValue("districtId");
	ajaxCall.remoteCall( "PartyService","deleteLanDistrict",[id],callBack);
}

//�ж�С�������Ƿ��Ѿ�����,������ϵͳ�б�֤С�������Ψһ��
function checkLanCodeExist( code ){
	var ajaxCall = new NDAjaxCall( false ) ;
	var result ;
	
	var callBack  = function(reply){
		result = reply.getResult() ;
	}
	
	ajaxCall.remoteCall("PartyService","checkLanCodeExist",[code],callBack);
	return result ;
}

//ȷ����ť����¼�����Ӧ����
function commit_onClick(){
	if( actionType == "" ){
		return ;
	}
	
	if( !$("lanDistrictInfoForm").validate()){
		return ;
	}
	
	var lanDistrictVO = new LanDistrictVO();
	for( var ele in lanDistrictVO ){
		lanDistrictVO[ele] = lanDistrictInfo.getValue(ele) ;
	}
	
	//Ϊ�˱�֤С��������ϵͳ��Ψһ��,���������Ӻͱ༭С����Ϣ��ʱ�����У��ϵͳ���Ƿ��Ѿ�����
	//���û������С������һ���ı���
	if( actionType == "insert" ){
		if( checkLanCodeExist(lanDistrictVO.districtCode) ){
			alert("�Ѿ����ڱ���Ϊ" + lanDistrictVO.districtCode + "��С��,����д��������!");
			return ;
		}
	}else{
		if( lanDistrictVO.districtCode != lanDistrictList.getValue("districtCode" )){
			if( checkLanCodeExist(lanDistrictVO.districtCode) ){
				alert("�Ѿ����ڱ���Ϊ" + lanDistrictVO.districtCode + "��С��,����д��������!");
				return ;
			}
		}
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ) {
		if( actionType == "insert" ){	
			alert("���Ӽ�¼�ɹ�!");
		}else if ( actionType == "update" ){
			alert("�༭��¼�ɹ�!");
		}
		actionType = "";
		Dataset.reloadData( lanDistrictList ) ;
		lanDistrictInfo.setReadOnly(true) ;
	}
	
	if( actionType == "insert" ){	
		ajaxCall.remoteCall( "PartyService","insertLanDistrict",[lanDistrictVO],callBack);
	}else if ( actionType == "update" ){
		ajaxCall.remoteCall( "PartyService","updateLanDistrict",[lanDistrictVO],callBack);
	}
}

//ȡ����ť����¼�����Ӧ����
function cancel_onClick(){
	actionType = "" ;
	lanDistrictInfo.setReadOnly(true) ;
	lanDistrictList_afterScroll();
}

//��ѯ��ϢForm�Ĵ�����ֶΰ�ť����¼�����Ӧ����,������Դ�ߵ���ѡ�񴰿�
//���û�ѡ�����
function button_queryInfo_dealExchName_onClick(){
	var vo = selectRegion("98D");
	if( !vo ) {
		return ;
	}
	queryInfo.setValue("dealExch",vo.regionId);
	queryInfo.setValue("dealExchName",vo.regionName);
}

//��ϸ��ϢForm�ľ�վ�ֶΰ�ť����¼�����Ӧ����,������Դ�ߵĵ���ѡ�񴰿����û�
//ѡ���վ
function button_lanDistrictInfo_subExchName_onClick(){
	if(actionType == "" ){
		return ;
	}
	var returnValue = selectRegion("98F") ;
	if( !returnValue ){
		return ;
	}
	lanDistrictInfo.setValue("subExch",returnValue["regionId"]);
	lanDistrictInfo.setValue("subExchName",returnValue["regionName"]);
	
	//ѡ����վ�Ժ�,ͨ���������˵���,��ѯ�û�ѡ��ľ�վ���ϼ������,
	//��ȡ����ֵ�ID�����Ʋ���ʼ������ϸ��ϢForm��
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ) {
		var vo = reply.getResult() ;
		if( vo != null ){
			lanDistrictInfo.setValue("dealExch",vo["regionId"]);
			lanDistrictInfo.setValue("dealExchName",vo["regionName"]);
		}
	}
	
	var id = lanDistrictInfo.getValue("subExch");
	ajaxCall.remoteCall( "PartyService","getDealExchByExchStation",[id],callBack);	
}

//ѡ����Դ�ߵ�����,��������regionLevelָ����Ҫѡ��ĵ��򼶱�.
function selectRegion(regionLevel){
	var arr1 = new Object();
	var menuCode = Global.getCurrentMenuCode();
	arr1["privilegeType"] = "3";//Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
	//Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
	arr1["privilegeCode"] = menuCode ;//�˵����� 
	arr1["regionLevel"] =  regionLevel;	 
	arr1["selectType"] = "1" ;//��ѡ��ѡ��־,1 Ϊ��ѡ,2 Ϊ��ѡ 

	returnValue = showModalDialog("../common/ResourceRegionSelect.jsp",arr1,"dialogHeight: 350pt; dialogWidth: 450pt;");
	if( returnValue == null || returnValue.length == 0 ){
		return null ;
	}
	return returnValue[0] ;
}

//С����Ϣֵ����
function LanDistrictVO(){
	this.districtId = "";//С��ID,����
	this.districtCode = "";//С������
	this.districtName = "";//С������
	this.districtType = "";//С������
	this.districtAddr = "";//С����ַ
	this.dealExch = "";//�����
	this.subExch = "";//��վ
}