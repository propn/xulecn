/**
97A        ����
97B        ʡ
97C        ������
97D        Ӫҵ��
97E        �ƷѾ���
98D        �����
98E        ĸ��
98F        ��վ
*/
function page_onLoad(){
	initRegion();
}
	
//��ʼ��TreeList�����������ʾ
function initRegion(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.regionTreeView.loadByXML(queryResult);
		
		//����ϸ����Ӧ��Dataset�ϲ���һ���ռ�¼
		regionInfo.insertRecord(null);
		
		globleItem = document.all.regionTreeView.items[0];
		fillValues(globleItem); 		
	}
	ajaxCall.remoteCall("RegionService","getBillRegionList",["-1"],callBack);
}

//�����ϸ����ϵ���Ϣ
function cleanValues(){
	regionInfo.clearData();//���Ź�˾
	regionInfo.insertRecord( null ) ;
	
	provinceInfo.clearData();//ʡ��˾
	provinceInfo.insertRecord(null);
	
	lanInfo.clearData();//������
	lanInfo.insertRecord( null ) ;
	
	businessInfo.clearData();//Ӫҵ��
	businessInfo.insertRecord( null ) ;
	
	exchangeInfo.clearData() ;//����
	exchangeInfo.insertRecord( null ) ;
}

//����һ�е���Ϣ����ϸ��Ϣ���
function fillValues(){
	//��ȡ��ǰ��ѡ�еļ�¼
	var selItem = document.all.regionTreeView.selectedItem;
	if ( selItem == null ) {
		return ;
	}
	
	//��ʾ������Ϣ
	for(var ele in selItem){
		//ʹ�õ�ǰ��ѡ�еļ�¼�����Dataset
		if(regionInfo.getField(ele)){
			regionInfo.setValue(ele, selItem[ele]);
		}
	}
		
	//��������Ĳ�ͬ������ʾ��ͬ����ϸ��Ϣ���
	var regionLevel = selItem.regionLevel ;//��ǰ��ѡ�еļ�¼�����򼶱�
	if( regionLevel == "97A" ){//���Ź�˾
		hidPannels();
		document.all.groupPannel.style.display ="block";
	}else if ( regionLevel == "97B" ){//ʡ��˾
		var province = new Province() ;
		var regionId = selItem.regionId ;
		province = getProvinceInfo( "RegionService", "getProvinceByRegionId", regionId );//���������ʶ���������˻�ȡ�͸������ʶ��Ӧ��ʡ����Ϣ

		if( province != null ){
			setProvinceInfo( province ) ;
			provinceInfo.setValue("prvDesc", selItem.regionDesc);
			provinceInfo.setValue("ngnFlag",selItem.ngnFlag);
			provinceInfo.setValue("isActualRegion",selItem.isActualRegion);
			provinceInfo.setValue("provinceCode",selItem.provinceCode);
		}
		hidPannels();
		document.all.provincePannel.style.display = "block" ;
	}else if ( regionLevel == "97C" ){//������
		var regionId = selItem.regionId ;
		var obj = getLanInfo( "RegionService", "getLanByRegionId", regionId );//���������ʶ���������˻�ȡ�͸������ʶ��Ӧ�ı�������Ϣ
		if( obj != null ){
			setLanInfo( obj ) ;
			lanInfo.setValue("lanDesc", selItem.regionDesc);
			lanInfo.setValue("ngnFlag", selItem.ngnFlag); 
			lanInfo.setValue("isActualRegion",selItem.isActualRegion);
			lanInfo.setValue("provinceCode",selItem.provinceCode);			
		}
		hidPannels();
		document.all.lanPannel.style.display = "block" ;
	}else if ( regionLevel == "97D" ){//Ӫҵ��
		var business = new Business() ;
		var lanId = selItem.regionId ;//����������
		business = getBusinessInfo( "RegionService", "getBusinessByRegionId", lanId );//���������ʶ���������˻�ȡ�͸������ʶ��Ӧ��Ӫҵ����Ϣ
		if( business != null){
			setBusinessInfo( business ) ; 
			businessInfo.setValue("businessDesc",selItem.regionDesc);
			businessInfo.setValue("ngnFlag",selItem.ngnFlag);
			businessInfo.setValue("isActualRegion",selItem.isActualRegion);
			businessInfo.setValue("provinceCode",selItem.provinceCode);			
		}
		hidPannels() ;
		document.all.businessPannel.style.display = "block" ;
	}else if ( regionLevel == "97E" ){//����
		//��ʾ������Ϣ
		exchangeInfo.clearData();
		exchangeInfo.insertRecord(null);
		for(var ele in selItem){//ʹ�õ�ǰ��ѡ�еļ�¼�����Dataset
			if(exchangeInfo.getField(ele)){
				exchangeInfo.setValue(ele, selItem[ele]);
				exchangeInfo.setValue("ngnFlag", selItem.ngnFlag ) ;
				exchangeInfo.setValue("isActualRegion",selItem.isActualRegion);
				exchangeInfo.setValue("provinceCode",selItem.provinceCode);			
			}
		}
		hidPannels();
		document.all.exchangePannel.style.display = "block" ;
		//����ǩҳ��λ����һҳ
		
	}
}

//���û�������������ʱ��,�жϵ�ǰ�ڵ��Ƿ����¼��ڵ�,���û��,
//���������˲鿴�Ƿ����¼��ڵ�,����������˷����˸ýڵ���¼�
//�ڵ�����,����Щ���ݽ�����Ϊ������¼��ڵ�,��ӵ�������.
function downloadSubItems(){
	var selItem = regionTreeView.selectedItem ;
	if( selItem == null ){
		return ;
	}
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
	
	var regionId = selItem.regionId ;
	ajaxCall.remoteCall("RegionService","getBillRegionList",[regionId], callBack);
}

//���ĵ���¼�������ϸ�������ʾ��ǰ�����еļ�¼����ϸ��Ϣ
function clickRegion(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	
	//��ȡ��ǰ�ڵ��µ������¼��ڵ�.
	downloadSubItems() ;
	
	cleanValues() ;//���Dataset�еľ�����
	fillValues();//��Dataset��ֵ	 
	disableDataset();
	actionType = "";
	nodeType = "";

	var parentItem = getParentRegionById( selItem.parentRegionId );
	if( parentItem != null ){
		if( selItem.regionLevel == "97A" ){//���Ź�˾
			
		}else if (selItem.regionLevel == "97B" ){//ʡ��˾

		}else if ( selItem.regionLevel == "97C" ){//������
			lanInfo.setValue("provId", parentItem.regionId);
			lanInfo.setValue("provName", parentItem.regionName);
		}else if ( selItem.regionLevel == "97D" ){//Ӫҵ��
			businessInfo.setValue("lanId", parentItem.regionId);
			businessInfo.setValue("lanName", parentItem.regionName) ;
		}else if ( selItem.regionLevel == "97E" ){//����
			exchangeInfo.setValue( "parentRegionId" , parentItem.regionId ) ;
			exchangeInfo.setValue( "parentRegionName", parentItem.regionName) ;
			queryNbrList();
			exchangePage.setSelectedPageIndex(0);
			//exchangePage_onPageChanged();
		}		
	}
	if( selItem.regionLevel == "97E" ){
		$("addSubRegion").disabled=true;
	}
	else{
		$("addSubRegion").disabled=false;
	}
}

//ͨ���ϼ�Ȩ��ID��ȡ�ϼ��������
function getParentRegionById( parentId ){
	var regionTree = document.all.regionTreeView;
	var items = regionTree.items;
	for( var i = 0 ; i < items.length ; i ++ ){
		if( items[i].regionId == parentId ){
			return items[i] ;
		}
	}
}

//��ȡ��ǰ��ѡ�е������¼��id
function getCurrentRegionId(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	if( selItem != null ){
		return selItem.regionId ;
	}else{
		return null ;
	}
}

//�������е���ϸ��Ϣ���
function hidPannels(){
	document.all.groupPannel.style.display = "none" ;
	document.all.provincePannel.style.display = "none" ;
	document.all.lanPannel.style.display = "none" ;
	document.all.businessPannel.style.display = "none" ;
	document.all.exchangePannel.style.display = "none" ;
}

//�༭����
function editRegion_onClick(){
	if( actionType != "" ) {
		alert("���ȱ������ȡ����ǰ�����ٱ༭����!");
		return;
	}
	actionType = "update" ;
	enableDataset();
} 

//��������
function addRegion(parentRegionId){
	enableDataset();
	var selItem = regionTreeView.selectedItem ;
	cleanValues();
	hidPannels();
	if( parentRegionId == null ){//����һ�������� 
		document.all.groupPannel.style.display = "block" ;//��ʾ������Ϣ���
		regionInfo.setValue("regionLevel", "97A"); 
		return;
	} 
	
	if( parentRegionId != null ){//�������һ���ϼ�������������������
		var item = document.all.regionTreeView.selectedItem ;//��ǰ��ѡ�еļ�¼
		
		regionInfo.setValue("parentRegionName", item.regionName ) ;//�����ϼ����������
		regionInfo.setValue("parentRegionId",item.regionId);//�����ϼ������ID
		
		var regionLevel = item.regionLevel ;
		
		if( regionLevel == "97A" ){//�ڼ��Ź�˾���棬���ӵ���ʡ��˾����
			document.all.provincePannel.style.display = "block";
			regionInfo.setValue("regionLevel", "97B"); 
		}else if ( regionLevel == "97B" ){//��ʡ��˾���棬���ӵ��Ǳ���������
			document.all.lanPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "97C");
			 //��������Dataset�����ϼ��������ƣ�����ʡ�����ƣ����ϼ�����ID
			 lanInfo.setValue("provId", selItem.regionId);
			 lanInfo.setValue("provName",selItem.regionName);
			 
		}else if ( regionLevel == "97C" ){ //�ڱ��������棬���ӵ���Ӫҵ������ 
			document.all.businessPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "97D");
			//��Ӫҵ��Dataset�����ϼ����������ƺ�ID
			businessInfo.setValue("lanId", selItem.regionId);
			businessInfo.setValue("lanName", selItem.regionName);
			businessInfo.setValue("businessCode",selItem.regionCode);
		}else if ( regionLevel == "97D" ){//��Ӫҵ�����棬���ӵ��Ǿ�������
			document.all.exchangePannel.style.display = "block" ;
			exchangeInfo.setValue("regionLevel", "97E");
			exchangeInfo.setValue("parentRegionId",selItem.regionId);
			exchangeInfo.setValue("parentRegionName",selItem.regionName);
			exchangeInfo.setValue("regionCode",selItem.regionCode);
		} 
	}
}

//ɾ��һ������
function deleteRegion_onClick(){
	if( actionType != "" ) {
		alert("���ȱ������ȡ����ǰ������ɾ������!");
		return;
	}
	if( confirm( "��ȷ��Ҫɾ����ǰ������Ϣ��?" )){
		var currentRegionId = getCurrentRegionId();
		
		var ajaxCall = new NDAjaxCall(true);
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;
			if( deleteResult ){
				deleteRegionFromTreeList();//����ǰ��¼��TreeList��ɾ��
				alert("ɾ��������Ϣ�ɹ���");
			}else{
				alert("ɾ��������Ϣʧ��!" );
			}
		}
		ajaxCall.remoteCall("RegionService","deleteRegion",[currentRegionId],callBack);
	}
}

//���������ɾ��һ����¼
function deleteRegionFromTreeList(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	selItem.remove();
}

function enableDataset(){
	regionInfo.setReadOnly( false ) ;
	provinceInfo.setReadOnly( false ) ;
	lanInfo.setReadOnly(false);
	businessInfo.setReadOnly( false ) ;
	exchangeInfo.setReadOnly( false ) ;
	
	var selItem = document.all.regionTreeView.selectedItem;
	if( selItem == null ){
		return ;
	}
	if( selItem.regionLevel == "97A"){//��ѡ���Ǽ��Ź�˾
		if( actionType == "update" ){
			regionInfo.setFieldReadOnly("regionLevel" , true );
		}else if( actionType =="insert"){
			if( nodeType == "root" ){
				regionInfo.setFieldReadOnly("regionLevel" , true );
			}else{
				
			}
		}
	}
	if( selItem.regionLevel == "97B" ){//��ѡ����ʡ��˾
		if( actionType == "insert" ){
			lanInfo.setFieldReadOnly("provName",true);
		}
	}
	if( selItem.regionLevel == "97C" ){//��ѡ���Ǳ�����
		if( actionType == "update" ){
			lanInfo.setFieldReadOnly("provName",true);
		}else if( actionType == "insert"){
			businessInfo.setFieldReadOnly("lanName",true);
		}
	}
	if( selItem.regionLevel == "97D" ){//��ѡ����Ӫҵ��
		if( actionType == "update" ){
			businessInfo.setFieldReadOnly("lanName",true);
		}else if ( actionType == "insert" ){
			exchangeInfo.setFieldReadOnly( "parentRegionName" , true ) ;
		}
	}
	if( selItem.regionLevel == "97E" ){
		if( actionType == "update" ){
			exchangeInfo.setFieldReadOnly( "parentRegionName" , true ) ;
		}
	}	
}
function disableDataset(){
	regionInfo.setReadOnly( true ) ;
	provinceInfo.setReadOnly( true ) ;
	lanInfo.setReadOnly(true);
	businessInfo.setReadOnly( true ) ;
	exchangeInfo.setReadOnly( true ) ;
}

function checkExistRegionCode( regionCode ) {
	var ajaxCall = new NDAjaxCall( false ) ;//�첽��ʽ��ѯ
	var result ;
	var callBack = function( reply ) {
		result = reply.getResult() ;
	}
	
	ajaxCall.remoteCall("RegionService", "checkExistRegionCode",[regionCode], callBack ) ;
	return result ;
}

//�ύ����һ�����������
function commitInsertRegion(){
	var regionTreeView = document.all.regionTreeView ;
	var selItem = regionTreeView.selectedItem;
	var actionResult = null ;
	
	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		actionResult = reply.getResult() ;//����������˲������صĽ��
		//�������˷��������ӵ������ID
		refreshDatainfo(actionResult);//��������Ժ󣬱������³�ʼ��������Dataset�������Ӧ��Ϣ	
		addRegionToTreeList();
		actionType = "" ; 
		nodeType = "" ;
		alert("�����ɹ���");
	}
	
	var regionLevel ;//��ǰ��ѡ�е�����ļ���
	if( selItem == null ){
		regionLevel == "" ;
	}else{
		regionLevel = selItem.regionLevel ;
	}
	
	//��������ǡ����Ź�˾������nodeType��root����˵��Ҫ���Ӽ��Ź�˾���ڵ�
	//������򼶱��ǡ�Ӫҵ���������ӵľ��Ǿ������Ӿ��򵥶����������Ϳ����ˣ�
	//ֻ���ڸ��������ú���ε�ʱ��Ż��������

	if( nodeType == "root" || regionLevel == "97D" ){
		if( regionLevel == "97D" ){
			if( !$("exchangeInfoForm").validate()){ 
				enableDataset();
				return ;
			}			
		}else{
			if( !$("regionInfoForm").validate()){
				enableDataset() ; 
				return ;
			}
		}
		//������������ҽ�Dataset�е�ֵ��ֵ��������
		var region = new Region() ;
		if( regionLevel == "97A" || nodeType == "root" ){
			for(var ele in region){ 
				if(regionInfo.getField(ele)){
					region[ele] = regionInfo.getValue( ele );
				} 
			}
			region.parentRegionId = "-1";
			region.regionLevel = "97A" ;
			
		}else if( regionLevel == "97D" ){
			region.regionId = exchangeInfo.getValue("regionId");
			region.regionName = exchangeInfo.getValue("regionName") ;
			region.regionCode = exchangeInfo.getValue("regionCode") ;
			region.regionDesc = exchangeInfo.getValue("regionDesc" );
			region.parentRegionId = selItem.regionId ;//�ϼ�����ID
			region.ngnFlag = exchangeInfo.getValue("ngnFlag");
			region.isActualRegion = exchangeInfo.getValue("isActualRegion");
			region.provinceCode = exchangeInfo.getValue("provinceCode");		
			region.regionLevel = "97E" ;

//			if( region.regionCode.substring(0,4) != selItem.regionCode ){
//				alert("Ӫҵ�������ǰ��λ������Ӫҵ������("+selItem.regionCode+") ��");
//				exchangeInfo.setValue("regionCode", selItem.regionCode);
//				enableDataset();
//				return ;
//			}

		}
		
		var arr = new Array();
		arr[0] = region;
		
		if( checkExistRegionCode( region.regionCode )){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		
	}else{//���ӵ����ӽڵ�
		//������򼶱��ǡ����Ź�˾�������ӵľ���ʡ��˾��
		if( regionLevel == "97A" ){
			if( !$("provinceInfoForm").validate()){
				enableDataset() ;
				return ;
			}
			//��Ҫͬʱ����Region���Province��
			var region = new Region() ;
			region.parentRegionId = selItem.regionId ;//�ϼ�����ID
			region.configId = "";
			region.regionLevel = "97B" ;//ʡ��˾ 
			region.regionName = provinceInfo.getValue("prvName") ;
			region.regionCode = provinceInfo.getValue("prvCode");
			region.regionDesc = provinceInfo.getValue("prvDesc") ;
			region.ngnFlag = provinceInfo.getValue("ngnFlag");
			region.isActualRegion = provinceInfo.getValue("isActualRegion");
			region.provinceCode = provinceInfo.getValue("provinceCode");
		
			var province = new Province() ;
			for( ele in province ){
				if( provinceInfo.getField( ele )){
					province[ele] = provinceInfo.getValue( ele ) ;
				}
			}
			
			if( checkExistRegionCode( region.regionCode )){
				alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
				enableDataset();
				return ;
			}
			var arr = new Array();
			arr[0] = region;
			arr[1] = province ;
			ajaxCall.remoteCall("RegionService","insertProvince",arr,callBack);
			
		}else if ( regionLevel == "97B" ){//��ʡ��˾���棬���ӵ��Ǳ���������
			if( !$("lanInfoForm").validate()){
				enableDataset();
				return ;
			}
			//��Ҫͬʱ����Region���Lan��
			var region = new Region() ;
			region.parentRegionId = selItem.regionId ;//�ϼ�����ID
			region.configId = "";
			region.regionLevel = "97C" ;
			region.regionName = lanInfo.getValue("lanName") ;
			region.regionCode = lanInfo.getValue("lanCode");
			region.regionDesc = lanInfo.getValue("lanDesc") ;
			region.areaCode = lanInfo.getValue("areaCode");
			region.flag = lanInfo.getValue("flag");
			region.ngnFlag = lanInfo.getValue("ngnFlag") ;
			region.isActualRegion = lanInfo.getValue("isActualRegion");
			region.provinceCode = lanInfo.getValue("provinceCode");
			
		if( checkExistRegionCode( region.regionCode )){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
			//������
			var lan = new Lan() ;
			for( ele in lan ){
				if( lanInfo.getField( ele )){
					lan[ele] = lanInfo.getValue( ele ) ;
				}
			}
			lan.flag = lanInfo.getValue("flag");
			var arr = new Array();
			arr[0] = region;
			arr[1] = lan ;
			ajaxCall.remoteCall("RegionService","insertLan",arr,callBack);
			
		}else if ( regionLevel == "97C" ){//�ڱ��������棬���ӵ���Ӫҵ������
			if( !$("businessInfoForm").validate()){
				enableDataset();
				return ;
			}
			//��Ҫͬʱ����Region���Business��
			var region = new Region() ;
			region.regionId = businessInfo.getValue("businessId");
			region.regionName = businessInfo.getValue("businessName") ;
			region.regionCode = businessInfo.getValue("businessCode") ;
			region.regionDesc = businessInfo.getValue("businessDesc" );
			region.parentRegionId = selItem.regionId ;//�ϼ�����ID
			region.regionLevel = "97D" ;
			region.ngnFlag = businessInfo.getValue("ngnFlag");
			region.isActualRegion = businessInfo.getValue("isActualRegion");
			region.provinceCode = businessInfo.getValue("provinceCode");
			region.configId = "";

//			if( region.regionCode.substring(0,2) != selItem.regionCode){
//				alert("Ӫҵ�������ǰ��λ�����Ǳ���������("+selItem.regionCode+") ��");
//				businessInfo.setValue("businessCode", selItem.regionCode);
//				enableDataset();
//				return ;
//			}

			if( checkExistRegionCode( region.regionCode )){
				alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
				enableDataset();
				return ;
			}
		
			//ʡ�ݱ�
			var business = new Business() ;
			for( ele in business ){
				if( businessInfo.getField( ele )){
					business[ele] = businessInfo.getValue( ele ) ;
				}
			}
			
			var arr = new Array();
			arr[0] = region;
			arr[1] = business ;
			ajaxCall.remoteCall("RegionService","insertBusiness",arr,callBack);

		}else if ( regionLevel == "97D" ){//��Ӫҵ�����棬���ӵ��Ǿ�������
			
		}
	}
}

//�ύ����һ�����������
function commitUpdateRegion(){
	var regionTreeView = document.all.regionTreeView ;
	var selItem = regionTreeView.selectedItem;
	var actionResult = null ;
	
	var regionLevel = selItem.regionLevel ;//��ǰ��ѡ�е�����ļ���
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		actionResult = reply.getResult() ;//����������˲������صĽ��
		if(actionResult){
			refreshTreeList();
			alert("�����ɹ���");
		}else{
			alert("����ʧ��!") ;
		}		
		actionType = "" ;
		nodeType = "" ;
	} 
	
	//��������ǡ����Ź�˾������nodeType��root����˵��Ҫ���Ӽ��Ź�˾���ڵ�
	//������򼶱��ǡ�Ӫҵ���������ӵľ��Ǿ������Ӿ��򵥶����������Ϳ����ˣ�
	//ֻ���ڸ��������ú���ε�ʱ��Ż�������� 
	if( regionLevel == "97A" || regionLevel == "97E" ){
		if( regionLevel == "97A"){
			if( !$("regionInfoForm").validate()){
				enableDataset();
				return;
			}
		}
		if( regionLevel == "97E"){
			if( !$("exchangeInfoForm").validate()){
				enableDataset();
				return;
			}
		}

		//������������ҽ�Dataset�е�ֵ��ֵ��������
		var region = new Region() ;
		for(var ele in region){
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
		
		if( regionLevel == "97E" ){ 
			region.regionName = exchangeInfo.getValue("regionName") ;
			region.regionCode = exchangeInfo.getValue("regionCode") ;
			region.regionDesc = exchangeInfo.getValue("regionDesc");
			region.ngnFlag = exchangeInfo.getValue("ngnFlag");
			region.isActualRegion = exchangeInfo.getValue("isActualRegion");
			region.provinceCode = exchangeInfo.getValue("provinceCode");
			region.regionLevel = "97E";

//			if( region.regionCode.substring(0,4) != selItem.regionCode ){
//				alert("Ӫҵ�������ǰ��λ������Ӫҵ������("+selItem.regionCode+") ��");
//				exchangeInfo.setValue("regionCode", selItem.regionCode);
//				enableDataset();
//				return ;
//			}

		}
		var arr = new Array();
		arr[0] = region;
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
		
	}else if( regionLevel == "97B" ){
		if( !$("provinceInfoForm").validate()){
			enableDataset();
			return;
		}
		var region = new Region() ; 
		region.regionLevel = "97B";
		region.regionName = provinceInfo.getValue("prvName") ;
		region.regionCode = provinceInfo.getValue("prvCode");
		region.regionDesc = provinceInfo.getValue("prvDesc") ;
		region.ngnFlag = provinceInfo.getValue("ngnFlag");
		region.isActualRegion = provinceInfo.getValue("isActualRegion");
		region.provinceCode = provinceInfo.getValue("provinceCode");
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		//ʡ�ݱ�
		var province = new Province() ;
		for( ele in province ){
			if( provinceInfo.getField( ele )){
				province[ele] = provinceInfo.getValue( ele ) ;
			}
		}
		
		var arr = new Array();
		arr[0] = region;
		arr[1] = province ;
		ajaxCall.remoteCall("RegionService","updateProvince",arr,callBack);
			
	}else if ( regionLevel == "97C" ){
		if( !$("lanInfoForm").validate()){
			enableDataset();
			return;
		}
		var region = new Region() ; 
		region.regionLevel = "97C";
		region.regionName = lanInfo.getValue("lanName") ;
		region.regionCode = lanInfo.getValue("lanCode");
		region.regionDesc = lanInfo.getValue("lanDesc") ;
		region.areaCode = lanInfo.getValue("areaCode");
		region.ngnFlag = lanInfo.getValue("ngnFlag");
		region.flag = lanInfo.getValue("flag");
		region.isActualRegion = lanInfo.getValue("isActualRegion");
		region.provinceCode = lanInfo.getValue("provinceCode");

		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		var lan = new Lan() ;
		for( ele in lan ){
			if( lanInfo.getField( ele )){
				lan[ele] = lanInfo.getValue( ele ) ;
			}
		}
		
		var arr = new Array();
		arr[0] = region;
		arr[1] = lan ;
		ajaxCall.remoteCall("RegionService","updateLan",arr,callBack);
			
	}else if ( regionLevel == "97D" ){
		if( !$("businessInfoForm").validate()){
			enableDataset();
			return;
		}
		var region = new Region() ;
		region.regionLevel = "97D";
		region.regionName = businessInfo.getValue("businessName");
		region.regionCode = businessInfo.getValue("businessCode");
		region.regionDesc = businessInfo.getValue("businessDesc");
		region.ngnFlag = businessInfo.getValue("ngnFlag");
		region.isActualRegion = businessInfo.getValue("isActualRegion");
		region.provinceCode = businessInfo.getValue("provinceCode");

//		if( region.regionCode.substring(0,2) != selItem.regionCode){
//			alert("Ӫҵ�������ǰ��λ�����Ǳ���������("+selItem.regionCode+") ��");
//			businessInfo.setValue("businessCode", selItem.regionCode);
//			enableDataset();
//			return ;
//		}

		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		var business = new Business() ;
		for( ele in business ){
			if( businessInfo.getField( ele )){
				business[ele] = businessInfo.getValue( ele ) ;
			}
		}
		
		var arr = new Array();
		arr[0] = region;
		arr[1] = business ;
		ajaxCall.remoteCall("RegionService","updateBusiness",arr,callBack);
	}
}

function btn_cancel_onClick(){
	actionType = "";
	nodeType = "" ;
	disableDataset();
	clickRegion();
}

function btn_commitRegion_onClick(){
	if(actionType=="") return ;
	if(!fieldVerify()) return;
	disableDataset();
	if( actionType == "insert" ){
		commitInsertRegion() ;
	}else if ( actionType == "update" ){
		commitUpdateRegion();
	}
}

function fieldVerify(){
	var regionLevel = document.all.regionTreeView.selectedItem.regionLevel;

	if((regionLevel=="97A" && actionType=="insert"  && nodeType=="root") ||(regionLevel=="97A" && actionType=="update")){
		if(!$("regionInfoForm").validate()) return false;
	}
	if((regionLevel=="97A" && actionType=="insert" &&nodeType=="child") ||(regionLevel=="97B" && actionType=="update")){
		if(!$("provinceInfoForm").validate()) return false;
	}
	if((regionLevel=="97B" && actionType=="insert") ||(regionLevel=="97C" && actionType=="update")){
		if(!$("lanInfoForm").validate()) return false;
	}
	if((regionLevel=="97C" && actionType=="insert") ||(regionLevel=="97D" && actionType=="update")){
		if(!$("businessInfoForm").validate()) return false;
	}
	if((regionLevel=="97D" && actionType=="insert") ||(regionLevel=="97E" && actionType=="update")){
		if(!$("exchangeInfoForm").validate()) return false;
	}
	return true;
}

//��Update��ǰ�������Ժ󣬸���TreeList�϶�Ӧ�ļ�¼
function refreshTreeList(){
	var regionTree = document.all.regionTreeView;
	var region = regionTree.selectedItem;
	var regionLevel = region.regionLevel ;		
	
	if( regionLevel == "97A" ){//����	
		for(var ele in region){
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
	}
	if( regionLevel == "97B" ){//ʡ��˾
		region.regionName = provinceInfo.getValue("prvName" ) ;
		region.regionCode = provinceInfo.getValue("prvCode" ) ;
		region.regionDesc = provinceInfo.getValue( "prvDesc" ) ;
		region.ngnFlag = provinceInfo.getValue("ngnFlag");
		region.isActualRegion = provinceInfo.getValue("isActualRegion");
		region.provinceCode = provinceInfo.getValue("provinceCode");
	}
	
	if( regionLevel == "97C" ){//������
		region.regionName = lanInfo.getValue("lanName" ) ;
		region.regionCode = lanInfo.getValue("lanCode" ) ;
		region.regionDesc = lanInfo.getValue( "lanDesc" ) ;
		region.ngnFlag = lanInfo.getValue("ngnFlag");
		region.isActualRegion = lanInfo.getValue("isActualRegion");
		region.provinceCode = lanInfo.getValue("provinceCode");

	}
	
	if( regionLevel == "97D" ){//Ӫҵ��
		region.regionName = businessInfo.getValue("businessName" ) ;
		region.regionCode = businessInfo.getValue("businessCode" ) ;
		region.regionDesc = businessInfo.getValue( "businessDesc" ) ;	
		region.ngnFlag = businessInfo.getValue("ngnFlag");
		region.isActualRegion = businessInfo.getValue("isActualRegion");
		region.provinceCode = businessInfo.getValue("provinceCode");

	}
	
	if( regionLevel == "97E" ){//����
		region.regionName = exchangeInfo.getValue("regionName" ) ;
		region.regionCode = exchangeInfo.getValue("regionCode" ) ;
		region.regionDesc = exchangeInfo.getValue( "regionDesc" ) ;	
		region.ngnFlag = exchangeInfo.getValue("ngnFlag");
		region.isActualRegion = exchangeInfo.getValue("isActualRegion");
		region.provinceCode = exchangeInfo.getValue("provinceCode");
	}
		
	region.refresh();
}

function refreshDatainfo(id){
	regionInfo.setValue("regionId", id ) ;
	
	var selItem = regionTreeView.selectedItem ;
	var regionLevel ;//��ǰ��ѡ�е�����ļ���
	if( selItem == null ){
		regionLevel == "" ;
	}else{
		regionLevel = selItem.regionLevel ;
	}
	
	if(  nodeType == "root" ){
		regionInfo.setValue("regionId", id ) ;
	}else {
		if( regionLevel == "97A" ){
			provinceInfo.setValue( "provId", id ) ;
		}
		if( regionLevel == "97B" ){
			lanInfo.setValue( "lanId", id ) ;
		}
		if( regionLevel == "97C" ){
			businessInfo.setValue( "businessId" , id ) ;
		}
		if( regionLevel == "97D" ){
			exchangeInfo.setValue( "regionId" , id ) ;
		}
	}	
}

//�����������ϲ���һ����¼
function addRegionToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}

//�����������ϲ���һ���ӽڵ��¼
function addChildToTreeList(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	var region = regionTree.createTreeNode();
	
	for(var ele in selItem){
		if(regionInfo.getField(ele)){
			region[ele] = regionInfo.getValue( ele );
		} 
	}
	
	if( selItem.regionLevel == "97A" ){//���ӵ���ʡ��˾
		region.regionId = provinceInfo.getValue( "provId" ) ;
		region.regionName = provinceInfo.getValue( "prvName" ) ;
		region.regionCode = provinceInfo.getValue( "prvCode") ;
		region.regionDesc = provinceInfo.getValue( "prvDesc" ) ;
		region.ngnFlag = provinceInfo.getValue("ngnFlag");
		region.isActualRegion = provinceInfo.getValue("isActualRegion");
		region.provinceCode = provinceInfo.getValue("provinceCode");
		region.regionLevel = "97B" ;
	}
	
	if( selItem.regionLevel == "97B" ){//���ӵ��Ǳ�����
		region.regionId = lanInfo.getValue("lanId") ;
		region.regionName = lanInfo.getValue("lanName") ;
		region.regionCode = lanInfo.getValue("lanCode") ;
		region.regionDesc = lanInfo.getValue("lanDesc");
		region.ngnFlag = lanInfo.getValue("ngnFlag");
		region.isActualRegion = lanInfo.getValue("isActualRegion");
		region.provinceCode = lanInfo.getValue("provinceCode");		
		region.regionLevel = "97C" ;		
	}
	
	if( selItem.regionLevel == "97C" ){//���ӵ���Ӫҵ��
		region.regionId = businessInfo.getValue("businessId");
		region.regionName = businessInfo.getValue("businessName") ;
		region.regionCode = businessInfo.getValue("businessCode") ;
		region.regionDesc = businessInfo.getValue("businessDesc") ;
		region.ngnFlag = businessInfo.getValue("ngnFlag");
		region.isActualRegion = businessInfo.getValue("isActualRegion");
		region.provinceCode = businessInfo.getValue("provinceCode");		
		region.regionLevel = "97D" ;		
	}
	
	if( selItem.regionLevel == "97D" ){//���ӵ��Ǿ���
		region.regionId = exchangeInfo.getValue( "regionId" ) ;
		region.regionName = exchangeInfo.getValue( "regionName" ) ;
		region.regionCode = exchangeInfo.getValue( "regionCode" ) ;
		region.regionDesc = exchangeInfo.getValue( "regionDesc" ) ;
		region.ngnFlag = exchangeInfo.getValue("ngnFlag");
		region.isActualRegion = exchangeInfo.getValue("isActualRegion");
		region.provinceCode = exchangeInfo.getValue("provinceCode");		
		region.regionLevel = "97E" ;		
	}
	region.parentRegionId = selItem.regionId ;
		
	selItem.add(region);
	
	region.setSelected();
}

//����������һ�����ڵ�
function addRootToTreeList(){
	var regionTree = document.all.regionTreeView;
	var region = regionTree.createTreeNode();
	
	var region1 = new Region();
	 
	for(var ele in region1){
		if(regionInfo.getField(ele)){
			region[ele] = regionInfo.getValue( ele );
		} 
	}
	
	regionTree.add(region);
	//�Ѹýڵ�ѡ���ϣ�����������룬���鲻Ҫѡ��
	region.setSelected();
}

var actionType = "";
//var regionLevel="";
var nodeType = "";

//����һ���¼�����
function addSubRegion_onClick(){
	if( actionType != "" ) {
		alert("���ȱ������ȡ����ǰ��������������!");
		return;
	}
	var selItem = regionTreeView.selectedItem ;
	if( selItem == null ){
		return ;
	}
	if( selItem.regionLevel == "97E" ){
		alert("��ǰ�ڵ��ǽ�����,�����ڽ�������������������!");
		return ;
	}
	actionType = "insert";
	nodeType = "child";
	addRegion( getCurrentRegionId() ) ;
}

//����һ��������
function addRootRegion_onClick(){ 
	if( actionType != "" ) {
		alert("���ȱ������ȡ����ǰ��������������!");
		return;
	}
	actionType = "insert";
	nodeType = "root" ;
	addRegion( null ) ;
}

function getBusinessInfo( actionName, actionMethod, lanId ) {
	return getRegionObject( actionName, actionMethod, lanId ) ;
}
function getExchangeInfo( actionName, actionMethod, regionId ){
	return getRegionObject( actionName, actionMethod, regionId ) ;
}

function setExchangeInfo( exchangeObj ){
	exchangeInfo.clearData() ;
	exchangeInfo.insertRecord( null ) ;
	for( var ele in exchangeObj ) {
		exchangeInfo.setValue( ele , exchangeObj[ele] );
	}
}
function setBusinessInfo( businessObj ){
	businessInfo.clearData();
	businessInfo.insertRecord( null ) ;
	for( var ele in businessObj ){
		if( businessInfo.getField( ele )){
			businessInfo.setValue( ele, businessObj[ele] ) ;
		}
	}
}


//��TabPage֮���л���ʱ�򴥷������л����Ŷι���ҳ���ʱ��ͨ����ǰ��
//����������ID���������˻�ȡ��������Ӧ�ĺŶ���Ϣ����ʾ��TreeList�С�
function exchangePage_onPageChanged(page,index){
		return;
}

function exchangePage_beforePageChanged( page, index ){
	if( actionType != "" && index == 0) {
		alert("���ȵ��'ȷ��'��ť���浱ǰ�������л���ǩҳ!");
		return false ;
	}
	return true ;
}

function queryNbrList(){
	var currentRegion = getCurrentRegionId();//��ȡ����TreeList�е�ǰ���������ID

	var parameterSet = accNbrList.parameters();
	parameterSet.setDataType("regionId","string");
	parameterSet.setValue("regionId", currentRegion);
	Dataset.reloadData( accNbrList );
}

function initAccNbrList(){
}

//����һ�������
function addAccNbr_onClick(){
	var arr = new Array() ;
	arr[0] = exchangeInfo;
	arr[1] = "insert" ;
	var selItem = regionTreeView.selectedItem ;
	var parentItem = getParentRegionById( selItem.parentRegionId );
	arr[2] = parentItem.regionId ;//Ӫҵ��ID
	arr[3] = parentItem.regionName ;//Ӫҵ������
	 
	var returnValue=window.showModalDialog("ExchangeDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue ){ 
		queryNbrList();
	}
}

//�༭һ�������
function editAccNbr_onClick(){
	//var selItem = document.all.accNbrTreeView.selectedItem;
	//if( selItem == null ) return ;
	//����ֵ����
	var exchangeObj = new Exchange();

	exchangeObj.exchangeId = accNbrList.getValue("exchId");// selItem.exchId;// exchangeInfo.getValue( "regionId" ) ;
	exchangeObj.regionId = exchangeInfo.getValue("regionId") ;
	exchangeObj.exchangeCode = exchangeInfo.getValue("regionCode");
	exchangeObj.exchangeName = exchangeInfo.getValue("regionName");
	exchangeObj.accNbrBegin = accNbrList.getValue("accNbrBegin");// selItem.accNbrBegin ;
	exchangeObj.accNbrEnd =  accNbrList.getValue("accNbrEnd");// selItem.accNbrEnd; 
	exchangeObj.state = accNbrList.getValue("state");// selItem.state;
	exchangeObj.effDate = accNbrList.getValue("effDate");// selItem.effDate ;
	exchangeObj.expDate = accNbrList.getValue("expDate");// selItem.expDate ;
	exchangeObj.areaId = accNbrList.getValue("areaId");// selItem.areaId;
	exchangeObj.prodFamilyId = accNbrList.getValue("prodFamilyId");// selItem.prodFamilyId ;
	exchangeObj.prodFamilyName = accNbrList.getValue("prodFamilyName");// selItem.prodFamilyName;
	
	var arr = new Array() ;
	arr[0] = exchangeObj;
	arr[1] = "update" ;
	var selItem = regionTreeView.selectedItem ;
	var parentItem = getParentRegionById( selItem.parentRegionId );
	arr[2] = parentItem.regionId ;//Ӫҵ��ID
	arr[3] = parentItem.regionName ;//Ӫҵ������
		
	var returnValue=window.showModalDialog("ExchangeDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue ){ 
		queryNbrList();
		//refreshAccNbrTreeList( returnValue ) ;
		//accNbrTreeView.selectedItem.exchId = returnValue["exchId"] ;
	}
}

/*
//�ڱ༭��һ��������Ժ���������ϵ�����
function refreshAccNbrTreeList( exchangeItem ){
	var accNbrTree = document.all.accNbrTreeView;
	var accNbr = accNbrTree.selectedItem;
	for(var ele in accNbr){
		if( exchangeItem[ele] ){
			accNbr[ele] = exchangeItem[ele] ;
		}
	} 
	accNbr.refresh();
}
*/

//ɾ��һ�������
function deleteAccNbr_onClick(){ 
	//var selItem = document.all.accNbrTreeView.selectedItem ;
	//if( selItem == null ) return ;
	var exchangeId = accNbrList.getValue("exchId");// selItem.exchId ;

	var ajaxCall = new NDAjaxCall(true); 
	var callBack = function(reply){
		var result = reply.getResult() ;
		if( result ){
			alert("ɾ������γɹ���");
			queryNbrList();
		}else {
			alert( "ɾ�������ʧ��!") ;
		}
	}
	ajaxCall.remoteCall("RegionService","deleteAccNbrOfExch",[exchangeId],callBack);
}

function Region(){
	this.regionId = "" ;
	this.parentRegionId = "" ;
	this.parentRegionName = "" ;
	this.configId = "" ;
	this.regionLevel = "" ;
	this.regionName = "" ;
	this.regionDesc = "" ;
	this.regionCode = "" ;
	this.pathCode = "" ;
	this.pathName = "" ;
	this.ngnFlag = "";
	this.isActualRegion = "";
	this.provinceCode = "";
}

//ʡ��ֵ����
function Province(){
	this.provId  = "";
	this.prvCode = "" ;
	this.prvName = "" ;
	this.prvFlag = "" ;
	this.prvDesc = "" ;
}

//������ֵ����
function Lan(){
	this.provId  = "";
	this.lanId = "" ;
	this.lanCode = "";
	this.lanName = "" ;
	this.lanDesc = "";
	this.flag = "" ;
	this.areaCode = "";
}

//Ӫҵ��ֵ����
function Business(){
	this.lanId = "" ;
	this.lanName = "" ;
	this.businessId = "" ;
	this.businessCode = "" ;
	this.businessName = "" ;
	this.businessDesc = "" ;
}

//����ֵ����
function Exchange(){
	this.exchId = "" ;
	this.regionId = "" ;
	this.exchCode = "" ;
	this.exchName = "" ;
	this.accNbrBegin = "" ;
	this.accNbrEnd = "" ;
	this.state = "" ;
	this.effDate = "" ;
	this.expDate = "" ;
	this.areaId = "" ;
	this.prodFamilyId = "" ;
}

function setLanInfo( lanObj ){
	lanInfo.clearData();
	lanInfo.insertRecord( null ) ;
	for( var ele in lanObj ) {
		if( lanInfo.getField(ele)){
			lanInfo.setValue( ele, lanObj[ele] ) ;
		}
	}
}
//ʹ��ֵ��������ʡ�����ݼ�����
function setProvinceInfo( provinceObj ){

	provinceInfo.clearData();
	provinceInfo.insertRecord(null) ;
	for(var ele in provinceObj){
		if(provinceInfo.getField(ele)){
			provinceInfo.setValue(ele, provinceObj[ele]);
		}
	}
}

//ͨ��regionId�ӷ������˻�ȡ�������ʶ��Ӧ��ʡ��ֵ����
function getProvinceInfo( actionName, actionMethod, regionId ) {
	return getRegionObject( actionName, actionMethod, regionId ) ;
}

function getLanInfo( actionName, actionMethod, provinceId ) {
	return getRegionObject( actionName, actionMethod, provinceId ) ;
}

//�ӷ������˻�ȡ��������ص�ֵ������Ϣ
function getRegionObject( actionName, methodName, objId ){
	var ajaxCall = new NDAjaxCall(false);
	var obj ;
	var callBack = function( reply ){
		obj = reply.getResult() ;
	}
	ajaxCall.remoteCall(actionName,methodName,[objId],callBack);
	return obj ;
}
