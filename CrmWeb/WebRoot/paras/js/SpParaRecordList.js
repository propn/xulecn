/**
	替换修改说明 ：
	1.queryData() 参数信息
	2.SpParaRecordListCheck() 
	3. 替换完毕，删除此段文字！
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
		
		//need-replace 查询参数,需要根据业务实际需要设定！
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
			   alert('操作成功');
			   dsSpParaRecordListForm.clearData();
			   dsSpParaRecordListForm.setReadOnly(true);
			   queryData() ;
			}else{
			   alert('操作失败');
			}
			
		});
		
		
	}
	
	//取消--代理商
	function cancelSpParaRecordList_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsSpParaRecordListForm.clearData();
		dsSpParaRecordListForm.setReadOnly(true);
		dsSpParaRecordListList_afterScroll();
	}
		
	
	function dsSpParaRecordListList_afterScroll(){
		//是否取消当前操作？
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('确认取消'+cs)) 
			return ;
		
		actionType = '';
		
		dsSpParaRecordListForm.setReadOnly(true);
		
		var crt = dsSpParaRecordListList.getCurrent();
		if(!crt) return;
		var para = dsSpParaRecordListForm.parameters() ; 
				
		dsSpParaRecordListForm.reloadData() ;
	}
	
	//删除、更新根据主键检查是否选中记录
	var para = [] ;//存放主键值数组
	function duCheck(){
		para = [] ;
				
		return true ;
	}
	//新增、修改时，需要进行状态检测
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertSpParaRecordList'){
				alert('请先取消新增操作！') ;
				return false ;
			}
			if(actionType == 'updateSpParaRecordList'){
				alert('请先取消修改操作！') ;
				return false ;
			}
		}
		return true ;
	}
	//获取当前状态文字描述
	function getCurrentStatus(){
		if(actionType == 'insertSpParaRecordList'){
			return '新增操作' ;
		}
		if(actionType == 'updateSpParaRecordList'){
			return '修改操作' ;
		}
		return '' ;
	}
	
	function deleteSpParaRecordList_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('请先取消'+cs) ;
			return ;
		}
		var crt = dsSpParaRecordListList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("确定删除当前记录？")) return;
		if(!duCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("SpParaRecordListService", "deleteSpParaRecordListById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！ ') ;
				queryData();
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}