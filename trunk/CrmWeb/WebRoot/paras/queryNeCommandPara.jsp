<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title>ѡ��Ŀ��������</title>
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
    	var command_id = record.getValue("command_id");
    	var command_name = record.getValue("name");
    //��NeCommandTemplate.js�������ı�־λ���Ա�ʶ���if�ж��ڵĴ���ֻΪNeCommandTemplate.js�е�insertNeCmdTemplatePara_onClick����
    if (faterWindowArgruments[2] && faterWindowArgruments[2] == '0') {
    	var template_id = faterWindowArgruments[0];
    	var template_name = faterWindowArgruments[1];
    	//����ģ���ʶ���������ʶ��ne_cmd_template_para�м��ģ������������Ψһ��
    	if (!validateTemplateCommond(template_id, template_name, command_id, command_name)) return;
    }
    //Ŀ��������
    //��SpOutOrderType.js�������ı�־λ���Ա�ʶ���if�ж��ڵĴ���ֻΪSpOutOrderType.js�е�insertSpParaCommands_onClick����
    if (faterWindowArgruments[2] && faterWindowArgruments[2] == '1') {
    	var out_order_type_id = faterWindowArgruments[0];
    	var order_type_name = faterWindowArgruments[1];
    	if (!validateOutOrderType(out_order_type_id, order_type_name, command_id, command_name)) return;
    }
    
    //insertParaRecord Ϊ��SpParaRecord.js�������ı�־λ���Ա�ʶ���if�ж��ڵĴ���ֻΪSpParaRecord.js��insertParaRecordList_onClick����
    if (faterWindowArgruments[0] && faterWindowArgruments[0] == 'insertParaRecord') {
    	var record_id = faterWindowArgruments[1];//��Ӧ���������¼id
    	var record_name = faterWindowArgruments[2];//��Ӧ�������߼�¼����
    	var command_id = record.getValue("command_id");//��ǰ��ѡ���Ŀ��������id
    	var command_name = record.getValue("name");//��ǰ��ѡ���Ŀ������������
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

//����ģ���ʶ���������ʶ��ne_cmd_template_para�м��ģ������������Ψһ��
  function validateTemplateCommond(template_id, template_name, command_id, command_name) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"NeCmdTemplateParaService", "validateTemplateCommond", [template_id, command_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('�Ѵ���ָ��ģ������Ϊ  "' + template_name + '", ����������Ϊ  "' + command_name + '" �ļ�¼,������ѡ���ȡ��!') ;
			}
		});
		return flag;
  } 
//���ݹ������ͱ�ʶ���������ʶ��SpOrderTypeCommands�м��ģ������������Ψһ��
  function validateOutOrderType(out_order_type_id, order_type_name, command_id, command_name) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"SpOrderTypeCommandsService", "validateSpOrderTypeCommond", [out_order_type_id, command_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('�Ѵ��ڹ�������Ϊ " ' + order_type_name + '", ����������Ϊ " ' + command_name + '" �ļ�¼,������ѡ���ȡ��!') ;
			}
		});
		return flag;
  } 
  
//����ʱ����֤ Ŀ�������� ��Ψһ��
 function validateCommandPara(record_id, record_name, command_id, command_name) {
 		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall(
			"SpParaRecordService", "validateCommandPara", [record_id, command_id], function(r) {
			var result = r.getResult();
			if(!result) {
				flag = false;
				alert('��ѡ���: ' + command_name + ' �����������¼  " ' + record_name + '"  �д���,������ѡ���ȡ��!') ;
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
				<ui:field label="Ŀ��������" field="command_id"  visible="false" ></ui:field>
				<ui:field label="����" field="name"  visible="true" ></ui:field>
																
				<ui:field label="����" field="para_code"  visible="true" ></ui:field>
																
				<ui:field label="ȡֵ��ʽ" field="get_method"  visible="true" attrCode="get_method"></ui:field>
				<ui:field label="�Ƿ�ؼ�������" field="is_key"  visible="true" attrCode="is_key"></ui:field>	
				<ui:field label="������ڵ�" field="node_path"  visible="true" ></ui:field>
																
				<ui:field label="�ڵ�����" field="node_attr"  visible="true"></ui:field>
				<ui:field label="Ŀ¼" field="catalogname"  visible="true"></ui:field>
				<ui:parameter parameterId="name" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="para_code" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="command_catalog_id" type="string" value=""></ui:parameter>
			</ui:dataset>

	</div>
<div id="layoutDefine">
	<ui:panel type="modalDialog" desc="Ŀ��������">
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
