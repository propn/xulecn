<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,validator,calendar">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<script language="javascript" src="js/Partner.js"></script>
		<ui:import library=""></ui:import>
	</head>
	<body>

		<div id="datasetDefine">
			<ui:dataset datasetId="queryInfo" loadDataAction="" loadDataActionMethod="" staticDataSource="true">
				<ui:field field="partnerCode" label="����" visible="true"></ui:field>
				<ui:field field="partnerType" label="����" blankValue="true" attrCode="PARTNER_TYPE" visible="true"></ui:field>
				<ui:field field="cooperateType" label="������ʽ" blankValue="true" attrCode="COOPERATE_TYPE" visible="true"></ui:field>
				<ui:field field="partnerGrade" label="����" blankValue="true" attrCode="PARTNER_GRADE" visible="true"></ui:field>
				<ui:field field="orgName" label="������" popup="true" visible="true"></ui:field>
				<ui:field field="orgId" label="������ID" visible="false"></ui:field>
				<ui:field field="staffName" label="������" popup="true" visible="true"></ui:field>
				<ui:field field="staffId" label="������ID" visible="false"></ui:field>
			</ui:dataset>

			<!-- ���������ϸ��Ϣ -->
			<ui:dataset datasetId="partnerInfo" readOnly="true" staticDataSource="true">
				<ui:field field="partnerId" label="��������ʶ" visible="false"></ui:field>
				<ui:field field="partyRoleId" label="�����˽�ɫ" visible="true"></ui:field>
				<ui:field field="partnerCode" label="����������" required="true" validType="require" validMsg="���������������" visible="true"></ui:field>
				<ui:field field="partnerType" label="�����������" required="true" validType="require" validMsg="��ѡ������������" attrCode="PARTNER_TYPE" blankValue="true" visible="true"></ui:field>
				<ui:field field="partnerDesc" label="�����������" required="true" validType="require" validMsg="����������������" visible="true"></ui:field>
				<ui:field field="corpAgent" label="���˴���" visible="true"></ui:field>
				<ui:field field="addr" label="��ַ��Ϣ" visible="true"></ui:field>
				<ui:field field="applyDate" label="��������" type="date" visible="true"></ui:field>
				<ui:field field="linkman" label="��ϵ��" visible="true"></ui:field>
				<ui:field field="corporateLicenceNo" label="Ӫҵִ��" visible="true"></ui:field>
				<ui:field field="spcpLicenceNo" label="��Ϣ����Ӫҵִ��" visible="true"></ui:field>
				<ui:field field="bankPermit" label="���п������֤" visible="true"></ui:field>
				<ui:field field="companySize" label="��ҵ��ģ" attrCode="COMPANY_SIZE" blankValue="true" visible="true"></ui:field>
				<ui:field field="companyBalns" label="�ʽ��ģ" attrCode="COMPANY_BALANCE" blankValue="true" visible="true"></ui:field>
				<ui:field field="cooperateType" label="������ʽ" attrCode="COOPERATE_TYPE" blankValue="true" visible="true"></ui:field>
				<ui:field field="devPlan" label="��չ�ƻ�" visible="true"></ui:field>
				<ui:field field="partnerGrade" label="����" attrCode="PARTNER_GRADE" visible="true"></ui:field>
				<ui:field field="state" label="״̬" required="true" attrCode="PARTNER_STATE" blankValue="true" visible="true" validType="require" validMsg="��ѡ��״̬"></ui:field>
				<ui:field field="orgId" label="������ID" visible="false"></ui:field>
				<ui:field field="orgName" label="������" popup="true" required="true" visible="true" validType="require" validMsg="��ѡ�������"></ui:field>
				<ui:field field="staffId" label="������ID" visible="false"></ui:field>
				<ui:field field="staffName" label="������" popup="true" required="true" validType="require" validMsg="��ѡ������" visible="true"></ui:field>
				<ui:field field="superPartnerId" label="�ϼ��������" visible="false"></ui:field>
				<ui:field field="pathCode" label="path code" visible="false"></ui:field>
			</ui:dataset>

			<!-- ��������ͬ�б� -->
			<ui:dataset datasetId="conferList" loadDataAction="PartnerService" loadDataActionMethod="queryConferByPartnerId" staticDataSource="true" async="false">
				<ui:field field="partnerConfId" label="��ͬ��ʶ" visible="false"></ui:field>
				<ui:field field="partnerId" label="��������ʶ" visible="false"></ui:field>
				<ui:field field="conferCode" label="��ͬ���" visible="true"></ui:field>
				<ui:field field="partnerConferType" label="��ͬ����" attrCode="PARTNER_CONFER_TYPE" visible="true"></ui:field>
				<ui:field field="signDate" label="ǩ������" visible="false"></ui:field>
				<ui:field field="expDate" label="��Ч��" visible="false"></ui:field>
				<ui:field field="cooperateType" label="������ʽ" visible="false"></ui:field>
				<ui:field field="conferContence" label="��������" visible="false"></ui:field>
				<ui:field field="balnsRule" label="�������" visible="false"></ui:field>
				<ui:field field="partnerDroitDuty" label="�������Ȩ������" visible="false"></ui:field>
				<ui:field field="droitDuty" label="������Ȩ������" visible="false"></ui:field>
				<ui:field field="abortCondiction" label="��ͬ��ֹ����" visible="false"></ui:field>
				<ui:field field="breakDuty" label="ΥԼ����" visible="false"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
			</ui:dataset>

			<!-- ���������ϸ��Ϣ -->
			<ui:dataset datasetId="conferInfo" readOnly="true" staticDataSource="true">
				<ui:field field="partnerConfId" label="��ͬ��ʶ" visible="false"></ui:field>
				<ui:field field="partnerId" label="��������ʶ" visible="false"></ui:field>
				<ui:field field="conferCode" label="��ͬ���" required="true" validType="require" validMsg="�������ͬ���!" visible="true"></ui:field>
				<ui:field field="partnerConferType" label="��ͬ����" required="true" validType="require" validMsg="��ѡ���ͬ����!" attrCode="PARTNER_CONFER_TYPE" visible="true"></ui:field>
				<ui:field field="signDate" label="ǩ������" required="true" validType="require" validMsg="��ѡ��ǩ������!" type="date" visible="true"></ui:field>
				<ui:field field="expDate" label="��Ч��" required="true" validType="require" validMsg="��ѡ��ǩ��Ч��!" type="date" visible="true"></ui:field>
				<ui:field field="cooperateType" label="������ʽ" attrCode="COOPERATE_TYPE" visible="true"></ui:field>
				<ui:field field="conferContence" label="��������" visible="true"></ui:field>
				<ui:field field="balnsRule" label="�������" visible="true"></ui:field>
				<ui:field field="partnerDroitDuty" label="�������Ȩ������" visible="true"></ui:field>
				<ui:field field="droitDuty" label="������Ȩ������" visible="true"></ui:field>
				<ui:field field="abortCondiction" label="��ͬ��ֹ����" visible="true"></ui:field>
				<ui:field field="breakDuty" label="ΥԼ����" visible="true"></ui:field>
				<ui:field field="state" label="״̬" attrCode="PARTNER_CONF_STATE" visible="true"></ui:field>
			</ui:dataset>

			<!--���Ź�ϵ�б� -->
			<ui:dataset datasetId="departRelatList" loadDataAction="PartnerService" loadDataActionMethod="queryDepartRelatByPartnerId" staticDataSource="true" async="false">
				<ui:field field="partyId" label="���ű�ʶ" visible="true"></ui:field>
				<ui:field field="partyCode" label="���ű���" visible="true"></ui:field>
				<ui:field field="partyName" label="��������" visible="true"></ui:field>
				<ui:field field="partnerId" label="��������ʶ" visible="false"></ui:field>
			</ui:dataset>
			<!-- ���Ź�ϵ��ϸ��Ϣ-->
			<ui:dataset datasetId="departRelatInfo" readOnly="true" staticDataSource="true">
				<ui:field field="partyId" label="���ű�ʶ" visible="true"></ui:field>
				<ui:field field="partyCode" label="���ű���" visible="true"></ui:field>
				<ui:field field="partyName" label="��������" visible="true"></ui:field>
				<ui:field field="partnerId" label="��������ʶ" visible="false"></ui:field>
			</ui:dataset>

		</div>

		<ui:layout type="border">
			<ui:pane position="top" style="height:80px">
				<ui:panel type="titleList" desc="��ѯ����">
					<ui:content>
						<ui:layout type="border">
							<ui:pane position="center">
								<ui:form submit="queryPartner" id="queryForm" dataset="queryInfo" labelLayout="13%" inputLayout="20%"></ui:form>
							</ui:pane>
							<ui:pane position="right" style="text-align:center">
								<ui:button id="queryPartner">��ѯ</ui:button>
								<ui:button id="queryReset">����</ui:button>
							</ui:pane>
						</ui:layout>
					</ui:content>
				</ui:panel>
			</ui:pane>

			<ui:pane position="center">
				<ui:layout type="border">
					<ui:pane position="center">

						<ui:layout type="border">
							<ui:pane position="top" style="height:25px">
								<ui:bar type="search" desc="��������б�">
									<ui:content>
										<ui:button id="addRootPartner">���һ���������</ui:button>
										<ui:button id="addChildPartner">����¼��������</ui:button>
										<ui:button id="editPartner">�޸�</ui:button>
									</ui:content>
								</ui:bar>
							</ui:pane>
							<ui:pane position="center">
								<ZTESOFT:treelist id="partnerTree" class="treelist" onItemClick="clickPartner()" showImage="false" showBorder="false" contBorder="true" height="185" showImage=false showHead="true">
									<ZTESOFT:columns>
										<ZTESOFT:column width="30%" display="true" displayText="����������" propertyName="partnerCode" />
										<ZTESOFT:column width="30%" display="true" displayText="������" propertyName="orgName" />
										<ZTESOFT:column width="20%" display="true" displayText="������" propertyName="staffName" />
										<ZTESOFT:column width="10%" display="true" displayText="������ʽ" propertyName="cooperateType" />
										<ZTESOFT:column width="10%" display="true" displayText="������鼶��" propertyName="partnerGrade" />
										<ZTESOFT:column width="" display="false" displayText="�����������" propertyName="partnerType" />
										
										<ZTESOFT:column width="" display="false" displayText="������Id" propertyName="orgId" />
										<ZTESOFT:column width="" display="false" displayText="������Id" propertyName="staffId" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="partnerId" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="partyRoleId" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="partnerDesc" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="corpAgent" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="addr" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="applyDate" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="linkman" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="corporateLicenceNo" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="spcpLicenceNo" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="bankPermit" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="companySize" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="companyBalns" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="devPlan" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="state" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="superPartnerId" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="pathCode" />
									</ZTESOFT:columns>
								</ZTESOFT:treelist>
							</ui:pane>
						</ui:layout>
					</ui:pane>

					<ui:pane position="bottom" style="height:320px">
						<ui:tabpane id="mainPage">
							<ui:tabpage desc="��ϸ��Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="savePartner" id="partnerInfoForm" dataset="partnerInfo" labelLayout="19%" inputLayout="30%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom" style="height:20px">
										<ui:button id="savePartner">����</ui:button>
										<ui:button id="cancelPartner">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="��ͬ��Ϣ">
								<ui:layout type="border">
									<ui:pane position="left" style="width:220px">
										<ui:layout type="border">
											<ui:pane position="top" style="height:20px">
												<ui:bar type="search" desc="">
													<ui:content>
														<ui:button id="insertConferInfo">����</ui:button>
														<ui:button id="editConferInfo">�޸�</ui:button>
													</ui:content>
												</ui:bar>
											</ui:pane>
											<ui:pane position="center">
												<ui:table dataset="conferList"></ui:table>
											</ui:pane>
										</ui:layout>
									</ui:pane>
									<ui:pane position="center">
										<ui:layout type="border">
											<ui:pane position="center">
												<ui:panel type="titleList" desc="��ͬ��ϸ��Ϣ">
													<ui:content>
														<ui:form submit="saveConfer" id="conferInfoForm" labelLayout="24%" inputLayout="25%" dataset="conferInfo"></ui:form>
													</ui:content>
												</ui:panel>
											</ui:pane>
											<ui:pane position="bottom" style="height:20px">
												<ui:button id="saveConfer">����</ui:button>
												<ui:button id="cancelConfer">ȡ��</ui:button>
											</ui:pane>
										</ui:layout>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="��������">
								<ui:layout type="border">
									<ui:pane position="top" style="height:20px">
										<ui:bar type="search" desc="��������">
											<ui:content>
												<ui:button id="insertDepartRelat">����</ui:button>
												<ui:button id="deleteDepartRelat">ɾ��</ui:button>
											</ui:content>
										</ui:bar>
									</ui:pane>
									<ui:pane position="center">
										<ui:table dataset="departRelatList"></ui:table>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</ui:pane>
				</ui:layout>
			</ui:pane>
		</ui:layout>
	</body>
</html>
