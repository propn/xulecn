<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- ��ѯFormʹ�õ�Dataset����-->
			<ui:dataset datasetId="queryInfo">
				<ui:field field="queryScope" label="��ѯ��Χ" dropDown="scopeSelect"></ui:field>
				<ui:field field="partyName" label="Ա������"></ui:field>
				<ui:field field="staffCode" label="Ա����"></ui:field>
			</ui:dataset>

			<!-- Ա�����ʹ�õ�Dataset-->
			<ui:dataset datasetId="staffList" pageIndex="1" pageSize="10" readOnly="false" editable="false" 
			loadDataAction="PartyService" loadDataActionMethod="getStaffsByStaffCondPaginate" 
			autoLoadPage="true" staticDataSource="true" loadAsNeeded="false" paginate="true" async="false">
				<ui:field field="partyRoleId" label="�����˽�ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="partyName" label="Ա������"></ui:field>
				<ui:field field="staffCode" label="Ա����"></ui:field>
				<ui:field field="staffDesc" label="Ա������"></ui:field>
				<ui:field field="officeId" label="�칫�ص�ID" visible="false"></ui:field>
				<ui:field field="officeName" label="�칫�ص�" visible="false"></ui:field>
				<ui:field field="limitCount" label="��¼��������"></ui:field>
				<ui:field field="password" label="����" visible="false"></ui:field>
				<ui:field field="pwdvalidtype" label="������Ч������" visible="false"></ui:field>
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				
				<ui:field field="lanId" label="������"  attrCode="DC_LAN_CODE"></ui:field>
				<ui:field field="orgPartyId" label="������֯" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="������֯" visible="true"></ui:field>
				<ui:field field="devUserBelong" label="devUserBelong" visible="false"></ui:field>
				<ui:field field="devUserBelongName" label="devUserBelongName" visible="false"></ui:field>
				<ui:field field="channelSegmentId" label="����" visible="true" attrCode="DC_CHANNEL_SEGMENT"></ui:field>
				<ui:field field="partyRoleType" label="�����˽�ɫ����" visible="false"></ui:field>
				<ui:field field="orgManager" label="�Ƿ���֯������" visible="false"></ui:field>
				<ui:field field="createDate" label="��������" visible="false"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" visible="false"></ui:field>
				<ui:field field="expDate" label="ʧЧ����" visible="false"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" visible="false"></ui:field>
				
				<ui:field field="businessId" label="Ӫҵ��" attrCode="DC_BUSINESS" ></ui:field>
				<ui:field field="countyType" label="�����־" dropDown="countyType"></ui:field>
				
				<!-- ���˱� ��Ϣά��-->
				<ui:field field="gender" label="�Ա�" visible="false"></ui:field>
				<ui:field field="birthPlace" label="����"></ui:field>
				<ui:field field="birthDate" label="����" visible="false"></ui:field>
				<ui:field field="maritalStatus" label="���" visible="false"></ui:field>
				<ui:field field="devOrgId" label="���������̰���ID" visible="false"></ui:field>
				<ui:field field="devOrgName" label="���������̰���" popup="true" visible="true"></ui:field>
				<ui:field field="skill" label="�س�" visible="false"></ui:field>
				<ui:parameter parameterId="condition" type="object" value=""></ui:parameter>
			</ui:dataset>			
		</div>
		<div id="dropdownDefine">
			<!-- ��ѯ��Χ -->
			<xml id="__scopeSelect">
			<items>
			<item label="����֯" value="0" />
			<item label="����֯��������֯" value="1" />
			</items>
			</xml>
			<code id="scopeSelect"></code>
			
			<!-- �����־ -->
			<xml id="__countyType">
			<items>
			<item label="����" value="0" />
			<item label="���" value="1" />
			</items>
			</xml>
			<code id="countyType"></code>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="��ѯ����">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="doQuery" dataset="queryInfo" labelLayout="15%" inputLayout="17%"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:120px;">
									<ui:button id="doQuery">��ѯ</ui:button>
									<ui:button id="doReset">����</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="left" style="width:280px">
<ui:layout type="border">
	<ui:pane position="center">
					<ui:panel type="titleList" desc="��֯����">
						<ui:content>
							<ZTESOFT:treelist id="orgTreeView" class="treelist" onItemClick="clickOrganization()" showImage="false" showBorder="false" contBorder="false" height="400" showImage=false showHead="false">
								<ZTESOFT:columns>
									<ZTESOFT:column width="100%" display="true" displayText="��֯����" propertyName="orgName" />
									<ZTESOFT:column width="" display="false" displayText="��֯����" propertyName="orgCode" />
									<ZTESOFT:column width="" display="false" displayText="��֯���ͱ�ʶ" propertyName="orgTypeId" />
									<ZTESOFT:column width="" display="false" displayText="��֯��ʶ" propertyName="partyId" />
									<ZTESOFT:column width="" display="false" displayText="state" propertyName="state" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
						</ui:content>
					</ui:panel>
	</ui:pane>
</ui:layout>
				</ui:pane>
				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:layout type="border">

								<ui:pane position="center">
									<!-- ��ʾ��Ա�б�-->
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="staffList"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<div class="customerpilot" extra="customerpilot" id="staffPilot" dataset="staffList"></div>
										 <ui:button id="btn_confirm">ȷ��</ui:button>
			      						 <ui:button id="btn_cancle">ȡ��</ui:button>
										</ui:pane>
										
									</ui:layout>
								</ui:pane>
							</ui:layout>
						</ui:pane>
					</ui:layout>
				</ui:pane>
			</ui:layout>
		</div>
		<script type="text/javascript">
		var menuCode = "";
		function page_onLoad(){
		    //var s = new Date() ;
			//menuCode = Global.getCurrentMenuCode(); //��ʱ��֪������������ �Ժ��޸�  oaas/Staff.js
			initOrganizationTree();
			//jsDebug.debug("staff page load time="+(new Date()-s));
		}
function initOrganizationTree(){
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		document.all.orgTreeView.loadByXML(queryResult );
		
		var rootItems = orgTreeView.rootItems ;
		for( var i = 0 ; i < rootItems.length ; i ++ ){
			var item = rootItems[i] ;
			if( ( item.orgTypeId == "5" || item.orgTypeId == "6") && item.state == "00A" ){
				item.setFontColor("#4422FF"); 
			}
			item.removeChildren();
		}
		clickOrganization();
	}
	
	//ajaxCall.remoteCall("PartyService","getRootOrganizationListByPrivControl",[menuCode],callBack);	
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
}
//��ѯStaff���������Ӧ�ı�����
function doQuery_onClick(){
	staffList.clearData();
	staffList.insertRecord(null);
	
	var staff = new Staff();
	if( orgTreeView.selectedItem == null ){
		return ;
	}
	staff.orgPartyId = orgTreeView.selectedItem.partyId ;
	staff.staffCode = queryInfo.getValue("staffCode" );
	staff.partyName = queryInfo.getValue("partyName");
	staff.scope = queryInfo.getValue("queryScope");
	
	
	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;
	Dataset.reloadData( staffList ) ;
	
}
//��֯���ĵ��ʱ��,�����ϻ�ȡorgId,��������֯id���������˲�ѯ��Ӧ����Ա
function clickOrganization(){
	downloadSubItems();
	getStaffByOrgId();
	//staffTableClick();
}
function downloadSubItems(){
	var selItem = orgTreeView.selectedItem ;
	if( selItem == null ){
		return ;
	}
	if( selItem.items ){
		return ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			
			if( selItem.items ){
				for( var i = 0; i < selItem.items.length ; i ++ ){
					var item = selItem.items[i] ;
					if( ( item.orgTypeId == "5" || item.orgTypeId == "6") && item.state == "00A" ){
						item.setFontColor( "#4422FF" ) ;
					}
				}
			}
		}
	}
	
	var regionId = selItem.partyId ;
	ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
}

function getStaffByOrgId(){
	var selItem = orgTreeView.selectedItem ;
	if( !selItem )
		return ;
	var orgId = selItem.partyId ;

	var staff = new Staff() ;
	staff["orgPartyId"] = orgId ;
	var parameterSet = staffList.parameters();
	parameterSet.setDataType("condition", "object");
	parameterSet.setValue("condition", staff ) ;
	parameterSet.setValue("pageIndex","1");
	Dataset.reloadData( staffList ) ;
	staffPilot.refreshCustomerPilot;
}
function Staff(){
	this.partyRoleId = "" ;
	this.partyName = "" ;
	this.staffCode = "" ;
	this.staffDesc = "" ;
	this.password = "" ;
	this.officeId = "";
	this.pwdvalidtype = "" ;
	this.limitCount = "" ;
	this.partyId = "" ;
	this.orgPartyId = "" ;
	this.orgPartyName = "";
	this.partyRoleType = "" ;
	this.orgManager = "" ;
	this.createDate = "" ;
	this.expDate = "" ;
	this.effDate = "" ;
	this.state = "" ;
	this.stateDate = "" ;
	this.gender = "" ;
	this.birthPlace = "" ;
	this.birthDate = "" ;
	this.maritalStatus = "" ;
	this.skill = "" ;
	this.scope="";//��ѯ��Χ
	this.channelSegmentId="";
	this.devUserBelong = "";
	this.devUserBelongName = "";
	//this.officeName = "";
	this.officeId = "";
	this.lanId = "";
	this.devOrgId = "";
	this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.StaffVO' ;	
}

  function btn_confirm_onClick()
  {
    var  record=staffList.getCurrent();
    if (!record) return;
    var back={};
    back['partyRoleId']=record.getValue("partyRoleId");
    back['partyName']=record.getValue("partyName");
    window.returnValue = back;
    window.close();
  }
		</script>
	</body>
</html>
