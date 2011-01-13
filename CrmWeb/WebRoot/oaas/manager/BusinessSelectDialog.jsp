<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title>Ӫҵ��ѡ��</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/BusinessSelectDialog.js"></script>
	</head>
	<body>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:panel type="titleList" desc="Ӫҵ��ѡ��">
						<ui:content>
					<ZTESOFT:treelist id="regionTreeView" onItemClick="clickRegion()" class="treelist">
						<ZTESOFT:columns>
							<ZTESOFT:column width="24%" display="true" displayText="��������" propertyName="regionName" />
							<ZTESOFT:column width="24%" display="true" displayText="�������" propertyName="regionCode" />
							<ZTESOFT:column width="50%" display="true" displayText="��������" propertyName="regionDesc" />
							<ZTESOFT:column width="" display="false" displayText="�ϼ������ʶ" propertyName="parentRegionId" />
							<ZTESOFT:column width="" display="false" displayText="�����ʶ" propertyName="regionId" />
							<ZTESOFT:column width="" display="false" displayText="���򼶱�" propertyName="regionLevel" />
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
