var menuCode = "";
function page_onLoad(){
	menuCode = window.dialogArguments[2] ; 
	dateInfor.setValue("expDate","2029-12-31");
	initStaffRoleDialog(); 
}
//初始化对话框信息
function initStaffRoleDialog(){
	initRoles();
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
		var rootItems = regionTreeView.rootItems ;
		for( var i = 0 ; i < rootItems.length ; i ++ ){
			rootItems[i].removeChildren() ;
		}
	}else{
		divOrganizationTree.style.display = "block" ;
		divRegionTree.style.display = "none" ;
	}
}

//表格的点击事件，在详细面板中显示当前被点中的记录的详细信息
function clickRegion(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	//获取当前节点下的所有下级节点.
	downloadSubItems() ;
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
		selItem.expand( true ) ;
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
	ajaxCall.remoteCall("PartyService","getPrivilegeOrganization",[menuCode],callBack);	
//	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}

//区域类型下拉框的onChange响应事件
function changeRegion(){
	initRegion() ;
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
	
	var regionId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
}

//初始化角色列表
function initRoles(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		roleTreeView.loadByXML(queryResult );
	}
	ajaxCall.remoteCall("PartyService","getCurrentStaffRoles",[],callBack);
}

function commit_onClick(){
	if( !$("dateInforForm").validate()){
		return ;
	}
	if( roleTreeView.checkedItems.length == 0 ){
		alert("请选择角色!");
		return ;
	}
	
	var result = null ;
	var ajaxCall = new NDAjaxCall(true);
	var callBack=function(reply){
		result = reply.getResult();
		if( result ){
			alert("操作成功!");
			window.returnValue = 0;
		}
		window.close();		
	}
	
	var regionArray = new Array();
	if( regionType.value != "3" ){
		var items = regionTreeView.checkedItems;
		if( items.length == 0 ){
			alert("请选择区域!");
			return ;
		}
		for( var i = 0; i < items.length ; i ++ ){
			regionArray[i] = items[i].regionId ;
		}
	}else{
		var items = organizationTreeView.checkedItems;
		if( items.length == 0 ){
			alert("请选择区域!");
			return ;
		}
		for( var i = 0; i < items.length ; i ++ ){
			regionArray[i] = items[i].partyId ;
		}	
	}

	var dataset = window.dialogArguments[1];
	var saveList = new Array();
	var count = 0 ;
	var checkRoles = roleTreeView.checkedItems;
	for( var j = 0 ; j < checkRoles.length ; j ++ ){
		for( var i = 0 ; i < regionArray.length ; i ++ ){
			var record = dataset.getFirstRecord(); 
			var newRecord = true ;
			while( record ) {
				if( checkRoles[j].roleId == record.getValue("roleId") &&		//如果钩选的角色ID相等,并且
					regionType.value == record.getValue("regionType") &&	//区域类型也限定,并且
					regionArray[i] == record.getValue("regionId")){				//区域ID也相等,则不需要增加该记录
					newRecord = false ;
					record = record.getNextRecord();
					continue ;	
				}
				record = record.getNextRecord() ;
			}
		}
		if( newRecord ) {
			var saveObj = new Object() ;
			saveObj.partyRoleId = window.dialogArguments[0] ;//参与人角色标识
			saveObj.roleId = checkRoles[j].roleId ;//角色ID
			saveObj.regionType = regionType.value ;//区域类型
			saveObj.regionIds = regionArray ;
			saveObj['_BUFFALO_OBJECT_CLASS_']='com.ztesoft.oaas.vo.SimpleStaffRoleVO' ;
			saveList[count] = saveObj ;
			count ++ ;
		}
	}
	
	if( saveList.length == 0 ){
		window.returnValue = -1 ;
		window.close() ;
	}
	var effDate = dateInfor.getValue("effDate");
	effDate = effDate + " 00:00:00" ;
	var expDate = dateInfor.getValue("expDate");
	expDate = expDate + " 23:59:59";
	ajaxCall.remoteCall("PartyService","insertBatchStaffRoleAndScopes",[saveList,effDate,expDate],callBack);
}

function cancel_onClick(){
	window.returnValue = -1 ;
	window.close() ;
}

function StaffRole(){
	this.staffRoleId;
	this.partyRoleId;
	this.roleId;
	this.roleName;
	this.state;
	this.effDate;
	this.expDate;
	this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.StaffRoleVO' ;
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
//响应记录上的check box的点击事件
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

function btnTest_onClick(){
	var ds = window.dialogArguments[1];
	alert("dataset value :" + ds.getFirstRecord().getValue(1));
}