

function getCheckBoxValuesByName(name) {
	var checkboxValues=[];
	var checkboxs = document.getElementsByName(name);
	for (var i = 0; i < checkboxs.length; i++) {
		if (checkboxs[i].checked) {
			checkboxValues[checkboxValues.length]=checkboxs[i].value;
		}
	}
	return checkboxValues;
}

//��ȡcheckBox�ĵ���ֵ������ȡ����
function getCheckBoxSingleValueByName(name) {
	var singleValue ="";
	var checkboxs = document.getElementsByName(name);
	for (var i = 0; i < checkboxs.length; i++) {
		if (checkboxs[i].checked) {
			return checkboxs[i].value;
		}
	}
	return singleValue;
}

function getRadioBoxValueByName(name) {
	var radioboxs = document.getElementsByName(name);
	for (var i = 0; i < radioboxs.length; i++) {
		if (radioboxs[i].checked) {
			return radioboxs[i].value;
		}
	}
}
function getRadioBoxOfferName(name) {
	var radioboxs = document.getElementsByName(name);
	for (var i = 0; i < radioboxs.length; i++) {
		if (radioboxs[i].checked) {
			return radioboxs[i].offerName;
		}
	}
}

function setRadioBoxValue(name, value) {
	var radioboxs = document.getElementsByName(name);
	for (var i = 0; i < radioboxs.length; i++) {
		if (radioboxs[i].value) {
			return radioboxs[i].checked = true;
		}
	}
}
var Extend = function () {
};
//��d���󿽱�c����
Extend.apply = function (c, d) {
	if (c && d && typeof c == "object") {
		for (var p in c) {
			d[p] = c[p];
		}
	}
	return d;
};
function SelectUtils() {

}
SelectUtils.prototype.getSubParameter = function (select) {
	//ͨ��select�ҵ��¼���������
	if (typeof (select.subList) == "undefined" || !select.subList) {
		return null;
	}
	var subSelect = document.getElementById(select.subList);
	if (!subSelect) {
		return null;
	}
	var parameter;
	var name;
	var markId;
	if (subSelect.id) {
		name = subSelect.id;
	} else {
		if (subSelect.name) {
			name = select.name;
		}
	}
	if (name == null) {
		return null;
	}
	
    	
	parameter = {"selectId":name, "attrCode":subSelect.attrCode, "parentAttrCode":select.attrCode, "parentValue":select.value, "_BUFFALO_OBJECT_CLASS_":"com.ztesoft.component.common.staticdata.vo.SelectParameter"};
	
	if(select.params)
    	parameter.params = select.params;//���ݲ��� ������ʽΪpara1;para2;para3
    	
	if (typeof (subSelect.blankValue) != "undefined" &&subSelect.blankValue) {
		parameter.blankValue=subSelect.blankValue;
	}
	if (typeof (subSelect.offerDetailId) != "undefined" && subSelect.offerDetailId) {
		parameter.fieldName = subSelect.fieldName;
		parameter.offerDetailId = subSelect.offerDetailId;//salesData.getBagOfferId();
		
	}
	var subSelectParameter = this.getSubParameter(subSelect);
	if (subSelectParameter != null) {
		parameter.subSelectParameter = subSelectParameter;
	}
	return parameter;
};
SelectUtils.prototype.getParameter = function (select) {
	var name;
	var markId;
	if (typeof (select.attrCode) == "undefined" || !select.attrCode) {
		return null;
	}
	if (select.id) {
		name = select.id;
	} else {
		if (select.name) {
			name = select.name;
		}
	}
	if (!name) {
		return null;
	}
	var parameter = {"selectId":name, "attrCode":select.attrCode, "_BUFFALO_OBJECT_CLASS_":"com.ztesoft.component.common.staticdata.vo.SelectParameter"};
    if(select.params)
    	parameter.params = select.params;//���ݲ��� ������ʽΪpara1;para2;para3
       	 	//������������Ʒ���Ա�ǵ�����Ҫ��ֵ��Ʒ���Ա��
 
	if (typeof (select.offerDetailId) != "undefined" && select.offerDetailId) {
		parameter.fieldName = select.fieldName;
		parameter.offerDetailId = select.offerDetailId;//salesData.getBagOfferId();
		
	}
	
	if (typeof (select.blankValue) != "undefined" &&select.blankValue) {
		parameter.blankValue=select.blankValue;
	}
	
	if (select.subList != null  && select.subList != "") {
		//�ҵ��¼���ѯ����
		
		var subSelect = document.getElementById(select.subList);

		if(subSelect != null){
			parameter.subSelectParameter = this.getParameter(subSelect);
		}
	}
	return parameter;
};
SelectUtils.prototype.getParameterTree = function (parentId) {
	var parameters = [];
	var temp = [];
	var oPane = document;
	if(parentId!=null) oPane = (typeof parentId == "string") ? document.getElementById(parentId) : parentId;
	var selects = oPane.getElementsByTagName("select");
	for (var i = 0; i < selects.length; i++) {
		//ɸѡ�¼���������Щ�������ϼ�������ʼ����ʱ��ᴦ����
		var select = selects[i];
		if(!select.params){
			select.params = "";
		} 
		if (typeof (select.attrCode) == "undefined" || !select.attrCode) {
			continue;
		}
		var name;
		if (select.id) {
			name = select.id;
		} else {
			if (select.name) {
				name = select.name;
			}
		}
		//������û�б����ó��¼�������
		var add = true;
		for (var k = 0; k < selects.length; k++) {
			if (typeof (selects[k].subList) != "undefined" && selects[k].subList) {
				if (selects[k].subList == name) {
					add = false;
					break;
				}
			}
		}
		if (add) {
			temp[temp.length] = select;
		}
	}
	for (var j = 0; j < temp.length; j++) {
		parameters[parameters.length] = this.getParameter(temp[j]);
	}
	return parameters;
};
SelectUtils.prototype.download = function (callBack,parentId) {

	var parameters = this.getParameterTree(parentId);
	SelectQuery.getSelectData([parameters], function (result) {
		function addOptions(select) {
			HTMLUtil.removeAllOptions(select.selectId);
			HTMLUtil.addOptions(select.selectId, select.data, "attrValue", "attrValueDesc");
		}
		for (var i = 0; i < result.length; i++) {
			addOptions(result[i]);
		}
		if(typeof(callBack)!="undefined"&&callBack!=null){
			callBack.call(this);
		}
	});
};
SelectUtils.prototype.downloadSubdata = function (select,callBack) {
	var parameter = this.getSubParameter(select);
	//alert(parameter)
	if(parameter==null)
		return ;
	SelectQuery.getSubSelectData([parameter], function (result) {
		function addOptions(select) {
			HTMLUtil.removeAllOptions(select.selectId);
			HTMLUtil.addOptions(select.selectId, select.data, "attrValue", "attrValueDesc");
		}
		for (var i = 0; i < result.length; i++) {
			addOptions(result[i]);
		}
		if(typeof(callBack)!="undefined"&&callBack!=null){
			callBack.call(this);
		}
	});
};
var selectUtils = new SelectUtils();



var SelectQuery = function () {

};
SelectQuery.getSelectData = function (param, callback) {
	var parameters = [];
	if (typeof (param) != "undefined" && param != null) {
		for (var i = 0; i < param.length; i++) {
			parameters[parameters.length] = param[i];
		}
	}
	var ajaxCall = Ajax.getSy();//ͬ������
	ajaxCall.remoteCall("SelectQuery", "getSelectData", parameters, function (reply) {
		var result = reply.getResult();
		callback.call(this, result);
	});
};
SelectQuery.getSubSelectData = function (param, callback) {
	var parameters = [];
	if (typeof (param) != "undefined" && param != null) {
		for (var i = 0; i < param.length; i++) {
			parameters[parameters.length] = param[i];
		}
	} 
	var ajaxCall = Ajax.getAsy();//�첽����
	ajaxCall.remoteCall("SelectQuery", "getSubSelectData", parameters, function (reply) {
		var result = reply.getResult();
		callback.call(this, result);
	});
};
