<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList,validator">
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/Roles.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- 查询Form使用的Dataset定义-->
			<ui:dataset datasetId="queryInfo">
				<ui:field field="roleName" label="角色名称"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="roleInfo" readOnly="true">
				<ui:field field="roleId" label="角色标识" visible="false"></ui:field>
				<ui:field field="roleName" label="角色名称" required="true" validType="require" validMsg="请输入角色名称!"></ui:field>
				<ui:field field="roleNameShort" label="角色名称缩写"></ui:field>				
				<ui:field field="roleDesc" label="角色描述"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
				<ui:field field="stateDate" label="状态时间" type="date" visible="false"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="roleList" loadDataAction="PrivilegeService" loadDataActionMethod="getRelatingRoleByRoleId" staticDataSource="true">
				<ui:field field="roleId" label="角色标识" visible="true"></ui:field>
				<ui:field field="roleName" label="角色名称"></ui:field>
				<ui:field field="roleNameShort" label="角色名称缩写"></ui:field>								
				<ui:field field="roleDesc" label="角色描述"></ui:field>
				<ui:field field="state" label="状态" attrCode="ROLES_STATE" visible="true"></ui:field>
				<ui:field field="stateDate" label="状态时间" visible="true"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="角色查询">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="doQuery" dataset="queryInfo"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:250px;text-align:center;">
									<ui:button id="doQuery">查询</ui:button>
									<ui:button id="doQueryAll">查询全部</ui:button>
									<ui:button id="addRole">添加</ui:button>
									<ui:button id="editRole">编辑</ui:button>
									<ui:button id="deleteRole">删除</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="roleTreeView" class="treelist" onItemClick="clickRole()" showBorder="false" contBorder="true" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="25%" display="true" displayText="角色名称" propertyName="roleName" />
							<ZTESOFT:column width="65%" display="true" displayText="角色描述" propertyName="roleDesc" />
							<ZTESOFT:column width="10%" display="true" displayText="缩写" propertyName="roleNameShort" />
							<ZTESOFT:column width="" display="false" displayText="角色标识" propertyName="roleId" />							
							<ZTESOFT:column width="" display="false" displayText="状态" propertyName="state" />
							<ZTESOFT:column width="" display="false" displayText="状态时间" propertyName="stateDate" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height: 250px">
					<!-- 标签页组件-->
					<ui:tabpane id="rolePage">
						<ui:tabpage desc="角色信息">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="btn_commitRole" dataset="roleInfo" id="roleInfoForm" labelLayout="16%" inputLayout="33%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="btn_commitRole">确定</ui:button>
									<ui:button id="btn_cancelRole">取消</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
						<ui:tabpage desc="角色权限">
							<ui:layout type="border">
								<ui:pane position="center">
									<ZTESOFT:treelist id="privilegeTreeView" class="treelist" showBorder="true" contBorder="true" height="190px" showHead="true">
										<ZTESOFT:columns>
											<ZTESOFT:column width="33%" display="true" displayText="权限名称" propertyName="privName" />
											<ZTESOFT:column width="33%" display="true" displayText="权限描述" propertyName="privDesc" />
											<ZTESOFT:column width="34%" display="true" displayText="权限编码" propertyName="privCode" />
											<ZTESOFT:column width="" display="false" displayText="parentPrgId" propertyName="parentPrgId" />
											<ZTESOFT:column width="" display="false" displayText="privilegeId" propertyName="privId" />
											<ZTESOFT:column width="" display="false" displayText="privilegeType" propertyName="privType" />
											<ZTESOFT:column width="" display="false" displayText="pathCode" propertyName="pathCode" />
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="addRolePrivilege">添加权限</ui:button>
									<ui:button id="deleteRolePrivilege">删除权限</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
						<ui:tabpage desc="关联角色">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:table dataset="roleList"></ui:table>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="relatingRole">关联角色</ui:button>
									<ui:button id="deleteRelation">删除关联</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
