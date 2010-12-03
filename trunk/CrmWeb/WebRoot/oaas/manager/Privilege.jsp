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
				<ui:field field="privId" label="权限编码" visible="false"></ui:field>
				<ui:field field="parentPrivilegeName" label="上级权限"></ui:field>
				<ui:field field="parentPrgId" label="父权限ID" visible="false"></ui:field>
				<ui:field field="privName" label="权限名称" required="true" validType="require" validMsg="请输入权限名称!"></ui:field>
				<ui:field field="privCode" label="权限编码" required="true" validType="limit" minLen="1" maxLen="6" validMsg="请输入长度为1到6的权限编码!"></ui:field>
				<ui:field field="privType" label="使用类型" attrCode="PRIVILEGE_TYPE"></ui:field>
				<ui:field field="privDesc" label="权限描述" inputLayout="56%" validType="require" required="true" validMsg="请输入权限描述信息!"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="dataPrivilegeRuleList" readOnly="true" loadDataAction="PrivilegeService" loadDataActionMethod="getSimpleDataPrivilegeRule" staticDataSource="false">
				<ui:field field="privType" label="privType" visible="true"></ui:field>
				<ui:field field="getValMode" label="getValMode" visible="true"></ui:field>
				<ui:field field="getValSql" label="getValSql" visible="true"></ui:field>
				<ui:field field="transSql" label="transSql" visible="true"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="privNameInfo">
				<ui:field field="privName" label="权限名称"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="权限管理">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="btn_queryPrivilege" dataset="privNameInfo" id="privNameInfoForm" labelLayout="75%" inputLayout="25%"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:300px">
									<ui:button id="btn_queryPrivilege">查询</ui:button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<ui:button id="addRootPrivilege">添加根权限</ui:button>
									<ui:button id="addSubPrivilege">添加子权限</ui:button>
									<ui:button id="editPrivilege">编辑</ui:button>
									<ui:button id="deletePrivilege">删除</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="privilegeTreeView" class="treelist" onItemClick="clickPrivilege()" showImage="false" showBorder="false" contBorder="false" showImage="false" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="43%" display="true" displayText="权限名称" propertyName="privName" />
							<ZTESOFT:column width="43%" display="true" displayText="权限描述" propertyName="privDesc" />
							<ZTESOFT:column width="14%" display="true" displayText="权限编码" propertyName="privCode" />
							<ZTESOFT:column width="" display="false" displayText="privilegeType" propertyName="privType" />
							<ZTESOFT:column width="" display="false" displayText="parentPrgId" propertyName="parentPrgId" />
							<ZTESOFT:column width="" display="false" displayText="privilegeId" propertyName="privId" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height:300px;">
					<ui:tabpane id="mainPage">
						<ui:tabpage desc="权限信息">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="btn_commitPrivilege" dataset="privilegeInfo" id="privilegeInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="btn_commitPrivilege">确定</ui:button>
									<ui:button id="btn_cancel">取消</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
						<ui:tabpage desc="权限关联数据" target="dynaFrame" href="BlankDataPrivilegeView.jsp" autoLoad="true">
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
