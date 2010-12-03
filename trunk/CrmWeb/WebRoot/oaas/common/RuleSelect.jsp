<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList">
		<title>����ѡ��</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/RuleSelect.js"></script>
	</head>
	<body>
	
	<div id="datasetDefine">
		<ui:dataset datasetId="ruleInfoList" loadDataAction="RuleEngineService" async="false" loadDataActionMethod="getRuleInfosByCatalogId" staticDataSource="true">
			<ui:field field="id" label="���" visible="true"></ui:field>
			<ui:field field="name" label="����"></ui:field>
			<ui:field field="ruleTypeId" label="��������"></ui:field>			
			<ui:field field="ruleCatgId" label="����Ŀ¼" visible="false"></ui:field>
			<ui:field field="content" label="����" visible="false"></ui:field>
			<ui:field field="ruleDesc" label="��������" visible="true"></ui:field>
		</ui:dataset>		
	</div>

<ui:layout type="border">
	<ui:pane position="center">
		<ui:layout type="border">
			<ui:pane position="left" style="width:250px">
				<ui:panel type="titleList" desc="����Ŀ¼">
					<ui:content>
						<ZTESOFT:treelist id="catalogTree" class="treelist" onItemClick="initRuleInfos()" showImage="false" showBorder="false" width="250" height="400" contBorder="false" showImage=false showHead="true">
							<ZTESOFT:columns>
								<ZTESOFT:column width="100%" height="100%" display="true" displayText="ҵ�������" propertyName="name" />
								<ZTESOFT:column width="" display="false" displayText="���" propertyName="id" />
								<ZTESOFT:column width="" display="false" displayText="��Ŀ¼���" propertyName="parentId" />									
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="center">
				<ui:panel type="titleList" desc="�����б�">
					<ui:content>
						<ui:table dataset="ruleInfoList"></ui:table>
					</ui:content>
				</ui:panel>			
			</ui:pane>
		</ui:layout>
	</ui:pane>
	<ui:pane position="bottom">
		<ui:button id="btn_confirm">ȷ��</ui:button>
		<ui:button id="btn_cancel">ȡ��</ui:button>
	</ui:pane>
</ui:layout>
	
</body>