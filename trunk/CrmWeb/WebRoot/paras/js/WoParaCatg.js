	function page_onLoad(){
		dsWoParaCatgList.reloadData();
	}
	function insertWoParaCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('ȷ��ȡ��'+cs)) 
			return ;
		dsSpParaInfoForm.setReadOnly(true);
		catgActionType = '';
        var back = openDialog("WoParaCatg.jsp",[],"","300px","200px");
        if(back!=null){
          page_onLoad();
        }
	}
	
	function updateWoParaCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('ȷ��ȡ��'+cs)) 
			return ;
		dsSpParaInfoForm.setReadOnly(true);
		catgActionType = '';
		var crt = dsWoParaCatgList.getCurrent();
		if(!crt) return;
		var para = dsSpParaInfoList.parameters() ; 
				
		var para_dir_id =  crt.getValue('para_dir_id');
		var back = openDialog("WoParaCatg.jsp",para_dir_id,"","300px","200px");
        if(back!=null){
          page_onLoad();
        }
	}

			
	
	function dsWoParaCatgList_afterScroll(){
		//�Ƿ�ȡ����ǰ������
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('ȷ��ȡ��'+cs)) 
			return ;
		dsSpParaInfoForm.setReadOnly(true);
		catgActionType = '';
		
		//dsWoParaCatgForm.setReadOnly(true);
		
		var crt = dsWoParaCatgList.getCurrent();
		if(!crt) return;
		var para = dsSpParaInfoList.parameters() ; 
				
		var para_dir_id =  crt.getValue('para_dir_id');
		para.setValue('para_dir_id' ,para_dir_id );
				
		dsSpParaInfoList.reloadData() ;
	}
	
	//ɾ�������¸�����������Ƿ�ѡ�м�¼
	var para = [] ;//�������ֵ����
	function cataDuCheck(){
		para = [] ;
		var crt = dsWoParaCatgList.getCurrent();	
		var para_dir_id =  crt.getValue('para_dir_id');
		if(para_dir_id == null || para_dir_id == ''){
			alert('para_dir_id����Ϊ��,��ѡ��ɾ�����ݼ�¼��');
			return false ;
		}
		para.push(para_dir_id) ;
		
				
		return true ;
	}
	function isRelate(){
		var isDelete = true;
		var crt = dsWoParaCatgList.getCurrent();
		var para_id = crt.getValue('para_dir_id');
		NDAjaxCall.getSyncInstance().remoteCall("WoParaCatgService", "isRelate",[para_id],function(r){
			var result = r.getResult() ;
			if(result){
				alert('ɾ�����ɹ�,����ɾ�������¼�Դ�����') ;
			}else isDelete = false;
		});
		return isDelete;
	}
	
	function deleteWoParaCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('����ȡ��'+cs) ;
			return ;
		}
		var crt = dsWoParaCatgList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		if(!cataDuCheck()) return ;
		if(isRelate()) return; //�ж��Ƿ��й�������
		NDAjaxCall.getAsyncInstance().remoteCall("WoParaCatgService", "deleteWoParaCatgById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ���') ;
				dsWoParaCatgList.reloadData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}