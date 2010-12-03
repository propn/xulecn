<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/FinanceItemDef.js"></script>
		<script language="javascript" src="../../common/script/CommDbOperat.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="financeItemList" pageIndex="1" pageSize="24" readOnly="true" 
			loadDataAction="CommDbOperatService" loadDataActionMethod="dbSelectWithPaging" autoLoadPage="true" 
			staticDataSource="true" loadAsNeeded="false" paginate="true" async="false">
				<ui:field field="id" label="��ĿID"></ui:field>
				<ui:field field="defFeeCode" label="��Ŀ����"></ui:field>
				<ui:field field="defFeeName" label="��Ŀ����"></ui:field>
				<ui:field field="prodType" label="��Ʒ����" attrCode="DC_PRODUCT_SUB_TYPE"></ui:field>
				<ui:field field="prodProp" label="��Ʒ����" attrCode="PRODUCT_PROP"></ui:field>
				<ui:field field="srvCode" label="ҵ������"  attrCode="BIZS_TYPE"></ui:field>
				<ui:field field="feeItems" label="����������" attrCode="SRV_ITEM"></ui:field>
				<ui:field field="orderNo" label="����ѭ��"></ui:field>
				<ui:field field="countFlag" label="ͳ�Ʒ���"></ui:field>
				<ui:field field="countyType" label="��������" attrCode="COUNTY_TYPE"></ui:field>
				<ui:field field="residFlag" label="�����ʶ" attrCode="RESID_FLAG"></ui:field>
				<ui:parameter parameterId="sql" type="string"></ui:parameter>
				<ui:parameter parameterId="sqlParams" type="object"></ui:parameter>				
				<ui:parameter parameterId="fieldNames" type="object"></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="financeItemDetail">
				<ui:field field="id" label="��ĿID" required="true" ></ui:field>
				<ui:field field="defFeeCode" label="��Ŀ����" required="true" ></ui:field>
				<ui:field field="defFeeName" label="��Ŀ����" required="true" ></ui:field>
				<ui:field field="prodType" label="��Ʒ����" attrCode="DC_PRODUCT_SUB_TYPE"></ui:field>
				<ui:field field="prodProp" label="��Ʒ����" attrCode="PRODUCT_PROP"></ui:field>
				<ui:field field="srvCode" label="ҵ������" attrCode="BIZS_TYPE"></ui:field>
				<ui:field field="feeItems" label="����������" attrCode="SRV_ITEM"></ui:field>
				<ui:field field="orderNo" label="����˳��" required="true" ></ui:field>
				<ui:field field="countFlag" label="ͳ�Ʒ���"></ui:field>
				<ui:field field="countyType" label="��������" attrCode="COUNTY_TYPE"></ui:field>
				<ui:field field="residFlag" label="�����ʶ" attrCode="RESID_FLAG"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:540px">
					<ui:layout type="border">
						<ui:pane position="top" style="height:30px">
							<ui:bar type="search" desc="�����Ŀ����">
								<ui:content>
									<ui:button id="addItem">����</ui:button>
									<ui:button id="editItem">�༭</ui:button>
									<ui:button id="deleteItem">ɾ��</ui:button>
								</ui:content>
							</ui:bar>
						</ui:pane>

						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:table dataset="financeItemList"></ui:table>
								</ui:pane>
								<ui:pane position="bottom">
									<div class="customerpilot" extra="customerpilot"  dataset="financeItemList"></div>
								</ui:pane>
							</ui:layout>
						</ui:pane>
					</ui:layout>
				</ui:pane>
				<ui:pane position="bottom" style="height:160px" >
					<ui:tabpane id="tabpane1" style="display:block">
						<ui:tabpage desc="���������Ŀ">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form dataset="financeItemDetail" id="financeItemDetailForm" submit="btn_ok" labelLayout="15%" inputLayout="20%"></ui:form>
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
