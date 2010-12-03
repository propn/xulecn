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
		function page_onLoad(){
 			var queryResult = null ;
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				queryResult = reply.getResult() ;
				orgTree.loadByXML(queryResult );
			}
			ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
		}
		
		function btnConfirm_onClick(){
			var arr = new Array() ;
			arr[0] = orgTree.selectedItem.partyId;
			arr[1] = orgTree.selectedItem.orgName ;
			window.returnValue = arr ;
			window.close();
		}
		function btnCancel_onClick(){
			window.returnValue = null ;
			window.close() ;
		}
		function clickOrganization(){
			var selItem = orgTree.selectedItem ;
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
			
			var orgId = selItem.partyId ;
			ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[orgId], callBack);
		}
		</script>
	</head>

	<body>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:panel type="titleList" desc="��֯ѡ��">
						<ui:content>
							<ZTESOFT:treelist id="orgTree" onItemClick="clickOrganization()" class="treelist" showImage="false" showBorder="true" contBorder="true" showImage=false width="100%" height="300" showHead=true>
								<ZTESOFT:columns>
									<ZTESOFT:column width="30%" display="true" displayText="��֯����" propertyName="orgName" />
									<ZTESOFT:column width="30%" display="true" displayText="��֯����" propertyName="orgCode" />
									<ZTESOFT:column width="40%" display="true" displayText="��֯���" propertyName="orgContent" />
									<ZTESOFT:column width="" display="false" displayText="�����˱�ʶ" propertyName="partyId" />
									<ZTESOFT:column width="" display="false" displayText="�ϼ���֯" propertyName="parentPartyId" />
									<ZTESOFT:column width="" display="false" displayText="��֯����" propertyName="orgLevel" />
									<ZTESOFT:column width="" display="false" displayText="��֯���ͱ�ʶ" propertyName="orgTypeId" />
									<ZTESOFT:column width="" display="false" displayText="��ַ��ʶ" propertyName="addressId" />
									<ZTESOFT:column width="" display="false" displayText="״̬" propertyName="state" />
									<ZTESOFT:column width="" display="false" displayText="״̬ʱ��" propertyName="stateDate" />
									<ZTESOFT:column width="" display="false" displayText="��֯����" propertyName="orgClass" />
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
		</div>
	</body>
</html>
