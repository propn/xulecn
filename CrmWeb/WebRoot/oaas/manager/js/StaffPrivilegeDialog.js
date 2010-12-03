var menuCode = "";
function page_onLoad(){
	menuCode = window.dialogArguments[0];
	dateInfor.setValue("expDate","2029-12-31");
	initStaffPrivilegeDialog();
}

function initStaffPrivilegeDialog(){
	initPrivileges();
}

//�����������ͳ�ʼ��TreeList�����������ʾ
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


//���������������onChange��Ӧ�¼�
function changeRegion(){
	var ifRegionRela = false ;
	var selItems = privilegeTreeView.checkedItems ;
	if( selItems.length == 0 ){
		alert("����ѡ��Ȩ��!");
		regionType.value = "999" ;
		return ;
	}
	
	for( var i = 0; i < selItems.length ; i ++){
		if( selItems[i].ifRegionRela != "F" ){
			ifRegionRela = true ;//����������ص�Ȩ��
			break ;
		}
	}
	//�������������ص�Ȩ��,�����û�û��ѡ������,����ʾ�û���Ҫѡ��
	if( ifRegionRela && regionType.value == "999" ){
		alert("��ѡ���Ȩ���д���������ص�Ȩ������,��ѡ������!");
		regionType.value = "999" ;
		return ;
	}else if( !ifRegionRela && regionType.value !="999" ){
		//����û�ѡ���Ȩ�޶��������޹ص�,�����û�ѡ��������,����ʾ�û�����ѡ������
		alert("��ǰȨ���ǵ����޹ص�,����ѡ�����!");
		regionType.value = "999" ;		
		return ;
	}

	initRegion() ;


}

//��ʼ��Ȩ���б�
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

//���ĵ���¼�������ϸ�������ʾ��ǰ�����еļ�¼����ϸ��Ϣ
function clickRegion(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	//��ȡ��ǰ�ڵ��µ������¼��ڵ�.
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

//���û�������������ʱ��,�жϵ�ǰ�ڵ��Ƿ����¼��ڵ�,���û��,
//���������˲鿴�Ƿ����¼��ڵ�,����������˷����˸ýڵ���¼�
//�ڵ�����,����Щ���ݽ�����Ϊ������¼��ڵ�,��ӵ�������.
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
		alert("��Ч���ڱ����ڽ�����߽���֮��!");
		return ;
	}
	if( dateInfor.getValue("expDate") <= currentDate ){
		alert("ʧЧ���ڱ�����ڽ���!");
		return ;
	}			
	if( dateInfor.getValue("effDate") > dateInfor.getValue("expDate")){
		alert("��Ч���ڱ���ǰ��ʧЧ����!");
		return;
	}
				
	var result = null ;
	var ajaxCall = new NDAjaxCall(false);
	
	var callBack=function(reply){
		result = reply.getResult();
		if( result ){
			alert("�����ɹ�!");
			window.returnValue = 0;
		}
		window.close();
	}
	
	var ifRegionRela = false ;//�û�ѡ���Ȩ�����Ƿ����������ص�Ȩ��,�����,�������ʾ�û�ѡ������.
	var selItems = privilegeTreeView.checkedItems;
	if( selItems.length == 0 ) {
		alert("��ѡ��Ȩ��!");
		return ;
	}
	for( var i = 0 ; i < selItems.length ; i ++ ){
		if( selItems[i].privCode == "DEFPRV" ){
			if( checkStaffDefaultPriv(window.dialogArguments[1]) ){
				alert("��ѡ���Ȩ���а�����Ĭ��Ȩ��,��Ȩ���Ѿ��������ǰ�Ĺ���,�����ظ�����!");
				return ;
			}
			if( regionType.value != "1" ){
				alert("��ѡ���Ȩ���а�����Ĭ��Ȩ��,��Ȩ�޵ĵ���ֻ������Դ��!");
				return ;
			}else{
				if( regionTreeView.checkedItems.length > 1 ){
					alert("��ѡ���Ȩ���а�����Ĭ��Ȩ��,��Ȩ�޵ĵ���Χ�����Ǵ������ֻ����Ψһ��!");
					return ;
				}else{
					if( regionTreeView.checkedItems[0].regionLevel != "98D" ){
						alert("��ѡ���Ȩ���а�����Ĭ��Ȩ��,��Ȩ�޵ĵ���Χ�����Ǵ����!");
						return ;
					}
				}
			}
		}
		if( selItems[i].privCode == "LANPRI" ){
			if( regionType.value != "1" ){
				alert("��ѡ���Ȩ���а����˱���������Ȩ��,��Ȩ�޵ĵ���ֻ������Դ��!");
				return ;
			}else{
				for(var j=0;j<regionTreeView.checkedItems.length;j++){
					if( regionTreeView.checkedItems[j].regionLevel != "97C" ){
						alert("��ѡ���Ȩ���а����˱���������Ȩ��,��Ȩ�޵ĵ���Χ�����Ǳ�����!");
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
 
 	//����û�ѡ��������,���¼�����ID
	if( regionType.value != "3" && regionType.value != "999" ){//ѡ������ߵ�����
		var items = regionTreeView.checkedItems;
		if( items.length == 0 && ifRegionRela ){
			alert("��ѡ������!");
			return ;
		}
		for( var i = 0; i < items.length ; i ++ ){
			regionArray[i] = items[i].regionId ;
		}
	}else{
		var items = organizationTreeView.checkedItems;
		if( items.length == 0  && ifRegionRela ){//ѡ����֯�ߵĵ���
			alert("��ѡ������!");
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
        if( item.ifRegionRela == "F" ){//��������޹� ,�򲻹��û��Ƿ�ѡ��������,������regionType=-1 , regionId = -1
            var saveObj = new Object() ; 
            saveObj.partyRoleId = window.dialogArguments[1] ;//�����˽�ɫ��ʶ
            saveObj.privId = item.privId ;//Ȩ��ID 
//            saveObj.regionType = "-1";//�����޹�,��������Ϊ-1
            saveObj.regionType = "9";//�����޹�,��������Ϊ9(���汾��ʹ��9��ʾ�����޹أ�������汾�У���ʱ����-1��ʾ�����޹�)
            saveObj.regionId = "-1";//�����޹�,��������Ϊ-1
            saveObj.effDate = dateInfor.getValue("effDate");
            saveObj.effDate = saveObj.effDate + " 00:00:00" ;
            saveObj.expDate = dateInfor.getValue("expDate");
            saveObj.expDate = saveObj.expDate + " 23:59:59" ;
            saveList[count] = saveObj ;
            count ++ ;
        }else{//�������
            for( var m = 0; m < regionArray.length ; m ++ ){
                var saveObj = new Object() ;
                saveObj.partyRoleId = window.dialogArguments[1];//�����˽�ɫ��ʶ
                saveObj.privId = item.privId ;//Ȩ��ID
                saveObj.regionType = regionType.value ;//��������
                saveObj.regionId = regionArray[m] ;//����ID
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
	//��Ӧ��¼�ϵ�check box�ĵ���¼�
	clickPriv();
	/*
	 *ѡ��Ȩ�޵Ĺ���:ѡ��һ���¼�Ȩ��,���Ȩ�޵������ϼ�Ȩ�޶��Զ���ѡ;���һ��Ȩ�޵��¼�Ȩ��
	 *�Ѿ���ѡ,���Ȩ��Ϊ��ѡ,����ͨ���κη�ʽȡ���ýڵ㱻ѡ��״̬.
	 */
	var selItem = privilegeTreeView.selectedItem;
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

//��ӦOrganization�����ϼ�¼��check  box �ĵ���¼�
function organizationChecked(){
	var selItem = organizationTreeView.selectedItem;
	if( selItem == null ){
		return ;
	}		
	/*var parentItem = getOrganizationItemById( selItem.parentPartyId ) ;
	if( parentItem != null ){
		uncheckParentItem( selItem ) ;//ȡ�������ϼ��ڵ��ѡ��״̬
	}*/
	var parentItem = selItem.getParentItem() ;
	while( parentItem ){
		parentItem.setChecked( false ) ;
		parentItem = parentItem.getParentItem();
	}
	if( selItem.getChecked()){
		uncheckChildren( selItem ) ;//ȡ�������¼��ڵ��ѡ��״̬
	}
}

//��ӦRegion�����ϼ�¼��check box�ĵ���¼�
function regionChecked(){
	var selItem = regionTreeView.selectedItem;
	if( selItem == null ){
		return ;
	}
	/*var parentItem = getRegionItemById( selItem.parentRegionId ) ;
	if( parentItem != null ){
		uncheckParentItem( selItem ) ;//ȡ�������ϼ��ڵ��ѡ��״̬
	}*/
	var parentItem = selItem.getParentItem() ;
	while( parentItem ){
		parentItem.setChecked( false ) ;
		parentItem = parentItem.getParentItem();
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
