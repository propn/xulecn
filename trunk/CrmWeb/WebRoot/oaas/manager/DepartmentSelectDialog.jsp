<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title>����ѡ��</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/DepartmentSelectDialog.js"></script>
	</head>
	<body>
	
	<ui:layout type="border">
		<ui:pane position="center">
			<ui:panel type="titleList" desc="����ѡ��">
				<ui:content>
					<ZTESOFT:treelist id="organizationTreeView" class="treelist" onItemClick="clickOrganization()" showImage="false" showBorder="true" contBorder="true" showImage=false width="100%" height="350" showHead=true>
						<ZTESOFT:columns>
							<ZTESOFT:column width="80%" display="true" displayText="��֯����" propertyName="orgName" />
							<ZTESOFT:column width="20%" display="true" displayText="��֯����" propertyName="orgCode" />
							<ZTESOFT:column width="" display="false" displayText="��֯���" propertyName="orgContent" />
							<ZTESOFT:column width="" display="false" displayText="��֯���ͱ�ʶ" propertyName="orgTypeId" />							
							<ZTESOFT:column width="" display="false" displayText="�����˱�ʶ" propertyName="partyId" />
							<ZTESOFT:column width="" display="false" displayText="�ϼ���֯" propertyName="parentPartyId" />
							<ZTESOFT:column width="" display="false" displayText="��֯����" propertyName="orgLevel" />
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
			<ui:button id="confirm">ȷ��</ui:button>
			<ui:button id="cancel">ȡ��</ui:button>
		</ui:pane>
	</ui:layout>
	
	</body>
</html>
	