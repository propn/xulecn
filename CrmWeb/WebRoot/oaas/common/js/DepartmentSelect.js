function page_onLoad(){
	//queryAllDepartment() ;
}

function btnQueryAll_onClick(){
	var departType = window.dialogArguments[0] ;
	doQuery( departType , "", "" ) ;
}

function btnQuery_onClick(){
	var departType = window.dialogArguments[0] ;
	var departName = queryInfo.getValue("name");
	var businessId = queryInfo.getValue("businessId");
	doQuery( departType , departName, businessId ) ;
}

function btnReset_onClick(){
	queryInfo.clearData() ;
	queryInfo.insertRecord(null);
}

function doQuery(departType , departName , businessId ){
	departList.clearData() ;
	
	departList.parameters().setDataType("departType", "string") ; 
	departList.parameters().setValue("departType", departType);
	
	departList.parameters().setDataType("departName", "string") ; 
	departList.parameters().setValue("departName", departName); 
	
	departList.parameters().setDataType("businessId", "string") ;
	departList.parameters().setValue("businessId", businessId);
	
	Dataset.reloadData( departList ) ;
}

function btn_Confirm_onClick(){
	var selectType = window.dialogArguments[1] ;//多选单选标志-- 1为单选,0为多选
	var departArray = new Array();
	var i = 0 ;
	var record = departList.getFirstRecord();
	while( record != null ){
		if( System.isTrue( record.getValue("select") ) ){
			var depart = new DepartVO();
			depart["partyId"] = record.getValue("partyId");
			depart["orgName"] = record.getValue("orgName");
			departArray[i] = depart ;
			i ++ ;
			if( selectType == "1" && i > 1 ){
				alert("只能选择一个部门,请重新选择!");
				window.returnValue = null ;
				return ;
			}
		}
		record = record.getNextRecord() ;
	}
	window.returnValue = departArray ;
	window.close();
}

function btn_Cancel_onClick(){
	window.returnValue = null ;
	window.close() ;
}

function DepartVO(){
	this.partyId ;
	this.orgName ;
}