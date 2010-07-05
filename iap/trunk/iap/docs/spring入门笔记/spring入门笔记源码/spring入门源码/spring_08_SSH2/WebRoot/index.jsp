<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>添加用户</title>
  </head>	
  	<form action="<%=request.getContextPath() %>/add.action">
  		用户名：<input type="text" name="username">
  		<input type="submit" value="提交">  	 
  	</form>
  <body >
  
  </body>
</html>
