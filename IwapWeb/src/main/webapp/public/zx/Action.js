ZX.Action =function(config){
	this.id='';
	this.event = '';
	this.name='';
	this.action='';
	this.cvalue='';
	this.value='';
  ZX.apply(this, config);
  this.init();
}
ZX.extend(ZX.Action,ZX.Basis,{
		
    init:function(){
        ZX.Action.superclass.constructor.call(this);
        this.id=this.event+this.name+this.action+this.cvalue;
    },
    exe:function(field){
    		var bean=field.getBean();	
    		var act=(this.cvalue==field.getValue());	
    		var effField=bean.getField(this.name);
    		if(this.action=='link')
    				effField.setValue(act?this.value:'');
    		else if(this.action=='disable')
    				effField.setDisabled(act?true:false);
    		else if(this.action=='readonly')
    				effField.setReadonly(act?true:false);
    		else if(this.action=='visible')
    				effField.setVisible(act?true:false); 				
    },
    setBoxValue:function(){
    		 /*
    		 if (ele.type == "radio" || ele.type == "checkbox") {
			      if (nodes && nodes.length >= 1) {
			        for (var i = 0; i < nodes.length; i++) {
			          var node = nodes.item(i);
			          if (node.type != ele.type) continue;
			          if (ZX.isArray(val)) {
			            node.checked = false;
			            for (var j = 0; j < val.length; j++)
			              if (val[j] == node.value) node.checked = true;
			          }
			          else {
			            node.checked = (node.value == val);
			          }
			        }
			      }
			      else {
			        ele.checked = (val == true);
			      }
		    }*/
    },
    setSelectValue:function(){
    		 /*
    		 if (ele.type == "radio" || ele.type == "checkbox") {
			      if (nodes && nodes.length >= 1) {
			        for (var i = 0; i < nodes.length; i++) {
			          var node = nodes.item(i);
			          if (node.type != ele.type) continue;
			          if (ZX.isArray(val)) {
			            node.checked = false;
			            for (var j = 0; j < val.length; j++)
			              if (val[j] == node.value) node.checked = true;
			          }
			          else {
			            node.checked = (node.value == val);
			          }
			        }
			      }
			      else {
			        ele.checked = (val == true);
			      }
		    }*/
    }
    
});
