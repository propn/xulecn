//页面加载事件,将查询信息中的处理局名称字段设置为只读,必须通过选择页面选择,不能键盘输入
function page_onLoad(){
	var menuCode = Global.getCurrentMenuCode();
	lanDistrictList.parameters().setValue("districtCode","");
	lanDistrictList.parameters().setValue("districtName","");
	lanDistrictList.parameters().setValue("dealExch","");
	lanDistrictList.parameters().setValue("menuCode",menuCode);
	Dataset.reloadData( lanDistrictList ) ;
	queryInfo.setFieldReadOnly("dealExchName",true);
}

//查询按钮点击事件的响应函数.查询条件为小区编码,小区名称和处理局
function btnQuery_onClick(){
	var menuCode = Global.getCurrentMenuCode();
	lanDistrictList.parameters().setValue("districtCode", queryInfo.getValue("districtCode"));
	lanDistrictList.parameters().setValue("districtName", queryInfo.getValue("districtName"));
	lanDistrictList.parameters().setValue("dealExch", queryInfo.getValue("dealExch"));
	lanDistrictList.parameters().setValue("menuCode",menuCode);
	Dataset.reloadData( lanDistrictList ) ;
}

//查询信息Form中重置按钮点击事件响应函数
function btnReset_onClick(){
	queryInfo.clearData() ;
}

//小区列表的游标滚动事件响应函数,使用列表上的数据初始化详细信息Form的数据
function lanDistrictList_afterScroll(){
	lanDistrictInfo.clearData();
	actionType = "" ;
	lanDistrictInfo.setReadOnly(true) ;
	for( var i = 0 ; i < lanDistrictInfo.fields.length ; i ++ ){
		if( lanDistrictList.getField( lanDistrictInfo.fields[i].name )){
			lanDistrictInfo.setValue( i , lanDistrictList.getValue( i ) );
		}
	}
	//setRegionVO( "dealExch","dealExchName") ;
	//setRegionVO( "subExch","subExchName") ;
}

//通过ID调用服务器端的服务获取地域的名称
/*
function setRegionVO( fieldId, fieldName ) {
	var ajaxCall = new NDAjaxCall( true ) ;
	var vo = new Object();
	var callBack = function( reply ) {
		vo = reply.getResult() ;
		if( vo != null ){
			lanDistrictInfo.setValue(fieldName,vo["regionName"]);
		}
	}
	var id = lanDistrictList.getValue(fieldId);
	ajaxCall.remoteCall( "PartyService","getRegionById",[id],callBack); 
	return vo ;
}
*/
var actionType = "";
function addLanDistrict_onClick(){//增加小区按钮的点击事件响应函数
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加!");
		return ;
	}
	actionType = "insert" ;
	lanDistrictInfo.clearData();
	lanDistrictInfo.setReadOnly(false) ;
	//处理局和局站字段值不能通过键盘输入,必须通过对话框选择,设置这两个字段为只读
	lanDistrictInfo.setFieldReadOnly("dealExchName",true);
	lanDistrictInfo.setFieldReadOnly("subExchName",true);	
}

//编辑按钮点击事件的响应函数
function editLanDistrict_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再编辑!");
		return ;
	}
	actionType = "update" ;
	lanDistrictInfo.setReadOnly(false) ;
	//处理局和局站字段值不能通过键盘输入,必须通过对话框选择,设置这两个字段为只读
	lanDistrictInfo.setFieldReadOnly("dealExchName",true);
	lanDistrictInfo.setFieldReadOnly("subExchName",true);	
}

//删除按钮点击事件的响应函数
function deleteLanDistrict_onClick(){
	if( !lanDistrictList.getCurrent() ){
		return ;
	}
	
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再删除!");
		return ;
	}
	
	if( !confirm( "您确定要删除当前记录吗?")){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ) {
		alert("删除记录成功!");
		Dataset.reloadData( lanDistrictList ) ;
	}
	
	var id = lanDistrictList.getValue("districtId");
	ajaxCall.remoteCall( "PartyService","deleteLanDistrict",[id],callBack);
}

//判断小区编码是否已经存在,必须在系统中保证小区编码的唯一性
function checkLanCodeExist( code ){
	var ajaxCall = new NDAjaxCall( false ) ;
	var result ;
	
	var callBack  = function(reply){
		result = reply.getResult() ;
	}
	
	ajaxCall.remoteCall("PartyService","checkLanCodeExist",[code],callBack);
	return result ;
}

//确定按钮点击事件的响应函数
function commit_onClick(){
	if( actionType == "" ){
		return ;
	}
	
	if( !$("lanDistrictInfoForm").validate()){
		return ;
	}
	
	var lanDistrictVO = new LanDistrictVO();
	for( var ele in lanDistrictVO ){
		lanDistrictVO[ele] = lanDistrictInfo.getValue(ele) ;
	}
	
	//为了保证小区编码在系统的唯一性,必须在增加和编辑小区信息的时候进行校验系统中是否已经存在
	//和用户输入的小区编码一样的编码
	if( actionType == "insert" ){
		if( checkLanCodeExist(lanDistrictVO.districtCode) ){
			alert("已经存在编码为" + lanDistrictVO.districtCode + "的小区,请填写其他编码!");
			return ;
		}
	}else{
		if( lanDistrictVO.districtCode != lanDistrictList.getValue("districtCode" )){
			if( checkLanCodeExist(lanDistrictVO.districtCode) ){
				alert("已经存在编码为" + lanDistrictVO.districtCode + "的小区,请填写其他编码!");
				return ;
			}
		}
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ) {
		if( actionType == "insert" ){	
			alert("增加记录成功!");
		}else if ( actionType == "update" ){
			alert("编辑记录成功!");
		}
		actionType = "";
		Dataset.reloadData( lanDistrictList ) ;
		lanDistrictInfo.setReadOnly(true) ;
	}
	
	if( actionType == "insert" ){	
		ajaxCall.remoteCall( "PartyService","insertLanDistrict",[lanDistrictVO],callBack);
	}else if ( actionType == "update" ){
		ajaxCall.remoteCall( "PartyService","updateLanDistrict",[lanDistrictVO],callBack);
	}
}

//取消按钮点击事件的响应函数
function cancel_onClick(){
	actionType = "" ;
	lanDistrictInfo.setReadOnly(true) ;
	lanDistrictList_afterScroll();
}

//查询信息Form的处理局字段按钮点击事件的响应函数,弹出资源线地域选择窗口
//由用户选择处理局
function button_queryInfo_dealExchName_onClick(){
	var vo = selectRegion("98D");
	if( !vo ) {
		return ;
	}
	queryInfo.setValue("dealExch",vo.regionId);
	queryInfo.setValue("dealExchName",vo.regionName);
}

//详细信息Form的局站字段按钮点击事件的响应函数,弹出资源线的地域选择窗口由用户
//选择局站
function button_lanDistrictInfo_subExchName_onClick(){
	if(actionType == "" ){
		return ;
	}
	var returnValue = selectRegion("98F") ;
	if( !returnValue ){
		return ;
	}
	lanDistrictInfo.setValue("subExch",returnValue["regionId"]);
	lanDistrictInfo.setValue("subExchName",returnValue["regionName"]);
	
	//选到局站以后,通过服务器端调用,查询用户选择的局站的上级处理局,
	//获取处理局的ID和名称并初始化到详细信息Form中
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ) {
		var vo = reply.getResult() ;
		if( vo != null ){
			lanDistrictInfo.setValue("dealExch",vo["regionId"]);
			lanDistrictInfo.setValue("dealExchName",vo["regionName"]);
		}
	}
	
	var id = lanDistrictInfo.getValue("subExch");
	ajaxCall.remoteCall( "PartyService","getDealExchByExchStation",[id],callBack);	
}

//选择资源线地域函数,函数参数regionLevel指定了要选择的地域级别.
function selectRegion(regionLevel){
	var arr1 = new Object();
	var menuCode = Global.getCurrentMenuCode();
	arr1["privilegeType"] = "3";//权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码；
	//权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
	arr1["privilegeCode"] = menuCode ;//菜单编码 
	arr1["regionLevel"] =  regionLevel;	 
	arr1["selectType"] = "1" ;//单选多选标志,1 为单选,2 为多选 

	returnValue = showModalDialog("../common/ResourceRegionSelect.jsp",arr1,"dialogHeight: 350pt; dialogWidth: 450pt;");
	if( returnValue == null || returnValue.length == 0 ){
		return null ;
	}
	return returnValue[0] ;
}

//小区信息值对象
function LanDistrictVO(){
	this.districtId = "";//小区ID,主键
	this.districtCode = "";//小区编码
	this.districtName = "";//小区名称
	this.districtType = "";//小区类型
	this.districtAddr = "";//小区地址
	this.dealExch = "";//处理局
	this.subExch = "";//局站
}