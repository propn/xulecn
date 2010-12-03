<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title>����ѡ��</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/ResourceRegionSelect.js"></script>
	</head>
	<body>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:panel type="titleList" desc="����ѡ��">
						<ui:content>
							<ZTESOFT:treelist id="regionTreeView" checkChildren="false" showTitleCheck= "false" onItemClick="clickRegion()" onItemChecked="checkRegion()" class="treelist" showImage="false" showCheck="true" showBorder="false" contBorder="true" showImage=false showHead=true>
								<ZTESOFT:columns>
									<ZTESOFT:column width="60%" display="true" displayText="��������" propertyName="regionName" />
									<ZTESOFT:column width="20%" display="true" displayText="��������" propertyName="regionDesc" />
									<ZTESOFT:column width="10%" display="true" displayText="���򼶱�" propertyName="regionLevelName" />
									<ZTESOFT:column width="10%" display="true" displayText="�����ʶ" propertyName="regionId" />
									<ZTESOFT:column width="" display="false" displayText="�������" propertyName="regionCode" />
									<ZTESOFT:column width="" display="false" displayText="���򼶱�" propertyName="regionLevel" />
									<ZTESOFT:column width="" display="false" displayText="Ȩ�ޱ�־" propertyName="privilegeFlag" />
									<ZTESOFT:column width="" display="false" displayText="�ϼ������ʶ" propertyName="parentRegionId" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_Confirm">ȷ��</ui:button>
					<ui:button id="btn_Cancel">ȡ��</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
