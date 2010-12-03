function page_onLoad(){
	initRegion();
}
	
//��ʼ��TreeList�����������ʾ
function initRegion(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		regionTreeView.loadByXML(queryResult);
	}
	ajaxCall.remoteCall("RegionService","getBillRegionList",["-1"],callBack);
}

//���ĵ���¼�������ϸ�������ʾ��ǰ�����еļ�¼����ϸ��Ϣ
function clickRegion(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	//��ȡ��ǰ�ڵ��µ������¼��ڵ�.
	downloadSubItems() ;
}

//���û�������������ʱ��,�жϵ�ǰ�ڵ��Ƿ����¼��ڵ�,���û��,
//���������˲鿴�Ƿ����¼��ڵ�,����������˷����˸ýڵ���¼�
//�ڵ�����,����Щ���ݽ�����Ϊ������¼��ڵ�,��ӵ�������.
function downloadSubItems(){
	var selItem = regionTreeView.selectedItem ; 
	if( selItem.items ){ 
		return ; 
	} 
	if( selItem.regionLevel == "97D" ){ 
		return ;
	}
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			if( selItem.items ){
				for( var i = 0; i < selItem.items.length; i ++ ){
					var item = selItem.items[i] ;
					if ( item.regionLevel == "97D" ){
						item.setBgColor( "#3399FF" ) ;
						//item.setFontColor( "red" ) ;
					}
				}
			}
		}
	}
	
	var regionId = selItem.regionId ;
	ajaxCall.remoteCall("RegionService","getBillRegionList",[regionId], callBack);
}

function btn_Confirm_onClick(){
	var selItem = regionTreeView.selectedItem ;
	if( selItem.regionLevel != "97D" ){
		alert("��ѡ���������Ӫҵ��,������ѡ��!");
		return ;
	}
	
	var returnArray = new Array() ;
	returnArray[0] = selItem.regionId ;
	returnArray[1] = selItem.regionName ;
	window.returnValue = returnArray ;
	window.close() ;
}

function btn_Cancel_onClick(){
	window.returnValue = null ;
	window.close() ;
}