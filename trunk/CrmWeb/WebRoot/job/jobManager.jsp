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
		<!-- 定义流程显示数据集-->
		<ui:dataset datasetId="ds_job_config" pageIndex="1" pageSize="23" readOnly="false" editable="false" loadDataAction="JobService" loadDataActionMethod="getJobPageModal" autoLoadPage="false" async="true" staticDataSource="true" loadAsNeeded="true"
			paginate="true">
			<!-- 传递参数-->
			<ui:parameter parameterId="jobtype" type="string"></ui:parameter>
			<ui:parameter parameterId="endBeginTime" type="string"></ui:parameter>
			<ui:parameter parameterId="endEndTime" type="string"></ui:parameter>
			<ui:parameter parameterId="jobState" type="string"></ui:parameter>


			<ui:field field="select" label="全选"></ui:field>
			<ui:field field="jobId" label="任务标志" visible="false"></ui:field>
			<ui:field field="jobName" label="任务名称"></ui:field>
			<ui:field field="jobType" label="任务类型" dropDown="ds_jobType"></ui:field>
			<ui:field field="jobState" label="任务状态" dropDown="ds_jobState"></ui:field>
			<ui:field field="jobStartDay" label="生效日期"></ui:field>
			<ui:field field="jobTerminateDay" label="失效日期"></ui:field>
			<ui:field field="jobGrpName" label="任务组名"></ui:field>
			<ui:field field="jobClassName" label="任务类名" visible="false"></ui:field>
			<ui:field field="jobInterval" label="执行时间间隔" visible="false"></ui:field>
			<ui:field field="jobMethod" label="调度方式" visible="false"></ui:field>
			<ui:field field="jobRule" label="调度规则" visible="false"></ui:field>
			<ui:field field="jobRuntime" label="定时执行时间" visible="false"></ui:field>
			<ui:field field="jobDesc" label="任务描述" ></ui:field>
			<ui:field field="jobArgs" label="参数"   visible="false"></ui:field>
			<ui:field field="jobStartRun" label="参数"   visible="false"></ui:field>
			<ui:field field="jobClustored" label="是否群集"   visible="false"></ui:field>
		</ui:dataset>

		<!--外围系统数据集-->
		<code id="outerSystem" attrCode="SYSTEM_ID" type="list" mapValue="true" blankValue="全部" blankId="-1" staticDataSource="false"></code>
		<ui:dropdown id="dropdownProdClass" attrCode="PRODUCT_CLASS" type="list" mapValue="true" staticDataSource="false" maxHeight="120"></ui:dropdown>
		<xml id="__ds_jobType">
		<items>
		<item label="全部" value="-1"></item>
		<item label="简单任务" value="0"></item>
		<item label="复杂任务" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobType" attrCode="dc_jobType"></code>
		<xml id="__ds_jobState">
		<items>
		<item label="全部" value="-1"></item>
		<item label="锁定" value="0"></item>
		<item label="激活" value="1"></item>
		<item label="失效" value="2"></item>
		</items>
		</xml>
		
		<code id="ds_jobState" attrCode="dc_jobState"></code>
		<!--定义查询数据集-->
		<ui:dataset datasetId="queryCond" readOnly="false">
		\
			<ui:field field="jobtype" label="任务类型" dropDown="ds_jobType"></ui:field>
			<ui:field field="endBeginTime" label="到期始日" type="date"></ui:field>
			<ui:field field="endEndTime" label="到期止日" type="date"></ui:field>
			<ui:field field="jobState" label="任务状态" dropDown="ds_jobState"></ui:field>
		</ui:dataset>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="left" style="width:220px">
					<ui:panel type="titleList" desc="定时任务查询条件">
						<ui:content>
							<ui:form dataset="queryCond"></ui:form>
							<div style="text-align: center;">
								<ui:button id="bntQueryCond">查询</ui:button>
								<ui:button id="btn_clear">重置</ui:button>
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="center">
					<ui:panel type="titleList" desc="查询列表">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:table dataset="ds_job_config"></ui:table>
										</ui:pane>
										<ui:pane position="bottom" style="height:60px;">
											<div>
												<ui:button id="bnt_copyAdd">添加</ui:button>
												<ui:button id="bnt_modify">修改</ui:button>
												<ui:button id="bnt_active">激活</ui:button>
												<ui:button id="bnt_delet">失效</ui:button>
												<ui:button id="bnt_revalid">再生效</ui:button>
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

