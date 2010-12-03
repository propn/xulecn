
<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<script>
 var sendParam = window.dialogArguments;
 
 
//数据初始化
function page_onLoad(){
          initialQueryCond(); 
}      
//初始化传入参数   
function initialQueryCond(){
   if( sendParam.oper == "1"){
     ds_job_config.setValue("jobType","0");
     ds_job_config.setFieldReadOnly("jobMethod", true);
     ds_job_config.setFieldReadOnly("jobRule", true);
     ds_job_config.setFieldReadOnly("jobRuntime", true);
   }else{
     ds_job_config.setValue("jobId",sendParam.data.getValue("jobId"));
     ds_job_config.setValue("jobName",sendParam.data.getValue("jobName"));
     ds_job_config.setValue("jobType",sendParam.data.getValue("jobType"));
     ds_job_config.setValue("jobState",sendParam.data.getValue("jobState"));
     ds_job_config.setValue("jobStartDay",sendParam.data.getValue("jobStartDay"));
     ds_job_config.setValue("jobTerminateDay",sendParam.data.getValue("jobTerminateDay"));
     ds_job_config.setValue("jobGrpName",sendParam.data.getValue("jobGrpName"));
     ds_job_config.setValue("jobClassName",sendParam.data.getValue("jobClassName"));
     ds_job_config.setValue("jobInterval",sendParam.data.getValue("jobInterval"));
     ds_job_config.setValue("jobMethod",sendParam.data.getValue("jobMethod"));
     ds_job_config.setValue("jobRule",sendParam.data.getValue("jobRule"));
     ds_job_config.setValue("jobRuntime",sendParam.data.getValue("jobRuntime"));
     ds_job_config.setValue("jobDesc",sendParam.data.getValue("jobDesc"));
     ds_job_config.setValue("jobArgs",sendParam.data.getValue("jobArgs"));
     ds_job_config.setValue("jobStartRun",sendParam.data.getValue("jobStartRun"));
     ds_job_config.setValue("jobClustored",sendParam.data.getValue("jobClustored"));
     
     if(sendParam.data.getValue("jobType") == "0"){
        ds_job_config.setValue("jobType","0");
        ds_job_config.setFieldReadOnly("jobMethod", true);
        ds_job_config.setFieldReadOnly("jobRule", true);
        ds_job_config.setFieldReadOnly("jobRuntime", true);
     }else{
        ds_job_config.setFieldReadOnly("jobInterval", true);
     }
   }
 
} 
  
 function ds_jobType_onSelect(dropdown, record, editor){
   var jobTypeId = record.getValue("value");
   ds_job_config.setFieldReadOnly("jobMethod", (jobTypeId=="0"));
   ds_job_config.setFieldReadOnly("jobRule", (jobTypeId=="0"));
   ds_job_config.setFieldReadOnly("jobRuntime", (jobTypeId=="0"));
   ds_job_config.setFieldReadOnly("jobInterval", (jobTypeId=="1"));
   if(jobTypeId == "1"){
    ds_job_config.setValue("jobInterval","");
   }else{
     ds_job_config.setValue("jobMethod","");
     ds_job_config.setValue("jobRule","");
     ds_job_config.setValue("jobRuntime","");
     ds_job_config.setValue("jobMethod","");
   }
   return true;
 }

//保存返回信息
var callBack = function(reply){
	 var backMessage= reply.getResult();
	  alert(backMessage);	
	  var currentConfig = new Object();
      currentConfig.jobType = ds_job_config.getValue("jobType");
	  currentConfig.jobState = ds_job_config.getValue("jobState");
	  window.returnValue = currentConfig;
	  if(backMessage == "保存成功"){
	     window.close();	
      }
  }
  
//保存数据
function bnt_save_onClick(){
         if(ds_job_config.getValue("jobName") == ""){
           alert("任务名不能为空");
           return;
         }
         if(ds_job_config.getValue("jobStartDay") == ""){
           alert("生效日期不能为空");
           return;
         }
         if(ds_job_config.getValue("jobTerminateDay") == ""){
           alert("失效日期不能为空");
           return;
         }
         if(ds_job_config.getValue("jobGrpName") == ""){
           alert("任务组名不能为空");
           return;
         }
         if(ds_job_config.getValue("jobClassName") == ""){
           alert("任务类名不能为空");
           return;
         }
         if(ds_job_config.getValue("jobStartDay") > ds_job_config.getValue("jobTerminateDay")){
           alert("生效日期不能大于失效日期");
           return;
         }
         
         if(ds_job_config.getValue("jobType") == "0"){
            if(ds_job_config.getValue("jobInterval") == ""){
               alert("执行时间间隔不能为空");
               return;
            }
         }else{
            if(ds_job_config.getValue("jobMethod") == ""){
               alert("调度方式不能为空");
               return;
            }
            if(ds_job_config.getValue("jobRule") == ""){
               alert("调度规则不能为空");
               return;
            }
            if(ds_job_config.getValue("jobRuntime") == ""){
               alert("定时执行时间不能为空");
               return;
            }        
         }
        
         var ajaxCall = new NDAjaxCall(false);
	     var paramObj=extractBeanFromDataset(ds_job_config,"com.ztesoft.component.job.vo.CrmJobVO");
	     ajaxCall.remoteCall("JobService", "saveCrmJobVO", [paramObj,0],callBack);
} 
function btn_clear_onClick(){
     // var currentConfig = new Object();
    //  currentConfig.jobType = ds_job_config.getValue("jobType");
	 // currentConfig.jobState = ds_job_config.getValue("jobState");
	 // window.returnValue = currentConfig;
      window.close();
}

</script>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator,tree,treeList">
		<title></title>
		<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
		<ui:import library=""></ui:import>
	</head>
	<body>
		<!-- 定义流程显示数据集-->
		<ui:dataset datasetId="ds_job_config" pageIndex="1" pageSize="23" readOnly="false" editable="false" loadDataAction="JobService" loadDataActionMethod="getJobPageModal" autoLoadPage="false" async="true" staticDataSource="true" loadAsNeeded="true"
			paginate="true">
			<ui:field field="jobId" label="任务标志" visible="false"></ui:field>
			<ui:field field="jobName" label="任务名称"></ui:field>
			<ui:field field="jobType" label="任务类型" dropDown="ds_jobType"></ui:field>
			<ui:field field="jobState" label="任务状态" readOnly="true" dropDown="ds_jobState"></ui:field>
			<ui:field field="jobClustored" label="是否单服务器运行" dropDown="ds_jobClustored"> </ui:field>
			<ui:field field="jobStartRun" label="是否启动立刻执行" dropDown="ds_jobStartRun"> </ui:field>
			<ui:field field="jobStartDay" label="生效日期" type="datetime"></ui:field>
			<ui:field field="jobTerminateDay" label="失效日期" type="datetime"></ui:field>
			<ui:field field="jobGrpName" label="任务组名"></ui:field>
			<ui:field field="jobClassName" label="任务类名"></ui:field>
			<ui:field field="jobArgs" label="参数"></ui:field>
			<ui:field field="jobInterval" label="执行时间间隔"></ui:field>
			<ui:field field="jobMethod" label="调度方式" dropDown="ds_jobMethod"></ui:field>
			<ui:field field="jobRule" label="调度规则"></ui:field>
			<ui:field field="jobRuntime" label="定时执行时间"></ui:field>
			<ui:field field="jobDesc" label="任务描述"></ui:field>
		</ui:dataset>
		
		<xml id="__ds_jobClustored">
		<items>
		<item label="否" value="0"></item>
		<item label="是" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobClustored" attrCode="dc_jobClustored"></code>
		
		
	   <xml id="__ds_jobStartRun">
		<items>
		<item label="否" value="0"></item>
		<item label="是" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobStartRun" attrCode="dc_jobStartRun"></code>
	
		<xml id="__ds_jobType">
		<items>
		<item label="简单任务" value="0"></item>
		<item label="复杂任务" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobType" attrCode="dc_jobType"></code>
		<xml id="__ds_jobState">
		<items>
		<item label="锁定" value="0"></item>
		<item label="激活" value="1"></item>
		<item label="失效" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobState" attrCode="dc_jobState"></code>
		<xml id="__ds_jobMethod">
		<items>
		<item label="日" value="2"></item>
		<item label="周" value="3"></item>
		<item label="月" value="4"></item>
		<item label="年" value="5"></item>
		</items>
		</xml>
		<code id="ds_jobMethod" attrCode="dc_jobMethod"></code>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:panel type="titleList" desc="定时任务视图">
						<ui:content>
							<ui:form dataset="ds_job_config"></ui:form>
							<div style="text-align: center;">
								<ui:button id="bnt_save">保存</ui:button>
								<ui:button id="btn_clear">关闭</ui:button>
							</div>
							<div style="text-align: left">
							是否运行与单个服务器: 是：只在一个服务器上运行，否，表示在每个服务器上运行
							<br> 调度规则配置说明：
							 <br>日调度: 格式(秒 分钟 小时 * * ? )如:每天10点运行表达式为 0 0 10 * * ? 
							 <br>周调度: 格式(秒 分钟 小时 ? * [SUN-SAT]如:每周星期天10点运行表达式为 0 0 10 ? * SUN
							 <br>月调度: 格式(秒 分钟 小时 日 * ？如:每月1号10点运行表达式为 0 0 10 1 * ？
							 <br>年调度: 格式(秒 分钟 小时 日 月 ？如:每年1月1号10点运行表达式为 0 0 10 1 1 ？
							 <br>--?表示一个月中有多少天或星期
							 <br>--*表示秒、分钟、小时、日、月、年的任意值
							 <br>---秒、分钟、小时、日、月、年、周的每段值均可用列举类型表示如：3,4,5 分钟中的第3、第4、第5分钟 
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>

