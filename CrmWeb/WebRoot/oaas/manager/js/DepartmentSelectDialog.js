function page_onLoad(){
	initOrganization();
}

function initOrganization(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		organizationTreeView.loadByXML(queryResult );
		
		var rootItems = organizationTreeView.items ;
		if( rootItems ){
			for( var i = 0; i < rootItems.length ; i ++ ){
				var item = rootItems[i] ;
				if( item.orgTypeId == "5" ){
					//item.setBgColor( "yellow" ) ;
					item.setFontColor( "#3399FF" ) ;
				}
			}
		}
	}
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}

function downloadSubItems(){
	var selItem = organizationTreeView.selectedItem ;
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( false ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			
			if( selItem.items ){ 
				for( var i = 0; i < selItem.items.length ; i ++ ){
					var item = selItem.items[i] ; 
					if( item.orgTypeId == "5" ){
						item.setFontColor( "#3399FF" ) ; 
					}
				}
			}
			
		}
	}
	
	var regionId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
}

function clickOrganization(){
	downloadSubItems() ;
}

function confirm_onClick(){
	var selItem = organizationTreeView.selectedItem ;
	if( selItem.orgTypeId != "5" ){
		alert("��ѡ�����֯���ǲ���,������ѡ��!");
		return ;
	}
	if( selItem.state != "00A" ){
		alert("��ѡ��Ĳ��ŵ�״̬������Ч״̬,������ѡ��!");
		return ;
	}
	var returnArray = new Array() ;
	returnArray[0] = selItem.partyId ;
	returnArray[1] = selItem.orgName ;
	
	window.returnValue = returnArray ;
	window.close() ;
}

function cancel_onClick(){
	window.returnValue = null ;
	window.close();
}