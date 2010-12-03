<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
<head>
<title>ѡ�񱾵���</title>
<META HTTP-EQUIV="library" CONTENT="kernel">
<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
<ui:import library=""></ui:import>
<script>

function btn_confirm_onClick(){
         window.returnValue=staffInfo.getValue("STAFF_CODE"); 
         window.close();
}

function btn_cancel_onClick(){
         window.close();
}

	   function table_staffInfo_ondblclick()
	   {
	   
	   		 btn_confirm_onClick();
	   }
</script>
</head>

<body>

		<div id="datasetDefine">
			<ui:dataset datasetId="staffInfo" loadDataAction="SignOnService" loadDataActionMethod="getVirtualStaffInfo" staticDataSource="false">
					<ui:field field="REGION_NAME" label="������"></ui:field>
					<ui:field field="ORG_NAME" label="����"></ui:field>
					<ui:field field="PARTY_ROLE_NAME" label="Ա������"></ui:field>
					<ui:field field="STAFF_CODE" label="����"></ui:field>
			</ui:dataset>
		</div>
		
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="ѡ�񱾵���">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
								<ui:table dataset="staffInfo" id="table_staffInfo"></ui:table>
						</ui:pane>
						<ui:pane position="bottom">
								<ui:button id="btn_confirm">ȷ��</ui:button>
								<ui:button id="btn_cancel">ȡ��</ui:button>	
						</ui:pane>
					</ui:layout>
				</ui:content>		
			</ui:panel>  
		</div>

</body>
</html>