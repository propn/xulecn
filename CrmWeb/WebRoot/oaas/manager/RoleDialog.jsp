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
			<ui:field field="roleId" label="角色标识" visible="false"></ui:field>
			<ui:field field="roleName" label="角色名称"></ui:field>
			<ui:field field="roleDesc" label="角色描述"></ui:field>
			<ui:field field="state" label="状态" visible="false"></ui:field>
			<ui:field field="stateDate" label="状态时间" type="date" visible="false"></ui:field>				
		</ui:dataset>
	</div>
	
		<ui:layout type="border">
			<ui:pane position="center">
				<ui:tabpane id="rolePage">
					<ui:tabpage desc="角色信息">
						<ui:table dataset="roleList"></ui:table>
					</ui:tabpage>
				</ui:tabpane>		
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="confirm">确定</ui:button>
				<ui:button id="cancel">取消</ui:button>
			</ui:pane>
		</ui:layout>
</body>
</html>