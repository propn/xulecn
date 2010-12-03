<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
<head>
<title>VSOPϵͳ:�޸�����</title>
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
	
	var paramStaffCode = "";
	function page_onLoad(){
		//paramStaffCode = GetArgsFromHrefs(location.search,"staffCode");
		var loginInfo=getStaffLoginInfo();
		paramStaffCode = loginInfo.staffCode;
	}
	
	function validPassword( strPassword ) {
			var strLength = strPassword.length;

			if(strLength>15){  //�ȼ�����������ܳ���num2λ
				alert("���벻�ܳ���15���ַ���(�������ո�)");//������ʾ�Ի���
				return false;
			}else if(strLength<6){  //�ȼ����������������num1λ
				alert("���벻������6���ַ���(�������ո�)");//������ʾ�Ի���
				return false;
			}
					
			if(/^[\da-z]+$/i.test(strPassword)) { //���ֻ�����ĸ
				if(/^[\d]+$/i.test(strPassword)) { //ȫ��������
					alert("�������Ϊ�ַ������ֵ���ϣ�");
					return false;
				}else if(/^[\a-z]+$/i.test(strPassword)) { //ȫ������ĸ
					alert("�������Ϊ�ַ������ֵ���ϣ�");
					return false;		
				}else {
				return true;
			}
		}else{
			alert("���뺬�зǷ��ַ���");
			return false;
		}
	}
	
	function btn_updatePassword_onClick(){
			var password = updatePasswordInfo.getValue("password");
			var newPassword = updatePasswordInfo.getValue("newPassword");
			var confirmPassword = updatePasswordInfo.getValue("confirmPassword");
			
			if( newPassword != confirmPassword ){
				alert("����������������벻һ��,����������!");
				updatePasswordInfo.setValue("newPassword","");
				updatePasswordInfo.setValue("confirmPassword","");
				$("text_updatePasswordInfo_newPassword").focus() ;
				return ;
			}
			
			if( !validPassword( newPassword ) ) {
				updatePasswordInfo.setValue("newPassword","");
				updatePasswordInfo.setValue("confirmPassword","");
				return ;
			}
			
			var arr = new Array();
			var result = null ;
			var changePasswordRequest = new ChangePasswordRequest();
			changePasswordRequest.preemption = "F";//�Ƿ�ǿ���޸�����,F:��,T:��
			changePasswordRequest.oldPassword = password ;
			changePasswordRequest.newPassword = newPassword;
			changePasswordRequest.staffCode = paramStaffCode.toUpperCase();
			
			arr[0] = changePasswordRequest;
			//arr[1] = paramStaffCode.toUpperCase();
			alert(changePasswordRequest.staffCode)
			var ajaxCall = new NDAjaxCall(true);
			//changePasswordRequest[Buffalo.BOCLASS] = 'com.ztesoft.oaas.struct.ChangePasswordRequest';
			var callBack = function( reply ){
				result = reply.getResult() ;
				if( result.success == 0 ){
					alert(result.reason ) ;
				}else{
					if( window.dialogArguments == "1" ){
						alert("�޸�����ɹ�,�����µ�½!");
					}else{
						alert("�޸�����ɹ�!");
					}
					window.returnValue = 0 ;
					window.close();
				}
			}
			ajaxCall.remoteCall("SignOnService","changePassword",[paramStaffCode.toUpperCase(),password,newPassword,changePasswordRequest.preemption],callBack);
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