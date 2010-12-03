<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<script language="javascript" src="js/ChannelRegionSelect.js"></script>
		<title>渠道片区</title>
		<script language="JavaScript" src="../../public/components/common2.js" charset="gb2312"></script>
		<ui:import library=""></ui:import>
	</head>
	<body>

		<ui:layout type="border">
			<ui:pane position="center">
				<ui:panel type="titleList" desc="渠道片区选择">
					<ui:content>
						<ZTESOFT:treelist id="chnRegionTreeView" class="treelist" onItemClick="clickChnRegion()" 
						showCheck="true" onItemChecked="checkChnRegion()" checkParent="false" checkChildren="false" 
						showImage="true" showBorder="false" contBorder="true" width="100%" height="305px" 
						showHead="true">
							<ZTESOFT:columns>
								<ZTESOFT:column width="65%" display="true" displayText="片区名称" propertyName="name" />
								<ZTESOFT:column width="20%" display="true" displayText="片区类型" propertyName="chnRegionTypeName" />
								<ZTESOFT:column width="15%" display="true" displayText="状态" propertyName="stateName" />
								<ZTESOFT:column width="" display="false" displayText="状态" propertyName="state" />
								<ZTESOFT:column width="" display="false" displayText="staff id" propertyName="staffId" />
								<ZTESOFT:column width="" display="false" displayText="片区级别" propertyName="chnRegionLevel" />
								<ZTESOFT:column width="" display="false" displayText="片区标识" propertyName="chnRegionId" />
								<ZTESOFT:column width="" display="false" displayText="管理客户类型" propertyName="custType" />								
								<ZTESOFT:column width="" display="false" displayText="" propertyName="chnRegionType" />
								<ZTESOFT:column width="" display="false" displayText="片区编码" propertyName="chnRegionCode" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btnConfirm">确定</ui:button>
				<ui:button id="btnCancel">取消</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
