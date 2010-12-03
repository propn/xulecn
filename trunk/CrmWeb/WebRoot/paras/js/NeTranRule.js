	var actionType = '';

	function page_onLoad(){
		queryData() ;
	}

	function btn_query_onClick(){
		queryData() ;
	}
	function btn_reset_onClick(){
		dsQuery.clearData();
	}
	function valid_date(start,end){
		if((null !=start||''!=start) && (null ==end||''==end))
			return true;
		var start_t = start.replace(/\-/g,"");
		var end_t = end.replace(/\-/g,"");
		if(start_t>end_t){
			alert("��ʼ����ʱ�䲻�ܴ�����ֹ����ʱ��");
			return false;
		}else return true;
	}

	function queryData(){
		dsNeTranRuleForm.clearData();
		var create_date =dsQuery.getValue('create_date');
		var end_date =dsQuery.getValue('end_date');
		if(!valid_date(create_date,end_date)) return;
		
		var params = dsNeTranRuleList.parameters() ;
		params.setValue('name',dsQuery.getValue('name'));
		params.setValue('create_date',create_date);
		params.setValue('end_date',end_date);
		
		dsNeTranRuleList.reloadData();
	}
	
	function insertNeTranRule_onClick(){
		if(!statusCheck('insertNeTranRule'))
			return ;
		dsNeTranRuleForm.clearData();
		dsNeTranRuleForm.setReadOnly(false) ;
		dsNeTranRuleForm.setFieldReadOnly("engine_name",true) ;
		dsNeTranRuleForm.setFieldReadOnly("map_type_name",true) ;
		dsNeTranRuleForm.setFieldReadOnly("business_obj_name",true) ;
		actionType = 'insertNeTranRule';
	}
	
	function updateNeTranRule_onClick(){
		if(!statusCheck('updateNeTranRule'))
			return ;
		if(!duCheck()) return ;
		
		dsNeTranRuleForm.setReadOnly(false) ;
		dsNeTranRuleForm.setFieldReadOnly("map_type_name",true) ;
		dsNeTranRuleForm.setFieldReadOnly("business_obj_name",true) ;
		dsNeTranRuleForm.setFieldReadOnly("engine_name",true) ;
		actionType = 'updateNeTranRule';
	}
	
	function NeTranRuleCheck(){
		return true ;
	}
	function validByMethod(){
		var get_method =dsNeTranRuleForm.getValue("get_method"); 
		var isok = true;
		if('200' ==get_method){
			var map_type_name =dsNeTranRuleForm.getValue("map_type_name"); 
			if(null == map_type_name || '' == map_type_name){
				isok = false;
				alert("ӳ�����Ͳ���Ϊ��");
			}
		}else if('300' ==get_method){
			var engine_name =dsNeTranRuleForm.getValue("engine_name"); 
			if(null == engine_name || '' == engine_name){
				isok = false;
				alert("��̬�������Ϊ��");
			}
		}else if('400' ==get_method){
			var business_obj_name =dsNeTranRuleForm.getValue("business_obj_name"); 
			if(null == business_obj_name || '' == business_obj_name){
				isok = false;
				alert("��̬�������Ϊ��");
			}
		}else if('500' ==get_method || '600' ==get_method){
			var execute_sql =dsNeTranRuleForm.getValue("execute_sql"); 
			if(null == execute_sql || '' == execute_sql){
				isok = false;
				alert("��̬sql����Ϊ��");
			}
		}
		return isok;
	}
	function saveNeTranRule_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsNeTranRuleForm").validate()) return;
		if(!NeTranRuleCheck()) return ;
		if(!validByMethod()) return ;
		var NeTranRuleVO = extractBeanFromDataset(dsNeTranRuleForm, 'java.util.HashMap');
		
		NDAjaxCall.getSyncInstance().remoteCall('NeTranRuleService',actionType,[NeTranRuleVO],
				function(replay){
			var result = replay.getResult() ;
			
			if(result == true ){
			   alert('�����ɹ�');
			   dsNeTranRuleForm.clearData();
			   dsNeTranRuleForm.setReadOnly(true);
			   if(actionType == 'insertNeTranRule'){
			   		alert("�����ת������Դ������");
			   }
			   actionType = '';
			   queryData() ;
			}else{
			   alert('����ʧ��');
			}
		});
	}
	
	//ȡ��--������
	function cancelNeTranRule_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsNeTranRuleForm.clearData();
		dsNeTranRuleForm.setReadOnly(true);
		dsNeTranRuleList_afterScroll();
	}
		
	
	function dsNeTranRuleList_afterScroll(){
		//�Ƿ�ȡ����ǰ������
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('ȷ��ȡ��'+cs)) 
			return ;
		
		actionType = '';
		
		dsNeTranRuleForm.setReadOnly(true);
		
		var crt = dsNeTranRuleList.getCurrent();
		if(!crt) return;
		var para = dsNeTranRuleForm.parameters() ; 
				
		var tran_rule_id =  crt.getValue('tran_rule_id');
		para.setValue('tran_rule_id' ,tran_rule_id );
				
		dsNeTranRuleForm.reloadData() ;
		
		var params = dsNeTranRuleParaList.parameters() ;
		params.setValue('tran_rule_id',tran_rule_id);
		dsNeTranRuleParaList.reloadData();
	}
	
	//ɾ�������¸�����������Ƿ�ѡ�м�¼
	var para = [] ;//�������ֵ����
	function duCheck(){
		para = [] ;
				
		var tran_rule_id =  dsNeTranRuleForm.getValue('tran_rule_id');
		if(tran_rule_id == null || tran_rule_id == ''){
			alert('tran_rule_id����Ϊ��,��ѡ��ɾ�����ݼ�¼��');
			return false ;
		}
		para.push(tran_rule_id) ;
		
				
		return true ;
	}
	//�������޸�ʱ����Ҫ����״̬���
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertNeTranRule'){
				alert('����ȡ������������') ;
				return false ;
			}
			if(actionType == 'updateNeTranRule'){
				alert('����ȡ���޸Ĳ�����') ;
				return false ;
			}
		}
		return true ;
	}
	//��ȡ��ǰ״̬��������
	function getCurrentStatus(){
		if(actionType == 'insertNeTranRule'){
			return '��������' ;
		}
		if(actionType == 'updateNeTranRule'){
			return '�޸Ĳ���' ;
		}
		return '' ;
	}
	function isRelateByCommPara(){
		var isdelete = true;
		var crt = dsNeTranRuleForm.getCurrent();
		var para_id = crt.getValue('tran_rule_id');
		NDAjaxCall.getSyncInstance().remoteCall("NeTranRuleService", "isRelateByCommPara",[para_id],function(r){
			var result = r.getResult() ;
			if(result){
				if(confirm('��ת������Ŀ��������'+result+'���ã��Ƿ����ɾ��')){
					isdelete = false;
				}else isdelete = true;
			}
		});
		return isdelete;
	}
	function isRelateByRulePara(){
		var isdelete = true;
		var crt = dsNeTranRuleForm.getCurrent();
		var para_id = crt.getValue('tran_rule_id');
		NDAjaxCall.getSyncInstance().remoteCall("NeTranRuleService", "isRelateByRulePara",[para_id],function(r){
			var result = r.getResult() ;
			if(result){
				isdelete = false;
				alert('ɾ�����ɹ�,����ɾ�������¼�Դ�����')
			}
		});
		return isdelete;
	}
	function deleteNeTranRule_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('����ȡ��'+cs) ;
			return ;
		}
		var crt = dsNeTranRuleList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		if(!duCheck()) return ;
		if(!isRelateByRulePara()) return ;
		if(!isRelateByCommPara()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("NeTranRuleService", "deleteNeTranRuleById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				queryData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}
	
	function button_dsNeTranRuleForm_map_type_name_onClick(){
		var back = openDialog("queryNeParaMapType.jsp","","","700px","720px");
         if(back!=null){
           dsNeTranRuleForm.setValue("map_type_id",back['map_type_id']);
           dsNeTranRuleForm.setValue("map_type_name",back['map_type_name']);
         }
	}
	function button_dsNeTranRuleForm_engine_name_onClick(){
		var back = openDialog("queryNeJavaEngine.jsp","","","600px","400px");
         if(back!=null){
           dsNeTranRuleForm.setValue("id",back['id']);
           dsNeTranRuleForm.setValue("engine_name",back['name']);
         }
	}
	function button_dsNeTranRuleForm_business_obj_name_onClick(){
		
		var para={};
		para['type'] = 2 ;
		para['business_obj_id'] =dsNeTranRuleForm.getValue("business_obj_id");
		var back = openDialog("../module/queryPbBussinessObj.jsp",para,"","600px","500px");
		if(null != back){
			dsNeTranRuleForm.setValue("business_obj_id",back["business_obj_id"]);
			dsNeTranRuleForm.setValue("business_obj_name",back["obj_name"]);
		}
	}
	// ת������Դ������
	function insertNeTranRulePara_onClick(){
		var crt = dsNeTranRuleList.getCurrent();
		if(!crt){
			alert("��ѡ��ת������");
			return;
		}
		var para = {};
		var tran_rule_id =  crt.getValue('tran_rule_id');
		para['tran_rule_id']	=tran_rule_id;
		para['type'] = 1; //1Ϊ����
 		var back = openDialog("NeTranRulePara.jsp",para,"","600px","500px");
         if(back!=null){
         	var para = dsNeTranRuleParaList.parameters() ;
         	para.setValue("tran_rule_id",tran_rule_id); 
           dsNeTranRuleParaList.reloadData() ;
         }
	}
	
	function updateNeTranRulePara_onClick(){
		var crt = dsNeTranRuleParaList.getCurrent();
		if(!crt){
			alert("��ѡ��ת������Դ������");
			return;
		}
		var tran_rule_id =  crt.getValue('tran_rule_id');
		var para = {};
		var para_id =  crt.getValue('para_id');
		var seq =  crt.getValue('seq');
		para['tran_rule_id']=tran_rule_id;
		para['para_id']	=para_id;
		para['seq'] = seq;
		para['type'] = 2; //2Ϊ�޸�
		var back = openDialog("NeTranRulePara.jsp",para,"","600px","500px");
         if(back!=null){
           dsNeTranRuleParaList.reloadData() ;
         }
	}
	function deleteNeTranRulePara_onClick(){
		var crt = dsNeTranRuleParaList.getCurrent();
		if(!crt){
			alert("��ѡ��ת������Դ������");
			return;
		}
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		var para = {};
		var tran_rule_id =  crt.getValue('tran_rule_id');
		var para_id =  crt.getValue('para_id');
		var seq =  crt.getValue('seq');
		para['tran_rule_id']=tran_rule_id;
		para['para_id']	=para_id;
		NDAjaxCall.getAsyncInstance().remoteCall("NeTranRuleParaService", "deleteNeTranRuleParaById",[para],function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				dsNeTranRuleParaList.reloadData() ;
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}