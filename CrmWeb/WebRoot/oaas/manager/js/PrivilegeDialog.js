function page_onLoad(){
	initPrivilegeDialog();
}

//初始化TreeList组件的数据显示
function initPrivilegeDialog(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.privilegeTreeView.loadByXML(queryResult);
	}
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",["-1"],callBack);
}

function btn_AddPrivilege_onClick(){
	var parentId = privilegeTreeView.selectedItem.privId ;
	var returnValue=window.showModalDialog("AddPrivilegeDialog.jsp",parentId,"dialogHeight: 208pt; dialogWidth: 400pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		initPrivilegeDialog();
	}
}

function getItemById( privilegeId ){
	for( var i = 0 ; i < privilegeTreeView.items.length; i ++){
		var item = privilegeTreeView.items[i] ;
		if( item.privId == privilegeId ){
			return item ;
		}
	}
}

function btn_OK_onClick(){
	var arguments = window.dialogArguments;
	var privilegeTree = document.all.privilegeTreeView;
	var selItem = privilegeTree.selectedItem;
	var ret = new PrivilegeObj();
	ret.privilegeId = selItem.privId;
	ret.privilegeName = selItem.privName;
	window.returnValue = ret ;
	window.close();
}

function PrivilegeObj(){
	this.privilegeId = "";
	this.privilegeName = "";
}

function btn_Cancel_onClick(){
	returnValue = null ;
	window.close();
}

function clickPrivilege(){
	var selItem = privilegeTreeView.selectedItem ;
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
	
	var privId = selItem.privId ;
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",[privId], callBack);
}