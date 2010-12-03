<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<META HTTP-EQUIV="library" CONTENT="kernel">
		<title>导出Excel文件</title>
		<script language="JavaScript" src="../../public/components/common2.js" charset="gb2312"></script>
		<ui:import library=""></ui:import>
		<script>
			function close_onClick(){
				window.close();
			}
			function export_onClick(){
				var excelURL = window.dialogArguments;
				if(excelURL){
                	window.frames["exportToExcel"].location = excelURL;
				}
			}
		</script>
	</head>
	<body>
     	<ui:panel type="titleList" desc="导出说明">
     		<ui:content>
     			<div style="text-align:center; padding-top: 20px;">
     				<ui:button id="export">导出</ui:button>
     				<ui:button id="close">关闭</ui:button>
     			</div>
     			<iframe id="exportToExcel" style="display:none;"></iframe>
     		</ui:content>
     	</ui:panel>
        <script >
            window.onLoad=export_onClick();
        </script>
	</body>
</html>
