<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<ui:dataset datasetId="Ds_BusinessOperationInfo" loadDataAction="com.ztesoft.crm.cs.cc.service.CcQueryService" loadDataActionMethod="businessOperationInfoQuery" excel="true" paginate="true">				
				<ui:field field="operationId" label="��Ӫ��ʶ" visible="false" ></ui:field>
				<ui:field field="businessId" label="Ӫҵ����ʶ" visible="false" ></ui:field>
				<ui:field field="lanId" label="������" attrCode="DC_AUTHORIZED_LAN" editable="true" ></ui:field>
				<ui:field field="businessName" label="Ӫҵ��" ></ui:field>
				<ui:field field="crmName" label="CRMϵͳӪҵ������"></ui:field>
				<ui:field field="partyRoleId" label="¼����" visible="false"></ui:field>
				<ui:field field="custInfoTotal" label="�ͻ���������"></ui:field>
				<ui:field field="custInfoTimelyCount" label="�ͻ�����������ʱ��"></ui:field>
				<ui:field field="custInfoTimelyRate" label="�ͻ�����������ʱ��"></ui:field>
				<ui:field field="custInfoAuditTotal" label="�ͻ����ϻ�������"></ui:field>
				<ui:field field="custInfoAuditErrCount" label="�ͻ����ϻ��˲������"></ui:field>
				<ui:field field="custInfoErrRate" label="�ͻ����ϲ����" ></ui:field>
				<ui:field field="removeKeepCount" label="���������¼��" ></ui:field>
				<ui:field field="removeKeepSuccessCount" label="��������ɹ���"></ui:field>
				<ui:field field="removeKeepSuccessRate" label="��������ɹ���"></ui:field>
				<ui:field field="custEvaluateCount" label="���۵Ŀͻ���"></ui:field>
				<ui:field field="custSatisfationCount" label="����ͻ���"></ui:field>
				<ui:field field="custSatisfationRate" label="�ͻ������"></ui:field>
				<ui:field field="enterDate" label="¼��ʱ��" visible="false" ></ui:field>
				
			   <ui:parameter parameterId="lanId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessId" value=""></ui:parameter>
			   <ui:parameter parameterId="crmBusinessHallName" value=""></ui:parameter>
			   <ui:parameter parameterId="startDate" value=""></ui:parameter>
			   <ui:parameter parameterId="endDate" value=""></ui:parameter>
</ui:dataset>