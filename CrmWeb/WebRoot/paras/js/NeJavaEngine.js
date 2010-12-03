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
			   alert('操作成功');
			   window.returnValue=true;
			}else{
			   alert('操作失败');
			}
		});
		window.close();
	}
	
	//取消--代理商
	function cancelNeJavaEngine_onClick(){
		window.close();
	}