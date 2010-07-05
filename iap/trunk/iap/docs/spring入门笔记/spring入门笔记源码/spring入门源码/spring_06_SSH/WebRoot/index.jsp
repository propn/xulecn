<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<link rel="SHORTCUT ICON"  href="http://localhost:8080/spring_06_SSH/images/1.ico">
  </head>
  
  <body >
     <a href="/spring_06_SSH/list.do">显示所有用户列表</a>
  </body>
</html>
