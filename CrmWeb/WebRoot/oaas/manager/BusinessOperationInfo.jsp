<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<ui:dataset datasetId="Ds_BusinessOperationInfo" loadDataAction="com.ztesoft.crm.cs.cc.service.CcQueryService" loadDataActionMethod="businessOperationInfoQuery" excel="true" paginate="true">				
				<ui:field field="operationId" label="运营标识" visible="false" ></ui:field>
				<ui:field field="businessId" label="营业区标识" visible="false" ></ui:field>
				<ui:field field="lanId" label="本地网" attrCode="DC_AUTHORIZED_LAN" editable="true" ></ui:field>
				<ui:field field="businessName" label="营业区" ></ui:field>
				<ui:field field="crmName" label="CRM系统营业厅名称"></ui:field>
				<ui:field field="partyRoleId" label="录入人" visible="false"></ui:field>
				<ui:field field="custInfoTotal" label="客户资料总数"></ui:field>
				<ui:field field="custInfoTimelyCount" label="客户资料完整及时数"></ui:field>
				<ui:field field="custInfoTimelyRate" label="客户资料完整及时率"></ui:field>
				<ui:field field="custInfoAuditTotal" label="客户资料稽核总数"></ui:field>
				<ui:field field="custInfoAuditErrCount" label="客户资料稽核差错数量"></ui:field>
				<ui:field field="custInfoErrRate" label="客户资料差错率" ></ui:field>
				<ui:field field="removeKeepCount" label="拆机挽留记录数" ></ui:field>
				<ui:field field="removeKeepSuccessCount" label="拆机挽留成功数"></ui:field>
				<ui:field field="removeKeepSuccessRate" label="拆机挽留成功率"></ui:field>
				<ui:field field="custEvaluateCount" label="评价的客户数"></ui:field>
				<ui:field field="custSatisfationCount" label="满意客户数"></ui:field>
				<ui:field field="custSatisfationRate" label="客户满意度"></ui:field>
				<ui:field field="enterDate" label="录入时间" visible="false" ></ui:field>
				
			   <ui:parameter parameterId="lanId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessId" value=""></ui:parameter>
			   <ui:parameter parameterId="crmBusinessHallName" value=""></ui:parameter>
			   <ui:parameter parameterId="startDate" value=""></ui:parameter>
			   <ui:parameter parameterId="endDate" value=""></ui:parameter>
</ui:dataset>