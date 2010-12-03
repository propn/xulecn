<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library"
			CONTENT="kernel,customerpilot,calendar,validator,tree,treeList">
		<title>营业厅运营数据管理</title>
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
		 		alert("开始时间不能大于结束时间！");
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
				alert("该记录录入时间为："+enterDate1+"，并非当月时间故不能对其进行修改操作！");
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
	        if(confirm('确定要删除该记录吗？')==true){
				var operationId = Ds_BusinessOperationInfo.getValue("operationId");
		        var callBack = function(reply ){
				   result = reply.getResult() ; 
					   if(result=='1'){
					      alert("操作成功！");
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
	  var year = now.getFullYear();//获取完整的年份
	  var month = now.getMonth()+1; //获取当前月份(0-11,0代表1月) 
	  var day =  now.getDate(); //获取当前日(1-31) 
	  var date = year+"-"+month+"-01"+"";//当月1号
	  var curDate = year+"-"+month+"-"+day+"";	  
	 
	  function btn_Confirm_onClick(){
 		if( actionType == "" ) {
 			    alert("当前操作为空，请重新选择!");
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
			alert("修改录入时间出错（范围为:大于等于当月1号且小于等于当天），请重新输入！");
			return;
		}
		//对输入数字进行校验
		if(custInfoTimelyCount>custInfoTotal){
			alert("客户资料完整及时数不能大于客户资料总数！请重新输入！");
			return;
		}
		if(custInfoAuditTotal>custInfoTotal){
			alert("客户资料稽核总数不能大于客户资料总数！请重新输入！");
			return;
		}
		if(custInfoAuditErrCount>custInfoAuditTotal){
			alert("客户资料稽核差错数量不能大于客户资料完整及时数！请重新输入！");
			return;
		}
		if(removeKeepCount>custInfoTotal){
			alert("拆机挽留记录数不能大于客户资料总数！请重新输入！");
			return;
		}
		if(removeKeepSuccessCount>removeKeepCount){
			alert("拆机挽留成功数不能大于拆机挽留记录数！请重新输入！");
			return;
		}
		if(custEvaluateCount>custInfoTotal){
			alert("评价的客户数不能大于客户资料总数！请重新输入！");
			return;
		}
		if(custSatisfationCount>custEvaluateCount){
			alert("满意客户数不能大于评价的客户数！请重新输入！");
			return;
		}
		
		var custInfoTimelyRate = ((custInfoTimelyCount/custInfoTotal)*100).toString().substr(0,4)+"%"; //客户资料完整及时率
		Ds_BusinessOperationData.setValue("custInfoTimelyRate",custInfoTimelyRate);
		var custInfoErrRate = ((custInfoAuditErrCount/custInfoAuditTotal)*100).toString().substr(0,4)+"%"; //客户资料差错率
		Ds_BusinessOperationData.setValue("custInfoErrRate",custInfoErrRate);
		var removeKeepSuccessRate = ((removeKeepSuccessCount/removeKeepCount)*100).toString().substr(0,4)+"%"; //拆机挽留成功率
		Ds_BusinessOperationData.setValue("removeKeepSuccessRate",removeKeepSuccessRate);
		var custSatisfationRate = ((custSatisfationCount/custEvaluateCount)*100).toString().substr(0,4)+"%";	//客户满意度	
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
				alert("操作成功！");	
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
		 *privilegeType -- 权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码
		 */
		obj["privilegeType"] = "3" ;
		/*
		 *privilegeCode -- 
		 *权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
		 */
		 var menuCode = Global.getCurrentMenuCode();
		obj["privilegeCode"] = menuCode;
		var regionIds = Ds_QryBusinessOperationInfo.getValue("businessId");
		var regionLevel = Ds_QryBusinessOperationInfo.getValue("regionLevel");
		if(regionIds!=null && regionIds!="")
		   obj["regionIds"] = regionIds.substr(0,regionIds.length-1);
		else
		   obj["regionIds"] = regionIds;
			
		obj["selectDistinctRegionLevel"] = 1 ;//1表示只能选择相同级别的地域,2表示可以选择不同级别的地域
		
		/*selectParent :1表示不能选择上级地域,2表示可以选择上级地域,默认不可以选择上级地域,在可
		 * 以选择上级地域的条件下,选择的多个区域必须是相同级别的,如传递的regionLevel参数为98F,且
		 * selectParent 为2,则可以选择局站和局站以上所有有权限的地域,但是多选的区域必须是相同级别额,比
		 * 如不能选择一个分公司和一个市公司.
		 */
		obj["selectParent"] = "2";
		obj["checkChildren"] = "2";//1表示钩选下级节点,2表示不钩选
		
		/*
		 * 区域级别,97A表示集团公司,97E表示省公司,97B表示本地网,97D表示营业区,98D
		 * 表示处理局,98E表示母局,98F表示局站
		 */
		 //if(regionLevel!=null && regionLevel!="")
		     //obj["regionLevel"] = regionLevel ;	
		 //else
		obj["regionLevel"] = "97D" ;	
		obj["selectType"] = "2" ;//单选多选标志,1 为单选,2 为多选
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
    /*导出Excel*/
	function btn_export_onClick(){
		exportExcel(Ds_BusinessOperationInfo, "BusinessOperationInfo.jsp");
	}    	  		  		     				
 	</script>
	</head>
	<body>
		<div id="datasetDefine">

			<!--营业厅运营数据列表查询数据集-->
			<ui:dataset datasetId="Ds_QryBusinessOperationInfo" readOnly="false" staticDataSource="true">
				<ui:field field="lanId" label="本地网" attrCode="DC_AUTHORIZED_LAN" readOnly="true"  required="true"></ui:field>
				<ui:field field="businessId" label="地域编码"  visible="false" ></ui:field>
				<ui:field field="regionLevel" label="地域类型" visible="false"></ui:field>
				<ui:field field="businessName" label="营业区" popup="true" keyField="businessId" readOnly="true" required="true" validType="required" validMsg="营业区不能为空!"></ui:field>
				<ui:field field="startDate" label="开始时间" type="date" defaultValue="today"></ui:field>
				<ui:field field="endDate" label="结束时间" type="date" defaultValue="today"></ui:field>
				<ui:field field="crmBusinessHallName" label="营业厅名称"  blankValue="" blankId=""></ui:field> 
			</ui:dataset>
 				
			<ui:dataset datasetId="Ds_BusinessOperationInfo" loadDataAction="CcQueryService" loadDataActionMethod="businessOperationInfoQuery" pageIndex="1" pageSize="10" autoLoadPage="true" staticDataSource="true" async="true" readOnly="true" loadAsNeeded="true" paginate="true">				
				<ui:field field="operationId" label="运营标识" visible="false" ></ui:field>
				<ui:field field="businessId" label="营业区标识" visible="false" ></ui:field>
				<ui:field field="lanId" label="本地网" attrCode="DC_AUTHORIZED_LAN" editable="true" ></ui:field>
				<ui:field field="businessName" label="营业区" ></ui:field>
				<ui:field field="crmName" label="CRM系统营业厅名称"></ui:field>
				<ui:field field="partyRoleId" label="录入人" visible="false"></ui:field>
				<ui:field field="custInfoTotal" label="客户资料总数"></ui:field>
				<ui:field field="custInfoTimelyCount" label="客户资料完整及时数"></ui:field>
				<ui:field field="custInfoTimelyRate" label="客户资料完整及时率"></ui:field>
				<ui:field field="custInfoAuditTotal" label="客户资料稽核总数"></ui:field>
				<ui:field field="custInfoAuditErrCount" label="客户资料稽核差错数量"></ui:field>
				<ui:field field="custInfoErrRate" label="客户资料差错率" ></ui:field>
				<ui:field field="removeKeepCount" label="拆机挽留记录数" ></ui:field>
				<ui:field field="removeKeepSuccessCount" label="拆机挽留成功数"></ui:field>
				<ui:field field="removeKeepSuccessRate" label="拆机挽留成功率"></ui:field>
				<ui:field field="custEvaluateCount" label="评价的客户数"></ui:field>
				<ui:field field="custSatisfationCount" label="满意客户数"></ui:field>
				<ui:field field="custSatisfationRate" label="客户满意度"></ui:field>
				<ui:field field="enterDate" label="录入时间" visible="false" ></ui:field>
				
			   <ui:parameter parameterId="lanId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessId" value=""></ui:parameter>
			   <ui:parameter parameterId="crmBusinessHallName" value=""></ui:parameter>
			   <ui:parameter parameterId="startDate" value=""></ui:parameter>
			   <ui:parameter parameterId="endDate" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="Ds_BusinessOperationData" readOnly="false" staticDataSource="true"  readOnly="true">				
				<ui:field field="operationId" label="运营标识" visible="false" ></ui:field>
				<ui:field field="businessId" label="营业区标识" visible="false" ></ui:field>
				<ui:field field="lanId" label="本地网" attrCode="DC_AUTHORIZED_LAN" editable="true"  visible="false"></ui:field>
				<ui:field field="businessName" label="营业区" visible="false"></ui:field>
				<ui:field field="crmName" label="CRM系统营业厅名称" visible="false" ></ui:field>
				<ui:field field="partyRoleId" label="录入人" visible="false"></ui:field>
				<ui:field field="custInfoTotal" label="客户资料总数" type="int" size="9" required="true" validType="required" validMsg="客户资料总数不能为空!"></ui:field>
				<ui:field field="custInfoTimelyCount" label="客户资料完整及时数" type="int" size="9" required="true" validType="required" validMsg="客户资料完整及时数不能为空!"></ui:field>
				<ui:field field="custInfoTimelyRate" label="客户资料完整及时率"  ></ui:field>
				<ui:field field="custInfoAuditTotal" label="客户资料稽核总数" type="int" size="9" required="true" validType="required" validMsg="客户资料稽核总数不能为空!"></ui:field>
				<ui:field field="custInfoAuditErrCount" label="客户资料稽核差错数量" type="int" size="9" required="true" validType="required" validMsg="客户资料稽核差错数量不能为空!"></ui:field>
				<ui:field field="custInfoErrRate" label="客户资料差错率"   ></ui:field>
				<ui:field field="removeKeepCount" label="拆机挽留记录数" type="int" size="9" required="true" validType="required" validMsg="拆机挽留记录数不能为空!"></ui:field>
				<ui:field field="removeKeepSuccessCount" label="拆机挽留成功数" type="int" size="9" required="true" validType="required" validMsg="拆机挽留成功数不能为空!"></ui:field>
				<ui:field field="removeKeepSuccessRate" label="拆机挽留成功率"  ></ui:field>
				<ui:field field="custEvaluateCount" label="评价的客户数" type="int" size="9" required="true" validType="required" validMsg="评价的客户数不能为空!"></ui:field>
				<ui:field field="custSatisfationCount" label="满意客户数" type="int" size="9" required="true" validType="required" validMsg="满意客户数不能为空!"></ui:field>
				<ui:field field="custSatisfationRate" label="客户满意度"  ></ui:field>
				<ui:field field="enterDate" label="录入时间" type="date"  required="true" validType="required" validMsg="录入时间不能为空!"></ui:field>
			</ui:dataset>			
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="left" style="width: 250px;">
					<ui:tabpane id="tabset_servManager">
						<ui:tabpage desc="查询条件">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form dataset="Ds_QryBusinessOperationInfo" submit="btn_query"></ui:form>
									<div style="text-align: center;">
										<ui:button id="btn_query">查询</ui:button>
									</div>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
				
				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="top" style="height:300px;">
							<ui:panel type="titleList" desc="营业厅映射列表">
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
								<ui:tabpage desc="营业厅映射资料">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:form dataset="Ds_BusinessOperationData"  labelLayout="21%" inputLayout="15%"></ui:form>
										</ui:pane>
										<ui:pane position="bottom" style="height:10px">
										    <div>
											<ui:button id="btn_Mofify">修改</ui:button>
											<ui:button id="btn_Add">增加</ui:button>
											<ui:button id="btn_Del">删除</ui:button>	
											<ui:button id="btn_Reset">重置</ui:button>	
											<ui:button id="btn_Confirm">保存</ui:button>
											<ui:button id="btn_cansel">取消</ui:button>
											<ui:button id="btn_export">导出Excel</ui:button>
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
