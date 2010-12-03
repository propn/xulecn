<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title>ѡ���������¼</title>
		<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
		<ui:import library=""></ui:import>
		<script>
	var faterWindowArgruments = "";
	
    function page_onLoad()
    {
       DS_Info.reloadData();
       faterWindowArgruments = window.dialogArguments;//�����ڴ������Ĳ�����
    } 

  function btn_confirm_onClick()
  {
    var  record=DS_Info.getCurrent();
    if (!record) return;
	var record_id = record.getValue("record_id");
	var record_name = record.getValue("record_name");
    //��NeCommandTemplate.js�������ı�־λ���Ա�ʶ���if�ж��ڵĴ���ֻΪNeCommandTemplate.js�е�insertNeCmdTemplateRec_onClick��������
	if (faterWindowArgruments[2] && faterWindowArgruments[2] == '1') {
		var template_id = faterWindowArgruments[0];
		var template_name = faterWindowArgruments[1];
		//����ģ���ʶ���������¼��ʶ�� ne_cmd_template_rec�м��ģ�������������¼Ψһ��
		if (!validateTemplateRecord(template_id, template_name, record_id, record_name)) return;
	}
    //�������¼�б�
    //��SpOutOrderType.js�������ı�־λ���Ա�ʶ���if�ж��ڵĴ���ֻΪSpOutOrderType.js�е�insertSpParaCommands_onClick����
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

//����ģ���ʶ���������¼��ʶ�� ne_cmd_template_rec�м��ģ�������������¼Ψһ��
  function validateTemplateRecord(template_id, template_name, record_id, record_name) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"NeCmdTemplateRecService", "validateTemplateRecord", [template_id, record_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('�Ѵ���ָ��ģ������Ϊ "' + template_name + '", �������¼����Ϊ "' + record_name + '" �ļ�¼,������ѡ���ȡ��!') ;
			}
		});
		return flag;
  } 
//���ݹ������ͱ�ʶ���������¼�б��ʶ��SpOrderTypeRecords�м��ģ������������Ψһ��
  function validateOutOrderType(out_order_type_id, order_type_name, record_id, record) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"SpOrderTypeRecordsService", "validateSpOrderTypeRecord", [out_order_type_id, record_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('�Ѵ��ڹ�������Ϊ "' + order_type_name + '", ����������Ϊ ' + record + '" �ļ�¼,������ѡ���ȡ��!') ;
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
				<ui:field label="��¼����" field="record_name"  visible="true"></ui:field>														
				<ui:field label="��¼·��" field="record_path"  visible="true"></ui:field>
				<ui:field label="������������" field="group_order_type"  visible="true" ></ui:field>
				<ui:field label="Ŀ��·��" field="target_path"  visible="true"></ui:field>
				<ui:parameter parameterId="record_name" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="record_path" type="string" value=""></ui:parameter>
			</ui:dataset>


	</div>
<div id="layoutDefine">
	<ui:panel type="modalDialog" desc="�������¼">
            <ui:content>
		 <ui:layout type="border">
               <ui:pane position="center">
			  	<ui:table dataset="DS_Info"></ui:table>
		      </ui:pane>
		      <ui:pane position="bottom">
			  <div class="customerpilot" extra="customerpilot" id="pilotAttribute" dataset="DS_Info">
			  </div>
			       <ui:button id="btn_confirm">ȷ��</ui:button>
			       <ui:button id="btn_cancle">ȡ��</ui:button>
		     </ui:pane>
		</ui:layout>
		</ui:content>
	     </ui:panel>
	  </div>
	</body>
</html>
