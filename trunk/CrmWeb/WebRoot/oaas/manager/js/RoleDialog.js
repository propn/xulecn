function confirm_onClick(){
	var roleId = window.dialogArguments[0] ;
	var selectedRoleIds = window.dialogArguments[1];
	for( var i = 0; i < selectedRoleIds.length; i ++ ){
		if( selectedRoleIds[i] == roleList.getValue("roleId")){
			alert("您选择的角色已经被关联到当前的角色中了,请另外选择!");
			return;
		}
	}
	if( roleList.getValue("roleId") == roleId ){
		alert("角色不能和自身关联!");
		return ;
	}else{
		window.returnValue = roleList.getValue("roleId");
		window.close();
	}
}

function cancel_onClick(){
	window.returnValue = null ;
	window.close();
}