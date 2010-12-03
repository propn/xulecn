<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title>选择目标数据项</title>
		<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
		<ui:import library=""></ui:import>
		<script>
	var faterWindowArgruments = "";
    function page_onLoad()
    {

       DS_Info.reloadData();
       faterWindowArgruments = window.dialogArguments;//父窗口传过来的参数组
    } 

  function btn_confirm_onClick()
  {
    var  record=DS_Info.getCurrent();
    if (!record) return;
    	var command_id = record.getValue("command_id");
    	var command_name = record.getValue("name");
    //从NeCommandTemplate.js传过来的标志位，以标识这个if判断内的代码只为NeCommandTemplate.js中的insertNeCmdTemplatePara_onClick所用
    if (faterWindowArgruments[2] && faterWindowArgruments[2] == '0') {
    	var template_id = faterWindowArgruments[0];
    	var template_name = faterWindowArgruments[1];
    	//根据模板标识、数据项标识在ne_cmd_template_para中检查模板输入数据项唯一性
    	if (!validateTemplateCommond(template_id, template_name, command_id, command_name)) return;
    }
    //目标数据项
    //从SpOutOrderType.js传过来的标志位，以标识这个if判断内的代码只为SpOutOrderType.js中的insertSpParaCommands_onClick所用
    if (faterWindowArgruments[2] && faterWindowArgruments[2] == '1') {
    	var out_order_type_id = faterWindowArgruments[0];
    	var order_type_name = faterWindowArgruments[1];
    	if (!validateOutOrderType(out_order_type_id, order_type_name, command_id, command_name)) return;
    }
    
    //insertParaRecord 为从SpParaRecord.js传过来的标志位，以标识这个if判断内的代码只为SpParaRecord.js的insertParaRecordList_onClick所用
    if (faterWindowArgruments[0] && faterWindowArgruments[0] == 'insertParaRecord') {
    	var record_id = faterWindowArgruments[1];//对应的数据项记录id
    	var record_name = faterWindowArgruments[2];//对应的数据线记录名称
    	var command_id = record.getValue("command_id");//当前所选择的目标数据项id
    	var command_name = record.getValue("name");//当前所选择的目标数据项名称
    	if (!validateCommandPara(record_id, record_name, command_id, command_name)) return;
    }
    
    var back = {};
    back['command_id'] = command_id;
    back['name'] = command_name;
	window.returnValue = back;		
    window.close();
  }

  function table_DS_Info_ondblclick()
  {
     btn_confirm_onClick();
  }

  function btn_cancle_onClick(){
    window.close();
  }

//根据模板标识、数据项标识在ne_cmd_template_para中检查模板输入数据项唯一性
  function validateTemplateCommond(template_id, template_name, command_id, command_name) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"NeCmdTemplateParaService", "validateTemplateCommond", [template_id, command_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('已存在指令模板名称为  "' + template_name + '", 数据项名称为  "' + command_name + '" 的记录,请重新选择或取消!') ;
			}
		});
		return flag;
  } 
//根据工单类型标识、数据项标识在SpOrderTypeCommands中检查模板输入数据项唯一性
  function validateOutOrderType(out_order_type_id, order_type_name, command_id, command_name) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"SpOrderTypeCommandsService", "validateSpOrderTypeCommond", [out_order_type_id, command_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('已存在工单名称为 " ' + order_type_name + '", 数据项名称为 " ' + command_name + '" 的记录,请重新选择或取消!') ;
			}
		});
		return flag;
  } 
  
//新增时，验证 目标数据项 的唯一性
 function validateCommandPara(record_id, record_name, command_id, command_name) {
 		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"SpParaRecordService", "validateCommandPara", [record_id, command_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('所选择的: ' + command_name + ' 已在数据项记录  " ' + record_name + '"  中存在,请重新选择或取消!') ;
			}
		});
		return flag;
 }   
  
</script>
	</head>
	<body submit="btn_confirm">
		<div id="datasetdefine">
			<ui:dataset datasetId="DS_Info" staticDataSource="true" 
				loadDataAction="NeCommandParaService" loadDataActionMethod="searchNeCommandParaData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
				<ui:field label="目标数据项" field="command_id"  visible="false" ></ui:field>
				<ui:field label="名称" field="name"  visible="true" ></ui:field>
																
				<ui:field label="编码" field="para_code"  visible="true" ></ui:field>
																
				<ui:field label="取值方式" field="get_method"  visible="true" attrCode="get_method"></ui:field>
				<ui:field label="是否关键数据项" field="is_key"  visible="true" attrCode="is_key"></ui:field>	
				<ui:field label="数据项节点" field="node_path"  visible="true" ></ui:field>
																
				<ui:field label="节点属性" field="node_attr"  visible="true"></ui:field>
				<ui:field label="目录" field="catalogname"  visible="true"></ui:field>
				<ui:parameter parameterId="name" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="para_code" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="command_catalog_id" type="string" value=""></ui:parameter>
			</ui:dataset>

	</div>
<div id="layoutDefine">
	<ui:panel type="modalDialog" desc="目标数据项">
            <ui:content>
		 <ui:layout type="border">
               <ui:pane position="center">
			  	<ui:table dataset="DS_Info"></ui:table>
		      </ui:pane>
		      <ui:pane position="bottom">
			  <div class="customerpilot" extra="customerpilot" id="pilotAttribute" dataset="DS_Info">
			  </div>
			       <ui:button id="btn_confirm">确定</ui:button>
			       <ui:button id="btn_cancle">取消</ui:button>
		     </ui:pane>
		</ui:layout>
		</ui:content>
	     </ui:panel>
	  </div>
	</body>
</html>
