<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList,validator">
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/queryRoles.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
		
			<ui:dataset datasetId="queryInfo">
				<ui:field field="roleName" label="��ɫ����"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="roleInfo" readOnly="true">
				<ui:field field="roleId" label="��ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="roleName" label="��ɫ����" required="true" validType="require" validMsg="�������ɫ����!"></ui:field>
				<ui:field field="roleNameShort" label="��ɫ������д"></ui:field>				
				<ui:field field="roleDesc" label="��ɫ����"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" type="date" visible="false"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="roleList" loadDataAction="PrivilegeService" loadDataActionMethod="getRelatingRoleByRoleId" staticDataSource="true">
				<ui:field field="roleId" label="��ɫ��ʶ" visible="true"></ui:field>
				<ui:field field="roleName" label="��ɫ����"></ui:field>
				<ui:field field="roleNameShort" label="��ɫ������д"></ui:field>								
				<ui:field field="roleDesc" label="��ɫ����"></ui:field>
				<ui:field field="state" label="״̬" attrCode="ROLES_STATE" visible="true"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" visible="true"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				
				<ui:pane position="top">
					<ui:bar type="search" desc="��ɫ��ѯ">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="doQuery" dataset="queryInfo"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:250px;text-align:center;">
									<ui:button id="doQuery">��ѯ</ui:button>
									<ui:button id="doQueryAll">��ѯȫ��</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				
				<ui:pane position="center">
					<ZTESOFT:treelist id="roleTreeView" class="treelist" onItemClick="clickRole()" showBorder="false" contBorder="true" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="25%" display="true" displayText="��ɫ����" propertyName="roleName" />
							<ZTESOFT:column width="65%" display="true" displayText="��ɫ����" propertyName="roleDesc" />
							<ZTESOFT:column width="10%" display="true" displayText="��д" propertyName="roleNameShort" />
							<ZTESOFT:column width="" display="false" displayText="��ɫ��ʶ" propertyName="roleId" />							
							<ZTESOFT:column width="" display="false" displayText="״̬" propertyName="state" />
							<ZTESOFT:column width="" display="false" displayText="״̬ʱ��" propertyName="stateDate" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				
				<ui:pane position="bottom" >
					<ui:button id="btn_commitRole">ȷ��</ui:button>
				</ui:pane>
				
			</ui:layout>
		</div>
	</body>
</html>
