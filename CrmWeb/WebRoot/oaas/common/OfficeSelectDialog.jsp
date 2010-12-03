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
			function doQuery_onClick(){
				var queryCond = document.getElementById("queryInput").value ;
				officeList.parameters().setValue("officeDesc", queryCond ) ;
				officeList.reloadData();
			}
			function btn_Confirm_onClick(){
				var arr = {} ;
				arr[0] = officeList.getValue("officeId");
				arr[1] = officeList.getValue("officeDesc");
				window.returnValue = arr ;
				window.close();
			}
			function btn_Cancel_onClick(){
				window.returnValue = null ;
				window.close();
			}
		</script>		
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="officeList" pageIndex="1" pageSize="14" loadDataAction="PartyService" loadDataActionMethod="queryOfficeList" autoLoadPage="true" staticDataSource="false" loadAsNeeded="false" paginate="true">
				<ui:field field="officeId" label="�칫�ص��ʶ" visible="false"></ui:field>
				<ui:field field="officeDesc" label="�칫�ص�����"></ui:field>
				<ui:parameter parameterId="officeDesc" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>

		<ui:layout type="border">
			<ui:pane position="center">
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:panel type="titleList" desc="�칫�ص�ѡ��">
							<ui:content>
								<div style="text-align:right">
									�칫�ص�����:
									<input type="text" id="queryInput" style="border: 1px solid #666666;"></input>
									<ui:button id="doQuery">��ѯ</ui:button>
								</div>
								<ui:table dataset="officeList" maxRow="14"></ui:table>
							</ui:content>
						</ui:panel>
					</ui:pane>
					<ui:pane position="bottom">
						<div class="customerpilot" extra="customerpilot" id="officeListPilot" dataset="officeList"></div>
					</ui:pane>
				</ui:layout>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btn_Confirm">ȷ��</ui:button>
				<ui:button id="btn_Cancel">ȡ��</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>