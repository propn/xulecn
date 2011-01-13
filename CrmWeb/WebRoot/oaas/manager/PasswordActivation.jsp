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
					alert("请输入员工号!");
					return ;
				}
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ) {
					var returnValue = reply.getResult() ;
					if( returnValue == 0 ){
						alert("工号" + staffCode.getValue("staffCode") + "已经被成功激活!");
					}else if( returnValue == 1 ) {
						alert("您输入的工号在系统中不存在!");
					}else if( returnValue == 2 ){
						alert("您输入的工号已经是有效状态,不需激活!");
					}
				}
				
				ajaxCall.remoteCall( "PartyService","passwordActivation",[staffCode.getValue("staffCode")],callBack);
			}
		</script>
	</head>

	<body>
		<ui:dataset datasetId="staffCode" staticDataSource="true">
			<ui:field field="staffCode" label="工号"></ui:field>
		</ui:dataset>
		<ui:layout type="border">
			<ui:pane position="top" style="height:80px">
				<ui:panel type="titleList" desc="员工激活">
					<ui:content>
						<ui:layout type="border">
							<ui:pane position="right" style="width:300px">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="staffCode" submit="confirm" id="staffCodeForm" labelLayout="30%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="confirm">激活</ui:button>
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
