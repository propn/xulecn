<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<title>����ι���</title>
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
			alert("ʧЧ���ڱ����ڽ�������֮��!");
			return ;
		}
		if( exchangeInfo.getValue("expDate") <= exchangeInfo.getValue("effDate")){
			alert("ʧЧ���ڱ��������Ч����!");
			return ;
		}
		if( exchangeInfo.getValue("accNbrBegin").length != 7 || exchangeInfo.getValue("accNbrEnd").length != 7 ){
			alert("��ʼ����ͽ�������ĳ��ȱ���Ϊ7!");
			return ;
		}
		if( exchangeInfo.getValue("accNbrBegin") > exchangeInfo.getValue("accNbrEnd")){
			alert("��ʼ�������С�ڽ�������!");
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
					alert("���ӺŶ�ʧ��,����β����ظ������ܽ���!");
					window.returnValue = null ;
				}else{
					alert("���Ӻ���β����ɹ���");
					exchange["exchId"] = result["resultObject"];
					window.returnValue = exchange ;
				}
			}else if( actionType == "update" ){
				if( result == false ){
					alert("�޸ĺŶ�ʧ��,����β����ظ������ܽ���!");
					window.returnValue = null ;
				}else{
					alert("�޸ĺ���γɹ�!");
					window.returnValue = exchange ;
				}
			}
			window.close() ;
		}
		
		var arr = new Array();
		arr[0] = exchange ;
		if( actionType == "insert" ){
			ajaxCall.remoteCall("RegionService","insertAccNbr",arr,callBack);//���÷�������������һ�������
		}else if ( actionType == "update" ){
			ajaxCall.remoteCall("RegionService","updateAccNbr",arr,callBack);//���÷�������������һ�������
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
			<!-- �������ݼ�(������))-->
			<ui:dataset datasetId="exchangeInfo">
				<ui:field field="exchId" label="�����ֱ�ʶ" visible="false"></ui:field>
				<ui:field field="regionId" label="�����ʶ" visible="false"></ui:field>
				<ui:field field="exchCode" label="�����ִ���" required="true" validType="require" validMsg="�����뽻���ִ���!"></ui:field>
				<ui:field field="exchName" label="����������" required="true" validType="require" validMsg="�����뽻��������!"></ui:field>
				<ui:field field="effDate" type="date" label="��Чʱ��"  required="true" validType="require" validMsg="��������Чʱ��!"></ui:field>
				<ui:field field="expDate" type="date" label="ʧЧʱ��"></ui:field>
				<ui:field field="accNbrBegin" label="��ʼ����" required="true" validType="number" validMsg="������8λ���ȵ���ʼ����!"></ui:field>
				<ui:field field="accNbrEnd" label="��ֹ����"  required="true" validType="number" validMsg="������8λ���ȵ���ֹ����!"></ui:field>
				<ui:field field="state" label="״̬" dropDown="stateSelect"  required="true" validType="require" validMsg="������״̬!"></ui:field>
				<ui:field field="areaId" label="Ӫҵ����ʶ" visible="false"></ui:field>
				<ui:field field="areaName" label="Ӫҵ��" ></ui:field>
				<ui:field field="prodFamilyId" label="��Ʒ�����ʶ" visible="false"></ui:field>
				<ui:field field="prodFamilyName" label="��Ʒ����"  required="true" validType="require" validMsg="�������Ʒ����!"  keyField="prodFamilyId" dropDown="productFamilyDropdown"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="staffProductFamilySelect" loadDataAction="RegionService" loadDataActionMethod="getProductFamily"  staticDataSource="false">
				<ui:field field="prodFamilyId" label="��Ʒ�����ʶ" visible="false"></ui:field>
				<ui:field field="prodFamilyName" label="��Ʒ����" dropDown="productFamilyDropdown"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<code type="dataset" id="productFamilyDropdown" dataset="staffProductFamilySelect" readFields="prodFamilyId" writeFields="prodFamilyId,prodFamilyName" visibleFields="prodFamilyName" showColumnHeader="false" staticDataSource="false"
				cachable="false">
			</code>
			
			<ui:dropdown id="stateSelect" attrCode="COMM_RECORD_STATE" staticDataSource="false"></ui:dropdown>

		</div>
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="����ι���">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:form dataset="exchangeInfo" submit="btn_return"  id="exchangeInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="btn_return">ȷ��</ui:button>
							<ui:button id="btn_cancel">ȡ��</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</div>
	</body>
</html>
