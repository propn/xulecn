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
		
		function btn_addOfferWarrDataPrivilege_onClick(){
			window.showModalDialog(addDataPrivilegeDialogURL,[privId],"dialogHeight: 458pt; dialogWidth: 700pt;");
			queryData();
		}
		
		function btn_deleteDataPrivilege_onClick(){
			var record = dataPrivilegeList.getFirstRecord() ;
			var objList = new Array() ;
			while( record ) {
				if( record.getValue("select") ){
					var obj = new Object() ;
					obj.privId = privId ;
					obj.dataPkey1 = record.getValue("pricingParamCatalogId");
					obj.dataPkey2 = "-1"
					obj.dataPkey3 = "-1";
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

		function queryData(){
			dataPrivilegeList.clearData();
			dataPrivilegeList.parameters().setValue("privilegeId",privId);
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
			<ui:dataset datasetId="dataPrivilegeList" readOnly="true" loadDataAction="PrivilegeService" 
			loadDataActionMethod="getOfferWarrByPrivilegeId" staticDataSource="true"
			 pageIndex="1" pageSize="8" autoLoadPage="true" loadAsNeeded="false" paginate="true">
			 	<ui:field field="select"></ui:field>
				<ui:field field="privId" label="Ȩ��ID" visible="false"></ui:field>
				<ui:field field="offId" label="��ƷID" visible="false"></ui:field>
				<ui:field field="offName" label="��Ʒ����" visible="true"></ui:field>
				<ui:field field="warrContent" label="��������" visible="true" attrCode="WARR_CONTENT" blankValue="true"></ui:field>
				<ui:field field="warrMode" label="������ʽ" visible="true" attrCode="WARR_MODE" blankValue="true"></ui:field>
				<ui:field field="warrValue" label="����ֵ" visible="true"></ui:field>
				<ui:field field="restrictServices" label="���Ʒ����ṩ�б�" visible="false"></ui:field>
				<ui:field field="warrLen" label="����ʱ��" visible="true"></ui:field>
				<ui:field field="uniteAcctRequest" label="����Ҫ��" visible="true" attrCode="UNITE_ACCT_REQUEST" blankValue="true"></ui:field>
				<ui:field field="maxWarrNum" label="�ɵ��������" visible="true"></ui:field>
				<ui:field field="maxUniteAcctNum" label="����������" visible="true"></ui:field>
				<ui:field field="offWarrRequementId" label="��Ʒ����Ҫ���ʶ" visible="false"></ui:field>
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
				<ui:button id="btn_addOfferWarrDataPrivilege">����</ui:button>
				<ui:button id="btn_deleteDataPrivilege">ɾ��</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
