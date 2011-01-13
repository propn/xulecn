<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList">
		<title>crm_param参数刷新</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
<script language="JavaScript">
        function btn_qry_onClick(){
        	var paramCode = ds_fresh.getValue("paramCode");;
        	if(paramCode==null||""==paramCode){
        		alert("请输入参数编码");
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
        		alert("请输入参数编码");
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
				<ui:field field="paramCode" label="参数编码"></ui:field>
				<ui:field field="paramValue" label="当前值" readOnly = "true"></ui:field>
			</ui:dataset>
		</div>
		<div id="layoutDefine">

			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="参数刷新--刷新crm-param.property文件中的指定参数取值到内存">
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ui:form dataset="ds_fresh"></ui:form>
				</ui:pane>
				<ui:pane position = "bottom">
					<ui:button id="btn_qry">查询取值</ui:button>
					<ui:button id="btn_fresh">刷新</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>