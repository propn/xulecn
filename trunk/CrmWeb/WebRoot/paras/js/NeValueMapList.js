/**
	�滻�޸�˵�� ��
	1.queryData() ������Ϣ
	2.NeValueMapListCheck() 
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
		dsNeValueMapListForm.clearData();
		
		//need-replace ��ѯ����,��Ҫ����ҵ��ʵ����Ҫ�趨��
		var params = dsNeValueMapListList.parameters() ;
		params.setValue('param1',dsQuery.getValue('param1'));
		params.setValue('param2',dsQuery.getValue('param2'));
		params.setValue('param3',dsQuery.getValue('param3'));
		
		dsNeValueMapListList.reloadData();
	}
	
	function insertNeValueMapList_onClick(){
		if(!statusCheck('insertNeValueMapList'))
			return ;
		dsNeValueMapListForm.clearData();
		dsNeValueMapListForm.setReadOnly(false) ;
		actionType = 'insertNeValueMapList';
	}
	
	function updateNeValueMapList_onClick(){
		if(!statusCheck('updateNeValueMapList'))
			return ;
		if(!duCheck()) return ;
		
		dsNeValueMapListForm.setReadOnly(false) ;
		actionType = 'updateNeValueMapList';
	}
	
	function NeValueMapListCheck(){
		return true ;
	}
	
	function saveNeValueMapList_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsNeValueMapListForm").validate()) return;
		if(!NeValueMapListCheck()) return ;
		var NeValueMapListVO = extractBeanFromDataset(dsNeValueMapListForm, 'java.util.HashMap');
		
		NDAjaxCall.getSyncInstance().remoteCall('NeValueMapListService',actionType,[NeValueMapListVO],
				function(replay){
			var result = replay.getResult() ;
			actionType = '';
			if(result == true ){
			   alert('�����ɹ�');
			   dsNeValueMapListForm.clearData();
			   dsNeValueMapListForm.setReadOnly(true);
			   queryData() ;
			}else{
			   alert('����ʧ��');
			}
			
		});
		
		
	}
	
	//ȡ��--������
	function cancelNeValueMapList_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsNeValueMapListForm.clearData();
		dsNeValueMapListForm.setReadOnly(true);
		dsNeValueMapListList_afterScroll();
	}
		
	
	function dsNeValueMapListList_afterScroll(){
		//�Ƿ�ȡ����ǰ������
		var cs = getCurrentStatus() ;
		if(cs != '' && confirm('ȷ��ȡ��'+cs)) 
			return ;
		
		actionType = '';
		
		dsNeValueMapListForm.setReadOnly(true);
		
		var crt = dsNeValueMapListList.getCurrent();
		if(!crt) return;
		var para = dsNeValueMapListForm.parameters() ; 
				
		dsNeValueMapListForm.reloadData() ;
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
			if(actionType == 'insertNeValueMapList'){
				alert('����ȡ������������') ;
				return false ;
			}
			if(actionType == 'updateNeValueMapList'){
				alert('����ȡ���޸Ĳ�����') ;
				return false ;
			}
		}
		return true ;
	}
	//��ȡ��ǰ״̬��������
	function getCurrentStatus(){
		if(actionType == 'insertNeValueMapList'){
			return '��������' ;
		}
		if(actionType == 'updateNeValueMapList'){
			return '�޸Ĳ���' ;
		}
		return '' ;
	}
	
	function deleteNeValueMapList_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('����ȡ��'+cs) ;
			return ;
		}
		var crt = dsNeValueMapListList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		if(!duCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("NeValueMapListService", "deleteNeValueMapListById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				queryData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}