function page_onLoad(){
  initRolePrivilegeDialog();
}

//��ʼ��TreeList�����������ʾ
function initRolePrivilegeDialog(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.rolePrivilegeTreeView.loadByXML(queryResult);
		document.all.rolePrivilegeTreeView.showCheck = "true";		
	}
	
//	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",["-1"],callBack);
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListWithRegionRelatByParentId",["-1"],callBack);//����Ȩ�޵Ĺ�����½Ա��ֻ�ܹ�����ӵ�е�Ȩ��
}

function btn_OK_onClick(){
	var privilegeTree = document.all.rolePrivilegeTreeView;
	
	var arr = privilegeTree.checkedItems;
	if(arr.length>0){
		var saveList = new Array() ;
		var count = 0 ;
		for( var i = 0; i < arr.length ; i ++ ){
			var checkedPrivId = arr[i].privId ;//�û�ѡ���Ȩ��ID
			var selectedIds = window.dialogArguments[0] ;//�Ѿ��������ɫ��Ȩ��ID
			var newPriv = true ;//�ж��û�ѡ���Ȩ���Ƿ����Ѿ��������ɫ��Ȩ��
			for( var j = 0; j < selectedIds.length; j ++ ){
				if( selectedIds[j] == checkedPrivId ){
					newPriv = false ;//���Ѿ��������Ȩ��
				}
			}
			if( newPriv ){//��������Ѿ��������Ȩ��,��...
				saveList[count] = arr[i].privId ;
				count ++ ;
			}			
		}
		
		if( saveList.length > 0 ){
			//���÷������˷������Ӹ�λ��Ȩ�޵Ķ�Ӧ��ϵ
			var result = null ;
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				alert("����Ȩ�޳ɹ���");
				window.returnValue = 1;
				window.close() ;
			}
			var arr = new Array();
			var currentRoleId = window.dialogArguments[1] ;
			arr[0] = currentRoleId ;//��ǰ��ɫID
			arr[1] = saveList;//Ȩ��ID����
			arr[2] = "0";//����Ϊ0��ʾȨ��,Ϊ1��ʾ������ɫ
			ajaxCall.remoteCall("PrivilegeService","insertRolePriv",arr,callBack);
		}else{
			alert("��ѡ���Ȩ���Ѿ��������ɫ!");
			window.returnValue = -1 ;
			window.close();
		}
	} else {
		alert("��û��ѡ��Ȩ�ޣ���ѡ������һ��Ȩ�޻��ߵ����ȡ������ť���أ�");
		return ;
	}
}

function btn_Cancel_onClick(){
	returnValue = null ;
	window.close();
}

//��Ӧ��¼�ϵ�check box�ĵ���¼�
function itemChecked(){
	clickPrivilege();
	
	/*
	 *ѡ��Ȩ�޵Ĺ���:ѡ��һ���¼�Ȩ��,���Ȩ�޵������ϼ�Ȩ�޶��Զ���ѡ;���һ��Ȩ�޵��¼�Ȩ��
	 *�Ѿ���ѡ,���Ȩ��Ϊ��ѡ,����ͨ���κη�ʽȡ���ýڵ㱻ѡ��״̬.
	 */
	var selItem = rolePrivilegeTreeView.selectedItem;
	var subItems = selItem.items ;
	if( selItem.getChecked() == false ){
		/*���ȡ����ѡһ��Ȩ��,�жϸ�Ȩ���Ƿ��������һ������ѡ����Ȩ��,�����,
		 *����ʾ�û������½���Ȩ�޹�ѡ��.
		 */
		if( subItems ){
			for( var i = 0 ; i < subItems.length ; i ++ ){
				if( subItems[i].getChecked()){
					alert("�¼�Ȩ�޴��ڱ�ѡ��״̬,�ϼ�Ȩ�޲���ȡ��ѡ��!");
					selItem.setChecked("true");
					return ;			
				}
			}
		}
	}
	
	if( selItem.getChecked() ){
		/**
		 *���һ��Ȩ�ޱ�ѡ��,���Զ��Ľ���Ȩ�޵������ϼ�Ȩ�޶�ѡ��
		 */
		var parentItem = selItem.getParentItem() ;
		while( parentItem ){
			parentItem.setChecked( true ) ;
			parentItem = parentItem.getParentItem() ;
		}
	}
}

/*
//�ݹ�ȡ�����е��ӽڵ��ѡ��״̬
function uncheckChildren( item ){
	if( item.items != null ){
		for( var i = 0 ; i < item.items.length ; i ++ ){
			var child = item.items[i] ;
			child.setChecked( false ) ;
			uncheckChildren( child ) ;
		}
	}
}
//�ݹ�ȡ�����е��ϼ��ڵ��ѡ��״̬
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
			
			/*if( selItem.getChecked() == true ){//�����ǰ�ڵ��Ǳ�ѡ�е�Ȩ��,������������Ȩ��Ҳ��ѡ��.
				for( var i = 0 ; i < selItem.items.length ; i ++ ){
					selItem.items[i].setChecked( true ) ;
				}
			}*/
		}
	}
	
	var privId = selItem.privId ;
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",[privId], callBack);
}