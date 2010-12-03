<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/Position.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="positionInfo" readOnly="true">
				<ui:field field="positionId" label="岗位标识" visible="false"></ui:field>
				<ui:field field="positionName" label="岗位名称" required="true" validType="require" validMsg="请输入岗位名称!"></ui:field>
				<ui:field field="positionDesc" label="岗位描述" required="true" validType="require" validMsg="请输入岗位描述!"></ui:field>
				<ui:field field="state" label="状态" dropDown="positionStateSelect"></ui:field>
				<ui:field field="stateDate" label="状态时间" type="date" visible="false"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="positionStateSelect" attrCode="COMM_RECORD_STATE" staticDataSource="false"></ui:dropdown>
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="岗位规格管理">
						<ui:content>
							<ui:button id="addPosition">添加</ui:button>
							<ui:button id="editPosition">编辑</ui:button>
							<ui:button id="deletePosition">删除</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="positionTreeView" class="treelist" onItemClick="clickPosition()" showBorder="true" contBorder="true" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="30%" display="true" displayText="岗位标识" propertyName="positionId" />
							<ZTESOFT:column width="30%" display="true" displayText="岗位名称" propertyName="positionName" />
							<ZTESOFT:column width="40%" display="true" displayText="岗位描述" propertyName="positionDesc" />
							<ZTESOFT:column width="" display="false" displayText="状态" propertyName="state" />
							<ZTESOFT:column width="" display="false" displayText="状态时间" propertyName="stateDate" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height:250px;">
					<ui:tabpane id="pane1">
						<ui:tabpage desc="岗位规格编辑">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="btn_commitPosition" dataset="positionInfo" id="positionForm" labelLayout="15%" inputLayout="35%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="btn_commitPosition">确定</ui:button>
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
