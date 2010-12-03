<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title>选择数据项记录</title>
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
	var record_id = record.getValue("record_id");
	var record_name = record.getValue("record_name");
    //从NeCommandTemplate.js传过来的标志位，以标识这个if判断内的代码只为NeCommandTemplate.js中的insertNeCmdTemplateRec_onClick方法所用
	if (faterWindowArgruments[2] && faterWindowArgruments[2] == '1') {
		var template_id = faterWindowArgruments[0];
		var template_name = faterWindowArgruments[1];
		//根据模板标识、数据项记录标识在 ne_cmd_template_rec中检查模板输入数据项记录唯一性
		if (!validateTemplateRecord(template_id, template_name, record_id, record_name)) return;
	}
    //数据项记录列表
    //从SpOutOrderType.js传过来的标志位，以标识这个if判断内的代码只为SpOutOrderType.js中的insertSpParaCommands_onClick所用
    if (faterWindowArgruments[2] && faterWindowArgruments[2] == '2') {
    	var out_order_type_id = faterWindowArgruments[0];
    	var order_type_name = faterWindowArgruments[1];
    	if (!validateOutOrderType(out_order_type_id, order_type_name, record_id, record_name)) return;
    }
    
    var back = {};
    back['record_id']=record_id;
    back['record_name']=record_name;
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

//根据模板标识、数据项记录标识在 ne_cmd_template_rec中检查模板输入数据项记录唯一性
  function validateTemplateRecord(template_id, template_name, record_id, record_name) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"NeCmdTemplateRecService", "validateTemplateRecord", [template_id, record_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('已存在指令模板名称为 "' + template_name + '", 数据项记录名称为 "' + record_name + '" 的记录,请重新选择或取消!') ;
			}
		});
		return flag;
  } 
//根据工单类型标识、数据项记录列表标识在SpOrderTypeRecords中检查模板输入数据项唯一性
  function validateOutOrderType(out_order_type_id, order_type_name, record_id, record) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"SpOrderTypeRecordsService", "validateSpOrderTypeRecord", [out_order_type_id, record_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('已存在工单名称为 "' + order_type_name + '", 数据项名称为 ' + record + '" 的记录,请重新选择或取消!') ;
			}
		});
		return flag;
  } 
</script>
	</head>
	<body submit="btn_confirm">
		<div id="datasetdefine">
			<ui:dataset datasetId="DS_Info" staticDataSource="true" 
				loadDataAction="SpParaRecordService" loadDataActionMethod="searchSpParaRecordData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
								
				<ui:field label="record_id" field="record_id"  visible="false" ></ui:field>									
				<ui:field label="记录名称" field="record_name"  visible="true"></ui:field>														
				<ui:field label="记录路径" field="record_path"  visible="true"></ui:field>
				<ui:field label="分组排序类型" field="group_order_type"  visible="true" ></ui:field>
				<ui:field label="目标路径" field="target_path"  visible="true"></ui:field>
				<ui:parameter parameterId="record_name" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="record_path" type="string" value=""></ui:parameter>
			</ui:dataset>


	</div>
<div id="layoutDefine">
	<ui:panel type="modalDialog" desc="数据项记录">
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
