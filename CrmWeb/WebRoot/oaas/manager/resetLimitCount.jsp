<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>

		<script>
			function confirm_onClick(){
				if( staffCode.getValue("staffCode") == "" ){
					alert("������Ա����!");
					return ;
				}
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ) {
					var returnValue = reply.getResult() ;
					if( returnValue == 0 ){
						alert("����" + staffCode.getValue("staffCode") + "�Ѿ������õ�¼����!");
					}else if( returnValue == 1 ) {
						alert("������Ĺ�����ϵͳ�в�����!");
					}
				}
				
				ajaxCall.remoteCall( "PartyService","reSetLoginedLimit",[staffCode.getValue("staffCode")],callBack);
			}
		</script>
	</head>

	<body>
		<ui:dataset datasetId="staffCode" staticDataSource="true">
			<ui:field field="staffCode" label="����"></ui:field>
		</ui:dataset>
		<ui:layout type="border">
			<ui:pane position="top" style="height:80px">
				<ui:panel type="titleList" desc="���õ�¼����">
					<ui:content>
						<ui:layout type="border">
							<ui:pane position="right" style="width:300px">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="staffCode" submit="confirm" id="staffCodeForm" labelLayout="30%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="confirm">���õ�¼����</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:pane>
						</ui:layout>
					</ui:content>
				</ui:panel>
			</ui:pane>
		</ui:layout>
	</body>
</html>
