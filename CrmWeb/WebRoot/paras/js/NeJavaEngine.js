	var actionType = 'insertNeJavaEngine';

	function page_onLoad(){
		var id = window.dialogArguments;
		dsNeJavaEngineForm.setValue("state","10I");
		if(null != id && '' != id){
			var para = dsNeJavaEngineForm.parameters() ; 
			para.setValue('id' ,id );
			dsNeJavaEngineForm.reloadData() ;
			actionType="updateNeJavaEngine";
		}
	}
	function NeJavaEngineCheck(){
		return true ;
	}
	function saveNeJavaEngine_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsNeJavaEngineForm").validate()) return;
		if(!NeJavaEngineCheck()) return ;
		var NeJavaEngineVO = extractBeanFromDataset(dsNeJavaEngineForm, 'java.util.HashMap');
		
		NDAjaxCall.getSyncInstance().remoteCall('NeJavaEngineService',actionType,[NeJavaEngineVO],
				function(replay){
			var result = replay.getResult() ;
			actionType = '';
			if(result == true ){
			   alert('�����ɹ�');
			   window.returnValue=true;
			}else{
			   alert('����ʧ��');
			}
		});
		window.close();
	}
	
	//ȡ��--������
	function cancelNeJavaEngine_onClick(){
		window.close();
	}