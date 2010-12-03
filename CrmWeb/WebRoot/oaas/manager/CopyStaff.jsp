<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<script language="javascript" src="../../public/components/encrypt.js"></script>
		<ui:import library=""></ui:import>
		
		<script>

	function btn_confirm_onClick(){
		if( !$("staffInfoForm").validate()){
			return;
		}
				
		var staffCode = staffInfo.getValue("staffCode");
		var password = staffInfo.getValue("password") ;
		if(!/^[\da-z]+$/i.test(staffCode)) { //���ֻ�����ĸ
			alert("���ź��зǷ��ַ���");
			return ;
		}
		if( password != "" ){
			if( !checkIsNumAndLetter( password)){
				alert("������������ֺ���ĸ���!");
				return ;
			}
			if(!/^[\da-z]+$/i.test(password)) { //���ֻ�����ĸ
				alert("������������ֺ���ĸ���!");
				return ;
			}
			var maxPasswordLen = getDcSystemParam("MAX_PASSWORD_LENGTH");
			var maxPasswordLen = parseInt(maxPasswordLen,10);
			minPasswordLen = getDcSystemParam("MIN_PASSWORD_LENGTH");
			minPasswordLen = parseInt(minPasswordLen,10);
			if( password.length < minPasswordLen || password.length > maxPasswordLen ){
				alert("���볤�ȱ�����" + minPasswordLen + "��" + maxPasswordLen + "���ַ�֮��!");
				return ;
			}
		}

				
		var projectCode = getProjectCode() ;
		if( projectCode == "CHONGQING" ){
			if( window.dialogArguments[1] == "12" ){
				if( isNaN(staffInfo.getValue("staffCode"))){
					alert("�������ű������������͵�!");
					return ;
				}
				if( staffCode.length > 9 ){
					alert("�������ŵĳ��Ȳ��ܴ���9!");
					return ;
				}
			}
		}
		
		var ajaxCall = new NDAjaxCall( true ) ;
		
		var callBack = function( reply ) {
			alert("����Ա���ɹ�!");
			window.close();
		}
		
		var arr = new Array() ;
		arr[0] = window.dialogArguments[0] ;	//ȡ�ô�window.showModalDialog���ݹ����Ĳ���
		arr[1] = staffInfo.getValue("staffCode") ;
		arr[2] = staffInfo.getValue("staffName");
		arr[3] = staffInfo.getValue("password") ;
		
		ajaxCall.remoteCall( "PartyService", "copyStaff", arr, callBack ) ;
	}
	
	
	function btn_cancel_onClick(){
		window.close();
	}
		</script>
		
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="staffInfo" staticDataSource="true">
				<ui:field field="staffName" label="Ա������" size="10" required="true" validType="require" validMsg="������Ա������!"></ui:field>
				<ui:field field="staffCode" label="Ա������" size="9" required="true" validType="require" validMsg="������Ա������!"></ui:field>
				<ui:field field="password" label="Ա������" size="15" password="true"></ui:field>
			</ui:dataset>
		</div>
		
		<ui:layout type="border">
			<ui:pane position="center">
				<ui:form id="staffInfoForm" submit="btn_confirm" inputLayout="70%" labelLayout="29%" dataset="staffInfo"></ui:form>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btn_confirm">ȷ��</ui:button>
				<ui:button id="btn_cancel">ȡ��</ui:button>				
			</ui:pane>
		</ui:layout>
	</body>
</html>
		