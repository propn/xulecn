<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList">
		<title>crm_param����ˢ��</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
<script language="JavaScript">
        function btn_qry_onClick(){
        	var paramCode = ds_fresh.getValue("paramCode");;
        	if(paramCode==null||""==paramCode){
        		alert("�������������");
        		return;
        	}
        	NDAjaxCall.getSyncInstance().remoteCall("StaticAttrService", "getCurrentParamValue", [paramCode], function callBack(reply){
				var value = reply.getResult();
				if(value){
					ds_fresh.setValue("paramValue",value);
				}
			});
        	
        	
        }
        
        function btn_fresh_onClick(){
        	var paramCode = ds_fresh.getValue("paramCode");;
        	if(paramCode==null||""==paramCode){
        		alert("�������������");
        		return;
        	}
        	NDAjaxCall.getSyncInstance().remoteCall("StaticAttrService", "updateCurrentParamValue", [paramCode], function callBack(reply){
				var value = reply.getResult();
				if(value){
					ds_fresh.setValue("paramValue",value);
				} 
			});
        	
        }        
       
</script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="ds_fresh">
				<ui:field field="paramCode" label="��������"></ui:field>
				<ui:field field="paramValue" label="��ǰֵ" readOnly = "true"></ui:field>
			</ui:dataset>
		</div>
		<div id="layoutDefine">

			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="����ˢ��--ˢ��crm-param.property�ļ��е�ָ������ȡֵ���ڴ�">
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ui:form dataset="ds_fresh"></ui:form>
				</ui:pane>
				<ui:pane position = "bottom">
					<ui:button id="btn_qry">��ѯȡֵ</ui:button>
					<ui:button id="btn_fresh">ˢ��</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>