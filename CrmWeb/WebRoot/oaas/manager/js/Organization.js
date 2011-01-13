var menuCode = "";
function page_onLoad(){
	menuCode = Global.getCurrentMenuCode();
	initOrganization();
}

function initOrganization(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.organizationTreeView.loadByXML(queryResult );
		if( document.all.organizationTreeView.items == null || document.all.organizationTreeView.items.length == 0 ){
			alert("您没有任何数据权限!");
			return ;
		}
		clickOrganization();
	}
	ajaxCall.remoteCall("PartyService","getRootOrganizationListByPrivControl",[menuCode],callBack);	
//	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}

function clearValues(){
	commonInfor.clearData() ;
	contactInfor.clearData() ;
	commonInfor.insertRecord( null ) ;
	contactInfor.insertRecord( null ) ;
}

//编辑组织
function editOrganization_onClick(){
	if( actionType != "" ){
		alert( "请先保存或者取消当前操作再编辑组织!" ) ;
		return ;
	}
	actionType = "update" ;
	commonInfor.setReadOnly( false ) ;
	commonInfor.setFieldReadOnly("orgTypeId",true);
	commonInfor.setFieldReadOnly("parentPartyName",true);
	commonInfor.setFieldReadOnly("orgLevel", true);
	commonInfor.setFieldReadOnly("orgType", true);
	contactInfor.setReadOnly( false ) ;
	
	if( organizationTreeView.selectedItem.orgTypeId == "6" ){//如果是班组
		commonInfor.setFieldReadOnly("orgType", false);	
		
		//added 2008-11-06
		departmentInfo.setReadOnly( false ) ;
		departmentInfo.setFieldPopupEnable("superPartyName",true);
		departmentInfo.setFieldPopupEnable("regionName",true);	//added end here
	}
	if( organizationTreeView.selectedItem.orgTypeId == "5" ){//如果是部门
		commonInfor.setFieldReadOnly("orgType", false);
		departmentInfo.setReadOnly( false ) ;
		departmentInfo.setFieldPopupEnable("superPartyName",true);
		departmentInfo.setFieldPopupEnable("regionName",true);		
	}
	mainPage.setSelectedPageIndex(0);
}

//从TreeList上删除一个记录
function deleteOrganizationFromTreeList(){
	var organizationTree = document.all.organizationTreeView;
	var selItem = organizationTree.selectedItem;
	selItem.remove();
}

//删除组织
function deleteOrganization_onClick(){
	if( actionType != "" ){
		alert( "请先保存或者取消当前操作再删除组织!" ) ;
		return ;
	}
	if( confirm( "您确信要删除当前组织吗?" )){
		var currentPrivilegeId = getCurrentOrganizationId();
		
		var ajaxCall = new NDAjaxCall(true);
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;
			if( deleteResult > 0){
				deleteOrganizationFromTreeList();
				alert( "删除组织成功！" );
			}
			else if( deleteResult ==0){
				alert( "删除组织失败!" );
			}
			else if(deleteResult ==-3){
				alert("组织节点下面还有下级节点,不能删除!");
			}
			else if(deleteResult ==-4){
				alert("组织下面还有员工,该组织不能删除!");
			}
		}
		ajaxCall.remoteCall("PartyService","deleteOrganization",[currentPrivilegeId],callBack);
	}
}

//增加一个组织
function addOrganization(parentOrgId){
	mainPage.setSelectedPageIndex(0);
	clearValues();
	commonInfor.setReadOnly(false);
	commonInfor.setFieldReadOnly( "parentPartyName", true );
	commonInfor.setFieldReadOnly("orgLevel", true);
	commonInfor.setFieldReadOnly("orgType",true);
	divDepartmentInfo.style.display = "none" ;
	contactInfor.setReadOnly( false ) ;
	
	if( parentOrgId != null ){//如果是在一个上级组织下面增加下级组织。
		var item = getPartyById(parentOrgId) ;
		commonInfor.setValue("parentPartyId", item.partyId ) ;
		commonInfor.setValue("parentPartyName",item.orgName);
		
		var orgTypeId = item.orgTypeId ;
		if( "0" == orgTypeId ){//上级组织是集团公司
			commonInfor.setValue("orgTypeId","1");//要增加的是省公司 
		}else if( "1" == orgTypeId ){//上级组织是省公司
			commonInfor.setValue("orgTypeId","2");//要增加的是市公司
		}else if( "2" == orgTypeId ){//上级组织是市公司
			commonInfor.setValue("orgTypeId","3");//要增加的是营业区
		}else if("3" == orgTypeId ){//上级是营业区
			commonInfor.setValue("orgTypeId","4");//要增加的是营销区
		}else if( "4" == orgTypeId ){//上级组织是分公司
			commonInfor.setValue("orgTypeId","5");//要增加的是部门
			commonInfor.setFieldReadOnly("orgType",false);
			divDepartmentInfo.style.display = "block" ;
			departmentInfo.setReadOnly( false ) ;
			departmentInfo.setFieldReadOnly("regionName",true);
			departmentInfo.setFieldReadOnly("superPartyName",true);		
			if( actionType == "insert" ){
				departmentInfo.clearData();
				departmentInfo.insertRecord(null);
				departmentInfo.setFieldPopupEnable("regionName",true);
				departmentInfo.setFieldPopupEnable("superPartyName",true);
			}			
		}else if( "5" == orgTypeId ){//上级组织是部门
			commonInfor.setValue("orgTypeId","6");//要增加的可以是部门,也可以是班组,默认为班组
			commonInfor.setFieldReadOnly("orgType",false);
			
			//add 2008-11-06
			divDepartmentInfo.style.display = "block" ;
			departmentInfo.setReadOnly( false ) ;
			departmentInfo.setFieldReadOnly("regionName",true);
			departmentInfo.setFieldReadOnly("superPartyName",true);		
			if( actionType == "insert" ){
				departmentInfo.clearData();
				departmentInfo.insertRecord(null);
				departmentInfo.setFieldPopupEnable("regionName",true);
				departmentInfo.setFieldPopupEnable("superPartyName",true);
			}//add end here
			
		}else if( "9" == orgTypeId ){//上级组织是其他组织
			commonInfor.setValue("orgTypeId","9");//要增加的下级组织只能是其他组织
		}
		if( orgTypeId != "5" ){//如果上级组织不是是部门,则不允许用户自己选择组织类型
			commonInfor.setFieldReadOnly("orgTypeId",true);
		}else{//如果上级组织是部门,则允许用户选择组织类型为部门或者班组.
			commonInfor.setFieldReadOnly("orgTypeId",false);
		}
	}
}

//增加根组织
function addRootOrganization_onClick(){
	if( actionType != "" ){
		alert( "请先保存或者取消当前操作再增加组织!" ) ;
		return ;
	}
	actionType = "insert";
	nodeType = "root" ;
	addOrganization( null ) ;
}

//增加下级组织
function addSubOrganization_onClick(){
	var orgTypeId = organizationTreeView.selectedItem.orgTypeId ;
	if( orgTypeId == "6" ){
		alert("当前组织是班组,不能在其下面增加下级组织!");
		return ;
	}
	if( actionType != "" ){
		alert( "请先保存或者取消当前操作再增加组织!" ) ;
		return ;
	}
	actionType = "insert" ;
	nodeType = "child" ;
	addOrganization( getCurrentOrganizationId() );
}

function inputNext_onClick(){
	if( !$("commonInforForm").validate()){
		return ;
	}
	//if( commonInfor.getValue("orgTypeId") == "5" ){
	if( commonInfor.getValue("orgTypeId") == "5"||commonInfor.getValue("orgTypeId") == "6" ){//modified 2008-11-06
		if( !$("departmentInfoForm").validate()){
			return ;
		}
	}
	//add by xiaoyong 控制增加或修改班组时：“部门类型”不能选择“社会渠道”--20090309
	if(commonInfor.getValue("orgTypeId") == "6"&&departmentInfo.getValue("departType")=="05"){
	    alert("增加或修改班组时：“部门类型”不能选择“社会渠道”,请重新选择！！");	
		return ;	
	}
	//add end;--20090309
	mainPage.setSelectedPageIndex(1);
}

function commitOrganization_onClick(){
	if( actionType == "" )
		return ;

	if( !$("contactInforForm").validate()){
		return ;
	}
	if( contactInfor.getValue("postcode").length != 6 ){
		alert("邮政编码长度必须是六位!");
		return ;
	}
	
	commonInfor.setReadOnly( true ) ;
	contactInfor.setReadOnly( true ) ;
	departmentInfo.setReadOnly( true ) ;
	
	//初始化Organization对象
	var organizationObj = new OrganizationInfo() ;
	for( var ele in organizationObj ){
		if( commonInfor.getField( ele )){
			organizationObj[ele] = commonInfor.getValue( ele ) ;
		}
	}
	organizationObj["addDescription"] = contactInfor.getValue("addDescription");
	//organizationObj["orgClass"] = "00" ;//orgClass;//组织分类 : 00--内部组织; 01--合作伙伴; 02--对等运营商; 03--共建局;-1--未知分类.
	
	if( nodeType == "root" ){
		organizationObj["parentPartyId"] = "-1";//如果是根节点,则上级组织的ID为-1
	}
	
	var orgTypeId = commonInfor.getValue("orgTypeId");
	if( orgTypeId == "0" ){//集团公司
		organizationObj.orgLevel = "1";
	}else if( orgTypeId == "1"){//省公司
		organizationObj.orgLevel = "2";
	}else if( orgTypeId == "2"){//本地网
		organizationObj.orgLevel = "3";
	}else if( orgTypeId == "3"){//营业区
		organizationObj.orgLevel = "4";	
	}else if( orgTypeId == "4"){//营销区
		organizationObj.orgLevel = "5";	
	}else if( orgTypeId == "5"){//部门
		organizationObj.orgLevel = "6";	
	}else if( orgTypeId == "6"){//班组
		organizationObj.orgLevel = "7";	
	}else if( orgTypeId == "9"){//其他组织
		organizationObj.orgLevel = "9";	
	}
	
	//初始化Address对象
	var contactObj = new AddressInfo() ;
	for( var ele in contactObj ){
		if( contactInfor.getField( ele ) ) {
			contactObj[ele] = contactInfor.getValue( ele ) ;
		}
	}

	//初始化Department对象
	//if( commonInfor.getValue("orgTypeId") == "5" ){
	if( commonInfor.getValue("orgTypeId") == "5" ||commonInfor.getValue("orgTypeId") == "6"){//modified 2008-11-06
		var departmentVo = new DepartmentVO();
		for( var ele in departmentVo ){
			departmentVo[ele] = departmentInfo.getValue( ele ) ;
		}
		departmentVo["termFlag"] = "-1";
	}
	
	var ajaxCall = new NDAjaxCall(true);
	var result ;
	
	var callBack = function( reply ){
		result = reply.getResult() ;
		if(result=="-1"){
		   alert("组织名称重复，请更换名字!!!");
		}
		else{
		if( actionType == "insert" ){//增加操作
			//服务器端返回新增加的主键
			commonInfor.setValue("partyId", result ) ;
			contactInfor.setValue("partyId", result ) ;
			getAddressIdByPartyId( result );
			addOrgToTreeList();
		}else if ( actionType == "update" ){//编辑操作
			//initOrganization();//刷新页面数据
			refreshTreeList();
			//locateRecord(result);//定位到更新的记录上
		}
		alert("操作成功!");
		}
		actionType = "" ;
		nodeType = "" ;		
	}
	
	var arr = new Array();
	arr[0] = organizationObj;	
	arr[1] = contactObj ;
	organizationObj[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.OrganizationVO' ;		
	contactObj[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.AddrVO' ;				
	if( actionType == "insert" ){
		//if( commonInfor.getValue("orgTypeId") == "5" ){
		if( commonInfor.getValue("orgTypeId") == "5" || commonInfor.getValue("orgTypeId") == "6"){//modified 2008-11-06
			departmentVo[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.MpDepartVO' ;	
			arr[2] = departmentVo;
			ajaxCall.remoteCall("PartyService","insertOrganizationWithDepartment",arr,callBack);
		}else{
			ajaxCall.remoteCall("PartyService","insertOrganization",arr,callBack);
		}
	}else if( actionType == "update" ){
		//if( commonInfor.getValue("orgTypeId") == "5" ){
		if( commonInfor.getValue("orgTypeId") == "5" || commonInfor.getValue("orgTypeId") == "6"){//modified 2008-11-06
			departmentVo[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.MpDepartVO' ;	
			arr[2] = departmentVo ;
			ajaxCall.remoteCall("PartyService","updateOrganizationWithDepartment",arr,callBack);
		}else{
			ajaxCall.remoteCall("PartyService","updateOrganization",arr,callBack);
		}
	}
}
function getAddressIdByPartyId( id ) {
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ) {
		contactInfor.setValue("addrId",reply.getResult());
		commonInfor.setValue("addrId",reply.getResult()) ;
	}
	ajaxCall.remoteCall("PartyService","getAddressIdByPartyId",[id],callBack);
}
function refreshTreeList(){
	var orgTree = document.all.organizationTreeView;
	var org = orgTree.selectedItem;
	
	for(var ele in org){
		if(commonInfor.getField(ele)){
			org[ele] = commonInfor.getValue( ele );
		} 
	}
		
	org.refresh();
}

//将新增加的节点增加到树上
function addOrgToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
}
//在树上增加一个子节点
function addChildToTreeList(){
	var orgTree = document.all.organizationTreeView;
	var selItem = orgTree.selectedItem;
	var org = orgTree.createTreeNode();
				//alert('j2'+$("text_commonInfor_orgTypeId").getValue());
	var orgTypeName = document.getElementById("text_commonInfor_orgTypeId").value;//$("text_commonInfor_orgTypeId").value; 
	org.orgTypeName = orgTypeName ;
	for(var ele in selItem){
		if(commonInfor.getField(ele)){
			org[ele] = commonInfor.getValue( ele );
		} 
	}
	org.parentPartyId = selItem.partyId ;
	selItem.add(org);
	org.setSelected();
}
//在树上增加一个根节点
function addRootToTreeList(){
	var orgTree = document.all.organizationTreeView;
	var org = orgTree.createTreeNode();
	
	var orgTypeName = $("text_commonInfor_orgTypeId").value;
	org.orgTypeName = orgTypeName ;
	var org1 = new OrganizationInfo();
	 
	for(var ele in org1){
		if(commonInfor.getField(ele)){
			org[ele] = commonInfor.getValue( ele );
		} 
	}
	
	orgTree.add(org);
	//把该节点选择上，如果批量插入，建议不要选择
	org.setSelected();
}

function cancelCommit1_onClick(){
	cancelCommit_onClick();
}

function cancelCommit_onClick(){
	if( actionType == "" )
		return ;
		
	clickOrganization() ;
	actionType = "" ;
	nodeType = "" ;
	commonInfor.setReadOnly( true );
	contactInfor.setReadOnly( true ) ;
	departmentInfo.setReadOnly( true ) ;
}

//定位到ID制定的组织记录上
function locateRecord( id ){
	var organizationTree = document.all.organizationTreeView;
	var items = organizationTree.items ;
	for( var i = 0; i < items.length; i ++ ){
		if( items.partyId == id ){
			items[i].setSelected;
			clickOrganization();
		}
	}
}

/*
function addOrganizationToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}

function addChildToTreeList(){
	var organizationTree = document.all.organizationTreeView;
	var selItem = organizationTree.selectedItem;
	var org = organizationTree.createTreeNode();
	
	for(var ele in selItem){
		if(commonInfor.getField(ele)){
			org[ele] = commonInfor.getValue( ele );
		} 
	}
	
	selItem.add(org);
	org.setSelected();
}

function addRootToTreeList(){
	var organizationTree = document.all.organizationTreeView;
	var organization = organizationTree.createTreeNode();
	
	var organization1 = new OrganizationInfo();
	 
	for(var ele in organization1){
		if(commonInfor.getField(ele)){
			organization[ele] = commonInfor.getValue( ele );
		} 
	}

	organizationTree.add(organization);
	organization.setSelected();
}
*/

function refreshDatainfo(id){
	commonInfor.setValue("partyId", id ) ;
	contactInfor.setValue("partyId", id ) ;	
}

var actionType = "";
var nodeType = "";

//获取当前被选中的组织的主键
function getCurrentOrganizationId(){
	var selItem = organizationTreeView.selectedItem;
	return selItem.partyId ;
}

function getCurrentPartyId(){
	var selItem = organizationTreeView.selectedItem;
	if( selItem.partyId == null || selItem.partyId == "" ) {
		return ;
	}
	
	return selItem.partyId ;
}
function mainPage_beforePageChanged( page, fromIndex, toIndex ){
	//如果当前不是处于编辑状态,则直接退出
	if( actionType == "" ) {
		return true ;
	}
	
	if( actionType != "" && toIndex  == 2 ){
		alert("请先保存常规信息和联系信息,再切换到扩展信息标签页!");
		return false ;
	}
	
	if( actionType != "" && fromIndex == "2" ){
		alert("请先保存扩张信息在切换到其他标签页!");
		return false ;
	}
	
	if( fromIndex == 0 ){//当前是"常规"标签页
		if( !$("commonInforForm").validate()){//校验常规信息的输入合法性
			return false ;
		}
		//if( commonInfor.getValue("orgTypeId") == "5" ){
		if( commonInfor.getValue("orgTypeId") == "5"|| commonInfor.getValue("orgTypeId") == "6" ){//modified 2008-11-06
			if( !$("departmentInfoForm").validate()){
				return false ;
			}
		}
	}
	
	if( fromIndex == 1 ){//当前是联系信息标签页
		if( !$("contactInforForm").validate()){//校验联系信息的输入合法性
			return false ;
		}
	}
	return true ;
}

function mainPage_onPageChanged( page, index ){
	/*
	if( actionType == "insert" || actionType == "update" ) {
		return ;
	}
	var partyId = getCurrentPartyId();
	if( index == 0 ){
	
	}else if ( index == 1 ){
		//初始化联系信息面板，填充组织的联系地址信息
		var addressInfo = new AddressInfo() ;
		addressInfo = getRemoteObject( "PartyService","getAddressInfoByPartyId",partyId);
		
		if( addressInfo != null ){ 
			contactInfor.clearData(); 
			contactInfor.insertRecord( null ) ;
			var addObj = addressInfo;
			for( var ele in addObj ){
				if( contactInfor.getField( ele )){
					contactInfor.setValue( ele, addObj[ ele ] ) ; 
				}
			}
			contactInfor.setValue("addDescription",commonInfor.getValue("addDescription"));
		}	
	}else if ( index == 2 ){
		//初始化逻辑地址列表
		queryLogicalAddrList();	
	}*/
	if( actionType == "insert" ) {
		return ;
	}
	if( index == 1 ){
		var addObj = new AddressInfo() ;
		var selItem = organizationTreeView.selectedItem ;
		if( selItem == null ){
			return ;
		}
		var partyId = selItem.partyId ;
		addObj = getRemoteObject( "PartyService","getAddressInfoByPartyId",partyId);
		
		if( addObj != null ){ 
			contactInfor.clearData(); 
			contactInfor.insertRecord( null ) ;
			
			contactInfor.disableControls() ;
			
			for( var ele in addObj ){
				if( contactInfor.getField( ele )){
					contactInfor.setValue( ele, addObj[ ele ] ) ; 
				}
			}
			contactInfor.setValue("addDescription",addressDescription);
			
			contactInfor.enableControls();
		}	
	}
	if( index == 2 ){
		queryLogicalAddrList();
	}
}

function DepartmentVO(){
	this.partyId = "";
	this.departType = "";
	this.termFlag = "";
	this.paySeatType = "";
	this.regionId = "";
	this.regionName = "";
	this.superPartyId = "";
	this.superPartyName = "";
}
 
function displayDepartmentInfo(){
	var partyId = organizationTreeView.selectedItem.partyId ;
	
	var ajaxCall = new NDAjaxCall( false );
	
	var callBack = function( reply ){
		var departmentVo = new DepartmentVO() ;
		departmentVo = reply.getResult() ;
		departmentInfo.clearData();
		departmentInfo.insertRecord( null );
		
		departmentInfo.disableControls(); 
		
		for( var ele in departmentVo ){
			if( departmentInfo.getField( ele )){
				departmentInfo.setValue( ele, departmentVo[ele] );
			}
		}
		departmentInfo.enableControls();
	}
	
	ajaxCall.remoteCall( "PartyService","getMpDepartByPartyId",[partyId], callBack ) ;
}

function downloadSubItems(){
	var selItem = organizationTreeView.selectedItem ;
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
	
	var regionId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
}

var addressDescription = "";
/*
在组织列表上点击记录
根据当前被点击的记录初始化常规信息面板，
初始化联系信息面板，
*/ 
function clickOrganization(){ 
	var selItem = organizationTreeView.selectedItem; 
	if( selItem.partyId == null || selItem.partyId == "" ) {
		return ;
	}
	downloadSubItems() ;
	var partyId = selItem.partyId ;
	
	//清除常规信息Dataset
	commonInfor.clearData();
	commonInfor.insertRecord(null);
	//清空联系信息Dataset
	contactInfor.clearData();
	contactInfor.insertRecord( null );
	//初始化常规信息面板，填充组织的基本信息
	var organizationInfo = new OrganizationInfo();
	organizationInfo = getRemoteObject( "PartyService","getOrganization",partyId);
	addressDescription = organizationInfo["addDescription"];
	
	if( organizationInfo != null ){
		commonInfor.clearData();
		commonInfor.insertRecord(null);
		var orgObj = organizationInfo;
		
		commonInfor.disableControls() ;
		
		for( var ele in orgObj ){
			if( commonInfor.getField( ele )){
				commonInfor.setValue( ele, orgObj[ele] ) ;
			}
		}
		
		var parentParty = getPartyById( selItem.parentPartyId ) ;
		if( parentParty ){
			commonInfor.setValue("parentPartyName", parentParty.orgName);
		}
		
		commonInfor.enableControls();
	}
	
	//if( commonInfor.getValue("orgTypeId") != "5" ){
	if((commonInfor.getValue("orgTypeId")!="5")&&(commonInfor.getValue("orgTypeId")!="6")){//modified 2008-11-06
		divDepartmentInfo.style.display = "none";
		departmentInfo.setReadOnly(true) ;
		departmentInfo.setFieldPopupEnable("regionName",false);
		departmentInfo.setFieldPopupEnable("superPartyName",false);
	}else{
		divDepartmentInfo.style.display = "block" ;
		departmentInfo.setReadOnly(true) ;
		displayDepartmentInfo() ;
		departmentInfo.setFieldPopupEnable("regionName",false);
		departmentInfo.setFieldPopupEnable("superPartyName",false);
	}

	//初始化逻辑地址列表
	//queryLogicalAddrList();
	
	commonInfor.setReadOnly( true );
	contactInfor.setReadOnly( true ) ;
	actionType = "" ;
	nodeType = "";
	mainPage.setSelectedPageIndex(0); 
}

function queryLogicalAddrList(){
	var selItem = organizationTreeView.selectedItem;
	if( selItem.partyId == null || selItem.partyId == "" ) {
		return ;
	}
	
	var partyId = selItem.partyId ;
	var parameters = logicalAddrList.parameters();
	parameters.setDataType("orgId","string");
	parameters.setValue("orgId",partyId);
	Dataset.reloadData(logicalAddrList);	
}
//根据组织分类获取该组织分类下的组织类型
function getOrganizationTypesByClass(){
	var parameterSet = orgTypeInfo.parameters();
	parameterSet.setDataType("orgClass", "string");
	//parameterSet.setValue("orgClass", orgClass) ;
	parameterSet.setValue("orgClass", "00") ;
	Dataset.reloadData( orgTypeInfo ) ;
}

function getPartyById(partyId ){
	var organizationTree = document.all.organizationTreeView;
	var items = organizationTree.items;
	for( var i = 0 ; i < items.length ; i ++ ){
		if( items[i].partyId == partyId ){
			return items[i] ;
		}
	}
}

function AddressInfo(){
	this.addrId = "" ;
	this.provinceName = "" ;
	this.cityName = "" ;
	this.countyName = "" ;
	this.streetName = "" ;
	this.streetNbr = "" ;
	this.deta = "" ;
	this.postcode = "" ;
	this.alias = "" ;
	this.addDescription = "" ;
	this.bankBranchId = "" ;
	this.bankAccNo = "" ;
	this.incrTaxNo = "" ;
}

function OrganizationInfo() {
	this.partyId="";				//参与人ID
	this.orgName="";			//参与人名称
	this.effDate="";				//生效时间
	this.state="";					//状态
	this.stateDate="";			//状态时间
	this.addDescription="";	//地址描述
	this.parentPartyId="";	//上级ID
	this.orgCode="";			//组织编码
	this.orgLevel="";			//组织级别
	this.orgTypeId="";		//组织类型ID(部门,班组......) 
	//this.orgTypeName = "";//组织类型名称
	this.orgContent="";		//组织描述
	this.orgClass="";			//组织分类
	this.orgType = "";////计费组织类型(92B:社会代办点,'92A':电信内部组织)
	this.addrId = "";//地址ID
//	this.expDate="";//失效时间()
	this.orgManager="";//是否组织管理
	this.partnerType="";//合作伙伴类型
}

//从服务器端获取组织或者地址值对象信息
function getRemoteObject( actionName, methodName, partyId ){
	var ajaxCall = new NDAjaxCall(false);//异步方式查询服务器端数据
	var obj ;
	var callBack = function( reply ){
		obj = reply.getResult() ;
	}
	ajaxCall.remoteCall(actionName,methodName,[partyId],callBack);
	return obj ;
}

//增加逻辑地址的点击事件
function addLogicAddr_onClick(){
	if( actionType != "" ){
		alert( "请先保存或者取消当前操作再执行增加操作!") ;
		return ;
	}
	var addressId = contactInfor.getValue("addrId");
	if( !addressId ){
		alert("请先填写联系信息并保存以后才能增加逻辑地址!");
		return ;
	}
	actionType = "insert" ;
	logicalAddrInfo.setReadOnly(false);
	
	logicalAddrInfo.clearData();
	logicalAddrInfo.insertRecord( null ) ;
}
//编辑逻辑地址的点击事件
function editLogicAddr_onClick(){
	if( actionType != "" ){
		alert( "请先保存或者取消当前操作再执行编辑操作!") ;
		return ;
	}
	var logicalAddrId = logicalAddrList.getValue("logicalAddrId");
	if( !logicalAddrId ){
		alert("无选中要编辑的逻辑地址记录!");
		return;
	}
	actionType = "update" ;
	logicalAddrInfo.setReadOnly(false);
}
//删除逻辑地址的点击事件
function deleteLogicAddr_onClick(){
	if( actionType != "" ){
		alert( "请先保存或者取消当前操作再执行删除操作!") ;
		return ;
	}
	var logicalAddrId = logicalAddrList.getValue("logicalAddrId");
	if( !logicalAddrId ){
		alert("请选中您要删除的逻辑地址记录!");
		return;
	}
	if(confirm("确定要删除当前的逻辑地址吗?")){
		var ajaxCall = new NDAjaxCall(true);
		var obj ;
		var callBack = function( reply ){
			obj = reply.getResult() ;
			if( obj  ){
				alert("删除成功!");
				queryLogicalAddrList();
			}else{
				alert("删除失败!");
			}
		}
		ajaxCall.remoteCall("PartyService","deleteLogicalAddr",[logicalAddrId],callBack);
	}
}
//提交按钮的点击事件
function commitLogicAddr_onClick(){
	if( logicalAddrInfo.getValue("logicalAddrDeta") == "" ){
		alert("请填写逻辑地址详细信息!");
		return ;
	}
	var ajaxCall = new NDAjaxCall(true);
	var obj ;
	
	var callBack = function( reply ){
		obj = reply.getResult() ;
		if( actionType == "insert"){
			alert("增加逻辑地址成功!");
		}else if( actionType == "update" ){
			alert("修改逻辑地址成功!");
		}
		queryLogicalAddrList();
		clickLogicalAddressList();
		
		actionType = "" ;
		logicalAddrInfo.setReadOnly(true);
	}
	
	var logicAddr = new LogicalAddress();
	logicAddr["addrId"] = contactInfor.getValue("addrId");
	logicAddr["logicalAddrId"] = logicalAddrInfo.getValue("logicalAddrId");
	logicAddr["logicalAddrType"] = logicalAddrInfo.getValue("logicalAddrType");
	logicAddr["logicalAddrDeta"] = logicalAddrInfo.getValue("logicalAddrDeta");
	logicAddr[Buffalo.BOCLASS]='com.ztesoft.oaas.vo.LogicalAddrVO' ;	
	if( actionType == "insert"){
		ajaxCall.remoteCall("PartyService","insertLogicalAddr",[logicAddr],callBack);
	}else if( actionType == "update"){
		ajaxCall.remoteCall("PartyService","updateLogicalAddr",[logicAddr],callBack);
	}
}
//取消按钮的点击事件
function cancelLogicAddr_onClick(){
	if( actionType == "" ){
		return ;
	}
	actionType = "" ;
	logicalAddrInfo.setReadOnly(true);
}

function LogicalAddress(){
	this.logicalAddrId = "";
	this.addrId = "";
	this.logicalAddrType = "";
	this.logicalAddrDeta = "";
}

function clickLogicalAddressList(){
	logicalAddrInfo.setValue("logicalAddrId",logicalAddrList.getValue("logicalAddrId"));
	logicalAddrInfo.setValue("addrId",logicalAddrList.getValue("addrId"));
	logicalAddrInfo.setValue("logicalAddrType",logicalAddrList.getValue("logicalAddrType"));
	logicalAddrInfo.setValue("logicalAddrDeta",logicalAddrList.getValue("logicalAddrDeta"));	
	logicalAddrInfo.setReadOnly( true );
}

//选择营业区
function button_departmentInfo_regionName_onClick(){
	if( actionType == "" ){
		return ;
	}
	var param = new Object();
	var menuCode = Global.getCurrentMenuCode();
	param["privilegeType"] = "3";//权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码；
	
	//权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
	param["privilegeCode"] = menuCode ;			//菜单编码
	param["regionLevel"] = "97D" ;	//营业区
	param["selectType"] = "1" ;//单选多选标志,1 为单选,2 为多选
	var loginInfo = getStaffLoginInfo();
	param["regionIds"] = loginInfo.businessId ;
	var returnValue=window.showModalDialog("../common/ResourceRegionSelect.jsp",param,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0){return;}
	departmentInfo.setValue("regionId", returnValue[0].regionId);
	departmentInfo.setValue("regionName", returnValue[0].regionName);
}



//选择上级部门
function button_departmentInfo_superPartyName_onClick(){
	if( actionType == "" ){
		return ;
	}

	var para = new Object();
	var menuCode = Global.getCurrentMenuCode();
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
	para["orgType"] = "5" ;//只能选部门
	para["selectParent"] = "2" ;
	para["selectType"] = "1" ;//1表示当选,2表示多选
	para["oldIds"] = getStaffOrganizationId();

	var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0 ) return ;
	departmentInfo.setValue("superPartyId", returnValue[0]["orgId"] );
	departmentInfo.setValue("superPartyName",returnValue[0]["orgName"]);
}

function orgTypeDropdown_onSelect( dropdown, record, editor){
	var newOrgTypeId = parseFloat( record.getValue("value") );//新选择的组织类型ID

	//如果当前的操作类型是插入,则----
	if( actionType == "insert") {
		if( nodeType == "root" ){//如果增加的是根节点,则只能选择类型为集团公司或者其他组织
			if( (newOrgTypeId != 9) && (newOrgTypeId != 0 )){
				alert("根组织的类型只能选择集团公司或者其他组织!");
				return false ;
			}
		}else{//如果增加 的 是下级组织
			var selItem =organizationTreeView.selectedItem ;
			if( selItem == null ){//如果没有选择任何上级组织,则直接返回
				alert("请选择上级组织!");
				return false;
			}
			
			var parentOrgTypeId = "" ;	//上级节点的组织类型ID
			parentOrgTypeId = parseFloat( selItem.orgTypeId );//当前被选中的节点就是新增加的节点的上级节点

			if( parentOrgTypeId == 9 || newOrgTypeId == 9 ){
				if( parentOrgTypeId != newOrgTypeId ){
					alert("类型为其他组织的组织的上下级组织只能是其他组织!");
					return false;
				}
			}			
			//如果上级组织是部门,则下级组织不能大于上级组织(可以等于上级组织,也是部门)
			if( newOrgTypeId < parentOrgTypeId ){//电信内部组织的下级组织不能大于上级组织
				alert("下级组织不能大于上级组织!");
				return false ;
			} 
		}
	}
	
	//if( newOrgTypeId == 5 ){
	if( newOrgTypeId == 5 || newOrgTypeId == 6){//modified 2008-11-06
		divDepartmentInfo.style.display = "block" ; 
		departmentInfo.setReadOnly( false ) ;
		departmentInfo.setFieldReadOnly("regionName",true);
		departmentInfo.setFieldReadOnly("superPartyName",true);		
		if( actionType == "insert" ){
			departmentInfo.clearData();
			departmentInfo.insertRecord(null);
			departmentInfo.setFieldPopupEnable("regionName",true);
			departmentInfo.setFieldPopupEnable("superPartyName",true);
		}
	}else{
		divDepartmentInfo.style.display = "none";
	}
	return true ;
}
 
