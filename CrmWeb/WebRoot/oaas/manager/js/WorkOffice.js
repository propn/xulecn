function doQuery_onClick(){
	var queryCond = document.getElementById("queryInput").value ;
	officeList.parameters().setValue("officeDesc", queryCond ) ;
	officeList.reloadData();
}

function addOffice_onClick(){
	var officeInfo = getOfficeInfo() ;//prompt("请输入办公地点名称:","");
	if( officeInfo && officeInfo.officeDesc ){
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ){
			if( reply.getResult() ){
				alert("增加办公地点成功!") ;
				officeList.reloadData() ;
			}else{
				alert("增加办公地点失败!") ;
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
				alert("编辑办公地点成功!") ;
				officeList.reloadData() ;
			}else{
				alert("编辑办公地点失败!") ;
			}
		}
		ajaxCall.remoteCall( "PartyService","updateWorkOffice",[officeList.getValue("officeId"),officeInfo.officeDesc,officeInfo.officeAddr],callBack) ;
	}
}

function deleteOffice_onClick(){
	if( confirm( "确定要删除办公地点吗?" )){
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ){
			if( reply.getResult() ){
				alert("删除办公地点成功!") ;
				officeList.reloadData() ;
			}else{
				alert("删除办公地点失败!") ;
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
		alert("请先保存当前操作再增加!"); 
		return ;
	}
	machineInfor.setReadOnly(false) ;
	machineInfor.clearData();
	actionType = "insert" ;
}

function editMachine_onClick(){
	if( actionType != "" ){
		alert("请先保存当前操作再编辑!"); 
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
		alert("请先保存当前操作再删除!"); 
		return ;
	}
	if( machineList.getValue( "machineId" ) == "" ){
		return ;
	}
	if( confirm( "确定要删除机位信息吗?")){
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ){
			if( reply.getResult() ){
				alert("删除机位信息成功!") ;
				machineList.reloadData() ;
			}else{
				alert("删除机位信息失败!") ;
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
		alert("请输入合法的IP地址!");
		return ;
	}
	for( var i = 0 ; i < ipArr.length ; i ++ ){
		if( ipArr[i].length > 3 || ipArr[i].length == 0 || isNaN( ipArr[i]) || parseFloat(ipArr[i]) > 255){
			alert("请输入合法的IP地址!") ;
			return ;
		}
	}
	
	//MAC地址的格式为：XX-XX-XX-XX-XX-XX即由6个00--FF组成的字符串
	var macArr = machineInfor.getValue("macAddr").split("-");
	if( macArr.length != 6 ){
		alert("请输入合法的MAC地址!");
		return ;
	}
	for( var i = 0 ; i < macArr.length ; i ++ ){
		if( macArr[i].length > 2 || macArr[i].length == 0 || macArr[i].toUpperCase() > "FF" ){
			alert("请输入合法的MAC地址!") ;
			return ;
		}
	}
		
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ){
		if( reply.getResult() ){
			alert("操作成功!") ;
			machineList.reloadData() ;
		}else{
			alert("操作失败!") ;
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
