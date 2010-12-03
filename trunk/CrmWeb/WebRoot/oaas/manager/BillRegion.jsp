<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<script language="javascript" src="../../common/script/CommDbOperat.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/BillRegion.js"></script>
	</head>
	<body>
		<div id="datasetDefine">

            <xml id="__ACTUALREG_FLAG">
            <items>
            <item label="��ʵ����" value="T" />
            <item label="��������" value="F" />
            </items>
            </xml>
            <code id="ACTUALREG_FLAG"></code>

			<!-- ������Ӧ������Դ-->
			<ui:dataset datasetId="regionInfo" readOnly="true">
				<ui:field field="regionId" label="�����ʶ" visible="false" visible="false"></ui:field>
				<ui:field field="parentRegionId" label="�ϼ������ʶ" visible="false"></ui:field>
				<ui:field field="parentRegionName" label="�ϼ���������" visible="false"></ui:field>
				<ui:field field="regionLevel" label="���򼶱�" visible="false" required="true" dropDown="regionLevelSelect"></ui:field>
				<ui:field field="regionName" label="��������" required="true" validType="require" size="20" validMsg="��������������!"></ui:field>
				<ui:field field="regionDesc" label="��������"></ui:field>
				<ui:field field="regionCode" label="�������" required="true" validType="require" size="20" validMsg="������20λ�����������!"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="isActualRegion" label="�Ƿ�ʵ������" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="ʡ����" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<!-- ʡ�����Ʊ��Ӧ������Դ-->
			<ui:dataset datasetId="provinceInfo" readOnly="true">
				<ui:field field="provId" label="ʡ�ݱ��" visible="false" visible="false"></ui:field>
			 	<ui:field field="prvCode" label="ʡ�ݴ���" required="true" validType="require" size="5" validMsg="������5λ���ڵ�ʡ�ݴ���!"></ui:field>
				<ui:field field="prvName" label="ʡ������" required="true" validType="require" size="20" validMsg="������ʡ������!"></ui:field>
				<ui:field field="prvDesc" label="����"></ui:field>
				<ui:field field="prvFlag" label="��ʡ��־" required="true" validType="require" validMsg="��ѡ��ʡ��־!" dropDown="prvFlagSelect"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="isActualRegion" label="�Ƿ�ʵ������" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="ʡ����" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<!-- ���������Ӧ������Դ-->
			<ui:dataset datasetId="lanInfo" readOnly="true">
				<ui:field field="provId" label="����ʡ��" visible="false"></ui:field>
				<ui:field field="provName" label="����ʡ��" required="true"></ui:field>
				<ui:field field="lanId" label="���������" visible="false"></ui:field>
				<ui:field field="lanCode" label="����������" required="true" validType="require" size="6" validMsg="������6λ���ڱ���������!"></ui:field>
				<ui:field field="lanName" label="����������" required="true" validType="require" size="20" validMsg="�����뱾��������!"></ui:field>
				<ui:field field="lanDesc" label="����������"></ui:field>
				<ui:field field="areaCode" label="�绰����" required="true" size="4" validType="number" validMsg="�������������͵ĵ绰����!"></ui:field>
				<ui:field field="flag" label="���ر�־" required="true" validType="require" validMsg="��ѡ�񱾵ر�ʶ!" dropDown="flagSelect"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="isActualRegion" label="�Ƿ�ʵ������" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="ʡ����" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<!-- Ӫҵ�����Ӧ������Դ-->
			<ui:dataset datasetId="businessInfo" readOnly="true">
				<ui:field field="lanId" label="����������ID" visible="false"></ui:field>
				<ui:field field="lanName" label="����������" required="true"></ui:field>
				<ui:field field="businessId" label="Ӫҵ�����" visible="false"></ui:field>
				<ui:field field="businessCode" label="Ӫҵ������" required="true" validType="require" validMsg="������Ӫҵ������!"></ui:field>
				<ui:field field="businessName" label="Ӫҵ������" required="true" validType="require" size="20" validMsg="������Ӫҵ������!"></ui:field>
				<ui:field field="businessDesc" label="Ӫҵ������"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="isActualRegion" label="�Ƿ�ʵ������" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="ʡ����" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<!-- ������Ӧ������Դ(���򼶱������ר�õ����ݼ�)-->
			<ui:dataset datasetId="exchangeInfo" readOnly="true">
				<ui:field field="regionId" label="�����ʶ" visible="false"></ui:field>
				<ui:field field="parentRegionId" label="�ϼ������ʶ" visible="false"></ui:field>
				<ui:field field="parentRegionName" label="����Ӫҵ��" required="true"></ui:field>
				<ui:field field="configId" label="Ψһ��ʶ" visible="false"></ui:field>
				<ui:field field="regionLevel" label="���򼶱�" visible="false"></ui:field>
				<ui:field field="regionName" label="����������" required="true" size="20" validType="require" validMsg="�����뽻��������!"></ui:field>
				<ui:field field="regionDesc" label="����������"></ui:field>
				<ui:field field="regionCode" label="�����ֱ���" required="true" size="8" validType="require" validMsg="�����뽻���ֱ���!"></ui:field>
				<ui:field field="ngnFlag" label="NGN��־" blankValue="true" attrCode="BOOLEAN_SELECT" visible="false"></ui:field>				
				<ui:field field="isActualRegion" label="�Ƿ�ʵ������" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="ʡ����" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<ui:dataset datasetId="accNbrList" readOnly="true" loadDataAction="RegionService" loadDataActionMethod="getAccNbrNodesByExchRegionId" staticDataSource="true">
				<ui:field field="exchName" label="����������" visible="false"></ui:field>
				<ui:field field="accNbrBegin" label="��ʼ����" visible="true"></ui:field>
				<ui:field field="accNbrEnd" label="��ֹ����"></ui:field>
				<ui:field field="regionId" label="�����ʶ" visible="false"></ui:field>
				<ui:field field="exchCode" label="�������" visible="false"></ui:field>
				<ui:field field="exchId" label="�����ֱ�ʶ" visible="false"></ui:field>
				<ui:field field="state" label="״̬" attrCode="COMM_RECORD_STATE"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" visible="true"></ui:field>
				<ui:field field="expDate" label="ʧЧʱ��" visible="true"></ui:field>
				<ui:field field="areaId" label="Ӫҵ����ʶ" visible="false"></ui:field>
				<ui:field field="prodFamilyId" label="��Ʒ�����ʶ" visible="false"></ui:field>
				<ui:field field="prodFamilyName" label="��Ʒ��������"></ui:field>
				<ui:field field="isActualRegion" label="�Ƿ�ʵ������" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="ʡ����" blankValue="true"  visible="false"></ui:field>				
		</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="regionLevelSelect" attrCode="BILL_REGION_LEVEL" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="prvFlagSelect" attrCode="PROVINCE_FLAG" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="flagSelect" attrCode="LAN_FLAG" staticDataSource="false"></ui:dropdown>
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="�Ʒ��������">
						<ui:content>
							<ui:button id="addRootRegion">�½�������</ui:button>
							<ui:button id="addSubRegion">�½�������</ui:button>
							<ui:button id="editRegion">�༭</ui:button>
							<ui:button id="deleteRegion">ɾ��</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="regionTreeView" class="treelist" onItemClick="clickRegion()" showImage="false" showBorder="false" contBorder="true" showImage=false showHead=true>
						<ZTESOFT:columns>
							<ZTESOFT:column width="55%" display="true" displayText="��������" propertyName="regionName" />
							<ZTESOFT:column width="10%" display="true" displayText="�������" propertyName="regionCode" />
							<ZTESOFT:column width="35%" display="true" displayText="��������" propertyName="regionDesc" />
							<ZTESOFT:column width="" display="false" displayText="ngnFlag" propertyName="ngnFlag" />							
							<ZTESOFT:column width="" display="false" displayText="�����ʶ" propertyName="regionId" />
							<ZTESOFT:column width="" display="false" displayText="�ϼ������ʶ" propertyName="parentRegionId" />
							<!-- ZTESOFT:column width="" display="false" displayText="Ψһ��ʶ" propertyName="configId" /-->
							<ZTESOFT:column width="" display="false" displayText="���򼶱�" propertyName="regionLevel" />
							<ZTESOFT:column width="" display="false" displayText="�Ƿ�ʵ������" propertyName="isActualRegion" />
							<ZTESOFT:column width="" display="false" displayText="ʡ����" propertyName="provinceCode" />

						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height:260px;">
					<div id="groupPannel" style="display:block">
						<!-- ���Ź�˾��ϸ��Ϣ���-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc="������Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="regionInfo" id="regionInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
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
										<ui:form dataset="provinceInfo" id="provinceInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
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
										<ui:form dataset="lanInfo" id="lanInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
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
										<ui:form dataset="businessInfo" id="businessInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">ȷ��</ui:button>
										<ui:button id="btn_cancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="exchangePannel" style="display:none;">
						<!-- ������ϸ��Ϣ���(��ʾ���Ƹ�Ϊ"������" - - 2006-04-25)-->
						<ui:tabpane id="exchangePage">
							<ui:tabpage desc="��������Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="exchangeInfo" id="exchangeInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">ȷ��</ui:button>
										<ui:button id="btn_cancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="�Ŷι���">
								<!-- ����ε�����ɾ���༭�������-->
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:table dataset="accNbrList" id="accNbrListTable"></ui:table>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="addAccNbr">���</ui:button>
										<ui:button id="editAccNbr">�༭</ui:button>
										<ui:button id="deleteAccNbr">ɾ��</ui:button>
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
