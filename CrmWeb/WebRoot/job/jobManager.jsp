<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>

<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator,tree,treeList">
		<title></title>
		<script language="javascript"
			src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<script language="javascript" src="js/action.js"></script>
		<ui:import library=""></ui:import>
	</head>
	<body>
		<!-- ����������ʾ���ݼ�-->
		<ui:dataset datasetId="ds_job_config" pageIndex="1" pageSize="23" readOnly="false" editable="false" loadDataAction="JobService" loadDataActionMethod="getJobPageModal" autoLoadPage="false" async="true" staticDataSource="true" loadAsNeeded="true"
			paginate="true">
			<!-- ���ݲ���-->
			<ui:parameter parameterId="jobtype" type="string"></ui:parameter>
			<ui:parameter parameterId="endBeginTime" type="string"></ui:parameter>
			<ui:parameter parameterId="endEndTime" type="string"></ui:parameter>
			<ui:parameter parameterId="jobState" type="string"></ui:parameter>


			<ui:field field="select" label="ȫѡ"></ui:field>
			<ui:field field="jobId" label="�����־" visible="false"></ui:field>
			<ui:field field="jobName" label="��������"></ui:field>
			<ui:field field="jobType" label="��������" dropDown="ds_jobType"></ui:field>
			<ui:field field="jobState" label="����״̬" dropDown="ds_jobState"></ui:field>
			<ui:field field="jobStartDay" label="��Ч����"></ui:field>
			<ui:field field="jobTerminateDay" label="ʧЧ����"></ui:field>
			<ui:field field="jobGrpName" label="��������"></ui:field>
			<ui:field field="jobClassName" label="��������" visible="false"></ui:field>
			<ui:field field="jobInterval" label="ִ��ʱ����" visible="false"></ui:field>
			<ui:field field="jobMethod" label="���ȷ�ʽ" visible="false"></ui:field>
			<ui:field field="jobRule" label="���ȹ���" visible="false"></ui:field>
			<ui:field field="jobRuntime" label="��ʱִ��ʱ��" visible="false"></ui:field>
			<ui:field field="jobDesc" label="��������" ></ui:field>
			<ui:field field="jobArgs" label="����"   visible="false"></ui:field>
			<ui:field field="jobStartRun" label="����"   visible="false"></ui:field>
			<ui:field field="jobClustored" label="�Ƿ�Ⱥ��"   visible="false"></ui:field>
		</ui:dataset>

		<!--��Χϵͳ���ݼ�-->
		<code id="outerSystem" attrCode="SYSTEM_ID" type="list" mapValue="true" blankValue="ȫ��" blankId="-1" staticDataSource="false"></code>
		<ui:dropdown id="dropdownProdClass" attrCode="PRODUCT_CLASS" type="list" mapValue="true" staticDataSource="false" maxHeight="120"></ui:dropdown>
		<xml id="__ds_jobType">
		<items>
		<item label="ȫ��" value="-1"></item>
		<item label="������" value="0"></item>
		<item label="��������" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobType" attrCode="dc_jobType"></code>
		<xml id="__ds_jobState">
		<items>
		<item label="ȫ��" value="-1"></item>
		<item label="����" value="0"></item>
		<item label="����" value="1"></item>
		<item label="ʧЧ" value="2"></item>
		</items>
		</xml>
		
		<code id="ds_jobState" attrCode="dc_jobState"></code>
		<!--�����ѯ���ݼ�-->
		<ui:dataset datasetId="queryCond" readOnly="false">
		\
			<ui:field field="jobtype" label="��������" dropDown="ds_jobType"></ui:field>
			<ui:field field="endBeginTime" label="����ʼ��" type="date"></ui:field>
			<ui:field field="endEndTime" label="����ֹ��" type="date"></ui:field>
			<ui:field field="jobState" label="����״̬" dropDown="ds_jobState"></ui:field>
		</ui:dataset>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="left" style="width:220px">
					<ui:panel type="titleList" desc="��ʱ�����ѯ����">
						<ui:content>
							<ui:form dataset="queryCond"></ui:form>
							<div style="text-align: center;">
								<ui:button id="bntQueryCond">��ѯ</ui:button>
								<ui:button id="btn_clear">����</ui:button>
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="center">
					<ui:panel type="titleList" desc="��ѯ�б�">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="ds_job_config"></ui:table>
										</ui:pane>
										<ui:pane position="bottom" style="height:60px;">
											<div>
												<ui:button id="bnt_copyAdd">���</ui:button>
												<ui:button id="bnt_modify">�޸�</ui:button>
												<ui:button id="bnt_active">����</ui:button>
												<ui:button id="bnt_delet">ʧЧ</ui:button>
												<ui:button id="bnt_revalid">����Ч</ui:button>
											</div>
											<div class="customerpilot" extra="customerpilot" id="pilotAttribute" dataset="ds_job_config"></div>
										</ui:pane>
									</ui:layout>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:panel>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>

