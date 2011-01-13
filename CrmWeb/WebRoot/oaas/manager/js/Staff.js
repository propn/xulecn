var menuCode = "";
function page_onLoad(){
    var s = new Date() ;
	menuCode = Global.getCurrentMenuCode();
	initOrganizationTree();
	//jsDebug.debug("staff page load time="+(new Date()-s));
}

function table_staffList_onclick(){
  	staffTableClick();
}

function initOrganizationTree(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.orgTreeView.loadByXML(queryResult );
		
		var rootItems = orgTreeView.rootItems ;
		for( var i = 0 ; i < rootItems.length ; i ++ ){
			var item = rootItems[i] ;
			if( ( item.orgTypeId == "5" || item.orgTypeId == "6") && item.state == "00A" ){
				item.setFontColor("#4422FF"); 
			}
			item.removeChildren();
		}
		clickOrganization();
		staffInfor.setFieldPopupEnable("orgPartyName", false);
		staffInfor.setFieldPopupEnable("officeName", false);
	}
	
	ajaxCall.remoteCall("PartyService","getRootOrganizationListByPrivControl",[menuCode],callBack);	
//	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}

//查询Staff并且填充相应的表格对象
function doQuery_onClick(){
	staffList.clearData();
	staffList.insertRecord(null);
	staffInfor.clearData();
	staffInfor.insertRecord(null);
	
	var staff = new Staff();
	if( orgTreeView.selectedItem == null ){
		return ;
	}
	staff.orgPartyId = orgTreeView.selectedItem.partyId ;
	staff.staffCode = queryInfo.getValue("staffCode" );
	staff.partyName = queryInfo.getValue("partyName");
	staff.scope = queryInfo.getValue("queryScope");
	
	
	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;
	Dataset.reloadData( staffList ) ;
	
	staffTableClick();
}

//重置查询Form
function doReset_onClick(){
	queryInfo.clearData();
	queryInfo.insertRecord( null ) ;
}

var actionType = "" ;
//增加员工
function addStaff_onClick(){ 
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再增加员工!") ;
		return ;
	}
	staffInfor.clearData();
	staffInfor.insertRecord( null ) ;
	staffInfor.setValue("state","00A");
	staffInfor.setValue("expDate","2029-12-31");
	staffInfor.setFieldReadOnly("state",true);//新增加的员工必须是有效的.
	actionType = "insert" ;
	staffInfor.setReadOnly( false ) ;
	staffInfor.setFieldPopupEnable("orgPartyName",true);
	staffInfor.setFieldPopupEnable("officeName",true);	
	staffInfoPage.setSelectedPageIndex(0);
	staffInfor.setFieldReadOnly("orgPartyName",true);
	staffInfor.setFieldReadOnly("officeName",true);	
	staffInfor.setFieldReadOnly("devOrgName",true);
	selectedOrgCode = "";
}
//通过拷贝当前员工的属性增加一个员工
function addCopyStaff_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再复制员工!") ;
		return ;
	}
	if( !staffList.getCurrent()){
		return ;
	}
	if( staffList.getValue("state") != "00A" ){
		alert("当前员工的状态不是有效状态,不能被复制!");
		return ;
	} 
	if( staffList.getValue("partyRoleId") == "" ){
		return ; 
	} 
	window.showModalDialog("CopyStaff.jsp",[staffList.getValue("partyRoleId"),staffList.getValue("channelSegmentId")],"dialogHeight: 300pt; dialogWidth: 300pt;");	
	getStaffByOrgId();
	return ;
}
//批量增加员工
function addBatchStaff_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再批量增加员工!") ;
		return ;
	}
	
	if( orgTreeView.selectedItem.orgTypeId != "5" 
		&& orgTreeView.selectedItem.orgTypeId != "6"){
		alert("当前组织不是部门或班组,只能在部门或班组下面增加员工!");
		return ;
	}
	if( orgTreeView.selectedItem.state != "00A" ){
		alert("当前组织的状态不是有效状态,不能在其下面增加员工!");
		return;
	}
		
	var arr = new Array();
	arr[0] = orgTreeView.selectedItem.partyId ;//人员所在组织的ID
	var returnValue=window.showModalDialog("BatchAddStaff.jsp",arr,"dialogHeight: 300pt; dialogWidth: 300pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue !=null){
		
		var orgPartyId = orgTreeView.selectedItem.partyId ;
		
		var ajaxCall = new NDAjaxCall(true);
		var result ;
		var callBack = function( reply ){
			result = reply.getResult() ; 
			alert(result );
/*			if( result != null || result!="" ){
				alert("以下工号已经在系统中存在,批量插入的时候忽略: " + result );
			}else{
				alert("批量插入员工成功!");
			}*/
			clickOrganization();
		}
		var arr = new Array();
		arr[0] = orgPartyId ; //所属组织Id
		arr[1] = returnValue["startStaffCode"];//parseInt(returnValue["startStaffCode"]);//起始员工号
		arr[2] = returnValue["endStaffCode"];//parseInt(returnValue["endStaffCode"]);//结束员工号
		arr[3] = returnValue["staffName"];//员工名称
		arr[4] = returnValue["orgPostId"];//岗位
		arr[5] = returnValue["state"];
		arr[6] = returnValue["effDate"];
		arr[7] ="2029-01-01 00:00:00";
		arr[8] = returnValue["channelSegmentId"];
		arr[9] = "123456";
		arr[10] = returnValue["lanId"];
		arr[11] = returnValue["businessId"];
		arr[12] = returnValue["countyType"];
		arr[13] = returnValue["orgManager"];//add 严俊波 2010/4/19
		arr[14] = returnValue["gender"];
		
		ajaxCall.remoteCall("PartyService","insertBatchStaff",arr,callBack); 
	}
}

function getSuperStaffCode(){
	var returnValue = "" ;
	var ajaxCall = new NDAjaxCall( false ) ;
	var callBack = function( reply ) {
		returnValue = reply.getResult();
	}
	ajaxCall.remoteCall("PartyService","getSuperStaffCode",[],callBack);
	return returnValue ;
}

function ifSuperManager(){
	var returnValue = false ;
	var ajaxCall = new NDAjaxCall( false ) ;
	var callBack = function( reply ) {
		returnValue = reply.getResult();
	}
	ajaxCall.remoteCall("PartyService","ifSuperManager",[],callBack);
	return returnValue ;
}
//在修改员工信息的时候,旧密码是密文状态的字符串,如果用户没有修改密码,则
//密文状态的密码长度会超过16为,所以必须保存旧密码,在提交的时候判断新密码
//是否和旧密码一致,如果一致,则无需判断密码的长度,否则,需要判断密码长度在6到15之间
var oldPassword = "" ;
//编辑员工
function editStaff_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再编辑员工!") ;
		return ;
	}
	//if( staffInfor.getValue("staffCode") == "999999" ){
	if( staffInfor.getValue("staffCode") == getSuperStaffCode() ){
		if( !ifSuperManager() ){
			alert("您无权修改超级工号信息!");
			return ;
		}
	}
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	actionType = "update";
	staffInfor.setReadOnly( false ) ;
	staffInfor.setFieldPopupEnable("orgPartyName",true);	
	staffInfor.setFieldReadOnly("orgPartyName",true);
	staffInfor.setFieldPopupEnable("officeName",true);	
	staffInfor.setFieldReadOnly("officeName",true);	
	staffInfor.setFieldReadOnly("staffCode",true);
	staffInfor.setFieldReadOnly("devOrgName",true);
	staffInfoPage.setSelectedPageIndex(0);	
	oldPassword = staffInfor.getValue("password") ;
}

//人员表格的点击事件响应函数
function staffTableClick(){
	if( staffInfoPage.getSelectedPageIndex() != 0 ){
		staffInfoPage.setSelectedPageIndex(0);
		return ;
	}
	
	//填充"人员信息"面板
	staffInfor.disableControls() ;
	staffInfor.clearData();
	staffInfor.insertRecord( null );
	staffInfor.setReadOnly( true );
	staffInfor.setFieldPopupEnable("orgPartyName", false);
	staffInfor.setFieldPopupEnable("officeName", false);
	var number = 0 ;
	for( var i = 0; i < staffList.fields.length; i ++ ){
		var fieldName = staffList.fields[i].name ;
		staffInfor.setValue( i, staffList.getValue(i) ) ;
		number ++ ;
	}
	//staffInfor.setValue("orgPartyName", orgTreeView.selectedItem.orgName);
	staffInfor.enableControls();
	actionType = "" ;
	getWorkingOfficeDesc( staffInfor.getValue("officeId"));
	getDevUserBelongName( staffInfor.getValue("devUserBelong"));
}
function getDevUserBelongName( orgId ) {
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ) {
		vo = reply.getResult();
		staffInfor.setValue( "devUserBelongName", vo.orgName );
	}
	ajaxCall.remoteCall("PartyService","getOrganization",[orgId],callBack);
}
function getWorkingOfficeDesc( officeId ) {
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ) {
		staffInfor.setValue( "officeName", reply.getResult());
	}
	ajaxCall.remoteCall("PartyService","getOfficeDescByOfficeId",[officeId],callBack);
}
function downloadSubItems(){
	var selItem = orgTreeView.selectedItem ;
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
			
			if( selItem.items ){
				for( var i = 0; i < selItem.items.length ; i ++ ){
					var item = selItem.items[i] ;
					if( ( item.orgTypeId == "5" || item.orgTypeId == "6") && item.state == "00A" ){
						item.setFontColor( "#4422FF" ) ;
					}
				}
			}
		}
	}
	
	var regionId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
}
//组织树的点击时间,从树上获取orgId,并根据组织id到服务器端查询对应的人员
function clickOrganization(){
	downloadSubItems();
	getStaffByOrgId();
	staffTableClick();
}

function staffInfoPage_beforePageChanged(page,index){
	if( actionType != ""){
		alert("请先点击'确定'按钮保存数据后再切换到其他标签页!");
		return false ;
	}
	return true ;
}

function staffInfoPage_onPageChanged(page, index){
	if( index == 0 ){
		if( staffList.getValue("partyRoleId" ) == "" ){
			return ;
		}
		//填充"人员信息"面板
		staffInfor.clearData();
		staffInfor.insertRecord( null );
	
		staffInfor.disableControls() ;
		
		for( var i = 0; i < staffList.fields.length; i ++ ){
			staffInfor.setValue( i, staffList.getValue(i) ) ;
		}
		
		staffInfor.enableControls();
		
	}else if ( index == 1 ){
		//填充"动态信息"面板 
		queryPartyIdentification();
	}else if( index == 2 ){
		//填充"人员角色"面板
		queryStaffRoles();
	}else if( index == 3 ){
		//填充"人员权限"面板
		queryStaffPrivilege();
		clickStaffPrivilege();
	}else if( index == 4 ){
		//填充"人员岗位"面板
		queryStaffPost();	
	}
}

//注销员工
function deleteStaff_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再注销员工!") ;
		return ;
	}
	//if( staffInfor.getValue("staffCode") == "999999" ){
	if( staffInfor.getValue("staffCode") == getSuperStaffCode() ){
		if( !ifSuperManager() ){
			alert("您无权注销超级工号信息!");
			return ;
		}
	}
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	if( !confirm("确定要注销当前员工吗?")){
		return ;
	}
	var partyRoleId = staffList.getValue( "partyRoleId" ) ; 
	var ajaxCall = new NDAjaxCall(true);
	var result ;
	var callBack = function( reply ){
		result = reply.getResult() ;
		if( result ){
			alert("注销员工成功!");
			staffInfor.clearData();
			staffInfor.insertRecord( null ) ;
			clickOrganization();
		}
	}
	ajaxCall.remoteCall("PartyService","deleteStaff",[partyRoleId],callBack); 
}

function cancelCommit_onClick(){	 
	actionType = "" ;
	staffInfor.setReadOnly( true ) ;
	staffTableClick();
}

/*
function getProjectCode(){
	var ajaxCall = new NDAjaxCall(false);
	var result ;
	var callBack = function( reply ){
		result = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getProjectCode",[],callBack); 
	return result ;
}*/

//提交按钮响应事件
function commitStaff_onClick(){
	var minPasswordLen ;
	var maxPasswordLen ;
	if( actionType == "" ) {
		return ;
	}
	
	if( !$("staffInforForm").validate()){
		return ;
	}
	if( staffInfor.getValue("limitCount") == "0"){
		alert("登陆次数必须大于0!");
		return ;
	}
	//add by xiaoyong  控制“所属部门”“发展用户归属”不能选择 “部门类型”为“社会渠道”的部门和班组 ---20090310
	if (!checkOraPartyNameAndDepartUserBelongName()) {
   		 alert("所属部门或发展用户归属的部门类型为：社会渠道,请重新输入!");
		return ;
	}
	//add end;---20090310
	var staffCode = staffInfor.getValue("staffCode");
	var channelSegmentId = staffInfor.getValue("channelSegmentId");
	/**var projectCode = getProjectCode() ;
	if( projectCode == "CHONGQING" ){
		//重庆工号的校验规则
		if( channelSegmentId == "12" || channelSegmentId == "-1") {
			if( isNaN( staffCode )){
				alert("渠道工号必须是数字类型的!");
				return ;
			}
			if( staffCode.length > 9 ){
				alert("渠道工号的长度不能大于9!");
				return ;
			}
		}
	}
	*/

		if(!/^[\da-z]+$/i.test(staffCode)) { //数字或者字母
			alert("工号含有非法字符！");
			return ;
		}
		
		if( staffInfor.getValue("password") != "" ){
			if( !checkIsNumAndLetter( staffInfor.getValue("password"))){
				alert("密码必须由数字和字母组成!");
				return ;
			}
			if(!/^[\da-z]+$/i.test(staffInfor.getValue("password"))) { //数字或者字母
				alert("密码必须由数字和字母组成!");
				return ;
			}
		}
		

	if( actionType == "insert" ){//如果是插入操作,则密码必须约束在6~15位之间
		if( staffInfor.getValue("password") != "" ){
			maxPasswordLen = getDcSystemParam("MAX_PASSWORD_LENGTH");
			maxPasswordLen = parseInt(maxPasswordLen,10);
			minPasswordLen = getDcSystemParam("MIN_PASSWORD_LENGTH");
			minPasswordLen = parseInt(minPasswordLen,10);
			if( staffInfor.getValue("password").length < minPasswordLen || staffInfor.getValue("password").length > maxPasswordLen ){
				alert("密码长度必须在" + minPasswordLen + "到" + maxPasswordLen + "个字符之间!");
				return ;
			}
		}
	}
	
	if( actionType == "update" ){//如果是编辑操作
		if( staffInfor.getValue("password") != oldPassword ) {//如果用户修改过密码,则必须约束新密码在6~15位之间
			maxPasswordLen = getDcSystemParam("MAX_PASSWORD_LENGTH");
			maxPasswordLen = parseInt(maxPasswordLen,10);
			minPasswordLen = getDcSystemParam("MIN_PASSWORD_LENGTH");
			minPasswordLen = parseInt(minPasswordLen,10);
			if( staffInfor.getValue("password").length < minPasswordLen || staffInfor.getValue("password").length > maxPasswordLen ){
				alert("密码长度必须在" + minPasswordLen + "到" + maxPasswordLen + "个字符之间!");
				return ;
			}
		}
		//如果用户没有更改过密码,则无需对密码进行约束判断(此时密码是密文,长度肯定超过16位,进行校验没有意义)
	}
	
	var currentDate = getTodayStr();
	if( actionType == "insert" ){
		if( staffInfor.getValue("effDate") != "" && staffInfor.getValue("effDate") > currentDate ){
			alert("生效日期必须在今天或者今天之前!");
			return ;
		}
		if( staffInfor.getValue("expDate") != "" && staffInfor.getValue("expDate") < currentDate ){
			alert("失效日期必须在今天或今天之后!");
			return ;
		}
	}
	if( staffInfor.getValue("expDate") != "" && staffInfor.getValue("effDate") != "" ){
		if( staffInfor.getValue("expDate") <= staffInfor.getValue("effDate")){
			alert("失效日期必须迟于生效日期!");
			return ;
		}
	}
	
	if( staffList.getCurrent() == null ) 
		staffList.insertRecord( null);
	if ( actionType == "" ) return ;

	if( staffInfor.getValue("orgPartyName") == "" || staffInfor.getValue("orgPartyName") == null ){
		if( orgTreeView.selectedItem.orgTypeId != "5" && orgTreeView.selectedItem.orgTypeId != "6"){
			alert("当前组织不是部门或班组,只能在部门或者班组下面增加员工!");
			return ;
		}
		if( orgTreeView.selectedItem.state != "00A" ){
			alert("当前组织的状态不是有效状态,不能在其下面增加员工!");
			return;
		}
	}
	//新建一个staff对象（修改后的员工信息），然后把它传递给service
	var staff = new Staff();
	for( var ele in staff ){
		if( staffInfor.getField( ele )){
			//在这里把jsp页面上的值都赋值到staff上去
			staff[ele] = staffInfor.getValue( ele ) ;
		}
	}
	if( staff["expDate"] == "" || staff["expDate"] == null ){
		staff["expDate"] = "2029-01-01 00:00:00" ;
	}else{
		staff["expDate"] = staff["expDate"] + " 23:59:59";
	}
	staff["effDate"] = staff["effDate"] + " 00:00:00";

	if( staff.orgPartyId == "" || staff.orgPartyId == null || staff.orgParty == "undefined" ){
		staff.orgPartyId = orgTreeView.selectedItem.partyId ;
	}
	
	if( staff.limitCount == "" || staff.limitCount == null || staff.limitCount == "undefined" ){
		staff.limitCount = "3" ;
	}

	var ajaxCall = new NDAjaxCall(true);
	var result ;
	var callBack = function( reply ){
		result = reply.getResult() ; 
			if( actionType == "insert" ){
				staffInfor.setValue("partyRoleId",result);
				staffList.setValue("partyRoleId",result);
				alert("插入员工成功!");
			}else if( actionType == "update" ){
				alert("修改员工信息成功!");
			}
			clickOrganization();
		staffInfor.setReadOnly( true ) ;
		actionType = "";
	}
	
	var arr = new Array();
	staff.businessId = staffInfor.getValue("businessId");	//营业区
	staff.countyType = staffInfor.getValue("countyType");	//城乡标
	arr[0] = staff; 

	staff["partyRoleType"] = "90A";//参与人类型为电信内部员工
	if( actionType == "insert" ){ 
		ajaxCall.remoteCall("PartyService","insertStaff",arr,callBack); 
	}else if( actionType == "update" ){
		//更新员工信息的时候，传递给service的参数是staff(包括了属性businessId和countyType)
		ajaxCall.remoteCall("PartyService","updateStaff",arr,callBack);	
	}
	
}

function Identification(){
	this.identId="";
	this.partyId="";
	this.partyRoleId = "";
	this.socialIdType="";
	this.socialId="";
	this.createdDate="";
	this.effDate="";
	this.expDate="";
}

function Staff(){
	this.partyRoleId = "" ;
	this.partyName = "" ;
	this.staffCode = "" ;
	this.staffDesc = "" ;
	this.password = "" ;
	this.officeId = "";
	this.pwdvalidtype = "" ;
	this.limitCount = "" ;
	this.partyId = "" ;
	this.orgPartyId = "" ;
	this.orgPartyName = "";
	this.partyRoleType = "" ;
	this.orgManager = "" ;
	this.createDate = "" ;
	this.expDate = "" ;
	this.effDate = "" ;
	this.state = "" ;
	this.stateDate = "" ;
	this.gender = "" ;
	this.birthPlace = "" ;
	this.birthDate = "" ;
	this.maritalStatus = "" ;
	this.skill = "" ;
	this.scope="";//查询范围
	this.channelSegmentId="";
	this.devUserBelong = "";
	this.devUserBelongName = "";
	//this.officeName = "";
	this.officeId = "";
	this.lanId = "";
	this.devOrgId = "";
	this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.StaffVO' ;	
}

function getStaffByOrgId(){
	var selItem = orgTreeView.selectedItem ;
	if( !selItem )
		return ;
	var orgId = selItem.partyId ;

	var staff = new Staff() ;
	staff["orgPartyId"] = orgId ;
	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;
	parameterSet.setValue("pageIndex","1");
	Dataset.reloadData( staffList ) ;
	staffPilot.refreshCustomerPilot;
}

//获取和当前员工相关的"动态信息"列表
function queryPartyIdentification(){
	var partyRoleId = staffList.getValue("partyRoleId");
	if( partyRoleId == "" ) {
		partyIdentification.clearData();
		return ;
	}
	var parameterSet = partyIdentification.parameters();
	parameterSet.setDataType("partyRoleId","string");
	parameterSet.setValue("partyRoleId", partyRoleId);
	Dataset.reloadData( partyIdentification );
}

//获取和当前员工相关的"人员角色"列表
function queryStaffRoles(){
	var partyRoleId = staffList.getValue("partyRoleId");//**************************
	if( partyRoleId == "" ) {
		staffRolesList.clearData();
		return ;
	}
	var parameterSet = staffRolesList.parameters();
	parameterSet.setDataType("partyId","string");
	parameterSet.setValue("partyId", partyRoleId);
	Dataset.reloadData( staffRolesList );
}

//获取何当前员工相关的"人员权限"列表
function queryStaffPrivilege(){
	var partyRoleId = staffList.getValue("partyRoleId");
	
/*	var parameterSet = staffPrivilegesList.parameters();
	parameterSet.setDataType("partyId","string");
	parameterSet.setValue("partyId",partyRoleId);
	Dataset.reloadData( staffPrivilegesList ) ;*/
	
	
	if( partyRoleId == null || partyRoleId == "" ){
		staffPrivilegeTreeView.loadByXML("<items/>");
		return ;
	}
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		staffPrivilegeTreeView.clear();
		staffPrivilegeTreeView.loadByXML(queryResult);
	} 
	ajaxCall.remoteCall("PartyService","getStaffPrivXMLItemByPartyRoleId",[partyRoleId],callBack);	 
}

//获取和当前员工相关的"人员岗位"列表
function queryStaffPost(){
	var partyRoleId = staffList.getValue( "partyRoleId" ) ;
	if( partyRoleId == "" ) {
		staffPostsList.clearData();
		return ;
	}
	var parameterSet = staffPostsList.parameters();
	parameterSet.setDataType("partyId","string");
	parameterSet.setValue("partyId",partyRoleId);
	Dataset.reloadData( staffPostsList ) ;
}

//增加参与人动态信息(参与人识别信息)
function addIdentification_onClick(){
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	var arr = new Array();
	arr[0]= "insert" ;
	arr[1] = staffList.getValue("partyRoleId");//传入参与人角色ID
	var returnValue=window.showModalDialog("IdentificationDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 350pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryPartyIdentification();
	}
}
//修改参与人动态信息(参与人识别信息)
function editIdentification_onClick(){
	if( !partyIdentification.getValue("identId") ){
		return;
	}
	var identification = new Identification(); 
	for( var ele in identification ){
		if( partyIdentification.getField( ele )){
			identification[ele] = partyIdentification.getValue( ele ) ;
		}
	}
	
	var arr = new Array();
	arr[0] = "update" ;
	arr[1] = identification ;
	var returnValue=window.showModalDialog("IdentificationDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 350pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryPartyIdentification();
	}
}
//删除参与人动态信息(参与人识别信息)
function deleteIdentification_onClick(){
	if( !partyIdentification.getValue("identId") ){
		return;
	}
	if( !confirm( "确定要删除当前员工动态信息吗?" ) ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall(true);
	var result ;
	var callBack = function( reply ){
		result = reply.getResult() ;
	
		if( result ){
			alert("删除动态信息成功!");
			queryPartyIdentification();
		}else{
			alert("删除动态信息失败!");
		}		
	}
	var identificationId = partyIdentification.getValue("identId");
	ajaxCall.remoteCall("PartyService","deletePartyIdentification",[identificationId],callBack);
}

//增加员工角色
function addRole_onClick(){
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	var arr = new Array();
	arr[0] = staffList.getValue( "partyRoleId" );
	arr[1] = staffRolesList ;
	arr[2] = menuCode ;
	var returnValue=window.showModalDialog("StaffRoleDialog.jsp",arr,"dialogHeight: 520pt; dialogWidth: 500pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffRoles();
	}
}

//编辑员工权限
/*
function editRole_onClick(){
	if( !staffRolesList.getValue("staffRoleId")){
		return ;
	}
	var arr = new Array();
	arr[0] = "update";
	arr[1] = staffRolesList.getValue("staffRoleId") ;
	var returnValue=window.showModalDialog("StaffRoleDialog.jsp",arr,"dialogHeight: 500pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffRoles();
	}	
}*/
 
//删除员工角色
function deleteRole_onClick(){
	if( !staffRolesList.getValue("partyRoleId")){
		return ;
	}
	if( !confirm( "确定要删除当前员工角色吗?" ) ){
		return ;
	}
	var staffRoleId = staffRolesList.getValue( "partyRoleId" ) ;
	var roleId = staffRolesList.getValue("roleId");
	var regionId = staffRolesList.getValue("regionId");
	var regionType = staffRolesList.getValue("regionType");
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var result = null ;
	var callBack = function( reply ){
		result = reply.getResult();
		if( result ){
			alert("删除员工角色成功!");
			queryStaffRoles();
		}
	}
	ajaxCall.remoteCall( "PartyService","deleteStaffRole",[staffRoleId,roleId,regionId,regionType],callBack);
}

function checkStaffRoles(){
	var partyRoleId = staffList.getValue("partyRoleId") ;
	
	var returnValue = true ;
	
	var callBack = function( reply ) {
		returnValue = reply.getResult() ;
	}
	
	var ajaxCall = new NDAjaxCall( false ) ;
	ajaxCall.remoteCall("PartyService","checkStaffRoles", [partyRoleId] , callBack ) ;
	
	return returnValue ;
}

function addPrivilege_onClick(){
	if( staffList.getValue("partyRoleId") == "" ){
		return ;
	}
	if( !checkStaffRoles() ){//校验员工是否已经分配了角色,系统不允许给没有任何角色的工号单独分配权限
		alert("当前员工没有分配任何角色,不能给没有角色的员工单独分配权限!");
		return ;
	}
	var selectedPrivilegeVOs = getPrivilegeVosByPartyRoleId() ;//getPrivilegeIdsByPartyRoleId();//获取员工已有的权限ID
	var arr = new Array();
	arr[0] = menuCode;
	arr[1] = staffList.getValue( "partyRoleId" );
	arr[2] = selectedPrivilegeVOs ;
	var returnValue=window.showModalDialog("StaffPrivilegeDialog.jsp",arr,"dialogHeight: 500pt; dialogWidth: 550pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffPrivilege();
	}
}

//查询当前工号所拥有的所有权限对象(StaffPrivVO)
function getPrivilegeVosByPartyRoleId(){
	var ajaxCall = new NDAjaxCall( false ) ;//同步方式访问数据库
	var privilegeIds = new Array() ;
	var callBack = function( reply ) {
		privilegeIds = reply.getResult() ;
	}
	var partyRoleId = staffList.getValue("partyRoleId");
	ajaxCall.remoteCall( "PrivilegeService","getPrivilegeVosByPartyRoleId", [partyRoleId] , callBack ) ;
	return privilegeIds ;
}

//查询当前工号所拥有的所有权限ID
function getPrivilegeIdsByPartyRoleId(){
	var ajaxCall = new NDAjaxCall( false ) ;//同步方式访问数据库
	var privilegeIds = new Array() ;
	var callBack = function( reply ) {
		privilegeIds = reply.getResult() ;
	}
	var partyRoleId = staffList.getValue("partyRoleId");
	ajaxCall.remoteCall( "PrivilegeService","getPrivilegeIdsByPartyRoleId", [partyRoleId] , callBack ) ;
	return privilegeIds ;
}

/*
function editPrivilege_onClick(){
	if( !staffPrivilegesList.getValue("staffPrivId")){
		return ;
	}
	var arr = new Array();
	arr[0] = "update";
	arr[1] = staffPrivilegesList.getValue("staffPrivId") ;
	var returnValue=window.showModalDialog("StaffPrivilegeDialog.jsp",arr,"dialogHeight: 500pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffPrivilege();
	}	
}*/

function deletePrivilege_onClick(){
	if( !staffPrivilegeTreeView.items){
		return ;
	}
	if( !staffPrivilegeTreeView.selectedItem){
		return ;
	}
	if( staffPrivilegeTreeView.selectedItem.items ){
		alert("您要删除的权限还有下级权限,请向删除下级权限!");
		return ;
	}
	if( !confirm( "确定要删除当前员工权限吗?" ) ){
		return ;
	}
	var partyRoleId = staffList.getValue( "partyRoleId" ) ;
	var staffRoleId = staffPrivilegesList.getValue( "staffPrivId" ) ;
	var privId = staffPrivilegeTreeView.selectedItem.privId ;
	var regionId = staffPrivilegeRegionList.getValue("regionId");
	var regionType = staffPrivilegeRegionList.getValue("regionType") ;
	if( regionId == "" ){
		regionId = "-1";
	}
	if( regionType == "" ){
		regionType = "-1";
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var result = null ;
	var callBack = function( reply ){
		result = reply.getResult();
		if( result ){
			queryStaffPrivilege();
			clickStaffPrivilege();
			alert("删除员权限成功!");
		}
	}
	ajaxCall.remoteCall( "PartyService","deleteStaffPriv",[partyRoleId,privId,regionId,regionType],callBack);
}

function addPost_onClick(){
	if( staffList.getValue("partyRoleId") == ""){
		return ;
	}
	var arr = new Array();
	arr[0] = "insert";
	arr[1] = staffInfor.getValue("orgPartyId");//orgTreeView.selectedItem.partyId ;//人员所在组织的ID
	arr[2] = staffList.getValue( "partyRoleId" );//人员的PartyRoleId
	
	var postArray = new Array();
	var curRecord = staffPostsList.getCurrent();
	var i = 0 ;
	while( curRecord ){
		postArray[i] = curRecord.getValue("orgPostId");
		i ++ ;
		curRecord = curRecord.getNextRecord();
	}
	arr[3] = postArray;
	var returnValue=window.showModalDialog("StaffPositionDialog.jsp",arr,"dialogHeight: 300pt; dialogWidth: 350pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffPost();
	}
}

function editPost_onClick(){
	if( !staffPostsList.getValue("partyRoleId")){
		return ;
	}
	var arr = new Array();
	arr[0] = "update" ;
	arr[1] = orgTreeView.selectedItem.partyId ;//人员所在组织的ID
	arr[2] = staffPostsList.getValue("partyRoleId");
	arr[3] = staffPostsList.getValue("orgPostId");
	arr[7] = staffPostsList.getValue("orgPostName");
	arr[4] = staffPostsList.getValue("state");
	arr[5] = staffPostsList.getValue("effDate");
	arr[6] = staffPostsList.getValue("expDate");
	var returnValue=window.showModalDialog("StaffPositionDialog.jsp",arr,"dialogHeight: 250pt; dialogWidth: 300pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == 0 ){
		queryStaffPost();
	}	
}

function deletePost_onClick(){
	if( !staffPostsList.getValue("partyRoleId")){
		return ;
	}
	if( !confirm("您确定要删除员工岗位吗?")){
		return ;
	}
	var partyRoleId = staffPostsList.getValue("partyRoleId");
	var orgPostId = staffPostsList.getValue("orgPostId");
	var result = null ;
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ){
		result = reply.getResult();
		if( result ){
			alert("删除员工岗位成功!");
			queryStaffPost();
		}
	}
	var arr = new Array();
	arr[0] = orgPostId;
	arr[1] = partyRoleId ;
	ajaxCall.remoteCall( "PartyService","deleteStaffPost",arr,callBack);
}

function button_staffInfor_staffCode_onClick(){
	var staffCode = "";
	if( actionType == "insert" ){
		if( staffInfor.getValue("staffCode") == "" ){
			alert("请先输入工号!");
			return ;
		}	
		
		var projectCode = getProjectCode() ;
		if( projectCode == "CHONGQING" ){
			var channelSegmentId = staffInfor.getValue("channelSegmentId") ;
			if( channelSegmentId == "" ){
				alert("请先选择渠道！");
				return ;
			}
			if( channelSegmentId != "12" && channelSegmentId != "-1" ){
				if( selectedOrgCode != ""){
					staffCode = selectedOrgCode + staffInfor.getValue("staffCode") ;
				}else{
					staffCode= orgTreeView.selectedItem.orgCode + staffInfor.getValue("staffCode");
				}
			}else{
				staffCode = staffInfor.getValue("staffCode");
			}
		}else{
			staffCode = staffInfor.getValue("staffCode");
		}
		window.showModalDialog("ValidStaffCode.jsp",staffCode,"dialogHeight: 120pt; dialogWidth: 250pt;");	
	}
}
function button_staffInfor_officeName_onClick(){
	if( actionType == "" ){
		return ;
	}
	var returnValue=window.showModalDialog("../common/OfficeSelectDialog.jsp",null,"dialogHeight: 350pt; dialogWidth: 400pt;");	
	if( returnValue ){
		staffInfor.setValue("officeId", returnValue[0]);
		staffInfor.setValue("officeName", returnValue[1] );
	}
}

function button_staffInfor_devOrgName_onClick(){
	if( actionType != "insert" ){
		if( actionType == "update"){
			alert("归属代理商班组不能编辑!");
		}
		return ;
	}
	var vo = selectOrganization() ;
	if( vo == null ){
		return ;
	}
	staffInfor.setValue("devOrgId", vo["orgId"]);
	staffInfor.setValue("devOrgName", vo["orgName"]);		
}

function button_staffInfor_orgPartyName_onClick(){
	if( actionType == "" ){
		return ;
	}
	var vo = selectOrganization() ;
	if( vo == null ){
		return ;
	}
	staffInfor.setValue("orgPartyId", vo["orgId"]);
	staffInfor.setValue("orgPartyName", vo["orgName"]);		
	selectedOrgCode = vo["orgCode"];
}
function button_staffInfor_devUserBelongName_onClick(){
	if( actionType == "" ){
		return ;
	}
	var vo = selectOrganization() ;
	if( vo == null ){
		return ;
	}
	staffInfor.setValue("devUserBelong",vo["orgId"]);
	staffInfor.setValue("devUserBelongName",vo["orgName"]);
}

function selectOrganization(){
	var para = new Object();
	/*
	 *privilegeType -- 权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码
	 */
	para["privilegeType"] = "3" ;
	/*
	 *privilegeCode -- 
	 *权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
	 */
	para["privilegeCode"] = menuCode;
	/*
	 *orgType -- 
	 *组织类型ID,5表示部门,0表示集团公司,1表示省公司,2表示市公司,3表示分公司,6表示班组,
	 *9表示其他组织,99表示不作类型限制,可以选择任何一种组织类型
	 */
//	para["orgType"] = "5" ;//只能选部门
	para["orgType"] = "7" ;//可以选择部门或者班组
	para["selectType"] = "1" ;//1表示当选,2表示多选
	para["oldIds"] = getStaffOrganizationId();
	
	var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0 ) 
		return null ;
	return returnValue[0] ;
}
var selectedOrgCode = "";

function clickStaffPrivilege(){
	var selItem = staffPrivilegeTreeView.selectedItem;
	if( selItem == null ){
		staffPrivilegeRegionList.clearData() ;
		return ;
	}
	var privilegeId = staffPrivilegeTreeView.selectedItem.privId ;
	var partyRoleId = staffList.getValue("partyRoleId");
	staffPrivilegeRegionList.parameters().setDataType("privId","string");
	staffPrivilegeRegionList.parameters().setValue("privId",privilegeId);
	
	staffPrivilegeRegionList.parameters().setDataType("partyRoleId","string");
	staffPrivilegeRegionList.parameters().setValue("partyRoleId",partyRoleId);
	Dataset.reloadData( staffPrivilegeRegionList ) 
}
//add by xiaoyong ---20090310
//在员工管理页面，增加工号时：控制“所属部门”“发展用户归属”不能选择 “部门类型”为“社会渠道”的部门和班组
function checkOraPartyNameAndDepartUserBelongName(){
    
    var isOk = true;
   	var orgPartyId = staffInfor.getValue("orgPartyId");   	 
	var devUserBelong = staffInfor.getValue("devUserBelong");	 
    var callBack = function(reply) 
            {
              var returnValues=new Object();
              returnValues=reply.getResult();
              if (returnValues!="")
              {
                  isOk = false;
               }
            } 
    var ajaxCall = new NDAjaxCall( false ) ;
	ajaxCall.remoteCall("PartyService", "judgePartyIsBelongSecietyChannels", [orgPartyId,devUserBelong], callBack);
    return isOk;	 
}
// add end;---20090310