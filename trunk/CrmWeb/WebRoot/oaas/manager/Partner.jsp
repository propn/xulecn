<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,validator,calendar">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<script language="javascript" src="js/Partner.js"></script>
		<ui:import library=""></ui:import>
	</head>
	<body>

		<div id="datasetDefine">
			<ui:dataset datasetId="queryInfo" loadDataAction="" loadDataActionMethod="" staticDataSource="true">
				<ui:field field="partnerCode" label="编码" visible="true"></ui:field>
				<ui:field field="partnerType" label="类型" blankValue="true" attrCode="PARTNER_TYPE" visible="true"></ui:field>
				<ui:field field="cooperateType" label="合作方式" blankValue="true" attrCode="COOPERATE_TYPE" visible="true"></ui:field>
				<ui:field field="partnerGrade" label="级别" blankValue="true" attrCode="PARTNER_GRADE" visible="true"></ui:field>
				<ui:field field="orgName" label="管理部门" popup="true" visible="true"></ui:field>
				<ui:field field="orgId" label="管理部门ID" visible="false"></ui:field>
				<ui:field field="staffName" label="负责人" popup="true" visible="true"></ui:field>
				<ui:field field="staffId" label="负责人ID" visible="false"></ui:field>
			</ui:dataset>

			<!-- 合作伙伴详细信息 -->
			<ui:dataset datasetId="partnerInfo" readOnly="true" staticDataSource="true">
				<ui:field field="partnerId" label="合作伙伴标识" visible="false"></ui:field>
				<ui:field field="partyRoleId" label="参与人角色" visible="true"></ui:field>
				<ui:field field="partnerCode" label="合作伙伴编码" required="true" validType="require" validMsg="请输入合作伙伴编码" visible="true"></ui:field>
				<ui:field field="partnerType" label="合作伙伴类型" required="true" validType="require" validMsg="请选择合作伙伴类型" attrCode="PARTNER_TYPE" blankValue="true" visible="true"></ui:field>
				<ui:field field="partnerDesc" label="合作伙伴描述" required="true" validType="require" validMsg="请输入合作伙伴描述" visible="true"></ui:field>
				<ui:field field="corpAgent" label="法人代表" visible="true"></ui:field>
				<ui:field field="addr" label="地址信息" visible="true"></ui:field>
				<ui:field field="applyDate" label="申请日期" type="date" visible="true"></ui:field>
				<ui:field field="linkman" label="联系人" visible="true"></ui:field>
				<ui:field field="corporateLicenceNo" label="营业执照" visible="true"></ui:field>
				<ui:field field="spcpLicenceNo" label="信息服务营业执照" visible="true"></ui:field>
				<ui:field field="bankPermit" label="银行开户许可证" visible="true"></ui:field>
				<ui:field field="companySize" label="企业规模" attrCode="COMPANY_SIZE" blankValue="true" visible="true"></ui:field>
				<ui:field field="companyBalns" label="资金规模" attrCode="COMPANY_BALANCE" blankValue="true" visible="true"></ui:field>
				<ui:field field="cooperateType" label="合作方式" attrCode="COOPERATE_TYPE" blankValue="true" visible="true"></ui:field>
				<ui:field field="devPlan" label="发展计划" visible="true"></ui:field>
				<ui:field field="partnerGrade" label="级别" attrCode="PARTNER_GRADE" visible="true"></ui:field>
				<ui:field field="state" label="状态" required="true" attrCode="PARTNER_STATE" blankValue="true" visible="true" validType="require" validMsg="请选择状态"></ui:field>
				<ui:field field="orgId" label="管理部门ID" visible="false"></ui:field>
				<ui:field field="orgName" label="管理部门" popup="true" required="true" visible="true" validType="require" validMsg="请选择管理部门"></ui:field>
				<ui:field field="staffId" label="负责人ID" visible="false"></ui:field>
				<ui:field field="staffName" label="负责人" popup="true" required="true" validType="require" validMsg="请选择负责人" visible="true"></ui:field>
				<ui:field field="superPartnerId" label="上级合作伙伴" visible="false"></ui:field>
				<ui:field field="pathCode" label="path code" visible="false"></ui:field>
			</ui:dataset>

			<!-- 合作伙伴合同列表 -->
			<ui:dataset datasetId="conferList" loadDataAction="PartnerService" loadDataActionMethod="queryConferByPartnerId" staticDataSource="true" async="false">
				<ui:field field="partnerConfId" label="合同标识" visible="false"></ui:field>
				<ui:field field="partnerId" label="合作伙伴标识" visible="false"></ui:field>
				<ui:field field="conferCode" label="合同编号" visible="true"></ui:field>
				<ui:field field="partnerConferType" label="合同类型" attrCode="PARTNER_CONFER_TYPE" visible="true"></ui:field>
				<ui:field field="signDate" label="签订日期" visible="false"></ui:field>
				<ui:field field="expDate" label="有效期" visible="false"></ui:field>
				<ui:field field="cooperateType" label="合作方式" visible="false"></ui:field>
				<ui:field field="conferContence" label="合作内容" visible="false"></ui:field>
				<ui:field field="balnsRule" label="结算规则" visible="false"></ui:field>
				<ui:field field="partnerDroitDuty" label="合作伙伴权利义务" visible="false"></ui:field>
				<ui:field field="droitDuty" label="运用商权利义务" visible="false"></ui:field>
				<ui:field field="abortCondiction" label="合同终止条件" visible="false"></ui:field>
				<ui:field field="breakDuty" label="违约责任" visible="false"></ui:field>
				<ui:field field="state" label="状态" visible="false"></ui:field>
			</ui:dataset>

			<!-- 合作伙伴详细信息 -->
			<ui:dataset datasetId="conferInfo" readOnly="true" staticDataSource="true">
				<ui:field field="partnerConfId" label="合同标识" visible="false"></ui:field>
				<ui:field field="partnerId" label="合作伙伴标识" visible="false"></ui:field>
				<ui:field field="conferCode" label="合同编号" required="true" validType="require" validMsg="请输入合同编号!" visible="true"></ui:field>
				<ui:field field="partnerConferType" label="合同类型" required="true" validType="require" validMsg="请选择合同类型!" attrCode="PARTNER_CONFER_TYPE" visible="true"></ui:field>
				<ui:field field="signDate" label="签订日期" required="true" validType="require" validMsg="请选择签订日期!" type="date" visible="true"></ui:field>
				<ui:field field="expDate" label="有效期" required="true" validType="require" validMsg="请选择签有效期!" type="date" visible="true"></ui:field>
				<ui:field field="cooperateType" label="合作方式" attrCode="COOPERATE_TYPE" visible="true"></ui:field>
				<ui:field field="conferContence" label="合作内容" visible="true"></ui:field>
				<ui:field field="balnsRule" label="结算规则" visible="true"></ui:field>
				<ui:field field="partnerDroitDuty" label="合作伙伴权利义务" visible="true"></ui:field>
				<ui:field field="droitDuty" label="运用商权利义务" visible="true"></ui:field>
				<ui:field field="abortCondiction" label="合同终止条件" visible="true"></ui:field>
				<ui:field field="breakDuty" label="违约责任" visible="true"></ui:field>
				<ui:field field="state" label="状态" attrCode="PARTNER_CONF_STATE" visible="true"></ui:field>
			</ui:dataset>

			<!--部门关系列表 -->
			<ui:dataset datasetId="departRelatList" loadDataAction="PartnerService" loadDataActionMethod="queryDepartRelatByPartnerId" staticDataSource="true" async="false">
				<ui:field field="partyId" label="部门标识" visible="true"></ui:field>
				<ui:field field="partyCode" label="部门编码" visible="true"></ui:field>
				<ui:field field="partyName" label="部门名称" visible="true"></ui:field>
				<ui:field field="partnerId" label="合作伙伴标识" visible="false"></ui:field>
			</ui:dataset>
			<!-- 部门关系详细信息-->
			<ui:dataset datasetId="departRelatInfo" readOnly="true" staticDataSource="true">
				<ui:field field="partyId" label="部门标识" visible="true"></ui:field>
				<ui:field field="partyCode" label="部门编码" visible="true"></ui:field>
				<ui:field field="partyName" label="部门名称" visible="true"></ui:field>
				<ui:field field="partnerId" label="合作伙伴标识" visible="false"></ui:field>
			</ui:dataset>

		</div>

		<ui:layout type="border">
			<ui:pane position="top" style="height:80px">
				<ui:panel type="titleList" desc="查询条件">
					<ui:content>
						<ui:layout type="border">
							<ui:pane position="center">
								<ui:form submit="queryPartner" id="queryForm" dataset="queryInfo" labelLayout="13%" inputLayout="20%"></ui:form>
							</ui:pane>
							<ui:pane position="right" style="text-align:center">
								<ui:button id="queryPartner">查询</ui:button>
								<ui:button id="queryReset">重置</ui:button>
							</ui:pane>
						</ui:layout>
					</ui:content>
				</ui:panel>
			</ui:pane>

			<ui:pane position="center">
				<ui:layout type="border">
					<ui:pane position="center">

						<ui:layout type="border">
							<ui:pane position="top" style="height:25px">
								<ui:bar type="search" desc="合作伙伴列表">
									<ui:content>
										<ui:button id="addRootPartner">添加一级合作伙伴</ui:button>
										<ui:button id="addChildPartner">添加下级合作伙伴</ui:button>
										<ui:button id="editPartner">修改</ui:button>
									</ui:content>
								</ui:bar>
							</ui:pane>
							<ui:pane position="center">
								<ZTESOFT:treelist id="partnerTree" class="treelist" onItemClick="clickPartner()" showImage="false" showBorder="false" contBorder="true" height="185" showImage=false showHead="true">
									<ZTESOFT:columns>
										<ZTESOFT:column width="30%" display="true" displayText="合作伙伴编码" propertyName="partnerCode" />
										<ZTESOFT:column width="30%" display="true" displayText="管理部门" propertyName="orgName" />
										<ZTESOFT:column width="20%" display="true" displayText="负责人" propertyName="staffName" />
										<ZTESOFT:column width="10%" display="true" displayText="合作方式" propertyName="cooperateType" />
										<ZTESOFT:column width="10%" display="true" displayText="合作伙伴级别" propertyName="partnerGrade" />
										<ZTESOFT:column width="" display="false" displayText="合作伙伴类型" propertyName="partnerType" />
										
										<ZTESOFT:column width="" display="false" displayText="管理部门Id" propertyName="orgId" />
										<ZTESOFT:column width="" display="false" displayText="负责人Id" propertyName="staffId" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="partnerId" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="partyRoleId" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="partnerDesc" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="corpAgent" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="addr" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="applyDate" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="linkman" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="corporateLicenceNo" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="spcpLicenceNo" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="bankPermit" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="companySize" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="companyBalns" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="devPlan" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="state" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="superPartnerId" />
										<ZTESOFT:column width="" display="false" displayText="" propertyName="pathCode" />
									</ZTESOFT:columns>
								</ZTESOFT:treelist>
							</ui:pane>
						</ui:layout>
					</ui:pane>

					<ui:pane position="bottom" style="height:320px">
						<ui:tabpane id="mainPage">
							<ui:tabpage desc="详细信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="savePartner" id="partnerInfoForm" dataset="partnerInfo" labelLayout="19%" inputLayout="30%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom" style="height:20px">
										<ui:button id="savePartner">保存</ui:button>
										<ui:button id="cancelPartner">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="合同信息">
								<ui:layout type="border">
									<ui:pane position="left" style="width:220px">
										<ui:layout type="border">
											<ui:pane position="top" style="height:20px">
												<ui:bar type="search" desc="">
													<ui:content>
														<ui:button id="insertConferInfo">新增</ui:button>
														<ui:button id="editConferInfo">修改</ui:button>
													</ui:content>
												</ui:bar>
											</ui:pane>
											<ui:pane position="center">
												<ui:table dataset="conferList"></ui:table>
											</ui:pane>
										</ui:layout>
									</ui:pane>
									<ui:pane position="center">
										<ui:layout type="border">
											<ui:pane position="center">
												<ui:panel type="titleList" desc="合同详细信息">
													<ui:content>
														<ui:form submit="saveConfer" id="conferInfoForm" labelLayout="24%" inputLayout="25%" dataset="conferInfo"></ui:form>
													</ui:content>
												</ui:panel>
											</ui:pane>
											<ui:pane position="bottom" style="height:20px">
												<ui:button id="saveConfer">保存</ui:button>
												<ui:button id="cancelConfer">取消</ui:button>
											</ui:pane>
										</ui:layout>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="关联部门">
								<ui:layout type="border">
									<ui:pane position="top" style="height:20px">
										<ui:bar type="search" desc="关联部门">
											<ui:content>
												<ui:button id="insertDepartRelat">新增</ui:button>
												<ui:button id="deleteDepartRelat">删除</ui:button>
											</ui:content>
										</ui:bar>
									</ui:pane>
									<ui:pane position="center">
										<ui:table dataset="departRelatList"></ui:table>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</ui:pane>
				</ui:layout>
			</ui:pane>
		</ui:layout>
	</body>
</html>
