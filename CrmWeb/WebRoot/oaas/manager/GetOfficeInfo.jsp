<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
	</head>
	
	<script>
		function page_onLoad(){
			var objParam = window.dialogArguments ;
			officeInfo.setValue("officeDesc",objParam.officeDesc);
			officeInfo.setValue("officeAddr",objParam.officeAddr);
		}
		
		function btnOK_onClick(){
			if( officeInfo.getValue("officeDesc") == "" ){
				alert("������칫�ص�����!");
				return ;
			}
			var objReturn = new Object();
			objReturn.officeDesc = officeInfo.getValue("officeDesc"); 
			objReturn.officeAddr = officeInfo.getValue("officeAddr"); 
			window.returnValue = objReturn;
			window.close() ;
		}
		function btnCancel_onClick(){
			window.returnValue = null ;
			window.close();
		}
	</script>
	
	<body>
		<ui:dataset datasetId="officeInfo">
			<ui:field field="officeDesc" label="�칫�ص�����"></ui:field>
			<ui:field field="officeAddr" label="�칫�ص��ַ"></ui:field>
		</ui:dataset>
		<ui:layout type="border">
			<ui:pane position="top" style="height:20px">
			
			</ui:pane>
			<ui:pane position="center">
				<ui:form dataset="officeInfo" inputLayout="82%" labelLayout="18%" ></ui:form>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btnOK">ȷ��</ui:button>
				<ui:button id="btnCancel">ȡ��</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
