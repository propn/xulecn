	function page_onLoad(){
		dsWoParaCatgList.reloadData();
	}
	function insertWoParaCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('确认取消'+cs)) 
			return ;
		dsSpParaInfoForm.setReadOnly(true);
		catgActionType = '';
        var back = openDialog("WoParaCatg.jsp",[],"","300px","200px");
        if(back!=null){
          page_onLoad();
        }
	}
	
	function updateWoParaCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('确认取消'+cs)) 
			return ;
		dsSpParaInfoForm.setReadOnly(true);
		catgActionType = '';
		var crt = dsWoParaCatgList.getCurrent();
		if(!crt) return;
		var para = dsSpParaInfoList.parameters() ; 
				
		var para_dir_id =  crt.getValue('para_dir_id');
		var back = openDialog("WoParaCatg.jsp",para_dir_id,"","300px","200px");
        if(back!=null){
          page_onLoad();
        }
	}

			
	
	function dsWoParaCatgList_afterScroll(){
		//是否取消当前操作？
		var cs = getCurrentStatus() ;
		if(cs != '' && !confirm('确认取消'+cs)) 
			return ;
		dsSpParaInfoForm.setReadOnly(true);
		catgActionType = '';
		
		//dsWoParaCatgForm.setReadOnly(true);
		
		var crt = dsWoParaCatgList.getCurrent();
		if(!crt) return;
		var para = dsSpParaInfoList.parameters() ; 
				
		var para_dir_id =  crt.getValue('para_dir_id');
		para.setValue('para_dir_id' ,para_dir_id );
				
		dsSpParaInfoList.reloadData() ;
	}
	
	//删除、更新根据主键检查是否选中记录
	var para = [] ;//存放主键值数组
	function cataDuCheck(){
		para = [] ;
		var crt = dsWoParaCatgList.getCurrent();	
		var para_dir_id =  crt.getValue('para_dir_id');
		if(para_dir_id == null || para_dir_id == ''){
			alert('para_dir_id不能为空,请选择删除数据记录！');
			return false ;
		}
		para.push(para_dir_id) ;
		
				
		return true ;
	}
	function isRelate(){
		var isDelete = true;
		var crt = dsWoParaCatgList.getCurrent();
		var para_id = crt.getValue('para_dir_id');
		NDAjaxCall.getSyncInstance().remoteCall("WoParaCatgService", "isRelate",[para_id],function(r){
			var result = r.getResult() ;
			if(result){
				alert('删除不成功,请先删除所有下级源数据项！') ;
			}else isDelete = false;
		});
		return isDelete;
	}
	
	function deleteWoParaCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('请先取消'+cs) ;
			return ;
		}
		var crt = dsWoParaCatgList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("确定删除当前记录？")) return;
		if(!cataDuCheck()) return ;
		if(isRelate()) return; //判断是否有关联数据
		NDAjaxCall.getAsyncInstance().remoteCall("WoParaCatgService", "deleteWoParaCatgById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！') ;
				dsWoParaCatgList.reloadData();
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}