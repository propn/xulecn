	function makeDataset(__dataset) {
	    //var startDate = new Date();
	     var __t = createDataset({'id': __dataset.datasetId});
	
	    
	    if( __dataset.type ){
		    __t.type = __dataset.type;
		}else{
			__t.type = "reference" ;
		}
		
		//dataset�Ƿ�Ϊֻ���ġ�
		if( __dataset.readOnly ){
		    __t.readOnly = System.isTrue(__dataset.readOnly);
		}else{
			__t.readOnly = false ;
		}
		
		//dataset��������Ƿ�ɱ༭��
		if( __dataset.editable ){
		    __t.editable = System.isTrue(__dataset.editable);
		}else{
			__t.editable = false ;
		}
			
		//�Ƿ��Զ��ڱ�Ҫ��ʱ����������ҳ������
		if( __dataset.autoLoadPage ){
		    __t.autoLoadPage = System.isTrue(__dataset.autoLoadPage);
		}else{
			__t.autoLoadPage = false ;
		}
	    //ÿҳ�ļ�¼��
	    if( __dataset.pageSize ){
		    __t.pageSize = parseInt(__dataset.pageSize);
		}else{
			__t.pageSize = 30 ;
		}
		//��ǰҳ������
	    if( __dataset.pageIndex ) {
		    __t.pageIndex = parseInt(__dataset.pageIndex);
		}else{
			__t.pageIndex = 1 ;
		}
		//��ҳ��
	    if( __dataset.pageCount ) {
		    __t.pageCount = parseInt(__dataset.pageCount);
		}else{
			__t.pageCount = 1 ;
		}
		//�ܼ�¼����
	    if( __dataset.recordCount ){
	    	__t.recordCount = parseInt( __dataset.recordCount );
	    }else{
	    	__t.recordCount = 0 ;
	    }
		//���ݻ�ȡ��ʽ��ͬ��/�첽
		if( __dataset.async ){
		    __t.async = System.isTrue( __dataset.async ) ;//�첽��ʽ/��ʽͬ����ȡ���ݣ�trueΪ�첽��falseΪͬ��
		}else{
			__t.async = true ;
		}
		//���ݻ�ȡ�ķ���Buffalo�������ƣ�
	    if( __dataset.loadDataAction ){
		    __t.loadDataAction = __dataset.loadDataAction;
		}else{
	    	__t.loadDataAction = "";
	    }
	    //Buffalo���񷽷�����
	    if( __dataset.loadDataActionMethod ){
		    __t.loadDataActionMethod = __dataset.loadDataActionMethod;
		}else{
			__t.loadDataActionMethod = "" ;
		}
		//��ѯ���ͣ����ܵ�ֵΪvo����dataset��Ĭ��Ϊdataset��ʽ��
		if( __dataset._queryType ) {
		    __t._queryType = __dataset._queryType;
		}else{
			__t._queryType = "dataset" ;
		}
		//�Ƿ�֧�ַ�ҳ���ܣ����������ṩ���ݵĽӿڷ����Ƿ���Ҫ��ҳ����Ҫ��pageIndex��pageSize������
		if( __dataset.paginate ){
		    __t.paginate = System.isTrue( __dataset.paginate );
		}else{
			__t.paginate = false ;
		}
	
		//�Ƿ�̬���ݣ�Dataset��������Դ��ͨ���������˻�ȡ������ҳ����ͨ��<xml>��ǩ��̬д�ɡ�
		if( __dataset.staticDataSource ){
		    __t.staticDataSource  = System.isTrue( __dataset.staticDataSource );
		}else{
			__t.staticDataSource = true ;
		}
		
		if( __dataset.xmlFormat ){
		    __t.xmlFormat = __dataset.xmlFormat;
		}else{
			__t.xmlFormat = "records";
		}
		
		if( __dataset.loadAsNeeded ){
			__t.loadAsNeeded = System.isTrue(__dataset.loadAsNeeded);
		}else{
			__t.loadAsNeeded = false ;
		}
	    
	    if( __dataset.masterDataset ){
		    __t.masterDataset = __dataset.masterDataset;
		}else{
			__t.masterDataset = "" ;
		}
		
		if( __dataset.masterKeyFields ){
		    __t.masterKeyFields = __dataset.masterKeyFields;
		}else{
			__t.masterKeyFields = "" ;
		}
		
		if( __dataset.detailKeyFields ){
		    __t.detailKeyFields = __dataset.detailKeyFields;
		}else{
			__t.detailKeyFields = "";
		}
		
		if( __dataset.relaDataset ){
		    __t.relaDataset = __dataset.relaDataset;
		    if(__t.relaDataset!=""){
		      var ownerDataset = Dataset.getDatasetByID(__t.relaDataset);
		      if(ownerDataset){
		        ownerDataset.propertyDataset = __dataset.datasetId;
		      }
		    }
		}else{
			__t.relaDataset = "";
		}	
	  
	    eval(__dataset.datasetId + "=__t");
	    // jsDebug.debug("...dataset000000 : "+((new Date())-startDate));
	    //var startDate = new Date();
	    var children = __dataset.children;
	
	    for (var i = 0; i < children.length; i++) {
	      var child = children[i];
	      if(child.nodeType!=1) continue;
	      
	    	if ((child.extra||child.tagName) == "parameter") {
	                var parameter = child;      
	                var __p = __t.parameters();
	                __p.setDataType(parameter.parameterId, parameter.type);
	                __p.setValue(parameter.parameterId, parameter.value);
	                
	        }else {
	            var field = child;    
	            
	            if(!field.type) field.type="string";
				if(field.type=="date"){
				  field.type="string";
				  field.dropDown="dropDownDate";
				}      
	                    
	            var __f = __t.addField({'fn':field.field,'dt': field.type}); 
	            
	            if(field.label)
	            __f.label = field.label; 
	            if(field.format)
	            __f.format = field.format;
	            if(field.visible) 
	            __f.visible = System.isTrue(field.visible);
	
	            if(field.readOnly)
	            __f.readOnly =  System.isTrue(field.readOnly);
	            if(field.editable)
	            __f.editable =  System.isTrue(field.editable);//�Ƿ���Ա༭���ֶ�
	            if(field.required)
	            __f.required = System.isTrue(field.required);
	            if(field.dropDown){
	            	__f.dropDown = field.dropDown;
	            }
	            
	            if(field.editorType)
	            __f.editorType = field.editorType;
	            
	            if(field.validType)
	            __f.validType = field.validType;
	            if(field.validMsg)
	            __f.validMsg = field.validMsg;       
	            
	            if(field.keyField) 
	            __f.keyField = field.keyField;
	            if(field.subList)         
	            __f.subList = field.subList;   
	            
	            if(field.popup)
	            __f.popup = field.popup; 
	            
	            if(field.attrCode){
	            	__f.dropDown = field.attrCode;
					LocalAction.getLocalAttrCode(__dataset, field.attrCode);
	            }
	        }    
	    
	    }	
	    //jsDebug.debug("...dataset1111111  :  "+((new Date())-startDate));
	    //var startDate = new Date();
	    fillDataset(__t);
	    //jsDebug.debug("...dataset22222222   :  "+((new Date())-startDate));
	    
	    if (__t.masterDataset) {
	       __t.setMasterDataset(__t.masterDataset, __t.masterKeyFields, __t.detailKeyFields);
	    }
	      
	    return __t;
	}	