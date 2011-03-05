ZX.Element = function(element){
    var dom = typeof element == "string" ?
              document.getElementById(element) : element;
    var id;
    if(!dom) 
    	return null;
    id = dom.id;
    this.dom = dom;
    this.id = id || ZX.id(dom);
};
ZX.Element.prototype = {
    set : function(o){
        var el = this.dom,
            attr,
            val;
        for(attr in o){
            val = o[attr];
            if (attr != "style" && !ZX.isFunction(val)) {
                if (attr == "cls" ) {
                    el.className = val;
                } else if (o.hasOwnProperty(attr)) {
                    if (!!el.setAttribute) el.setAttribute(attr, val);
                    else el[attr] = val;
                }
            }
        }
        return this;
    },
    getDom:function(){
    	return this.dom;
    },
    /*click,dblclick,mousedown,mouseup,mouseover,mousemove,mouseout
			mouseenter,mouseleave,keypress,keydown,keyup,load,unload,abort,error
			mouseenter,mouseleave,keypress,keydown,keyup,load,unload,abort,error,select,change,submit,focus,blur*/ 

    focus : function() {
        try{
            this.dom.focus();
        }catch(e){}
        return this;  
    },
    blur : function() {
        try{
            this.dom.blur();
        }catch(e){}
        return this;
    },
    setValue : function(val){
        //var val=asNumber ? parseInt(val, 10) : val;
        this.dom.value=val;
        
        if(val==''){
        	//对下拉框进行特殊处理,选中'--请选择--'
        	if(this.dom.type=='select-one'&&this.dom.options.length>0){
	        		var item = this.dom.options[0]; 
	        		item.selected=true;   
			}
        }
    },
	getFirstValue:function(){
		if(this.dom.type=='select-one'&&this.dom.options.length>0){
	        var item = this.dom.options[0];    
	        return item.value;
		}
		return null;
	},
    getValue : function(){
        var val = this.dom.value;
        return val;
    },
    hover : function(overFn, outFn){
        var me = this;
        me.on('mouseenter', overFn);
        me.on('mouseleave', outFn);
        return me;
    },
    getAttribute :function(name){
        return this.getAttribute(name);
    },
    update : function(html) {
        this.dom.innerHTML = html;
        return this;
    },
    
    setVisible:function(visible){
       this.dom.style.visibility=visible;
    },
    setDisabled:function(disabled){
         this.dom.disabled=disabled;
    },
    setReadonly:function(readonly){
        this.dom.readOnly=readonly;
    }
};
