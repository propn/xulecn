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
		if(!/^[\da-z]+$/i.test(staffCode)) { //数字或者字母
			alert("工号含有非法字符！");
			return ;
		}
		if( password != "" ){
			if( !checkIsNumAndLetter( password)){
				alert("密码必须由数字和字母组成!");
				return ;
			}
			if(!/^[\da-z]+$/i.test(password)) { //数字或者字母
				alert("密码必须由数字和字母组成!");
				return ;
			}
			var maxPasswordLen = getDcSystemParam("MAX_PASSWORD_LENGTH");
			var maxPasswordLen = parseInt(maxPasswordLen,10);
			minPasswordLen = getDcSystemParam("MIN_PASSWORD_LENGTH");
			minPasswordLen = parseInt(minPasswordLen,10);
			if( password.length < minPasswordLen || password.length > maxPasswordLen ){
				alert("密码长度必须在" + minPasswordLen + "到" + maxPasswordLen + "个字符之间!");
				return ;
			}
		}

				
		var projectCode = getProjectCode() ;
		if( projectCode == "CHONGQING" ){
			if( window.dialogArguments[1] == "12" ){
				if( isNaN(staffInfo.getValue("staffCode"))){
					alert("渠道工号必须是数字类型的!");
					return ;
				}
				if( staffCode.length > 9 ){
					alert("渠道工号的长度不能大于9!");
					return ;
				}
			}
		}
		
		var ajaxCall = new NDAjaxCall( true ) ;
		
		var callBack = function( reply ) {
			alert("复制员工成功!");
			window.close();
		}
		
		var arr = new Array() ;
		arr[0] = window.dialogArguments[0] ;	//取得从window.showModalDialog传递过来的参数
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
				<ui:field field="staffName" label="员工姓名" size="10" required="true" validType="require" validMsg="请输入员工姓名!"></ui:field>
				<ui:field field="staffCode" label="员工工号" size="9" required="true" validType="require" validMsg="请输入员工工号!"></ui:field>
				<ui:field field="password" label="员工密码" size="15" password="true"></ui:field>
			</ui:dataset>
		</div>
		
		<ui:layout type="border">
			<ui:pane position="center">
				<ui:form id="staffInfoForm" submit="btn_confirm" inputLayout="70%" labelLayout="29%" dataset="staffInfo"></ui:form>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btn_confirm">确定</ui:button>
				<ui:button id="btn_cancel">取消</ui:button>				
			</ui:pane>
		</ui:layout>
	</body>
</html>
		