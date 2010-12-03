function page_onLoad(){
	logicalAddrList.insertRecord();
}

function commitAddr_onClick(){
	if( !$("contactForm").validate()){
		return ;
	}
	var address = new Address();
	for( ele in address ){
		if( contactInfor.getValue( ele ) ){
			address[ele] = contactInfor.getValue( ele) ;
		}
	}
	
	var arrLogicalAddr = new Array() ;
	var i = 0 ;
	var record = logicalAddrList.getFirstRecord() ;
	while( record ){
		var logicalAddr = new LogicalAddress() ;
		for( ele in logicalAddr ){
			logicalAddr[ele] = record.getValue( ele ) ;
		}
		arrLogicalAddr[i] = logicalAddr ;
		i ++ ;
		record = record.getNextRecord();
	}
	
	var ajaxCall = new NDAjaxCall(true);
	var obj ;
	var callBack = function( reply ){
		alert("增加联系地址成功!");
	}
	ajaxCall.remoteCall("PartyService","insertAddrWithLogicalAddr",[address,arrLogicalAddr],callBack);
}

function addLogicAddr_onClick(){
	var record=logicalAddrList.getCurrent();
	if(record){
   		record=record.getNextRecord();
   		logicalAddrList.insertRecord();
	}else{
		logicalAddrList.insertRecord();
	}
}

function deleteLogicAddr_onClick(){
	logicalAddrList.deleteRecord( logicalAddrList.getCurrent());
}

function closeDialog_onClick(){
	window.close();
}

function Address(){
	this.addrId = "";
	this.provinceName = "";
	this.cityName = "";
	this.countyName = "";
	this.streetName = "";
	this.streetNbr = "";
	this.deta = "";
	this.postcode = "";
	this.alias = "";
}

function LogicalAddress(){
	this.logicalAddrId = "";
	this.addrId = "";
	this.logicalAddrType = "";
	this.logicalAddrDeta = "";
}