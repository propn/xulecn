<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/OrgStaffSelect.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- 查询Form使用的Dataset定义-->
			<ui:dataset datasetId="queryInfo">
				<ui:field field="queryScope" label="查询范围" dropDown="scopeSelect"></ui:field>
				<ui:field field="partyName" label="员工姓名"></ui:field>
				<ui:field field="staffCode" label="员工号"></ui:field>
			</ui:dataset>
			
			<!-- 员工表格使用的Dataset-->
			<ui:dataset datasetId="staffList" pageIndex="1" pageSize="20" readOnly="false" editable="false" 
				loadDataAction="PartyService" loadDataActionMethod="getStaffsByStaffCondPaginate" autoLoadPage="true" 
				staticDataSource="true" loadAsNeeded="false" paginate="true" async="false">
				<ui:field field="select" label="" visible="true"></ui:field>
				<ui:field field="orgPartyId" label="组织标识" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="组织名称"></ui:field>
				<ui:field field="partyRoleId" label="参与人角色标识" visible="false"></ui:field>
				<ui:field field="partyName" label="员工姓名"></ui:field>
				<ui:field field="staffCode" label="员工号"></ui:field>
				<ui:field field="staffDesc" label="员工描述"></ui:field>
				<ui:field field="officeId" label="办公地点ID" visible="false"></ui:field>
				<ui:field field="officeName" label="办公地点" visible="false"></ui:field>				
				<ui:field field="password" label="密码" visible="false"></ui:field>
				<ui:field field="pwdvalidtype" label="密码有效期类型" visible="false"></ui:field>
				<ui:field field="limitCount" label="登录次数限制" visible="false"></ui:field>
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				<ui:field field="orgPartyId" label="所属组织" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="所属组织" visible="false"></ui:field>
				<ui:field field="channelSegmentId" label="渠道ID" visible="false"></ui:field>
				<ui:field field="partyRoleType" label="参与人角色类型" visible="false"></ui:field>
				<ui:field field="orgManager" label="是否组织管理者" visible="false"></ui:field>
				<ui:field field="createDate" label="创建日期" visible="false"></ui:field>
				<ui:field field="expDate" label="失效日期" visible="false"></ui:field>
				<ui:field field="effDate" label="生效时间" visible="false"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
				<ui:field field="stateDate" label="状态时间" visible="false"></ui:field>
				<!-- 个人表 信息维护-->
				<ui:field field="gender" label="性别" visible="false"></ui:field>
				<ui:field field="birthPlace" label="籍贯" visible="false"></ui:field>
				<ui:field field="birthDate" label="生日" visible="false"></ui:field>
				<ui:field field="maritalStatus" label="婚否" visible="false"></ui:field>
				<ui:field field="skill" label="特长" visible="false"></ui:field>
				<ui:parameter parameterId="condition" type="object" value=""></ui:parameter>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			
			<!-- 查询范围 -->
			<xml id="__scopeSelect">
				<items>
					<item label="本组织" value="0" />
					<item label="本组织及下属组织" value="1" />
				</items>
			</xml>
			<code id="scopeSelect"></code>
		</div>
		
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="left" style="width:200px">
					<ui:panel type="titleList" desc="组织名称">
						<ui:content>
							<ZTESOFT:treelist id="orgTreeView" class="treelist" onItemClick="clickOrganization()" showImage="false" showBorder="false" contBorder="false" height="400" showImage=false showHead="false">
								<ZTESOFT:columns>
									<ZTESOFT:column width="100%" display="true" displayText="组织名称" propertyName="orgName" />
									<ZTESOFT:column width="" display="false" displayText="组织类型标识" propertyName="orgTypeId" />											
									<ZTESOFT:column width="" display="false" displayText="组织标识" propertyName="partyId" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="top" style="height:70px">
							<!-- 显示查询条件-->
							<ui:panel type="titleList" desc="查询条件">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:form dataset="queryInfo" inputLayout="20%" labelLayout="13%"></ui:form>
										</ui:pane>
										<ui:pane position="right">
											<ui:button id="doQuery">查询</ui:button>
											<ui:button id="doReset">重置</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:content>
							</ui:panel>
						</ui:pane>
						<ui:pane position="center">
							<!-- 显示人员列表-->
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:table dataset="staffList" skipRebuild="false" readOnly="true" showHeader="true" showIndicator="true" highlightSelection="true" maxRow="1000"></ui:table>
								</ui:pane>
								<ui:pane position="bottom">
									<div class="customerpilot" extra="customerpilot" id="staffPilot" dataset="staffList"></div>
								</ui:pane>
							</ui:layout>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="confirm">确定</ui:button>
							<ui:button id="cancel">取消</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
