<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<title>河南通信综合营销系统--修改密码</title>
		<META HTTP-EQUIV="library" CONTENT="kernel">
		<script type="text/javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="JavaScript">
		var ajaxCall = new NDAjaxCall();

	function confirm_onClick(){
			var arr = new Array();
			var organizationPosition = new OrganizationPosition();
			var callBack = function( reply ){
				var result = reply.getResult() ;
				alert(result);
			}
			arr[0] = organizationPosition;
			ajaxCall.remoteCall("OAASTestService","addOrganizationPosition",arr,callBack);
			returnValue = organizationPosition;
	}
	
	function concel_onClick(){
		returnValue = null ;
		window.close();
	}
	
	function OrganizationPosition(){
	
	}
</script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="orgPositionSelect" loadDataAction="OAASTestService" loadDataActionMethod="getOrganizationPositions" async="false" staticDataSource="false">
				<ui:field field="positionId" label="岗位标识" visible="false"></ui:field>
				<ui:field field="positionName" label="岗位" dropDown="orgPositionDropdown"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="orgPositionInfo">
				<ui:field field="positionId" label="岗位标识" visible="false"></ui:field>
				<ui:field field="positionName" label="岗位" dropDown="orgPositionDropdown"></ui:field>
				<ui:field field="state" label="状态"></ui:field>
				<ui:field field="stateDate" label="状态时间"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<code type="dataset" id="orgPositionDropdown" dataset="orgPositionSelect" readFields="positionId" writeFields="positionId,positionName" visibleFields="positionName" showColumnHeader="false" staticDataSource="false" cachable="true"></code>
		</div>
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="岗位组织">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:form submit="confirm" dataset="orgPositionInfo" labelLayout="25%" inputLayout="70%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="confirm">确定</ui:button>
							<ui:button id="concel">取消</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</div>
	</body>
</html>
