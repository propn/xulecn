function page_onLoad(){
	initMenu();
}
//初始化TreeList组件的数据显示
function initMenu(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		menuTreeView.loadByXML(queryResult);
		
		//在详细面板对应的Dataset上插入一个空记录
		menuInfo.insertRecord(null);
		globleItem = menuTreeView.items[0];
		fillValues(globleItem); 		
	}
	
	ajaxCall.remoteCall("PrivilegeService","getMenuList",[],callBack);
}

//填充一行的信息到详细信息面板
function fillValues(selItem){
    var item = selItem.getColumnData();
	for(var ele in item){
		if(menuInfo.getField(ele)){
			menuInfo.setValue(ele, item[ele]);
		}
	}
	//menuInfo.setFieldPopupEnable("privilegeName",false);
}
//选择权限按钮的点击事件
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
	
	cleanValues() ;//清除Dataset中的旧数据
	fillValues(selItem);//给Dataset赋值
	
	menuInfo.enableControls();
	
	var parentItem = getParentMenuById( selItem.superId );
	if( parentItem != null ){
		menuInfo.setValue("superName",parentItem.menuName); 
	}
	menuInfo.setReadOnly(true);
	actionType = "";
	nodeType = "";
}

var actionType = "" ;//对菜单的操作类型，可选的值为insert,update,delete
var nodeType = "";//增加的节点的类型，可选的值为root,child;

//增加一个子菜单
function addSubMenu_onClick(){
	if( actionType != "" ){
		alert("请保存或取消当前的操作再增加菜单!");
		return ;
	}
	if( menuInfo.getValue("openFlag") == "0"){
		alert("当前菜单是叶子菜单,不能在其下面添加子菜单!");
		return ;
	}
	actionType = "insert";
	nodeType = "child";
	addMenu( getCurrentMenuId() ) ;
}

//增加一个根菜单
function addRootMenu_onClick(){
	if( actionType != "" ){
		alert("请保存或取消当前的操作再增加菜单!");
		return ;
	}
	actionType = "insert";
	nodeType = "root" ;
	addMenu( null ) ;
}

//增加菜单
function addMenu(parentMenuId){
	cleanValues();
	menuInfo.setReadOnly(false);
	//menuInfo.setFieldPopupEnable("privilegeName",true);
	
	menuInfo.setFieldReadOnly( "superName", true ) ;
	//menuInfo.setFieldReadOnly("privilegeName",true);
	if(nodeType == "child"){	//增加的是子节点
		var selItem = menuTreeView.selectedItem;
		menuInfo.setValue("systemId", selItem.systemId );
		menuInfo.setFieldReadOnly("systemId",true);
	}
	if( parentMenuId != null ){//如果是在一个上级菜单下面增加子菜单。
		var item = getParentMenuById(parentMenuId) ;
		menuInfo.setValue("superName", item.menuName ) ;//设置上级菜单的名称
		menuInfo.setValue("superId",item.menuId);//设置上级菜单的ID
	}
}

//通过上级菜单ID获取上级菜单对象
function getParentMenuById( parentId ){
	var menuTree = document.all.menuTreeView;
	var items = menuTree.items;
	for( var i = 0 ; i < items.length ; i ++ ){
		if( items[i].menuId == parentId ){
			return items[i] ;
		}
	}
}

//编辑菜单
function editMenu_onClick(){
	if( actionType != "" ){
		alert("请保存或取消当前的操作再编辑菜单!");
		return ;
	}
	actionType = "update" ;
	menuInfo.setReadOnly(false);
	menuInfo.setFieldReadOnly("superName",true);
	//menuInfo.setFieldPopupEnable("privilegeName",true);	
}

//获取当前被选中的权限菜单的id
function getCurrentMenuId(){
	var menuTree = document.all.menuTreeView;
	var selItem = menuTree.selectedItem;
	if( selItem != null ){
		return selItem.menuId ;
	}else{
		return null ;
	}
}

//清楚详细面板上的信息
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
	var ajaxCall = new NDAjaxCall(false) ;//异步方式查询服务器端数据
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
				alert("当前菜单已经有下级菜单,不能修改为非叶子节点!");
				return ;
			}
		}
	}
	if( checkMenuCodeInUsed() ){//如果菜单编码已经被其他菜单使用了,则返回
		alert("菜单编码已经被系统中其他菜单使用了,请使用其他编码!");
		return ;
	}
	var menu = new Menu();//创建一个菜单对象
	//将Dataset里面的数据赋值给菜单对象
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
		if( actionType == "insert" ){//增加操作
			//服务器端返回新增加的菜单的ID
			refreshDatainfo(result);//完成增加以后，必须重新初始化关联的Dataset里面的相应信息			
			addMenuToTreeList();
			alert("增加菜单成功!");
		}else if ( actionType == "update" ){//编辑操作 
			refreshTreeList();
			alert("编辑菜单成功!");
		}
		actionType = "";
		nodeType = "";
		menuInfo.setReadOnly(true);
	}
	
	var arr = new Array();
	arr[0] = menu;
	if( actionType == "insert" ){//增加操作
		ajaxCall.remoteCall("PrivilegeService","insertMenu",arr,callBack);
	}else if ( actionType == "update" ){//编辑操作
		ajaxCall.remoteCall("PrivilegeService","updateMenu",arr,callBack);
	}
}

//在TreeList上增加一个记录 
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

//在Update当前的菜单以后，更新TreeList上对应的记录
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
	if( confirm( "您确信要删除当前菜单记录吗?" )){ 
	
		var currentMenuId = getCurrentMenuId();
		
		var ajaxCall = new NDAjaxCall(true);
		
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;
			if(deleteResult='-1'){
				if(confirm("此节点下面有子节点，也要确认删除吗?")){
					ajaxCall.remoteCall("PrivilegeService","deleteMenuWithSubNotes",[currentMenuId],function(){
					initMenu();
					alert("删除菜单成功！");
					});
				}
			}else{
				deleteMenuFromTreeList();//将当前记录从TreeList上删除
				initMenu();
				alert("删除菜单成功！");
			}

		}
		
		ajaxCall.remoteCall("PrivilegeService","deleteMenu",[currentMenuId],callBack);
	}
}

//从TreeList上删除一个记录
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