/*
 * 功能：跟踪一个函数执行所花费的时间 
 * 注意：请不要在设置onunload方法之前调用该方法; 如果必须这样，那么你可以
 * 手工调用save方法来保存跟踪结果
 */ 
function TimeLogger(allAjaxCall, globalName, globalComments)
{
	this.records = [];
	this.ajaxCalls = {}; // class.method = comments
	this.allAjaxCall = allAjaxCall||false;
	this.globalName = globalName;
	this.globalComments = globalComments;
	// 调度深度
	this.callDepth = 0;
	this.globalId = this.beg(this.globalName,  this.globalComments);
	// 在关闭窗口的时候保存数据
	var oldunload = window.onbeforeunload;
	var timeLogger = this;
	if(oldunload == null || oldunload == undefined){
		window.onbeforeunload =  function()
		{
			timeLogger.save();
		}; 
		return;
	}	
	// 如果原来已经有相应的事件处理器，我们不能破坏他。先调用应用程序的，因为多个ajax调用有问题，我们这个不怕被破坏
	window.onbeforeunload = function()
	{
		timeLogger.save();
		oldunload.apply(null, arguments);
	};
}

TimeLogger.isExtAjaxCall = false;

TimeLogger.prototype = {
	// 开始一次记录
	beg : function(name, comments)
	{
		var id = this.records.length;
		this.records[id] = {name : name, comments: comments, begTime : new Date(), callDepth :"" + this.callDepth, duaring : "0"};
		return id;
	},
	// 结束一次记录
	end : function (id){
		//this.records[id].endTime = new Date();
		this.records[id].duaring = "" + ( (new Date()).getTime() - this.records[id].begTime.getTime());
		delete this.records[id].begTime;
	},
	// 保存数据到服务端, 异步保存, 及时出错也不作任何处理。
	save : function()
	{
		this.end(this.globalId);
		function callBack(reply){}
		NDAjaxCall.getAsyncInstance().remoteCall("TimeLogger", "save", [this.records], callBack);
		this.records = [];	
		this.globalId = this.beg(this.globalName, this.globalComments);
	},
	// 扩展静态的全局函数
	extGlbFn : function(root, name, comments)
	{
		var timeLogger = this;
		var fn = root[name];
		root[name] = function()
		{
			timeLogger.callDepth = timeLogger.callDepth  + 1;
			var id = timeLogger.beg(name, comments);
			var ret = fn.apply(null, arguments);
			timeLogger.end(id);
			timeLogger.callDepth = timeLogger.callDepth - 1;			
			return ret;
		};
	},
	// 扩展类型成员函数
	extObjFn : function(obj, name, comments)
	{
		var timeLogger = this;
		var fn = obj[name];
		obj[name] = function()
		{
			timeLogger.callDepth = timeLogger.callDepth  + 1;
			var id = timeLogger.beg(name, comments);
			var ret = fn.apply(obj, arguments);
			timeLogger.end(id);
			timeLogger.callDepth = timeLogger.callDepth - 1;			
			return ret;
		};
	},
	// 扩展类成员函数, 在类定义上扩展
	extMemFn : function(cls, name, comments)
	{
		var timeLogger = this;
		var fn = cls.prototype[name];
		cls.prototype[name] = function()
		{
			timeLogger.callDepth = timeLogger.callDepth  + 1;
			var id = timeLogger.beg(name, comments);
			var ret = fn.apply(this, arguments);
			timeLogger.end(id);
			timeLogger.callDepth = timeLogger.callDepth - 1;		
			return ret;	
		};
	},
	
	extAJAXCallOnPageLoad : function()
	{
		var old_page_onLoad = window.page_onLoad;
		var timeLogger = this;
		window.page_onLoad = function()
		{
			timeLogger.extAJAXCall();
			if (old_page_onLoad != undefined && old_page_onLoad != null) return old_page_onLoad();
		};
	},
	
	// 扩展AJAX的remoteCall函数， 当回调函数被调用的时候，认为已经结束了，深度度不做处理。
	extAJAXCall : function()
	{
		// 只能扩展一次，如果第一个实例扩展了，那么第二个实例就不能扩展
		if (TimeLogger.isExtAjaxCall ) return;
		TimeLogger.isExtAjaxCall = true;
		
		var timeLogger = this;
		var oldFunc = NDAjaxCall.prototype.remoteCall;
		
		NDAjaxCall.prototype.remoteCall =  function()
		{
			var cls = arguments[0];
			var fnc = arguments[1];
			if (!timeLogger.allAjaxCall){
				var axcs = timeLogger.ajaxCalls;
				if (axcs[cls] == undefined || axcs[cls] == null){
					return oldFunc.apply(this, arguments);
				}
				
				var mayICall = axcs[cls]["allFunction"]||axcs[cls][fnc];				
				if (mayICall == undefined || mayICall == null){
					return oldFunc.apply(this, arguments);
				}	
		
				var comments =  axcs[cls][fnc]||axcs[cls]["allFunction"]||"";
			}else {
				var comments = "";
			}
			
			var name = "AJAXCall:" + cls + "." + fnc;
			var callInstance = {};
			callInstance.id = timeLogger.beg(name+".server", comments);
			callInstance.callBack = arguments[3]; 
			arguments[3] = function()
			{
				// 完成记录服务端的处理时间TimeLogger的保存也是调用了AJAX的，这一次不考虑在内。 在save的时候已经被清空了。
				if (timeLogger.records[callInstance.id] != undefined && timeLogger.records[callInstance.id] != null)
					timeLogger.end(callInstance.id);
				// 记录回调函数(客户端)的处理时间
				var clientDealId = timeLogger.beg(name+".client", comments)	
				var ret = callInstance.callBack.apply(null, arguments);
				timeLogger.end(clientDealId);
				return ret;
			};		
			return oldFunc.apply(this, arguments);
		};
	},
	// 把一个ajax调用添加到跟踪列表中, 如果func == allFunction, 表示类cls所有的方法都跟踪
	addAJAXCall : function(cls, func, comments)
	{
		if (this.ajaxCalls[cls] == undefined || this.ajaxCalls[cls] == null)
			this.ajaxCalls[cls] = {};
		this.ajaxCalls[cls][func] = comments||"";
	}
};