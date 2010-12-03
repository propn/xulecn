<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<title>��̬��Ϣ</title>
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,validator">
		<script type="text/javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="JavaScript">
		var ajaxCall = new NDAjaxCall();
</script>
		<script>
function Identification(){
	this.identId="";
	this.partyId="";
	this.partyRoleId = "";
	this.socialIdType="";
	this.socialId="";
	this.createdDate="";
	this.effDate="";
	this.expDate="";
	this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.PartyIdentificationVO' ;
}
function page_onLoad(){
  init();
}
	function init(){
		identificationInfo.clearData();
		identificationInfo.insertRecord( null ) ;
		var actionType = window.dialogArguments[0];
		if( actionType == "update" ){
			var id = window.dialogArguments[1];
			for( var ele in id ){
				if( identificationInfo.getField( ele )){
					identificationInfo.setValue( ele, id[ele] ) ;
				}
			}
		}else if( actionType == "insert" ){
			identificationInfo.setValue( "partyRoleId", window.dialogArguments[1] );
		}
	}
	
	function commit_onClick(){
		if( !$("identificationForm").validate()){
			return ;
		}
		var currentDate = getTodayStr();
		if( identificationInfo.getValue("effDate") > currentDate ){
			alert("��Ч���ڱ����ڽ�����߽���֮ǰ!");
			return ;
		}
		if( identificationInfo.getValue("expDate") <= currentDate ){
			alert("ʧЧ���ڱ�����ڽ���!");
			return ;
		}			
		if( identificationInfo.getValue("effDate") > identificationInfo.getValue("expDate")){
			alert("��Ч���ڱ���ǰ��ʧЧ����!");
			return;
		}		
		var actionType = window.dialogArguments[0];//��������;
		var partyRoleId = window.dialogArguments[1];//�����˽�ɫId
		
		var ajaxCall = new NDAjaxCall(true);
		var result ;
		
		var callBack = function( reply ){
			result = reply.getResult() ;
			if( result ){
				if( actionType == "insert" ){
					alert("���Ӷ�̬��ʶ�ɹ�!");
				}else if ( actionType == "update" ){
					alert("�޸Ķ�̬��ʶ�ɹ�!");
				}
				window.returnValue = 0 ;
			}else{
				window.returnValue = -1;
			}
			window.close();			
		}
		
		var identification = new Identification();
		for( var ele in identification ){
			if( identificationInfo.getField( ele )){
				identification[ele] = identificationInfo.getValue( ele ) ;
			}
		}
		
	if( identification["expDate"] == "" || identification["expDate"] == null ){
		identification["expDate"] = "2029-01-01 00:00:00" ;
	}else{
		identification["expDate"] = identification["expDate"] + " 23:59:59";
	}
	identification["effDate"] = identification["effDate"] + " 00:00:00";

		var arr = new Array();
		arr[0] = identification ;
		
		if( actionType == "insert" ){			
			arr[1] = partyRoleId ;
			ajaxCall.remoteCall("PartyService","insertPartyIdentification",arr,callBack);
		}else if( actionType == "update" ){
			ajaxCall.remoteCall("PartyService","updatePartyIdentification",arr,callBack);	
		}
	}
	
	function cancel_onClick(){
		window.returnValue = -1 ;
		window.close() ;
	}

</script>
	</head>
	<body>
		<div id="datasetDefine">
			<div id="datasetDefine">
				<ui:dataset datasetId="identificationInfo">
					<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
					<ui:field field="partyRoleId" label="�����˽�ɫ��ʶ" visible="false"></ui:field>
					<ui:field field="identId" label="ʶ����Ϣ��ʶ" visible="false"></ui:field>
					<ui:field field="effDate" label="��Чʱ��" type="date"  required="true" validType="require" validMsg="��ѡ����Чʱ��!"></ui:field>
					<ui:field field="expDate" label="ʧЧʱ��" type="date"  required="true" validType="require" validMsg="��ѡ��ʧЧʱ��!"></ui:field>
					<ui:field field="socialIdType" label="����ʶ����" dropDown="socialIdTypeSelect" required="true" validType="require" validMsg="��ѡ������ʶ����!"></ui:field>
					<ui:field field="socialId" label="����ʶ��" size="30" required="true" validType="require" validMsg="����������ʶ��!"></ui:field>
					<ui:field field="createdDate" label="����ʱ��" type="date" visible="false"></ui:field>
				</ui:dataset>
			</div>
		</div>
		<div id="dropdownDefine">
			<!-- xml id="__socialIdTypeSelect">
			<items>
			<item label="���֤" value="91A" />
			<item label="����" value="91B" />
			<item label="˰��Ǽ�֤" value="91C" />
			</items>
			</xml>
			<code id="socialIdTypeSelect"></code-->
			<ui:dropdown id="socialIdTypeSelect" attrCode="SOCIAL_ID_TYPE" staticDataSource="false"></ui:dropdown>
						
		</div>
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="��̬��Ϣ">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:form dataset="identificationInfo" id="identificationForm" labelLayout="25%" inputLayout="70%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="commit">ȷ��</ui:button>
							<ui:button id="cancel">ȡ��</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</div>
	</body>
</html>
