<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/Organization.js"></script>
		<script>
		function table_logicalAddrList_onclick(){
			clickLogicalAddressList();
		}
		</script>
	</head>
	<body>
		<ui:permission>
		</ui:permission>
		
		<div id="datasetDefine">
			<ui:dataset datasetId="departmentInfo" staticDataSource="true" readOnly="true">
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				<ui:field field="departType" label="部门类型" attrCode="DEPART_TYPE" required="true" validType="require" validMsg="请选择部门类型!"></ui:field>
				<ui:field field="termFlag" label="班次标识" visible="false"></ui:field>
				<ui:field field="paySeatType" label="收款席位类型" blankValue="true" attrCode="PAY_SEAT_TYPE" ></ui:field>
				<ui:field field="regionId" label="营业区编号" visible="false"></ui:field>
				<ui:field field="regionName" label="营业区" popup="true" required="true" validType="require" validMsg="请选择营业区!"></ui:field>
				<ui:field field="superPartyId" label="上级部门编号" visible="false"></ui:field>
				<ui:field field="superPartyName" label="上级部门" popup="true" required="true" validType="require" validMsg="请选择上级部门!"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="logicalAddrList" loadDataAction="PartyService" loadDataActionMethod="getLogicalAddrsByPartyId" async="false" staticDataSource="true">
				<ui:field field="logicalAddrId" label="逻辑地址标识"></ui:field>
				<ui:field field="addrId" label="地址标识" visible="false"></ui:field>
				<ui:field field="logicalAddrType" label="逻辑地址类型" dropDown="logicalAddrTypeDropdown"></ui:field>
				<ui:field field="logicalAddrDeta" label="逻辑地址详细信息"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="logicalAddrInfo" staticDataSource="true" readOnly="true">
				<ui:field field="logicalAddrId" label="逻辑地址标识" visible="false"></ui:field>
				<ui:field field="addrId" label="地址标识" visible="false"></ui:field>
				<ui:field field="logicalAddrType" label="逻辑地址类型" dropDown="logicalAddrTypeDropdown"></ui:field>
				<ui:field field="logicalAddrDeta" label="逻辑地址详细信息" required="true"></ui:field>
			</ui:dataset>
			<!-- 组织常规信息Dataset-->
			<ui:dataset datasetId="commonInfor" readOnly="true"> 
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				<ui:field field="parentPartyId" label="上级组织ID" visible="false"></ui:field>
				<ui:field field="parentPartyName" label="上级组织"></ui:field>
				<ui:field field="orgCode" label="组织编码" required="true" validType="require" validMsg="请填写组织编码!"></ui:field>
				<ui:field field="orgName" label="组织名称" required="true" validType="require" validMsg="请填写组织名称!"></ui:field>
				<ui:field field="orgLevel" label="组织级别" size="5"></ui:field>
				<ui:field field="orgTypeId" label="组织类型" required="true" validType="require" validMsg="请选择组织类型!" dropDown="orgTypeDropdown"></ui:field>
				<ui:field field="orgContent" label="组织简介"></ui:field>
				<ui:field field="state" label="状态" required="true" validType="require" validMsg="请选择状态!" dropDown="stateSelect"></ui:field>
				<ui:field field="addrId" label="地址标识" visible="false"></ui:field>
				<ui:field field="stateDate" label="状态时间" type="date" visible="false"></ui:field>
				<ui:field field="effDate" label="生效时间" type="date" required="true" validType="require" validMsg="请填写生效时间!"></ui:field>
				<ui:field field="orgClass" label="组织分类" visible="true" attrCode="ORG_CLASS_NEW"></ui:field>
				<ui:field field="orgType" label="计费组织类型" visible="true" attrCode="BSN_ORG_TYPE"></ui:field>
			</ui:dataset>
			<!-- 联系信息Dataset-->
			<ui:dataset datasetId="contactInfor" readOnly="true">
				<ui:field field="addrId" label="地址标识" visible="false"></ui:field>
				<ui:field field="partyId" label="参与人标识" visible="false"></ui:field>
				<ui:field field="provinceName" label="省名" required="true" validType="require" validMsg="请填写省名!"></ui:field>
				<ui:field field="cityName" label="城市名" required="true" validType="require" validMsg="请填写城市名!"></ui:field>
				<ui:field field="countyName" label="县区" required="true" validType="require" validMsg="请填写县区!"></ui:field>
				<ui:field field="streetName" label="街道名" required="true" validType="require" validMsg="请填写街道名!"></ui:field>
				<ui:field field="streetNbr" label="门牌号" required="true" validType="require" validMsg="请填写门牌号!"></ui:field>
				<ui:field field="deta" label="地址详细信息" required="true" validType="require" validMsg="请填写详细信息!"></ui:field>
				<ui:field field="postcode" label="邮政编码" required="true" size="6" validType="number" validMsg="请填写数字类型的邮政编码!"></ui:field>
				<ui:field field="alias" label="地址别名"></ui:field>
				<ui:field field="addDescription" label="地址描述"></ui:field>
				<ui:field field="bankBranchId" label="银行分行" attrCode=""></ui:field>
				<ui:field field="bankAccNo" label="银行帐号" size="30" validType="number" validMsg="银行帐号必须为数字类型!"></ui:field>
				<ui:field field="incrTaxNo" label="增值税号" size="30"></ui:field>												
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="logicalAddrTypeDropdown" attrCode="LOGICAL_ADDR_TYPE" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="isOrgManagerSelect" attrCode="IS_ORG_MANAGER" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="stateSelect" attrCode="COMM_RECORD_STATE" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="orgTypeDropdown" attrCode="DC_ORG_TYPE" staticDataSource="false"></ui:dropdown>

		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:325px">
					<ui:layout type="border">
						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="top" style="height:30px">
							<ui:bar type="search" desc="电信组织管理">
								<ui:content>
									<ui:button id="addRootOrganization">添加根组织</ui:button>
									<ui:button id="addSubOrganization">添加子组织</ui:button>
									<ui:button id="editOrganization">编辑</ui:button>
									<ui:button id="deleteOrganization">删除</ui:button>
								</ui:content>
							</ui:bar>
								</ui:pane>
								<ui:pane position="center">
							<ZTESOFT:treelist id="organizationTreeView" class="treelist" showCheck="true" onItemClick="clickOrganization()" showImage="false" showBorder="true" contBorder="true" showImage="false" showHead="true">
								<ZTESOFT:columns>
									<ZTESOFT:column width="40%" display="true" displayText="组织名称" propertyName="orgName" />
									<ZTESOFT:column width="20%" display="true" displayText="组织编码" propertyName="orgCode" />
									<ZTESOFT:column width="30%" display="true" displayText="组织简介" propertyName="orgContent" />
									<ZTESOFT:column width="10%" display="true" displayText="组织类型" propertyName="orgTypeName" />									
									<ZTESOFT:column width="" display="false" displayText="参与人标识" propertyName="partyId" />
									<ZTESOFT:column width="" display="false" displayText="上级组织" propertyName="parentPartyId" />
									<ZTESOFT:column width="" display="false" displayText="组织级别" propertyName="orgLevel" />
									<ZTESOFT:column width="" display="false" displayText="组织类型标识" propertyName="orgTypeId" />
									<ZTESOFT:column width="" display="false" displayText="地址标识" propertyName="addressId" />
									<ZTESOFT:column width="" display="false" displayText="状态" propertyName="state" />
									<ZTESOFT:column width="" display="false" displayText="状态时间" propertyName="stateDate" />
									<ZTESOFT:column width="" display="false" displayText="组织分类" propertyName="orgClass" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
								</ui:pane>
							</ui:layout>
						</ui:pane>
					</ui:layout>
				</ui:pane>
				<ui:pane position="center">
					<!-- 详细信息面板，显示多个Tab Page控件-->
					<ui:tabpane id="mainPage">
						<ui:tabpage desc="常规">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="inputNext" dataset="commonInfor" id="commonInforForm" labelLayout="15%" inputLayout="34%"></ui:form>
									<div id="divDepartmentInfo" style="display:none">
										<ui:panel type="titleList" desc="部门信息" style="height:85px">
											<ui:content>
												<ui:form submit="inputNext" dataset="departmentInfo" id="departmentInfoForm" labelLayout="15%" inputLayout="34%"></ui:form>
											</ui:content>
										</ui:panel>
									</div>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="inputNext">下一步</ui:button>
									<ui:button id="cancelCommit">取消</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
						<ui:tabpage desc="详细信息">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="commitOrganization"  dataset="contactInfor" id="contactInforForm" labelLayout="15%" inputLayout="34%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="commitOrganization">确定</ui:button>
									<ui:button id="cancelCommit1">取消</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
						<ui:tabpage desc="扩展信息">
							<ui:layout type="border">
								<ui:pane position="top">
									<ui:bar type="search" desc="扩展信息">
										<ui:content>
											<ui:button id="addLogicAddr">增加</ui:button>
											<ui:button id="editLogicAddr">编辑</ui:button>
											<ui:button id="deleteLogicAddr">删除</ui:button>
										</ui:content>
									</ui:bar>
								</ui:pane>
								<ui:pane position="center">
									<ui:table dataset="logicalAddrList"></ui:table>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:form submit="commitLogicAddr" dataset="logicalAddrInfo" labelLayout="15%" inputLayout="34%"></ui:form>
									<ui:button id="commitLogicAddr">确定</ui:button>
									<ui:button id="cancelLogicAddr">取消</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
