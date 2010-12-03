<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,calendar,validator">
		<title>��Ա��ɫ</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/StaffRoleDialog.js"></script>
	</head>
	<body>
		<ui:dataset datasetId="dateInfor" staticDataSource="true">
			<ui:field field="effDate" type="date" label="��Ч����" defaultValue="today" required="true" validType="require" validMsg="��ѡ����Ч����!" ></ui:field>
			<ui:field field="expDate" type="date" label="ʧЧ����" required="true" validType="require" validMsg="��ѡ��ʧЧ����!"></ui:field>
		</ui:dataset>
			
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="��Ա��ɫ">
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
									<ZTESOFT:treelist id="roleTreeView" class="treelist" showBorder="false" contBorder="false" 
									showCheck="true"  showHead="true">
										<ZTESOFT:columns>
											<ZTESOFT:column width="30%" display="true" displayText="��ɫ����" propertyName="roleName" />
											<ZTESOFT:column width="70%" display="true" displayText="��ɫ����" propertyName="roleDesc" />
											<ZTESOFT:column width="" display="false" displayText="��ɫ��ʶ" propertyName="roleId" />											
											<ZTESOFT:column width="" display="false" displayText="״̬" propertyName="state" />
											<ZTESOFT:column width="" display="false" displayText="״̬ʱ��" propertyName="stateDate" />
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ui:pane>
							</ui:layout>
						</ui:pane>
						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="top" style="height:50px">
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
													<!-- option value="2">
														Ӫ���ĵ���
													</option-->
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
										<ZTESOFT:treelist id="regionTreeView" checkChildren="false" onItemClick="clickRegion()" onItemChecked="regionChecked()" class="treelist" showImage="false" showCheck="true" showBorder="false" contBorder="false" showImage=false width="100%"
											 showHead=true>
											<ZTESOFT:columns>
												<ZTESOFT:column width="25%" display="true" displayText="��������" propertyName="regionName" />
												<ZTESOFT:column width="25%" display="true" displayText="�����ʶ" propertyName="regionId" />
												<ZTESOFT:column width="25%" display="true" displayText="�������" propertyName="regionCode" />
												<ZTESOFT:column width="25%" display="true" displayText="��������" propertyName="regionDesc" />
												<ZTESOFT:column width="" display="false" displayText="�ϼ������ʶ" propertyName="parentRegionId" />
												<ZTESOFT:column width="" display="false" displayText="���򼶱�" propertyName="regionLevel" />
											</ZTESOFT:columns>
										</ZTESOFT:treelist>
									</div>
									<div id="divOrganizationTree" style="display:none">
										<ZTESOFT:treelist id="organizationTreeView" onItemClick="clickOrganization()" checkChildren="false" onItemChecked="organizationChecked()" class="treelist" showImage="false" showCheck="true" showBorder="false" contBorder="false" showImage=false
											width="100%"  showHead=true>
											<ZTESOFT:columns>
												<ZTESOFT:column width="30%" display="true" displayText="��֯����" propertyName="orgName" />
												<ZTESOFT:column width="30%" display="true" displayText="��֯����" propertyName="orgCode" />
												<ZTESOFT:column width="40%" display="true" displayText="��֯���" propertyName="orgContent" />
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
							<ui:button id="commit">ȷ��</ui:button>
							<ui:button id="cancel">ȡ��</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</div>
	</body>
</html>
