	var actionType = '';
	
	function insertSpParaInfo_onClick(){
		if(!statusCheck('insertSpParaInfo'))
			return ;
		if(!dsWoParaCatgList.getCurrent()){
			alert("��������Դ������Ŀ¼");
			return;
		}
			
		dsSpParaInfoForm.clearData();
		dsSpParaInfoForm.setReadOnly(false) ;
		actionType = 'insertSpParaInfo';
	}
	
	function updateSpParaInfo_onClick(){
		if(!statusCheck('updateSpParaInfo'))
			return ;
		if(!duCheck()) return ;
		
		dsSpParaInfoForm.setReadOnly(false) ;
		actionType = 'updateSpParaInfo';
	}
	
	function SpParaInfoCheck(){
		return true ;
	}
	
	function saveSpParaInfo_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsSpParaInfoForm").validate()) return;
		if(!SpParaInfoCheck()) return ;
		var paradirid = dsWoParaCatgList.getCurrent().getValue("para_dir_id");
		if(!paradirid){
			alert("��ѡ��Ŀ¼");
		 return ;
		 }
		dsSpParaInfoForm.setValue('para_dir_id',paradirid);
		var SpParaInfoVO = extractBeanFromDataset(dsSpParaInfoForm, 'java.util.HashMap');
		
		var para_code = dsSpParaInfoForm.getValue("para_code");
		if (!validateCode(para_code)) return;//�������������зǺ�����У��
		
		NDAjaxCall.getSyncInstance().remoteCall('SpParaInfoService',actionType,[SpParaInfoVO],
				function(replay){
			var result = replay.getResult() ;
			actionType = '';
			if(result == true ){
			   alert('�����ɹ�');
			   dsSpParaInfoForm.clearData();
			   dsSpParaInfoForm.setReadOnly(true);
			   dsSpParaInfoList.reloadData();
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
	         	alert("���������ֻ��Ϊ��ĸ�����֡��»��ߵ����!");
	         }
		}
		return flag;
	}
	
	//ȡ��--������
	function cancelSpParaInfo_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsSpParaInfoForm.clearData();
		dsSpParaInfoForm.setReadOnly(true);
		dsSpParaInfoList_afterScroll();
	}
		
	
	function dsSpParaInfoList_afterScroll(){
		//�Ƿ�ȡ����ǰ������
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('ȷ��ȡ��'+cs)) 
			return ;
		
		actionType = '';
		
		dsSpParaInfoForm.setReadOnly(true);
		
		var crt = dsSpParaInfoList.getCurrent();
		if(!crt) return;
		var para = dsSpParaInfoForm.parameters() ; 
				
		var para_id =  crt.getValue('para_id');
		para.setValue('para_id' ,para_id );
				
		dsSpParaInfoForm.reloadData() ;
	}
	
	//ɾ�������¸�����������Ƿ�ѡ�м�¼
	var para = [] ;//�������ֵ����
	function duCheck(){
		para = [] ;
				
		var para_id =  dsSpParaInfoForm.getValue('para_id');
		if(para_id == null || para_id == ''){
			alert('para_id����Ϊ��,��ѡ��ɾ�����ݼ�¼��');
			return false ;
		}
		para.push(para_id) ;
		
				
		return true ;
	}
	//�������޸�ʱ����Ҫ����״̬���
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertSpParaInfo'){
				alert('����ȡ������������') ;
				return false ;
			}
			if(actionType == 'updateSpParaInfo'){
				alert('����ȡ���޸Ĳ�����') ;
				return false ;
			}
		}
		return true ;
	}
	//��ȡ��ǰ״̬��������
	function getCurrentStatus(){
		if(actionType == 'insertSpParaInfo'){
			return '��������' ;
		}
		if(actionType == 'updateSpParaInfo'){
			return '�޸Ĳ���' ;
		}
		return '' ;
	}
	
	function deleteSpParaInfo_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('����ȡ��'+cs) ;
			return ;
		}
		var crt = dsSpParaInfoList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		if(!duCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("SpParaInfoService", "deleteSpParaInfoById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				dsSpParaInfoList.reloadData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}