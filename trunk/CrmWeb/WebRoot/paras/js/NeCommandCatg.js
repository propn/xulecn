
	function page_onLoad(){
		dsNeCommandCatgList.reloadData();
	}
	function insertNeCommandCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' && confirm('确认取消'+cs)) 
			return ;
		dsNeCommandParaForm.setReadOnly(true);
		catgActionType = '';
        var back = openDialog("NeCommandCatg.jsp",[],"","300px","200px");
        if(back!=null){
          page_onLoad();
        }
	}
	
	function updateNeCommandCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' && confirm('确认取消'+cs)) 
			return ;
		dsNeCommandParaForm.setReadOnly(true);
		catgActionType = '';
		var crt = dsNeCommandCatgList.getCurrent();
		if(!crt) return;
		var para = dsNeCommandParaList.parameters() ; 
				
		var command_catalog_id =  crt.getValue('command_catalog_id');
		var back = openDialog("NeCommandCatg.jsp",command_catalog_id,"","300px","200px");
        if(back!=null){
          page_onLoad();
        }
	}
	function dsNeCommandCatgList_afterScroll(){
		//是否取消当前操作？
		var cs = getCurrentStatus() ;
		if(cs != '' && confirm('确认取消'+cs)) 
			return ;
		
		actionType = '';
		
		//dsNeCommandCatgForm.setReadOnly(true);
		
		var crt = dsNeCommandCatgList.getCurrent();
		if(!crt) return;
		var para = dsNeCommandParaList.parameters() ; 
		
		var command_catalog_id =  crt.getValue('command_catalog_id');
		para.setValue('command_catalog_id' ,command_catalog_id );
				
		dsNeCommandParaList.reloadData() ;
		dsNeCommandParaForm.clearData();
		
	}
	
	//删除、更新根据主键检查是否选中记录
	var para = [] ;//存放主键值数组
	function catgDduCheck(){
		para = [] ;
		var crt = dsNeCommandCatgList.getCurrent();
		var command_catalog_id = crt.getValue('command_catalog_id');
		if(command_catalog_id == null || command_catalog_id == ''){
			alert('command_catalog_id不能为空,请选择删除数据记录！');
			return false ;
		}
		para.push(command_catalog_id) ;
		
				
		return true ;
	}
	function isRelate(){
		var isdelete = true;
		var crt = dsNeCommandCatgList.getCurrent();
		var para_id = crt.getValue('command_catalog_id');
		NDAjaxCall.getSyncInstance().remoteCall("NeCommandCatgService", "isRelate",[para_id],function(r){
			var result = r.getResult() ;
			if(result){
				alert('删除不成功,请先删除所有下级源数据项！') ;
			}else isdelete = false;
		});
		return isdelete;
	}
	function deleteNeCommandCatg_onClick(){
		var cs = getCurrentStatus() ;
		if(cs != '' ){
			alert('请先取消'+cs) ;
			return ;
		}
		var crt = dsNeCommandCatgList.getCurrent();
		if(!crt){
			return;
		}
		
		
		if(!confirm("确定删除当前记录？")) return;
		if(isRelate()) return ;
		if(!catgDduCheck()) return ;
		NDAjaxCall.getAsyncInstance().remoteCall("NeCommandCatgService", "deleteNeCommandCatgById",para,function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('操作成功！ ') ;
				dsNeCommandCatgList.reloadData();
			}else{
				alert('操作失败 ,如有问题请联系管理员 。');
			}
			
		});
	}