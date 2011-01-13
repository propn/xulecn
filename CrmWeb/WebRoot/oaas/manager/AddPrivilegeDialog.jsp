<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,validator">
		<title>增加权限</title>
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
	//将Dataset里面的数据赋值给菜单对象
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
		alert("增加权限成功!");
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

//权限对象
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
				<ui:field field="privId" label="权限编码" visible="false"></ui:field>
				<ui:field field="parentPrivilegeName" label="选择父权限" popup="true" visible="false"></ui:field>
				<ui:field field="parentPrgId" label="父权限ID" visible="false"></ui:field>
				<ui:field field="privName" label="权限名称" required="true" validType="require" validMsg="请输入权限名称!"></ui:field>
				<ui:field field="privCode" label="权限编码" required="true" validType="require" validMsg="请输入权限编码!"></ui:field>
				<ui:field field="privType" label="使用类型" attrCode="PRIVILEGE_TYPE" required="true" validType="require" validMsg="请选择使用类型!"></ui:field>
				<ui:field field="privDesc" label="权限描述"></ui:field>
			</ui:dataset>
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:form dataset="privilegeInfo" id="privilegeInfoFomr" submit="btn_commitPrivilege" labelLayout="23%" inputLayout="75%"></ui:form>
				</ui:pane>
				<ui:pane position="bottom">
					<ui:button id="btn_commitPrivilege">确定</ui:button>
					<ui:button id="btn_cancel">取消</ui:button>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
