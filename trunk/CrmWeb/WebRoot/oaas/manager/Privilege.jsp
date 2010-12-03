<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/Privilege.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="privilegeInfo" readOnly="true">
				<ui:field field="privId" label="Ȩ�ޱ���" visible="false"></ui:field>
				<ui:field field="parentPrivilegeName" label="�ϼ�Ȩ��"></ui:field>
				<ui:field field="parentPrgId" label="��Ȩ��ID" visible="false"></ui:field>
				<ui:field field="privName" label="Ȩ������" required="true" validType="require" validMsg="������Ȩ������!"></ui:field>
				<ui:field field="privCode" label="Ȩ�ޱ���" required="true" validType="limit" minLen="1" maxLen="6" validMsg="�����볤��Ϊ1��6��Ȩ�ޱ���!"></ui:field>
				<ui:field field="privType" label="ʹ������" attrCode="PRIVILEGE_TYPE"></ui:field>
				<ui:field field="privDesc" label="Ȩ������" inputLayout="56%" validType="require" required="true" validMsg="������Ȩ��������Ϣ!"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="dataPrivilegeRuleList" readOnly="true" loadDataAction="PrivilegeService" loadDataActionMethod="getSimpleDataPrivilegeRule" staticDataSource="false">
				<ui:field field="privType" label="privType" visible="true"></ui:field>
				<ui:field field="getValMode" label="getValMode" visible="true"></ui:field>
				<ui:field field="getValSql" label="getValSql" visible="true"></ui:field>
				<ui:field field="transSql" label="transSql" visible="true"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="privNameInfo">
				<ui:field field="privName" label="Ȩ������"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="Ȩ�޹���">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="btn_queryPrivilege" dataset="privNameInfo" id="privNameInfoForm" labelLayout="75%" inputLayout="25%"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:300px">
									<ui:button id="btn_queryPrivilege">��ѯ</ui:button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<ui:button id="addRootPrivilege">��Ӹ�Ȩ��</ui:button>
									<ui:button id="addSubPrivilege">�����Ȩ��</ui:button>
									<ui:button id="editPrivilege">�༭</ui:button>
									<ui:button id="deletePrivilege">ɾ��</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="privilegeTreeView" class="treelist" onItemClick="clickPrivilege()" showImage="false" showBorder="false" contBorder="false" showImage="false" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="43%" display="true" displayText="Ȩ������" propertyName="privName" />
							<ZTESOFT:column width="43%" display="true" displayText="Ȩ������" propertyName="privDesc" />
							<ZTESOFT:column width="14%" display="true" displayText="Ȩ�ޱ���" propertyName="privCode" />
							<ZTESOFT:column width="" display="false" displayText="privilegeType" propertyName="privType" />
							<ZTESOFT:column width="" display="false" displayText="parentPrgId" propertyName="parentPrgId" />
							<ZTESOFT:column width="" display="false" displayText="privilegeId" propertyName="privId" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height:300px;">
					<ui:tabpane id="mainPage">
						<ui:tabpage desc="Ȩ����Ϣ">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="btn_commitPrivilege" dataset="privilegeInfo" id="privilegeInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="btn_commitPrivilege">ȷ��</ui:button>
									<ui:button id="btn_cancel">ȡ��</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
						<ui:tabpage desc="Ȩ�޹�������" target="dynaFrame" href="BlankDataPrivilegeView.jsp" autoLoad="true">
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
