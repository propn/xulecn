/**
	替换修改说明 ：
	1.queryData() 参数信息
	2.NeValueMapListCheck() 
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
		dsNeValueMapListForm.clearData();
		
		//need-replace 查询参数,需要根据业务实际需要设定！
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
			   alert('操作成功');
			   dsNeValueMapListForm.clearData();
			   dsNeValueMapListForm.setReadOnly(true);
			   queryData() ;
			}else{
			   alert('操作失败');
			}
			
		});
		
		
	}
	
	//取消--代理商
	function cancelNeValueMapList_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsNeValueMapListForm.clearData();
		dsNeValueMapListForm.setReadOnly(true);
		dsNeValueMapListList_afterScroll();
	}
		
	
	function dsNeValueMapListList_afterScroll(){
		//是否取消当前操作？
		var cs = getCurrentStatus() ;
		if(cs != '' && confirm('确认取消'+cs)) 
			return ;
		
		actionType = '';
		
		dsNeValueMapListForm.setReadOnly(true);
		
		var crt = dsNeValueMapListList.getCurrent();
		if(!crt) return;
		var para = dsNeValueMapListForm.parameters() ; 
				
		dsNeValueMapListForm.reloadData() ;
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
			if(actionType == 'insertNeValueMapList'){
				alert('请先取消新增操作！') ;
				return false ;
			}
			if(actionType == 'updateNeValueMapList'){
				alert('请先取消修改操作！') ;
				return false ;
			}
		}
		return true ;
	}
	//获取当前状态文字描述
	function getCurrentStatus(){
		if(actionType == 'insertNeValueMapList'){
			return '新增操作' ;
		}
		if(actionType == 'updateNeValueMapList'){
			return '修改操作' ;
		}
		return '' ;
	}
	
	function deleteNeValueMapList_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('请先取消'+cs) ;
			return ;
		}
		var crt = dsNeValueMapListList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("确定删除当前记录？")) return;
		if(!duCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("NeValueMapListService", "deleteNeValueMapListById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！ ') ;
				queryData();
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}