<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/PromotionStaff.js"></script>
	<body>
		<div id="datasetDefine">
			<!-- 查询Form使用的Dataset定义-->
			<ui:dataset datasetId="queryInfo">
				<ui:field field="partyName" label="员工名称"></ui:field>
				<ui:field field="staffCode" label="员工号"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="staffList" pageIndex="1" pageSize="12" readOnly="false" editable="false" 
			loadDataAction="PartyService" loadDataActionMethod="getStaffsByStaffCondPromotion" 
			autoLoadPage="true" staticDataSource="true" loadAsNeeded="false" paginate="true">
				<ui:field field="partyRoleId" label="参与人角色标识" visible="false"></ui:field>
				<ui:field field="partyName" label="员工名称"></ui:field>
				<ui:field field="staffCode" label="员工号"></ui:field>
				<ui:parameter parameterId="condition" type="object" value=""></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="selectedStaffList" staticDataSource="true" 
			loadDataAction="PartyService"  pageIndex="1" pageSize="100"
			loadDataActionMethod="getStaffsByStaffCondPromotion" 
			autoLoadPage="true" staticDataSource="true"
			loadAsNeeded="false" paginate="true">
				<ui:field field="partyRoleId" label="参与人角色标识" visible="false"></ui:field>
				<ui:field field="partyName" label="员工名称"></ui:field>
				<ui:field field="staffCode" label="员工号" visible="false"></ui:field>
				<ui:parameter parameterId="condition" type="object" value=""></ui:parameter>
			</ui:dataset>
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="查询条件">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="doQuery" dataset="queryInfo" labelLayout="13%" inputLayout="30%"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:120px;">
									<ui:button id="doQuery">查询</ui:button>
									<ui:button id="doQueryAll">全部员工</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="left" style="width:480px">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:panel type="titleList" desc="可选员工">
										<ui:content>
											<ui:table dataset="staffList" id="staffTable"></ui:table>
										</ui:content>
									</ui:panel>
								</ui:pane>
								<ui:pane position="bottom">
									<div class="customerpilot" extra="customerpilot" id="staffPilot" dataset="staffList"></div>
								</ui:pane>
							</ui:layout>
						</ui:pane>

						<ui:pane position="center">
							<div align="center"><br/><br/>
								<table>
									<tr><td><ui:button id="addOne" style="display:block;">&nbsp&nbsp&gt&nbsp</ui:button></td></tr>
									<tr><td><ui:button id="addAll" style="display:block;">&nbsp&gt&gt&nbsp</ui:button></td></tr>
									<tr><td><ui:button id="removeOne" style="display:block;">&nbsp&lt&nbsp&nbsp</ui:button></td></tr>
									<tr><td><ui:button id="removeAll" style="display:block;">&nbsp&lt&lt&nbsp</ui:button></td></tr>
								</table>
							</div>
						</ui:pane>

						<ui:pane position="right" style="width:160px">
							<ui:panel type="titleList" desc="选择员工">
								<ui:content>
									<ui:table dataset="selectedStaffList" id="selectedStaffTable"></ui:table>
								</ui:content>
							</ui:panel>
						</ui:pane>
					</ui:layout>
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_Confirm">确定</ui:button>
					<ui:button id="btn_Cancel">取消</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
