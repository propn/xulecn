
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
		
		//need-replace 查询参数,需要根据业务实际需要设定！
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
		
		//对3个编码的非汉字验证
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
			   alert('操作成功');
			   dsSpParaRecordForm.clearData();
			   dsSpParaRecordForm.setReadOnly(true);
			   queryData() ;
			}else{
			   alert('操作失败');
			}
			
		});
	}
	
	//验证编码不能含有汉字
	function validateGroupbyCode(code) {
		var flag= true;
		if(code) {
			if(/^[\w\d]*$/.test(code)) {
			//if (/[u4E00-u9FA5]/.test(code)) {
	         	flag = true;
	         } else {
	         	flag = false;
	         	alert("分组编码只能为字母、数字和下划线等字符的组合!");
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
	         	alert("升序降序编码只能为字母、数字和下划线等字符的组合!");
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
	         	alert("降序排序编码只能为字母、数字和下划线等字符的组合!");
	         }
		}
		return flag;
	}	
	
	//取消--代理商
	function cancelSpParaRecord_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsSpParaRecordForm.clearData();
		dsSpParaRecordForm.setReadOnly(true);
		dsSpParaRecordList_afterScroll();
	}
		
	
	function dsSpParaRecordList_afterScroll(){
		//是否取消当前操作？
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('确认取消'+cs)) 
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
	
	//删除、更新根据主键检查是否选中记录
	var para = [] ;//存放主键值数组
	function duCheck(){
		para = [] ;
				
		var record_id =  dsSpParaRecordForm.getValue('record_id');
		if(record_id == null || record_id == ''){
			alert('record_id不能为空,请选择删除数据记录！');
			return false ;
		}
		para.push(record_id) ;
		
				
		return true ;
	}
	//新增、修改时，需要进行状态检测
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertSpParaRecord'){
				alert('请先取消新增操作！') ;
				return false ;
			}
			if(actionType == 'updateSpParaRecord'){
				alert('请先取消修改操作！') ;
				return false ;
			}
		}
		return true ;
	}
	//获取当前状态文字描述
	function getCurrentStatus(){
		if(actionType == 'insertSpParaRecord'){
			return '新增操作' ;
		}
		if(actionType == 'updateSpParaRecord'){
			return '修改操作' ;
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
				alert('删除不成功,请先删除所有目标数据项列表！') ;
			}else isdelete = false;
		});
		return isdelete;
	}
	function deleteSpParaRecord_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('请先取消'+cs) ;
			return ;
		}
		var crt = dsSpParaRecordList.getCurrent();
		if(!crt){
			return;
		}
		
		if(!confirm("确定删除当前记录？")) return;
		
		////根据数据项记录标识在SP_ORDER_Type_RECORDS检查是否被工单类型引用
		var recordId = crt.getValue('record_id');
		if(!validateRecordId(recordId)) return;
		
		if(!duCheck()) return ;
		if(isRelate()) return ;
		NDAjaxCall.getSyncInstance().remoteCall("SpParaRecordService", "deleteSpParaRecordById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！ ') ;
				queryData();
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}
	
	//根据数据项记录标识在SP_ORDER_Type_RECORDS检查是否被工单类型引用
	function validateRecordId(recordId) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("SpParaRecordService", "validateRecordId",[recordId],
				function(r){
					var result = r.getResult();
					if(result && result != "-1"){
						flag = false;//一定要在alert之前赋值，不然后果很严重。
						alert('该数据项记录被工单类型   ' + result + ' 关联，不允许删除！');
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
		var record_flag = "insertParaRecord";//标志位，传到queryNeCommandPara.jsp页面，以标识某段代码是SpParaRecord.js中的insertParaRecordList_onClick专用
  		var back = openDialog("queryNeCommandPara.jsp",[record_flag,record_id,record_name],"","600px","500px");
  		
        if(back!=null){
         	var commandparam = {};
         	commandparam['command_id'] = back['command_id'];
         	commandparam['record_id'] = record_id;
			NDAjaxCall.getSyncInstance().remoteCall('SpParaRecordListService','insertSpParaRecordList',[commandparam],function(replay){
				var result = replay.getResult() ;
				if(result == true ){
				    alert('操作成功');
					var sppara = dsSpParaRecordListList.parameters() ; 
					sppara.setValue('record_id' ,record_id );
					dsSpParaRecordListList.reloadData();
				}else{
				   alert('操作失败');
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
		if(!confirm("确定删除当前记录？")) return;
		var recordId = crt.getValue('record_id');
		var commandId = crt.getValue('command_id');
		NDAjaxCall.getAsyncInstance().remoteCall("SpParaRecordListService", "deleteSpParaRecordListById",[recordId,commandId],function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！ ') ;
				dsSpParaRecordListList.reloadData();
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}