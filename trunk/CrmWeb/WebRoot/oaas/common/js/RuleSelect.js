function page_onLoad(){
	initCatalogTree();
}

//初始化目录树
function initCatalogTree(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		catalogTree.loadByXML(queryResult);
		initRuleInfos();
	}
	ajaxCall.remoteCall("RuleEngineService","getCatalogListXML",[],callBack);	
}


//根据规则目录树的当前节点初始化业务规则
function initRuleInfos(){
	var catalogId = catalogTree.selectedItem.id ;
	var parameterSet = ruleInfoList.parameters();
	parameterSet.setDataType("catalogId" , "string" ) ;
	parameterSet.setValue("catalogId", catalogId ) ;
	Dataset.reloadData( ruleInfoList ) ;
}

function btn_confirm_onClick(){
	var ruleInfo = new RuleInfo() ;
	ruleInfo.id = ruleInfoList.getValue("id") ;
	ruleInfo.name = ruleInfoList.getValue("name") ;
	ruleInfo.desc = ruleInfoList.getValue("ruleDesc") ;
	window.returnValue = ruleInfo;
	window.close() ;
}

function btn_cancel_onClick(){
	window.returnValue = null ;
	window.close();
}

function RuleInfo(){
	this.id = "";
	this.name = "";
	this.desc = "";	
}