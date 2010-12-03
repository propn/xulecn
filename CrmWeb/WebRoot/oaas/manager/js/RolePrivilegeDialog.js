function page_onLoad(){
  initRolePrivilegeDialog();
}

//初始化TreeList组件的数据显示
function initRolePrivilegeDialog(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.rolePrivilegeTreeView.loadByXML(queryResult);
		document.all.rolePrivilegeTreeView.showCheck = "true";		
	}
	
//	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",["-1"],callBack);
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListWithRegionRelatByParentId",["-1"],callBack);//对于权限的管理，登陆员工只能管理其拥有的权限
}

function btn_OK_onClick(){
	var privilegeTree = document.all.rolePrivilegeTreeView;
	
	var arr = privilegeTree.checkedItems;
	if(arr.length>0){
		var saveList = new Array() ;
		var count = 0 ;
		for( var i = 0; i < arr.length ; i ++ ){
			var checkedPrivId = arr[i].privId ;//用户选择的权限ID
			var selectedIds = window.dialogArguments[0] ;//已经分配给角色的权限ID
			var newPriv = true ;//判断用户选择的权限是否是已经分配给角色的权限
			for( var j = 0; j < selectedIds.length; j ++ ){
				if( selectedIds[j] == checkedPrivId ){
					newPriv = false ;//是已经分配过的权限
				}
			}
			if( newPriv ){//如果不是已经分配过的权限,则...
				saveList[count] = arr[i].privId ;
				count ++ ;
			}			
		}
		
		if( saveList.length > 0 ){
			//调用服务器端服务，增加岗位和权限的对应关系
			var result = null ;
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				alert("分配权限成功！");
				window.returnValue = 1;
				window.close() ;
			}
			var arr = new Array();
			var currentRoleId = window.dialogArguments[1] ;
			arr[0] = currentRoleId ;//当前角色ID
			arr[1] = saveList;//权限ID数组
			arr[2] = "0";//类型为0表示权限,为1表示关联角色
			ajaxCall.remoteCall("PrivilegeService","insertRolePriv",arr,callBack);
		}else{
			alert("您选择的权限已经分配给角色!");
			window.returnValue = -1 ;
			window.close();
		}
	} else {
		alert("您没有选择权限，请选择最少一个权限或者点击“取消”按钮返回！");
		return ;
	}
}

function btn_Cancel_onClick(){
	returnValue = null ;
	window.close();
}

//响应记录上的check box的点击事件
function itemChecked(){
	clickPrivilege();
	
	/*
	 *选择权限的规则:选择一个下级权限,则该权限的所有上级权限都自动被选;如果一个权限的下级权限
	 *已经被选,则该权限为必选,不能通过任何方式取消该节点被选择状态.
	 */
	var selItem = rolePrivilegeTreeView.selectedItem;
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
}

/*
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
	parentItem = getItemById( item.parentPrgId ) ;
	if( parentItem != null ){
		parentItem.setChecked( false ) ;
		uncheckParentItem( parentItem ) ;
	}
}

function getItemById( id ){
	for( var i = 0; i < rolePrivilegeTreeView.items.length; i ++ ){
		var item = rolePrivilegeTreeView.items[i] ;
		if( item.privId == id ){
			return item ;
		}
	}
}
*/

function clickPrivilege(){
	var selItem = rolePrivilegeTreeView.selectedItem ;
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			
			/*if( selItem.getChecked() == true ){//如果当前节点是被选中的权限,则它的所有子权限也被选中.
				for( var i = 0 ; i < selItem.items.length ; i ++ ){
					selItem.items[i].setChecked( true ) ;
				}
			}*/
		}
	}
	
	var privId = selItem.privId ;
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",[privId], callBack);
}