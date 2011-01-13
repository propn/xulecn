<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
<head>
<title>生成资源文件</title>
<META HTTP-EQUIV="library" CONTENT="kernel">
<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
<ui:import library="kernel"></ui:import>

<script>
	function btn_makeDir_onClick(){
	
		var siteUrlValue=makepagekeys.getValue('siteUrl');
		var dirUrlValue=makepagekeys.getValue('dirUrl');
		
		var callBackRefresh=function(reply){
            window.alert('生成目录资源文件成功!');
        } 
        NDAjaxCall.getSyncInstance().remoteCall("PageKeyService", "makeDirKeys", [dirUrlValue],callBackRefresh); 
	}
	
	function btn_makePage_onClick(){
	
		var siteUrlValue=makepagekeys.getValue('siteUrl');
		var pageUrlValue=makepagekeys.getValue('pageUrl');
		
		var callBackRefresh=function(reply){
            window.alert('生成页面资源文件成功!');
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
					<ui:field field="siteUrl" label="网站URL" ></ui:field>
					<ui:field field="dirUrl" label="目录URL" ></ui:field>
					<ui:field field="pageUrl" label="页面URL" ></ui:field>
			</ui:dataset>
		</div>
		
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="生成资源文件">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
								<ui:form dataset="makepagekeys" labelLayout="25%" inputLayout="70%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
								<ui:button id="btn_makeDir">生成目录</ui:button>
								<ui:button id="btn_makePage">生成页面</ui:button>
								<ui:button id="btn_cancel">取消</ui:button>	
						</ui:pane>
					</ui:layout>
				</ui:content>		
			</ui:panel>  
		</div>

</body>
</html>