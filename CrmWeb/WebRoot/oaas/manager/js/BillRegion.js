/**
97A        集团
97B        省
97C        本地网
97D        营业区
97E        计费局向
98D        处理局
98E        母局
98F        局站
*/
function page_onLoad(){
	initRegion();
}
	
//初始化TreeList组件的数据显示
function initRegion(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.regionTreeView.loadByXML(queryResult);
		
		//在详细面板对应的Dataset上插入一个空记录
		regionInfo.insertRecord(null);
		
		globleItem = document.all.regionTreeView.items[0];
		fillValues(globleItem); 		
	}
	ajaxCall.remoteCall("RegionService","getBillRegionList",["-1"],callBack);
}

//清除详细面板上的信息
function cleanValues(){
	regionInfo.clearData();//集团公司
	regionInfo.insertRecord( null ) ;
	
	provinceInfo.clearData();//省公司
	provinceInfo.insertRecord(null);
	
	lanInfo.clearData();//本地网
	lanInfo.insertRecord( null ) ;
	
	businessInfo.clearData();//营业区
	businessInfo.insertRecord( null ) ;
	
	exchangeInfo.clearData() ;//局向
	exchangeInfo.insertRecord( null ) ;
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
			provinceInfo.setValue("prvDesc", selItem.regionDesc);
			provinceInfo.setValue("ngnFlag",selItem.ngnFlag);
			provinceInfo.setValue("isActualRegion",selItem.isActualRegion);
			provinceInfo.setValue("provinceCode",selItem.provinceCode);
		}
		hidPannels();
		document.all.provincePannel.style.display = "block" ;
	}else if ( regionLevel == "97C" ){//本地网
		var regionId = selItem.regionId ;
		var obj = getLanInfo( "RegionService", "getLanByRegionId", regionId );//根据区域标识到服务器端获取和该区域标识对应的本地网信息
		if( obj != null ){
			setLanInfo( obj ) ;
			lanInfo.setValue("lanDesc", selItem.regionDesc);
			lanInfo.setValue("ngnFlag", selItem.ngnFlag); 
			lanInfo.setValue("isActualRegion",selItem.isActualRegion);
			lanInfo.setValue("provinceCode",selItem.provinceCode);			
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
			businessInfo.setValue("isActualRegion",selItem.isActualRegion);
			businessInfo.setValue("provinceCode",selItem.provinceCode);			
		}
		hidPannels() ;
		document.all.businessPannel.style.display = "block" ;
	}else if ( regionLevel == "97E" ){//局向
		//显示区域信息
		exchangeInfo.clearData();
		exchangeInfo.insertRecord(null);
		for(var ele in selItem){//使用当前被选中的记录来填充Dataset
			if(exchangeInfo.getField(ele)){
				exchangeInfo.setValue(ele, selItem[ele]);
				exchangeInfo.setValue("ngnFlag", selItem.ngnFlag ) ;
				exchangeInfo.setValue("isActualRegion",selItem.isActualRegion);
				exchangeInfo.setValue("provinceCode",selItem.provinceCode);			
			}
		}
		hidPannels();
		document.all.exchangePannel.style.display = "block" ;
		//将标签页定位到第一页
		
	}
}

//当用户点击区域树表的时候,判断当前节点是否有下级节点,如果没有,
//到服务器端查看是否有下级节点,如果服务器端返回了该节点的下级
//节点数据,则将这些数据解析成为树表的下级节点,添加到树表上.
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
		if( selItem.regionLevel == "97A" ){//集团公司
			
		}else if (selItem.regionLevel == "97B" ){//省公司

		}else if ( selItem.regionLevel == "97C" ){//本地网
			lanInfo.setValue("provId", parentItem.regionId);
			lanInfo.setValue("provName", parentItem.regionName);
		}else if ( selItem.regionLevel == "97D" ){//营业区
			businessInfo.setValue("lanId", parentItem.regionId);
			businessInfo.setValue("lanName", parentItem.regionName) ;
		}else if ( selItem.regionLevel == "97E" ){//局向
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
	document.all.businessPannel.style.display = "none" ;
	document.all.exchangePannel.style.display = "none" ;
}

//编辑区域
function editRegion_onClick(){
	if( actionType != "" ) {
		alert("请先保存或者取消当前操作再编辑区域!");
		return;
	}
	actionType = "update" ;
	enableDataset();
} 

//增加区域
function addRegion(parentRegionId){
	enableDataset();
	var selItem = regionTreeView.selectedItem ;
	cleanValues();
	hidPannels();
	if( parentRegionId == null ){//增加一个根区域 
		document.all.groupPannel.style.display = "block" ;//显示集团信息面板
		regionInfo.setValue("regionLevel", "97A"); 
		return;
	} 
	
	if( parentRegionId != null ){//如果是在一个上级区域下面增加子区域。
		var item = document.all.regionTreeView.selectedItem ;//当前被选中的记录
		
		regionInfo.setValue("parentRegionName", item.regionName ) ;//设置上级区域的名称
		regionInfo.setValue("parentRegionId",item.regionId);//设置上级区域的ID
		
		var regionLevel = item.regionLevel ;
		
		if( regionLevel == "97A" ){//在集团公司下面，增加的是省公司区域
			document.all.provincePannel.style.display = "block";
			regionInfo.setValue("regionLevel", "97B"); 
		}else if ( regionLevel == "97B" ){//在省公司下面，增加的是本地网区域
			document.all.lanPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "97C");
			 //给本地网Dataset设置上级区域名称（所属省份名称）和上级区域ID
			 lanInfo.setValue("provId", selItem.regionId);
			 lanInfo.setValue("provName",selItem.regionName);
			 
		}else if ( regionLevel == "97C" ){ //在本地网下面，增加的是营业区区域 
			document.all.businessPannel.style.display = "block" ;
			regionInfo.setValue("regionLevel", "97D");
			//给营业区Dataset设置上级本地网名称和ID
			businessInfo.setValue("lanId", selItem.regionId);
			businessInfo.setValue("lanName", selItem.regionName);
			businessInfo.setValue("businessCode",selItem.regionCode);
		}else if ( regionLevel == "97D" ){//在营业区下面，增加的是局向区域
			document.all.exchangePannel.style.display = "block" ;
			exchangeInfo.setValue("regionLevel", "97E");
			exchangeInfo.setValue("parentRegionId",selItem.regionId);
			exchangeInfo.setValue("parentRegionName",selItem.regionName);
			exchangeInfo.setValue("regionCode",selItem.regionCode);
		} 
	}
}

//删除一个区域
function deleteRegion_onClick(){
	if( actionType != "" ) {
		alert("请先保存或者取消当前操作再删除区域!");
		return;
	}
	if( confirm( "您确信要删除当前区域信息吗?" )){
		var currentRegionId = getCurrentRegionId();
		
		var ajaxCall = new NDAjaxCall(true);
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;
			if( deleteResult ){
				deleteRegionFromTreeList();//将当前记录从TreeList上删除
				alert("删除区域信息成功！");
			}else{
				alert("删除区域信息失败!" );
			}
		}
		ajaxCall.remoteCall("RegionService","deleteRegion",[currentRegionId],callBack);
	}
}

//在区域表上删除一个记录
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
	if( selItem.regionLevel == "97A"){//被选的是集团公司
		if( actionType == "update" ){
			regionInfo.setFieldReadOnly("regionLevel" , true );
		}else if( actionType =="insert"){
			if( nodeType == "root" ){
				regionInfo.setFieldReadOnly("regionLevel" , true );
			}else{
				
			}
		}
	}
	if( selItem.regionLevel == "97B" ){//被选的是省公司
		if( actionType == "insert" ){
			lanInfo.setFieldReadOnly("provName",true);
		}
	}
	if( selItem.regionLevel == "97C" ){//被选的是本地网
		if( actionType == "update" ){
			lanInfo.setFieldReadOnly("provName",true);
		}else if( actionType == "insert"){
			businessInfo.setFieldReadOnly("lanName",true);
		}
	}
	if( selItem.regionLevel == "97D" ){//被选的是营业区
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
	var ajaxCall = new NDAjaxCall( false ) ;//异步方式查询
	var result ;
	var callBack = function( reply ) {
		result = reply.getResult() ;
	}
	
	ajaxCall.remoteCall("RegionService", "checkExistRegionCode",[regionCode], callBack ) ;
	return result ;
}

//提交插入一个区域的请求
function commitInsertRegion(){
	var regionTreeView = document.all.regionTreeView ;
	var selItem = regionTreeView.selectedItem;
	var actionResult = null ;
	
	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		actionResult = reply.getResult() ;//保存服务器端操作返回的结果
		//服务器端返回新增加的区域的ID
		refreshDatainfo(actionResult);//完成增加以后，必须重新初始化关联的Dataset里面的响应信息	
		addRegionToTreeList();
		actionType = "" ; 
		nodeType = "" ;
		alert("操作成功！");
	}
	
	var regionLevel ;//当前被选中的区域的级别
	if( selItem == null ){
		regionLevel == "" ;
	}else{
		regionLevel = selItem.regionLevel ;
	}
	
	//如果级别是“集团公司”并且nodeType是root，就说明要增加集团公司根节点
	//如果区域级别是“营业区”，增加的就是局向，增加局向单独插入区域表就可以了，
	//只有在给局向设置号码段的时候才会插入局向表。

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
		//创建区域对象并且将Dataset中的值赋值到对象中
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
			region.parentRegionId = selItem.regionId ;//上级区域ID
			region.ngnFlag = exchangeInfo.getValue("ngnFlag");
			region.isActualRegion = exchangeInfo.getValue("isActualRegion");
			region.provinceCode = exchangeInfo.getValue("provinceCode");		
			region.regionLevel = "97E" ;

//			if( region.regionCode.substring(0,4) != selItem.regionCode ){
//				alert("营业厅编码的前四位必须是营业厅编码("+selItem.regionCode+") ！");
//				exchangeInfo.setValue("regionCode", selItem.regionCode);
//				enableDataset();
//				return ;
//			}

		}
		
		var arr = new Array();
		arr[0] = region;
		
		if( checkExistRegionCode( region.regionCode )){
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		ajaxCall.remoteCall("RegionService","insertRegion",arr,callBack);
		
	}else{//增加的是子节点
		//如果区域级别是“集团公司”，增加的就是省公司，
		if( regionLevel == "97A" ){
			if( !$("provinceInfoForm").validate()){
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
			region.isActualRegion = provinceInfo.getValue("isActualRegion");
			region.provinceCode = provinceInfo.getValue("provinceCode");
		
			var province = new Province() ;
			for( ele in province ){
				if( provinceInfo.getField( ele )){
					province[ele] = provinceInfo.getValue( ele ) ;
				}
			}
			
			if( checkExistRegionCode( region.regionCode )){
				alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
				enableDataset();
				return ;
			}
			var arr = new Array();
			arr[0] = region;
			arr[1] = province ;
			ajaxCall.remoteCall("RegionService","insertProvince",arr,callBack);
			
		}else if ( regionLevel == "97B" ){//在省公司下面，增加的是本地网区域
			if( !$("lanInfoForm").validate()){
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
			region.flag = lanInfo.getValue("flag");
			region.ngnFlag = lanInfo.getValue("ngnFlag") ;
			region.isActualRegion = lanInfo.getValue("isActualRegion");
			region.provinceCode = lanInfo.getValue("provinceCode");
			
		if( checkExistRegionCode( region.regionCode )){
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		
			//本地网
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
			
		}else if ( regionLevel == "97C" ){//在本地网下面，增加的是营业区区域
			if( !$("businessInfoForm").validate()){
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
			region.isActualRegion = businessInfo.getValue("isActualRegion");
			region.provinceCode = businessInfo.getValue("provinceCode");
			region.configId = "";

//			if( region.regionCode.substring(0,2) != selItem.regionCode){
//				alert("营业区编码的前两位必须是本地网编码("+selItem.regionCode+") ！");
//				businessInfo.setValue("businessCode", selItem.regionCode);
//				enableDataset();
//				return ;
//			}

			if( checkExistRegionCode( region.regionCode )){
				alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
				enableDataset();
				return ;
			}
		
			//省份表
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

		}else if ( regionLevel == "97D" ){//在营业区下面，增加的是局向区域
			
		}
	}
}

//提交更新一个区域的请求
function commitUpdateRegion(){
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
		actionType = "" ;
		nodeType = "" ;
	} 
	
	//如果级别是“集团公司”并且nodeType是root，就说明要增加集团公司根节点
	//如果区域级别是“营业区”，增加的就是局向，增加局向单独插入区域表就可以了，
	//只有在给局向设置号码段的时候才会插入局向表。 
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

		//创建区域对象并且将Dataset中的值赋值到对象中
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
//				alert("营业厅编码的前四位必须是营业厅编码("+selItem.regionCode+") ！");
//				exchangeInfo.setValue("regionCode", selItem.regionCode);
//				enableDataset();
//				return ;
//			}

		}
		var arr = new Array();
		arr[0] = region;
		
		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
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
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
			enableDataset();
			return ;
		}
		
		//省份表
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
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
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
//			alert("营业区编码的前两位必须是本地网编码("+selItem.regionCode+") ！");
//			businessInfo.setValue("businessCode", selItem.regionCode);
//			enableDataset();
//			return ;
//		}

		if( checkExistRegionCode( region.regionCode ) && region.regionCode != selItem.regionCode ){
			alert("系统中已经存在编码为" + region.regionCode + "的记录了,请使用其他编码!");
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

//在Update当前的区域以后，更新TreeList上对应的记录
function refreshTreeList(){
	var regionTree = document.all.regionTreeView;
	var region = regionTree.selectedItem;
	var regionLevel = region.regionLevel ;		
	
	if( regionLevel == "97A" ){//集团	
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
		region.isActualRegion = provinceInfo.getValue("isActualRegion");
		region.provinceCode = provinceInfo.getValue("provinceCode");
	}
	
	if( regionLevel == "97C" ){//本地网
		region.regionName = lanInfo.getValue("lanName" ) ;
		region.regionCode = lanInfo.getValue("lanCode" ) ;
		region.regionDesc = lanInfo.getValue( "lanDesc" ) ;
		region.ngnFlag = lanInfo.getValue("ngnFlag");
		region.isActualRegion = lanInfo.getValue("isActualRegion");
		region.provinceCode = lanInfo.getValue("provinceCode");

	}
	
	if( regionLevel == "97D" ){//营业区
		region.regionName = businessInfo.getValue("businessName" ) ;
		region.regionCode = businessInfo.getValue("businessCode" ) ;
		region.regionDesc = businessInfo.getValue( "businessDesc" ) ;	
		region.ngnFlag = businessInfo.getValue("ngnFlag");
		region.isActualRegion = businessInfo.getValue("isActualRegion");
		region.provinceCode = businessInfo.getValue("provinceCode");

	}
	
	if( regionLevel == "97E" ){//局向
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
	var regionLevel ;//当前被选中的区域的级别
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

//在区域树表上插入一个记录
function addRegionToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
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
		region.isActualRegion = provinceInfo.getValue("isActualRegion");
		region.provinceCode = provinceInfo.getValue("provinceCode");
		region.regionLevel = "97B" ;
	}
	
	if( selItem.regionLevel == "97B" ){//增加的是本地网
		region.regionId = lanInfo.getValue("lanId") ;
		region.regionName = lanInfo.getValue("lanName") ;
		region.regionCode = lanInfo.getValue("lanCode") ;
		region.regionDesc = lanInfo.getValue("lanDesc");
		region.ngnFlag = lanInfo.getValue("ngnFlag");
		region.isActualRegion = lanInfo.getValue("isActualRegion");
		region.provinceCode = lanInfo.getValue("provinceCode");		
		region.regionLevel = "97C" ;		
	}
	
	if( selItem.regionLevel == "97C" ){//增加的是营业区
		region.regionId = businessInfo.getValue("businessId");
		region.regionName = businessInfo.getValue("businessName") ;
		region.regionCode = businessInfo.getValue("businessCode") ;
		region.regionDesc = businessInfo.getValue("businessDesc") ;
		region.ngnFlag = businessInfo.getValue("ngnFlag");
		region.isActualRegion = businessInfo.getValue("isActualRegion");
		region.provinceCode = businessInfo.getValue("provinceCode");		
		region.regionLevel = "97D" ;		
	}
	
	if( selItem.regionLevel == "97D" ){//增加的是局向
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
	//把该节点选择上，如果批量插入，建议不要选择
	region.setSelected();
}

var actionType = "";
//var regionLevel="";
var nodeType = "";

//增加一个下级区域
function addSubRegion_onClick(){
	if( actionType != "" ) {
		alert("请先保存或者取消当前操作再增加区域!");
		return;
	}
	var selItem = regionTreeView.selectedItem ;
	if( selItem == null ){
		return ;
	}
	if( selItem.regionLevel == "97E" ){
		alert("当前节点是交换局,不能在交换局下面增加子区域!");
		return ;
	}
	actionType = "insert";
	nodeType = "child";
	addRegion( getCurrentRegionId() ) ;
}

//增加一个根区域
function addRootRegion_onClick(){ 
	if( actionType != "" ) {
		alert("请先保存或者取消当前操作再增加区域!");
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


//在TabPage之间切换的时候触发，当切换到号段管理页面的时候，通过当前的
//局向的区域的ID到服务器端获取这个局向对应的号段信息，显示在TreeList中。
function exchangePage_onPageChanged(page,index){
		return;
}

function exchangePage_beforePageChanged( page, index ){
	if( actionType != "" && index == 0) {
		alert("请先点击'确定'按钮保存当前操作再切换标签页!");
		return false ;
	}
	return true ;
}

function queryNbrList(){
	var currentRegion = getCurrentRegionId();//获取区域TreeList中当前局向区域的ID

	var parameterSet = accNbrList.parameters();
	parameterSet.setDataType("regionId","string");
	parameterSet.setValue("regionId", currentRegion);
	Dataset.reloadData( accNbrList );
}

function initAccNbrList(){
}

//增加一个号码段
function addAccNbr_onClick(){
	var arr = new Array() ;
	arr[0] = exchangeInfo;
	arr[1] = "insert" ;
	var selItem = regionTreeView.selectedItem ;
	var parentItem = getParentRegionById( selItem.parentRegionId );
	arr[2] = parentItem.regionId ;//营业区ID
	arr[3] = parentItem.regionName ;//营业区名称
	 
	var returnValue=window.showModalDialog("ExchangeDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue ){ 
		queryNbrList();
	}
}

//编辑一个号码段
function editAccNbr_onClick(){
	//var selItem = document.all.accNbrTreeView.selectedItem;
	//if( selItem == null ) return ;
	//局向值对象
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
	arr[2] = parentItem.regionId ;//营业区ID
	arr[3] = parentItem.regionName ;//营业区名称
		
	var returnValue=window.showModalDialog("ExchangeDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue ){ 
		queryNbrList();
		//refreshAccNbrTreeList( returnValue ) ;
		//accNbrTreeView.selectedItem.exchId = returnValue["exchId"] ;
	}
}

/*
//在编辑好一个号码段以后更新树表上的数据
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

//删除一个号码段
function deleteAccNbr_onClick(){ 
	//var selItem = document.all.accNbrTreeView.selectedItem ;
	//if( selItem == null ) return ;
	var exchangeId = accNbrList.getValue("exchId");// selItem.exchId ;

	var ajaxCall = new NDAjaxCall(true); 
	var callBack = function(reply){
		var result = reply.getResult() ;
		if( result ){
			alert("删除号码段成功！");
			queryNbrList();
		}else {
			alert( "删除号码段失败!") ;
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

//省份值对象
function Province(){
	this.provId  = "";
	this.prvCode = "" ;
	this.prvName = "" ;
	this.prvFlag = "" ;
	this.prvDesc = "" ;
}

//本地网值对象
function Lan(){
	this.provId  = "";
	this.lanId = "" ;
	this.lanCode = "";
	this.lanName = "" ;
	this.lanDesc = "";
	this.flag = "" ;
	this.areaCode = "";
}

//营业区值对象
function Business(){
	this.lanId = "" ;
	this.lanName = "" ;
	this.businessId = "" ;
	this.businessCode = "" ;
	this.businessName = "" ;
	this.businessDesc = "" ;
}

//局向值对象
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
	var ajaxCall = new NDAjaxCall(false);
	var obj ;
	var callBack = function( reply ){
		obj = reply.getResult() ;
	}
	ajaxCall.remoteCall(actionName,methodName,[objId],callBack);
	return obj ;
}
