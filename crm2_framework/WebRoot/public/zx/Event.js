	//事件对象
ZX.Event=function(name){
		this.name=name;
		this.eventfns=new Array();	
}
	//增加事件的监听执行函数
ZX.Event.prototype.addEventFn=function(fn){
		this.eventfns.push(fn);
}
//获取事件的名称
ZX.Event.prototype.getEventName=function(){
	return this.name;
}
	//触发事件
	//{data}触发事件时所附带的数据
ZX.Event.prototype.fireEvent=function(data){
		for(var key in this.eventfns){
				this.eventfns[key](data);			
		}		
}