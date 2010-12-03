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
							<ZTESOFT:column width="40%" display="true" displayText="组织名称" propertyName="orgName" />
							<ZTESOFT:column width="20%" display="true" displayText="组织编码" propertyName="orgCode" />
							<ZTESOFT:column width="40%" display="true" displayText="组织简介" propertyName="orgContent" />
							<ZTESOFT:column width="" display="false" displayText="权限标志" propertyName="privilegeFlag" />							
							<ZTESOFT:column width="" display="false" displayText="参与人标识" propertyName="partyId" />
							<ZTESOFT:column width="" display="false" displayText="上级组织" propertyName="parentPartyId" />
							<ZTESOFT:column width="" display="false" displayText="组织级别" propertyName="orgLevel" />
							<ZTESOFT:column width="" display="false" displayText="组织类型标识" propertyName="orgTypeId" />
							<ZTESOFT:column width="" display="false" displayText="地址标识" propertyName="addressId" />
							<ZTESOFT:column width="" display="false" displayText="状态" propertyName="state" />
							<ZTESOFT:column width="" display="false" displayText="状态时间" propertyName="stateDate" />
							<ZTESOFT:column width="" display="false" displayText="组织分类" propertyName="orgClass" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_Confirm">确定</ui:button>
					<ui:button id="btn_Cancel">取消</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
