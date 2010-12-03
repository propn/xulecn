<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library"
			CONTENT="kernel,customerpilot,calendar,validator,tree,treeList">
		<title>Ӫҵ����Ӫ���ݹ���</title>
		<script language="JavaScript" src="../../public/components/common2.js"
			charset="gb2312"></script>
		<ui:import library=""></ui:import>

	<script language="JavaScript">
	
		var actionType = "" ;
		var defaultBusinessId = "";
		var defaultBusinessName = "";
		
		function page_onLoad(){
			document.all.btn_Mofify.disabled=true;
			document.all.btn_Add.disabled=false;
			document.all.btn_Del.disabled=true;
			document.all.btn_Reset.disabled=false;
			document.all.btn_cansel.disabled=false;	
			document.all.btn_Confirm.disabled=false;
			var obj = getStaffLoginInfo();
			defaultBusinessId = obj.businessId+",";
			defaultBusinessName = obj.businessName;
			Ds_QryBusinessOperationInfo.setValue("businessName",defaultBusinessName);
			Ds_QryBusinessOperationInfo.setValue("businessId",defaultBusinessId);
		}
		
		function btn_query_onClick() {
			
			actionType = "" ;

			if( !$("form_Ds_QryBusinessOperationInfo").validate()){
				return ;
			}
						
			Ds_BusinessOperationData.setReadOnly(true);
			document.all.btn_Add.disabled=false;
			document.all.btn_Del.disabled=false;
			document.all.btn_Reset.disabled=true;
			document.all.btn_Mofify.disabled=false;
			Ds_BusinessOperationInfo.clearData();
			
			var lanId = Ds_QryBusinessOperationInfo.getValue("lanId");
			businessId = Ds_QryBusinessOperationInfo.getValue("businessId");
			var crmBusinessHallName = Ds_QryBusinessOperationInfo.getValue("crmBusinessHallName");
		 	var startDate = Ds_QryBusinessOperationInfo.getValue("startDate")+"";
		 	var endDate = Ds_QryBusinessOperationInfo.getValue("endDate")+"";
		 	if(startDate>endDate){
		 		alert("��ʼʱ�䲻�ܴ��ڽ���ʱ�䣡");
		 		return;
		 	}
			Ds_BusinessOperationInfo.parameters().setValue("lanId",lanId);
			Ds_BusinessOperationInfo.parameters().setValue("businessId",businessId);	
			Ds_BusinessOperationInfo.parameters().setValue("crmBusinessHallName",crmBusinessHallName);
			Ds_BusinessOperationInfo.parameters().setValue("startDate",startDate);
			Ds_BusinessOperationInfo.parameters().setValue("endDate",endDate);
			Ds_BusinessOperationInfo.flushData();  
			var cur = Ds_BusinessOperationInfo.getCurrent();
			if(cur){
			 	Ds_BusinessOperationData.setValue("crmName",cur.getValue("crmName"));
			 	Ds_BusinessOperationData.setValue("partyRoleId",cur.getValue("partyRoleId"));
			 	Ds_BusinessOperationData.setValue("custInfoTotal",cur.getValue("custInfoTotal"));
			 	Ds_BusinessOperationData.setValue("custInfoTimelyCount",cur.getValue("custInfoTimelyCount"));
			 	Ds_BusinessOperationData.setValue("custInfoTimelyRate",cur.getValue("custInfoTimelyRate"));
			 	Ds_BusinessOperationData.setValue("custInfoAuditTotal",cur.getValue("custInfoAuditTotal"));
			 	Ds_BusinessOperationData.setValue("custInfoAuditErrCount",cur.getValue("custInfoAuditErrCount"));
			 	Ds_BusinessOperationData.setValue("custInfoErrRate",cur.getValue("custInfoErrRate"));
			 	Ds_BusinessOperationData.setValue("removeKeepCount",cur.getValue("removeKeepCount"));
			 	Ds_BusinessOperationData.setValue("removeKeepSuccessCount",cur.getValue("removeKeepSuccessCount"));
			 	Ds_BusinessOperationData.setValue("removeKeepSuccessRate",cur.getValue("removeKeepSuccessRate"));
			 	Ds_BusinessOperationData.setValue("custEvaluateCount",cur.getValue("custEvaluateCount"));
			 	Ds_BusinessOperationData.setValue("custSatisfationCount",cur.getValue("custSatisfationCount"));
			 	Ds_BusinessOperationData.setValue("custSatisfationRate",cur.getValue("custSatisfationRate"));
			 	var enterDate = cur.getValue("enterDate");
			 	Ds_BusinessOperationData.setValue("enterDate",enterDate.substr(0,enterDate.length-10));
		 	}
		}
		
		function btn_Mofify_onClick() {
		    actionType = "update" ;
		    var cur = Ds_BusinessOperationInfo.getCurrent();
		    if(cur==null){
		    	return;
		    }
		    var enterDate = cur.getValue("enterDate");
			var enterDate1=enterDate.substring(0,4)+"-"+enterDate.substring(5,7)+"-"+enterDate.substring(8,10)+"";
			if(enterDate1<date || enterDate1>curDate){
				alert("�ü�¼¼��ʱ��Ϊ��"+enterDate1+"�����ǵ���ʱ��ʲ��ܶ�������޸Ĳ�����");
				return;
			}		    
			Ds_BusinessOperationData.setReadOnly(false);
			Ds_BusinessOperationData.setFieldReadOnly("custInfoTimelyRate",true);
			Ds_BusinessOperationData.setFieldReadOnly("custInfoErrRate",true);
			Ds_BusinessOperationData.setFieldReadOnly("removeKeepSuccessRate",true);
			Ds_BusinessOperationData.setFieldReadOnly("custSatisfationRate",true);
			document.all.btn_Mofify.disabled=true;
			document.all.btn_Add.disabled=true;
			document.all.btn_Del.disabled=true;
			document.all.btn_Reset.disabled=false;
		}
		
	   function btn_Add_onClick(){
	        actionType = "insert" ;
	        Ds_BusinessOperationData.clearData();
	        Ds_BusinessOperationData.setReadOnly(false);
			Ds_BusinessOperationData.setFieldReadOnly("custInfoTimelyRate",true);
			Ds_BusinessOperationData.setFieldReadOnly("custInfoErrRate",true);
			Ds_BusinessOperationData.setFieldReadOnly("removeKeepSuccessRate",true);
			Ds_BusinessOperationData.setFieldReadOnly("custSatisfationRate",true);	 
			Ds_BusinessOperationData.setFieldReadOnly("custSatisfationRate",true);   
			Ds_BusinessOperationData.setValue("enterDate",curDate);   
			document.all.btn_Mofify.disabled=true;	
			document.all.btn_Add.disabled=true;
			document.all.btn_Del.disabled=true;
			document.all.btn_Reset.disabled=false;
		}
		
	  function btn_Del_onClick(){	
	     if(Ds_BusinessOperationInfo.getCurrent()){  
	        if(confirm('ȷ��Ҫɾ���ü�¼��')==true){
				var operationId = Ds_BusinessOperationInfo.getValue("operationId");
		        var callBack = function(reply ){
				   result = reply.getResult() ; 
					   if(result=='1'){
					      alert("�����ɹ���");
					      btn_query_onClick();
					   } 
				   }
		  		var ajaxCall = new NDAjaxCall(true);
		  		ajaxCall.remoteCall("CcQueryService","delBusinessOperationInfo",[operationId],callBack); 
		  		}
		     }
	   }
	   
	  function btn_Reset_onClick(){
	  	if(actionType){	 
			Ds_BusinessOperationData.clearData() ; 
		}
		Ds_BusinessOperationData.setValue("enterDate",curDate); 
	  }	
	  
	  var now = new Date(); 
	  var year = now.getFullYear();//��ȡ���������
	  var month = now.getMonth()+1; //��ȡ��ǰ�·�(0-11,0����1��) 
	  var day =  now.getDate(); //��ȡ��ǰ��(1-31) 
	  var date = year+"-"+month+"-01"+"";//����1��
	  var curDate = year+"-"+month+"-"+day+"";	  
	 
	  function btn_Confirm_onClick(){
 		if( actionType == "" ) {
 			    alert("��ǰ����Ϊ�գ�������ѡ��!");
		  	return ;
		}
		if( !$("form_Ds_BusinessOperationData").validate()){
			return ;
		}		
		var custInfoTotal = Ds_BusinessOperationData.getValue("custInfoTotal");
		var custInfoTimelyCount = Ds_BusinessOperationData.getValue("custInfoTimelyCount");
		var custInfoAuditTotal = Ds_BusinessOperationData.getValue("custInfoAuditTotal");
		var custInfoAuditErrCount = Ds_BusinessOperationData.getValue("custInfoAuditErrCount");
		var removeKeepCount = Ds_BusinessOperationData.getValue("removeKeepCount");
		var removeKeepSuccessCount = Ds_BusinessOperationData.getValue("removeKeepSuccessCount");
		var custEvaluateCount = Ds_BusinessOperationData.getValue("custEvaluateCount");
		var custSatisfationCount = Ds_BusinessOperationData.getValue("custSatisfationCount");
		var enterDate = Ds_BusinessOperationData.getValue("enterDate")+"";


		var enterDate1=enterDate.substring(0,4)+"-"+enterDate.substring(5,7)+"-"+enterDate.substring(8,10)+"";
		if(enterDate1<date || enterDate1>curDate){
			alert("�޸�¼��ʱ�������ΧΪ:���ڵ��ڵ���1����С�ڵ��ڵ��죩�����������룡");
			return;
		}
		//���������ֽ���У��
		if(custInfoTimelyCount>custInfoTotal){
			alert("�ͻ�����������ʱ�����ܴ��ڿͻ��������������������룡");
			return;
		}
		if(custInfoAuditTotal>custInfoTotal){
			alert("�ͻ����ϻ����������ܴ��ڿͻ��������������������룡");
			return;
		}
		if(custInfoAuditErrCount>custInfoAuditTotal){
			alert("�ͻ����ϻ��˲���������ܴ��ڿͻ�����������ʱ�������������룡");
			return;
		}
		if(removeKeepCount>custInfoTotal){
			alert("���������¼�����ܴ��ڿͻ��������������������룡");
			return;
		}
		if(removeKeepSuccessCount>removeKeepCount){
			alert("��������ɹ������ܴ��ڲ��������¼�������������룡");
			return;
		}
		if(custEvaluateCount>custInfoTotal){
			alert("���۵Ŀͻ������ܴ��ڿͻ��������������������룡");
			return;
		}
		if(custSatisfationCount>custEvaluateCount){
			alert("����ͻ������ܴ������۵Ŀͻ��������������룡");
			return;
		}
		
		var custInfoTimelyRate = ((custInfoTimelyCount/custInfoTotal)*100).toString().substr(0,4)+"%"; //�ͻ�����������ʱ��
		Ds_BusinessOperationData.setValue("custInfoTimelyRate",custInfoTimelyRate);
		var custInfoErrRate = ((custInfoAuditErrCount/custInfoAuditTotal)*100).toString().substr(0,4)+"%"; //�ͻ����ϲ����
		Ds_BusinessOperationData.setValue("custInfoErrRate",custInfoErrRate);
		var removeKeepSuccessRate = ((removeKeepSuccessCount/removeKeepCount)*100).toString().substr(0,4)+"%"; //��������ɹ���
		Ds_BusinessOperationData.setValue("removeKeepSuccessRate",removeKeepSuccessRate);
		var custSatisfationRate = ((custSatisfationCount/custEvaluateCount)*100).toString().substr(0,4)+"%";	//�ͻ������	
		Ds_BusinessOperationData.setValue("custSatisfationRate",custSatisfationRate);	
														
		var obj = new Object();
		obj["custInfoTotal"] = custInfoTotal.toString();
		obj["custInfoTimelyCount"] = custInfoTimelyCount.toString();
		obj["custInfoTimelyRate"] = custInfoTimelyRate.toString();
		obj["custInfoAuditTotal"] = custInfoAuditTotal.toString();
		obj["custInfoAuditErrCount"] = custInfoAuditErrCount.toString();
		obj["custInfoErrRate"] = custInfoErrRate.toString();
		obj["removeKeepCount"] = removeKeepCount.toString();
		obj["removeKeepSuccessCount"] = removeKeepSuccessCount.toString();
		obj["removeKeepSuccessRate"] = removeKeepSuccessRate.toString();
		obj["custEvaluateCount"] = custEvaluateCount.toString();
		obj["custSatisfationCount"] = custSatisfationCount.toString();
		obj["custSatisfationRate"] = custSatisfationRate.toString();
		obj["enterDate"] = enterDate;
		
		var ajaxCall = new NDAjaxCall(true);		 
		var callBack = function(reply ){
			var reMap = reply.getResult() ;
			if(reMap){
				alert("�����ɹ���");	
			}
			btn_query_onClick();// 
		}
		
		if( actionType == "insert" ){
			ajaxCall.remoteCall("CcQueryService","insertBusinessOperationInfo",[obj],callBack); 
		}
		else if( actionType == "update" ){
			var operationId = Ds_BusinessOperationInfo.getValue("operationId");
			obj["operationId"] = operationId.toString();
			ajaxCall.remoteCall("CcQueryService","updateBusinessOperationInfo",[obj],callBack);
		}
		Ds_BusinessOperationData.setReadOnly(true);
		actionType = "" ;
	  }
	  
	 function table_BusinessOperationInfo_onclick(){
		Ds_BusinessOperationData.clearData();
		Ds_BusinessOperationData.setReadOnly(true);
		var cur = Ds_BusinessOperationInfo.getCurrent();
		if(cur){
		 	Ds_BusinessOperationData.setValue("crmName",cur.getValue("crmName"));
		 	Ds_BusinessOperationData.setValue("partyRoleId",cur.getValue("partyRoleId"));
		 	Ds_BusinessOperationData.setValue("custInfoTotal",cur.getValue("custInfoTotal"));
		 	Ds_BusinessOperationData.setValue("custInfoTimelyCount",cur.getValue("custInfoTimelyCount"));
		 	Ds_BusinessOperationData.setValue("custInfoTimelyRate",cur.getValue("custInfoTimelyRate"));
		 	Ds_BusinessOperationData.setValue("custInfoAuditTotal",cur.getValue("custInfoAuditTotal"));
		 	Ds_BusinessOperationData.setValue("custInfoAuditErrCount",cur.getValue("custInfoAuditErrCount"));
		 	Ds_BusinessOperationData.setValue("custInfoErrRate",cur.getValue("custInfoErrRate"));
		 	Ds_BusinessOperationData.setValue("removeKeepCount",cur.getValue("removeKeepCount"));
		 	Ds_BusinessOperationData.setValue("removeKeepSuccessCount",cur.getValue("removeKeepSuccessCount"));
		 	Ds_BusinessOperationData.setValue("removeKeepSuccessRate",cur.getValue("removeKeepSuccessRate"));
		 	Ds_BusinessOperationData.setValue("custEvaluateCount",cur.getValue("custEvaluateCount"));
		 	Ds_BusinessOperationData.setValue("custSatisfationCount",cur.getValue("custSatisfationCount"));
		 	Ds_BusinessOperationData.setValue("custSatisfationRate",cur.getValue("custSatisfationRate"));
		 	var enterDate = cur.getValue("enterDate");
		 	Ds_BusinessOperationData.setValue("enterDate",enterDate.substr(0,enterDate.length-10));
			document.all.btn_Mofify.disabled=false;
		    document.all.btn_Del.disabled=false; 
		   	document.all.btn_Reset.disabled=true; 
		    document.all.btn_Add.disabled=false;
	    }
	 }
	 
	function btn_cansel_onClick(){
		actionType = "" ;
		Ds_BusinessOperationData.setReadOnly(true);
		var cur = Ds_BusinessOperationInfo.getCurrent();
		if(cur){
		 	Ds_BusinessOperationData.setValue("crmName",cur.getValue("crmName"));
		 	Ds_BusinessOperationData.setValue("partyRoleId",cur.getValue("partyRoleId"));
		 	Ds_BusinessOperationData.setValue("custInfoTotal",cur.getValue("custInfoTotal"));
		 	Ds_BusinessOperationData.setValue("custInfoTimelyCount",cur.getValue("custInfoTimelyCount"));
		 	Ds_BusinessOperationData.setValue("custInfoTimelyRate",cur.getValue("custInfoTimelyRate"));
		 	Ds_BusinessOperationData.setValue("custInfoAuditTotal",cur.getValue("custInfoAuditTotal"));
		 	Ds_BusinessOperationData.setValue("custInfoAuditErrCount",cur.getValue("custInfoAuditErrCount"));
		 	Ds_BusinessOperationData.setValue("custInfoErrRate",cur.getValue("custInfoErrRate"));
		 	Ds_BusinessOperationData.setValue("removeKeepCount",cur.getValue("removeKeepCount"));
		 	Ds_BusinessOperationData.setValue("removeKeepSuccessCount",cur.getValue("removeKeepSuccessCount"));
		 	Ds_BusinessOperationData.setValue("removeKeepSuccessRate",cur.getValue("removeKeepSuccessRate"));
		 	Ds_BusinessOperationData.setValue("custEvaluateCount",cur.getValue("custEvaluateCount"));
		 	Ds_BusinessOperationData.setValue("custSatisfationCount",cur.getValue("custSatisfationCount"));
		 	Ds_BusinessOperationData.setValue("custSatisfationRate",cur.getValue("custSatisfationRate"));
		 	var enterDate = cur.getValue("enterDate");
		 	Ds_BusinessOperationData.setValue("enterDate",enterDate.substr(0,enterDate.length-10));
			document.all.btn_Mofify.disabled=false;
			document.all.btn_Del.disabled=false;
		}
		else{	
			document.all.btn_Mofify.disabled=true;
		    document.all.btn_Del.disabled=true; 
	    }
	    document.all.btn_Add.disabled=false; 	
	    	
	}	

   function button_Ds_QryBusinessOperationInfo_businessName_onClick(){
		var obj = new Object();
		/*
		 *privilegeType -- Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵�����
		 */
		obj["privilegeType"] = "3" ;
		/*
		 *privilegeCode -- 
		 *Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
		 */
		 var menuCode = Global.getCurrentMenuCode();
		obj["privilegeCode"] = menuCode;
		var regionIds = Ds_QryBusinessOperationInfo.getValue("businessId");
		var regionLevel = Ds_QryBusinessOperationInfo.getValue("regionLevel");
		if(regionIds!=null && regionIds!="")
		   obj["regionIds"] = regionIds.substr(0,regionIds.length-1);
		else
		   obj["regionIds"] = regionIds;
			
		obj["selectDistinctRegionLevel"] = 1 ;//1��ʾֻ��ѡ����ͬ����ĵ���,2��ʾ����ѡ��ͬ����ĵ���
		
		/*selectParent :1��ʾ����ѡ���ϼ�����,2��ʾ����ѡ���ϼ�����,Ĭ�ϲ�����ѡ���ϼ�����,�ڿ�
		 * ��ѡ���ϼ������������,ѡ��Ķ�������������ͬ�����,�紫�ݵ�regionLevel����Ϊ98F,��
		 * selectParent Ϊ2,�����ѡ���վ�;�վ����������Ȩ�޵ĵ���,���Ƕ�ѡ�������������ͬ�����,��
		 * �粻��ѡ��һ���ֹ�˾��һ���й�˾.
		 */
		obj["selectParent"] = "2";
		obj["checkChildren"] = "2";//1��ʾ��ѡ�¼��ڵ�,2��ʾ����ѡ
		
		/*
		 * ���򼶱�,97A��ʾ���Ź�˾,97E��ʾʡ��˾,97B��ʾ������,97D��ʾӪҵ��,98D
		 * ��ʾ�����,98E��ʾĸ��,98F��ʾ��վ
		 */
		 //if(regionLevel!=null && regionLevel!="")
		     //obj["regionLevel"] = regionLevel ;	
		 //else
		obj["regionLevel"] = "97D" ;	
		obj["selectType"] = "2" ;//��ѡ��ѡ��־,1 Ϊ��ѡ,2 Ϊ��ѡ
		var returnValue=window.showModalDialog("../../oaas/common/ResourceRegionSelect.jsp",obj,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	          
	          var businessId = "";
	          var businessName = "";
	          if(returnValue!=null && returnValue.length!=0){ 
	           for(var i=0; i<returnValue.length; i++){
	             businessId =   returnValue[i]["regionId"] + "," + businessId;
	             businessName =   returnValue[i]["regionName"] + "," + businessName;
			}
			Ds_QryBusinessOperationInfo.setValue("businessId", businessId);
			Ds_QryBusinessOperationInfo.setValue("businessName", businessName.substr(0,businessName.length-1));
			Ds_QryBusinessOperationInfo.setValue("regionLevel", returnValue[0]["regionLevel"]);
		}
		else{
		  return;
		}
    }
    /*����Excel*/
	function btn_export_onClick(){
		exportExcel(Ds_BusinessOperationInfo, "BusinessOperationInfo.jsp");
	}    	  		  		     				
 	</script>
	</head>
	<body>
		<div id="datasetDefine">

			<!--Ӫҵ����Ӫ�����б��ѯ���ݼ�-->
			<ui:dataset datasetId="Ds_QryBusinessOperationInfo" readOnly="false" staticDataSource="true">
				<ui:field field="lanId" label="������" attrCode="DC_AUTHORIZED_LAN" readOnly="true"  required="true"></ui:field>
				<ui:field field="businessId" label="�������"  visible="false" ></ui:field>
				<ui:field field="regionLevel" label="��������" visible="false"></ui:field>
				<ui:field field="businessName" label="Ӫҵ��" popup="true" keyField="businessId" readOnly="true" required="true" validType="required" validMsg="Ӫҵ������Ϊ��!"></ui:field>
				<ui:field field="startDate" label="��ʼʱ��" type="date" defaultValue="today"></ui:field>
				<ui:field field="endDate" label="����ʱ��" type="date" defaultValue="today"></ui:field>
				<ui:field field="crmBusinessHallName" label="Ӫҵ������"  blankValue="" blankId=""></ui:field> 
			</ui:dataset>
 				
			<ui:dataset datasetId="Ds_BusinessOperationInfo" loadDataAction="CcQueryService" loadDataActionMethod="businessOperationInfoQuery" pageIndex="1" pageSize="10" autoLoadPage="true" staticDataSource="true" async="true" readOnly="true" loadAsNeeded="true" paginate="true">				
				<ui:field field="operationId" label="��Ӫ��ʶ" visible="false" ></ui:field>
				<ui:field field="businessId" label="Ӫҵ����ʶ" visible="false" ></ui:field>
				<ui:field field="lanId" label="������" attrCode="DC_AUTHORIZED_LAN" editable="true" ></ui:field>
				<ui:field field="businessName" label="Ӫҵ��" ></ui:field>
				<ui:field field="crmName" label="CRMϵͳӪҵ������"></ui:field>
				<ui:field field="partyRoleId" label="¼����" visible="false"></ui:field>
				<ui:field field="custInfoTotal" label="�ͻ���������"></ui:field>
				<ui:field field="custInfoTimelyCount" label="�ͻ�����������ʱ��"></ui:field>
				<ui:field field="custInfoTimelyRate" label="�ͻ�����������ʱ��"></ui:field>
				<ui:field field="custInfoAuditTotal" label="�ͻ����ϻ�������"></ui:field>
				<ui:field field="custInfoAuditErrCount" label="�ͻ����ϻ��˲������"></ui:field>
				<ui:field field="custInfoErrRate" label="�ͻ����ϲ����" ></ui:field>
				<ui:field field="removeKeepCount" label="���������¼��" ></ui:field>
				<ui:field field="removeKeepSuccessCount" label="��������ɹ���"></ui:field>
				<ui:field field="removeKeepSuccessRate" label="��������ɹ���"></ui:field>
				<ui:field field="custEvaluateCount" label="���۵Ŀͻ���"></ui:field>
				<ui:field field="custSatisfationCount" label="����ͻ���"></ui:field>
				<ui:field field="custSatisfationRate" label="�ͻ������"></ui:field>
				<ui:field field="enterDate" label="¼��ʱ��" visible="false" ></ui:field>
				
			   <ui:parameter parameterId="lanId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessId" value=""></ui:parameter>
			   <ui:parameter parameterId="crmBusinessHallName" value=""></ui:parameter>
			   <ui:parameter parameterId="startDate" value=""></ui:parameter>
			   <ui:parameter parameterId="endDate" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="Ds_BusinessOperationData" readOnly="false" staticDataSource="true"  readOnly="true">				
				<ui:field field="operationId" label="��Ӫ��ʶ" visible="false" ></ui:field>
				<ui:field field="businessId" label="Ӫҵ����ʶ" visible="false" ></ui:field>
				<ui:field field="lanId" label="������" attrCode="DC_AUTHORIZED_LAN" editable="true"  visible="false"></ui:field>
				<ui:field field="businessName" label="Ӫҵ��" visible="false"></ui:field>
				<ui:field field="crmName" label="CRMϵͳӪҵ������" visible="false" ></ui:field>
				<ui:field field="partyRoleId" label="¼����" visible="false"></ui:field>
				<ui:field field="custInfoTotal" label="�ͻ���������" type="int" size="9" required="true" validType="required" validMsg="�ͻ�������������Ϊ��!"></ui:field>
				<ui:field field="custInfoTimelyCount" label="�ͻ�����������ʱ��" type="int" size="9" required="true" validType="required" validMsg="�ͻ�����������ʱ������Ϊ��!"></ui:field>
				<ui:field field="custInfoTimelyRate" label="�ͻ�����������ʱ��"  ></ui:field>
				<ui:field field="custInfoAuditTotal" label="�ͻ����ϻ�������" type="int" size="9" required="true" validType="required" validMsg="�ͻ����ϻ�����������Ϊ��!"></ui:field>
				<ui:field field="custInfoAuditErrCount" label="�ͻ����ϻ��˲������" type="int" size="9" required="true" validType="required" validMsg="�ͻ����ϻ��˲����������Ϊ��!"></ui:field>
				<ui:field field="custInfoErrRate" label="�ͻ����ϲ����"   ></ui:field>
				<ui:field field="removeKeepCount" label="���������¼��" type="int" size="9" required="true" validType="required" validMsg="���������¼������Ϊ��!"></ui:field>
				<ui:field field="removeKeepSuccessCount" label="��������ɹ���" type="int" size="9" required="true" validType="required" validMsg="��������ɹ�������Ϊ��!"></ui:field>
				<ui:field field="removeKeepSuccessRate" label="��������ɹ���"  ></ui:field>
				<ui:field field="custEvaluateCount" label="���۵Ŀͻ���" type="int" size="9" required="true" validType="required" validMsg="���۵Ŀͻ�������Ϊ��!"></ui:field>
				<ui:field field="custSatisfationCount" label="����ͻ���" type="int" size="9" required="true" validType="required" validMsg="����ͻ�������Ϊ��!"></ui:field>
				<ui:field field="custSatisfationRate" label="�ͻ������"  ></ui:field>
				<ui:field field="enterDate" label="¼��ʱ��" type="date"  required="true" validType="required" validMsg="¼��ʱ�䲻��Ϊ��!"></ui:field>
			</ui:dataset>			
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="left" style="width: 250px;">
					<ui:tabpane id="tabset_servManager">
						<ui:tabpage desc="��ѯ����">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form dataset="Ds_QryBusinessOperationInfo" submit="btn_query"></ui:form>
									<div style="text-align: center;">
										<ui:button id="btn_query">��ѯ</ui:button>
									</div>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
				
				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="top" style="height:300px;">
							<ui:panel type="titleList" desc="Ӫҵ��ӳ���б�">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table id="table_BusinessOperationInfo" dataset="Ds_BusinessOperationInfo" >  </ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<div class="customerpilot" extra="customerpilot" id="pilotAttribute" dataset="Ds_BusinessOperationInfo"></div>
										</ui:pane>
									</ui:layout>
								</ui:content>
							</ui:panel>
						</ui:pane>
						
						<ui:pane position="center">
							<ui:tabpane id="tabset_queryMb">
								<ui:tabpage desc="Ӫҵ��ӳ������">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:form dataset="Ds_BusinessOperationData"  labelLayout="21%" inputLayout="15%"></ui:form>
										</ui:pane>
										<ui:pane position="bottom" style="height:10px">
										    <div>
											<ui:button id="btn_Mofify">�޸�</ui:button>
											<ui:button id="btn_Add">����</ui:button>
											<ui:button id="btn_Del">ɾ��</ui:button>	
											<ui:button id="btn_Reset">����</ui:button>	
											<ui:button id="btn_Confirm">����</ui:button>
											<ui:button id="btn_cansel">ȡ��</ui:button>
											<ui:button id="btn_export">����Excel</ui:button>
										    </div>
									    </ui:pane>
									</ui:layout>
								</ui:tabpage>
							</ui:tabpane>
						</ui:pane>
		
						<ui:pane position="bottom">
                             <br>	 
						</ui:pane>
					</ui:layout>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
