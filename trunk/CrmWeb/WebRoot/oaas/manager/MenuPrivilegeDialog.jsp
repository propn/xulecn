<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList">
		<title>选择菜单权限</title>
		<script language="javascript" src="js/PrivilegeDialog.js"></script>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
	</head>
	<body>
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="权限列表">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
							<ZTESOFT:treelist id="privilegeTreeView" onItemClick="clickPrivilege()" class="treelist" showImage="false" showBorder="true" contBorder="true" showImage=false width="100%" height="450" showHead=true>
								<ZTESOFT:columns>
									<ZTESOFT:column width="30%" display="true" displayText="PrivilegeName" propertyName="privName" />
									<ZTESOFT:column width="30%" display="true" displayText="权限描述" propertyName="privDesc" />
									<ZTESOFT:column width="30%" display="true" displayText="权限编码" propertyName="privCode" />
									<ZTESOFT:column width="" display="false" displayText="privilegeId" propertyName="privId" />
									<ZTESOFT:column width="" display="false" displayText="parentPrgId" propertyName="parentPrgId" />
									<ZTESOFT:column width="" display="false" displayText="privilegeType" propertyName="privType" />
									<ZTESOFT:column width="" display="false" displayText="pathCode" propertyName="pathCode" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="btn_AddPrivilege">新建权限</ui:button>
							<ui:button id="btn_OK">确定</ui:button>
							<ui:button id="btn_Cancel">取消</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</div>
	</body>
</html>
