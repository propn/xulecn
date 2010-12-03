<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.ztesoft.oaas.struct.LoginRespond" %>
<%@page import="com.ztesoft.common.util.CrmParamsConfig"%>
<%@page import="com.ztesoft.oaas.struct.EncryptRequest"%>
<%@page import="com.ztesoft.oaas.struct.EncryptRespond"%> 
<%@page import="com.ztesoft.oaas.utils.OAASProxy"%>

<%
	/**
	 *由10000号建立统一的CRM的登陆工号（包括查询工号和管理工号两个账户），同时
	 *设置工号的密码，在CRM系统集成界面过程中，使用这些工号和查询密码作为默认的
	 *工号密码。本页面通过配置文件获取10000提供的登陆工号和加过密的密码密文,然后
	 *将密码解密,再将密码和工号作为10000号提供的URL的请求参数传递给知识库管理页面.
	 */
	 
	LoginRespond loginRespond = (LoginRespond)request.getSession().getAttribute("LoginRespond");
	if( loginRespond == null ){
		return ;
	}
	
	//从配置文件中读取由10000号建立统一的给CRM的管理知识库的工号和密码
	String staffCode = CrmParamsConfig.getInstance().getParamValue("ACK_MANAGER_STAFF_CODE");
	String password = CrmParamsConfig.getInstance().getParamValue("ACK_MANAGER_STAFF_PWD");
	
	//对从配置文件中读取出来的密码进行解密
	EncryptRequest req = new EncryptRequest() ;
	req.setFlag("F");
	req.setSecretBuff(password) ;
	EncryptRespond res = OAASProxy.encrypt(req);
	password = res.getStrResultBuff();
	
	/**
	 * 传递给10000号知识库页面的参数,参数名称和意义:
	 * username : 登陆10000号知识库的管理知识库的工号,由10000号方面提供
	 * password : 登陆10000号知识库的工号密码,由10000号方面提供
	 * page : 页面参数,取值为Ack_query和Ack_admin,意义分别是:
	 *            Ack_query : 查询知识库页面
	 *            Ack_admin : 知识库管理页面
	 * 在本页中传递Ack_admin.
	 */
	response.sendRedirect("http://136.9.7.137:7001/1000/10000WebForCRM.jsp?username=" + staffCode + "&password=" + password + "&page=Ask_admin") ;
	//response.sendRedirect("http://localhost:7001/VsopWeb/oaas/manager/AckTest.jsp?username="+ staffCode + "&password=" + password + "&page=Ack_admin") ;
%>