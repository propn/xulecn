<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/CPromotion.js"></script>
	</head>
	<body>
		<div id="datasetDefine">

			<ui:dataset datasetId="queryInfo" staticDataSource="true">
			    <ui:field field="lanId" visible="false" label="本地网ID"></ui:field>
				<ui:field field="lanName" visible="true" label="本地网" keyField="lanId" popup="true"></ui:field>
				<ui:field field="orgId" visible="false" label="所属组织ID"></ui:field>
				<ui:field field="orgName" visible="true" label="组织名称" keyField="orgId" popup="true" visible="true"></ui:field>
				<ui:field field="promotionId" visible="false" label="发展人ID"></ui:field>
				<ui:field field="promotionCode" visible="true" label="发展人编码"></ui:field>
				<ui:field field="promotionName" visible="true" label="发展人姓名" popup="true" keyField="partyRoleId"></ui:field>
				<ui:field field="partyRoleId" visible="false" label="关联参与人标识"></ui:field>
				<ui:field field="promotionType" visible="true" label="发展人类型" attrCode="PROMOTION_TYPE" blankValue="true"></ui:field>
				<ui:field field="ifSysOper" visible="true" label="是否系统工号" attrCode="IF_SYS_OPER" blankValue="true"></ui:field>				
				<ui:field field="state" visible="true" label="状态" attrCode="COMM_RECORD_STATE" blankValue="true"></ui:field>
				<ui:field field="teamName" visible="true" label="班组"></ui:field>
				<ui:field field="postName" visible="true" label="岗位"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="CPromotionList" pageIndex="1" pageSize="16" loadDataAction="PartyService" loadDataActionMethod="queryCPromotionList" autoLoadPage="true" staticDataSource="true" loadAsNeeded="false" paginate="true">
				<ui:field field="lanId" visible="true" label="所属本地网ID"></ui:field>
				<ui:field field="lanName" visible="true" label="所属本地网"></ui:field>
				<ui:field field="orgId" visible="false" label="所属组织ID"></ui:field>
				<ui:field field="orgName" visible="true" label="所属组织名称"></ui:field>
				<ui:field field="promotionId" visible="true" label="发展人ID"></ui:field>
				<ui:field field="promotionType" visible="true" label="发展人类型" attrCode="PROMOTION_TYPE" blankValue="true"></ui:field>
				<ui:field field="promotionName" visible="true" label="发展人姓名"></ui:field>
				<ui:field field="promotionCode" visible="true" label="发展人编码"></ui:field>
				<ui:field field="partyRoleId" visible="false" label="关联参与人标识"></ui:field>
				<ui:field field="ifSysOper" visible="true" label="是否系统工号" attrCode="IF_SYS_OPER" blankValue="true"></ui:field>
				<ui:field field="state" visible="true" label="状态" attrCode="COMM_RECORD_STATE"></ui:field>
				<ui:field field="contactName" visible="true" label="联系人"></ui:field>
				<ui:field field="contactPhone" visible="true" label="联系电话"></ui:field>
				<ui:field field="shortName" visible="true" label="简拼"></ui:field>
				<ui:field field="comments" visible="true" label="描述"></ui:field>
				<ui:field field="operId" visible="true" label="录入人"></ui:field>
				<ui:field field="createDate" visible="true" label="录入日期"></ui:field>
				<ui:field field="teamName" visible="true" label="班组"></ui:field>
				<ui:field field="postName" visible="true" label="岗位"></ui:field>
				<ui:parameter parameterId="cond" type="object" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="CPromotionInfo" staticDataSource="true" readOnly="true">
			    <ui:field field="lanId" visible="false" label="所属本地网ID"></ui:field>
				<ui:field field="lanName" visible="true" label="所属本地网" popup="true" required="true" validType="require" validMsg="请选择所属本地网!"></ui:field>
				<ui:field field="orgId" visible="false" label="所属组织ID"></ui:field>
				<ui:field field="orgName" visible="true" label="所属组织" popup="true" required="true" validType="require" validMsg="请选择所属组织!"></ui:field>
				<ui:field field="promotionId" visible="true" label="发展人ID"></ui:field>
				<ui:field field="promotionType" visible="true" label="发展人类型" required="true" validType="require" validMsg="请选择发展人类型!" attrCode="PROMOTION_TYPE" blankValue="true"></ui:field>
				<ui:field field="promotionName" visible="true" label="发展人姓名" popup="true" required="true" validType="require" validMsg="请选择或者输入发展人姓名!"></ui:field>
				<ui:field field="promotionCode" visible="false" label="发展人编码"></ui:field>
				<ui:field field="partyRoleId" visible="false" label="关联参与人标识"></ui:field>
				<ui:field field="ifSysOper" visible="true" label="是否系统工号" attrCode="IF_SYS_OPER" blankValue="true"></ui:field>				
				<ui:field field="state" visible="true" label="状态" attrCode="COMM_RECORD_STATE" blankValue="true"></ui:field>
				<ui:field field="contactName" visible="true" label="联系人"></ui:field>
				<ui:field field="contactPhone" visible="true" label="联系电话"></ui:field>
				<ui:field field="shortName" visible="true" label="简拼"></ui:field>
				<ui:field field="comments" visible="true" label="描述"></ui:field>
				<ui:field field="operId" visible="false" label="录入人"></ui:field>
				<ui:field field="createDate" visible="false" label="录入日期"></ui:field>
				<ui:field field="teamName" visible="true" label="班组"></ui:field>
				<ui:field field="postName" visible="true" label="岗位"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="Ds_PromotionDirec">
				<ui:field field="promotionId" label="" required="true" validType="require" validMsg="" visible="false"></ui:field>
				<ui:field field="promotionItem" label="发展项" attrCode="PROMOTION_ITEM"></ui:field>
				<ui:field field="state" label="状态" attrCode="COMM_RECORD_STATE"></ui:field>
			</ui:dataset>


		</div>

		<ui:layout type="border">
			<ui:pane position="left" style="width:200px">
				<ui:panel type="titleList" desc="查询条件">
					<ui:content>
						<ui:form submit="doQuery" dataset="queryInfo" inputLayout="50%" labelLayout="49%"></ui:form>
						<div align="center">
							<ui:button id="doQuery">查询</ui:button>
							<ui:button id="doReset">重置</ui:button>
						</div>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="center">
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:bar type="search" desc="发展人信息列表">
							<ui:content>
								<ui:button id="addCPromotion">添加</ui:button>
								<ui:button id="editCPromotion">编辑</ui:button>
								<ui:button id="deleteCPromotion">删除</ui:button>
							</ui:content>
						</ui:bar>
						<ui:table dataset="CPromotionList" id="tableProm"></ui:table>
					</ui:pane>
					<ui:pane position="bottom" style="height:200px">
						<div class="customerpilot" extra="customerpilot" id="CPromotionListPilot" dataset="CPromotionList"></div>
						<ui:tabpane id="tabpaneProm" scroll="true">
							<ui:tabpage desc="发展人详细信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="CPromotionInfo" id="CPromotionInfoForm" inputLayout="20%" labelLayout="15%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btnSave">保存</ui:button>
										<ui:button id="btnCancel">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="发展人发展方向" id="promDirecTabpage" visible="false">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:table dataset="Ds_PromotionDirec" id='PromotionDirec_table' editable="true" readOnly="false"></ui:table>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="add_PromotionDirec">增加</ui:button>
										<ui:button id="update_PromotionDirec">修改</ui:button>
										<ui:button id="delete_PromotionDirec">删除</ui:button>
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
