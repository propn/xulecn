<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
<head>
<title>������Դ�ļ�</title>
<META HTTP-EQUIV="library" CONTENT="kernel">
<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
<ui:import library="kernel"></ui:import>

<script>
	function btn_makeDir_onClick(){
	
		var siteUrlValue=makepagekeys.getValue('siteUrl');
		var dirUrlValue=makepagekeys.getValue('dirUrl');
		
		var callBackRefresh=function(reply){
            window.alert('����Ŀ¼��Դ�ļ��ɹ�!');
        } 
        NDAjaxCall.getSyncInstance().remoteCall("PageKeyService", "makeDirKeys", [dirUrlValue],callBackRefresh); 
	}
	
	function btn_makePage_onClick(){
	
		var siteUrlValue=makepagekeys.getValue('siteUrl');
		var pageUrlValue=makepagekeys.getValue('pageUrl');
		
		var callBackRefresh=function(reply){
            window.alert('����ҳ����Դ�ļ��ɹ�!');
        } 
        NDAjaxCall.getSyncInstance().remoteCall("PageKeyService", "makePageKeys", [pageUrlValue],callBackRefresh); 
	}
	
	function btn_cancel_onClick(){
		window.close();
	}
</script>
</head>

<body>

		<div id="datasetDefine">
			<ui:dataset datasetId="makepagekeys">
					<ui:field field="siteUrl" label="��վURL" ></ui:field>
					<ui:field field="dirUrl" label="Ŀ¼URL" ></ui:field>
					<ui:field field="pageUrl" label="ҳ��URL" ></ui:field>
			</ui:dataset>
		</div>
		
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="������Դ�ļ�">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
								<ui:form dataset="makepagekeys" labelLayout="25%" inputLayout="70%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
								<ui:button id="btn_makeDir">����Ŀ¼</ui:button>
								<ui:button id="btn_makePage">����ҳ��</ui:button>
								<ui:button id="btn_cancel">ȡ��</ui:button>	
						</ui:pane>
					</ui:layout>
				</ui:content>		
			</ui:panel>  
		</div>

</body>
</html>