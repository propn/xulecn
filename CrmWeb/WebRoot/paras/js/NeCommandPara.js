
	var actionType = '';

	function btn_query_onClick(){
		queryData() ;
	}
	function btn_reset_onClick(){
		dsQuery.clearData();
	}
	function queryData(){
		dsNeCommandParaForm.clearData();
		var crt = dsNeCommandCatgList.getCurrent();
		if(!crt) return;	
		var command_catalog_id =  crt.getValue('command_catalog_id');
		if(null == command_catalog_id ||'' == command_catalog_id){
			alert("请选择目标数据项目录");
			return ;
		}
		var params = dsNeCommandParaList.parameters() ;
		params.setValue('name',dsQuery.getValue('name'));
		params.setValue('para_code',dsQuery.getValue('para_code'));
		params.setValue('command_catalog_id',command_catalog_id);
		
		dsNeCommandParaList.reloadData();
	}
	
	function insertNeCommandPara_onClick(){
		if(!statusCheck('insertNeCommandPara'))
			return ;
		dsNeCommandParaForm.clearData();
		dsNeCommandParaForm.setReadOnly(false) ;
		dsNeCommandParaForm.setFieldReadOnly("paraname",true);
		dsNeCommandParaForm.setFieldReadOnly("rulename",true);
		actionType = 'insertNeCommandPara';
	}
	
	function updateNeCommandPara_onClick(){
		if(!statusCheck('updateNeCommandPara'))
			return ;
		if(!duCheck()) return ;
		
		dsNeCommandParaForm.setReadOnly(false) ;
		dsNeCommandParaForm.setFieldReadOnly("paraname",true);
		dsNeCommandParaForm.setFieldReadOnly("rulename",true);
		actionType = 'updateNeCommandPara';
	}
	
	function NeCommandParaCheck(){
		return true ;
	}
	function validByType(){
		var isok = true;
		var get_method = dsNeCommandParaForm.getValue("get_method");
		if('100' == get_method){
			var paraname = dsNeCommandParaForm.getValue("paraname");
			if(null == paraname || ''==paraname){
				isok = false;
				alert("请选择源数据项!");
			}
		}else if('200' == get_method){
			var rulename = dsNeCommandParaForm.getValue("rulename");
			if(null == rulename || ''==rulename){
				isok = false;
				alert("请选择转换规则!");
			}
		}else if('300' == get_method){
			var default_value = dsNeCommandParaForm.getValue("default_value");
			if(null == default_value || ''==default_value){
				isok = false;
				alert("静态值不能为空!");
			}
		}
		return isok;
	}
	function saveNeCommandPara_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsNeCommandParaForm").validate()) return;
		if(!NeCommandParaCheck()) return ;
		var catlogId = dsNeCommandCatgList.getCurrent().getValue("command_catalog_id");//当前目录id值
		if(!catlogId){
			alert("请选择目录");
		 return ;
		 }
		 if(!validByType()) return ;
		dsNeCommandParaForm.setValue('command_catalog_id',catlogId);
		var NeCommandParaVO = extractBeanFromDataset(dsNeCommandParaForm, 'java.util.HashMap');
		
		//验证编码不能含有汉字
		var para_code = dsNeCommandParaForm.getValue("para_code");
		if (!validateCode(para_code)) return;
		
		var command_id = dsNeCommandParaForm.getValue("command_id");
		//验证编码的唯一性
		if(!validateParaCode(catlogId, para_code, command_id))return;
		
		NDAjaxCall.getSyncInstance().remoteCall('NeCommandParaService',actionType,[NeCommandParaVO],
				function(replay){
			var result = replay.getResult() ;
			actionType = '';
			if(result == true ){
			   alert('操作成功');
			   dsNeCommandParaForm.clearData();
			   dsNeCommandParaForm.setReadOnly(true);
			   queryData() ;
			}else{
			   alert('操作失败');
			}
			
		});
	}
	
	//验证编码不能含有汉字
	function validateCode(code) {
		var flag= true;
		if(code) {
			if(/^[\w\d]*$/.test(code)) {
			//if (/[u4E00-u9FA5]/.test(code)) {
	         	flag = true;
	         } else {
	         	flag = false;
	         	alert("编码只能为字母、数字、下划线的组合!");
	         }
		}
		return flag;
	}
	
	//验证编码的唯一性	
	function validateParaCode(command_catalog_id, para_code,command_id) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandParaService", "validateParaCode",[command_catalog_id, para_code,command_id],function(r){
			var result = r.getResult();
			if(!result){
				flag = false;
				alert('编码已存在，请重新录入!') ;
			}
		});
		return flag;
	}
	
	//取消--代理商
	function cancelNeCommandPara_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsNeCommandParaForm.clearData();
		dsNeCommandParaForm.setReadOnly(true);
		dsNeCommandParaList_afterScroll();
	}
		
	
	function dsNeCommandParaList_afterScroll(){
		//是否取消当前操作？
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('确认取消'+cs)) 
			return ;
		
		actionType = '';
		
		dsNeCommandParaForm.setReadOnly(true);
		
		var crt = dsNeCommandParaList.getCurrent();
		if(!crt) return;
		var para = dsNeCommandParaForm.parameters() ; 
				
		var command_id =  crt.getValue('command_id');
		para.setValue('command_id' ,command_id );
				
		dsNeCommandParaForm.reloadData() ;
	}
	
	//删除、更新根据主键检查是否选中记录
	var para = [] ;//存放主键值数组
	function duCheck(){
		para = [] ;
				
		var command_id =  dsNeCommandParaForm.getValue('command_id');
		if(command_id == null || command_id == ''){
			alert('command_id不能为空,请选择删除数据记录！');
			return false ;
		}
		para.push(command_id) ;
		
				
		return true ;
	}
	//新增、修改时，需要进行状态检测
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertNeCommandPara'){
				alert('请先取消新增操作！') ;
				return false ;
			}
			if(actionType == 'updateNeCommandPara'){
				alert('请先取消修改操作！') ;
				return false ;
			}
		}
		return true ;
	}
	//获取当前状态文字描述
	function getCurrentStatus(){
		if(actionType == 'insertNeCommandPara'){
			return '新增操作' ;
		}
		if(actionType == 'updateNeCommandPara'){
			return '修改操作' ;
		}
		return '' ;
	}
	
	function deleteNeCommandPara_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('请先取消'+cs) ;
			return ;
		}
		var crt = dsNeCommandParaList.getCurrent();
		if(!crt){
			return;
		}
		
		if(!confirm("确定删除当前记录？")) return;
		
		var command_id = crt.getValue("command_id");
		if (!validateForParaRecord(command_id)) return;//检查是否被数据项记录引用
		if (!validateForOrderType(command_id)) return;//检查是否被工单类型引用
		if(!validateForTemplatePara(command_id))return;//检查是否被指令模板引用
		if(!duCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("NeCommandParaService", "deleteNeCommandParaById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！ ') ;
				queryData();
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}
	
	//检查是否被数据项记录引用
	function validateForParaRecord(command_id) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandParaService", "validateForParaRecord",[command_id],function(r){
			var result = r.getResult();
			if(result && result != '-1'){
				flag = false;
				alert('该目标数据项被  "' + result + '" 数据项记录引用，不允许删除!') ;
			}
		});
		return flag;
	}
	
	//检查是否被工单类型引用
	function validateForOrderType(command_id) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandParaService", "validateForOrderType",[command_id],function(r){
			var result = r.getResult();
			if(result && result != '-1'){
				flag = false;
				alert('该目标数据项被  "' + result + '" 工单类型引用，不允许删除!') ;
			}
		});
		return flag;
	}
	//检查是否被指令模板引用
	function validateForTemplatePara(command_id) {
		var flag = true;
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandParaService", "validateForTemplatePara",[command_id],function(r){
			var result = r.getResult();
			if(result && result != '-1'){
				flag = false;
				alert('该目标数据项被  "' + result + '" 指令模板引用，不允许删除!') ;
			}
		});
		return flag;
	}
	
	function button_dsNeCommandParaForm_paraname_onClick(){
         var back = openDialog("queryParaInfo.jsp",[],"","600px","500px");
         if(back!=null){
           dsNeCommandParaForm.setValue('para_id',back["para_id"]);
           dsNeCommandParaForm.setValue('paraname',back["paraname"]);
           dsNeCommandParaForm.setValue('node_path',back["node_path"]);
           }
	}
	function button_dsNeCommandParaForm_rulename_onClick(){
         var back = openDialog("queryNeTranRule.jsp",[],"","600px","500px");
         if(back!=null){
           dsNeCommandParaForm.setValue('tran_rule_id',back["tran_rule_id"]);
           dsNeCommandParaForm.setValue('rulename',back["rulename"]);
           }
	}