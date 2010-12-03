
	var actionType = '';

	function page_onLoad(){
		queryData() ;
	}

	var s = null ;
	function btn_query_onClick(){
		s = new Date() ;
		dsSecondForm.clearData();
		dsMasterForm.clearData();
		
		dsList.parameters().setValue('mName',dsQuery.getValue('mName'));
		dsList.reloadData(myLog);
		
	}
	function queryData(isQuery){
		dsSecondForm.clearData();
		dsMasterForm.clearData();
		
		dsList.parameters().setValue('mName',dsQuery.getValue('mName'));
		dsList.reloadData();
	}
	
	function myLog(){
		jsDebug.debug("nxa page query data cost time="+(new Date()-s));
	}
	function insert_onClick(){
		if(actionType == 'insert') return;

		dsSecondForm.clearData();
		dsMasterForm.clearData();
		dsSecondForm.setReadOnly(false) ;
		dsMasterForm.setReadOnly(false) ;
		actionType = 'insert';
	}
	//修改代理商
	function update_onClick(){
		if(dsMasterForm.getValue('id') == '') return;
		dsSecondForm.setReadOnly(false) ;
		dsMasterForm.setReadOnly(false) ;
		actionType = 'update';
	}
	
	function save_onClick(){
	    var s = new Date() ;
		if (!$("form_dsMasterForm").validate()) return;
		if (!$("form_dsSecondForm").validate()) return;
		
		var TSecondVO = extractBeanFromDataset(dsSecondForm, 'fortest.xa.vo.TSecondVO');
		var TMasterVO = extractBeanFromDataset(dsMasterForm, 'fortest.xa.vo.TMasterVO');
		
		NDAjaxCall.getSyncInstance().remoteCall('XAService',actionType,
			[TMasterVO,TSecondVO],function(replay){
			jsDebug.debug("xa page "+actionType+" data cost time="+(new Date()-s));
			var result = replay.getResult() ;
			if(result == true ){
			   alert('操作成功');
			   dsMasterForm.clearData();
			   dsSecondForm.clearData();
			   dsMasterForm.setReadOnly(true);
			   dsSecondForm.setReadOnly(true);
			   queryData() ;
			}else{
			   alert('操作失败');
			}
			
			actionType = '';
		});
		
		
	}
	
	//取消--代理商
	function cancel_onClick(){
		actionType = '';
		dsMasterForm.clearData();
		dsSecondForm.clearData();
		dsMasterForm.setReadOnly(true);
		dsSecondForm.setReadOnly(true);
		dsList_afterScroll();
	}
		
	
	function dsList_afterScroll(){
		dsMasterForm.setReadOnly(true);
		dsSecondForm.setReadOnly(true);
		
		var crt = dsList.getCurrent();
		if(!crt) return;
		var id = crt.getValue("id");
		if(id == '') return;
		
		
		//NDAjaxCall.getAsyncInstance().remoteCall("NXAService", "getObjById", [id], function (reply) {
			//var reSult = reply.getResult();
				//alert(reSult['sid']) ;
			dsSecondForm.setValue('sid',crt.getValue('sid'));
			dsSecondForm.setValue('fId',crt.getValue('fId'));
			dsSecondForm.setValue('sName',crt.getValue('sName'));
			dsSecondForm.setValue('sDesc',crt.getValue('sDesc'));
			
			dsMasterForm.setValue('id',crt.getValue('id'));
			dsMasterForm.setValue('mName',crt.getValue('mName'));
			dsMasterForm.setValue('mDesc',crt.getValue('mDesc'));
		//});
	}
	
	function delete_onClick(){
	    var s = new Date() ;
		var crt = dsList.getCurrent();
		if(!crt){
			return;
		}
		var id =  dsMasterForm.getValue('id');
		var sid = dsSecondForm.getValue('sid');
		if(id == null || id == ''){
			alert('请先选择！');
			return;
		}
		if(!confirm("确定删除选择的记录吗？")) return;
		
		NDAjaxCall.getAsyncInstance().remoteCall("XAService", "delete",[id,sid],function(r){
			jsDebug.debug("xa page delete data cost time="+(new Date()-s));
			if(r.getResult()){
				queryData();
			}else{
				alert('操作失败，发生异常');
			}
			
		});
	}