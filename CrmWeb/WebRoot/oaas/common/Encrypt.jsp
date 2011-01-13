<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,validator,calendar">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<script language="javascript" src="../../public/components/encrypt.js"></script>
		<ui:import library=""></ui:import>
		<script>
			function encrypt_onClick(){
				var encryptor = new Encrypt();
				destinct.value = encryptor.encrypt( source.value ) ;
			}
			function decrypt_onClick(){
				var encryptor = new Encrypt();
				source.value = encryptor.decrypt( destinct.value ) ;
			}
		</script>
	</head>
	<body>
				<ui:panel type="titleList" desc="加密解密测试">
					<ui:content>
						明文 : <INPUT type="text" id="source"></INPUT>
						密文 : <INPUT type="text" id="destinct"></INPUT><br/>
						<ui:button id="encrypt">加密</ui:button>
						<ui:button id="decrypt">解密</ui:button>
					</ui:content>
				</ui:panel>
					
	</body>
</html>