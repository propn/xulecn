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
		
		function btn_addPricCataDataPrivilege_onClick(){
			var id = dataPrivilegeList.getValue("pricingParamCatalogId");
			if( id == "-1" ){
				alert("已经是全部参数目录,不能再增加!");
				return ;
			}
			window.showModalDialog(addDataPrivilegeDialogURL,[privId],"dialogHeight: 458pt; dialogWidth: 500pt;");
			queryData();
		}
		
		function btn_addAllPricCataDataPrivilege_onClick(){
			if( !confirm( "真的要把全部定价参数目录赋给该权限吗?")){
				return ;
			}
			var obj = new Object() ;
			obj.privId = privId;
			obj.dataPkey1 = "-1";
			obj.dataPkey2 = "-1";
			obj.dataPkey3 = "-1";
			var saveObjList = new Array() ;
			saveObjList[0] = obj ;
						
			var ajaxCall = new NDAjaxCall( true ) ;
			var callBack = function( reply ) {
				alert("增加成功!") ;
				queryData();
			}
			ajaxCall.remoteCall("PrivilegeService","addPricCataPrivileges",[saveObjList],callBack);
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
			var privilegeId = GetArgsFromHrefs(location.search,"privilegeId");
			dataPrivilegeList.parameters().setValue("privilegeId",privilegeId);
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
			loadDataActionMethod="getPriceCataContentByPrivilegeId" staticDataSource="true"
			 pageIndex="1" pageSize="8" autoLoadPage="true" loadAsNeeded="false" paginate="true">
			 	<ui:field field="select"></ui:field>
				<ui:field field="privId" label="" visible="false"></ui:field>
				<ui:field field="pricingParamCatalogId" label="参数目录标识" visible="false"></ui:field>
				<ui:field field="offerName" label="商品名称"></ui:field>
				<ui:field field="pricingPlanName" label="定价计划"></ui:field>
				<ui:field field="catalogType" label="目录类型" attrCode="CATALOG_TYPE"></ui:field>
				<ui:field field="pricingParamCatalogId" label="目录名称标识" visible="true"></ui:field>
				<ui:field field="pricingParamCatalogName" label="目录名称"></ui:field>			
				<ui:field field="productName" label="包含对象"></ui:field>
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
				<ui:button id="btn_addPricCataDataPrivilege">增加</ui:button>
				<ui:button id="btn_addAllPricCataDataPrivilege">增加全部参数目录</ui:button>				
				<ui:button id="btn_deleteDataPrivilege">删除</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
