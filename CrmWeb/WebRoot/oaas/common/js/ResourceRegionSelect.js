/**
 * 资源地域选择组件,参数对象属性:
 * @param staffCode :员工工号,预留参数,不需传递
 * @param privilegeType : 权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码；
 * @param privilegeCode : 权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
 * @param regionLevel : 地域级别,97A表示集团公司,97B表示省公司,97C表示本地网,97D表示营业区,98D
 * 表示处理局,98E表示母局,98F表示局站
 * @param selectType : 多选/单选标志,1表示单选,2表示多选
 * @param regionIds : 默认选择的地域ID列表串,以逗号分割.
 * @param selectParent :	1表示不能选择上级地域,2表示可以选择上级地域,默认不可以选择上级地域,在可
 * 以选择上级地域的条件下,选择的多个地域必须是相同级别的,如传递的regionLevel参数为98F,且
 * selectParent 为2,则可以选择局站和局站以上所有有权限的地域,但是多选的地域必须是相同级别额,比
 * 如不能选择一个分公司和一个市公司.
 * @param selectDistinctRegionLevel :1表示只能选择相同级别的地域,2表示可以选择不同级别的地域
 * @param checkChildren
 * @param uncheckedParent 
 * @param downloadWhenChecked
 */

var staffCode = "" ;//工号
var privType = "";//权限条件类型
var privCode = "";//权限条件编码
var regionLevel = "" ;//可选的最低地域级别
var selectType = "" ;//单选/多选标志
var regionIds = "" ;//默认选择的地域ID
var selectParent = "" ;//是否可以选择最低地域级别参数所指定的上级地域
var selectDistinctRegionLevel = "" ;//是否只能选择相同级别的地域
var checkChildren = "" ;//是否在钩选上级地域的时候自动钩选下级地域
var uncheckedParent = "" ;//是否在钩选下级地域的时候自动取消钩选上级地域
var downloadWhenChecked = "" ;//是否在钩选地域的时候下载下级地域
var pathCode = "";
var pathCodeArray = new Array();

function page_onLoad(){
	var paramObj = window.dialogArguments;
	if( paramObj != null ){
		staffCode = paramObj["staffCode"] ;
		privType = paramObj["privilegeType"] ;
		privCode = paramObj["privilegeCode"] ;
		regionLevel = paramObj["regionLevel"] ;
		regionIds = paramObj["regionIds"];
		selectType = paramObj["selectType"];
		checkChildren = paramObj["checkChildren"];
		uncheckedParent = paramObj["uncheckedParent"];
		downloadWhenChecked = paramObj["downloadWhenChecked"];
		selectParent = paramObj["selectParent"];
		selectDistinctRegionLevel = paramObj["selectDistinctRegionLevel"] ;
		
		if( selectDistinctRegionLevel == null || selectDistinctRegionLevel == "undefined" || selectDistinctRegionLevel == "" ){
			selectDistinctRegionLevel = "1" ;//默认为只能选择相同类型的组织
		}
		if( selectParent == null || selectParent == "undefined" || selectParent == "" ){
			selectParent = "1" ;//默认不能选择上级组织,只能选择regionLevel参数指定的区域
		}
		if( staffCode == null ){
			staffCode = "" ;//默认为空
		}
		if( privType == null ){
			privType = "" ;//默认为空
			ifPrivLimit = false ;
		}		
		if( privCode == null ){
			privCode = "" ;//默认为空
		}
		if( regionLevel == null ){
			regionLevel = "" ;//默认为空
		}
		if( regionIds == null ){
			regionIds = "" ;//默认为空
		}
		if( selectType == null ){
			selectType = "1" ;//默认为单选
		}
		if( checkChildren == null ){
			checkChildren = "2" ;//默认当钩选当前组织的时候不自动钩选下级节点
		}
		if( uncheckedParent == null ){
			uncheckedParent = "2" ;//默认不作处理
		}
		if( downloadWhenChecked == null ){
			downloadWhenChecked = "1" ;//默认下载子节点
		}
		if( regionIds != "" ){
			var arr = regionIds.split(",") ;
			pathCode = getPathCode( arr[0] ) ;
		}
	}
	initRegion();
}
function getPathCode( orgId ) {
	var ajaxCall = new NDAjaxCall( false ) ;
	var returnValue = "";
	var callBack = function( reply ) {
		returnValue = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getPathCode",[orgId,"1"],callBack);
	return returnValue ;
}
function initRegion(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(false); 
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ; 
		regionTreeView.loadByXML(queryResult );
		if( regionTreeView.items == null || regionTreeView.items.length == 0 ){
			alert("您没有任何数据权限!");
			return ;
		}

        for( var i = 0 ; i < regionTreeView.items.length ; i ++ ){
        	var arr = regionIds.split( "." ) ;
        	for( var j = 0 ; j < arr.length ; j ++ ){
        		if( arr[j] == regionTreeView.items[i].regionId ){
        			regionTreeView.items[i].setChecked( true ) ;
        		}
        	}
        }

		if( pathCode != "" ){
        	var arr = pathCode.split(".");
        	var breakRoop = false ;
        	for( var i = 0 ; i < regionTreeView.items.length ; i ++ ){
        		for( var j = 0 ; j < arr.length ; j ++ ){
	        		if( regionTreeView.items[i].regionId == arr[j] ) { 
	        			breakRoop = true ;
						getCheckedItem(regionTreeView.items[i]) ;
						break;
					}
				}
				if( breakRoop ){
					break ;
				}
			}
		}
	}
	
	/**
	 *采用分层下载的方式实现.如果当前的层次在regionLevel之上,则允许通过点击下载被点击的地域的下级
	 *地域,如果当前的层次已经是regionLevel所指定的地域了,则不需要再次向服务器端请求下载往下层次的
	 *地域.下载层次等于regionLevel的地域的时候,需要再传递一个权限编码参数,服务器端过滤掉不属于这个
	 *权限所控制的地域的记录,只把用户有权限的地域下载下来.
	 **/
	if( privType == "-1" ){
		ajaxCall.remoteCall("RegionService","getResourceRegionListWithPrivFlag",["-1"],callBack); 	
	}else{
		ajaxCall.remoteCall("RegionService","getResourceRegionListByFilter",["-1",regionLevel,privType,privCode],callBack); 
	}
//	ajaxCall.remoteCall("RegionService","getResourceRegionListByFilter",["-1",regionLevel,privType,privCode],callBack); 
} 

function getCheckedItem( item ){ 
	clickRegion( item ) ;

	var subItems = item.items; 
	if( subItems ){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			var arr = pathCode.split("."); 
			for( var j = 0 ; j < arr.length ; j ++ ){
				if( arr[j] == subItems[i].regionId ){
					getCheckedItem( subItems[i] );
				}
			}
		}
	}else{
		//alert("subItems is null");
	}
}

function getCheckedItem1( item ){
	clickRegion( item ) ;
	var subItems = item.items; 
	if( subItems ){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			var arr = pathCode.split(".");
			for( var j = 0 ; j < arr.length ; j ++ ){
				if( arr[j] == subItems[i].regionId ){
					getCheckedItem( subItems[i] );
				}
			}
		}
	}
}

/*
 *currentSelectedRegionLevel变量用于保存用户钩选的第一个地域的地域级别,用于保证用户选择的多个区
 *域的级别是统一的,即在一次选择中,不能选择多个不同级别的地域
 */ 
var currentSelectedRegionLevel = "";
function checkRegion(){
	if( regionTreeView.selectedItem == null ){
		return ;
	}
	if( downloadWhenChecked == "1" ) {
		clickRegion();
	}
	//保存第一个被选中的地域级别
	if( currentSelectedRegionLevel == "" ){
		var checkedItem = regionTreeView.checkedItems[0] ;
		if( checkedItem != null && typeof( checkedItem ) != "undefined" ){
			currentSelectedRegionLevel = checkedItem.regionLevel ;
		}
	}

	var canCheckCurrentItem = true ;//判断当前节点是否能被钩选的变量
	var selItem = regionTreeView.selectedItem ;
	var selLevel = selItem.regionLevel ;
	
	if( !selItem.getChecked() ){//如果用户是取消钩选,则返回
		if( regionTreeView.checkedItems.length == 0 ){
			currentSelectedRegionLevel = "" ;
		}
		return ;
	}
	
	//selectType参数指定单选而实际选择了多个地域
	if( selectType == "1" && regionTreeView.checkedItems.length > 1 ){//单选条件不符合
		alert("只能选择一个地域") ;
		selItem.setChecked(false) ;
		if( regionTreeView.checkedItems.length == 0 ){
			currentSelectedRegionLevel = "";
		}		
		return ;
	}
	//没有权限的地域
	if( selItem.privilegeFlag != "T" ){//权限条件不符合
		alert("你在当前的地域没有权限!");
		selItem.setChecked(false) ;
		if( regionTreeView.checkedItems.length == 0 ){
			currentSelectedRegionLevel = "";
		}
		return ;
	}
	//地域级别不能选择最低地域级别上级的地域
	if( selItem.regionLevel != regionLevel && selectParent == "1" ){//组织类型条件不符合
		canCheckCurrentItem = false ;
		showErrorMsg(regionLevel) ;
	}
	if( selectParent == "2" ){//可以选择regionLevel参数指定的地域级别的所有上级地域
		//要求多选的多个地域级别必须一致,而实际的选择不一致.
		var currentCheckedItems = regionTreeView.checkedItems ;
		for( var n = 0 ; n < currentCheckedItems.length ; n ++ ){
//			if( selectDistinctRegionLevel == "1" && currentSelectedRegionLevel != "" && currentSelectedRegionLevel != selItem.regionLevel ){//如果要求选择的地域级别都一致,判断是否符合条件
			if( selectDistinctRegionLevel == "1" && currentSelectedRegionLevel != "" && currentSelectedRegionLevel != currentCheckedItems[n].regionLevel ){//如果要求选择的地域级别都一致,判断是否符合条件
				canCheckCurrentItem = false ;
				alert("你选择的多个地域的级别必须一致!");
				break ;
			}
		}
	}
	
	//确认是否钩选当前组织节点
	if( !canCheckCurrentItem ){
		selItem.setChecked(false) ;
		if( regionTreeView.checkedItems.length == 0 ){
			currentSelectedRegionLevel = "";
		}
	}
	
	//确定是否要取消钩选上级地域
	if( uncheckedParent == "1" && canCheckCurrentItem ){//如果参数要求选择地域的时候自动将上级组织的钩选取消.
		var parentItem = selItem.getParentItem();
		while( parentItem ){
			parentItem.setChecked( false ) ;
			parentItem = parentItem.getParentItem();
		}		
	}
	
	//确定是否钩选下级组织 
	if( checkChildren == "1" && selectType == "2" ){//必须是多选条件下才能自动钩选下级组织
		var subItems = selItem.items ;
		if( subItems ){//如果有下级节点才处理
			//如果当前节点的地域级别为regionLevel参数指定的级别
			if( selItem.regionLevel == regionLevel ){
				if( selectParent == "2" && currentSelectedRegionLevel != selItem.regionLevel && selectDistinctRegionLevel == "1"){//如果可以钩选上级地域,并且钩选的多个地域的级别不同,则属于不合法选择.
					alert("您钩选的多个地域必须是属于相同级别的!");
					selItem.setChecked( false ) ;
				}
				return ;
			}else {
				//如果钩选的不是传递参数所指定的地域级别.
				if( //如果选择的地域是参数指定地域级别的上级
					(( selLevel == "97A" ) && ( regionLevel == "97B" )) ||		//如果钩选的是集团公司而要选择的是省公司
					((selLevel == "97B" ) && ( regionLevel == "97C" )) ||		//如果钩选的是省公司而要选择的是本地网
					((selLevel == "97C" ) && ( regionLevel == "97D" )) ||		//如果钩选的是本地网而要选择的是营业区
					((selLevel == "97D" ) && ( regionLevel == "98D" )) ||		//如果钩选的是营业区而要选择的是处理局
					((selLevel == "98D" ) && ( regionLevel == "98E" )) ||		//如果钩选的是处理局而要选择的是母局
					(( selLevel == "98E" ) && ( regionLevel == "98F"))) {	//如果钩选的是母局而要选择的是局站
					//则钩选下级等于要选择的组织.
					for( var i = 0 ; i < subItems.length ; i ++ ){
						if( subItems[i].privilegeFlag != "T" ){//如果没有权限,则忽略掉
							continue ;
						}
						if( subItems[i].regionLevel == regionLevel ){//如果下级地域的级别是regionLevel参数指定的地域,则自动钩选
							if( selectDistinctRegionLevel == "1" ){//如果要求选择的地域级别必须一致,则再加下面的判断
								if( subItems[i].regionLevel == currentSelectedRegionLevel || currentSelectedRegionLevel == ""){
									subItems[i].setChecked(true) ;
								}
							}else{
									subItems[i].setChecked(true) ;
							}
						}else{
							//如果下级地域的级别不是regionLevel参数指定的地域,但是selectParent参数指定可以选择选择regionLevel的上级地域
							if( selectParent == "2" ){
								if( selectDistinctRegionLevel == "1" ){//如果要求选择的地域级别必须一致,则再加下面的判断
									if( subItems[i].regionLevel == currentSelectedRegionLevel || currentSelectedRegionLevel == ""){
										subItems[i].setChecked( true ) ;
									}
								}else{//否则无需判断
									subItems[i].setChecked( true ) ;
								}
							}
						}
					}
				}else{
					//如果下级地域的级别不是regionLevel参数指定的地域,但是selectParent参数指定可以选择选择regionLevel的上级地域
					if( selectParent == "2" ){
						for( var i = 0 ; i < subItems.length ; i ++ ){
							if( subItems[i].privilegeFlag != "T" ){
								continue ;
							}
							if( selectDistinctRegionLevel == "1" ){//如果要求选择的地域级别必须一致,则再加下面的判断
								if( subItems[i].regionLevel == currentSelectedRegionLevel ){
									subItems[i].setChecked( true ) ;
								}
							}else{//否则无需判断
								subItems[i].setChecked( true ) ;
							}
						}
					}
				}
			}
		}
	}
}

function showErrorMsg(regionLevel){
	if( regionLevel == "97A" ){
		alert("只能选择中国电信集团!");
	}else if( regionLevel == "97B" ){
		alert("只能选择省公司!");
	}else if( regionLevel == "97C" ){
		alert("只能选择本地网!");
	}else if( regionLevel == "97D" ){
		alert("只能选择营业区!");
	}else if( regionLevel == "98D" ){
		alert("只能选择处理局!");
	}else if( regionLevel == "98E" ){
		alert("只能选择母局!") ;
	}else if( regionLevel == "98F" ){
		alert("只能选择局站!");
	}
}

function clickRegion(paramItem){
	var selItem = null ;
	if( paramItem != null ){
		selItem = paramItem ;
	}else{
		selItem = regionTreeView.selectedItem ;
	}

	if( selItem == null ){ 
		return ;
	}
	if( selItem.items ){ 
		return ;
	}
	
	var selLevel = selItem.regionLevel ;
	
	if( selLevel == regionLevel ){ 
		return ;
	}
	downloadSubRegions(privType, privCode, regionLevel ,selItem ) ;  
}

function downloadSubRegions( privType,privCode,regionLevel , item ){
	var selItem = null ;
	if( item != null ){
		selItem = item ;
	}else {
		selItem = regionTreeView.selectedItem ;
	}
	if( selItem == null ){
		return ;
	}
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( false ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			
			var childItems = selItem.items ;
			if( childItems[0].regionLevel == regionLevel || selectParent == "2" ){
				if( regionIds != "" ){
					var arr = regionIds.split(",");
					if( childItems ){
						for( var j = 0; j < childItems.length; j ++ ){
							for( var i = 0 ; i < arr.length ; i ++ ){
								if( childItems[j].regionId == arr[i] ){
									childItems[j].setChecked("true") ;
								}
							}
						}
					}
				}
			}
		}
	}
	
	var regionId = selItem.regionId ;
	if( selItem.privilegeFlag == "T" ){//当前节点是权限范围节点
		//查询资源地域当前节点下的所有节点,并为这些下级节点加上权限标志.
		ajaxCall.remoteCall("RegionService","getResourceRegionListWithPrivFlag",[regionId],callBack); 
	}else {
		//查询资源地域,并过滤掉没有权限的地域
		ajaxCall.remoteCall("RegionService","getResourceRegionListByFilter",[regionId,regionLevel,privType,privCode],callBack); 	
	}
}

function btn_Confirm_onClick(){
	var items = regionTreeView.checkedItems;
	if( items == null ){
		return ;
	}
	
	//判断多选或者是单选
	if( selectType == "1" && items.length > 1 ){
		alert("只能选中一个记录!");
		return ;
	}

	//判断选择的地域层次是否为参数所制定的层次
	var regionArray = new Array();
	for( var i = 0; i < items.length ; i ++ ){
		if( items[i].regionLevel != regionLevel && selectParent == "1" && selectDistinctRegionLevel == "1" ){
			showErrorMsg(regionLevel);
		}
		var regionVO = new Object();
		regionVO["regionId"] = items[i].regionId ;
		regionVO["regionName"] = items[i].regionName ;
		regionVO["regionCode"] = items[i].regionCode ;
		regionVO["regionLevel"] = items[i].regionLevel ;
		regionArray[i] = regionVO ;
	}
	window.returnValue = regionArray;
	window.close();
}

function btn_Cancel_onClick(){
	window.returnValue = "" ;
	window.close();
}