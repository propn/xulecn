<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/LanDistrict.js"></script>
	</head>
	<body>
		<ui:dataset datasetId="queryInfo">
			<ui:field field="districtCode" label="С������" visible="true"></ui:field>
			<ui:field field="districtName" label="С������" visible="true"></ui:field>
			<ui:field field="dealExch" label="���������ID" visible="false"></ui:field>
			<ui:field field="dealExchName" label="���������" visible="true" popup="true" keyField="dealExch"></ui:field>
		</ui:dataset>
		<!-- Ա����̬��Ϣ�б�Dataset����-->
		<ui:dataset datasetId="lanDistrictList" loadDataAction="PartyService" loadDataActionMethod="queryLanDistrictPaginate" 
		pageIndex="1" pageSize="16" autoLoadPage="true" staticDataSource="true" loadAsNeeded="false" paginate="true">
			<ui:field field="districtId" label="С�����" visible="false"></ui:field>
			<ui:field field="districtCode" label="С������" visible="true"></ui:field>
			<ui:field field="districtName" label="С������" visible="true"></ui:field>
			<ui:field field="districtType" label="С������" visible="false"></ui:field>
			<ui:field field="districtAddr" label="С����ַ" visible="true"></ui:field>
			<ui:field field="dealExch" label="���������" visible="false"></ui:field>
			<ui:field field="subExch" label="������վ" visible="false"></ui:field>
			<ui:field field="dealExchName" label="���������" visible="true"></ui:field>			
			<ui:field field="subExchName" label="������վ" visible="true"></ui:field>
			<ui:parameter parameterId="districtCode" value=""></ui:parameter>
			<ui:parameter parameterId="districtName" value=""></ui:parameter>
			<ui:parameter parameterId="dealExch" value=""></ui:parameter>
			<ui:parameter parameterId="menuCode" value=""></ui:parameter>
		</ui:dataset>
		
		<ui:dataset datasetId="lanDistrictInfo" staticDataSource="true" readOnly="true">
			<ui:field field="districtId" label="С�����" visible="false"></ui:field>
			<ui:field field="districtCode" label="С������" visible="true" required="true" validType="require" validMsg="������С������!"></ui:field>
			<ui:field field="districtName" label="С������" visible="true" required="true" validType="require" validMsg="������С������!"></ui:field>
			<ui:field field="districtType" label="С������" visible="false"></ui:field>
			<ui:field field="districtAddr" label="С����ַ" visible="true"></ui:field>
			<ui:field field="dealExch" label="���������ID" visible="false"></ui:field>
			<ui:field field="subExch" label="������վId" visible="false"></ui:field>
			<ui:field field="dealExchName" label="���������" visible="true"></ui:field>			
			<ui:field field="subExchName" label="������վ" visible="true" popup="true"></ui:field>			
		</ui:dataset>
		
		<ui:layout type="border">
			<ui:pane position="top" style="height:85px">
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:panel type="titleList" desc="��ѯ����">
							<ui:content>
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="queryInfo" id="queryInfoForm" submit="btnQuery" inputLayout="20%" labelLayout="13%"></ui:form>
									</ui:pane>
									<ui:pane position="right" style="width:100px">
										<ui:button id="btnQuery">��ѯ</ui:button>
										<ui:button id="btnReset">����</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:content>
						</ui:panel>
					</ui:pane>
					<ui:pane position="bottom">
						<ui:bar type="search" desc="С���б�">
							<ui:content>
								<ui:button id="addLanDistrict">���</ui:button>
								<ui:button id="editLanDistrict">�༭</ui:button>
								<ui:button id="deleteLanDistrict">ɾ��</ui:button>
							</ui:content>
						</ui:bar>
					</ui:pane>
				</ui:layout>
			</ui:pane>
			<ui:pane position="center">
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:table dataset="lanDistrictList" skipRebuild="false" readOnly="true" showHeader="true" showIndicator="true" highlightSelection="true" maxRow="1000"></ui:table>
					</ui:pane>
					<ui:pane position="bottom">
						<div class="customerpilot" extra="customerpilot" id="lanDistrictListPilot" dataset="lanDistrictList"></div>
					</ui:pane>
				</ui:layout>
			</ui:pane>
			<ui:pane position="bottom" style="height:150px">
				<ui:tabpane id="pane">
					<ui:tabpage desc="С����ϸ��Ϣ">
						<ui:layout type="border">
							<ui:pane position="center">
								<ui:form submit="commit" dataset="lanDistrictInfo" id="lanDistrictInfoForm" labelLayout="16%" inputLayout="33%"></ui:form>
							</ui:pane>
							<ui:pane position="bottom">
								<ui:button id="commit">ȷ��</ui:button>
								<ui:button id="cancel">ȡ��</ui:button>
							</ui:pane>
						</ui:layout>
					</ui:tabpage>
				</ui:tabpane>
			</ui:pane>
		</ui:layout>
	</body>
</html>
