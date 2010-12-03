/**
	�滻�޸�˵�� ��
	1.queryData() ������Ϣ
	2.SpParaRecordListCheck() 
	3. �滻��ϣ�ɾ���˶����֣�
 */
	var actionType = '';

	function page_onLoad(){
		queryData() ;
	}

	function btn_query_onClick(){
		queryData() ;
	}
	function queryData(){
		dsSpParaRecordListForm.clearData();
		
		//need-replace ��ѯ����,��Ҫ����ҵ��ʵ����Ҫ�趨��
		var params = dsSpParaRecordListList.parameters() ;
		params.setValue('param1',dsQuery.getValue('param1'));
		params.setValue('param2',dsQuery.getValue('param2'));
		params.setValue('param3',dsQuery.getValue('param3'));
		
		dsSpParaRecordListList.reloadData();
	}
	
	function insertSpParaRecordList_onClick(){
		if(!statusCheck('insertSpParaRecordList'))
			return ;
		dsSpParaRecordListForm.clearData();
		dsSpParaRecordListForm.setReadOnly(false) ;
		actionType = 'insertSpParaRecordList';
	}
	
	function updateSpParaRecordList_onClick(){
		if(!statusCheck('updateSpParaRecordList'))
			return ;
		if(!duCheck()) return ;
		
		dsSpParaRecordListForm.setReadOnly(false) ;
		actionType = 'updateSpParaRecordList';
	}
	
	function SpParaRecordListCheck(){
		return true ;
	}
	
	function saveSpParaRecordList_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsSpParaRecordListForm").validate()) return;
		if(!SpParaRecordListCheck()) return ;
		var SpParaRecordListVO = extractBeanFromDataset(dsSpParaRecordListForm, 'java.util.HashMap');
		
		NDAjaxCall.getSyncInstance().remoteCall('SpParaRecordListService',actionType,[SpParaRecordListVO],
				function(replay){
			var result = replay.getResult() ;
			actionType = '';
			if(result == true ){
			   alert('�����ɹ�');
			   dsSpParaRecordListForm.clearData();
			   dsSpParaRecordListForm.setReadOnly(true);
			   queryData() ;
			}else{
			   alert('����ʧ��');
			}
			
		});
		
		
	}
	
	//ȡ��--������
	function cancelSpParaRecordList_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsSpParaRecordListForm.clearData();
		dsSpParaRecordListForm.setReadOnly(true);
		dsSpParaRecordListList_afterScroll();
	}
		
	
	function dsSpParaRecordListList_afterScroll(){
		//�Ƿ�ȡ����ǰ������
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('ȷ��ȡ��'+cs)) 
			return ;
		
		actionType = '';
		
		dsSpParaRecordListForm.setReadOnly(true);
		
		var crt = dsSpParaRecordListList.getCurrent();
		if(!crt) return;
		var para = dsSpParaRecordListForm.parameters() ; 
				
		dsSpParaRecordListForm.reloadData() ;
	}
	
	//ɾ�������¸�����������Ƿ�ѡ�м�¼
	var para = [] ;//�������ֵ����
	function duCheck(){
		para = [] ;
				
		return true ;
	}
	//�������޸�ʱ����Ҫ����״̬���
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertSpParaRecordList'){
				alert('����ȡ������������') ;
				return false ;
			}
			if(actionType == 'updateSpParaRecordList'){
				alert('����ȡ���޸Ĳ�����') ;
				return false ;
			}
		}
		return true ;
	}
	//��ȡ��ǰ״̬��������
	function getCurrentStatus(){
		if(actionType == 'insertSpParaRecordList'){
			return '��������' ;
		}
		if(actionType == 'updateSpParaRecordList'){
			return '�޸Ĳ���' ;
		}
		return '' ;
	}
	
	function deleteSpParaRecordList_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('����ȡ��'+cs) ;
			return ;
		}
		var crt = dsSpParaRecordListList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		if(!duCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("SpParaRecordListService", "deleteSpParaRecordListById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				queryData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}