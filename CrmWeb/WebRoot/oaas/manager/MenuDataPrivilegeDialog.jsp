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
				//��Ӧ��¼�ϵ�check box�ĵ���¼�
				clickMenu();
				/*
				 *ѡ��˵��Ĺ���:ѡ��һ���¼��˵�,��ò˵��������ϼ��˵����Զ���ѡ;���һ���˵����¼��˵�
				 *�Ѿ���ѡ,��ò˵�Ϊ��ѡ,����ͨ���κη�ʽȡ���ýڵ㱻ѡ��״̬.
				 */
				var selItem = menuTreeView.selectedItem;
				var subItems = selItem.items ;
				if( selItem.getChecked() == false ){
					/*���ȡ����ѡһ���˵�,�жϸ�Ȩ���Ƿ��������һ������ѡ���Ӳ˵�,�����,
					 *����ʾ�û������½��ò˵���ѡ��.
					 */
					if( subItems ){
						for( var i = 0 ; i < subItems.length ; i ++ ){
							if( subItems[i].getChecked()){
								alert("�¼��˵����ڱ�ѡ��״̬,�ϼ��˵�����ȡ��ѡ��!");
								selItem.setChecked("true");
								return ;			
							}
						}
					}
				}
				
				if( selItem.getChecked() ){
					/**
					 *���һ���˵���ѡ��,���Զ��Ľ��ò˵��������ϼ��˵���ѡ��
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
					alert("û��ѡ���µĲ˵���¼,��ѡ���¼!");
					return ;
				}
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ) {
					alert("���ӳɹ�!") ;
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
				<ui:panel type="titleList" desc="Ȩ�޹�������">
					<ui:content>
						<ZTESOFT:treelist id="menuTreeView" class="treelist" showCheck="true" showImage="true" showBorder="false" checkParent="false" checkChildren="false"
						contBorder="true" width="100%" height="100%" showHead="true" onItemChecked="checkedItem()" onItemClick="clickMenu()">
							<ZTESOFT:columns>
								<ZTESOFT:column width="50%" display="true" displayText="�˵�����" propertyName="menuName" />
								<ZTESOFT:column width="50%" display="true" displayText="�˵�����" propertyName="menuCode" />							
								<ZTESOFT:column width="" display="false" displayText="�˵�ID" propertyName="menuId" />								
								<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="targetName" />								
								<ZTESOFT:column width="" display="false" displayText="����" propertyName="para" />
								<ZTESOFT:column width="" display="false" displayText="Ȩ������" propertyName="privilegeName" />
								<ZTESOFT:column width="" display="false" displayText="�˵��򿪱�־" propertyName="openFlag" />
								<ZTESOFT:column width="" display="false" displayText="Ȩ���жϱ�־" propertyName="privilegeFlag" />
								<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="menuType" />
								<ZTESOFT:column width="" display="false" displayText="ϵͳ��ʶ" propertyName="systemId" />
								<ZTESOFT:column width="" display="false" displayText="ͬ�����" propertyName="orderId" />
								<ZTESOFT:column width="" display="false" displayText="��Ч��־" propertyName="validFlag" />
								<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="menuGrade" />
								<ZTESOFT:column width="" display="false" displayText="�ϼ��˵���ʶ" propertyName="superId" />
								<ZTESOFT:column width="" display="false" displayText="�˵�ͼƬ·��" propertyName="imagePath" />
								<ZTESOFT:column width="" display="false" displayText="��ע" propertyName="comments" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btnConfirm">ȷ��</ui:button>
				<ui:button id="btnCancel">ȡ��</ui:button>
			</ui:pane>
		</ui:layout>		
	
	</body>
</html>