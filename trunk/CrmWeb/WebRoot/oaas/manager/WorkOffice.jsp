<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<script language="javascript" src="js/WorkOffice.js"></script>
		<ui:import library=""></ui:import>
	</head>

	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="officeList" pageIndex="1" pageSize="25" loadDataAction="PartyService" loadDataActionMethod="queryOfficeList" autoLoadPage="true" staticDataSource="false" loadAsNeeded="false" paginate="true">
				<ui:field field="officeId" label="办公地点标识" visible="false"></ui:field>
				<ui:field field="officeDesc" label="办公地点名称"></ui:field>
				<ui:field field="officeAddr" label="办公地点地址" size="100"></ui:field>
				<ui:parameter parameterId="officeDesc" type="string" value=""></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="machineList" loadDataAction="PartyService" loadDataActionMethod="getOfficeMachinesByOfficeId" staticDataSource="true">
				<ui:field field="machineId" label="机位标识" visible="false"></ui:field>
				<ui:field field="officeId" label="办公地点标识" visible="false"></ui:field>
				<ui:field field="macAddr" label="MAC地址"></ui:field>
				<ui:field field="ipAddr" label="IP地址"></ui:field>
				<ui:parameter parameterId="officeId" type="string" value=""></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="machineInfor" staticDataSource="true" readOnly="true">
				<ui:field field="macAddr" label="MAC地址" required="true" validType="require" validMsg="请输入MAC地址!"></ui:field>
				<ui:field field="ipAddr" label="IP地址" required="true" validType="require" validMsg="请输入IP地址!"></ui:field>
			</ui:dataset>			
		</div>
		<ui:layout type="border">
			<ui:pane position="left" style="width:300px">
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:layout type="border">
							<ui:pane position="center">
								<ui:panel type="titleList" desc="办公地点列表">
									<ui:content>
										<div style="text-align:left">
											办公地点名称:
											<input type="text" id="queryInput" style="border: 1px solid #666666;"></input>
											<ui:button id="doQuery">查询</ui:button><br/>
										</div>
										<div style="text-align:right">
											<ui:button id="addOffice">增加</ui:button>
											<ui:button id="editOffice">编辑</ui:button>
											<ui:button id="deleteOffice">删除</ui:button>											
										</div>
										<ui:table dataset="officeList" maxRow="25"></ui:table>
									</ui:content>
								</ui:panel>
							</ui:pane>
							<ui:pane position="bottom">
								<div class="customerpilot" extra="customerpilot" id="officeListPilot" dataset="officeList"></div>
							</ui:pane>
						</ui:layout>
					</ui:pane>
				</ui:layout>
			</ui:pane>
			<ui:pane position="center">
				<ui:layout type="border">
					<ui:pane position="top" style="height:50px">
						<ui:panel type="titleList" desc="机位信息">
							<ui:content>
								<div style="text-align:right">
									<ui:button id="addMachine">增加</ui:button>
									<ui:button id="editMachine">编辑</ui:button>
									<ui:button id="deleteMachine">删除</ui:button>											
								</div>
							</ui:content>
						</ui:panel>
					</ui:pane>
					<ui:pane position="center">
						<ui:table dataset="machineList"></ui:table>
					</ui:pane>
					<ui:pane position="bottom">
						<ui:form dataset="machineInfor" submit="saveMachine" id="machineInforForm" inputLayout="30%" labelLayout="19%"></ui:form>
						<ui:button id="saveMachine">确定</ui:button>
						<ui:button id="cancelMachine">取消</ui:button>
					</ui:pane>
				</ui:layout>
			</ui:pane>
		</ui:layout>
	</body>
</html>
