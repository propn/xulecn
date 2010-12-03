/**
97A        ����
97B        ʡ
97C        ������
97D        Ӫҵ��
98D        �����
98E        ĸ��
98F        ��վ
*/
function checkExistRegionCode( regionCode ) {
	var ajaxCall = new NDAjaxCall( false ) ;//ͬ����ʽ��ѯ������������
	var result ;
	var callBack = function( reply ) {
		result = reply.getResult() ;
	}
	
	ajaxCall.remoteCall("RegionService", "checkExistRegionCode",[regionCode], callBack ) ;
	return result ;
}

var menuCode = "";
function page_onLoad(){
	menuCode = Global.getCurrentMenuCode();//20202020
  initRegion();
}

//��ʼ��TreeList�����������ʾ
function initRegion(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		
		document.all.regionTreeView.loadByXML(queryResult);
		if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
			alert("��û���κ�����Ȩ��!");
			return ;
		}
		
		//����ϸ����Ӧ��Dataset�ϲ���һ���ռ�¼
		regionInfo.insertRecord(null);
		
		globleItem = document.all.regionTreeView.items[0];
		fillValues(globleItem);
	}
	//ajaxCall.remoteCall("RegionService","getResourceRegionList",["-1"],callBack);
	ajaxCall.remoteCall("RegionService","getRootResourceRegionListByPrivControl",[menuCode],callBack);	
}

//�����ϸ����ϵ���Ϣ
function cleanValues(){
	regionInfo.clearData();//���Ź�˾
	regionInfo.insertRecord( null ) ;
	
	provinceInfo.clearData();//ʡ��˾
	provinceInfo.insertRecord(null);
	
	lanInfo.clearData();//������
	lanInfo.insertRecord( null ) ;
	
	businessInfo.clearData() ;//Ӫҵ��
	businessInfo.insertRecord(null);
	
	ppdomInfo.clearData();//Ͻ��
	ppdomInfo.insertRecord( null ) ;
	
	exchInfo.clearData() ;//��Դ�����
	exchInfo.insertRecord( null ) ;
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
		}
		provinceInfo.setValue("prvDesc", selItem.regionDesc);
		provinceInfo.setValue("ngnFlag", selItem.ngnFlag);
		hidPannels();
		document.all.provincePannel.style.display = "block" ;
		
	}else if ( regionLevel == "97C" ){//������
		var lan = new Lan() ;
		var regionId = selItem.regionId ;
		lan = getLanInfo( "RegionService", "getLanByRegionId", regionId );//���������ʶ���������˻�ȡ�͸������ʶ��Ӧ�ı�������Ϣ
		if( lan != null ){
			setLanInfo( lan ) ;
			lanInfo.setValue("lanDesc",selItem.regionDesc);
			lanInfo.setValue("lanCode",selItem.regionCode);
			lanInfo.setValue("ngnFlag", selItem.ngnFlag);
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
		}
		hidPannels() ;
		document.all.businessPannel.style.display = "block" ;	
	}else if ( regionLevel == "98D" ){
		hidPannels();
		document.all.ppdomPannel.style.display ="block";
	}else if( regionLevel == "98E" ) {
		hidPannels();
		document.all.exchPannel.style.display ="block";
	}else if( regionLevel == "98F"  ){
		hidPannels();
		document.all.stationPannel.style.display ="block";
	}
}

function enableDataset(){
	regionInfo.setReadOnly( false ) ;
	provinceInfo.setReadOnly( false ) ;
	lanInfo.setReadOnly(false);
	businessInfo.setReadOnly(false);
	ppdomInfo.setReadOnly( false ) ;
	exchInfo.setReadOnly( false ) ;
	
	var selItem = document.all.regionTreeView.selectedItem;
	if( selItem == null ){
		return ;
	}
	if( actionType == "update") {
	  if(selItem.regionLevel == "98E"){
	    regionInfo.setFieldReadOnly("virtualDealFlag",false);
	  }
	  else{
		regionInfo.setFieldReadOnly("virtualDealFlag",true);		
	  }
	}
	else if( actionType == "insert" ){
	
	  if( selItem.regionLevel =="98D"){		
		   regionInfo.setFieldReadOnly("virtualDealFlag",false);		
		}
	  else{
	       regionInfo.setFieldReadOnly("virtualDealFlag",true);
	  }	
	}
	 
	if( selItem.regionLevel == "97A"){//ѡ�м���
		if( actionType == "update" ){
			regionInfo.setFieldReadOnly("regionLevel" , true );
            regionInfo.setPopupEnable("partyName",true);
           
		}else if( actionType =="insert"){
			if( nodeType == "root" ){
				regionInfo.setFieldReadOnly("regionLevel" , true );
				regionInfo.setPopupEnable("partyName",true);
			}
		}
	}
	if( selItem.regionLevel == "97B" ){//ѡ��ʡ��˾		
		if( actionType == "update"){
           provinceInfo.setPopupEnable("partyName",true);
		}
		else  if( actionType == "insert" ){
			lanInfo.setFieldReadOnly("provName",true);
			lanInfo.setPopupEnable("partyName",true);
		}
	}
	if( selItem.regionLevel == "97C" ){//ѡ�б�����
		if( actionType == "update" ){
			lanInfo.setFieldReadOnly("provName",true);
			lanInfo.setPopupEnable("partyName",true);
		}else if( actionType == "insert"){ 
			businessInfo.setFieldReadOnly("lanName",true);
			businessInfo.setPopupEnable("partyName",true);
		}
	}
	if( selItem.regionLevel == "97D" ){//Ӫҵ��
		if( actionType == "update" ){
			businessInfo.setFieldReadOnly("lanName",true);
            businessInfo.setPopupEnable("partyName",true);
		}else if ( actionType == "insert" ){
			regionInfo.setFieldReadOnly("regionLevel" , true );
			regionInfo.setPopupEnable("partyName",true);
		}
	}
	if( selItem.regionLevel == "98D" ){//�����
		regionInfo.setFieldReadOnly("regionLevel" , true );
	}
	
	if( selItem.regionLevel == "98E" ){//ѡ��ĸ��
		regionInfo.setFieldReadOnly("regionLevel" , true );

	}
	if( selItem.regionLevel == "98F" ){
		
	}
	regionInfo.setFieldReadOnly("partyName",true);
	provinceInfo.setFieldReadOnly("partyName",true);
	lanInfo.setFieldReadOnly("partyName",true);
	businessInfo.setFieldReadOnly("partyName",true);
}
function disableDataset(){
	regionInfo.setReadOnly( true ) ;
	provinceInfo.setReadOnly( true ) ;
	lanInfo.setReadOnly(true);
	businessInfo.setReadOnly(true);
	ppdomInfo.setReadOnly( true ) ;
	exchInfo.setReadOnly( true ) ;
}

//�༭����
function editRegion_onClick(){
		if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
			alert("��û���κ�����Ȩ��!");
			return ;
		}
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�����ٱ༭����");
		return ;
	}
	actionType = "update" ;
	enableDataset();
}

//����һ���¼�����
function addSubRegion_onClick(){
	if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
		alert("��û���κ�����Ȩ��!");
		return ;
	}
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ��������������");
		return ;
	}
	var selItem = regionTreeView.selectedItem ;
	if( selItem == null ){
		return ;
	}
	/*
	if( selItem.regionLevel == "98E" && selItem.virtualDealFlag == "T" ){
		alert("��ǰ����������ĸ��,���������¼�����!");
		return ;
	}*/	
	if( selItem.regionLevel == "98F" ){
		alert("��ǰ�ڵ��Ǿ�վ,�����ھ�վ��������������!");
		return ;
	}
	actionType = "insert";
	nodeType = "child";
	addRegion( getCurrentRegionId() ) ;
}

//����һ��������
function addRootRegion_onClick(){  
	if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
		alert("��û���κ�����Ȩ��!");
		return ;
	}
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ��������������");
		return ;
	}
	actionType = "insert";
	nodeType = "root" ;
	addRegion( null ) ;
}

//��������
function addRegion(parentRegionId){
	enableDataset();
	
	cleanValues();
	hidPannels();
	
	if( parentRegionId == null ){//����һ��������
		document.all.groupPannel.style.display = "block" ;//��ʾ������Ϣ���
		regionInfo.setValue("regionLevel", "97A");
		regionInfo.setFieldReadOnly("virtualDealFlag",true);
		regionInfo.setFieldReadOnly("partyName",true);
		return ;
	}
	
	if( parentRegionId != null ){//�������һ���ϼ�������������������
		var item = document.all.regionTreeView.selectedItem ;//��ǰ��ѡ�еļ�¼
		
		regionInfo.setValue("parentRegionName", item.regionName ) ;//�����ϼ����������
		regionInfo.setValue("parentRegionId",item.regionId);//�����ϼ������ID
		
		var regionLevel = item.regionLevel ;
			
		if( regionLevel == "97A" ){//�ڼ��Ź�˾���棬���ӵ���ʡ��˾����
			document.all.provincePannel.style.display = "block";
			regionInfo.setValue("regionLevel", "97B"); 	
			provinceInfo.setFieldReadOnly("partyName", true);
		}else if ( regionLevel == "97B" ){//��ʡ��˾���棬���ӵ��Ǳ��������� 
			document.all.lanPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "97C"); 
			//��������Dataset�����ϼ��������ƣ�����ʡ�����ƣ����ϼ�����ID
			 lanInfo.setValue("provId", item.regionId);
			 lanInfo.setValue("provName",item.regionName);
			 
			 lanInfo.setFieldReadOnly("partyName",true);
		}else if ( regionLevel == "97C" ){//�ڱ���������,���ӵ���Ӫҵ��
			document.all.businessPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "97D");
			//��Ӫҵ��Dataset�����ϼ����������ƺ�ID
			businessInfo.setValue("lanId", item.regionId);
			businessInfo.setValue("lanName", item.regionName);		
			businessInfo.setFieldReadOnly("partyName",true);
		}else if( regionLevel == "97D" ){//��Ӫҵ�����棬���ӵ��Ǵ����
			document.all.ppdomPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "98D");
			
			//regionInfo.setFieldReadOnly("partyName",true);
		}else if ( regionLevel == "98D" ){//�ڴ��������,���ӵ���ĸ��
			document.all.exchPannel.style.display = "block" ;
		     
			regionInfo.setValue("regionLevel", "98E");
			//regionInfo.setFieldReadOnly("partyName",true);
		}else if ( regionLevel == "98E" ){//��ĸ������,���ӵ��Ǿ�վ
			document.all.stationPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "98F");
			//regionInfo.setFieldReadOnly("partyName",true);
		}
	   /*.
		 if( regionLevel != "98D" ){
		 	regionInfo.setFieldReadOnly("virtualDealFlag",true);
		 }else{
		 	regionInfo.setFieldReadOnly("virtualDealFlag",false);
		 }
		 */
	}
}

function btn_cancel_onClick(){
	actionType = "" ;
	nodeType = "" ;
	disableDataset();
	clickRegion() ;
}

//�ύ��ť��Ӧ�¼�
function btn_commitRegion_onClick(){
	var regionName=regionInfo.getValue("regionName");
	if(actionType == "" ){
		return;
	}
	if(regionName.length>50){
		alert("������������������ѳ�����󳤶ȣ�50�����֣������������룡");
		return;
	}
	
	disableDataset();
	if( actionType == "insert" ){
		commitInsertRegion() ;
	}else if ( actionType == "update" ){
		commitUpdateRegion();
	}
}

function validateBusinessForm(){
	if( businessInfo.getValue("businessCode") == "" ){
		alert("������Ӫҵ������!");
		return false ;
	}
	if( businessInfo.getValue("businessCode").length > 3 ){
		alert("������3λ���ڵ�Ӫҵ������!");
		return false ;
	}
	if( businessInfo.getValue("businessName") == "" ){
		alert("������Ӫҵ������!");
		return false ;
	}
	return true ;
}

function validateLanForm(){
	if( lanInfo.getValue( "lanCode" )== "" ){
		alert( "�����뱾��������!");
		return false ;
	}
	if( lanInfo.getValue( "lanName" )== "" ){
		alert( "�����뱾��������!");
		return false ;
	}
	if( lanInfo.getValue( "areaCode" )== "" ){
		alert( "������绰����!");
		return false ;
	}
	if( lanInfo.getValue( "flag" )== "" ){
		alert( "�����뱾�ر�־!");
		return false ;
	}
	return true ;
}

function validateProvinceForm(){
	if( provinceInfo.getValue("prvCode") == "" ){
		alert("������ʡ�ݴ���!");
		return false ;
	}
	if( provinceInfo.getValue("prvName") == "" ){
		alert("������ʡ������!");
		return false ;
	}
	if( provinceInfo.getValue("prvFlag") == "" ){
		alert("�����뱾ʡ��־!");
		return false ;
	}
	return true ;
}

function validateRegionForm(){
	if( regionInfo.getValue("regionName") == "" ){
		alert("��������������!");
		return false ;
	}
	if( regionInfo.getValue("regionCode") == "" ){
		alert("�������������!");
		return false ;
	}
	return true ;
}

//�ύ����һ�����������
function commitInsertRegion(){
	if( actionType == "" ){
		return ;
	}
	
	var actionResult = null ;
	
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		actionResult = reply.getResult() ;//����������˲������صĽ��
		//�������˷��������ӵ������ID
		refreshDatainfo(actionResult); //��������Ժ󣬱������³�ʼ��������Dataset�������Ӧ��Ϣ	
		addRegionToTreeList();
		actionType = "" ;
		nodeType = "" ;		
		alert("�����ɹ���");
	}
	
	var regionTreeView = document.all.regionTreeView ;
	var selItem = regionTreeView.selectedItem;
	var regionLevel ;//��ǰ��ѡ�е�����ļ���
	if( selItem == null ){
		regionLevel == "" ; 
	}else{
		regionLevel = selItem.regionLevel ;
	}
	//��������ǡ����Ź�˾������nodeType��root����˵��Ҫ���Ӽ��Ź�˾���ڵ�
	if(nodeType == "root" ){
		//if( !$("regionInfoForm").validate()){
		if( !validateRegionForm()){
			enableDataset() ;
			return ;
		}
		//������������ҽ�Dataset�е�ֵ��ֵ��������
		var region = new Region() ;
		for(var ele in region){ 
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
		if( checkExistRegionCode( region.regionCode )){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		region.parentRegionId = "-1" ;//�ϼ�����IDΪ��
		region.configId = "" ;
		region.regionLevel = "97A" ;
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
				
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		
	}else{//���ӵ����ӽڵ�
		//������򼶱��ǡ����Ź�˾�������ӵľ���ʡ��˾��
		if( regionLevel == "97A" ){
			//if( !$("provinceInfoForm").validate()){
			if( !validateProvinceForm()){
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
			region.partyId = provinceInfo.getValue("partyId");
            region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
			if( checkExistRegionCode( region.regionCode )){
				alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
				enableDataset();
				return ;
			}
		
			//ʡ�ݱ�
			var province = new Province() ;
		    province[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RrProvinceVO';
			for( ele in province ){
				if( provinceInfo.getField( ele )){
					province[ele] = provinceInfo.getValue( ele ) ;
				}
			}
			
			var arr = new Array();
			arr[0] = region;
			arr[1] = province ;
			ajaxCall.remoteCall("RegionService","insertProvince",arr,callBack);
			
		}else if ( regionLevel == "97B" ){//��ʡ��˾���棬���ӵ��Ǳ���������
			//if( !$("lanInfoForm").validate()){
			if(!validateLanForm()){
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
			region.ngnFlag = lanInfo.getValue("ngnFlag");
			region.flag = lanInfo.getValue("flag");
			region.partyId = lanInfo.getValue("partyId");
			region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
		if( checkExistRegionCode( region.regionCode )){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
			//������
			var lan = new Lan() ;
			lan[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RrLanVO';
			for( ele in lan ){
				if( lanInfo.getField( ele )){
					lan[ele] = lanInfo.getValue( ele ) ;
				}
			}
			
			var arr = new Array();
			arr[0] = region;
			arr[1] = lan ;
			ajaxCall.remoteCall("RegionService","insertLan",arr,callBack);
			
		}else if ( regionLevel == "97C" ){//�ڱ��������棬���ӵ���Ӫҵ������
			//if( !$("businessInfoForm").validate()){
			if( !validateBusinessForm()){
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
			region.configId = "";
			region.partyId = businessInfo.getValue("partyId") ;
			region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
			if( checkExistRegionCode( region.regionCode )){
				alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
				enableDataset();
				return ;
			}
		
			//ʡ�ݱ�
			var business = new Business() ;
			business[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RrBusinessVO';
			for( ele in business ){
				if( businessInfo.getField( ele )){
					business[ele] = businessInfo.getValue( ele ) ;
				}
			}
			
			var arr = new Array();
			arr[0] = region;
			arr[1] = business ;
			ajaxCall.remoteCall("RegionService","insertBusiness",arr,callBack);
		}else if( regionLevel == "97D" ){//Ӫҵ������,���ӵ��Ǵ����
			//if( !$("regionInfoForm").validate()){//û��ͨ������У��
			if( !validateRegionForm()){
				enableDataset() ;
				return ;
			}
			// regionInfo.getValue("virtualDealFlag") == ""
			
			//������������ҽ�Dataset�е�ֵ��ֵ��������
			var region = new Region() ;
			for(var ele in region){ 
				if(regionInfo.getField(ele)){
					region[ele] = regionInfo.getValue( ele );
				} 
			}			
			region.parentRegionId = selItem.regionId ;//�ϼ�����ID
			region.configId = "" ;
			region.regionLevel = "98D" ;
			region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';		
			if( checkExistRegionCode( region.regionCode )){
				alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
				enableDataset();
				return ;
			}
		
			var arr = new Array();
			arr[0] = region;
			ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		}else if ( regionLevel == "98D" ){//�ڴ��������,���ӵ���ĸ��
			//if( !$("regionInfoForm").validate()){//û��ͨ������У��
			if( !validateRegionForm()){
				enableDataset() ;
				return ;
			}
			if( regionInfo.getValue("virtualDealFlag") == "" || regionInfo.getValue("virtualDealFlag") == null || regionInfo.getValue("virtualDealFlag") == "undefined"){
				alert("��ѡ�������־!");
				enableDataset() ;
				return ;
			}
			
			
			//������������ҽ�Dataset�е�ֵ��ֵ��������
			var region = new Region() ;
			region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
			for(var ele in region){ 
				if(regionInfo.getField(ele)){
					region[ele] = regionInfo.getValue( ele );
				} 
			}
			
			region.parentRegionId = selItem.regionId ;//�ϼ�����ID
			region.configId = "" ;
			region.regionLevel = "98E" ;
			
			if( checkExistRegionCode( region.regionCode )){
				alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
				enableDataset();
				return ;
			}
			
			var arr = new Array();
			arr[0] = region;
			ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		}else if ( regionLevel == "98E" ){//��ĸ������,���ӵ��Ǿ�վ
			//if( !$("regionInfoForm").validate()){//û��ͨ������У��
			if( !validateRegionForm()){
				enableDataset() ;
				return ;
			}
			if( regionInfo.getValue("ngnFlag") == "" || regionInfo.getValue("ngnFlag") == null || regionInfo.getValue("ngnFlag") == "undefined" ){
				alert("��ѡ��NGN��־!");
				enableDataset() ;				
				return ;
			}
			//������������ҽ�Dataset�е�ֵ��ֵ��������
			var region = new Region() ;
			region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
			for(var ele in region){ 
				if(regionInfo.getField(ele)){
					region[ele] = regionInfo.getValue( ele );
				} 
			}
			
			region.parentRegionId = selItem.regionId ;//�ϼ�����ID
			region.configId = "" ;
			region.regionLevel = "98F" ;
					
			if( checkExistRegionCode( region.regionCode )){
				alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
				enableDataset();
				return ;
			}
		
			var arr = new Array();
			arr[0] = region;
			ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		}
	}
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
	
	if( nodeType == "root" ){
		regionInfo.setValue("regionId", id ) ;
	}else {
		if( regionLevel == "97A" ){//ʡ��˾
			provinceInfo.setValue( "provId", id ) ;
		}
		if( regionLevel == "97B" ){//������ 
			lanInfo.setValue( "lanId", id ) ;
		}
		if( regionLevel == "97C" ){//Ӫҵ��
			businessInfo.setValue("businessId",id); 
		}
		if( regionLevel == "98D" || regionLevel == "98E" || regionLevel == "98F" ){//�����,ĸ��,��վ
			regionInfo.setValue( "regionId" , id ) ;
		}
	}	
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
	region.setSelected();
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
		region.regionLevel = "97B" ;
	}
	
	if( selItem.regionLevel == "97B" ){//���ӵ��Ǳ����� 
		region.regionId = lanInfo.getValue("lanId") ;
		region.regionName = lanInfo.getValue("lanName") ;
		region.regionCode = lanInfo.getValue("lanCode") ;
		region.regionDesc = lanInfo.getValue("lanDesc");
		region.ngnFlag = lanInfo.getValue("ngnFlag") ;
		region.regionLevel = "97C" ;		
	}
	
	if( selItem.regionLevel == "97C" ){//���ӵ���Ӫҵ
		region.regionId = businessInfo.getValue("businessId");
		region.regionName = businessInfo.getValue("businessName") ;
		region.regionCode = businessInfo.getValue("businessCode") ;
		region.regionDesc = businessInfo.getValue("businessDesc") ;
		region.ngnFlag = businessInfo.getValue("ngnFlag");
		region.regionLevel = "97D" ;	
		
	}

	if( selItem.regionLevel == "97D"){//�����
		region.regionLevel = "98D" 
	}
	
	if( selItem.regionLevel == "98D" ){//ĸ��
		region.regionLevel = "98E" 
	}
	
	if( selItem.regionLevel == "98E" ){// ��վ
		region.regionLevel = "98F" 
	}
	
	region.parentRegionId = selItem.regionId ;
	selItem.add(region);
	region.setSelected();
}

//�ύ����һ�����������
function commitUpdateRegion(){
	if( actionType == "" ) {
		return ;
	}
	
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
		nodeType ="" ;
		actionType = "" ;
	}
	
	//��������ǡ����Ź�˾��
	if( regionLevel == "97A" ){
		//if( !$("regionInfoForm").validate()){
		if( !validateRegionForm()){
			enableDataset();
			return;
		}
		//������������ҽ�Dataset�е�ֵ��ֵ��������
		var region = new Region() ;
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
		for(var ele in region){ 
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
		region.regionLevel = "97A" ;
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
		
	}else if( regionLevel == "97B" ){//ʡ��˾
		//if( !$("provinceInfoForm").validate()){
		if( !validateProvinceForm()){
			enableDataset();
			return;
		}
		var region = new Region() ; 
		region.regionName = provinceInfo.getValue("prvName") ;
		region.regionCode = provinceInfo.getValue("prvCode");
		region.regionDesc = provinceInfo.getValue("prvDesc") ;
		region.ngnFlag = provinceInfo.getValue("ngnFlag");
		region.regionLevel = "97B" ;
		region.partyId = provinceInfo.getValue("partyId");
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
		//ʡ�ݱ�
		var province = new Province() ;
		province[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RrProvinceVO';
		for( ele in province ){
			if( provinceInfo.getField( ele )){
				province[ele] = provinceInfo.getValue( ele ) ;
			}
		}
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;
		arr[1] = province ;
		ajaxCall.remoteCall("RegionService","updateProvince",arr,callBack);
			
	}else if ( regionLevel == "97C" ){//������
		//if( !$("lanInfoForm").validate()){
		if( !validateLanForm()){
			enableDataset();
			return ;
		}
		var region = new Region() ; 
		region.regionName = lanInfo.getValue("lanName") ;
		region.regionCode = lanInfo.getValue("lanCode");
		region.regionDesc = lanInfo.getValue("lanDesc") ;
		region.areaCode = lanInfo.getValue("areaCode");
		region.ngnFlag = lanInfo.getValue("ngnFlag");
		region.flag = lanInfo.getValue("flag");
		region.partyId = lanInfo.getValue("partyId");
		region.regionLevel = "97C" ;
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		var lan = new Lan() ;
		lan[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RrLanVO';
		for( ele in lan ){
			if( lanInfo.getField( ele )){
				lan[ele] = lanInfo.getValue( ele ) ;
			}
		}
		
		var arr = new Array();
		arr[0] = region;
		arr[1] = lan ;
		ajaxCall.remoteCall("RegionService","updateLan",arr,callBack);
			
	}else if( regionLevel == "97D" ){//Ӫҵ��
		//if( !$("businessInfoForm").validate()){
		if( !validateBusinessForm()){
			enableDataset();
			return;
		}
		var region = new Region() ;
		region.regionLevel = "97D";
		region.regionName = businessInfo.getValue("businessName");
		region.regionCode = businessInfo.getValue("businessCode");
		region.regionDesc = businessInfo.getValue("businessDesc");
		region.ngnFlag = businessInfo.getValue("ngnFlag");
		region.partyId = businessInfo.getValue("partyId");
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		var business = new Business();
		business[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RrBusinessVO';
		for( ele in business ){
			if( businessInfo.getField( ele )){
				business[ele] = businessInfo.getValue( ele ) ;
			}
		}
		
		var arr = new Array();
		arr[0] = region;
		arr[1] = business ;
		ajaxCall.remoteCall("RegionService","updateBusiness",arr,callBack);	
	}else if ( regionLevel == "98D" ){//�����
		/*
		if( selItem.items ){
			if( regionInfo.getValue("virtualDealFlag") == "T" ){
				alert("��ǰ��������¼�ĸ��,�����޸�Ϊ�����!");
				enableDataset();
				return ;
			}
		}
		*/
		if( !validateRegionForm()){
			enableDataset();
			return;
		}
		/*
		if( regionInfo.getValue("virtualDealFlag") == ""){
			alert("��ѡ�������־!");
			enableDataset() ;
			return ;
		}*/
		var region = new Region() ;
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
		for(var ele in region){ 
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
		region.regionLevel = "98D" ;
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;		
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
	}else if ( regionLevel == "98E" ){ //ĸ��
		//if( !$("regionInfoForm").validate()){
		if( !validateRegionForm()){
			enableDataset();
			return;
		}
		if( regionInfo.getValue("virtualDealFlag") == ""){
			alert("��ѡ�������־!");
			enableDataset() ;
			return ;
		}
		if( selItem.items ){
		if( regionInfo.getValue("virtualDealFlag") == "T" ){
				alert("��ǰĸ�����¼���վ,�����޸�Ϊ�����!");
				enableDataset();
				return ;
		}
		}
		var region = new Region() ;
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
		for(var ele in region){ 
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
		region.regionLevel = "98E" ;
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
	}else if( regionLevel == "98F" ){//��վ
		//if( !$("regionInfoForm").validate()){
		if( !validateRegionForm()){
			enableDataset();
			return;
		}
		var region = new Region() ;
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
		for(var ele in region){ 
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
		region.regionLevel = "98F" ;
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("ϵͳ���Ѿ����ڱ���Ϊ" + region.regionCode + "�ļ�¼��,��ʹ����������!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
	}
}

//��Update��ǰ�������Ժ󣬸���TreeList�϶�Ӧ�ļ�¼
function refreshTreeList(){
	var regionTree = document.all.regionTreeView;
	var region = regionTree.selectedItem;
	var regionLevel = region.regionLevel ;	
		
	if( regionLevel == "97A" ||  regionLevel == "98D" || regionLevel == "98E" || regionLevel == "98F"){//����		
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
	}
	
	if( regionLevel == "97C" ){//������
		region.regionName = lanInfo.getValue("lanName" ) ;
		region.regionCode = lanInfo.getValue("lanCode" ) ;
		region.regionDesc = lanInfo.getValue( "lanDesc" ) ;
		region.ngnFlag = lanInfo.getValue("ngnFlag");
	}
	
	if( regionLevel == "97D" ){//Ӫҵ��
		region.regionName = businessInfo.getValue("businessName" ) ;
		region.regionCode = businessInfo.getValue("businessCode" ) ;
		region.regionDesc = businessInfo.getValue( "businessDesc" ) ;	
		region.ngnFlag = businessInfo.getValue("ngnFlag");
	}
	
	region.refresh();
}

var actionType = "" ;
var nodeType = "" ;

//�����������ϲ���һ����¼
function addRegionToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}

function getBusinessInfo( actionName, actionMethod, lanId ) {
	return getRegionObject( actionName, actionMethod, lanId ) ;
}
function getPpdomInfo( actionName, methodName, regionId ){
	return getRegionObject( actionName, methodName, regionId ) ;
}
function getExchInfo( actionName, methodName, regionId){
	return getRegionObject( actionName, methodName, regionId ) ;
}
function setExchInfo( exchObj ){
	exchInfo.clearData();
	exchInfo.insertRecord( null ) ;
	for( var ele in exchObj ){
		if( exchInfo.getField( ele ) ){
			exchInfo.setValue( ele, exchObj[ele] ) ;
		}
	}
}
function setPpdomInfo( ppdomObj ){
	ppdomInfo.clearData();
	ppdomInfo.insertRecord( null ) ;
	for( var ele in ppdomObj ){
		if( ppdomInfo.getField( ele )){
			ppdomInfo.setValue( ele, ppdomObj[ele] ) ;
		}
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
//���������ɾ��һ����¼
function deleteRegionFromTreeList(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	selItem.remove();
}

//ɾ��һ������
function deleteRegion_onClick(){
    var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
			alert("��û���κ�����Ȩ��!");
			return ;
	}
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ������ɾ������");
		return ;
	}
	if(selItem.items){
	alert("����ɾ����ǰ����������������ٽ���ɾ������");
	return;
	}
	
	
	
	
	if( confirm( "��ȷ��Ҫɾ����ǰ������Ϣ��?" )){
		var currentRegionId = getCurrentRegionId();
		
		var ajaxCall = new NDAjaxCall(true);
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;	
			if( deleteResult > 0 ){		     
				deleteRegionFromTreeList();//����ǰ��¼��TreeList��ɾ��
				alert("ɾ��������Ϣ�ɹ���");
			}else if(deleteResult == -9999)
			{
				alert("����ɾ����ǰ����������������ٽ���ɾ������");		
			}
			else{
			    alert("ɾ��������Ϣʧ��!" );
			}
		}
		ajaxCall.remoteCall("RegionService","deleteRegion",[currentRegionId],callBack);
	}
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
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
		}
	}
	
	var regionId = selItem.regionId ;
	ajaxCall.remoteCall("RegionService","getResourceRegionList",[regionId], callBack); 
}

function getOrganizationByRegionId(){
	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		var organizationVO = reply.getResult() ;
		if( organizationVO == null ){
			return ;
		}
		var orgName = organizationVO.orgName ;
		var selItem = regionTreeView.selectedItem ;
		if( selItem != null ){
			if( selItem.regionLevel == "97A" ){
				regionInfo.setValue("partyName",orgName);
			}else if (selItem.regionLevel == "97B" ){ 
				provinceInfo.setValue("partyName",orgName);
			}else if ( selItem.regionLevel == "97C" ){ 
				lanInfo.setValue("partyName", orgName);
			}else if( selItem.regionLevel == "97D" ){
				businessInfo.setValue("partyName", orgName);
			}else if ( selItem.regionLevel == "98D" || selItem.regionLevel == "98E" || selItem.regionLevel == "98F"){
				regionInfo.setValue("partyName",orgName);
			}
		}
	}
	var regionId = regionTreeView.selectedItem.regionId ;
	if( regionId == "" ){
		return ;
	}
	ajaxCall.remoteCall("RegionService","getOrganizationByRegionId",[regionId],callBack);
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
		if( selItem.regionLevel == "97A" ){
			
		}else if (selItem.regionLevel == "97B" ){ 

		}else if ( selItem.regionLevel == "97C" ){ 
			lanInfo.setValue("provId", parentItem.regionId);
			lanInfo.setValue("provName", parentItem.regionName);
		}else if( selItem.regionLevel == "97D" ){
			businessInfo.setValue("lanId", parentItem.regionId);
			businessInfo.setValue("lanName", parentItem.regionName) ;
		}else if ( selItem.regionLevel == "98D" ){
			ppdomInfo.setValue("lanId", parentItem.regionId);
			ppdomInfo.setValue("lanName", parentItem.regionName) ;
		}else if ( selItem.regionLevel == "98E" ){
			exchInfo.setValue( "ppdomId" , parentItem.regionId ) ;
			exchInfo.setValue( "ppdomName", parentItem.regionName) ;
			//����Դ��������������ı�����ID�ͱ���������
			var lanItem = getParentRegionById( parentItem.parentRegionId); 
			exchInfo.setValue("lanId", lanItem.regionId);
			exchInfo.setValue("lanName", lanItem.regionName);			
		}
	}
	getOrganizationByRegionId();
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
	document.all.businessPannel.style.display = "none";
	document.all.ppdomPannel.style.display = "none" ;//�����
	document.all.exchPannel.style.display = "none" ;//ĸ��
	document.all.stationPannel.style.display = "none" ;//��վ
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
	this.virtualDealFlag = "";
	this.partyId = "";
	this.partyName = "";
	this.countryType = "";
}

//ʡ��ֵ����
function Province(){
	this.provId  = "";
	this.prvCode = "" ;
	this.prvName = "" ;
	this.prvFlag = "" ;
	this.partyId = "";
	this.partyName = "";
}

//������ֵ����
function Lan(){
	this.provId  = "";
	this.lanId = "" ;
	this.lanCode = "";
	this.lanName = "" ;
	this.flag = "" ;
	this.areaCode ="";
	this.partyId = "";
	this.partyName = "";
}
//Ӫҵ��ֵ����
function Business(){
	this.lanId = "" ;
	this.lanName = "" ;
	this.businessId = "" ;
	this.businessCode = "" ;
	this.businessName = "" ;
	this.businessDesc = "" ;
	this.partyId = "" ;
	this.partyName = "";
}
function Ppdom(){
	this.lanId = "" ;
	this.ppdomId = "" ;
	this.ppdomCode = "" ;
	this.ppdomName = "" ;
	this.ppdomDesc = "";
}

function Exch(){
	this.lanId = "" ;
	this.ppdomId = "" ;
	this.exchId = "" ;
	this.exchCode = "" ;
	this.exchDesc = "" ;
	this.exchType = "" ;
	this.exchName = "" ;
	this.superId = "" ;
	this.comments = "" ;
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
	var ajaxCall = new NDAjaxCall(false);//ͬ����ʽ��ѯ���ݿ�
	var obj ;
	var callBack = function( reply ){
		obj = reply.getResult() ;
	}
	ajaxCall.remoteCall(actionName,methodName,[objId],callBack);
	return obj ;
}

function button_regionInfo_partyName_onClick(){
	selectOrganization(regionInfo) ;
}

function button_provinceInfo_partyName_onClick(){
	selectOrganization(provinceInfo) ;
}

function button_lanInfo_partyName_onClick(){
	selectOrganization(lanInfo) ;
}

function button_businessInfo_partyName_onClick(){
	selectOrganization(businessInfo) ;
}

function selectOrganization( dataset ) {
	if( actionType == "" ){
		return ;
	}
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	if( selItem == null ){
		return ;
	}
	if( selItem.regionLevel != "98E" && actionType == "update" ){//98E��ʾĸ��
		alert("ֻ�е�ѡ����������򼶱�Ϊĸ��ʱ����ά��'������'��Ϣ!");
		return ;
	}
	if( selItem.regionLevel != "98D" && actionType == "insert" ){//98E��ʾĸ��
		alert("ֻ�е�ѡ����������򼶱�Ϊĸ��ʱ����ά��'������'��Ϣ!");
		return ;
	}
	if(dataset.getValue('virtualDealFlag')=='T'){
	    alert("��ǰ����������ĸ�֣�����ά��'������'��Ϣ!");
	    return;
	}
	
	

	var para = new Object();
	para["privilegeType"] = "3" ;
	var menuCode = Global.getCurrentMenuCode();
	para["privilegeCode"] = menuCode;
	/*
	 *orgType -- 
	 *��֯����ID,5��ʾ����,0��ʾ���Ź�˾,1��ʾʡ��˾,2��ʾ�й�˾,3��ʾ�ֹ�˾,6��ʾ����,
	 *9��ʾ������֯,99��ʾ������������,����ѡ���κ�һ����֯����
	 */
	para["orgType"] = "5" ;
	para["selectType"] = "1" ;//1��ʾ��ѡ,2��ʾ��ѡ
	para["checkChildren"] = "2";//1��ʾ��ѡ�¼��ڵ�,2��ʾ����ѡ
	para["uncheckedParent"] = "2";//1��ʾ��ѡ�¼��ڵ�ʱ�Զ�ȡ����ѡ�ϼ��ڵ�.2��ʾ�������� 
	para["downloadWhenChecked"] = "2";//1��ʾ����ѡ��¼��ʱ�������¼��ڵ�,2��ʾ��ѡ��¼��ʱ�������¼��ڵ�
	para["selectParent"] = "1";//1��ʾ����ѡ���ϼ�����,2��ʾ����ѡ���ϼ�����,Ĭ�ϲ�����ѡ���ϼ�����
	para["selectDistinctOrgType"] = "1" ;//1��ʾֻ��ѡ����ͬ�������֯,2��ʾ����ѡ��ͬ�������֯

	
	var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt;");
	if( returnValue == null || returnValue.length == 0) return ;
	vo = returnValue[0] ;
	dataset.setValue("partyId", vo.orgId);
	dataset.setValue("partyName",vo.orgName);
}