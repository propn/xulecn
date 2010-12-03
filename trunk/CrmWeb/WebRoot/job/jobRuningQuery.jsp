<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<script>
function page_onLoad(){
   ds_job_config.parameters().setValue("jobState","0");
   ds_job_config.reloadData();
   initialStat();
} 
function bnt_reflesh_onClick(){
  ds_job_config.reloadData();
  initialStat();
}
function getJobs() {
    var offerId = new Array();
    var record = ds_job_config.getFirstRecord();
    while (record) {
        if (System.isTrue(record.getValue("select"))) {
                offerId[i] = record.getValue("jobId");
                i = i + 1;
        }
        record = record.getNextRecord();
    }
    return offerId;
}
function bntQueryCond_onClick(){
    var current = getJobs();
    if( queryCond.getValue("endBeginTime") == ""){
      alert("请选择日志开始日期");
      return;
    }
    if( queryCond.getValue("endEndTime") == ""){
      alert("请选择日志结束日期");
      return;
    }
    if( queryCond.getValue("endEndTime") <  queryCond.getValue("endBeginTime")){
      alert("日志开始不能大于结束日期");
      return;
    }
    var endBeginTime =""+ queryCond.getValue("endBeginTime");
    var endEndTime = ""+ queryCond.getValue("endEndTime")
    ds_job_log.parameters().setValue("jobid",current);
    ds_job_log.parameters().setValue("endBeginTime",endBeginTime);
    ds_job_log.parameters().setValue("endEndTime",endEndTime);
    ds_job_log.reloadData();
 
}   
function bnt_clear_onClick(){
  var current = "" + getJobs();
  if(current == ""){
    alert("请选择需要清除日志的任务");
  }
   if( queryCond.getValue("endBeginTime") == ""){
      alert("请选择日志开始日期");
      return;
    }
    if( queryCond.getValue("endEndTime") == ""){
      alert("请选择日志结束日期");
      return;
    }
    if( queryCond.getValue("endEndTime") <  queryCond.getValue("endBeginTime")){
      alert("日志开始不能大于结束日期");
      return;
    }
     var callBack = function (reply) {
        if (reply.getResult() > 0) {
            alert("日志清除成功");
        } else {
            alert("日志清除失败");
        }
        ds_job_log.reloadData();
    };
    var ajaxCall = new NDAjaxCall();
    var offers = "" +current;
    ajaxCall.remoteCall("JobService", "clearLog", [current,queryCond.getValue("endBeginTime"),queryCond.getValue("endEndTime")], callBack);
}    
//启动任务
function bnt_startup_onClick(){
   var callBack = function (reply) {
        if (reply.getResult() == 2) {
          alert("任务正在停止中,稍候再启动!");
          return ;  
        }
        if (reply.getResult() == 0) {
            alert("调度启动成功");
        } else {
            alert("调度已启动");
        }
        document.all.bnt_startup.disabled = true;
        document.all.bnt_shutdown.disabled = false;
        ds_job_config.reloadData();
    };
   var ajaxCall = new NDAjaxCall();
   ajaxCall.remoteCall("JobService", "startup", [], callBack);
}  
//停止任务
function bnt_shutdown_onClick(){
   var callBack = function (reply) {
        if (reply.getResult() > 0) {
            alert("调度停止成功");
        } else {
            alert("调度已停止");
        }
        ds_job_config.reloadData();
        document.all.bnt_startup.disabled = false;
        document.all.bnt_shutdown.disabled = true;
    };
   var ajaxCall = new NDAjaxCall();
   ajaxCall.remoteCall("JobService", "shutdown", [], callBack);
}  

//初始化状态
function initialStat(){
 var callBack = function (reply) {
        if (reply.getResult() == 2) {
            alert("任务正在停止中，请稍后再作操作");
            document.all.bnt_startup.disabled = true;
            document.all.bnt_shutdown.disabled = false;
        }
        if (reply.getResult() > 0) {
            document.all.bnt_startup.disabled = true;
            document.all.bnt_shutdown.disabled = false;
        } else {
            document.all.bnt_startup.disabled = false;
            document.all.bnt_shutdown.disabled = true;
        }
    };
   var ajaxCall = new NDAjaxCall();
   ajaxCall.remoteCall("JobService", "getCurrentState", [], callBack);
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
		<ui:dataset datasetId="ds_job_config" loadDataAction="JobService" loadDataActionMethod="getCurrentRuningJob" async="true">
			<ui:field field="jobId" label="任务标志" visible="false"></ui:field>
			<ui:field field="jobName" label="任务名称"></ui:field>
			<ui:field field="clustorId" label="多服务标志"></ui:field>
			<ui:field field="lastExeuteBeginTime" label="最近运行始时间"></ui:field>
			<ui:field field="lastExeuteEndTime" label="最近运行止时间"></ui:field>
			<ui:field field="lastExeuteReSult" label="最近运行结果"></ui:field>
		</ui:dataset>
	
		<ui:bar type="search" desc="调度管理">
			<ui:content>
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:form dataset="loginInfo" submit="cond" labelLayout="30" inputLayout="60"></ui:form>
					</ui:pane>
					<ui:pane position="right" style="width :400px">
						<ui:button id="bnt_reflesh">刷新任务列表</ui:button>
						<ui:button id="bnt_startup">启动调度</ui:button>
						<ui:button id="bnt_shutdown">停止调度</ui:button>
					</ui:pane>
				</ui:layout>
			</ui:content>
		</ui:bar>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:panel type="titleList" desc="已启动任务列表">
						<ui:content>
							<ui:table dataset="ds_job_config"></ui:table>
						</ui:content>
					</ui:panel>
				</ui:pane>

			</ui:layout>
		</div>
	</body>
</html>

