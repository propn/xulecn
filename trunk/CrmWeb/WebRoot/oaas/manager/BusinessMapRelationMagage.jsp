<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library"
			CONTENT="kernel,customerpilot,calendar,validator,tree,treeList">
		<title>营业厅映射关系管理</title>
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
	        if(confirm('确定要删除该记录吗？')==true){
				var mapId = Ds_BusinessMapInfo.getValue("mapId");
		        var callBack = function(reply ){
				   result = reply.getResult() ; 
					   if(result=='1'){
					      alert("操作成功！");
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
 			    alert("当前操作为空，请重新选择!");
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
				alert("操作成功！");	
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
		 *privilegeType -- 权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码
		 */
		obj["privilegeType"] = "3" ;
		/*
		 *privilegeCode -- 
		 *权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
		 */
		 var menuCode = Global.getCurrentMenuCode();
		obj["privilegeCode"] = menuCode;
		var regionIds = Ds_QryBusinessMapInfo.getValue("businessId");
		var regionLevel = Ds_QryBusinessMapInfo.getValue("regionLevel");
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
			Ds_QryBusinessMapInfo.setValue("businessId", businessId);
			Ds_QryBusinessMapInfo.setValue("businessName", businessName.substr(0,businessName.length-1));
			Ds_QryBusinessMapInfo.setValue("regionLevel", returnValue[0]["regionLevel"]);
		}
		else{
		  return;
		}
    }	
    /*导出Excel*/
	function btn_export_onClick(){
		exportExcel(Ds_BusinessMapInfo, "BusinessMapInfo.jsp");
	} 

	function  button_Ds_BusinessMapRelaInfo_crmName_onClick(){
        var menuCode = Global.getCurrentMenuCode(); 
		var para = new Object();
		para["staffCode"] = "";
        para["privilegeType"] = "3";//权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码；
        para["privilegeCode"] = menuCode;//权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID
        para["orgType"] = "6" ;//组织类型ID,5表示部门,0表示集团公司,1表示省公司,2表示市公司,3表示分公司,6表示班组,9表示其他组织.如果不传递这个参数,表示所有的组织类型
        para["selectType"] = "1" ;//1表示单选,2表示多选
        para["oldIds"] =  getStaffOrganizationId();//打开组织选择页面时默认被选择的记录
        para["checkChildren"] = "2" ;//1表示当钩选当前组织的时候自动钩选下级节点,2表示不自动钩选
        para["uncheckedParent"] = "1" ;//1表示钩选下级节点时自动取消钩选上级节点.2表示不作处理
        para["downloadWhenChecked"] = "2" ;//1表示当钩选记录的时候下载下级节点,2表示钩选记录的时候不下载下级节点(点击记录的时候才下载下级节点)
        para["selectParent"] = "2" ;//1表示不能选择上级组织,2表示可以选择上级组织
        para["selectDistinctOrgType"] = "2" ;//1表示只能选择相同级别的组织,2表示可以选择不同级别的组织
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
            	alert("只能选择部门或班组！");
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

			<!--营业厅映射关系管理列表查询数据集-->
			<ui:dataset datasetId="Ds_QryBusinessMapInfo" readOnly="false" staticDataSource="true">
				<ui:field field="lanId" label="本地网" attrCode="DC_AUTHORIZED_LAN" readOnly="true"  required="true"></ui:field>
				<ui:field field="businessId" label="地域编码"  visible="false" ></ui:field>
				<ui:field field="regionLevel" label="地域类型" visible="false"></ui:field>
				<ui:field field="businessName" label="营业区" popup="true" keyField="businessId" readOnly="true" required="true" validType="required" validMsg="营业区不能为空!"></ui:field>
				<ui:field field="businessHallName" label="营业厅名称"  blankValue="" blankId=""></ui:field> 
			</ui:dataset>
						
			<ui:dataset datasetId="Ds_BusinessMapInfo" loadDataAction="CcQueryService" loadDataActionMethod="businessMapInfoQuery" pageIndex="1" pageSize="20" autoLoadPage="true" staticDataSource="true"  readOnly="true" loadAsNeeded="true" paginate="true">				
				<ui:field field="mapId" label="映射标识" visible="false" ></ui:field>
				<ui:field field="businessId" label="营业区标识" visible="false" ></ui:field>
				<ui:field field="lanId" label="本地网" attrCode="DC_AUTHORIZED_LAN" editable="true" ></ui:field>
				<ui:field field="businessName" label="营业区" ></ui:field>
				<ui:field field="crmId" label="CRM系统营业厅ID"></ui:field> 
				<ui:field field="crmName" label="CRM系统营业厅名称"></ui:field>
				<ui:field field="hbId" label="HB系统营业厅名称"></ui:field>
				<ui:field field="hbName" label="HB系统营业厅名称"></ui:field>
				<ui:field field="autoPayId" label="自助缴费系统营业厅ID"></ui:field>
				<ui:field field="autoPayName" label="自助缴费系统营业厅名称"></ui:field>
				<ui:field field="customerId" label="前台客户排队系统营业厅ID"></ui:field>
				<ui:field field="customerName" label="前台客户排队系统营业厅名称"></ui:field>
				<ui:field field="custServId" label="10000号系统营业厅ID" ></ui:field>
				<ui:field field="custServName" label="10000号系统营业厅名称" ></ui:field>
			 		   				
			   <ui:parameter parameterId="lanId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessId" value=""></ui:parameter>
			   <ui:parameter parameterId="businessHallName" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="Ds_BusinessMapRelaInfo" readOnly="false" staticDataSource="true"  readOnly="true">				
				<ui:field field="lanId" label="本地网" attrCode="DC_AUTHORIZED_LAN" visible="false" ></ui:field>
				<ui:field field="businessName" label="营业区" visible="false" ></ui:field>
				<ui:field field="departLevel" label="级别" visible="false" ></ui:field> 
				<ui:field field="crmId" label="CRM系统营业厅ID" readOnly="true" ></ui:field> 
				<ui:field field="crmName" label="CRM系统营业厅名称" popup="true" readOnly="true" keyField="crmId" required="true" validMsg="CRM系统营业厅名称不能为空"></ui:field>
				<ui:field field="hbId" label="HB系统营业厅ID" size="30"></ui:field>
				<ui:field field="hbName" label="HB系统营业厅名称" size="90"></ui:field>
				<ui:field field="autoPayId" label="自助缴费系统营业厅ID" size="30"></ui:field>
				<ui:field field="autoPayName" label="自助缴费系统营业厅名称" size="90"></ui:field>
				<ui:field field="customerId" label="前台客户排队系统营业厅ID" size="30"></ui:field>
				<ui:field field="customerName" label="前台客户排队系统营业厅名称" size="90"></ui:field>
				<ui:field field="custServId" label="10000号投诉管理系统营业厅ID" size="30"></ui:field>
				<ui:field field="custServName" label="10000号投诉管理系统营业厅名称" size="90" ></ui:field>
			</ui:dataset>			
		</div>
		
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="left" style="width: 250px;">
					<ui:tabpane id="tabset_servManager">
						<ui:tabpage desc="查询条件">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form dataset="Ds_QryBusinessMapInfo" submit="btn_query"></ui:form>
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
								<ui:tabpage desc="营业厅映射资料">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:form dataset="Ds_BusinessMapRelaInfo"  labelLayout="21%" inputLayout="15%"></ui:form>
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
