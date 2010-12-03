<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/OrgStaffSelect.js"></script>
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
			<ui:dataset datasetId="staffList" pageIndex="1" pageSize="20" readOnly="false" editable="false" 
				loadDataAction="PartyService" loadDataActionMethod="getStaffsByStaffCondPaginate" autoLoadPage="true" 
				staticDataSource="true" loadAsNeeded="false" paginate="true" async="false">
				<ui:field field="select" label="" visible="true"></ui:field>
				<ui:field field="orgPartyId" label="��֯��ʶ" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="��֯����"></ui:field>
				<ui:field field="partyRoleId" label="�����˽�ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="partyName" label="Ա������"></ui:field>
				<ui:field field="staffCode" label="Ա����"></ui:field>
				<ui:field field="staffDesc" label="Ա������"></ui:field>
				<ui:field field="officeId" label="�칫�ص�ID" visible="false"></ui:field>
				<ui:field field="officeName" label="�칫�ص�" visible="false"></ui:field>				
				<ui:field field="password" label="����" visible="false"></ui:field>
				<ui:field field="pwdvalidtype" label="������Ч������" visible="false"></ui:field>
				<ui:field field="limitCount" label="��¼��������" visible="false"></ui:field>
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				<ui:field field="orgPartyId" label="������֯" visible="false"></ui:field>
				<ui:field field="orgPartyName" label="������֯" visible="false"></ui:field>
				<ui:field field="channelSegmentId" label="����ID" visible="false"></ui:field>
				<ui:field field="partyRoleType" label="�����˽�ɫ����" visible="false"></ui:field>
				<ui:field field="orgManager" label="�Ƿ���֯������" visible="false"></ui:field>
				<ui:field field="createDate" label="��������" visible="false"></ui:field>
				<ui:field field="expDate" label="ʧЧ����" visible="false"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" visible="false"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" visible="false"></ui:field>
				<!-- ���˱� ��Ϣά��-->
				<ui:field field="gender" label="�Ա�" visible="false"></ui:field>
				<ui:field field="birthPlace" label="����" visible="false"></ui:field>
				<ui:field field="birthDate" label="����" visible="false"></ui:field>
				<ui:field field="maritalStatus" label="���" visible="false"></ui:field>
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
		</div>
		
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="left" style="width:200px">
					<ui:panel type="titleList" desc="��֯����">
						<ui:content>
							<ZTESOFT:treelist id="orgTreeView" class="treelist" onItemClick="clickOrganization()" showImage="false" showBorder="false" contBorder="false" height="400" showImage=false showHead="false">
								<ZTESOFT:columns>
									<ZTESOFT:column width="100%" display="true" displayText="��֯����" propertyName="orgName" />
									<ZTESOFT:column width="" display="false" displayText="��֯���ͱ�ʶ" propertyName="orgTypeId" />											
									<ZTESOFT:column width="" display="false" displayText="��֯��ʶ" propertyName="partyId" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="top" style="height:70px">
							<!-- ��ʾ��ѯ����-->
							<ui:panel type="titleList" desc="��ѯ����">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:form dataset="queryInfo" inputLayout="20%" labelLayout="13%"></ui:form>
										</ui:pane>
										<ui:pane position="right">
											<ui:button id="doQuery">��ѯ</ui:button>
											<ui:button id="doReset">����</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:content>
							</ui:panel>
						</ui:pane>
						<ui:pane position="center">
							<!-- ��ʾ��Ա�б�-->
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:table dataset="staffList" skipRebuild="false" readOnly="true" showHeader="true" showIndicator="true" highlightSelection="true" maxRow="1000"></ui:table>
								</ui:pane>
								<ui:pane position="bottom">
									<div class="customerpilot" extra="customerpilot" id="staffPilot" dataset="staffList"></div>
								</ui:pane>
							</ui:layout>
						</ui:pane>
						<ui:pane position="bottom">
							<ui:button id="confirm">ȷ��</ui:button>
							<ui:button id="cancel">ȡ��</ui:button>
						</ui:pane>
					</ui:layout>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
