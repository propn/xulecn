<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,calendar,validator">
		<title>��ԱȨ��</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/StaffPrivilegeDialog.js"></script>
	</head>
	<body>
		<ui:dataset datasetId="dateInfor" staticDataSource="true">
			<ui:field field="effDate" type="date" label="��Ч����" defaultValue="today" required="true" validType="require" validMsg="��ѡ����Ч����!"></ui:field>
			<ui:field field="expDate" type="date" label="ʧЧ����" required="true" validType="require" validMsg="��ѡ��ʧЧ����!"></ui:field>
		</ui:dataset>

		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="��ԱȨ��">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="top" style="height:255px">
							<ui:layout type="border">
								<ui:pane position="top" style="height:55px">
									<ui:panel type="titleList" desc="��Ч��">
										<ui:content>
											<ui:form dataset="dateInfor" id="dateInforForm" inputLayout="30%" labelLayout="13%"></ui:form>
										</ui:content>
									</ui:panel>
								</ui:pane>
								<ui:pane position="center">
									<ZTESOFT:treelist id="privilegeTreeView" onItemClick="clickPriv()" onItemChecked="itemChecked()" showCheck="true" class="treelist" showImage="false" showBorder="false" contBorder="true" showImage=false height="230" showHead=true
										checkParent="false" checkChildren="false">
										<ZTESOFT:columns>
											<ZTESOFT:column width="34%" display="true" displayText="Ȩ������" propertyName="privName" />
											<ZTESOFT:column width="33%" display="true" displayText="Ȩ������" propertyName="privDesc" />
											<ZTESOFT:column width="10%" display="true" displayText="Ȩ������" propertyName="privTypeName" />
											<ZTESOFT:column width="13%" display="true" displayText="�Ƿ��������" propertyName="ifRegionRelaName" />
											<ZTESOFT:column width="10%" display="false" displayText="Ȩ�ޱ���" propertyName="privCode" />											
											<ZTESOFT:column width="" display="false" displayText="ifRegionRela" propertyName="ifRegionRela" />
											<ZTESOFT:column width="" display="false" displayText="parentPrgId" propertyName="parentPrivilegeId" />
											<ZTESOFT:column width="" display="false" displayText="Ȩ�ޱ�ʶ" propertyName="privId" />
											<ZTESOFT:column width="" display="false" displayText="privilegeType" propertyName="privType" />
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ui:pane>
							</ui:layout>
						</ui:pane>
						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="top" >
									<ui:layout type="border">
										<ui:pane position="bottom" style="height:50px">
											<ui:panel type="titleList" desc="��Ч����Χ">
												<ui:content>
													<div align="left">
														������������:
														<select id="regionType" onchange="changeRegion()">
															<option value="999">
																��ѡ��һ��������������
															</option>
															<option value="0">
																�Ʒ��ߵĵ���
															</option>
															<option value="1">
																��Դ�ߵ���
															</option>
															<option value="3">
																Ӫ���ߵ���֯
															</option>
														</select>
													</div>
												</ui:content>
											</ui:panel>
										</ui:pane>
									</ui:layout>
								</ui:pane>
								<ui:pane position="center">
									<!-- ��������-->
									<div id="divRegionTree">
										<ZTESOFT:treelist id="regionTreeView" checkChildren="false" onItemClick="clickRegion()" onItemChecked="regionChecked()" class="treelist" showImage="false" showCheck="true" showBorder="true" contBorder="true" showImage=false width="100%"
											height="300" showHead=true>
											<ZTESOFT:columns>
												<ZTESOFT:column width="43%" display="true" displayText="��������" propertyName="regionName" />
												<ZTESOFT:column width="13%" display="true" displayText="�������" propertyName="regionCode" />
												<ZTESOFT:column width="43%" display="true" displayText="��������" propertyName="regionDesc" />
												<ZTESOFT:column width="" display="false" displayText="�ϼ������ʶ" propertyName="parentRegionId" />
												<ZTESOFT:column width="" display="false" displayText="���򼶱�" propertyName="regionLevel" />
												<ZTESOFT:column width="" display="false" displayText="�����ʶ" propertyName="regionId" />
											</ZTESOFT:columns>
										</ZTESOFT:treelist>
									</div>
									<div id="divOrganizationTree" style="display:none">
										<ZTESOFT:treelist id="organizationTreeView" onItemClick="clickOrganization()" checkChildren="false" onItemChecked="organizationChecked()" class="treelist" showImage="false" showCheck="true" showBorder="true" contBorder="true" showImage=false
											width="100%" height="300" showHead=true>
											<ZTESOFT:columns>
												<ZTESOFT:column width="43%" display="true" displayText="��֯����" propertyName="orgName" />
												<ZTESOFT:column width="13%" display="true" displayText="��֯����" propertyName="orgCode" />
												<ZTESOFT:column width="43%" display="true" displayText="��֯���" propertyName="orgContent" />
												<ZTESOFT:column width="" display="false" displayText="�����˱�ʶ" propertyName="partyId" />
												<ZTESOFT:column width="" display="false" displayText="�ϼ���֯" propertyName="parentPartyId" />
												<ZTESOFT:column width="" display="false" displayText="��֯����" propertyName="orgLevel" />
												<ZTESOFT:column width="" display="false" displayText="��֯���ͱ�ʶ" propertyName="orgTypeId" />
												<ZTESOFT:column width="" display="false" displayText="��ַ��ʶ" propertyName="addressId" />
												<ZTESOFT:column width="" display="false" displayText="״̬" propertyName="state" />
												<ZTESOFT:column width="" display="false" displayText="״̬ʱ��" propertyName="stateDate" />
												<ZTESOFT:column width="" display="false" displayText="��֯����" propertyName="orgClass" />
											</ZTESOFT:columns>
										</ZTESOFT:treelist>
									</div>
								</ui:pane>
							</ui:layout>
						</ui:pane>
						<ui:pane position="bottom">
							<div id="divButtom">
								<ui:button id="commit">ȷ��</ui:button>
								<ui:button id="cancel">ȡ��</ui:button>
							</div>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</div>
	</body>
</html>
