var menuCode = "";
function page_onLoad(){
	menuCode = window.dialogArguments[0];
	dateInfor.setValue("expDate","2029-12-31");
	initStaffPrivilegeDialog();
}

function initStaffPrivilegeDialog(){
	initPrivileges();
}

//根据区域类型初始化TreeList组件的数据显示
function initRegion(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
	}

	if( regionType.value == "999" ){
		return ;
	}else if( regionType.value == "0"){
		var regionIds = new Array() ;
		var checkedRegions = privilegeTreeView.checkedItems ;
		for( var i = 0 ; i < checkedRegions.length ; i ++ ){
			regionIds[i] = checkedRegions[i].privId ;
		}
		ajaxCall.remoteCall("RegionService","getPrivilegeRegion",[menuCode,"0"],callBack);
	}else if( regionType.value == "1" ){
		ajaxCall.remoteCall("RegionService","getPrivilegeRegion",[menuCode,"1"],callBack);
	}else if( regionType.value == "2" ){
		ajaxCall.remoteCall("RegionService","getPrivilegeRegion",[menuCode,"2"],callBack);
	}else if( regionType.value == "3" ){
		initOrganization();
	}
	
	if( regionType.value != "3" ){
		divOrganizationTree.style.display = "none" ;
		divRegionTree.style.display = "block" ;
		document.all.regionTreeView.loadByXML(queryResult );
		var rootItem = regionTreeView.rootItems; 
		for( var i = 0 ; i < rootItem.length ; i ++ ){
			rootItem[i].removeChildren();
		}
	}else{
		divOrganizationTree.style.display = "block" ;
		divRegionTree.style.display = "none" ;
	}
}

function clickOrganization(){
	var selItem = organizationTreeView.selectedItem ;
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
		}
	}
	
	var partyId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[partyId], callBack);
}

function initOrganization(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		organizationTreeView.loadByXML( queryResult );
		var rootItems = organizationTreeView.rootItems ;
		for( var i = 0 ; i < rootItems.length ; i ++ ){
			rootItems[i].removeChildren() ;
		}
	}
	//ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
	ajaxCall.remoteCall("PartyService","getPrivilegeOrganization",[menuCode],callBack);	
}


//区域类型下拉框的onChange响应事件
function changeRegion(){
	var ifRegionRela = false ;
	var selItems = privilegeTreeView.checkedItems ;
	if( selItems.length == 0 ){
		alert("请先选择权限!");
		regionType.value = "999" ;
		return ;
	}
	
	for( var i = 0; i < selItems.length ; i ++){
		if( selItems[i].ifRegionRela != "F" ){
			ifRegionRela = true ;//存在区域相关的权限
			break ;
		}
	}
	//如果存在区域相关的权限,并且用户没有选择区域,则提示用户需要选择
	if( ifRegionRela && regionType.value == "999" ){
		alert("您选择的权限中带有区域相关的权限类型,请选择区域!");
		regionType.value = "999" ;
		return ;
	}else if( !ifRegionRela && regionType.value !="999" ){
		//如果用户选择的权限都是区域无关的,而且用户选择了区域,则提示用户不需选择区域
		alert("当前权限是地域无关的,无需选择地域!");
		regionType.value = "999" ;		
		return ;
	}

	initRegion() ;


}

//初始化权限列表
function initPrivileges(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		privilegeTreeView.loadByXML(queryResult);
		var rootItems = privilegeTreeView.rootItems ;
		for( var i = 0 ; i < rootItems.length; i ++ ){
			rootItems[i].removeChildren() ;
		}
	}
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListWithRegionRelatByParentId",["-1"],callBack);
}

//表格的点击事件，在详细面板中显示当前被点中的记录的详细信息
function clickRegion(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	//获取当前节点下的所有下级节点.
	downloadSubItems() ;
}

function clickPriv(){
	var selItem = privilegeTreeView.selectedItem ;
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( false ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
		}
	}
	
	var privId = selItem.privId ;
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListWithRegionRelatByParentId",[privId], callBack);
}

//当用户点击区域树表的时候,判断当前节点是否有下级节点,如果没有,
//到服务器端查看是否有下级节点,如果服务器端返回了该节点的下级
//节点数据,则将这些数据解析成为树表的下级节点,添加到树表上.
function downloadSubItems(){
	var selItem = regionTreeView.selectedItem ;
	if( selItem.items ){ 
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		selItem.insertByXML( result ) ;
		selItem.expand(true);
	}
	
	var regionId = selItem.regionId ;
	if( regionType.value == "0" ){
		ajaxCall.remoteCall("RegionService","getBillRegionList",[regionId], callBack);
	}else if( regionType.value == "1" ){
		ajaxCall.remoteCall("RegionService","getResourceRegionList",[regionId], callBack);
	}else if( regionType.value == "2" ){
		ajaxCall.remoteCall("RegionService","getSaleRegionList",[regionId], callBack);
	}
}
function checkStaffDefaultPriv(partyRoleId){
	var returnValue = false ;
	var ajaxCall = new NDAjaxCall( false );
	var callBack = function( reply ) {
		returnValue = reply.getResult() ;
	}
	ajaxCall.remoteCall( "PartyService","checkStaffDefaultPriv",[partyRoleId],callBack);
	return returnValue ;
}

function commit_onClick(){
	if( !$("dateInforForm").validate()){
		return ;
	}
	
	var currentDate = getTodayStr();
	if( dateInfor.getValue("effDate") < currentDate ){
		alert("生效日期必须在今天或者今天之后!");
		return ;
	}
	if( dateInfor.getValue("expDate") <= currentDate ){
		alert("失效日期必须迟于今天!");
		return ;
	}			
	if( dateInfor.getValue("effDate") > dateInfor.getValue("expDate")){
		alert("生效日期必须前于失效日期!");
		return;
	}
				
	var result = null ;
	var ajaxCall = new NDAjaxCall(false);
	
	var callBack=function(reply){
		result = reply.getResult();
		if( result ){
			alert("操作成功!");
			window.returnValue = 0;
		}
		window.close();
	}
	
	var ifRegionRela = false ;//用户选择的权限中是否存在区域相关的权限,如果有,则必须提示用户选择区域.
	var selItems = privilegeTreeView.checkedItems;
	if( selItems.length == 0 ) {
		alert("请选择权限!");
		return ;
	}
	for( var i = 0 ; i < selItems.length ; i ++ ){
		if( selItems[i].privCode == "DEFPRV" ){
			if( checkStaffDefaultPriv(window.dialogArguments[1]) ){
				alert("您选择的权限中包含了默认权限,该权限已经分配给当前的工号,不能重复分配!");
				return ;
			}
			if( regionType.value != "1" ){
				alert("您选择的权限中包含了默认权限,该权限的地域只能是资源线!");
				return ;
			}else{
				if( regionTreeView.checkedItems.length > 1 ){
					alert("您选择的权限中包含了默认权限,该权限的地域范围必须是处理局且只能是唯一的!");
					return ;
				}else{
					if( regionTreeView.checkedItems[0].regionLevel != "98D" ){
						alert("您选择的权限中包含了默认权限,该权限的地域范围必须是处理局!");
						return ;
					}
				}
			}
		}
		if( selItems[i].privCode == "LANPRI" ){
			if( regionType.value != "1" ){
				alert("您选择的权限中包含了本地网控制权限,该权限的地域只能是资源线!");
				return ;
			}else{
				for(var j=0;j<regionTreeView.checkedItems.length;j++){
					if( regionTreeView.checkedItems[j].regionLevel != "97C" ){
						alert("您选择的权限中包含了本地网控制权限,该权限的地域范围必须是本地网!");
						return ;
					}
				}
			}
		}
		if( selItems[i].ifRegionRela !="F" ){
			ifRegionRela = true ;
			//break ;
		}
	}
	var regionArray = new Array();
 
 	//如果用户选择了区域,则记录区域的ID
	if( regionType.value != "3" && regionType.value != "999" ){//选择地域线的区域
		var items = regionTreeView.checkedItems;
		if( items.length == 0 && ifRegionRela ){
			alert("请选择区域!");
			return ;
		}
		for( var i = 0; i < items.length ; i ++ ){
			regionArray[i] = items[i].regionId ;
		}
	}else{
		var items = organizationTreeView.checkedItems;
		if( items.length == 0  && ifRegionRela ){//选择组织线的地域
			alert("请选择区域!");
			return ;
		}		
		for( var i = 0; i < items.length ; i ++ ){
			regionArray[i] = items[i].partyId ;
		}	
	}

	var checkedPrivs = privilegeTreeView.checkedItems ;
	var saveList = new Array() ;
	var count = 0 ;
	
	for( var i =0; i < checkedPrivs.length ; i ++ ){
        var item = checkedPrivs[i];
        var isNew = true ;
        if( item.ifRegionRela == "F" ){//如果区域无关 ,则不管用户是否选择了区域,都设置regionType=-1 , regionId = -1
            var saveObj = new Object() ; 
            saveObj.partyRoleId = window.dialogArguments[1] ;//参与人角色标识
            saveObj.privId = item.privId ;//权限ID 
//            saveObj.regionType = "-1";//区域无关,区域类型为-1
            saveObj.regionType = "9";//区域无关,区域类型为9(天津版本中使用9表示区域无关，在重庆版本中，暂时还是-1表示区域无关)
            saveObj.regionId = "-1";//区域无关,区域类型为-1
            saveObj.effDate = dateInfor.getValue("effDate");
            saveObj.effDate = saveObj.effDate + " 00:00:00" ;
            saveObj.expDate = dateInfor.getValue("expDate");
            saveObj.expDate = saveObj.expDate + " 23:59:59" ;
            saveList[count] = saveObj ;
            count ++ ;
        }else{//区域相关
            for( var m = 0; m < regionArray.length ; m ++ ){
                var saveObj = new Object() ;
                saveObj.partyRoleId = window.dialogArguments[1];//参与人角色标识
                saveObj.privId = item.privId ;//权限ID
                saveObj.regionType = regionType.value ;//区域类型
                saveObj.regionId = regionArray[m] ;//区域ID
                saveObj.effDate = dateInfor.getValue("effDate");
                saveObj.effDate = saveObj.effDate + " 00:00:00" ;
                saveObj.expDate = dateInfor.getValue("expDate");
                saveObj.expDate = saveObj.expDate + " 23:59:59" ;
                saveObj[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.StaffPrivVO' ;	
                saveList[count] = saveObj ;
                count ++ ;
            }
        }
	} 
	if( saveList.length > 0 ) {
		ajaxCall.remoteCall("PartyService","insertBatchStaffPrivAndScopes",[saveList],callBack);
	}else{
		window.returnValue = -1 ;
		window.close() ;
	}
}

function cancel_onClick(){
	window.returnValue = -1 ;
	window.close() ;
}

function itemChecked(){
	//响应记录上的check box的点击事件
	clickPriv();
	/*
	 *选择权限的规则:选择一个下级权限,则该权限的所有上级权限都自动被选;如果一个权限的下级权限
	 *已经被选,则该权限为必选,不能通过任何方式取消该节点被选择状态.
	 */
	var selItem = privilegeTreeView.selectedItem;
	var subItems = selItem.items ;
	if( selItem.getChecked() == false ){
		/*如果取消钩选一个权限,判断该权限是否存在任意一个被钩选的子权限,如果有,
		 *则提示用户并重新将该权限钩选上.
		 */
		if( subItems ){
			for( var i = 0 ; i < subItems.length ; i ++ ){
				if( subItems[i].getChecked()){
					alert("下级权限处于被选择状态,上级权限不能取消选择!");
					selItem.setChecked("true");
					return ;			
				}
			}
		}
	}
	
	if( selItem.getChecked() ){
		/**
		 *如果一个权限被选中,则自动的将该权限的所有上级权限都选中
		 */
		var parentItem = selItem.getParentItem() ;
		while( parentItem ){
			parentItem.setChecked( true ) ;
			parentItem = parentItem.getParentItem() ;
		}
	}
	
	regionType.value = "999";
	regionTreeView.loadByXML("<items/>");
	organizationTreeView.loadByXML("<items/>");
}

function StaffPrivilege(){
	this.staffPrivilegeId;
	this.partyRoleId;
	this.privilegeId;
	this.privilegeName;
	this.state;
	this.effDate;
	this.expDate;
}

//响应Organization树表上记录的check  box 的点击事件
function organizationChecked(){
	var selItem = organizationTreeView.selectedItem;
	if( selItem == null ){
		return ;
	}		
	/*var parentItem = getOrganizationItemById( selItem.parentPartyId ) ;
	if( parentItem != null ){
		uncheckParentItem( selItem ) ;//取消所有上级节点的选中状态
	}*/
	var parentItem = selItem.getParentItem() ;
	while( parentItem ){
		parentItem.setChecked( false ) ;
		parentItem = parentItem.getParentItem();
	}
	if( selItem.getChecked()){
		uncheckChildren( selItem ) ;//取消所有下级节点的选中状态
	}
}

//响应Region树表上记录的check box的点击事件
function regionChecked(){
	var selItem = regionTreeView.selectedItem;
	if( selItem == null ){
		return ;
	}
	/*var parentItem = getRegionItemById( selItem.parentRegionId ) ;
	if( parentItem != null ){
		uncheckParentItem( selItem ) ;//取消所有上级节点的选中状态
	}*/
	var parentItem = selItem.getParentItem() ;
	while( parentItem ){
		parentItem.setChecked( false ) ;
		parentItem = parentItem.getParentItem();
	}
	if( selItem.getChecked()){
		uncheckChildren( selItem ) ;//取消所有下级节点的选中状态
	}
}

//递归取消所有的子节点的选中状态
function uncheckChildren( item ){
	if( item.items != null ){
		for( var i = 0 ; i < item.items.length ; i ++ ){
			var child = item.items[i] ;
			child.setChecked( false ) ;
			uncheckChildren( child ) ;
		}
	}
}

//递归取消所有的上级节点的选中状态
function uncheckParentItem( item ){
	var parentItem = null ;
	if( item.regionId ){
		parentItem = getRegionItemById( item.parentRegionId ) ;
	}else if( item.partyId ){
		parentItem = getOrganizationItemById( item.parentPartyId);
	}
	if( parentItem != null ){
		parentItem.setChecked( false ) ;
		uncheckParentItem( parentItem ) ;
	}
}

function getOrganizationItemById( partyId ){
	for( var i = 0; i < organizationTreeView.items.length; i ++ ){
		var item = organizationTreeView.items[i] ;
		if( item.partyId == partyId ){
			return item ;
		}
	}
}

function getRegionItemById( regionId ){
	for( var i = 0; i < regionTreeView.items.length; i ++ ){
		var item = regionTreeView.items[i] ;
		if( item.regionId == regionId ){
			return item ;
		}
	}
}
