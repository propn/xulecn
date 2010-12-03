<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,validator,calendar">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/AddressDialog.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<!--  
			ui:dataset datasetId="logicalAddrList" loadDataAction="PartyService" loadDataActionMethod="getLogicalAddrsByAddrId" 
			async="false" staticDataSource="true" 
			-->
			<ui:dataset datasetId="logicalAddrList" editable="true" staticDataSource="true">
				<ui:field field="logicalAddrId" label="逻辑地址标识" visible="false"></ui:field>
				<ui:field field="addrId" label="地址标识" visible="false"></ui:field>
				<ui:field field="logicalAddrType" label="逻辑地址类型"  dropDown="logicalAddrTypeDropdown"></ui:field>
				<ui:field field="logicalAddrDeta" label="逻辑地址详细信息"></ui:field>
			</ui:dataset>

			<ui:dropdown id="logicalAddrTypeDropdown" attrCode="LOGICAL_ADDR_TYPE" staticDataSource="false"></ui:dropdown>

			<!-- 联系信息Dataset-->
			<ui:dataset datasetId="contactInfor" editable="true">
				<ui:field field="addrId" label="地址标识" visible="false"></ui:field>
				<ui:field field="provinceName" label="省名" required="true" validType="require" validMsg="请输入省名"></ui:field>
				<ui:field field="cityName" label="城市名" required="true" validType="require" validMsg="请输入城市名"></ui:field>
				<ui:field field="countyName" label="县区" required="true" validType="require" validMsg="请输入县区名"></ui:field>
				<ui:field field="streetName" label="街道名" required="true" validType="require" validMsg="请输入街道名"></ui:field>
				<ui:field field="streetNbr" label="门牌号" required="true" validType="require" validMsg="请输入门牌号"></ui:field>
				<ui:field field="deta" label="详细信息" required="true" validType="require" validMsg="请输入详细信息"></ui:field>
				<ui:field field="postcode" label="邮政编码" required="true" validType="require" validMsg="请输入邮政编码"></ui:field>
				<ui:field field="alias" label="地址别名"></ui:field>
			</ui:dataset>
		</div>
		
		
		
				<ui:layout type="border">
					<ui:pane position="top" style="height:140px">
						<ui:panel type="titleList" desc="地址信息" >
							<ui:content>
								<ui:form dataset="contactInfor" id="contactForm" labelLayout="15%" inputLayout="34%"></ui:form>
							</ui:content>
						</ui:panel>
					</ui:pane>
					
					<ui:pane position="center">
								<ui:bar type="search" desc="逻辑地址">
									<ui:content>
										<ui:button id="addLogicAddr">增加</ui:button>
										<ui:button id="deleteLogicAddr">删除</ui:button>
									</ui:content>
								</ui:bar>
								<ui:table dataset="logicalAddrList"></ui:table>
					</ui:pane>
					
					<ui:pane position="bottom">
						<ui:button id="commitAddr">保存</ui:button>
						<ui:button id="closeDialog">关闭</ui:button>						
					</ui:pane>
				</ui:layout>

	</body>
</html>