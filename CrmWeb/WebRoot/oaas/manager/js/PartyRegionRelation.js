
var actionType = "";
function page_onLoad() {
    relationInfo.setFieldPopupEnable("orgName", false);
    relationInfo.setFieldPopupEnable("regionName", false);
    var sql = "SELECT a.party_id,b.org_name,a.region_id,c.region_name,a.relation_type FROM party_region a,organization b,region c WHERE b.party_id=a.party_id AND c.region_id=a.region_id";
    dbSelectWithPaging(sql, new Array(), partyRegionInfo);
}

function button_relationInfo_regionName_onClick() {
    var obj = new Object();
    obj["selectDistinctRegionLevel"] = "2";//1��ʾֻ��ѡ����ͬ����ĵ���,2��ʾ����ѡ��ͬ����ĵ���
    obj["checkChildren"] = "2";//1��ʾ��ѡ�¼��ڵ�,2��ʾ����ѡ
    obj["selectParent"] = "2";
    /*
	 * ���򼶱�,97A��ʾ���Ź�˾,97E��ʾʡ��˾,97B��ʾ������,97C��ʾӪҵ��,98D
	 * ��ʾ�����,98E��ʾĸ��,98F��ʾ��վ
	 */
    obj["regionLevel"] = "98F";
    obj["selectType"] = "1";//��ѡ��ѡ��־,1 Ϊ��ѡ,2 Ϊ��ѡ
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
    obj["selectType"] = "1";//1��ʾ��ѡ,2��ʾ��ѡ
    obj["checkChildren"] = "1";//1��ʾ��ѡ�¼��ڵ�,2��ʾ����ѡ
    obj["uncheckedParent"] = "2";//1��ʾ��ѡ�¼��ڵ�ʱ�Զ�ȡ����ѡ�ϼ��ڵ�.2��ʾ�������� 
    obj["downloadWhenChecked"] = "1";//1��ʾ����ѡ��¼��ʱ�������¼��ڵ�,2��ʾ��ѡ��¼��ʱ�������¼��ڵ�
    obj["selectParent"] = "2";//1��ʾ����ѡ���ϼ�����,2��ʾ����ѡ���ϼ�����,Ĭ�ϲ�����ѡ���ϼ�����
    var returnValue = window.showModalDialog("../common/OrganizationSelect.jsp", obj, "dialogHeight: 450pt; dialogWidth: 500pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
    if (returnValue != null) {
        relationInfo.setValue("orgName", returnValue[0]["orgName"]);
        relationInfo.setValue("partyId", returnValue[0]["orgId"]);
        actionType = "edit";
    }
}


function addRelation_onClick() {
    if (actionType == "edit") {
        alert("�㻹��δ��������ݣ����ȱ���!");
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
        alert("�㻹��δ��������ݣ����ȱ���!");
	 return;
    }

    if(relationInfo.getValue(0) == "" || relationInfo.getValue(2) == "" ){
	  alert("��û��ѡ���¼��");
	  return;
    }	
	
    var conf = confirm("��Ҫɾ��[" + relationInfo.getValue(1) + "/" + relationInfo.getValue(3) + "] ������¼������");
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
                alert("�˼�¼�Ѿ����ڣ�");
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
        alert("�����ֶβ���Ϊ�գ�");
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

