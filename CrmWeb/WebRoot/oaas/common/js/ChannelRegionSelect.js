/**
 * ����Ƭ��ѡ�����,����:
 * @param channelRegionType : 004 ��ʾӪ��ְ��Ƭ��,005��ʾά��ְ��Ƭ��,001��ʾְ��Ƭ��(����Ӫ��ְ�ܺ�
 * ά��ְ��), 000 ��ʾ����ְ��
 * @param selectType : 1 ��ʾ��ѡ, 2 ��ʾ��ѡ
 * @param staffCond : 1 ��ʾ�Ƿ�������Ϊ��ѯ����, 0��ʾ����Ҫʹ�ø�������Ϊ��ѯ����;���ʹ�ø���
 * ����Ϊ��ѯ����,���ѯ����������Ƭ���Ǹ����������������Ƭ��,������ȫ��.
 */

var channelRegionType = "" ;	//����Ƭ������
var staffCond = "" ;	//�Ƿ�������Ƭ����������Ϊ��ѯ����
var selectType = "" ;	//��ѡ/��ѡ��־
var checkChildren = "";
var custType = "";//����ͻ�����
var businessId = "";//�ֹ�˾ID
var oldIds = "";
var pathCode = "";

//��ʼ��TreeList�����������ʾ
function page_onLoad(){
	var paramObj = window.dialogArguments;

	if( paramObj == null ){
		channelRegionType = "001" ;
		staffCond = "0" ;
		selectType = "1" ;
		checkChildren = "1" ;
		custType = "-1";
		businessId = "-1" ;
		oldIds = "";
	}else{
		
		channelRegionType = paramObj["channelRegionType"] ;
		staffCond = paramObj["staffCond"];
		selectType = paramObj["selectType"];
		checkChildren = paramObj["checkChildren"];
		custType = paramObj["custType"] ;
		businessId = paramObj["businessId"];
		oldIds = paramObj["oldIds"];

		if( oldIds == null ){
			oldIds = "" ;//Ĭ��Ϊ��
		}
		
		if( custType == "" || custType == null || custType == "undefined" ){
			custType = "-1" ;//����ͻ�����
		}
		if( businessId == "" || businessId == null || businessId == "undefined" ){
			businessId = "-1" ;//����ͻ�����
		}
				
		if( channelRegionType == "" || channelRegionType == null || channelRegionType == "undefined" ){
			channelRegionType = "001" ;//û��������������
		}
		if( staffCond == "" || staffCond == null || staffCond == "undefined" ){
			staffCond = "0" ;//��ʹ�ø�������Ϊ��ѯ����
		}
		if( selectType == "" || selectType == null || selectType == "undefined" ){
			selectType = "1" ;//Ĭ��Ϊ��ѡ
		}
		if( checkChildren == "" || checkChildren == null || checkChildren == "undefined" ){
			checkChildren = "1" ;//Ĭ���Զ���ѡ�¼�
		}
		if( oldIds != "" ){
			var arr = oldIds.split(",") ;
			pathCode = getPathCode( arr[0] ) ;
		}
	}

 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		chnRegionTreeView.loadByXML(queryResult);
		if( chnRegionTreeView.items == null || chnRegionTreeView.items.length == 0 ){
        	alert("��û���κ�����Ȩ��!");
        }
        for( var i = 0 ; i < chnRegionTreeView.items.length ; i ++ ){
        	var arr = oldIds.split( "." ) ;
        	for( var j = 0 ; j < arr.length ; j ++ ){
        		if( arr[j] == chnRegionTreeView.items[i].chnRegionId ){
        			chnRegionTreeView.items[i].setChecked( true ) ;
        		}
        	}
        }
        if( pathCode != "" ){
        	var arr = pathCode.split(".");
        	for( var i = 0 ; i < chnRegionTreeView.items.length ; i ++ ){
        		for( var j = 0 ; j < arr.length ; j ++ ){ 
	        		if( chnRegionTreeView.items[i].chnRegionId == arr[j] ) { 
						getCheckedItem(chnRegionTreeView.items[i]) ;
					}
				}
			}
		}
	}
	if( staffCond == "0" ){
		ajaxCall.remoteCall("ChmService","getChnRegionsByParentId",["-1", channelRegionType, staffCond, "-1", "-1", "00A" ],callBack);
	}else {
		ajaxCall.remoteCall("ChmService","getChnRegionsByParentId",["", channelRegionType, staffCond, "-1", "-1", "00A" ],callBack);
	}
}
function getCheckedItem( item ){ 
	clickChnRegion( item ) ;

	var subItems = item.items; 
	if( subItems ){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			var arr = pathCode.split("."); 
			for( var j = 0 ; j < arr.length ; j ++ ){
				if( arr[j] == subItems[i].chnRegionId ){
					getCheckedItem( subItems[i] );
				}
			}
		}
	}else{
		//alert("subItems is null");
	}
}
function getPathCode( orgId ) {
	var ajaxCall = new NDAjaxCall( false ) ;
	var returnValue = "";
	var callBack = function( reply ) {
		returnValue = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getPathCode",[orgId,"3"],callBack);
	return returnValue ;
}
function clickChnRegion(item){
	var selItem = null;
	if( item != null ){
		selItem = item ;
	}else{
		selItem = chnRegionTreeView.selectedItem ;
	}
	if(selItem == null ) return;
	if( selItem.items ) {
		return ;
	}
	var chnRegionId = selItem.chnRegionId ;
	
	var ajaxCall = new NDAjaxCall( false ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			
			for( var i = 0 ; i < selItem.items.length ; i ++ ){
	        	var arr = oldIds.split( "." ) ;
	        	for( var j = 0 ; j < arr.length ; j ++ ){
	        		if( arr[j] == selItem.items[i].chnRegionId ){
	        			selItem.items[i].setChecked( true ) ;
	        		}
	        	}
	        }
		}
	}
	ajaxCall.remoteCall("ChmService","getChnRegionsByParentId",[chnRegionId, channelRegionType, "0", custType, businessId, "00A" ],callBack);
}

function checkChnRegion(){
	if( chnRegionTreeView.items == null || chnRegionTreeView.items.length == 0 ){
		return ;
	}
	clickChnRegion() ;
	
	var selItem = chnRegionTreeView.selectedItem ;
	
	if( selItem.chnRegionType != channelRegionType && selItem.getChecked() ){
		//����Ƭ������,004 ��ʾӪ��ְ��Ƭ��,005��ʾά��ְ��Ƭ��,001��ʾְ��Ƭ
		//��(����Ӫ��ְ�ܺ�ά��ְ��),000��ʾ����Ƭ��
		if( channelRegionType == "004" ){
			alert("ֻ��ѡ��Ӫ��ְ��Ƭ��!");
			selItem.setChecked( false ) ;
			return ;
		}else if( channelRegionType == "005" ){
			alert("ֻ��ѡ��ά��ְ��Ƭ��!");
			selItem.setChecked( false ) ;
			return ;
		}else if( channelRegionType == "001" && selItem.chnRegionType == "000"){
			alert("ֻ��ѡ��Ӫ��ְ��Ƭ������ά��ְ��Ƭ��!");
			selItem.setChecked( false ) ;
			return ;
		}else if( channelRegionType == "000" ){
			alert("ֻ��ѡ�����Ƭ��!");
			selItem.setChecked( false ) ;
			return ;
		}
	}
	var subItems = selItem.items ;
	if( subItems && selectType != "1" && checkChildren == "1"){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			if( subItems[i].chnRegionType == channelRegionType ) { 
				subItems[i].setChecked( true ) ;
			}
		}
	}
	//******************************************************************************
	
	/*var subItems = selItem.items ;
	if( !subItems ){//�����ǰ�ڵ�û���ӽڵ�
		if( selItem.chnRegionType == "000" && channelRegionType != "000" ){//�����ǰ�ڵ��ǹ�������,�򷵻�
			alert("����ѡ��Ƭ������Ϊ����ļ�¼!");
			selItem.setChecked( false ) ;
		}
		return ;
	}
	
	//�����ǰ�ڵ����ӽڵ�,��˵����ǰ�ڵ�Ϊ����ڵ�,����ѡ��.���ǿ���ѡ���ӽڵ�.
	//���ѡ������Ϊ��ѡ,���Զ�Ϊ�����¼��ڵ�����Ϊ����Ҫ��Ľڵ㹳��
	if( selectType != "1" && checkChildren == "1"){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			//if( subItems[i].chnRegionType != "000" ){
			if( subItems[i].chnRegionType == channelRegionType ) {
				subItems[i].setChecked( true ) ;
			}
		}
	}else{
		if( selItem.chnRegionType == "000" && channelRegionType != "000"){ 
			alert("����ѡ��Ƭ������Ϊ����ļ�¼!");
			selItem.setChecked( false ) ;
		}
	}*/
}

function btnConfirm_onClick(){
	var returnList = new Array() ;
	var count = 0 ;
	
	var checkedItems = chnRegionTreeView.checkedItems ;
	for( var i = 0 ; i < checkedItems.length ; i ++ ){
		var obj = new Object() ;
		obj.chnRegionId = checkedItems[i].chnRegionId;
		obj.name = checkedItems[i].name;
		obj.chnRegionCode = checkedItems[i].chnRegionCode;
		obj.custType = checkedItems[i].custType ;
		obj.chnRegionLevel = checkedItems[i].chnRegionLevel ;
		obj.staffId = checkedItems[i].staffId ;
		returnList[count] = obj ;
		count ++ ;
	}
	if( returnList.length == 0 ){
		alert("��ѡ���¼,���ߵ��ȡ����ť����!");
		return ;
	}else{
		if( returnList.length > 1 && selectType == "1" ){
			alert("ֻ��ѡ��һ����¼!");
			return ;
		}
		window.returnValue = returnList ;
		window.close();
	}
}

function btnCancel_onClick(){
	window.returnValue = null ;
	window.close();
}