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
	 *将密码解密,再将密码和工号作为10000号提供的URL的请求参数传递给知识库查询页面.
	 */
	 
	LoginRespond loginRespond = (LoginRespond)request.getSession().getAttribute("LoginRespond");
	if( loginRespond == null ){
		return ;
	}
	//从配置文件中读取由10000号建立统一的给CRM的查询知识库的工号和密码
	String staffCode = CrmParamsConfig.getInstance().getParamValue("ACK_QUERY_STAFF_CODE");
	String password = CrmParamsConfig.getInstance().getParamValue("ACK_QUERY_STAFF_PWD");

	//对从配置文件中读取出来的密码进行解密
	EncryptRequest req = new EncryptRequest() ;
	req.setFlag("F");
	req.setSecretBuff(password) ;
	EncryptRespond res = OAASProxy.encrypt(req);
	password = res.getStrResultBuff();
	
	/**
	 * 传递给10000号知识库页面的参数,参数意义:
	 * username : 登陆10000号系统的工号,由10000号方面统一提供,10000号提供一个知识库查询工号
	 * 和一个知识库管理工号,本页面为知识库查询页面,将传递查询工号;
	 * password : 登陆10000号系统的工号密码,此处为查询知识库工号的密码;密码也有10000号方面
	 * 统一提供;
	 * page : 页面参数,定义其取值为Ack_query和Ack_admin,分别代表知识库查询和知识库管理,本页面
	 * 传递Ack_query,查询知识库.
	 */
	response.sendRedirect("http://136.9.7.137:7001/1000/10000WebForCRM.jsp?username=" + staffCode + "&password=" + password + "&page=Ask_query") ;
	//response.sendRedirect("http://localhost:7001/VsopWeb/oaas/manager/AckTest.jsp?username="+ staffCode + "&password=" + password + "&page=Ack_query") ;
%>