<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar" />
		<title>���۲���Ŀ¼Ȩ������</title>
		<script type="text/javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script>
		  var privilegeId;
		  function page_onLoad(){
		       if(window.dialogArguments){
		           privilegeId = window.dialogArguments;
		           //offerPriceListExist.parameters().setValue("privilegeId", privilegeId);
		           //offerPriceListExist.reloadData();
		       }
		  }
      
		  function btn_query_onClick(){
		     var cataTypeId = queryCond.getValue("cataType");
		     var cataName = queryCond.getValue("cataName");
		     var pricePlanName = queryCond.getValue("pricePlanName");
		     var offerName = queryCond.getValue("offerName");

		     offerPriceList.parameters().setValue("privilegeId", privilegeId);
		     offerPriceList.parameters().setValue("cataTypeId", cataTypeId);
		     offerPriceList.parameters().setValue("cataName", cataName);
		     offerPriceList.parameters().setValue("pricePlanName", pricePlanName);
		     offerPriceList.parameters().setValue("offerName", offerName);
		     offerPriceList.reloadData();
		     /*�Ѵ�����Ȩ���еĶ��۲���Ŀ¼*/
		     
		  }
		  
		  function btn_reset_onClick(){
		    queryCond.setValue("cataType","");
		    queryCond.setValue("cataName","");
		    queryCond.setValue("pricePlanName","");
		    queryCond.setValue("offerName","");
		  }
		  
		  function btn_add_onClick(){
		    var record = newOfferPriceList.getFirstRecord();
		    var i = 0;
		    var saveObjList = new Array();
			while( record ){
				/*var isNew = true ;
				var existRecord = offerPriceListExist.getFirstRecord() ;
				while( existRecord ) {
					alert("new ID : " + record.getValue("pricingCataId") + "    Old Id : " + existRecord.getValue("pricingCataId"));
					if( existRecord.getValue("pricingCataId") == record.getValue("pricingCataId")){
						isNew = false ;
						break ;
					}
					existRecord = existRecord.getNextRecord() ;
				}
				if( isNew ){*/
					var obj = new Object() ;
					obj.privId = window.dialogArguments[0] ;
					obj.dataPkey1 = record.getValue("pricingCataId");
					obj.dataPkey2 = "-1";
					obj.dataPkey3 = "-1";
					saveObjList[i] = obj ;
					i ++ ;
				//}
				record = record.getNextRecord() ;
			}
			if( saveObjList.length > 0 ){
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ) {
					alert("���ӳɹ�!") ;
					window.close();
				}
				ajaxCall.remoteCall("PrivilegeService","addPricCataPrivileges",[saveObjList],callBack);
			}
		}
		  
		function btn_cancel_onClick(){
			window.close();
		}
		
		function btn_addOne_onClick(){
		 var record = offerPriceList.getCurrent();
		 if(!record)return;
		 if(record && !isSelected(record)){
		    newOfferPriceList.insertRecord();
			newOfferPriceList.copyRecord(record);
			offerPriceList.moveNext();
		 }
		 else{
		    alert("�ü�¼���������Ȩ���б���!");
		 }
		} 
		
		function btn_addAll_onClick(){
		 var record = offerPriceList.getFirstRecord();
		 var count = offerPriceList.getCount();
		 var i = 0;
		 if(!record)return;
		 while(record){
		    if(isSelected(record)){
		      record = record.getNextRecord();
		      i++;
		      continue;
		     }
		    newOfferPriceList.insertRecord();
			newOfferPriceList.copyRecord(record);
			record = record.getNextRecord();
		 }
		 if(i==count){
		   alert("����ȫ�����!");
		   return;
		 }
		} 
		
		function btn_removeOne_onClick(){
		 if(newOfferPriceList.getCurrent()){
		     newOfferPriceList.deleteRecord();
		 }
		} 
		
		function isSelected(rec){
		  	var record=newOfferPriceList.getFirstRecord();
		  	while(record){
		  		if(rec.getValue("pricingCataId")==record.getValue("pricingCataId"))
		  		return true;
		  		record=record.getNextRecord();
		  	}
		  	return false;
	   }
	   
      function btn_removeAll_onClick(){
	    newOfferPriceList.clearData() ;
      }
		</script>
	</head>
	<body>

		<div id="datasetDefine">
			<ui:dataset datasetId="queryCond">
				<ui:field field="cataType" label="Ŀ¼����" attrCode="CATALOG_TYPE" blankValue=" " blankId=""></ui:field>
				<ui:field field="cataName" label="Ŀ¼����"></ui:field>
				<ui:field field="pricePlanName" label="���ۼƻ�����"></ui:field>
				<ui:field field="offerName" label="��Ʒ����"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="offerPriceList" loadDataAction="PricePlanService" loadDataActionMethod="queryProdOfferPriceCata" staticDataSource="true" pageIndex="1" pageSize="15" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
				<ui:field field="prodOfferName" label="��Ʒ����"></ui:field>
				<ui:field field="pricePlanName" label="���ۼƻ�"></ui:field>
				<ui:field field="pricingCataType" label="Ŀ¼����" attrCode="CATALOG_TYPE"></ui:field>
				<ui:field field="pricingCataId" label="Ŀ¼���Ʊ�ʶ" visible="false"></ui:field>
				<ui:field field="pricingCataName" label="Ŀ¼����"></ui:field>
				<ui:field field="pricingObjectName" label="��������"></ui:field>
				<ui:parameter parameterId="privilegeId" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="cataTypeId" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="cataName" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="pricePlanName" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="offerName" type="string" value=""></ui:parameter>
			</ui:dataset>
			
			<ui:dataset datasetId="offerPriceListExist" loadDataAction="PricePlanService" 
			loadDataActionMethod="queryProdOfferPriceCataExist" staticDataSource="true">
				<ui:field field="prodOfferName" label="��Ʒ����"></ui:field>
				<ui:field field="pricePlanName" label="���ۼƻ�"></ui:field>
				<ui:field field="pricingCataType" label="Ŀ¼����" attrCode="CATALOG_TYPE"></ui:field>
				<ui:field field="pricingCataId" label="Ŀ¼���Ʊ�ʶ" visible="false"></ui:field>
				<ui:field field="pricingCataName" label="Ŀ¼����"></ui:field>
				<ui:field field="pricingObjectName" label="��������"></ui:field>
				<ui:parameter parameterId="privilegeId" type="string" value=""></ui:parameter>
				
			</ui:dataset>
			<ui:dataset datasetId="newOfferPriceList" loadDataAction="PricePlanService" loadDataActionMethod="queryProdOfferPriceCataExist" staticDataSource="true">
				<ui:field field="prodOfferName" label="��Ʒ����"></ui:field>
				<ui:field field="pricePlanName" label="���ۼƻ�"></ui:field>
				<ui:field field="pricingCataType" label="Ŀ¼����" attrCode="CATALOG_TYPE"></ui:field>
				<ui:field field="pricingCataId" label="Ŀ¼���Ʊ�ʶ" visible="false"></ui:field>
				<ui:field field="pricingCataName" label="Ŀ¼����"></ui:field>
				<ui:field field="pricingObjectName" label="������������"></ui:field>
			</ui:dataset>
		</div>


		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:100px">
					<ui:panel type="titleTable" desc="��ѯ����">
						<ui:content>
							<ui:form dataset="queryCond" submit="btn_query" inputLayout="23%" labelLayout="26%"></ui:form>
							<div style="text-align:center">
								<ui:button id="btn_query">��ѯ</ui:button>
								<ui:button id="btn_reset">����</ui:button>
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>

				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="left" style="width:300px">
							<ui:panel type="titleTable" desc="���۲���Ŀ¼�б�">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:layout type="border">
												<ui:pane position="center">
													<ui:table dataset="offerPriceList"></ui:table>
												</ui:pane>
												<ui:pane position="bottom">
													<div class="customerpilot" id="pilotAttribute" dataset="offerPriceList" type="short" ></div>
												</ui:pane>
											</ui:layout>
										</ui:pane>
									</ui:layout>
								</ui:content>
							</ui:panel>
						</ui:pane>

						<ui:pane position="center" style="width:50px">
						  <div align="center"><br/><br/><br/><br/><br/><br/><br/>
								<table>
									<tr><td><ui:button id="btn_addOne" style="display:block;">&nbsp&nbsp&gt&nbsp</ui:button></td></tr>
									<tr><td><ui:button id="btn_addAll" style="display:block;">&nbsp&gt&gt&nbsp</ui:button></td></tr>
									<tr><td><ui:button id="btn_removeOne" style="display:block;">&nbsp&lt&nbsp&nbsp</ui:button></td></tr>
									<tr><td><ui:button id="btn_removeAll" style="display:block;">&nbsp&lt&lt&nbsp</ui:button></td></tr>
								</table>
							</div>
						</ui:pane>

						<ui:pane position="right" style="width:300px">
							<ui:panel type="titleTable" desc="����Ķ��۲���Ŀ¼�б�">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:layout type="border">
												<ui:pane position="center">
													<ui:table dataset="newOfferPriceList"></ui:table>
												</ui:pane>
												<ui:pane position="bottom">
												</ui:pane>
											</ui:layout>
										</ui:pane>
									</ui:layout>
								</ui:content>
							</ui:panel>
						</ui:pane>
					  <ui:pane position="bottom" style="height:30px">
					        <ui:button id="btn_add">ȷ��</ui:button>
							<ui:button id="btn_cancel">ȡ��</ui:button>
					  </ui:pane>
					  
					</ui:layout>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
