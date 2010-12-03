<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script>
			var privId = "";
			var privType = "";
			var selectedMenuId = new Array();
			
			function page_onLoad(){
				privId = window.dialogArguments[0] ;
				privType = window.dialogArguments[1] ;
				getMenuByPrivilege();
				
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ){
					var result = reply.getResult() ;
					menuTreeView.loadByXML( result ) ;
					if( selectedMenuId.length > 0 ){
						for( var i = 0; i < menuTreeView.items.length ; i ++ ){
							for( var j = 0 ; j < selectedMenuId.length ; j ++ ){
								if( selectedMenuId[j] == menuTreeView.items[i].menuId ){
									menuTreeView.items[i].setChecked( true ) ;
								}
							}
						}
					}
				}
				ajaxCall.remoteCall("PrivilegeService","getMenuByParentId",["-1"],callBack);
			}
			
			function clickMenu(){
				if( menuTreeView.selectedItem.items ){
					return ;
				}
				
				var menuId = menuTreeView.selectedItem.menuId ;
				
				var ajaxCall = new NDAjaxCall( false ) ;
				var callBack = function( reply ){
					var result = reply.getResult() ;
					menuTreeView.selectedItem.insertByXML( result ) ;
					if( selectedMenuId.length > 0 ){
						for( var i = 0; i < menuTreeView.selectedItem.items.length ; i ++ ){
							for( var j = 0 ; j < selectedMenuId.length ; j ++ ){
								if( selectedMenuId[j] == menuTreeView.selectedItem.items[i].menuId ){
									menuTreeView.selectedItem.items[i].setChecked( true ) ;
								}
							}
						}
					}
					menuTreeView.selectedItem.expand(true);
				}
				ajaxCall.remoteCall("PrivilegeService","getMenuByParentId",[menuId],callBack);			
			}
			
			function checkedItem(){
				//响应记录上的check box的点击事件
				clickMenu();
				/*
				 *选择菜单的规则:选择一个下级菜单,则该菜单的所有上级菜单都自动被选;如果一个菜单的下级菜单
				 *已经被选,则该菜单为必选,不能通过任何方式取消该节点被选择状态.
				 */
				var selItem = menuTreeView.selectedItem;
				var subItems = selItem.items ;
				if( selItem.getChecked() == false ){
					/*如果取消钩选一个菜单,判断该权限是否存在任意一个被钩选的子菜单,如果有,
					 *则提示用户并重新将该菜单钩选上.
					 */
					if( subItems ){
						for( var i = 0 ; i < subItems.length ; i ++ ){
							if( subItems[i].getChecked()){
								alert("下级菜单处于被选择状态,上级菜单不能取消选择!");
								selItem.setChecked("true");
								return ;			
							}
						}
					}
				}
				
				if( selItem.getChecked() ){
					/**
					 *如果一个菜单被选中,则自动的将该菜单的所有上级菜单都选中
					 */
					var parentItem = selItem.getParentItem() ;
					while( parentItem ){
						parentItem.setChecked( true ) ;
						parentItem = parentItem.getParentItem() ;
					}
				}
			}
			
			function getMenuByPrivilege(){
				var ajaxCall = new NDAjaxCall( false ) ;
				var callBack = function( reply ){
					selectedMenuId = reply.getResult() ;
				}
				ajaxCall.remoteCall("PrivilegeService","getMenuIdByPrivilege",[privId],callBack);
			}
			
			function btnConfirm_onClick(){
				var saveObjList = new Array() ;
				var checkedMenus = menuTreeView.checkedItems ;
				var count = 0 ;
				for( var i = 0; i < checkedMenus.length ; i ++ ) {
					var newMenu = true ;
					for( var j = 0 ; j < selectedMenuId.length ; j ++ ){
						if( selectedMenuId[j] == checkedMenus[i].menuId ){
							newMenu = false ;
							break ;
						}
					}
					if( newMenu ) {
						var obj = new MmDataPrivVO() ;
						obj["privId"] = window.dialogArguments[0] ; 
						obj["dataPkey1"]= checkedMenus[i].menuId ;
						obj["dataPkey2"] = "-1" ; 
						obj["dataPkey3"] = "-1"; 
						obj["dataPkey4"] = ""; 
						obj["name"] = "" ; 
						obj["datasetName"] = "b"; 
						obj["fieldName"] = "" ; 
						obj["offerName"] = ""; 
						obj["serviceOfferName"] = "";

						saveObjList[count] = obj ; 
						count ++ ;
					}
				}
				if( saveObjList.length == 0 ){
					alert("没有选择新的菜单记录,请选择记录!");
					return ;
				}
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ) {
					alert("增加成功!") ;
					window.returnValue = 0 ;
					window.close();
				}
				ajaxCall.remoteCall("PrivilegeService","addMenuDataPrivileges",[saveObjList],callBack);
			}
			
			function btnCancel_onClick(){
				window.returnValue = -1 ;
				window.close() ;
			}
			function DataPrivilegeVO(){
				this.privId = "";
				this.dataPkey1 = "";
				this.dataPkey2 = "";
				this.dataPkey3 = "";
			}
			function MmDataPrivVO(){
				this.privId = "";
				this.dataPkey1 = "";
				this.dataPkey2 = "";
				this.dataPkey3 = "";
				this.dataPkey4 = "";
				this.name = "";
				this.datasetName = "";
				this.fieldName = "";
				this.offerName = "";
				this.serviceOfferName = "";
				this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.MmDataPrivVO' ;	
			}
		</script>
	</head>
	<body>
		<ui:layout type="border">
			<ui:pane position="center">
				<ui:panel type="titleList" desc="权限关联数据">
					<ui:content>
						<ZTESOFT:treelist id="menuTreeView" class="treelist" showCheck="true" showImage="true" showBorder="false" checkParent="false" checkChildren="false"
						contBorder="true" width="100%" height="100%" showHead="true" onItemChecked="checkedItem()" onItemClick="clickMenu()">
							<ZTESOFT:columns>
								<ZTESOFT:column width="50%" display="true" displayText="菜单名称" propertyName="menuName" />
								<ZTESOFT:column width="50%" display="true" displayText="菜单编码" propertyName="menuCode" />							
								<ZTESOFT:column width="" display="false" displayText="菜单ID" propertyName="menuId" />								
								<ZTESOFT:column width="" display="false" displayText="菜单连接" propertyName="targetName" />								
								<ZTESOFT:column width="" display="false" displayText="参数" propertyName="para" />
								<ZTESOFT:column width="" display="false" displayText="权限名称" propertyName="privilegeName" />
								<ZTESOFT:column width="" display="false" displayText="菜单打开标志" propertyName="openFlag" />
								<ZTESOFT:column width="" display="false" displayText="权限判断标志" propertyName="privilegeFlag" />
								<ZTESOFT:column width="" display="false" displayText="菜单类型" propertyName="menuType" />
								<ZTESOFT:column width="" display="false" displayText="系统标识" propertyName="systemId" />
								<ZTESOFT:column width="" display="false" displayText="同级序号" propertyName="orderId" />
								<ZTESOFT:column width="" display="false" displayText="有效标志" propertyName="validFlag" />
								<ZTESOFT:column width="" display="false" displayText="菜单级别" propertyName="menuGrade" />
								<ZTESOFT:column width="" display="false" displayText="上级菜单标识" propertyName="superId" />
								<ZTESOFT:column width="" display="false" displayText="菜单图片路径" propertyName="imagePath" />
								<ZTESOFT:column width="" display="false" displayText="备注" propertyName="comments" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btnConfirm">确定</ui:button>
				<ui:button id="btnCancel">取消</ui:button>
			</ui:pane>
		</ui:layout>		
	
	</body>
</html>