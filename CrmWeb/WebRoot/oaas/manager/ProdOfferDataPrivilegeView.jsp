<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		
	<script>
		var privType = "";
		var privId = "";
		var addDataPrivilegeDialogURL = "";
		
		function page_onLoad(){
			privType = GetArgsFromHrefs(location.search,"privilegeType");
			privId = GetArgsFromHrefs(location.search,"privilegeId");
			addDataPrivilegeDialogURL = GetArgsFromHrefs(location.search,"addDataPrivilegeDialogURL");
			
			queryData();
		}
		function btn_addDataPrivilege_onClick(){
			window.showModalDialog(addDataPrivilegeDialogURL,[privId],"dialogHeight: 468pt; dialogWidth: 500pt;");
			queryData();
		}
		
		function queryData(){
			dataPrivilegeList.clearData();
			var privilegeId = GetArgsFromHrefs(location.search,"privilegeId");
			
			dataPrivilegeList.parameters().setDataType("privilegeId","string");
			dataPrivilegeList.parameters().setValue("privilegeId",privilegeId);
			Dataset.reloadData( dataPrivilegeList ) ;
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
					objList[objList.length] = obj ;
				}
				record = record.getNextRecord();
			}
			if( objList.length == 0 ){
				alert("��ѡ����Ҫɾ���ļ�¼��");
				return ;
			}
			if( !confirm("��ȷ��Ҫɾ����ǰ��¼��?")){
				return ;
			}
			
			var ajaxCall = new NDAjaxCall( true ) ;
			var callBack = function( reply ) {
				alert("ɾ���ɹ�!");
				queryData();
			}
			ajaxCall.remoteCall("PrivilegeService","deleteDataPrivilege",[objList],callBack);
		}
	</script>
	
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="dataPrivilegeList" readOnly="true" loadDataAction="PrivilegeService" 
			loadDataActionMethod="getProdOfferDataPrivilegeByPrivilegeId" staticDataSource="true"
			pageIndex="1" pageSize="8" autoLoadPage="true" loadAsNeeded="false" paginate="true">
				<ui:field field="select"></ui:field>
				<ui:field field="privId" label="privId" visible="false"></ui:field>
				<ui:field field="dataPkey1" label="��Ʒ��ʶ" visible="true"></ui:field>
				<ui:field field="offerName" label="��Ʒ����" visible="true"></ui:field>
				<ui:field field="dataPkey2" label="�����ṩ��ʶ" visible="true"></ui:field>
				<ui:field field="dataPkey3" label="dataPkey3" visible="false"></ui:field>
				<ui:field field="serviceOfferName" label="�����ṩ����" visible="true"></ui:field>				
				<ui:parameter parameterId="privilegeId" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>

		<ui:layout type="border">
			<ui:pane position="center">
				<ui:panel type="titleList" desc="Ȩ�޹�������">
					<ui:content>
						<ui:table dataset="dataPrivilegeList"></ui:table>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="bottom">
			<div class="customerpilot" extra="customerpilot" id="dataPrivilegeListPilot" dataset="dataPrivilegeList"></div>
				<ui:button id="btn_addDataPrivilege">����</ui:button>
				<ui:button id="btn_deleteDataPrivilege">ɾ��</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
