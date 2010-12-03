function page_onLoad(){
	initCatalogTree();
}

//��ʼ��Ŀ¼��
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


//���ݹ���Ŀ¼���ĵ�ǰ�ڵ��ʼ��ҵ�����
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