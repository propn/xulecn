/**
 * ��Դ����ѡ�����,������������:
 * @param staffCode :Ա������,Ԥ������,���贫��
 * @param privilegeType : Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
 * @param privilegeCode : Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
 * @param regionLevel : ���򼶱�,97A��ʾ���Ź�˾,97B��ʾʡ��˾,97C��ʾ������,97D��ʾӪҵ��,98D
 * ��ʾ�����,98E��ʾĸ��,98F��ʾ��վ
 * @param selectType : ��ѡ/��ѡ��־,1��ʾ��ѡ,2��ʾ��ѡ
 * @param regionIds : Ĭ��ѡ��ĵ���ID�б�,�Զ��ŷָ�.
 * @param selectParent :	1��ʾ����ѡ���ϼ�����,2��ʾ����ѡ���ϼ�����,Ĭ�ϲ�����ѡ���ϼ�����,�ڿ�
 * ��ѡ���ϼ������������,ѡ��Ķ�������������ͬ�����,�紫�ݵ�regionLevel����Ϊ98F,��
 * selectParent Ϊ2,�����ѡ���վ�;�վ����������Ȩ�޵ĵ���,���Ƕ�ѡ�ĵ����������ͬ�����,��
 * �粻��ѡ��һ���ֹ�˾��һ���й�˾.
 * @param selectDistinctRegionLevel :1��ʾֻ��ѡ����ͬ����ĵ���,2��ʾ����ѡ��ͬ����ĵ���
 * @param checkChildren
 * @param uncheckedParent 
 * @param downloadWhenChecked
 */

var staffCode = "" ;//����
var privType = "";//Ȩ����������
var privCode = "";//Ȩ����������
var regionLevel = "" ;//��ѡ����͵��򼶱�
var selectType = "" ;//��ѡ/��ѡ��־
var regionIds = "" ;//Ĭ��ѡ��ĵ���ID
var selectParent = "" ;//�Ƿ����ѡ����͵��򼶱������ָ�����ϼ�����
var selectDistinctRegionLevel = "" ;//�Ƿ�ֻ��ѡ����ͬ����ĵ���
var checkChildren = "" ;//�Ƿ��ڹ�ѡ�ϼ������ʱ���Զ���ѡ�¼�����
var uncheckedParent = "" ;//�Ƿ��ڹ�ѡ�¼������ʱ���Զ�ȡ����ѡ�ϼ�����
var downloadWhenChecked = "" ;//�Ƿ��ڹ�ѡ�����ʱ�������¼�����
var pathCode = "";
var pathCodeArray = new Array();

function page_onLoad(){
	var paramObj = window.dialogArguments;
	if( paramObj != null ){
		staffCode = paramObj["staffCode"] ;
		privType = paramObj["privilegeType"] ;
		privCode = paramObj["privilegeCode"] ;
		regionLevel = paramObj["regionLevel"] ;
		regionIds = paramObj["regionIds"];
		selectType = paramObj["selectType"];
		checkChildren = paramObj["checkChildren"];
		uncheckedParent = paramObj["uncheckedParent"];
		downloadWhenChecked = paramObj["downloadWhenChecked"];
		selectParent = paramObj["selectParent"];
		selectDistinctRegionLevel = paramObj["selectDistinctRegionLevel"] ;
		
		if( selectDistinctRegionLevel == null || selectDistinctRegionLevel == "undefined" || selectDistinctRegionLevel == "" ){
			selectDistinctRegionLevel = "1" ;//Ĭ��Ϊֻ��ѡ����ͬ���͵���֯
		}
		if( selectParent == null || selectParent == "undefined" || selectParent == "" ){
			selectParent = "1" ;//Ĭ�ϲ���ѡ���ϼ���֯,ֻ��ѡ��regionLevel����ָ��������
		}
		if( staffCode == null ){
			staffCode = "" ;//Ĭ��Ϊ��
		}
		if( privType == null ){
			privType = "" ;//Ĭ��Ϊ��
			ifPrivLimit = false ;
		}		
		if( privCode == null ){
			privCode = "" ;//Ĭ��Ϊ��
		}
		if( regionLevel == null ){
			regionLevel = "" ;//Ĭ��Ϊ��
		}
		if( regionIds == null ){
			regionIds = "" ;//Ĭ��Ϊ��
		}
		if( selectType == null ){
			selectType = "1" ;//Ĭ��Ϊ��ѡ
		}
		if( checkChildren == null ){
			checkChildren = "2" ;//Ĭ�ϵ���ѡ��ǰ��֯��ʱ���Զ���ѡ�¼��ڵ�
		}
		if( uncheckedParent == null ){
			uncheckedParent = "2" ;//Ĭ�ϲ�������
		}
		if( downloadWhenChecked == null ){
			downloadWhenChecked = "1" ;//Ĭ�������ӽڵ�
		}
		if( regionIds != "" ){
			var arr = regionIds.split(",") ;
			pathCode = getPathCode( arr[0] ) ;
		}
	}
	initRegion();
}
function getPathCode( orgId ) {
	var ajaxCall = new NDAjaxCall( false ) ;
	var returnValue = "";
	var callBack = function( reply ) {
		returnValue = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getPathCode",[orgId,"1"],callBack);
	return returnValue ;
}
function initRegion(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(false); 
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ; 
		regionTreeView.loadByXML(queryResult );
		if( regionTreeView.items == null || regionTreeView.items.length == 0 ){
			alert("��û���κ�����Ȩ��!");
			return ;
		}

        for( var i = 0 ; i < regionTreeView.items.length ; i ++ ){
        	var arr = regionIds.split( "." ) ;
        	for( var j = 0 ; j < arr.length ; j ++ ){
        		if( arr[j] == regionTreeView.items[i].regionId ){
        			regionTreeView.items[i].setChecked( true ) ;
        		}
        	}
        }

		if( pathCode != "" ){
        	var arr = pathCode.split(".");
        	var breakRoop = false ;
        	for( var i = 0 ; i < regionTreeView.items.length ; i ++ ){
        		for( var j = 0 ; j < arr.length ; j ++ ){
	        		if( regionTreeView.items[i].regionId == arr[j] ) { 
	        			breakRoop = true ;
						getCheckedItem(regionTreeView.items[i]) ;
						break;
					}
				}
				if( breakRoop ){
					break ;
				}
			}
		}
	}
	
	/**
	 *���÷ֲ����صķ�ʽʵ��.�����ǰ�Ĳ����regionLevel֮��,������ͨ��������ر�����ĵ�����¼�
	 *����,�����ǰ�Ĳ���Ѿ���regionLevel��ָ���ĵ�����,����Ҫ�ٴ���������������������²�ε�
	 *����.���ز�ε���regionLevel�ĵ����ʱ��,��Ҫ�ٴ���һ��Ȩ�ޱ������,�������˹��˵����������
	 *Ȩ�������Ƶĵ���ļ�¼,ֻ���û���Ȩ�޵ĵ�����������.
	 **/
	if( privType == "-1" ){
		ajaxCall.remoteCall("RegionService","getResourceRegionListWithPrivFlag",["-1"],callBack); 	
	}else{
		ajaxCall.remoteCall("RegionService","getResourceRegionListByFilter",["-1",regionLevel,privType,privCode],callBack); 
	}
//	ajaxCall.remoteCall("RegionService","getResourceRegionListByFilter",["-1",regionLevel,privType,privCode],callBack); 
} 

function getCheckedItem( item ){ 
	clickRegion( item ) ;

	var subItems = item.items; 
	if( subItems ){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			var arr = pathCode.split("."); 
			for( var j = 0 ; j < arr.length ; j ++ ){
				if( arr[j] == subItems[i].regionId ){
					getCheckedItem( subItems[i] );
				}
			}
		}
	}else{
		//alert("subItems is null");
	}
}

function getCheckedItem1( item ){
	clickRegion( item ) ;
	var subItems = item.items; 
	if( subItems ){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			var arr = pathCode.split(".");
			for( var j = 0 ; j < arr.length ; j ++ ){
				if( arr[j] == subItems[i].regionId ){
					getCheckedItem( subItems[i] );
				}
			}
		}
	}
}

/*
 *currentSelectedRegionLevel�������ڱ����û���ѡ�ĵ�һ������ĵ��򼶱�,���ڱ�֤�û�ѡ��Ķ����
 *��ļ�����ͳһ��,����һ��ѡ����,����ѡ������ͬ����ĵ���
 */ 
var currentSelectedRegionLevel = "";
function checkRegion(){
	if( regionTreeView.selectedItem == null ){
		return ;
	}
	if( downloadWhenChecked == "1" ) {
		clickRegion();
	}
	//�����һ����ѡ�еĵ��򼶱�
	if( currentSelectedRegionLevel == "" ){
		var checkedItem = regionTreeView.checkedItems[0] ;
		if( checkedItem != null && typeof( checkedItem ) != "undefined" ){
			currentSelectedRegionLevel = checkedItem.regionLevel ;
		}
	}

	var canCheckCurrentItem = true ;//�жϵ�ǰ�ڵ��Ƿ��ܱ���ѡ�ı���
	var selItem = regionTreeView.selectedItem ;
	var selLevel = selItem.regionLevel ;
	
	if( !selItem.getChecked() ){//����û���ȡ����ѡ,�򷵻�
		if( regionTreeView.checkedItems.length == 0 ){
			currentSelectedRegionLevel = "" ;
		}
		return ;
	}
	
	//selectType����ָ����ѡ��ʵ��ѡ���˶������
	if( selectType == "1" && regionTreeView.checkedItems.length > 1 ){//��ѡ����������
		alert("ֻ��ѡ��һ������") ;
		selItem.setChecked(false) ;
		if( regionTreeView.checkedItems.length == 0 ){
			currentSelectedRegionLevel = "";
		}		
		return ;
	}
	//û��Ȩ�޵ĵ���
	if( selItem.privilegeFlag != "T" ){//Ȩ������������
		alert("���ڵ�ǰ�ĵ���û��Ȩ��!");
		selItem.setChecked(false) ;
		if( regionTreeView.checkedItems.length == 0 ){
			currentSelectedRegionLevel = "";
		}
		return ;
	}
	//���򼶱���ѡ����͵��򼶱��ϼ��ĵ���
	if( selItem.regionLevel != regionLevel && selectParent == "1" ){//��֯��������������
		canCheckCurrentItem = false ;
		showErrorMsg(regionLevel) ;
	}
	if( selectParent == "2" ){//����ѡ��regionLevel����ָ���ĵ��򼶱�������ϼ�����
		//Ҫ���ѡ�Ķ�����򼶱����һ��,��ʵ�ʵ�ѡ��һ��.
		var currentCheckedItems = regionTreeView.checkedItems ;
		for( var n = 0 ; n < currentCheckedItems.length ; n ++ ){
//			if( selectDistinctRegionLevel == "1" && currentSelectedRegionLevel != "" && currentSelectedRegionLevel != selItem.regionLevel ){//���Ҫ��ѡ��ĵ��򼶱�һ��,�ж��Ƿ��������
			if( selectDistinctRegionLevel == "1" && currentSelectedRegionLevel != "" && currentSelectedRegionLevel != currentCheckedItems[n].regionLevel ){//���Ҫ��ѡ��ĵ��򼶱�һ��,�ж��Ƿ��������
				canCheckCurrentItem = false ;
				alert("��ѡ��Ķ������ļ������һ��!");
				break ;
			}
		}
	}
	
	//ȷ���Ƿ�ѡ��ǰ��֯�ڵ�
	if( !canCheckCurrentItem ){
		selItem.setChecked(false) ;
		if( regionTreeView.checkedItems.length == 0 ){
			currentSelectedRegionLevel = "";
		}
	}
	
	//ȷ���Ƿ�Ҫȡ����ѡ�ϼ�����
	if( uncheckedParent == "1" && canCheckCurrentItem ){//�������Ҫ��ѡ������ʱ���Զ����ϼ���֯�Ĺ�ѡȡ��.
		var parentItem = selItem.getParentItem();
		while( parentItem ){
			parentItem.setChecked( false ) ;
			parentItem = parentItem.getParentItem();
		}		
	}
	
	//ȷ���Ƿ�ѡ�¼���֯ 
	if( checkChildren == "1" && selectType == "2" ){//�����Ƕ�ѡ�����²����Զ���ѡ�¼���֯
		var subItems = selItem.items ;
		if( subItems ){//������¼��ڵ�Ŵ���
			//�����ǰ�ڵ�ĵ��򼶱�ΪregionLevel����ָ���ļ���
			if( selItem.regionLevel == regionLevel ){
				if( selectParent == "2" && currentSelectedRegionLevel != selItem.regionLevel && selectDistinctRegionLevel == "1"){//������Թ�ѡ�ϼ�����,���ҹ�ѡ�Ķ������ļ���ͬ,�����ڲ��Ϸ�ѡ��.
					alert("����ѡ�Ķ�����������������ͬ�����!");
					selItem.setChecked( false ) ;
				}
				return ;
			}else {
				//�����ѡ�Ĳ��Ǵ��ݲ�����ָ���ĵ��򼶱�.
				if( //���ѡ��ĵ����ǲ���ָ�����򼶱���ϼ�
					(( selLevel == "97A" ) && ( regionLevel == "97B" )) ||		//�����ѡ���Ǽ��Ź�˾��Ҫѡ�����ʡ��˾
					((selLevel == "97B" ) && ( regionLevel == "97C" )) ||		//�����ѡ����ʡ��˾��Ҫѡ����Ǳ�����
					((selLevel == "97C" ) && ( regionLevel == "97D" )) ||		//�����ѡ���Ǳ�������Ҫѡ�����Ӫҵ��
					((selLevel == "97D" ) && ( regionLevel == "98D" )) ||		//�����ѡ����Ӫҵ����Ҫѡ����Ǵ����
					((selLevel == "98D" ) && ( regionLevel == "98E" )) ||		//�����ѡ���Ǵ���ֶ�Ҫѡ�����ĸ��
					(( selLevel == "98E" ) && ( regionLevel == "98F"))) {	//�����ѡ����ĸ�ֶ�Ҫѡ����Ǿ�վ
					//��ѡ�¼�����Ҫѡ�����֯.
					for( var i = 0 ; i < subItems.length ; i ++ ){
						if( subItems[i].privilegeFlag != "T" ){//���û��Ȩ��,����Ե�
							continue ;
						}
						if( subItems[i].regionLevel == regionLevel ){//����¼�����ļ�����regionLevel����ָ���ĵ���,���Զ���ѡ
							if( selectDistinctRegionLevel == "1" ){//���Ҫ��ѡ��ĵ��򼶱����һ��,���ټ�������ж�
								if( subItems[i].regionLevel == currentSelectedRegionLevel || currentSelectedRegionLevel == ""){
									subItems[i].setChecked(true) ;
								}
							}else{
									subItems[i].setChecked(true) ;
							}
						}else{
							//����¼�����ļ�����regionLevel����ָ���ĵ���,����selectParent����ָ������ѡ��ѡ��regionLevel���ϼ�����
							if( selectParent == "2" ){
								if( selectDistinctRegionLevel == "1" ){//���Ҫ��ѡ��ĵ��򼶱����һ��,���ټ�������ж�
									if( subItems[i].regionLevel == currentSelectedRegionLevel || currentSelectedRegionLevel == ""){
										subItems[i].setChecked( true ) ;
									}
								}else{//���������ж�
									subItems[i].setChecked( true ) ;
								}
							}
						}
					}
				}else{
					//����¼�����ļ�����regionLevel����ָ���ĵ���,����selectParent����ָ������ѡ��ѡ��regionLevel���ϼ�����
					if( selectParent == "2" ){
						for( var i = 0 ; i < subItems.length ; i ++ ){
							if( subItems[i].privilegeFlag != "T" ){
								continue ;
							}
							if( selectDistinctRegionLevel == "1" ){//���Ҫ��ѡ��ĵ��򼶱����һ��,���ټ�������ж�
								if( subItems[i].regionLevel == currentSelectedRegionLevel ){
									subItems[i].setChecked( true ) ;
								}
							}else{//���������ж�
								subItems[i].setChecked( true ) ;
							}
						}
					}
				}
			}
		}
	}
}

function showErrorMsg(regionLevel){
	if( regionLevel == "97A" ){
		alert("ֻ��ѡ���й����ż���!");
	}else if( regionLevel == "97B" ){
		alert("ֻ��ѡ��ʡ��˾!");
	}else if( regionLevel == "97C" ){
		alert("ֻ��ѡ�񱾵���!");
	}else if( regionLevel == "97D" ){
		alert("ֻ��ѡ��Ӫҵ��!");
	}else if( regionLevel == "98D" ){
		alert("ֻ��ѡ�����!");
	}else if( regionLevel == "98E" ){
		alert("ֻ��ѡ��ĸ��!") ;
	}else if( regionLevel == "98F" ){
		alert("ֻ��ѡ���վ!");
	}
}

function clickRegion(paramItem){
	var selItem = null ;
	if( paramItem != null ){
		selItem = paramItem ;
	}else{
		selItem = regionTreeView.selectedItem ;
	}

	if( selItem == null ){ 
		return ;
	}
	if( selItem.items ){ 
		return ;
	}
	
	var selLevel = selItem.regionLevel ;
	
	if( selLevel == regionLevel ){ 
		return ;
	}
	downloadSubRegions(privType, privCode, regionLevel ,selItem ) ;  
}

function downloadSubRegions( privType,privCode,regionLevel , item ){
	var selItem = null ;
	if( item != null ){
		selItem = item ;
	}else {
		selItem = regionTreeView.selectedItem ;
	}
	if( selItem == null ){
		return ;
	}
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( false ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			
			var childItems = selItem.items ;
			if( childItems[0].regionLevel == regionLevel || selectParent == "2" ){
				if( regionIds != "" ){
					var arr = regionIds.split(",");
					if( childItems ){
						for( var j = 0; j < childItems.length; j ++ ){
							for( var i = 0 ; i < arr.length ; i ++ ){
								if( childItems[j].regionId == arr[i] ){
									childItems[j].setChecked("true") ;
								}
							}
						}
					}
				}
			}
		}
	}
	
	var regionId = selItem.regionId ;
	if( selItem.privilegeFlag == "T" ){//��ǰ�ڵ���Ȩ�޷�Χ�ڵ�
		//��ѯ��Դ����ǰ�ڵ��µ����нڵ�,��Ϊ��Щ�¼��ڵ����Ȩ�ޱ�־.
		ajaxCall.remoteCall("RegionService","getResourceRegionListWithPrivFlag",[regionId],callBack); 
	}else {
		//��ѯ��Դ����,�����˵�û��Ȩ�޵ĵ���
		ajaxCall.remoteCall("RegionService","getResourceRegionListByFilter",[regionId,regionLevel,privType,privCode],callBack); 	
	}
}

function btn_Confirm_onClick(){
	var items = regionTreeView.checkedItems;
	if( items == null ){
		return ;
	}
	
	//�ж϶�ѡ�����ǵ�ѡ
	if( selectType == "1" && items.length > 1 ){
		alert("ֻ��ѡ��һ����¼!");
		return ;
	}

	//�ж�ѡ��ĵ������Ƿ�Ϊ�������ƶ��Ĳ��
	var regionArray = new Array();
	for( var i = 0; i < items.length ; i ++ ){
		if( items[i].regionLevel != regionLevel && selectParent == "1" && selectDistinctRegionLevel == "1" ){
			showErrorMsg(regionLevel);
		}
		var regionVO = new Object();
		regionVO["regionId"] = items[i].regionId ;
		regionVO["regionName"] = items[i].regionName ;
		regionVO["regionCode"] = items[i].regionCode ;
		regionVO["regionLevel"] = items[i].regionLevel ;
		regionArray[i] = regionVO ;
	}
	window.returnValue = regionArray;
	window.close();
}

function btn_Cancel_onClick(){
	window.returnValue = "" ;
	window.close();
}