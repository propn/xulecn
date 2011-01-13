<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/PartyRegionRelation.js"></script>
		<script language="javascript" src="../../common/script/CommDbOperat.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="partyRegionInfo" pageIndex="1" pageSize="24" readOnly="true" 
			loadDataAction="CommDbOperatService" loadDataActionMethod="dbSelectWithPaging" autoLoadPage="true" 
			staticDataSource="true" loadAsNeeded="false" paginate="true" async="true">
				<ui:field field="partyId" label="��֯ID" visible="false"></ui:field>
				<ui:field field="orgName" label="��֯����"></ui:field>
				<ui:field field="regionId" label="����ID" visible="false"></ui:field>
				<ui:field field="regionName" label="��������"></ui:field>
				<ui:field field="relationType" label="��ϵ����" attrCode="ORGREG_RELATION_TYPE"></ui:field>
				<ui:parameter parameterId="sql" type="string"></ui:parameter>
				<ui:parameter parameterId="sqlParams" type="object"></ui:parameter>				
				<ui:parameter parameterId="fieldNames" type="object"></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="relationInfo">
				<ui:field field="partyId" label="��֯ID" visible="false"></ui:field>
				<ui:field field="orgName" label="��֯����" readOnly="true" popup="true" required="true" validType="require" validMsg="��������֯!" size="8"></ui:field>
				<ui:field field="regionId" label="����ID" visible="false"></ui:field>
				<ui:field field="regionName" label="��������" readOnly="true"  popup="true" required="true" validType="require" validMsg="����������!" size="8"></ui:field>
				<ui:field field="relationType" label="��ϵ����" readOnly="true" attrCode="ORGREG_RELATION_TYPE"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:560px">
					<ui:layout type="border">
						<ui:pane position="top" style="height:30px">
							<ui:bar type="search" desc="��֯�����ϵ����">
								<ui:content>
									<ui:button id="addRelation">����</ui:button>
									<ui:button id="deleteRelation">ɾ��</ui:button>
								</ui:content>
							</ui:bar>
						</ui:pane>

						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:table dataset="partyRegionInfo"></ui:table>
								</ui:pane>
								<ui:pane position="bottom">
									<div class="customerpilot" extra="customerpilot" dataset="partyRegionInfo"></div>
								</ui:pane>
							</ui:layout>
						</ui:pane>
					</ui:layout>
				</ui:pane>
				<ui:pane position="bottom" style="height:150px" >
					<ui:tabpane id="tabpane1" style="display:block">
						<ui:tabpage desc="������֯�������ϵ">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form dataset="relationInfo" id="relationInfoForm" submit="btn_ok" labelLayout="15%" inputLayout="20%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="btn_ok">ȷ��</ui:button>
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
