<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<ui:dataset datasetId="Ds_BusinessOperationInfo" loadDataAction="com.ztesoft.crm.cs.cc.service.CcQueryService" loadDataActionMethod="businessMapInfoQuery" excel="true" paginate="true">				
				<ui:field field="mapId" label="ӳ���ʶ" visible="false" ></ui:field>
				<ui:field field="businessId" label="Ӫҵ����ʶ" visible="false" ></ui:field>
				<ui:field field="lanId" label="������" attrCode="DC_AUTHORIZED_LAN" editable="true" ></ui:field>
				<ui:field field="businessName" label="Ӫҵ��" ></ui:field>
				<ui:field field="crmId" label="CRMϵͳӪҵ��ID"></ui:field> 
				<ui:field field="crmName" label="CRMϵͳӪҵ������"></ui:field>
				<ui:field field="hbId" label="HBϵͳӪҵ������"></ui:field>
				<ui:field field="hbName" label="HBϵͳӪҵ������"></ui:field>
				<ui:field field="autoPayId" label="�����ɷ�ϵͳӪҵ��ID"></ui:field>
				<ui:field field="autoPayName" label="�����ɷ�ϵͳӪҵ������"></ui:field>
				<ui:field field="customerId" label="ǰ̨�ͻ��Ŷ�ϵͳӪҵ��ID"></ui:field>
				<ui:field field="customerName" label="ǰ̨�ͻ��Ŷ�ϵͳӪҵ������"></ui:field>
				<ui:field field="custServId" label="�Ű�Ͷ�߹���ϵͳӪҵ��ID" ></ui:field>
				<ui:field field="custServName" label="�Ű�Ͷ�߹���ϵͳӪҵ������" ></ui:field>
			 		   				
			   <ui:parameter parameterId="lanId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessHallName" value=""></ui:parameter>
			</ui:dataset>