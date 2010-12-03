<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList">
		<title>岗位角色</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/OrgRoleDialog.js"></script>
	</head>
	<body>
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="岗位角色">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="top" style="height:200px">
							<ui:layout type="border">
								<ui:pane position="center">
									<ZTESOFT:treelist id="roleTreeView" class="treelist"  contBorder="true" width="100%" height="180" showHead="true">
										<ZTESOFT:columns>
											<ZTESOFT:column width="25%" display="true" displayText="角色名称" propertyName="roleName" />
											<ZTESOFT:column width="25%" display="true" displayText="角色标识" propertyName="roleId" />
											<ZTESOFT:column width="50%" display="true" displayText="角色描述" propertyName="roleDesc" />
											<ZTESOFT:column width="" display="false" displayText="状态" propertyName="state" />
											<ZTESOFT:column width="" display="false" displayText="状态时间" propertyName="stateDate" />
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ui:pane>
							</ui:layout>
						</ui:pane>
						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="top">
									<div align="left">
										管理区域类型:
										<select id="regionType" onchange="changeRegion()">
											<option value="999">
												请选择一个管理区域类型
											</option>
											<option value="0">
												计费线的地域
											</option>
											<option value="1">
												资源线地域
											</option>
											<!-- option value="2">
												营销的地域
											</option-->
											<option value="3">
												营销线的组织
											</option>
										</select>
									</div>
								</ui:pane>
								<ui:pane position="center">
									<!-- 区域树表-->
									<div id="divRegionTree">
										<ZTESOFT:treelist id="regionTreeView" checkChildren="false" onItemClick="clickRegion()" onItemChecked="regionChecked()" class="treelist" showImage="false" showCheck="true" showBorder="true" contBorder="true" showImage=false width="100%"
											height="300" showHead=true>
											<ZTESOFT:columns>
												<ZTESOFT:column width="25%" display="true" displayText="区域名称" propertyName="regionName" />
												<ZTESOFT:column width="25%" display="true" displayText="区域标识" propertyName="regionId" />
												<ZTESOFT:column width="25%" display="true" displayText="区域编码" propertyName="regionCode" />
												<ZTESOFT:column width="25%" display="true" displayText="区域描述" propertyName="regionDesc" />
												<ZTESOFT:column width="" display="false" displayText="上级区域标识" propertyName="parentRegionId" />
												<ZTESOFT:column width="" display="false" displayText="区域级别" propertyName="regionLevel" />
											</ZTESOFT:columns>
										</ZTESOFT:treelist>
									</div>
									<div id="divOrganizationTree" style="display:none">
										<ZTESOFT:treelist id="organizationTreeView" onItemClick="clickOrganization()" checkChildren="false" onItemChecked="organizationChecked()" class="treelist" showImage="false" showCheck="true" showBorder="true" contBorder="true" showImage=false width="100%" height="300"
											showHead=true>
											<ZTESOFT:columns>
												<ZTESOFT:column width="30%" display="true" displayText="组织名称" propertyName="orgName" />
												<ZTESOFT:column width="30%" display="true" displayText="组织编码" propertyName="orgCode" />
												<ZTESOFT:column width="40%" display="true" displayText="组织简介" propertyName="orgContent" />
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
									</div>
								</ui:pane>
							</ui:layout>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="commit">确定</ui:button>
							<ui:button id="cancel">取消</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</div>
	</body>


</html>
