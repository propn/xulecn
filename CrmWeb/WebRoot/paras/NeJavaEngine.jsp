<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/NeJavaEngine.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	
			<ui:dataset datasetId="dsNeJavaEngineForm"  readOnly="true" 
				loadDataAction="NeJavaEngineService" loadDataActionMethod="getNeJavaEngineById" 
				readOnly="false" autoLoadPage="true" async="true" loadAsNeeded="true" >
				<ui:field label="id" field="id"  visible="false" ></ui:field>
				<ui:field label="动态组件名称" field="name"  visible="true" required="true"  validMsg="请输入动态组件名称!" ></ui:field>
				<ui:field label="动态组件描述" field="rule_desc"  visible="true"></ui:field>
				<ui:field label="动态组件内容" field="rule_content"  visible="true"></ui:field>
				<ui:field label="状态" field="state"  visible="false" defaultValue="10I"></ui:field>
				<ui:field label="class_name" field="class_name"  visible="false"></ui:field>
				<ui:field label="创建时间" field="ceate_date"  visible="false"></ui:field>
				<ui:parameter parameterId="id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				
				<ui:pane position="center">
					<ui:panel type="titleList" desc="动态组件">
						<ui:content>
							<ui:form dataset="dsNeJavaEngineForm"  labelLayout="30%" inputLayout="60%"></ui:form>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom"  style="height:25px;">
					<div style="clear:both;text-align:center;">
						<ui:button id="saveNeJavaEngine">保存</ui:button>&nbsp;&nbsp;
						<ui:button id="cancelNeJavaEngine">取消</ui:button>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
