<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		
		<script>
			function btnConfirm_onClick(){
				var saveObjList = new Array() ;
				if( custDataPrivilegeInfo.getValue("controlType") == "10" ){
					var obj = new Object() ;
					obj.privId = window.dialogArguments[0] ;
					obj.dataPkey1 = "-1" ;//custDataPrivilegeInfo.getValue("custTypeId") ;
					obj.dataPkey2 = custDataPrivilegeInfo.getValue("datasetId") ;
					obj.dataPkey3 = custDataPrivilegeInfo.getValue("custTypeId");//"-1" ;
					saveObjList[0] = obj ;
				}else if( custDataPrivilegeInfo.getValue("controlType" ) == "20"){
					var record = custDataPrivilegeList.getFirstRecord() ;
					var i = 0 ;
					while( record ){
						if( System.isTrue(record.getValue("select"))){
							var obj = new Object() ;
							obj.privId = window.dialogArguments[0] ;
							obj.dataPkey1 = record.getValue("fieldId");//custDataPrivilegeInfo.getValue("custTypeId") ;
							obj.dataPkey2 = custDataPrivilegeInfo.getValue("datasetId") ;
							obj.dataPkey3 = custDataPrivilegeInfo.getValue("custTypeId") ;//record.getValue("fieldId") ;
							saveObjList[i] = obj ;
							i ++ ;
						}
						record = record.getNextRecord() ;
					}
					if( i == 0 ){
						alert("请选择数据项!");
						return ;
					}
				}else{
					alert("请选择控制类型!");
				}
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ) {
					alert("增加成功!") ;
					window.close();
				}
				if( saveObjList.length > 0 ){
					ajaxCall.remoteCall("PrivilegeService","addCustDataPrivileges",[saveObjList],callBack);
				}
			}
			
			function btnCancel_onClick(){
				window.close();
			}
			
			function DC_DATAPAGE_onSelect( dropdown, record, editor){
				custDataPrivilegeInfo.setValue("controlType","");
				custDataPrivilegeList.clearData() ;	
				return true ;
			}
			
			function DC_DATASET_onSelect( dropdown, record, editor ){
				custDataPrivilegeInfo.setValue("controlType","");
				custDataPrivilegeList.clearData() ;
				return true ;
			}
			
			function DP_CONTROL_TYPE_onSelect(dropdown, record, editor){
				var controlType = record.getValue("value");
				if( "20" == controlType ){
					
					var privilegeId = window.dialogArguments[0] ;
					if( privilegeId == "" || privilegeId == null || privilegeId == "undefined" ){
						return ;
					}
					var custTypeId = custDataPrivilegeInfo.getValue("custTypeId");
					if( custTypeId == "" || custTypeId == null || custTypeId == "undefined" ){
						alert("请选择客户类型!");
						return false;
					}
					queryCustDataPrivilegeList();
				}else if( "10" == controlType ){
					custDataPrivilegeList.clearData();
				}
				return true ;
			}
			
			function queryCustDataPrivilegeList(){
				var privilegeId = window.dialogArguments[0] ;
				if( privilegeId == "" || privilegeId == null || privilegeId == "undefined" ){
					return ;
				}
				var custTypeId = custDataPrivilegeInfo.getValue("custTypeId");
				if( custTypeId == "" || custTypeId == null || custTypeId == "undefined" ){
					alert("请选择客户类型!");
					return ;
				}
				var datasetId = custDataPrivilegeInfo.getValue("datasetId");
				
				custDataPrivilegeList.parameters().setDataType("privilegeId","string");
				custDataPrivilegeList.parameters().setDataType("custTypeId","string");
				custDataPrivilegeList.parameters().setDataType("datasetId","string");
				
				custDataPrivilegeList.parameters().setValue("privilegeId",privilegeId); 
				custDataPrivilegeList.parameters().setValue("custTypeId",custTypeId); 
				custDataPrivilegeList.parameters().setValue("datasetId",datasetId); 
				
				Dataset.reloadData(custDataPrivilegeList);
				
				var record = custDataPrivilegeList.getFirstRecord() ;
				while( record ) {
					if( record.getValue("isChecked") == "1" ){
						record.setValue("select","true");
					}
					record = record.getNextRecord() ;
				}
			}
		</script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="custDataPrivilegeInfo" staticDataSource="true">
				<ui:field field = "custTypeId" label="客户类型" attrCode="CUST_TYPE" visible="true"></ui:field>
				<ui:field field = "pageId" label="页面" attrCode="DC_DATAPAGE" visible="true" subList="datasetId"></ui:field>
				<ui:field field = "datasetId" label="数据集" attrCode="DC_DATASET" visible="true"></ui:field>
				<ui:field field = "controlType" label="控制类型" attrCode="DP_CONTROL_TYPE" blankValue="true" visible="true"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="custDataPrivilegeList" staticDataSource="true" async="false"
			loadDataAction="PrivilegeService" loadDataActionMethod="getCustDataPrivilege">
				<ui:field field="select"></ui:field>
				<ui:field field = "isChecked" label="isChecked" visible="false"></ui:field>
				<ui:field field = "fieldId" label="fieldId" visible="false"></ui:field>
				<ui:field field = "fieldName" label="数据项" visible="true" subList="datasetId"></ui:field>
			</ui:dataset>
		</div>
		<ui:layout type="border">
			<ui:pane position="center">
				<ui:layout type="border">
					<ui:pane position="top" style = "height:80px">
						<ui:form dataset="custDataPrivilegeInfo" inputLayout="35%" labelLayout="14%"></ui:form>
					</ui:pane>
					<ui:pane position="center">
						<ui:table dataset="custDataPrivilegeList"></ui:table>
					</ui:pane>
				</ui:layout>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btnConfirm">确定</ui:button>
				<ui:button id="btnCancel">取消</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>