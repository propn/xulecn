<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,calendar,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/RolePrivilegeDialog.js"></script>
	</head>
	<body>
		<ui:dataset datasetId="dateInfor" staticDataSource="true">
			<ui:field field="effDate" type="date" label="��Ч����" defaultValue="today" required="true" validType="require" validMsg="��ѡ����Ч����!" ></ui:field>
			<ui:field field="expDate" type="date" label="ʧЧ����" required="true" validType="require" validMsg="��ѡ��ʧЧ����!"></ui:field>
		</ui:dataset>
		
				<ui:panel type="modalDialog" desc="��ɫȨ��">
					<ui:content>
						<ui:layout type="border">
							<ui:pane position="center">
								<ZTESOFT:treelist id="rolePrivilegeTreeView" onItemClick="clickPrivilege()" onItemChecked="itemChecked()" 
								showCheck="true" class="treelist" showImage="false" showBorder="true" contBorder="true" 
								showImage=false width="100%" height="450" showHead=true checkParent="false" checkChildren="false">
									<ZTESOFT:columns>
										<ZTESOFT:column width="33%" display="true" displayText="Ȩ������" propertyName="privName" />
										<ZTESOFT:column width="33%" display="true" displayText="Ȩ������" propertyName="privDesc" />
										<ZTESOFT:column width="33%" display="true" displayText="Ȩ�ޱ���" propertyName="privCode" />
										<ZTESOFT:column width="" display="false" displayText="parentPrgId" propertyName="parentPrgId" />
										<ZTESOFT:column width="" display="false" displayText="privilegeId" propertyName="privId" />
										<ZTESOFT:column width="" display="false" displayText="privilegeType" propertyName="privType" />
									</ZTESOFT:columns>
								</ZTESOFT:treelist>
							</ui:pane>
							<ui:pane position="bottom">
								<ui:button id="btn_OK">ȷ��</ui:button>
								<ui:button id="btn_Cancel">ȡ��</ui:button>
							</ui:pane>
						</ui:layout>
					</ui:content>
				</ui:panel>
	</body>
</html>
