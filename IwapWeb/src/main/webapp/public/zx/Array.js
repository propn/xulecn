ZX.Array=function(){
	this.items = [];
    this.map = {};
    this.keys = [];
    this.length = 0;
}
ZX.Array.prototype = {
     add: function(key, o){//长度为1时,表示添加数组对象 add的动作可能是add 也可能是update
        if(arguments.length == 1){
            o = arguments[0];
            key = this.getKey(o);
        }
        if(typeof key != 'undefined' && key !== null){
            var old = this.map[key];
            if(typeof old != 'undefined'){
                return this.replace(key, o); //直接返回
            }
            this.map[key] = o;
        }
        this.length++;
        this.items.push(o);
        this.keys.push(key);
        return o;
    },
    getKey : function(o){
         return o.id;
    },
    replace : function(key, o){
        if(arguments.length == 1){
            o = arguments[0];
            key = this.getKey(o);
        }
        var old = this.map[key];
        if(typeof key == "undefined" || key === null || typeof old == "undefined"){
             return this.add(key, o);
        }
        var index = this.indexOfKey(key);
        this.items[index] = o;
        this.map[key] = o;
        return o;
    },
    addAll : function(objs){
        if(arguments.length > 1 || ZX.isArray(objs)){
            var args = arguments.length > 1 ? arguments : objs;
            for(var i = 0, len = args.length; i < len; i++){
                this.add(args[i]);
            }
        }else{
            for(var key in objs){
                if(typeof objs[key] != "function"){
                    this.add(key, objs[key]);
                }
            }
        }
    },
    each : function(fn, scope){
        var items = [].concat(this.items);
        for(var i = 0, len = items.length; i < len; i++){
            if(fn.call(scope || items[i], items[i], i, len) === false){
                break;
            }
        }
    },
    eachKey : function(fn, scope){
        for(var i = 0, len = this.keys.length; i < len; i++){
            fn.call(scope || window, this.keys[i], this.items[i], i, len);
        }
    },
    find : function(fn, scope){
        for(var i = 0, len = this.items.length; i < len; i++){
            if(fn.call(scope || window, this.items[i], this.keys[i])){
                return this.items[i];
            }
        }
        return null;
    },
    insert : function(index, key, o){
        if(arguments.length == 2){
            o = arguments[1];
            key = this.getKey(o);
        }
        if(this.containsKey(key)){
            this.removeKey(key);
        }
        if(index >= this.length){ //大于指定的长度，直接插入返回
            return this.add(key, o);
        }
        this.length++;
        this.items.splice(index, 0, o); //插入元素
        if(typeof key != "undefined" && key !== null){
            this.map[key] = o;
        }
        this.keys.splice(index, 0, key);
        return o;
    },
    remove : function(o){
        return this.removeAt(this.indexOf(o));
    },
    removeAt : function(index){
        if(index < this.length && index >= 0){
            this.length--;
            var o = this.items[index];
            this.items.splice(index, 1); //移出一个特定的元素
            var key = this.keys[index];
            if(typeof key != "undefined"){
                delete this.map[key]; //删除json对象中的某一个元素
            }
            this.keys.splice(index, 1);
            return o;
        }
        return false;
    },
    removeKey : function(key){
        return this.removeAt(this.indexOfKey(key));
    },
    getCount : function(){
        return this.length;
    },
    indexOf : function(o){
        return this.items.indexOf(o); //ZX.indexOf保持一致
    },
    indexOfKey : function(key){ 
        return this.keys.indexOf(key); //返回int i
    },
    get : function(index){
        return this.items[index];
    },
    key : function(key){
        return this.map[key];
    },
    contains : function(o){
        return this.indexOf(o) != -1;
    },
    containsKey : function(key){
        return typeof this.map[key] != "undefined";
    },
    clear : function(){
		for(var i=0;i<this.items.length;i++){
			this.items[i]=null;
		}
        this.length = 0;
        this.items = [];
        this.keys = [];
        this.map = {};
    },
    first : function(){
        return this.items[0];
    },
    last : function(){
        return this.items[this.length-1];
    }
	
}