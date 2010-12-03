<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/Position.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="positionInfo" readOnly="true">
				<ui:field field="positionId" label="��λ��ʶ" visible="false"></ui:field>
				<ui:field field="positionName" label="��λ����" required="true" validType="require" validMsg="�������λ����!"></ui:field>
				<ui:field field="positionDesc" label="��λ����" required="true" validType="require" validMsg="�������λ����!"></ui:field>
				<ui:field field="state" label="״̬" dropDown="positionStateSelect"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" type="date" visible="false"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="positionStateSelect" attrCode="COMM_RECORD_STATE" staticDataSource="false"></ui:dropdown>
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="��λ������">
						<ui:content>
							<ui:button id="addPosition">���</ui:button>
							<ui:button id="editPosition">�༭</ui:button>
							<ui:button id="deletePosition">ɾ��</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="positionTreeView" class="treelist" onItemClick="clickPosition()" showBorder="true" contBorder="true" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="30%" display="true" displayText="��λ��ʶ" propertyName="positionId" />
							<ZTESOFT:column width="30%" display="true" displayText="��λ����" propertyName="positionName" />
							<ZTESOFT:column width="40%" display="true" displayText="��λ����" propertyName="positionDesc" />
							<ZTESOFT:column width="" display="false" displayText="״̬" propertyName="state" />
							<ZTESOFT:column width="" display="false" displayText="״̬ʱ��" propertyName="stateDate" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height:250px;">
					<ui:tabpane id="pane1">
						<ui:tabpage desc="��λ���༭">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="btn_commitPosition" dataset="positionInfo" id="positionForm" labelLayout="15%" inputLayout="35%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="btn_commitPosition">ȷ��</ui:button>
									<ui:button id="btn_cancel">ȡ��</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
