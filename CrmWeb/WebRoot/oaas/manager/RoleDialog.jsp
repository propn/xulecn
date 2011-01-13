<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList">
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/RoleDialog.js"></script>
	</head>
	<body>
	<div id="datasetDefine">
		<ui:dataset datasetId="roleList" loadDataAction="PartyService" loadDataActionMethod="getCurrentStaffRolesList" staticDataSource="false">
			<ui:field field="roleId" label="��ɫ��ʶ" visible="false"></ui:field>
			<ui:field field="roleName" label="��ɫ����"></ui:field>
			<ui:field field="roleDesc" label="��ɫ����"></ui:field>
			<ui:field field="state" label="״̬" visible="false"></ui:field>
			<ui:field field="stateDate" label="״̬ʱ��" type="date" visible="false"></ui:field>				
		</ui:dataset>
	</div>
	
		<ui:layout type="border">
			<ui:pane position="center">
				<ui:tabpane id="rolePage">
					<ui:tabpage desc="��ɫ��Ϣ">
						<ui:table dataset="roleList"></ui:table>
					</ui:tabpage>
				</ui:tabpane>		
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="confirm">ȷ��</ui:button>
				<ui:button id="cancel">ȡ��</ui:button>
			</ui:pane>
		</ui:layout>
</body>
</html>