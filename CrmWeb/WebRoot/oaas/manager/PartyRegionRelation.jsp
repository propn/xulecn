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
				<ui:field field="partyId" label="组织ID" visible="false"></ui:field>
				<ui:field field="orgName" label="组织名称"></ui:field>
				<ui:field field="regionId" label="区域ID" visible="false"></ui:field>
				<ui:field field="regionName" label="区域名称"></ui:field>
				<ui:field field="relationType" label="关系类型" attrCode="ORGREG_RELATION_TYPE"></ui:field>
				<ui:parameter parameterId="sql" type="string"></ui:parameter>
				<ui:parameter parameterId="sqlParams" type="object"></ui:parameter>				
				<ui:parameter parameterId="fieldNames" type="object"></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="relationInfo">
				<ui:field field="partyId" label="组织ID" visible="false"></ui:field>
				<ui:field field="orgName" label="组织名称" readOnly="true" popup="true" required="true" validType="require" validMsg="请输入组织!" size="8"></ui:field>
				<ui:field field="regionId" label="区域ID" visible="false"></ui:field>
				<ui:field field="regionName" label="区域名称" readOnly="true"  popup="true" required="true" validType="require" validMsg="请输入区域!" size="8"></ui:field>
				<ui:field field="relationType" label="关系类型" readOnly="true" attrCode="ORGREG_RELATION_TYPE"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:560px">
					<ui:layout type="border">
						<ui:pane position="top" style="height:30px">
							<ui:bar type="search" desc="组织区域关系管理">
								<ui:content>
									<ui:button id="addRelation">增加</ui:button>
									<ui:button id="deleteRelation">删除</ui:button>
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
						<ui:tabpage desc="建立组织与区域关系">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form dataset="relationInfo" id="relationInfoForm" submit="btn_ok" labelLayout="15%" inputLayout="20%"></ui:form>
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
