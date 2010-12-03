<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title>����ѡ��</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/DepartmentSelect.js"></script>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="queryInfo" staticDataSource="true">
				<ui:field field="name" label="��������"></ui:field>
				<ui:field field="businessId" label="����Ӫҵ��" attrCode="DC_BUSINESS"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="departList" loadDataAction="PartyService" loadDataActionMethod="getDepartByType" staticDataSource="true">
				<ui:field field="select" label="" visible="true"></ui:field>
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				<ui:field field="orgName" label="��������" visible="true"></ui:field>
				<ui:field field="departType" label="��������" visible="true"></ui:field>
				<ui:field field="termFlag" label="��α�־" visible="false"></ui:field>
				<ui:field field="paySeatType" label="�տ�ϯλ����" visible="false"></ui:field>
				<ui:field field="regionId" label="Ӫҵ�����" visible="false"></ui:field>
				<ui:field field="regionName" label="Ӫҵ��" visible="false"></ui:field>
				<ui:field field="superPartyId" label="�ϼ�����ID" visible="false"></ui:field>
				<ui:field field="superPartyName" label="�ϼ�����" visible="false"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:60px">
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:form dataset="queryInfo" inputLayout="30%" labelLayout="19%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="btnQuery">��ѯ</ui:button>
							<!--  u i : button id="btnQueryAll">ȫ��< / u i : button-->
							<ui:button id="btnReset">����</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:pane>
				<ui:pane position="center">
					<ui:table dataset="departList"></ui:table>
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_Confirm">ȷ��</ui:button>
					<ui:button id="btn_Cancel">ȡ��</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
