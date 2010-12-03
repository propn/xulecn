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
	function valid_date(start,end){
		if((null !=start||''!=start) && (null ==end||''==end))
			return true;
		var start_t = start.replace(/\-/g,"");
		var end_t = end.replace(/\-/g,"");
		if(start_t>end_t){
			alert("起始生成时间不能大于终止生成时间");
			return false;
		}else return true;
	}

	function queryData(){
		dsNeTranRuleForm.clearData();
		var create_date =dsQuery.getValue('create_date');
		var end_date =dsQuery.getValue('end_date');
		if(!valid_date(create_date,end_date)) return;
		
		var params = dsNeTranRuleList.parameters() ;
		params.setValue('name',dsQuery.getValue('name'));
		params.setValue('create_date',create_date);
		params.setValue('end_date',end_date);
		
		dsNeTranRuleList.reloadData();
	}
	
	function insertNeTranRule_onClick(){
		if(!statusCheck('insertNeTranRule'))
			return ;
		dsNeTranRuleForm.clearData();
		dsNeTranRuleForm.setReadOnly(false) ;
		dsNeTranRuleForm.setFieldReadOnly("engine_name",true) ;
		dsNeTranRuleForm.setFieldReadOnly("map_type_name",true) ;
		dsNeTranRuleForm.setFieldReadOnly("business_obj_name",true) ;
		actionType = 'insertNeTranRule';
	}
	
	function updateNeTranRule_onClick(){
		if(!statusCheck('updateNeTranRule'))
			return ;
		if(!duCheck()) return ;
		
		dsNeTranRuleForm.setReadOnly(false) ;
		dsNeTranRuleForm.setFieldReadOnly("map_type_name",true) ;
		dsNeTranRuleForm.setFieldReadOnly("business_obj_name",true) ;
		dsNeTranRuleForm.setFieldReadOnly("engine_name",true) ;
		actionType = 'updateNeTranRule';
	}
	
	function NeTranRuleCheck(){
		return true ;
	}
	function validByMethod(){
		var get_method =dsNeTranRuleForm.getValue("get_method"); 
		var isok = true;
		if('200' ==get_method){
			var map_type_name =dsNeTranRuleForm.getValue("map_type_name"); 
			if(null == map_type_name || '' == map_type_name){
				isok = false;
				alert("映射类型不能为空");
			}
		}else if('300' ==get_method){
			var engine_name =dsNeTranRuleForm.getValue("engine_name"); 
			if(null == engine_name || '' == engine_name){
				isok = false;
				alert("动态组件不能为空");
			}
		}else if('400' ==get_method){
			var business_obj_name =dsNeTranRuleForm.getValue("business_obj_name"); 
			if(null == business_obj_name || '' == business_obj_name){
				isok = false;
				alert("静态组件不能为空");
			}
		}else if('500' ==get_method || '600' ==get_method){
			var execute_sql =dsNeTranRuleForm.getValue("execute_sql"); 
			if(null == execute_sql || '' == execute_sql){
				isok = false;
				alert("动态sql不能为空");
			}
		}
		return isok;
	}
	function saveNeTranRule_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsNeTranRuleForm").validate()) return;
		if(!NeTranRuleCheck()) return ;
		if(!validByMethod()) return ;
		var NeTranRuleVO = extractBeanFromDataset(dsNeTranRuleForm, 'java.util.HashMap');
		
		NDAjaxCall.getSyncInstance().remoteCall('NeTranRuleService',actionType,[NeTranRuleVO],
				function(replay){
			var result = replay.getResult() ;
			
			if(result == true ){
			   alert('操作成功');
			   dsNeTranRuleForm.clearData();
			   dsNeTranRuleForm.setReadOnly(true);
			   if(actionType == 'insertNeTranRule'){
			   		alert("请添加转换规则源数据项");
			   }
			   actionType = '';
			   queryData() ;
			}else{
			   alert('操作失败');
			}
		});
	}
	
	//取消--代理商
	function cancelNeTranRule_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsNeTranRuleForm.clearData();
		dsNeTranRuleForm.setReadOnly(true);
		dsNeTranRuleList_afterScroll();
	}
		
	
	function dsNeTranRuleList_afterScroll(){
		//是否取消当前操作？
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('确认取消'+cs)) 
			return ;
		
		actionType = '';
		
		dsNeTranRuleForm.setReadOnly(true);
		
		var crt = dsNeTranRuleList.getCurrent();
		if(!crt) return;
		var para = dsNeTranRuleForm.parameters() ; 
				
		var tran_rule_id =  crt.getValue('tran_rule_id');
		para.setValue('tran_rule_id' ,tran_rule_id );
				
		dsNeTranRuleForm.reloadData() ;
		
		var params = dsNeTranRuleParaList.parameters() ;
		params.setValue('tran_rule_id',tran_rule_id);
		dsNeTranRuleParaList.reloadData();
	}
	
	//删除、更新根据主键检查是否选中记录
	var para = [] ;//存放主键值数组
	function duCheck(){
		para = [] ;
				
		var tran_rule_id =  dsNeTranRuleForm.getValue('tran_rule_id');
		if(tran_rule_id == null || tran_rule_id == ''){
			alert('tran_rule_id不能为空,请选择删除数据记录！');
			return false ;
		}
		para.push(tran_rule_id) ;
		
				
		return true ;
	}
	//新增、修改时，需要进行状态检测
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertNeTranRule'){
				alert('请先取消新增操作！') ;
				return false ;
			}
			if(actionType == 'updateNeTranRule'){
				alert('请先取消修改操作！') ;
				return false ;
			}
		}
		return true ;
	}
	//获取当前状态文字描述
	function getCurrentStatus(){
		if(actionType == 'insertNeTranRule'){
			return '新增操作' ;
		}
		if(actionType == 'updateNeTranRule'){
			return '修改操作' ;
		}
		return '' ;
	}
	function isRelateByCommPara(){
		var isdelete = true;
		var crt = dsNeTranRuleForm.getCurrent();
		var para_id = crt.getValue('tran_rule_id');
		NDAjaxCall.getSyncInstance().remoteCall("NeTranRuleService", "isRelateByCommPara",[para_id],function(r){
			var result = r.getResult() ;
			if(result){
				if(confirm('该转换规则被目标数据项'+result+'引用，是否继续删除')){
					isdelete = false;
				}else isdelete = true;
			}
		});
		return isdelete;
	}
	function isRelateByRulePara(){
		var isdelete = true;
		var crt = dsNeTranRuleForm.getCurrent();
		var para_id = crt.getValue('tran_rule_id');
		NDAjaxCall.getSyncInstance().remoteCall("NeTranRuleService", "isRelateByRulePara",[para_id],function(r){
			var result = r.getResult() ;
			if(result){
				isdelete = false;
				alert('删除不成功,请先删除所有下级源数据项！')
			}
		});
		return isdelete;
	}
	function deleteNeTranRule_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('请先取消'+cs) ;
			return ;
		}
		var crt = dsNeTranRuleList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("确定删除当前记录？")) return;
		if(!duCheck()) return ;
		if(!isRelateByRulePara()) return ;
		if(!isRelateByCommPara()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("NeTranRuleService", "deleteNeTranRuleById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！ ') ;
				queryData();
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}
	
	function button_dsNeTranRuleForm_map_type_name_onClick(){
		var back = openDialog("queryNeParaMapType.jsp","","","700px","720px");
         if(back!=null){
           dsNeTranRuleForm.setValue("map_type_id",back['map_type_id']);
           dsNeTranRuleForm.setValue("map_type_name",back['map_type_name']);
         }
	}
	function button_dsNeTranRuleForm_engine_name_onClick(){
		var back = openDialog("queryNeJavaEngine.jsp","","","600px","400px");
         if(back!=null){
           dsNeTranRuleForm.setValue("id",back['id']);
           dsNeTranRuleForm.setValue("engine_name",back['name']);
         }
	}
	function button_dsNeTranRuleForm_business_obj_name_onClick(){
		
		var para={};
		para['type'] = 2 ;
		para['business_obj_id'] =dsNeTranRuleForm.getValue("business_obj_id");
		var back = openDialog("../module/queryPbBussinessObj.jsp",para,"","600px","500px");
		if(null != back){
			dsNeTranRuleForm.setValue("business_obj_id",back["business_obj_id"]);
			dsNeTranRuleForm.setValue("business_obj_name",back["obj_name"]);
		}
	}
	// 转换规则源数据项
	function insertNeTranRulePara_onClick(){
		var crt = dsNeTranRuleList.getCurrent();
		if(!crt){
			alert("请选择转换规则");
			return;
		}
		var para = {};
		var tran_rule_id =  crt.getValue('tran_rule_id');
		para['tran_rule_id']	=tran_rule_id;
		para['type'] = 1; //1为新增
 		var back = openDialog("NeTranRulePara.jsp",para,"","600px","500px");
         if(back!=null){
         	var para = dsNeTranRuleParaList.parameters() ;
         	para.setValue("tran_rule_id",tran_rule_id); 
           dsNeTranRuleParaList.reloadData() ;
         }
	}
	
	function updateNeTranRulePara_onClick(){
		var crt = dsNeTranRuleParaList.getCurrent();
		if(!crt){
			alert("请选择转换规则源数据项");
			return;
		}
		var tran_rule_id =  crt.getValue('tran_rule_id');
		var para = {};
		var para_id =  crt.getValue('para_id');
		var seq =  crt.getValue('seq');
		para['tran_rule_id']=tran_rule_id;
		para['para_id']	=para_id;
		para['seq'] = seq;
		para['type'] = 2; //2为修改
		var back = openDialog("NeTranRulePara.jsp",para,"","600px","500px");
         if(back!=null){
           dsNeTranRuleParaList.reloadData() ;
         }
	}
	function deleteNeTranRulePara_onClick(){
		var crt = dsNeTranRuleParaList.getCurrent();
		if(!crt){
			alert("请选择转换规则源数据项");
			return;
		}
		if(!confirm("确定删除当前记录？")) return;
		var para = {};
		var tran_rule_id =  crt.getValue('tran_rule_id');
		var para_id =  crt.getValue('para_id');
		var seq =  crt.getValue('seq');
		para['tran_rule_id']=tran_rule_id;
		para['para_id']	=para_id;
		NDAjaxCall.getAsyncInstance().remoteCall("NeTranRuleParaService", "deleteNeTranRuleParaById",[para],function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！ ') ;
				dsNeTranRuleParaList.reloadData() ;
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}