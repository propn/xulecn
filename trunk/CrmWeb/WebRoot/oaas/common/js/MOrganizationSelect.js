function page_onLoad(){
	initOrganization();
}

function initOrganization(){
 	var queryResult = null ; 

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		organizationTreeView.loadByXML( queryResult);

		//根据传递进来的组织ID参数,将ID对应的记录钩选上	
		var paramObj = window.dialogArguments;
		if( paramObj != null ){
			var oldIds = new Array();
			if( paramObj["orgIds"] != null && paramObj["orgIds"] != "undefined" && paramObj["orgIds"] != "" ){
				oldIds = paramObj["orgIds"].split(",");
			
				for( var i = 0 ; i < oldIds.length ; i ++ ){
					for( var j = 0; j < organizationTreeView.items.length; j ++ ){
						if( organizationTreeView.items[j].partyId == oldIds[i] ){
							organizationTreeView.items[j].setChecked("true") ;
						}
					}
				}
			}
		}		
	}
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}

//点击组织记录的时候,根据被点击的组织ID到服务器端查询以该ID为上级ID的组织.
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
			var paramObj = window.dialogArguments;
			if( paramObj == null ){
				return; 
			}
			if( paramObj["orgIds"] != null && paramObj["orgIds"] != "undefined" && paramObj["orgIds"] != "" ){
				oldIds = paramObj["orgIds"].split(",");
				var childItems = selItem.items ;
				if( childItems ){
					for( var j = 0; j < childItems.length; j ++ ){
						for( var i = 0 ; i < oldIds.length ; i ++ ){
							if( childItems[j].partyId == oldIds[i] ){
									childItems[j].setChecked("true") ;
							}
						}
					}
				}
			}
		}
	}
	var regionId = selItem.partyId ;
	
	var parameter = window.dialogArguments;
	if( parameter == null ){//如果没有传递参数,则按照没有权限控制的形式获取下级.
		ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
	}else{
		var privType = parameter["privilegeType"] ;//权限条件类型:0-权限ID，1-权限编码，2-菜单ID，3-菜单编码；
		var privCode =  parameter["privilegeCode"];//权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
		var orgType = parameter["orgType"] ;//要选取的组织类型
		if( orgType == null || orgType == "" || orgType == "undefined" ){
			orgType = "9" ;
		}
		 
		if( privType == null || privType == "" || privCode == null || privCode == "" ){//如果没有传递权限信息,也按照没有权限控制的形式获取下级
			ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
		}else{
			if( selItem.privilegeFlag == "T" ){//当前节点是权限范围节点
				//查询组织区域当前节点下的所有节点,并为这些下级节点加上权限标志.
				ajaxCall.remoteCall("RegionService","getOrganizationRegionListWithPrivFlag",[regionId],callBack); 
			}else {
				//查询组织区域,并过滤掉没有权限的区域 
				ajaxCall.remoteCall("RegionService","getOrganizationRegionListByFilter",[regionId,privType,privCode,orgType],callBack); 	
			}
		}
	}
}

//返回用户钩选的记录,如果传递的selectType为当选,则只能选择一个记录,否则可以多选; 
//无论当选还是多选,返回的都是包含VO的数组. 
function btn_Confirm_onClick(){
	var orgArray = new Array();
	var items = organizationTreeView.checkedItems;
	var paramObj = window.dialogArguments;
	if( paramObj != null ){
		var selectType = paramObj["selectType"] ;
		if( selectType != "" && selectType != null ){
			if( paramObj["selectType"] == "1" && items.length > 1 ){
				alert("只能选择一个组织!");
				return;
			}
		}
	}
	var vo = null ;
	for( var i = 0; i < items.length ; i ++ ){
		vo = new ReturnVO();	
		vo.orgId = items[i].partyId ;
		if( paramObj != null ){
			if( paramObj["privilegeCode"] != "" && paramObj["privilegeCode"] != null 
			&& paramObj["privilegeType"] != "" && paramObj["privilegeType"] != null ){
				if( items[i].privilegeFlag !="T"){
					alert("您在组织" + items[i].orgName + "上没有权限!");
					return ;
				}
			}
		}
		vo.orgName = items[i].orgName;
		orgArray[i] = vo ;
	}
	window.returnValue = orgArray;
	window.close();
}

function btn_Cancel_onClick(){
	window.returnValue = null ;
	window.close();
}

//响应记录上的check box的点击事件
function organizationChecked(){
	var selItem = organizationTreeView.selectedItem;
	var parentItem = getOrganizationItemById( selItem.parentPartyId ) ;
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
	parentItem = getOrganizationItemById( item.parentPartyId ) ;
	if( parentItem != null ){
		parentItem.setChecked( false ) ;
		uncheckParentItem( parentItem ) ;
	}
}
function getOrganizationItemById( orgId ){
	for( var i = 0; i < organizationTreeView.items.length; i ++ ){
		var item = organizationTreeView.items[i] ;
		if( item.partyId == orgId ){
			return item ;
		}
	}
}

function ReturnVO(){
	this.orgId = "";
	this.orgName = "";
}

function Parameter(){
	this.staffCode = "";
	this.privilegeCode = "";
	this.orgType = "";
	this.orgIds = "";
}