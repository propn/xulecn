
	var actionType = '';

	function btn_query_onClick(){
		queryData() ;
	}
	function btn_reset_onClick(){
		dsQuery.clearData();
	}
	function queryData(){
		dsNeCommandParaForm.clearData();
		var crt = dsNeCommandCatgList.getCurrent();
		if(!crt) return;	
		var command_catalog_id =  crt.getValue('command_catalog_id');
		if(null == command_catalog_id ||'' == command_catalog_id){
			alert("��ѡ��Ŀ��������Ŀ¼");
			return ;
		}
		var params = dsNeCommandParaList.parameters() ;
		params.setValue('name',dsQuery.getValue('name'));
		params.setValue('para_code',dsQuery.getValue('para_code'));
		params.setValue('command_catalog_id',command_catalog_id);
		
		dsNeCommandParaList.reloadData();
	}
	
	function insertNeCommandPara_onClick(){
		if(!statusCheck('insertNeCommandPara'))
			return ;
		dsNeCommandParaForm.clearData();
		dsNeCommandParaForm.setReadOnly(false) ;
		dsNeCommandParaForm.setFieldReadOnly("paraname",true);
		dsNeCommandParaForm.setFieldReadOnly("rulename",true);
		actionType = 'insertNeCommandPara';
	}
	
	function updateNeCommandPara_onClick(){
		if(!statusCheck('updateNeCommandPara'))
			return ;
		if(!duCheck()) return ;
		
		dsNeCommandParaForm.setReadOnly(false) ;
		dsNeCommandParaForm.setFieldReadOnly("paraname",true);
		dsNeCommandParaForm.setFieldReadOnly("rulename",true);
		actionType = 'updateNeCommandPara';
	}
	
	function NeCommandParaCheck(){
		return true ;
	}
	function validByType(){
		var isok = true;
		var get_method = dsNeCommandParaForm.getValue("get_method");
		if('100' == get_method){
			var paraname = dsNeCommandParaForm.getValue("paraname");
			if(null == paraname || ''==paraname){
				isok = false;
				alert("��ѡ��Դ������!");
			}
		}else if('200' == get_method){
			var rulename = dsNeCommandParaForm.getValue("rulename");
			if(null == rulename || ''==rulename){
				isok = false;
				alert("��ѡ��ת������!");
			}
		}else if('300' == get_method){
			var default_value = dsNeCommandParaForm.getValue("default_value");
			if(null == default_value || ''==default_value){
				isok = false;
				alert("��ֵ̬����Ϊ��!");
			}
		}
		return isok;
	}
	function saveNeCommandPara_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsNeCommandParaForm").validate()) return;
		if(!NeCommandParaCheck()) return ;
		var catlogId = dsNeCommandCatgList.getCurrent().getValue("command_catalog_id");//��ǰĿ¼idֵ
		if(!catlogId){
			alert("��ѡ��Ŀ¼");
		 return ;
		 }
		 if(!validByType()) return ;
		dsNeCommandParaForm.setValue('command_catalog_id',catlogId);
		var NeCommandParaVO = extractBeanFromDataset(dsNeCommandParaForm, 'java.util.HashMap');
		
		//��֤���벻�ܺ��к���
		var para_code = dsNeCommandParaForm.getValue("para_code");
		if (!validateCode(para_code)) return;
		
		var command_id = dsNeCommandParaForm.getValue("command_id");
		//��֤�����Ψһ��
		if(!validateParaCode(catlogId, para_code, command_id))return;
		
		NDAjaxCall.getSyncInstance().remoteCall('NeCommandParaService',actionType,[NeCommandParaVO],
				function(replay){
			var result = replay.getResult() ;
			actionType = '';
			if(result == true ){
			   alert('�����ɹ�');
			   dsNeCommandParaForm.clearData();
			   dsNeCommandParaForm.setReadOnly(true);
			   queryData() ;
			}else{
			   alert('����ʧ��');
			}
			
		});
	}
	
	//��֤���벻�ܺ��к���
	function validateCode(code) {
		var flag= true;
		if(code) {
			if(/^[\w\d]*$/.test(code)) {
			//if (/[u4E00-u9FA5]/.test(code)) {
	         	flag = true;
	         } else {
	         	flag = false;
	         	alert("����ֻ��Ϊ��ĸ�����֡��»��ߵ����!");
	         }
		}
		return flag;
	}
	
	//��֤�����Ψһ��	
	function validateParaCode(command_catalog_id, para_code,command_id) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandParaService", "validateParaCode",[command_catalog_id, para_code,command_id],function(r){
			var result = r.getResult();
			if(!result){
				flag = false;
				alert('�����Ѵ��ڣ�������¼��!') ;
			}
		});
		return flag;
	}
	
	//ȡ��--������
	function cancelNeCommandPara_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsNeCommandParaForm.clearData();
		dsNeCommandParaForm.setReadOnly(true);
		dsNeCommandParaList_afterScroll();
	}
		
	
	function dsNeCommandParaList_afterScroll(){
		//�Ƿ�ȡ����ǰ������
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('ȷ��ȡ��'+cs)) 
			return ;
		
		actionType = '';
		
		dsNeCommandParaForm.setReadOnly(true);
		
		var crt = dsNeCommandParaList.getCurrent();
		if(!crt) return;
		var para = dsNeCommandParaForm.parameters() ; 
				
		var command_id =  crt.getValue('command_id');
		para.setValue('command_id' ,command_id );
				
		dsNeCommandParaForm.reloadData() ;
	}
	
	//ɾ�������¸�����������Ƿ�ѡ�м�¼
	var para = [] ;//�������ֵ����
	function duCheck(){
		para = [] ;
				
		var command_id =  dsNeCommandParaForm.getValue('command_id');
		if(command_id == null || command_id == ''){
			alert('command_id����Ϊ��,��ѡ��ɾ�����ݼ�¼��');
			return false ;
		}
		para.push(command_id) ;
		
				
		return true ;
	}
	//�������޸�ʱ����Ҫ����״̬���
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertNeCommandPara'){
				alert('����ȡ������������') ;
				return false ;
			}
			if(actionType == 'updateNeCommandPara'){
				alert('����ȡ���޸Ĳ�����') ;
				return false ;
			}
		}
		return true ;
	}
	//��ȡ��ǰ״̬��������
	function getCurrentStatus(){
		if(actionType == 'insertNeCommandPara'){
			return '��������' ;
		}
		if(actionType == 'updateNeCommandPara'){
			return '�޸Ĳ���' ;
		}
		return '' ;
	}
	
	function deleteNeCommandPara_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('����ȡ��'+cs) ;
			return ;
		}
		var crt = dsNeCommandParaList.getCurrent();
		if(!crt){
			return;
		}
		
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		
		var command_id = crt.getValue("command_id");
		if (!validateForParaRecord(command_id)) return;//����Ƿ��������¼����
		if (!validateForOrderType(command_id)) return;//����Ƿ񱻹�����������
		if(!validateForTemplatePara(command_id))return;//����Ƿ�ָ��ģ������
		if(!duCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("NeCommandParaService", "deleteNeCommandParaById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				queryData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}
	
	//����Ƿ��������¼����
	function validateForParaRecord(command_id) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandParaService", "validateForParaRecord",[command_id],function(r){
			var result = r.getResult();
			if(result && result != '-1'){
				flag = false;
				alert('��Ŀ�������  "' + result + '" �������¼���ã�������ɾ��!') ;
			}
		});
		return flag;
	}
	
	//����Ƿ񱻹�����������
	function validateForOrderType(command_id) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandParaService", "validateForOrderType",[command_id],function(r){
			var result = r.getResult();
			if(result && result != '-1'){
				flag = false;
				alert('��Ŀ�������  "' + result + '" �����������ã�������ɾ��!') ;
			}
		});
		return flag;
	}
	//����Ƿ�ָ��ģ������
	function validateForTemplatePara(command_id) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandParaService", "validateForTemplatePara",[command_id],function(r){
			var result = r.getResult();
			if(result && result != '-1'){
				flag = false;
				alert('��Ŀ�������  "' + result + '" ָ��ģ�����ã�������ɾ��!') ;
			}
		});
		return flag;
	}
	
	function button_dsNeCommandParaForm_paraname_onClick(){
         var back = openDialog("queryParaInfo.jsp",[],"","600px","500px");
         if(back!=null){
           dsNeCommandParaForm.setValue('para_id',back["para_id"]);
           dsNeCommandParaForm.setValue('paraname',back["paraname"]);
           dsNeCommandParaForm.setValue('node_path',back["node_path"]);
           }
	}
	function button_dsNeCommandParaForm_rulename_onClick(){
         var back = openDialog("queryNeTranRule.jsp",[],"","600px","500px");
         if(back!=null){
           dsNeCommandParaForm.setValue('tran_rule_id',back["tran_rule_id"]);
           dsNeCommandParaForm.setValue('rulename',back["rulename"]);
           }
	}