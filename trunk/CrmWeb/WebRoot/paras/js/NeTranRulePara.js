
	var actionType = 'insertNeTranRulePara';
	function page_onLoad(){
		var args = window.dialogArguments;
		dsNeTranRuleParaForm.setFieldReadOnly("para_name",true);
		dsNeTranRuleParaForm.setValue("tran_rule_id",args['tran_rule_id'])
		if('2' == args['type']){
			var param = dsNeTranRuleParaForm.parameters() ;
			param.setValue("tran_rule_id",args['tran_rule_id']);
			param.setValue('para_id',args['para_id']);
			param.setValue('seq',args['seq']);
			dsNeTranRuleParaForm.reloadData();
			actionType = 'updateNeTranRulePara';
		}
	}

	function saveNeTranRulePara_onClick(){
		if (!$("form_dsNeTranRuleParaForm").validate()) return;
		var NeTranRuleParaVO = extractBeanFromDataset(dsNeTranRuleParaForm, 'java.util.HashMap');
		var paramMap = {};
		var param = dsNeTranRuleParaForm.parameters() ;
		paramMap['tran_rule_id'] = param.getValue("tran_rule_id");
		paramMap['para_id'] = param.getValue("para_id");
		paramMap['seq'] = param.getValue("seq");
		var valueMap = {};
		valueMap['tran_rule_id'] = dsNeTranRuleParaForm.getValue("tran_rule_id");
		valueMap['para_id'] = dsNeTranRuleParaForm.getValue("para_id");
		valueMap['seq'] = dsNeTranRuleParaForm.getValue("seq");
		var seq= dsNeTranRuleParaForm.getValue("seq");
		if(/^\d+\.?\d*$/.test(seq)){
		}else{
			alert("������˳�����Ϊ����,����������");
			return;
		}
		if(actionType == 'insertNeTranRulePara' && !validateRepartPara(valueMap)) return;
		if(!validateRepartSeq(valueMap)) return;
		NDAjaxCall.getSyncInstance().remoteCall('NeTranRuleParaService',actionType,[NeTranRuleParaVO,paramMap],
				function(replay){
			var result = replay.getResult() ;
			if(result == true ){
			   alert('�����ɹ�');
			   window.returnValue=true;
			}else{
			   alert('����ʧ��');
			}
			
		});
		window.close();
	}
	function validateRepartPara(paramMap){
			var flag = true;
			NDAjaxCall.getSyncInstance().remoteCall('NeTranRuleParaService',"validateRepartPara",[paramMap],
					function(replay){
				var result = replay.getResult() ;
				if(result == 1){
				   flag= false;
				   alert('��ת������Դ�������Ѵ���');
				}
			});
			return flag;
		}	
	function validateRepartSeq(paramMap){
			var flag = true;
			if(actionType == 'insertNeTranRulePara')
				paramMap['para_id'] = "";
			NDAjaxCall.getSyncInstance().remoteCall('NeTranRuleParaService',"validateRepartSeq",[paramMap],
					function(replay){
				var result = replay.getResult() ;
				 if(result == 2){
				   flag= false;
				   alert('��������˳���Ѵ���');
				}
			});
			return flag;
		}
	//ȡ��--������
	function cancelNeTranRulePara_onClick(){
		window.close();
	}
	function button_dsNeTranRuleParaForm_para_name_onClick(){
 		var back = openDialog("queryParaInfo.jsp",[],"","600px","500px");
         if(back!=null){
           dsNeTranRuleParaForm.setValue("para_id",back['para_id']);
           dsNeTranRuleParaForm.setValue("para_name",back['paraname']);
         }
	}
	
	function deleteNeTranRulePara_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('����ȡ��'+cs) ;
			return ;
		}
		var crt = dsNeTranRuleParaList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		if(!duCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("NeTranRuleParaService", "deleteNeTranRuleParaById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				queryData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}