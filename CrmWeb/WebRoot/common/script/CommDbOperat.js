
//���ô˺�����jsp ҳ���dataset ����,
//���붨��sql,sqlParams,fieldNames ����������
function dbSelectWithPaging(sql, sqlParams, datasetId) {
    var fieldNames = new Array();
    for (var i = 0; i < datasetId.fields.fieldCount; i++) {
        fieldNames[fieldNames.length] = datasetId.fields[i].name;
    }
    var parameterSet = datasetId.parameters();
    parameterSet.setValue("sql", sql);
    parameterSet.setValue("sqlParams", sqlParams);
    parameterSet.setValue("fieldNames", fieldNames);
    datasetId.reloadData();
}


function dbSelectNoPaging(sql, sqlParams, callBack) {
    var ajaxCall = new NDAjaxCall(true);
    ajaxCall.remoteCall("CommDbOperatService", "dbSelectNoPaging", [sql, sqlParams], callBack);
}

function dbModify(sql, sqlParams) {
    var ajaxCall = new NDAjaxCall(false);
    var callBack = function (reply) {
    var result = reply.getResult();	
        if(result!=null){
        	alert("���ݿ������ɣ������� "+result+" �����ݣ�");
        }
    }
    ajaxCall.remoteCall("CommDbOperatService", "dbModify", [sql, sqlParams], callBack);
}



