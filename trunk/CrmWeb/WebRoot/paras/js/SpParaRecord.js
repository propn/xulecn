
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
	function queryData(){
		dsSpParaRecordForm.clearData();
		
		//need-replace ��ѯ����,��Ҫ����ҵ��ʵ����Ҫ�趨��
		var params = dsSpParaRecordList.parameters() ;
		params.setValue('record_name',dsQuery.getValue('record_name'));
		params.setValue('record_path',dsQuery.getValue('record_path'));
		
		dsSpParaRecordList.reloadData();
	}
	
	function insertSpParaRecord_onClick(){
		if(!statusCheck('insertSpParaRecord'))
			return ;
		dsSpParaRecordForm.clearData();
		dsSpParaRecordForm.setReadOnly(false) ;
		actionType = 'insertSpParaRecord';
	}
	
	function updateSpParaRecord_onClick(){
		if(!statusCheck('updateSpParaRecord'))
			return ;
		if(!duCheck()) return ;
		
		dsSpParaRecordForm.setReadOnly(false) ;
		actionType = 'updateSpParaRecord';
	}
	
	function SpParaRecordCheck(){
		return true ;
	}
	
	function saveSpParaRecord_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsSpParaRecordForm").validate()) return;
		if(!SpParaRecordCheck()) return ;
		var SpParaRecordVO = extractBeanFromDataset(dsSpParaRecordForm, 'java.util.HashMap');
		
		//��3������ķǺ�����֤
		var groupby_codes = dsSpParaRecordForm.getValue("groupby_codes");
		if (!validateGroupbyCode(groupby_codes)) return;
		var orderby_asc_codes = dsSpParaRecordForm.getValue("orderby_asc_codes");
		if (!validateAscCode(orderby_asc_codes)) return;
		var orderby_desc_codes = dsSpParaRecordForm.getValue("orderby_desc_codes");
		if (!validateDescCode(orderby_desc_codes)) return;
		
		NDAjaxCall.getSyncInstance().remoteCall('SpParaRecordService',actionType,[SpParaRecordVO],
				function(replay){
			var result = replay.getResult() ;
			actionType = '';
			if(result == true ){
			   alert('�����ɹ�');
			   dsSpParaRecordForm.clearData();
			   dsSpParaRecordForm.setReadOnly(true);
			   queryData() ;
			}else{
			   alert('����ʧ��');
			}
			
		});
	}
	
	//��֤���벻�ܺ��к���
	function validateGroupbyCode(code) {
		var flag= true;
		if(code) {
			if(/^[\w\d]*$/.test(code)) {
			//if (/[u4E00-u9FA5]/.test(code)) {
	         	flag = true;
	         } else {
	         	flag = false;
	         	alert("�������ֻ��Ϊ��ĸ�����ֺ��»��ߵ��ַ������!");
	         }
		}
		return flag;
	}	
	
	function validateAscCode(code) {
		var flag= true;
		if(code) {
			if(/^[\w\d]*$/.test(code)) {
			//if (/[u4E00-u9FA5]/.test(code)) {
	         	flag = true;
	         } else {
	         	flag = false;
	         	alert("���������ֻ��Ϊ��ĸ�����ֺ��»��ߵ��ַ������!");
	         }
		}
		return flag;
	}	
	
	function validateDescCode(code) {
		var flag= true;
		if(code) {
			if(/^[\w\d]*$/.test(code)) {
			//if (/[u4E00-u9FA5]/.test(code)) {
	         	flag = true;
	         } else {
	         	flag = false;
	         	alert("�����������ֻ��Ϊ��ĸ�����ֺ��»��ߵ��ַ������!");
	         }
		}
		return flag;
	}	
	
	//ȡ��--������
	function cancelSpParaRecord_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsSpParaRecordForm.clearData();
		dsSpParaRecordForm.setReadOnly(true);
		dsSpParaRecordList_afterScroll();
	}
		
	
	function dsSpParaRecordList_afterScroll(){
		//�Ƿ�ȡ����ǰ������
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('ȷ��ȡ��'+cs)) 
			return ;
		
		actionType = '';
		
		dsSpParaRecordForm.setReadOnly(true);
		
		var crt = dsSpParaRecordList.getCurrent();
		if(!crt) return;
		var para = dsSpParaRecordForm.parameters() ; 
				
		var record_id =  crt.getValue('record_id');
		para.setValue('record_id' ,record_id );
				
		dsSpParaRecordForm.reloadData() ;
		var listpara = dsSpParaRecordListList.parameters() ; 
		listpara.setValue('record_id' ,record_id );
		dsSpParaRecordListList.reloadData();
	}
	
	//ɾ�������¸�����������Ƿ�ѡ�м�¼
	var para = [] ;//�������ֵ����
	function duCheck(){
		para = [] ;
				
		var record_id =  dsSpParaRecordForm.getValue('record_id');
		if(record_id == null || record_id == ''){
			alert('record_id����Ϊ��,��ѡ��ɾ�����ݼ�¼��');
			return false ;
		}
		para.push(record_id) ;
		
				
		return true ;
	}
	//�������޸�ʱ����Ҫ����״̬���
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertSpParaRecord'){
				alert('����ȡ������������') ;
				return false ;
			}
			if(actionType == 'updateSpParaRecord'){
				alert('����ȡ���޸Ĳ�����') ;
				return false ;
			}
		}
		return true ;
	}
	//��ȡ��ǰ״̬��������
	function getCurrentStatus(){
		if(actionType == 'insertSpParaRecord'){
			return '��������' ;
		}
		if(actionType == 'updateSpParaRecord'){
			return '�޸Ĳ���' ;
		}
		return '' ;
	}
	function text_dsSpParaRecordForm_record_path_onUpdate(editor){
		if(editor.value){
			dsSpParaRecordForm.setValue("target_path",editor.value);
		}
		return true;
	}
	function isRelate(){
		var isdelete = true;
		var crt = dsSpParaRecordList.getCurrent();
		var para_id = crt.getValue('record_id');
		NDAjaxCall.getSyncInstance().remoteCall("SpParaRecordListService", "isRelate",[para_id],function(r){
			var result = r.getResult() ;
			if(result){
				alert('ɾ�����ɹ�,����ɾ������Ŀ���������б�') ;
			}else isdelete = false;
		});
		return isdelete;
	}
	function deleteSpParaRecord_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('����ȡ��'+cs) ;
			return ;
		}
		var crt = dsSpParaRecordList.getCurrent();
		if(!crt){
			return;
		}
		
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		
		////�����������¼��ʶ��SP_ORDER_Type_RECORDS����Ƿ񱻹�����������
		var recordId = crt.getValue('record_id');
		if(!validateRecordId(recordId)) return;
		
		if(!duCheck()) return ;
		if(isRelate()) return ;
		NDAjaxCall.getSyncInstance().remoteCall("SpParaRecordService", "deleteSpParaRecordById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				queryData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}
	
	//�����������¼��ʶ��SP_ORDER_Type_RECORDS����Ƿ񱻹�����������
	function validateRecordId(recordId) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("SpParaRecordService", "validateRecordId",[recordId],
				function(r){
					var result = r.getResult();
					if(result && result != "-1"){
						flag = false;//һ��Ҫ��alert֮ǰ��ֵ����Ȼ��������ء�
						alert('���������¼����������   ' + result + ' ������������ɾ����');
					}
				}
		);
		return flag;
	}
	
	function insertParaRecordList_onClick(){
		var crt = dsSpParaRecordList.getCurrent();
		if(!crt){
			return;
		}
		var record_id = crt.getValue("record_id");
		var record_name = crt.getValue("record_name");
		var record_flag = "insertParaRecord";//��־λ������queryNeCommandPara.jspҳ�棬�Ա�ʶĳ�δ�����SpParaRecord.js�е�insertParaRecordList_onClickר��
  		var back = openDialog("queryNeCommandPara.jsp",[record_flag,record_id,record_name],"","600px","500px");
  		
        if(back!=null){
         	var commandparam = {};
         	commandparam['command_id'] = back['command_id'];
         	commandparam['record_id'] = record_id;
			NDAjaxCall.getSyncInstance().remoteCall('SpParaRecordListService','insertSpParaRecordList',[commandparam],function(replay){
				var result = replay.getResult() ;
				if(result == true ){
				    alert('�����ɹ�');
					var sppara = dsSpParaRecordListList.parameters() ; 
					sppara.setValue('record_id' ,record_id );
					dsSpParaRecordListList.reloadData();
				}else{
				   alert('����ʧ��');
				}
			});
          }
	}
	function deleteParaRecordList_onClick(){
		//dsSpParaRecordListList.reloadData();
		var crt = dsSpParaRecordListList.getCurrent();
		if(!crt){
			return;
		}
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		var recordId = crt.getValue('record_id');
		var commandId = crt.getValue('command_id');
		NDAjaxCall.getAsyncInstance().remoteCall("SpParaRecordListService", "deleteSpParaRecordListById",[recordId,commandId],function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				dsSpParaRecordListList.reloadData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}