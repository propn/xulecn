<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/SaleRegion.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- ������Ӧ������Դ-->
			<ui:dataset datasetId="regionInfo" readOnly="true">
				<ui:field field="regionId" label="�����ʶ" visible="false" visible="false"></ui:field>
				<ui:field field="parentRegionId" label="�ϼ������ʶ" visible="false"></ui:field>
				<ui:field field="parentRegionName" label="�ϼ���������" visible="false"></ui:field>
				<ui:field field="configId" label="Ψһ��ʶ" visible="false"></ui:field>
				<ui:field field="regionLevel" label="���򼶱�" required="true" validType="require" validMsg="��ѡ�����򼶱�!" dropDown="regionLevelSelect"></ui:field>
				<ui:field field="regionName" label="��������" required="true" validType="require" validMsg="��������������!"></ui:field>
				<ui:field field="regionDesc" label="��������"></ui:field>
				<ui:field field="regionCode" label="�������" required="true" validType="require" validMsg="�������������!"></ui:field>
			</ui:dataset>
			<!-- ʡ�����Ʊ��Ӧ������Դ-->
			<ui:dataset datasetId="provinceInfo" readOnly="true">
				<ui:field field="provId" label="ʡ�ݱ��" visible="false" visible="false"></ui:field>
				<ui:field field="prvCode" label="ʡ�ݴ���" required="true" validType="require" validMsg="������ʡ�ݴ���!"></ui:field>
				<ui:field field="prvName" label="ʡ������" required="true" validType="require" validMsg="������ʡ������!"></ui:field>
				<ui:field field="prvDesc" label="����"></ui:field>
				<ui:field field="prvFlag" label="��ʡ��־" dropDown="prvFlagSelect"></ui:field>
			</ui:dataset>
			<!-- ���������Ӧ������Դ-->
			<ui:dataset datasetId="lanInfo" readOnly="true">
				<ui:field field="provId" label="����ʡ��" visible="false"></ui:field>
				<ui:field field="provName" label="����ʡ��" required="true" validType="require" validMsg="����������ʡ��!"></ui:field>
				<ui:field field="lanId" label="���������" visible="false"></ui:field>
				<ui:field field="lanCode" label="����������" required="true" validType="require" validMsg="�����뱾��������!"></ui:field>
				<ui:field field="lanName" label="����������" required="true" validType="require" validMsg="�����뱾��������!"></ui:field>
				<ui:field field="areaCode" label="�绰����"  required="true" validType="number" validMsg="�������������͵ĵ绰����!"></ui:field>
				<ui:field field="lanDesc" label="����������"></ui:field>
				<ui:field field="flag" label="���ر�־" required="true" validType="require" validMsg="��ѡ�񱾵ر�ʶ!" dropDown="flagSelect"></ui:field>
			</ui:dataset>
			<!-- Ӫ��Ƭ����Ϣ���Ӧ������Դ-->
			<ui:dataset datasetId="saleAreaInfo" readOnly="true">
				<ui:field field="areaId" label="Ӫ�������ʶ" visible="false"></ui:field>
				<ui:field field="lanId" label="���������" visible="false"></ui:field>
				<ui:field field="lanName" label="����������" required="true" validType="require" validMsg="����������������!"></ui:field>
				<ui:field field="areaName" label="Ӫ����������"  required="true" validType="require" validMsg="������Ӫ����������!"></ui:field>
				<ui:field field="areaCode" label="Ӫ���������"  required="true" validType="require" validMsg="������Ӫ���������!"></ui:field>
				<ui:field field="areaDesc" label="Ӫ����������"></ui:field>
				<ui:field field="validFlag" label="��Ч��־" required="true" validType="require" validMsg="��ѡ����Ч��־!" dropDown="validFlagSelect"></ui:field>
				<ui:field field="remark" label="��ע"></ui:field>
			</ui:dataset>
			<!-- Ӫ���������Ӧ������Դ-->
			<ui:dataset datasetId="saleCommInfo" readOnly="true">
				<ui:field field="commId" label="�������" visible="false"></ui:field>
				<ui:field field="commName" label="��������"  required="true" validType="require" validMsg="��������������!"></ui:field>
				<ui:field field="commCode" label="��������"  required="true" validType="require" validMsg="��������������!"></ui:field>
				<ui:field field="commDesc" label="��������"></ui:field>
				<ui:field field="areaId" label="����Ӫ��������" visible="false"></ui:field>
				<ui:field field="areaName" label="����Ӫ������" required="true"></ui:field>
				<ui:field field="manager" label="��������"  required="true" validType="number" validMsg="��������������!"></ui:field>
				<ui:field field="validFlag" label="��Ч��־" required="true" validType="require" validMsg="��ѡ����Ч��־!" dropDown="validFlagSelect"></ui:field>
				<ui:field field="remark" label="��ע"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<!-- xml id="__regionLevelSelect">
			<items>
			<item label="����" value="97A" />
			<item label="ʡ��˾" value="97B" />
			<item label="������" value="97D" />
			<item label="Ӫ��Ƭ��" value="99D" />
			<item label="Ӫ������" value="99E" />
			</items>
			</xml>
			<code id="regionLevelSelect" staticDataSource="true"></code-->
			<ui:dropdown id="regionLevelSelect" attrCode="SALE_REGION_LEVEL" staticDataSource="false"></ui:dropdown>

			<!-- xml id="__prvFlagSelect">
			<items>
			<item label="��ʡ" value="0" />
			<item label="�Ǳ�ʡ" value="1" />
			</items>
			</xml>
			<code id="prvFlagSelect"></code-->
			<ui:dropdown id="prvFlagSelect" attrCode="PROVINCE_FLAG" staticDataSource="false"></ui:dropdown>

			<!-- xml id="__flagSelect">
			<items>
			<item label="���ر���" value="0" />
			<item label="��ر���" value="1" />
			</items>
			</xml>
			<code id="flagSelect"></code-->
			<ui:dropdown id="flagSelect" attrCode="LAN_FLAG" staticDataSource="false"></ui:dropdown>

			<!-- xml id="__validFlagSelect">
			<items>
			<item label="��Ч" value="0" />
			<item label="��Ч" value="1" />
			</items>
			</xml>
			<code id="validFlagSelect"></code-->
			<ui:dropdown id="validFlagSelect" attrCode="VALID_FLAG" staticDataSource="false"></ui:dropdown>
			
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="Ӫ���������">
						<ui:content>
							<ui:button id="addRootRegion">�½�������</ui:button>
							<ui:button id="addSubRegion">�½�������</ui:button>
							<ui:button id="editRegion">�༭</ui:button>
							<ui:button id="deleteRegion">ɾ��</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="regionTreeView" class="treelist" onItemClick="clickRegion()" showImage="false" showBorder="false" contBorder="false" showImage=false width="100%" height="315" showHead=true>
						<ZTESOFT:columns>
							<ZTESOFT:column width="25%" display="true" displayText="��������" propertyName="regionName" />
							<ZTESOFT:column width="25%" display="true" displayText="�����ʶ" propertyName="regionId" />
							<ZTESOFT:column width="25%" display="true" displayText="�������" propertyName="regionCode" />
							<ZTESOFT:column width="25%" display="true" displayText="��������" propertyName="regionDesc" />

							<ZTESOFT:column width="" display="false" displayText="�ϼ������ʶ" propertyName="parentRegionId" />
							<ZTESOFT:column width="" display="false" displayText="Ψһ��ʶ" propertyName="configId" />
							<ZTESOFT:column width="" display="false" displayText="���򼶱�" propertyName="regionLevel" />

							<ZTESOFT:column width="" display="false" displayText="���ر�־" propertyName="downloadFlag" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height: 250px">
					<div id="groupPannel" style="display:block">
						<ui:bar type="pageTitle" desc="������Ϣ"></ui:bar>
						<ui:form dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">ȷ��</ui:button>
							<ui:button id="btn_cancel">ȡ��</ui:button>
						</div>
					</div>

					<div id="provincePannel" style="display:none">
						<!-- ʡ��˾��ϸ��Ϣ���-->
						<ui:bar type="pageTitle" desc="ʡ��˾��Ϣ">
						</ui:bar>
						<ui:form dataset="provinceInfo" id="provinceInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">ȷ��</ui:button>
							<ui:button id="btn_cancel">ȡ��</ui:button>
						</div>
					</div>
					<div id="lanPannel" style="display:none">
						<!-- ��������ϸ��Ϣ���-->
						<ui:bar type="pageTitle" desc="��������Ϣ"></ui:bar>
						<ui:form dataset="lanInfo" id="lanInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">ȷ��</ui:button>
							<ui:button id="btn_cancel">ȡ��</ui:button>
						</div>
					</div>
					<div id="saleAreaPannel" style="display:none">
						<ui:bar type="pageTitle" desc="Ӫ��������Ϣ"></ui:bar>
						<ui:form dataset="saleAreaInfo" id="saleAreaInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">ȷ��</ui:button>
							<ui:button id="btn_cancel">ȡ��</ui:button>
						</div>
					</div>
					<div id="saleCommPannel" style="display:none">
						<ui:bar type="pageTitle" desc="Ӫ��������Ϣ"></ui:bar>
						<ui:form dataset="saleCommInfo" id="saleCommInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">ȷ��</ui:button>
							<ui:button id="btn_cancel">ȡ��</ui:button>
						</div>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
