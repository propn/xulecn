
	function page_onLoad(){
		dsNeCommandCatgList.reloadData();
	}
	function insertNeCommandCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' && confirm('ȷ��ȡ��'+cs)) 
			return ;
		dsNeCommandParaForm.setReadOnly(true);
		catgActionType = '';
        var back = openDialog("NeCommandCatg.jsp",[],"","300px","200px");
        if(back!=null){
          page_onLoad();
        }
	}
	
	function updateNeCommandCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' && confirm('ȷ��ȡ��'+cs)) 
			return ;
		dsNeCommandParaForm.setReadOnly(true);
		catgActionType = '';
		var crt = dsNeCommandCatgList.getCurrent();
		if(!crt) return;
		var para = dsNeCommandParaList.parameters() ; 
				
		var command_catalog_id =  crt.getValue('command_catalog_id');
		var back = openDialog("NeCommandCatg.jsp",command_catalog_id,"","300px","200px");
        if(back!=null){
          page_onLoad();
        }
	}
	function dsNeCommandCatgList_afterScroll(){
		//�Ƿ�ȡ����ǰ������
		var cs = getCurrentStatus() ;
		if(cs != '' && confirm('ȷ��ȡ��'+cs)) 
			return ;
		
		actionType = '';
		
		//dsNeCommandCatgForm.setReadOnly(true);
		
		var crt = dsNeCommandCatgList.getCurrent();
		if(!crt) return;
		var para = dsNeCommandParaList.parameters() ; 
		
		var command_catalog_id =  crt.getValue('command_catalog_id');
		para.setValue('command_catalog_id' ,command_catalog_id );
				
		dsNeCommandParaList.reloadData() ;
		dsNeCommandParaForm.clearData();
		
	}
	
	//ɾ�������¸�����������Ƿ�ѡ�м�¼
	var para = [] ;//�������ֵ����
	function catgDduCheck(){
		para = [] ;
		var crt = dsNeCommandCatgList.getCurrent();
		var command_catalog_id = crt.getValue('command_catalog_id');
		if(command_catalog_id == null || command_catalog_id == ''){
			alert('command_catalog_id����Ϊ��,��ѡ��ɾ�����ݼ�¼��');
			return false ;
		}
		para.push(command_catalog_id) ;
		
				
		return true ;
	}
	function isRelate(){
		var isdelete = true;
		var crt = dsNeCommandCatgList.getCurrent();
		var para_id = crt.getValue('command_catalog_id');
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandCatgService", "isRelate",[para_id],function(r){
			var result = r.getResult() ;
			if(result){
				alert('ɾ�����ɹ�,����ɾ�������¼�Դ�����') ;
			}else isdelete = false;
		});
		return isdelete;
	}
	function deleteNeCommandCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('����ȡ��'+cs) ;
			return ;
		}
		var crt = dsNeCommandCatgList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		if(isRelate()) return ;
		if(!catgDduCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("NeCommandCatgService", "deleteNeCommandCatgById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				dsNeCommandCatgList.reloadData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}