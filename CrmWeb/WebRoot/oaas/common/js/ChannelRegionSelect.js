/**
 * 渠道片区选择组件,参数:
 * @param channelRegionType : 004 表示营销职能片区,005表示维护职能片区,001表示职能片区(包括营销职能和
 * 维护职能), 000 表示管理职能
 * @param selectType : 1 表示单选, 2 表示多选
 * @param staffCond : 1 表示是否负责人作为查询条件, 0表示不需要使用负责人作为查询条件;如果使用负责
 * 人作为查询条件,则查询出来的渠道片区是负责人所负责的渠道片区,而不是全部.
 */

var channelRegionType = "" ;	//渠道片区类型
var staffCond = "" ;	//是否以渠道片区负责人作为查询条件
var selectType = "" ;	//单选/多选标志
var checkChildren = "";
var custType = "";//管理客户类型
var businessId = "";//分公司ID
var oldIds = "";
var pathCode = "";

//初始化TreeList组件的数据显示
function page_onLoad(){
	var paramObj = window.dialogArguments;

	if( paramObj == null ){
		channelRegionType = "001" ;
		staffCond = "0" ;
		selectType = "1" ;
		checkChildren = "1" ;
		custType = "-1";
		businessId = "-1" ;
		oldIds = "";
	}else{
		
		channelRegionType = paramObj["channelRegionType"] ;
		staffCond = paramObj["staffCond"];
		selectType = paramObj["selectType"];
		checkChildren = paramObj["checkChildren"];
		custType = paramObj["custType"] ;
		businessId = paramObj["businessId"];
		oldIds = paramObj["oldIds"];

		if( oldIds == null ){
			oldIds = "" ;//默认为空
		}
		
		if( custType == "" || custType == null || custType == "undefined" ){
			custType = "-1" ;//管理客户类型
		}
		if( businessId == "" || businessId == null || businessId == "undefined" ){
			businessId = "-1" ;//管理客户类型
		}
				
		if( channelRegionType == "" || channelRegionType == null || channelRegionType == "undefined" ){
			channelRegionType = "001" ;//没有渠道类型限制
		}
		if( staffCond == "" || staffCond == null || staffCond == "undefined" ){
			staffCond = "0" ;//不使用负责人作为查询条件
		}
		if( selectType == "" || selectType == null || selectType == "undefined" ){
			selectType = "1" ;//默认为单选
		}
		if( checkChildren == "" || checkChildren == null || checkChildren == "undefined" ){
			checkChildren = "1" ;//默认自动钩选下级
		}
		if( oldIds != "" ){
			var arr = oldIds.split(",") ;
			pathCode = getPathCode( arr[0] ) ;
		}
	}

 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		chnRegionTreeView.loadByXML(queryResult);
		if( chnRegionTreeView.items == null || chnRegionTreeView.items.length == 0 ){
        	alert("您没有任何数据权限!");
        }
        for( var i = 0 ; i < chnRegionTreeView.items.length ; i ++ ){
        	var arr = oldIds.split( "." ) ;
        	for( var j = 0 ; j < arr.length ; j ++ ){
        		if( arr[j] == chnRegionTreeView.items[i].chnRegionId ){
        			chnRegionTreeView.items[i].setChecked( true ) ;
        		}
        	}
        }
        if( pathCode != "" ){
        	var arr = pathCode.split(".");
        	for( var i = 0 ; i < chnRegionTreeView.items.length ; i ++ ){
        		for( var j = 0 ; j < arr.length ; j ++ ){ 
	        		if( chnRegionTreeView.items[i].chnRegionId == arr[j] ) { 
						getCheckedItem(chnRegionTreeView.items[i]) ;
					}
				}
			}
		}
	}
	if( staffCond == "0" ){
		ajaxCall.remoteCall("ChmService","getChnRegionsByParentId",["-1", channelRegionType, staffCond, "-1", "-1", "00A" ],callBack);
	}else {
		ajaxCall.remoteCall("ChmService","getChnRegionsByParentId",["", channelRegionType, staffCond, "-1", "-1", "00A" ],callBack);
	}
}
function getCheckedItem( item ){ 
	clickChnRegion( item ) ;

	var subItems = item.items; 
	if( subItems ){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			var arr = pathCode.split("."); 
			for( var j = 0 ; j < arr.length ; j ++ ){
				if( arr[j] == subItems[i].chnRegionId ){
					getCheckedItem( subItems[i] );
				}
			}
		}
	}else{
		//alert("subItems is null");
	}
}
function getPathCode( orgId ) {
	var ajaxCall = new NDAjaxCall( false ) ;
	var returnValue = "";
	var callBack = function( reply ) {
		returnValue = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getPathCode",[orgId,"3"],callBack);
	return returnValue ;
}
function clickChnRegion(item){
	var selItem = null;
	if( item != null ){
		selItem = item ;
	}else{
		selItem = chnRegionTreeView.selectedItem ;
	}
	if(selItem == null ) return;
	if( selItem.items ) {
		return ;
	}
	var chnRegionId = selItem.chnRegionId ;
	
	var ajaxCall = new NDAjaxCall( false ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			
			for( var i = 0 ; i < selItem.items.length ; i ++ ){
	        	var arr = oldIds.split( "." ) ;
	        	for( var j = 0 ; j < arr.length ; j ++ ){
	        		if( arr[j] == selItem.items[i].chnRegionId ){
	        			selItem.items[i].setChecked( true ) ;
	        		}
	        	}
	        }
		}
	}
	ajaxCall.remoteCall("ChmService","getChnRegionsByParentId",[chnRegionId, channelRegionType, "0", custType, businessId, "00A" ],callBack);
}

function checkChnRegion(){
	if( chnRegionTreeView.items == null || chnRegionTreeView.items.length == 0 ){
		return ;
	}
	clickChnRegion() ;
	
	var selItem = chnRegionTreeView.selectedItem ;
	
	if( selItem.chnRegionType != channelRegionType && selItem.getChecked() ){
		//渠道片区类型,004 表示营销职能片区,005表示维护职能片区,001表示职能片
		//区(包括营销职能和维护职能),000表示管理片区
		if( channelRegionType == "004" ){
			alert("只能选择营销职能片区!");
			selItem.setChecked( false ) ;
			return ;
		}else if( channelRegionType == "005" ){
			alert("只能选择维护职能片区!");
			selItem.setChecked( false ) ;
			return ;
		}else if( channelRegionType == "001" && selItem.chnRegionType == "000"){
			alert("只能选择营销职能片区或者维护职能片区!");
			selItem.setChecked( false ) ;
			return ;
		}else if( channelRegionType == "000" ){
			alert("只能选择管理片区!");
			selItem.setChecked( false ) ;
			return ;
		}
	}
	var subItems = selItem.items ;
	if( subItems && selectType != "1" && checkChildren == "1"){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			if( subItems[i].chnRegionType == channelRegionType ) { 
				subItems[i].setChecked( true ) ;
			}
		}
	}
	//******************************************************************************
	
	/*var subItems = selItem.items ;
	if( !subItems ){//如果当前节点没有子节点
		if( selItem.chnRegionType == "000" && channelRegionType != "000" ){//如果当前节点是管理类型,则返回
			alert("不能选择片区类型为管理的记录!");
			selItem.setChecked( false ) ;
		}
		return ;
	}
	
	//如果当前节点有子节点,则说明当前节点为管理节点,不能选择.但是可以选择子节点.
	//如果选择类型为多选,则自动为所有下级节点类型为符合要求的节点钩上
	if( selectType != "1" && checkChildren == "1"){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			//if( subItems[i].chnRegionType != "000" ){
			if( subItems[i].chnRegionType == channelRegionType ) {
				subItems[i].setChecked( true ) ;
			}
		}
	}else{
		if( selItem.chnRegionType == "000" && channelRegionType != "000"){ 
			alert("不能选择片区类型为管理的记录!");
			selItem.setChecked( false ) ;
		}
	}*/
}

function btnConfirm_onClick(){
	var returnList = new Array() ;
	var count = 0 ;
	
	var checkedItems = chnRegionTreeView.checkedItems ;
	for( var i = 0 ; i < checkedItems.length ; i ++ ){
		var obj = new Object() ;
		obj.chnRegionId = checkedItems[i].chnRegionId;
		obj.name = checkedItems[i].name;
		obj.chnRegionCode = checkedItems[i].chnRegionCode;
		obj.custType = checkedItems[i].custType ;
		obj.chnRegionLevel = checkedItems[i].chnRegionLevel ;
		obj.staffId = checkedItems[i].staffId ;
		returnList[count] = obj ;
		count ++ ;
	}
	if( returnList.length == 0 ){
		alert("请选择记录,或者点击取消按钮返回!");
		return ;
	}else{
		if( returnList.length > 1 && selectType == "1" ){
			alert("只能选择一个记录!");
			return ;
		}
		window.returnValue = returnList ;
		window.close();
	}
}

function btnCancel_onClick(){
	window.returnValue = null ;
	window.close();
}