<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
<head>
<title>�����ͨ�ۺϿͷ�����ϵͳ:�޸�����</title>
<META HTTP-EQUIV="library" CONTENT="kernel">
<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
<ui:import library=""></ui:import>

<script>
	function ChangePasswordRequest(){
		this.staffCode = "";  
		this.preemption = ""; 
		this.oldPassword = "";
		this.newPassword = "";
	}
	
	function btn_updatePassword_onClick(){
			var password = updatePasswordInfo.getValue("password");
			var newPassword = updatePasswordInfo.getValue("newPassword");
			var confirmPassword = updatePasswordInfo.getValue("confirmPassword");
			
			var arr = new Array();
			var result = null ;
			var changePasswordRequest = new ChangePasswordRequest();
			changePasswordRequest.preemption = "F";//�Ƿ�ǿ���޸�����,F:��,T:��
			changePasswordRequest.oldPassword = password ;
			changePasswordRequest.newPassword = newPassword;
			arr[0] = changePasswordRequest;
			
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				result = reply.getResult() ;
				alert("�޸�����ɹ�!");
				window.close();
			}
			ajaxCall.remoteCall("LoginService","changePassword",arr,callBack);
	}
	
	function btn_cancel_onClick(){
		window.close();
	}
</script>
</head>

<body>

		<div id="datasetDefine">
			<ui:dataset datasetId="updatePasswordInfo">
					<ui:field field="password" label="������" password="true"></ui:field>
					<ui:field field="newPassword" label="������" password="true"></ui:field>
					<ui:field field="confirmPassword" label="����ȷ��" password="true"></ui:field>
			</ui:dataset>
		</div>
		
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="�޸�����">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
								<ui:form dataset="updatePasswordInfo" labelLayout="25%" inputLayout="70%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
								<ui:button id="btn_updatePassword">ȷ��</ui:button>
								<ui:button id="btn_cancel">ȡ��</ui:button>	
						</ui:pane>
					</ui:layout>
				</ui:content>		
			</ui:panel>  
		</div>

</body>
</html>