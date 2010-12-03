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
				<ui:field field="privId" label="权限ID" visible="false"></ui:field>
				<ui:field field="offId" label="商品ID" visible="false"></ui:field>
				<ui:field field="offName" label="商品名称" visible="true"></ui:field>
				<ui:field field="warrContent" label="担保内容" visible="true" attrCode="WARR_CONTENT" blankValue="true"></ui:field>
				<ui:field field="warrMode" label="担保方式" visible="true" attrCode="WARR_MODE" blankValue="true"></ui:field>
				<ui:field field="warrValue" label="担保值" visible="true"></ui:field>
				<ui:field field="restrictServices" label="限制服务提供列表" visible="false"></ui:field>
				<ui:field field="warrLen" label="担保时长" visible="true"></ui:field>
				<ui:field field="uniteAcctRequest" label="合帐要求" visible="true" attrCode="UNITE_ACCT_REQUEST" blankValue="true"></ui:field>
				<ui:field field="maxWarrNum" label="可担保最大数" visible="true"></ui:field>
				<ui:field field="maxUniteAcctNum" label="最大合帐数量" visible="true"></ui:field>
				<ui:field field="offWarrRequementId" label="商品担保要求标识" visible="false"></ui:field>
				<ui:parameter parameterId="privilegeId" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>

		<ui:layout type="border">
			<ui:pane position="center">
				<ui:panel type="titleList" desc="权限关联数据">
					<ui:content>
						<ui:table dataset="dataPrivilegeList"></ui:table>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="bottom">
				<div class="customerpilot" extra="customerpilot" id="dataPrivilegeListPilot" dataset="dataPrivilegeList"></div>
				<ui:button id="btn_addOfferWarrDataPrivilege">增加</ui:button>
				<ui:button id="btn_deleteDataPrivilege">删除</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
