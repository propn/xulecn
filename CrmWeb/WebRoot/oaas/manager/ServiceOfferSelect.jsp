<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,validator">
		<title>��Ʒ�����ṩ</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>

		<script>
			function btnQuery_onClick(){
				serviceOfferList.parameters().setValue("serviceOfferName", queryInfo.getValue("serviceOfferName"));
				serviceOfferList.reloadData();
			}
			
			function btnConfirm_onClick(){
				var serviceOfferId = serviceOfferList.getValue("serviceOfferId");
				if( serviceOfferId == "" ){
					alert("û��ѡ�еķ����ṩ!");
					return ;
				}else {
					var obj = new Object();
					obj.serviceOfferId = serviceOfferId ;
					obj.serviceOfferName = serviceOfferList.getValue("serviceOfferName");
					window.returnValue = obj ;
					window.close();
				}
			}
			
			function btnCancel_onClick(){
				window.returnValue = null ;
				window.close() ;
			}
		</script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="queryInfo" staticDataSource="true">
				<ui:field field="serviceOfferName" label="�����ṩ����"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="serviceOfferList" loadDataAction="PrivilegeService"
			pageIndex="1" pageSize="20" autoLoadPage="true" loadAsNeeded="false" paginate="true"
			loadDataActionMethod="queryServiceOffer" staticDataSource="true" async="false">
				<ui:field field="serviceOfferId" label="�����ṩ��ʶ" visible="true"></ui:field>
				<ui:field field="serviceOfferName" label="�����ṩ����" popup="true"></ui:field>
				<ui:parameter parameterId="serviceOfferName" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>

		<ui:layout type="border">
			<ui:pane position="top" style="height:30px">
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:form dataset="queryInfo" inputLayout="49%" labelLayout="30%"></ui:form>
					</ui:pane>
					<ui:pane position="right">
						<ui:button id="btnQuery">��ѯ</ui:button>
					</ui:pane>
				</ui:layout>
			</ui:pane>
			<ui:pane position="center">
				<ui:table dataset="serviceOfferList"></ui:table>
			</ui:pane>
			<ui:pane position="bottom" style="height:30px">
				<div class="customerpilot" extra="customerpilot" id="serviceOfferListPilot" dataset="serviceOfferList"></div>
				<ui:button id="btnConfirm">ȷ��</ui:button>
				<ui:button id="btnCancel">ȡ��</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
