<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,validator,calendar">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/AddressDialog.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<!--  
			ui:dataset datasetId="logicalAddrList" loadDataAction="PartyService" loadDataActionMethod="getLogicalAddrsByAddrId" 
			async="false" staticDataSource="true" 
			-->
			<ui:dataset datasetId="logicalAddrList" editable="true" staticDataSource="true">
				<ui:field field="logicalAddrId" label="�߼���ַ��ʶ" visible="false"></ui:field>
				<ui:field field="addrId" label="��ַ��ʶ" visible="false"></ui:field>
				<ui:field field="logicalAddrType" label="�߼���ַ����"  dropDown="logicalAddrTypeDropdown"></ui:field>
				<ui:field field="logicalAddrDeta" label="�߼���ַ��ϸ��Ϣ"></ui:field>
			</ui:dataset>

			<ui:dropdown id="logicalAddrTypeDropdown" attrCode="LOGICAL_ADDR_TYPE" staticDataSource="false"></ui:dropdown>

			<!-- ��ϵ��ϢDataset-->
			<ui:dataset datasetId="contactInfor" editable="true">
				<ui:field field="addrId" label="��ַ��ʶ" visible="false"></ui:field>
				<ui:field field="provinceName" label="ʡ��" required="true" validType="require" validMsg="������ʡ��"></ui:field>
				<ui:field field="cityName" label="������" required="true" validType="require" validMsg="�����������"></ui:field>
				<ui:field field="countyName" label="����" required="true" validType="require" validMsg="������������"></ui:field>
				<ui:field field="streetName" label="�ֵ���" required="true" validType="require" validMsg="������ֵ���"></ui:field>
				<ui:field field="streetNbr" label="���ƺ�" required="true" validType="require" validMsg="���������ƺ�"></ui:field>
				<ui:field field="deta" label="��ϸ��Ϣ" required="true" validType="require" validMsg="��������ϸ��Ϣ"></ui:field>
				<ui:field field="postcode" label="��������" required="true" validType="require" validMsg="��������������"></ui:field>
				<ui:field field="alias" label="��ַ����"></ui:field>
			</ui:dataset>
		</div>
		
		
		
				<ui:layout type="border">
					<ui:pane position="top" style="height:140px">
						<ui:panel type="titleList" desc="��ַ��Ϣ" >
							<ui:content>
								<ui:form dataset="contactInfor" id="contactForm" labelLayout="15%" inputLayout="34%"></ui:form>
							</ui:content>
						</ui:panel>
					</ui:pane>
					
					<ui:pane position="center">
								<ui:bar type="search" desc="�߼���ַ">
									<ui:content>
										<ui:button id="addLogicAddr">����</ui:button>
										<ui:button id="deleteLogicAddr">ɾ��</ui:button>
									</ui:content>
								</ui:bar>
								<ui:table dataset="logicalAddrList"></ui:table>
					</ui:pane>
					
					<ui:pane position="bottom">
						<ui:button id="commitAddr">����</ui:button>
						<ui:button id="closeDialog">�ر�</ui:button>						
					</ui:pane>
				</ui:layout>

	</body>
</html>