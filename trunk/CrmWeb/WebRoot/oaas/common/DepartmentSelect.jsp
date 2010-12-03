<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title>部门选择</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/DepartmentSelect.js"></script>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="queryInfo" staticDataSource="true">
				<ui:field field="name" label="部门名称"></ui:field>
				<ui:field field="businessId" label="所属营业区" attrCode="DC_BUSINESS"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="departList" loadDataAction="PartyService" loadDataActionMethod="getDepartByType" staticDataSource="true">
				<ui:field field="select" label="" visible="true"></ui:field>
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				<ui:field field="orgName" label="部门名称" visible="true"></ui:field>
				<ui:field field="departType" label="部门类型" visible="true"></ui:field>
				<ui:field field="termFlag" label="班次标志" visible="false"></ui:field>
				<ui:field field="paySeatType" label="收款席位类型" visible="false"></ui:field>
				<ui:field field="regionId" label="营业区编号" visible="false"></ui:field>
				<ui:field field="regionName" label="营业区" visible="false"></ui:field>
				<ui:field field="superPartyId" label="上级部门ID" visible="false"></ui:field>
				<ui:field field="superPartyName" label="上级部门" visible="false"></ui:field>
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
							<ui:button id="btnQuery">查询</ui:button>
							<!--  u i : button id="btnQueryAll">全部< / u i : button-->
							<ui:button id="btnReset">重置</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:pane>
				<ui:pane position="center">
					<ui:table dataset="departList"></ui:table>
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_Confirm">确定</ui:button>
					<ui:button id="btn_Cancel">取消</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
