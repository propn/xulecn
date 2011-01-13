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
			<!-- 查询Form使用的Dataset定义-->
			<ui:dataset datasetId="queryInfo">
				<ui:field field="queryScope" label="查询范围" dropDown="scopeSelect"></ui:field>
				<ui:field field="partyName" label="员工名称"></ui:field>
				<ui:field field="staffCode" label="员工号"></ui:field>
			</ui:dataset>

			<!-- 员工表格使用的Dataset-->
			<ui:dataset datasetId="staffList" pageIndex="1" pageSize="10" readOnly="false" editable="false" 
			loadDataAction="PartyService" loadDataActionMethod="getStaffsByStaffCondPaginate" 
			autoLoadPage="true" staticDataSource="true" loadAsNeeded="false" paginate="true" async="false">
				<ui:field field="partyRoleId" label="参与人角色标识" visible="false"></ui:field>
				<ui:field field="partyName" label="员工名称"></ui:field>
				<ui:field field="staffCode" label="员工号"></ui:field>
				<ui:field field="staffDesc" label="员工描述"></ui:field>
				<ui:field field="officeId" label="办公地点ID" visible="false"></ui:field>
				<ui:field field="officeName" label="办公地点" visible="false"></ui:field>
				<ui:field field="limitCount" label="登录次数限制"></ui:field>
				<ui:field field="password" label="密码" visible="false"></ui:field>
				<ui:field field="pwdvalidtype" label="密码有效期类型" visible="false"></ui:field>
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				
				<ui:field field="lanId" label="本地网"  attrCode="DC_LAN_CODE"></ui:field>
				<ui:field field="orgPartyId" label="所属组织" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="所属组织" visible="true"></ui:field>
				<ui:field field="devUserBelong" label="devUserBelong" visible="false"></ui:field>
				<ui:field field="devUserBelongName" label="devUserBelongName" visible="false"></ui:field>
				<ui:field field="channelSegmentId" label="渠道" visible="true" attrCode="DC_CHANNEL_SEGMENT"></ui:field>
				<ui:field field="partyRoleType" label="参与人角色类型" visible="false"></ui:field>
				<ui:field field="orgManager" label="是否组织管理者" visible="false"></ui:field>
				<ui:field field="createDate" label="创建日期" visible="false"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
				<ui:field field="effDate" label="生效时间" visible="false"></ui:field>
				<ui:field field="expDate" label="失效日期" visible="false"></ui:field>
				<ui:field field="stateDate" label="状态时间" visible="false"></ui:field>
				
				<ui:field field="businessId" label="营业区" attrCode="DC_BUSINESS" ></ui:field>
				<ui:field field="countyType" label="城乡标志" dropDown="countyType"></ui:field>
				
				<!-- 个人表 信息维护-->
				<ui:field field="gender" label="性别" visible="false"></ui:field>
				<ui:field field="birthPlace" label="籍贯"></ui:field>
				<ui:field field="birthDate" label="生日" visible="false"></ui:field>
				<ui:field field="maritalStatus" label="婚否" visible="false"></ui:field>
				<ui:field field="devOrgId" label="归属代理商班组ID" visible="false"></ui:field>
				<ui:field field="devOrgName" label="归属代理商班组" popup="true" visible="true"></ui:field>
				<ui:field field="skill" label="特长" visible="false"></ui:field>
				<ui:parameter parameterId="condition" type="object" value=""></ui:parameter>
			</ui:dataset>			
		</div>
		<div id="dropdownDefine">
			<!-- 查询范围 -->
			<xml id="__scopeSelect">
			<items>
			<item label="本组织" value="0" />
			<item label="本组织及下属组织" value="1" />
			</items>
			</xml>
			<code id="scopeSelect"></code>
			
			<!-- 城乡标志 -->
			<xml id="__countyType">
			<items>
			<item label="城市" value="0" />
			<item label="乡村" value="1" />
			</items>
			</xml>
			<code id="countyType"></code>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="查询条件">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="doQuery" dataset="queryInfo" labelLayout="15%" inputLayout="17%"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:120px;">
									<ui:button id="doQuery">查询</ui:button>
									<ui:button id="doReset">重置</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="left" style="width:280px">
<ui:layout type="border">
	<ui:pane position="center">
					<ui:panel type="titleList" desc="组织名称">
						<ui:content>
							<ZTESOFT:treelist id="orgTreeView" class="treelist" onItemClick="clickOrganization()" showImage="false" showBorder="false" contBorder="false" height="400" showImage=false showHead="false">
								<ZTESOFT:columns>
									<ZTESOFT:column width="100%" display="true" displayText="组织名称" propertyName="orgName" />
									<ZTESOFT:column width="" display="false" displayText="组织编码" propertyName="orgCode" />
									<ZTESOFT:column width="" display="false" displayText="组织类型标识" propertyName="orgTypeId" />
									<ZTESOFT:column width="" display="false" displayText="组织标识" propertyName="partyId" />
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
									<!-- 显示人员列表-->
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="staffList"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<div class="customerpilot" extra="customerpilot" id="staffPilot" dataset="staffList"></div>
										 <ui:button id="btn_confirm">确定</ui:button>
			      						 <ui:button id="btn_cancle">取消</ui:button>
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
			//menuCode = Global.getCurrentMenuCode(); //暂时不知道从哪里来得 以后修改  oaas/Staff.js
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
//查询Staff并且填充相应的表格对象
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
//组织树的点击时间,从树上获取orgId,并根据组织id到服务器端查询对应的人员
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
	this.scope="";//查询范围
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
