<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar" />
		<title>��Ʒ����Ҫ��</title>
		<script type="text/javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script>
var privilegeId;

function OffWarrRequementVO(){
	this.privId = "";
	this.offId = "";
	this.offName = "";
	this.warrContent = "";
	this.warrMode = "";
	this.warrValue = "";
	this.restrictServices = "";
	this.warrLen = "";
	this.uniteAcctRequest = "";
	this.maxWarrNum = "";
	this.maxUniteAcctNum = "";
	this.offWarrRequementId = "";
}

function page_onLoad(){
	if(window.dialogArguments){
		privilegeId = window.dialogArguments;
	}
	btn_query_onClick() ;
}
      
function btn_query_onClick(){//��ѯ��ť��Ӧ�¼�
	var queryObj = new OffWarrRequementVO() ;
	for( var ele in queryObj ){
		queryObj[ele] = queryCond.getValue( ele ) ;
	}
	offerWarrList.parameters().setValue("cond", queryObj);
	offerWarrList.reloadData();
}
		  
function btn_reset_onClick(){//���ð�ť��Ӧ�¼�
	queryCond.clearData() ;
}
		  
function btn_add_onClick(){
    var record = newOfferWarrList.getFirstRecord();
    var i = 0;
    var saveObjList = new Array();
	while( record ){
			var obj = new Object() ;
			obj.privId = window.dialogArguments[0] ;
			obj.dataPkey1 = record.getValue("offWarrRequementId");
			obj.dataPkey2 = "-1";
			obj.dataPkey3 = "-1";
			saveObjList[i] = obj ;
			i ++ ;
		record = record.getNextRecord() ;
	}
	if( saveObjList.length > 0 ){
		var ajaxCall = new NDAjaxCall( true ) ;
		var callBack = function( reply ) {
			alert("���ӳɹ�!") ;
			window.close();
		}
		ajaxCall.remoteCall("PrivilegeService","addOfferWarrPrivileges",[saveObjList],callBack);
	}
}
		  
function btn_cancel_onClick(){
	window.close();
}
		
function btn_addOne_onClick(){
	var record = offerWarrList.getCurrent();
	if(!record){
		return;
	}
	
	if(record && !isSelected(record)){
		newOfferWarrList.insertRecord();
		newOfferWarrList.copyRecord(record);
		offerWarrList.moveNext();
	}
	else{
		alert("�ü�¼���������Ȩ���б���!");
	}
} 

function isSelected(rec){
  	var record=newOfferWarrList.getFirstRecord();
  	while(record){
  		if(rec.getValue("offWarrRequementId")==record.getValue("offWarrRequementId"))
  		return true;
  		record=record.getNextRecord();
  	}
  	return false;
}
	   		
function btn_addAll_onClick(){
	var record = offerWarrList.getFirstRecord();
	var count = offerWarrList.getCount();
	var i = 0;
	if(!record)return;
	while(record){
		if(isSelected(record)){
			record = record.getNextRecord();
			i++;
			continue;
		}
		newOfferWarrList.insertRecord();
		newOfferWarrList.copyRecord(record);
		record = record.getNextRecord();
	}
	if(i==count){
		alert("����ȫ�����!");
		return;
	}
} 
		
function btn_removeOne_onClick(){
	if(newOfferWarrList.getCurrent()){
		newOfferWarrList.deleteRecord();
	}
}
	   
function btn_removeAll_onClick(){
	newOfferWarrList.clearData() ;
}
		</script>
	</head>
	<body>

		<div id="datasetDefine">
			<ui:dataset datasetId="queryCond">
				<ui:field field="offName" label="��Ʒ����" visible="true"></ui:field>
				<ui:field field="warrContent" label="��������" visible="true" attrCode="WARR_CONTENT" blankValue="true"></ui:field>
				<ui:field field="warrMode" label="������ʽ" visible="true" attrCode="WARR_MODE" blankValue="true"></ui:field>
				<ui:field field="warrValue" label="����ֵ" visible="true"></ui:field>
				<ui:field field="warrLen" label="����ʱ��" visible="true"></ui:field>
				<ui:field field="uniteAcctRequest" label="����Ҫ��" visible="true" attrCode="UNITE_ACCT_REQUEST" blankValue="true"></ui:field>
				<ui:field field="maxWarrNum" label="�ɵ��������" visible="true"></ui:field>
				<ui:field field="maxUniteAcctNum" label="����������" visible="true"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="offerWarrList" loadDataAction="PrivilegeService" 
			loadDataActionMethod="queryOfferWarr" staticDataSource="true" pageIndex="1" pageSize="15"
			 autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
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
				<ui:parameter parameterId="cond" type="object" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="offerWarrListExist" loadDataAction="PrivilegeService" 
			loadDataActionMethod="getOfferWarrByPrivilegeId" staticDataSource="true">
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

			<ui:dataset datasetId="newOfferWarrList" staticDataSource="true">
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
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:100px">
					<ui:panel type="titleTable" desc="��ѯ����">
						<ui:content>
							<ui:form dataset="queryCond" submit="btn_query" inputLayout="10%" labelLayout="14%"></ui:form>
							<div style="text-align:center">
								<ui:button id="btn_query">��ѯ</ui:button>
								<ui:button id="btn_reset">����</ui:button>
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>

				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="left" style="width:430px">
							<ui:panel type="titleTable" desc="��Ʒ����Ҫ���б�">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:layout type="border">
												<ui:pane position="center">
													<ui:table dataset="offerWarrList"></ui:table>
												</ui:pane>
												<ui:pane position="bottom">
													<div class="customerpilot" id="pilotAttribute" dataset="offerWarrList" type="short"></div>
												</ui:pane>
											</ui:layout>
										</ui:pane>
									</ui:layout>
								</ui:content>
							</ui:panel>
						</ui:pane>

						<ui:pane position="center" style="width:50px">
							<div align="center">
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<table>
									<tr>
										<td>
											<ui:button id="btn_addOne" style="display:block;">&nbsp&nbsp&gt&nbsp</ui:button>
										</td>
									</tr>
									<tr>
										<td>
											<ui:button id="btn_addAll" style="display:block;">&nbsp&gt&gt&nbsp</ui:button>
										</td>
									</tr>
									<tr>
										<td>
											<ui:button id="btn_removeOne" style="display:block;">&nbsp&lt&nbsp&nbsp</ui:button>
										</td>
									</tr>
									<tr>
										<td>
											<ui:button id="btn_removeAll" style="display:block;">&nbsp&lt&lt&nbsp</ui:button>
										</td>
									</tr>
								</table>
							</div>
						</ui:pane>

						<ui:pane position="right" style="width:430px">
							<ui:panel type="titleTable" desc="�������Ʒ����Ҫ���б�">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:layout type="border">
												<ui:pane position="center">
													<ui:table dataset="newOfferWarrList"></ui:table>
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
