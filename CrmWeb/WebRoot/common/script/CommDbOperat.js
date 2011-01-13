
//调用此函数在jsp 页面的dataset 里面,
//必须定义sql,sqlParams,fieldNames 这三个参数
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
        	alert("数据库操作完成，更新了 "+result+" 条数据！");
        }
    }
    ajaxCall.remoteCall("CommDbOperatService", "dbModify", [sql, sqlParams], callBack);
}



