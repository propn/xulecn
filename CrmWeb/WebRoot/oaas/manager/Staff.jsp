<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/Staff.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="staffPrivilegeRegionList" loadDataAction="PartyService" loadDataActionMethod="getStaffPrivilegeRegionInfo" staticDataSource="true">
				<ui:field field="regionId" label="区域标识" visible="false"></ui:field>
				<ui:field field="regionName" label="区域名称" visible="true"></ui:field>
				<ui:field field="regionType" label="区域类型" visible="true" attrCode="REGION_TYPE"></ui:field>								
			</ui:dataset>
			<!-- 员工动态信息列表Dataset定义-->
			<ui:dataset datasetId="partyIdentification" loadDataAction="PartyService" loadDataActionMethod="getPartyIdentificationByPartyId" staticDataSource="true">
				<ui:field field="identId" label="参与人识别信息标识" visible="false"></ui:field>
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				<ui:field field="socialIdType" label="参与人社会标识类型" visible="false"></ui:field>
				<ui:field field="socialId" label="社会标识码"></ui:field>
				<ui:field field="createdDate" label="创建时间"></ui:field>
				<ui:field field="effDate" label="生效时间"></ui:field>
				<ui:field field="expDate" label="失效时间"></ui:field>
			</ui:dataset>
			<!-- 人员角色列表Dataset定义-->
			<ui:dataset datasetId="staffRolesList" loadDataAction="PartyService" loadDataActionMethod="getStaffRolesByPartyRoleId" staticDataSource="true">
				<ui:field field="partyRoleId" label="参与人角色标识" visible="false"></ui:field>
				<ui:field field="roleId" label="角色标识" visible="false"></ui:field>
				<ui:field field="roleName" label="角色名称"></ui:field>
				<ui:field field="regionId" label="区域标识" visible="false"></ui:field>
				<ui:field field="regionName" label="区域名称"></ui:field>
				<ui:field field="regionType" label="区域类型" attrCode="REGION_TYPE"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
				<ui:field field="effDate" label="生效时间" visible="false"></ui:field>
				<ui:field field="expDate" label="失效时间" visible="false"></ui:field>
			</ui:dataset>
			<!-- 人员权限列表Dataset定义-->
			<ui:dataset datasetId="staffPrivilegesList" loadDataAction="PartyService" loadDataActionMethod="getStaffPrivsByPartyRoleId" staticDataSource="true">
				<ui:field field="staffPrivId" label="角色权限标识" visible="false"></ui:field>
				<ui:field field="partyRoleId" label="参与人角色标识" visible="false"></ui:field>
				<ui:field field="privId" label="权限标识" visible="false"></ui:field>
				<ui:field field="privName" label="权限名称"></ui:field>
				<ui:field field="regionId" label="区域标识" visible="false"></ui:field>
				<ui:field field="regionName" label="区域名称"></ui:field>
				<ui:field field="regionType" label="区域类型" attrCode="REGION_TYPE"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
				<ui:field field="effDate" label="生效时间" visible="false"></ui:field>
				<ui:field field="expDate" label="失效时间" visible="false"></ui:field>
				<ui:field field="privType" label="权限类型" visible="true" attrCode="PRIVILEGE_TYPE"></ui:field>				
			</ui:dataset>
			<!-- 人员岗位列表Dataset定义-->
			<ui:dataset datasetId="staffPostsList" loadDataAction="PartyService" 
			loadDataActionMethod="getStaffPostByPartyRoleId" staticDataSource="true">
				<ui:field field="partyRoleId" label="参与人角色标识" visible="false"></ui:field>
				<ui:field field="orgPostId" label="岗位标识" visible="false"></ui:field>
				<ui:field field="orgPostName" label="岗位名称"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
				<ui:field field="effDate" label="生效时间" visible="true"></ui:field>
				<ui:field field="expDate" label="失效时间" visible="true"></ui:field>
			</ui:dataset>
			<!-- 查询Form使用的Dataset定义-->
			<ui:dataset datasetId="queryInfo">
				<ui:field field="queryScope" label="查询范围" dropDown="scopeSelect"></ui:field>
				<ui:field field="partyName" label="员工名称"></ui:field>
				<ui:field field="staffCode" label="员工号"></ui:field>
			</ui:dataset>
			<!-- 员工表格使用的Dataset-->
			<ui:dataset datasetId="staffList" pageIndex="1" pageSize="10" readOnly="false" editable="false" 
			loadDataAction="PartyService" loadDataActionMethod="getStaffsByStaffCondPaginate" 
			autoLoadPage="true" staticDataSource="true" loadAsNeeded="false" paginate="true" async="false">
				<ui:field field="partyRoleId" label="参与人角色标识" visible="false"></ui:field>
				<ui:field field="partyName" label="员工名称"></ui:field>
				<ui:field field="staffCode" label="员工号"></ui:field>
				<ui:field field="staffDesc" label="员工描述"></ui:field>
				<ui:field field="officeId" label="办公地点ID" visible="false"></ui:field>
				<ui:field field="officeName" label="办公地点" visible="false"></ui:field>
				<ui:field field="limitCount" label="登录次数限制"></ui:field>
				<ui:field field="password" label="密码" visible="false"></ui:field>
				<ui:field field="pwdvalidtype" label="密码有效期类型" visible="false"></ui:field>
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				
				<ui:field field="lanId" label="本地网"  attrCode="DC_LAN_CODE"></ui:field>
				<ui:field field="orgPartyId" label="所属组织" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="所属组织" visible="true"></ui:field>
				<ui:field field="devUserBelong" label="devUserBelong" visible="false"></ui:field>
				<ui:field field="devUserBelongName" label="devUserBelongName" visible="false"></ui:field>
				<ui:field field="channelSegmentId" label="渠道" visible="true" attrCode="DC_CHANNEL_SEGMENT"></ui:field>
				<ui:field field="partyRoleType" label="参与人角色类型" visible="false"></ui:field>
				<ui:field field="orgManager" label="是否组织管理者" visible="false"></ui:field>
				<ui:field field="createDate" label="创建日期" visible="false"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
				<ui:field field="effDate" label="生效时间" visible="false"></ui:field>
				<ui:field field="expDate" label="失效日期" visible="false"></ui:field>
				<ui:field field="stateDate" label="状态时间" visible="false"></ui:field>
				
				<ui:field field="businessId" label="营业区" attrCode="DC_BUSINESS" ></ui:field>
				<ui:field field="countyType" label="城乡标志" dropDown="countyType"></ui:field>
				
				<!-- 个人表 信息维护-->
				<ui:field field="gender" label="性别" visible="false"></ui:field>
				<ui:field field="birthPlace" label="籍贯"></ui:field>
				<ui:field field="birthDate" label="生日" visible="false"></ui:field>
				<ui:field field="maritalStatus" label="婚否" visible="false"></ui:field>
				<ui:field field="devOrgId" label="归属代理商班组ID" visible="false"></ui:field>
				<ui:field field="devOrgName" label="归属代理商班组" popup="true" visible="true"></ui:field>
				<ui:field field="skill" label="特长" visible="false"></ui:field>
				<ui:parameter parameterId="condition" type="object" value=""></ui:parameter>
			</ui:dataset>
			<!-- 增加,编辑员工信息的Form使用的Dataset定义-->
			<!-- staffInfor.orgManager, -->
			<ui:dataset datasetId="staffInfor" readOnly="true">
				<ui:field field="partyRoleId" label="参与人角色标识" visible="false"></ui:field>
				<ui:field field="partyName" label="员工名称" size="50" required="true" required="true" validType="require" validMsg="请输入员工名称!"></ui:field>
				<ui:field field="staffCode" label="员工号" size="9" popup="true" required="true" validType="require" validMsg="请输入工号!"></ui:field>
				<ui:field field="staffDesc" label="员工描述" size="100"></ui:field>
				<ui:field field="officeId" label="办公地点ID" visible="false"></ui:field>
				<ui:field field="officeName" label="办公地点" visible="true" popup="true"></ui:field>
				<ui:field field="limitCount" label="登录次数限制" size="6" validType="number" validMsg="请输入数字类型的登陆次数限制!"></ui:field>
				<ui:field field="password" label="密码" password="true"></ui:field>
				<ui:field field="pwdvalidtype" label="密码有效期" attrCode="PWD_VALID_TYPE" visible="false"></ui:field>
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				
				

				<ui:field field="lanId" label="本地网"  attrCode="DC_LAN_CODE" visible="true" required="true" validType="require"></ui:field>
				<ui:field field="orgPartyId" label="所属组织ID" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="所属部门" popup="true" required="true"></ui:field>
				<ui:field field="devUserBelong" label="devUserBelong" visible="false"></ui:field>
				<ui:field field="devUserBelongName" label="发展用户归属" popup="true"></ui:field>
				<ui:field field="channelSegmentId" label="渠道" required="true" validType="require" validMsg="请选择渠道!" visible="true" attrCode="DC_CHANNEL_SEGMENT"></ui:field>
				<ui:field field="partyRoleType" label="角色类型" visible="false" attrCode="PARTY_ROLE_TYPE"></ui:field>
				<ui:field field="orgManager" label="是否管理者" required="true" validType="require" validMsg="请选择是否管理者!" attrCode="IS_ORG_MANAGER"></ui:field>
				<ui:field field="createDate" label="创建日期" type="date" visible="false"></ui:field>
				<ui:field field="state" label="状态" required="true" validType="require" validMsg="请选择状态!" attrCode="COMM_RECORD_STATE"></ui:field>
				<ui:field field="effDate" label="生效时间" type="date" required="true" validType="require" validMsg="请选择生效时间!"></ui:field>
				<ui:field field="expDate" label="失效日期" type="date" required="true" validType="require" validMsg="请选择失效日期!"></ui:field>
				<ui:field field="stateDate" label="状态时间" type="date" visible="false"></ui:field>				
				<ui:field field="businessId" label="营业区" attrCode="DC_BUSINESS_CODE" required="true" validType="require" validMsg="请选择营业区!"></ui:field>
				<ui:field field="countyType" label="城乡标志" dropDown="countyType" required="true" validType="require" validMsg="请选择城乡标志!"></ui:field>
				<!-- 个人表INDIVIDUAL 信息维护-->
				<ui:field field="gender" label="性别" attrCode="SEX" required="true" validType="require" validMsg="请选择性别!"></ui:field>
				<ui:field field="birthPlace" label="籍贯" size="10" required="false" validType="require" validMsg="请输入籍贯!"></ui:field>
				<ui:field field="birthDate" label="生日" type="date" required="false" validType="require" validMsg="请选择生日!"></ui:field>
				<ui:field field="maritalStatus" label="婚否" attrCode="MARITAL_STATUS" required="false" validType="require" validMsg="请婚姻状态!"></ui:field>
				<ui:field field="devOrgId" label="归属代理商班组ID" visible="false"></ui:field>
				<ui:field field="devOrgName" label="归属代理商班组" visible="true"></ui:field>
				<ui:field field="skill" label="特长" size="100" required="false" validType="require" validMsg="请输入特长!"></ui:field>
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
			
			<!-- 城乡标志 -->
			<xml id="__countyType">
			<items>
			<item label="城市" value="0" />
			<item label="乡村" value="1" />
			</items>
			</xml>
			<code id="countyType"></code>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="查询条件">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="doQuery" dataset="queryInfo" labelLayout="15%" inputLayout="17%"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:120px;">
									<ui:button id="doQuery">查询</ui:button>
									<ui:button id="doReset">重置</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="left" style="width:280px">
<ui:layout type="border">
	<ui:pane position="center">
					<ui:panel type="titleList" desc="组织名称">
						<ui:content>
							<ZTESOFT:treelist id="orgTreeView" class="treelist" onItemClick="clickOrganization()" showImage="false" showBorder="false" contBorder="false" height="400" showImage=false showHead="false">
								<ZTESOFT:columns>
									<ZTESOFT:column width="100%" display="true" displayText="组织名称" propertyName="orgName" />
									<ZTESOFT:column width="" display="false" displayText="组织编码" propertyName="orgCode" />
									<ZTESOFT:column width="" display="false" displayText="组织类型标识" propertyName="orgTypeId" />
									<ZTESOFT:column width="" display="false" displayText="组织标识" propertyName="partyId" />
									<ZTESOFT:column width="" display="false" displayText="state" propertyName="state" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
						</ui:content>
					</ui:panel>
	</ui:pane>
</ui:layout>
				</ui:pane>
				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="top">

									<ui:bar type="search" desc="人员列表">
										<ui:content>
											<ui:button id="addStaff">添加</ui:button>
											<ui:button id="addBatchStaff">批量添加</ui:button>
											<ui:button id="addCopyStaff">复制人员</ui:button>
											<ui:button id="editStaff">编辑</ui:button>
											<ui:button id="deleteStaff">注销</ui:button>
										</ui:content>
									</ui:bar>
								</ui:pane>
								<ui:pane position="center">
									<!-- 显示人员列表-->
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="staffList"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<div class="customerpilot" extra="customerpilot" id="staffPilot" dataset="staffList"></div>
										</ui:pane>
									</ui:layout>
								</ui:pane>
							</ui:layout>
						</ui:pane>

						<ui:pane position="bottom" style="height:350px">
							<ui:tabpane id="staffInfoPage">
								<ui:tabpage desc="人员信息">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:form submit="commitStaff" dataset="staffInfor" id="staffInforForm" labelLayout="18%" inputLayout="31%"></ui:form>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="commitStaff">确定</ui:button>
											<ui:button id="cancelCommit">取消</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<ui:tabpage desc="动态信息">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="partyIdentification" id="partyIdentificationTable"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addIdentification">增加</ui:button>
											<ui:button id="editIdentification">修改</ui:button>
											<ui:button id="deleteIdentification">删除</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>

								<ui:tabpage desc="人员角色">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="staffRolesList" id="staffRolesListTable"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addRole">角色分配</ui:button>
											<ui:button id="deleteRole">删除</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<ui:tabpage desc="人员权限">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:layout type="border">
												<ui:pane position="center">
													<ui:panel type="titleList" desc="人员权限">
														<ui:content>
															<ZTESOFT:treelist id="staffPrivilegeTreeView" class="treelist" showBorder="false" 
															onItemClick="clickStaffPrivilege()" contBorder="false" showHead="true">
																<ZTESOFT:columns>
																	<ZTESOFT:column width="75%" display="true" displayText="权限名称" propertyName="privName" />
																	<ZTESOFT:column width="25%" display="true" displayText="权限类型" propertyName="privType" />													
																	<ZTESOFT:column width="" display="false" displayText="区域名称" propertyName="regionName" />
																	<ZTESOFT:column width="" display="false" displayText="区域类型" propertyName="regionType" />
																	<ZTESOFT:column width="" display="" displayText="pathCode" propertyName="pathCode" />													
																	<ZTESOFT:column width="" display="false" displayText="parentPrgId" propertyName="parentPrgId" />
																	<ZTESOFT:column width="" display="false" displayText="privilegeId" propertyName="privId" />
																</ZTESOFT:columns>
															</ZTESOFT:treelist>
														</ui:content>
													</ui:panel>
												</ui:pane>
												<ui:pane position="right" style="width:280px">
													<ui:panel type="titleList" desc="权限区域范围">
														<ui:content>
															<ui:table dataset="staffPrivilegeRegionList"></ui:table>
														</ui:content>
													</ui:panel>
												</ui:pane>
											</ui:layout>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addPrivilege">权限分配</ui:button>
											<ui:button id="deletePrivilege">删除</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<!--
								<u i:tabpage desc="人员岗位">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="staffPostsList" id="staffPostsListTable"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addPost">岗位分配</ui:button>
											<ui:button id="editPost">编辑</ui:button>
											<ui:button id="deletePost">删除</ui:button>
										</ui:pane>
									</ui:layout>
								</u i:tabpage>
								-->
							</ui:tabpane>
						</ui:pane>
					</ui:layout>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
