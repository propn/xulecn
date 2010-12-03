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
		init();
	}
	
	function init(){
		var partyId = window.dialogArguments[0] ;//Ա����������֯��partyId
		
		var parameterSet = staffPositionSelect.parameters();
		parameterSet.setDataType("partyId","string");
		parameterSet.setValue("partyId",partyId);
		Dataset.reloadData( staffPositionSelect ) ;
	}
	
	function confirm_onClick(){
			if( !$("staffPositionInfoForm").validate()){
				return ;
			}
			if( isNaN( staffPositionInfo.getValue("startStaffCode"))){
				alert("�������ӵĹ��ű�������������!");
				return ;
			}
			if( isNaN( staffPositionInfo.getValue("endStaffCode"))){
				alert("�������ӵĹ��ű�������������!");
				return ;
			}
			if( staffPositionInfo.getValue("endStaffCode").indexOf(".") > 0 ){
				alert("�������ӵĹ��ű�������������!");
				return ;
			}
			var start = parseInt( staffPositionInfo.getValue("startStaffCode") ) ;
			var end = parseInt( staffPositionInfo.getValue("endStaffCode"));
			
			var staffNameArray = staffPositionInfo.getValue("staffName").split(",");
			if( (end - start) != ( staffNameArray.length - 1)) {
				alert("Ա�������������͹���������һ��,Ա������֮��ʹ��Ӣ�Ķ��ŷָ�!");
				return ;
			}
			/*for( var i = 0 ; i < staffNameArray.length ; i ++ ){
				alert("Staff Name : " + staffNameArray[i] ) ;
			}*/
			
			if( end < start ){
				alert("��ʼ���ű���С�ڽ�������!");
				return;
			}
			var currentDate = getTodayStr();
			/*if( staffPositionInfo.getValue("effDate") > currentDate ){
				alert("��Ч���ڱ����ڽ�����߽���֮ǰ!");
				return ;
			}*/ 
			if( staffPositionInfo.getValue("expDate") <= currentDate ){
				alert("ʧЧ���ڱ�����ڽ���!");
				return ;
			}			
			if( staffPositionInfo.getValue("effDate") > staffPositionInfo.getValue("expDate")){
				alert("��Ч���ڱ���ǰ��ʧЧ����!");
				return;
			}
			var staffWithPostVO = new StaffWithPostVO();
			staffWithPostVO["orgPostId"] = staffPositionInfo.getValue("orgPostId");
			staffWithPostVO["state"] = "00A";
			staffWithPostVO["effDate"] =staffPositionInfo.getValue("effDate");
			staffWithPostVO["expDate"]  = staffPositionInfo.getValue("expDate") ;
			staffWithPostVO["startStaffCode"] = staffPositionInfo.getValue("startStaffCode");
			staffWithPostVO["endStaffCode"] = staffPositionInfo.getValue("endStaffCode");
			staffWithPostVO["staffName"] = staffPositionInfo.getValue("staffName");
			staffWithPostVO["channelSegmentId"] = staffPositionInfo.getValue("channelSegmentId");
			staffWithPostVO["lanId"] = staffPositionInfo.getValue("lanId");
			staffWithPostVO["businessId"] = staffPositionInfo.getValue("businessId");
			staffWithPostVO["countyType"] = staffPositionInfo.getValue("countyType");
			staffWithPostVO["orgManager"] = staffPositionInfo.getValue("orgManager");
			staffWithPostVO["gender"] = staffPositionInfo.getValue("gender");
			
			window.returnValue = staffWithPostVO;
			window.close();
	}
	function StaffWithPostVO(){
		this.orgPostId = "";
		this.state = "";
		this.effDate = "";
		this.expDate = "";
		this.startStaffCode = "";
		this.endStaffCode = "";
		this.staffName = "";
		this.channelSegmentId = "";
		this.lanId = "";
		this.businessId = "";
		this.countyType = "";
		this.orgManager = "";
		this.gender = "";
	}
	function concel_onClick(){
		returnValue = null ;
		window.close();
	}
	
</script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="staffPositionSelect" loadDataAction="PartyService" loadDataActionMethod="getPositionListByPartyId" async="false" staticDataSource="true">
				<ui:field field="orgPostId" label="��λ��ʶ" visible="false"></ui:field>
				<ui:field field="positionName" label="��λ" dropDown="staffPositionDropdown"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="staffPositionInfo">
				<ui:field field="startStaffCode" label="��ʼ����" size="9" required="true" validType="require" validMsg="��������ʼ����!"></ui:field>
				<ui:field field="endStaffCode" label="��ֹ����" size="9" required="true" validType="require" validMsg="��������ֹ����!"></ui:field>
				<ui:field field="effDate" type="date" label="��Чʱ��" type="date" required="true" validType="require" validMsg="��������Чʱ��!"></ui:field>
				<ui:field field="expDate" type="date" label="ʧЧʱ��" type="date" required="true" validType="require" validMsg="������ʧЧʱ��!"></ui:field>				
				<ui:field field="channelSegmentId" label="��������" visible="true" dropDown="channelDropdown"></ui:field>				
				<ui:field field="orgPostId" label="��λ��ʶ" visible="false"></ui:field>
				<ui:field field="positionName" label="��λ����" visible="false" keyField="orgPostId" dropDown="staffPositionDropdown"  required="false" validType="require" validMsg="��ѡ���λ!"></ui:field>
				<ui:field field="state" label="״̬" dropDown="stateSelect" visible="false"></ui:field>
				<ui:field field="lanId" label="������" attrCode="DC_LAN_CODE" visible="true"></ui:field>
				<ui:field field="businessId" label="Ӫҵ��" attrCode="DC_BUSINESS" ></ui:field>
				<ui:field field="countyType" label="�����־" dropDown="countyType"></ui:field>
				<ui:field field="orgManager" label="�Ƿ������" required="true" validType="require" validMsg="��ѡ���Ƿ������!" attrCode="IS_ORG_MANAGER"></ui:field>
				<ui:field field="gender" label="�Ա�" attrCode="SEX" required="true" validType="require" validMsg="��ѡ���Ա�!"></ui:field>
				<ui:field field="staffName" label="Ա������" textarea="true" textareaHeight="100px" inputLayout="88%" required="true" validType="require" validMsg="������Ա������!"></ui:field>				
			</ui:dataset>
		</div>
		
		<div id="dropdownDefine">	
			<!-- �����־ -->
			<xml id="__countyType">
			<items>
			<item label="����" value="0" />
			<item label="���" value="1" />
			</items>
			</xml>
			<code id="countyType"></code>
		</div>
		
		<div id="dropdownDefine">
			<ui:dropdown id="channelDropdown" attrCode="DC_CHANNEL_SEGMENT" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="stateSelect" attrCode="COMM_RECORD_STATE" staticDataSource="false"></ui:dropdown>
			<code type="dataset" maxHeight="80" id="staffPositionDropdown" dataset="staffPositionSelect" readFields="orgPostId" writeFields="orgPostId,positionName" visibleFields="positionName" showColumnHeader="false" staticDataSource="false" cachable="false"></code>
		</div>
		<div id="layoutDefine">
			<ui:panel type="modalDialog" desc="����Ա������(���Ա������֮��ʹ��Ӣ�Ķ��ŷָ�)">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:form dataset="staffPositionInfo" id="staffPositionInfoForm" submit="confirm" labelLayout="25%" inputLayout="70%"></ui:form>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="confirm">ȷ��</ui:button>
							<ui:button id="concel">ȡ��</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</div>
	</body>

</html>
