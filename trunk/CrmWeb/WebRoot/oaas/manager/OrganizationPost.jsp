<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/OrganizationPost.js"></script>
		<script>
		  function table_postList_onclick(){
		  		postTableClick();
		  }
		</script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- 查询Form使用的Dataset定义-->
			<ui:dataset datasetId="queryInfo">
				<ui:field field="postName" label="岗位名称"></ui:field>
				<ui:field field="state" label="状态" dropDown="stateSelect"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="postInfo" staticDataSource="true" readOnly="true">
				<ui:field field="orgPostId" label="组织岗位标识" visible="false"></ui:field>
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				<ui:field field="stateDate" label="状态时间" type="date" visible="false"></ui:field>
				<ui:field field="positionId" label="岗位名称" dropDown="orgPositionDropdown" required="true" validType="require" validMsg="请选择岗位名称!"></ui:field>
				<ui:field field="state" label="状态" dropDown="stateSelect" required="true" validType="require" validMsg="请选择状态!"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="postList" loadDataAction="PartyService" loadDataActionMethod="getPositionListByPartyId" staticDataSource="true" async="false">
				<ui:field field="orgPostId" label="组织岗位标识" visible="false"></ui:field>
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				<ui:field field="positionId" label="岗位标识" visible="true"></ui:field>
				<ui:field field="positionName" label="岗位名称" visible="true"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
				<ui:field field="stateDate" label="状态时间" visible="false"></ui:field>
			</ui:dataset>
			<!-- 岗位角色列表Dataset定义-->
			<ui:dataset datasetId="postRolesList" loadDataAction="PartyService" loadDataActionMethod="getRolesByOrgPost" staticDataSource="true">
				<ui:field field="orgPostRoleId" label="组织岗位角色标识" visible="false"></ui:field>
				<ui:field field="roleId" label="角色标识" visible="false"></ui:field>
				<ui:field field="roleName" label="角色名称"></ui:field>
				<ui:field field="roleDesc" label="角色描述"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
				<ui:field field="effDate" label="生效时间" visible="true" visible="false"></ui:field>
				<ui:field field="abilityGradeNum" label="abilityGradeNum" visible="false"></ui:field>
				<ui:field field="regionId" label="区域标识" visible="false"></ui:field>
				<ui:field field="regionName" label="区域名称" visible="true"></ui:field>
				<ui:field field="regionType" label="区域类型" visible="true" dropDown="regionTypeSelect"></ui:field>
			</ui:dataset>
			<!-- 员工列表Dataset定义-->
			<ui:dataset datasetId="staffList" pageIndex="1" pageSize="12" readOnly="true" 
			loadDataAction="PartyService" loadDataActionMethod="getStaffListByPartyAndPost" autoLoadPage="true" 
			staticDataSource="true" loadAsNeeded="false" paginate="true" async="true">
				<ui:field field="orgPartyName" label="组织名称"></ui:field>
				<ui:field field="staffCode" label="员工号"></ui:field>
				<ui:field field="staffDesc" label="员工描述"></ui:field>
				<ui:parameter parameterId="partyId" type="string"></ui:parameter>
				<ui:parameter parameterId="positionId" type="string"></ui:parameter>
			</ui:dataset>

		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="orgPositionDropdown" attrCode="DC_POST_TYPE" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="stateSelect" attrCode="COMM_RECORD_STATE" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="regionTypeSelect" attrCode="REGION_TYPE" staticDataSource="false"></ui:dropdown>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="查询条件">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="doQuery" dataset="queryInfo" labelLayout="22%" inputLayout="27%"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:120px;">
									<ui:button id="doQuery">查询</ui:button>
									<ui:button id="doQueryAll">全部</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="left" style="width:220px">

					<ui:panel type="titleList" desc="组织名称">
						<ui:content>
							<!-- 显示组织树-->
							<ZTESOFT:treelist id="orgTreeView" class="treelist" onItemClick="clickOrganization()" showImage="false" showBorder="false" contBorder="false" height="400" showImage=false showHead="false">
								<ZTESOFT:columns>
									<ZTESOFT:column width="100%" display="true" displayText="组织名称" propertyName="orgName" />
									<ZTESOFT:column width="" display="false" displayText="组织标识" propertyName="partyId" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
						</ui:content>
					</ui:panel>

				</ui:pane>
				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="top">
							<ui:bar type="search" desc="岗位列表">
								<ui:content>
									<ui:button id="addPost">添加</ui:button>
									<ui:button id="editPost">编辑</ui:button>
									<ui:button id="deletePost">删除</ui:button>
								</ui:content>
							</ui:bar>
						</ui:pane>
						<ui:pane position="center">
							<!-- 显示岗位列表-->
							<ui:table dataset="postList"></ui:table>
						</ui:pane>
						<ui:pane position="bottom" style="height:270px">
							<ui:tabpane id="postPage">
								<ui:tabpage desc="岗位信息">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:form submit="commitPost" dataset="postInfo" id="postInfoForm" labelLayout="16%" inputLayout="33%"></ui:form>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="commitPost">确定</ui:button>
											<ui:button id="cancelCommit">取消</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<ui:tabpage desc="岗位角色">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="postRolesList"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addRole">增加</ui:button>
											<ui:button id="deleteRole">删除</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<ui:tabpage desc="岗位员工列表">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="staffList"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<div class="customerpilot" extra="customerpilot" id="staffPilot" dataset="staffList"></div>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
							</ui:tabpane>
						</ui:pane>
					</ui:layout>
				</ui:pane>
			</ui:layout>
		</div>
	</body>

</html>
