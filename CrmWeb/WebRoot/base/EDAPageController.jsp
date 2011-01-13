<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="com.ztesoft.oaas.struct.LoginRespond"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel">
		<title></title>
		<script language="javascript" src="../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		
<%
	String url = request.getParameter("url");
	LoginRespond loginRespond = (LoginRespond)request.getSession().getAttribute("LoginRespond");
	String staffCode = loginRespond.getStaffCode();//"CW0X02CX";
	String password = loginRespond.getPassword();
	String partyRoleId = loginRespond.getPartyRoleId();
	//password = partyRoleId+password;//"200001450lhs282899";
%>

		<script> 
		
	function page_onLoad(){
		submitform.submit();
	}
	</script>
<body>
<FORM id="submitform" action=<%=url%> method="post">
	<input type="hidden" name="iUsercode" value=<%=staffCode%>></input>
	<input type="hidden" name="iPassword" value=<%=password%>></input>
	<input type="hidden" name="iType" value="0"></input>
</FORM>
</body>
</html>
