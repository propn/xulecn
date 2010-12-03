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
		var partyId = window.dialogArguments[0] ;//员工所属的组织的partyId
		
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
				alert("批量增加的工号必须是数字类型!");
				return ;
			}
			if( isNaN( staffPositionInfo.getValue("endStaffCode"))){
				alert("批量增加的工号必须是数字类型!");
				return ;
			}
			if( staffPositionInfo.getValue("endStaffCode").indexOf(".") > 0 ){
				alert("批量增加的工号必须是数字类型!");
				return ;
			}
			var start = parseInt( staffPositionInfo.getValue("startStaffCode") ) ;
			var end = parseInt( staffPositionInfo.getValue("endStaffCode"));
			
			var staffNameArray = staffPositionInfo.getValue("staffName").split(",");
			if( (end - start) != ( staffNameArray.length - 1)) {
				alert("员工姓名的数量和工号数量不一致,员工姓名之间使用英文逗号分割!");
				return ;
			}
			/*for( var i = 0 ; i < staffNameArray.length ; i ++ ){
				alert("Staff Name : " + staffNameArray[i] ) ;
			}*/
			
			if( end < start ){
				alert("起始工号必须小于结束工号!");
				return;
			}
			var currentDate = getTodayStr();
			/*if( staffPositionInfo.getValue("effDate") > currentDate ){
				alert("生效日期必须在今天或者今天之前!");
				return ;
			}*/ 
			if( staffPositionInfo.getValue("expDate") <= currentDate ){
				alert("失效日期必须迟于今天!");
				return ;
			}			
			if( staffPositionInfo.getValue("effDate") > staffPositionInfo.getValue("expDate")){
				alert("生效日期必须前于失效日期!");
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
				<ui:field field="orgPostId" label="岗位标识" visible="false"></ui:field>
				<ui:field field="positionName" label="岗位" dropDown="staffPositionDropdown"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="staffPositionInfo">
				<ui:field field="startStaffCode" label="起始工号" size="9" required="true" validType="require" validMsg="请输入起始工号!"></ui:field>
				<ui:field field="endStaffCode" label="终止工号" size="9" required="true" validType="require" validMsg="请输入终止工号!"></ui:field>
				<ui:field field="effDate" type="date" label="生效时间" type="date" required="true" validType="require" validMsg="请输入生效时间!"></ui:field>
				<ui:field field="expDate" type="date" label="失效时间" type="date" required="true" validType="require" validMsg="请输入失效时间!"></ui:field>				
				<ui:field field="channelSegmentId" label="渠道名称" visible="true" dropDown="channelDropdown"></ui:field>				
				<ui:field field="orgPostId" label="岗位标识" visible="false"></ui:field>
				<ui:field field="positionName" label="岗位名称" visible="false" keyField="orgPostId" dropDown="staffPositionDropdown"  required="false" validType="require" validMsg="请选择岗位!"></ui:field>
				<ui:field field="state" label="状态" dropDown="stateSelect" visible="false"></ui:field>
				<ui:field field="lanId" label="本地网" attrCode="DC_LAN_CODE" visible="true"></ui:field>
				<ui:field field="businessId" label="营业区" attrCode="DC_BUSINESS" ></ui:field>
				<ui:field field="countyType" label="城乡标志" dropDown="countyType"></ui:field>
				<ui:field field="orgManager" label="是否管理者" required="true" validType="require" validMsg="请选择是否管理者!" attrCode="IS_ORG_MANAGER"></ui:field>
				<ui:field field="gender" label="性别" attrCode="SEX" required="true" validType="require" validMsg="请选择性别!"></ui:field>
				<ui:field field="staffName" label="员工姓名" textarea="true" textareaHeight="100px" inputLayout="88%" required="true" validType="require" validMsg="请输入员工姓名!"></ui:field>				
			</ui:dataset>
		</div>
		
		<div id="dropdownDefine">	
			<!-- 城乡标志 -->
			<xml id="__countyType">
			<items>
			<item label="城市" value="0" />
			<item label="乡村" value="1" />
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
			<ui:panel type="modalDialog" desc="批量员工增加(多个员工姓名之间使用英文逗号分割)">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:form dataset="staffPositionInfo" id="staffPositionInfoForm" submit="confirm" labelLayout="25%" inputLayout="70%"></ui:form>
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
