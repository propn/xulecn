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
			<ui:field field="districtCode" label="小区编码" visible="true"></ui:field>
			<ui:field field="districtName" label="小区名称" visible="true"></ui:field>
			<ui:field field="dealExch" label="所属处理局ID" visible="false"></ui:field>
			<ui:field field="dealExchName" label="所属处理局" visible="true" popup="true" keyField="dealExch"></ui:field>
		</ui:dataset>
		<!-- 员工动态信息列表Dataset定义-->
		<ui:dataset datasetId="lanDistrictList" loadDataAction="PartyService" loadDataActionMethod="queryLanDistrictPaginate" 
		pageIndex="1" pageSize="16" autoLoadPage="true" staticDataSource="true" loadAsNeeded="false" paginate="true">
			<ui:field field="districtId" label="小区编号" visible="false"></ui:field>
			<ui:field field="districtCode" label="小区编码" visible="true"></ui:field>
			<ui:field field="districtName" label="小区名称" visible="true"></ui:field>
			<ui:field field="districtType" label="小区类型" visible="false"></ui:field>
			<ui:field field="districtAddr" label="小区地址" visible="true"></ui:field>
			<ui:field field="dealExch" label="所属处理局" visible="false"></ui:field>
			<ui:field field="subExch" label="所属局站" visible="false"></ui:field>
			<ui:field field="dealExchName" label="所属处理局" visible="true"></ui:field>			
			<ui:field field="subExchName" label="所属局站" visible="true"></ui:field>
			<ui:parameter parameterId="districtCode" value=""></ui:parameter>
			<ui:parameter parameterId="districtName" value=""></ui:parameter>
			<ui:parameter parameterId="dealExch" value=""></ui:parameter>
			<ui:parameter parameterId="menuCode" value=""></ui:parameter>
		</ui:dataset>
		
		<ui:dataset datasetId="lanDistrictInfo" staticDataSource="true" readOnly="true">
			<ui:field field="districtId" label="小区编号" visible="false"></ui:field>
			<ui:field field="districtCode" label="小区编码" visible="true" required="true" validType="require" validMsg="请输入小区编码!"></ui:field>
			<ui:field field="districtName" label="小区名称" visible="true" required="true" validType="require" validMsg="请输入小区名称!"></ui:field>
			<ui:field field="districtType" label="小区类型" visible="false"></ui:field>
			<ui:field field="districtAddr" label="小区地址" visible="true"></ui:field>
			<ui:field field="dealExch" label="所属处理局ID" visible="false"></ui:field>
			<ui:field field="subExch" label="所属局站Id" visible="false"></ui:field>
			<ui:field field="dealExchName" label="所属处理局" visible="true"></ui:field>			
			<ui:field field="subExchName" label="所属局站" visible="true" popup="true"></ui:field>			
		</ui:dataset>
		
		<ui:layout type="border">
			<ui:pane position="top" style="height:85px">
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:panel type="titleList" desc="查询条件">
							<ui:content>
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="queryInfo" id="queryInfoForm" submit="btnQuery" inputLayout="20%" labelLayout="13%"></ui:form>
									</ui:pane>
									<ui:pane position="right" style="width:100px">
										<ui:button id="btnQuery">查询</ui:button>
										<ui:button id="btnReset">重置</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:content>
						</ui:panel>
					</ui:pane>
					<ui:pane position="bottom">
						<ui:bar type="search" desc="小区列表">
							<ui:content>
								<ui:button id="addLanDistrict">添加</ui:button>
								<ui:button id="editLanDistrict">编辑</ui:button>
								<ui:button id="deleteLanDistrict">删除</ui:button>
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
					<ui:tabpage desc="小区详细信息">
						<ui:layout type="border">
							<ui:pane position="center">
								<ui:form submit="commit" dataset="lanDistrictInfo" id="lanDistrictInfoForm" labelLayout="16%" inputLayout="33%"></ui:form>
							</ui:pane>
							<ui:pane position="bottom">
								<ui:button id="commit">确定</ui:button>
								<ui:button id="cancel">取消</ui:button>
							</ui:pane>
						</ui:layout>
					</ui:tabpage>
				</ui:tabpane>
			</ui:pane>
		</ui:layout>
	</body>
</html>
