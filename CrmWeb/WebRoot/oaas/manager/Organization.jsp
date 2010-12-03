<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/Organization.js"></script>
		<script>
		function table_logicalAddrList_onclick(){
			clickLogicalAddressList();
		}
		</script>
	</head>
	<body>
		<ui:permission>
		</ui:permission>
		
		<div id="datasetDefine">
			<ui:dataset datasetId="departmentInfo" staticDataSource="true" readOnly="true">
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				<ui:field field="departType" label="��������" attrCode="DEPART_TYPE" required="true" validType="require" validMsg="��ѡ��������!"></ui:field>
				<ui:field field="termFlag" label="��α�ʶ" visible="false"></ui:field>
				<ui:field field="paySeatType" label="�տ�ϯλ����" blankValue="true" attrCode="PAY_SEAT_TYPE" ></ui:field>
				<ui:field field="regionId" label="Ӫҵ�����" visible="false"></ui:field>
				<ui:field field="regionName" label="Ӫҵ��" popup="true" required="true" validType="require" validMsg="��ѡ��Ӫҵ��!"></ui:field>
				<ui:field field="superPartyId" label="�ϼ����ű��" visible="false"></ui:field>
				<ui:field field="superPartyName" label="�ϼ�����" popup="true" required="true" validType="require" validMsg="��ѡ���ϼ�����!"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="logicalAddrList" loadDataAction="PartyService" loadDataActionMethod="getLogicalAddrsByPartyId" async="false" staticDataSource="true">
				<ui:field field="logicalAddrId" label="�߼���ַ��ʶ"></ui:field>
				<ui:field field="addrId" label="��ַ��ʶ" visible="false"></ui:field>
				<ui:field field="logicalAddrType" label="�߼���ַ����" dropDown="logicalAddrTypeDropdown"></ui:field>
				<ui:field field="logicalAddrDeta" label="�߼���ַ��ϸ��Ϣ"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="logicalAddrInfo" staticDataSource="true" readOnly="true">
				<ui:field field="logicalAddrId" label="�߼���ַ��ʶ" visible="false"></ui:field>
				<ui:field field="addrId" label="��ַ��ʶ" visible="false"></ui:field>
				<ui:field field="logicalAddrType" label="�߼���ַ����" dropDown="logicalAddrTypeDropdown"></ui:field>
				<ui:field field="logicalAddrDeta" label="�߼���ַ��ϸ��Ϣ" required="true"></ui:field>
			</ui:dataset>
			<!-- ��֯������ϢDataset-->
			<ui:dataset datasetId="commonInfor" readOnly="true"> 
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				<ui:field field="parentPartyId" label="�ϼ���֯ID" visible="false"></ui:field>
				<ui:field field="parentPartyName" label="�ϼ���֯"></ui:field>
				<ui:field field="orgCode" label="��֯����" required="true" validType="require" validMsg="����д��֯����!"></ui:field>
				<ui:field field="orgName" label="��֯����" required="true" validType="require" validMsg="����д��֯����!"></ui:field>
				<ui:field field="orgLevel" label="��֯����" size="5"></ui:field>
				<ui:field field="orgTypeId" label="��֯����" required="true" validType="require" validMsg="��ѡ����֯����!" dropDown="orgTypeDropdown"></ui:field>
				<ui:field field="orgContent" label="��֯���"></ui:field>
				<ui:field field="state" label="״̬" required="true" validType="require" validMsg="��ѡ��״̬!" dropDown="stateSelect"></ui:field>
				<ui:field field="addrId" label="��ַ��ʶ" visible="false"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" type="date" visible="false"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" type="date" required="true" validType="require" validMsg="����д��Чʱ��!"></ui:field>
				<ui:field field="orgClass" label="��֯����" visible="true" attrCode="ORG_CLASS_NEW"></ui:field>
				<ui:field field="orgType" label="�Ʒ���֯����" visible="true" attrCode="BSN_ORG_TYPE"></ui:field>
			</ui:dataset>
			<!-- ��ϵ��ϢDataset-->
			<ui:dataset datasetId="contactInfor" readOnly="true">
				<ui:field field="addrId" label="��ַ��ʶ" visible="false"></ui:field>
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				<ui:field field="provinceName" label="ʡ��" required="true" validType="require" validMsg="����дʡ��!"></ui:field>
				<ui:field field="cityName" label="������" required="true" validType="require" validMsg="����д������!"></ui:field>
				<ui:field field="countyName" label="����" required="true" validType="require" validMsg="����д����!"></ui:field>
				<ui:field field="streetName" label="�ֵ���" required="true" validType="require" validMsg="����д�ֵ���!"></ui:field>
				<ui:field field="streetNbr" label="���ƺ�" required="true" validType="require" validMsg="����д���ƺ�!"></ui:field>
				<ui:field field="deta" label="��ַ��ϸ��Ϣ" required="true" validType="require" validMsg="����д��ϸ��Ϣ!"></ui:field>
				<ui:field field="postcode" label="��������" required="true" size="6" validType="number" validMsg="����д�������͵���������!"></ui:field>
				<ui:field field="alias" label="��ַ����"></ui:field>
				<ui:field field="addDescription" label="��ַ����"></ui:field>
				<ui:field field="bankBranchId" label="���з���" attrCode=""></ui:field>
				<ui:field field="bankAccNo" label="�����ʺ�" size="30" validType="number" validMsg="�����ʺű���Ϊ��������!"></ui:field>
				<ui:field field="incrTaxNo" label="��ֵ˰��" size="30"></ui:field>												
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="logicalAddrTypeDropdown" attrCode="LOGICAL_ADDR_TYPE" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="isOrgManagerSelect" attrCode="IS_ORG_MANAGER" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="stateSelect" attrCode="COMM_RECORD_STATE" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="orgTypeDropdown" attrCode="DC_ORG_TYPE" staticDataSource="false"></ui:dropdown>

		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:325px">
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="top" style="height:30px">
							<ui:bar type="search" desc="������֯����">
								<ui:content>
									<ui:button id="addRootOrganization">��Ӹ���֯</ui:button>
									<ui:button id="addSubOrganization">�������֯</ui:button>
									<ui:button id="editOrganization">�༭</ui:button>
									<ui:button id="deleteOrganization">ɾ��</ui:button>
								</ui:content>
							</ui:bar>
								</ui:pane>
								<ui:pane position="center">
							<ZTESOFT:treelist id="organizationTreeView" class="treelist" showCheck="true" onItemClick="clickOrganization()" showImage="false" showBorder="true" contBorder="true" showImage="false" showHead="true">
								<ZTESOFT:columns>
									<ZTESOFT:column width="40%" display="true" displayText="��֯����" propertyName="orgName" />
									<ZTESOFT:column width="20%" display="true" displayText="��֯����" propertyName="orgCode" />
									<ZTESOFT:column width="30%" display="true" displayText="��֯���" propertyName="orgContent" />
									<ZTESOFT:column width="10%" display="true" displayText="��֯����" propertyName="orgTypeName" />									
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
								</ui:pane>
							</ui:layout>
						</ui:pane>
					</ui:layout>
				</ui:pane>
				<ui:pane position="center">
					<!-- ��ϸ��Ϣ��壬��ʾ���Tab Page�ؼ�-->
					<ui:tabpane id="mainPage">
						<ui:tabpage desc="����">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="inputNext" dataset="commonInfor" id="commonInforForm" labelLayout="15%" inputLayout="34%"></ui:form>
									<div id="divDepartmentInfo" style="display:none">
										<ui:panel type="titleList" desc="������Ϣ" style="height:85px">
											<ui:content>
												<ui:form submit="inputNext" dataset="departmentInfo" id="departmentInfoForm" labelLayout="15%" inputLayout="34%"></ui:form>
											</ui:content>
										</ui:panel>
									</div>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="inputNext">��һ��</ui:button>
									<ui:button id="cancelCommit">ȡ��</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
						<ui:tabpage desc="��ϸ��Ϣ">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="commitOrganization"  dataset="contactInfor" id="contactInforForm" labelLayout="15%" inputLayout="34%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="commitOrganization">ȷ��</ui:button>
									<ui:button id="cancelCommit1">ȡ��</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
						<ui:tabpage desc="��չ��Ϣ">
							<ui:layout type="border">
								<ui:pane position="top">
									<ui:bar type="search" desc="��չ��Ϣ">
										<ui:content>
											<ui:button id="addLogicAddr">����</ui:button>
											<ui:button id="editLogicAddr">�༭</ui:button>
											<ui:button id="deleteLogicAddr">ɾ��</ui:button>
										</ui:content>
									</ui:bar>
								</ui:pane>
								<ui:pane position="center">
									<ui:table dataset="logicalAddrList"></ui:table>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:form submit="commitLogicAddr" dataset="logicalAddrInfo" labelLayout="15%" inputLayout="34%"></ui:form>
									<ui:button id="commitLogicAddr">ȷ��</ui:button>
									<ui:button id="cancelLogicAddr">ȡ��</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
