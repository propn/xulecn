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
				<ui:field field="id" label="科目ID"></ui:field>
				<ui:field field="defFeeCode" label="科目编码"></ui:field>
				<ui:field field="defFeeName" label="科目名称"></ui:field>
				<ui:field field="prodType" label="产品类型" attrCode="DC_PRODUCT_SUB_TYPE"></ui:field>
				<ui:field field="prodProp" label="产品性质" attrCode="PRODUCT_PROP"></ui:field>
				<ui:field field="srvCode" label="业务类型"  attrCode="BIZS_TYPE"></ui:field>
				<ui:field field="feeItems" label="费用项名称" attrCode="SRV_ITEM"></ui:field>
				<ui:field field="orderNo" label="排列循序"></ui:field>
				<ui:field field="countFlag" label="统计分组"></ui:field>
				<ui:field field="countyType" label="城乡类型" attrCode="COUNTY_TYPE"></ui:field>
				<ui:field field="residFlag" label="政企标识" attrCode="RESID_FLAG"></ui:field>
				<ui:parameter parameterId="sql" type="string"></ui:parameter>
				<ui:parameter parameterId="sqlParams" type="object"></ui:parameter>				
				<ui:parameter parameterId="fieldNames" type="object"></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="financeItemDetail">
				<ui:field field="id" label="科目ID" required="true" ></ui:field>
				<ui:field field="defFeeCode" label="科目编码" required="true" ></ui:field>
				<ui:field field="defFeeName" label="科目名称" required="true" ></ui:field>
				<ui:field field="prodType" label="产品类型" attrCode="DC_PRODUCT_SUB_TYPE"></ui:field>
				<ui:field field="prodProp" label="产品性质" attrCode="PRODUCT_PROP"></ui:field>
				<ui:field field="srvCode" label="业务类型" attrCode="BIZS_TYPE"></ui:field>
				<ui:field field="feeItems" label="费用项名称" attrCode="SRV_ITEM"></ui:field>
				<ui:field field="orderNo" label="排列顺序" required="true" ></ui:field>
				<ui:field field="countFlag" label="统计分组"></ui:field>
				<ui:field field="countyType" label="城乡类型" attrCode="COUNTY_TYPE"></ui:field>
				<ui:field field="residFlag" label="政企标识" attrCode="RESID_FLAG"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:540px">
					<ui:layout type="border">
						<ui:pane position="top" style="height:30px">
							<ui:bar type="search" desc="财务科目设置">
								<ui:content>
									<ui:button id="addItem">增加</ui:button>
									<ui:button id="editItem">编辑</ui:button>
									<ui:button id="deleteItem">删除</ui:button>
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
						<ui:tabpage desc="建立财务科目">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form dataset="financeItemDetail" id="financeItemDetailForm" submit="btn_ok" labelLayout="15%" inputLayout="20%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="btn_ok">确定</ui:button>
									<ui:button id="btn_cancel">取消</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
