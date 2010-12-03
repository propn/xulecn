
<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<script>
 var sendParam = window.dialogArguments;
 
 
//���ݳ�ʼ��
function page_onLoad(){
          initialQueryCond(); 
}      
//��ʼ���������   
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

//���淵����Ϣ
var callBack = function(reply){
	 var backMessage= reply.getResult();
	  alert(backMessage);	
	  var currentConfig = new Object();
      currentConfig.jobType = ds_job_config.getValue("jobType");
	  currentConfig.jobState = ds_job_config.getValue("jobState");
	  window.returnValue = currentConfig;
	  if(backMessage == "����ɹ�"){
	     window.close();	
      }
  }
  
//��������
function bnt_save_onClick(){
         if(ds_job_config.getValue("jobName") == ""){
           alert("����������Ϊ��");
           return;
         }
         if(ds_job_config.getValue("jobStartDay") == ""){
           alert("��Ч���ڲ���Ϊ��");
           return;
         }
         if(ds_job_config.getValue("jobTerminateDay") == ""){
           alert("ʧЧ���ڲ���Ϊ��");
           return;
         }
         if(ds_job_config.getValue("jobGrpName") == ""){
           alert("������������Ϊ��");
           return;
         }
         if(ds_job_config.getValue("jobClassName") == ""){
           alert("������������Ϊ��");
           return;
         }
         if(ds_job_config.getValue("jobStartDay") > ds_job_config.getValue("jobTerminateDay")){
           alert("��Ч���ڲ��ܴ���ʧЧ����");
           return;
         }
         
         if(ds_job_config.getValue("jobType") == "0"){
            if(ds_job_config.getValue("jobInterval") == ""){
               alert("ִ��ʱ��������Ϊ��");
               return;
            }
         }else{
            if(ds_job_config.getValue("jobMethod") == ""){
               alert("���ȷ�ʽ����Ϊ��");
               return;
            }
            if(ds_job_config.getValue("jobRule") == ""){
               alert("���ȹ�����Ϊ��");
               return;
            }
            if(ds_job_config.getValue("jobRuntime") == ""){
               alert("��ʱִ��ʱ�䲻��Ϊ��");
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
		<!-- ����������ʾ���ݼ�-->
		<ui:dataset datasetId="ds_job_config" pageIndex="1" pageSize="23" readOnly="false" editable="false" loadDataAction="JobService" loadDataActionMethod="getJobPageModal" autoLoadPage="false" async="true" staticDataSource="true" loadAsNeeded="true"
			paginate="true">
			<ui:field field="jobId" label="�����־" visible="false"></ui:field>
			<ui:field field="jobName" label="��������"></ui:field>
			<ui:field field="jobType" label="��������" dropDown="ds_jobType"></ui:field>
			<ui:field field="jobState" label="����״̬" readOnly="true" dropDown="ds_jobState"></ui:field>
			<ui:field field="jobClustored" label="�Ƿ񵥷���������" dropDown="ds_jobClustored"> </ui:field>
			<ui:field field="jobStartRun" label="�Ƿ���������ִ��" dropDown="ds_jobStartRun"> </ui:field>
			<ui:field field="jobStartDay" label="��Ч����" type="datetime"></ui:field>
			<ui:field field="jobTerminateDay" label="ʧЧ����" type="datetime"></ui:field>
			<ui:field field="jobGrpName" label="��������"></ui:field>
			<ui:field field="jobClassName" label="��������"></ui:field>
			<ui:field field="jobArgs" label="����"></ui:field>
			<ui:field field="jobInterval" label="ִ��ʱ����"></ui:field>
			<ui:field field="jobMethod" label="���ȷ�ʽ" dropDown="ds_jobMethod"></ui:field>
			<ui:field field="jobRule" label="���ȹ���"></ui:field>
			<ui:field field="jobRuntime" label="��ʱִ��ʱ��"></ui:field>
			<ui:field field="jobDesc" label="��������"></ui:field>
		</ui:dataset>
		
		<xml id="__ds_jobClustored">
		<items>
		<item label="��" value="0"></item>
		<item label="��" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobClustored" attrCode="dc_jobClustored"></code>
		
		
	   <xml id="__ds_jobStartRun">
		<items>
		<item label="��" value="0"></item>
		<item label="��" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobStartRun" attrCode="dc_jobStartRun"></code>
	
		<xml id="__ds_jobType">
		<items>
		<item label="������" value="0"></item>
		<item label="��������" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobType" attrCode="dc_jobType"></code>
		<xml id="__ds_jobState">
		<items>
		<item label="����" value="0"></item>
		<item label="����" value="1"></item>
		<item label="ʧЧ" value="1"></item>
		</items>
		</xml>
		<code id="ds_jobState" attrCode="dc_jobState"></code>
		<xml id="__ds_jobMethod">
		<items>
		<item label="��" value="2"></item>
		<item label="��" value="3"></item>
		<item label="��" value="4"></item>
		<item label="��" value="5"></item>
		</items>
		</xml>
		<code id="ds_jobMethod" attrCode="dc_jobMethod"></code>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:panel type="titleList" desc="��ʱ������ͼ">
						<ui:content>
							<ui:form dataset="ds_job_config"></ui:form>
							<div style="text-align: center;">
								<ui:button id="bnt_save">����</ui:button>
								<ui:button id="btn_clear">�ر�</ui:button>
							</div>
							<div style="text-align: left">
							�Ƿ������뵥��������: �ǣ�ֻ��һ�������������У��񣬱�ʾ��ÿ��������������
							<br> ���ȹ�������˵����
							 <br>�յ���: ��ʽ(�� ���� Сʱ * * ? )��:ÿ��10�����б��ʽΪ 0 0 10 * * ? 
							 <br>�ܵ���: ��ʽ(�� ���� Сʱ ? * [SUN-SAT]��:ÿ��������10�����б��ʽΪ 0 0 10 ? * SUN
							 <br>�µ���: ��ʽ(�� ���� Сʱ �� * ����:ÿ��1��10�����б��ʽΪ 0 0 10 1 * ��
							 <br>�����: ��ʽ(�� ���� Сʱ �� �� ����:ÿ��1��1��10�����б��ʽΪ 0 0 10 1 1 ��
							 <br>--?��ʾһ�������ж����������
							 <br>--*��ʾ�롢���ӡ�Сʱ���ա��¡��������ֵ
							 <br>---�롢���ӡ�Сʱ���ա��¡��ꡢ�ܵ�ÿ��ֵ�������о����ͱ�ʾ�磺3,4,5 �����еĵ�3����4����5���� 
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>

