<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/ResourceRegion.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- ������Ӧ������Դ-->
			<ui:dataset datasetId="regionInfo" readOnly="true">
				<ui:field field="regionId" label="�����ʶ" visible="false" visible="false"></ui:field>
				<ui:field field="parentRegionId" label="�ϼ������ʶ" visible="false"></ui:field>
				<ui:field field="parentRegionName" label="�ϼ���������" visible="false"></ui:field>
				<ui:field field="configId" label="Ψһ��ʶ" visible="false"></ui:field>
				<ui:field field="regionLevel" label="���򼶱�" visible="false" required="true" dropDown="regionLevelSelect"></ui:field>
				<ui:field field="regionName" label="��������" required="true" validType="require" size="70" validMsg="��������������!"></ui:field>
				<ui:field field="regionDesc" label="��������"></ui:field>
				<ui:field field="regionCode" label="�������" required="true" size="24" validMsg="������24λ�����������!"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>
				<ui:field field="virtualDealFlag" label="�����־" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>
				<ui:field field="partyId" label="������ID" visible="false"></ui:field>
				<ui:field field="partyName" label="������" popup="true" keyField="partyId"></ui:field>
				<ui:field field="countryType" label="��������" blankValue="true" attrCode="REPORT_COUNTY_TYPE"></ui:field>
			</ui:dataset>
			<!-- ʡ�����Ʊ��Ӧ������Դ-->
			<ui:dataset datasetId="provinceInfo" readOnly="true">
				<ui:field field="provId" label="ʡ�ݱ��" visible="false" visible="false"></ui:field>
				<ui:field field="prvCode" label="ʡ�ݴ���" required="true" validType="require" size="20" validMsg="������5λ����ʡ�ݴ���!"></ui:field>
				<ui:field field="prvName" label="ʡ������" required="true" validType="require"  size="20" validMsg="������ʡ������!"></ui:field>
				<ui:field field="prvDesc" label="����"></ui:field>
				<ui:field field="prvFlag" label="��ʡ��־" required="true" validType="require" validMsg="��ѡ��ʡ��־!" dropDown="prvFlagSelect"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>
				<ui:field field="partyId" label="������ID" visible="false"></ui:field>
				<ui:field field="partyName" label="������" popup="true" keyField="partyId"></ui:field>
			</ui:dataset>
			<!-- ���������Ӧ������Դ-->
			<ui:dataset datasetId="lanInfo" readOnly="true">
				<ui:field field="provId" label="����ʡ��" visible="false"></ui:field>
				<ui:field field="provName" label="����ʡ��" required="true"></ui:field>
				<ui:field field="lanId" label="���������" visible="false"></ui:field>
				<ui:field field="lanCode" label="����������" required="true" validType="require"  size="6" validMsg="������6λ���ڵı���������!"></ui:field>
				<ui:field field="lanName" label="����������" required="true" validType="require"  size="20" validMsg="�����뱾��������!"></ui:field>
				<ui:field field="lanDesc" label="����������"></ui:field>
				<ui:field field="areaCode" label="�绰����" required="true" validType="number" validMsg="�������������͵ĵ绰����!"></ui:field>
				<ui:field field="flag" label="���ر�־" required="true" validType="require" validMsg="��ѡ�񱾵ر�־!" dropDown="flagSelect"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT" ></ui:field>
				<ui:field field="partyId" label="������ID" visible="false"></ui:field>
				<ui:field field="partyName" label="������" popup="true" keyField="partyId"></ui:field>
			</ui:dataset>
			<!-- Ӫҵ�����Ӧ������Դ-->
			<ui:dataset datasetId="businessInfo" readOnly="true">
				<ui:field field="lanId" label="����������ID" visible="false"></ui:field>
				<ui:field field="lanName" label="����������" required="true"></ui:field>
				<ui:field field="businessId" label="Ӫҵ�����" visible="false"></ui:field>
				<ui:field field="businessCode" label="Ӫҵ������" required="true" validType="require" size="3" validMsg="������3λ���ڵ�Ӫҵ������!"></ui:field>
				<ui:field field="businessName" label="Ӫҵ������" required="true" validType="require" size="20" validMsg="������Ӫҵ������!"></ui:field>
				<ui:field field="businessDesc" label="Ӫҵ������"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="partyId" label="������ID" visible="false"></ui:field>
				<ui:field field="partyName" label="������" popup="true" keyField="partyId"></ui:field>
			</ui:dataset>			
			<!-- Ͻ����Ϣ���Ӧ������Դ-->
			<ui:dataset datasetId="ppdomInfo" readOnly="true">
				<ui:field field="lanId" label="����������ID" visible="false"></ui:field>
				<ui:field field="lanName" label="����������" required="true"></ui:field>
				<ui:field field="ppdomId" label="Ͻ�����" visible="false"></ui:field>
				<ui:field field="ppdomCode" label="Ͻ������" required="true" validType="require" size="6" validMsg="������Ͻ������!"></ui:field>
				<ui:field field="ppdomName" label="Ͻ������" required="true" validType="require" size="6" validMsg="������Ͻ������!"></ui:field>
				<ui:field field="ppdomDesc" label="Ͻ������"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT" required="true"></ui:field>
			</ui:dataset>
			<!-- ��Դ�������Ϣ���Ӧ������Դ-->
			<ui:dataset datasetId="exchInfo" readOnly="true">
				<ui:field field="lanId" label="����������ID" visible="false"></ui:field>
				<ui:field field="lanName" label="����������" required="true" validType="require" size="6"></ui:field>
				<ui:field field="ppdomId" label="����Ͻ��ID" visible="false"></ui:field>
				<ui:field field="ppdomName" label="����Ͻ��" required="true" validType="require" size="6"></ui:field>
				<ui:field field="exchId" label="����ֱ��" visible="false"></ui:field>
				<ui:field field="exchCode" label="����ֱ���" required="true" validType="require" size="6" validMsg="���������ֱ���!"></ui:field>
				<ui:field field="exchDesc" label="���������"></ui:field>
				<ui:field field="exchType" label="���������" required="true" validType="require" validMsg="��������������!"></ui:field>
				<ui:field field="exchName" label="���������" required="true" validType="require" validMsg="��������������!"></ui:field>
				<ui:field field="superId" label="�ϼ�������" visible="false"></ui:field>
				<ui:field field="comments" label="��ע"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT" required="true"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="regionLevelSelect" attrCode="RESOURCE_REGION_LEVEL" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="prvFlagSelect" attrCode="PROVINCE_FLAG" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="flagSelect" attrCode="LAN_FLAG" staticDataSource="false"></ui:dropdown>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="��Դ�������">
						<ui:content>
							<ui:button id="addRootRegion">�½����ڵ�</ui:button>
							<ui:button id="addSubRegion">�½�������</ui:button>
							<ui:button id="editRegion">�༭</ui:button>
							<ui:button id="deleteRegion">ɾ��</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="regionTreeView" class="treelist" onItemClick="clickRegion()" showImage="false" showBorder="false" contBorder="false" showImage="false" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="55%" display="true" displayText="��������" propertyName="regionName" />
							<ZTESOFT:column width="10%" display="true" displayText="�������" propertyName="regionCode" />
							<ZTESOFT:column width="35%" display="true" displayText="��������" propertyName="regionDesc" />
							<ZTESOFT:column width="" display="false" displayText="�����־" propertyName="virtualDealFlag" />							
							<ZTESOFT:column width="" display="false" displayText="�����ʶ" propertyName="regionId" />							
							<ZTESOFT:column width="" display="false" displayText="NGN Flag" propertyName="ngnFlag" />							
							<ZTESOFT:column width="" display="false" displayText="�����ʶ" propertyName="regionId" />
							<ZTESOFT:column width="" display="false" displayText="�ϼ������ʶ" propertyName="parentRegionId" />
							<ZTESOFT:column width="" display="false" displayText="Ψһ��ʶ" propertyName="configId" />
							<ZTESOFT:column width="" display="false" displayText="���򼶱�" propertyName="regionLevel" />
							<ZTESOFT:column width="" display="false" displayText="·������" propertyName="pathCode" />
							<ZTESOFT:column width="" display="false" displayText="·������" propertyName="pathName" />
							<ZTESOFT:column width="" display="false" displayText="��������" propertyName="countryType" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height: 250px">
					<div id="groupPannel" style="display:block">
						<!-- ���Ź�˾��ϸ��Ϣ���-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc="������Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">ȷ��</ui:button>
										<ui:button id="btn_cancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="provincePannel" style="display:none">
						<!-- ʡ��˾��ϸ��Ϣ���-->
						<ui:tabpane id="pane2">
							<ui:tabpage desc="ʡ��˾��Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="provinceInfo" id="provinceInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">ȷ��</ui:button>
										<ui:button id="btn_cancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="lanPannel" style="display:none">
						<!-- ��������ϸ��Ϣ���-->
						<ui:tabpane id="pane3">
							<ui:tabpage desc="��������Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="lanInfo" id="lanInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">ȷ��</ui:button>
										<ui:button id="btn_cancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="businessPannel" style="display:none">
						<!-- Ӫҵ����ϸ��Ϣ���-->
						<ui:tabpane id="pane4">
							<ui:tabpage desc="Ӫҵ����Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="businessInfo" id="businessInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">ȷ��</ui:button>
										<ui:button id="btn_cancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					
					
					<div id="ppdomPannel" style="display:none">
						<!-- �������ϸ��Ϣ���-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc="�������Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">ȷ��</ui:button>
										<ui:button id="btn_cancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="exchPannel" style="display:none">
						<!-- ĸ����ϸ��Ϣ���-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc="ĸ����Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">ȷ��</ui:button>
										<ui:button id="btn_cancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="stationPannel" style="display:none">
						<!--  ��վ��ϸ��Ϣ���-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc=" ��վ��Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">ȷ��</ui:button>
										<ui:button id="btn_cancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>					
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
