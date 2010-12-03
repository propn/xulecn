<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<title></title>
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,validator">
		<script type="text/javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script>
	function page_onLoad(){
		initStaffPositionDialog();
	}
	
	function initStaffPositionDialog(){
		var orgPartyId = window.dialogArguments[1] ;//Ա����������֯��partyId
		staffPositionSelect.parameters().setValue("orgPartyId",orgPartyId);
		
		Dataset.reloadData( staffPositionSelect ) ;
		var recordCount = staffPositionSelect.getCount();
		if( recordCount == 0 ){
			alert("��û�пɹ�����ĸ�λȨ��!");
			return ;
		}
		
		var addedPostIds = window.dialogArguments[3];
		var record = staffPositionSelect.getFirstRecord() ;
		
		while( record ) {
			for( var i = 0 ; i < addedPostIds.length ; i ++ ){
				if( record.getValue("orgPostId") == addedPostIds[i] ){
					record.setValue("select","true");
				}
			}
			record = record.getNextRecord() 
		}
		
		/*
		if( window.dialogArguments[0] == "update"){
			staffPositionInfo.setFieldReadOnly("orgPostId",true);
			staffPositionInfo.setFieldReadOnly("positionName",true);
			staffPositionInfo.setValue("orgPostId", window.dialogArguments[3]);//��֯��λ���
			staffPositionInfo.setValue("positionName", window.dialogArguments[7]);//��֯��λ����
			staffPositionInfo.setValue("state",window.dialogArguments[4]);//״̬
			staffPositionInfo.setValue("effDate",window.dialogArguments[5]);//��Чʱ��
			staffPositionInfo.setValue("expDate",window.dialogArguments[6]);//ʧЧʱ��
		}*/
	}
	
	function confirm_onClick(){
		if( staffPositionSelect.getCount() == 0 ){
			returnValue = null ;
			window.close();
		}
		
		if( !$("staffPositionForm").validate()){
			return ;
		}
		
			var currentDate = getTodayStr();
			if( staffPositionInfo.getValue("effDate") > currentDate ){
				alert("��Ч���ڱ����ڽ�����߽���֮ǰ!");
				return ;
			}
			if( staffPositionInfo.getValue("expDate") <= currentDate ){
				alert("ʧЧ���ڱ�����ڽ���!");
				return ;
			}			
			if( staffPositionInfo.getValue("effDate") > staffPositionInfo.getValue("expDate")){
				alert("��Ч���ڱ���ǰ��ʧЧ����!");
				return;
			}
			
			var addedPostIds = window.dialogArguments[3];
			var record = staffPositionSelect.getFirstRecord() ;
			var saveList = new Array() ;
			var count = 0 ;
			while( record ){
				if( !System.isTrue( record.getValue("select"))){
					record = record.getNextRecord() ;
					continue ;
				}
				var orgPostVo = new StaffPostVO();
				orgPostVo["partyRoleId"] = window.dialogArguments[2] ;//�����˽�ɫ��ʶ
				orgPostVo["orgPostId"] = record.getValue("orgPostId");
				orgPostVo["state"] = "00A";
				orgPostVo["effDate"] =staffPositionInfo.getValue("effDate");
				orgPostVo["effDate"] = orgPostVo["effDate"] + " 00:00:00" ;
				orgPostVo["expDate"]  = staffPositionInfo.getValue("expDate");
				orgPostVo["expDate"] = orgPostVo["expDate"] + " 23:59:59";
				saveList[count] = orgPostVo ;
				count ++ ;
				record = record.getNextRecord() ;
			}
			var result = null ;
			var ajaxCall = new NDAjaxCall( true ) ;
			var callBack = function( reply ){
				result = reply.getResult() ; 
				alert("��λ����ɹ�!");
				window.returnValue = 0 ;
				window.close();
			}
			
			if( saveList.length == 0 ){
				alert("û��ѡ���λ,��ѡ��Ҫ�����ӵĸ�λ���ߵ��ȡ����ť����!");
				return ;
			}
			if( window.dialogArguments[0] == "insert" ){
				ajaxCall.remoteCall("PartyService", "insertStaffPost", [saveList], callBack );
			}
			/*else if( window.dialogArguments[0] == "update" ){
				ajaxCall.remoteCall("PartyService", "updateStaffPost",arr,callBack);
			}*/
	}
	
	function concel_onClick(){
		returnValue = null ;
		window.close();
	}
	
	function StaffPostVO(){
		this.partyRoleId = "";
		this.orgPostId = "";
		this.effDate = "";
		this.expDate = "";
		this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.StaffPostVO' ;
	}
</script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="staffPositionSelect" loadDataAction="PartyService" 
			loadDataActionMethod="getCurrentStaffPost" staticDataSource="true" async="false">
				<ui:field field="select"></ui:field>			
				<ui:field field="orgPostId" label="��λ��ʶ" visible="true"></ui:field>
				<ui:field field="positionName" label="��λ"></ui:field>
				<ui:parameter parameterId="orgPartyId" type="string" value=""></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="staffPositionInfo">
				<ui:field field="effDate" label="��Чʱ��" type="date" required="true" validType="require" validMsg="��ѡ��Чʱ��!"></ui:field>
				<ui:field field="expDate" label="ʧЧʱ��" type="date" required="true" validType="require" validMsg="��ѡʧЧʱ��!"></ui:field>
			</ui:dataset>
		</div>
		
		<div id="layoutDefine">

					<ui:layout type="border">
						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="top">
									<ui:form submit="confirm" dataset="staffPositionInfo" id="staffPositionForm" labelLayout="15%" inputLayout="34%"></ui:form>
								</ui:pane>							
								<ui:pane position="center">
									<ui:panel type="titleList" desc="��λ�б�">
										<ui:content>
											<ui:table dataset="staffPositionSelect"></ui:table>
										</ui:content>
									</ui:panel>
								</ui:pane>
							</ui:layout>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="confirm">ȷ��</ui:button>
							<ui:button id="concel">ȡ��</ui:button>
						</ui:pane>
					</ui:layout>

		</div>
	</body>

</html>
