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
							<ZTESOFT:column width="55%" display="true" displayText="区域名称" propertyName="regionName" />
							<ZTESOFT:column width="10%" display="true" displayText="区域编码" propertyName="regionCode" />
							<ZTESOFT:column width="35%" display="true" displayText="区域描述" propertyName="regionDesc" />
							<ZTESOFT:column width="" display="false" displayText="区域标识" propertyName="regionId" />							
							<ZTESOFT:column width="" display="false" displayText="上级区域标识" propertyName="parentRegionId" />
							<ZTESOFT:column width="" display="false" displayText="区域级别" propertyName="regionLevel" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_Confirm">确定</ui:button>
					<ui:button id="btn_Cancel">取消</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
