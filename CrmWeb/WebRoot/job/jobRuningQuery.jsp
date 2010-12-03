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
      alert("��ѡ����־��ʼ����");
      return;
    }
    if( queryCond.getValue("endEndTime") == ""){
      alert("��ѡ����־��������");
      return;
    }
    if( queryCond.getValue("endEndTime") <  queryCond.getValue("endBeginTime")){
      alert("��־��ʼ���ܴ��ڽ�������");
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
    alert("��ѡ����Ҫ�����־������");
  }
   if( queryCond.getValue("endBeginTime") == ""){
      alert("��ѡ����־��ʼ����");
      return;
    }
    if( queryCond.getValue("endEndTime") == ""){
      alert("��ѡ����־��������");
      return;
    }
    if( queryCond.getValue("endEndTime") <  queryCond.getValue("endBeginTime")){
      alert("��־��ʼ���ܴ��ڽ�������");
      return;
    }
     var callBack = function (reply) {
        if (reply.getResult() > 0) {
            alert("��־����ɹ�");
        } else {
            alert("��־���ʧ��");
        }
        ds_job_log.reloadData();
    };
    var ajaxCall = new NDAjaxCall();
    var offers = "" +current;
    ajaxCall.remoteCall("JobService", "clearLog", [current,queryCond.getValue("endBeginTime"),queryCond.getValue("endEndTime")], callBack);
}    
//��������
function bnt_startup_onClick(){
   var callBack = function (reply) {
        if (reply.getResult() == 2) {
          alert("��������ֹͣ��,�Ժ�������!");
          return ;  
        }
        if (reply.getResult() == 0) {
            alert("���������ɹ�");
        } else {
            alert("����������");
        }
        document.all.bnt_startup.disabled = true;
        document.all.bnt_shutdown.disabled = false;
        ds_job_config.reloadData();
    };
   var ajaxCall = new NDAjaxCall();
   ajaxCall.remoteCall("JobService", "startup", [], callBack);
}  
//ֹͣ����
function bnt_shutdown_onClick(){
   var callBack = function (reply) {
        if (reply.getResult() > 0) {
            alert("����ֹͣ�ɹ�");
        } else {
            alert("������ֹͣ");
        }
        ds_job_config.reloadData();
        document.all.bnt_startup.disabled = false;
        document.all.bnt_shutdown.disabled = true;
    };
   var ajaxCall = new NDAjaxCall();
   ajaxCall.remoteCall("JobService", "shutdown", [], callBack);
}  

//��ʼ��״̬
function initialStat(){
 var callBack = function (reply) {
        if (reply.getResult() == 2) {
            alert("��������ֹͣ�У����Ժ���������");
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
		<!-- ����������ʾ���ݼ�-->
		<ui:dataset datasetId="ds_job_config" loadDataAction="JobService" loadDataActionMethod="getCurrentRuningJob" async="true">
			<ui:field field="jobId" label="�����־" visible="false"></ui:field>
			<ui:field field="jobName" label="��������"></ui:field>
			<ui:field field="clustorId" label="������־"></ui:field>
			<ui:field field="lastExeuteBeginTime" label="�������ʼʱ��"></ui:field>
			<ui:field field="lastExeuteEndTime" label="�������ֹʱ��"></ui:field>
			<ui:field field="lastExeuteReSult" label="������н��"></ui:field>
		</ui:dataset>
	
		<ui:bar type="search" desc="���ȹ���">
			<ui:content>
				<ui:layout type="border">
					<ui:pane position="center">
						<ui:form dataset="loginInfo" submit="cond" labelLayout="30" inputLayout="60"></ui:form>
					</ui:pane>
					<ui:pane position="right" style="width :400px">
						<ui:button id="bnt_reflesh">ˢ�������б�</ui:button>
						<ui:button id="bnt_startup">��������</ui:button>
						<ui:button id="bnt_shutdown">ֹͣ����</ui:button>
					</ui:pane>
				</ui:layout>
			</ui:content>
		</ui:bar>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:panel type="titleList" desc="�����������б�">
						<ui:content>
							<ui:table dataset="ds_job_config"></ui:table>
						</ui:content>
					</ui:panel>
				</ui:pane>

			</ui:layout>
		</div>
	</body>
</html>

