<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/MOrganizationSelect.js"></script>
	</head>
	<body>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ZTESOFT:treelist id="organizationTreeView" onItemClick="clickOrganization()" checkChildren="false"
					class="treelist" showImage="false" showCheck="true" showBorder="true" contBorder="true" showImage=false width="100%" height="300" showHead=true>
						<ZTESOFT:columns>
							<ZTESOFT:column width="40%" display="true" displayText="��֯����" propertyName="orgName" />
							<ZTESOFT:column width="20%" display="true" displayText="��֯����" propertyName="orgCode" />
							<ZTESOFT:column width="40%" display="true" displayText="��֯���" propertyName="orgContent" />
							<ZTESOFT:column width="" display="false" displayText="Ȩ�ޱ�־" propertyName="privilegeFlag" />							
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
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_Confirm">ȷ��</ui:button>
					<ui:button id="btn_Cancel">ȡ��</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
