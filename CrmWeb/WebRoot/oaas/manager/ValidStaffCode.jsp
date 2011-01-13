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
			function page_onLoad(){
				var staffCode = window.dialogArguments ;
				if( staffCode ){
					txtStaffCode.value = staffCode ;
				}
			}
			function btnCheckStaffCode_onClick(){
				if( txtStaffCode.value == "" ){
					alert("请输入要校验的工号!");
					return;
				}
				var ajaxCall = new NDAjaxCall(true);
				var callBack = function(reply){
					if( reply.getResult() ){
						alert("系统中已经存在工号" + txtStaffCode.value ) ;
					}	else {
						alert("系统中不存在工号" + txtStaffCode.value ) ;
					}
					window.close();
				}
				var txt = document.getElementById("txtStaffCode");
				var staffCode = txt.value.toUpperCase() ;
				ajaxCall.remoteCall("PartyService","checkStaffCode",[staffCode],callBack);
			}
			function btnReturn_onClick(){
				window.close() ;
			}
		</script>
	</head>

	<body>
		<ui:panel type="titleList" desc="工号校验">
			<ui:content>
				<ui:layout type="border">
					<ui:pane position="center">
						<div style="text-align:center">
							<br />
							校验工号:
							<input type="text" id="txtStaffCode" disabled style="font-size:12px; border: 1px solid #666666;background-color:#F0F0F0"></input>
						</div>
					</ui:pane>
					<ui:pane position="bottom">
						<ui:button id="btnCheckStaffCode">校验工号</ui:button>
						<ui:button id="btnReturn">返回</ui:button>
					</ui:pane>
				</ui:layout>
			</ui:content>
		</ui:panel>
	</body>
</html>
