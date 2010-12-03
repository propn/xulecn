<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>

		<script>
		
		var privType = "";
		var privId = "";
		var addDataPrivilegeDialogURL = "";
			
		function btn_addDataPrivilege_onClick(){
			var returnValue = window.showModalDialog("PrivilegeDataDialog.jsp",[privId,privType],"dialogHeight: 408pt; dialogWidth: 344pt;");
			if( returnValue == 0 )
				queryData() ;
		}
		function btn_deleteDataPrivilege_onClick(){
			var record = dataPrivilegeList.getFirstRecord() ;
			var objList = new Array() ;
			while( record ) {
				if( record.getValue("select") ){
					var obj = new Object() ;
					obj.privId = privId ;
					obj.dataPkey1 = record.getValue("dataPkey1");
					obj.dataPkey2 = record.getValue("dataPkey2");
					obj.dataPkey3 = record.getValue("dataPkey3");
					objList[objList.length] = obj;
				}
				record = record.getNextRecord();
			}
			if( objList.length == 0 ){
				alert("请选择您要删除的记录！");
				return ;
			}
			if( !confirm("您确定要删除当前记录吗?")){
				return ;
			}
			
			var ajaxCall = new NDAjaxCall( true ) ;
			var callBack = function( reply ) {
				alert("删除成功!");
				queryData();
			}
			ajaxCall.remoteCall("PrivilegeService","deleteDataPrivilege",[objList],callBack);
		}

		function queryData(){

			dataPrivilegeList.clearData();
			
			dataPrivilegeList.parameters().setDataType("privilegeId","string");
			dataPrivilegeList.parameters().setValue("privilegeId",privId);
			dataPrivilegeList.parameters().setDataType("privilegeType","string");
			dataPrivilegeList.parameters().setValue("privilegeType",privType);
	
			Dataset.reloadData( dataPrivilegeList ) ;		
		}
		
		function page_onLoad(){
			privType = GetArgsFromHrefs(location.search,"privilegeType");
			privId = GetArgsFromHrefs(location.search,"privilegeId");
			addDataPrivilegeDialogURL = GetArgsFromHrefs(location.search,"addDataPrivilegeDialogURL");
			queryData();
		}
		</script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="dataPrivilegeList" readOnly="true" loadDataAction="PrivilegeService" loadDataActionMethod="getDataPrivilegeByPrivilegeId" staticDataSource="true">
				<ui:field field="select"></ui:field>
				<ui:field field="privId" label="" visible="false"></ui:field>
				<ui:field field="dataPkey1" label="" visible="false"></ui:field>
				<ui:field field="dataPkey2" label="" visible="false"></ui:field>
				<ui:field field="dataPkey3" label="" visible="false"></ui:field>
				<ui:field field="name" label="名称" visible="true"></ui:field>
			</ui:dataset>
		</div>

		<ui:layout type="border">
			<ui:pane position="center">
				<ui:table dataset="dataPrivilegeList"></ui:table>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btn_addDataPrivilege">增加</ui:button>
				<ui:button id="btn_deleteDataPrivilege">删除</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
