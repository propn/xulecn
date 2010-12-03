function page_onLoad(){
  initRegion();
}

//??????TreeList??????????????
function initRegion(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		
		document.all.regionTreeView.loadByXML(queryResult);
		
		//????????????????Dataset????????????????
		regionInfo.insertRecord(null);
		
		globleItem = document.all.regionTreeView.items[0];
		globleItem.expand();
		fillValues( globleItem );
//		setTimeout("fillValues(globleItem)", 500); 		
	}
	ajaxCall.remoteCall("RegionService","getSaleRegionList",["-1"],callBack);
}

//????????????????????
function cleanValues(){
	regionInfo.clearData();//????????
	regionInfo.insertRecord( null ) ;
	
	provinceInfo.clearData();//??????
	provinceInfo.insertRecord(null);
	
	lanInfo.clearData();//??????
	lanInfo.insertRecord( null ) ;
	
	saleAreaInfo.clearData();//????
	saleAreaInfo.insertRecord( null ) ;
	
	saleCommInfo.clearData() ;//??????????
	saleCommInfo.insertRecord( null ) ;
}

//??????????????????????????????
function fillValues(){
	//????????????????????
	var selItem = document.all.regionTreeView.selectedItem;
	if ( selItem == null ) {
		return ;
	}
	
	//????????????
	for(var ele in selItem){
		//??????????????????????????Dataset
		if(regionInfo.getField(ele)){
			regionInfo.setValue(ele, selItem[ele]);
		}
	}

	//????????????????????????????????????????
	var regionLevel = selItem.regionLevel ;//??????????????????????????
	if( regionLevel == "97A" ){//????????
		hidPannels();
		document.all.groupPannel.style.display ="block";
	//}else if ( regionLevel == "97B" ){//??????
	}else if ( regionLevel == "97E" ){//??????
		var province = new Province() ;
		var regionId = selItem.regionId ;
		province = getProvinceInfo( "RegionService", "getProvinceByRegionId", regionId );//????????????????????????????????????????????????????
		if( province != null ){
			setProvinceInfo( province ) ;
			provinceInfo.setValue("prvDesc", selItem.regionDesc);
		}
		hidPannels();
		document.all.provincePannel.style.display = "block" ;
	//}else if ( regionLevel == "97D" ){//??????
	}else if ( regionLevel == "97B" ){//??????
		var lan = new Lan() ;
		var regionId = selItem.regionId ;
		lan = getLanInfo( "RegionService", "getLanByRegionId", regionId );//??????????????????????????????????????????????????????
		if( lan != null ){
			setLanInfo( lan ) ;
			lanInfo.setValue("lanDesc", selItem.regionDesc);
		}
		hidPannels();
		document.all.lanPannel.style.display = "block" ;
	}else if ( regionLevel == "99D" ){//????????
		var saleArea = new SaleArea() ;
		var regionId = selItem.regionId ;
		saleArea = getSaleAreaInfo( "RegionService", "getSaleAreaByRegionId", regionId ); 

		if( saleArea != null ){
			setSaleAreaInfo( saleArea ) ;
			saleAreaInfo.setValue("areaDesc",selItem.regionDesc);
			saleAreaInfo.setValue("areaCode",selItem.regionCode);
		}
		hidPannels();
		document.all.saleAreaPannel.style.display = "block" ;
	}else if ( regionLevel == "99E" ){//????????
		var saleComm = new SaleComm() ;
		var regionId = selItem.regionId ;
		saleComm = getSaleCommInfo( "RegionService", "getSaleCommByRegionId", regionId ); 
		if( saleComm != null ){
			setSaleCommInfo( saleComm ) ;
			saleCommInfo.setValue("commCode",selItem.regionCode);
			saleCommInfo.setValue("commDesc",selItem.regionDesc) ;
		}
		hidPannels();
		document.all.saleCommPannel.style.display = "block" ;
	}
}

function enableDataset(){
	regionInfo.setReadOnly( false ) ;
	provinceInfo.setReadOnly( false ) ;
	lanInfo.setReadOnly(false);
	saleAreaInfo.setReadOnly( false ) ;
	saleCommInfo.setReadOnly( false ) ;
	
	var selItem = document.all.regionTreeView.selectedItem;
	if( selItem.regionLevel == "97A"){
		if( actionType == "update" ){
			regionInfo.setFieldReadOnly("regionLevel" , true );
		}else if( actionType =="insert"){
			if( nodeType == "root" ){
				regionInfo.setFieldReadOnly("regionLevel" , true );
			}else{
				
			}
		}
	}
	//if( selItem.regionLevel == "97B" ){
	if( selItem.regionLevel == "97E" ){
		if( actionType == "insert" ){
			lanInfo.setFieldReadOnly("provName",true);
		}
	}
	//if( selItem.regionLevel == "97D" ){
	if( selItem.regionLevel == "97B" ){
		if( actionType == "update" ){
			lanInfo.setFieldReadOnly("provName",true);
		}else if( actionType == "insert"){
			saleAreaInfo.setFieldReadOnly("lanName",true);
		}
	}
	if( selItem.regionLevel == "99D" ){
		if( actionType == "update" ){
			saleAreaInfo.setFieldReadOnly("lanName",true);
		}else if( actionType == "insert"){
			saleCommInfo.setFieldReadOnly( "areaName" , true ) ;
		}
	}
	if( selItem.regionLevel == "99E" ){
		if( actionType == "update"){
			saleCommInfo.setFieldReadOnly( "areaName" , true ) ;
		}
	}
}
function disableDataset(){
	regionInfo.setReadOnly( true ) ;
	provinceInfo.setReadOnly( true ) ;
	lanInfo.setReadOnly(true);
	saleAreaInfo.setReadOnly( true ) ;
	saleCommInfo.setReadOnly( true ) ;
}

//????????
function editRegion_onClick(){
	actionType = "update" ;
	enableDataset();
}

//????????????????
function addSubRegion_onClick(){
	actionType = "insert";
	nodeType = "child";
	addRegion( getCurrentRegionId() ) ;
}

//??????????????
function addRootRegion_onClick(){  
	actionType = "insert";
	nodeType = "root" ;
	addRegion( null ) ;
}

//????????
function addRegion(parentRegionId){
	enableDataset();
	var selItem = regionTreeView.selectedItem ;
	cleanValues();
	hidPannels();
	
	if( parentRegionId == null ){//??????????????
		document.all.groupPannel.style.display = "block" ;//????????????????
		regionInfo.setValue("regionLevel", "97A");
		return ;
	}
	
	if( parentRegionId != null ){//????????????????????????????????????
		var item = document.all.regionTreeView.selectedItem ;//????????????????
		
		regionInfo.setValue("parentRegionName", item.regionName ) ;//??????????????????
		regionInfo.setValue("parentRegionId",item.regionId);//??????????????ID
		regionInfo.setFieldReadOnly("regionLevel",true);
		
		var regionLevel = item.regionLevel ;
		
		if( regionLevel == "97A" ){//??????????????????????????????????
			document.all.provincePannel.style.display = "block";
			//regionInfo.setValue("regionLevel", "97B"); 
			regionInfo.setValue("regionLevel", "97E"); 
		//}else if ( regionLevel == "97B" ){//????????????????????????????????
		}else if ( regionLevel == "97E" ){//????????????????????????????????
			document.all.lanPannel.style.display = "block" ;
			//regionInfo.setValue("regionLevel", "97D");
			regionInfo.setValue("regionLevel", "97B");
			//????????Dataset??????????????????????????????????????????ID
			 lanInfo.setValue("provId", selItem.regionId);
			 lanInfo.setValue("provName",selItem.regionName);
			 lanInfo.setFieldReadOnly("provName",true);
		//}else if ( regionLevel == "97D" ){ //??????????????????????????????
		}else if ( regionLevel == "97B" ){ //??????????????????????????????
			document.all.saleAreaPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "99D");
			//??????????Dataset????????????????????ID
			saleAreaInfo.setValue("lanId", selItem.regionId);
			saleAreaInfo.setValue("lanName", selItem.regionName);
			saleAreaInfo.setFieldReadOnly("lanName",true);
		}else if ( regionLevel == "99D" ){//????????????????????????????????
			document.all.saleCommPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "99E"); 
			//??????????Dataset??????????????????ID??????
			saleCommInfo.setValue("areaId", selItem.regionId);
			saleCommInfo.setValue("areaName", selItem.regionName);
			saleCommInfo.setFieldReadOnly("areaName",true);
		} 
	}
}

function btn_cancel_onClick(){
	actionType = "" ;
	nodeType = "";
	disableDataset();
	clickRegion();
}

//????????????????
function btn_commitRegion_onClick(){
	disableDataset();
	if( actionType == "insert" ){
		commitInsertRegion() ;
	}else if ( actionType == "update" ){
		commitUpdateRegion();
	}
}
//??????????????????????
function commitUpdateRegion(){
	if( actionType == "" ) {
		return ;
	}
	
	var regionTreeView = document.all.regionTreeView ;
	var selItem = regionTreeView.selectedItem;
	var actionResult = null ;
	
	var regionLevel = selItem.regionLevel ;//??????????????????????
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		actionResult = reply.getResult() ;//??????????????????????????
		if(actionResult){
			refreshTreeList();
			alert("??????????");
		}else{
			alert("????????:\n" + actionResult["resultMessage"]) ;
		}
		actionType = "" ;
		nodeType = "" ;
	}
	
	//??????????????????????
	if( regionLevel == "97A" ){
		if( !$("regionInfoForm").validate()){
			enableDataset();
			return ;
		}
		//??????????????????Dataset??????????????????
		var region = new Region() ;
		for(var ele in region){ 
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
		region.regionLevel = "97A" ;
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
		
	//}else if( regionLevel == "97B" ){
	}else if( regionLevel == "97E" ){
		if( !$("provinceInfoForm").validate()){
			enableDataset();
			return ;
		}
		//??????
		var region = new Region() ; 
		region.regionName = provinceInfo.getValue("prvName") ;
		region.regionCode = provinceInfo.getValue("prvCode");
		region.regionDesc = provinceInfo.getValue("prvDesc") ;
		//region.regionLevel = "97B" ;
		region.regionLevel = "97E" ;
		//??????
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
			
	//}else if ( regionLevel == "97D" ){ 
	}else if ( regionLevel == "97B" ){ 
		if( !$("lanInfoForm").validate()){
			enableDataset() ;
			return;
		}		
		var region = new Region() ; 
		region.regionName = lanInfo.getValue("lanName") ;
		region.regionCode = lanInfo.getValue("lanCode");
		region.regionDesc = lanInfo.getValue("lanDesc") ;
		region.areaCode = lanInfo.getValue("areaCode");
		region.flag = lanInfo.getValue("flag");		
		//region.regionLevel = "97D" ;
		region.regionLevel = "97B" ;
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
			
	}else if ( regionLevel == "99D" ){ 
		if( !$("saleAreaInfoForm").validate()){
			enableDataset();
			return ;
		}
		var region = new Region() ;
		region.regionName = saleAreaInfo.getValue("areaName");
		region.regionCode = saleAreaInfo.getValue("areaCode");
		region.regionDesc = saleAreaInfo.getValue("areaDesc");
		region.regionLevel = "99D" ;
		var saleArea = new SaleArea() ;
		for( ele in saleArea ){
			if( saleAreaInfo.getField( ele )){
				saleArea[ele] = saleAreaInfo.getValue( ele ) ;
			}
		}
		
		var arr = new Array();
		arr[0] = region;
		arr[1] = saleArea ;
		ajaxCall.remoteCall("RegionService","updateSaleArea",arr,callBack);
	}else if ( regionLevel == "99E" ){ 
		if( !$("saleCommInfoForm").validate()){
			enableDataset();
			return ;
		}	
		var region = new Region() ;
		region.regionName = saleCommInfo.getValue("commName") ;
		region.regionCode = saleCommInfo.getValue("commCode") ;
		region.regionDesc = saleCommInfo.getValue("commDesc");
		region.regionLevel = "99E" ;
		var saleComm = new SaleComm() ;
		for( ele in saleComm ){
			if( saleCommInfo.getField( ele )){
				saleComm[ele] = saleCommInfo.getValue( ele ) ;
			}
		}
		
		var arr = new Array();
		arr[0] = region;
		arr[1] = saleComm ;
		ajaxCall.remoteCall("RegionService","updateSaleComm",arr,callBack);
	}
}
//??????????????????????
function commitInsertRegion(){
	if( actionType == "" ) {
		return ;
	}
	var regionTreeView = document.all.regionTreeView ;
	var selItem = regionTreeView.selectedItem;
	var actionResult = null ;
	
	var regionLevel = selItem.regionLevel ;//??????????????????????
	
	var ajaxCall = new NDAjaxCall(true);
	
	//????????
	var callBack = function( reply ){
		actionResult = reply.getResult() ;//??????????????????????????
		//??????????????????????????ID
		refreshDatainfo(actionResult);//??????????????????????????????????Dataset??????????????	
		addRegionToTreeList();
		actionType = "" ;
		nodeType = "" ;
		alert("??????????");
	}
	
	//??????????????????????????nodeType??root????????????????????????????
	if( nodeType == "root" ){
		if( !$("regionInfoForm").validate()){
			enableDataset();
			return ;
		}
		//??????????????????Dataset??????????????????
		var region = new Region() ;
		for(var ele in region){ 
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			}
		}
		region.parentRegionId = "-1" ;//????????ID????
		region.configId = "" ;
		region.regionLevel = "97A" ;
		
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		
	}else{//??????????????
	
		//??????????????????????????????????????????????????????????????????
		if( regionLevel == "97A" ){
			if( !$("provinceInfoForm").validate()){
				enableDataset();
				return ;
			}
			//????????????Region????Province??
			var region = new Region() ;
			region.parentRegionId = selItem.regionId ;//????????ID
			region.configId = "";
			//region.regionLevel = "97B" ;//??????
			region.regionLevel = "97E" ;//??????
			region.regionName = provinceInfo.getValue("prvName") ;
			region.regionCode = provinceInfo.getValue("prvCode");
			region.regionDesc = provinceInfo.getValue("prvDesc") ;
			//??????
			var province = new Province() ;
			for( ele in province ){
				if( provinceInfo.getField( ele )){
					province[ele] = provinceInfo.getValue( ele ) ;
				}
			}
			var arr = new Array();
			arr[0] = region;
			arr[1] = province ;
			ajaxCall.remoteCall("RegionService","insertProvince",arr,callBack);
		//}else if ( regionLevel == "97B" ){//????????????????????????????????
		}else if ( regionLevel == "97E" ){//????????????????????????????????
			//????????????Region????Lan??
			if( !$("lanInfoForm").validate()){
				enableDataset() ;
				return;
			}
			var region = new Region() ;
			region.parentRegionId = selItem.regionId ;//????????ID
			region.configId = "";
			//region.regionLevel = "97D" ;
			region.regionLevel = "97B" ;
			region.regionName = lanInfo.getValue("lanName") ;
			region.regionCode = lanInfo.getValue("lanCode");
			region.regionDesc = lanInfo.getValue("lanDesc") ;
			region.areaCode = lanInfo.getValue("areaCode");
			region.flag = lanInfo.getValue("flag");
			
			//??????
			var lan = new Lan() ;
			for( ele in lan ){
				if( lanInfo.getField( ele )){
					lan[ele] = lanInfo.getValue( ele ) ;
				}
			}
			
			var arr = new Array();
			arr[0] = region;
			arr[1] = lan ;
			ajaxCall.remoteCall("RegionService","insertLan",arr,callBack);
			
		//}else if ( regionLevel == "97D" ){//????????????????????????????????
		}else if ( regionLevel == "97B" ){//????????????????????????????????
			if( !$("saleAreaInfoForm").validate()){
				enableDataset();
				return ;
			}		
			//????????????Region??????????????
			var region = new Region() ;
			region.regionId = saleAreaInfo.getValue("areaId");
			region.regionName = saleAreaInfo.getValue("areaName") ;
			region.regionCode = saleAreaInfo.getValue("areaCode") ;
			region.regionDesc = saleAreaInfo.getValue("areaDesc" );
			region.parentRegionId = selItem.regionId ;//????????ID
			region.configId = "";
			region.regionLevel = "99D" ;

			//????????			
			var saleArea = new SaleArea() ;
			for( ele in saleArea ){
				if( saleAreaInfo.getField( ele )){
					saleArea[ele] = saleAreaInfo.getValue( ele ) ;
				}
			}
			
			var arr = new Array();
			arr[0] = region;
			arr[1] = saleArea ;
			ajaxCall.remoteCall("RegionService","insertSaleArea",arr,callBack);

		}else if ( regionLevel == "99D" ){//????????????,????????????????
			if( !$("saleCommInfoForm").validate()){
				enableDataset();
				return ;
			}
			var region = new Region() ;
			region.regionId = saleCommInfo.getValue( "commId" ) ;
			region.regionName = saleCommInfo.getValue( "commName") ;
			region.regionCode = saleCommInfo.getValue( "commCode" ) ;
			region.regionDesc = saleCommInfo.getValue( "commDesc" ) ;
			region.areaId = saleCommInfo.getValue("areaId");
			region.parentRegionId = selItem.regionId ;//????????ID
			region.configId = "";
			region.regionLevel = "99E" ;
			//????????
			var saleComm = new SaleComm() ;
			for( ele in saleComm ){
				if( saleCommInfo.getField( ele )){
					saleComm[ele] = saleCommInfo.getValue( ele ) ;
				}
			}
			var arr = new Array();
			arr[0] = region;
			arr[1] = saleComm ;
			ajaxCall.remoteCall("RegionService","insertSaleComm",arr,callBack);
		}
	}
}

function refreshDatainfo(id){
	var selItem = regionTreeView.selectedItem ;
	var regionLevel = selItem.regionLevel ;
	if( nodeType == "root" ){
		regionInfo.setValue("regionId", id ) ;
	}else {
		if( regionLevel == "97A" ){
			provinceInfo.setValue( "provId", id ) ;
		}
		//if( regionLevel == "97B" ){
		if( regionLevel == "97E" ){
			lanInfo.setValue( "lanId", id ) ;
		}
		//if( regionLevel == "97D" ){
		if( regionLevel == "97B" ){
			saleAreaInfo.setValue( "areaId" , id ) ;
		}
		if( regionLevel == "99D" ){
			saleCommInfo.setValue( "commId" , id ) ;
		}
	}
}

//????????????????????
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
	//??????????????????????????????????????????
	region.setSelected();
}

//??????????????????????????????
function addChildToTreeList(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	var region = regionTree.createTreeNode();
	
	for(var ele in selItem){
		if(regionInfo.getField(ele)){
			region[ele] = regionInfo.getValue( ele );
		} 
	}
	
	if( selItem.regionLevel == "97A" ){//??????????????
		region.regionId = provinceInfo.getValue( "provId" ) ;
		region.regionName = provinceInfo.getValue( "prvName" ) ;
		region.regionCode = provinceInfo.getValue( "prvCode") ;
		region.regionDesc = provinceInfo.getValue( "prvDesc" ) ;
		//region.regionLevel = "97B" ;
		region.regionLevel = "97E" ;
	}
	
	//if( selItem.regionLevel == "97B" ){//??????????????
	if( selItem.regionLevel == "97E" ){//??????????????
		region.regionId = lanInfo.getValue("lanId") ;
		region.regionName = lanInfo.getValue("lanName") ;
		region.regionCode = lanInfo.getValue("lanCode") ;
		region.regionDesc = lanInfo.getValue("lanDesc");
		//region.regionLevel = "97D" ;		
		region.regionLevel = "97B" ;		
	}
	
	//if( selItem.regionLevel == "97D" ){//????????????????
	if( selItem.regionLevel == "97B" ){//????????????????
		region.regionId = saleAreaInfo.getValue("areaId");
		region.regionName = saleAreaInfo.getValue("areaName") ;
		region.regionCode = saleAreaInfo.getValue("areaCode") ;
		region.regionDesc = saleAreaInfo.getValue("areaDesc") ;
		region.regionLevel = "99D" ;		
	}
	
	if( selItem.regionLevel == "99D" ){//????????????????
		region.regionId = saleCommInfo.getValue( "commId" ) ;
		region.regionName = saleCommInfo.getValue( "commName" ) ;
		region.regionCode = saleCommInfo.getValue( "commCode" ) ;
		region.regionDesc = saleCommInfo.getValue( "commDesc" ) ;
		region.regionLevel = "99E" ;		
	}
	region.parentRegionId = selItem.regionId ;

	selItem.add(region);
	
	region.setSelected();
}

//??Update????????????????????TreeList????????????
function refreshTreeList(){
	var regionTree = document.all.regionTreeView;
	var region = regionTree.selectedItem;
	var regionLevel = region.regionLevel ;	
	
	if( regionLevel == "97A" ){//????
		for(var ele in region){
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}	
	}
	
	//if( regionLevel == "97B" ){//??????
	if( regionLevel == "97E" ){//??????
		region.regionName = provinceInfo.getValue("prvName" ) ;
		region.regionCode = provinceInfo.getValue("prvCode" ) ;
		region.regionDesc = provinceInfo.getValue( "prvDesc" ) ;
	}
	
	//if( regionLevel == "97D" ){//??????
	if( regionLevel == "97B" ){//??????
		region.regionName = lanInfo.getValue("lanName" ) ;
		region.regionCode = lanInfo.getValue("lanCode" ) ;
		region.regionDesc = lanInfo.getValue( "lanDesc" ) ;
	}
	
	if( regionLevel == "99D" ){//????????
		region.regionName = saleAreaInfo.getValue("areaName" ) ;
		region.regionCode = saleAreaInfo.getValue("areaCode" ) ;
		region.regionDesc = saleAreaInfo.getValue( "areaDesc" ) ;	
	}
	
	if( regionLevel == "99E" ){//????????
		region.regionName = saleCommInfo.getValue("commName" ) ;
		region.regionCode = saleCommInfo.getValue("commCode" ) ;
		region.regionDesc = saleCommInfo.getValue( "commDesc" ) ;	
	}
	region.refresh();
}

var actionType = "" ;
var nodeType = "" ;

//????????????????????????
function addRegionToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}

function getSaleAreaInfo( actionName, methodName, regionId ){
	var obj = getRegionObject( actionName, methodName, regionId ) ;
	return obj;
}
function getSaleCommInfo( actionName, methodName, regionId){
	return getRegionObject( actionName, methodName, regionId ) ;
}
function setSaleCommInfo( saleCommObj ){
	saleCommInfo.clearData();
	saleCommInfo.insertRecord( null ) ;
	for( var ele in saleCommObj ){
		if( saleCommInfo.getField( ele ) ){
			saleCommInfo.setValue( ele, saleCommObj[ele] ) ;
		}
	}
}
function setSaleAreaInfo( saleAreaObj ){
	saleAreaInfo.clearData();
	saleAreaInfo.insertRecord( null ) ;
	for( var ele in saleAreaObj ){
		if( saleAreaInfo.getField( ele )){
			saleAreaInfo.setValue( ele, saleAreaObj[ele] ) ;
		}
	}
}

//??????????????????????
function deleteRegionFromTreeList(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	selItem.remove();
}

//????????????
function deleteRegion_onClick(){
	if( confirm( "???????????????????????????" )){
		var currentRegionId = getCurrentRegionId();
		
		var ajaxCall = new NDAjaxCall(true);
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;
			if( deleteResult ){
				deleteRegionFromTreeList();//????????????TreeList??????
				alert("??????????????????");
			}else{
				alert("????????????????!" );
			}
		}
		ajaxCall.remoteCall("RegionService","deleteRegion",[currentRegionId],callBack);
	}
}

//????????????????????????,??????????????????????????,????????,
//????????????????????????????,??????????????????????????????
//????????,??????????????????????????????????,????????????.
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
	ajaxCall.remoteCall("RegionService","getSaleRegionList",[regionId], callBack);
}
//??????????????????????????????????????????????????????????
function clickRegion(){ 
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	
	//????????????????????????????.
	downloadSubItems() ;
	
	cleanValues() ;//????Dataset??????????
	fillValues();//??Dataset????	 
	disableDataset();
	actionType = "";
	nodeType = "";

	var parentItem = getParentRegionById( selItem.parentRegionId );
	if( parentItem != null ){
		if( selItem.regionLevel == "97A" ){
			
		//}else if (selItem.regionLevel == "97B" ){
		}else if (selItem.regionLevel == "97E" ){

		//}else if ( selItem.regionLevel == "97D" ){
		}else if ( selItem.regionLevel == "97B" ){
			lanInfo.setValue("provId", parentItem.regionId);
			lanInfo.setValue("provName", parentItem.regionName);
		}else if ( selItem.regionLevel == "99D" ){
			saleAreaInfo.setValue("lanId", parentItem.regionId);
			saleAreaInfo.setValue("lanName", parentItem.regionName) ;
		}else if ( selItem.regionLevel == "99E" ){
			saleCommInfo.setValue( "areaId" , parentItem.regionId ) ;
			saleCommInfo.setValue( "areaName", parentItem.regionName) ;
		}
	}
}

//????????????ID????????????????
function getParentRegionById( parentId ){
	var regionTree = document.all.regionTreeView;
	var items = regionTree.items;
	for( var i = 0 ; i < items.length ; i ++ ){
		if( items[i].regionId == parentId ){
			return items[i] ;
		}
	}
}

//??????????????????????????id
function getCurrentRegionId(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	if( selItem != null ){
		return selItem.regionId ;
	}else{
		return null ;
	}
}

//??????????????????????
function hidPannels(){
	document.all.groupPannel.style.display = "none" ;
	document.all.provincePannel.style.display = "none" ;
	document.all.lanPannel.style.display = "none" ;
	document.all.saleAreaPannel.style.display = "none" ;
	document.all.saleCommPannel.style.display = "none" ;
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
}

//??????????
function Province(){
	this.provId  = "";
	this.prvCode = "" ;
	this.prvName = "" ;
	this.prvFlag = "" ;
}

//????????????
function Lan(){
	this.provId  = "";
	this.lanId = "" ;
	this.lanCode = "";
	this.lanName = "" ;
	this.flag = "" ;
	this.areaCode = "";
}

function SaleArea(){
	this.areaId  = "";
	this.lanId  = "";
	this.areaName  = "";
	this.areaCode  = "";
	this.areaDesc  = "";
	this.validFlag  = "";
	this.remark  = "";
}

function SaleComm(){
	this.commId  = "";
	this.commName  = "";
	this.commCode  = "";
	this.commDesc  = "";
	this.areaId  = "";
	this.manager  = "";
	this.validFlag  = "";
	this.remark  = "";
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
//????????????????????????????
function setProvinceInfo( provinceObj ){
	provinceInfo.clearData();
	provinceInfo.insertRecord(null) ;
	for(var ele in provinceObj){
		if(provinceInfo.getField(ele)){
			provinceInfo.setValue(ele, provinceObj[ele]);
		}
	}
}

//????regionId????????????????????????????????????????
function getProvinceInfo( actionName, actionMethod, regionId ) {
	return getRegionObject( actionName, actionMethod, regionId ) ;
}

function getLanInfo( actionName, actionMethod, provinceId ) {
	return getRegionObject( actionName, actionMethod, provinceId ) ;
}

//????????????????????????????????????
function getRegionObject( actionName, methodName, objId ){
	var ajaxCall = new NDAjaxCall(false);
	var obj ;
	var callBack = function( reply ){
		obj = reply.getResult() ;
	}
	ajaxCall.remoteCall(actionName,methodName,[objId],callBack);
	return obj ;
}