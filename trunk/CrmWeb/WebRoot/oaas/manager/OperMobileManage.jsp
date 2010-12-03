<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<%@ page import="com.ztesoft.oaas.struct.LoginRespond" %>
<%
LoginRespond loginRespond = (LoginRespond)request.getSession().getAttribute("LoginRespond");
String staffCode="";
String partyName="";
if( loginRespond != null ){
	staffCode = loginRespond.getStaffCode();
	partyName=loginRespond.getPartyName();
	
}
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<script language="javascript" src="../../public/components/encrypt.js"></script>
		<ui:import library=""></ui:import>
		
		
		<script>
    var staffCode=<%=staffCode%>;
    var staffName=<%=partyName%>;
    var orialMobile="";
    function page_onLoad(){
       staffInfo.setValue("staffCode",staffCode);
       getStaffMobile(staffCode);
       staffInfo.setValue("mobile",orialMobile);
      // staffInfo.setValue("staffName",partyName);
    }
	function btn_confirm_onClick(){
		if( !$("staffInfoForm").validate()){
			return;
		}
				
		var staffCode = staffInfo.getValue("staffCode");
		var staffName = staffInfo.getValue("staffName");
		var mobile = staffInfo.getValue("mobile") ;
		var rmobile = staffInfo.getValue("rMobile") ;
		if(mobile!=rmobile){
		   alert("����������ֻ����벻һ�£�");
		   return;
		} 

	
		 
		
		var ajaxCall = new NDAjaxCall( true ) ;
		
		var callBack = function( reply ) {			
		var result = reply.getResult();
        if (result=="1") {  //succ             
           alert("�������ֻ�����ɹ�!");             
        }
        else if (result=="2") {  //succ             
           alert("�޸İ��ֻ�����ɹ�!");             
        } 
         else {
        	alert("\u64cd\u4f5c\u5931\u8d25");
        }  
		}		
		//var arr = new Array() ;
		//arr[0] =staffCode ;
		//arr[1] =mobile ;	
		//alert( staffCode+ " :" +mobile);                 
		ajaxCall.remoteCall( "MessageManageService", "setStaffMobile",[staffCode+"",mobile+""], callBack );
	}
	
	function getStaffMobile(staffcode){
	    
	    var ajaxCall = new NDAjaxCall( false ) ;
	    var callBack = function( reply ) {			
	     orialMobile = reply.getResult();
         
		}				         
		ajaxCall.remoteCall( "MessageManageService", "getStaffMobile",[staffCode+""], callBack );		 
	}
	
	function btn_cancel_onClick(){
		window.close();
	}
		</script>
		
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="staffInfo" staticDataSource="true">
				<!--  field field="staffName" label="Ա������" size="10" required="true" validType="require" validMsg="������Ա������!"-->
			 			 
				<ui:field field="staffCode" label="Ա������" size="9" required="true" validType="require" readOnly="true" validMsg="������Ա������!"></ui:field>				 
				 
				<ui:field field="mobile" label="�ֻ�����" size="15" validType="number" ></ui:field>
				 
				<ui:field field="rMobile" label="�ֻ�����ȷ��" size="15" validType="number"  ></ui:field>
			</ui:dataset>
		</div>
		
		<ui:layout type="border">
		    <ui:pane position="top">
				<ui:bar type="search" desc="�������ֻ������"></ui:bar>
			</ui:pane>
	       
			
			<ui:pane position="center"  >
			    <ui:layout type="border">
			    <ui:pane position="top" style="height:100px"> </ui:pane>
			    <ui:pane position="center">
			    	 <ui:layout type="border">
			    	 	<ui:pane position="left" style="width:160px"> </ui:pane>
			    		 <ui:pane position="center">
							<ui:form id="staffInfoForm" submit="btn_confirm" inputLayout="36%" labelLayout="34%" dataset="staffInfo"></ui:form>
							<div align="center">
								<ui:button id="btn_confirm">����</ui:button>
								
								<!--  &nbsp;&nbsp; ui:button id="btn_cancel"ȡ��-->
							</div>	
						</ui:pane>
						<ui:pane position="right" style="width:160px"> </ui:pane>
					</ui:layout>			
				</ui:pane>
				</ui:layout>
			</ui:pane>
			 
			
		</ui:layout>
	</body>
</html>
		