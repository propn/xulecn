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
				<ui:field field="regionId" label="�����ʶ" visible="false"></ui:field>
				<ui:field field="regionName" label="��������" visible="true"></ui:field>
				<ui:field field="regionType" label="��������" visible="true" attrCode="REGION_TYPE"></ui:field>								
			</ui:dataset>
			<!-- Ա����̬��Ϣ�б�Dataset����-->
			<ui:dataset datasetId="partyIdentification" loadDataAction="PartyService" loadDataActionMethod="getPartyIdentificationByPartyId" staticDataSource="true">
				<ui:field field="identId" label="������ʶ����Ϣ��ʶ" visible="false"></ui:field>
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				<ui:field field="socialIdType" label="����������ʶ����" visible="false"></ui:field>
				<ui:field field="socialId" label="����ʶ��"></ui:field>
				<ui:field field="createdDate" label="����ʱ��"></ui:field>
				<ui:field field="effDate" label="��Чʱ��"></ui:field>
				<ui:field field="expDate" label="ʧЧʱ��"></ui:field>
			</ui:dataset>
			<!-- ��Ա��ɫ�б�Dataset����-->
			<ui:dataset datasetId="staffRolesList" loadDataAction="PartyService" loadDataActionMethod="getStaffRolesByPartyRoleId" staticDataSource="true">
				<ui:field field="partyRoleId" label="�����˽�ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="roleId" label="��ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="roleName" label="��ɫ����"></ui:field>
				<ui:field field="regionId" label="�����ʶ" visible="false"></ui:field>
				<ui:field field="regionName" label="��������"></ui:field>
				<ui:field field="regionType" label="��������" attrCode="REGION_TYPE"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" visible="false"></ui:field>
				<ui:field field="expDate" label="ʧЧʱ��" visible="false"></ui:field>
			</ui:dataset>
			<!-- ��ԱȨ���б�Dataset����-->
			<ui:dataset datasetId="staffPrivilegesList" loadDataAction="PartyService" loadDataActionMethod="getStaffPrivsByPartyRoleId" staticDataSource="true">
				<ui:field field="staffPrivId" label="��ɫȨ�ޱ�ʶ" visible="false"></ui:field>
				<ui:field field="partyRoleId" label="�����˽�ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="privId" label="Ȩ�ޱ�ʶ" visible="false"></ui:field>
				<ui:field field="privName" label="Ȩ������"></ui:field>
				<ui:field field="regionId" label="�����ʶ" visible="false"></ui:field>
				<ui:field field="regionName" label="��������"></ui:field>
				<ui:field field="regionType" label="��������" attrCode="REGION_TYPE"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" visible="false"></ui:field>
				<ui:field field="expDate" label="ʧЧʱ��" visible="false"></ui:field>
				<ui:field field="privType" label="Ȩ������" visible="true" attrCode="PRIVILEGE_TYPE"></ui:field>				
			</ui:dataset>
			<!-- ��Ա��λ�б�Dataset����-->
			<ui:dataset datasetId="staffPostsList" loadDataAction="PartyService" 
			loadDataActionMethod="getStaffPostByPartyRoleId" staticDataSource="true">
				<ui:field field="partyRoleId" label="�����˽�ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="orgPostId" label="��λ��ʶ" visible="false"></ui:field>
				<ui:field field="orgPostName" label="��λ����"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" visible="true"></ui:field>
				<ui:field field="expDate" label="ʧЧʱ��" visible="true"></ui:field>
			</ui:dataset>
			<!-- ��ѯFormʹ�õ�Dataset����-->
			<ui:dataset datasetId="queryInfo">
				<ui:field field="queryScope" label="��ѯ��Χ" dropDown="scopeSelect"></ui:field>
				<ui:field field="partyName" label="Ա������"></ui:field>
				<ui:field field="staffCode" label="Ա����"></ui:field>
			</ui:dataset>
			<!-- Ա�����ʹ�õ�Dataset-->
			<ui:dataset datasetId="staffList" pageIndex="1" pageSize="10" readOnly="false" editable="false" 
			loadDataAction="PartyService" loadDataActionMethod="getStaffsByStaffCondPaginate" 
			autoLoadPage="true" staticDataSource="true" loadAsNeeded="false" paginate="true" async="false">
				<ui:field field="partyRoleId" label="�����˽�ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="partyName" label="Ա������"></ui:field>
				<ui:field field="staffCode" label="Ա����"></ui:field>
				<ui:field field="staffDesc" label="Ա������"></ui:field>
				<ui:field field="officeId" label="�칫�ص�ID" visible="false"></ui:field>
				<ui:field field="officeName" label="�칫�ص�" visible="false"></ui:field>
				<ui:field field="limitCount" label="��¼��������"></ui:field>
				<ui:field field="password" label="����" visible="false"></ui:field>
				<ui:field field="pwdvalidtype" label="������Ч������" visible="false"></ui:field>
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				
				<ui:field field="lanId" label="������"  attrCode="DC_LAN_CODE"></ui:field>
				<ui:field field="orgPartyId" label="������֯" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="������֯" visible="true"></ui:field>
				<ui:field field="devUserBelong" label="devUserBelong" visible="false"></ui:field>
				<ui:field field="devUserBelongName" label="devUserBelongName" visible="false"></ui:field>
				<ui:field field="channelSegmentId" label="����" visible="true" attrCode="DC_CHANNEL_SEGMENT"></ui:field>
				<ui:field field="partyRoleType" label="�����˽�ɫ����" visible="false"></ui:field>
				<ui:field field="orgManager" label="�Ƿ���֯������" visible="false"></ui:field>
				<ui:field field="createDate" label="��������" visible="false"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" visible="false"></ui:field>
				<ui:field field="expDate" label="ʧЧ����" visible="false"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" visible="false"></ui:field>
				
				<ui:field field="businessId" label="Ӫҵ��" attrCode="DC_BUSINESS" ></ui:field>
				<ui:field field="countyType" label="�����־" dropDown="countyType"></ui:field>
				
				<!-- ���˱� ��Ϣά��-->
				<ui:field field="gender" label="�Ա�" visible="false"></ui:field>
				<ui:field field="birthPlace" label="����"></ui:field>
				<ui:field field="birthDate" label="����" visible="false"></ui:field>
				<ui:field field="maritalStatus" label="���" visible="false"></ui:field>
				<ui:field field="devOrgId" label="���������̰���ID" visible="false"></ui:field>
				<ui:field field="devOrgName" label="���������̰���" popup="true" visible="true"></ui:field>
				<ui:field field="skill" label="�س�" visible="false"></ui:field>
				<ui:parameter parameterId="condition" type="object" value=""></ui:parameter>
			</ui:dataset>
			<!-- ����,�༭Ա����Ϣ��Formʹ�õ�Dataset����-->
			<!-- staffInfor.orgManager, -->
			<ui:dataset datasetId="staffInfor" readOnly="true">
				<ui:field field="partyRoleId" label="�����˽�ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="partyName" label="Ա������" size="50" required="true" required="true" validType="require" validMsg="������Ա������!"></ui:field>
				<ui:field field="staffCode" label="Ա����" size="9" popup="true" required="true" validType="require" validMsg="�����빤��!"></ui:field>
				<ui:field field="staffDesc" label="Ա������" size="100"></ui:field>
				<ui:field field="officeId" label="�칫�ص�ID" visible="false"></ui:field>
				<ui:field field="officeName" label="�칫�ص�" visible="true" popup="true"></ui:field>
				<ui:field field="limitCount" label="��¼��������" size="6" validType="number" validMsg="�������������͵ĵ�½��������!"></ui:field>
				<ui:field field="password" label="����" password="true"></ui:field>
				<ui:field field="pwdvalidtype" label="������Ч��" attrCode="PWD_VALID_TYPE" visible="false"></ui:field>
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				
				

				<ui:field field="lanId" label="������"  attrCode="DC_LAN_CODE" visible="true" required="true" validType="require"></ui:field>
				<ui:field field="orgPartyId" label="������֯ID" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="��������" popup="true" required="true"></ui:field>
				<ui:field field="devUserBelong" label="devUserBelong" visible="false"></ui:field>
				<ui:field field="devUserBelongName" label="��չ�û�����" popup="true"></ui:field>
				<ui:field field="channelSegmentId" label="����" required="true" validType="require" validMsg="��ѡ������!" visible="true" attrCode="DC_CHANNEL_SEGMENT"></ui:field>
				<ui:field field="partyRoleType" label="��ɫ����" visible="false" attrCode="PARTY_ROLE_TYPE"></ui:field>
				<ui:field field="orgManager" label="�Ƿ������" required="true" validType="require" validMsg="��ѡ���Ƿ������!" attrCode="IS_ORG_MANAGER"></ui:field>
				<ui:field field="createDate" label="��������" type="date" visible="false"></ui:field>
				<ui:field field="state" label="״̬" required="true" validType="require" validMsg="��ѡ��״̬!" attrCode="COMM_RECORD_STATE"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" type="date" required="true" validType="require" validMsg="��ѡ����Чʱ��!"></ui:field>
				<ui:field field="expDate" label="ʧЧ����" type="date" required="true" validType="require" validMsg="��ѡ��ʧЧ����!"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" type="date" visible="false"></ui:field>				
				<ui:field field="businessId" label="Ӫҵ��" attrCode="DC_BUSINESS_CODE" required="true" validType="require" validMsg="��ѡ��Ӫҵ��!"></ui:field>
				<ui:field field="countyType" label="�����־" dropDown="countyType" required="true" validType="require" validMsg="��ѡ������־!"></ui:field>
				<!-- ���˱�INDIVIDUAL ��Ϣά��-->
				<ui:field field="gender" label="�Ա�" attrCode="SEX" required="true" validType="require" validMsg="��ѡ���Ա�!"></ui:field>
				<ui:field field="birthPlace" label="����" size="10" required="false" validType="require" validMsg="�����뼮��!"></ui:field>
				<ui:field field="birthDate" label="����" type="date" required="false" validType="require" validMsg="��ѡ������!"></ui:field>
				<ui:field field="maritalStatus" label="���" attrCode="MARITAL_STATUS" required="false" validType="require" validMsg="�����״̬!"></ui:field>
				<ui:field field="devOrgId" label="���������̰���ID" visible="false"></ui:field>
				<ui:field field="devOrgName" label="���������̰���" visible="true"></ui:field>
				<ui:field field="skill" label="�س�" size="100" required="false" validType="require" validMsg="�������س�!"></ui:field>
			</ui:dataset>
			
		</div>
		<div id="dropdownDefine">
			<!-- ��ѯ��Χ -->
			<xml id="__scopeSelect">
			<items>
			<item label="����֯" value="0" />
			<item label="����֯��������֯" value="1" />
			</items>
			</xml>
			<code id="scopeSelect"></code>
			
			<!-- �����־ -->
			<xml id="__countyType">
			<items>
			<item label="����" value="0" />
			<item label="���" value="1" />
			</items>
			</xml>
			<code id="countyType"></code>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="��ѯ����">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="doQuery" dataset="queryInfo" labelLayout="15%" inputLayout="17%"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:120px;">
									<ui:button id="doQuery">��ѯ</ui:button>
									<ui:button id="doReset">����</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="left" style="width:280px">
<ui:layout type="border">
	<ui:pane position="center">
					<ui:panel type="titleList" desc="��֯����">
						<ui:content>
							<ZTESOFT:treelist id="orgTreeView" class="treelist" onItemClick="clickOrganization()" showImage="false" showBorder="false" contBorder="false" height="400" showImage=false showHead="false">
								<ZTESOFT:columns>
									<ZTESOFT:column width="100%" display="true" displayText="��֯����" propertyName="orgName" />
									<ZTESOFT:column width="" display="false" displayText="��֯����" propertyName="orgCode" />
									<ZTESOFT:column width="" display="false" displayText="��֯���ͱ�ʶ" propertyName="orgTypeId" />
									<ZTESOFT:column width="" display="false" displayText="��֯��ʶ" propertyName="partyId" />
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

									<ui:bar type="search" desc="��Ա�б�">
										<ui:content>
											<ui:button id="addStaff">���</ui:button>
											<ui:button id="addBatchStaff">�������</ui:button>
											<ui:button id="addCopyStaff">������Ա</ui:button>
											<ui:button id="editStaff">�༭</ui:button>
											<ui:button id="deleteStaff">ע��</ui:button>
										</ui:content>
									</ui:bar>
								</ui:pane>
								<ui:pane position="center">
									<!-- ��ʾ��Ա�б�-->
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
								<ui:tabpage desc="��Ա��Ϣ">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:form submit="commitStaff" dataset="staffInfor" id="staffInforForm" labelLayout="18%" inputLayout="31%"></ui:form>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="commitStaff">ȷ��</ui:button>
											<ui:button id="cancelCommit">ȡ��</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<ui:tabpage desc="��̬��Ϣ">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="partyIdentification" id="partyIdentificationTable"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addIdentification">����</ui:button>
											<ui:button id="editIdentification">�޸�</ui:button>
											<ui:button id="deleteIdentification">ɾ��</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>

								<ui:tabpage desc="��Ա��ɫ">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="staffRolesList" id="staffRolesListTable"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addRole">��ɫ����</ui:button>
											<ui:button id="deleteRole">ɾ��</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<ui:tabpage desc="��ԱȨ��">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:layout type="border">
												<ui:pane position="center">
													<ui:panel type="titleList" desc="��ԱȨ��">
														<ui:content>
															<ZTESOFT:treelist id="staffPrivilegeTreeView" class="treelist" showBorder="false" 
															onItemClick="clickStaffPrivilege()" contBorder="false" showHead="true">
																<ZTESOFT:columns>
																	<ZTESOFT:column width="75%" display="true" displayText="Ȩ������" propertyName="privName" />
																	<ZTESOFT:column width="25%" display="true" displayText="Ȩ������" propertyName="privType" />													
																	<ZTESOFT:column width="" display="false" displayText="��������" propertyName="regionName" />
																	<ZTESOFT:column width="" display="false" displayText="��������" propertyName="regionType" />
																	<ZTESOFT:column width="" display="" displayText="pathCode" propertyName="pathCode" />													
																	<ZTESOFT:column width="" display="false" displayText="parentPrgId" propertyName="parentPrgId" />
																	<ZTESOFT:column width="" display="false" displayText="privilegeId" propertyName="privId" />
																</ZTESOFT:columns>
															</ZTESOFT:treelist>
														</ui:content>
													</ui:panel>
												</ui:pane>
												<ui:pane position="right" style="width:280px">
													<ui:panel type="titleList" desc="Ȩ������Χ">
														<ui:content>
															<ui:table dataset="staffPrivilegeRegionList"></ui:table>
														</ui:content>
													</ui:panel>
												</ui:pane>
											</ui:layout>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addPrivilege">Ȩ�޷���</ui:button>
											<ui:button id="deletePrivilege">ɾ��</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<!--
								<u i:tabpage desc="��Ա��λ">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="staffPostsList" id="staffPostsListTable"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addPost">��λ����</ui:button>
											<ui:button id="editPost">�༭</ui:button>
											<ui:button id="deletePost">ɾ��</ui:button>
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
