	var actionType = '';
	
	function insertSpParaInfo_onClick(){
		if(!statusCheck('insertSpParaInfo'))
			return ;
		if(!dsWoParaCatgList.getCurrent()){
			alert("请先增加源数据项目录");
			return;
		}
			
		dsSpParaInfoForm.clearData();
		dsSpParaInfoForm.setReadOnly(false) ;
		actionType = 'insertSpParaInfo';
	}
	
	function updateSpParaInfo_onClick(){
		if(!statusCheck('updateSpParaInfo'))
			return ;
		if(!duCheck()) return ;
		
		dsSpParaInfoForm.setReadOnly(false) ;
		actionType = 'updateSpParaInfo';
	}
	
	function SpParaInfoCheck(){
		return true ;
	}
	
	function saveSpParaInfo_onClick(){
		if(actionType == '' ) return ;
		if (!$("form_dsSpParaInfoForm").validate()) return;
		if(!SpParaInfoCheck()) return ;
		var paradirid = dsWoParaCatgList.getCurrent().getValue("para_dir_id");
		if(!paradirid){
			alert("请选择目录");
		 return ;
		 }
		dsSpParaInfoForm.setValue('para_dir_id',paradirid);
		var SpParaInfoVO = extractBeanFromDataset(dsSpParaInfoForm, 'java.util.HashMap');
		
		var para_code = dsSpParaInfoForm.getValue("para_code");
		if (!validateCode(para_code)) return;//对数据项编码进行非含汉字校验
		
		NDAjaxCall.getSyncInstance().remoteCall('SpParaInfoService',actionType,[SpParaInfoVO],
				function(replay){
			var result = replay.getResult() ;
			actionType = '';
			if(result == true ){
			   alert('操作成功');
			   dsSpParaInfoForm.clearData();
			   dsSpParaInfoForm.setReadOnly(true);
			   dsSpParaInfoList.reloadData();
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
	         	alert("数据项编码只能为字母、数字、下划线的组合!");
	         }
		}
		return flag;
	}
	
	//取消--代理商
	function cancelSpParaInfo_onClick(){
		if(actionType == '') return ;
		actionType = '';
		dsSpParaInfoForm.clearData();
		dsSpParaInfoForm.setReadOnly(true);
		dsSpParaInfoList_afterScroll();
	}
		
	
	function dsSpParaInfoList_afterScroll(){
		//是否取消当前操作？
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('确认取消'+cs)) 
			return ;
		
		actionType = '';
		
		dsSpParaInfoForm.setReadOnly(true);
		
		var crt = dsSpParaInfoList.getCurrent();
		if(!crt) return;
		var para = dsSpParaInfoForm.parameters() ; 
				
		var para_id =  crt.getValue('para_id');
		para.setValue('para_id' ,para_id );
				
		dsSpParaInfoForm.reloadData() ;
	}
	
	//删除、更新根据主键检查是否选中记录
	var para = [] ;//存放主键值数组
	function duCheck(){
		para = [] ;
				
		var para_id =  dsSpParaInfoForm.getValue('para_id');
		if(para_id == null || para_id == ''){
			alert('para_id不能为空,请选择删除数据记录！');
			return false ;
		}
		para.push(para_id) ;
		
				
		return true ;
	}
	//新增、修改时，需要进行状态检测
	function statusCheck(willChangeStatus){
		if(actionType != ''){
			if(actionType == willChangeStatus )
				return false ;
			if(actionType == 'insertSpParaInfo'){
				alert('请先取消新增操作！') ;
				return false ;
			}
			if(actionType == 'updateSpParaInfo'){
				alert('请先取消修改操作！') ;
				return false ;
			}
		}
		return true ;
	}
	//获取当前状态文字描述
	function getCurrentStatus(){
		if(actionType == 'insertSpParaInfo'){
			return '新增操作' ;
		}
		if(actionType == 'updateSpParaInfo'){
			return '修改操作' ;
		}
		return '' ;
	}
	
	function deleteSpParaInfo_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('请先取消'+cs) ;
			return ;
		}
		var crt = dsSpParaInfoList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("确定删除当前记录？")) return;
		if(!duCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("SpParaInfoService", "deleteSpParaInfoById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！ ') ;
				dsSpParaInfoList.reloadData();
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}