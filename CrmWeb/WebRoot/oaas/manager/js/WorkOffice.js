function doQuery_onClick(){
	var queryCond = document.getElementById("queryInput").value ;
	officeList.parameters().setValue("officeDesc", queryCond ) ;
	officeList.reloadData();
}

function addOffice_onClick(){
	var officeInfo = getOfficeInfo() ;//prompt("������칫�ص�����:","");
	if( officeInfo && officeInfo.officeDesc ){
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ){
			if( reply.getResult() ){
				alert("���Ӱ칫�ص�ɹ�!") ;
				officeList.reloadData() ;
			}else{
				alert("���Ӱ칫�ص�ʧ��!") ;
			}
		}
		
		ajaxCall.remoteCall( "PartyService","addWorkOffice",[officeInfo.officeDesc,officeInfo.officeAddr],callBack) ;
	}
}

function getOfficeInfo(officeDesc,officeAddr){
	var objParam = new Object();
	objParam.officeDesc = officeDesc;
	objParam.officeAddr = officeAddr;
	return window.showModalDialog("GetOfficeInfo.jsp",objParam,"dialogHeight: 150pt; dialogWidth: 400pt;");	
}


function editOffice_onClick(){
	if( officeList.getValue("officeId") == "" ){
		return ;
	}
	var officeInfo = getOfficeInfo( officeList.getValue("officeDesc"), officeList.getValue("officeAddr") );
	if( officeInfo && officeInfo.officeDesc ){
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ){
			if( reply.getResult() ){
				alert("�༭�칫�ص�ɹ�!") ;
				officeList.reloadData() ;
			}else{
				alert("�༭�칫�ص�ʧ��!") ;
			}
		}
		ajaxCall.remoteCall( "PartyService","updateWorkOffice",[officeList.getValue("officeId"),officeInfo.officeDesc,officeInfo.officeAddr],callBack) ;
	}
}

function deleteOffice_onClick(){
	if( confirm( "ȷ��Ҫɾ���칫�ص���?" )){
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ){
			if( reply.getResult() ){
				alert("ɾ���칫�ص�ɹ�!") ;
				officeList.reloadData() ;
			}else{
				alert("ɾ���칫�ص�ʧ��!") ;
			}
		}
		
		ajaxCall.remoteCall( "PartyService","deleteWorkOffice",[officeList.getValue("officeId")],callBack) ;	
	}
}

function officeList_afterScroll( dataset ) {
	machineList.parameters().setValue("officeId",officeList.getValue("officeId"));
	machineList.reloadData();
}

function machineList_afterScroll( dataset ) {
	machineInfor.setValue("macAddr",machineList.getValue("macAddr"));
	machineInfor.setValue("ipAddr",machineList.getValue("ipAddr"));	
	actionType = "" ;
	machineInfor.setReadOnly( true ) ;
}

var actionType = "";
function addMachine_onClick(){
	if( actionType != "" ){
		alert("���ȱ��浱ǰ����������!"); 
		return ;
	}
	machineInfor.setReadOnly(false) ;
	machineInfor.clearData();
	actionType = "insert" ;
}

function editMachine_onClick(){
	if( actionType != "" ){
		alert("���ȱ��浱ǰ�����ٱ༭!"); 
		return ;
	}
	if( machineList.getValue( "machineId" ) == "" ){
		return ;
	}
	machineInfor.setReadOnly(false) ;
	actionType = "update" ;
}

function deleteMachine_onClick(){
	if( actionType != "" ){
		alert("���ȱ��浱ǰ������ɾ��!"); 
		return ;
	}
	if( machineList.getValue( "machineId" ) == "" ){
		return ;
	}
	if( confirm( "ȷ��Ҫɾ����λ��Ϣ��?")){
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ){
			if( reply.getResult() ){
				alert("ɾ����λ��Ϣ�ɹ�!") ;
				machineList.reloadData() ;
			}else{
				alert("ɾ����λ��Ϣʧ��!") ;
			}
		}
		
		ajaxCall.remoteCall( "PartyService","deleteOfficeMachine",[machineList.getValue("machineId")],callBack) ;			
	}
}

function saveMachine_onClick(){
	if( actionType == "" ){
		return ;
	}
	if( !$("machineInforForm").validate()){
		return ;
	}
	var ipArr = machineInfor.getValue("ipAddr").split(".");
	if( ipArr.length != 4 ){
		alert("������Ϸ���IP��ַ!");
		return ;
	}
	for( var i = 0 ; i < ipArr.length ; i ++ ){
		if( ipArr[i].length > 3 || ipArr[i].length == 0 || isNaN( ipArr[i]) || parseFloat(ipArr[i]) > 255){
			alert("������Ϸ���IP��ַ!") ;
			return ;
		}
	}
	
	//MAC��ַ�ĸ�ʽΪ��XX-XX-XX-XX-XX-XX����6��00--FF��ɵ��ַ���
	var macArr = machineInfor.getValue("macAddr").split("-");
	if( macArr.length != 6 ){
		alert("������Ϸ���MAC��ַ!");
		return ;
	}
	for( var i = 0 ; i < macArr.length ; i ++ ){
		if( macArr[i].length > 2 || macArr[i].length == 0 || macArr[i].toUpperCase() > "FF" ){
			alert("������Ϸ���MAC��ַ!") ;
			return ;
		}
	}
		
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ){
		if( reply.getResult() ){
			alert("�����ɹ�!") ;
			machineList.reloadData() ;
		}else{
			alert("����ʧ��!") ;
		}
		cancelMachine_onClick();
	}
	var machineVO = new Object() ;
	machineVO.officeId = officeList.getValue("officeId");
	machineVO.macAddr = machineInfor.getValue("macAddr").toUpperCase();
	machineVO.ipAddr = machineInfor.getValue("ipAddr") ;
	if( actionType == "insert" ){
		ajaxCall.remoteCall( "PartyService","insertOfficeMachine",[machineVO],callBack) ;				
	}else if( actionType == "update" ) {
		machineVO.machineId = machineList.getValue("machineId") ;
		ajaxCall.remoteCall( "PartyService","updateOfficeMachine",[machineVO],callBack) ;				
	}
}

function cancelMachine_onClick(){
	actionType = "" ;
	machineInfor.setReadOnly(true) ;
}
