<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<ui:dataset datasetId="Ds_BusinessOperationInfo" loadDataAction="com.ztesoft.crm.cs.cc.service.CcQueryService" loadDataActionMethod="businessMapInfoQuery" excel="true" paginate="true">				
				<ui:field field="mapId" label="映射标识" visible="false" ></ui:field>
				<ui:field field="businessId" label="营业区标识" visible="false" ></ui:field>
				<ui:field field="lanId" label="本地网" attrCode="DC_AUTHORIZED_LAN" editable="true" ></ui:field>
				<ui:field field="businessName" label="营业区" ></ui:field>
				<ui:field field="crmId" label="CRM系统营业厅ID"></ui:field> 
				<ui:field field="crmName" label="CRM系统营业厅名称"></ui:field>
				<ui:field field="hbId" label="HB系统营业厅名称"></ui:field>
				<ui:field field="hbName" label="HB系统营业厅名称"></ui:field>
				<ui:field field="autoPayId" label="自助缴费系统营业厅ID"></ui:field>
				<ui:field field="autoPayName" label="自助缴费系统营业厅名称"></ui:field>
				<ui:field field="customerId" label="前台客户排队系统营业厅ID"></ui:field>
				<ui:field field="customerName" label="前台客户排队系统营业厅名称"></ui:field>
				<ui:field field="custServId" label="号百投诉管理系统营业厅ID" ></ui:field>
				<ui:field field="custServName" label="号百投诉管理系统营业厅名称" ></ui:field>
			 		   				
			   <ui:parameter parameterId="lanId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessHallName" value=""></ui:parameter>
			</ui:dataset>