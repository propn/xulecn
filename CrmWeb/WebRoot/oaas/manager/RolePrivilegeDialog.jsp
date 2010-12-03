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
			<ui:field field="effDate" type="date" label="生效日期" defaultValue="today" required="true" validType="require" validMsg="请选择生效日期!" ></ui:field>
			<ui:field field="expDate" type="date" label="失效日期" required="true" validType="require" validMsg="请选择失效日期!"></ui:field>
		</ui:dataset>
		
				<ui:panel type="modalDialog" desc="角色权限">
					<ui:content>
						<ui:layout type="border">
							<ui:pane position="center">
								<ZTESOFT:treelist id="rolePrivilegeTreeView" onItemClick="clickPrivilege()" onItemChecked="itemChecked()" 
								showCheck="true" class="treelist" showImage="false" showBorder="true" contBorder="true" 
								showImage=false width="100%" height="450" showHead=true checkParent="false" checkChildren="false">
									<ZTESOFT:columns>
										<ZTESOFT:column width="33%" display="true" displayText="权限名称" propertyName="privName" />
										<ZTESOFT:column width="33%" display="true" displayText="权限描述" propertyName="privDesc" />
										<ZTESOFT:column width="33%" display="true" displayText="权限编码" propertyName="privCode" />
										<ZTESOFT:column width="" display="false" displayText="parentPrgId" propertyName="parentPrgId" />
										<ZTESOFT:column width="" display="false" displayText="privilegeId" propertyName="privId" />
										<ZTESOFT:column width="" display="false" displayText="privilegeType" propertyName="privType" />
									</ZTESOFT:columns>
								</ZTESOFT:treelist>
							</ui:pane>
							<ui:pane position="bottom">
								<ui:button id="btn_OK">确定</ui:button>
								<ui:button id="btn_Cancel">取消</ui:button>
							</ui:pane>
						</ui:layout>
					</ui:content>
				</ui:panel>
	</body>
</html>
