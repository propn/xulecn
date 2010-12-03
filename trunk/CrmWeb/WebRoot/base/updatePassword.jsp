<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
<head>
<title>VSOP系统:修改密码</title>
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

			if(strLength>15){  //先检验密码最长不能超过num2位
				alert("密码不能超过15个字符！(不包含空格)");//弹出提示对话框
				return false;
			}else if(strLength<6){  //先检验密码最长不能少于num1位
				alert("密码不能少于6个字符！(不包含空格)");//弹出提示对话框
				return false;
			}
					
			if(/^[\da-z]+$/i.test(strPassword)) { //数字或者字母
				if(/^[\d]+$/i.test(strPassword)) { //全部是数字
					alert("密码必须为字符和数字的组合！");
					return false;
				}else if(/^[\a-z]+$/i.test(strPassword)) { //全部是字母
					alert("密码必须为字符和数字的组合！");
					return false;		
				}else {
				return true;
			}
		}else{
			alert("密码含有非法字符！");
			return false;
		}
	}
	
	function btn_updatePassword_onClick(){
			var password = updatePasswordInfo.getValue("password");
			var newPassword = updatePasswordInfo.getValue("newPassword");
			var confirmPassword = updatePasswordInfo.getValue("confirmPassword");
			
			if( newPassword != confirmPassword ){
				alert("您两次输入的新密码不一致,请重新输入!");
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
			changePasswordRequest.preemption = "F";//是否强制修改密码,F:否,T:是
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
						alert("修改密码成功,请重新登陆!");
					}else{
						alert("修改密码成功!");
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
					<ui:field field="password" label="旧密码" password="true"></ui:field>
					<ui:field field="newPassword" label="新密码" password="true"></ui:field>
					<ui:field field="confirmPassword" label="密码确认" password="true"></ui:field>
			</ui:dataset>
		</div>
		
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="修改密码">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
								<ui:form dataset="updatePasswordInfo" labelLayout="25%" inputLayout="70%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
								<ui:button id="btn_updatePassword">确定</ui:button>
								<ui:button id="btn_cancel">取消</ui:button>	
						</ui:pane>
					</ui:layout>
				</ui:content>		
			</ui:panel>  
		</div>

</body>
</html>