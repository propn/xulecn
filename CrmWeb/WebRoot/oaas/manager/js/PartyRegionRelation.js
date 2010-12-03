
var actionType = "";
function page_onLoad() {
    relationInfo.setFieldPopupEnable("orgName", false);
    relationInfo.setFieldPopupEnable("regionName", false);
    var sql = "SELECT a.party_id,b.org_name,a.region_id,c.region_name,a.relation_type FROM party_region a,organization b,region c WHERE b.party_id=a.party_id AND c.region_id=a.region_id";
    dbSelectWithPaging(sql, new Array(), partyRegionInfo);
}

function button_relationInfo_regionName_onClick() {
    var obj = new Object();
    obj["selectDistinctRegionLevel"] = "2";//1表示只能选择相同级别的地域,2表示可以选择不同级别的地域
    obj["checkChildren"] = "2";//1表示钩选下级节点,2表示不钩选
    obj["selectParent"] = "2";
    /*
	 * 区域级别,97A表示集团公司,97E表示省公司,97B表示本地网,97C表示营业区,98D
	 * 表示处理局,98E表示母局,98F表示局站
	 */
    obj["regionLevel"] = "98F";
    obj["selectType"] = "1";//单选多选标志,1 为单选,2 为多选
    var returnValue = window.showModalDialog("../common/ResourceRegionSelect.jsp", obj, "dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
    if (returnValue != null) {
        relationInfo.setValue("regionId", returnValue[0]["regionId"]);
        relationInfo.setValue("regionName", returnValue[0]["regionName"]);
        actionType = "edit";
    }
}

function button_relationInfo_orgName_onClick() {
    var obj = new Object();
    obj["privilegeType"] = "3";
    obj["orgType"] = "99";
    obj["selectType"] = "1";//1表示当选,2表示多选
    obj["checkChildren"] = "1";//1表示钩选下级节点,2表示不钩选
    obj["uncheckedParent"] = "2";//1表示钩选下级节点时自动取消钩选上级节点.2表示不作处理 
    obj["downloadWhenChecked"] = "1";//1表示当钩选记录的时候下载下级节点,2表示钩选记录的时候不下载下级节点
    obj["selectParent"] = "2";//1表示不能选择上级地域,2表示可以选择上级地域,默认不可以选择上级地域
    var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp", obj, "dialogHeight: 450pt; dialogWidth: 500pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
    if (returnValue != null) {
        relationInfo.setValue("orgName", returnValue[0]["orgName"]);
        relationInfo.setValue("partyId", returnValue[0]["orgId"]);
        actionType = "edit";
    }
}


function addRelation_onClick() {
    if (actionType == "edit") {
        alert("你还有未保存的数据，请先保存!");
	 return;
    }
    relationInfo.clearData();
    relationInfo.insertRecord(null);
    relationInfo.setFieldPopupEnable("orgName", true);
    relationInfo.setFieldPopupEnable("regionName", true);
    relationInfo.setFieldReadOnly("relationType", false);
}

function deleteRelation_onClick() {
    if (actionType == "edit") {
        alert("你还有未保存的数据，请先保存!");
	 return;
    }

    if(relationInfo.getValue(0) == "" || relationInfo.getValue(2) == "" ){
	  alert("你没有选择记录！");
	  return;
    }	
	
    var conf = confirm("你要删除[" + relationInfo.getValue(1) + "/" + relationInfo.getValue(3) + "] 这条记录，是吗？");
    if (!conf) {
        return;
    }
    var delSql = "DELETE FROM party_region WHERE party_id=? AND region_id=? AND relation_type=?";
    var sqlParams = new Array();
    sqlParams[0] = relationInfo.getValue(0);
    sqlParams[1] = relationInfo.getValue(2);
    sqlParams[2] = relationInfo.getValue(4);
    dbModify(delSql, sqlParams);
    page_onLoad();
    actionType = "";
}

function partyRegionInfo_afterScroll() {
    for (var i = 0; i < partyRegionInfo.fields.length; i++) {
        relationInfo.setValue(i, partyRegionInfo.getValue(i));
    }
    actionType = "";
}

function btn_ok_onClick() {
    var callBack = function (reply) {
    	var retArray = reply.getResult();
        if (retArray != null && retArray[0] != null && retArray[0][0] != null) {
            if (retArray[0][0] > 0) {
                alert("此记录已经存在！");
                return;
            }
        }

        var insSql = "INSERT INTO party_region(party_id,region_id,relation_type) VALUES(?,?,?)";
        if(!dbModify(insSql, sqlParams)) return;
        page_onLoad();
        actionType = "";
    }
    
    var sqlParams = new Array();
    sqlParams[0] = relationInfo.getValue("partyId");
    sqlParams[1] = relationInfo.getValue("regionId");
    sqlParams[2] = relationInfo.getValue("relationType");
    if (sqlParams[0] == "" || sqlParams[1] == "" || sqlParams[2] == "") {
        alert("输入字段不能为空！");
        return;
    }
    var selSql = "SELECT count(*) FROM party_region WHERE party_id=? AND region_id=? AND relation_type=?";
    dbSelectNoPaging(selSql, sqlParams, callBack);
}


function btn_cancel_onClick() {
    for (var i = 0; i < partyRegionInfo.fields.length; i++) {
        relationInfo.setValue(i, partyRegionInfo.getValue(i));
    }
    actionType = "";
}

