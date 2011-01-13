var menuCode = "";
function page_onLoad(){
	menuCode = window.dialogArguments[2];
	initOrgRoleDialog();
}

//初始化对话框信息
function initOrgRoleDialog(){
	if( window.dialogArguments[0] == "update" ){
		initRegion();
	} 
	initRoles(); 
}

//根据区域类型初始化TreeList组件的数据显示
/*function initRegion(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
	}
	
	if( regionType.value == "999" ){
		return ;
	}else if( regionType.value == "0"){
		ajaxCall.remoteCall("RegionService","getBillRegionList",["-1"],callBack);
	}else if( regionType.value == "1" ){
		ajaxCall.remoteCall("RegionService","getResourceRegionList",["-1"],callBack);
	}else if( regionType.value == "2" ){
		ajaxCall.remoteCall("RegionService","getSaleRegionList",["-1"],callBack);
	}else if( regionType.value == "3" ){
		initOrganization();
	}
	
	if( regionType.value != "3" ){
		divOrganizationTree.style.display = "none" ;
		divRegionTree.style.display = "block" ;
		document.all.regionTreeView.loadByXML(queryResult );
		
		//如果是编辑,则将已经拥有的控制区域默认选上
		if( window.dialogArguments[0] == "update" ){
			for( var i = 0 ; i < oldIds.length ; i ++ ){
				for( var j = 0; j < regionTreeView.items.length; j ++ ){
					if( regionTreeView.items[j].regionId == oldIds[i] ){
						regionTreeView.items[j].setChecked("true") ;
					}
				}
			}
		}
	}else{
		divOrganizationTree.style.display = "block" ;
		divRegionTree.style.display = "none" ;
		//如果是编辑,则将已经拥有的控制区域默认选上
		if( window.dialogArguments[0] == "update" ){
			for( var i = 0 ; i < oldIds.length ; i ++ ){
				for( var j = 0; j < organizationTreeView.items.length; j ++ ){
					if( organizationTreeView.items[j].partyId == oldIds[i] ){
						organizationTreeView.items[j].setChecked("true") ;
					}
				}
			}
		}	
	}
}*/
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
	
	var orgId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[orgId], callBack);
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
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
		}
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
//区域类型下拉框的onChange响应事件
function changeRegion(){
	initRegion() ;
}

/*
function getOrgPostRegionIds(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
	}
	var arr = new Array();
	arr[0] = window.dialogArguments[1] ;//组织岗位角色Id
	ajaxCall.remoteCall("PartyService","getOrgPostRoleScopesByOrgPostRoleId",arr,callBack); 
	if( queryResult["resultCode"] == 0 ){
		return queryResult["resultObject"];
	}else{
		alert( queryResult["resultMessage"]);
	}
}
*/

/*function initOrganization(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		organizationTreeView.loadByXML( queryResult);
	}
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}*/
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

//初始化角色列表
function initRoles(){
	if( window.dialogArguments[0] == "update" ){
		divRoleTree.innerHTML = "";
		regionTreeView.height = "100%";
		return ;
	}

 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(false);

	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		roleTreeView.loadByXML(queryResult );
	}

	//ajaxCall.remoteCall("PrivilegeService","getXMLRoleList",[],callBack);
	ajaxCall.remoteCall("PartyService","getCurrentStaffRoles",[],callBack);//登陆员工只能管理其拥有的角色
}

function commit_onClick(){ 
	var result = null ;
	var ajaxCall = new NDAjaxCall(false);
	var callBack=function(reply){
		result = reply.getResult();
			alert("操作成功!");
			window.returnValue = 0;
		window.close();
	}
	
	var regionArray = new Array();
	var items;
	if(regionType.value != "3" ){
		items = regionTreeView.checkedItems;
		if( items.length == 0 ){
			alert("请选择区域或者点击取消按钮返回!");
			return ;
		}
		for( var i = 0; i < items.length ; i ++ ){
			regionArray[i] = items[i].regionId ;
		}
	}else{
		items = organizationTreeView.checkedItems;
		if( items.length == 0 ){
			alert("请选择组织或者点击取消按钮返回!");
			return ;
		}		
		for( var i = 0; i < items.length ; i ++ ){
			regionArray[i] = items[i].partyId ;
		}
	}
	

	var arr = new Array();
	if( window.dialogArguments[0] == "insert" ){
		arr[0] = window.dialogArguments[1];
		arr[1] = roleTreeView.selectedItem.roleId ;
		arr[2] = regionType.value ;
		arr[3] = regionArray;
		ajaxCall.remoteCall("PartyService","insertOrgRoleAndScopes",arr,callBack);
	}else if( window.dialogArguments[0] = "update" ){
		arr[0] = window.dialogArguments[1];
		arr[1] = regionType.value ;
		arr[2] = regionArray;
		ajaxCall.remoteCall("PartyService","updateOrgRoleAndScopes",arr,callBack);
	}
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
}
//响应Organization树表上记录的check  box 的点击事件
function organizationChecked(){
	var selItem = organizationTreeView.selectedItem;
	if( selItem == null ){
		return ;
	}	
	var parentItem = getOrganizationItemById( selItem.parentPartyId ) ;
	if( parentItem != null ){
		uncheckParentItem( selItem ) ;//取消所有上级节点的选中状态
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
	var parentItem = getRegionItemById( selItem.parentRegionId ) ;
	if( parentItem != null ){
		uncheckParentItem( selItem ) ;//取消所有上级节点的选中状态
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