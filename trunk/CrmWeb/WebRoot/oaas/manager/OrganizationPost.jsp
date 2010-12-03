<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/OrganizationPost.js"></script>
		<script>
		  function table_postList_onclick(){
		  		postTableClick();
		  }
		</script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- ��ѯFormʹ�õ�Dataset����-->
			<ui:dataset datasetId="queryInfo">
				<ui:field field="postName" label="��λ����"></ui:field>
				<ui:field field="state" label="״̬" dropDown="stateSelect"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="postInfo" staticDataSource="true" readOnly="true">
				<ui:field field="orgPostId" label="��֯��λ��ʶ" visible="false"></ui:field>
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" type="date" visible="false"></ui:field>
				<ui:field field="positionId" label="��λ����" dropDown="orgPositionDropdown" required="true" validType="require" validMsg="��ѡ���λ����!"></ui:field>
				<ui:field field="state" label="״̬" dropDown="stateSelect" required="true" validType="require" validMsg="��ѡ��״̬!"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="postList" loadDataAction="PartyService" loadDataActionMethod="getPositionListByPartyId" staticDataSource="true" async="false">
				<ui:field field="orgPostId" label="��֯��λ��ʶ" visible="false"></ui:field>
				<ui:field field="partyId" label="�����˱�ʶ" visible="false"></ui:field>
				<ui:field field="positionId" label="��λ��ʶ" visible="true"></ui:field>
				<ui:field field="positionName" label="��λ����" visible="true"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
				<ui:field field="stateDate" label="״̬ʱ��" visible="false"></ui:field>
			</ui:dataset>
			<!-- ��λ��ɫ�б�Dataset����-->
			<ui:dataset datasetId="postRolesList" loadDataAction="PartyService" loadDataActionMethod="getRolesByOrgPost" staticDataSource="true">
				<ui:field field="orgPostRoleId" label="��֯��λ��ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="roleId" label="��ɫ��ʶ" visible="false"></ui:field>
				<ui:field field="roleName" label="��ɫ����"></ui:field>
				<ui:field field="roleDesc" label="��ɫ����"></ui:field>
				<ui:field field="state" label="״̬" visible="false"></ui:field>
				<ui:field field="effDate" label="��Чʱ��" visible="true" visible="false"></ui:field>
				<ui:field field="abilityGradeNum" label="abilityGradeNum" visible="false"></ui:field>
				<ui:field field="regionId" label="�����ʶ" visible="false"></ui:field>
				<ui:field field="regionName" label="��������" visible="true"></ui:field>
				<ui:field field="regionType" label="��������" visible="true" dropDown="regionTypeSelect"></ui:field>
			</ui:dataset>
			<!-- Ա���б�Dataset����-->
			<ui:dataset datasetId="staffList" pageIndex="1" pageSize="12" readOnly="true" 
			loadDataAction="PartyService" loadDataActionMethod="getStaffListByPartyAndPost" autoLoadPage="true" 
			staticDataSource="true" loadAsNeeded="false" paginate="true" async="true">
				<ui:field field="orgPartyName" label="��֯����"></ui:field>
				<ui:field field="staffCode" label="Ա����"></ui:field>
				<ui:field field="staffDesc" label="Ա������"></ui:field>
				<ui:parameter parameterId="partyId" type="string"></ui:parameter>
				<ui:parameter parameterId="positionId" type="string"></ui:parameter>
			</ui:dataset>

		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="orgPositionDropdown" attrCode="DC_POST_TYPE" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="stateSelect" attrCode="COMM_RECORD_STATE" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="regionTypeSelect" attrCode="REGION_TYPE" staticDataSource="false"></ui:dropdown>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="��ѯ����">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="doQuery" dataset="queryInfo" labelLayout="22%" inputLayout="27%"></ui:form>
								</ui:pane>
								<ui:pane position="right" style="width:120px;">
									<ui:button id="doQuery">��ѯ</ui:button>
									<ui:button id="doQueryAll">ȫ��</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="left" style="width:220px">

					<ui:panel type="titleList" desc="��֯����">
						<ui:content>
							<!-- ��ʾ��֯��-->
							<ZTESOFT:treelist id="orgTreeView" class="treelist" onItemClick="clickOrganization()" showImage="false" showBorder="false" contBorder="false" height="400" showImage=false showHead="false">
								<ZTESOFT:columns>
									<ZTESOFT:column width="100%" display="true" displayText="��֯����" propertyName="orgName" />
									<ZTESOFT:column width="" display="false" displayText="��֯��ʶ" propertyName="partyId" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
						</ui:content>
					</ui:panel>

				</ui:pane>
				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="top">
							<ui:bar type="search" desc="��λ�б�">
								<ui:content>
									<ui:button id="addPost">���</ui:button>
									<ui:button id="editPost">�༭</ui:button>
									<ui:button id="deletePost">ɾ��</ui:button>
								</ui:content>
							</ui:bar>
						</ui:pane>
						<ui:pane position="center">
							<!-- ��ʾ��λ�б�-->
							<ui:table dataset="postList"></ui:table>
						</ui:pane>
						<ui:pane position="bottom" style="height:270px">
							<ui:tabpane id="postPage">
								<ui:tabpage desc="��λ��Ϣ">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:form submit="commitPost" dataset="postInfo" id="postInfoForm" labelLayout="16%" inputLayout="33%"></ui:form>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="commitPost">ȷ��</ui:button>
											<ui:button id="cancelCommit">ȡ��</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<ui:tabpage desc="��λ��ɫ">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="postRolesList"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<ui:button id="addRole">����</ui:button>
											<ui:button id="deleteRole">ɾ��</ui:button>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
								<ui:tabpage desc="��λԱ���б�">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="staffList"></ui:table>
										</ui:pane>
										<ui:pane position="bottom">
											<div class="customerpilot" extra="customerpilot" id="staffPilot" dataset="staffList"></div>
										</ui:pane>
									</ui:layout>
								</ui:tabpage>
							</ui:tabpane>
						</ui:pane>
					</ui:layout>
				</ui:pane>
			</ui:layout>
		</div>
	</body>

</html>
