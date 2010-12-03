function page_onLoad(){
	initPartnerTree() ;
}

function initPartnerTree(){
	var callBack = function( reply ){
		var returnXML = reply.getResult() ;
		partnerTree.loadByXML( returnXML ) ;
		clickPartner();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var queryVo = new PartnerQueryVO() ;
	queryVo.superPartnerId = "-1" ;
	ajaxCall.remoteCall( "PartnerService", "queryPartner", [queryVo], callBack ) ;
}

var actionType = "";
var nodeType = "";
function editPartner_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再编辑合作伙伴!");
		return ;
	}
	var selItem = partnerTree.selectedItem;
	if ( selItem == null ){
		return ;
	}
	if( selItem.partnerId == "" || selItem.partnerId == null ){
		return ;
	}
	actionType = "update" ;
	partnerInfo.setReadOnly( false ) ;

	partnerInfo.setFieldPopupEnable("orgName",true);
	partnerInfo.setFieldPopupEnable("staffName",true);	
	
	partnerInfo.setFieldReadOnly("orgName",true);
	partnerInfo.setFieldReadOnly("staffName",true);	
		
	mainPage.setSelectedPageIndex(0);
}
//增加一级合作伙伴
function addRootPartner_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加合作伙伴!");
		return ;
	}
	nodeType = "root" ;	
	addPartner() ;
}
//增加下级合作伙伴
function addChildPartner_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再增加合作伙伴!");
		return ;
	}
	nodeType = "child" ;
	addPartner() ;	
}
//增加合作伙伴
function addPartner(){
	actionType = "insert" ;
	partnerInfo.setReadOnly( false ) ;
	partnerInfo.clearData();
	partnerInfo.insertRecord( null ) ;

	partnerInfo.setFieldPopupEnable("orgName",true);
	partnerInfo.setFieldPopupEnable("staffName",true);
	
	partnerInfo.setFieldReadOnly("orgName",true);
	partnerInfo.setFieldReadOnly("staffName",true);
	
	mainPage.setSelectedPageIndex(0);
}
function savePartner_onClick(){
	if( !$("partnerInfoForm").validate()){
		return ;
	}	
	var saveVO =  new PartnerInfoVO() ;
	for( var ele in saveVO ){
		if( partnerInfo.getField( ele ) ){
			saveVO[ele] = partnerInfo.getValue( ele ) ;
		}
	}
	if( nodeType == "root" ){
		saveVO["superPartnerId"] = "-1" ;
	}else if( nodeType == "child"){
		var selItem = partnerTree.selectedItem ;
		saveVO["superPartnerId"] = selItem.partnerId ;
	}
	
	var callBack = function( reply ) {
		if( actionType == "insert" ){
			alert("增加合作伙伴成功!");
			partnerInfo.setValue("partnerId",reply.getResult());
			addPartnerToTreeList() ;
		}else if( actionType == "update" ){
			alert("修改合作伙伴成功!");
			refreshTreeList();
		}
		//initPartnerTree();
		actionType = "" ;
		nodeType = "" ;
		partnerInfo.setReadOnly( true ) ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	if( actionType == "insert" ) {
		ajaxCall.remoteCall( "PartnerService", "insertPartner",[saveVO],callBack);
	}else if( actionType == "update") {
		ajaxCall.remoteCall( "PartnerService", "updatePartner",[saveVO],callBack);
	}
}
function refreshTreeList(){
	var partner = partnerTree.selectedItem;

	for(var ele in partner){
		if(partnerInfo.getField(ele)){
			partner[ele] = partnerInfo.getValue( ele );
		} 
	}
		
	partner.refresh();
}
function addPartnerToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}
function addRootToTreeList(){
	var partner = partnerTree.createTreeNode();
	 
	 var partner1 = new PartnerInfoVO();
	for(var ele in partner1){
		if(partnerInfo.getField(ele)){
			partner[ele] = partnerInfo.getValue( ele );
		} 
	}
	partnerTree.add(partner);
	partner.setSelected();
}
function addChildToTreeList(){
	var partner = partnerTree.createTreeNode();
	var selItem = partnerTree.selectedItem;
	
	for(var ele in selItem){
		if(partnerInfo.getField(ele)){
			partner[ele] = partnerInfo.getValue( ele );
		} 
	}
	selItem.add(partner);
	partner.setSelected();
}
function cancelPartner_onClick(){
	actionType = "" ;
	nodeType = "" ;
	partnerInfo.setReadOnly(true) ;
	clickPartner();
}

//标签页切换前的事件,判断当前情况是否允许用户切换标签页
function mainPage_beforePageChanged(page,index){
	if( actionType != ""){
		alert("请先保存或取消后再切换到其他标签页!");
		return false ;
	}
	return true ;
}

//切换标签页事件,加载相关数据
function mainPage_onPageChanged(page, index){
	if( actionType == ""){
		if( index == 0 ){
			cancelPartner_onClick();
		}
		if( index == 1 ){
			loadConferList();
		}
		if( index == 2 ) {
			loadDepartRelatList() ;
		}
	}
}

//加载合作伙伴相关的合同列表
function loadConferList(){
	var selItem = partnerTree.selectedItem ;
	if( selItem == null ){
		return ;
	}
	conferList.parameters().setDataType( "partnerId","string");
	conferList.parameters().setValue("partnerId",partnerTree.selectedItem.partnerId);
	Dataset.reloadData( conferList ) ;
	table_conferList_onclick();
}

//加载合作伙伴相关的关联部门列表
function loadDepartRelatList(){
	var selItem = partnerTree.selectedItem ;
	if( selItem == null ){
		return ;
	}
	departRelatList.parameters().setDataType("partnerId", "string") ;
	departRelatList.parameters().setValue("partnerId", partnerTree.selectedItem.partnerId);
	Dataset.reloadData( departRelatList ) ;
	//table_departRelatList_onclick();
}

//增加合同按钮响应事件
function insertConferInfo_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再增加合同!");
		return ;
	}
	actionType = "insert" ;
	conferInfo.setReadOnly( false ) ;
	conferInfo.clearData() ;
	conferInfo.insertRecord( null ) ;
}

//编辑合同按钮响应事件
function editConferInfo_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再编辑合同!");
		return ;
	}
	if( conferList.getValue("partnerConfId") == "" || conferList.getValue("partnerConfId") == null ){
		return ;
	}
	actionType = "update" ;
	conferInfo.setReadOnly( false ) ;
}

//保存合同按钮响应事件
function saveConfer_onClick(){
	if( !$("conferInfoForm").validate()){
		return ;
	}
	
	var conferVo = new PartnerConferInfoVO();
	for( var ele in conferVo ){
		if( conferInfo.getField( ele )){
			conferVo[ele] = conferInfo.getValue( ele ) ;
		}
	}
	
	conferVo["partnerId"] = partnerTree.selectedItem.partnerId ;
	
	var callBack = function( reply ){
		if( actionType == "insert" ){
			alert("插入合同成功!") ;
		}else if( actionType =="update") {
			alert("修改合同成功!") ;
		}
		actionType = "" ;
		conferInfo.setReadOnly(true);
		loadConferList();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	if( actionType == "insert" ){
		ajaxCall.remoteCall( "PartnerService","insertPartnerConferInfo",[conferVo], callBack ) ;
	}else if( actionType == "update"){
		ajaxCall.remoteCall( "PartnerService","updatePartnerConferInfo",[conferVo], callBack ) ;
	}
}

//取消合同按钮响应事件
function cancelConfer_onClick(){
	actionType = "" ;
	conferInfo.setReadOnly( true ) ;
	table_conferList_onclick() ;
}

//合同列表的点击事件
function table_conferList_onclick(){
	for( var i = 0 ; i < conferList.fields.length ; i ++ ){
		conferInfo.setValue( i , conferList.getValue( i ) ) ;
	}
}

/*
//关联部门列表的点击事件
function table_departRelatList_onclick(){
	for( var i = 0 ; i < departRelatInfo.fields.length ; i ++ ){
		departRelatInfo.setValue( i, departRelatList.getValue( i ) ) ;
	}
}
*/

//增加关联部门按钮的响应事件
function insertDepartRelat_onClick(){
	var arr = new Array() ;
	arr[0] = "05";
	arr[1] = "1" ;//单选
	var returnValue=window.showModalDialog("../common/DepartmentSelect.jsp",arr,"dialogHeight: 400pt; dialogWidth: 400pt");
	if( returnValue == null || returnValue.length == 0){
		return ;
	}
	var departVo = new PartnerDeptRelatVO();
	departVo["partnerId"] = partnerTree.selectedItem.partnerId ;
	departVo["partyId"] = returnValue[0]["partyId"] ;
	
	//判断用户选中的部门是否已经在departRelatList数据集中
	var record = departRelatList.getFirstRecord();
	while ( record ){
		if( record.getValue("partyId") == departVo["partyId"] ){
			alert("您选择的部门已经被关联了!");
			return ;
		}
		record = record.getNextRecord();
	}
	
	var callBack = function( reply ){
		alert("插入关联部门成功!");
		departRelatInfo.setReadOnly( true ) ;
		loadDepartRelatList();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	ajaxCall.remoteCall( "PartnerService","insertPartnerDepartRelat",[departVo], callBack ) ;
}

//删除关联部门按钮的响应事件
function deleteDepartRelat_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再删除合作伙伴!");
		return ;
	}
	if( departRelatList.getValue("partyId") == "" || departRelatList.getValue("partyId") == null ){
		return ;
	}
	if( !confirm( "您确定要删除当前记录吗?")){
		return ;
	} 
	
	var callBack = function( reply ) {
		alert("删除关联部门成功!");
		loadDepartRelatList();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var arr = new Array() ;
	arr[0] = departRelatList.getValue("partnerId");
	arr[1] = departRelatList.getValue("partyId");
	ajaxCall.remoteCall("PartnerService","deletePartnerDepartRelat", arr,callBack);
}
/*
//保存关联部门的按钮响应事件
function saveDepartRelat_onClick(){
	var departVo = new PartnerDeptRelatVO();
	for( var ele in departVo ){
		departVo[ele] = departRelatInfo.getValue( ele ) ;
	}
	
	departVo["partnerId"] = partnerTree.selectedItem.partnerId ;
	
	var callBack = function( reply ){
		if( actionType == "insert" ){
			alert("插入关联部门成功!");
		}else if( actionType == "update" ){
			alert("修改关联部门成功!");
		}
		actionType = "" ;
		departRelatInfo.setReadOnly( true ) ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	if( actionType == "insert" ) {
		ajaxCall.remoteCall( "PartnerService","insertPartnerDepartRelat",[departVo], callBack ) ;
	}else if ( actionType == "update" ) {
		ajaxCall.remoteCall( "PartnerService","updatePartnerDepartRelat",[departVo], callBack ) ;	
	}
}

//取消关联部门按钮的响应事件
function cancelDepartRelat_onClick(){
	actionType = "" ;
	departRelatInfo.setReadOnly( true ) ;
}
*/

//查询按钮响应事件
function queryPartner_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前操作再查询合作伙伴!");
		return ;
	}
	var queryVo = new PartnerQueryVO() ;
	for( var ele in queryVo ){
		if( queryInfo.getField( ele )){
			queryVo[ele] = queryInfo.getValue( ele ) ;
		}
	}
	
	var callBack = function( reply ){
		var returnXML = reply.getResult() ;
		partnerTree.loadByXML( returnXML ) ;
		clickPartner();
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	ajaxCall.remoteCall( "PartnerService", "queryPartner", [queryVo], callBack ) ;
}

//查询重置按钮响应事件
function queryReset_onClick(){
	queryInfo.clearData() ;
	queryInfo.insertRecord( null ) ;
}

//合作伙伴树表点击事件
function clickPartner(){
	//获取当前节点下的所有下级节点.
	downloadSubItems() ;
	actionType = "" ;
	nodeType = "" ;
	partnerInfo.setReadOnly(true) ;
	var selItem = partnerTree.selectedItem ;
	
	partnerInfo.disableControls();
	for( var ele in selItem ){
		if( partnerInfo.getField( ele )){
			partnerInfo.setValue( ele, selItem[ele] ) ;
		}	
	}
	partnerInfo.setFieldPopupEnable("orgName",false);
	partnerInfo.setFieldPopupEnable("staffName",false);
	partnerInfo.enableControls();
	
	mainPage.setSelectedPageIndex(0);
}

//当用户点击区域树表的时候,判断当前节点是否有下级节点,如果没有,
//到服务器端查看是否有下级节点,如果服务器端返回了该节点的下级
//节点数据,则将这些数据解析成为树表的下级节点,添加到树表上.
function downloadSubItems(){
	var selItem = partnerTree.selectedItem ;
	if( selItem == null ) 
		return ;
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
	
	var parentId = selItem.partnerId ;
	var queryVO = new PartnerQueryVO() ;
	queryVO.superPartnerId = parentId ;
	ajaxCall.remoteCall("PartnerService","queryPartner",[queryVO], callBack);
}

//合作伙伴数据集 管理部门按钮 按钮点击事件
function button_partnerInfo_orgName_onClick(){
	var orgInfo = getOrganization();
	if( orgInfo == null ){
		return ;
	}
	partnerInfo.setValue( "orgId", orgInfo["orgId"] ) ;
	partnerInfo.setValue( "orgName", orgInfo["orgName"] ) ;
	partnerInfo.setValue("staffId","");
	partnerInfo.setValue("staffName","");
}

//查询信息数据集 管理部门按钮 点击事件
function button_queryInfo_orgName_onClick(){
	var orgInfo = getOrganization();
	if( orgInfo == null ){
		return ;
	}
	queryInfo.setValue( "orgId", orgInfo["orgId"] ) ;
	queryInfo.setValue( "orgName", orgInfo["orgName"] ) ;
}

function button_queryInfo_staffName_onClick(){
	var orgId = queryInfo.getValue("orgId") ;
	if( orgId == null ||orgId == ""){
		alert("请先选中管理部门!");
		return;
	}
	var staffInfo = getStaff( orgId ) ;
	if( staffInfo == null ){
		return ;
	}
	if( staffInfo[0] == null ){
		return ;
	}
	queryInfo.setValue("staffId", staffInfo[0]["id"] ) ;
	queryInfo.setValue("staffName",staffInfo[0]["name"]);
}

//合作伙伴 数据集 负责人按钮点击事件
function button_partnerInfo_staffName_onClick(){
	var orgId = partnerInfo.getValue("orgId") ;
	if( orgId == null ||orgId == ""){
		alert("请先选中管理部门!");
		return;
	}
	var staffInfo = getStaff( orgId ) ;
	
	if( staffInfo == null || staffInfo[0] == null ){
		partnerInfo.setValue("staffId", "" ) ;
		partnerInfo.setValue("staffName","");		
		return ;
	}
	partnerInfo.setValue("staffId", staffInfo[0]["id"] ) ;
	partnerInfo.setValue("staffName",staffInfo[0]["name"]);
}

function getStaff(orgId){
	return returnValue=window.showModalDialog("../common/StaffSelect.jsp",[orgId,"0","1"],"dialogHeight: 458pt; dialogWidth: 544pt");
}

//调用组织选中组件,获取用户选中的组织.
function getOrganization(){
	var para = new Object() ;
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
	para["orgType"] = "5" ;
	para["selectType"] = "1" ;//1表示当选,2表示多选
	para["oldIds"] = getStaffOrganizationId();
	
	var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0) return null ;
	return returnValue[0];
}

function PartnerQueryVO(){
	this.partnerCode = "" ;
	this.partnerType  = "" ;
	this.cooperateType = "" ;
	this.partnerGrade = "" ;
	this.orgId = "" ;
	this.staffId = "" ;
	this.superPartnerId = "";
}

function PartnerInfoVO(){
	this.partnerId = "";
	this.partyRoleId = "";
	this.partnerCode = "";
	this.partnerType = "";
	this.partnerDesc = "";
	this.corpAgent = "";
	this.addr = "";
	this.applyDate = "";
	this.linkman = "";
	this.corporateLicenceNo = "";
	this.spcpLicenceNo = "";
	this.bankPermit = "";
	this.companySize = "";
	this.companyBalns = "";
	this.cooperateType = "";
	this.devPlan = "";
	this.partnerGrade = "";
	this.state = "";
	this.orgId = "";
	this.orgName = "";
	this.staffName = "";
	this.staffId = "";
	this.superPartnerId = "";
	this.pathCode = "";
}

function PartnerConferInfoVO(){
	this.partnerConfId = "";
	this.partnerId = "";
	this.conferCode = "";
	this.partnerConferType = "";
	this.signDate = "";
	this.expDate = "";
	this.cooperateType = "";
	this.conferContence = "";
	this.balnsRule = "";
	this.partnerDroitDuty = "";
	this.droitDuty = "";
	this.abortCondiction = "";
	this.breakDuty = "";
	this.state = "";
}

function PartnerDeptRelatVO(){
	this.partyId = "";
	this.partnerId = "";
}