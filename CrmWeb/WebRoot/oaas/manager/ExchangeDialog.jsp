<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<title>号码段管理</title>
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,validator">
		<script type="text/javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/BillRegion.js"></script>
		<script>
	function btn_cancel_onClick(){
		window.close();
	}
	
	function btn_return_onClick(){
		if( !$("exchangeInfoForm").validate()){
			return ;
		}
		var currentDate = getTodayStr();
		if( exchangeInfo.getValue("expDate") < currentDate ){
			alert("失效日期必须在今天或今天之后!");
			return ;
		}
		if( exchangeInfo.getValue("expDate") <= exchangeInfo.getValue("effDate")){
			alert("失效日期必须迟于生效日期!");
			return ;
		}
		if( exchangeInfo.getValue("accNbrBegin").length != 7 || exchangeInfo.getValue("accNbrEnd").length != 7 ){
			alert("起始号码和结束号码的长度必须为7!");
			return ;
		}
		if( exchangeInfo.getValue("accNbrBegin") > exchangeInfo.getValue("accNbrEnd")){
			alert("起始号码必须小于结束号码!");
			return ;
		}
		var actionType = window.dialogArguments[1] ;
		
		var exchange = new Exchange() ;
		var actionType = window.dialogArguments[1] ;

		for( var ele in exchange ){
			if( exchangeInfo.getField(ele)){
				exchange[ele] = exchangeInfo.getValue( ele ) ;
			}
		}
		
		var ajaxCall = new NDAjaxCall(true);
		
		var callBack = function( reply ){
			result = reply.getResult() ;
			
			if( actionType == "insert" ){
				if( result == "-1" ){
					alert("增加号段失败,号码段不能重复，不能交叉!");
					window.returnValue = null ;
				}else{
					alert("增加号码段操作成功！");
					exchange["exchId"] = result["resultObject"];
					window.returnValue = exchange ;
				}
			}else if( actionType == "update" ){
				if( result == false ){
					alert("修改号段失败,号码段不能重复，不能交叉!");
					window.returnValue = null ;
				}else{
					alert("修改号码段成功!");
					window.returnValue = exchange ;
				}
			}
			window.close() ;
		}
		
		var arr = new Array();
		arr[0] = exchange ;
		if( actionType == "insert" ){
			ajaxCall.remoteCall("RegionService","insertAccNbr",arr,callBack);//调用服务器服务，增加一个号码段
		}else if ( actionType == "update" ){
			ajaxCall.remoteCall("RegionService","updateAccNbr",arr,callBack);//调用服务器服务，增加一个号码段
		}
	}
	
	function init(){
		var actionType = window.dialogArguments[1] ;
		
		exchangeInfo.clearData();
		exchangeInfo.insertRecord(null);
		exchangeInfo.setValue("areaId" , window.dialogArguments[2] );
		exchangeInfo.setFieldReadOnly("areaId",true);
		exchangeInfo.setValue("areaName",window.dialogArguments[3]);
		exchangeInfo.setFieldReadOnly("areaName",true);
		exchangeInfo.setFieldReadOnly("exchId",true);
		exchangeInfo.setFieldReadOnly("exchName",true);			
		exchangeInfo.setFieldReadOnly("exchCode",true);			
		exchangeInfo.setFieldReadOnly("exchCode",true);		
		exchangeInfo.setFieldReadOnly("regionId",true);
			
		if( actionType == "insert" ){
			var regionInfo = window.dialogArguments[0];
			exchangeInfo.setValue( "exchId" , regionInfo.getValue("regionId") );	 
			exchangeInfo.setValue("exchName", regionInfo.getValue("regionName") );	 
			exchangeInfo.setValue("exchCode",regionInfo.getValue("regionCode")); 
			exchangeInfo.setValue("regionId",regionInfo.getValue("regionId"));
			exchangeInfo.setValue("effDate",formatDate(new Date(),"yyyy-MM-dd"));
			exchangeInfo.setValue("expDate","2030-01-01");
		}else if( actionType == "update") {
			exchangeObj = window.dialogArguments[0] ;
			exchangeInfo.setValue("exchId", exchangeObj.exchangeId);
			exchangeInfo.setValue("regionId",exchangeObj.regionId);
			exchangeInfo.setValue("exchCode",exchangeObj.exchangeCode);
			exchangeInfo.setValue("exchName",exchangeObj.exchangeName);
			var start = exchangeObj.accNbrBegin.substring(4,12);
			var end = exchangeObj.accNbrEnd.substring(4,12 ) ;
			exchangeInfo.setValue("accNbrBegin",start);
			exchangeInfo.setValue("accNbrEnd",end);
			exchangeInfo.setValue("state",exchangeObj.state);
			exchangeInfo.setValue("effDate",exchangeObj.effDate);
			exchangeInfo.setValue("expDate",exchangeObj.expDate);
			exchangeInfo.setValue("areaId",exchangeObj.areaId);
			exchangeInfo.setValue("prodFamilyId",exchangeObj.prodFamilyId);
			exchangeInfo.setValue("prodFamilyName",exchangeObj.prodFamilyName);
		}
	}
	
	function page_onLoad(){
		setTimeout( "init()", 500 ) ;		
	}
	
	

</script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- 局向数据集(交换局))-->
			<ui:dataset datasetId="exchangeInfo">
				<ui:field field="exchId" label="交换局标识" visible="false"></ui:field>
				<ui:field field="regionId" label="区域标识" visible="false"></ui:field>
				<ui:field field="exchCode" label="交换局代码" required="true" validType="require" validMsg="请输入交换局代码!"></ui:field>
				<ui:field field="exchName" label="交换局名称" required="true" validType="require" validMsg="请输入交换局名称!"></ui:field>
				<ui:field field="effDate" type="date" label="生效时间"  required="true" validType="require" validMsg="请输入生效时间!"></ui:field>
				<ui:field field="expDate" type="date" label="失效时间"></ui:field>
				<ui:field field="accNbrBegin" label="起始号码" required="true" validType="number" validMsg="请输入8位长度的起始号码!"></ui:field>
				<ui:field field="accNbrEnd" label="终止号码"  required="true" validType="number" validMsg="请输入8位长度的终止号码!"></ui:field>
				<ui:field field="state" label="状态" dropDown="stateSelect"  required="true" validType="require" validMsg="请输入状态!"></ui:field>
				<ui:field field="areaId" label="营业区标识" visible="false"></ui:field>
				<ui:field field="areaName" label="营业区" ></ui:field>
				<ui:field field="prodFamilyId" label="产品家族标识" visible="false"></ui:field>
				<ui:field field="prodFamilyName" label="产品家族"  required="true" validType="require" validMsg="请输入产品家族!"  keyField="prodFamilyId" dropDown="productFamilyDropdown"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="staffProductFamilySelect" loadDataAction="RegionService" loadDataActionMethod="getProductFamily"  staticDataSource="false">
				<ui:field field="prodFamilyId" label="产品家族标识" visible="false"></ui:field>
				<ui:field field="prodFamilyName" label="产品家族" dropDown="productFamilyDropdown"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<code type="dataset" id="productFamilyDropdown" dataset="staffProductFamilySelect" readFields="prodFamilyId" writeFields="prodFamilyId,prodFamilyName" visibleFields="prodFamilyName" showColumnHeader="false" staticDataSource="false"
				cachable="false">
			</code>
			
			<ui:dropdown id="stateSelect" attrCode="COMM_RECORD_STATE" staticDataSource="false"></ui:dropdown>

		</div>
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="号码段管理">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:form dataset="exchangeInfo" submit="btn_return"  id="exchangeInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="btn_return">确定</ui:button>
							<ui:button id="btn_cancel">取消</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</div>
	</body>
</html>
