
var actionType = "";
function page_onLoad() {
    var sql = "SELECT id,def_fee_code,def_fee_name,prod_type,prod_prop,srv_code,fee_items,"
			+ "orderno,count_flag,county_type,resid_flag FROM rpt_finance_match";
    dbSelectWithPaging(sql, new Array(), financeItemList);
    financeItemDetail.setReadOnly(true) ;
}


function addItem_onClick() {
    if (actionType == "edit") {
        alert("你还有未保存的数据，请先保存!");
    }
    financeItemDetail.setValue("id","");
    financeItemDetail.setValue("defFeeCode","");
    financeItemDetail.setValue("defFeeName","");
    financeItemDetail.setReadOnly(false);
    actionType = "add";
}

function editItem_onClick() {
    financeItemDetail.setReadOnly(false) ;
    financeItemDetail.setFieldReadOnly("id",true);
    actionType = "edit";
}


function deleteItem_onClick() {
    if (actionType == "add" ||actionType == "edit" ) {
		alert("你还有未保存的数据，请先保存!");
		return;
    }

    if(financeItemDetail.getValue(0) == ""){
	  alert("你没有选择记录！");
	  return;
    }	
	
    var conf = confirm("你要删除[" + financeItemDetail.getValue(1) + "/" + financeItemDetail.getValue(2) + "] 这个科目这吗？");
    if (!conf) {
        return;
    }
    var delSql = "DELETE FROM rpt_finance_match WHERE id=?";
    var sqlParams = new Array();
    sqlParams[0] = financeItemDetail.getValue(0);
    dbModify(delSql, sqlParams);
    page_onLoad();
    actionType = "";
}

function financeItemList_afterScroll() {
    for (var i = 0; i < financeItemList.fields.length; i++) {
        financeItemDetail.setValue(i, financeItemList.getValue(i));
    }
    financeItemDetail.setReadOnly(true) ;
    actionType = "";
}

function btn_ok_onClick() {
    if (financeItemDetail.getValue("id")== "" ) {
        alert("标识字段不能为空！");
        return;
    }
    if (financeItemDetail.getValue("defFeeCode") == "" ) {
        alert("科目编码字段不能为空！");
        return;
    }
    if (financeItemDetail.getValue("defFeeName") == "" ) {
        alert("科目名称字段不能为空！");
        return;
    }
    if (financeItemDetail.getValue("orderNo") == "" ) {
        alert("排列顺序字段不能为空！");
        return;
    }
		 
    if (actionType == "add") {
		insertRow();
    }
    else if(actionType == "edit") {
	    var upsSql = 
			"UPDATE rpt_finance_match SET def_fee_code=?,def_fee_name=?,prod_type=?,prod_prop=?,"
			+ "srv_code=?,fee_items=?,orderno=?,count_flag=?,county_type=?,resid_flag=? WHERE id=?";
	    var updSqlParams = new Array();
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("defFeeCode");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("defFeeName");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("prodType");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("prodProp");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("srvCode");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("feeItems");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("orderNo");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("countFlag");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("countyType");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("residFlag");
	    updSqlParams[updSqlParams.length] = financeItemDetail.getValue("id");
	    dbModify(upsSql, updSqlParams);
           page_onLoad();
    }
}



function insertRow(){
     var callBack = function (reply) {
    	var retArray = reply.getResult();
	
        if (retArray != null && retArray[0] != null && retArray[0][0] != null) {
            if (retArray[0][0] > 0) {
                alert("此记录已经存在！");
                return;
            }
        }
        var insSqlParams = new Array();
		
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("id");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("defFeeCode");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("defFeeName");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("prodType");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("prodProp");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("srvCode");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("feeItems");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("orderNo");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("countFlag");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("countyType");
	 insSqlParams[insSqlParams.length] = financeItemDetail.getValue("residFlag");
				
        var insSql = "INSERT INTO rpt_finance_match(id,def_fee_code,def_fee_name,prod_type,"
			+ "prod_prop,srv_code,fee_items,orderno,count_flag,county_type,resid_flag) "
        	+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        if(!dbModify(insSql, insSqlParams)) return;
        page_onLoad();
        actionType = "";
    }   
	 
    var selSqlParams = new Array();
    selSqlParams[0] = financeItemDetail.getValue("id");
    selSqlParams[1] = financeItemDetail.getValue("defFeeCode");
    selSqlParams[2] = financeItemDetail.getValue("defFeeName");
    var selSql = "SELECT count(*) FROM rpt_finance_match WHERE id=? OR def_fee_code=? OR def_fee_name=?";
    dbSelectNoPaging(selSql, selSqlParams, callBack);
}


function btn_cancel_onClick() {
    for (var i = 0; i < financeItemList.fields.length; i++) {
        financeItemDetail.setValue(i, financeItemList.getValue(i));
    }
    actionType = "";
}

