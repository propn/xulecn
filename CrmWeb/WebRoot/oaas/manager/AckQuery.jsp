<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.ztesoft.oaas.struct.LoginRespond" %>
<%@page import="com.ztesoft.common.util.CrmParamsConfig"%>
<%@page import="com.ztesoft.oaas.struct.EncryptRequest"%>
<%@page import="com.ztesoft.oaas.struct.EncryptRespond"%> 
<%@page import="com.ztesoft.oaas.utils.OAASProxy"%>

<%
	/**
	 *��10000�Ž���ͳһ��CRM�ĵ�½���ţ�������ѯ���ź͹������������˻�����ͬʱ
	 *���ù��ŵ����룬��CRMϵͳ���ɽ�������У�ʹ����Щ���źͲ�ѯ������ΪĬ�ϵ�
	 *�������롣��ҳ��ͨ�������ļ���ȡ10000�ṩ�ĵ�½���źͼӹ��ܵ���������,Ȼ��
	 *���������,�ٽ�����͹�����Ϊ10000���ṩ��URL������������ݸ�֪ʶ���ѯҳ��.
	 */
	 
	LoginRespond loginRespond = (LoginRespond)request.getSession().getAttribute("LoginRespond");
	if( loginRespond == null ){
		return ;
	}
	//�������ļ��ж�ȡ��10000�Ž���ͳһ�ĸ�CRM�Ĳ�ѯ֪ʶ��Ĺ��ź�����
	String staffCode = CrmParamsConfig.getInstance().getParamValue("ACK_QUERY_STAFF_CODE");
	String password = CrmParamsConfig.getInstance().getParamValue("ACK_QUERY_STAFF_PWD");

	//�Դ������ļ��ж�ȡ������������н���
	EncryptRequest req = new EncryptRequest() ;
	req.setFlag("F");
	req.setSecretBuff(password) ;
	EncryptRespond res = OAASProxy.encrypt(req);
	password = res.getStrResultBuff();
	
	/**
	 * ���ݸ�10000��֪ʶ��ҳ��Ĳ���,��������:
	 * username : ��½10000��ϵͳ�Ĺ���,��10000�ŷ���ͳһ�ṩ,10000���ṩһ��֪ʶ���ѯ����
	 * ��һ��֪ʶ���������,��ҳ��Ϊ֪ʶ���ѯҳ��,�����ݲ�ѯ����;
	 * password : ��½10000��ϵͳ�Ĺ�������,�˴�Ϊ��ѯ֪ʶ�⹤�ŵ�����;����Ҳ��10000�ŷ���
	 * ͳһ�ṩ;
	 * page : ҳ�����,������ȡֵΪAck_query��Ack_admin,�ֱ����֪ʶ���ѯ��֪ʶ�����,��ҳ��
	 * ����Ack_query,��ѯ֪ʶ��.
	 */
	response.sendRedirect("http://136.9.7.137:7001/1000/10000WebForCRM.jsp?username=" + staffCode + "&password=" + password + "&page=Ask_query") ;
	//response.sendRedirect("http://localhost:7001/VsopWeb/oaas/manager/AckTest.jsp?username="+ staffCode + "&password=" + password + "&page=Ack_query") ;
%>