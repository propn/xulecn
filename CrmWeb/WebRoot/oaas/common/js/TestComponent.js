function page_onLoad(){
	//Dataset.reloadData( regionList ) ;
	//initRoles();
}

function qryPagination(pPage){
	var pageSize = document.all.roleTreeView.pageSize ;
	var pageIndex = pPage;
	var t1 = new Date();
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(false);
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		old_alert(queryResult);
		document.all.roleTreeView.loadByXML( queryResult );
		document.all.roleTreeView.paginate(3000, pageIndex);
		//alert("InitTree is waste of time:" + (new Date() - t1)+"ms");
	}
	ajaxCall.remoteCall("TestService","getRoles",[pageIndex, pageSize],callBack);
}

function bindTable(tableId) {
	/*var vo = new RegionClass() ;
	vo["regionId"] = "paramId" ;
	vo["regionName"] = "paramName";
	vo["regionType"] = "paramType";
	vo[Buffalo.BOCLASS] = "com.ztesoft.framework.RegionClass";*/
	
	var t1 = new Date();
	var ajaxCall = new NDAjaxCall(false);
	ajaxCall.remoteCall("TestService","getRoleList",[],function(reply){
		Buffalo.bind(tableId,reply.getResult());
		alert("Bind is waste of time:" + (new Date() - t1)+"ms");
	})
}

function RegionClass(){
	this.regionId = "";
	this.regionName = "";
	this.regionType = "";
}

function tableList_onTableHeaderClick(){
	alert();
}

function initRegionDataSet_onClick(){
	var t1 = new Date();
	/*var vo = new RegionClass() ;
	vo["regionId"] = "paramId" ;
	vo["regionName"] = "paramName";
	vo["regionType"] = "paramType";
	vo[Buffalo.BOCLASS] = "com.ztesoft.framework.RegionClass";
	var parameterSet = regionList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", vo ) ;*/
	Dataset.reloadData( regionList ) ;
	
	//alert("InitDataset is waste of time:" + (new Date() - t1)+"ms");
}

function initRolesTree_onClick(){
	var t1 = new Date();
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(false);
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		//document.all.roleTreeView.paginate(3000, 1);
		document.all.roleTreeView.loadByXML( queryResult );
		alert("InitTree is waste of time:" + (new Date() - t1)+"ms");
	}
	ajaxCall.remoteCall("TestService","getRoles",[1,5],callBack);
}

function invokeException_onClick(){
	var ajaxCall = new NDAjaxCall(true);
	ajaxCall.remoteCall("TestService","getExceptionVO",[],function(reply){
		var obj = reply.getResult();
		alert("ID = " + obj["regionId"] + ",Name = " + obj["regionName"] + ",Type = " + obj["regionType"]);
	});
}

function invokeObjectParam_onClick(){
	var callBack = function( reply ) {
		var obj = reply.getResult();
		for( var ele in obj ){
			//alert( ele + " : " + obj[ele] );
		}
		old_alert(reply.getResult());
	}
	var arr = new Array();
			
	var loginRequest = new RegionClass() ;
	loginRequest["regionId"] = "paramId" ;
	loginRequest["regionName"] = "paramName";
	loginRequest["regionType"] = "paramType";
	
	var loginRequest1 = new RegionClass() ;
	loginRequest1["regionId"] = "paramId 1" ;
	loginRequest1["regionName"] = "paramName 1";
	loginRequest1["regionType"] = "paramType 1";
	
	arr[0] = loginRequest ;
	arr[1] = loginRequest1 ;
	var ajaxCall = new NDAjaxCall(true);
	loginRequest[Buffalo.BOCLASS] = "com.ztesoft.framework.RegionClass";
	loginRequest1[Buffalo.BOCLASS] = "com.ztesoft.framework.RegionClass";
	ajaxCall.remoteCall("TestService","invokeObjectParam",arr,callBack);
}

function clickRole(){
}