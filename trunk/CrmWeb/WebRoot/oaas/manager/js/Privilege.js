function page_onLoad(){
	initPrivilege();
}

function btn_queryPrivilege_onClick(){
 	var queryResult = null ;
 	var privName = privNameInfo.getValue("privName");

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.privilegeTreeView.loadByXML(queryResult);
		privilegeInfo.insertRecord(null);
		globleItem = document.all.privilegeTreeView.items[0];
		fillValues(globleItem); 
	}
	if(privName==null||privName=='')initPrivilege();
	else
	   ajaxCall.remoteCall("PrivilegeService","getPrivilegeListWithRegionRelatByName",[privName],callBack);
}

//��ʼ��TreeList�����������ʾ
function initPrivilege(){ 
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.privilegeTreeView.loadByXML(queryResult);
		//����ϸ����Ӧ��Dataset�ϲ���һ���ռ�¼
		privilegeInfo.insertRecord(null);
		
		globleItem = document.all.privilegeTreeView.items[0];
		fillValues(globleItem); 
		//queryPrivilegeData();
	}
	
	//ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",["-1"],callBack);
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListWithRegionRelatByParentId",["-1"],callBack);//����Ȩ�޵Ĺ�����½Ա��ֻ�ܹ�����ӵ�е�Ȩ��
}

function clickPriv(){
	
}

//����һ�е���Ϣ����ϸ��Ϣ���
function fillValues(item){
	for(var ele in item){
		if(privilegeInfo.getField(ele)){
			privilegeInfo.setValue(ele, item[ele]);
		}
	}					
}

//�����ϸ����ϵ���Ϣ
function cleanValues(){
	privilegeInfo.clearData();
	privilegeInfo.insertRecord(null);
}

function downloadSubItems(){
	var selItem = privilegeTreeView.selectedItem ;
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
	
	var privId = selItem.privId ;
	ajaxCall.remoteCall("PrivilegeService","getPrivilegeListByParentId",[privId], callBack);
}

/*function queryPrivilegeData(){
	dataPrivilegeList.clearData();
	var privilegeType = privilegeTreeView.selectedItem.privType ;
	var privilegeId = privilegeTreeView.selectedItem.privId ;
	dataPrivilegeList.parameters().setDataType("privilegeId","string");
	dataPrivilegeList.parameters().setValue("privilegeId",privilegeId);
	dataPrivilegeList.parameters().setDataType("privilegeType","string");
	dataPrivilegeList.parameters().setValue("privilegeType",privilegeType);
	
	Dataset.reloadData( dataPrivilegeList ) ;
}*/

//���ĵ���¼�������ϸ�������ʾ��ǰ�����еļ�¼����ϸ��Ϣ
function clickPrivilege(){
	var privilegeTree = document.all.privilegeTreeView;
	var selItem = privilegeTree.selectedItem;
	
	downloadSubItems() ;
	
	cleanValues() ;//���Dataset�еľ�����
	fillValues(selItem);//��Dataset��ֵ
	privilegeInfo.setReadOnly( true ) ;
	actionType = "";
	nodeType = "";

	var parentItem = selItem.getParentItem() ;
	if( parentItem != null ){
		privilegeInfo.setValue("parentPrivilegeName",parentItem.privName); 
	}
	mainPage.setSelectedPageIndex(0);
	window.frames["dynaFrame"].location = "BlankDataPrivilegeView.jsp";
}

function changeDataPrivilegeView(){
	var privType = privilegeTreeView.selectedItem.privType ;
	var privId = privilegeTreeView.selectedItem.privId ;
	
	var getValMode = "" ;
	var url = "";
	var addDataPrivilegeDialogURL = "";
	var record = dataPrivilegeRuleList.getFirstRecord() ;
	while( record ) {
		if( privType == record.getValue("privType") ){
			getValMode = record.getValue("getValMode") ;
			url = record.getValue("transSql") ;
			addDataPrivilegeDialogURL = record.getValue("getValSql");
			break ;
		}
		record = record.getNextRecord() ;
	}
	
	var privilegeType = privilegeTreeView.selectedItem.privType ;
	var privilegeId = privilegeTreeView.selectedItem.privId ;
	if( "0" == getValMode ){
		window.frames["dynaFrame"].location = "BaseDataPrivilegeView.jsp?privilegeType=" + privilegeType + "&privilegeId=" + privilegeId + "&addDataPrivilegeDialogURL=" + addDataPrivilegeDialogURL;
	}else if( "1" == getValMode ){
		window.frames["dynaFrame"].location = url + "?privilegeType=" + privilegeType + "&privilegeId=" + privilegeId + "&addDataPrivilegeDialogURL=" + addDataPrivilegeDialogURL; 
	}else if( "2" == getValMode ){//�����2,���ʾ��ǰȨ�޲������ݹ���
		return false ;
	}
	return true ;
}

//��ȡ��ǰ��ѡ�е�Ȩ�޼�¼��id
function getCurrentPrivilegeId(){
	var privilegeTree = document.all.privilegeTreeView;
	var selItem = privilegeTree.selectedItem;
	if( selItem != null ){
		return selItem.privId ;
	}else{
		return null ;
	}
}

//ͨ���ϼ�Ȩ��ID��ȡ�ϼ�Ȩ�޶���
function getParentPrivilegeById( parentId ){
	var privilegeTree = document.all.privilegeTreeView;
	var items = privilegeTree.items;
	for( var i = 0 ; i < items.length ; i ++ ){
		if( items[i].privId == parentId ){
			return items[i] ;
		}
	}
}

//����Ȩ��
function addPrivilege(parentPrivilegeId){
	cleanValues();
	mainPage.setSelectedPageIndex(0);
	if( parentPrivilegeId != null ){//�������һ���ϼ�Ȩ������������Ȩ�ޡ�
		var item = getParentPrivilegeById(parentPrivilegeId) ;
		privilegeInfo.setValue("parentPrivilegeName", item.privName ) ;//�����ϼ�Ȩ�޵�����
		privilegeInfo.setValue("parentPrgId",item.privId);//�����ϼ�Ȩ�޵�ID
		privilegeInfo.setValue("privType",item.privType);
	}
	privilegeInfo.setReadOnly(false);
	if( nodeType == "root"){
		privilegeInfo.setFieldReadOnly("parentPrivilegeName",true);
	}else if( nodeType == "child" ){ 
		privilegeInfo.setFieldReadOnly("parentPrivilegeName",true);	
		privilegeInfo.setFieldReadOnly("privType",true);
	}
}

//����һ����Ȩ��
function addSubPrivilege_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ����������Ȩ��!");
		return ;
	}
	actionType = "insert";
	nodeType = "child";
	addPrivilege( getCurrentPrivilegeId() ) ;
}

//����һ����Ȩ��
function addRootPrivilege_onClick(){ 
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ����������Ȩ��!");
		return ;
	}
	actionType = "insert";
	nodeType = "root" ;
	addPrivilege( null ) ;
}

function deletePrivilege_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ����������Ȩ��!");
		return ;
	}
	if( !confirm( "��ȷ��Ҫɾ����ǰȨ�޼�¼��?" )){ 
		return ;
	}
	
	var currentPrivilegeId = getCurrentPrivilegeId();
	
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		var deleteResult = reply.getResult() ;
		if( deleteResult == 0 ){
			deletePrivilegeFromTreeList();//����ǰ��¼��TreeList��ɾ��
			alert("ɾ��Ȩ�޳ɹ���");
		}else if( deleteResult == 1 ){
			alert("Ȩ���Ѿ�������ʹ��,����ɾ��!");
		}else {
			alert("ɾ��Ȩ��ʧ�ܣ��������¼��ڵ㡣" );
		}
	}
	
	ajaxCall.remoteCall("PrivilegeService","deletePrivilege",[currentPrivilegeId],callBack);
}

//��������fucPWDchk
//���ܽ��ܣ�����Ƿ��з����ֻ���ĸ
//����˵����Ҫ�����ַ���
//����ֵ��0������ 1��ȫ��Ϊ���ֻ���ĸ
function fucPWDchk(str)
{
  var strSource ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  var ch;
  var i;
  var temp;

  for (i=0;i<=(str.length-1);i++)
  {

    ch = str.charAt(i);
    temp = strSource.indexOf(ch);
    if (temp==-1)
    {
     return 0;
    }
  }
  if (strSource.indexOf(ch)==-1)
  {
    return 0;
  }
  else
  {
    return 1;
  }
}


//�ύ��ť��Ӧ�¼������÷������˷�������¼
function btn_commitPrivilege_onClick(){
	if( actionType == "" ){
		return ;
	}
	
	if(!$("privilegeInfoForm").validate()){
		return ;
	}
	if(fucPWDchk(privilegeInfo.getValue("privCode"))==0){
	    alert("Ȩ�ޱ���ֻ��Ϊ6λ�����ֻ���ĸ!");
	    return;
	}
	
	
	var ajaxCall = new NDAjaxCall(true);
	

	var privilege = new Privilege();
	//��Dataset��������ݸ�ֵ���˵�����
	for(var ele in privilege){ 
		if(privilegeInfo.getField(ele)){
			privilege[ele] = privilegeInfo.getValue( ele );
		} 
	}
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result ) {
			if( actionType == "insert" ){//���Ӳ���
				//�������˷��������ӵ�Ȩ�޵�ID
				refreshDatainfo(result);//��������Ժ󣬱������³�ʼ��������Dataset�������Ӧ��Ϣ			
				addPrivilegeToTreeList(); 
				alert("����Ȩ�޳ɹ�!");
			}else if ( actionType == "update" ){//�༭����
				refreshTreeList(); 
				alert("�༭Ȩ�޳ɹ�!");
			}
		}

		privilegeInfo.setReadOnly(true);	
		actionType = "" ;
		nodeType = "" ;
				
	}
	
	//���Ȩ�ޱ����Ƿ����
   var checkPrivCode=function(reply){
	var result=reply.getResult();
	if(result==true){
	     alert("Ȩ�ޱ����Ѿ����ڣ�����������һ�����룡");
	     return;
	    }
	    else{
	    
	var arr = new Array();
	arr[0] = privilege;
	if( actionType == "insert" ){//���Ӳ���
		if( nodeType == "root" ){
			privilege["parentPrgId"] = "-1";
		}
		ajaxCall.remoteCall("PrivilegeService","insertPrivilege",arr,callBack);
	}else if ( actionType == "update" ){//�༭����
		ajaxCall.remoteCall("PrivilegeService","updatePrivilege",arr,callBack);
	}
	    }
	}
	ajaxCall.remoteCall("PrivilegeService","hasPrivlegeByCode",[privilegeInfo.getValue("privCode")],checkPrivCode);
}

//��Update��ǰ��Ȩ���Ժ󣬸���TreeList�϶�Ӧ�ļ�¼
function refreshTreeList(){
	var privilegeTree = document.all.privilegeTreeView;
	var privilege = privilegeTree.selectedItem;
	
	for(var ele in privilege){
		if(privilegeInfo.getField(ele)){
			privilege[ele] = privilegeInfo.getValue( ele );
		} 
	}
	privilege.refresh();
}

function refreshDatainfo(id){
	privilegeInfo.setValue("privId", id ) ;
}

function addChildToTreeList(){
	var privilegeTree = document.all.privilegeTreeView;
	var selItem = privilegeTree.selectedItem;
	var privilege = privilegeTree.createTreeNode();
	
	for(var ele in selItem){
		if(privilegeInfo.getField(ele)){
			privilege[ele] = privilegeInfo.getValue( ele );
		} 
	}
	
	selItem.add(privilege);
	privilege.setSelected();
}

function addRootToTreeList(){
	var privilegeTree = document.all.privilegeTreeView;
	var privilege = privilegeTree.createTreeNode();
	
	var privilege1 = new Privilege();
	 
	for(var ele in privilege1){
		if(privilegeInfo.getField(ele)){
			privilege[ele] = privilegeInfo.getValue( ele );
		} 
	}

	//
	privilegeTree.add(privilege);
	//�Ѹýڵ�ѡ���ϣ�����������룬���鲻Ҫѡ��
	privilege.setSelected();
}

var actionType = "" ;//ȫ�ֱ������������ͣ�����Ϊupdate:�༭��insert:����
var nodeType = "";

//�༭Ȩ��
function editPrivilege_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�����ٱ༭Ȩ��!");
		return ;
	}
	mainPage.setSelectedPageIndex(0);
	actionType = "update" ;
	privilegeInfo.setReadOnly(false);
	privilegeInfo.setFieldReadOnly("privType",true);
	privilegeInfo.setFieldReadOnly("parentPrivilegeName",true);
}

//��TreeList������һ����¼ 
function addPrivilegeToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}

//��TreeList��ɾ��һ����¼
function deletePrivilegeFromTreeList(){
	var privilegeTree = document.all.privilegeTreeView;
	var selItem = privilegeTree.selectedItem;
	selItem.remove();
}

function btn_cancel_onClick(){
	actionType = "";
	nodeType = "";
	privilegeInfo.setReadOnly(true);	
	clickPrivilege();
}

//Ȩ�޶���
function Privilege(){
	this.privId = "";
	this.privName = "";
	this.privCode = "";
	this.parentPrgId = "";
	this.privDesc = "";
	this.privType = "";
	this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.PrivVO' ;
}

function button_privilegeInfo_parentPrivilegeName_onClick(){
	if( actionType == "" || nodeType == "root" ){
		return ;
	}
	var returnValue=window.showModalDialog("PrivilegeDialog.jsp",null,"dialogHeight: 408pt; dialogWidth: 444pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue != null ){
		privilegeInfo.setValue("parentPrgId",returnValue["privilegeId"]) ;
		var parentPrivilege = getParentPrivilegeById( returnValue["privilegeId"] ) ;
		privilegeInfo.setValue("parentPrivilegeName",parentPrivilege.privName) ;
	}
}

/*function btn_deleteDataPrivilege_onClick(){
	var obj = new Object() ;
	obj.privId = privilegeTreeView.selectedItem.privId ;
	obj.dataPkey1 = dataPrivilegeList.getValue("dataPkey1");
	obj.dataPkey2 = dataPrivilegeList.getValue("dataPkey2");
	obj.dataPkey3 = dataPrivilegeList.getValue("dataPkey3");
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ) {
		alert("ɾ���ɹ�!");
		queryPrivilegeData();
	}
	ajaxCall.remoteCall("PrivilegeService","deleteDataPrivilege",[obj],callBack);
}
*/
function mainPage_beforePageChanged(page,index){
	if( actionType != ""){
		alert("���ȵ��'ȷ��'��ť�������ݺ����л���������ǩҳ!");
		return false ;
	}
	return true ;
}

function mainPage_onPageChanged(page, index ){
	if( index == 1 ){
		if( !changeDataPrivilegeView() ) {
			alert("��ǰ��Ȩ�����Ͳ����������!");
			mainPage.setSelectedPageIndex( 0 ) ;
		}
	}
}

function btn_addDataPrivilege_onClick(){
	var privType = privilegeTreeView.selectedItem.privType ;
	var privId = privilegeTreeView.selectedItem.privId ;
	
	var getValMode = "" ;
	var url = "";
	var record = dataPrivilegeRuleList.getFirstRecord() ;
	while( record ) {
		if( privType == record.getValue("privType") ){
			getValMode = record.getValue("getValMode") ;
			url = record.getValue("getValSql" ) ;
			break ;
		}
		record = record.getNextRecord() ;
	}
	var returnValue = null ;
	if( getValMode == "0" ){
		returnValue = window.showModalDialog("PrivilegeDataDialog.jsp",[privId,privType],"dialogHeight: 408pt; dialogWidth: 344pt;");
	}else if( getValMode == "1" ){
		returnValue = window.showModalDialog(url,[privId],"dialogHeight: 408pt; dialogWidth: 344pt;");
	}
	
	/*if( returnValue != null ){
		var record = dataPrivilegeList.getFirstRecord() ;
		while( record ) {
			if( record.getValue( "dataPkey1" ) == returnValue.dataPeky1 &&
			record.getValue( "dataPkey2" ) == returnValue.dataPeky2 &&
			record.getValue( "dataPkey3" ) == returnValue.dataPeky3 ){
				alert("��ѡ���Ȩ�޹��������Ѿ�����!");
				return ;
			}
			record = record.getNextRecord() ;
		}
	
		returnValue.privId = privilegeTreeView.selectedItem.privId ;
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ) { 
			alert("���ӳɹ�!"); 
			queryPrivilegeData();
		}
		ajaxCall.remoteCall("PrivilegeService","addDataPrivilege",[returnValue],callBack);
	}*/
}
