function page_onLoad(){
	getStaffByOrgId();//通过参数传递进来的组织ID查询该组织下的所有员工
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
	
	if( window.dialogArguments[2] == "1" ){//单选
		document.all.removeOne.disabled = true;
		document.all.addAll.disabled = true;
		document.all.removeAll.disabled = true;
	}
}

//查询按钮的响应事件
function doQuery_onClick(){
	getStaffByOrgId();
}

//查询全部员工按钮的响应事件
function doQueryAll_onClick(){
	queryInfo.clearData();
	getStaffByOrgId();
}

//执行查询
function getStaffByOrgId(){ 
	var orgId = window.dialogArguments[0] ;
	var scope = window.dialogArguments[1]; 
	
	var staff = new Staff() ;
	staff["orgPartyId"] = orgId ;//所属组织
	staff["scope"] = scope ;//查询范围
	staff["staffCode"] = queryInfo.getValue("staffCode" );//员工工号
	staff["partyName"] = queryInfo.getValue("partyName" );//员工姓名
	
	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;
	parameterSet.setValue("pageIndex","1");

	Dataset.reloadData( staffList ) ;
}

//判断一个记录是否已经被选择
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

//从待选员工列表中移动一个记录到选中列表中
function addOne_onClick(){
	if( !staffList.getCurrent() ){//如果待选列表没有记录,返回
		return ;
	}
	if( ifRecordSelected( staffList.getValue("partyRoleId"))){
		alert("当前员工已经在选择列表中了!");
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
//从待选员工列表中移动所有记录到选中列表中
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

//从已选员工列表中删除当前记录
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

//清楚已选员工列表
function removeAll_onClick(){
	selectedStaffList.clearData() ;
}

//确定按钮响应事件
function btn_Confirm_onClick(){
	var selectFlag = window.dialogArguments[2] ;//单选标志,1 为单选, 0 为多选
	if( selectFlag == "1" ){
		if( selectedStaffList.getCount() > 1 ){
			alert("只能选择一个员工!");
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

//取消按钮响应事件
function btn_Cancel_onClick(){
	window.returnValue = null ;
	window.close();
}

function Staff(){
	this.partyRoleId = "" ;//参与人角色ID
	this.partyName = "" ;//参与人姓名
	this.staffCode = "" ;//工号
	this.orgPartyId = "" ;//所属组织
	this.scope="";//查询范围
}
