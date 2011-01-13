<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/RegionSelect.js"></script>
	</head>
	<body>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ZTESOFT:treelist id="regionTreeView" checkChildren="false" onItemChecked="regionChecked()" class="treelist" showImage="false" showCheck="true" showBorder="true" contBorder="true" showImage=false showHead=true>
						<ZTESOFT:columns>
							<ZTESOFT:column width="55%" display="true" displayText="��������" propertyName="regionName" />
							<ZTESOFT:column width="10%" display="true" displayText="�������" propertyName="regionCode" />
							<ZTESOFT:column width="35%" display="true" displayText="��������" propertyName="regionDesc" />
							<ZTESOFT:column width="" display="false" displayText="�����ʶ" propertyName="regionId" />							
							<ZTESOFT:column width="" display="false" displayText="�ϼ������ʶ" propertyName="parentRegionId" />
							<ZTESOFT:column width="" display="false" displayText="���򼶱�" propertyName="regionLevel" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_Confirm">ȷ��</ui:button>
					<ui:button id="btn_Cancel">ȡ��</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
