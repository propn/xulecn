<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.ztesoft.oaas.struct.LoginRespond" %>
<%@page import="com.ztesoft.common.util.CrmParamsConfig"%>
<%@page import="com.ztesoft.oaas.struct.EncryptRequest"%>
<%@page import="com.ztesoft.oaas.struct.EncryptRespond"%> 
<%@page import="com.ztesoft.oaas.utils.OAASProxy"%>

<%
	/**
	 *��10000�Ž���ͳһ��CRM�ĵ�½���ţ�������ѯ���ź͹����������˻�����ͬʱ
	 *���ù��ŵ����룬��CRMϵͳ���ɽ�������У�ʹ����Щ���źͲ�ѯ������ΪĬ�ϵ�
	 *�������롣��ҳ��ͨ�������ļ���ȡ10000�ṩ�ĵ�½���źͼӹ��ܵ���������,Ȼ��
	 *���������,�ٽ�����͹�����Ϊ10000���ṩ��URL������������ݸ�֪ʶ�����ҳ��.
	 */
	 
	LoginRespond loginRespond = (LoginRespond)request.getSession().getAttribute("LoginRespond");
	if( loginRespond == null ){
		return ;
	}
	
	//�������ļ��ж�ȡ��10000�Ž���ͳһ�ĸ�CRM�Ĺ���֪ʶ��Ĺ��ź�����
	String staffCode = CrmParamsConfig.getInstance().getParamValue("ACK_MANAGER_STAFF_CODE");
	String password = CrmParamsConfig.getInstance().getParamValue("ACK_MANAGER_STAFF_PWD");
	
	//�Դ������ļ��ж�ȡ������������н���
	EncryptRequest req = new EncryptRequest() ;
	req.setFlag("F");
	req.setSecretBuff(password) ;
	EncryptRespond res = OAASProxy.encrypt(req);
	password = res.getStrResultBuff();
	
	/**
	 * ���ݸ�10000��֪ʶ��ҳ��Ĳ���,�������ƺ�����:
	 * username : ��½10000��֪ʶ��Ĺ���֪ʶ��Ĺ���,��10000�ŷ����ṩ
	 * password : ��½10000��֪ʶ��Ĺ�������,��10000�ŷ����ṩ
	 * page : ҳ�����,ȡֵΪAck_query��Ack_admin,����ֱ���:
	 *            Ack_query : ��ѯ֪ʶ��ҳ��
	 *            Ack_admin : ֪ʶ�����ҳ��
	 * �ڱ�ҳ�д���Ack_admin.
	 */
	response.sendRedirect("http://136.9.7.137:7001/1000/10000WebForCRM.jsp?username=" + staffCode + "&password=" + password + "&page=Ask_admin") ;
	//response.sendRedirect("http://localhost:7001/VsopWeb/oaas/manager/AckTest.jsp?username="+ staffCode + "&password=" + password + "&page=Ack_admin") ;
%>