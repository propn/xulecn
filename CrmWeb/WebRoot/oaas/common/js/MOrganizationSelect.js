function page_onLoad(){
	initOrganization();
}

function initOrganization(){
 	var queryResult = null ; 

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		organizationTreeView.loadByXML( queryResult);

		//���ݴ��ݽ�������֯ID����,��ID��Ӧ�ļ�¼��ѡ��	
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

//�����֯��¼��ʱ��,���ݱ��������֯ID���������˲�ѯ�Ը�IDΪ�ϼ�ID����֯.
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
	if( parameter == null ){//���û�д��ݲ���,����û��Ȩ�޿��Ƶ���ʽ��ȡ�¼�.
		ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
	}else{
		var privType = parameter["privilegeType"] ;//Ȩ����������:0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
		var privCode =  parameter["privilegeCode"];//Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
		var orgType = parameter["orgType"] ;//Ҫѡȡ����֯����
		if( orgType == null || orgType == "" || orgType == "undefined" ){
			orgType = "9" ;
		}
		 
		if( privType == null || privType == "" || privCode == null || privCode == "" ){//���û�д���Ȩ����Ϣ,Ҳ����û��Ȩ�޿��Ƶ���ʽ��ȡ�¼�
			ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
		}else{
			if( selItem.privilegeFlag == "T" ){//��ǰ�ڵ���Ȩ�޷�Χ�ڵ�
				//��ѯ��֯����ǰ�ڵ��µ����нڵ�,��Ϊ��Щ�¼��ڵ����Ȩ�ޱ�־.
				ajaxCall.remoteCall("RegionService","getOrganizationRegionListWithPrivFlag",[regionId],callBack); 
			}else {
				//��ѯ��֯����,�����˵�û��Ȩ�޵����� 
				ajaxCall.remoteCall("RegionService","getOrganizationRegionListByFilter",[regionId,privType,privCode,orgType],callBack); 	
			}
		}
	}
}

//�����û���ѡ�ļ�¼,������ݵ�selectTypeΪ��ѡ,��ֻ��ѡ��һ����¼,������Զ�ѡ; 
//���۵�ѡ���Ƕ�ѡ,���صĶ��ǰ���VO������. 
function btn_Confirm_onClick(){
	var orgArray = new Array();
	var items = organizationTreeView.checkedItems;
	var paramObj = window.dialogArguments;
	if( paramObj != null ){
		var selectType = paramObj["selectType"] ;
		if( selectType != "" && selectType != null ){
			if( paramObj["selectType"] == "1" && items.length > 1 ){
				alert("ֻ��ѡ��һ����֯!");
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
					alert("������֯" + items[i].orgName + "��û��Ȩ��!");
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

//��Ӧ��¼�ϵ�check box�ĵ���¼�
function organizationChecked(){
	var selItem = organizationTreeView.selectedItem;
	var parentItem = getOrganizationItemById( selItem.parentPartyId ) ;
	if( parentItem != null ){
		uncheckParentItem( selItem ) ;//ȡ�������ϼ��ڵ��ѡ��״̬
	}
	if( selItem.getChecked()){
		uncheckChildren( selItem ) ;//ȡ�������¼��ڵ��ѡ��״̬
	}
}

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