function page_onLoad(){
	initMenu();
}
//��ʼ��TreeList�����������ʾ
function initMenu(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		menuTreeView.loadByXML(queryResult);
		
		//����ϸ����Ӧ��Dataset�ϲ���һ���ռ�¼
		menuInfo.insertRecord(null);
		globleItem = menuTreeView.items[0];
		fillValues(globleItem); 		
	}
	
	ajaxCall.remoteCall("PrivilegeService","getMenuList",[],callBack);
}

//���һ�е���Ϣ����ϸ��Ϣ���
function fillValues(selItem){
    var item = selItem.getColumnData();
	for(var ele in item){
		if(menuInfo.getField(ele)){
			menuInfo.setValue(ele, item[ele]);
		}
	}
	//menuInfo.setFieldPopupEnable("privilegeName",false);
}
//ѡ��Ȩ�ް�ť�ĵ���¼�
function btn_selectPrivilege_onClick(){
	var returnValue=window.showModalDialog("MenuPrivilegeDialog.jsp",null,"dialogHeight: 408pt; dialogWidth: 444pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue != null ){
		menuInfo.setValue("privilegeId",returnValue) ;
	}
}

function clickMenu(){
	var menuTree = document.all.menuTreeView;
	var selItem = menuTree.selectedItem;
	
	menuInfo.disableControls();
	
	cleanValues() ;//���Dataset�еľ�����
	fillValues(selItem);//��Dataset��ֵ
	
	menuInfo.enableControls();
	
	var parentItem = getParentMenuById( selItem.superId );
	if( parentItem != null ){
		menuInfo.setValue("superName",parentItem.menuName); 
	}
	menuInfo.setReadOnly(true);
	actionType = "";
	nodeType = "";
}

var actionType = "" ;//�Բ˵��Ĳ������ͣ���ѡ��ֵΪinsert,update,delete
var nodeType = "";//���ӵĽڵ�����ͣ���ѡ��ֵΪroot,child;

//����һ���Ӳ˵�
function addSubMenu_onClick(){
	if( actionType != "" ){
		alert("�뱣���ȡ����ǰ�Ĳ��������Ӳ˵�!");
		return ;
	}
	if( menuInfo.getValue("openFlag") == "0"){
		alert("��ǰ�˵���Ҷ�Ӳ˵�,����������������Ӳ˵�!");
		return ;
	}
	actionType = "insert";
	nodeType = "child";
	addMenu( getCurrentMenuId() ) ;
}

//����һ�����˵�
function addRootMenu_onClick(){
	if( actionType != "" ){
		alert("�뱣���ȡ����ǰ�Ĳ��������Ӳ˵�!");
		return ;
	}
	actionType = "insert";
	nodeType = "root" ;
	addMenu( null ) ;
}

//���Ӳ˵�
function addMenu(parentMenuId){
	cleanValues();
	menuInfo.setReadOnly(false);
	//menuInfo.setFieldPopupEnable("privilegeName",true);
	
	menuInfo.setFieldReadOnly( "superName", true ) ;
	//menuInfo.setFieldReadOnly("privilegeName",true);
	if(nodeType == "child"){	//���ӵ����ӽڵ�
		var selItem = menuTreeView.selectedItem;
		menuInfo.setValue("systemId", selItem.systemId );
		menuInfo.setFieldReadOnly("systemId",true);
	}
	if( parentMenuId != null ){//�������һ���ϼ��˵����������Ӳ˵���
		var item = getParentMenuById(parentMenuId) ;
		menuInfo.setValue("superName", item.menuName ) ;//�����ϼ��˵�������
		menuInfo.setValue("superId",item.menuId);//�����ϼ��˵���ID
	}
}

//ͨ���ϼ��˵�ID��ȡ�ϼ��˵�����
function getParentMenuById( parentId ){
	var menuTree = document.all.menuTreeView;
	var items = menuTree.items;
	for( var i = 0 ; i < items.length ; i ++ ){
		if( items[i].menuId == parentId ){
			return items[i] ;
		}
	}
}

//�༭�˵�
function editMenu_onClick(){
	if( actionType != "" ){
		alert("�뱣���ȡ����ǰ�Ĳ����ٱ༭�˵�!");
		return ;
	}
	actionType = "update" ;
	menuInfo.setReadOnly(false);
	menuInfo.setFieldReadOnly("superName",true);
	//menuInfo.setFieldPopupEnable("privilegeName",true);	
}

//��ȡ��ǰ��ѡ�е�Ȩ�޲˵���id
function getCurrentMenuId(){
	var menuTree = document.all.menuTreeView;
	var selItem = menuTree.selectedItem;
	if( selItem != null ){
		return selItem.menuId ;
	}else{
		return null ;
	}
}

//�����ϸ����ϵ���Ϣ
function cleanValues(){
	menuInfo.clearData();
	menuInfo.insertRecord(null);
}

function btn_cancel_onClick(){
	actionType = "";
	nodeType = "";
	clickMenu();	
	menuInfo.setReadOnly(true);	
}

function checkMenuCodeInUsed(){
	var ajaxCall = new NDAjaxCall(false) ;//�첽��ʽ��ѯ������������
	var returnValue = false ;
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( actionType == "insert" ){
			if( result > 0 ){
				returnValue = true ;
			}
		}else if( actionType == "update" ){
			if( result > 1 ){
				returnValue = true ;
			}
		}
	}
	
	var menuCode = menuInfo.getValue("menuCode");
	ajaxCall.remoteCall("PrivilegeService","checkMenuCodeInUsed",[menuCode],callBack);
	return returnValue ;
}

function btn_commitMenu_onClick(){
	if( !$("formMenuInfo").validate()){
		return ;
	}
	
	if( actionType == "" ){
		return ;
	}
	
	if( actionType == "update" ){
		var selItem = menuTreeView.selectedItem ;
		if( selItem.items ){
			if( menuInfo.getValue( "openFlag" ) == "0" ){
				alert("��ǰ�˵��Ѿ����¼��˵�,�����޸�Ϊ��Ҷ�ӽڵ�!");
				return ;
			}
		}
	}
	if( checkMenuCodeInUsed() ){//����˵������Ѿ��������˵�ʹ����,�򷵻�
		alert("�˵������Ѿ���ϵͳ�������˵�ʹ����,��ʹ����������!");
		return ;
	}
	var menu = new Menu();//����һ���˵�����
	//��Dataset��������ݸ�ֵ���˵�����
	for(var ele in menu){ 
		if(menuInfo.getField(ele)){
			menu[ele] = menuInfo.getValue( ele );
		} 
	}
	menu["orderId"] = "1";
	menu["menuGrade"] = "1";
	menu["menuType"] = "0" ;
	menu["privFlag"] = "1";
	
	if( nodeType == "root" ){
		menu["superId"] = "-1" ;
	}
	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( actionType == "insert" ){//���Ӳ���
			//�������˷��������ӵĲ˵���ID
			refreshDatainfo(result);//��������Ժ󣬱������³�ʼ��������Dataset�������Ӧ��Ϣ			
			addMenuToTreeList();
			alert("���Ӳ˵��ɹ�!");
		}else if ( actionType == "update" ){//�༭���� 
			refreshTreeList();
			alert("�༭�˵��ɹ�!");
		}
		actionType = "";
		nodeType = "";
		menuInfo.setReadOnly(true);
	}
	
	var arr = new Array();
	arr[0] = menu;
	if( actionType == "insert" ){//���Ӳ���
		ajaxCall.remoteCall("PrivilegeService","insertMenu",arr,callBack);
	}else if ( actionType == "update" ){//�༭����
		ajaxCall.remoteCall("PrivilegeService","updateMenu",arr,callBack);
	}
}

//��TreeList������һ����¼ 
function addMenuToTreeList(){
	if( nodeType == "root" ){
		addRootToTreeList();
	}else if( nodeType = "child" ) {
		addChildToTreeList();
	}
	nodeType = "" ;
}

function addRootToTreeList(){
	var menuTree = document.all.menuTreeView;
	var menu = menuTree.createTreeNode();
	var menu1 = new Menu();
	 
	for(var ele in menu1){
		if(menuInfo.getField(ele)){
			menu[ele] = menuInfo.getValue( ele );
		} 
	}
	menuTree.add(menu);
	menu.setSelected();
}

function addChildToTreeList(){
	var menuTree = document.all.menuTreeView;
	var menu = menuTree.createTreeNode();
	var selItem = menuTree.selectedItem;
	
	for(var ele in selItem){
		if(menuInfo.getField(ele)){
			menu[ele] = menuInfo.getValue( ele );
		} 
	}
	selItem.add(menu);
	menu.setSelected();
}

//��Update��ǰ�Ĳ˵��Ժ󣬸���TreeList�϶�Ӧ�ļ�¼
function refreshTreeList(){
	var menuTree = document.all.menuTreeView;
	var menu = menuTree.selectedItem;

	for(var ele in menu){
		if(menuInfo.getField(ele)){
			menu[ele] = menuInfo.getValue( ele );
		} 
	}
		
	menu.refresh();
}

function refreshDatainfo(id){
	menuInfo.setValue("menuId", id ) ;
}

function Menu(){
	this.menuId = "";
	this.menuName = "";
	this.systemId = "";
	this.menuCode = "";
	this.orderId = "1";
	this.targetName = "";
	this.para = "";
	this.openFlag = "";
	//this.privilegeFlag = "";
	this.validFlag = "";
	this.menuType = "";
	this.menuGrade = "1";
	this.superId = "";
	//this.superName = "";
	this.imagePath = "";
	this.comments = "";
	this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.MmMenuVO' ;	
	//this.privilegeId = "" ;
	//this.privilegeName = "" ;
}

function deleteMenu_onClick(){
	if( confirm( "��ȷ��Ҫɾ����ǰ�˵���¼��?" )){ 
	
		var currentMenuId = getCurrentMenuId();
		
		var ajaxCall = new NDAjaxCall(true);
		
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;
			if(deleteResult='-1'){
				if(confirm("�˽ڵ��������ӽڵ㣬ҲҪȷ��ɾ����?")){
					ajaxCall.remoteCall("PrivilegeService","deleteMenuWithSubNotes",[currentMenuId],function(){
					initMenu();
					alert("ɾ���˵��ɹ���");
					});
				}
			}else{
				deleteMenuFromTreeList();//����ǰ��¼��TreeList��ɾ��
				initMenu();
				alert("ɾ���˵��ɹ���");
			}

		}
		
		ajaxCall.remoteCall("PrivilegeService","deleteMenu",[currentMenuId],callBack);
	}
}

//��TreeList��ɾ��һ����¼
function deleteMenuFromTreeList(){
	var menuTree = document.all.menuTreeView;
	var selItem = menuTree.selectedItem;
	selItem.remove();
}
/*
function button_menuInfo_privilegeName_onClick(){
	if( actionType == "" ){
		return ;
	}
	var returnValue=window.showModalDialog("MenuPrivilegeDialog.jsp",null,"dialogHeight: 408pt; dialogWidth: 444pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue != null ){
		menuInfo.setValue("privilegeId", returnValue["privilegeId"]);
		menuInfo.setValue("privilegeName",returnValue["privilegeName"]);
	}
}
*/