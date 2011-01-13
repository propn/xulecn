function page_onLoad(){
	initRegion();
}
function initRegion(){
	var parameter = window.dialogArguments[0];
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(true); 
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ; 
		regionTreeView.loadByXML(queryResult );
			
		var oldIds = new Array();
		oldIds = parameter["regionIds"].split(",");
	
		for( var i = 0 ; i < oldIds.length ; i ++ ){
			for( var j = 0; j < regionTreeView.items.length; j ++ ){
				if( regionTreeView.items[j].regionId == oldIds[i] ){
					regionTreeView.items[j].setChecked("true") ;
				}
			}
		}		
	}
	
	var arr = new Array(); 
	arr[0] = "" + parameter["staffCode"] ;//����;
	arr[1] = "" +  parameter["privilegeCode"];//Ȩ�ޱ���
	arr[2] = "" +  parameter["regionLevel"]; //������򼶱�
	arr[3] = "" +  parameter["regionType"];//�����б�
	
	/**
	 *���÷ֲ����صķ�ʽʵ��.�����ǰ�Ĳ����regionLevelֻ��,������ͨ��������ر������������¼�
	 *����,�����ǰ�Ĳ���Ѿ���regionLevel��ָ����������,����Ҫ�ٴ���������������������²�ε�
	 *����.���ز�ε���regionLevel�������ʱ��,��Ҫ�ٴ���һ��Ȩ�ޱ������,�������˹��˵����������
	 *Ȩ�������Ƶ�����ļ�¼,ֻ���û���Ȩ�޵�������������.
	**/
	ajaxCall.remoteCall("RegionService","getRegionByCond", arr, callBack); 

}
function btn_Confirm_onClick(){
	var regionArray = new Array();
	var items = regionTreeView.checkedItems;
	for( var i = 0; i < items.length ; i ++ ){
		regionArray[i] = items[i].regionId ;
	}
	window.returnValue = regionArray.join(",");
	window.close();
}
function btn_Cancel_onClick(){
	window.returnValue = "" ;
	window.close();
}
function Parameter(){
	this.staffCode = "";
	this.privilegeCode = "";
	this.regionLevel = "";
	this.regionType = "";
	this.regionIds = "";
	this.checkBox = "";
}

//��Ӧ��¼�ϵ�check box�ĵ���¼�
function regionChecked(){
	var selItem = regionTreeView.selectedItem;
	var parentItem = getRegionItemById( selItem.parentRegionId ) ;
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
	parentItem = getRegionItemById( item.parentRegionId ) ;
	if( parentItem != null ){
		parentItem.setChecked( false ) ;
		uncheckParentItem( parentItem ) ;
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