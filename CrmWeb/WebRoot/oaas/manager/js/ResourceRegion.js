/**
97A        集团
97B        省
97C        本地网
97D        营业区
98D        处理局
98E        母局
98F        局站
*/
function checkExistRegionCode( regionCode ) {
	var ajaxCall = new NDAjaxCall( false ) ;//同步方式查询服务器端数据
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

//初始化TreeList组件的数据显示
function initRegion(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		
		document.all.regionTreeView.loadByXML(queryResult);
		if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
			alert("您没有任何数据权限!");
			return ;
		}
		
		//在详细面板对应的Dataset上插入一个空记录
		regionInfo.insertRecord(null);
		
		globleItem = document.all.regionTreeView.items[0];
		fillValues(globleItem);
	}
	//ajaxCall.remoteCall("RegionService","getResourceRegionList",["-1"],callBack);
	ajaxCall.remoteCall("RegionService","getRootResourceRegionListByPrivControl",[menuCode],callBack);	
}

//清除详细面板上的信息
function cleanValues(){
	regionInfo.clearData();//集团公司
	regionInfo.insertRecord( null ) ;
	
	provinceInfo.clearData();//省公司
	provinceInfo.insertRecord(null);
	
	lanInfo.clearData();//本地网
	lanInfo.insertRecord( null ) ;
	
	businessInfo.clearData() ;//营业区
	businessInfo.insertRecord(null);
	
	ppdomInfo.clearData();//辖区
	ppdomInfo.insertRecord( null ) ;
	
	exchInfo.clearData() ;//资源管理局
	exchInfo.insertRecord( null ) ;
}

//填充第一行的信息到详细信息面板
function fillValues(){
	//获取当前被选中的记录
	var selItem = document.all.regionTreeView.selectedItem;
	if ( selItem == null ) {
		return ;
	}
	
	//显示区域信息
	for(var ele in selItem){
		//使用当前被选中的记录来填充Dataset
		if(regionInfo.getField(ele)){
			regionInfo.setValue(ele, selItem[ele]);
		}
	}
		
	//根据区域的不同级别显示不同的详细信息面板
	var regionLevel = selItem.regionLevel ;//当前被选中的记录的区域级别
	if( regionLevel == "97A" ){//集团公司
		hidPannels();
		document.all.groupPannel.style.display ="block";
	}else if ( regionLevel == "97B" ){//省公司
		var province = new Province() ;
		var regionId = selItem.regionId ;
		province = getProvinceInfo( "RegionService", "getProvinceByRegionId", regionId );//根据区域标识到服务器端获取和该区域标识对应的省份信息
		if( province != null ){
			setProvinceInfo( province ) ;
		}
		provinceInfo.setValue("prvDesc", selItem.regionDesc);
		provinceInfo.setValue("ngnFlag", selItem.ngnFlag);
		hidPannels();
		document.all.provincePannel.style.display = "block" ;
		
	}else if ( regionLevel == "97C" ){//本地网
		var lan = new Lan() ;
		var regionId = selItem.regionId ;
		lan = getLanInfo( "RegionService", "getLanByRegionId", regionId );//根据区域标识到服务器端获取和该区域标识对应的本地网信息
		if( lan != null ){
			setLanInfo( lan ) ;
			lanInfo.setValue("lanDesc",selItem.regionDesc);
			lanInfo.setValue("lanCode",selItem.regionCode);
			lanInfo.setValue("ngnFlag", selItem.ngnFlag);
		}
		hidPannels();
		document.all.lanPannel.style.display = "block" ;
		
	}else if ( regionLevel == "97D" ){//营业区
		var business = new Business() ;
		var lanId = selItem.regionId ;//所属本地网
		business = getBusinessInfo( "RegionService", "getBusinessByRegionId", lanId );//根据区域标识到服务器端获取和该区域标识对应的营业区信息
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
	 
	if( selItem.regionLevel == "97A"){//选中集团
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
	if( selItem.regionLevel == "97B" ){//选中省公司		
		if( actionType == "update"){
           provinceInfo.setPopupEnable("partyName",true);
		}
		else  if( actionType == "insert" ){
			lanInfo.setFieldReadOnly("provName",true);
			lanInfo.setPopupEnable("partyName",true);
		}
	}
	if( selItem.regionLevel == "97C" ){//选中本地网
		if( actionType == "update" ){
			lanInfo.setFieldReadOnly("provName",true);
			lanInfo.setPopupEnable("partyName",true);
		}else if( actionType == "insert"){ 
			businessInfo.setFieldReadOnly("lanName",true);
			businessInfo.setPopupEnable("partyName",true);
		}
	}
	if( selItem.regionLevel == "97D" ){//营业区
		if( actionType == "update" ){
			businessInfo.setFieldReadOnly("lanName",true);
            businessInfo.setPopupEnable("partyName",true);
		}else if ( actionType == "insert" ){
			regionInfo.setFieldReadOnly("regionLevel" , true );
			regionInfo.setPopupEnable("partyName",true);
		}
	}
	if( selItem.regionLevel == "98D" ){//处理局
		regionInfo.setFieldReadOnly("regionLevel" , true );
	}
	
	if( selItem.regionLevel == "98E" ){//选中母局
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

//编辑区域
function editRegion_onClick(){
		if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
			alert("您没有任何数据权限!");
			return ;
		}
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再编辑区域");
		return ;
	}
	actionType = "update" ;
	enableDataset();
}

//增加一个下级区域
function addSubRegion_onClick(){
	if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
		alert("您没有任何数据权限!");
		return ;
	}
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加区域");
		return ;
	}
	var selItem = regionTreeView.selectedItem ;
	if( selItem == null ){
		return ;
	}
	/*
	if( selItem.regionLevel == "98E" && selItem.virtualDealFlag == "T" ){
		alert("当前区域是虚拟母局,不能增加下级区域!");
		return ;
	}*/	
	if( selItem.regionLevel == "98F" ){
		alert("当前节点是局站,不能在局站下面增加子区域!");
		return ;
	}
	actionType = "insert";
	nodeType = "child";
	addRegion( getCurrentRegionId() ) ;
}

//增加一个根区域
function addRootRegion_onClick(){  
	if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
		alert("您没有任何数据权限!");
		return ;
	}
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加区域");
		return ;
	}
	actionType = "insert";
	nodeType = "root" ;
	addRegion( null ) ;
}

//增加区域
function addRegion(parentRegionId){
	enableDataset();
	
	cleanValues();
	hidPannels();
	
	if( parentRegionId == null ){//增加一个根区域
		document.all.groupPannel.style.display = "block" ;//显示集团信息面板
		regionInfo.setValue("regionLevel", "97A");
		regionInfo.setFieldReadOnly("virtualDealFlag",true);
		regionInfo.setFieldReadOnly("partyName",true);
		return ;
	}
	
	if( parentRegionId != null ){//如果是在一个上级区域下面增加子区域。
		var item = document.all.regionTreeView.selectedItem ;//当前被选中的记录
		
		regionInfo.setValue("parentRegionName", item.regionName ) ;//设置上级区域的名称
		regionInfo.setValue("parentRegionId",item.regionId);//设置上级区域的ID
		
		var regionLevel = item.regionLevel ;
			
		if( regionLevel == "97A" ){//在集团公司下面，增加的是省公司区域
			document.all.provincePannel.style.display = "block";
			regionInfo.setValue("regionLevel", "97B"); 	
			provinceInfo.setFieldReadOnly("partyName", true);
		}else if ( regionLevel == "97B" ){//在省公司下面，增加的是本地网区域 
			document.all.lanPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "97C"); 
			//给本地网Dataset设置上级区域名称（所属省份名称）和上级区域ID
			 lanInfo.setValue("provId", item.regionId);
			 lanInfo.setValue("provName",item.regionName);
			 
			 lanInfo.setFieldReadOnly("partyName",true);
		}else if ( regionLevel == "97C" ){//在本地网下面,增加的是营业区
			document.all.businessPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "97D");
			//给营业区Dataset设置上级本地网名称和ID
			businessInfo.setValue("lanId", item.regionId);
			businessInfo.setValue("lanName", item.regionName);		
			businessInfo.setFieldReadOnly("partyName",true);
		}else if( regionLevel == "97D" ){//在营业区下面，增加的是处理局
			document.all.ppdomPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "98D");
			
			//regionInfo.setFieldReadOnly("partyName",true);
		}else if ( regionLevel == "98D" ){//在处理局下面,增加的是母局
			document.all.exchPannel.style.display = "block" ;
		     
			regionInfo.setValue("regionLevel", "98E");
			//regionInfo.setFieldReadOnly("partyName",true);
		}else if ( regionLevel == "98E" ){//在母局下面,增加的是局站
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

//提交按钮响应事件
function btn_commitRegion_onClick(){
	var regionName=regionInfo.getValue("regionName");
	if(actionType == "" ){
		return;
	}
	if(regionName.length>50){
		alert("您所输入的区域名称已超过最大长度（50个汉字），请重新输入！");
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
		alert("请输入营业区编码!");
		return false ;
	}
	if( businessInfo.getValue("businessCode").length > 3 ){
		alert("请输入3位以内的营业区编码!");
		return false ;
	}
	if( businessInfo.getValue("businessName") == "" ){
		alert("请输入营业区名称!");
		return false ;
	}
	return true ;
}

function validateLanForm(){
	if( lanInfo.getValue( "lanCode" )== "" ){
		alert( "请输入本地网编码!");
		return false ;
	}
	if( lanInfo.getValue( "lanName" )== "" ){
		alert( "请输入本地网名称!");
		return false ;
	}
	if( lanInfo.getValue( "areaCode" )== "" ){
		alert( "请输入电话区号!");
		return false ;
	}
	if( lanInfo.getValue( "flag" )== "" ){
		alert( "请输入本地标志!");
		return false ;
	}
	return true ;
}

function validateProvinceForm(){
	if( provinceInfo.getValue("prvCode") == "" ){
		alert("请输入省份代码!");
		return false ;
	}
	if( provinceInfo.getValue("prvName") == "" ){
		alert("请输入省份名称!");
		return false ;
	}
	if( provinceInfo.getValue("prvFlag") == "" ){
		alert("请输入本省标志!");
		return false ;
	}
	return true ;
}

function validateRegionForm(){
	if( regionInfo.getValue("regionName") == "" ){
		alert("请输入区域名称!");
		return false ;
	}
	if( regionInfo.getValue("regionCode") == "" ){
		alert("请输入区域编码!");
		return false ;
	}
	return true ;
}

//提交插入一个区域的请求
function commitInsertRegion(){
	if( actionType == "" ){
		return ;
	}
	
	var actionResult = null ;
	
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		actionResult = reply.getResult() ;//保存服务器端操作返回的结果
		//服务器端返回新增加的区域的ID
		refreshDatainfo(actionResult); //完成增加以后，必须重新初始化关联的Dataset里面的响应信息	
		addRegionToTreeList();
		actionType = "" ;
		nodeType = "" ;		
		alert("操作成功！");
	}
	
	var regionTreeView = document.all.regionTreeView ;
	var selItem = regionTreeView.selectedItem;
	var regionLevel ;//当前被选中的区域的级别
	if( selItem == null ){
		regionLevel == "" ; 
	}else{
		regionLevel = selItem.regionLevel ;
	}
	//如果级别是“集团公司”并且nodeType是root，就说明要增加集团公司根节点
	if(nodeType == "root" ){
		//if( !$("regionInfoForm").validate()){
		if( !validateRegionForm()){
			enableDataset() ;
			return ;
		}
		//创建区域对象并且将Dataset中的值赋值到对象中
		var region = new Region() ;
		for(var ele in region){ 
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
		if( checkExistRegionCode( region.regionCode )){
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		
		region.parentRegionId = "-1" ;//上级区域ID为空
		region.configId = "" ;
		region.regionLevel = "97A" ;
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
				
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		
	}else{//增加的是子节点
		//如果区域级别是“集团公司”，增加的就是省公司，
		if( regionLevel == "97A" ){
			//if( !$("provinceInfoForm").validate()){
			if( !validateProvinceForm()){
				enableDataset() ;
				return ;
			}
			//需要同时插入Region表和Province表
			var region = new Region() ;
			region.parentRegionId = selItem.regionId ;//上级区域ID
			region.configId = "";
			region.regionLevel = "97B" ;//省公司
			region.regionName = provinceInfo.getValue("prvName") ;
			region.regionCode = provinceInfo.getValue("prvCode");
			region.regionDesc = provinceInfo.getValue("prvDesc") ;
			region.ngnFlag = provinceInfo.getValue("ngnFlag");
			region.partyId = provinceInfo.getValue("partyId");
            region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
			if( checkExistRegionCode( region.regionCode )){
				alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
				enableDataset();
				return ;
			}
		
			//省份表
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
			
		}else if ( regionLevel == "97B" ){//在省公司下面，增加的是本地网区域
			//if( !$("lanInfoForm").validate()){
			if(!validateLanForm()){
				enableDataset();
				return ;
			}	
			//需要同时插入Region表和Lan表
			var region = new Region() ;
			region.parentRegionId = selItem.regionId ;//上级区域ID
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
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		
			//本地网
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
			
		}else if ( regionLevel == "97C" ){//在本地网下面，增加的是营业区区域
			//if( !$("businessInfoForm").validate()){
			if( !validateBusinessForm()){
				enableDataset();
				return ;
			}
			//需要同时插入Region表和Business表
			var region = new Region() ;
			region.regionId = businessInfo.getValue("businessId");
			region.regionName = businessInfo.getValue("businessName") ;
			region.regionCode = businessInfo.getValue("businessCode") ;
			region.regionDesc = businessInfo.getValue("businessDesc" );
			region.parentRegionId = selItem.regionId ;//上级区域ID
			region.regionLevel = "97D" ;
			region.ngnFlag = businessInfo.getValue("ngnFlag");
			region.configId = "";
			region.partyId = businessInfo.getValue("partyId") ;
			region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
			if( checkExistRegionCode( region.regionCode )){
				alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
				enableDataset();
				return ;
			}
		
			//省份表
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
		}else if( regionLevel == "97D" ){//营业区下面,增加的是处理局
			//if( !$("regionInfoForm").validate()){//没有通过输入校验
			if( !validateRegionForm()){
				enableDataset() ;
				return ;
			}
			// regionInfo.getValue("virtualDealFlag") == ""
			
			//创建区域对象并且将Dataset中的值赋值到对象中
			var region = new Region() ;
			for(var ele in region){ 
				if(regionInfo.getField(ele)){
					region[ele] = regionInfo.getValue( ele );
				} 
			}			
			region.parentRegionId = selItem.regionId ;//上级区域ID
			region.configId = "" ;
			region.regionLevel = "98D" ;
			region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';		
			if( checkExistRegionCode( region.regionCode )){
				alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
				enableDataset();
				return ;
			}
		
			var arr = new Array();
			arr[0] = region;
			ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		}else if ( regionLevel == "98D" ){//在处理局下面,增加的是母局
			//if( !$("regionInfoForm").validate()){//没有通过输入校验
			if( !validateRegionForm()){
				enableDataset() ;
				return ;
			}
			if( regionInfo.getValue("virtualDealFlag") == "" || regionInfo.getValue("virtualDealFlag") == null || regionInfo.getValue("virtualDealFlag") == "undefined"){
				alert("请选择虚拟标志!");
				enableDataset() ;
				return ;
			}
			
			
			//创建区域对象并且将Dataset中的值赋值到对象中
			var region = new Region() ;
			region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
			for(var ele in region){ 
				if(regionInfo.getField(ele)){
					region[ele] = regionInfo.getValue( ele );
				} 
			}
			
			region.parentRegionId = selItem.regionId ;//上级区域ID
			region.configId = "" ;
			region.regionLevel = "98E" ;
			
			if( checkExistRegionCode( region.regionCode )){
				alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
				enableDataset();
				return ;
			}
			
			var arr = new Array();
			arr[0] = region;
			ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		}else if ( regionLevel == "98E" ){//在母局下面,增加的是局站
			//if( !$("regionInfoForm").validate()){//没有通过输入校验
			if( !validateRegionForm()){
				enableDataset() ;
				return ;
			}
			if( regionInfo.getValue("ngnFlag") == "" || regionInfo.getValue("ngnFlag") == null || regionInfo.getValue("ngnFlag") == "undefined" ){
				alert("请选择NGN标志!");
				enableDataset() ;				
				return ;
			}
			//创建区域对象并且将Dataset中的值赋值到对象中
			var region = new Region() ;
			region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
			for(var ele in region){ 
				if(regionInfo.getField(ele)){
					region[ele] = regionInfo.getValue( ele );
				} 
			}
			
			region.parentRegionId = selItem.regionId ;//上级区域ID
			region.configId = "" ;
			region.regionLevel = "98F" ;
					
			if( checkExistRegionCode( region.regionCode )){
				alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
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
	var regionLevel ;//当前被选中的区域的级别
	if( selItem == null ){
		regionLevel == "" ;
	}else{
		regionLevel = selItem.regionLevel ;
	}
	
	if( nodeType == "root" ){
		regionInfo.setValue("regionId", id ) ;
	}else {
		if( regionLevel == "97A" ){//省公司
			provinceInfo.setValue( "provId", id ) ;
		}
		if( regionLevel == "97B" ){//本地网 
			lanInfo.setValue( "lanId", id ) ;
		}
		if( regionLevel == "97C" ){//营业区
			businessInfo.setValue("businessId",id); 
		}
		if( regionLevel == "98D" || regionLevel == "98E" || regionLevel == "98F" ){//处理局,母局,局站
			regionInfo.setValue( "regionId" , id ) ;
		}
	}	
}

//在树上增加一个根节点
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

//在区域树表上插入一个子节点记录
function addChildToTreeList(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	var region = regionTree.createTreeNode();
	
	for(var ele in selItem){
		if(regionInfo.getField(ele)){
			region[ele] = regionInfo.getValue( ele );
		}
	}
	if( selItem.regionLevel == "97A" ){//增加的是省公司
		region.regionId = provinceInfo.getValue( "provId" ) ;
		region.regionName = provinceInfo.getValue( "prvName" ) ;
		region.regionCode = provinceInfo.getValue( "prvCode") ;
		region.regionDesc = provinceInfo.getValue( "prvDesc" ) ;
		region.ngnFlag = provinceInfo.getValue("ngnFlag");
		region.regionLevel = "97B" ;
	}
	
	if( selItem.regionLevel == "97B" ){//增加的是本地网 
		region.regionId = lanInfo.getValue("lanId") ;
		region.regionName = lanInfo.getValue("lanName") ;
		region.regionCode = lanInfo.getValue("lanCode") ;
		region.regionDesc = lanInfo.getValue("lanDesc");
		region.ngnFlag = lanInfo.getValue("ngnFlag") ;
		region.regionLevel = "97C" ;		
	}
	
	if( selItem.regionLevel == "97C" ){//增加的是营业
		region.regionId = businessInfo.getValue("businessId");
		region.regionName = businessInfo.getValue("businessName") ;
		region.regionCode = businessInfo.getValue("businessCode") ;
		region.regionDesc = businessInfo.getValue("businessDesc") ;
		region.ngnFlag = businessInfo.getValue("ngnFlag");
		region.regionLevel = "97D" ;	
		
	}

	if( selItem.regionLevel == "97D"){//处理局
		region.regionLevel = "98D" 
	}
	
	if( selItem.regionLevel == "98D" ){//母局
		region.regionLevel = "98E" 
	}
	
	if( selItem.regionLevel == "98E" ){// 局站
		region.regionLevel = "98F" 
	}
	
	region.parentRegionId = selItem.regionId ;
	selItem.add(region);
	region.setSelected();
}

//提交更新一个区域的请求
function commitUpdateRegion(){
	if( actionType == "" ) {
		return ;
	}
	
	var regionTreeView = document.all.regionTreeView ;
	var selItem = regionTreeView.selectedItem;
	var actionResult = null ;
	
	var regionLevel = selItem.regionLevel ;//当前被选中的区域的级别
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		actionResult = reply.getResult() ;//保存服务器端操作返回的结果
		if(actionResult){
			refreshTreeList();
			alert("操作成功！");
		}else{
			alert("操作失败!") ;
		}
		nodeType ="" ;
		actionType = "" ;
	}
	
	//如果级别是“集团公司”
	if( regionLevel == "97A" ){
		//if( !$("regionInfoForm").validate()){
		if( !validateRegionForm()){
			enableDataset();
			return;
		}
		//创建区域对象并且将Dataset中的值赋值到对象中
		var region = new Region() ;
		region[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RegionVO';
		for(var ele in region){ 
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
		region.regionLevel = "97A" ;
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
		
	}else if( regionLevel == "97B" ){//省公司
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
		//省份表
		var province = new Province() ;
		province[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.RrProvinceVO';
		for( ele in province ){
			if( provinceInfo.getField( ele )){
				province[ele] = provinceInfo.getValue( ele ) ;
			}
		}
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;
		arr[1] = province ;
		ajaxCall.remoteCall("RegionService","updateProvince",arr,callBack);
			
	}else if ( regionLevel == "97C" ){//本地网
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
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
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
			
	}else if( regionLevel == "97D" ){//营业区
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
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
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
	}else if ( regionLevel == "98D" ){//处理局
		/*
		if( selItem.items ){
			if( regionInfo.getValue("virtualDealFlag") == "T" ){
				alert("当前处理局有下级母局,不能修改为虚拟局!");
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
			alert("请选择虚拟标志!");
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
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;		
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
	}else if ( regionLevel == "98E" ){ //母局
		//if( !$("regionInfoForm").validate()){
		if( !validateRegionForm()){
			enableDataset();
			return;
		}
		if( regionInfo.getValue("virtualDealFlag") == ""){
			alert("请选择虚拟标志!");
			enableDataset() ;
			return ;
		}
		if( selItem.items ){
		if( regionInfo.getValue("virtualDealFlag") == "T" ){
				alert("当前母局有下级局站,不能修改为虚拟局!");
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
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
	}else if( regionLevel == "98F" ){//局站
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
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		
		var arr = new Array();
		arr[0] = region;
		ajaxCall.remoteCall("RegionService","updateRegion",arr,callBack);
	}
}

//在Update当前的区域以后，更新TreeList上对应的记录
function refreshTreeList(){
	var regionTree = document.all.regionTreeView;
	var region = regionTree.selectedItem;
	var regionLevel = region.regionLevel ;	
		
	if( regionLevel == "97A" ||  regionLevel == "98D" || regionLevel == "98E" || regionLevel == "98F"){//集团		
		for(var ele in region){
			if(regionInfo.getField(ele)){
				region[ele] = regionInfo.getValue( ele );
			} 
		}
	}
	
	if( regionLevel == "97B" ){//省公司
		region.regionName = provinceInfo.getValue("prvName" ) ;
		region.regionCode = provinceInfo.getValue("prvCode" ) ;
		region.regionDesc = provinceInfo.getValue( "prvDesc" ) ;
		region.ngnFlag = provinceInfo.getValue("ngnFlag");
	}
	
	if( regionLevel == "97C" ){//本地网
		region.regionName = lanInfo.getValue("lanName" ) ;
		region.regionCode = lanInfo.getValue("lanCode" ) ;
		region.regionDesc = lanInfo.getValue( "lanDesc" ) ;
		region.ngnFlag = lanInfo.getValue("ngnFlag");
	}
	
	if( regionLevel == "97D" ){//营业区
		region.regionName = businessInfo.getValue("businessName" ) ;
		region.regionCode = businessInfo.getValue("businessCode" ) ;
		region.regionDesc = businessInfo.getValue( "businessDesc" ) ;	
		region.ngnFlag = businessInfo.getValue("ngnFlag");
	}
	
	region.refresh();
}

var actionType = "" ;
var nodeType = "" ;

//在区域树表上插入一个记录
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
//在区域表上删除一个记录
function deleteRegionFromTreeList(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	selItem.remove();
}

//删除一个区域
function deleteRegion_onClick(){
    var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	if( document.all.regionTreeView.items == null || document.all.regionTreeView.items.length == 0 ){
			alert("您没有任何数据权限!");
			return ;
	}
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再删除区域");
		return ;
	}
	if(selItem.items){
	alert("请先删除当前区域的所有子区域，再进行删除操作");
	return;
	}
	
	
	
	
	if( confirm( "您确信要删除当前区域信息吗?" )){
		var currentRegionId = getCurrentRegionId();
		
		var ajaxCall = new NDAjaxCall(true);
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;	
			if( deleteResult > 0 ){		     
				deleteRegionFromTreeList();//将当前记录从TreeList上删除
				alert("删除区域信息成功！");
			}else if(deleteResult == -9999)
			{
				alert("请先删除当前区域的所有子区域，再进行删除操作");		
			}
			else{
			    alert("删除区域信息失败!" );
			}
		}
		ajaxCall.remoteCall("RegionService","deleteRegion",[currentRegionId],callBack);
	}
}

//当用户点击区域树表的时候,判断当前节点是否有下级节点,如果没有,
//到服务器端查看是否有下级节点,如果服务器端返回了该节点的下级
//节点数据,则将这些数据解析成为树表的下级节点,添加到树表上.
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
//表格的点击事件，在详细面板中显示当前被点中的记录的详细信息
function clickRegion(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	//获取当前节点下的所有下级节点.
	downloadSubItems() ;
	
	cleanValues() ;//清除Dataset中的旧数据
	fillValues();//给Dataset赋值	  
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
			//给资源管理局设置所属的本地网ID和本地网名称
			var lanItem = getParentRegionById( parentItem.parentRegionId); 
			exchInfo.setValue("lanId", lanItem.regionId);
			exchInfo.setValue("lanName", lanItem.regionName);			
		}
	}
	getOrganizationByRegionId();
}

//通过上级权限ID获取上级区域对象
function getParentRegionById( parentId ){
	var regionTree = document.all.regionTreeView;
	var items = regionTree.items;
	for( var i = 0 ; i < items.length ; i ++ ){
		if( items[i].regionId == parentId ){
			return items[i] ;
		}
	}
}

//获取当前被选中的区域记录的id
function getCurrentRegionId(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	if( selItem != null ){
		return selItem.regionId ;
	}else{
		return null ;
	}
}

//隐藏所有的详细信息面板
function hidPannels(){
	document.all.groupPannel.style.display = "none" ;
	document.all.provincePannel.style.display = "none" ;
	document.all.lanPannel.style.display = "none" ;
	document.all.businessPannel.style.display = "none";
	document.all.ppdomPannel.style.display = "none" ;//处理局
	document.all.exchPannel.style.display = "none" ;//母局
	document.all.stationPannel.style.display = "none" ;//局站
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

//省份值对象
function Province(){
	this.provId  = "";
	this.prvCode = "" ;
	this.prvName = "" ;
	this.prvFlag = "" ;
	this.partyId = "";
	this.partyName = "";
}

//本地网值对象
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
//营业区值对象
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
//使用值对象设置省份数据集对象
function setProvinceInfo( provinceObj ){
	provinceInfo.clearData();
	provinceInfo.insertRecord(null) ;
	for(var ele in provinceObj){
		if(provinceInfo.getField(ele)){
			provinceInfo.setValue(ele, provinceObj[ele]);
		}
	}
}

//通过regionId从服务器端获取和区域标识对应的省份值对象
function getProvinceInfo( actionName, actionMethod, regionId ) {
	return getRegionObject( actionName, actionMethod, regionId ) ;
}

function getLanInfo( actionName, actionMethod, provinceId ) {
	return getRegionObject( actionName, actionMethod, provinceId ) ;
}

//从服务器端获取和区域相关的值对象信息
function getRegionObject( actionName, methodName, objId ){
	var ajaxCall = new NDAjaxCall(false);//同步方式查询数据库
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
	if( selItem.regionLevel != "98E" && actionType == "update" ){//98E表示母局
		alert("只有当选中区域的区域级别为母局时才能维护'共建局'信息!");
		return ;
	}
	if( selItem.regionLevel != "98D" && actionType == "insert" ){//98E表示母局
		alert("只有当选中区域的区域级别为母局时才能维护'共建局'信息!");
		return ;
	}
	if(dataset.getValue('virtualDealFlag')=='T'){
	    alert("当前区域是虚拟母局，不能维护'共建局'信息!");
	    return;
	}
	
	

	var para = new Object();
	para["privilegeType"] = "3" ;
	var menuCode = Global.getCurrentMenuCode();
	para["privilegeCode"] = menuCode;
	/*
	 *orgType -- 
	 *组织类型ID,5表示部门,0表示集团公司,1表示省公司,2表示市公司,3表示分公司,6表示班组,
	 *9表示其他组织,99表示不作类型限制,可以选择任何一种组织类型
	 */
	para["orgType"] = "5" ;
	para["selectType"] = "1" ;//1表示当选,2表示多选
	para["checkChildren"] = "2";//1表示钩选下级节点,2表示不钩选
	para["uncheckedParent"] = "2";//1表示钩选下级节点时自动取消钩选上级节点.2表示不作处理 
	para["downloadWhenChecked"] = "2";//1表示当钩选记录的时候下载下级节点,2表示钩选记录的时候不下载下级节点
	para["selectParent"] = "1";//1表示不能选择上级地域,2表示可以选择上级地域,默认不可以选择上级地域
	para["selectDistinctOrgType"] = "1" ;//1表示只能选择相同级别的组织,2表示可以选择不同级别的组织

	
	var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt;");
	if( returnValue == null || returnValue.length == 0) return ;
	vo = returnValue[0] ;
	dataset.setValue("partyId", vo.orgId);
	dataset.setValue("partyName",vo.orgName);
}