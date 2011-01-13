<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library"
			CONTENT="kernel,customerpilot,calendar,validator,tree,treeList">
		<title>Ӫҵ��ӳ���ϵ����</title>
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
			
			Ds_QryBusinessMapInfo.setValue("businessName",defaultBusinessName);
			Ds_QryBusinessMapInfo.setValue("businessId",defaultBusinessId);
			Ds_BusinessMapRelaInfo.setFieldReadOnly("crmName",true);
			$("button_Ds_BusinessMapRelaInfo_crmName").disabled=true;
			
		}
		
		function btn_query_onClick() {
			actionType = "" ;
			if( !$("form_Ds_QryBusinessMapInfo").validate()){
				return ;
			}
			Ds_BusinessMapRelaInfo.setReadOnly(true);
			document.all.btn_Add.disabled=false;
			document.all.btn_Del.disabled=false;
			document.all.btn_Reset.disabled=true;
			document.all.btn_Mofify.disabled=false;
			Ds_BusinessMapInfo.clearData();
			var lanId = Ds_QryBusinessMapInfo.getValue("lanId");
			businessId = Ds_QryBusinessMapInfo.getValue("businessId");
			var businessHallName = Ds_QryBusinessMapInfo.getValue("businessHallName");
		 
			Ds_BusinessMapInfo.parameters().setValue("lanId",lanId);
			Ds_BusinessMapInfo.parameters().setValue("businessId",businessId);	
			Ds_BusinessMapInfo.parameters().setValue("businessHallName",businessHallName);
			Ds_BusinessMapInfo.flushData();  
			  
			var cur = Ds_BusinessMapInfo.getFirstRecord();
			if(cur){
			 	Ds_BusinessMapRelaInfo.setValue("crmId",cur.getValue("crmId"));
			 	Ds_BusinessMapRelaInfo.setValue("crmName",cur.getValue("crmName"));
			 	Ds_BusinessMapRelaInfo.setValue("hbId",cur.getValue("hbId"));
			 	Ds_BusinessMapRelaInfo.setValue("hbName",cur.getValue("hbName"));
			 	Ds_BusinessMapRelaInfo.setValue("autoPayId",cur.getValue("autoPayId"));
			 	Ds_BusinessMapRelaInfo.setValue("autoPayName",cur.getValue("autoPayName"));
			 	Ds_BusinessMapRelaInfo.setValue("customerId",cur.getValue("customerId"));
			 	Ds_BusinessMapRelaInfo.setValue("customerName",cur.getValue("customerName"));
			 	Ds_BusinessMapRelaInfo.setValue("custServId",cur.getValue("custServId"));
			 	Ds_BusinessMapRelaInfo.setValue("custServName",cur.getValue("custServName"));
		 	}
		}
		
		function btn_Mofify_onClick() {
		    actionType = "update" ;
		    var cur = Ds_BusinessMapInfo.getCurrent();
		    if(cur==null){
		    	return;
		    }
			Ds_BusinessMapRelaInfo.setReadOnly(false);
			document.all.btn_Mofify.disabled=true;
			document.all.btn_Add.disabled=true;
			document.all.btn_Del.disabled=true;
			document.all.btn_Reset.disabled=false;
			$("button_Ds_BusinessMapRelaInfo_crmName").disabled=false;
			Ds_BusinessMapRelaInfo.setFieldReadOnly("crmId",true);
			Ds_BusinessMapRelaInfo.setFieldReadOnly("crmName",true);
		}
		
	   function btn_Add_onClick(){
	        actionType = "insert" ;
	        Ds_BusinessMapRelaInfo.clearData();
	        Ds_BusinessMapRelaInfo.setReadOnly(false);
			document.all.btn_Mofify.disabled=true;	
			document.all.btn_Add.disabled=true;
			document.all.btn_Del.disabled=true;
			document.all.btn_Reset.disabled=false;
			$("button_Ds_BusinessMapRelaInfo_crmName").disabled=false;
			Ds_BusinessMapRelaInfo.setFieldReadOnly("crmId",true);
			Ds_BusinessMapRelaInfo.setFieldReadOnly("crmName",true);
		}
		
	  function btn_Del_onClick(){	
	     if(Ds_BusinessMapInfo.getCurrent()){  
	        if(confirm('ȷ��Ҫɾ���ü�¼��')==true){
				var mapId = Ds_BusinessMapInfo.getValue("mapId");
		        var callBack = function(reply ){
				   result = reply.getResult() ; 
					   if(result=='1'){
					      alert("�����ɹ���");
					      btn_query_onClick();
					   } 
				   }
		  		var ajaxCall = new NDAjaxCall(true);
		  		ajaxCall.remoteCall("CcQueryService","delBusinessMapInfo",[mapId],callBack); 
		  		}
		     }
	   }
	   
	  function btn_Reset_onClick(){	
	   if(actionType){ 
			Ds_BusinessMapRelaInfo.clearData() ; 
		}
		$("button_Ds_BusinessMapRelaInfo_crmName").disabled=true;	
	  }	
	  
	  function btn_Confirm_onClick(){
 		if( actionType == "" ) {
 			    alert("��ǰ����Ϊ�գ�������ѡ��!");
		  	return ;
		}
		if( !$("form_Ds_BusinessMapRelaInfo").validate()){
			return ;
		}

		var crmId = Ds_BusinessMapRelaInfo.getValue("crmId");
		var crmName = Ds_BusinessMapRelaInfo.getValue("crmName");
		var hbId = Ds_BusinessMapRelaInfo.getValue("hbId");
		var hbName = Ds_BusinessMapRelaInfo.getValue("hbName");
		var autoPayId = Ds_BusinessMapRelaInfo.getValue("autoPayId");
		var autoPayName = Ds_BusinessMapRelaInfo.getValue("autoPayName");
		var customerId = Ds_BusinessMapRelaInfo.getValue("customerId");
		var customerName = Ds_BusinessMapRelaInfo.getValue("customerName");
		var custServId = Ds_BusinessMapRelaInfo.getValue("custServId");
		var custServName = Ds_BusinessMapRelaInfo.getValue("custServName");
		
		var ajaxCall = new NDAjaxCall(true);		 
		var callBack = function(reply ){
			var reMap = reply.getResult() ;
			if(reMap){
				alert("�����ɹ���");	
			}
			btn_query_onClick();// 
		}
		
		if( actionType == "insert" ){
			ajaxCall.remoteCall("CcQueryService","insertBusinessMapInfo",[crmId,crmName,hbId,hbName,autoPayId,
																		autoPayName,customerId,customerName,custServId,custServName],callBack); 
		}
		else if( actionType == "update" ){
			var mapId = Ds_BusinessMapInfo.getValue("mapId");
			var lanId = Ds_BusinessMapInfo.getValue("lanId");
			var businessId = Ds_BusinessMapInfo.getValue("businessId");
			var businessName = Ds_BusinessMapInfo.getValue("businessName");		
			ajaxCall.remoteCall("CcQueryService","updateBusinessMapInfo",[mapId,lanId,businessId,businessName,crmId,crmName,hbId,hbName,autoPayId,
																		autoPayName,customerId,customerName,custServId,custServName],callBack);
		}
		Ds_BusinessMapRelaInfo.setReadOnly(true);
		actionType = "" ;
	  }
	  
	 function table_BusinessMapInfo_onclick(){
		Ds_BusinessMapRelaInfo.clearData();
		Ds_BusinessMapRelaInfo.setReadOnly(true);
		var cur = Ds_BusinessMapInfo.getCurrent();
		if(cur){
		 	Ds_BusinessMapRelaInfo.setValue("crmId",cur.getValue("crmId"));
		 	Ds_BusinessMapRelaInfo.setValue("crmName",cur.getValue("crmName"));
		 	Ds_BusinessMapRelaInfo.setValue("hbId",cur.getValue("hbId"));
		 	Ds_BusinessMapRelaInfo.setValue("hbName",cur.getValue("hbName"));
		 	Ds_BusinessMapRelaInfo.setValue("autoPayId",cur.getValue("autoPayId"));
		 	Ds_BusinessMapRelaInfo.setValue("autoPayName",cur.getValue("autoPayName"));
		 	Ds_BusinessMapRelaInfo.setValue("customerId",cur.getValue("customerId"));
		 	Ds_BusinessMapRelaInfo.setValue("customerName",cur.getValue("customerName"));
		 	Ds_BusinessMapRelaInfo.setValue("custServId",cur.getValue("custServId"));
		 	Ds_BusinessMapRelaInfo.setValue("custServName",cur.getValue("custServName"));
			document.all.btn_Mofify.disabled=false;
		    document.all.btn_Del.disabled=false; 
		    document.all.btn_Reset.disabled=true; 
		    document.all.btn_Add.disabled=false;
	    }
	 }
	function btn_cansel_onClick(){
		actionType = "" ;
		Ds_BusinessMapRelaInfo.setReadOnly(true);
		var cur = Ds_BusinessMapInfo.getCurrent();
		if(cur){
		 	Ds_BusinessMapRelaInfo.setValue("crmId",cur.getValue("crmId"));
		 	Ds_BusinessMapRelaInfo.setValue("crmName",cur.getValue("crmName"));
		 	Ds_BusinessMapRelaInfo.setValue("hbId",cur.getValue("hbId"));
		 	Ds_BusinessMapRelaInfo.setValue("hbName",cur.getValue("hbName"));
		 	Ds_BusinessMapRelaInfo.setValue("autoPayId",cur.getValue("autoPayId"));
		 	Ds_BusinessMapRelaInfo.setValue("autoPayName",cur.getValue("autoPayName"));
		 	Ds_BusinessMapRelaInfo.setValue("customerId",cur.getValue("customerId"));
		 	Ds_BusinessMapRelaInfo.setValue("customerName",cur.getValue("customerName"));
		 	Ds_BusinessMapRelaInfo.setValue("custServId",cur.getValue("custServId"));
		 	Ds_BusinessMapRelaInfo.setValue("custServName",cur.getValue("custServName"));
			document.all.btn_Mofify.disabled=false;
			document.all.btn_Del.disabled=false;
		}
		else{	
			document.all.btn_Mofify.disabled=true;
		    document.all.btn_Del.disabled=true; 
	    }
	    document.all.btn_Add.disabled=false; 
	    $("button_Ds_BusinessMapRelaInfo_crmName").disabled=true;	
	    	
	}	

   function button_Ds_QryBusinessMapInfo_businessName_onClick(){
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
		var regionIds = Ds_QryBusinessMapInfo.getValue("businessId");
		var regionLevel = Ds_QryBusinessMapInfo.getValue("regionLevel");
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
			Ds_QryBusinessMapInfo.setValue("businessId", businessId);
			Ds_QryBusinessMapInfo.setValue("businessName", businessName.substr(0,businessName.length-1));
			Ds_QryBusinessMapInfo.setValue("regionLevel", returnValue[0]["regionLevel"]);
		}
		else{
		  return;
		}
    }	
    /*����Excel*/
	function btn_export_onClick(){
		exportExcel(Ds_BusinessMapInfo, "BusinessMapInfo.jsp");
	} 

	function  button_Ds_BusinessMapRelaInfo_crmName_onClick(){
        var menuCode = Global.getCurrentMenuCode(); 
		var para = new Object();
		para["staffCode"] = "";
        para["privilegeType"] = "3";//Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
        para["privilegeCode"] = menuCode;//Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID
        para["orgType"] = "6" ;//��֯����ID,5��ʾ����,0��ʾ���Ź�˾,1��ʾʡ��˾,2��ʾ�й�˾,3��ʾ�ֹ�˾,6��ʾ����,9��ʾ������֯.����������������,��ʾ���е���֯����
        para["selectType"] = "1" ;//1��ʾ��ѡ,2��ʾ��ѡ
        para["oldIds"] =  getStaffOrganizationId();//����֯ѡ��ҳ��ʱĬ�ϱ�ѡ��ļ�¼
        para["checkChildren"] = "2" ;//1��ʾ����ѡ��ǰ��֯��ʱ���Զ���ѡ�¼��ڵ�,2��ʾ���Զ���ѡ
        para["uncheckedParent"] = "1" ;//1��ʾ��ѡ�¼��ڵ�ʱ�Զ�ȡ����ѡ�ϼ��ڵ�.2��ʾ��������
        para["downloadWhenChecked"] = "2" ;//1��ʾ����ѡ��¼��ʱ�������¼��ڵ�,2��ʾ��ѡ��¼��ʱ�������¼��ڵ�(�����¼��ʱ��������¼��ڵ�)
        para["selectParent"] = "2" ;//1��ʾ����ѡ���ϼ���֯,2��ʾ����ѡ���ϼ���֯
        para["selectDistinctOrgType"] = "2" ;//1��ʾֻ��ѡ����ͬ�������֯,2��ʾ����ѡ��ͬ�������֯
  		var returnValue = window.showModalDialog("../../oaas/common/OrganizationSelect.jsp", para, "dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
        
        if (null == returnValue) {
            return;
        }
        var reLength = returnValue.length;
        var orgId = null;
        var orgName = null;
        var orgType = null;
        
        
        //if(returnValue)
        //{
        //alert(orgName);
        //return;
        //}
        
         for( var i = 0; i < reLength; i++ ){
            orgType = returnValue[0]["orgTypeId"];
            if(orgType!=5&&orgType!=6){
            	alert("ֻ��ѡ���Ż���飡");
            	return;
            }
            if (0 == i) {
                orgId = returnValue[i]["orgId"];
                orgName = returnValue[i]["orgName"];
            } else if (1 == i) {
                orgId = orgId + "," + returnValue[i]["orgId"];
                orgName = orgName + "...";
            } else {
                orgId = orgId + "," + returnValue[i]["orgId"];
            }
        }
        
       
        if (0 < reLength) {
            Ds_BusinessMapRelaInfo.setValue("crmName", orgName);
            Ds_BusinessMapRelaInfo.setValue("crmId", orgId);
        }
        
        
    }
    
 	</script>
	</head>
	<body>
		<div id="datasetDefine">

			<!--Ӫҵ��ӳ���ϵ�����б��ѯ���ݼ�-->
			<ui:dataset datasetId="Ds_QryBusinessMapInfo" readOnly="false" staticDataSource="true">
				<ui:field field="lanId" label="������" attrCode="DC_AUTHORIZED_LAN" readOnly="true"  required="true"></ui:field>
				<ui:field field="businessId" label="�������"  visible="false" ></ui:field>
				<ui:field field="regionLevel" label="��������" visible="false"></ui:field>
				<ui:field field="businessName" label="Ӫҵ��" popup="true" keyField="businessId" readOnly="true" required="true" validType="required" validMsg="Ӫҵ������Ϊ��!"></ui:field>
				<ui:field field="businessHallName" label="Ӫҵ������"  blankValue="" blankId=""></ui:field> 
			</ui:dataset>
						
			<ui:dataset datasetId="Ds_BusinessMapInfo" loadDataAction="CcQueryService" loadDataActionMethod="businessMapInfoQuery" pageIndex="1" pageSize="20" autoLoadPage="true" staticDataSource="true"  readOnly="true" loadAsNeeded="true" paginate="true">				
				<ui:field field="mapId" label="ӳ���ʶ" visible="false" ></ui:field>
				<ui:field field="businessId" label="Ӫҵ����ʶ" visible="false" ></ui:field>
				<ui:field field="lanId" label="������" attrCode="DC_AUTHORIZED_LAN" editable="true" ></ui:field>
				<ui:field field="businessName" label="Ӫҵ��" ></ui:field>
				<ui:field field="crmId" label="CRMϵͳӪҵ��ID"></ui:field> 
				<ui:field field="crmName" label="CRMϵͳӪҵ������"></ui:field>
				<ui:field field="hbId" label="HBϵͳӪҵ������"></ui:field>
				<ui:field field="hbName" label="HBϵͳӪҵ������"></ui:field>
				<ui:field field="autoPayId" label="�����ɷ�ϵͳӪҵ��ID"></ui:field>
				<ui:field field="autoPayName" label="�����ɷ�ϵͳӪҵ������"></ui:field>
				<ui:field field="customerId" label="ǰ̨�ͻ��Ŷ�ϵͳӪҵ��ID"></ui:field>
				<ui:field field="customerName" label="ǰ̨�ͻ��Ŷ�ϵͳӪҵ������"></ui:field>
				<ui:field field="custServId" label="10000��ϵͳӪҵ��ID" ></ui:field>
				<ui:field field="custServName" label="10000��ϵͳӪҵ������" ></ui:field>
			 		   				
			   <ui:parameter parameterId="lanId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessHallName" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="Ds_BusinessMapRelaInfo" readOnly="false" staticDataSource="true"  readOnly="true">				
				<ui:field field="lanId" label="������" attrCode="DC_AUTHORIZED_LAN" visible="false" ></ui:field>
				<ui:field field="businessName" label="Ӫҵ��" visible="false" ></ui:field>
				<ui:field field="departLevel" label="����" visible="false" ></ui:field> 
				<ui:field field="crmId" label="CRMϵͳӪҵ��ID" readOnly="true" ></ui:field> 
				<ui:field field="crmName" label="CRMϵͳӪҵ������" popup="true" readOnly="true" keyField="crmId" required="true" validMsg="CRMϵͳӪҵ�����Ʋ���Ϊ��"></ui:field>
				<ui:field field="hbId" label="HBϵͳӪҵ��ID" size="30"></ui:field>
				<ui:field field="hbName" label="HBϵͳӪҵ������" size="90"></ui:field>
				<ui:field field="autoPayId" label="�����ɷ�ϵͳӪҵ��ID" size="30"></ui:field>
				<ui:field field="autoPayName" label="�����ɷ�ϵͳӪҵ������" size="90"></ui:field>
				<ui:field field="customerId" label="ǰ̨�ͻ��Ŷ�ϵͳӪҵ��ID" size="30"></ui:field>
				<ui:field field="customerName" label="ǰ̨�ͻ��Ŷ�ϵͳӪҵ������" size="90"></ui:field>
				<ui:field field="custServId" label="10000��Ͷ�߹���ϵͳӪҵ��ID" size="30"></ui:field>
				<ui:field field="custServName" label="10000��Ͷ�߹���ϵͳӪҵ������" size="90" ></ui:field>
			</ui:dataset>			
		</div>
		
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="left" style="width: 250px;">
					<ui:tabpane id="tabset_servManager">
						<ui:tabpage desc="��ѯ����">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form dataset="Ds_QryBusinessMapInfo" submit="btn_query"></ui:form>
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
											<ui:table id="table_BusinessMapInfo" dataset="Ds_BusinessMapInfo" >  </ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<div class="customerpilot" extra="customerpilot" id="pilotAttribute" dataset="Ds_BusinessMapInfo"></div>
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
											<ui:form dataset="Ds_BusinessMapRelaInfo"  labelLayout="21%" inputLayout="15%"></ui:form>
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
