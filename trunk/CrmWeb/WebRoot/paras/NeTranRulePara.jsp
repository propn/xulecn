<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title>ת������Դ������</title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/NeTranRulePara.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	

			<ui:dataset datasetId="dsNeTranRuleParaForm"  readOnly="true" 
				loadDataAction="NeTranRuleParaService" loadDataActionMethod="getNeTranRuleParaById" 
				readOnly="false" autoLoadPage="true" async="true" loadAsNeeded="true" >
				<ui:field label="tran_rule_id" field="tran_rule_id"  visible="false"></ui:field>
				<ui:field label="Դ������Id" field="para_id"  visible="false"></ui:field>
				<ui:field label="Դ������" field="para_name"  visible="true" keyField="para_Id" popup="true" required="true" validMsg="��ѡ��Դ������"></ui:field>
				<ui:field label="Դ������˳��" field="seq"  visible="true" type="int" required="true" validMsg="������Դ������˳��"></ui:field>
				<ui:field label="����" field="code"  visible="false"></ui:field>
				<ui:parameter parameterId="tran_rule_id" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="para_id" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="seq" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				
				<ui:pane position="center">
					<ui:panel type="titleList" desc="ת������Դ������">
						<ui:content>
							<ui:form dataset="dsNeTranRuleParaForm"  labelLayout="30%" inputLayout="60%"></ui:form>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom"  style="height:25px;">
					<div style="clear:both;text-align:center;">
						<ui:button id="saveNeTranRulePara">����</ui:button>&nbsp;&nbsp;
						<ui:button id="cancelNeTranRulePara">ȡ��</ui:button>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
