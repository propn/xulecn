
//��ѯ
function bntQueryCond_onClick() {
    ds_job_config.parameters().setValue("jobtype", queryCond.getValue("jobtype"));
    ds_job_config.parameters().setValue("endBeginTime", queryCond.getValue("endBeginTime"));
    ds_job_config.parameters().setValue("endEndTime", queryCond.getValue("endEndTime"));
    ds_job_config.parameters().setValue("jobState", queryCond.getValue("jobState"));
    ds_job_config.reloadData();
}
//�������
function btn_clear_onClick() {
    queryCond.setValue("jobtype", "-1");
    queryCond.setValue("endBeginTime", "");
    queryCond.setValue("endEndTime", "");
    queryCond.setValue("jobState", "-1");
}
//�������
function bnt_copyAdd_onClick() {
    //��ѯ����
    var sendParam = new Object();
    sendParam.jobtype = queryCond.getValue("jobtype");
    sendParam.jobState = queryCond.getValue("jobState");
    sendParam.data = ds_job_config.getCurrent();
    sendParam.oper = "1";
    var reqSel = window.showModalDialog("jobDetailView.jsp", sendParam, "help:no;center:yes;status:no;dialogHeight:550px;dialogWidth:600px");
    if (reqSel) {
        queryCond.setValue("jobtype", reqSel.jobType);
        queryCond.setValue("jobState", reqSel.jobState);
        ds_job_config.parameters().setValue("jobtype", reqSel.jobType);
        ds_job_config.parameters().setValue("jobState", reqSel.jobState);
        ds_job_config.reloadData();
    }
}
//�޸�����
function bnt_modify_onClick() {
    //��ѯ����
    if (ds_job_config.getCurrent() == null) {
        alert("\u8bf7\u9009\u62e9\u9700\u8981\u4fee\u6539\u7684\u4efb\u52a1");
        return;
    }
    if (ds_job_config.getCurrent().getValue("jobState") == "1") {
        if (confirm("\u4efb\u52a1\u5df2\u6fc0\u6d3b\uff0c\u786e\u8ba4\u662f\u5426\u9700\u8981\u5f3a\u5236\u4fee\u6539") == false) {
            return;
        }
    }
    var sendParam = new Object();
    sendParam.jobtype = queryCond.getValue("jobtype");
    sendParam.jobState = queryCond.getValue("jobState");
    sendParam.data = ds_job_config.getCurrent();
    sendParam.oper = "2";
    var reqSel = window.showModalDialog("jobDetailView.jsp", sendParam, "help:no;center:yes;status:no;dialogHeight:550px;dialogWidth:600px");
    if (reqSel) {
        queryCond.setValue("jobtype", reqSel.jobType);
        queryCond.setValue("jobState", reqSel.jobState);
        ds_job_config.parameters().setValue("jobtype", reqSel.jobType);
        ds_job_config.parameters().setValue("jobState", reqSel.jobState);
        ds_job_config.reloadData();
    }
}
//ѡ����δ�����Ķ���
function getlockList() {
    var offerId = new Array();
    var record = ds_job_config.getFirstRecord();
    while (record) {
        if (System.isTrue(record.getValue("select"))) {
            if ("0" != record.getValue("jobState")) {
                if ("1" == record.getValue("jobState")) {
                   alert("�Ѽ��������ܱ��ټ���");
                   return "ERR";
                }
                if ("2" == record.getValue("jobState")) {
                   alert("ѡ����ʧЧ����");
                   return "ERR";
                }
                 
            } else {
                offerId[i] = record.getValue("jobId");
                i = i + 1;
            }
        }
        record = record.getNextRecord();
    }
    return offerId;
}
//ѡ����δ�����Ķ���
function getunlockList() {
    var offerId = new Array();
    var record = ds_job_config.getFirstRecord();
    while (record) {
        if (System.isTrue(record.getValue("select"))) {
            if ("2" == record.getValue("jobState")) {
                 alert("ѡ����ʧЧ����");
                 return "ERR";
            } else {
                offerId[i] = record.getValue("jobId");
                i = i + 1;
            }
        }
        record = record.getNextRecord();
    }
    return offerId;
}
//ѡ����δ�����Ķ���
function getunValidlockList() {
    var offerId = new Array();
    var record = ds_job_config.getFirstRecord();
    while (record) {
        if (System.isTrue(record.getValue("select"))) {
            if ("2" == record.getValue("jobState")) {
                offerId[i] = record.getValue("jobId");
                i = i + 1;
            }else{
               alert("ѡ���˷�ʧЧ����");
               return "ERR";
            }
        }
        record = record.getNextRecord();
    }
    return offerId;
}

//����
function bnt_active_onClick() {
    var jobList = "" + getlockList();
    if("ERR" == jobList){
      return;
    }
   
    if (jobList == "") {
        alert("\u8bf7\u9009\u62e9\u9700\u8981\u6fc0\u6d3b\u7684\u4efb\u52a1");
        return;
    }
    var callBack = function (reply) {
        if (reply.getResult() > 0) {
            alert("\u6210\u529f\u6fc0\u6d3b" + reply.getResult() + "\u6761");
        } else {
            alert("\u6fc0\u6d3b\u5931\u8d25");
        }
        ds_job_config.flushData();
    };
    var ajaxCall = new NDAjaxCall();
    var offers = "" + jobList;
    ajaxCall.remoteCall("JobService", "activeJob", [jobList], callBack);
}

//ʧЧ
function bnt_delet_onClick() {
    var jobList = "" + getunlockList();
    if("ERR" == jobList){
      return;
    }
   
    if (jobList == "") {
        alert("\u8bf7\u9009\u62e9\u9700\u8981\u5931\u6548\u7684\u4efb\u52a1");
        return;
    }
    var callBack = function (reply) {
        if (reply.getResult() > 0) {
            alert("\u6210\u529f\u5931\u6548" + reply.getResult() + "\u6761");
        } else {
            alert("\u6fc0\u6d3b\u5931\u6548");
        }
        ds_job_config.flushData();
    };
    var ajaxCall = new NDAjaxCall();
    var offers = "" +jobList;
    ajaxCall.remoteCall("JobService", "invalidJob", [jobList], callBack);
}

//����Ч
function bnt_revalid_onClick() {
    var jobList = "" + getunValidlockList();
     if("ERR" == jobList){
      return;
    }
    if (jobList == "") {
        alert("\u8bf7\u9009\u62e9\u5931\u6548\u4efb\u52a1");
        return;
    }
    var callBack = function (reply) {
        if (reply.getResult() > 0) {
            alert("\u518d\u751f\u6548\u4efb\u52a1" + reply.getResult() + "\u6761");
        } else {
            alert("\u518d\u751f\u6548\u5931\u8d25");
        }
        ds_job_config.flushData();
    };
    var ajaxCall = new NDAjaxCall();
    var offers = "" + jobList;
    ajaxCall.remoteCall("JobService", "revalidJob", [jobList], callBack);
}

