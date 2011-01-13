<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
<script language="javascript" src="../../public/components/common2.js"></script>
<ui:import library=""></ui:import>
<script language="javascript" src="../../public/components/calendar/calendar.js"></script>
<script>
var regionType = GetArgsFromHrefs(document.location.href, "regionType");
//alert("#########:" + regionType);
</script>
</head>
<body>

<script>
	var str = "<div class='tab-pane' id='mainPage'>"
			+" <div class='tab-page' desc='组织机构' target='Organization' href='Organization.htm?regionType=" 
			+ regionType + "' autoLoad='true'>"
			+ "</div><div class='tab-page' desc='员工' target='Staff' href='Staff.htm?regionType="
			+ regionType + "' autoLoad='true'></div></div>"; 
	//alert(str) ;
	document.write(str);
</script>		
</body>
</html>