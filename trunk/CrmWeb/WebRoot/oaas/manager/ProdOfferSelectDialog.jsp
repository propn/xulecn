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
			function button_serviceInfo_serviceOfferName_onClick(){
				var result = openDialog("ServiceOfferSelect.jsp","0", "", "500px", "600px");
				var privId = window.dialogArguments[0];
				if( result != null ){
					serviceInfo.setValue("serviceOfferId", result.serviceOfferId);
					serviceInfo.setValue("serviceOfferName", result.serviceOfferName);
					
					prodOffList.parameters().setValue("serviceOfferId", result.serviceOfferId);
					prodOffList.parameters().setValue("privilegeId", privId);
					Dataset.reloadData(prodOffList);
					
					selectedProdOffList.parameters().setValue("serviceOfferId", result.serviceOfferId);
					selectedProdOffList.parameters().setValue("privilegeId", privId);
					Dataset.reloadData(selectedProdOffList);
				}
			}
		
			function button_offInfo_offName_onClick(){
				var result = openDialog("../../ss/pm/getProdOfferName.jsp","0", "", "700px", "600px");
				if( result != null ){
					var privId = window.dialogArguments[0];
					if( result == -1 ){
						offInfo.setValue("offId","-1");
						offInfo.setValue("offName","ȫ����Ʒ");
						
						offServList.parameters().setDataType("isAllType","string");
						offServList.parameters().setValue("isAllType","1");
						offServList.parameters().setDataType("privilegeId","string");
						offServList.parameters().setValue("privilegeId",privId);
						offServList.parameters().setDataType("offerId","string");
						offServList.parameters().setValue("offerId","-1");																		
						
						Dataset.reloadData( offServList ) ;
					}else{
						offInfo.setValue("offId",result.elementId);
						offInfo.setValue("offName",result.elementName);

						offServList.parameters().setDataType("isAllType","string");
						offServList.parameters().setValue("isAllType","2");
						offServList.parameters().setDataType("privilegeId","string");
						offServList.parameters().setValue("privilegeId",privId);
						offServList.parameters().setDataType("offerId","string");
						offServList.parameters().setValue("offerId",result.elementId);
						
						Dataset.reloadData( offServList ) ;
					}
					var record = offServList.getFirstRecord() ;
					while( record ) {
						if( record.getValue("isChecked") == "1" ){
							record.setValue("select","true");
						}
						record = record.getNextRecord() ;
					}
				}
			}
			
			function DataPrivilegeVO(){
				this.privId = "";
				this.dataPkey1 = "";
				this.dataPkey2 = "";
				this.dataPkey3 = "";
			}
			
			function btnConfirm_onClick(){
				var saveObjList = new Array() ;
				var activeIndex = addTabPane.getSelectedPageIndex()  ;
				if( activeIndex == 0 ){
					var record = offServList.getFirstRecord() ;//�����ṩ�б�
					var i = 0 ;
					while( record ){
						if( System.isTrue(record.getValue("select")) ){
							var obj = new DataPrivilegeVO() ;
							obj.privId = window.dialogArguments[0];
							obj.dataPkey1 = offInfo.getValue("offId");//��ƷID
							obj.dataPkey2 = record.getValue("servOffId");//�����ṩID
							obj.dataPkey3 = "-1";
							saveObjList[i] = obj ;
							i ++ ;
						}
						record = record.getNextRecord() ;
					}
					if( i == 0 ){
						alert("��ѡ����Ʒ�����ṩ!");
						return ;
					}
				}else{
					var record = selectedProdOffList.getFirstRecord() ;//��Ʒ�б�
					var i = 0 ;
					while( record ){
							var obj = new DataPrivilegeVO() ;
							obj.privId = window.dialogArguments[0];
							obj.dataPkey1 = record.getValue("servOffId");//��ƷID
							obj.dataPkey2 = serviceInfo.getValue("serviceOfferId");//�����ṩID
							obj.dataPkey3 = "-1";
							saveObjList[i] = obj ;
							i ++ ;
							record = record.getNextRecord() ;
					}
					if( i == 0 ){
						alert("��ѡ����Ʒ!");
						return ;
					}
				}
				
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ) {
					alert("���ӳɹ�!") ;
					window.close();
				}
				if( activeIndex == 0 ){
					ajaxCall.remoteCall("PrivilegeService","addProdOfferDataPrivileges",[saveObjList, "1"],callBack);
				}else if( activeIndex == 1 ){
					ajaxCall.remoteCall("PrivilegeService","addProdOfferDataPrivileges",[saveObjList, "2"],callBack);
				}
			}
			function btnCancel_onClick(){
				window.returnValue = null ;
				window.close() ;
			}
			
			
			
			
			//�Ӵ�ѡ�б����ƶ�һ����¼��ѡ���б���
			function addOne_onClick(){
				if( !prodOffList.getCurrent() ){//�����ѡ�б�û�м�¼,����
					return ;
				}
				if( ifRecordSelected( prodOffList.getValue("servOffId"))){
					alert("��ǰ��¼�Ѿ���ѡ���б�����!");
					return ;
				}
				selectedProdOffList.insertRecord() ;
				for( var i = 0 ; i < prodOffList.fields.length ; i ++ ){
					selectedProdOffList.setValue(i, prodOffList.getValue(i));
				}
				prodOffList.moveNext();
			}
			//�Ӵ�ѡ�б����ƶ����м�¼��ѡ���б���
			function addAll_onClick(){
				var record = prodOffList.getFirstRecord() ;
				while( record ) {
					if( ifRecordSelected( record.getValue("servOffId"))){
						record = record.getNextRecord() ;
						continue ;
					}
					selectedProdOffList.insertRecord() ;
					for( var i = 0 ; i < record.fields.length ; i ++ ){
						selectedProdOffList.setValue(i, record.getValue(i));
					}
					record = record.getNextRecord() ;
				}
			}
			
			//����ѡ�б���ɾ����ǰ��¼
			function removeOne_onClick(){
				if( !selectedProdOffList.getCurrent()){
					return ;
				}
				selectedProdOffList.deleteRecord() ;
			}
			
			//�����ѡ�б�
			function removeAll_onClick(){
				selectedProdOffList.clearData() ;
			}
			
			//�ж�һ����¼�Ƿ��Ѿ���ѡ��
			function ifRecordSelected( id ) {
				var record = selectedProdOffList.getFirstRecord() ;
				while( record ) {
					if( record.getValue("servOffId" ) == id || record.getValue("servOffId" ) == -1){
						return true ;
					}
					record = record.getNextRecord() ;
				}
				return false ;
			}
		</script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- ������Ʒ��ѯ�����ķ����ṩ�б�-->
			<ui:dataset datasetId="offServList" loadDataAction="PrivilegeService" 
			loadDataActionMethod="getOfferServ" staticDataSource="true" async="false">
				<ui:field field="select" visible="true"></ui:field>
				<ui:field field="isChecked" label="isChecked" visible="false"></ui:field>
				<ui:field field="servOffId" label="�����ṩ��ʶ" visible="true"></ui:field>
				<ui:field field="servOffName" label="�����ṩ����" popup="true"></ui:field>
			</ui:dataset>
			
			<!-- ���շ����ṩ��ѯ�����Ĳ�Ʒ�б� -->
			<ui:dataset datasetId="prodOffList" loadDataAction="PrivilegeService"
			pageIndex="1" pageSize="20" autoLoadPage="true" loadAsNeeded="false" paginate="true"
			loadDataActionMethod="getProductOfferList" staticDataSource="true" async="false">
				<ui:field field="select" visible="false"></ui:field>
				<ui:field field="isChecked" label="isChecked" visible="false"></ui:field>
				<ui:field field="servOffId" label="��Ʒ��ʶ" visible="true"></ui:field>
				<ui:field field="servOffName" label="��Ʒ����" popup="true"></ui:field>
				<ui:parameter parameterId="serviceOfferId" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="privilegeId" type="string" value=""></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="selectedProdOffList" loadDataAction="PrivilegeService"
			loadDataActionMethod="getSelectedProductOfferList" staticDataSource="true" async="false">
				<ui:field field="select" visible="false"></ui:field>
				<ui:field field="isChecked" label="isChecked" visible="false"></ui:field>
				<ui:field field="servOffId" label="��Ʒ��ʶ" visible="true"></ui:field>
				<ui:field field="servOffName" label="��Ʒ����" popup="true"></ui:field>
				<ui:parameter parameterId="serviceOfferId" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="privilegeId" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="offInfo" readOnly="true">
				<ui:field field="offId" label="��Ʒ" visible="false"></ui:field>
				<ui:field field="offName" label="��Ʒ����" popup="true"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="serviceInfo" readOnly="true">
				<ui:field field="serviceOfferId" label="�����ṩID" visible="false"></ui:field>
				<ui:field field="serviceOfferName" label="�����ṩ����" popup="true"></ui:field>
			</ui:dataset>
		</div>

		<ui:layout type="border">
			<ui:pane position="center">
				<ui:tabpane id="addTabPane">
					<ui:tabpage desc="�������ṩ">
						<ui:layout type="border">
							<ui:pane position="top">
								<ui:form dataset="offInfo" inputLayout="69%" labelLayout="30%"></ui:form>
							</ui:pane>
							<ui:pane position="center">
								<ui:table dataset="offServList"></ui:table>
							</ui:pane>
						</ui:layout>
					</ui:tabpage>

					<ui:tabpage desc="����Ʒ">
						<ui:layout type="border">
							<ui:pane position="top">
								<ui:form dataset="serviceInfo" inputLayout="69%" labelLayout="30%"></ui:form>
							</ui:pane>
							<ui:pane position="center">
								<ui:layout type="border">
									<ui:pane position="left" style="width:260px">
										<ui:table dataset="prodOffList"></ui:table>
										<div type="short" class="customerpilot" extra="customerpilot" id="prodOffListPilot" dataset="prodOffList"></div>
									</ui:pane>
									<ui:pane position="center">
										<div align="center"><br/><br/>
											<table>
												<tr><td><ui:button id="addOne" style="display:block;">&nbsp&nbsp&gt&nbsp</ui:button></td></tr>
												<tr><td><ui:button id="addAll" style="display:block;">&nbsp&gt&gt&nbsp</ui:button></td></tr>
												<tr><td><ui:button id="removeOne" style="display:block;">&nbsp&lt&nbsp&nbsp</ui:button></td></tr>
												<tr><td><ui:button id="removeAll" style="display:block;">&nbsp&lt&lt&nbsp</ui:button></td></tr>
											</table>
										</div>
									</ui:pane>
									<ui:pane position="right" style="width:260px">
										<ui:table dataset="selectedProdOffList"></ui:table>
									</ui:pane>
								</ui:layout>
							</ui:pane>
						</ui:layout>
					</ui:tabpage>
				</ui:tabpane>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btnConfirm">ȷ��</ui:button>
				<ui:button id="btnCancel">ȡ��</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
