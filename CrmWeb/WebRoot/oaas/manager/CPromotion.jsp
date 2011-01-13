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
			    <ui:field field="lanId" visible="false" label="������ID"></ui:field>
				<ui:field field="lanName" visible="true" label="������" keyField="lanId" popup="true"></ui:field>
				<ui:field field="orgId" visible="false" label="������֯ID"></ui:field>
				<ui:field field="orgName" visible="true" label="��֯����" keyField="orgId" popup="true" visible="true"></ui:field>
				<ui:field field="promotionId" visible="false" label="��չ��ID"></ui:field>
				<ui:field field="promotionCode" visible="true" label="��չ�˱���"></ui:field>
				<ui:field field="promotionName" visible="true" label="��չ������" popup="true" keyField="partyRoleId"></ui:field>
				<ui:field field="partyRoleId" visible="false" label="���������˱�ʶ"></ui:field>
				<ui:field field="promotionType" visible="true" label="��չ������" attrCode="PROMOTION_TYPE" blankValue="true"></ui:field>
				<ui:field field="ifSysOper" visible="true" label="�Ƿ�ϵͳ����" attrCode="IF_SYS_OPER" blankValue="true"></ui:field>				
				<ui:field field="state" visible="true" label="״̬" attrCode="COMM_RECORD_STATE" blankValue="true"></ui:field>
				<ui:field field="teamName" visible="true" label="����"></ui:field>
				<ui:field field="postName" visible="true" label="��λ"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="CPromotionList" pageIndex="1" pageSize="16" loadDataAction="PartyService" loadDataActionMethod="queryCPromotionList" autoLoadPage="true" staticDataSource="true" loadAsNeeded="false" paginate="true">
				<ui:field field="lanId" visible="true" label="����������ID"></ui:field>
				<ui:field field="lanName" visible="true" label="����������"></ui:field>
				<ui:field field="orgId" visible="false" label="������֯ID"></ui:field>
				<ui:field field="orgName" visible="true" label="������֯����"></ui:field>
				<ui:field field="promotionId" visible="true" label="��չ��ID"></ui:field>
				<ui:field field="promotionType" visible="true" label="��չ������" attrCode="PROMOTION_TYPE" blankValue="true"></ui:field>
				<ui:field field="promotionName" visible="true" label="��չ������"></ui:field>
				<ui:field field="promotionCode" visible="true" label="��չ�˱���"></ui:field>
				<ui:field field="partyRoleId" visible="false" label="���������˱�ʶ"></ui:field>
				<ui:field field="ifSysOper" visible="true" label="�Ƿ�ϵͳ����" attrCode="IF_SYS_OPER" blankValue="true"></ui:field>
				<ui:field field="state" visible="true" label="״̬" attrCode="COMM_RECORD_STATE"></ui:field>
				<ui:field field="contactName" visible="true" label="��ϵ��"></ui:field>
				<ui:field field="contactPhone" visible="true" label="��ϵ�绰"></ui:field>
				<ui:field field="shortName" visible="true" label="��ƴ"></ui:field>
				<ui:field field="comments" visible="true" label="����"></ui:field>
				<ui:field field="operId" visible="true" label="¼����"></ui:field>
				<ui:field field="createDate" visible="true" label="¼������"></ui:field>
				<ui:field field="teamName" visible="true" label="����"></ui:field>
				<ui:field field="postName" visible="true" label="��λ"></ui:field>
				<ui:parameter parameterId="cond" type="object" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="CPromotionInfo" staticDataSource="true" readOnly="true">
			    <ui:field field="lanId" visible="false" label="����������ID"></ui:field>
				<ui:field field="lanName" visible="true" label="����������" popup="true" required="true" validType="require" validMsg="��ѡ������������!"></ui:field>
				<ui:field field="orgId" visible="false" label="������֯ID"></ui:field>
				<ui:field field="orgName" visible="true" label="������֯" popup="true" required="true" validType="require" validMsg="��ѡ��������֯!"></ui:field>
				<ui:field field="promotionId" visible="true" label="��չ��ID"></ui:field>
				<ui:field field="promotionType" visible="true" label="��չ������" required="true" validType="require" validMsg="��ѡ��չ������!" attrCode="PROMOTION_TYPE" blankValue="true"></ui:field>
				<ui:field field="promotionName" visible="true" label="��չ������" popup="true" required="true" validType="require" validMsg="��ѡ��������뷢չ������!"></ui:field>
				<ui:field field="promotionCode" visible="false" label="��չ�˱���"></ui:field>
				<ui:field field="partyRoleId" visible="false" label="���������˱�ʶ"></ui:field>
				<ui:field field="ifSysOper" visible="true" label="�Ƿ�ϵͳ����" attrCode="IF_SYS_OPER" blankValue="true"></ui:field>				
				<ui:field field="state" visible="true" label="״̬" attrCode="COMM_RECORD_STATE" blankValue="true"></ui:field>
				<ui:field field="contactName" visible="true" label="��ϵ��"></ui:field>
				<ui:field field="contactPhone" visible="true" label="��ϵ�绰"></ui:field>
				<ui:field field="shortName" visible="true" label="��ƴ"></ui:field>
				<ui:field field="comments" visible="true" label="����"></ui:field>
				<ui:field field="operId" visible="false" label="¼����"></ui:field>
				<ui:field field="createDate" visible="false" label="¼������"></ui:field>
				<ui:field field="teamName" visible="true" label="����"></ui:field>
				<ui:field field="postName" visible="true" label="��λ"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="Ds_PromotionDirec">
				<ui:field field="promotionId" label="" required="true" validType="require" validMsg="" visible="false"></ui:field>
				<ui:field field="promotionItem" label="��չ��" attrCode="PROMOTION_ITEM"></ui:field>
				<ui:field field="state" label="״̬" attrCode="COMM_RECORD_STATE"></ui:field>
			</ui:dataset>


		</div>

		<ui:layout type="border">
			<ui:pane position="left" style="width:200px">
				<ui:panel type="titleList" desc="��ѯ����">
					<ui:content>
						<ui:form submit="doQuery" dataset="queryInfo" inputLayout="50%" labelLayout="49%"></ui:form>
						<div align="center">
							<ui:button id="doQuery">��ѯ</ui:button>
							<ui:button id="doReset">����</ui:button>
						</div>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="center">
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:bar type="search" desc="��չ����Ϣ�б�">
							<ui:content>
								<ui:button id="addCPromotion">���</ui:button>
								<ui:button id="editCPromotion">�༭</ui:button>
								<ui:button id="deleteCPromotion">ɾ��</ui:button>
							</ui:content>
						</ui:bar>
						<ui:table dataset="CPromotionList" id="tableProm"></ui:table>
					</ui:pane>
					<ui:pane position="bottom" style="height:200px">
						<div class="customerpilot" extra="customerpilot" id="CPromotionListPilot" dataset="CPromotionList"></div>
						<ui:tabpane id="tabpaneProm" scroll="true">
							<ui:tabpage desc="��չ����ϸ��Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="CPromotionInfo" id="CPromotionInfoForm" inputLayout="20%" labelLayout="15%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btnSave">����</ui:button>
										<ui:button id="btnCancel">ȡ��</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="��չ�˷�չ����" id="promDirecTabpage" visible="false">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:table dataset="Ds_PromotionDirec" id='PromotionDirec_table' editable="true" readOnly="false"></ui:table>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="add_PromotionDirec">����</ui:button>
										<ui:button id="update_PromotionDirec">�޸�</ui:button>
										<ui:button id="delete_PromotionDirec">ɾ��</ui:button>
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
