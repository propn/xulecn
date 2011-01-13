<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,validator">
		<title>����Ȩ��</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript">
		
function button_privilegeInfo_parentPrivilegeName_onClick(){
	var returnValue=window.showModalDialog("PrivilegeDialog.jsp",null,"dialogHeight: 408pt; dialogWidth: 444pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue != null ){
		privilegeInfo.setValue("parentPrgId",returnValue["privilegeId"]) ;
		privilegeInfo.setValue("parentPrivilegeName",returnValue["privilegeName"]) ;
	}
}

function page_onLoad(){
	privilegeInfo.clearData();
	privilegeInfo.insertRecord( null ) ;
//	privilegeInfo.setFieldReadOnly("parentPrivilegeName",true) ;
}

function btn_commitPrivilege_onClick(){
	if( !$("privilegeInfoFomr").validate()){
		return ;
	}
	var privilege = new Privilege();
	//��Dataset��������ݸ�ֵ���˵�����
	for(var ele in privilege){ 
		if(privilegeInfo.getField(ele)){
			privilege[ele] = privilegeInfo.getValue( ele );
		} 
	}
	var parentId = window.dialogArguments;
	privilege["parentPrgId"] = parentId ;

	var ajaxCall = new NDAjaxCall(true);
	var result = null;
	var callBack = function( reply ){
		result = reply.getResult() ;
		alert("����Ȩ�޳ɹ�!");
		window.returnValue = 0 ;
		window.close();
	}
	var arr = new Array();
	arr[0] = privilege;
	ajaxCall.remoteCall("PrivilegeService","insertPrivilege",arr,callBack);
}

function btn_cancel_onClick(){
	window.returnValue = 1;
	window.close();
}

//Ȩ�޶���
function Privilege(){
	this.privId = "";
	this.privName = "";
	this.privCode = "";
	this.parentPrgId = "";
	this.privDesc = "";
	this.privType = "";
}
</script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="privilegeInfo">
				<ui:field field="privId" label="Ȩ�ޱ���" visible="false"></ui:field>
				<ui:field field="parentPrivilegeName" label="ѡ��Ȩ��" popup="true" visible="false"></ui:field>
				<ui:field field="parentPrgId" label="��Ȩ��ID" visible="false"></ui:field>
				<ui:field field="privName" label="Ȩ������" required="true" validType="require" validMsg="������Ȩ������!"></ui:field>
				<ui:field field="privCode" label="Ȩ�ޱ���" required="true" validType="require" validMsg="������Ȩ�ޱ���!"></ui:field>
				<ui:field field="privType" label="ʹ������" attrCode="PRIVILEGE_TYPE" required="true" validType="require" validMsg="��ѡ��ʹ������!"></ui:field>
				<ui:field field="privDesc" label="Ȩ������"></ui:field>
			</ui:dataset>
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:form dataset="privilegeInfo" id="privilegeInfoFomr" submit="btn_commitPrivilege" labelLayout="23%" inputLayout="75%"></ui:form>
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_commitPrivilege">ȷ��</ui:button>
					<ui:button id="btn_cancel">ȡ��</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
