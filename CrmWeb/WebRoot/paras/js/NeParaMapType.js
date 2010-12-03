	var actionType = '';

	function page_onLoad(){
		queryData() ;
	}

	function btn_query_onClick(){
		queryData() ;
	}
	function queryData(){
		
		var params = dsNeParaMapTypeList.parameters() ;
		params.setValue('name',dsQuery.getValue('name'));
		
		dsNeParaMapTypeList.reloadData();
	}
	function dsNeParaMapTypeList_afterScroll(){
		var crt = dsNeParaMapTypeList.getCurrent();
		if(!crt) return;
		var map_type_id = crt.getValue("map_type_id");
		var params = dsNeValueMapListList.parameters() ;
		params.setValue('map_type_id',map_type_id);
		dsNeValueMapListList.reloadData();
	}
	function insertNeParaMapType_onClick(){
 		var back = openDialog("NeParaMapType.jsp","","","400px","300px");
         if(back!=null){
           dsNeParaMapTypeList.reloadData() ;
         }
	}
	
	function updateNeParaMapType_onClick(){
		var crt = dsNeParaMapTypeList.getCurrent();
		if(!crt) return;
		var map_type_id =  crt.getValue('map_type_id');
		var back = openDialog("NeParaMapType.jsp",map_type_id,"","400px","300px");
         if(back!=null){
           dsNeParaMapTypeList.reloadData() ;
         }
	}
	function btn_reset_onClick(){
		window.close();
	}
  function btn_confirm_onClick()
  {
    var  record=dsNeParaMapTypeList.getCurrent();
    if (!record) return;
    var back = {};
    back['map_type_id'] = record.getValue("map_type_id");
     back['map_type_name'] = record.getValue("name");
	window.returnValue = back;		
    window.close();
  }

  function table_dsNeParaMapTypeList_ondblclick()
  {
     btn_confirm_onClick();
  }

  function btn_cancle_onClick(){
    window.close();
  }
  
	function deleteNeParaMapType_onClick(){
		var crt = dsNeParaMapTypeList.getCurrent();
		if(!crt){
			alert("��ѡ��ӳ������");
			return;
		}
		var map_type_id =  crt.getValue('map_type_id');
		if(!confirm("ȷ��ɾ����ǰ��¼������ɾ����Ӧӳ��ֵ�б�?")) return;
		NDAjaxCall.getAsyncInstance().remoteCall("NeParaMapTypeService", "deleteNeParaMapTypeById",[map_type_id],function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				queryData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
		});
	}
	
	// ӳ������ӳ��ֵ
	function insertNeValueMapList_onClick(){
		var crt = dsNeParaMapTypeList.getCurrent();
		if(!crt){
			alert("��ѡ��ӳ������ӳ��ֵ");
			return;
		}
		var para = {};
		var map_type_id =  crt.getValue('map_type_id');
		para['map_type_id']	=map_type_id;
		para['type'] = 1; //1Ϊ����
 		var back = openDialog("NeValueMapList.jsp",para,"","400px","300px");
         if(back!=null){
         	var para = dsNeValueMapListList.parameters() ;
         	para.setValue("map_type_id",map_type_id); 
           dsNeValueMapListList.reloadData() ;
         }
	}
	
	function updateNeValueMapList_onClick(){
		var crt = dsNeValueMapListList.getCurrent();
		if(!crt){
			alert("����ѡ��һ��ӳ������ӳ��ֵ�б�");
			return;
		}
		var para = {};
		var map_type_id =  crt.getValue('map_type_id');
		var para_value =  crt.getValue('para_value');
		var map_value =  crt.getValue('map_value');
		para['map_type_id']	=map_type_id;
		para['para_value']	=para_value;
		para['map_value']	=map_value;
		para['type'] = 2; //1Ϊ����
		var back = openDialog("NeValueMapList.jsp",para,"","400px","300px");
         if(back!=null){
           dsNeValueMapListList.reloadData() ;
         }
	}
	function deleteNeValueMapList_onClick(){
		var crt = dsNeValueMapListList.getCurrent();
		if(!crt){
			alert("����ѡ��һ��ӳ������ӳ��ֵ");
			return;
		}
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		var para = {};
		var map_type_id =  crt.getValue('map_type_id');
		var para_value =  crt.getValue('para_value');
		var map_value =  crt.getValue('map_value');
		para['map_type_id']	=map_type_id;
		para['para_value']	=para_value;
		para['map_value']	=map_value;
		NDAjaxCall.getAsyncInstance().remoteCall("NeValueMapListService", "deleteNeValueMapListById",[para],function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				dsNeValueMapListList.reloadData() ;
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}