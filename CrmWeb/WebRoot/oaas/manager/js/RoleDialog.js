function confirm_onClick(){
	var roleId = window.dialogArguments[0] ;
	var selectedRoleIds = window.dialogArguments[1];
	for( var i = 0; i < selectedRoleIds.length; i ++ ){
		if( selectedRoleIds[i] == roleList.getValue("roleId")){
			alert("��ѡ��Ľ�ɫ�Ѿ�����������ǰ�Ľ�ɫ����,������ѡ��!");
			return;
		}
	}
	if( roleList.getValue("roleId") == roleId ){
		alert("��ɫ���ܺ��������!");
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